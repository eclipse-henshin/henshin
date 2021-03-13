/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.utils.printer.actions;

import java.io.File;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.presentation.HenshinEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import org.eclipse.emf.henshin.ocl2ac.utils.printer.CoreCommand;
import org.eclipse.emf.henshin.ocl2ac.utils.printer.HenshinNACPrinter;
import org.eclipse.emf.henshin.ocl2ac.utils.printer.ViewCommand;
import graph.util.extensions.Constants;

public class HenshinNACPrintViewActionLongVersion implements IObjectActionDelegate {

	// Workbench part:
	protected IWorkbenchPart workbenchPart;

	// Henshin rule:
	protected Rule rule;

	private CoreCommand wincmd;
	private File pdfFile = null;

	@Override
	public void run(IAction action) {
		print();
	}

	/**
	 * 
	 */
	protected void print() {
		HenshinNACPrinter henshinNACPrinter = new HenshinNACPrinter(rule, rule.eClass().getEPackage(), false);
		henshinNACPrinter.printDocument();

		String filepath = henshinNACPrinter.getOutputFilePath();

		Thread thread = new Thread() {
			public void run() {
				System.out.println("Thread Running");
				compileLatex2PDF(filepath);

				File texFile = new File(filepath);
				int lastIndexOf = texFile.getName().lastIndexOf(Constants.TEX);
				String pdfFileName = texFile.getName().substring(0, lastIndexOf).concat(".pdf");
				pdfFile = new File(texFile.getParent(), pdfFileName);

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

	private void compileLatex2PDF(String filePath) {
		Path p = new Path(filePath);
		wincmd = new CoreCommand();
		wincmd.executePDFLatexCommand(p.toOSString(), p.toFile().getParent());
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
