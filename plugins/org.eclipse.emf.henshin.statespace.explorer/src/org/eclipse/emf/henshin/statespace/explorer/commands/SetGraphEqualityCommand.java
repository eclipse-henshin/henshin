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

import org.eclipse.emf.henshin.statespace.StateSpaceManager;

/**
 * Command for setting the graph equality property of state spaces.
 * This requires a reset of the state space, which is done automatically.
 * @author Christian Krause
 *
 */
public class SetGraphEqualityCommand extends ResetStateSpaceCommand {

	// Graph-equality property.
	private boolean graphEquality;

	public SetGraphEqualityCommand(StateSpaceManager manager, boolean graphEquality) {
		super(manager);
		setLabel("set equality type");
		this.graphEquality = graphEquality;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.explorer.commands.AbstractStateSpaceCommand#doExecute()
	 */
	@Override
	public void doExecute() { 
		
		// Set the graph-equality property:
		getStateSpaceManager().getStateSpace().setUseGraphEquality(graphEquality);
		
		// Now do a reset:
		super.doExecute();
	}
	
}
