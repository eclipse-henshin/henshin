/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.exporters.HenshinAGGExporter;
import org.eclipse.emf.henshin.model.impl.EdgeImpl;
import org.eclipse.emf.henshin.multicda.cpa.importer.AggHenshinCriticalPairTranslator;
import org.eclipse.emf.henshin.multicda.cpa.result.CPAResult;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;

import agg.parser.ConflictsDependenciesContainer;
import agg.parser.CriticalPairOption;
import agg.parser.DependencyPairContainer;
import agg.parser.ExcludePairContainer;
import agg.parser.ParserFactory;
import agg.util.XMLHelper;
import agg.xt_basis.GraGra;
import agg.xt_basis.GraTraOptions;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.Rule;

/**
 * The Implementation of the critical pair analysis for the <code>ICriticalPairAnalysis</code> interface by using the
 * analysis implemented in AGG.
 * 
 * @author Kristopher Born
 *
 */
public class CpaByAGG implements ICriticalPairAnalysis {
	GraGra gragra;
	Module module;

	AggHenshinCriticalPairTranslator importer;

	private boolean monitorProgress = false;
	private IProgressMonitor progressMonitor;

	Set<org.eclipse.emf.henshin.model.Rule> firstHenshinRuleSetForAnalysis;
	Set<org.eclipse.emf.henshin.model.Rule> secondHenshinRuleSetForAnalysis;

	Set<agg.xt_basis.Rule> firstAggRuleSetForAnalysis;
	Set<agg.xt_basis.Rule> secondAggRuleSetForAnalysis;

	String workingPathWOExtension;

	CDAOptions options;

	private boolean generateCpxFile = false;
	private boolean createRuleParameterForAllAttributes = true;

	private String resultDirectory = File.separator.concat("files").concat(File.separator).concat("results")
			.concat(File.separator);
	private String debugFileName = "debug.ggx";
	File pathToProject = new File("");
	File aggDebugFile = new File(
			pathToProject.getAbsolutePath().concat(File.separator).concat(resultDirectory).concat(debugFileName));

	/**
	 * Default constructor.
	 */
	public CpaByAGG() {
	}

	/**
	 * A constructor which allows to store the result of the critical pair analysis by AGG within AGGs format.
	 * 
	 * @param debugFile The File for storing the result of the critical pair analysis by AGG within AGGs format.
	 */
	public CpaByAGG(File debugFile) {
		generateCpxFile = true;
		aggDebugFile = debugFile;
	}

	/**
	 * Initializes the critical pair calculation with the <code>rules</code> parameter, used as first and second rules
	 * as well as the <code>options</code>. Even performs a check to ensure the parameters full fill the requirements to
	 * perform the analysis.
	 * 
	 * @param rules The rules for which the critical pair analysis shall be executed.
	 * @param options The options settings that shall be applied on the calculation of the critical pairs
	 * @throws UnsupportedRuleException in case of invalid rules.
	 */
	@Override
	public void init(Set<org.eclipse.emf.henshin.model.Rule> rules, CDAOptions options)
			throws UnsupportedRuleException {
		init(rules, rules, options);
	}

