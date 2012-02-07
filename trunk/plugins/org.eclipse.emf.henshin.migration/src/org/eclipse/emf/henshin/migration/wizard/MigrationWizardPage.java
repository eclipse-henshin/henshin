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
		fd_MigrateDiagramFileCheck.left = new FormAttachment(0, 10);
		MigrateDiagramFileCheck.setLayoutData(fd_MigrateDiagramFileCheck);
		MigrateDiagramFileCheck.setText("migrate corresponding diagram file");
		
		Label lblHenshinFileTo = new Label(container, SWT.NONE);
		FormData fd_lblHenshinFileTo = new FormData();
		lblHenshinFileTo.setLayoutData(fd_lblHenshinFileTo);
		lblHenshinFileTo.setText("henshin file to migrate");
		
		Button changeHenshinFileButton = new Button(container, SWT.NONE);
		FormData fd_changeHenshinFileButton = new FormData();
		fd_changeHenshinFileButton.top = new FormAttachment(lblHenshinFileTo, -5, SWT.TOP);
		changeHenshinFileButton.setLayoutData(fd_changeHenshinFileButton);
		changeHenshinFileButton.setText("select");
		
		final Button OptimizeNestedConditionsCheck = new Button(container, SWT.CHECK);
		OptimizeNestedConditionsCheck.setSelection(true);
		FormData fd_OptimizeNestedConditionsCheck = new FormData();
		fd_OptimizeNestedConditionsCheck.left = new FormAttachment(0, 10);
		OptimizeNestedConditionsCheck.setLayoutData(fd_OptimizeNestedConditionsCheck);
		OptimizeNestedConditionsCheck.setText("optimize nested conditions");
		
		Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		fd_changeHenshinFileButton.right = new FormAttachment(label, 0, SWT.RIGHT);
		fd_MigrateDiagramFileCheck.top = new FormAttachment(label, 6);
		FormData fd_label = new FormData();
		fd_label.left = new FormAttachment(0, 10);
		fd_label.right = new FormAttachment(100, -10);
		fd_label.bottom = new FormAttachment(100, -209);
		fd_label.top = new FormAttachment(OptimizeNestedConditionsCheck, 10);
		label.setLayoutData(fd_label);
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		fd_lblHenshinFileTo.right = new FormAttachment(lblNewLabel, 0, SWT.RIGHT);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.left = new FormAttachment(MigrateDiagramFileCheck, 0, SWT.LEFT);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("diagram file to migrate");
		
		final Button changeDiagramFileButton = new Button(container, SWT.NONE);
		FormData fd_changeDiagramFileButton = new FormData();
		fd_changeDiagramFileButton.top = new FormAttachment(lblNewLabel, -5, SWT.TOP);
		fd_changeDiagramFileButton.left = new FormAttachment(changeHenshinFileButton, 0, SWT.LEFT);
		changeDiagramFileButton.setLayoutData(fd_changeDiagramFileButton);

		changeDiagramFileButton.setText("select");
		changeDiagramFileButton.setEnabled(false);
		
		henshinDiagramFileText = new Text(container, SWT.BORDER);
		fd_lblNewLabel.top = new FormAttachment(henshinDiagramFileText, 3, SWT.TOP);
		FormData fd_henshinDiagramFileText = new FormData();
		fd_henshinDiagramFileText.right = new FormAttachment(100, -58);
		fd_henshinDiagramFileText.left = new FormAttachment(lblNewLabel, 6);
		fd_henshinDiagramFileText.top = new FormAttachment(MigrateDiagramFileCheck, 6);
		fd_henshinDiagramFileText.bottom = new FormAttachment(100, -160);
		henshinDiagramFileText.setLayoutData(fd_henshinDiagramFileText);
		henshinDiagramFileText.setEnabled(false);
		henshinDiagramFileText.setEditable(false);
		
		henshinFileText = new Text(container, SWT.BORDER);
		fd_OptimizeNestedConditionsCheck.top = new FormAttachment(henshinFileText, 14);
		fd_lblHenshinFileTo.top = new FormAttachment(henshinFileText, 3, SWT.TOP);
		FormData fd_henshinFileText = new FormData();
		fd_henshinFileText.left = new FormAttachment(lblHenshinFileTo, 4);
		fd_henshinFileText.right = new FormAttachment(changeHenshinFileButton, -6);
		fd_henshinFileText.bottom = new FormAttachment(100, -251);
		fd_henshinFileText.top = new FormAttachment(0, 10);
		henshinFileText.setLayoutData(fd_henshinFileText);
		henshinFileText.setEditable(false);
		henshinFileText.setText(selectedHenshinFile==null?"":selectedHenshinFile.getName());
		
		
		
		
		
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



	}
}



/*
 * 				IFile[] files = WorkspaceResourceDialog.openFileSelection(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), InterpreterUIPlugin.LL("_UI_BrowseWorkspace_Title"), InterpreterUIPlugin.LL("_UI_BrowseWorkspace_Message"), false, null,
*/

/*
 * 
 * 		browseWorkspaceButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				IFile[] files = WorkspaceResourceDialog.openFileSelection(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), InterpreterUIPlugin.LL("_UI_BrowseWorkspace_Title"), InterpreterUIPlugin.LL("_UI_BrowseWorkspace_Message"), false, null,
						null);
				if (files.length != 1)
					return;
				IFile file = files[0];
				if (file != null) {
					URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
					uriField.setText(uri.toString());
					fireURIChanged();
				}
			}
		});
*/
