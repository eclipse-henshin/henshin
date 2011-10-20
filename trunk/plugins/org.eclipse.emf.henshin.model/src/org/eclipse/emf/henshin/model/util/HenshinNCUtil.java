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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Common utility function for accessing and modifying
 * negative application conditions of rules (NACs). Negative
 * application conditions are negated {@link NestedCondition}s
 * that are associated to the LHS of a rule.
 * 
 * @author Christian Krause
 */
public class HenshinNCUtil {
	
	/**
	 * Find all nested condition of a Rule.
	 * @param rule		Rule.
	 * @param positive	<code>true</code> if PACs should be found, <code>false</code> if NACs should be found.
	 * @return			List of nested conditions.
	 */
	public static List<NestedCondition> getAllNCs(Rule rule, boolean positive) {
		List<NestedCondition> ncs = new ArrayList<NestedCondition>();
		addNCs(rule.getLhs().getFormula(), ncs, positive);
		return ncs;
	}
	
	/**
	 * Find all negative application conditions (NACs) of a rule.
	 * @param rule Rule.
	 * @return List of negative application conditions.
	 */
	@Deprecated
	public static List<NestedCondition> getAllNACs(Rule rule) {
		return getAllNCs(rule, false);
		/*
		// XXX
		List<NestedCondition> nacs = new ArrayList<NestedCondition>();
		addNACs(rule.getLhs().getFormula(), nacs);
		return nacs; */
	}
	
	
	/**
	 * Find a Nested Condition with a given name.
	 * @param rule		Rule.
	 * @param name		Name of the Nested Condition.
	 * @param positive	<code>true</code> if PACs should be found, <code>false</code> if NACs should be found.
	 * @return	the nested condition if found.
	 */
	public static NestedCondition getNC(Rule rule, String name, boolean positive) {
		for (NestedCondition nc : getAllNCs(rule, positive)) {
			if (name.equals(nc.getConclusion().getName())) {
				return nc;
			}
		}
		return null;
	}
	
	/**
	 * Find a NAC with a given name.
	 * @param rule Rule.
	 * @param name Name of the NAC.
	 * @return The NAC if found.
	 */
	@Deprecated
	public static NestedCondition getNAC(Rule rule, String name) {
		// XXX
		/*
		for (NestedCondition nac : getAllNACs(rule)) {
			if (name.equals(nac.getConclusion().getName())) return nac;
		}
		return null;*/
		return getNC(rule, name, false);
	}
	
	/**
	 * Collect all nested conditions of a Rule recursively
	 * @param formula
	 * @param ncs
	 * @param positive	<code>true</code> if PACs should be collected, <code>false</code> if NACs should be collected
	 */
	private static void addNCs(Formula formula, List<NestedCondition> ncs, boolean positive) {
		// Conjunction (And):
		if (formula instanceof And) {
			addNCs(((And) formula).getLeft(), ncs, positive);
			addNCs(((And) formula).getRight(), ncs, positive);
		} 
		// XXX: This part will be removed --v
		else if (formula instanceof NestedCondition) {
			NestedCondition nested = (NestedCondition) formula;
			if (nested.isNegated() != positive) {
				ncs.add(nested);
			}
		}
		// XXX: End of removal part --^
		else if (formula instanceof Not) {
			Formula child = ((Not) formula).getChild();
			if (child instanceof NestedCondition) {
				NestedCondition nested = (NestedCondition) child;
				if (nested.isNegated() == positive) {	// check will be removed
					ncs.add(nested);
				}
			}
		}
	}
	
	/*
	 * Collect all NACs of rule recursively.
	 */
	@Deprecated
	private static void addNACs(Formula formula, List<NestedCondition> nacs) {
		addNCs(formula, nacs, false);
		/*
		// XXX
		// Conjunction (And):
		if (formula instanceof And) {
			addNACs(((And) formula).getLeft(),nacs);
			addNACs(((And) formula).getRight(),nacs);
		}
		
		// The actual NACs (NestedCondition):
		
		// THIS PART WILL BE REMOVED:
		else if (formula instanceof NestedCondition) {
			NestedCondition nested = (NestedCondition) formula;
			if (nested.isNegated()) nacs.add(nested);
		}
		// END OF REMOVAL PART
		
		// NestedConditions wrapped by a Not (the NACs):
		else if (formula instanceof Not) {
			Formula child = ((Not) formula).getChild();
			if (child instanceof NestedCondition) {
				NestedCondition nested = (NestedCondition) child;
				if (!nested.isNegated()) nacs.add(nested);	// CHECK WILL BE REMOVED
			}
		}
		*/
		// Done.
	}

	
	/**
	 * Create a new Nested Condition
	 * @param rule		Rule.
	 * @param name		Name of the NC.
	 * @param positive	<code>true</code> if a PAC should be created, <code>false</code> if a NAC should be created
	 * @return	the created NC
	 */
	public static NestedCondition createNC(Rule rule, String name, boolean positive) {
		// Create the NC
		NestedCondition nc = HenshinFactory.eINSTANCE.createNestedCondition();
		Graph graph = HenshinFactory.eINSTANCE.createGraph();
		graph.setName(name);
		nc.setConclusion(graph);
		
		Formula ncFormula;
		
		// Wrapped in a 'Not' if it is a NAC:
		if (!positive) {
			Not not = HenshinFactory.eINSTANCE.createNot();
			not.setChild(nc);
			ncFormula = not;
		} else {
			ncFormula = nc;
		}
		
		// Add it to the rule:
		if (rule.getLhs().getFormula() == null) {
			rule.getLhs().setFormula(ncFormula);
		} else {
			And and = HenshinFactory.eINSTANCE.createAnd();
			and.setLeft(rule.getLhs().getFormula());
			and.setRight(ncFormula);
			rule.getLhs().setFormula(and);
		}
		
		return nc;
	
	}
	
