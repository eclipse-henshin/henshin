/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
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
