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
package org.eclipse.emf.henshin.editor.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;

/**
 * Copies a subgraph which is defined by a set of nodes into another given graph
 * and creates corresponding mappings.
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack (sjurack)
 */
public class CopySubgraphCommand extends CompoundCommand {
	
	private EditingDomain domain;
	private Collection<Node> subgraphNodes;
	private Collection<Mapping> mappings;
	
	private Graph targetGraph;
	private Graph sourceGraph;
	private Collection<Node> newNodes;
	private Collection<Mapping> newMappings;
	private Collection<Edge> newEdges;
	
	private Map<Node, Node> map = new HashMap<Node, Node>();
	
	private boolean isMappingOrigin = true;
	
	/**
	 * @param domain
	 */
	public void setDomain(EditingDomain domain) {
		this.domain = domain;
	}
	
	/**
	 * @param nodes
	 */
	public void setNodes(Collection<Node> nodes) {
		this.subgraphNodes = nodes;
	}
	
	/**
	 * @param mappings
	 */
	public void setMappings(Collection<Mapping> mappings) {
		this.mappings = mappings;
	}
	
	/**
	 * @param targetGraph
	 */
	public void setTargetGraph(Graph targetGraph) {
		this.targetGraph = targetGraph;
	}
	
	/**
	 * @param sourceGraph
	 */
	public void setSourceGraph(Graph sourceGraph) {
		this.sourceGraph = sourceGraph;
	}
	
	/**
	 * @param isMappingOrigin
	 */
	public void setMappingOrigin(boolean isMappingOrigin) {
		this.isMappingOrigin = isMappingOrigin;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.CompoundCommand#prepare()
	 */
	@Override
	protected boolean prepare() {
		
		for (Node node : subgraphNodes) {
			if (!node.getGraph().equals(sourceGraph)) {
				System.out.println("false");
				
				return false;
			}// if
		}// for
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.CompoundCommand#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		
		Collection<Node> nodesToCopy = new ArrayList<Node>();
		for (Node node : subgraphNodes) {
			if (!isMapped(node)) nodesToCopy.add(node);
		}
		Command copyCmd = CopyCommand.create(domain, nodesToCopy);
		appendAndExecute(copyCmd);
		newNodes = (Collection<Node>) copyCmd.getResult();
		
		newMappings = new ArrayList<Mapping>();
		Iterator<Node> origNodeIter = nodesToCopy.iterator();
		Iterator<Node> newNodeIter = newNodes.iterator();
		while (origNodeIter.hasNext() && newNodeIter.hasNext()) {
			Node origNode = origNodeIter.next();
			Node newNode = newNodeIter.next();
			Mapping m;
			if (isMappingOrigin)
				m = HenshinFactory.eINSTANCE.createMapping(origNode, newNode);
			else
				m = HenshinFactory.eINSTANCE.createMapping(newNode, origNode);
			map.put(origNode, newNode);
			newMappings.add(m);
		}
		
		newEdges = new ArrayList<Edge>();
		
		for (Edge origEdge : sourceGraph.getEdges()) {
			// check only edges between selected nodes
			//
			if (subgraphNodes.contains(origEdge.getSource())
					&& subgraphNodes.contains(origEdge.getTarget())) {
				
				// new edges need to be created if
				// 1. at least one of its nodes is copied.
				//
				if (nodesToCopy.contains(origEdge.getSource())
						|| nodesToCopy.contains(origEdge.getTarget())) {
					Node sourceNode;
					Node targetNode;
					if (map.containsKey(origEdge.getSource()))
						sourceNode = map.get(origEdge.getSource());
					else
						sourceNode = getMappedNode(origEdge.getSource());
					
					if (map.containsKey(origEdge.getTarget()))
						targetNode = map.get(origEdge.getTarget());
					else
						targetNode = getMappedNode(origEdge.getTarget());
					newEdges.add(HenshinFactory.eINSTANCE.createEdge(sourceNode, targetNode,
							origEdge.getType()));
				} else {
					// or
					// 2. both edges already exists without edge between them
					//
					boolean edgeFound = false;
					
					Node sourceNode = getMappedNode(origEdge.getSource());
					Node targetNode = getMappedNode(origEdge.getTarget());
					for (Edge targetEdge : targetGraph.getEdges()) {
						if (targetEdge.getSource() == sourceNode
								&& targetEdge.getTarget() == targetNode) {
							edgeFound = true;
							break;
						}
					}
					if (!edgeFound)
						newEdges.add(HenshinFactory.eINSTANCE.createEdge(sourceNode, targetNode,
								origEdge.getType()));
					
				}
			}
		}
		
		redo();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.CompoundCommand#redo()
	 */
	@Override
	public void redo() {
		targetGraph.getNodes().addAll(newNodes);
		targetGraph.getEdges().addAll(newEdges);
		mappings.addAll(newMappings);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		targetGraph.getNodes().removeAll(newNodes);
		targetGraph.getEdges().removeAll(newEdges);
		mappings.removeAll(newMappings);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.CompoundCommand#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return true;
	}
	
	/**
	 * @param node
	 * @return
	 */
	private boolean isMapped(Node node) {
		for (Mapping m : mappings) {
			if (isMappingOrigin) {
				if (m.getOrigin() == node) return true;
			} else if (m.getImage() == node) return true;
		}
		return false;
	}
	
	/**
	 * @param sourceGraphNode
	 * @return
	 */
	private Node getMappedNode(Node sourceGraphNode) {
		for (Mapping m : mappings) {
			if (isMappingOrigin) {
				if (m.getOrigin() == sourceGraphNode) return m.getImage();
			} else {
				if (m.getImage() == sourceGraphNode) return m.getOrigin();
			}
		}
		return null;
	}
	
}
