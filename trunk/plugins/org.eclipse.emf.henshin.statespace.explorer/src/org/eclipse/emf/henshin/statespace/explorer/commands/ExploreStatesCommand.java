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

import java.util.ArrayList;
import java.util.Collection;
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
	
	// Whether to generate locations for new states:
	private boolean generateLocations = true;
	
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
	public ExploreStatesCommand(StateSpaceManager manager, Collection<State> states) {
		super("explore states", manager);
		this.states = new ArrayList<State>(states);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.explorer.commands.AbstractStateSpaceCommand#doExecute()
	 */
	public void doExecute() throws StateSpaceException {
		getStateSpaceManager().exploreStates(states, generateLocations);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return false;
	}
	
	public List<State> getStatesToExplore() {
		return states;
	}
	
	public void setGenerateLocations(boolean generateLocations) {
		this.generateLocations = generateLocations;
	}
	
}
