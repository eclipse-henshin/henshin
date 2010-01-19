package org.eclipse.emf.henshin.statespace.explorer.commands;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.gef.commands.Command;

/**
 * Command for creating an initial state.
 * @author Christian Krause
 */
public class CreateInitialStateCommand extends Command {
	
	// State space manager.
	private final StateSpaceManager manager;

	// State to be added.
	private State state;
	
	// State model:
	private Resource model;
		
	// State coordinates:
	private int[] location;
	
	/**
	 * Default constructor.
	 * @param state State to be added.
	 * @param stateSpace State space.
	 */
	public CreateInitialStateCommand(Resource model, StateSpaceManager manager) {
		this.model = model;
		this.manager = manager;
		setLabel("adding state");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return model!=null && manager!=null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		redo();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		State state = manager.createInitialState(model);
		state.setLocation(location);	
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		manager.removeState(state);
	}
	
	/**
	 * Set the state coordinates.
	 */
	public void setLocation(int... location) {
		this.location = location;
	}
	
}