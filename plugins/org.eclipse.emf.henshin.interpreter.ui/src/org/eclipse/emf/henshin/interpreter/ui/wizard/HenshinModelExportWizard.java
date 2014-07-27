/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.ui.wizard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.HenshinModelExporter;
import org.eclipse.emf.henshin.HenshinModelPlugin;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Wizard for exporting Henshin models.
 * @author Christian Krause
 */
public class HenshinModelExportWizard extends Wizard implements IExportWizard {
	
	// Wizard pages:
	private ChooseExporterPage chooseExporterPage;
	private FileCreationPage fileCreationPage;
	
	// The workbench:
	private IWorkbench workbench;
	
	// Module to be exported:
	private Module module;
	
	// Selection:
	private IStructuredSelection selection;
	
	// Default base file name (without extension):
	private String baseName;

	// Image for the wizard:
	private ImageDescriptor wizban;//, icon;
		
	/**
	 * Default constructor.
	 */
	public HenshinModelExportWizard() {
		wizban = AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui", "$nl$/icons/full/wizban/export_wiz.png");
		setNeedsProgressMonitor(true);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		
		setWindowTitle("Export Henshin Model");
		this.workbench = workbench;
		this.selection = selection;
		
		// Try to extract the state space out of the selection:
		if (selection!=null) {
			Iterator<?> iterator = selection.iterator();
			while (iterator.hasNext() && module==null) {
				Object current = iterator.next();
				if (current instanceof Module) {
					module = (Module) current;
				} else if (current instanceof IFile) {
					HenshinResourceSet resourceSet = new HenshinResourceSet();
					URI fileURI = URI.createPlatformResourceURI(((IFile) current).getFullPath().toString(), true);
					try {
						module = (Module) resourceSet.getResource(fileURI, true).getContents().get(0);
					} catch (Throwable t) {
						HenshinModelPlugin.INSTANCE.logError("Error loading transformation model from file " + fileURI.toFileString(), t);
					}
				}
			}
		}
		
		// Module must be set by now.
		if (module==null) {
			throw new RuntimeException("Module not set");
		}
		
		// Initialize the default file name for the export:
		baseName = module.eResource().getURI().trimFileExtension().lastSegment();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		addPage(chooseExporterPage = new ChooseExporterPage("exporter-selection"));
		addPage(fileCreationPage = new FileCreationPage("file-creation", selection));
	}

	/*
	 * Update the file name for the file creation page.
	 */
	private void updateFileName(String ext) {
		
		String directory = module.eResource().getURI().trimSegments(1).toPlatformString(true);
		IContainer container = (IContainer) ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(directory));
		fileCreationPage.setContainerFullPath(container.getFullPath());
		
