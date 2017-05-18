/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.part;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.presentation.ImportPackagesWizardPage;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * @generated
 */
public class HenshinCreationWizard extends Wizard implements INewWizard {

	/**
	 * @generated
	 */
	private IWorkbench workbench;

	/**
	 * @generated
	 */
	protected IStructuredSelection selection;

	/**
	 * @generated
	 */
	protected HenshinCreationWizardPage diagramModelFilePage;

	/**
	 * @generated
	 */
	protected HenshinCreationWizardPage domainModelFilePage;

	/**
	 * @generated
	 */
	protected Resource diagram;

	/**
	 * @generated
	 */
	private boolean openNewlyCreatedDiagramEditor = true;

	/**
	 * @generated
	 */
	public IWorkbench getWorkbench() {
		return workbench;
	}

	/**
	 * @generated
	 */
	public IStructuredSelection getSelection() {
		return selection;
	}

	/**
	 * @generated
	 */
	public final Resource getDiagram() {
		return diagram;
	}

	/**
	 * @generated
	 */
	public final boolean isOpenNewlyCreatedDiagramEditor() {
		return openNewlyCreatedDiagramEditor;
	}

	/**
	 * @generated
	 */
	public void setOpenNewlyCreatedDiagramEditor(boolean openNewlyCreatedDiagramEditor) {
		this.openNewlyCreatedDiagramEditor = openNewlyCreatedDiagramEditor;
	}

	/**
	 * @generated
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		setWindowTitle(Messages.HenshinCreationWizardTitle);
		setDefaultPageImageDescriptor(
				HenshinDiagramEditorPlugin.getBundledImageDescriptor("icons/wizban/NewHenshinWizard.gif")); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}

	private ImportPackagesWizardPage importPackagesPage;

	/**
	 * @generated NOT
	 */
	@Override
	public void addPages() {

		// Diagram file page:
		diagramModelFilePage = new HenshinCreationWizardPage("DiagramModelFile", getSelection(), "henshin_diagram"); //$NON-NLS-1$ //$NON-NLS-2$
		diagramModelFilePage.setTitle(Messages.HenshinCreationWizard_DiagramModelFilePageTitle);
		diagramModelFilePage.setDescription(Messages.HenshinCreationWizard_DiagramModelFilePageDescription);
		addPage(diagramModelFilePage);

		// Domain file page:
		domainModelFilePage = new HenshinCreationWizardPage("DomainModelFile", getSelection(), "henshin") { //$NON-NLS-1$ //$NON-NLS-2$
			private boolean activated = false;

			@Override
			public boolean isPageComplete() {
				return activated && super.isPageComplete();
			}

			@Override
			public void setVisible(boolean visible) {
				if (visible) {
					activated = true;
					String fileName = diagramModelFilePage.getFileName();
					fileName = fileName.substring(0, fileName.length() - ".henshin_diagram".length()); //$NON-NLS-1$
					setFileName(
							HenshinDiagramEditorUtil.getUniqueFileName(getContainerFullPath(), fileName, "henshin")); //$NON-NLS-1$
				}
				super.setVisible(visible);
			}
		};
		domainModelFilePage.setTitle(Messages.HenshinCreationWizard_DomainModelFilePageTitle);
		domainModelFilePage.setDescription(Messages.HenshinCreationWizard_DomainModelFilePageDescription);
		addPage(domainModelFilePage);

		// Import packages page:
		importPackagesPage = new ImportPackagesWizardPage("importPackages");
		addPage(importPackagesPage);

	}

	/**
	 * @generated NOT
	 */
	public boolean performFinish() {

		// Create a workspace operation:
		IRunnableWithProgress op = new WorkspaceModifyOperation(null) {

			protected void execute(IProgressMonitor monitor) throws CoreException, InterruptedException {

				// Create the diagram and model resources:
				diagram = HenshinDiagramEditorUtil.createDiagram(diagramModelFilePage.getURI(),
						domainModelFilePage.getURI(), monitor);

				// Add the imported packages:
				final ResourceSet resourceSet = diagram.getResourceSet();
				final Diagram theDiagram = (Diagram) diagram.getContents().get(0);
				final Module module = (Module) theDiagram.getElement();
				TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(theDiagram);
				if (editingDomain != null) {
					AbstractTransactionalCommand command = new AbstractTransactionalCommand(editingDomain,
							"Import Packages", null) {
						protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
								throws ExecutionException {
							for (URI uri : importPackagesPage.getPackageURIs()) {
								EObject object = resourceSet.getEObject(uri, true);
								if (object instanceof EPackage) {
									module.getImports().add((EPackage) object);
								}
							}
							// Save again:
							try {
								module.eResource().save(null);
								diagram.save(null);
							} catch (IOException e) {
								throw new ExecutionException("Error saving Henshin model/diagram", e);
							}
							return CommandResult.newOKCommandResult();
						}
					};
					try {
						command.execute(null, null);
					} catch (ExecutionException e) {
						HenshinDiagramEditorPlugin.getInstance().logError("Error creating diagram", e); //$NON-NLS-1$
					}
				}

				// Open in editor:
				if (isOpenNewlyCreatedDiagramEditor() && diagram != null) {
					try {
						HenshinDiagramEditorUtil.openDiagram(diagram);
					} catch (PartInitException e) {
						ErrorDialog.openError(getContainer().getShell(), Messages.HenshinCreationWizardOpenEditorError,
								null, e.getStatus());
					}
				}
			}
		};

		// Execute the operation:
		try {
			getContainer().run(false, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof CoreException) {
				ErrorDialog.openError(getContainer().getShell(), Messages.HenshinCreationWizardCreationError, null,
						((CoreException) e.getTargetException()).getStatus());
			} else {
				HenshinDiagramEditorPlugin.getInstance().logError("Error creating diagram", e.getTargetException()); //$NON-NLS-1$
			}
			return false;
		}

		// Done.
		return diagram != null;

	}
}
