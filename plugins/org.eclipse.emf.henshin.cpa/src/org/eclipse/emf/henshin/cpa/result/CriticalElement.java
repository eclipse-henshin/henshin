/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.cpa.result;

import org.eclipse.emf.henshin.model.GraphElement;

import agg.xt_basis.GraphObject;

/**
 * This class maps the different occurrences of each critical element.
 * 
 * @author Kristopher Born
 *
 */
public class CriticalElement {

	/**
	 * The critical element from within the AGG result.
	 */
	public GraphObject commonElementOfCriticalGraph;

	/**
	 * The occurrence of the critical element in the first rule.
	 */
	public GraphElement elementInFirstRule;

	/**
	 * The occurrence of the critical element in the second rule.
	 */
	public GraphElement elementInSecondRule;
}
