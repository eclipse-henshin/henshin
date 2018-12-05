package org.eclipse.emf.henshin.multicda.cda.runner.pullback;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.multicda.cda.ConflictAnalysis;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason;
import org.eclipse.emf.henshin.multicda.cda.tasks.AtomicResultContainer;
import org.eclipse.emf.henshin.multicda.cda.tasks.CalculateAtomicCpaTask;
import org.eclipse.emf.henshin.multicda.cda.tasks.CalculateCpaTask;
import org.eclipse.emf.henshin.multicda.cda.tasks.CalculateCpaTask.AnalysisKind;
import org.eclipse.emf.henshin.multicda.cda.tasks.SingleCpaTaskResultContainer;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cpa.CDAOptions;
import org.eclipse.emf.henshin.multicda.cpa.CpaByAGG;
import org.eclipse.emf.henshin.multicda.cpa.ICriticalPairAnalysis;
import org.eclipse.emf.henshin.multicda.cpa.result.CPAResult;
import org.eclipse.emf.henshin.multicda.cpa.result.Conflict;
import org.eclipse.emf.henshin.multicda.cpa.result.ConflictKind;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;

public class Runner_WithPullback {

// options to turn on and off different analyses
	boolean runNormalCPA = true;
	boolean runEssentialCPA = true;
	boolean runAtomicAnalysis = true;

	private boolean noApplicationConditions = false;
	private boolean removeAllMultirules = false;
	private Set<String> limitedSetOfRulesByRuleNames;

	ICriticalPairAnalysis normalCpa = new CpaByAGG();
	CDAOptions normalOptions = new CDAOptions();

	ICriticalPairAnalysis essentialCpa = new CpaByAGG();
	CDAOptions essentialOptions = new CDAOptions();

	int numberOfAllEssentialConflicts = 0;
	int numberOfFilteredEssentialConflicts = 0;

	long totalNormalRuntime = 0;
	long totalEssentialRuntime = 0;
	long totalAtomicRuntime = 0;

	int totalNumberOfNormalCPs = 0;
	int totalNumberOfEssentialCPs = 0;
	int totalNumberOfAtomicCPs = 0;
	int totalNumberOfConflictAtomCandidates = 0;
	int totalNumberOfMinimalConflictReasons = 0;
	List<LoggerPB> loggers = new LinkedList<>();
	LoggerPB normalCpaLogger = new NormalCpaLoggerPB();
	LoggerPB minimalReasonLogger = new MinimalConflReasonLoggerPB();
	LoggerPB essentialCpaLogger = new EssentialCpaLoggerPB();

	int currentRow = 0;
	int firstRowToAnalyse = 0;
	int lastRowToAnalyse = 70;
	private boolean ruleMetricAdded;

