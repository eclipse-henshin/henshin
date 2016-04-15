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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.henshin.cpa.CpaByAGG;
import org.eclipse.emf.henshin.cpa.CPAOptions;
import org.eclipse.emf.henshin.cpa.CPAUtility;
import org.eclipse.emf.henshin.cpa.ICriticalPairAnalysis;
import org.eclipse.emf.henshin.cpa.UnsupportedRuleException;
import org.eclipse.emf.henshin.cpa.persist.CriticalPairNode;
import org.eclipse.emf.henshin.cpa.result.CPAResult;
import org.eclipse.emf.henshin.cpa.result.CriticalPair;
import org.eclipse.emf.henshin.cpa.ui.presentation.CpaResultsView;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

public class CpaWizard extends Wizard {

	protected RuleAndCpKindSelectionWizardPage ruleAndCpKindSelectionWizardPage;
	protected OptionSettingsWizardPage optionSettingsWizardPage;
	String fileName = "";
	String resultPath = "";
	String optionsFile = "";
	ICriticalPairAnalysis aggCPA;
	CPAResult cpaResult;
	HashMap<Rule, String> rulesAndAssociatedFileNames;

	/**
	 * Constructor of the wizard for configuring the critical pair analysis in the user interface.
	 * 
	 * @param selectedFiles The List of Files which serve as source for the rules.
	 */
	public CpaWizard(List<?> selectedFiles) {

		rulesAndAssociatedFileNames = new HashMap<Rule, String>();

		// collect the rules of all selected files
		for (Object selection : selectedFiles) {
			if (selection instanceof IFile) {

				IPath path = ((IFile) selection).getLocation();
				String pathAsString = path.toString();
				String pathWithoutFile = pathAsString.substring(0, pathAsString.indexOf(path.lastSegment()));

				if (resultPath.equals(""))
					resultPath = pathWithoutFile;
				resultPath = greatestCommonPrefix(resultPath, pathWithoutFile);
				String fileName = path.segment(path.segmentCount() - 1);
				HenshinResourceSet henshinResourceSet = new HenshinResourceSet();
				Module module = henshinResourceSet.getModule(path.toOSString());
				for (Unit unit : module.getUnits()) {
					if (unit instanceof Rule) {
						rulesAndAssociatedFileNames.put((Rule) unit, fileName);
					}
				}
			}
			// filename for the options. Defined here static for the usage of the options with this wizard.
			optionsFile = resultPath + ".cpa.options";
		}
		aggCPA = new CpaByAGG();
	}

	public void addPages() {

		ruleAndCpKindSelectionWizardPage = new RuleAndCpKindSelectionWizardPage(rulesAndAssociatedFileNames);
		addPage(ruleAndCpKindSelectionWizardPage);

		optionSettingsWizardPage = new OptionSettingsWizardPage("OPTIONS", optionsFile);
		addPage(optionSettingsWizardPage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#needsProgressMonitor
	 */
	@Override
	public boolean needsProgressMonitor() {
		return true;
	}

	/**
	 * By finishing the Wizard the calculation of the critical pairs starts and afterwards the results are loaded within
	 * the <code>CPAView</code>.
	 */
	@Override
	public boolean performFinish() {

		CPAOptions options = new CPAOptions();
		options.setComplete(optionSettingsWizardPage.getComplete());
		options.setIgnore(optionSettingsWizardPage.getIgnoreIdenticalRules());
		options.setReduceSameRuleAndSameMatch(optionSettingsWizardPage.getReduceSameMatch());
		options.persist(optionsFile);

		List<Rule> selectedRules = ruleAndCpKindSelectionWizardPage.getEnabledRules();

		boolean analysableRules = false;

		try {
			aggCPA.init(selectedRules, options);
			analysableRules = true;
		} catch (UnsupportedRuleException e) {
			MessageDialog.openError(getShell(), "Error occured while initialising the critical pair analysis!",
					e.getDetailedMessage() + "\n Thus critical pairs cannot be calculated.");
		}

		if (analysableRules) {

			try {
				getContainer().run(false, false, new IRunnableWithProgress() {

					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

						int totalWork = 10100;

						// adjustment in the case of calculation of dependencies and conflicts
						if (ruleAndCpKindSelectionWizardPage.getComputeConflicts()
								&& ruleAndCpKindSelectionWizardPage.getComputeDependencies())
							totalWork = 20100;

						monitor.beginTask("Calculating Critical Pairs... ", totalWork);

						monitor.worked(30);

						CPAResult conflictResult = null;
						CPAResult dependencyResult = null;

						if (ruleAndCpKindSelectionWizardPage.getComputeConflicts()) {
							conflictResult = aggCPA.runConflictAnalysis(monitor);
							monitor.worked(1000);
						}
						if (ruleAndCpKindSelectionWizardPage.getComputeDependencies()) {
							dependencyResult = aggCPA.runDependencyAnalysis(monitor);
							monitor.worked(1000);
						}

						cpaResult = joinCPAResults(conflictResult, dependencyResult);

						ResourceSet resSet = new ResourceSetImpl();
						resSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
								.put("ecore", new XMLResourceFactoryImpl());

						monitor.worked(20);
						monitor.done();
					}

					private CPAResult joinCPAResults(CPAResult conflictResult, CPAResult dependencyResult) {
						// if only conflicts or dependencies exist just return those.
						if (conflictResult == null)
							return dependencyResult;
						if (dependencyResult == null)
							return conflictResult;

						// join the conflicts and dependencies
						CPAResult cpaResult = new CPAResult();
						if (conflictResult != null && dependencyResult != null) {
							List<CriticalPair> conflCriticalPairs = conflictResult.getCriticalPairs();
							for (CriticalPair critPair : conflCriticalPairs) {
								cpaResult.addResult(critPair);
							}
							List<CriticalPair> depCriticalPairs = dependencyResult.getCriticalPairs();
							for (CriticalPair critPair : depCriticalPairs) {
								cpaResult.addResult(critPair);
							}
						}
						return cpaResult;
					}
				});

				HashMap<String, Set<CriticalPairNode>> persistedResults = CPAUtility.persistCpaResult(cpaResult,
						resultPath);

				ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);

				IViewPart cPAView = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.showView("org.eclipse.emf.henshin.cpa.ui.views.CPAView");
				if (cPAView instanceof CpaResultsView) {
					CpaResultsView view = (CpaResultsView) cPAView;
					view.setContent(persistedResults);
					view.update();
				}

				return true; // close Wizard

			} catch (InvocationTargetException e1) {
				// errors caused by the IRunnable
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// Should never occur because cancelable is set to false
				e1.printStackTrace();
			} catch (CoreException e) {
				// from ResourcesPlugin...refreshLocal(..)
				e.printStackTrace();
			}
		}

		return false; // keep Wizard open after ErrorMessage for reselection of the rules
	}

	private String greatestCommonPrefix(String a, String b) {
		int minLength = Math.min(a.length(), b.length());
		for (int i = 0; i < minLength; i++) {
			if (a.charAt(i) != b.charAt(i)) {
				return a.substring(0, i);
			}
		}
		return a.substring(0, minLength);
	}

}
