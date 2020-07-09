package org.eclipse.emf.henshin.interpreter.ui.giraph;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.henshin.giraph.GiraphGenerator;
import org.eclipse.emf.henshin.giraph.GiraphValidator;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class GenerateGiraphCodeWizard extends Wizard {

	private final GiraphGenerator generator;

	public GenerateGiraphCodeWizard(Unit unit) {
		setWindowTitle("Henshin Giraph Code Generator");
		setNeedsProgressMonitor(true);
		generator = new GiraphGenerator();
		generator.setMainUnit(unit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		addPage(new GiraphPage());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {

		final List<IFile> javaFile = new ArrayList<IFile>();
		final List<CoreException> exception = new ArrayList<CoreException>();
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					try {
						javaFile.add(generator.generate(monitor));
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
		} else {
			IWorkbench wb = PlatformUI.getWorkbench();
			IEditorDescriptor desc = wb.getEditorRegistry().getDefaultEditor(javaFile.get(0).getName());
			try {
				wb.getActiveWorkbenchWindow().getActivePage()
						.openEditor(new FileEditorInput(javaFile.get(0)), desc.getId());
			} catch (PartInitException e) {
				MessageDialog.openError(getShell(), "Error", e.getMessage());
			}
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
		Button testEnvCheckBox;

		public GiraphPage() {
			super("Giraph");
			setTitle("Henshin Giraph Code Generator");
			setDescription("Enter the details for the Giraph code generation");
		}

		private void validate() {
			IStatus status = generator.validateAll();
			setMessage(status.getSeverity() == IStatus.WARNING ? status.getMessage() : null, WARNING);
			setErrorMessage(status.getSeverity() == IStatus.ERROR ? status.getMessage() : null);
			setPageComplete(status.getSeverity() != IStatus.ERROR);
		}

		@Override
		public void createControl(Composite parent) {
			Composite comp = new Composite(parent, SWT.FILL);
			comp.setLayout(new GridLayout(1, true));
			Label label;
			Group group;

			{
				group = new Group(comp, SWT.NONE);
				group.setText("Paths");
				GridData data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
				data.horizontalSpan = 2;
				group.setLayoutData(data);
				group.setLayout(new GridLayout(2, false));

				label = new Label(group, SWT.NONE);
				label.setText("Project Name:");
				label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
				projectNameText = new Text(group, SWT.BORDER);
				projectNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				projectNameText.setText(generator.getProjectName() + "");
				projectNameText.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent e) {
						generator.setProjectName(projectNameText.getText());
						validate();
					}
				});

				label = new Label(group, SWT.NONE);
				label.setText("Package Name:");
				label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
				packageNameText = new Text(group, SWT.BORDER);
				packageNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				packageNameText.setText(generator.getPackageName() + "");
				packageNameText.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent e) {
						generator.setPackageName(packageNameText.getText());
						validate();
					}
				});

				label = new Label(group, SWT.NONE);
				label.setText("Compute Class Name:");
				label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
				classNameText = new Text(group, SWT.BORDER);
				classNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				classNameText.setText(generator.getClassName() + "");
				classNameText.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent e) {
						generator.setClassName(classNameText.getText());
						validate();
					}
				});
			}

			{
				group = new Group(comp, SWT.NONE);
				group.setText("Options");
				group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				group.setLayout(new GridLayout(4, false));

				label = new Label(group, SWT.NONE);
				label.setText("Use Java UUIDs as Vertex IDs:");
				label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
				uuidsCheckBox = new Button(group, SWT.CHECK);
				uuidsCheckBox.setSelection(generator.isUseUUIDs());
				uuidsCheckBox.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						generator.setUseUUIDs(uuidsCheckBox.getSelection());
						validate();
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});

				label = new Label(group, SWT.NONE);
				label.setText("     Enable Master Logging:");
				label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
				masterLoggingCheckBox = new Button(group, SWT.CHECK);
				masterLoggingCheckBox.setSelection(generator.isMasterLogging());
				masterLoggingCheckBox.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						generator.setMasterLogging(masterLoggingCheckBox.getSelection());
						validate();
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});

				label = new Label(group, SWT.NONE);
				label.setText("Install Test Environment (Hadoop 0.20.203.0, Linux only):");
				label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
				testEnvCheckBox = new Button(group, SWT.CHECK);
				testEnvCheckBox.setEnabled(GiraphValidator.validatePlatformForTesting().getSeverity() != IStatus.ERROR);
				testEnvCheckBox.setSelection(false);
				generator.setTestEnvironment(false);
				testEnvCheckBox.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						generator.setTestEnvironment(testEnvCheckBox.getSelection());
						validate();
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});
				testEnvCheckBox.addFocusListener(new FocusListener() {
					@Override
					public void focusLost(FocusEvent arg0) {
					}

					@Override
					public void focusGained(FocusEvent arg0) {
						validate();
					}
				});

				label = new Label(group, SWT.NONE);
				label.setText("     Enable Vertex Logging:");
				label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
				vertexLoggingCheckBox = new Button(group, SWT.CHECK);
				vertexLoggingCheckBox.setSelection(generator.isVertexLogging());
				vertexLoggingCheckBox.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						generator.setVertexLogging(vertexLoggingCheckBox.getSelection());
						validate();
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});

			}
			validate();
			setControl(comp);
		}

	}

}
