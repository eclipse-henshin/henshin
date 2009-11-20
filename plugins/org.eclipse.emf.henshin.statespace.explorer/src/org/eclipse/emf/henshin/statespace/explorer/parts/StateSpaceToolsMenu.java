package org.eclipse.emf.henshin.statespace.explorer.parts;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.gef.EditDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

/**
 * Composite for the tools menu in the state space explorer.
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceToolsMenu extends Composite {
	
	// Background color to be used:
	private static final Color BACKGROUND = new Color(null, 255, 255, 255);
	
	// Edit domain:
	private EditDomain editDomain;
	
	// The state space:
	private StateSpace stateSpace;
		
	// Labels:
	private Label statesLabel;
	private Label transitionsLabel;
	
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
	 * Initialise the menu.
	 */
	private void init() {
		
		setBackground(BACKGROUND);
		setLayout(new GridLayout(1, false));
		
		Label label;
		
		// The info group:
		Group details = new Group(this, SWT.NONE);
		details.setText("Statespace Details");
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
	
	
	// State space adapter:
	private Adapter adapter = new AdapterImpl() {
		  public void notifyChanged(Notification event) {
			  refresh();
		  }
	};
}
