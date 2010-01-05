package org.eclipse.emf.henshin.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class NodeCreateCommand extends EditElementCommand {

	/**
	 * Key for the node type parameter in creation requests.
	 * @generated NOT
	 */
	public static final String TYPE_PARAMETER_KEY = "henshin_node_type";

	/**
	 * @generated
	 */
	public NodeCreateCommand(CreateElementRequest req) {
		super(req.getLabel(), null, req);
	}

	/**
	 * FIXME: replace with setElementToEdit()
	 * @generated
	 */
	protected EObject getElementToEdit() {
		EObject container = ((CreateElementRequest) getRequest())
				.getContainer();
		if (container instanceof View) {
			container = ((View) container).getElement();
		}
		return container;
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		return true;

	}

	/**
	 * @generated NOT
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {

		// The node is created in the context of a rule (NONE-action):
		Rule rule = (Rule) getElementToEdit();
		if (rule.getLhs() == null) {
			Graph lhs = HenshinFactory.eINSTANCE.createGraph();
			lhs.setName("LHS");
			rule.setLhs(lhs);
		}
		if (rule.getRhs() == null) {
			Graph rhs = HenshinFactory.eINSTANCE.createGraph();
			rhs.setName("RHS");
			rule.setRhs(rhs);
		}

		// Create two new node instances:
		Node lhsNode = HenshinFactory.eINSTANCE.createNode();
		Node rhsNode = HenshinFactory.eINSTANCE.createNode();

		// Add them to the LHS / RHS:
		rule.getLhs().getNodes().add(lhsNode);
		rule.getRhs().getNodes().add(rhsNode);

		// Create a mapping:
		Mapping mapping = HenshinFactory.eINSTANCE.createMapping();
		mapping.setOrigin(lhsNode);
		mapping.setImage(rhsNode);
		rule.getMappings().add(mapping);

		// Set the type of the nodes:
		CreateElementRequest request = (CreateElementRequest) getRequest();
		if (request.getParameter(TYPE_PARAMETER_KEY) instanceof EClass) {
			EClass type = (EClass) request.getParameter(TYPE_PARAMETER_KEY);
			lhsNode.setType(type);
			rhsNode.setType(type);
		}

		// This shouldn't do anything, but we call it to be sure:
		doConfigure(lhsNode, monitor, info);

		request.setNewElement(lhsNode);
		return CommandResult.newOKCommandResult(lhsNode);

	}

	/**
	 * @generated
	 */
	protected void doConfigure(Node newElement, IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		IElementType elementType = ((CreateElementRequest) getRequest())
				.getElementType();
		ConfigureRequest configureRequest = new ConfigureRequest(
				getEditingDomain(), newElement, elementType);
		configureRequest.setClientContext(((CreateElementRequest) getRequest())
				.getClientContext());
		configureRequest.addParameters(getRequest().getParameters());
		ICommand configureCommand = elementType
				.getEditCommand(configureRequest);
		if (configureCommand != null && configureCommand.canExecute()) {
			configureCommand.execute(monitor, info);
		}
	}

}
