/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.gc2ac.actions;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.presentation.HenshinEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import org.eclipse.emf.henshin.ocl2ac.gc2ac.core.Lefter;

public class LeftAction implements IObjectActionDelegate {

	// Workbench part:
	protected IWorkbenchPart workbenchPart;

	// Henshin rule:
	protected Rule rule;

	@Override
	public void run(IAction action) {
		runLeftCommand();
	}

	protected void runLeftCommand() {

		// Create the new command:
		Command command = new AbstractCommand("Left") {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.common.command.AbstractCommand#canUndo()
			 */
			@Override
			public boolean canUndo() {
				return false;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
			 */
			@Override
			public boolean prepare() {
				return true;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.common.command.Command#execute()
			 */
			public void execute() {
				redo();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.common.command.Command#redo()
			 */
			public void redo() {
				left();
			}
		};

		// Execute it:
		HenshinEditor editor = (HenshinEditor) workbenchPart;
		CommandStack stack = editor.getEditingDomain().getCommandStack();
		stack.execute(command);
	}

	protected void left() {
		Lefter lefter = new Lefter(rule);
		lefter.left();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		rule = null;
		if (selection instanceof IStructuredSelection) {
			Object first = ((IStructuredSelection) selection).getFirstElement();
			if (first instanceof Rule) {
				rule = (Rule) first;
			}
		}
		action.setEnabled(rule != null);
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart part) {
		workbenchPart = (part instanceof HenshinEditor) ? part : null;
		action.setEnabled(workbenchPart != null);
	}

}
