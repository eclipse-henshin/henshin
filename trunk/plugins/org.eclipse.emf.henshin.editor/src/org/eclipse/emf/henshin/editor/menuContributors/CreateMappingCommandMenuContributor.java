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

import java.util.List;

import org.eclipse.emf.henshin.editor.commands.CreateMappingCommand;
import org.eclipse.emf.henshin.editor.commands.MenuContributor;
import org.eclipse.emf.henshin.editor.commands.QuantUtil;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
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
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.henshin.editor.commands.MenuContributor#contributeActions
	 * (org.eclipse.jface.action.IMenuManager, java.util.List)
	 */
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
		// if (!QuantUtil.allIdenticalAndNotNull(sourceNode.getType(),
		// targetNode.getType())) return;
		
		CreateMappingCommand cmd = new CreateMappingCommand(sourceNode, targetNode);
		if (cmd.canExecute()) menuManager.add(createAction(getLabel(COMMAND_LABEL), cmd));
		
		cmd = new CreateMappingCommand(targetNode, sourceNode);
		if (cmd.canExecute()) menuManager.add(createAction(getLabel(COMMAND_LABEL), cmd));
		
	}// contributeActions
	
}// class
