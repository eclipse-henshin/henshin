package org.eclipse.emf.henshin.statespace.explorer.commands;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.gef.commands.Command;

/**
 * Command for creating a new transition.
 * @author Christian Krause
 * 
 */
public class TransitionCreateCommand extends Command {
	
	// Transition to be created.
	private Transition connection;
	
	// Source and target.
	private State source, target;

	/**
	 * Constructor.
	 * @param source Source of the transition.
	 */
	public TransitionCreateCommand(State source) {
		if (source == null) {
			throw new IllegalArgumentException();
		}
		setLabel("transition creation");
		this.source = source;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return true;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		connection = new Transition(source, target, "test");
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		connection.reconnect();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		connection.disconnect();
	}

	/**
	 * Set the target endpoint for the transition.
	 */
	public void setTarget(State target) {
		this.target = target;
	}

}
