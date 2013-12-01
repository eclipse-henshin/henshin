/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.ui.wizard;

import org.eclipse.emf.henshin.interpreter.ui.HenshinInterpreterUIPlugin;
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
 * @author Gregor Bonifer, Stefan Jurack, Christian Krause
 */
public class HenshinWizardDialog extends WizardDialog {
	
	// Preview button:
	protected Button previewButton;
	
	/**
	 * Constructor.
	 * @param parentShell Parent shell.
	 * @param wizard The wizard.
	 */
	public HenshinWizardDialog(Shell parentShell, HenshinWizard wizard) {
		super(parentShell, wizard);
		wizard.addCompletionListener(new CompletionListener() {
			@Override
			public void completionChanged() {
				HenshinWizardDialog.this.updateButtons();
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardDialog#getWizard()
	 */
	@Override
	protected HenshinWizard getWizard() {
		return (HenshinWizard) super.getWizard();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardDialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		
		// Comment by CK: what is this?:
		((GridLayout) parent.getLayout()).numColumns += 2;
		// System.out.println(((GridLayout) parent.getLayout()).numColumns);
		
		final Button saveOnCancelButton = new Button(parent, SWT.CHECK);
		{
			GridData data = new GridData();
			data.horizontalSpan = 4;
			data.horizontalAlignment = GridData.END;
			saveOnCancelButton.setLayoutData(data);
		}
		saveOnCancelButton.setText(HenshinInterpreterUIPlugin.LL("_UI_SaveConfigurationOnCancel"));
		saveOnCancelButton.setSelection(HenshinInterpreterUIPlugin.getPlugin().getPreferenceStore()
				.getBoolean("saveOnCancel"));
		saveOnCancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HenshinInterpreterUIPlugin.getPlugin().getPreferenceStore()
						.setValue("saveOnCancel", saveOnCancelButton.getSelection());
			}
		});
		
		previewButton = new Button(parent, SWT.PUSH);
		previewButton.setText(HenshinInterpreterUIPlugin.LL("_UI_ShowPreview"));
		previewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HenshinWizardDialog.this.getWizard().performPreview();
			}
		});
		setButtonLayoutData(previewButton);
		
		super.createButtonsForButtonBar(parent);
		
		for (Control ctrl : parent.getChildren()) {
			if (ctrl instanceof Button) {
				Button b = (Button) ctrl;
				if (b.getText().equals(IDialogConstants.FINISH_LABEL)) {
					b.setText(HenshinInterpreterUIPlugin.LL("_UI_ApplyTransformation"));
				}
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardDialog#updateButtons()
	 */
	@Override
	public void updateButtons() {
		super.updateButtons();
		previewButton.setEnabled(getWizard().canFinish());
	}
	
}