	/**
	 * Initializes the critical pair calculation with <code>r1</code> as first rules and <code>r2</code> as second rules
	 * of the critical pairs as well as the <code>options</code>. Even performs a check to ensure the parameters full
	 * fill the requirements to perform the analysis.
	 * 
	 * @param r1 the first rules for the critical pair analysis.
	 * @param r2 the second rules for the critical pair analysis.
	 * @param options the options settings that shall be applied on the calculation of the critical pairs.
	 * @throws UnsupportedRuleException in case of invalid rules.
	 */
	@Override
	public void init(Set<org.eclipse.emf.henshin.model.Rule> r1, Set<org.eclipse.emf.henshin.model.Rule> r2,
			CDAOptions options) throws UnsupportedRuleException {
		this.options = options;
		this.firstHenshinRuleSetForAnalysis = r1;
		this.secondHenshinRuleSetForAnalysis = r2;

		if (r1.size() == 0 || r2.size() == 0) {
			throw new UnsupportedRuleException(UnsupportedRuleException.noInputRule);
		}

		// prevents to export a rule twice.
		List<org.eclipse.emf.henshin.model.Rule> listOfAllOriginalRulesToBeExported = new LinkedList<org.eclipse.emf.henshin.model.Rule>();
		for (org.eclipse.emf.henshin.model.Rule rule : r1) {
			if (!listOfAllOriginalRulesToBeExported.contains(rule))
				listOfAllOriginalRulesToBeExported.add(rule);
		}
		for (org.eclipse.emf.henshin.model.Rule rule : r2) {
			if (!listOfAllOriginalRulesToBeExported.contains(rule))
				listOfAllOriginalRulesToBeExported.add(rule);
		}

		Module originalModuleOfFirstRule = null;
		org.eclipse.emf.henshin.model.Rule ruleForModuleCopy = null;

		//find a rule, which provides a useful module
		for (org.eclipse.emf.henshin.model.Rule rule : listOfAllOriginalRulesToBeExported) {
			if (originalModuleOfFirstRule != null && ruleForModuleCopy != null)
				break;
			if (rule.eContainer() != null
					&& (((Module) rule.eContainer()).getImports().get(0).getEClassifiers().size() > 0)) {
				originalModuleOfFirstRule = (Module) rule.eContainer();
				ruleForModuleCopy = rule;
			}
		}

		if (originalModuleOfFirstRule == null || ruleForModuleCopy == null) {
			System.err.println("ERROR: None of the rules provide a useful Module – no meta-model could be resolved.");
			return;
		}

		// creating a appropriate module for the export, with the aim to keep the provided rules and their modules unchanged
		module = (Module) EcoreUtil.copy(ruleForModuleCopy.eContainer());
		// since even unsatisfactory copies of the rules and units have been created, those have to be removed.
		module.getUnits().clear();

		// Only the designated rules are copied and added to the module for the export. The original rules remain unchanged. 
		for (org.eclipse.emf.henshin.model.Rule rule : listOfAllOriginalRulesToBeExported) {
			org.eclipse.emf.henshin.model.Rule copyOfRule = EcoreUtil.copy(rule);
			module.getUnits().add(copyOfRule);
			module.toString();
		}

		// checks the rules which shall serve later on for the analysis.
		Set<org.eclipse.emf.henshin.model.Rule> rulesToBeChecked = new HashSet<org.eclipse.emf.henshin.model.Rule>();
		for (Unit unit : module.getUnits()) {
			if (unit instanceof org.eclipse.emf.henshin.model.Rule)
				rulesToBeChecked.add((org.eclipse.emf.henshin.model.Rule) unit);
		}
		check(rulesToBeChecked);

		List<org.eclipse.emf.henshin.model.Rule> originalRules = new LinkedList<org.eclipse.emf.henshin.model.Rule>();
		originalRules.addAll(firstHenshinRuleSetForAnalysis);
		originalRules.addAll(secondHenshinRuleSetForAnalysis);
		// its important to instantiate the importer with the original rules, since the created results shall reference elements within the original rules
		importer = new AggHenshinCriticalPairTranslator(originalRules);

//		// first of all complete the rules by inserting missing EOpposite Edges, since they have to be present in the rule set within AGG
//		// the modification of the rules is negligible since we are working with copies of the original rules.
//		completeRulesByEOppositeEdges();

		// *.ggx file generation

		IStatus exportModuleToAGGStatus = exportModuleToAGG(workingPathWOExtension + ".ggx");
		if (exportModuleToAGGStatus.getSeverity() == IStatus.ERROR) {
			throw new UnsupportedRuleException(
					UnsupportedRuleException.exportFailed + " " + exportModuleToAGGStatus.getMessage());
		}
		String ggxFile = workingPathWOExtension + ".ggx";
		gragra = load(ggxFile);

		boolean checkDangling = true; // default
		boolean injectiveMatching = true; // default

		firstAggRuleSetForAnalysis = new HashSet<Rule>();
		secondAggRuleSetForAnalysis = new HashSet<Rule>();
		for (org.eclipse.emf.henshin.model.Rule rule : r1) {
			if (rule != null) {
				Rule equivalentAggRule = gragra.getRule(rule.getName());
				if (equivalentAggRule != null)
					firstAggRuleSetForAnalysis.add(equivalentAggRule);
				if (!rule.isCheckDangling())
					checkDangling = false;
				if (!rule.isInjectiveMatching())
					injectiveMatching = false;
			}
		}

		for (org.eclipse.emf.henshin.model.Rule rule : r2) {
			if (rule != null) {
				Rule equivalentAggRule = gragra.getRule(rule.getName());
				if (equivalentAggRule != null)
					secondAggRuleSetForAnalysis.add(equivalentAggRule);
				if (!rule.isCheckDangling())
					checkDangling = false;
				if (!rule.isInjectiveMatching())
					injectiveMatching = false;
			}
		}

		// set dangling and injective option for AGG
		MorphCompletionStrategy morphCompletionStrategy = gragra.getMorphismCompletionStrategy();
		if (!checkDangling)
			morphCompletionStrategy.removeProperty(GraTraOptions.DANGLING);
		if (!injectiveMatching)
			morphCompletionStrategy.removeProperty(GraTraOptions.INJECTIVE);
	}