	public void runWithActivatedRules(String fullSubDirectoryPath, List<String> activatedRules) {

		essentialOptions.essentialCP = true;
		loggers.add(normalCpaLogger);
		loggers.add(essentialCpaLogger);
		loggers.add(minimalReasonLogger);

		File dir = new File(fullSubDirectoryPath);
		List<String> pathsToHenshinFiles = inspectDirectoryForHenshinFiles(dir);

		List<Rule> allLoadedRules = loadAllActivatedRulesFromFileSystemPaths(pathsToHenshinFiles, activatedRules);

		for (Rule rule : allLoadedRules) {
			rule.setCheckDangling(true);
		}

		if (noApplicationConditions) {
			for (Rule rule : allLoadedRules) {
				List<NestedCondition> allPACs = new LinkedList<>(rule.getLhs().getPACs());
				for (NestedCondition pac : allPACs)
					rule.getLhs().removeNestedCondition(pac);
				List<NestedCondition> allNACs = new LinkedList<>(rule.getLhs().getNACs());
				for (NestedCondition nac : allNACs)
					rule.getLhs().removeNestedCondition(nac);
			}
		}

		if (removeAllMultirules) {
			for (Rule rule : allLoadedRules) {
				rule.getMultiRules().clear();
			}
		}

		List<Rule> copiesOfRulesWithoutDeletion = buildCopiesOfRulesWithoutDeletion(allLoadedRules);

		int numberOfAddedRules = allLoadedRules.size();

		for (LoggerPB loggerPB : loggers) {
			loggerPB.init(numberOfAddedRules);
			loggerPB.setAddDetailsOnRuleName(true);
		}

		List<String> recalculateEssentialCpaRules = new LinkedList<>();
		recalculateEssentialCpaRules.add("Generalization_2-2");
		recalculateEssentialCpaRules.add("Generalization_2-5");
		recalculateEssentialCpaRules.add("Refactoring_1-9");

		List<Rule> skippedRules = new LinkedList<Rule>();

		AnalysisKind analysisKind = CalculateCpaTask.AnalysisKind.CONFLICT;

		for (Rule firstRule : allLoadedRules) {

			if (skippedRules.contains(firstRule))
				System.err.println("skipping row for rule: " + firstRule.getName());
			if (!skippedRules.contains(firstRule)) {
				if (ruleIsntLimited(firstRule)) {
					if (currentRow >= firstRowToAnalyse && currentRow <= lastRowToAnalyse) {
						Graph lhsOfFirstRule = firstRule.getLhs();

						int elementsInLhsOfFirstRule = lhsOfFirstRule.getNodes().size()
								+ lhsOfFirstRule.getEdges().size();
						for (LoggerPB loggerPB : loggers) {
							loggerPB.addData(firstRule, null, Integer.toString(elementsInLhsOfFirstRule),
									Integer.toString(elementsInLhsOfFirstRule));
						}
						ruleMetricAdded = false;

						for (Rule secondRule : copiesOfRulesWithoutDeletion) {

							Rule originalRuleOfRule2 = allLoadedRules
									.get(copiesOfRulesWithoutDeletion.indexOf(secondRule));
							Graph lhsOfOriginalRuleOfRule2 = originalRuleOfRule2.getLhs();

							int elementsInLhsOfSecondRule = lhsOfOriginalRuleOfRule2.getNodes().size()
									+ lhsOfOriginalRuleOfRule2.getEdges().size();
							if (skippedRules.contains(secondRule) || skippedRules.contains(originalRuleOfRule2))
								System.err.println("skipping column for rule: " + secondRule.getName());
							if (!skippedRules.contains(secondRule) && !skippedRules.contains(originalRuleOfRule2)) {
								if (ruleIsntLimited(secondRule)) {
									boolean canceled = false;
									if (runNormalCPA)
										canceled = runNormalCPA(firstRule, secondRule, originalRuleOfRule2,
												skippedRules, analysisKind, elementsInLhsOfSecondRule);
									if (runEssentialCPA && !canceled)
										canceled = runEssentialCPA(firstRule, secondRule, originalRuleOfRule2,
												skippedRules, analysisKind, elementsInLhsOfSecondRule);
									if (runAtomicAnalysis)
										canceled = runAtomicAnalysis(firstRule, secondRule, originalRuleOfRule2,
												skippedRules, analysisKind, elementsInLhsOfSecondRule);
								}

							}
						}
						ruleMetricAdded = true;
						System.gc();
					}
				}
			}

			currentRow++;

		}

		System.err.println("numberOfAllEssentialConflicts: " + numberOfAllEssentialConflicts);
		System.err.println("numberOfFilteredEssentialConflicts: " + numberOfFilteredEssentialConflicts);
		System.out.println("HALT");

		for (LoggerPB loggerPB : loggers) {
			loggerPB.exportStoredRuntimeToCSV(fullSubDirectoryPath + File.separator);
			loggerPB.exportStoredConflictsToCSV(fullSubDirectoryPath + File.separator);
		}
	}

	private boolean ruleIsntLimited(Rule ruleToCheck) {
		if (limitedSetOfRulesByRuleNames == null) {
			return true;
		} else {
			System.out.println(ruleToCheck.getName());
			if (limitedSetOfRulesByRuleNames.contains(ruleToCheck.getName())) {
				return true;
			}
			return false;
		}
	}

	private List<Rule> buildCopiesOfRulesWithoutDeletion(List<Rule> allLoadedRules) {
		HenshinFactory henshinFactory = new HenshinFactoryImpl();
		List<Rule> copiesOfRulesWithoutDeletion = new LinkedList<>();
		for (Rule ruleToCopy : allLoadedRules) {
			Copier copierForRule = new Copier();
			Rule copyOfRule = (Rule) copierForRule.copy(ruleToCopy);
			copyOfRule.setName(copyOfRule.getName().concat(Reason.NODE_SEPARATOR));
			copierForRule.copyReferences();

			MappingList mappings = copyOfRule.getMappings();

			mappings.clear();
			for (Node nodeInLhs : copyOfRule.getLhs().getNodes()) {
				nodeInLhs.getAttributes().clear();
			}
			Copier copierForLhsGraph = new Copier();
			Graph copiedLhs = (Graph) copierForLhsGraph.copy(copyOfRule.getLhs());
			copierForLhsGraph.copyReferences();
			copiedLhs.setName("Rhs");
			copyOfRule.setRhs(copiedLhs);

			for (Node nodeInLhsOfCopiedRule : copyOfRule.getLhs().getNodes()) {
				Node nodeInNewRhs = (Node) copierForLhsGraph.get(nodeInLhsOfCopiedRule);
				Mapping createdMapping = henshinFactory.createMapping(nodeInLhsOfCopiedRule, nodeInNewRhs);
				mappings.add(createdMapping);
			}

			copiesOfRulesWithoutDeletion.add(copyOfRule);

		}
		return copiesOfRulesWithoutDeletion;
	}

