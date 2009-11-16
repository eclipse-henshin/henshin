package org.eclipse.emf.henshin.statespace.explorer.commands;

import java.util.List;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.gef.commands.Command;

/**
 * State deletion command.
 * @author Christian Krause
 */
public class StateDeleteCommand extends Command {
	
	// State to be deleted:
	private State state;
	
	// State space to deleted from:
	private StateSpace stateSpace;
	
	// Incoming and outgoing transitions:
	private List<Transition> outgoing, incoming;
	
	// Done flag:
	private boolean done;
	
	/**
	 * Default constructor.
	 * @param state State to be deleted.
	 * @param stateSpace State space.
	 */
	public StateDeleteCommand(State state, StateSpace stateSpace) {
		this.stateSpace = stateSpace;
		this.state = state;
		setLabel("state deletion");
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return done;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		outgoing = state.getOutgoing();
		incoming = state.getIncoming();
		redo();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		done = stateSpace.getStates().remove(state);
		if (done) {
			disconnect(outgoing);
			disconnect(incoming);
		}	
	}


	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if (stateSpace.getStates().add(state)) {
			reconnect(outgoing);
			reconnect(incoming);
		}
	}
	
	/*
	 * Disconnect a list of transitions.
	 */
	private void disconnect(List<Transition> transitions) {
		for (Transition transition : transitions) {
			transition.disconnect();
		}
	}
	
	/*
	 * Reconnect a list of transitions.
	 */
	private void reconnect(List<Transition> transitions) {
		for (Transition transition: transitions) {
			transition.reconnect();
		}
	}
	
}