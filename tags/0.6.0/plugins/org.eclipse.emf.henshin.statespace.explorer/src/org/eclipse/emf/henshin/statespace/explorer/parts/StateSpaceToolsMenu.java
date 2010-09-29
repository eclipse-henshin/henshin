/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.explorer.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Viewport;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.henshin.statespace.StateEqualityHelper;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;
import org.eclipse.emf.henshin.statespace.explorer.commands.SetGraphEqualityCommand;
import org.eclipse.emf.henshin.statespace.explorer.jobs.LayoutStateSpaceJob;
import org.eclipse.emf.henshin.statespace.explorer.jobs.StateSpaceJobManager;
import org.eclipse.emf.henshin.statespace.explorer.jobs.ValidateStateSpaceJob;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSpringLayouter;
import org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator;
import org.eclipse.emf.henshin.statespace.validation.StateValidator;
import org.eclipse.emf.henshin.statespace.validation.ValidationResult;
import org.eclipse.emf.henshin.statespace.validation.Validator;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Text;

/**
 * Composite for the tools menu in the state space explorer.
 * @author Christian Krause
 */
public class StateSpaceToolsMenu extends Composite {
	
	// Supported zoom levels:
	public static double[] ZOOM_LEVELS = {  .1, .15, .2, .25, .3, .35, .4, .45, .5, .55, 
											.6, .65, .7, .75, .8, .85, .9, .95, 1};
	
	public static final double REPULSION_FACTOR = 2;
	public static final double ATTRACTION_FACTOR = 0.05;
	
	public static final int NATURAL_LENGTH = 25;
	
	// Edit domain:
	private EditDomain editDomain;
	
	// State space job manager:
	private StateSpaceJobManager jobManager;

	// State space explorer instance:
	private StateSpaceExplorer explorer;

	// Labels:
	private Label statesLabel;
	private Label transitionsLabel;
	private Label rulesLabel;
		
	// ZoomManager:
	private ZoomManager zoomManager;
	private Scale zoomScale;
	
	// Canvas:
	private FigureCanvas canvas;
	
	// Scales:
	private Scale repulsionScale;
	private Scale attractionScale;
	
	// Check boxes:
	private Button layouterCheckbox;
	private Button explorerCheckbox;
	private Button ignoreAttributesCheckbox;

	// Radio-buttons:
	private Button ecoreButton;
	private Button graphButton;

	// Normal buttons:
	private Button validateButton;
	
	// Text widget for validation property:
	private Text validationText;
	
	// Combo for choosing validator:
	private Combo validatorCombo;

	// List of registered validators.
	private List<Validator> validators;
	
	
	/**
	 * Default constructor
	 * @param parent Parent composite.
	 */
	public StateSpaceToolsMenu(Composite parent, EditDomain editDomain) {
		super(parent, SWT.NONE);
		this.editDomain = editDomain;
		init();
	}
	
