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
package org.eclipse.emf.henshin.editor.menuContributors;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.editor.commands.CreateMappingCommand;
import org.eclipse.emf.henshin.editor.commands.MenuContributor;
import org.eclipse.emf.henshin.editor.commands.QuantUtil;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.emf.henshin.model.util.HenshinRuleAnalysisUtil;
import org.eclipse.jface.action.IMenuManager;

/**
 * Contributes creation of {@link Mapping}s between nodes.
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack (sjurack)
 */
public class CreateMappingCommandMenuContributor extends MenuContributor {
	
	public static MenuContributor INSTANCE = new CreateMappingCommandMenuContributor();
	
	private static final String COMMAND_LABEL = "CreateMapping";
	
	@Override
	public void contributeActions(IMenuManager menuManager, List<?> selection) {
		
		if (selection.size() != 2) return;
		
		if (!QuantUtil.allInstancesOf(Node.class, selection.get(0), selection.get(1))) return;
		
		Node sourceNode = (Node) selection.get(0);
		Node targetNode = (Node) selection.get(1);
		
		// Nodes must be contained in different graphs
		//
		if (QuantUtil.anyNull(sourceNode.getGraph(), targetNode.getGraph())
				|| QuantUtil.allIdentical(sourceNode.getGraph(), targetNode.getGraph())) return;
		
		// Nodes must have the same type.
		//
		if (!QuantUtil.allIdenticalAndNotNull(sourceNode.getType(), targetNode.getType())) return;
		
		// 1. The nodes come from lhs and rhs of the same rule
		//
		if (isUnmappedLhsRhsPairFromSameRule(sourceNode, targetNode)) {
			menuManager.add(createAction(getLabel(COMMAND_LABEL),
					CreateMappingCommand.createCreateMappingInRuleCommand(sourceNode, targetNode)));
			return;
		}
		
		// Reverse of 1.
		//
		if (isUnmappedLhsRhsPairFromSameRule(targetNode, sourceNode)) {
			menuManager.add(createAction(getLabel(COMMAND_LABEL),
					CreateMappingCommand.createCreateMappingInRuleCommand(targetNode, sourceNode)));
			return;
		}
		
		// 2. The targetNode is contained in a reachable formula of the
		// sourceNodes graph which is directly contained in a Rule
		//
		if ((sourceNode.getGraph().eContainer() instanceof Rule)
				&& isMappableInReachableFormula(sourceNode, targetNode)) {
			menuManager.add(createAction(getLabel(COMMAND_LABEL),
					CreateMappingCommand.createCreateMappingInRuleCommand(sourceNode, targetNode)));
			return;
		}
		
		// Reverse of 2.
		//
		if ((targetNode.getGraph().eContainer() instanceof Rule)
				&& isMappableInReachableFormula(targetNode, sourceNode)) {
			menuManager.add(createAction(getLabel(COMMAND_LABEL),
					CreateMappingCommand.createCreateMappingInRuleCommand(targetNode, sourceNode)));
			return;
		}
		
		// 3. The targetNode is contained in a reachable formula of the
		// sourceNodes graph which is contained in a NestedCondition
		//
		if ((sourceNode.getGraph().eContainer() instanceof NestedCondition)
				&& isMappableInReachableFormula(sourceNode, targetNode)) {
			menuManager.add(createAction(getLabel(COMMAND_LABEL), CreateMappingCommand
					.createCreateMappingInNestedConditionCommand(sourceNode, targetNode)));
			return;
		}
		
		// Reverse of 3.
		//
		if ((targetNode.getGraph().eContainer() instanceof NestedCondition)
				&& isMappableInReachableFormula(targetNode, sourceNode)) {
			menuManager.add(createAction(getLabel(COMMAND_LABEL), CreateMappingCommand
					.createCreateMappingInNestedConditionCommand(targetNode, sourceNode)));
			return;
		}
	}
	
	protected boolean isMappableInReachableFormula(Node sourceNode, Node targetNode) {
		Formula formula = sourceNode.getGraph().getFormula();
		Collection<Mapping> mappings = null;
		EObject graphContainer = sourceNode.getGraph().eContainer();
		if (graphContainer instanceof Rule) {
			mappings = ((Rule) graphContainer).getMappings();
		} else if (graphContainer instanceof NestedCondition) {
			mappings = ((NestedCondition) graphContainer).getMappings();
		} else
			return false;
		Collection<Node> targetNodes = getNodeCollectionFromReachableNestedConditionContainingNode(
				formula, targetNode);
		if (targetNodes == null) return false;
		return isMappable(sourceNode, targetNode, mappings);
	}
	
	protected Collection<Node> getNodeCollectionFromReachableNestedConditionContainingNode(
			Formula formula, Node node) {
		if (formula == null) return null;
		if (formula instanceof BinaryFormula) {
			Collection<Node> nodes = getNodeCollectionFromReachableNestedConditionContainingNode(
					((BinaryFormula) formula).getLeft(), node);
			if (nodes != null) return nodes;
			return getNodeCollectionFromReachableNestedConditionContainingNode(
					((BinaryFormula) formula).getRight(), node);
		}
		
		if (formula instanceof UnaryFormula)
			return getNodeCollectionFromReachableNestedConditionContainingNode(
					((Not) formula).getChild(), node);
		if ((formula instanceof NestedCondition)
				&& (((NestedCondition) formula).getConclusion() != null)) {
			if (((NestedCondition) formula).getConclusion().getNodes().contains(node))
				return ((NestedCondition) formula).getConclusion().getNodes();
		}
		return null;
	}
	
	/**
	 * 
	 * @param source
	 * @param target
	 * @return whether the source's graph is the lhs of the same rule holding
	 *         the target's graph as it's rhs
	 */
	protected boolean isLhsRhsPairFromSameRule(Node source, Node target) {
		return QuantUtil.noneNull(source, target)
				&& QuantUtil.noneNull(source.getGraph(), target.getGraph())
				&& HenshinRuleAnalysisUtil.isLHS(source.getGraph())
				&& HenshinRuleAnalysisUtil.isRHS(target.getGraph())
				&& (source.getGraph().getContainerRule() == target.getGraph().getContainerRule());
	}
	
	protected boolean isUnmappedLhsRhsPairFromSameRule(Node source, Node target) {
		return isLhsRhsPairFromSameRule(source, target)
				&& isMappable(source, target, source.getGraph().getContainerRule().getMappings());
	}
	
	/**
	 * Two nodes can be mapped if there is no mapping from one node into the
	 * collection containing the other node.
	 * 
	 * @param sourceNode
	 * @param targetNode
	 * @param mappings
	 * @return
	 */
	protected boolean isMappable(Node sourceNode, Node targetNode, Collection<Mapping> mappings) {
		@SuppressWarnings("unchecked")
		Collection<Node> sourceNodes = (Collection<Node>) sourceNode.eContainer().eGet(
				sourceNode.eContainingFeature());
		@SuppressWarnings("unchecked")
		Collection<Node> targetNodes = (Collection<Node>) targetNode.eContainer().eGet(
				targetNode.eContainingFeature());
		for (Mapping mapping : mappings) {
			if ((mapping.getOrigin() == sourceNode) && targetNodes.contains(mapping.getImage()))
				return false;
			if ((mapping.getImage() == targetNode) && sourceNodes.contains(mapping.getOrigin()))
				return false;
		}
		return true;
	}
}
