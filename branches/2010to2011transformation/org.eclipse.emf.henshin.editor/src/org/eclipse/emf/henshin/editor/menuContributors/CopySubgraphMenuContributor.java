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

import org.eclipse.emf.henshin.editor.commands.CopySubgraphCommand;
import org.eclipse.emf.henshin.editor.commands.MenuContributor;
import org.eclipse.emf.henshin.editor.commands.QuantUtil;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.util.HenshinRuleAnalysisUtil;
import org.eclipse.jface.action.IMenuManager;

/**
 * Contributes copy actions for subgraphs.
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack (sjurack)
 * 
 */
public class CopySubgraphMenuContributor extends MenuContributor {
	
	public static MenuContributor INSTANCE = new CopySubgraphMenuContributor();
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.henshin.editor.commands.MenuContributor#contributeActions
	 * (org.eclipse.jface.action.IMenuManager, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void contributeActions(IMenuManager menuManager, List<?> selection) {
		
		if (selection.size() == 0) return;
		
		if (!QuantUtil.allInstancesOf(Node.class, selection.toArray())) return;
		
		Graph sourceGraph = ((Node) selection.get(0)).getGraph();
		
		if (HenshinRuleAnalysisUtil.isLHS(sourceGraph)) {
			
			CopySubgraphCommand cmd = new CopySubgraphCommand();
			cmd.setDomain(domain);
			cmd.setMappingOrigin(true);
			
			cmd.setSourceGraph(sourceGraph);
			cmd.setTargetGraph(sourceGraph.getContainerRule().getRhs());
			
			cmd.setMappings(sourceGraph.getContainerRule().getMappings());
			cmd.setNodes((Collection<Node>) selection);
			
			if (cmd.canExecute()) menuManager.add(createAction("Copy to RHS", cmd));
			
			walkNC(menuManager, sourceGraph.getFormula(), sourceGraph, (Collection<Node>) selection);
			
		} else if (HenshinRuleAnalysisUtil.isRHS(sourceGraph)) {
			CopySubgraphCommand cmd = new CopySubgraphCommand();
			cmd.setDomain(domain);
			cmd.setMappingOrigin(false);
			
			cmd.setSourceGraph(sourceGraph);
			cmd.setTargetGraph(sourceGraph.getContainerRule().getLhs());
			
			cmd.setMappings(sourceGraph.getContainerRule().getMappings());
			cmd.setNodes((Collection<Node>) selection);
			if (cmd.canExecute()) menuManager.add(createAction("Copy to LHS", cmd));
			
		} else if (HenshinRuleAnalysisUtil.isConclusion(sourceGraph)) {
			walkNC(menuManager, sourceGraph.getFormula(), sourceGraph, (Collection<Node>) selection);
		}
	}
	
	/**
	 * Create Copy Action for every reachable {@link NestedCondition}.
	 * 
	 * @param menu
	 * @param formula
	 * @param sourceGraph
	 * @param selection
	 */
	private void walkNC(IMenuManager menu, Formula formula, Graph sourceGraph,
			Collection<Node> selection) {
		if (formula == null) return;
		if (formula instanceof NestedCondition) {
			if (((NestedCondition) formula).getConclusion() == null) return;
			
			CopySubgraphCommand cmd = new CopySubgraphCommand();
			cmd.setDomain(domain);
			cmd.setMappingOrigin(true);
			
			cmd.setSourceGraph(sourceGraph);
			cmd.setTargetGraph(((NestedCondition) formula).getConclusion());
			
			cmd.setMappings(((NestedCondition) formula).getMappings());
			
			cmd.setNodes((Collection<Node>) selection);
			
			if (cmd.canExecute())
				menu.add(createAction("Copy to NC: "
						+ ((NestedCondition) formula).getConclusion().getName(), cmd));
			
			// menuManager.add(createAction("CopySubgraph To NCs ...", cmd));
		} else if (formula instanceof BinaryFormula) {
			walkNC(menu, ((BinaryFormula) formula).getLeft(), sourceGraph, selection);
			walkNC(menu, ((BinaryFormula) formula).getRight(), sourceGraph, selection);
		} else if (formula instanceof Not)
			walkNC(menu, ((Not) formula).getChild(), sourceGraph, selection);
		
	}
}
