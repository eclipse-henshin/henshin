package org.eclipse.emf.henshin.statespace.explorer.edit;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

/**
 * Edit part for transitions.
 * @generated NOT
 * @author Christian Krause
 */
public class TransitionEditPart extends AbstractConnectionEditPart {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
//			getTransition().eAdapters().add(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		if (isActive()) {
//			getTransition().eAdapters().remove(this);
			super.deactivate();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		// Makes the connection show a feedback, when selected by the user.
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		PolylineConnection connection = (PolylineConnection) super.createFigure();
		connection.setTargetDecoration(new PolygonDecoration());
		Label label = new Label(getTransition().getRule() + "!");
		connection.add(label);
		connection.getLayoutManager().setConstraint(label, new MidpointLocator(connection, 0) {
			
			@Override
			protected Point getReferencePoint() {
				Point p = super.getReferencePoint();
				p.y -= 10;
				return p;
			}

		});
		return connection;
	}

	public Transition getTransition() {
		return (Transition) getModel();
	}
	
}
