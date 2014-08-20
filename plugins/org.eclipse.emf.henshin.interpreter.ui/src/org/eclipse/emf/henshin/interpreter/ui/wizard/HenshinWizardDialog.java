/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.ui.wizard;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Gregor Bonifer, Stefan Jurack, Christian Krause
 */
public class HenshinWizardDialog extends WizardDialog {

	/**
	 * Constructor.
	 * 
	 * @param parentShell
	 *            Parent shell.
	 * @param wizard
	 *            The wizard.
	 */
	public HenshinWizardDialog(Shell parentShell, HenshinWizard wizard) {
		super(parentShell, wizard);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButton(org.eclipse.swt.widgets.Composite, int, java.lang.String, boolean)
	 */
	@Override
	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		if (id == IDialogConstants.FINISH_ID) {
			label = "Transform";
		}
		return super.createButton(parent, id, label, defaultButton);
	}

}
