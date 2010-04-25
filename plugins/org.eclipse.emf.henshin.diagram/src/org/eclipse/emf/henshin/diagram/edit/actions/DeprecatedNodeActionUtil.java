/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.diagram.edit.actions;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.diagram.edit.maps.NodeMapEditor;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;
import org.eclipse.emf.henshin.model.util.HenshinNACUtil;

/**
 * Static methods for determining and changing action for nodes.
 * @generated NOT
 * @author Christian Krause
 * @deprecated
 */
class DeprecatedNodeActionUtil {
	
	/**
	 * Determine the action for a node. If this returns <code>null</code>
	 * the node is not considered to be a proper action node and hence
	 * should not be displayed in an integrated rule view.
	 * @param node Node.
	 * @return Action or <code>null</code>.
	 */
	public static Action getNodeAction(Node node) {
		return null; //HenshinActionGetter.getAction(node);
	}
	
	/**
	 * Set the action for a node.
	 * @param node Node.
	 * @param action Action.
	 * @deprecated
	 */
	public static void setNodeAction(Node node, Action action) {
		
		// Get the current action.
		Action current = getNodeAction(node);
		if (action.equals(current)) return;
		
		// Get the container graph and rule.
		Graph graph = node.getGraph();
		Rule rule = graph.getContainerRule();
		NodeMapEditor editor;
		
		// Current action type = NONE?
		if (current.getType()==ActionType.NONE) {
			
			// We know that the node is contained in the LHS and that it is mapped to a node in the RHS.
			editor = new NodeMapEditor(rule.getLhs(), rule.getRhs(), rule.getMappings());
			Node image = editor.getOpposite(node);
			
			// For DELETE actions, delete the image in the RHS:
			if (action.getType()==ActionType.DELETE) {
				editor.remove(image);
			}
			
			// For CREATE actions, replace the image in the RHS by the origin:
			else if (action.getType()==ActionType.CREATE) {
				editor.replace(image);
			}
			
			// For FORBID actions, delete the image in the RHS and move the node to the NAC:
			else if (action.getType()==ActionType.FORBID) {
				editor.remove(image);
				
				// Move the node to the NAC:
				NestedCondition nac = ActionNACUtil.getOrCreateNAC(action, rule);
				editor = new NodeMapEditor(rule.getLhs(), nac.getConclusion(), nac.getMappings());
				editor.move(node);
			}
			
		}
		
		// Current action type = CREATE?
		else if (current.getType()==ActionType.CREATE) {
			
			// We know that the node is contained in the RHS and that it is not an image of a mapping.
			editor = new NodeMapEditor(rule.getLhs(), rule.getRhs(), rule.getMappings());
			
			// We move the node to the LHS in any case:
			editor.move(node);
			
			// For NONE actions, create a copy of the node in the RHS and map to it:
			if (action.getType()==ActionType.NONE) {
				editor.copy(node);
			}
			
			// For FORBID actions, move the node further to the NAC:
			else if (action.getType()==ActionType.FORBID) {
				NestedCondition nac = ActionNACUtil.getOrCreateNAC(action, rule);
				editor = new NodeMapEditor(rule.getLhs(), nac.getConclusion(), nac.getMappings());
				editor.move(node);
			}
			
		}

		// Current action type = DELETE?
		else if (current.getType()==ActionType.DELETE) {
			
			// We know that the node is contained in the LHS and that it has no image in the RHS.
			editor = new NodeMapEditor(rule.getLhs(), rule.getRhs(), rule.getMappings());
			
			// For NONE actions, create a copy of the node in the RHS and map to it:
			if (action.getType()==ActionType.NONE) {
				editor.copy(node);
			}
			
			// For CREATE actions, move the node to the RHS:
			else if (action.getType()==ActionType.CREATE) {
				editor.move(node);
			}
			
			// For FORBID actions, move the node to the NAC:
			else if (action.getType()==ActionType.FORBID) {
				NestedCondition nac = ActionNACUtil.getOrCreateNAC(action, rule);
				editor = new NodeMapEditor(rule.getLhs(), nac.getConclusion(), nac.getMappings());
				editor.move(node);
			}
			
		}		
		
		// Current action type = FORBID?
		else if (current.getType()==ActionType.FORBID) {
			
			// We know that the node is contained in a NAC and that it has no origin in the LHS.
			NestedCondition nac = (NestedCondition) graph.eContainer();
			editor = new NodeMapEditor(rule.getLhs(), nac.getConclusion(), nac.getMappings());
			
			// We move the node to the LHS in any case:
			editor.move(node);
			
			// For NONE actions, create a copy in the RHS as well:
			if (action.getType()==ActionType.NONE) {
				editor = new NodeMapEditor(rule.getLhs(), rule.getRhs(), rule.getMappings());
				editor.copy(node);
			}

			// For FORBID actions, move the node to the new NAC:
			else if (action.getType()==ActionType.FORBID) {
				NestedCondition newNac = ActionNACUtil.getOrCreateNAC(action, rule);
				editor = new NodeMapEditor(rule.getLhs(), newNac.getConclusion(), newNac.getMappings());
				editor.move(node);
			}
			
			// Delete the NAC is it became empty or trivial due to the current change.
			if (HenshinNACUtil.isTrivialNAC(nac)) {
				HenshinNACUtil.removeNAC(rule, nac);
			}
			
		}

	}

	/**
	 * Get all nodes in a rule that are associated with the given argument action.
	 * @param rule Rule.
	 * @param action Action or <code>null</code> for any action.
	 * @return List of nodes.
	 */
	public static List<Node> getActionNodes(Rule rule, Action action) {
		return null; // HenshinActionGetter.getActionElements(rule, action, HenshinPackage.eINSTANCE.getGraph_Nodes());
	}


}
