package org.eclipse.emf.henshin.statespace.explorer.edit;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.explorer.commands.StateCreateCommand;
import org.eclipse.emf.henshin.statespace.explorer.commands.StateSetConstraintCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.swt.SWT;

/**
 * State space layout edit policy.
 * @author Christian Krause
 */
public class StateSpaceLayoutEditPolicy extends XYLayoutEditPolicy {

	/* 
	 * (non-Javadoc)
	 * @see ConstrainedLayoutEditPolicy#createChangeConstraintCommand(ChangeBoundsRequest, EditPart, Object)
	 */
	@Override
	protected Command createChangeConstraintCommand(ChangeBoundsRequest request, EditPart child, Object constraint) {
		
		if (child instanceof StateDiagramEditPart && 
			constraint instanceof Rectangle) {
			
			State state = ((StateDiagramEditPart) child).getState();
			Rectangle bounds = (Rectangle) constraint;
			return new StateSetConstraintCommand(state, request, bounds);
			
		} else {
			return super.createChangeConstraintCommand(request, child, constraint);
		}
		
	}

	/* 
	 * (non-Javadoc)
	 * @see ConstrainedLayoutEditPolicy#createChangeConstraintCommand(EditPart, Object)
	 */
	@Override
	protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
		return null;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see LayoutEditPolicy#getCreateCommand(CreateRequest)
	 */
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Object type = request.getNewObjectType();
		
		if (type==State.class) {
			StateSpace stateSpace = (StateSpace) getHost().getModel();
			State state = (State) request.getNewObject();
			Rectangle bounds = (Rectangle) getConstraintFor(request);
			StateCreateCommand command = new StateCreateCommand(state, stateSpace);
			command.setXY(bounds.x, bounds.y);
			return command;
		}
		
		return null;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChildEditPolicy(org.eclipse.gef.EditPart)
	 */
	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		return new NonResizableEditPolicy() {
			
			@Override
			protected IFigure createDragSourceFeedbackFigure() {
				
				// Use a ghost ellipse for feedback:
				Ellipse ghost = new Ellipse();
				ghost.setAntialias(SWT.ON);				
				FigureUtilities.makeGhostShape(ghost);
				ghost.setLineStyle(Graphics.LINE_DOT);
				ghost.setForegroundColor(ColorConstants.white);
				ghost.setBounds(getInitialFeedbackBounds());
				addFeedback(ghost);
				return ghost;
				
			}
			
		};
	}

}
