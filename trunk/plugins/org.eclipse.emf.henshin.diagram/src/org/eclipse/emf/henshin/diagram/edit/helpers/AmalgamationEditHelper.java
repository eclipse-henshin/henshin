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
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;

/**
 * Helper methods for handling amalgamation units in
 * the graphical editor.
 * 
 * @author Christian Krause
 */
public class AmalgamationEditHelper {
	
	/**
	 * Prefix for multi-rule names. This is to distinguish multi-rules
	 * from kernel rules easily.
	 */
	public static final String MULTI_RULE_NAME_PREFIX = "multi_";

	/**
	 * Get the associated default amalgamation unit for a given
	 * kernel or multi rule. This delegates to {@link #getAmalgamationFromKernelRule(Rule)}
	 * and {@link #getAmalgamationFromMultiRule(Rule)}.
	 * @param rule Rule.
	 * @return The amalgamation or <code>null</code>.
	 */
	public static AmalgamationUnit getAmalgamation(Rule rule) {
		AmalgamationUnit amalgamation = getAmalgamationFromKernelRule(rule);
		if (amalgamation==null) {
			amalgamation = getAmalgamationFromMultiRule(rule);
		}
		return amalgamation;
	}
	
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
	 * Check if a graph element in a multi-rule has a preimage in the
	 * kernel rule. 
	 * @param element Graph element.
	 */
	public static GraphElement getPreimageInKernelRule(GraphElement element, AmalgamationUnit amalgamation) {
		
		Graph graph = element.getGraph();
		if (graph==null) return null;
		Rule rule = graph.getContainerRule();
		if (rule==null) return null;
		
		if (graph==rule.getLhs()) {
			return HenshinMappingUtil.getOrigin(element,
					amalgamation.getLhsMappings());
		}
		else if (graph==rule.getRhs()) {
			return HenshinMappingUtil.getOrigin(element,
					amalgamation.getRhsMappings());
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
	 * Create a new multi-rule.
	 * @param kernel Kernel rule.
	 * @param amalgamation Amalgamation.
	 * @param name Name.
	 * @return The new multi-rule.
	 */
	public static Rule createMultiRule(AmalgamationUnit amalgamation, String name) {
		Rule multiRule = HenshinFactory.eINSTANCE.createRule();
		multiRule.setName(MULTI_RULE_NAME_PREFIX + name);
		amalgamation.getMultiRules().add(multiRule);
		amalgamation.getKernelRule().getTransformationSystem().getRules().add(multiRule);
		return multiRule;
	}

	/**
	 * Create a new default multi-rule.
	 */
	public static Rule createDefaultMultiRule(AmalgamationUnit amalgamation) {
		return createMultiRule(amalgamation, amalgamation.getName());
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

	/**
	 * Check whether a rule is a trivial multi-rule. A multi-rule
	 * is trivial if all elements in its LHS/RHS have a pre-image
	 * in the kernel rule's LHS/RHS.
	 * @param multi Rule to be checked.
	 * @return <code>true</code> if it is trivial.
	 */
	public static boolean isTrivialMultiRule(Rule multi, AmalgamationUnit amalgamation) {
		
		// Create a list of all elements in the multi-rule:
		List<GraphElement> elements = new ArrayList<GraphElement>();
		elements.addAll(multi.getLhs().getNodes());
		elements.addAll(multi.getLhs().getEdges());
		elements.addAll(multi.getRhs().getNodes());
		elements.addAll(multi.getRhs().getEdges());
		
		// Check if there have preimages.
		for (GraphElement element : elements) {
			if (getPreimageInKernelRule(element, amalgamation)==null)
				return false;
		}
		
		// No reason why it shouldn't be trivial.
		return true;
	}

	
	public static void cleanUpAmalagamation(AmalgamationUnit amalgamation) {

		// Get the transformation system:
		if (amalgamation==null || amalgamation.getKernelRule()==null) return;
		TransformationSystem system = amalgamation.getKernelRule().getTransformationSystem();
		if (system==null) return;
		
		// Delete trivial multi-rules:
		for (int i=0; i<amalgamation.getMultiRules().size(); i++) {
			Rule multi = amalgamation.getMultiRules().get(i);
			if (isTrivialMultiRule(multi, amalgamation)) {
				amalgamation.getMultiRules().remove(i--);
				system.getRules().remove(multi);
			}
		}
		
		// Check if there are no multi-rules left:
		if (amalgamation.getMultiRules().isEmpty() && 
				system.getTransformationUnits().contains(amalgamation)) {
			amalgamation.setKernelRule(null);
			system.getTransformationUnits().remove(amalgamation);
		}
		
	}

	public static void cleanUpAmalagamation(Rule rule) {
		AmalgamationUnit amalgamation = getAmalgamation(rule);
		if (amalgamation!=null) {
			cleanUpAmalagamation(amalgamation);
		}
	}

}