	// this refactoring of the rules may be extractet into another class and rewritten as an Henshin inplace
	// transformation
	private void completeRulesByEOppositeEdges() {
		// 1. have to be done for all the rules
		for (Unit unit : module.getUnits()) {
			if (unit instanceof org.eclipse.emf.henshin.model.Rule) {
				// 2. all graphs of a rule have to be refactored, to be complete
				org.eclipse.emf.henshin.model.Rule rule = (org.eclipse.emf.henshin.model.Rule) unit;
				completeGraphByMissingEOppositeEdges(rule.getLhs());

				completeGraphByMissingEOppositeEdges(rule.getRhs());

				// 3. even the nested conditions have to be completed
				EList<NestedCondition> nestedConditionsOfLhs = rule.getLhs().getNestedConditions();
				for (NestedCondition nestedCondition : nestedConditionsOfLhs) {
					completeGraphByMissingEOppositeEdges(nestedCondition.getConclusion());
				}

				EList<NestedCondition> nestedConditionsOfRhs = rule.getLhs().getNestedConditions();
				for (NestedCondition nestedCondition : nestedConditionsOfRhs) {
					completeGraphByMissingEOppositeEdges(nestedCondition.getConclusion());
				}
			}
		}
	}

	private void completeGraphByMissingEOppositeEdges(Graph graph) {
		List<Edge> listOfEdgeWithMissingEOpposites = new LinkedList<Edge>();
		for (Edge edge : graph.getEdges()) {
			EReference eOppositeTypeOfEdge = edge.getType().getEOpposite();
			if (eOppositeTypeOfEdge != null) {
				if (edge.getTarget().getOutgoing(eOppositeTypeOfEdge, edge.getSource()) == null) {
					// the EOpposite edge does not exist within the graph
					listOfEdgeWithMissingEOpposites.add(edge);
				}
			}
		}
		for (Edge edgeWithMissingEOpposite : listOfEdgeWithMissingEOpposites) {
			new EdgeImpl(edgeWithMissingEOpposite.getTarget(), edgeWithMissingEOpposite.getSource(),
					edgeWithMissingEOpposite.getType().getEOpposite());
		}
	}

	/**
	 * Check for the validity of the rules in regard to the supported features.
	 * 
	 * @param rules The rules to be checked.
	 * @return <code>true</code> if the rules are valid for critical pair analysis.
	 * @throws UnsupportedRuleException in case of invalid rules.
	 */
	@Override
	public boolean check(Set<org.eclipse.emf.henshin.model.Rule> rules) throws UnsupportedRuleException {

		return InputDataChecker.getInstance().check(rules);
	}

	/**
	 * Saves the result of the <code>HenshinAGGExporter</code>.
	 * 
	 * @param savePath The path for saving the AGG file of the export process.
	 * @return
	 */
	private IStatus exportModuleToAGG(String savePath) {
		URI uri = URI.createFileURI(savePath);
		HenshinAGGExporter exporter = new HenshinAGGExporter();
		// exporter.setCreateRuleParameterForAllAttributes(createRuleParameterForAllAttributes); //reintroduce with
		// Henshin 1.3
		if (options.essentialCP)
			exporter.setExportWithoutUpperLimitsOnTypeGraph(true);
		return exporter.doExport(module, uri, options.isIgnoreMultiplicities());
	}

	/**
	 * Loads the *.ggx file <code>fName</code> into the <code>GraGra</code> representation
	 * 
	 * @param fileName input *.ggx file
	 * @return the <code>GraGra</code> if input was *.ggx else <code>null</code>
	 */
	private GraGra load(String fileName) {

		if (fileName.endsWith(".ggx")) {
			XMLHelper h = new XMLHelper();

			if (h.read_from_xml(fileName)) {
				GraGra gra = new GraGra(true);
				h.getTopObject(gra);
				gra.setFileName(fileName);
				return gra;
			}
		}
		return null;
	}

