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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;

/**
 * Static methods for determining and changing actions for edges.
 * @generated NOT
 * @author Christian Krause
 * @deprecated
 */
class DeprecatedEdgeActionUtil {
	
	/**
	 * Set the action for an edge.
	 * @param edge Edge.
	 * @param action Action.
	 */
	public static void setEdgeAction(Edge edge, Action action) {
		
		// Get the current action.
		Action current = null; //getEdgeAction(edge);
		if (action.equals(current)) return;
		
		// Get the container graph, rule, source and target.
		Graph graph = edge.getGraph();
		Rule rule = graph.getContainerRule();
		Node source = edge.getSource();
		Node target = edge.getTarget();
		
		// Current action type = NONE?
		if (current.getType()==ActionType.NONE) {
			
			// We know that the edge is contained in the LHS and that it is mapped to an edge in the RHS.
			
			// Find the the image in the RHS:
			Edge image = HenshinMappingUtil.getEdgeImage(edge, rule.getRhs(), rule.getMappings());
			Node sourceImage = image.getSource();
			Node targetImage = image.getTarget();
			
			// We delete the edge image:
			graph.removeEdge(image);
			
			// For CREATE actions, move the edge to the RHS:
			if (action.getType()==ActionType.CREATE) {
				edge.setSource(sourceImage);
				edge.setTarget(targetImage);
				edge.setGraph(rule.getRhs());
			}
			
		}
		
		// Current action type = CREATE?
		else if (current.getType()==ActionType.CREATE) {
			
			// We know that the edge is contained in the RHS and that it is not an image of a mapping.
			Node sourceOrigin = HenshinMappingUtil.getNodeOrigin(source, rule.getMappings());
			Node targetOrigin = HenshinMappingUtil.getNodeOrigin(target, rule.getMappings());
			if (sourceOrigin==null || targetOrigin==null) return;
			
			// Move the edge to the LHS:
			edge.setSource(sourceOrigin);
			edge.setTarget(targetOrigin);
			edge.setGraph(rule.getLhs());
			
			// For NONE actions, copy the edge to the RHS again:
			if (action.getType()==ActionType.NONE) {
				Edge copy = (Edge) EcoreUtil.copy(edge);
				copy.setSource(source);
				copy.setTarget(target);
				copy.setGraph(rule.getRhs());
			}
			
		}

		// Current action type = DELETE?
		else if (current.getType()==ActionType.DELETE) {
			
			// We know that the node is contained in the LHS and that it has no image in the RHS.
			Node sourceImage = HenshinMappingUtil.getNodeImage(source, rule.getRhs(), rule.getMappings());
			Node targetImage = HenshinMappingUtil.getNodeImage(target, rule.getRhs(), rule.getMappings());
			if (sourceImage==null || targetImage==null) return;
			
			// For NONE actions, create a copy of the edge in the RHS:
			if (action.getType()==ActionType.NONE) {
				Edge copy = (Edge) EcoreUtil.copy(edge);
				copy.setSource(sourceImage);
				copy.setTarget(targetImage);
				copy.setGraph(rule.getRhs());
			}
			
			// For CREATE actions, move the edge to the RHS:
			else if (action.getType()==ActionType.CREATE) {
				edge.setSource(sourceImage);
				edge.setTarget(targetImage);
				edge.setGraph(rule.getRhs());
			}
		}		
		
	}
	
}
