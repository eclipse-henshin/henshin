package org.eclipse.emf.henshin.interpreter.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.emf.henshin.interpreter.debug.DebugValueObject;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugVariable;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

public class AddConstraintInstanceBreakpoint extends AbstractHandler implements IActionDelegate {

	private HenshinDebugVariable var;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run(IAction action) {
		// get the current variable and set a breakpoint for it
		if (var != null) {
			DebugValueObject value;
			try {
				value = (DebugValueObject)var.getValue();
				DebugApplicationCondition.getInstance().setConstraintInstanceBreakpoint(value.getValueString());
			} catch (DebugException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		var = (HenshinDebugVariable) structuredSelection.getFirstElement();
		
		action.setEnabled(var != null && var.isConstraint());
	}

}
