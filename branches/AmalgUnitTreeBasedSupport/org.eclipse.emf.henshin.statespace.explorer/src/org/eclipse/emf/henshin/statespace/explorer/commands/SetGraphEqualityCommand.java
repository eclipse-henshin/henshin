/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
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

	private boolean graphEquality;
	private boolean ignoreAttributes;

	public SetGraphEqualityCommand(StateSpaceManager manager, boolean graphEquality, boolean ignoreAttributes) {
		super(manager);
		setLabel("set equality type");
		this.graphEquality = graphEquality;
		this.ignoreAttributes = ignoreAttributes;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.explorer.commands.AbstractStateSpaceCommand#doExecute()
	 */
	@Override
	public void doExecute() { 
		
		// Set the graph-equality property:
		getStateSpaceManager().getStateSpace().getEqualityHelper().setGraphEquality(graphEquality);
		getStateSpaceManager().getStateSpace().getEqualityHelper().setIgnoreAttributes(ignoreAttributes);
		
		// Now do a reset:
		super.doExecute();
	}
	
}