	public void setAnalysisKinds(boolean runNormalCPA, boolean runEssentialCPA, boolean runAtomicAnalysis) {
		this.runNormalCPA = runNormalCPA;
		this.runEssentialCPA = runEssentialCPA;
		this.runAtomicAnalysis = runAtomicAnalysis;
	}

	private List<Rule> loadAllActivatedRulesFromFileSystemPaths(List<String> pathsToHenshinFiles,
			List<String> namesOfActivatedRules) {
		List<Rule> allEditRulesWithoutAmalgamation = new LinkedList<Rule>();

		for (String pathToHenshinFiles : pathsToHenshinFiles) {
			HenshinResourceSet henshinResourceSet = new HenshinResourceSet();
			Module module = henshinResourceSet.getModule(pathToHenshinFiles);
			for (Unit unit : module.getUnits()) {
				if (unit instanceof Rule /* && numberOfAddedRules<10 */) {
					boolean activatedRule = false;

					if (namesOfActivatedRules.contains(unit.getName()))
						allEditRulesWithoutAmalgamation.add((Rule) unit);
					if (activatedRule) {
						allEditRulesWithoutAmalgamation.add((Rule) unit);
					}
				}
			}
		}
		return allEditRulesWithoutAmalgamation;
	}

	private List<CriticalPair> filterDeleteUseConflicts(CPAResult essentialResult) {
		if (essentialResult != null) {
			List<CriticalPair> criticalPairs = essentialResult.getCriticalPairs();
			List<CriticalPair> filteredDeleteUseConflicts = new LinkedList<CriticalPair>();
			for (CriticalPair cp : criticalPairs) {
				if (cp instanceof Conflict) {
					if (((Conflict) cp).getConflictKind().equals(ConflictKind.DELETE_USE_CONFLICT)) {
						filteredDeleteUseConflicts.add(cp);
					}
				}
			}
			return filteredDeleteUseConflicts;
		}
		return new LinkedList<CriticalPair>();
	}

