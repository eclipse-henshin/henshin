package org.eclipse.emf.henshin.diagram.edit.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.diagram.edit.commands.EdgeCreateCommand;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeConnectionRequest;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;

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
		
		// Both types must be set:
		List<EReference> result = new ArrayList<EReference>();
		if (src.getType()==null || trg.getType()==null) {
			return result;
		}
		
		// Collect all matching references:
		for (EReference reference : src.getType().getEReferences()) {
			EClass refType = reference.getEReferenceType();
			if (trg.getType().isSuperTypeOf(refType)) {
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
		
	}
	
}