	/*
	 * Initialize the menu.
	 */
	private void init() {
		
		setLayout(new FillLayout());
		
		// Create expand bar:
		ExpandBar bar = new ExpandBar(this, SWT.V_SCROLL);
		
		// The details group:
		Composite details = StateSpaceToolsMenuFactory.newExpandItemComposite(bar,2);
		statesLabel = StateSpaceToolsMenuFactory.newDoubleLabel(details, "States:", "0");
		transitionsLabel = StateSpaceToolsMenuFactory.newDoubleLabel(details, "Transitions:", "0");
		rulesLabel = StateSpaceToolsMenuFactory.newDoubleLabel(details, "Rules:", "0");
		StateSpaceToolsMenuFactory.newLabel(details, "Equality:", GridData.HORIZONTAL_ALIGN_END);
		Composite radioButtons = new Composite(details, SWT.NONE);
		radioButtons.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout(2,false);
		layout.marginHeight = layout.marginWidth = 0;
		radioButtons.setLayout(layout);
		ecoreButton = new Button(radioButtons, SWT.RADIO);
		ecoreButton.setText("Ecore");
		graphButton = new Button(radioButtons, SWT.RADIO);
		graphButton.setText("Graph");
		StateSpaceToolsMenuFactory.newLabel(details, "Attributes:", GridData.HORIZONTAL_ALIGN_END);
		ignoreAttributesCheckbox = new Button(details, SWT.CHECK);
		ignoreAttributesCheckbox.setText("Ignore");
		ignoreAttributesCheckbox.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		StateSpaceToolsMenuFactory.newExpandItem(bar, details, "Details", 0);		
		
		// The display group:
		Composite display = StateSpaceToolsMenuFactory.newExpandItemComposite(bar,3);
		StateSpaceToolsMenuFactory.newLabel(display, "Zoom: " + (int) (ZOOM_LEVELS[0]*100) + "%", GridData.HORIZONTAL_ALIGN_END);
		zoomScale = new Scale(display, SWT.NONE);
		zoomScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		zoomScale.setEnabled(false);
		zoomScale.setIncrement(1);
		zoomScale.setPageIncrement(2);
		zoomScale.setMinimum(0);
		zoomScale.setMaximum(ZOOM_LEVELS.length-1);
		zoomScale.setSelection(ZOOM_LEVELS.length-1);
		StateSpaceToolsMenuFactory.newLabel(display, (int) (ZOOM_LEVELS[ZOOM_LEVELS.length-1]*100) + "%", GridData.HORIZONTAL_ALIGN_BEGINNING);
		StateSpaceToolsMenuFactory.newExpandItem(bar, display, "Display", 1);
		
		// The actions group:
		Composite actions = StateSpaceToolsMenuFactory.newExpandItemComposite(bar,3);
		explorerCheckbox = new Button(actions, SWT.CHECK);
		explorerCheckbox.setText("Run state space explorer");
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		explorerCheckbox.setLayoutData(data);
		layouterCheckbox = new Button(actions, SWT.CHECK);
		layouterCheckbox.setText("Run spring layouter");
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		layouterCheckbox.setLayoutData(data);
		repulsionScale = StateSpaceToolsMenuFactory.newScale(actions, "State repulsion:", 1, 100, 5, 10, true, null);
		attractionScale = StateSpaceToolsMenuFactory.newScale(actions, "Transition attraction:", 1, 100, 5, 10, true, null);
		StateSpaceToolsMenuFactory.newExpandItem(bar, actions, "Actions", 2);
		
		// The validation group:
		Composite validation = StateSpaceToolsMenuFactory.newExpandItemComposite(bar,2);
		validationText = StateSpaceToolsMenuFactory.newMultiText(validation, 2, 80);
		validatorCombo = new Combo(validation, SWT.BORDER);
		validatorCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		validateButton = StateSpaceToolsMenuFactory.newButton(validation, "Validate");
		StateSpaceToolsMenuFactory.newExpandItem(bar, validation, "Validation", 3);

		initControlsData();
		setEnabled(false);
		
	}
	
	// Some preference keys:
	private static final String VALIDATOR_KEY = "validator";
	private static final String VALIDATION_PROPERTY_KEY = "validationProperty";
	
	/*
	 * Initialize the controls with their required data.
	 */
	private void initControlsData() {
		
		// Load the validators. We make new instances.
		validators = new ArrayList<Validator>();
		String lastId = getPreferenceStore().getString(VALIDATOR_KEY);
		Validator lastValidator = null;
		for (Validator validator : StateSpacePlugin.INSTANCE.getValidators().values()) {
			if (lastId!=null && StateSpacePlugin.INSTANCE.getValidators().get(lastId)==validator) {
				lastValidator = validator;
			}
			try {
				Validator old = validator;
				validator = validator.getClass().newInstance();
				if (lastValidator==old) lastValidator = validator;
			} catch (Throwable t) {
				StateSpaceExplorerPlugin.getInstance().logError("Validator cannot be reinstantiated", t);
			}
			validators.add(validator);
		}
		Collections.sort(validators, new Comparator<Validator>() {
			public int compare(Validator v1, Validator v2) {
				String n1 = v1.getName();
				String n2 = v2.getName();
				return (n1!=null && n2!=null) ? n1.compareTo(n2) : 0;
			}
		});
		for (Validator current : validators) {
			String name = current.getName();
			if (!(current instanceof StateSpaceValidator) && (current instanceof StateValidator)) {
				name = name + " (invariant)";
			}
			validatorCombo.add(name);
		}
		validatorCombo.select((lastValidator!=null) ? validators.indexOf(lastValidator) : 0);
		
		// Validation property:
		String property = getPreferenceStore().getString(VALIDATION_PROPERTY_KEY);
		validationText.setText(property!=null ? property : "");
		
	}	
	
