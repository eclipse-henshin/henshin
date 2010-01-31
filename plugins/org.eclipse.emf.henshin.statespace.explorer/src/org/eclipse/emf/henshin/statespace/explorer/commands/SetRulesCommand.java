package org.eclipse.emf.henshin.statespace.explorer.commands;

import java.util.List;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.StateSpaceException;

/**
 * @author Christian Krause
 */
public class SetRulesCommand extends AbstractStateSpaceCommand {
	
	// Rules to be used:
	private List<Rule> rules;

	/**
	 * Default constructor.
	 * @param manager Statespace manager.
	 */
	public SetRulesCommand(StateSpaceManager manager, List<Rule> rules) {
		super("set rules", manager);
		this.rules = rules;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.explorer.commands.AbstractStateSpaceCommand#doExecute()
	 */
	@Override
	public void doExecute() throws StateSpaceException {
		StateSpace stateSpace = getStateSpaceManager().getStateSpace();
		stateSpace.getRules().clear();
		stateSpace.getRules().addAll(rules);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return false; // command taints the state space.
	}

}
