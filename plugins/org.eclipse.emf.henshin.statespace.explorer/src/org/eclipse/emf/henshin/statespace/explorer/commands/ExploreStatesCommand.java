package org.eclipse.emf.henshin.statespace.explorer.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.StateSpaceException;

/**
 * Command for exploring states.
 * @author Christian Krause
 */
public class ExploreStatesCommand extends AbstractStateSpaceCommand {
	
	// States to be explored.
	private List<State> states;
	
	/**
	 * Default constructor. Explores all open states.
	 * @param manager State space manager.
	 */
	public ExploreStatesCommand(StateSpaceManager manager) {
		this(manager, manager.getStateSpace().getOpenStates());
	}
	
	/**
	 * Constructor for exploring a single state.
	 * @param manager State space manager.
	 * @param state State to be explored.
	 */
	public ExploreStatesCommand(StateSpaceManager manager, State state) {
		this(manager, Collections.singletonList(state));
	}

	/**
	 * General constructor.
	 * @param manager State space manager.
	 * @param states States to be explored.
	 */
	public ExploreStatesCommand(StateSpaceManager manager, List<State> states) {
		super("explore states", manager);
		this.states = new ArrayList<State>(states);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.explorer.commands.AbstractStateSpaceCommand#doExecute()
	 */
	public void doExecute() throws StateSpaceException {
		for (State state : states) {
			getStateSpaceManager().exploreState(state);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return super.canExecute() && !states.isEmpty();
	}

}
