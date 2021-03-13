/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package de.unimarburg.swt.ocl2ac.utils.printer;

import java.io.File;

import org.eclipse.ui.PlatformUI;

import de.unimarburg.swt.ocl2ac.utils.viewer.PDFViewer;

public class ViewCommand {

	private static final String NAS_OCL2AC_VIEWER_PDF_VIEWER = "nas.ocl2ac.viewer.PDFViewer";

	public static void openView(File pdfFile) {
		try {
			PDFViewer.pdfpath = null;
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(NAS_OCL2AC_VIEWER_PDF_VIEWER);
			PDFViewer.update(pdfFile.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
