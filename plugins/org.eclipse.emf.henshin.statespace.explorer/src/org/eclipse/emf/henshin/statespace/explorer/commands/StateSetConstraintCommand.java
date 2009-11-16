package org.eclipse.emf.henshin.statespace.explorer.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

/**
 * A command to resize and/or move a shape.
 */
public class StateSetConstraintCommand extends Command {
	
	private Point oldLocation, newLocation;
	
	/** A request to move/resize an edit part. */
	private final ChangeBoundsRequest request;

	/** State to manipulate. */
	private final State shape;

	/**
	 * Create a command that can resize and/or move a shape. 
	 * @param shape	the shape to manipulate
	 * @param req		the move and resize request
	 * @param newBounds the new size and location
	 * @throws IllegalArgumentException if any of the parameters is null
	 */
	public StateSetConstraintCommand(State shape, ChangeBoundsRequest req, 
			Rectangle newBounds) {
		if (shape == null || req == null || newBounds == null) {
			throw new IllegalArgumentException();
		}
		this.shape = shape;
		this.request = req;
		this.newLocation = newBounds.getLocation();
		setLabel("move / resize");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	public boolean canExecute() {
		Object type = request.getType();
		// make sure the Request is of a type we support:
		return (RequestConstants.REQ_MOVE.equals(type)
				|| RequestConstants.REQ_MOVE_CHILDREN.equals(type) 
				|| RequestConstants.REQ_RESIZE.equals(type)
				|| RequestConstants.REQ_RESIZE_CHILDREN.equals(type));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		oldLocation = new Point(shape.getX(), shape.getY());
		redo();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		shape.setXY(newLocation.x, newLocation.y);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		shape.setXY(oldLocation.x, oldLocation.y);
	}
}
