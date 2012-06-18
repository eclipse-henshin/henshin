package org.eclipse.emf.henshin.migration.wizard;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.handlers.HandlerUtil;

public class MigrationWizardHandler extends AbstractHandler {
		
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);
		
		if (selection.size() == 1) {
			Object firstElement = selection.getFirstElement();
			if (firstElement instanceof IFile) {
				IFile iFile = (IFile) firstElement;
				if (!iFile.getFileExtension().equals("henshin")) {
					return null;
				}
				MigrationWizard migrationWizard = new MigrationWizard(iFile);
				WizardDialog wd = new WizardDialog(HandlerUtil.getActiveShell(event), migrationWizard);
				wd.open();
			}
		}
		return null;
	}
}


