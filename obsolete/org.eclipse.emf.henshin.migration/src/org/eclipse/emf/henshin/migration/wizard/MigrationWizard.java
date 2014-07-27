/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.migration.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.henshin.migration.Transformation;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class MigrationWizard extends Wizard {

	private MigrationWizardPage migrationPage;
	private IFile initialHenshinFile = null;
	
	public MigrationWizard(IFile henshinFile) {
		setWindowTitle("Henshin Model migration");
		initialHenshinFile = henshinFile;
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		migrationPage = new MigrationWizardPage(initialHenshinFile);
		addPage(migrationPage);
	}

	@Override
	public boolean performFinish() {
		final Transformation tr = new Transformation();
		try {
			WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
				@Override
				protected void execute(IProgressMonitor monitor) {
					try {
						if (migrationPage.selectedDiagramFile != null && 
							migrationPage.migrateDiagramFile) {
							tr.migrate(migrationPage.selectedHenshinFile.getLocationURI(), 
									migrationPage.selectedDiagramFile.getLocationURI(), 
									migrationPage.optimizeNestedConditions, 
									migrationPage.retainKernelAndMultiRules, monitor);
							migrationPage.selectedDiagramFile.getParent().refreshLocal(2, new NullProgressMonitor());
						} else {
							tr.migrate(migrationPage.selectedHenshinFile.getLocationURI(), 
									null, migrationPage.optimizeNestedConditions, 
									migrationPage.retainKernelAndMultiRules, monitor);
						}
						migrationPage.selectedHenshinFile.getParent().refreshLocal(2, new NullProgressMonitor());
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			};
			getContainer().run(false, false, operation);
			return true;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	}

}
