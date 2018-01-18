package org.eclipse.emf.henshin.editor.commands;

import java.util.List;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.RuleGeneralizer;

/**
 * @author Timo Kehrer
 */
public class GeneralizeRuleCommand extends AbstractCommand {

	private List<?> elements;

	public void setElements(List<?> elements) {
		this.elements = elements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return !elements.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#execute()
	 */
	@Override
	public void execute() {
		for (Object element : elements) {
			if (element instanceof Rule) {
				// HenshinModelCleaner.cleanRule((Rule) element);
				System.out.println("GeneralizeRuleCommand::execute");
				RuleGeneralizer.generalizeRule((Rule) element);
			}
		}

	}

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
	 * @see org.eclipse.emf.common.command.Command#redo()
	 */
	@Override
	public void redo() {
	}

}
