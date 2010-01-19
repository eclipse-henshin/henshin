package org.eclipse.emf.henshin.statespace.explorer.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

/**
 * A command for moving a state.
 * @author Christian Krause
 */
public class MoveStateCommand extends Command {
	
	// Locations:
	private Point oldLocation, newLocation;
	
	// Request to move/resize an edit part:
	private final ChangeBoundsRequest request;

	// State to be moved:
	private final State state;

	/**
	 * Default constructor.
	 */
	public MoveStateCommand(State state, ChangeBoundsRequest req, Rectangle newBounds) {
		if (state == null || req == null || newBounds == null) {
			throw new IllegalArgumentException();
		}
		this.state = state;
		this.request = req;
		this.newLocation = newBounds.getLocation();
		setLabel("move");
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		// Make sure the Request is of a type we support:
		Object type = request.getType();		
		return (RequestConstants.REQ_MOVE.equals(type)
			|| RequestConstants.REQ_MOVE_CHILDREN.equals(type));
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		int[] loc = state.getLocation();
		oldLocation = new Point(loc[0], loc[1]);
		redo();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		state.setLocation(newLocation.x, newLocation.y);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		state.setLocation(oldLocation.x, oldLocation.y);
	}
	
}
