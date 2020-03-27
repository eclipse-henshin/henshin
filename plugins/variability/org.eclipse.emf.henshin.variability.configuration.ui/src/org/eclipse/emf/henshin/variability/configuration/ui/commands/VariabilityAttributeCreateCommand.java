package org.eclipse.emf.henshin.variability.configuration.ui.commands;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.diagram.edit.commands.AttributeCreateCommand;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinModelCleaner;
import org.eclipse.emf.henshin.variability.configuration.ui.helpers.VariabilityModelHelper;
import org.eclipse.emf.henshin.variability.wrapper.TransactionalVariabilityFactory;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.menus.PopupMenu;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.swt.widgets.Shell;

import configuration.Configuration;

/**
 * This class provides a command to create variability enabled {@link Attribute}s.
 * 
 * @author Stefan Schulz
 *
 */
public class VariabilityAttributeCreateCommand extends AttributeCreateCommand {
	
	private final Configuration configuration;

	public VariabilityAttributeCreateCommand(CreateElementRequest req, Shell shell, Configuration configuration) {
		super(req, shell);
		this.configuration = configuration;
	}

	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {

		// The node and the rule:
		Node node = (Node) getElementToEdit();
		Rule rule = node.getGraph().getRule();

		List<EAttribute> attributes = getCandidateAttributes(node);
		EAttribute type = null;

		// Display the pop-up menu:
		if (shell!=null && attributes.size()>1) {
			PopupMenu menu = getPopupMenu(attributes);
			if (menu.show(shell) == false) {
				monitor.setCanceled(true);
				return CommandResult.newCancelledCommandResult();
			}
			type = (EAttribute) menu.getResult();
		} else {
			type = attributes.get(0);
		}

		// Create the attribute.
		Attribute attribute = HenshinFactory.eINSTANCE.createAttribute();
		attribute.setType(type);
		Parameter param = null;
		for (Parameter p : rule.getParameters()) {
			if (p.getType()==null || p.getType()==type.getEAttributeType()) {
				param = p;
				break;
			}
		}
		attribute.setValue(param!=null ? param.getName() : String.valueOf(type.getDefaultValue()));
		TransactionalVariabilityFactory.INSTANCE.createVariabilityAttribute(attribute).setPresenceCondition(VariabilityModelHelper.getPresenceCondition(configuration));
		node.getAttributes().add(attribute);

		// and to all mapped nodes...
		Node lhsNode = getLhsNode(node);
		if (lhsNode != null) {
			Node rhsNode = rule.getMappings().getImage(lhsNode, rule.getRhs());
			if (rhsNode != null) {
				addAttribute(rhsNode, (Attribute) EcoreUtil.copy(attribute));
			}
			for (NestedCondition ac : rule.getLhs().getNestedConditions()) {
				Node acNode = ac.getMappings().getImage(lhsNode,
						ac.getConclusion());
				if (acNode != null) {
					addAttribute(acNode, (Attribute) EcoreUtil.copy(attribute));
				}
			}
		}

		// Clean up:
		HenshinModelCleaner.cleanRule(rule.getRootRule());
		
		doConfigure(attribute, monitor, info);
		((CreateElementRequest) getRequest()).setNewElement(attribute);
		return CommandResult.newOKCommandResult(attribute);
	}
}
