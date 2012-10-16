/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.statespace;

/**
 * Keys for common state space properties.
 * 
 * @author Christian Krause
 */
public class StateSpaceProperties {

	/**
	 * Key for a state space property that determines whether the order of links matters.
	 */
	public static final String CHECK_LINK_ORDER = "checkLinkOrder";

	/**
	 * Key for a state space property that defines a list of types for which object identity matters.
	 */
	public static final String IDENTITY_TYPES = "identityTypes";

	/**
	 * Key for a state space property that defines a list of attributes whose values should be ignored.
	 */
	public static final String IGNORED_ATTRIBUTES = "ignoredAttributes";

	/**
	 * Key for a state space property that defines a list of clock attributes.
	 */
	public static final String CLOCK_DECLARATIONS = "clockDeclarations";
	
	/**
	 * Key for a state space property that decides whether clocks should be used.
	 */
	public static final String USE_CLOCKS = "useClocks";
	
	/**
	 * Key for a state space property that determines whether missing root objects should be collected.
	 */
	public static final String COLLECT_MISSING_ROOTS = "collectMissingRoots";

	/**
	 * Key for a state space property that determines whether duplicate transitions should be ignored.
	 * Transitions are duplicate if their sources, targets and rules are respectively the same. 
	 */
	public static final String IGNORE_DUPLICATE_TRANSITIONS = "ignoreDuplicateTransitions";

}
