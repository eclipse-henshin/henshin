package org.eclipse.emf.henshin.interpreter.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugValue;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugVariable;
import org.eclipse.emf.henshin.interpreter.matching.conditions.debug.DebugApplicationCondition;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

public class AddValueBreakpoint extends AbstractHandler implements IActionDelegate {

	private HenshinDebugVariable var;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("execute --- --- --- --- --- --- ");
		return null;
	}

	@Override
	public void run(IAction action) {
		if (var != null) {
			try {
				HenshinDebugValue val = (HenshinDebugValue) var.getValue();
				
				// get index and set value breakpoint with index and value
				int index = val.getIndexInDomain();
				DebugApplicationCondition.getInstance().setValueBreakpoint(val, index);
							
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
		if (var != null) {
			// if there is no variable we now we look at a value, so we disable 'Add Variable Breakpoint'
			try {
				Variable variable = var.getVariable();
				HenshinDebugValue val;
				val = (HenshinDebugValue)var.getValue();
				
				// if index in domain is -1 we do not look at a variable, nor a value - which means we clicked on a domain
				int index = val.getIndexInDomain();
				action.setEnabled(variable == null && index >= 0);
			} catch (DebugException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}

}
