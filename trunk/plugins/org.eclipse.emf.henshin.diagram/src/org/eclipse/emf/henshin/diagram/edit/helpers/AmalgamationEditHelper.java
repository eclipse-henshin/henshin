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
package org.eclipse.emf.henshin.diagram.edit.helpers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;

/**
 * Helper methods for handling amalgamation units in
 * the graphical editor.
 * @author Christian Krause
 */
public class AmalgamationEditHelper {
	
	/**
	 * Prefix for multi-rule names. This is to distinguish multi-rules
	 * from kernel rules easily.
	 */
	public static final String MULTI_RULE_NAME_PREFIX = "_";
	
	/**
	 * Find the default amalgamation unit for a given kernel rule.
	 * The amalgamation unit must have the argument rule as kernel
	 * rule and moreover the same name as the rule.
	 * @param kernel Kernel rule of the amalgamation.
	 * @return Amalgamation unit, if found.
	 */
	public static AmalgamationUnit getAmalgamationFromKernelRule(Rule kernel) {
		
		// Rule must be contained in a transformation system.
		TransformationSystem system = kernel.getTransformationSystem();
		if (system==null) {
			return null;
		}
		
		// Name of the kernel rule must be set.
		String name = kernel.getName();
		if (name==null) {
			return null;
		}
		
		// Find an amalgamation unit with the same name and the given kernel rule.
		for (TransformationUnit unit : system.getTransformationUnits()) {
			if (unit instanceof AmalgamationUnit && name.equals(unit.getName())) {
				AmalgamationUnit amalgamation = (AmalgamationUnit) unit;
				if (amalgamation.getKernelRule()==kernel) {
					return amalgamation;
				}
			}
		}
		
		// None found.
		return null;
	}

	/**
	 * Get the default amalgamation unit from a given multi-rule.
	 * @see #getAmalgamationFromKernelRule(Rule)
	 * @param rule Multi-rule.
	 * @return The amalgamation unit if found.
	 */
	public static AmalgamationUnit getAmalgamationFromMultiRule(Rule rule) {
		
		// Rule must be contained in a transformation system.
		TransformationSystem system = rule.getTransformationSystem();
		if (system==null) {
			return null;
		}
		
		for (Rule kernel : system.getRules()) {
			if (kernel==rule) continue;
			AmalgamationUnit amalgamation = getAmalgamationFromKernelRule(kernel);
			if (amalgamation!=null && amalgamation.getMultiRules().contains(rule)) {
				return amalgamation;
			}
		}
		
		// Not a multi-rule of any of the default amalgamations.
		return null;

	}

	
	/**
	 * Check whether a given rule is a multi-rule for any of
	 * the default amalgamation units in the container 
	 * transformation system.
	 * @param rule Rule to be checked.
	 * @return <code>true</code> if it is a multi-rule for a default amalgamation.
	 * @see #getAmalgamationFromKernelRule(Rule)
	 */
	public static boolean isMultiRule(Rule rule) {
		return (getAmalgamationFromMultiRule(rule)!=null);
	}
	
	/**
	 * Check whether a rule is a trivial multi-rule. A multi-rule
	 * is trivial if all elements in its LHS/RHS have a pre-image
	 * in the kernel rule's LHS/RHS.
	 * @param rule Rule to be checked.
	 * @return <code>true</code> if it is trivial.
	 */
	public static boolean isTrivialMultiRule(Rule rule) {
		
		// Get the amalgamation unit. Must be not null.
		AmalgamationUnit amalgamation = getAmalgamationFromMultiRule(rule);
		if (amalgamation==null) {
			return false;
		}
		
		// Create a list of all elements in the multi-rule:
		List<GraphElement> elements = new ArrayList<GraphElement>();
		elements.addAll(rule.getLhs().getNodes());
		elements.addAll(rule.getLhs().getEdges());
		elements.addAll(rule.getRhs().getNodes());
		elements.addAll(rule.getRhs().getEdges());
		
		// Check if there have preimages.
		for (GraphElement element : elements) {
			if (hasPreimageInKernelRule(element, amalgamation))
				return false;
		}
		
		// No reason why it shouldn't be trivial.
		return true;
		
	}
	