		// Check if the file exists already:
		String filename = baseName + "." + ext;
		for (int i=1; container.findMember(filename)!=null; ++i) {
			filename = baseName + i + "." + ext;
		}
		fileCreationPage.setFileName(filename);

	}

	/*
	 * Pretty print allowed extensions.
	 */
	private static String printAllowedExtensions(HenshinModelExporter exporter) {
		String[] allowed = exporter.getExportFileExtensions();
		String pretty = "";
		for (int i=0; i<allowed.length; i++) {
			pretty = pretty + "*." + allowed[i];
			if (i<allowed.length-1) pretty = pretty + ", ";
		}
		return pretty;
	}
	
	public void setModule(Module module) {
		this.module = module;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			final IFile file = getFile();
			final HenshinModelExporter exporter = getExporter();
			WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
				@Override
				protected void execute(IProgressMonitor monitor) {
					IStatus status = performExport(exporter, file, monitor);
					if (status.getSeverity()==IStatus.ERROR) {
						MessageDialog.openError(getShell(), "Export Transformation Model", status.getMessage());
						HenshinModelPlugin.INSTANCE.logError("Error exporting transformation model: " + status.getMessage(), status.getException());
					}
					if (status.getSeverity()==IStatus.WARNING) {
						MessageDialog.openWarning(getShell(), "Export Transformation Model", status.getMessage());
						HenshinModelPlugin.INSTANCE.logError(status.getMessage(), status.getException());
					}
				}
			};
			getContainer().run(false, false, operation);
			// Refresh:
			file.getParent().refreshLocal(2, new NullProgressMonitor());
			if (file.exists()) {
				openExportedFile(file);
			}
			return true;
		}
		catch (Throwable t) {
			HenshinModelPlugin.INSTANCE.logError("Error exporting transformation model", t);
			return false;
		}
	}
	
	/*
	 * Perform the export operation.
	 */
	protected IStatus performExport(HenshinModelExporter exporter, IFile file, IProgressMonitor monitor) {
		monitor.beginTask("Exporting transformation model...", -1);
		URI fileURI = URI.createFileURI(file.getLocation().toOSString());
		IStatus status;
		try {
			status = exporter.doExport(module, fileURI);
		} catch (Throwable t) {
			status = new Status(IStatus.ERROR, HenshinModelPlugin.PLUGIN_ID, "Error running exporter", t);
		} finally {
			monitor.done();
		}
		return status;
	}
	
	
	/* 
	 * Open the new file resource in the current window.
	 */
	protected void openExportedFile(IFile file) {
		try {
			IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			IDE.openEditor(page, file, true);
		}
		catch (Throwable t) {
			HenshinModelPlugin.INSTANCE.logError("Error opening exported file.", t);
		}
	}
		
	/**
	 * Get the file from the page.
	 * @return the target file.
	 */
	public IFile getFile() {
		return fileCreationPage.getFile();
	}
	
	/**
	 * Get the exporter to be used.
	 * @return The exporter.
	 */
	public HenshinModelExporter getExporter() {
		return chooseExporterPage.getExporter();
	}
	
	/*
	 * Choose exporter page.
	 */
	protected class ChooseExporterPage extends WizardPage {
		
		// List of registered exporters.
		private java.util.List<HenshinModelExporter> exporters;
		
		// Currently selected exporter.
		private int current = 0;
		
		/*
		 * Constructor.
		 */
		public ChooseExporterPage(String pageId) {
			super(pageId);
			setDescription("Choose an exporter");
			if (wizban!=null) {
				setImageDescriptor(wizban);
			}
			exporters = new ArrayList<HenshinModelExporter>(HenshinModelPlugin.INSTANCE.getExporters().values());
			Collections.sort(exporters, new Comparator<HenshinModelExporter>() {
				@Override
				public int compare(HenshinModelExporter o1, HenshinModelExporter o2) {
					return String.valueOf(o1.getExporterName()).compareTo(String.valueOf(o2.getExporterName()));
				}
			});
		}
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		@Override
		public void createControl(Composite parent) {
			
			Composite container = new Composite(parent, SWT.NONE);
			container.setLayout(new FillLayout());			
			final List list = new List(container, SWT.BORDER);
			for (HenshinModelExporter exporter : exporters) {
				list.add(exporter.getExporterName() + " (" + printAllowedExtensions(exporter) + ")");
			}
			list.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					current = list.getSelectionIndex();
					updateFilePage();
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
					getContainer().showPage(fileCreationPage);
				}
			});
			list.select(current);
			updateFilePage();
			setControl(container);
		}
		
		private void updateFilePage() {
			if (fileCreationPage!=null && current<exporters.size()) {
				String[] fileExts = exporters.get(current).getExportFileExtensions();
				if (fileExts.length>0) {
					updateFileName(fileExts[0]);
				}
			}
		}
		
		public HenshinModelExporter getExporter() {
			return exporters.get(current);
		}
		
	}

	/*
	 * File creation page.
	 */
	protected class FileCreationPage extends WizardNewFileCreationPage {
		
		/*
		 * Constructor.
		 */
		public FileCreationPage(String pageId, IStructuredSelection selection) {
			super(pageId, selection);
			setDescription("Choose the target file for the export ");
			if (wizban!=null) {
				setImageDescriptor(wizban);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validatePage()
		 */
		@Override
		protected boolean validatePage() {
			if (!super.validatePage()) return false;
			String extension = new Path(getFileName()).getFileExtension();
			String[] allowed = getExporter().getExportFileExtensions();
			boolean ok = false;
			for (int i=0; i<allowed.length; i++) {
				if (String.valueOf(allowed[i]).equals(extension)) ok = true;
			}
			if (!ok) {
				setErrorMessage("Invalid file extension, should be one of " + 
						printAllowedExtensions(getExporter()));
				return false;
			}
			return true;
		}
		
		public IFile getFile() {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(getContainerFullPath().append(getFileName()));
		}
	}
	

}