	/*
	 * Update the layouter properties.
	 */
	private void updateLayouterProperties() {
		
		// Get the layouter:
		if (jobManager==null) return;
		LayoutStateSpaceJob layoutJob = jobManager.getLayoutJob();
		StateSpaceSpringLayouter layouter = layoutJob.getLayouter();
		StateSpace stateSpace = explorer.getStateSpaceManager().getStateSpace();
		
		// Set basic properties:
		layouter.setStateRepulsion(((double) stateSpace.getStateRepulsion()+10) * REPULSION_FACTOR);
		layouter.setTransitionAttraction(((double) stateSpace.getTransitionAttraction()+40) * ATTRACTION_FACTOR);
		layouter.setNaturalTransitionLength(NATURAL_LENGTH);

		double zoom = ZOOM_LEVELS[stateSpace.getZoomLevel() * (ZOOM_LEVELS.length-1) / 100];
		if (zoomManager!=null) {
			zoomManager.setZoom(zoom);
		}

		// Set the center:
		if (canvas!=null) {
			Viewport port = canvas.getViewport();
			double x = (port.getHorizontalRangeModel().getValue() + (port.getHorizontalRangeModel().getExtent() / 2)) / zoom;
			double y = (port.getVerticalRangeModel().getValue() + (port.getVerticalRangeModel().getExtent() / 2)) / zoom;
			layouter.setCenter(new double[] {x,y});
		} else {
			layouter.setCenter(null);
		}		
		
	}
	
	private void commitMetadata() {
		StateSpace stateSpace = explorer.getStateSpaceManager().getStateSpace();
		stateSpace.setZoomLevel((zoomScale.getSelection()+1) * 100 / ZOOM_LEVELS.length);
		stateSpace.setStateRepulsion(repulsionScale.getSelection());
		stateSpace.setTransitionAttraction(attractionScale.getSelection());
	}

	/**
	 * Refresh the tools menu content.
	 */
	public void refresh() {
		if (jobManager==null) {
			statesLabel.setText("0");
			transitionsLabel.setText("0");
			rulesLabel.setText("0");
		} else {
			StateSpace stateSpace = jobManager.getStateSpaceManager().getStateSpace();
			StateEqualityHelper helper = stateSpace.getEqualityHelper();
			statesLabel.setText(stateSpace.getStates().size() + " (" + stateSpace.getOpenStates().size() + " open)");
			transitionsLabel.setText(stateSpace.getTransitionCount() + "");
			rulesLabel.setText(stateSpace.getRules().size() + "");
			graphButton.setSelection(helper.isGraphEquality());
			ecoreButton.setSelection(!helper.isGraphEquality());
			ignoreAttributesCheckbox.setSelection(helper.isIgnoreAttributes());
		}
	}
	
	private void updateScales() {
		StateSpace stateSpace = jobManager.getStateSpaceManager().getStateSpace();
		zoomScale.setSelection(stateSpace.getZoomLevel() * ZOOM_LEVELS.length / 100);
		repulsionScale.setSelection(stateSpace.getStateRepulsion());
		attractionScale.setSelection(stateSpace.getTransitionAttraction());		
	}
	
	/**
	 * Set the job manager to be used.
	 * @param manager Job manager.
	 */
	public void setJobManager(StateSpaceJobManager jobManager) {
		if (this.jobManager!=null) removeListeners();
		this.jobManager = jobManager;
		setEnabled(jobManager!=null);
		refresh();
		updateScales();
		if (jobManager!=null) addListeners();
	}	
	
	/**
	 * Enable or disable this menu.
	 */
	public void setEnabled(boolean enabled) {
		layouterCheckbox.setEnabled(enabled);
		explorerCheckbox.setEnabled(enabled);
		repulsionScale.setEnabled(enabled);
		attractionScale.setEnabled(enabled);
		graphButton.setEnabled(enabled);
		ecoreButton.setEnabled(enabled);
		validateButton.setEnabled(enabled);
		validatorCombo.setEnabled(enabled);
		validationText.setEnabled(enabled);
		ignoreAttributesCheckbox.setEnabled(enabled);
	}
	
	/**
	 * Set the zoom manager to be used.
	 * @param zoomManager Zoom manager.
	 */
	public void setZoomManager(ZoomManager zoomManager) {
		this.zoomManager = zoomManager;
		zoomScale.setEnabled(zoomManager!=null);
		if (zoomManager!=null) {
			zoomManager.setZoomLevels(ZOOM_LEVELS);
			zoomManager.setZoomAnimationStyle(ZoomManager.ANIMATE_ZOOM_IN_OUT);
		}
	}
	
