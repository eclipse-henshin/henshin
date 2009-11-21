package org.eclipse.emf.henshin.statespace.explorer.parts;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.explorer.actions.StateSpaceLayouterJob;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

/**
 * Composite for the tools menu in the state space explorer.
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceToolsMenu extends Composite {
	
	// Background color to be used:
	public static final Color BACKGROUND = new Color(null, 255, 255, 255);
	
	// Supported zoom levels:
	public static double[] ZOOM_LEVELS = { .1, .2, .3, .4, .5, .6, .7, .8, .9, 1};
	
	// Minimum and maximum repulsion:
	public static final int LAYOUTER_PROPERTY_MIN = 10;
	public static final int LAYOUTER_PROPERTY_MAX = 100;
	
	public static final double REPULSION_FACTOR = 2;
	public static final double ATTRACTION_FACTOR = 0.2;
	
	public static final int NATURAL_LENGTH = 40;
	
	// Edit domain:
	private EditDomain editDomain;
	
	// The state space:
	private StateSpace stateSpace;
		
	// Labels:
	private Label statesLabel;
	private Label transitionsLabel;
		
	// ZoomManager:
	private ZoomManager zoomManager;
	private Scale zoomScale;

	// Layouter:
	private Button layouterCheckbox;
	private StateSpaceLayouterJob layouterJob;
	private Scale repulsionScale;
	private Scale attractionScale;
	
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
		
		setBackground(BACKGROUND);
		setLayout(new GridLayout(1, false));
		
		Label label;
		
		// The info group:
		Group details = new Group(this, SWT.NONE);
		details.setText("Information");
		details.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		details.setBackground(BACKGROUND);		
		details.setLayout(new GridLayout(2, false));
		
		label = new Label(details, SWT.NONE);
		label.setText("States:");
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		label.setBackground(BACKGROUND);
		
		statesLabel = new Label(details, SWT.NONE);
		statesLabel.setText("0");
		statesLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		statesLabel.setBackground(BACKGROUND);

		label = new Label(details, SWT.NONE);
		label.setText("Transitions:");
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		label.setBackground(BACKGROUND);
		
		transitionsLabel = new Label(details, SWT.NONE);
		transitionsLabel.setText("0");
		transitionsLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		transitionsLabel.setBackground(BACKGROUND);

		// The zoom group:
		Group zoom = new Group(this, SWT.NONE);
		zoom.setText("Zoom");
		zoom.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		zoom.setBackground(BACKGROUND);		
		zoom.setLayout(new GridLayout(3, false));
		
		label = new Label(zoom, SWT.NONE);
		label.setText((int) (ZOOM_LEVELS[0]*100) + "%");
		label.setBackground(BACKGROUND);
		
		zoomScale = new Scale(zoom, SWT.NONE);
		zoomScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		zoomScale.setBackground(BACKGROUND);
		zoomScale.setEnabled(false);
		zoomScale.setIncrement(1);
		zoomScale.setPageIncrement(2);
		zoomScale.setMinimum(0);
		zoomScale.setMaximum(ZOOM_LEVELS.length-1);
		zoomScale.setSelection(ZOOM_LEVELS.length-1);
		zoomScale.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (zoomManager!=null) {
					int index = zoomScale.getSelection();
					zoomManager.setZoom(ZOOM_LEVELS[index]);
				}
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		label = new Label(zoom, SWT.NONE);
		label.setText((int) (ZOOM_LEVELS[ZOOM_LEVELS.length-1]*100) + "%");
		label.setBackground(BACKGROUND);

		// The layouter group:
		Group layouter = new Group(this, SWT.NONE);
		layouter.setText("Layouter");
		layouter.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		layouter.setBackground(BACKGROUND);
		layouter.setLayout(new GridLayout(3, false));
		
		layouterCheckbox = new Button(layouter, SWT.CHECK);
		layouterCheckbox.setText("Run spring layouter");
		layouterCheckbox.setBackground(BACKGROUND);
		layouterCheckbox.addSelectionListener(new SelectionListener() {			
			public void widgetSelected(SelectionEvent e) {
				if (layouterCheckbox.getSelection()) startLayouter();
				else stopLayouter();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		layouterCheckbox.setLayoutData(data);
		
		repulsionScale = newLayoutSlider(layouter, "State repulsion:", LAYOUTER_PROPERTY_MIN, LAYOUTER_PROPERTY_MAX);
		attractionScale = newLayoutSlider(layouter, "Transition attraction:", LAYOUTER_PROPERTY_MIN, LAYOUTER_PROPERTY_MAX);

	}
	
	private Scale newLayoutSlider(Composite parent, String name, int min, int max) {

		Label label = new Label(parent, SWT.NONE);
		label.setText(name);
		label.setBackground(BACKGROUND);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		label.setLayoutData(data);
		
		label = new Label(parent, SWT.NONE);
		label.setText(min + "");
		label.setBackground(BACKGROUND);
		
		final Scale scale = new Scale(parent, SWT.NONE);
		scale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		scale.setBackground(BACKGROUND);
		scale.setIncrement(1);
		scale.setPageIncrement(2);
		scale.setMinimum(min);
		scale.setMaximum(max);
		scale.setSelection((max-min) / 2);
		scale.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				updateLayouterProperties();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				updateLayouterProperties();
			}
		});
		
		label = new Label(parent, SWT.NONE);
		label.setText(max + "");
		label.setBackground(BACKGROUND);
		return scale;
		
	}
	
	private void updateLayouterProperties() {
		if (layouterJob!=null) {
			layouterJob.getLayouter().setStateRepulsion((int) (repulsionScale.getSelection() * REPULSION_FACTOR));
			layouterJob.getLayouter().setTransitionAttraction((int) (attractionScale.getSelection() * ATTRACTION_FACTOR));
			layouterJob.getLayouter().setNaturalTransitionLength(NATURAL_LENGTH);
		}
	}
	
	public void startLayouter() {
		layouterCheckbox.setSelection(true);
		layouterJob = new StateSpaceLayouterJob(stateSpace, Display.getCurrent());
		updateLayouterProperties();
		layouterJob.schedule();
	}
	
	public void stopLayouter() {
		layouterJob.cancel();
		layouterCheckbox.setSelection(false);
	}
	
	public void refresh() {
		
		// Number of states and transitions:
		statesLabel.setText(stateSpace.getStates().size() + " (" + stateSpace.getExploredCount() + " explored)");
		transitionsLabel.setText(stateSpace.getTransitionCount() + "");
		
	}

	
	/**
	 * Set the state space to be used.
	 * @param stateSpace State space.
	 */
	public void setStateSpace(StateSpace stateSpace) {
		
		// Unhook the adapter from the old state space:
		if (this.stateSpace!=null) {
			this.stateSpace.eAdapters().remove(adapter);
		}
		
		// Set the state space:
		this.stateSpace = stateSpace;
		
		// Refresh:
		if (stateSpace!=null) {
			stateSpace.eAdapters().add(adapter);
			refresh();
		}
		
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
	
	// State space adapter:
	private Adapter adapter = new AdapterImpl() {
		  public void notifyChanged(Notification event) {
			  refresh();
		  }
	};
	
}
