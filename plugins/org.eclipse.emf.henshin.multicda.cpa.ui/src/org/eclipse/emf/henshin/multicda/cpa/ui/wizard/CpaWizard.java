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

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.multicda.cda.ConflictAnalysis;
import org.eclipse.emf.henshin.multicda.cda.DependencyAnalysis;
import org.eclipse.emf.henshin.multicda.cda.MultiGranularAnalysis;
import org.eclipse.emf.henshin.multicda.cda.Pair;
import org.eclipse.emf.henshin.multicda.cda.framework.CdaWorker;
import org.eclipse.emf.henshin.multicda.cda.framework.ResultCreator;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cpa.CDAOptions;
import org.eclipse.emf.henshin.multicda.cpa.CDAOptions.ConflictType;
import org.eclipse.emf.henshin.multicda.cpa.CDAOptions.GranularityType;
import org.eclipse.emf.henshin.multicda.cpa.CPAUtility;
import org.eclipse.emf.henshin.multicda.cpa.CpaByAGG;
import org.eclipse.emf.henshin.multicda.cpa.ICriticalPairAnalysis;
import org.eclipse.emf.henshin.multicda.cpa.UnsupportedRuleException;
import org.eclipse.emf.henshin.multicda.cpa.persist.SpanNode;
import org.eclipse.emf.henshin.multicda.cpa.result.CPAResult;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;
import org.eclipse.emf.henshin.multicda.cpa.ui.presentation.CpaResultsView;
import org.eclipse.emf.henshin.multicda.cpa.ui.util.CpEditorUtil;
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
	MultiGranularAnalysis CDAdep;
	MultiGranularAnalysis CDAcon;
	Map<Rule, Map<Rule, Pair<Set<Reason>, Set<Reason>>>> cdaResult = new HashMap<>();
	Set<Atom> cdaResultB = new HashSet<>();
	Set<Reason> cdaResultC = new HashSet<>();
	Set<Reason> cdaResultF = new HashSet<>();
	CPAResult cpaResult;
	HashMap<Rule, String> rulesAndAssociatedFileNames;
	boolean isTableB;
	boolean isTableC;
	boolean isTableF;
	boolean isATableB;
	boolean isATableC;
	boolean isATableF;

	/**
	 * Constructor of the wizard for configuring the critical pair analysis in the
	 * user interface.
	 * 
	 * @param selectedFiles
	 *            The List of Files which serve as source for the rules.
	 */
	public CpaWizard(List<?> selectedFiles) {

		rulesAndAssociatedFileNames = new HashMap<Rule, String>();

		// collect the rules of all selected files
		for (Object selection : selectedFiles) {
			if (selection instanceof IFile) {

				IPath pathOfSelection = ((IFile) selection).getLocation();
				String pathAsString = pathOfSelection.toString();
				String pathWithoutFile = pathAsString.substring(0, pathAsString.indexOf(pathOfSelection.lastSegment()));

				if (resultPath.equals(""))
					resultPath = pathWithoutFile;
				resultPath = greatestCommonPrefix(resultPath, pathWithoutFile);
				IPath pathOfHenshinTransformationRules = pathOfSelection;
				if (pathOfSelection.getFileExtension().equals("henshin_diagram")) {
					pathOfHenshinTransformationRules = pathOfSelection.removeFileExtension();
					pathOfHenshinTransformationRules = pathOfHenshinTransformationRules.addFileExtension("henshin");
				}
				String fileNameOfTransformationRules = pathOfHenshinTransformationRules
						.segment(pathOfHenshinTransformationRules.segmentCount() - 1);
				HenshinResourceSet henshinResourceSet = new HenshinResourceSet();
				Module module = henshinResourceSet.getModule(pathOfHenshinTransformationRules.toOSString());
				for (Unit unit : module.getUnits()) {
					if (unit instanceof Rule) {
						rulesAndAssociatedFileNames.put((Rule) unit, fileNameOfTransformationRules);
					}
				}
			}
			// filename for the options. Defined here static for the usage of the options
			// with this wizard.
			optionsFile = resultPath + ".cda.options";
		}
	}

	public void addPages() {

		ruleAndCpKindSelectionWizardPage = new RuleAndCpKindSelectionWizardPage(rulesAndAssociatedFileNames,
				optionsFile);
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

	Map<String, List<SpanNode>> persistedB;
	Map<String, List<SpanNode>> persistedC;
	Map<String, List<SpanNode>> persistedF;
	Map<String, List<SpanNode>> initialCpaResult = new HashMap<>();
	Map<String, List<SpanNode>> essentialCpaResult = new HashMap<>();
	Map<String, List<SpanNode>> otherCpaResult = new HashMap<>();

	/**
	 * By finishing the Wizard the calculation of the critical pairs starts and
	 * afterwards the results are loaded within the <code>CPAView</code>.
	 */
	@Override
	public boolean performFinish() {

		cdaResult = new HashMap<>();
		cdaResultB = new HashSet<>();
		cdaResultC = new HashSet<>();
		cdaResultF = new HashSet<>();
		isTableB = optionSettingsWizardPage.isBinTable();
		isTableC = optionSettingsWizardPage.isCoarseTable();
		isTableF = optionSettingsWizardPage.isFineTable();
		isATableB = optionSettingsWizardPage.isABinTable();
		isATableC = optionSettingsWizardPage.isACoarseTable();
		isATableF = optionSettingsWizardPage.isAFineTable();
		CDAOptions options = optionSettingsWizardPage.getOptions();
		options.persist(optionsFile);
		options.cpTypes = ruleAndCpKindSelectionWizardPage.cpType;
		Pair<Set<Rule>, Set<Rule>> selectedRules = ruleAndCpKindSelectionWizardPage.getEnabledRules();
		Map<Rule, Rule> ignoredRulePairs = new HashMap<>();
		String rulePairs = "";
		Set<GranularityType> gs = options.granularities;
		for (Rule r1 : selectedRules.first) {
			for (Rule r2 : selectedRules.second) {
				if (r1.isMultiRule() || r2.isMultiRule()) {
					ignoredRulePairs.put(r1, r2);
					rulePairs += "\n" + r1.getName() + " -> " + r2.getName();
				}
			}
		}
		if (!ignoredRulePairs.isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Multirule kindness of rules is not fully supported by the multicda jet. The following rule pairs will be ignored by binary, coarse and fine granularities:\n"
							+ rulePairs,
					"Ignored Rule Pairs", JOptionPane.INFORMATION_MESSAGE);
		}
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

					int task1Klicks = 100;
					int task2Klicks = 1000;
					int work = selectedRules.first.size() * selectedRules.second.size();
					int totalWork = 0;
					int worked = 1;
					if (options.essentialCP || options.initialCP)
						totalWork += work * task2Klicks;
					if (options.otherCP)
						totalWork += work * task2Klicks;
					Set<GranularityType> gt = options.granularities;
					if (gt.contains(GranularityType.BINARY) || gt.contains(GranularityType.COARSE)
							|| gt.contains(GranularityType.FINE))
						totalWork += task1Klicks * selectedRules.first.size() * selectedRules.second.size();
					else
						task1Klicks = 0;
					if (options.cpTypes == ConflictType.BOTH)
						totalWork *= 2;
					String title = "Calculating ... ";
					monitor.beginTask(title, totalWork);

					long milis = System.currentTimeMillis();

					CPAResult conflictResult = null;
					CPAResult dependencyResult = null;
					Set<GranularityType> granularities = options.granularities;
					if (options.cpTypes == ConflictType.BOTH || options.cpTypes == ConflictType.CONFLICT) {
						for (Rule r1 : selectedRules.first) {
							for (Rule r2 : selectedRules.second) {
								long timeLeft = (totalWork - worked) * (System.currentTimeMillis() - milis) / worked;
								String desc = title + "\t " + (worked * 100 / totalWork) + "%" + "\t " + r1.getName()
										+ "  --conflict-->  " + r2.getName();
								if (System.currentTimeMillis() - milis > 3000) {
									desc = title + "\t " + (worked * 100 / totalWork) + "%" + " " + time(timeLeft) + "\t "
											+ r1.getName() + "  --conflict-->  " + r2.getName();
								}
								Copier r1C = new Copier();
								Copier r2C = new Copier();
								Rule r1s = (Rule) r1C.copy(r1);
								Rule r2s = (Rule) r2C.copy(r2);
								r1C.copyReferences();
								r2C.copyReferences();
								monitor.setTaskName(desc);
								if (!ignoredRulePairs.containsKey(r1) && !ignoredRulePairs.containsKey(r2))
									if (!(options.isIgnoreSameRules() && r1 == r2)) {
										CDAcon = new ConflictAnalysis(r1s, r2s);
										if (granularities.contains(GranularityType.BINARY)) {
											Atom a = CDAcon.computeResultsBinary();
											if (a != null)
												cdaResultB.add(a);
										}
										if (granularities.contains(GranularityType.COARSE))
											cdaResultC.addAll(CDAcon.computeResultsCoarse());
										if (granularities.contains(GranularityType.FINE))
											cdaResultF.addAll(CDAcon.computeResultsFine());
										if (granularities.contains(GranularityType.VERY_FINE)) {
											if (options.essentialCP || options.initialCP) {
												conflictResult = CpaByAGG.joinCPAResults(conflictResult,
														runCPA(r1, r2, options, true, true));
												monitor.worked(task2Klicks);
												worked += task2Klicks;
											}
											if (options.otherCP) {
												conflictResult = CpaByAGG.joinCPAResults(conflictResult,
														runCPA(r1, r2, options, true, false));
												monitor.worked(task2Klicks);
												worked += task2Klicks;
											}
										}
										monitor.worked(task1Klicks);
										worked += task1Klicks;
									}
								if (monitor.isCanceled()) {
									cdaResult = null;
									cpaResult = null;
									return;
								}

							}
						}
					}
					if (options.cpTypes == ConflictType.BOTH || options.cpTypes == ConflictType.DEPENDENCY) {
						for (Rule r1 : selectedRules.first) {
							for (Rule r2 : selectedRules.second) {
								long timeLeft = (totalWork - worked) * (System.currentTimeMillis() - milis) / worked;
								String desc = title + "\t " + (worked * 100 / totalWork) + "%" + "\t " + r1.getName()
										+ "  --dependency-->  " + r2.getName();
								if (System.currentTimeMillis() - milis > 3000)
									desc = title + "\t " + (worked * 100 / totalWork) + "%" + " " + time(timeLeft) + "\t "
											+ r1.getName() + "  --dependency-->  " + r2.getName();
								Copier r1C = new Copier();
								Copier r2C = new Copier();
								Rule r1s = (Rule) r1C.copy(r1);
								Rule r2s = (Rule) r2C.copy(r2);
								r1C.copyReferences();
								r2C.copyReferences();
								monitor.setTaskName(desc);
								if (!ignoredRulePairs.containsKey(r1) && !ignoredRulePairs.containsKey(r2))
									if (!(options.isIgnoreSameRules() && r1 == r2)) {
										CDAdep = new DependencyAnalysis(r1s, r2s);
										if (granularities.contains(GranularityType.BINARY)) {
											Atom a = CDAdep.computeResultsBinary();
											if (a != null)
												cdaResultB.add(a);
										}
										if (granularities.contains(GranularityType.COARSE))
											cdaResultC.addAll(CDAdep.computeResultsCoarse());
										if (granularities.contains(GranularityType.FINE))
											cdaResultF.addAll(CDAdep.computeResultsFine());
										if (granularities.contains(GranularityType.VERY_FINE)) {
											if (options.essentialCP || options.initialCP) {
												dependencyResult = CpaByAGG.joinCPAResults(dependencyResult,
														runCPA(r1, r2, options, false, true));
												monitor.worked(task2Klicks);
												worked += task2Klicks;
											}
											if (options.otherCP) {
												dependencyResult = CpaByAGG.joinCPAResults(dependencyResult,
														runCPA(r1, r2, options, false, false));
												monitor.worked(task2Klicks);
												worked += task2Klicks;
											}
											monitor.worked(task1Klicks);
											worked += task1Klicks;
										}
									}
								if (monitor.isCanceled()) {
									cdaResult = null;
									cpaResult = null;
									return;
								}
							}
						}
					}
					cpaResult = CpaByAGG.joinCPAResults(conflictResult, dependencyResult);

					int selected = cdaResultB.size() + cdaResultC.size() + cdaResultF.size();
					if (cpaResult != null)
						selected += cpaResult.getOtherCriticalPairs().size();
					monitor.beginTask("Creating result tree ...", selected);

					ResourceSet resSet = new ResourceSetImpl();
					resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore",
							new XMLResourceFactoryImpl());

					String path = getUniquePath();
					if (monitor.isCanceled()) {
						cdaResult = null;
						cpaResult = null;
						return;
					}
					persistedB = CpEditorUtil.persistCdaResult(cdaResultB, path);
					monitor.worked(cdaResultB.size());
					persistedC = CpEditorUtil.persistCdaResult(cdaResultC, path);
					monitor.worked(cdaResultC.size());
					persistedF = CpEditorUtil.persistCdaResult(cdaResultF, path);
					monitor.worked(cdaResultF.size());
					ResultCreator.create(cdaResultB, selectedRules.first, selectedRules.second, isTableB, isATableB, "Binary",
							path);
					ResultCreator.create(cdaResultC, selectedRules.first, selectedRules.second, isTableC, isATableC, "Coarse",
							path);
					ResultCreator.create(cdaResultF, selectedRules.first, selectedRules.second, isTableF, isATableF, "Fine",
							path);
					if (monitor.isCanceled()) {
						cdaResult = null;
						cpaResult = null;
						return;
					}
					if (cpaResult != null) {
						List<CriticalPair> essential = new ArrayList<>();
						List<CriticalPair> initial = new ArrayList<>();
						List<CriticalPair> other = new ArrayList<>();
						if (options.initialCP)
							initial = cpaResult.getInitialCriticalPairs(true);
						if (options.essentialCP) {
							essential = cpaResult.getEssentialCriticalPairs();
						}
						if (options.otherCP) {
							other = cpaResult.getOtherCriticalPairs();
							// if (initial != null) //TODO: CPs must have a good equals method for correct deleting. If so, comment this lines in.
							// other.removeAll(initial);
							// if (essential != null)
							// other.removeAll(essential);
						}

						if (monitor.isCanceled()) {
							cdaResult = null;
							cpaResult = null;
							return;
						}
						monitor.done();
						initialCpaResult = CPAUtility.persistCpaResult(initial, path);
						essentialCpaResult = CPAUtility.persistCpaResult(essential, path);
						otherCpaResult = CPAUtility.persistCpaResult(other, path);
					}
				}

				private String getUniquePath() {
					Date timestamp = new Date();
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
					String timestampFolder = simpleDateFormat.format(timestamp);

					return resultPath + File.separator + timestampFolder;
				}

				private String time(long time) {
					long seconds = (time / 1000) % 60;
					long minutes = (time / 60000) % 60;
					long hours = (time / 3600000) % 24;
					long days = (time / 86400000) % 365;
					long years = (time / 31536000000L);

					return "  " + (years == 0 ? "" : years + "y, ") + (days == 0 ? "" : days + "d, ")
							+ (hours == 0 ? "" : hours + "h, ") + (minutes == 0 ? "" : minutes + "m, ")
							+ (seconds + "s");
				}

			});

			if (cpaResult == null && cdaResult == null)
				return false;
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
			IViewPart cPAView = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView("org.eclipse.emf.henshin.multicda.cpa.ui.views.CPAView");
			if (cPAView instanceof CpaResultsView) {
				CpaResultsView view = (CpaResultsView) cPAView;
				view.setContent(persistedB, persistedC, persistedF, initialCpaResult, essentialCpaResult,
						otherCpaResult);
				view.update();
			}
			return true;
		} catch (InvocationTargetException e1) {
			// errors caused by the IRunnable
			e1.printStackTrace();
			return false;
		} catch (InterruptedException e1) {
			System.err.println("Interrupted");
			return false;
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return true;
	}

	private CPAResult runCPA(Rule r1, Rule r2, CDAOptions options, boolean conf, boolean essential) {
		ConflictAnalysis.unnamedNodeID = 0;
		r1 = ConflictAnalysis.prepareWithACons(r1);
		r2 = ConflictAnalysis.prepareWithACons(r2);
		boolean essentialTemp = options.essentialCP;
		options.essentialCP = essential;
		CPAResult result;
		ICriticalPairAnalysis cpa = new CpaByAGG();
		Set<Rule> r1s = new HashSet<>();
		r1s.add(r1);
		Set<Rule> r2s = new HashSet<>();
		r2s.add(r2);
		try {
			cpa.init(r1s, r2s, options);

		} catch (UnsupportedRuleException e) {
			MessageDialog.openError(getShell(), "Error occured while initialising the critical pair analysis!",
					e.getDetailedMessage() + "\n Thus critical pairs cannot be calculated.");
		}
		if (conf)
			result = cpa.runConflictAnalysis();
		else
			result = cpa.runDependencyAnalysis();
		options.essentialCP = essentialTemp;
		return result;
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
