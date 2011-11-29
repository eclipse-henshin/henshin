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

import org.eclipse.emf.henshin.statespace.StateEqualityHelper;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;

/**
 * Command for setting the graph equality property of state spaces.
 * This requires a reset of the state space, which is done automatically.
 * @author Christian Krause
 *
 */
public class SetGraphEqualityCommand extends ResetStateSpaceCommand {

	private boolean useGraphEquality;
	private boolean useObjectIdentities;
	private boolean useObjectAttributes;

	public SetGraphEqualityCommand(StateSpaceManager manager, 
			boolean useGraphEquality, 
			boolean useObjectIdentites,
			boolean useObjectAttributes) {
		
		super(manager);
		setLabel("set equality type");
		this.useGraphEquality = useGraphEquality;
		this.useObjectIdentities = useObjectIdentites;
		this.useObjectAttributes = useObjectAttributes;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.explorer.commands.AbstractStateSpaceCommand#doExecute()
	 */
	@Override
	public void doExecute() throws StateSpaceException { 
		
		StateEqualityHelper helper = getStateSpaceManager().getStateSpace().getEqualityHelper();
		
		// Set the graph-equality property:
		helper.setUseGraphEquality(useGraphEquality);
		helper.setUseObjectIdentities(useObjectIdentities);
		helper.setUseObjectAttributes(useObjectAttributes);
		
		// Now do a reset:
		super.doExecute();
	}
	
}