	private List<String> inspectDirectoryForHenshinFiles(File dir) {
		List<String> pathsToHenshinFiles = new LinkedList<String>();
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				System.out.println("recursive call of exploration method");
				String fileName = child.getName();
				if (fileName.endsWith(".henshin")) {
					pathsToHenshinFiles.add(child.getAbsolutePath());
				} else if (!child.getName().contains(".")) {
					File subDir = child;
					pathsToHenshinFiles.addAll(inspectDirectoryForHenshinFiles(subDir));
				}
			}
		}
		return pathsToHenshinFiles;
	}

	public void setNoApplicationConditions(boolean removeAllApplicationConditions) {
		this.noApplicationConditions = removeAllApplicationConditions;
	}

	public void setNoMultirules(boolean removeAllMultirules) {
		this.removeAllMultirules = removeAllMultirules;
	}

	public void limitSetOfRulesByRuleNames(Set<String> limitedSetOfRulesByRuleNames) {
		this.limitedSetOfRulesByRuleNames = limitedSetOfRulesByRuleNames;
	}

	private int computePullback(Rule r1, Match m1, Match m2, Rule r2) {

		List<EObject> temporary = new LinkedList<EObject>();//empty
		List<EObject> r2MatchTarget = new LinkedList<EObject>();//empty
		List<EObject> result = new LinkedList<EObject>();//empty

		for (Node node : r1.getLhs().getNodes()) {
			temporary.add(m1.getNodeTarget(node));
		}
		for (Node node : r2.getLhs().getNodes()) {
			r2MatchTarget.add(m2.getNodeTarget(node));
			if (temporary.contains(m2.getNodeTarget(node)))
				result.add(node);
		}
		return result.size();
	}

	private boolean runAtomicAnalysis(Rule firstRule, Rule secondRule, Rule originalRuleOfRule2,
			List<Rule> skippedRules, AnalysisKind analysisKind, int elementsInLhsOfSecondRule) {
		boolean canceled = false;
		StringBuffer runTimesOfRuleCombination = new StringBuffer();
		StringBuffer amountOfDeleteUseConflictsOfRulecombination = new StringBuffer();

		int numberOfAnalysis = 0;
		int numberOfConflictsOverall = 0;
		String ruleCombination = firstRule.getName() + " -> " + secondRule.getName();
		long atomicStartTime = System.currentTimeMillis();

		AtomicResultContainer resultKeeper = new AtomicResultContainer(firstRule, secondRule);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		try {
			executor.submit(new CalculateAtomicCpaTask(resultKeeper)).get(15, TimeUnit.MINUTES);
		} catch (NullPointerException | InterruptedException | ExecutionException e) {
			System.err.println("Timeout!");
			executor.shutdown();
		} catch (TimeoutException e) {
			System.err.println("TIME OUT!!!");
			e.printStackTrace();
		}
//
		ConflictAnalysis atomicCoreCPA = new ConflictAnalysis(firstRule, secondRule);
		Set<ConflictAtom> computeConflictAtoms = atomicCoreCPA.computeAtoms();

		Set<Atom> atomicCoreCpaConflictAtoms = resultKeeper.getConflictAtoms();
		Set<Atom> atomicCoreCpaCandidates = resultKeeper.getCandidates();
		Set<ConflictReason> atomicCoreMinimalConflictReasons = resultKeeper.getMinimalConflictReasons();

		long atomicEndTime = System.currentTimeMillis();
		long atomiRunTime = atomicEndTime - atomicStartTime;
		totalAtomicRuntime += atomiRunTime;

		runTimesOfRuleCombination.append(String.valueOf(atomiRunTime));

		if (!ruleMetricAdded) {
			minimalReasonLogger.addData(firstRule, originalRuleOfRule2, Integer.toString(elementsInLhsOfSecondRule),
					Integer.toString(elementsInLhsOfSecondRule));
		}
		if (!canceled)
			minimalReasonLogger.addData(firstRule, originalRuleOfRule2, runTimesOfRuleCombination.toString(),
					String.valueOf(atomicCoreMinimalConflictReasons.size()));

		return canceled;
	}

	private boolean runEssentialCPA(Rule firstRule, Rule secondRule, Rule originalRuleOfRule2, List<Rule> skippedRules,
			AnalysisKind analysisKind, int elementsInLhsOfSecondRule) {

		boolean canceled = false;
		StringBuffer runTimesOfRuleCombination = new StringBuffer();
		StringBuffer amountOfDeleteUseConflictsOfRulecombination = new StringBuffer();

		long essentialStartTime = System.currentTimeMillis();
		List<Rule> firstRuleList = new LinkedList<Rule>();
		firstRuleList.add(firstRule);
		List<Rule> secondRuleList = new LinkedList<Rule>();
		secondRuleList.add(secondRule);
		CPAResult essentialResult = null;

		SingleCpaTaskResultContainer singleCpaTaskResultContainer = new SingleCpaTaskResultContainer(firstRuleList,
				secondRuleList, essentialOptions);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		try {
			executor.submit(new CalculateCpaTask(singleCpaTaskResultContainer, analysisKind)).get(10, TimeUnit.SECONDS);
		} catch (NullPointerException | InterruptedException | ExecutionException e) {
			System.err.println("Timeout!");
			executor.shutdown();
		} catch (TimeoutException e) {
			canceled = true;
			skippedRules.add(firstRule);
			skippedRules.add(originalRuleOfRule2);
			skippedRules.add(secondRule);
			System.err.println("TIME OUT!!!");
			e.printStackTrace();
		}
		long essentialEndTime = System.currentTimeMillis();

		executor.shutdown();
		essentialResult = singleCpaTaskResultContainer.getResult();

		long essentialRunTime = essentialEndTime - essentialStartTime;
		totalEssentialRuntime += essentialRunTime;

		if (essentialResult != null) {
			runTimesOfRuleCombination.append(String.valueOf(essentialRunTime));
//			runTimesOfRuleCombination.append("/");

			numberOfAllEssentialConflicts += essentialResult.getCriticalPairs().size();
			List<CriticalPair> filteredDeleteUseConflicts = filterDeleteUseConflicts(essentialResult);
			numberOfFilteredEssentialConflicts += filteredDeleteUseConflicts.size();
			System.err.println("delete-use-conflicts: " + filteredDeleteUseConflicts.size());

			totalNumberOfEssentialCPs += filteredDeleteUseConflicts.size();

			int sumOfElementsInPullback = 0;
			for (CriticalPair critPair : filteredDeleteUseConflicts) {
				Conflict confl = (Conflict) critPair;
				Rule r1 = confl.getFirstRule();
				Rule r2 = confl.getFirstRule();
				Match match1 = confl.getMatch1();
				Match match2 = confl.getMatch2();
				int elementsOfPullBack = computePullback(r1, match1, match2, r2);
				sumOfElementsInPullback += elementsOfPullBack;
			}
			amountOfDeleteUseConflictsOfRulecombination.append(sumOfElementsInPullback);

		} else {
			runTimesOfRuleCombination.append("TO");

			amountOfDeleteUseConflictsOfRulecombination.append("TO");
		}

		if (!ruleMetricAdded) {
			essentialCpaLogger.addData(firstRule, originalRuleOfRule2, Integer.toString(elementsInLhsOfSecondRule),
					Integer.toString(elementsInLhsOfSecondRule));
		}
		if (!canceled)
			essentialCpaLogger.addData(firstRule, originalRuleOfRule2, runTimesOfRuleCombination.toString(),
					amountOfDeleteUseConflictsOfRulecombination.toString());
		return canceled;
	}

	private boolean runNormalCPA(Rule firstRule, Rule secondRule, Rule originalRuleOfRule2, List<Rule> skippedRules,
			AnalysisKind analysisKind, int elementsInLhsOfSecondRule) {

		boolean canceled = false;
		StringBuffer runTimesOfRuleCombination = new StringBuffer();
		StringBuffer amountOfDeleteUseConflictsOfRulecombination = new StringBuffer();

		long normalStartTime = System.currentTimeMillis();
		List<Rule> firstRuleList = new LinkedList<Rule>();
		firstRuleList.add(firstRule);
		List<Rule> secondRuleList = new LinkedList<Rule>();
		secondRuleList.add(secondRule);
		CPAResult normalResult = null;

		SingleCpaTaskResultContainer singleCpaTaskResultContainer = new SingleCpaTaskResultContainer(firstRuleList,
				secondRuleList, normalOptions);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		try {
			executor.submit(new CalculateCpaTask(singleCpaTaskResultContainer, analysisKind)).get(10, TimeUnit.SECONDS);
		} catch (NullPointerException | InterruptedException | ExecutionException e) {
			System.err.println("Timeout!");
			executor.shutdown();
		} catch (TimeoutException e) {
			canceled = true;
			skippedRules.add(firstRule);
			skippedRules.add(originalRuleOfRule2);
			skippedRules.add(secondRule);
			System.err.println("TIME OUT!!!");
			e.printStackTrace();
		}

		executor.shutdown();
		normalResult = singleCpaTaskResultContainer.getResult();

		long normalRunTime = singleCpaTaskResultContainer.getAnalysisDuration();

		totalNormalRuntime += normalRunTime;

		if (normalResult != null) {
			runTimesOfRuleCombination.append(String.valueOf(normalRunTime));

			List<CriticalPair> filteredDeleteUseConflicts = filterDeleteUseConflicts(normalResult);

			totalNumberOfNormalCPs += filteredDeleteUseConflicts.size();

			int sumOfElementsInPullback = 0;
			for (CriticalPair critPair : filteredDeleteUseConflicts) {
				Conflict confl = (Conflict) critPair;
				Rule r1 = confl.getFirstRule();
				Rule r2 = confl.getFirstRule();
				Match match1 = confl.getMatch1();
				Match match2 = confl.getMatch2();
				int elementsOfPullBack = computePullback(r1, match1, match2, r2);
				sumOfElementsInPullback += elementsOfPullBack;
			}
			amountOfDeleteUseConflictsOfRulecombination.append(sumOfElementsInPullback);

		} else {
			System.err.println("normal CPA failed!");

			runTimesOfRuleCombination.append("TO");

			amountOfDeleteUseConflictsOfRulecombination.append("TO");
		}

		if (!ruleMetricAdded) {
			normalCpaLogger.addData(firstRule, originalRuleOfRule2, Integer.toString(elementsInLhsOfSecondRule),
					Integer.toString(elementsInLhsOfSecondRule));
		}
		if (!canceled)
			normalCpaLogger.addData(firstRule, originalRuleOfRule2, runTimesOfRuleCombination.toString(),
					amountOfDeleteUseConflictsOfRulecombination.toString());
		return canceled;
	}
}
