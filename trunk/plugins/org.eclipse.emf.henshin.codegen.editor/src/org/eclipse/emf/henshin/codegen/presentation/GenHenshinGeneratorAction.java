package org.eclipse.emf.henshin.codegen.presentation;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.henshin.codegen.generator.HenshinCodeGenerator;
import org.eclipse.emf.henshin.codegen.model.GenHenshin;
import org.eclipse.emf.henshin.codegen.model.GenTransformation;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Action for triggering the code generation.
 * @author Christian Krause
 */
public class GenHenshinGeneratorAction implements IObjectActionDelegate {
	
	// Henshin transformation generator models:
	private Set<GenTransformation> genTrafos = new LinkedHashSet<GenTransformation>();
	
	// Workbench part:
	private IWorkbenchPart part;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		
		// Run as job...
		Job generateJob = new Job("Generate Transformation Code...") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				
				// Generate the code:
				monitor.beginTask("Generate Transformation Code...", genTrafos.size());
				for (GenTransformation genTrafo : genTrafos) {
					try {
						IStatus status = HenshinCodeGenerator.generate(genTrafo, new SubProgressMonitor(monitor,1));
						if (status.getSeverity()==IStatus.ERROR) {
							return status;
						}
					} catch (Throwable t) {
						return new Status(IStatus.ERROR, "org.eclipse.emf.henshin.codegen.editor", "Error generating transformation code", t);
					}
				}
				
				// Pop-up OK dialog:
				final Shell shell = part.getSite().getShell();
				shell.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						MessageDialog.openInformation(shell, "Henshin Code Generator", "Transformation code succesfully generated.");
					}
				});
				return Status.OK_STATUS;
			}
		};
		generateJob.schedule();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		
		genTrafos.clear();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structured = (IStructuredSelection) selection;
			for (Object current : structured.toList()) {
				if (current instanceof GenTransformation) {
					genTrafos.add((GenTransformation) current);
				}
				if (current instanceof GenHenshin) {
					genTrafos.addAll(((GenHenshin) current).getGenTransformations());
				}
			}
		}
		action.setEnabled(!genTrafos.isEmpty());
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart part) {
		this.part = part;
	}

}
