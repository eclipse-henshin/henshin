package org.eclipse.emf.henshin.statespace.explorer.parts;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Viewport;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.commands.SetGraphEqualityCommand;
import org.eclipse.emf.henshin.statespace.explorer.jobs.LayoutStateSpaceJob;
import org.eclipse.emf.henshin.statespace.explorer.jobs.StateSpaceJobManager;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSpringLayouter;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;

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
	
	public static final int NATURAL_LENGTH = 20;
	
	// Edit domain:
	private EditDomain editDomain;
	
	// State space job manager:
	private StateSpaceJobManager jobManager;
	
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
	private Scale delayScale;
	
	// Check boxes:
	private Button layouterCheckbox;
	private Button explorerCheckbox;

	// Radio-buttons
	private Button ecoreButton;
	private Button graphButton;

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

		
		// The explorer group:
		Composite explorer = StateSpaceToolsMenuFactory.newExpandItemComposite(bar,3);
		explorerCheckbox = new Button(explorer, SWT.CHECK);
		explorerCheckbox.setText("Run explorer");
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		explorerCheckbox.setLayoutData(data);
		StateSpaceToolsMenuFactory.newLabel(explorer, "Equality:", GridData.HORIZONTAL_ALIGN_BEGINNING);
		Composite radioButtons = new Composite(explorer, SWT.NONE);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		radioButtons.setLayoutData(data);
		radioButtons.setLayout(new GridLayout(2,false));
		ecoreButton = new Button(radioButtons, SWT.RADIO);
		ecoreButton.setText("Ecore");
		graphButton = new Button(radioButtons, SWT.RADIO);
		graphButton.setText("Graph");
		delayScale = StateSpaceToolsMenuFactory.newScale(explorer, "Delay:", 0, 1000, 50, 100, false, "ms");
		StateSpaceToolsMenuFactory.newExpandItem(bar, explorer, "Explorer", 2);
		
		// The layouter group:
		Composite layouter = StateSpaceToolsMenuFactory.newExpandItemComposite(bar,3);
		layouterCheckbox = new Button(layouter, SWT.CHECK);
		layouterCheckbox.setText("Run spring layouter");
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		layouterCheckbox.setLayoutData(data);
		repulsionScale = StateSpaceToolsMenuFactory.newScale(layouter, "State repulsion:", 10, 100, 5, 10, true, null);
		attractionScale = StateSpaceToolsMenuFactory.newScale(layouter, "Transition attraction:", 10, 100, 5, 10, true, null);
		StateSpaceToolsMenuFactory.newExpandItem(bar, layouter, "Layouter", 3);
		
		setEnabled(false);
	}
	
	
	
	/*
	 * Update the layouter properties.
	 */
	private void updateLayouterProperties() {
		
		// Get the layouter:
		if (jobManager==null) return;
		LayoutStateSpaceJob layoutJob = jobManager.getLayoutJob();
		StateSpaceSpringLayouter layouter = layoutJob.getLayouter();
		
		// Set basic properties:
		layouter.setStateRepulsion((int) (repulsionScale.getSelection() * REPULSION_FACTOR));
		layouter.setTransitionAttraction((int) (attractionScale.getSelection() * ATTRACTION_FACTOR));
		layouter.setNaturalTransitionLength(NATURAL_LENGTH);
		
		// Set the center:
		if (canvas!=null) {
			Viewport port = canvas.getViewport();
			int x = port.getHorizontalRangeModel().getValue() + (port.getHorizontalRangeModel().getExtent() / 2);
			int y = port.getVerticalRangeModel().getValue() + (port.getVerticalRangeModel().getExtent() / 2);
			layouter.setCenter(new int[] {x,y});
		} else {
			layouter.setCenter(null);
		}
		
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
			statesLabel.setText(stateSpace.getStates().size() + " (" + stateSpace.getOpenStates().size() + " open)");
			transitionsLabel.setText(stateSpace.getTransitionCount() + "");
			rulesLabel.setText(stateSpace.getRules().size() + "");
			graphButton.setSelection(stateSpace.isUseGraphEquality());
			ecoreButton.setSelection(!stateSpace.isUseGraphEquality());
		}
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
		canvas.getHorizontalBar().addSelectionListener(layouterScaleListener);
		canvas.getVerticalBar().addSelectionListener(layouterScaleListener);
		canvas.addListener(SWT.Resize, canvasListener);
	}
	
	/*
	 * Change the graph-equality property.
	 */
	private void setGraphEquality(boolean graphEquality) {
		StateSpaceManager manager = jobManager.getStateSpaceManager();
		if (graphEquality!=manager.getStateSpace().isUseGraphEquality()) {
			boolean confirmed = MessageDialog.openConfirm(getShell(), "Reset required", 
					"Changing the equality type requires a reset of the state space. Continue?");
			if (confirmed) {
				// Execute as command:
				Command command = new SetGraphEqualityCommand(manager,graphEquality);
				editDomain.getCommandStack().execute(command);
			}
			refresh();
		}
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
		delayScale.addSelectionListener(delayListener);
		explorerCheckbox.addSelectionListener(explorerListener);
		layouterCheckbox.addSelectionListener(layouterListener);
		graphButton.addSelectionListener(graphEqualityListener);
		addJobListener(jobManager.getLayoutJob(), layouterCheckbox);
		addJobListener(jobManager.getExploreJob(), explorerCheckbox);
	}
	
	/*
	 * Remove all listeners.
	 */
	private void removeListeners() {
		jobManager.getStateSpaceManager().getStateSpace().eAdapters().remove(adapter);	
		repulsionScale.removeSelectionListener(layouterScaleListener);
		attractionScale.removeSelectionListener(layouterScaleListener);
		delayScale.removeSelectionListener(delayListener);
		zoomScale.removeSelectionListener(zoomListener);
		graphButton.removeSelectionListener(graphEqualityListener);
		explorerCheckbox.removeSelectionListener(explorerListener);
		layouterCheckbox.removeSelectionListener(layouterListener);
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
	private SelectionListener graphEqualityListener = new SelectionListener() {
		public void widgetSelected(SelectionEvent e) {
			setGraphEquality(graphButton.getSelection());
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
			if (zoomManager!=null) {
				zoomManager.setZoom(ZOOM_LEVELS[zoomScale.getSelection()]);
			}
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
				jobManager.stopExploreJob();
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
	 * Delay scale listener:
	 */
	private SelectionListener delayListener = new SelectionListener() {
		public void widgetSelected(SelectionEvent e) {
			jobManager.getExploreJob().setDelay(delayScale.getSelection());
		}
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}		
	};
	
	/*
	 * Add a job listener.
	 */
	private void addJobListener(Job job, final Button checkbox) {
		job.addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						if (checkbox!=null && !checkbox.isDisposed()) {
							checkbox.setSelection(false);
						}
					}
				});
			}
		});		
	}
	
}
