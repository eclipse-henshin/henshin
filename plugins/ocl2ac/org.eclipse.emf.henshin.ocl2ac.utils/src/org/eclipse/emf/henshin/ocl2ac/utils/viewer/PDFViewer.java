/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.utils.viewer;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.CloseWindowListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class PDFViewer extends ViewPart {

	public static final String ID = "nas.ocl2ac.viewer.PDFViewer"; //$NON-NLS-1$
	public static String pdfpath;
	public static Browser browser;

	/**
	 * @wbp.parser.constructor
	 */
	public PDFViewer() {
	}

	@Override
	public void createPartControl(Composite parent) {
		{
			browser = new Browser(parent, SWT.NONE);
			browser.addCloseWindowListener(new CloseWindowListener() {
				public void close(WindowEvent event) {
					pdfpath = null;
					browser.setUrl(null);
					browser = null;
				}
			});
			if (getPdfpath() != null)
				browser.setUrl(getPdfpath());
		}

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	public void dispose() {
		super.dispose();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions

	}

	public static void update(String pdfpath) {
		browser.setUrl(pdfpath);
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager manager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	public String getPdfpath() {
		return pdfpath;
	}

	public void setPdfpath(String pdfpath) {
		PDFViewer.pdfpath = pdfpath;
	}

}
