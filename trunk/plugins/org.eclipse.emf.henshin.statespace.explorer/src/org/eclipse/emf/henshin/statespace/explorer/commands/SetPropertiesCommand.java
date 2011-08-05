package org.eclipse.emf.henshin.statespace.explorer.commands;

import java.util.Map;

import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;

/**
 * Set the properties of a state space.
 * @author Christian Krause
 */
public class SetPropertiesCommand extends AbstractStateSpaceCommand {
	
	// New properties to be set:
	private Map<String,String> properties;
	
	// New maximum state distance:
	private Integer maxStateDistance;
	
	/**
	 * Default constructor.
	 * @param manager Statespace manager.
	 */
	public SetPropertiesCommand(StateSpaceManager manager, Map<String,String> properties) {
		super("set properties", manager);
		this.properties = properties;
	}
	
	public void setMaxStateDistance(int maxStateDistance) {
		this.maxStateDistance = maxStateDistance;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.explorer.commands.AbstractStateSpaceCommand#doExecute()
	 */
	@Override
	public void doExecute() throws StateSpaceException {
		StateSpace stateSpace = getStateSpaceManager().getStateSpace();
		stateSpace.getProperties().clear();
		stateSpace.getProperties().putAll(properties);
		if (maxStateDistance!=null) {
			stateSpace.setMaxStateDistance(maxStateDistance);
		}
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
