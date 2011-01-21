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
package org.eclipse.emf.henshin.editor.transformationCommands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.editor.commands.MenuContributor;
import org.eclipse.emf.henshin.editor.commands.QuantUtil;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;

/**
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack (sjurack)
 */
public class TC_CreateEdge extends TCMenuContributor {
	
	public static MenuContributor INSTANCE = new TC_CreateEdge();
	
	protected Node node1, node2;
	
	protected EmfEngine typeGraphEngine, instanceGraphEngine;
	
	protected Rule findEReferencesForOwnerWithTypeRule, edgeExistsForSourceAndTargetAndTypeRule,
			findEdgesForSourceAndTypeRule, createEdgeForSourceAndTargetAndTypeRule;
	
	@Override
	protected boolean selectionYieldsApplicationContext(List<?> selection) {
		
		if (selection.size() != 2) return false;
		
		if (!QuantUtil.allInstancesOf(Node.class, selection.toArray())) return false;
		
		node1 = (Node) selection.get(0);
		node2 = (Node) selection.get(1);
		
		if (node1.getGraph() != node2.getGraph()) return false;
		
		if (QuantUtil.anyNull(node1.getType(), node2.getType())) return false;
		
		typeGraphEngine = getEngineForRoots(getEContainmentRoot(node1.getType()));
		instanceGraphEngine = getEngineForRoots(getEContainmentRoot(node1));
		
		return true;
	}
	
	@Override
	protected void loadTransformation() {
		TransformationSystem ts = loadTransformationSystem(getUriToPluginFile("transformations/CreateEdge/CreateEdge_FindType.henshin"));
		findEReferencesForOwnerWithTypeRule = ts.findRuleByName("findEReferencesForOwnerWithType");
		edgeExistsForSourceAndTargetAndTypeRule = ts
				.findRuleByName("edgeExistsForSourceAndTargetAndType");
		findEdgesForSourceAndTypeRule = ts.findRuleByName("findEdgesForSourceAndType");
		createEdgeForSourceAndTargetAndTypeRule = ts
				.findRuleByName("createEdgeForSourceAndTargetAndType");
	}
	
	@Override
	void contributeActions(IMenuManager menuManager) {
		
		IMenuManager subMenu = new MenuManager(getLabel("CreateEdge") + ": "
				+ node1.getType().getName() + " -> " + node2.getType().getName());
		menuManager.add(subMenu);
		
		for (EReference ref : findReferenceTypes(node1.getType(), node2.getType())) {
			if (edgeExists(node1, ref, node2)) {
				subMenu.add(createUnrunnableItem(ref.getName() + " *Edge already exists*"));
			} else {
				if (multiplicityExceeded(node1, ref, node2)) {
					subMenu.add(createUnrunnableItem(ref.getName() + "*Multiplicity exceeded*"));
				} else {
					RuleApplication ruleApp = new RuleApplication(instanceGraphEngine,
							createEdgeForSourceAndTargetAndTypeRule);
					ruleApp.setParameterValue("source", node1);
					ruleApp.setParameterValue("target", node2);
					ruleApp.setParameterValue("type", ref);
					String label = ref.getName();
					subMenu.add(createAction(label, createRuleApplicationCommand(ruleApp)));
				}
			}
		}
		
		subMenu = new MenuManager(getLabel("CreateEdge") + ": " + node2.getType().getName()
				+ " -> " + node1.getType().getName());
		menuManager.add(subMenu);
		
		for (EReference ref : findReferenceTypes(node2.getType(), node1.getType())) {
			if (edgeExists(node2, ref, node1)) {
				subMenu.add(createUnrunnableItem(ref.getName() + " *Edge already exists*"));
			} else {
				if (multiplicityExceeded(node2, ref, node1)) {
					subMenu.add(createUnrunnableItem(ref.getName() + "*Multiplicity exceeded*"));
				} else {
					RuleApplication ruleApp = new RuleApplication(instanceGraphEngine,
							createEdgeForSourceAndTargetAndTypeRule);
					ruleApp.setParameterValue("source", node2);
					ruleApp.setParameterValue("target", node1);
					ruleApp.setParameterValue("type", ref);
					String label = ref.getName();
					subMenu.add(createAction(label, createRuleApplicationCommand(ruleApp)));
				}
			}
		}
	}
	
	protected boolean edgeExists(Node source, EReference ref, Node target) {
		RuleApplication ruleApp = new RuleApplication(instanceGraphEngine,
				edgeExistsForSourceAndTargetAndTypeRule);
		ruleApp.setParameterValue("source", source);
		ruleApp.setParameterValue("target", target);
		ruleApp.setParameterValue("referenceType", ref);
		if (ruleApp.findAllMatches().size() > 0)
			return true;
		else
			return false;
	}
	
	protected boolean multiplicityExceeded(Node source, EReference ref, Node target) {
		if (ref.getUpperBound() == -1) return false;
		RuleApplication ruleApp = new RuleApplication(instanceGraphEngine,
				findEdgesForSourceAndTypeRule);
		ruleApp.setParameterValue("source", source);
		ruleApp.setParameterValue("referenceType", ref);
		if (ruleApp.findAllMatches().size() < ref.getUpperBound()) return false;
		return true;
	}
	
	protected Collection<EReference> findReferenceTypes(EClass refOwner, EClass refType) {
		RuleApplication ruleApp = new RuleApplication(typeGraphEngine,
				findEReferencesForOwnerWithTypeRule);
		ruleApp.setParameterValue("owner", refOwner);
		ruleApp.setParameterValue("type", refType);
		Collection<EReference> references = new ArrayList<EReference>();
		for (RuleApplication matchApp : getMatchApplications(ruleApp)) {
			if (!matchApp.apply())
				throw new IllegalArgumentException("RuleApplication returned non-applicable match!");
			EReference ref = (EReference) matchApp.getComatch().getParameterValues()
					.get(findEReferencesForOwnerWithTypeRule.getParameterByName("reference"));
			references.add(ref);
		}
		return references;
	}
	
	@Override
	protected String getLocalHenshinFile() {
		return "transformations/CreateEdge/CreateEdge.henshin";
	}
	
}
