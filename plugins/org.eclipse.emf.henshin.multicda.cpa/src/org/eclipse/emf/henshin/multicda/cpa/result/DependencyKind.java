/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa.result;

/**
 * This class provides the different kinds of supported dependencies
 * 
 * @author Kristopher Born
 */
public enum DependencyKind {

	DELETE_FORBID_DEPENDENCY("delete-forbid-dependency"), PRODUCE_USE_DEPENDENCY("produce-use-dependency"), CHANGE_USE_ATTR_DEPENDENCY(
			"change-use-attr-dependency"), CHANGE_FORBID_ATTR_DEPENDENCY("change-forbid-attr-dependency");

	private String name;

	/**
	 * Default internal constructor.
	 * 
	 * @param name The name of the dependency kind.
	 */
	private DependencyKind(String name) {
		this.name = name;
	}

	/**
	 * @return The name of the dependency kind.
	 */
	public String toString() {
		return name;
	}

	/**
	 * commented out dependencies are in stock for future use. If necessary they will be implemented and reactivated
	 */
	// DELETE_FORBID_DEPENDENCY // r1.LHS --> r2.NAC
	// PRODUCE_USE_DEPENDENCY // r1.RHS --> r2.LHS
	// PRODUCE_NEED_DEPENDENCY // r1.RHS --> r2.PAC
	// CHANGE_USE_ATTR_DEPENDENCY // r1.LHS --> r2.LHS
	// CHANGE_NEED_ATTR_DEPENDENCY // r1.LHS --> r2.LHS + PAC
	// CHANGE_FORBID_ATTR_DEPENDENCY // r1.LHS --> r2.NAC
	// PRODUCE_DELETE_DEPENDENCY // r2 deletes, inverse r1 preserves/produces
	// FORBID_PRODUCE_DEPENDENCY // r2 produces, inverse r1 forbids
	// PRODUCE_CHANGE_DEPENDENCY // r2 changes, inverse r1 changes

}
