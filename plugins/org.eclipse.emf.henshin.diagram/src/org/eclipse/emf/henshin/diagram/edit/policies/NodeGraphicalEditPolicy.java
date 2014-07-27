/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.edit.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.diagram.edit.commands.EdgeCreateCommand;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.presentation.HenshinIcons;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeConnectionRequest;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * A custom graphical node edit policy that pops up a selection menu
 * for the different edge types when drawing an edge.
 * 
 * @generated NOT
 * @author Christian Krause
 */
public class NodeGraphicalEditPolicy extends GraphicalNodeEditPolicy {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy#getConnectionCompleteCommand(java.lang.Object, org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected Command getConnectionCompleteCommand(Object type, CreateConnectionRequest request) {
		if (type instanceof EReference &&
			request instanceof CreateUnspecifiedTypeConnectionRequest) {
			CreateRequest realRequest = ((CreateUnspecifiedTypeConnectionRequest) request).getRequestForType(HenshinElementTypes.Edge_4001);
			if (realRequest!=null) {
				realRequest.getExtendedData().put(EdgeCreateCommand.TYPE_PARAMETER_KEY, type);
			}
			type = HenshinElementTypes.Edge_4001;
		}
		return super.getConnectionCompleteCommand(type, request);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy#getConnectionMenuContent(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected List<EReference> getConnectionMenuContent(CreateConnectionRequest request) {
		
		// Source and target edit parts:
		IGraphicalEditPart source = (IGraphicalEditPart) request.getSourceEditPart();
		IGraphicalEditPart target = (IGraphicalEditPart) request.getTargetEditPart();
		
		// Corresponding nodes:
		Node src = (Node) source.getNotationView().getElement();
		Node trg = (Node) target.getNotationView().getElement();
		
		//no type was assigned yet
		if (src.getType()==null)
			return new ArrayList<EReference>();
		
		Rule rule = src.getGraph().getRule();
		
		// Collect all matching references:
		List<EReference> result = new ArrayList<EReference>();
		for (EReference reference : src.getType().getEAllReferences()) {
			if (rule.canCreateEdge(src, trg, reference)) {
				result.add(reference);
			}
		}
		
		return result;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy#getPromptAndCreateConnectionCommand(java.util.List, org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected ICommand getPromptAndCreateConnectionCommand(List content, CreateConnectionRequest request) {
		return new PromptAndCreateEdgeCommand(content, request);
	}
	
	/*
	 * Command that pops up a selection menu for edge types.
	 */
	private class PromptAndCreateEdgeCommand extends PromptAndCreateConnectionCommand {
		
		public PromptAndCreateEdgeCommand(List<EReference> content, CreateConnectionRequest request) {
			super(content, request);
		}
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.gmf.runtime.diagram.ui.commands.CreateOrSelectElementCommand#getLabelProvider()
		 */
		@Override
		protected ILabelProvider getLabelProvider() {
			return new EReferenceLabelProvider();
		}
		
	}
	
	/*
	 * Label provider class for EReferences.
	 */
	private class EReferenceLabelProvider extends LabelProvider {
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(Object object) {
			return ((EReference )object).getName();
		}
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
		 */
		@Override
		public Image getImage(Object element) {
			return HenshinIcons.EREFERENCE;
		}
		
	}
	
}
