/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa.ui.wizard;

import java.util.Set;

import org.eclipse.emf.henshin.multicda.cpa.CDAOptions;
import org.eclipse.emf.henshin.multicda.cpa.CDAOptions.GranularityType;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class OptionSettingsWizardPage extends WizardPage {

	private Composite container;

	private CDAOptions cdaOptions;

	private boolean optionsLoaded;
	Button binaryButton;
	Button binaryTableButton;
	Button binaryATableButton;
	Button coarseButton;
	Button coarseTableButton;
	Button coarseATableButton;
	Button fineButton;
	Button fineTableButton;
	Button fineATableButton;
	Button veryFineButon;
	Button enableIgnoreIdenticalRulesButton;

	Button initialConflicts;
	Button essentialConflicts;
	Button conflicts;

	private final static String IGNOREIDENTICALRULES = "Ignore conflicts and dependencies of the same rule.";

	/**
	 * Default Constructor for the second page of the wizard. This page provides the
	 * functionality to adapt the options for the critical pair analysis.
	 * 
	 * @param pageName
	 *            The name of the page.
	 * @param optionsFile
	 *            Path to the options file.
	 */
	public OptionSettingsWizardPage(String pageName, String optionsFile) {
		super(pageName);
		setTitle("Conflict and Dependency Analysis - Granularity of Analysis");
		setDescription("Please indicate the depth of analysis.");

		cdaOptions = new CDAOptions();

		if (cdaOptions.load(optionsFile)) {
			optionsLoaded = true;
		} else {
			optionsLoaded = false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.henshin.multicda.cpa.ui.wizard.OptionSettingsWizardPage.
	 * createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout());

		Group granularities = new Group(container, SWT.NONE);
		granularities.setLayout(new GridLayout(2, true));
		granularities.setText("Granularities");
		granularities.setToolTipText("Choose your granularity to compute");
		granularities.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		binaryButton = new Button(granularities, SWT.CHECK);
		binaryButton.setText(GranularityType.BINARY.name);
		binaryButton.addListener(SWT.Selection, checkListener);
		binaryButton.setSelection(getGranularity().contains(GranularityType.BINARY));
		binaryButton.setData(GranularityType.BINARY);
		Group granularity = new Group(granularities, SWT.NONE);
		granularity.setLayout(new GridLayout(1, true));
		Label label = new Label(granularity, SWT.NONE);
		label.setText(GranularityType.BINARY.description);
		binaryTableButton = new Button(granularity, SWT.CHECK);
		binaryTableButton.setText("Create full result table");
		binaryTableButton.setSelection(cdaOptions.isBTable);
		binaryTableButton.setEnabled(getGranularity().contains(GranularityType.BINARY));
		binaryATableButton = new Button(granularity, SWT.CHECK);
		binaryATableButton.setText("Create abstract result table");
		binaryATableButton.setSelection(cdaOptions.isBATable);
		binaryATableButton.setEnabled(getGranularity().contains(GranularityType.BINARY));

		coarseButton = new Button(granularities, SWT.CHECK);
		coarseButton.setText(GranularityType.COARSE.name);
		coarseButton.addListener(SWT.Selection, checkListener);
		coarseButton.setSelection(getGranularity().contains(GranularityType.COARSE));
		coarseButton.setData(GranularityType.COARSE);
		granularity = new Group(granularities, SWT.NONE);
		granularity.setLayout(new GridLayout(1, true));
		label = new Label(granularity, SWT.NONE);
		label.setText(GranularityType.COARSE.description);
		coarseTableButton = new Button(granularity, SWT.CHECK);
		coarseTableButton.setText("Create full result table");
		coarseTableButton.setSelection(cdaOptions.isCTable);
		coarseTableButton.setEnabled(getGranularity().contains(GranularityType.COARSE));
		coarseATableButton = new Button(granularity, SWT.CHECK);
		coarseATableButton.setText("Create abstract result table");
		coarseATableButton.setSelection(cdaOptions.isCATable);
		coarseATableButton.setEnabled(getGranularity().contains(GranularityType.COARSE));

		fineButton = new Button(granularities, SWT.CHECK);
		fineButton.setText(GranularityType.FINE.name);
		fineButton.addListener(SWT.Selection, checkListener);
		fineButton.setSelection(getGranularity().contains(GranularityType.FINE));
		fineButton.setData(GranularityType.FINE);
		granularity = new Group(granularities, SWT.NONE);
		granularity.setLayout(new GridLayout(1, true));
		label = new Label(granularity, SWT.NONE);
		label.setText(GranularityType.FINE.description);
		fineTableButton = new Button(granularity, SWT.CHECK);
		fineTableButton.setText("Create full result table");
		fineTableButton.setSelection(cdaOptions.isFTable);
		fineTableButton.setEnabled(getGranularity().contains(GranularityType.FINE));
		fineATableButton = new Button(granularity, SWT.CHECK);
		fineATableButton.setText("Create abstract result table");
		fineATableButton.setSelection(cdaOptions.isFATable);
		fineATableButton.setEnabled(getGranularity().contains(GranularityType.FINE));

		boolean veryFine = getGranularity().contains(GranularityType.VERY_FINE);
		veryFineButon = new Button(granularities, SWT.CHECK);
		veryFineButon.setText(GranularityType.VERY_FINE.name);
		veryFineButon.addListener(SWT.Selection, checkListener);
		veryFineButon.setSelection(veryFine);
		veryFineButon.setData(GranularityType.VERY_FINE);
		label = new Label(granularities, SWT.NONE);
		label.setText(GranularityType.VERY_FINE.description);

		// ________________________________________________________
		Group cpaOptions = new Group(granularities, SWT.NONE);
		cpaOptions.setLayout(new GridLayout(1, true));
		cpaOptions.setText("CPA options");
		cpaOptions.setToolTipText("Choose critical pair kind to compute");

		cdaOptions.initialCP = cdaOptions.initialCP
				|| (!cdaOptions.essentialCP && !cdaOptions.initialCP && !cdaOptions.otherCP);

		initialConflicts = new Button(cpaOptions, SWT.CHECK);
		initialConflicts.setText("Compute initial conflicts (initial dependencies)");
		initialConflicts.addListener(SWT.Selection, checkListener);
		initialConflicts.setSelection(cdaOptions.initialCP);
		initialConflicts.setEnabled(veryFine);

		essentialConflicts = new Button(cpaOptions, SWT.CHECK);
		essentialConflicts.setText("Compute all further essential critical pairs");
		essentialConflicts.addListener(SWT.Selection, checkListener);
		essentialConflicts.setSelection(cdaOptions.essentialCP);
		essentialConflicts.setEnabled(veryFine);

		conflicts = new Button(cpaOptions, SWT.CHECK);
		conflicts.setText("Compute all further critical pairs");
		conflicts.addListener(SWT.Selection, checkListener);
		conflicts.setSelection(cdaOptions.otherCP);
		conflicts.setEnabled(veryFine);

		Composite buttonsComposite = new Composite(container, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(1, true));
		enableIgnoreIdenticalRulesButton = new Button(buttonsComposite, SWT.CHECK);
		enableIgnoreIdenticalRulesButton.setText(IGNOREIDENTICALRULES);
		enableIgnoreIdenticalRulesButton.addListener(SWT.Selection, checkListener);
		enableIgnoreIdenticalRulesButton.setSelection(getIgnoreIdenticalRules());
		setControl(container);
	}

	Listener checkListener = new Listener() {
		public void handleEvent(Event event) {
			Button button = (Button) (event.widget);
			Object data = button.getData();
			if (!binaryButton.getSelection() && !coarseButton.getSelection() && !fineButton.getSelection()
					&& !veryFineButon.getSelection())
				button.setSelection(true);
			else if (data != null && data instanceof GranularityType)
				if (((Button) event.widget).getSelection())
					cdaOptions.granularities.add((GranularityType) data);
				else
					cdaOptions.granularities.remove(data);
			else if (button == enableIgnoreIdenticalRulesButton)
				cdaOptions.setIgnoreSameRules(button.getSelection());

			boolean enableCpaOptions = cdaOptions.granularities.contains(GranularityType.VERY_FINE);
			initialConflicts.setEnabled(enableCpaOptions);
			essentialConflicts.setEnabled(enableCpaOptions);
			conflicts.setEnabled(enableCpaOptions);
			if (!initialConflicts.getSelection() && !essentialConflicts.getSelection() && !conflicts.getSelection())
				if ((button == initialConflicts || button == essentialConflicts || button == conflicts))
					button.setSelection(true);
				else
					initialConflicts.setSelection(true);
			if (data == GranularityType.BINARY) {
				binaryTableButton.setEnabled(button.getSelection());
				binaryATableButton.setEnabled(button.getSelection());
				if (!button.getSelection()) {
					binaryTableButton.setSelection(false);
					binaryATableButton.setSelection(false);
				}
			} else if (data == GranularityType.COARSE) {
				coarseTableButton.setEnabled(button.getSelection());
				coarseATableButton.setEnabled(button.getSelection());
				if (!button.getSelection()) {
					coarseTableButton.setSelection(false);
					coarseATableButton.setSelection(false);
				}
			} else if (data == GranularityType.FINE) {
				fineTableButton.setEnabled(button.getSelection());
				fineATableButton.setEnabled(button.getSelection());
				if (!button.getSelection()) {
					fineTableButton.setSelection(false);
					fineATableButton.setSelection(false);
				}
			}
			cdaOptions.initialCP = initialConflicts.getSelection() && initialConflicts.isEnabled();
			cdaOptions.essentialCP = essentialConflicts.getSelection() && essentialConflicts.isEnabled();
			cdaOptions.otherCP = conflicts.getSelection() && conflicts.isEnabled();
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

	public CDAOptions getOptions() {
		cdaOptions.isBTable = isBinTable();
		cdaOptions.isBATable = isABinTable();
		cdaOptions.isCTable = isCoarseTable();
		cdaOptions.isCATable = isACoarseTable();
		cdaOptions.isFTable = isFineTable();
		cdaOptions.isFATable = isAFineTable();
		return cdaOptions;
	}

	public boolean isBinTable() {
		return binaryTableButton.getSelection();
	}

	public boolean isCoarseTable() {
		return coarseTableButton.getSelection();
	}

	public boolean isFineTable() {
		return fineTableButton.getSelection();
	}

	public boolean isABinTable() {
		return binaryATableButton.getSelection();
	}

	public boolean isACoarseTable() {
		return coarseATableButton.getSelection();
	}

	public boolean isAFineTable() {
		return fineATableButton.getSelection();
	}

	public Boolean getComplete() {
		return cdaOptions.isComplete();
	}

	public Set<GranularityType> getGranularity() {
		return cdaOptions.granularities;
	}

	public Boolean getStrongAttrCheck() {
		return cdaOptions.isStrongAttrCheck();
	}

	public Boolean getEqualVariableNameOfAttrMapping() {
		return cdaOptions.isEqualVName();
	}

	public Boolean getIgnoreIdenticalRules() {
		return cdaOptions.isIgnoreSameRules();
	}

	public void setIgnoreIdenticalRules(Boolean ignoreIdenticalRules) {
		cdaOptions.setIgnoreSameRules(ignoreIdenticalRules);
	}

	public Boolean getReduceSameMatch() {
		return cdaOptions.isReduceSameRuleAndSameMatch();
	}

	public void setReduceSameMatch(Boolean reduceSameMatch) {
		cdaOptions.setReduceSameRuleAndSameMatch(reduceSameMatch);
	}

	public Boolean getDirectlyStrictConfluent() {
		return cdaOptions.isDirectlyStrictConfluent();
	}
}