	/**
	 * Set the used figure canvas for the explorer.
	 * @param canvas Figure canvas.
	 */
	public void setCanvas(FigureCanvas canvas) {
		this.canvas = canvas;
		canvas.getHorizontalBar().addSelectionListener(scrollBarListener);
		canvas.getVerticalBar().addSelectionListener(scrollBarListener);
		canvas.addListener(SWT.Resize, canvasListener);
	}
	
	/**
	 * Set the state space explorer to be used.
	 * @param explorer Explorer instance.
	 */
	public void setExplorer(StateSpaceExplorer explorer) {
		this.explorer = explorer;
	}
	
	/*
	 * Change the graph-equality property.
	 */
	private void setEqualityType(boolean graphEquality, boolean ignoreAttributes) {
		StateSpaceManager manager = jobManager.getStateSpaceManager();
		StateEqualityHelper helper = manager.getStateSpace().getEqualityHelper();
		if (graphEquality!=helper.isGraphEquality() || ignoreAttributes!=helper.isIgnoreAttributes()) {
			
			StateSpace stateSpace = manager.getStateSpace();
			boolean confirmed = stateSpace.getStates().size()==stateSpace.getInitialStates().size() ||
								MessageDialog.openConfirm(getShell(), "Reset required", 
								"Changing the equality type requires a reset of the state space. Continue?");
			
			if (confirmed) {
				// Execute as command:
				Command command = new SetGraphEqualityCommand(manager, graphEquality, ignoreAttributes);
				editDomain.getCommandStack().execute(command);
			}
			refresh();
		}
	}
	
	/*
	 * Called when the validation job has finished.
	 */
	private void validationFinished(ValidationResult result, IStatus status) {
		if (status.isOK() && result!=null) {
			if (result.getTrace()!=null && explorer!=null) {
				explorer.selectTrace(result.getTrace());
			}
			if (result.isValid()) {
				MessageDialog.openInformation(getShell(), "Validation", "Property satified.");
			} else {
				MessageDialog.openError(getShell(), "Validation", "Property not satified.");					
			}
		}
		validateButton.setEnabled(true);		
	}

	private Validator getActiveValidator() {
		int index = validatorCombo.getSelectionIndex();
		return (index>=0) ? validators.get(index) : null;
	}

	private IPreferenceStore getPreferenceStore() {
		return StateSpaceExplorerPlugin.getInstance().getPreferenceStore();
	}
	
	// ------------------- //
	// ---- LISTENERS ---- // 
	// ------------------- //
	
