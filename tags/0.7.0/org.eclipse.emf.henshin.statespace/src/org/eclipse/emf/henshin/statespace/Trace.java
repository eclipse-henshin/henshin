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
package org.eclipse.emf.henshin.statespace;

import java.util.ArrayDeque;

/**
 * Data type for traces.
 * @generated NOT
 */
public class Trace extends ArrayDeque<Transition> {
	
	// Default serial ID:
	private static final long serialVersionUID = 1L;
	
	// Source / target state, for empty traces:
	private State state;
	
	/**
	 * Default constructor.
	 */
	public Trace() {
		super();
	}
	
	/**
	 * Alternative constructor.
	 */
	public Trace(State state) {
		super();
		setState(state);
	}
	
	/**
	 * Alternative constructor.
	 * @param transitions Transitions.
	 */
	public Trace(Transition... transitions) {
		super();
		for (Transition transition : transitions) {
			addLast(transition);
		}
	}

	public State getSource() {
		return isEmpty() ? state : getFirst().getSource();
	}
	
	public State getTarget() {
		return isEmpty() ? state : getLast().getTarget();
	}
		
	public void setState(State state) {
		this.state = state;
	}
	
}