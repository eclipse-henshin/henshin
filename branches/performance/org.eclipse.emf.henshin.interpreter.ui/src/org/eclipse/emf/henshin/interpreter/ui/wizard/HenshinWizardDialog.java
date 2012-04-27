/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter.ui.wizard;

import org.eclipse.emf.henshin.interpreter.ui.InterpreterUIPlugin;
import org.eclipse.emf.henshin.interpreter.ui.wizard.HenshinWizard.CompletionListener;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class HenshinWizardDialog extends WizardDialog {
	protected Button previewButton;
	
	/**
	 * @param parentShell
	 * @param newWizard
	 */
	public HenshinWizardDialog(Shell parentShell, HenshinWizard newWizard) {
		super(parentShell, newWizard);
		newWizard.addCompletionListener(new CompletionListener() {
			@Override
			public void completionChanged() {
				HenshinWizardDialog.this.updateButtons();
			}
		});
	}
	
	@Override
	protected HenshinWizard getWizard() {
		return (HenshinWizard) super.getWizard();
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jface.wizard.WizardDialog#createButtonsForButtonBar(org.eclipse
	 * .swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		
		((GridLayout) parent.getLayout()).numColumns += 2;
		System.out.println(((GridLayout) parent.getLayout()).numColumns);
		
		final Button saveOnCancelButton = new Button(parent, SWT.CHECK);
		{
			GridData data = new GridData();
			data.horizontalSpan = 4;
			data.horizontalAlignment = GridData.END;
			saveOnCancelButton.setLayoutData(data);
		}
		saveOnCancelButton.setText(InterpreterUIPlugin.LL("_UI_SaveConfigurationOnCancel"));
		saveOnCancelButton.setSelection(InterpreterUIPlugin.getPlugin().getPreferenceStore()
				.getBoolean("saveOnCancel"));
		saveOnCancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InterpreterUIPlugin.getPlugin().getPreferenceStore()
						.setValue("saveOnCancel", saveOnCancelButton.getSelection());
			}
		});
		
		previewButton = new Button(parent, SWT.PUSH);
		previewButton.setText(InterpreterUIPlugin.LL("_UI_ShowPreview"));
		previewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HenshinWizardDialog.this.getWizard().performPreview();
			}
		});
		setButtonLayoutData(previewButton);
		
		super.createButtonsForButtonBar(parent);
		
		for (Control ctrl : parent.getChildren())
			if (ctrl instanceof Button) {
				Button b = (Button) ctrl;
				if (b.getText().equals(IDialogConstants.FINISH_LABEL))
					b.setText(InterpreterUIPlugin.LL("_UI_ApplyTransformation"));
			}
	}
	
	@Override
	public void updateButtons() {
		super.updateButtons();
		previewButton.setEnabled(getWizard().canFinish());
	}
	
}
