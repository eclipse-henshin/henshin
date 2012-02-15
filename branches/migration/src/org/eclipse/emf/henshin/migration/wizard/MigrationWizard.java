package org.eclipse.emf.henshin.migration.wizard;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.henshin.migration.Transformation;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

public class MigrationWizard extends Wizard {

	private MigrationWizardPage migrationPage;
	private IFile initialHenshinFile = null;
	
	public MigrationWizard(IFile henshinFile) {
		setWindowTitle("Henshin Model migration");
		initialHenshinFile = henshinFile;
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
			IWorkbench wb = PlatformUI.getWorkbench();
			IProgressService ps = wb.getProgressService();
			ps.busyCursorWhile(new IRunnableWithProgress() {
				
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException,	InterruptedException {
					try {
						if (migrationPage.selectedDiagramFile != null && migrationPage.migrateDiagramFile) {
							tr.migrate(migrationPage.selectedHenshinFile.getLocationURI(), migrationPage.selectedDiagramFile.getLocationURI(), migrationPage.optimizeNestedConditions, monitor);
							try {
								migrationPage.selectedDiagramFile.getParent().refreshLocal(2, new NullProgressMonitor());
							} catch (CoreException e) {}
						} else {
							tr.migrate(migrationPage.selectedHenshinFile.getLocationURI(), null, migrationPage.optimizeNestedConditions, monitor);
						}
						try {
							migrationPage.selectedHenshinFile.getParent().refreshLocal(2, new NullProgressMonitor());
						} catch (CoreException e) {}
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
					}
				}
			});
			return true;
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return false;
	}

}