	/**
	 * Create a new NAC.
	 * @param rule Rule.
	 * @param name Name of the NAC.
	 * @return The newly create NAC.
	 */
	@Deprecated
	public static NestedCondition createNAC(Rule rule, String name) {
		/*
		// XXX
		// Create the NAC:
		NestedCondition nac = HenshinFactory.eINSTANCE.createNestedCondition();
		Graph graph = HenshinFactory.eINSTANCE.createGraph();
		graph.setName(name);
		nac.setConclusion(graph);
		
		// Wrapped in a 'Not'!!
		Not not = HenshinFactory.eINSTANCE.createNot();
		not.setChild(nac);
		
		// Add it to the rule:
		if (rule.getLhs().getFormula()==null) {
			rule.getLhs().setFormula(not);
		} else {
			And and = HenshinFactory.eINSTANCE.createAnd();
			and.setLeft(rule.getLhs().getFormula());
			and.setRight(not);
			rule.getLhs().setFormula(and);
		}
		
		// Done.
		return nac;
		*/
		return createNC(rule, name, false);
	}
	
	/**
	 * Remove a Nested Condition from a rule.
	 * @param rule	Rule to be modified.
	 * @param nc	Nested Condition to be removed.
	 */
	public static void removeNC(Rule rule, NestedCondition nc) {
		// Remember the container and destroy the object:
		EObject container = nc.eContainer();
		EcoreUtil.remove(nc);
		
		// Check if the container was a binary formula:
		if (container instanceof BinaryFormula) {
			BinaryFormula binary = (BinaryFormula) container;
			
			// Replace the formula by the remaining sub-formula:
			Formula remainder = (binary.getLeft()!=null) ? binary.getLeft() : binary.getRight();
			EcoreUtil.replace(binary, remainder);
		}
	}
	
	/**
	 * Remove a NAC from a rule.
	 * @param rule Rule to be modified.
	 * @param nac NAC to be removed.
	 */
	@Deprecated
	public static void removeNAC(Rule rule, NestedCondition nac) {
		removeNC(rule, nac);
	}
	
	/**
	 * Check whether a NAC is trivial. A trivial NAC is one that
	 * can always be matched and hence causes the rule never to
	 * be applicable.
	 * @param nac NAC.
	 * @return <code>true</code> if the NAC can always be matched.
	 */
	public static boolean isTrivialNAC(NestedCondition nac) {
		
		// NAC Details:
		Graph graph = nac.getConclusion();
		EList<Mapping> mappings = nac.getMappings();
		
		// Check if any of the nodes is not the image of a mapping.
		for (Node node : graph.getNodes()) {
			if (HenshinMappingUtil.getNodeOrigin(node, mappings)==null) return false;
			
			// Check the attributes of this node as well.
			for (Attribute attribute : node.getAttributes()) {
				Attribute origin = HenshinMappingUtil.getAttributeOrigin(attribute, mappings);
				if (origin==null || !HenshinRuleAnalysisUtil.valueEquals(attribute.getValue(), origin.getValue())) {
					return false;
				}
			}
		}

		// Check if any of the edges is not the image of a mapping.
		for (Edge edge : graph.getEdges()) {
			if (HenshinMappingUtil.getEdgeOrigin(edge, mappings)==null) return false;
		}
		
		// Otherwise it is trivial:
		return true;
		
	}
	
	/**
	 * Remove all trivial NACs from a rule.
	 * @param rule Rule.
	 */
	public static void removeTrivialNACs(Rule rule) {
		for (NestedCondition nac : getAllNCs(rule, false)) {
			if (isTrivialNAC(nac)) {
				removeNC(rule, nac);
			}
		}
	}
	
}
