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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.util.HenshinRuleAnalysisUtil;

/**
 * This was a try to delete all pending edges when a node is deleted. Does not
 * work properly as part of a composite delete command that explicitly tries to
 * delete a pending edge.
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack (sjurack)
 * 
 */
public class NodeRemoveCommand extends CompoundCommand {
	
	EditingDomain domain;
	Graph owningGraph;
	Collection<Node> nodes;
	HenshinPackage pack = HenshinPackage.eINSTANCE;
	
	Map<EObject, Map<EStructuralFeature, Set<Mapping>>> removeMap = new HashMap<EObject, Map<EStructuralFeature, Set<Mapping>>>();
	
	public NodeRemoveCommand(EditingDomain domain, EObject owner, Collection<Node> nodes) {
		super();
		this.domain = domain;
		this.owningGraph = (Graph) owner;
		this.nodes = nodes;
	}
	
	/**
	 * Registers {@link Mapping}s to be removed from a given owners feature.
	 * 
	 * @param owner
	 * @param feature
	 * @param mapping
	 */
	protected void addToRemoveMap(EObject owner, EStructuralFeature feature, Mapping mapping) {
		
		if (!removeMap.containsKey(owner))
			removeMap.put(owner, new HashMap<EStructuralFeature, Set<Mapping>>());
		if (!removeMap.get(owner).containsKey(feature))
			removeMap.get(owner).put(feature, new HashSet<Mapping>());
		removeMap.get(owner).get(feature).add(mapping);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.CompoundCommand#prepare()
	 */
	@Override
	protected boolean prepare() {
		
		// edges requires set semantics to avoid duplicate RemoveCommands
		Collection<Edge> edges = new HashSet<Edge>();
		for (Node node : nodes)
			edges.addAll(node.getAllEdges());
		
		if (HenshinRuleAnalysisUtil.isRHS(owningGraph))
			;// prepareRemovalFromRHS();
		else if (HenshinRuleAnalysisUtil.isLHS(owningGraph))
			prepareRemovalFromLHS();
		else if (HenshinRuleAnalysisUtil.isConclusion(owningGraph))
			prepareRemovalFromNestedCondition();
		
		this.append(new RemoveCommand(domain, owningGraph, pack.getGraph_Nodes(), nodes));
		
		if (edges.size() > 0) this.append(new DeleteCommand(domain, edges));
		
		// (domain, owningGraph, pack.getGraph_Edges(), edges));
		
		for (EObject aOwner : removeMap.keySet()) {
			Map<EStructuralFeature, Set<Mapping>> fMap = removeMap.get(aOwner);
			for (EStructuralFeature aFeature : fMap.keySet()) {
				Set<Mapping> aMappingSet = fMap.get(aFeature);
				if (aMappingSet.isEmpty()) continue;
				this.append(new NegligentRemoveCommand(domain, aOwner, aFeature, aMappingSet));
			}
		}
		
		// System.out.println("commands: " + commandList.size());
		return super.prepare();
		
	}
	
	/**
	 * lookup mappings if nodes are removed from a rules RHS
	 */
	protected void prepareRemovalFromRHS() {
		Rule rule = owningGraph.getContainerRule();
		for (Mapping mapping : rule.getMappings())
			// Mappings may map nodes from LHS or into NestedCondition of
			// RHS
			if (nodes.contains(mapping.getImage()) || nodes.contains(mapping.getOrigin()))
				addToRemoveMap(rule, pack.getRule_Mappings(), mapping);
		for (AmalgamationUnit aUnit : getAmalgamationUnitsWithKernelRule(rule))
			for (Mapping mapping : aUnit.getRhsMappings())
				if (nodes.contains(mapping.getOrigin()))
					addToRemoveMap(aUnit, pack.getAmalgamationUnit_RhsMappings(), mapping);
		for (AmalgamationUnit aUnit : getAmalgamationUnitsContainingMultiRule(rule))
			for (Mapping mapping : aUnit.getRhsMappings())
				if (nodes.contains(mapping.getImage()))
					addToRemoveMap(aUnit, pack.getAmalgamationUnit_RhsMappings(), mapping);
	}
	
	/**
	 * lookup mappings if nodes are removed from a rules LHS
	 */
	protected void prepareRemovalFromLHS() {
		Rule rule = owningGraph.getContainerRule();
		// mappings from lhs to rhs of a rule
		for (Mapping mapping : rule.getMappings())
			if (nodes.contains(mapping.getOrigin()))
				addToRemoveMap(rule, pack.getRule_Mappings(), mapping);
		// mappings from a amalgamationUnit's kernelRule to multiRules
		for (AmalgamationUnit aUnit : getAmalgamationUnitsWithKernelRule(rule))
			for (Mapping mapping : aUnit.getLhsMappings())
				if (nodes.contains(mapping.getOrigin()))
					addToRemoveMap(aUnit, pack.getAmalgamationUnit_LhsMappings(), mapping);
		for (AmalgamationUnit aUnit : getAmalgamationUnitsContainingMultiRule(rule))
			for (Mapping mapping : aUnit.getLhsMappings())
				if (nodes.contains(mapping.getImage()))
					addToRemoveMap(aUnit, pack.getAmalgamationUnit_LhsMappings(), mapping);
	}
	
	/**
	 * lookup {@link Mapping}s if {@link Node}s are removed from a
	 * {@link NestedCondition}'s conclusion {@link Graph}
	 */
	protected void prepareRemovalFromNestedCondition() {
		NestedCondition nc = (NestedCondition) owningGraph.eContainer();
		// 1. mappings from the given NC into deeper NC
		for (Mapping mapping : nc.getMappings()) {
			if (nodes.contains(mapping.getOrigin()))
				addToRemoveMap(nc, pack.getNestedCondition_Mappings(), mapping);
		}
		
		// 2. mappings from container into given nestedCondition
		Graph ncOwnerGraph = getContainingGraph(nc);
		
		// given NC is contained in another NC
		if (HenshinRuleAnalysisUtil.isConclusion(ncOwnerGraph)) {
			NestedCondition ncOwnerGraphOwner = (NestedCondition) ncOwnerGraph.eContainer();
			for (Mapping mapping : ncOwnerGraphOwner.getMappings())
				if (nodes.contains(mapping.getImage()))
					addToRemoveMap(ncOwnerGraphOwner, pack.getNestedCondition_Mappings(), mapping);
		} else
		// given NC is directly contained in a rule graph
		if (HenshinRuleAnalysisUtil.isLHS(ncOwnerGraph)
				|| HenshinRuleAnalysisUtil.isRHS(ncOwnerGraph)) {
			Rule rule = ncOwnerGraph.getContainerRule();
			for (Mapping mapping : rule.getMappings())
				if (nodes.contains(mapping.getImage()))
					addToRemoveMap(rule, pack.getRule_Mappings(), mapping);
		}
	}
	
	protected Graph getContainingGraph(Formula formula) {
		if (formula.eContainer() instanceof Graph)
			return (Graph) formula.eContainer();
		else if (formula.eContainer() instanceof Formula) {
			return getContainingGraph((Formula) formula.eContainer());
		} else
			throw new IllegalArgumentException("Formula is notproperly contained in the model!");
	}
	
	/**
	 * 
	 * @param rule
	 * @return all {@link AmalgamationUnit}s which use the given {@link Rule} as
	 *         their kernelRule
	 */
	protected Collection<AmalgamationUnit> getAmalgamationUnitsWithKernelRule(Rule rule) {
		if (rule.getTransformationSystem() == null) return Collections.emptyList();
		Collection<AmalgamationUnit> result = new ArrayList<AmalgamationUnit>();
		for (TransformationUnit unit : rule.getTransformationSystem().getTransformationUnits()) {
			if (unit instanceof AmalgamationUnit) {
				AmalgamationUnit aUnit = (AmalgamationUnit) unit;
				if (aUnit.getKernelRule() == rule) result.add(aUnit);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param rule
	 * @return all {@link AmalgamationUnit}s which use the given {@link Rule} as
	 *         one of their multiRules
	 */
	protected Collection<AmalgamationUnit> getAmalgamationUnitsContainingMultiRule(Rule rule) {
		if (rule.getTransformationSystem() == null) return Collections.emptyList();
		Collection<AmalgamationUnit> result = new ArrayList<AmalgamationUnit>();
		for (TransformationUnit unit : rule.getTransformationSystem().getTransformationUnits()) {
			if (unit instanceof AmalgamationUnit) {
				AmalgamationUnit aUnit = (AmalgamationUnit) unit;
				if (aUnit.getMultiRules().contains(rule)) result.add(aUnit);
			}
		}
		return result;
	}
}