	/**
	 * Check if a graph element in a multi-rule has a preimage in the
	 * kernel rule. This throws exceptions if something is wrong.
	 * @param element Graph element.
	 * @return <code>true</code> if it has a preimage.
	 */
	public static boolean hasPreimageInKernelRule(GraphElement element, AmalgamationUnit amalgamation) {
		
		// Check the element first:
		if (element==null || 
			amalgamation==null ||
			element.getGraph()==null || 
			element.getGraph().getContainerRule()==null ||
			amalgamation.getMultiRules().contains(element.getGraph().getContainerRule())) {
			throw new IllegalArgumentException();
		}
		
		Graph graph = element.getGraph();
		Rule rule = graph.getContainerRule();
		
		if (graph==rule.getLhs()) {
			return (HenshinMappingUtil.getOrigin(element,
					amalgamation.getLhsMappings()) == null);
		}
		else if (graph==rule.getRhs()) {
			return (HenshinMappingUtil.getOrigin(element,
					amalgamation.getRhsMappings()) == null);
		}
		else {
			throw new IllegalArgumentException("Element is neither part of the LHS nor the RHS");
		}

	}
	
	/**
	 * Get a multi-rule by its name.
	 * @param kernel Kernel rule.
	 * @param name Name of the multi-rule.
	 * @return The multi-rule, if found.
	 */
	public static Rule getMultiRule(Rule kernel, String name) {
		
		// Get the amalgamation unit.
		AmalgamationUnit amalgamation = getAmalgamationFromKernelRule(kernel);
		if (amalgamation==null) {
			return null;
		}
		
		// Find the multi-rule:
		name = MULTI_RULE_NAME_PREFIX + name;
		for (Rule multi : amalgamation.getMultiRules()) {
			if (name.equals(multi.getName())) return multi;
		}
		
		// No matching multi-rule found.
		return null;
		
	}
	
	/**
	 * Get the display name of a multi-rule. If it is the default
	 * multi-rule, the empty string is returned.
	 * @param rule Multi-rule.
	 * @return Its display name.
	 */
	public static String getMultiRuleName(Rule rule, AmalgamationUnit amalgamation) {
		if (rule.getName()==null) return null;
		String name = rule.getName();
		if (name.startsWith(MULTI_RULE_NAME_PREFIX)) {
			name = name.substring(MULTI_RULE_NAME_PREFIX.length());
		}
		if (name.equals(amalgamation.getName())) {
			name = "";
		}
		return name;
	}
	
	/**
	 * Get the default multi-rule for a given kernel rule.
	 * The default multi-rule has the same name as the kernel
	 * rule, but prefixed by "{@value #MULTI_RULE_NAME_PREFIX}".
	 * @param kernel Kernel rule
	 * @return Default multi-rule, if found.
	 */
	public static Rule getDefaultMultiRule(Rule kernel) {
		if (kernel.getName()==null) {
			return null;
		} else {
			return getMultiRule(kernel, kernel.getName());
		}
	}
	
	/**
	 * Rename a kernel rule.
	 * @param kernel Kernel rule.
	 * @param newName New name of the rule.
	 */
	public static void renameKernelRule(Rule kernel, String newName) {
		
		// Check if there is default amalgamation / multi-rule:
		AmalgamationUnit amalgamation = getAmalgamationFromKernelRule(kernel);
		Rule multi = getDefaultMultiRule(kernel);
		
		// Update all names:
		if (amalgamation!=null) {
			amalgamation.setName(newName);
		}
		if (multi!=null) {
			multi.setName(MULTI_RULE_NAME_PREFIX + newName);
		}
		kernel.setName(newName);
		
	}
	
}
