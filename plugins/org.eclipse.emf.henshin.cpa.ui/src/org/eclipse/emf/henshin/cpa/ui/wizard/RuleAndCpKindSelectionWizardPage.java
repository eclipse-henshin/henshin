/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.cpa.ui.wizard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.henshin.cpa.InputDataChecker;
import org.eclipse.emf.henshin.cpa.UnsupportedRuleException;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;

/**
 * First wizard page for the selection of the rules and whether the set of rules shall be anaylsed for conflict,
 * dependencies or both.
 * 
 * @author Kristopher Born
 *
 */
public class RuleAndCpKindSelectionWizardPage extends WizardPage {

	private Composite container;
	private Group rulesGroup;
	private Group criticalPairGroup;
	private boolean sufficientRulesSelected;
	private HashMap<Rule, String> rulesAndAssociatedFileNames;
	private final String CONFLICT_BUTTON_TXT = "Conflicts";
	private final String DEPENDENCY_BUTTON_TXT = "Dependencies";

	private final class RuleNameComparator implements Comparator<Rule> {
		public int compare(Rule r1, Rule r2) {
		    int compareResult = r1.getName().compareTo(r2.getName());
		    
		    if(compareResult<0)
		    	return -1;
		    else if(compareResult>0)
		    	return 1;
		    else
		    	return 0;
		}
	}

	private enum CPTypesEnum {
		CONFLICT, DEPENDENCY
	};

	EnumSet<CPTypesEnum> selectedCPTypes = EnumSet.noneOf(CPTypesEnum.class);

	/**
	 * Default constructor for this wizard page.
	 * 
	 * @param rulesAndAssociatedFileNames The rules which shall be provided as selection within in the wizard.
	 */
	public RuleAndCpKindSelectionWizardPage(HashMap<Rule, String> rulesAndAssociatedFileNames) {
		super("Precondition");
		setTitle("Critical Pair Analysis - Rule selection");
		setDescription("Please select the rules you want to check by the Critical Pair Analysis.");

		this.rulesAndAssociatedFileNames = rulesAndAssociatedFileNames;

		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		rulesGroup = new Group(container, SWT.NONE);
		rulesGroup.setLayout(new GridLayout());
		rulesGroup.setText("Rules");

		criticalPairGroup = new Group(container, SWT.NONE);
		criticalPairGroup.setLayout(new GridLayout());
		criticalPairGroup.setText("Calculate...");

		//sort the rules alphabetic
	    List<Rule> rulesForSelectionList = new ArrayList<Rule>(rulesAndAssociatedFileNames.keySet());
	    Collections.sort(rulesForSelectionList, new RuleNameComparator());

		for (Rule rule : rulesForSelectionList) {
			Button ruleSelectionButton = new Button(rulesGroup, SWT.CHECK);
			ruleSelectionButton.setText((rule.getName() == null) ? "null" : rule.getName()); // handle unnamed rules
			ruleSelectionButton.setData(rule);
			ruleSelectionButton.setToolTipText(rulesAndAssociatedFileNames.get(rule));
			ruleSelectionButton.addListener(SWT.Selection, checkListener);
		}

		Button selectAllButton = new Button(container, SWT.CHECK);
		selectAllButton.setText("Select all");
		selectAllButton.addListener(SWT.Selection, selectAllListener);
		selectAllButton.addListener(SWT.Selection, checkListener);

		Button conflictAnalysisButton = new Button(criticalPairGroup, SWT.CHECK);
		conflictAnalysisButton.setText(CONFLICT_BUTTON_TXT);
		conflictAnalysisButton.setData(CPTypesEnum.CONFLICT);
		conflictAnalysisButton.addListener(SWT.Selection, calcListener);

		Button dependencyAnalysisButton = new Button(criticalPairGroup, SWT.CHECK);
		dependencyAnalysisButton.setText(DEPENDENCY_BUTTON_TXT);
		dependencyAnalysisButton.setData(CPTypesEnum.DEPENDENCY);
		dependencyAnalysisButton.addListener(SWT.Selection, calcListener);

		setControl(container);
		setPageComplete(false);

	}

	/**
	 * returns the selection
	 * 
	 * @return returns <code>true</code> for Critical Pairs or <code>false</code> for Dependencies
	 */
	public boolean getComputeConflicts() {
		return selectedCPTypes.contains(CPTypesEnum.CONFLICT);
	}

	public boolean getComputeDependencies() {
		return selectedCPTypes.contains(CPTypesEnum.DEPENDENCY);
	}

	Listener calcListener = new Listener() {
		public void handleEvent(Event event) {
			Button button = (Button) (event.widget);
			CPTypesEnum cpTypeSelection = (CPTypesEnum) button.getData();
			if (button.getSelection()) {
				selectedCPTypes.add(cpTypeSelection);
			} else {
				selectedCPTypes.remove(cpTypeSelection);
			}
			updateFinishButton();
		}
	};

	Listener checkListener = new Listener() {
		public void handleEvent(Event event) {
			boolean selectAll = true;
			for (Control elem : rulesGroup.getChildren()) {
				if (elem instanceof Button) {
					selectAll &= ((Button) elem).getSelection();
				}
			}

			for (Control elem : container.getChildren()) {
				if (elem instanceof Button) {
					((Button) elem).setSelection(selectAll);
				}
			}

			checkAtLeastOneRuleIsSelected();

			try {
				InputDataChecker.getInstance().check(getEnabledRules());
				setErrorMessage(null);
			} catch (UnsupportedRuleException e) {
				setErrorMessage(e.getDetailedMessage());
				//TODO: differentiate between Errors (no analysis possible) and warnings (realisation see line below) 
//				setMessage(e.getDetailedMessage(), 2);
			}
		}
	};

	/**
	 * Listener for the "Select all" rules button.
	 */
	Listener selectAllListener = new Listener() {

		public void handleEvent(Event event) {
			Control[] controlArray = rulesGroup.getChildren();
			for (Control elem : controlArray) {
				if (elem instanceof Button) {
					boolean selectAll = ((Button) (event.widget)).getSelection();
					((Button) elem).setSelection(selectAll);
				}
			}
			checkAtLeastOneRuleIsSelected();
		}

	};

	/**
	 * This check ensures, that at least one rule is select before the wizard can be finished and by that the critical
	 * analysis is started.
	 */
	private void checkAtLeastOneRuleIsSelected() {
		sufficientRulesSelected = false;
		int checked = 0;
		Control[] controlArray = rulesGroup.getChildren();

		for (Control elem : controlArray) {
			if (elem instanceof Button) {
				if (((Button) elem).getSelection()) {
					checked++;
				}
			}
		}
		if (checked >= 1)
			sufficientRulesSelected = true;
		updateFinishButton();
	}

	private void updateFinishButton() {
		setPageComplete(false);
		if (sufficientRulesSelected && selectedCPTypes.size() > 0) {
			setPageComplete(true);
		}
		getWizard().getContainer().updateButtons();
	}

	public List<Rule> getEnabledRules() {
		List<Rule> result = new LinkedList<Rule>();

		Control[] controlArray = rulesGroup.getChildren();
		for (Control elem : controlArray) {
			if (elem instanceof Button) {
				if (((Button) elem).getSelection()) {
					Object data = elem.getData();
					if (data instanceof Rule) {
						result.add((Rule) data);
					}
				}
			}
		}
		return result;
	}
}
