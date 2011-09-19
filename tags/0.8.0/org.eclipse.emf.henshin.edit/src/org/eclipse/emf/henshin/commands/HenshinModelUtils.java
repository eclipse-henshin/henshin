/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.commands;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * Utility class that provides static helper methods for:
 * <ul>
 * <li>generate names for nodes
 * <li>generate names for rules
 * </ul>
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack (sjurack)
 */
public abstract class HenshinModelUtils {
	
	protected static final int INITIAL_INDEX = 1;
	
	private static final String PREFIX_NODE = "node";
	private static final String PREFIX_RULE = "rule";
	
	protected HenshinModelUtils() {
	}
	
	/**
	 * Looks up the max Integer-parsable name and returns the next higher
	 * Integer value as the new name
	 */
	public static String generateNewNodeName(Graph graph) {
		int max = INITIAL_INDEX;
		for (Node node : graph.getNodes()) {
			String name = node.getName();
			name = name != null ? name : "";
			if (name.startsWith(PREFIX_NODE)) {
				try {
					int tmp = Integer.parseInt(name.substring(PREFIX_NODE.length()));
					if (tmp > max) {
						max = tmp;
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		return PREFIX_NODE + (++max);
	}
	
	/**
	 * Looks up the max Integer-parsable name and returns the next higher
	 * Integer value as the new name
	 */
	public static String generateNewRuleName(TransformationSystem tSys) {
		int max = INITIAL_INDEX;
		for (Rule rule : tSys.getRules()) {
			String name = rule.getName();
			name = name != null ? name : "";
			if (name.startsWith(PREFIX_RULE)) {
				try {
					int tmp = Integer.parseInt(name.substring(PREFIX_RULE.length()));
					if (tmp > max) {
						max = tmp;
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		return PREFIX_RULE + (++max);
	}
	
}
