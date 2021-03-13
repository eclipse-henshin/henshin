/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.utils.printer.actions;

import java.io.File;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import org.eclipse.emf.henshin.ocl2ac.utils.printer.CoreCommand;
import org.eclipse.emf.henshin.ocl2ac.utils.printer.NestedConditionPrinter;
import org.eclipse.emf.henshin.ocl2ac.utils.printer.ViewCommand;
import graph.util.extensions.Constants;
import nestedcondition.NestedConstraint;

public class NGCPrintViewActionShortVersion implements IObjectActionDelegate {

	// Workbench part:
	protected IWorkbenchPart workbenchPart;

	// Nested graph constraint:
	protected NestedConstraint constraint;

	private CoreCommand wincmd;
	File pdfFile = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		print();
	}

	/*
	 * Print the graph constraint to the console
	 */
	protected void print() {
		if (constraint != null) {

			NestedConditionPrinter nestedConditionPrinter = new NestedConditionPrinter(constraint, true);
			nestedConditionPrinter.printDocument();

			String filepath = nestedConditionPrinter.getOutputFilePath();

			Thread thread = new Thread() {
				public void run() {
					System.out.println("Thread Running");

					compileLatex2PDF(filepath);
					File texFile = new File(filepath);
					int lastIndexOf = texFile.getName().lastIndexOf(Constants.TEX);
					String pdfFileName = texFile.getName().substring(0, lastIndexOf).concat(".pdf");
					pdfFile = new File(texFile.getParent(), pdfFileName);
					System.err.println(pdfFile.getPath());

				}
			};

			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (pdfFile != null && !pdfFile.exists()) {
				System.err.println("The PDF file of the generated latex is not produced.");
			} else {
				ViewCommand.openView(pdfFile);
			}

		}
	}

	/**
	 * 
	 * @param filePath
	 */
	private void compileLatex2PDF(String filePath) {
		Path p = new Path(filePath);
		wincmd = new CoreCommand();
		wincmd.executePDFLatexCommand(p.toOSString(), p.toFile().getParent());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		constraint = null;
		if (selection instanceof IStructuredSelection) {
			Object first = ((IStructuredSelection) selection).getFirstElement();
			if (first instanceof NestedConstraint) {
				constraint = (NestedConstraint) first;
				System.out.println(constraint.getName() + " " + constraint.eClass().getEPackage().getName());
			}
		}
		action.setEnabled(constraint != null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
	 * action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart part) {

	}

}
