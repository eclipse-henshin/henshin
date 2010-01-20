package org.eclipse.emf.henshin.diagram.parsers;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.diagram.actions.Action;
import org.eclipse.emf.henshin.diagram.actions.ActionType;
import org.eclipse.emf.henshin.diagram.actions.NodeActionUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

/**
 * @generated NOT
 * @author Christian Krause
 */
public class AttributeParser  extends AbstractParser {
	
	/**
	 * Default constructor.
	 */
	public AttributeParser() {
		super(new EAttribute[] { HenshinPackage.eINSTANCE.getNamedElement_Name() });
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getPrintString(IAdaptable element, int flags) {
		Attribute attribute = (Attribute) element.getAdapter(EObject.class);
		String type = attribute.getType()!=null ? attribute.getType().getName() : null;
		return type + "=" + attribute.getValue();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getEditString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getEditString(IAdaptable element, int flags) {
		return getPrintString(element, flags);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getParseCommand(org.eclipse.core.runtime.IAdaptable, java.lang.String, int)
	 */
	public ICommand getParseCommand(IAdaptable element, final String value, int flags) {
		
		// Resolve the attribute:
		final Attribute attribute = (Attribute) element.getAdapter(EObject.class);
		
		// Get the editing domain:
		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(attribute);
		if (editingDomain == null) {
			return UnexecutableCommand.INSTANCE;
		}
		
		// Create parse command:
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(editingDomain, "Parse Attribute", null) {
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				return doParsing(value, attribute);
			}
		};
		return command;
		
	}
	
	/*
	 * Parse an attribute.
	 */
	private CommandResult doParsing(String value, Attribute attribute) {
		
		Node node = attribute.getNode();
		if (node==null || node.getType()==null) {
			return CommandResult.newErrorCommandResult("Node and node type must be set");
		}
		
		int equalSign = value.indexOf('=');
		if (equalSign<0) {
			return CommandResult.newErrorCommandResult("Expected '='");
		}
		
		String name = value.substring(equalSign+1).trim();
		String type = value.substring(0,equalSign).trim();
		
		// Find the EAttribute:
		EAttribute attr = null;
		for (EAttribute current : node.getType().getEAllAttributes()) {
			if (type.equals(current.getName())) {
				attr = current;
				break;
			}
		}
		
		if (attr==null) {
			return CommandResult.newErrorCommandResult("Unknown attribute: " + type);
		}
		
		// Set the properties:
		attribute.setValue(name);
		attribute.setType(attr);
		
		// Update mapped node as well:
		Action action = NodeActionUtil.getNodeAction(node);
		if (action!=null && action.getType()==ActionType.NONE) {
			
			Rule rule = node.getGraph().getContainerRule();
			Node image = HenshinMappingUtil.getImage(node, rule.getRhs(), rule.getMappings());
			Attribute imageAttribute = image.findAttributeOfType(attr);
			
			// Update the image attribute:
			if (imageAttribute!=null) {
				imageAttribute.setValue(name);
			} else {
				imageAttribute = (Attribute) EcoreUtil.copy(attribute);
				image.getAttributes().add(imageAttribute);
			}
		}
		
		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.parsers.AbstractParser#isAffectingFeature(java.lang.Object)
	 */
	@Override
	protected boolean isAffectingFeature(Object feature) {
		if (feature==HenshinPackage.eINSTANCE.getAttribute_Value()) return true;
		if (feature==HenshinPackage.eINSTANCE.getAttribute_Type()) return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isValidEditString(org.eclipse.core.runtime.IAdaptable, java.lang.String)
	 */
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}

}
