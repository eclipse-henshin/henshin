package org.eclipse.emf.henshin.statespace.explorer.edit;

import java.util.List;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.properties.PropertyChangeEvent;
import org.eclipse.emf.henshin.statespace.properties.PropertyChangeListener;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.swt.SWT;

/**
 * State space diagram edit part.
 * @author Christian Krause
 */
public class StateSpaceDiagramEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
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
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE,  new StateSpaceLayoutEditPolicy());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		
		Figure layer = new FreeformLayer();
		layer.setBorder(new MarginBorder(3));
		layer.setLayoutManager(new FreeformLayout());
		
		// Create the static router for the connection layer
		ConnectionLayer connectionLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
		connectionLayer.setConnectionRouter(new ShortestPathConnectionRouter(layer));
		connectionLayer.setAntialias(SWT.ON);
		return layer;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getStateSpace().removePropertyChangeListener(this);
		}
	}
	
	/**
	 * Get the state space corresponding to this edit part.
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
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePropertyChangeListener#propertyChanged(java.lang.Object, java.lang.String)
	 */
	public void propertyChanged(PropertyChangeEvent event) {
		if (event.getProperty()==StateSpace.STATES) {
			refreshChildren();
		}
	}

}