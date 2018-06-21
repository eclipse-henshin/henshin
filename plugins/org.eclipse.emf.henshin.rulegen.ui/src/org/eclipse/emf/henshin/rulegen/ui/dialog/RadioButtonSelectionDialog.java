package org.eclipse.emf.henshin.rulegen.ui.dialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * Allows the user to choose only one option from a (small) selection that is specified beforehand.
 * 
 *  
 * @author Maike Basmer
 */

public class RadioButtonSelectionDialog extends SelectionDialog {

	private List<Entry<String, String>> buttonList; // ButtonID , Button Label
	private List<Button> widgetButtonList;

	/**
	 * @param parentShell - Parent Shell
	 */
	public RadioButtonSelectionDialog(Shell parentShell) {

		super(parentShell);
		
		// Avoid help button popping up.
		this.setHelpAvailable(false);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		GridLayout containerLayout = new GridLayout(1, true);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(containerLayout);

		// Create the message label
		GridData labelGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		Label label = super.createMessageArea(container);
		label.setLayoutData(labelGridData);

		// Add radio buttons to dialog.
		widgetButtonList = new ArrayList<>();
		GridData buttonGroupGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		Group buttonGroup = new Group(container, 0);
		buttonGroup.setText("Selected Resources");
		buttonGroup.setLayout(new GridLayout(1, true));
		buttonGroup.setLayoutData(buttonGroupGridData);

		boolean firstButtonSet = false;

		for (Entry<String, String> usrbutton : buttonList) {
			Button tmpButton = new Button(buttonGroup, SWT.RADIO);
			tmpButton.setText(usrbutton.getValue());

			// Auto-select first button.
			if (firstButtonSet == false) {
				tmpButton.setSelection(true);
				firstButtonSet = true;
			}
			widgetButtonList.add(tmpButton);
		}

		return container;
	}

	protected void saveInput() {

		// Remember which button was selected.
		for (int i = 0; i < widgetButtonList.size(); i++) {
			if (widgetButtonList.get(i).getSelection()) {
				List<String> result = new ArrayList<String>();
				result.add(buttonList.get(i).getKey());
				super.setResult(result);
			}
		}
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}
	
	public void setButtonList(List<Entry<String, String>> buttons){
		this.buttonList = buttons;
	}
	
	public List<Entry<String, String>> getButtonList(){
		return this.buttonList;
	}
}