	/**
	 * Starts the calculation of conflicts for the initialized rules and options.
	 * 
	 * @return a <code>CPAResult</code>, which consists of a set of <code>Conflict</code>s.
	 */
	@Override
	public CPAResult runConflictAnalysis() {
		ExcludePairContainer epc = (ExcludePairContainer) ParserFactory.createEmptyCriticalPairs(gragra,
				CriticalPairOption.EXCLUDEONLY, false);
		epc.enablePACs(true);
		CPAResult conflictResult = null;

		setOptionsOnContainer(epc, options);
		computeCriticalPairs(firstAggRuleSetForAnalysis, secondAggRuleSetForAnalysis, epc);
		conflictResult = importer.importExcludePairContainer(epc, options.essentialCP);
		if (generateCpxFile)
			saveCPAasCPX(aggDebugFile.getAbsolutePath().replaceAll(".ggx", ".cpx"), epc, null);

		return conflictResult;
	}

	public static CPAResult joinCPAResults(CPAResult cr1, CPAResult cr2) {
		if (cr1 == null)
			return cr2;
		if (cr2 == null)
			return cr1;

		CPAResult cpaResult = new CPAResult();
		List<CriticalPair> cp1 = cr1.getCriticalPairs();
		for (CriticalPair critPair : cp1) {
			cpaResult.addResult(critPair);
		}
		List<CriticalPair> cp2 = cr2.getCriticalPairs();
		for (CriticalPair critPair : cp2) {
			cpaResult.addResult(critPair);
		}

		return cpaResult;
	}

	/**
	 * Starts the calculation of the conflicts for the initialized rules and options.
	 * 
	 * @param monitor a monitor to report the progress of the calculation.
	 * @return a set of critical pair results which are conflicts.
	 */
	@Override
	public CPAResult runConflictAnalysis(IProgressMonitor monitor) {
		progressMonitor = monitor;
		monitorProgress = true;
		return runConflictAnalysis();
	}

	/**
	 * Starts the calculation of the dependencies for the initialized rules and options.
	 * 
	 * @return a set of critical pair results which are dependencies.
	 */
	@Override
	public CPAResult runDependencyAnalysis() {

		DependencyPairContainer dpc = (DependencyPairContainer) ParserFactory.createEmptyCriticalPairs(gragra,
				CriticalPairOption.TRIGGER_DEPEND, false/* defaultValue */);
		dpc.enablePACs(true);

		CPAResult dependencyResult = null;
		setOptionsOnContainer(dpc, options);
		computeCriticalPairs(firstAggRuleSetForAnalysis, secondAggRuleSetForAnalysis, dpc);
		dependencyResult = importer.importExcludePairContainer(dpc, options.essentialCP);
		if (generateCpxFile)
			saveCPAasCPX(aggDebugFile.getAbsolutePath().replaceAll(".ggx", ".cpx"), null, dpc);

		return dependencyResult;
	}

	/**
	 * Starts the calculation of the dependencies for the initialized rules and options.
	 * 
	 * @param monitor a monitor to report the progress of the calculation.
	 * @return a set of critical pair results which are dependencies.
	 */
	@Override
	public CPAResult runDependencyAnalysis(IProgressMonitor monitor) {
		progressMonitor = monitor;
		monitorProgress = true;
		return runDependencyAnalysis();
	}

