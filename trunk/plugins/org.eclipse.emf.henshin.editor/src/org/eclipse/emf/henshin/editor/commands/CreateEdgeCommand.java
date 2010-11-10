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

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;

/**
 * Creates an {@link Edge} of a given type between two given {@link Node}s.
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack (sjurack)
 * 
 */
public class CreateEdgeCommand extends AbstractCommand {
	
	protected Node source;
	protected Node target;
	protected EReference edgeType;
	protected Graph graph;
	protected Edge edge;
	
	public CreateEdgeCommand(Node source, Node target, EReference edgeType) {
		this.source = source;
		this.target = target;
		this.edgeType = edgeType;
	}
	
	@Override
	protected boolean prepare() {
		graph = source.getGraph();
		return QuantUtil.noneNull(source, target, edgeType, graph);
	}
	
	@Override
	public void execute() {
		edge = HenshinFactory.eINSTANCE.createEdge();
		edge.setSource(source);
		edge.setTarget(target);
		edge.setType(edgeType);
		redo();
	}
	
	@Override
	public void undo() {
		graph.getEdges().remove(edge);
	}
	
	@Override
	public void redo() {
		graph.getEdges().add(edge);
	}
	
	@Override
	public Collection<?> getAffectedObjects() {
		return Collections.singleton(edge);
	}
	
}
