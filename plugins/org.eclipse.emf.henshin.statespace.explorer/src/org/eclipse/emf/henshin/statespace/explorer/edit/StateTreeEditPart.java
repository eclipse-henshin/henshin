package org.eclipse.emf.henshin.statespace.explorer.edit;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.properties.PropertyChangeEvent;
import org.eclipse.emf.henshin.statespace.properties.PropertyChangeListener;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;

/**
 * TreeEditPart used for State instances (more specific for EllipticalShape and
 * RectangularShape instances). This is used in the Outline View of the ShapesEditor.
 * <p>This edit part must implement the PropertyChangeListener interface, 
 * so it can be notified of property changes in the corresponding model element.
 * </p>
 * 
 * @author Elias Volanakis
 */
public class StateTreeEditPart extends AbstractTreeEditPart implements PropertyChangeListener {

	/**
	 * Create a new instance of this edit part using the given model element.
	 * @param model a non-null Shapes instance
	 */
	StateTreeEditPart(State model) {
		super(model);
	}

	/**
	 * Upon activation, attach to the model element as a property change listener.
	 */
	public void activate() {
		if (!isActive()) {
			super.activate();
			getState().addPropertyChangeListener(this);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new StateComponentEditPolicy());
	}

	/**
	 * Upon deactivation, detach from the model element as a property change listener.
	 */
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getState().removePropertyChangeListener(this);
		}
	}

	private State getState() {
		return (State) getModel();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getText()
	 */
	protected String getText() {
		return getState().getName()!=null ? getState().getName() : "s" + getState().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePropertyChangeListener#propertyChanged(org.eclipse.emf.henshin.statespace.StateSpacePropertyChangeEvent)
	 */
	public void propertyChanged(PropertyChangeEvent event) {
		refreshVisuals();
	}
}