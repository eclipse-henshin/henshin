/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.utils.printer;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class CoreCommand {

	/**
	 * 
	 * @param latexFilePath
	 * @param outputFolderPath
	 */
	public void executePDFLatexCommand(String latexFilePath, String outputFolderPath) {
		String command = "pdflatex -output-directory " + outputFolderPath + " " + latexFilePath;
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
		builder.redirectErrorStream(true);
		Process p = null;
		try {
			p = builder.start();
			boolean waitFor = p.waitFor(5, TimeUnit.SECONDS);
			// int waitFor = p.waitFor();
			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while (true) {
				line = r.readLine();
				if (line == null) {
					break;
				}
				System.out.println(line);
			}
			if (!waitFor) {
				System.err.println("Timeout: The latex file is not compiled.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			p.destroy();
		}
	}

	public void runFile(File file) {
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", file.getPath());
		builder.redirectErrorStream(true);
		Process p;
		try {
			p = builder.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while (true) {
				line = r.readLine();
				if (line == null) {
					break;
				}
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * 
	 * @param pdfFile
	 */
	public void desktopRun(File pdfFile) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().open(pdfFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Awt Desktop is not supported!");
		}
	}

	public void browseFile(String filePath) {
		Display display = new Display();

		Shell shell = new Shell(display);
		Browser browser = new Browser(shell, SWT.NONE);
		browser.setUrl(filePath);

		shell.setLocation(100, 100);
		shell.setLayout(new FillLayout());
		shell.layout(true);
		shell.setVisible(true);
		shell.open();

		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();

		display.dispose();
	}

	public static void refreshFolders() {
		try {
			getActualProject().refreshLocal(IProject.DEPTH_INFINITE, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("finally")
	private static IProject getActualProject() {
		IProject actualProject = null;
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		try {
			IEditorPart editorPart = window.getActivePage().getActiveEditor();
			if (editorPart != null) {
				IEditorInput input = editorPart.getEditorInput();
				if (input instanceof IFileEditorInput) {
					IFileEditorInput fileInput = (IFileEditorInput) input;
					actualProject = fileInput.getFile().getProject();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return actualProject;
		}
	}

}