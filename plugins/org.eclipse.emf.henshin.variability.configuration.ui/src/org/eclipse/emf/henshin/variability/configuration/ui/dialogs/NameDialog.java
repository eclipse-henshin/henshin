package org.eclipse.emf.henshin.variability.configuration.ui.dialogs;

import java.util.List;
import java.util.PropertyResourceBundle;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.henshin.variability.ui.Activator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Provides a dialog window for creating named elements.
 * When a name is entered, the dialog will check it against a given list of names.
 * If the entered name is already taken, an error is displayed.
 * 
 * @author Stefan Schulz
 *
 */
public class NameDialog extends Dialog {

	private String name = "";
	private String title;
	private String description;
	private String error;
	
	private Text txtName;
	private Label lblError;
	private List<String> takenNames;
	
	public NameDialog(Shell parentShell, String dialogContext, List<String> takenNames) {
		super(parentShell);
		PropertyResourceBundle properties = Activator.getProperties();
		this.title = properties.getString("_Dialog_Name_Title_" + dialogContext);
		this.description = properties.getString("_Dialog_Name_Description_" + dialogContext);
		this.error = properties.getString("_Dialog_Name_Error_" + dialogContext);
		this.takenNames = takenNames;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout(2, false);
		layout.marginTop = 5;
		layout.marginRight = 5;
		layout.marginLeft = 5;
		container.setLayout(layout);

		Label lblDescription = new Label(container, SWT.NONE);
		lblDescription.setText(description);
		lblDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		Label lblSeparator = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		Label lblName = new Label(container, SWT.NONE);
		lblName.setText("Name");
		txtName = new Text(container, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtName.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				Text textWidget = (Text) e.getSource();
				String nameText = textWidget.getText();
				name = nameText;
				validate();
			}
		});
		new Label(container, SWT.NONE);
		lblError = new Label(container, SWT.NONE);
		lblError.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblError.setForeground(ColorConstants.red);
		lblError.setAlignment(SWT.CENTER);

		return super.createDialogArea(parent);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(title);
	}
	
	@Override
	  protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
	  }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private void validate() {
		if(name.isEmpty()) {
			getButton(IDialogConstants.OK_ID).setEnabled(false);
			lblError.setText("");
		} else if (takenNames.contains(name)) {
			getButton(IDialogConstants.OK_ID).setEnabled(false);
			lblError.setText(error);
		} else {
			getButton(IDialogConstants.OK_ID).setEnabled(true);
			lblError.setText("");
		}
	}
}
