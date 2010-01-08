package org.eclipse.emf.henshin.diagram.edit.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.diagram.edit.commands.EdgeCreateCommand;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeConnectionRequest;
import org.eclipse.jface.viewers.ILabelProvider;

/**
 * A custom graphical node edit policy that pops up a selection menu
 * for the different edge types when drawing an edge.
 * 
 * @generated NOT
 * @author Christian Krause
 */
public class NodeGraphicalEditPolicy extends GraphicalNodeEditPolicy {
	
	/**
	 * Command that pops up a selection menu for edge types.
	 * @generated NOT
	 * @author Christian Krause
	 */
	private class PromptAndCreateEdgeCommand extends PromptAndCreateConnectionCommand {

		/**
		 * Default constructor.
		 * @param content Possible edge types.
		 * @param request CreateRequect.
		 */
		public PromptAndCreateEdgeCommand(List<EReference> content, CreateConnectionRequest request) {
			super(content, request);
		}
		
		@Override
		protected ILabelProvider getLabelProvider() {
			return new LabelProvider() {
				@Override
				public String getText(Object object) {
					return ((EReference )object).getName();
				}
			};
		}

	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy#getUnspecifiedConnectionCompleteCommand(org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeConnectionRequest)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected Command getUnspecifiedConnectionCompleteCommand(
			CreateUnspecifiedTypeConnectionRequest request) {
		
		// Reverse connection?
		if (request.isDirectionReversed()) {
			return getReversedUnspecifiedConnectionCompleteCommand(request);
		}
		
		// Get the possible edge types.
		List<EReference> references = getConnectionMenuContent(request);
		if (references.isEmpty()) {
			return null;
		}
		else if (references.size()==1) {
			request.getExtendedData().put(EdgeCreateCommand.TYPE_PARAMETER_KEY, references.get(0));
			return getConnectionCompleteCommand(HenshinElementTypes.Edge_4001, request);
		}
		else {
			return new ICommandProxy(new PromptAndCreateEdgeCommand(references, request));
		}
		
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
			if (reference.getEReferenceType().isSuperTypeOf(trg.getType())) {
				result.add(reference);
			}
		}
		
		return result;
		
	}
	
}
