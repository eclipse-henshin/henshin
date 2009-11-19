package org.eclipse.emf.henshin.statespace.explorer.commands;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.gef.commands.Command;

/**
 * A command for removing a transition.
 * The command can be undone and redone.
 * @author Christian Krause
 */
public class TransitionDeleteCommand extends Command {

	// Transition instance:
	private final Transition transition;
	
	// Source and target of the transition:
	private State source, target;
	
	/**
	 * Default constructor.
	 */
	public TransitionDeleteCommand(Transition transition) {
		if (transition == null) {
			throw new IllegalArgumentException();
		}
		this.transition = transition;
		setLabel("transition deletion");
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		source = transition.getSource();
		target = transition.getTarget();
		redo();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		transition.setSource(null);
		transition.setTarget(null);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		transition.setSource(source);
		transition.setTarget(target);
	}
	
}