	/*
	 * Add all listeners.
	 */
	private void addListeners() {
		jobManager.getStateSpaceManager().getStateSpace().eAdapters().add(adapter);
		repulsionScale.addSelectionListener(layouterScaleListener);
		attractionScale.addSelectionListener(layouterScaleListener);
		zoomScale.addSelectionListener(zoomListener);
		explorerCheckbox.addSelectionListener(explorerListener);
		layouterCheckbox.addSelectionListener(layouterListener);
		graphButton.addSelectionListener(equalityTypeListener);
		ignoreAttributesCheckbox.addSelectionListener(equalityTypeListener);
		validateButton.addSelectionListener(validateListener);
		validationText.addModifyListener(validationTextListener);
		validatorCombo.addSelectionListener(validatorComboListener);
		
		addButtonJobListener(jobManager.getLayoutJob(), layouterCheckbox);
		addButtonJobListener(jobManager.getExploreJob(), explorerCheckbox);
		final ValidateStateSpaceJob validateJob = jobManager.getValidateJob();
		addButtonJobListener(validateJob, validateButton);
		validateJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(final IJobChangeEvent event) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						validationFinished(validateJob.getValidationResult(), event.getResult());
					}
				});
			}			
		});
	}
	
	/*
	 * Remove all listeners.
	 */
	private void removeListeners() {
		jobManager.getStateSpaceManager().getStateSpace().eAdapters().remove(adapter);	
		repulsionScale.removeSelectionListener(layouterScaleListener);
		attractionScale.removeSelectionListener(layouterScaleListener);
		zoomScale.removeSelectionListener(zoomListener);
		graphButton.removeSelectionListener(equalityTypeListener);
		ignoreAttributesCheckbox.removeSelectionListener(equalityTypeListener);
		explorerCheckbox.removeSelectionListener(explorerListener);
		layouterCheckbox.removeSelectionListener(layouterListener);
		validateButton.removeSelectionListener(validateListener);
		validationText.removeModifyListener(validationTextListener);
		validatorCombo.removeSelectionListener(validatorComboListener);
	}
	
	/*
	 *  State space adapter.
	 */
	private Adapter adapter = new AdapterImpl() {
		  public void notifyChanged(Notification event) {
			  refresh();
		  }
	};
	
	/*
	 * Selection listeners for layouter scales.
	 */
	private SelectionListener layouterScaleListener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent e) {
			commitMetadata();
			updateLayouterProperties();
		}
		public void widgetSelected(SelectionEvent e) {
			commitMetadata();
			updateLayouterProperties();
		}
	};

	
	/*
	 * Selection listeners for the scroll bars.
	 */
	private SelectionListener scrollBarListener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent e) {
			updateLayouterProperties();
		}
		public void widgetSelected(SelectionEvent e) {
			updateLayouterProperties();
		}
	};

	
	/*
	 * Canvas listener.
	 */
	private Listener canvasListener = new Listener() {
		public void handleEvent(Event event) {
			updateLayouterProperties();
		}
	};
	
	/*
	 * Selection listener for graph equality radio buttons.
	 */
	private SelectionListener equalityTypeListener = new SelectionListener() {
		public void widgetSelected(SelectionEvent e) {
			setEqualityType(graphButton.getSelection(), ignoreAttributesCheckbox.getSelection());
		}
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}
	};

	/*
	 * Zoom listener.
	 */
	private SelectionListener zoomListener = new SelectionListener() {
		public void widgetSelected(SelectionEvent e) {
			commitMetadata();
			updateLayouterProperties();
		}
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}
	};
	
	/*
	 * Explorer checkbox  listener.
	 */
	private SelectionListener explorerListener = new SelectionListener() {			
		public void widgetSelected(SelectionEvent e) {
			if (jobManager==null) return;
			if (explorerCheckbox.getSelection()) {
				jobManager.startExploreJob();
			} else {
				jobManager.getExploreJob().cancel();
			}
		}
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}
	};

	/*
	 * Layouter checkbox listener.
	 */
	private SelectionListener layouterListener = new SelectionListener() {
		public void widgetSelected(SelectionEvent e) {
			if (jobManager==null) return;
			if (layouterCheckbox.getSelection()) {
				updateLayouterProperties();
				jobManager.startLayoutJob();
			} else {
				jobManager.stopLayoutJob();
			}
		}
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}
	};

	/*
	 * Explorer checkbox listener.
	 */
	private SelectionListener validateListener = new SelectionListener() {			
		public void widgetSelected(SelectionEvent e) {
			if (jobManager==null) return;
			validateButton.setEnabled(false);
			jobManager.getValidateJob().setProperty(validationText.getText());
			jobManager.getValidateJob().setValidator(getActiveValidator());
			jobManager.startValidateJob();
		}
		public void widgetDefaultSelected(SelectionEvent e) {
		}
	};

	/*
	 * Add a job listener for a (check-box) button.
	 */
	private void addButtonJobListener(Job job, final Button checkbox) {
		job.addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						if (checkbox!=null && !checkbox.isDisposed()) {
							checkbox.setEnabled(true);    // normal buttons
							checkbox.setSelection(false); // check-boxes
						}
					}
				});
			}
		});		
	}
	
	/*
	 * Modify listener for the validation property.
	 */
	private ModifyListener validationTextListener = new ModifyListener() {
		public void modifyText(ModifyEvent e) {
			getPreferenceStore().setValue(VALIDATION_PROPERTY_KEY, validationText.getText());
			Validator validator = getActiveValidator();
			for (String id : StateSpacePlugin.INSTANCE.getValidators().keySet()) {
				if (StateSpacePlugin.INSTANCE.getValidators().get(id)==validator) {
					getPreferenceStore().setValue(VALIDATOR_KEY, id);
					break;
				}
			}
		}
	};
	
	/*
	 * Selection listener for the validator combo.
	 */
	private SelectionListener validatorComboListener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent e) {
		}
		public void widgetSelected(SelectionEvent e) {
			Validator validator = getActiveValidator();
			for (String id : StateSpacePlugin.INSTANCE.getValidators().keySet()) {
				if (StateSpacePlugin.INSTANCE.getValidators().get(id)==validator) {
					getPreferenceStore().setValue(VALIDATOR_KEY, id);
					break;
				}
			}
		}
	};

}