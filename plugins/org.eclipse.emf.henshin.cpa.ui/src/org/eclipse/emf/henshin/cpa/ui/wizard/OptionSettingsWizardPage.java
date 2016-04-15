/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.cpa.ui.wizard;

import org.eclipse.emf.henshin.cpa.CPAOptions;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class OptionSettingsWizardPage extends WizardPage {

	private Composite container;

	private CPAOptions cpaOptions;

	private boolean optionsLoaded;

	private final static String COMPLETE = "complete critical pairs (if not selected, search up to first critical match)";
	private final static String IGNOREIDENTICALRULES = "ignore critical pairs of same rules";
	private final static String REDUCESAMEMATCH = "ignore critical pairs of same rules and same matches";

	/**
	 * Default Constructor for the second page of the wizard. This page provides the functionality to adapt the options
	 * for the critical pair analysis.
	 * 
	 * @param pageName The name of the page.
	 * @param optionsFile Path to the options file.
	 */
	public OptionSettingsWizardPage(String pageName, String optionsFile) {
		super(pageName);
		setTitle("Critical Pair Analysis - Option Settings");
		setDescription("Customize the options.");

		cpaOptions = new CPAOptions();

		if (cpaOptions.load(optionsFile)) {
			optionsLoaded = true;
		} else {
			optionsLoaded = false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.henshin.cpa.ui.wizard.OptionSettingsWizardPage.createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout());

		Button enableCompleteButton = new Button(container, SWT.CHECK);
		enableCompleteButton.setText(COMPLETE);
		enableCompleteButton.addListener(SWT.Selection, checkListener);
		enableCompleteButton.setSelection(getComplete());

		Button enableIgnoreIdenticalRulesButton = new Button(container, SWT.CHECK);
		enableIgnoreIdenticalRulesButton.setText(IGNOREIDENTICALRULES);
		enableIgnoreIdenticalRulesButton.addListener(SWT.Selection, checkListener);
		enableIgnoreIdenticalRulesButton.setSelection(getIgnoreIdenticalRules());

		setControl(container);
	}

	Listener checkListener = new Listener() {

		public void handleEvent(Event event) {
			Button button = (Button) (event.widget);

			// improve by switch-case statement with change to Java 1.7
			if (button.getText().equals(COMPLETE)) {
				setComplete(button.getSelection());
			} else if (button.getText().equals(IGNOREIDENTICALRULES)) {
				setIgnoreIdenticalRules(button.getSelection());
			} else if (button.getText().equals(REDUCESAMEMATCH)) {
				setReduceSameMatch(button.getSelection());
			}
		}

	};

	/**
	 * Returns <code>true</code> if the options had been loaded.
	 * 
	 * @return <code>true</code> if the options had been loaded.
	 */
	protected boolean isOptionsLoaded() {
		return optionsLoaded;
	}

	public CPAOptions getOptions() {
		return cpaOptions;
	}

	public Boolean getComplete() {
		return cpaOptions.isComplete();
	}

	public void setComplete(Boolean complete) {
		cpaOptions.setComplete(complete);
	}

	public Boolean getStrongAttrCheck() {
		return cpaOptions.isStrongAttrCheck();
	}

	public Boolean getEqualVariableNameOfAttrMapping() {
		return cpaOptions.isEqualVName();
	}

	public Boolean getIgnoreIdenticalRules() {
		return cpaOptions.isIgnore();
	}

	public void setIgnoreIdenticalRules(Boolean ignoreIdenticalRules) {
		cpaOptions.setIgnore(ignoreIdenticalRules);
	}

	public Boolean getReduceSameMatch() {
		return cpaOptions.isReduceSameRuleAndSameMatch();
	}

	public void setReduceSameMatch(Boolean reduceSameMatch) {
		cpaOptions.setReduceSameRuleAndSameMatch(reduceSameMatch);
	}

	public Boolean getDirectlyStrictConfluent() {
		return cpaOptions.isDirectlyStrictConfluent();
	}
}
