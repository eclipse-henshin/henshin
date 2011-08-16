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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.emf.henshin.model.util.HenshinRuleAnalysisUtil;

/**
 * This command removes the given list of nodes. <br>
 * Since removal of nodes shall always be possible, in the preparation phase (
 * {@link #prepare()}) this command simply returns true, i.e. no further
 * commands are created at that time. Only when the command is to be executed (
 * {@link #execute()}), the related commands are created and executed on after
 * the other.
 * 
 * 
 * @author Stefan Jurack (sjurack)
 * 
 */
public class NodeComplexRemoveCommand extends CompoundCommand {
	
	protected final EditingDomain domain;
	protected final Collection<Node> nodes;
	Graph owningGraph;
	
	/**
	 * @param domain
	 * @param owner
	 * @param nodes
	 */
	public NodeComplexRemoveCommand(EditingDomain domain, Graph owner, Collection<Node> nodes) {
		super();
		this.domain = domain;
		this.nodes = nodes;
		this.owningGraph = owner;
	}// constructor
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.CompoundCommand#prepare()
	 */
	@Override
	protected boolean prepare() {
		isPrepared = true;
		isExecutable = true;
		return true;
	}// prepare
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		
		removeEdges();
		removeMappings();
		
		// Remove the nodes.
		this.appendAndExecute(new RemoveCommand(domain, owningGraph,
				HenshinPackage.Literals.GRAPH__NODES, nodes));
		
	}// execute
	
	/**
	 * Collects all edges and deletes them. The related command is executed and
	 * added to <code>this</code> command's queue.
	 */
	private void removeEdges() {
		final Set<Edge> edgeSet = new HashSet<Edge>();
		
		for (Node node : nodes) {
			for (Edge edge : node.getAllEdges()) {
				/*
				 * Add only edges to be removed, which are not yet removed.
				 */
				if (edge.getGraph() != null) edgeSet.add(edge);
			}// for
		}// for
		
		// Do the removal
		if (!edgeSet.isEmpty()) this.appendAndExecute(new DeleteCommand(domain, edgeSet));
	}// removeEdges
	
	/**
	 * Collects all mappings and deletes them. The related command is added to
	 * <code>this</code> command's queue.
	 */
	private void removeMappings() {
		final Set<Mapping> mappingSet = new HashSet<Mapping>();
		
		Object graphContainer = owningGraph.eContainer();
		/*
		 * If the owningGraph is already removed from the containment tree,
		 * related mappings are already removed as well (see
		 * GraphComplexUnsetCommand).
		 */
		if (graphContainer == null) return;
		
		// Otherwise distinguish between Rule, NestedCondition and direct subgraphs
		if (graphContainer instanceof Rule) {
			Rule rule = (Rule) graphContainer;
			filterMappings(rule.getMappings(), mappingSet);
			
			if (rule.eContainer() != null) {
				collectAmalgamationUnitRelatedMappings(mappingSet, rule);
			}// if
			
		} else if (graphContainer instanceof NestedCondition) {
			NestedCondition nc = (NestedCondition) graphContainer;
			filterMappings(nc.getMappings(), mappingSet);
		} else {
			// direct subgraph of TransformationSystem
			return; // nothing to remove here, as direct subgraphs don't have mappings.
		}
		
		/*
		 * Search in (directly) contained NestedConditions if there is a mapping
		 * with 'origin' being in <code>nodes</code>.
		 */
		if (owningGraph.getFormula() != null) {
			List<NestedCondition> ncList = new ArrayList<NestedCondition>();
			findDirectlyContainedNestedConditions(owningGraph.getFormula(), ncList);
			
			for (NestedCondition n : ncList)
				filterMappings(n.getMappings(), mappingSet);
		}// if
		
		// Do the removal
		if (!mappingSet.isEmpty()) this.appendAndExecute(new DeleteCommand(domain, mappingSet));
	}// removeMappings
	
	/**
	 * If the TransformationSystem is accessible through this <code>rule</code>,
	 * this method iterates over all AmalgamationUnits and watches out if the
	 * <code>rule</code>, consequently also <code>owningGraph</code>, is part of
	 * them. If this is the case, it look if there are AU-mappings related to
	 * our <code>nodes</code> to be deleted. Such mappings are put into
	 * mappingSet.
	 * 
	 * @param mappingSet
	 * @param rule
	 */
	private void collectAmalgamationUnitRelatedMappings(final Set<Mapping> mappingSet, Rule rule) {
		if (rule.eContainer() != null && rule.eContainer() instanceof TransformationSystem){
			TransformationSystem trafoSys = (TransformationSystem) rule.eContainer();
			for (TransformationUnit unit : trafoSys.getTransformationUnits()) {
				if (unit instanceof AmalgamationUnit) {
					AmalgamationUnit au = (AmalgamationUnit) unit;
					if (au.getKernelRule() == rule || au.getMultiRules().contains(rule)) {
						/*
						 * If the rule is part of an AU, then its graph is as
						 * well ;-)
						 */
						if (HenshinRuleAnalysisUtil.isLHS(owningGraph))
							filterMappings(au.getLhsMappings(), mappingSet);
						else
							filterMappings(au.getRhsMappings(), mappingSet); // rhs
					}// if
				}// if
			}// for
		}// if
	}// collectAmalgamationUnitRelatedMappings
	
	/**
	 * Finds directly nested NestedConditions and puts it into <code>list</code>
	 * .<br>
	 * <code>list</code> must not be <code>null</code>.
	 * 
	 * @param f
	 * @param list
	 */
	private void findDirectlyContainedNestedConditions(Formula f, List<NestedCondition> list) {
		if (f instanceof UnaryFormula) {
			UnaryFormula uf = (UnaryFormula) f;
			findDirectlyContainedNestedConditions(uf.getChild(), list);
		} else if (f instanceof BinaryFormula) {
			BinaryFormula bf = (BinaryFormula) f;
			findDirectlyContainedNestedConditions(bf.getLeft(), list);
			findDirectlyContainedNestedConditions(bf.getRight(), list);
		} else if (f != null) list.add((NestedCondition) f);
	}// findAllNestedConditions
	
	/**
	 * Iterates <code>unfilteredList</code> and put those mappings into
	 * <code>targetSet</code> which relate to a node in this classes
	 * <code>nodes</code>.
	 * 
	 * @param unfilteredList
	 * @param targetSet
	 */
	private void filterMappings(List<Mapping> unfilteredList, Set<Mapping> targetSet) {
		for (Mapping mapping : unfilteredList) {
			// if mapping isn't already deleted
			if (mapping.eContainer() != null) {
				for (Node node : nodes) {
					if (mapping.getImage() == node || mapping.getOrigin() == node) {
						targetSet.add(mapping);
						break;
					}// if
				}// for
			}// if
		}// for
	}// filterMappings
	
}// class
