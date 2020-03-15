/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.edit.commands;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.diagram.edit.helpers.ColorModeHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.EClassComparator;
import org.eclipse.emf.henshin.diagram.edit.helpers.ModuleEditHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.RootObjectEditHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.RuleEditHelper;
import org.eclipse.emf.henshin.diagram.part.HenshinDiagramEditorPlugin;
import org.eclipse.emf.henshin.diagram.part.HenshinPaletteTools.EClassNodeTool;
import org.eclipse.emf.henshin.diagram.part.Messages;
import org.eclipse.emf.henshin.diagram.preferences.DiagramPreferenceInitializer;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.model.actions.MapEditor;
import org.eclipse.emf.henshin.model.actions.NodeActionHelper;
import org.eclipse.emf.henshin.model.actions.NodeMapEditor;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinModelCleaner;
import org.eclipse.emf.henshin.presentation.HenshinIcons;
import org.eclipse.emf.henshin.provider.util.HenshinColorMode;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * @generated
 */
public class NodeCreateCommand extends EditElementCommand {

	private static final ILabelProvider labelProvider = new LabelProvider() {

		@Override
		public String getText(Object element) {
			return ((EClassifier) element).getName();
		}

		@Override
		public Image getImage(Object element) {
			return HenshinIcons.ECLASS;
		}
	};

	private final Shell shell = HenshinDiagramEditorPlugin.getInstance().getWorkbench().getDisplay().getActiveShell();

	/**
	 * @generated
	 */
	public NodeCreateCommand(CreateElementRequest req) {
		super(req.getLabel(), null, req);
	}

	/**
	 * FIXME: replace with setElementToEdit()
	 * 
	 * @generated
	 */
	protected EObject getElementToEdit() {
		EObject container = ((CreateElementRequest) getRequest()).getContainer();
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
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		// The node is created in the context of a rule:
		Rule rule = (Rule) getElementToEdit();

		// Get the default action to be used:
		Action action = RuleEditHelper.getDefaultAction(rule);

		// Set the type of the nodes:
		CreateElementRequest request = (CreateElementRequest) getRequest();
		Object type = request.getParameter(EClassNodeTool.TYPE_PARAMETER_KEY);

		Module module = rule.getModule();
		SingleEClassSelectionDialog dialog = null;

		// if no type has been specified yet, let the user choose one
		if (type == null) {
			dialog = new SingleEClassSelectionDialog(module, action);
			type = dialog.openAndReturnSelection();
		}

		Node node;
		if (type instanceof EClass) {
			node = rule.createNode((EClass) type);
		} else {
			return CommandResult.newCancelledCommandResult();
		}

		// Update containment:
		updateContainment(rule, node);

		// Finally, we set the user-defined action:
		if (dialog != null) {
			action = dialog.getAction();
		}
		try {
			//Set action of node
			node.setAction(action);
			
			//update the ACs in the rule
		    NodeActionHelper.INSTANCE.updateACsAndSubrules(rule,node,null,action);
			
			//Set default-action for the next operation 
			RuleEditHelper.setDefaultAction(rule, action);
			
		} catch (Throwable t) {
			HenshinDiagramEditorPlugin.getInstance().logError("Error setting node action", t);
		}

		// Complete multi-rules:
		HenshinModelCleaner.completeMultiRules(rule.getRootRule());

		// Clean up:
		HenshinModelCleaner.cleanRule(rule.getRootRule());

		// Configure the new node:
		doConfigure(node, monitor, info);
		request.setNewElement(node);
		return CommandResult.newOKCommandResult(node);

	}

	/*
	 * Update containment for the new node.
	 */
	private void updateContainment(Rule rule, Node newNode) throws ExecutionException {

		// Update the root containment for the new node:
		View ruleView = RuleEditHelper.findRuleView(rule);
		RootObjectEditHelper.updateRootContainment(ruleView, newNode);

		// Check preferences:
		Boolean edgeCreationIsEnabled = HenshinDiagramEditorPlugin.getInstance().getPreferenceStore()
				.getBoolean(DiagramPreferenceInitializer.CREATE_CONTAINMENT_EDGES);
		if (!edgeCreationIsEnabled) {
			return;
		}

		// Check if it makes sense to create a containment edge to the new node:
		if (!newNode.getIncoming().isEmpty()) {
			return;
		}
		for (Node container : rule.getActionNodes(null)) {
			if (container == newNode || container.getType() == null) {
				continue;
			}
			for (EReference ref : container.getType().getEAllContainments()) {
				EClass refType = ref.getEReferenceType();
				if (refType != null && (refType.isSuperTypeOf(newNode.getType()))
						|| (newNode.getType()).isSuperTypeOf(refType)) {
					if (rule.canCreateEdge(container, newNode, ref)) {
						rule.createEdge(container, newNode, ref);
						return;
					}
				}
			}
		}
		
	}

