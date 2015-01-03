package org.eclipse.emf.henshin.interpreter.ui.giraph;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.giraph.GiraphGenerator;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class GenerateGiraphCodeWizard extends Wizard {

	private Unit mainUnit;

	private GiraphPage page;

	public GenerateGiraphCodeWizard(Unit unit) {
		this.mainUnit = unit;
		setWindowTitle("Henshin Giraph Code Generator");
		setNeedsProgressMonitor(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		addPage(page = new GiraphPage());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {

		final GiraphGenerator generator = new GiraphGenerator();
		final String className = page.classNameText.getText();
		generator.setProjectName(page.projectNameText.getText());
		generator.setPackageName(page.packageNameText.getText());
		generator.setMasterLogging(page.masterLoggingCheckBox.getSelection());
		generator.setVertexLogging(page.vertexLoggingCheckBox.getSelection());
		generator.setUseUUIDs(page.uuidsCheckBox.getSelection());
		generator.setExampleJSON(page.jsonCheckBox.getSelection());

		final List<IFile> javaFile = new ArrayList<IFile>();
		final List<CoreException> exception = new ArrayList<CoreException>();
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					try {
						javaFile.add(generator.generate(mainUnit, className, monitor));
					} catch (CoreException e) {
						exception.add(e);
					}
				}
			});
		} catch (InvocationTargetException e1) {
		} catch (InterruptedException e1) {
		}

		if (!exception.isEmpty()) {
			MessageDialog.openError(getShell(), "Error", exception.get(0).getMessage());
			exception.get(0).printStackTrace();
			return false;
		}

		IWorkbench wb = PlatformUI.getWorkbench();
		IEditorDescriptor desc = wb.getEditorRegistry().getDefaultEditor(javaFile.get(0).getName());
		try {
			wb.getActiveWorkbenchWindow().getActivePage()
					.openEditor(new FileEditorInput(javaFile.get(0)), desc.getId());
		} catch (PartInitException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}

		return true;
	}

	private class GiraphPage extends WizardPage {

		Text projectNameText;
		Text packageNameText;
		Text classNameText;

		Button masterLoggingCheckBox;
		Button vertexLoggingCheckBox;
		Button uuidsCheckBox;
		Button jsonCheckBox;

		public GiraphPage() {
			super("Giraph");
			setTitle("Henshin Giraph Code Generator");
			setDescription("Enter the details for the Giraph code generation.");
		}

		@Override
		public void createControl(Composite parent) {
			Composite comp = new Composite(parent, SWT.FILL);
			comp.setLayout(new GridLayout(2, false));
			Label label;

			label = new Label(comp, SWT.NONE);
			label.setText("Project Name:");
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			projectNameText = new Text(comp, SWT.BORDER);
			projectNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			projectNameText.setText(GiraphGenerator.DEFAULT_PROJECT_NAME);

			label = new Label(comp, SWT.NONE);
			label.setText("Package Name:");
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			packageNameText = new Text(comp, SWT.BORDER);
			packageNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			packageNameText.setText(GiraphGenerator.DEFAULT_PACKAGE_NAME);

			label = new Label(comp, SWT.NONE);
			label.setText("Compute Class Name:");
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			classNameText = new Text(comp, SWT.BORDER);
			classNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			String className = mainUnit.getName();
			className = className.substring(0, 1).toUpperCase() + className.substring(1);
			classNameText.setText(className);

			label = new Label(comp, SWT.NONE);
			label.setText("Master Logging:");
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			masterLoggingCheckBox = new Button(comp, SWT.CHECK);
			masterLoggingCheckBox.setSelection(true);

			label = new Label(comp, SWT.NONE);
			label.setText("Vertex Logging:");
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			vertexLoggingCheckBox = new Button(comp, SWT.CHECK);

			label = new Label(comp, SWT.NONE);
			label.setText("Use Java UUIDs:");
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			uuidsCheckBox = new Button(comp, SWT.CHECK);
			uuidsCheckBox.setSelection(true);

			label = new Label(comp, SWT.NONE);
			label.setText("Example JSON:");
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			jsonCheckBox = new Button(comp, SWT.CHECK);

			setControl(comp);
		}

	}

}
