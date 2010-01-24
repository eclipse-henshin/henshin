package org.eclipse.emf.henshin.statespace.explorer.parts;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Viewport;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.explorer.jobs.LayoutStateSpaceJob;
import org.eclipse.emf.henshin.statespace.explorer.jobs.StateSpaceJobManager;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSpringLayouter;
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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;

/**
 * Composite for the tools menu in the state space explorer.
 * @author Christian Krause
 */
public class StateSpaceToolsMenu extends Composite {
	
	// Background color to be used:
	public static final Color BACKGROUND = new Color(null, 255, 255, 255);
	
	// Supported zoom levels:
	public static double[] ZOOM_LEVELS = {  .1, .15, .2, .25, .3, .35, .4, .45, .5, .55, 
											.6, .65, .7, .75, .8, .85, .9, .95, 1};
	
	// Minimum and maximum repulsion:
	public static final int LAYOUTER_PROPERTY_MIN = 10;
	public static final int LAYOUTER_PROPERTY_MAX = 100;
	
	public static final double REPULSION_FACTOR = 2;
	public static final double ATTRACTION_FACTOR = 0.1;
	
	public static final int NATURAL_LENGTH = 30;
	
	// Edit domain:
	private EditDomain editDomain;
	
	// State space job manager:
	private StateSpaceJobManager jobManager;
	
	// Labels:
	private Label statesLabel;
	private Label transitionsLabel;
		
	// ZoomManager:
	private ZoomManager zoomManager;
	private Scale zoomScale;
	
	// Canvas:
	private FigureCanvas canvas;

	// Layouter:
	private Button layouterCheckbox;
	private Scale repulsionScale;
	private Scale attractionScale;

	private Label rulesLabel;
	
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
		
		// The info group:
		Group details = new Group(this, SWT.NONE);
		details.setText("Information");
		details.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		details.setBackground(BACKGROUND);		
		details.setLayout(new GridLayout(2, false));
		
		newLabel(details, "Rules:", GridData.HORIZONTAL_ALIGN_END);
		rulesLabel = newLabel(details, "0", GridData.HORIZONTAL_ALIGN_BEGINNING);
		newLabel(details, "States:", GridData.HORIZONTAL_ALIGN_END);
		statesLabel = newLabel(details, "0", GridData.HORIZONTAL_ALIGN_BEGINNING);
		newLabel(details, "Transitions:", GridData.HORIZONTAL_ALIGN_END);
		transitionsLabel = newLabel(details, "0", GridData.HORIZONTAL_ALIGN_BEGINNING);
		
		// The zoom group:
		Group display = new Group(this, SWT.NONE);
		display.setText("Display");
		display.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		display.setBackground(BACKGROUND);		
		display.setLayout(new GridLayout(3, false));
		
		newLabel(display, "Zoom: " + (int) (ZOOM_LEVELS[0]*100) + "%", GridData.HORIZONTAL_ALIGN_END);
		
		zoomScale = new Scale(display, SWT.NONE);
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
		
		newLabel(display, (int) (ZOOM_LEVELS[ZOOM_LEVELS.length-1]*100) + "%", GridData.HORIZONTAL_ALIGN_BEGINNING);

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
				else if (jobManager!=null) jobManager.stopLayoutJob();
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
	
	/*
	 * Create a new label.
	 */
	private Label newLabel(Composite parent, String text, int align) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		label.setLayoutData(new GridData(align));
		label.setBackground(BACKGROUND);
		return label;
	}
	
	/*
	 * Create a new slider.
	 */
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
	
	/*
	 * Update the layouter properties.
	 */
	private void updateLayouterProperties() {
		
		if (jobManager==null) return;
		LayoutStateSpaceJob layoutJob = jobManager.getLayoutJob();
		if (layoutJob==null) return;

		StateSpaceSpringLayouter layouter = layoutJob.getLayouter();
		layouter.setStateRepulsion((int) (repulsionScale.getSelection() * REPULSION_FACTOR));
		layouter.setTransitionAttraction((int) (attractionScale.getSelection() * ATTRACTION_FACTOR));
		layouter.setNaturalTransitionLength(NATURAL_LENGTH);

		if (canvas!=null) {
			Viewport port = canvas.getViewport();
			int x = port.getHorizontalRangeModel().getValue() + (port.getHorizontalRangeModel().getExtent() / 2);
			int y = port.getVerticalRangeModel().getValue() + (port.getVerticalRangeModel().getExtent() / 2);
			layouter.setCenter(new int[] {x,y});
		} else {
			layouter.setCenter(null);
		}
		
	}
	
	/*
	 * Start the background spring layouter job.
	 */
	public void startLayouter() {
		layouterCheckbox.setSelection(jobManager!=null);
		if (jobManager!=null) {
			jobManager.startLayoutJob().addJobChangeListener(new JobChangeAdapter() {
				public void done(IJobChangeEvent event) {
					getDisplay().asyncExec(new Runnable() {
						public void run() {
							if (layouterCheckbox!=null && !layouterCheckbox.isDisposed()) {
								layouterCheckbox.setSelection(false);
							}
						}
					});
				}
			});
			updateLayouterProperties();
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
		}
	}

	/**
	 * Set the job manager to be used.
	 * @param manager Job manager.
	 */
	public void setJobManager(StateSpaceJobManager jobManager) {
		if (this.jobManager!=null) {
			this.jobManager.getStateSpaceManager().getStateSpace().eAdapters().remove(adapter);
		}
		this.jobManager = jobManager;
		if (jobManager!=null) {
			jobManager.getStateSpaceManager().getStateSpace().eAdapters().add(adapter);
		}
		refresh();
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

	private SelectionListener listener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent e) {
			updateLayouterProperties();
		}
		public void widgetSelected(SelectionEvent e) {
			updateLayouterProperties();
		}
	};

	private Listener listener2 = new Listener() {
		public void handleEvent(Event event) {
			updateLayouterProperties();
		}
	};
	
	public void setCanvas(FigureCanvas canvas) {
		this.canvas = canvas;
		canvas.getHorizontalBar().addSelectionListener(listener);
		canvas.getVerticalBar().addSelectionListener(listener);
		canvas.addListener(SWT.Resize, listener2);
	}
	
}