	/**
	 * @generated
	 */
	protected void doConfigure(Node newElement, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		IElementType elementType = ((CreateElementRequest) getRequest()).getElementType();
		ConfigureRequest configureRequest = new ConfigureRequest(getEditingDomain(), newElement, elementType);
		configureRequest.setClientContext(((CreateElementRequest) getRequest()).getClientContext());
		configureRequest.addParameters(getRequest().getParameters());
		ICommand configureCommand = elementType.getEditCommand(configureRequest);
		if (configureCommand != null && configureCommand.canExecute()) {
			configureCommand.execute(monitor, info);
		}
	}

	/**
	 * Dialog for choosing an EClassifier in order to specify the node type to
	 * be created.
	 * 
	 * @author Stefan Jurack (sjurack), Christian Krause
	 * 
	 */
	private final class SingleEClassSelectionDialog extends ElementListSelectionDialog {

		final Module module;

		private Action action;

		/**
		 * Constructor
		 * 
		 * @param module
		 */
		public SingleEClassSelectionDialog(Module module, Action action) {
			super(shell, labelProvider);
			this.setMultipleSelection(false);
			this.setBlockOnOpen(true);
			this.setTitle(Messages.SingleEClassifierSelectionDialog_title);
			this.setMessage(Messages.SingleEClassifierSelectionDialog_msg);
			this.module = module;
			this.action = action;
		}// constructor

		/**
		 * Opens the dialog and returns the first selected EClass in the
		 * list. If no EClass is available in the module or
		 * the dialog is canceled, null is returned.
		 * 
		 * @return
		 */
		public final EClass openAndReturnSelection() {

			List<EClass> elements = new ArrayList<EClass>(ModuleEditHelper.collectAllEClasses(module, false));

			EClass result = null;
			if (elements.size() > 0) {
				Collections.sort(elements, new EClassComparator());
				this.setElements(elements.toArray());
				int returnCode = this.open();
				if (returnCode == ElementListSelectionDialog.OK) {
					result = (EClass) getFirstResult();
				}
			}
			return result;
		}

		/*
		 * @see Dialog#createDialogArea(Composite)
		 */
		@Override
		protected Control createDialogArea(Composite parent) {
			Composite contents = (Composite) super.createDialogArea(parent);

			// Action group:
			Group group = new Group(contents, SWT.NONE);
			GridData data = new GridData();
			data.grabExcessHorizontalSpace = true;
			data.horizontalAlignment = GridData.FILL;
			data.verticalAlignment = GridData.END;

			group.setLayoutData(data);
			group.setFont(parent.getFont());
			group.setText("Action");
			group.setLayout(new GridLayout(2, false));

			createLabel("Action Type:", group);
			Composite buttons = new Composite(group, SWT.NONE);
			buttons.setLayout(new RowLayout(SWT.HORIZONTAL));

			// Get the default color mode:
			HenshinColorMode colorMode = HenshinColorMode.getDefaultColorMode();

			// Action types as radio buttons:
			for (final Type type : Type.values()) {
				Button button = new Button(buttons, SWT.RADIO);
				button.setText(type.toString());

				// Set the action color:
				if (colorMode != null) {
					HenshinColorMode.Color color = colorMode.getActionColor(new Action(type), true);
					if (color != null) {
						button.setForeground(ColorModeHelper.getSWTColor(color));
					}
				}

				if (type == action.getType()) {
					button.setSelection(true);
				}
				button.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						action = new Action(type, action.isMulti(), action.getPath(), action.getFragment());
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						widgetSelected(e);
					}
				});
			}

			// Multi-flag:
			createLabel("Multi-node:", group);
			final Button multi = new Button(group, SWT.CHECK);
			multi.setSelection(action.isMulti());
			multi.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					action = new Action(action.getType(), multi.getSelection(), action.getPath());
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});

			// Path:
			createLabel("Path:", group);
			final Text path = new Text(group, SWT.BORDER | SWT.SINGLE);
			path.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			String text = action.toString().replaceFirst(action.getType().toString(), "");
			if (action.isMulti()) {
				text = text.replaceFirst("\\" + Action.MULTI_MARKER + "", "");
			}

			path.setText(text);
			path.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					String text = action.getType().toString();
					if (action.isMulti()) {
						text = text + Action.MULTI_MARKER;
					}
					String pathText = path.getText().trim();
					if (pathText.length() > 0 && !pathText.startsWith(Action.PATH_SEPARATOR + "")
							&& !pathText.startsWith(Action.FRAGMENT_START + "")) {
						if (action.isMulti()) {
							pathText = Action.PATH_SEPARATOR + pathText;
						} else if (action.getType() == Type.FORBID || action.getType() == Type.REQUIRE) {
							pathText = Action.FRAGMENT_START + pathText;
						}
					}
					text = text + pathText;
					try {
						action = Action.parse(text);
					} catch (ParseException e1) {
					}
				}
			});
			return contents;
		}

		private Label createLabel(String label, Composite grid) {
			Label l = new Label(grid, SWT.NONE);
			l.setText(label);
			l.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
			applyDialogFont(l);
			return l;
		}

		public Action getAction() {
			return action;
		}

	}// inner class

}
