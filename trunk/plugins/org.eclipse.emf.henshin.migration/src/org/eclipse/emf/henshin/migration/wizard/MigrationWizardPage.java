package org.eclipse.emf.henshin.migration.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class MigrationWizardPage extends WizardPage {
	
	private Text henshinDiagramFileText;
	private Text henshinFileText;

	protected IFile selectedHenshinFile = null;
	protected IFile selectedDiagramFile = null;
	
	protected boolean optimizeNestedConditions = true;
	protected boolean retainKernelAndMultiRules = true;
	protected boolean migrateDiagramFile = false;
	
	/**
	 * Create the wizard.
	 */
	public MigrationWizardPage(IFile initialHenshinFile) {
		super("wizardPage");
		setTitle("Henshin migration");
		setDescription("Migrate old henshin files and diagrams to the new henshin version");
		if (initialHenshinFile != null) {
			selectedHenshinFile = initialHenshinFile;
			
		}
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(3, false));

		Label lblHenshinFileTo = new Label(container, SWT.NONE);
		lblHenshinFileTo.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		lblHenshinFileTo.setText("Henshin file to migrate:");

		henshinFileText = new Text(container, SWT.BORDER);
		henshinFileText.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		henshinFileText.setEditable(false);
		henshinFileText.setText(selectedHenshinFile==null? "" : selectedHenshinFile.getFullPath().toString());

		Button changeHenshinFileButton = new Button(container, SWT.NONE);
		changeHenshinFileButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		changeHenshinFileButton.setText("Select");

		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 3;

		final Button OptimizeNestedConditionsCheck = new Button(container, SWT.CHECK);
		OptimizeNestedConditionsCheck.setSelection(true);
		OptimizeNestedConditionsCheck.setLayoutData(data);
		OptimizeNestedConditionsCheck.setText("Optimize nested conditions");
		
		final Button btnRetainSeparateKernel = new Button(container, SWT.CHECK);
		btnRetainSeparateKernel.setSelection(true);
		btnRetainSeparateKernel.setLayoutData(data);
		btnRetainSeparateKernel.setText("Keep separate kernel and multi-rules");

		if (selectedHenshinFile!=null) {
			String diagramName = selectedHenshinFile.getName().replaceFirst(".henshin", ".henshin_diagram");
			IResource diagramFile = selectedHenshinFile.getParent().findMember(diagramName);
			if (diagramFile instanceof IFile && diagramFile.exists()) {
				selectedDiagramFile = (IFile) diagramFile;
			}
		}

		final Button MigrateDiagramFileCheck = new Button(container, SWT.CHECK);
		MigrateDiagramFileCheck.setSelection(selectedDiagramFile!=null);
		MigrateDiagramFileCheck.setLayoutData(data);
		MigrateDiagramFileCheck.setText("Migrate corresponding diagram file");
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		lblNewLabel.setText("Diagram file to migrate:");

		henshinDiagramFileText = new Text(container, SWT.BORDER);
		henshinDiagramFileText.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		henshinDiagramFileText.setEditable(false);
		henshinDiagramFileText.setEnabled(selectedDiagramFile!=null);
		if (selectedDiagramFile!=null) {
			henshinDiagramFileText.setText(selectedDiagramFile.getFullPath().toString());
		}

		final Button changeDiagramFileButton = new Button(container, SWT.NONE);
		changeDiagramFileButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));		
		changeDiagramFileButton.setText("Select");
		changeDiagramFileButton.setEnabled(selectedDiagramFile!=null);
		
		
		MigrateDiagramFileCheck.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				henshinDiagramFileText.setEnabled(MigrateDiagramFileCheck.getSelection());
				changeDiagramFileButton.setEnabled(MigrateDiagramFileCheck.getSelection());
				migrateDiagramFile = MigrateDiagramFileCheck.getSelection();
			}
		});
		
		changeDiagramFileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedDiagramFile = WorkspaceResourceDialog.openFileSelection(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "henshin migration", "select henshin diagram file to migrate", false, null, null)[0];
				if (selectedDiagramFile != null) {
					henshinDiagramFileText.setText(selectedDiagramFile.getName());
				}
			}
		});
		
		changeHenshinFileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedHenshinFile = WorkspaceResourceDialog.openFileSelection(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "henshin migration", "select henshin file to migrate", false, null, null)[0];
				if (selectedHenshinFile != null) {
					henshinFileText.setText(selectedHenshinFile.getName());
				}
			}
		});
		
		OptimizeNestedConditionsCheck.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				optimizeNestedConditions = OptimizeNestedConditionsCheck.getSelection();
			}
		});

		btnRetainSeparateKernel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				retainKernelAndMultiRules = btnRetainSeparateKernel.getSelection();
			}
		});

		setControl(container);

	}
}