	/**
	 * compute the critical pair matrix between the two lists. in the case of N rules1 and M rules2, only N*M + M*N
	 * combinations will be evaluated. (instead of otherwise (n+m)*(m+n) )
	 * 
	 * @param rules1 first list of rules (horizontal order)
	 * @param rules2 second list of rules (vertical order)
	 */
	private void computeCriticalPairs(Set<Rule> rules1, Set<Rule> rules2, ExcludePairContainer exclude) {

		if (firstHenshinRuleSetForAnalysis.size() == 0 || secondHenshinRuleSetForAnalysis.size() == 0)
			return; // : hier muss mehr/etwas besseres hin als ein reines "return"

		if (exclude != null) {

			// progress handling
			int progressIntervalR1 = 10000;
			int progressIntervalR2 = 10000;
			if (firstHenshinRuleSetForAnalysis != null && secondHenshinRuleSetForAnalysis != null) {
				progressIntervalR1 = 10000 / firstHenshinRuleSetForAnalysis.size(); //: wenn "firstHenshinRuleSetForAnalysis" keine Elemnte enthält kommt es zu einem Fehler 
				progressIntervalR2 = progressIntervalR1 / secondHenshinRuleSetForAnalysis.size(); //: wenn "secondHenshinRuleSetForAnalysis" keine Elemnte enthält kommt es zu einem Fehler
			}

			for (Rule r1 : rules1) {
				int partialProgress = 0;

				for (Rule r2 : rules2) {
					try {
						// BenchmarkUtility.startTimeMeasurement();
						// Long startTime = System.currentTimeMillis(:)
						// TimeUnit.MILLISECONDS.
						// System.out.println("time: ");

						exclude.getCriticalPair(r1, r2, agg.parser.CriticalPair.EXCLUDE, true);

						// System.out.println("duration: ");

					} catch (Exception ex) {
						ex.printStackTrace();
					}
					if (monitorProgress) {
						progressMonitor.worked(progressIntervalR2);
						partialProgress += progressIntervalR2;
					}
				}
				if (monitorProgress) {
					progressMonitor.worked(progressIntervalR1 - partialProgress);
				}
			}
		}
	}

	/**
	 * Saves the analysis result into a <code>*.ggx</code> file.
	 * 
	 * @param cpaFileName path and name for the desired <code>*.ggx</code> file.
	 * @param epc The container object for conflict results.
	 * @param dpc The container object for dependency results.
	 * 
	 */
	private void saveCPAasCPX(String cpaFileName, ExcludePairContainer epc, DependencyPairContainer dpc) {
		ConflictsDependenciesContainer cDC = new ConflictsDependenciesContainer(epc, dpc);
		XMLHelper xmlHelper = new XMLHelper();
		xmlHelper.addTopObject(cDC);
		xmlHelper.save_to_xml(cpaFileName);
	}

	/**
	 * Enables the generation of the CPX file, which provides the results of AGG in its file format.
	 */
	public void enableCpxFileGeneration() {
		this.generateCpxFile = true;
	}

	/**
	 * Sets the options from the henshin interface in the associated part in AGG.
	 * 
	 * @param epc The container with the rules, options and many more within AGG for calculating the critical pairs.
	 * @param options The options set for the calculation by the henshin interface.
	 */
	private void setOptionsOnContainer(ExcludePairContainer epc, CDAOptions options) {
		// input values
		epc.enableComplete(options.isComplete());
		// no more supported, since theses parameters are predefined
		epc.enableReduce(options.essentialCP);
		// epc.enableConsistent(options.isConsistent());
		epc.enableStrongAttrCheck(options.isStrongAttrCheck());
		epc.enableEqualVariableNameOfAttrMapping(options.isEqualVName());
		epc.enableIgnoreIdenticalRules(options.isIgnoreSameRules());
		epc.enableReduceSameMatch(options.isReduceSameRuleAndSameMatch());
		epc.enableDirectlyStrictConfluent(options.isDirectlyStrictConfluent());
		epc.enableDirectlyStrictConfluentUpToIso(options.isDirectlyStrictConfluentUpToIso());
		epc.enablePACs(true);
	}

	/**
	 * Sets the creation of rule parameters for all attributes used in a rule. The default case is to create rule
	 * parameters for all attributes used in a rule.
	 * 
	 * @param createRuleParameterForAllAttributes The new boolean value for the creation of rule parameters for all
	 *            rules beeing exported to AGG.
	 */
	public void setCreateRuleParameterForAllAttributes(boolean createRuleParameterForAllAttributes) {
		this.createRuleParameterForAllAttributes = createRuleParameterForAllAttributes;
	}

	private void setAppliedAnalysisToEssential(CPAResult cpaResult) {
		for (CriticalPair cp : cpaResult.getCriticalPairs()) {
			cp.setAppliedAnalysis(CriticalPair.AppliedAnalysis.ESSENTIAL);
		}
	}

	private void setAppliedAnalysisToNotEssential(CPAResult cpaResult) {
		for (CriticalPair cp : cpaResult.getCriticalPairs()) {
			cp.setAppliedAnalysis(CriticalPair.AppliedAnalysis.COMPLETE);
		}
	}

}
