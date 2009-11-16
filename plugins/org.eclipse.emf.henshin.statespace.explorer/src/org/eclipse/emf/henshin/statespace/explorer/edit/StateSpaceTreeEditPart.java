package org.eclipse.emf.henshin.statespace.explorer.edit;

import java.util.List;

import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.properties.PropertyChangeEvent;
import org.eclipse.emf.henshin.statespace.properties.PropertyChangeListener;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

/**
 * State space tree edit part.
 * @author Christian Krause
 */
public class StateSpaceTreeEditPart extends AbstractTreeEditPart implements PropertyChangeListener {

	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 */
	public StateSpaceTreeEditPart(StateSpace stateSpace) {
		super(stateSpace);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#activate()
	 */
	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getStateSpace().addPropertyChangeListener(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getStateSpace().removePropertyChangeListener(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		if (getParent() instanceof RootEditPart) {
			installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		}
	}

	/**
	 * Get the state space that this edit part belongs to.
	 * @return State space.
	 */
	public StateSpace getStateSpace() {
		return (StateSpace) getModel();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected List getModelChildren() {
		return getStateSpace().getStates();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.properties.PropertyChangeListener#propertyChanged(org.eclipse.emf.henshin.statespace.properties.PropertyChangeEvent)
	 */
	@Override
	public void propertyChanged(PropertyChangeEvent event) {		
		if (event.getProperty()==StateSpace.STATES) {
			if (getStateSpace().getStates().contains(event.getValue())) {
				addChild(createChild(event.getValue()), -1);
			} else {
				removeChild(getEditPartForChild(event.getValue()));				
			}
		} else {
			refreshVisuals();
		}
	}
	
	/*
	 * Convenience method that returns the edit part corresponding to a given child.
	 */
	private EditPart getEditPartForChild(Object child) {
		return (EditPart) getViewer().getEditPartRegistry().get(child);
	}

}
