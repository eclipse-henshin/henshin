package org.eclipse.emf.henshin.interpreter.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugVariable;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;

public class AddVariableBreakpoint extends AbstractHandler implements IActionDelegate {
	
	private HenshinDebugVariable var;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("execute --- --- --- --- --- --- ");
		return null;
	}

	@Override
	public void run(IAction action) {		
		// get the current variable and set a breakpoint for it
		if (var != null) {
			Variable variable = var.getVariable();
			
			if (variable != null) {
				DebugApplicationCondition.getInstance().setVariableBreakpoint(variable);
			}
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		var = (HenshinDebugVariable) structuredSelection.getFirstElement();

		// get variable
		if (var != null) {
			Variable variable = var.getVariable();
			action.setEnabled(variable != null);
		}
	}

}
