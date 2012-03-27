package org.eclipse.emf.henshin.migration.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
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
		setControl(container);
		container.setLayout(new FormLayout());
		
		final Button MigrateDiagramFileCheck = new Button(container, SWT.CHECK);

		FormData fd_MigrateDiagramFileCheck = new FormData();
		MigrateDiagramFileCheck.setLayoutData(fd_MigrateDiagramFileCheck);
		MigrateDiagramFileCheck.setText("migrate corresponding diagram file");
		
		Label lblHenshinFileTo = new Label(container, SWT.NONE);
		fd_MigrateDiagramFileCheck.left = new FormAttachment(lblHenshinFileTo, 0, SWT.LEFT);
		FormData fd_lblHenshinFileTo = new FormData();
		fd_lblHenshinFileTo.left = new FormAttachment(0,10);
		fd_lblHenshinFileTo.top = new FormAttachment(0, 13);
		lblHenshinFileTo.setLayoutData(fd_lblHenshinFileTo);
		lblHenshinFileTo.setText("henshin file to migrate");
		
		Button changeHenshinFileButton = new Button(container, SWT.NONE);
		FormData fd_changeHenshinFileButton = new FormData();
		fd_changeHenshinFileButton.right = new FormAttachment(100, -10);
		changeHenshinFileButton.setLayoutData(fd_changeHenshinFileButton);
		changeHenshinFileButton.setText("select");
		
		final Button OptimizeNestedConditionsCheck = new Button(container, SWT.CHECK);
		OptimizeNestedConditionsCheck.setSelection(true);
		FormData fd_OptimizeNestedConditionsCheck = new FormData();
		fd_OptimizeNestedConditionsCheck.left = new FormAttachment(0, 10);
		OptimizeNestedConditionsCheck.setLayoutData(fd_OptimizeNestedConditionsCheck);
		OptimizeNestedConditionsCheck.setText("optimize nested conditions");
		
		
		final Button btnRetainSeparateKernel = new Button(container, SWT.CHECK);
		fd_MigrateDiagramFileCheck.top = new FormAttachment(btnRetainSeparateKernel, 61);
		btnRetainSeparateKernel.setSelection(true);
		FormData fd_btnRetainSeparateKernel = new FormData();
		fd_btnRetainSeparateKernel.left = new FormAttachment(0, 10);
		fd_btnRetainSeparateKernel.top = new FormAttachment(OptimizeNestedConditionsCheck, 14);
		btnRetainSeparateKernel.setLayoutData(fd_btnRetainSeparateKernel);
		btnRetainSeparateKernel.setText("retain separate kernel and multi rules");
		
		
		
		Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(btnRetainSeparateKernel);
		fd_label.left = new FormAttachment(0, 10);
		fd_label.right = new FormAttachment(100, -10);
		fd_label.bottom = new FormAttachment(100, -170);
		label.setLayoutData(fd_label);
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		FormData fd_lblNewLabel = new FormData();
		lblNewLabel.setText("diagram file to migrate");
		
		final Button changeDiagramFileButton = new Button(container, SWT.NONE);
		fd_changeHenshinFileButton.left = new FormAttachment(changeDiagramFileButton, 0, SWT.LEFT);
		fd_lblNewLabel.left = new FormAttachment(0, 10);
		FormData fd_changeDiagramFileButton = new FormData();
		fd_changeDiagramFileButton.right = new FormAttachment(100, -10);
		changeDiagramFileButton.setLayoutData(fd_changeDiagramFileButton);		
		
		changeDiagramFileButton.setText("select");
		changeDiagramFileButton.setEnabled(false);
		
		henshinDiagramFileText = new Text(container, SWT.BORDER);
		fd_changeDiagramFileButton.bottom = new FormAttachment(henshinDiagramFileText, 0, SWT.BOTTOM);
		FormData fd_henshinDiagramFileText = new FormData();
		fd_henshinDiagramFileText.left = new FormAttachment(lblNewLabel, 10);
		fd_henshinDiagramFileText.right = new FormAttachment(100, -60);
		fd_henshinDiagramFileText.top = new FormAttachment(MigrateDiagramFileCheck, 4);
		//fd_henshinDiagramFileText.bottom = new FormAttachment(100, -85);
		fd_henshinDiagramFileText.height = 16;
		henshinDiagramFileText.setLayoutData(fd_henshinDiagramFileText);
		henshinDiagramFileText.setEnabled(false);
		henshinDiagramFileText.setEditable(false);
		
		fd_lblNewLabel.top = new FormAttachment(henshinDiagramFileText, -18);
		lblNewLabel.setLayoutData(fd_lblNewLabel);

		
		henshinFileText = new Text(container, SWT.BORDER);
		fd_changeHenshinFileButton.bottom = new FormAttachment(henshinFileText, 0, SWT.BOTTOM);
		fd_OptimizeNestedConditionsCheck.top = new FormAttachment(henshinFileText, 10);
		fd_lblHenshinFileTo.right = new FormAttachment(henshinFileText, -4);
		FormData fd_henshinFileText = new FormData();
		fd_henshinFileText.right = new FormAttachment(changeHenshinFileButton, -10);
		fd_henshinFileText.left = new FormAttachment(lblHenshinFileTo, 25);
		fd_henshinFileText.bottom = new FormAttachment(100, -247);
		fd_henshinFileText.top = new FormAttachment(0, 10);
		henshinFileText.setLayoutData(fd_henshinFileText);
		henshinFileText.setEditable(false);
		henshinFileText.setText(selectedHenshinFile==null?"":selectedHenshinFile.getName());
		

		
		FormData fd_migrateDiagramFileCheck = new FormData();
		fd_migrateDiagramFileCheck.top = new FormAttachment(label, 50);
		fd_migrateDiagramFileCheck.left = new FormAttachment(0, 10);
		fd_migrateDiagramFileCheck.height = 25;
		MigrateDiagramFileCheck.setLayoutData(fd_migrateDiagramFileCheck);
		
		
		
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

	}
}

