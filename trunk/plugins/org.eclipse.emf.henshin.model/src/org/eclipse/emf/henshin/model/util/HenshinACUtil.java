/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.model.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.UnaryFormula;

/**
 * Common utility function for accessing and modifying positive and negative
 * application conditions of rules (PACs and NACs). Application conditions are
 * {@link NestedCondition}s that are associated to the LHS of a rule.
 * 
 * @author Christian Krause, Felix Rieger
 */
public class HenshinACUtil {
	
	/**
	 * Find all positive and negative application conditions of a Rule.
	 * 
	 * @param rule
	 *            Rule.
	 * @return List of PACs and NACs.
	 */
	public static List<NestedCondition> getAllACs(Rule rule) {
		List<NestedCondition> acs = new ArrayList<NestedCondition>();
		addACs(rule.getLhs().getFormula(), acs, true);
		addACs(rule.getLhs().getFormula(), acs, false);
		return acs;
	}
	
	/**
	 * Find all positive or negative application conditions of a Rule.
	 * 
	 * @param rule
	 *            Rule.
	 * @param positive
	 *            <code>true</code> if PACs should be found, <code>false</code>
	 *            if NACs should be found.
	 * @return List of nested conditions.
	 */
	public static List<NestedCondition> getAllACs(Rule rule, boolean positive) {
		List<NestedCondition> acs = new ArrayList<NestedCondition>();
		addACs(rule.getLhs().getFormula(), acs, positive);
		return acs;
	}
	
	/**
	 * Find a positive or a negative application condition by its name.
	 * 
	 * @param rule
	 *            Rule.
	 * @param name
	 *            Name of the PAC/NAC.
	 * @param positive
	 *            <code>true</code> if PACs should be found, <code>false</code>
	 *            if NACs should be found.
	 * @return the nested condition if found.
	 */
	public static NestedCondition getAC(Rule rule, String name, boolean positive) {
		for (NestedCondition ac : getAllACs(rule, positive)) {
			if (name.equals(ac.getConclusion().getName())) {
				return ac;
			}
		}
		return null;
	}
	
	/**
	 * Collect all nested conditions of a Rule recursively
	 * 
	 * @param formula
	 * @param acs
	 * @param positive
	 *            <code>true</code> if PACs should be collected,
	 *            <code>false</code> if NACs should be collected
	 */
	private static void addACs(Formula formula, List<NestedCondition> acs, boolean positive) {
		if (formula instanceof And) {
			addACs(((And) formula).getLeft(), acs, positive);
			addACs(((And) formula).getRight(), acs, positive);
		}
		else if (formula instanceof Not) {
			addACs(((Not) formula).getChild(), acs, !positive);
		}
		else if (formula instanceof NestedCondition && positive) {
			acs.add((NestedCondition) formula);
		}
	}
	
	/**
	 * Create a new positive or negative application condition (PAC or NAC).
	 * 
	 * @param rule
	 *            Rule.
	 * @param name
	 *            Name of the application condition.
	 * @param positive
	 *            <code>true</code> if a PAC should be created,
	 *            <code>false</code> if a NAC should be created
	 * @return the created application condition
	 */
	public static NestedCondition createAC(Rule rule, String name, boolean positive) {
		
		// Create the application condition:
		NestedCondition ac = HenshinFactory.eINSTANCE.createNestedCondition();
		Graph graph = HenshinFactory.eINSTANCE.createGraph();
		graph.setName(name);
		ac.setConclusion(graph);
		
		Formula acFormula;
		
		// Wrapped in a 'Not' if it is a NAC:
		if (!positive) {
			Not not = HenshinFactory.eINSTANCE.createNot();
			not.setChild(ac);
			acFormula = not;
		} else {
			acFormula = ac;
		}
		
		// Add it to the rule:
		if (rule.getLhs().getFormula() == null) {
			rule.getLhs().setFormula(acFormula);
		} else {
			And and = HenshinFactory.eINSTANCE.createAnd();
			and.setLeft(rule.getLhs().getFormula());
			and.setRight(acFormula);
			rule.getLhs().setFormula(and);
		}
		
		// Done.
		return ac;
		
	}
	
	/**
	 * Remove an application condition from a rule.
	 * 
	 * @param rule
	 *            Rule to be modified.
	 * @param ac
	 *            application condition to be removed.
	 */
	public static void removeAC(Rule rule, NestedCondition ac) {
		
		// Remember the container and destroy the object:
		EObject container = ac.eContainer();
		EcoreUtil.remove(ac);
		
		// Destroy unary containers:
		while (container instanceof UnaryFormula) {
			EObject dummy = container;
			container = container.eContainer();
			EcoreUtil.remove(dummy);
		}
		
		// Check if the container was a binary formula:
		if (container instanceof BinaryFormula) {
			BinaryFormula binary = (BinaryFormula) container;
			
			// Replace the formula by the remaining sub-formula:
			Formula remainder = (binary.getLeft() != null) ? binary.getLeft() : binary.getRight();
			EcoreUtil.replace(binary, remainder);
		}
		
	}
	
	/**
	 * Check whether a AC is trivial. A trivial AC is one that can always be
	 * matched.
	 * 
	 * @param ac
	 *            Application condition.
	 * @return <code>true</code> if the application condition can always be
	 *         matched.
	 */
	public static boolean isTrivialAC(NestedCondition ac) {
		
		// AC Details:
		Graph graph = ac.getConclusion();
		MappingList mappings = ac.getMappings();
		
		// Check if any of the nodes is not the image of a mapping.
		for (Node node : graph.getNodes()) {
			if (mappings.getOrigin(node) == null)
				return false;
			
			// Check the attributes of this node as well.
			for (Attribute attribute : node.getAttributes()) {
				Attribute origin = mappings.getOrigin(attribute);
				if (origin == null
						|| !valueEquals(attribute.getValue(), origin.getValue())) {
					return false;
				}
			}
		}
		
		// Check if any of the edges is not the image of a mapping.
		for (Edge edge : graph.getEdges()) {
			if (mappings.getOrigin(edge) == null)
				return false;
		}
		
		// Otherwise it is trivial:
		return true;
		
	}
	
	/*
	 * Check if to attribute values are equal.
	 */
	static boolean valueEquals(String v1, String v2) {
		if (v1 == null)
			return (v2 == null);
		if (v2 == null)
			return false;
		return v1.trim().equals(v2.trim());
	}

	/**
	 * Remove all trivial application conditions from a rule.
	 * 
	 * @param rule
	 *            Rule.
	 */
	public static void removeTrivialACs(Rule rule) {
		for (NestedCondition ac : getAllACs(rule, false)) {
			if (isTrivialAC(ac)) {
				removeAC(rule, ac);
			}
		}
	}
	
}
