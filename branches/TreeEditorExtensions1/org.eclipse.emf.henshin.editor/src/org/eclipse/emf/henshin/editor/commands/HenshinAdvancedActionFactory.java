package org.eclipse.emf.henshin.editor.commands;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.action.StaticSelectionCommandAction;
import org.eclipse.emf.henshin.editor.commands.node.RemoveImageNodeCommand;
import org.eclipse.emf.henshin.editor.commands.node.RemoveOriginNodeCommand;
import org.eclipse.emf.henshin.editor.commands.rule.AddMappedNodeCommand;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;

public class HenshinAdvancedActionFactory {

	protected EditingDomain editingDomain;
	protected IEditorPart activeEditorPart;

	public HenshinAdvancedActionFactory(IEditorPart part) {
		activeEditorPart = part;
		editingDomain = ((IEditingDomainProvider) part).getEditingDomain();
		
	}

	public Collection<IAction> generateAdvancedCreateActions(EditingDomain editingDomain, ISelection selection) {
		Collection<IAction> actions = new ArrayList<IAction>();

		if (!(selection instanceof IStructuredSelection && ((IStructuredSelection) selection).size() == 1))
			return actions;

		IStructuredSelection sselection = (IStructuredSelection) selection;

		Object selObject = sselection.getFirstElement();

		if (selObject instanceof Rule) {
			addAdvancedRuleActions(actions, sselection, (Rule) selObject);
		}
		if (selObject instanceof Node) {
			addAdvancedNodeActions(actions, sselection, (Node) selObject);
		}

		return actions;
	}

	protected void addAdvancedRuleActions(Collection<IAction> actions, IStructuredSelection selection, Rule rule) {

		AddMappedNodeCommand cmd = new AddMappedNodeCommand();
		cmd.setRule(rule);
		StaticSelectionCommandAction action = wrapCommand("Create Mapped Node", cmd);
		action.configureAction(selection);
		actions.add(action);
	}
	

	protected void addAdvancedNodeActions(Collection<IAction> actions, IStructuredSelection selection, Node node) {

		RemoveImageNodeCommand removeImageCmd = new RemoveImageNodeCommand();
		removeImageCmd.setNode(node);
		StaticSelectionCommandAction action = wrapCommand("Remove Image Node", removeImageCmd);
		action.configureAction(selection);
		actions.add(action);
		
		RemoveOriginNodeCommand removeOriginCmd = new RemoveOriginNodeCommand();
		removeOriginCmd.setNode(node);
		action = wrapCommand("Remove Origin Node", removeOriginCmd);
		action.configureAction(selection);
		actions.add(action);		
		
	}

	protected StaticSelectionCommandAction wrapCommand(final String label, final Command cmd) {
		StaticSelectionCommandAction action = new StaticSelectionCommandAction(editingDomain) {

			@Override
			public String getText() {
				return label;
			}

			@Override
			public void run() {
				super.run();
			}

			@Override
			protected Command createActionCommand(EditingDomain editingDomain, final Collection<?> collection) {
				return cmd;
			}
		};
		return action;
	}

}
