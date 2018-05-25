package org.eclipse.emf.henshin.multicda.cda.runner;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
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
import org.eclipse.emf.henshin.multicda.cda.compareLogger.Logger2;
import org.eclipse.emf.henshin.multicda.cda.tasks.AtomicResultContainer;
import org.eclipse.emf.henshin.multicda.cda.tasks.CalculateAtomicCpaTask;
import org.eclipse.emf.henshin.multicda.cda.tasks.CalculateCpaTask;
import org.eclipse.emf.henshin.multicda.cda.tasks.CalculateCpaTask.AnalysisKind;
import org.eclipse.emf.henshin.multicda.cda.tasks.SingleCpaTaskResultContainer;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalConflict;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cda.units.Span;
import org.eclipse.emf.henshin.multicda.cpa.CDAOptions;
import org.eclipse.emf.henshin.multicda.cpa.result.CPAResult;
import org.eclipse.emf.henshin.multicda.cpa.result.Conflict;
import org.eclipse.emf.henshin.multicda.cpa.result.ConflictKind;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;
import org.eclipse.emf.henshin.multicda.cpa.result.Dependency;
import org.eclipse.emf.henshin.multicda.cpa.result.DependencyKind;

public class Runner {

// options to turn on and off different analyses
	boolean runConflictAnalysis = true;
	boolean runDependencyAnalysis = true;
	boolean runEssentialConflictAnalysis = true;
	boolean runEssentialDependencyAnalysis = true;
	boolean runAtomicConflictAnalysis = true;
	boolean runAtomicDependencyAnalysis = true;

	private boolean noApplicationConditions = false;
	private boolean removeAllMultirules = false;
//	private Set<String> limitedSetOfRulesByRuleNames;

	/** Vorhalten der ermittelten Ergebisse: */
	CPAResult normalCpaResults;

	/**
	 * @return the normalCpaResults
	 */
	public CPAResult getNormalCpaResults() {
		return normalCpaResults;
	}

	/**
	 * @return the essentialCpaResults
	 */
	public CPAResult getEssentialCpaResults() {
		return essentialCpaResults;
	}

	/**
	 * @return the minimalConflicts
	 */
	public List<MinimalConflict> getMinimalConflicts() {
		return minimalConflicts;
	}

	CPAResult essentialCpaResults;
	List<MinimalConflict> minimalConflicts; //Perhaps it should be MinimalConflictReason? Because Minimal Conflict is a Critical Pair and not Conflict Reason

	public void run(String fullSubDirectoryPath, List<String> deactivatedRules) {

		File dir = new File(fullSubDirectoryPath);
		List<String> pathsToHenshinFiles = inspectDirectoryForHenshinFiles(dir);

		run(pathsToHenshinFiles, fullSubDirectoryPath, deactivatedRules);
	}

	public void run(List<String> pathsToHenshinFiles, String resultPath, List<String> deactivatedRules) {
		deactivatedRules.clear();
		List<Rule> allLoadedRules = loadAllRulesFromFileSystemPaths(pathsToHenshinFiles, deactivatedRules);

		// initialisieren der Ergebnisspeicher:
		normalCpaResults = new CPAResult();
		essentialCpaResults = new CPAResult();
		minimalConflicts = new LinkedList<MinimalConflict>();

		// fix inconsistent dangling options : all rules shall "check dangling"
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

		// aufgrund der Erkenntnis, dass die nutzende (use) Regel keine löschenden Teile haben darf 
		// wird zu jeder zu analysierenden Regel eine angepasste Regel als zweite nutzende (use) Regel erstellt
		List<Rule> copiesOfRulesWithoutDeletion = buildCopiesOfRulesWithoutDeletion(allLoadedRules);
//		List<Rule> copiesOfRulesWithoutDeletion = allLoadedRules;

		List<Logger2> loggers = new LinkedList<Logger2>();
		Logger2 deleteUseLogger = new Logger2(Logger2.LogData.DELTE_USE_CONFLICTS, allLoadedRules);
		loggers.add(deleteUseLogger);
		Logger2 produceUseLogger = new Logger2(Logger2.LogData.PRODUCE_USE_DEPENDENCY, allLoadedRules);
		loggers.add(produceUseLogger);

		Logger2 essentialDeleteUseLogger = new Logger2(Logger2.LogData.ESSENTIAL_DELTE_USE_CONFLICTS, allLoadedRules);
		loggers.add(essentialDeleteUseLogger);
		Logger2 essentialProduceUseLogger = new Logger2(Logger2.LogData.ESSENTIAL_PRODUCE_USE_DEPENDENCY,
				allLoadedRules);
		loggers.add(essentialProduceUseLogger);

		Logger2 conflictAtomCandidateLogger = new Logger2(Logger2.LogData.CONFLICT_CANDIDATE, allLoadedRules);
		loggers.add(conflictAtomCandidateLogger);
		Logger2 conflictAtomLogger = new Logger2(Logger2.LogData.CONFLICT_ATOM, allLoadedRules);
		loggers.add(conflictAtomLogger);
		Logger2 minimalConflictReasonLogger = new Logger2(Logger2.LogData.MINIMAL_CONFLICT_REASON, allLoadedRules);
		loggers.add(minimalConflictReasonLogger);
		Logger2 conflictReasonLogger = new Logger2(Logger2.LogData.CONFLICT_REASON, allLoadedRules);
		loggers.add(conflictReasonLogger);

		Logger2 dependencyAtomCandidateLogger = new Logger2(Logger2.LogData.DEPENDENCY_CANDIDATE, allLoadedRules);
		loggers.add(dependencyAtomCandidateLogger);
		Logger2 dependencyAtomLogger = new Logger2(Logger2.LogData.DEPENDENCY_ATOM, allLoadedRules);
		loggers.add(dependencyAtomLogger);
		Logger2 minimalDependencyReasonLogger = new Logger2(Logger2.LogData.MINIMAL_DEPENDENCY_REASON, allLoadedRules);
		loggers.add(minimalDependencyReasonLogger);
		Logger2 dependencyReasonLogger = new Logger2(Logger2.LogData.DEPENDENCY_REASON, allLoadedRules);
		loggers.add(dependencyReasonLogger);

//		Namen müssen dabei adaptiert werden, sonst ist nach dem Export bei der Ansteuerung von agg nicht klar welche Regel die erste und welche die zweite ist.
		// dennoch kann (sollte!) beim loggen der Ergebnisse wieder der original name verwendet werden - DONE!

		int numberOfAddedRules = allLoadedRules.size();

		// normal CPA setup
		CDAOptions normalOptions = new CDAOptions();

		// essential CPA setup
		CDAOptions essentialOptions = new CDAOptions();
		essentialOptions.essentialCP = true;

		int numberOfAllConflictAtoms = 0;
		int numberOfAllMinimalConflictReasons = 0;

		long totalAtomicRuntime = 0;

//		int totalNumberOfEssentialCPs = 0;
		int totalNumberOfAtomicCPs = 0;
		int totalNumberOfConflictAtomCandidates = 0;
		int totalNumberOfMinimalConflictReasons = 0;

		int currentRow = 0;
		int firstRowToAnalyse = 0;
		int lastRowToAnalyse = 70;

		List<Rule> skippedRules = new LinkedList<Rule>();

		System.err.println("number of loaded rules: " + allLoadedRules.size());

		for (Rule firstRule : allLoadedRules) {

			if (skippedRules.contains(firstRule))
				System.err.println("skipping row for rule: " + firstRule.getName());
			if (!skippedRules.contains(firstRule)) {

//				if(ruleIsntLimited(firstRule)){

				if (currentRow >= firstRowToAnalyse && currentRow <= lastRowToAnalyse) {

					//METRICS!

					Graph lhsOfFirstRule = firstRule.getLhs();

					int elementsInLhsOfFirstRule = lhsOfFirstRule.getNodes().size() + lhsOfFirstRule.getEdges().size();
					//sum up the details!
//						logger.addData(firstRule, null, Integer.toString(elementsInLhsOfFirstRule),Integer.toString(elementsInLhsOfFirstRule));
					// (14.04.2017) Das initialisieren mit "null" ist eine schlechte Herangehensweise und wird auf diese Art nciht merh gemacht! 
//						for(Logger logger : loggers){
//							logger.addData(firstRule, null, Integer.toString(elementsInLhsOfFirstRule),Integer.toString(elementsInLhsOfFirstRule));
//						}
					// (14.04.2017) Hier kein berechnen und hinzufügen irgendwelcher Regelmetriken (rule metric)
//						boolean ruleMetricAdded = false;

					System.err
							.println("number of copiesOfRulesWithoutDeletion: " + copiesOfRulesWithoutDeletion.size());
					for (Rule secondRule : copiesOfRulesWithoutDeletion) {

						Rule originalRuleOfRule2 = allLoadedRules.get(copiesOfRulesWithoutDeletion.indexOf(secondRule));
						Graph lhsOfOriginalRuleOfRule2 = originalRuleOfRule2.getLhs();

						int elementsInLhsOfSecondRule = lhsOfOriginalRuleOfRule2.getNodes().size()
								+ lhsOfOriginalRuleOfRule2.getEdges().size();

						if (skippedRules.contains(secondRule) || skippedRules.contains(originalRuleOfRule2))
							System.err.println("skipping column for rule: " + secondRule.getName());
						if (!skippedRules.contains(secondRule) && !skippedRules.contains(originalRuleOfRule2)) {

//								if(ruleIsntLimited(secondRule)){

//									StringBuffer runTimesOfRuleCombination = new StringBuffer();
//									StringBuffer amountOfDeleteUseConflictsOfRulecombination = new StringBuffer();

							boolean canceled = false;

							if (runConflictAnalysis) {

								canceled = runCPA(deleteUseLogger, normalOptions, skippedRules, firstRule, secondRule,
										originalRuleOfRule2, canceled);
							}

							if (runDependencyAnalysis) {
								canceled = runCPA(produceUseLogger, normalOptions, skippedRules, firstRule, secondRule,
										originalRuleOfRule2, canceled);
							}

							if (runEssentialConflictAnalysis && !canceled) {

								canceled = runEssCPA(essentialDeleteUseLogger, essentialOptions, skippedRules,
										firstRule, secondRule, originalRuleOfRule2, canceled);
							}

							if (runEssentialDependencyAnalysis && !canceled) {

								canceled = runEssCPA(essentialProduceUseLogger, essentialOptions, skippedRules,
										firstRule, secondRule, originalRuleOfRule2, canceled);
							}

							//hier fehlt ncoh der entsprechende Teil für die AtomiDependencyAnalyse!
							if (runAtomicConflictAnalysis) {

								if (firstRule.getName().contains("28085"))
									System.out.println("28085");
								if (firstRule.getName().contains("28086"))
									System.out.println("28086");

//										StringBuffer runTimesOfRuleCombination = new StringBuffer();
								StringBuffer amountOfDeleteUseConflictsOfRulecombination = new StringBuffer();

								// List<String> shortResults = new LinkedList<String>();
								int numberOfAnalysis = 0;
								int numberOfConflictsOverall = 0;
								String ruleCombination = firstRule.getName() + " -> " + secondRule.getName();
//						System.out.println("start combination: " + ruleCombination);
								long atomicStartTime = System.currentTimeMillis();
//						List<ConflictAtom> computeConflictAtoms = atomicCoreCPA.computeConflictAtoms(firstRule, secondRule);

								AtomicResultContainer resultKeeper = new AtomicResultContainer(firstRule, secondRule);
								ExecutorService executor = Executors.newSingleThreadExecutor();
								try {
									executor.submit(new CalculateAtomicCpaTask(resultKeeper)).get(1, TimeUnit.MINUTES);
								} catch (NullPointerException | InterruptedException | ExecutionException e) {
									System.err.println("Timeout!");
									executor.shutdown();
								} catch (TimeoutException e) {
									System.err.println("TIME OUT!!!");
									e.printStackTrace();
								}
//								
//										AtomicCoreCPA atomicCoreCPA = new AtomicCoreCPA();
//										List<ConflictAtom> computeConflictAtoms = atomicCoreCPA.computeConflictAtoms(firstRule, secondRule);

								Set<Atom> atomicCoreCpaConflictAtoms = resultKeeper.getConflictAtoms();
								Set<Atom> atomicCoreCpaCandidates = resultKeeper.getCandidates();
								Set<MinimalReason> atomicCoreMinimalConflictReasons = resultKeeper
										.getMinimalConflictReasons();
								Set<Reason> atomicCoreConflictReasons = resultKeeper.getConflictReasons();

//										long atomicEndTime = System.currentTimeMillis();
//										long atomiRunTime = atomicEndTime - atomicStartTime;
//										totalAtomicRuntime += atomiRunTime;

//										runTimesOfRuleCombination.append(String.valueOf(atomiRunTime));

								if (atomicCoreCpaConflictAtoms == null || atomicCoreMinimalConflictReasons == null)
									System.out.println("WTF!?!");
								if (atomicCoreCpaConflictAtoms.size() > 0) {
									String quickAtomicResultSummary = "" + atomicCoreCpaConflictAtoms.size() + "CA - "
											+ atomicCoreMinimalConflictReasons.size() + " MCR - " + ruleCombination;
									System.out.println("quickAtomicResultSummary: " + quickAtomicResultSummary);
								}

								numberOfAllConflictAtoms += atomicCoreCpaConflictAtoms.size();
								numberOfAllMinimalConflictReasons += atomicCoreMinimalConflictReasons.size();

//								System.out.println("executed: " + ruleCombination + " del-use-confl: " + atomicCoreCpaConflictAtoms.size()
//								+ " in " + atomiRunTime + " ms");
//								numberOfAnalysis++;
//								System.err.println("number of analysisDuration: " + numberOfAnalysis);
//								// shortResults.add(computeConflictAtoms.size()+" conflicts in "+ruleCombination);
//								numberOfConflictsOverall += atomicCoreCpaConflictAtoms.size();
//								
//										amountOfDeleteUseConflictsOfRulecombination.append(String.valueOf(atomicCoreCpaConflictAtoms.size()));
//										amountOfDeleteUseConflictsOfRulecombination.append(",");
//										amountOfDeleteUseConflictsOfRulecombination.append(String.valueOf(atomicCoreCpaCandidates.size()));
//										amountOfDeleteUseConflictsOfRulecombination.append(",");
//										amountOfDeleteUseConflictsOfRulecombination.append(String.valueOf(atomicCoreCpaOverallReasons.size()));
//								
//								totalNumberOfAtomicCPs += atomicCoreCpaConflictAtoms.size();
//								totalNumberOfConflictAtomCandidates += atomicCoreCpaCandidates.size();
//								totalNumberOfMinimalConflictReasons += atomicCoreCpaOverallReasons.size();

								// relies on equal order of original rule and associated copy without deletion! 
//									Rule originalRuleOfRule2 = allLoadedRules.get(copiesOfRulesWithoutDeletion.indexOf(secondRule));

								// METRICS ->
//							ruleMetrics = ruleSetMetricsCalculator.computeMetrics(originalRuleOfRule2);
//							numberOfNodesAndEdges = ruleMetrics.getNumberOfEdges();
//							numberOfNodesAndEdges += ruleMetrics.getNumberOfNodes();

								// (14.04.2017) Hier kein berechnen und hinzufügen irgendwelcher Regelmetriken (rule metric)
//										if(!ruleMetricAdded){
//											conflictAtomLogger.addData(firstRule, originalRuleOfRule2, Integer.toString(elementsInLhsOfSecondRule),Integer.toString(elementsInLhsOfSecondRule));
//										}
								if (!canceled) {
									conflictAtomLogger.addData(firstRule, originalRuleOfRule2,
											String.valueOf(resultKeeper.getRunTimeOfMinimalConflictReasons()),
											String.valueOf(atomicCoreCpaConflictAtoms.size()));
								}

								// (14.04.2017) Hier kein berechnen und hinzufügen irgendwelcher Regelmetriken (rule metric)
//										if(!ruleMetricAdded){
//											candidatesLogger.addData(firstRule, originalRuleOfRule2, Integer.toString(elementsInLhsOfSecondRule),Integer.toString(elementsInLhsOfSecondRule));
//										}
								if (!canceled) {
									conflictAtomCandidateLogger.addData(firstRule, originalRuleOfRule2,
											String.valueOf(resultKeeper.getRunTimeOfMinimalConflictReasons()),
											String.valueOf(atomicCoreCpaCandidates.size()));
								}

								// (14.04.2017) Hier kein berechnen und hinzufügen irgendwelcher Regelmetriken (rule metric)
//										if(!ruleMetricAdded){
//											minimalReasonLogger.addData(firstRule, originalRuleOfRule2, Integer.toString(elementsInLhsOfSecondRule),Integer.toString(elementsInLhsOfSecondRule));
//										}
								if (!canceled)
									minimalConflictReasonLogger.addData(firstRule, originalRuleOfRule2,
											String.valueOf(resultKeeper.getRunTimeOfMinimalConflictReasons()),
											String.valueOf(atomicCoreMinimalConflictReasons.size()));

								for (Span minimalConflictReason : resultKeeper.getMinimalConflictReasons()) {
									//(11.04.2017): es sollten nur die relevanten 'conflictAtoms' und 'conflictAtomCandidates' zum 'minimalConflictReason' übergeben werden. Wie lassen sich diese abrufen/identifizieren???^
//									MinimalConflict minimalConflict = new MinimalConflict(firstRule,
//											originalRuleOfRule2, minimalConflictReason, resultKeeper.getConflictAtoms(),
//											resultKeeper.getCandidates()); //TODO: Which analysis kind is running? Essential or Complete? Every Critical Pair should know which Analysis he created from.
//									minimalConflicts.add(minimalConflict);
								}

								if (!canceled)
									conflictReasonLogger.addData(firstRule, originalRuleOfRule2,
											String.valueOf(resultKeeper.getConflictReasonOverallTime()),
											String.valueOf(atomicCoreConflictReasons.size()));
							}

						}
					}
					System.gc();
				}
			}

			currentRow++;

		}

		System.err.println("-------------------------");
		System.err.println(
				"number of all essential Delete-Use-Conflicts: " + essentialDeleteUseLogger.getTotalResultAmount());
		System.err.println(
				"runtime for all essential Delete-Use-Conflicts: " + essentialDeleteUseLogger.getTotalRuntimeAmount());
		System.err.println("-------------------------");
		System.err.println("numberOfAllConflictAtoms: " + numberOfAllConflictAtoms);
		System.err.println("numberOfAllMinimalConflictReasons: " + numberOfAllMinimalConflictReasons);
		System.err.println("-------------------------");
		System.out.println("HALT");

		DateFormat dateFormat = new SimpleDateFormat("yy_MM_dd-HHmmss");
		Date start = new Date();
		String targetFolder = resultPath + File.separator + dateFormat.format(start) + File.separator;
		File theDir = new File(targetFolder);
		if (!theDir.exists())
			theDir.mkdir();

		for (Logger2 logger : loggers) {
			logger.exportStoredRuntimeToCSV(targetFolder);
			logger.exportStoredResultsToCSV(targetFolder);
		}
	}

	private boolean runEssCPA(Logger2 essCpaLogger, CDAOptions essentialCpaOptions, List<Rule> skippedRules,
			Rule firstRule, Rule secondRule, Rule originalSecondRule, boolean canceled) {

		String runTimesOfRuleCombination = "";
		String amountOfDeleteUseConflictsOfRulecombination = "";

		long essentialStartTime = System.currentTimeMillis();
		List<Rule> firstRuleList = new LinkedList<Rule>();
		firstRuleList.add(firstRule);
		List<Rule> secondRuleList = new LinkedList<Rule>();
		secondRuleList.add(secondRule);
		;
		CPAResult essentialResult = null;

		AnalysisKind analysisKind;
		if (essCpaLogger.getLogData() == Logger2.LogData.ESSENTIAL_DELTE_USE_CONFLICTS) {
			analysisKind = CalculateCpaTask.AnalysisKind.CONFLICT;
		} else if (essCpaLogger.getLogData() == Logger2.LogData.ESSENTIAL_PRODUCE_USE_DEPENDENCY) {
			analysisKind = CalculateCpaTask.AnalysisKind.DEPENDENCY;
		} else {
			System.err.println("invalid configuration");
			return true;
		}

		SingleCpaTaskResultContainer singleCpaTaskResultContainer = new SingleCpaTaskResultContainer(firstRuleList,
				secondRuleList, essentialCpaOptions);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		try {
			executor.submit(new CalculateCpaTask(singleCpaTaskResultContainer, analysisKind)).get(1, TimeUnit.MINUTES);
		} catch (NullPointerException | InterruptedException | ExecutionException e) {
			System.err.println("Timeout!");
			executor.shutdown();
		} catch (TimeoutException e) {
			canceled = true;
			skippedRules.add(firstRule);
			skippedRules.add(originalSecondRule);
			skippedRules.add(secondRule);
			System.err.println("TIME OUT!!!");
			e.printStackTrace();
		}
		long essentialEndTime = System.currentTimeMillis();

		executor.shutdown();
		essentialResult = singleCpaTaskResultContainer.getResult();

		long essentialRunTime = essentialEndTime - essentialStartTime;

		if (essentialResult != null) {
			runTimesOfRuleCombination = String.valueOf(essentialRunTime);

			List<CriticalPair> filteredDeleteUseConflicts = filterCriticalPairs(essentialResult, analysisKind);
			System.err.println("delete-use-conflicts: " + filteredDeleteUseConflicts.size());

			amountOfDeleteUseConflictsOfRulecombination = String.valueOf(filteredDeleteUseConflicts.size());
		} else {
			runTimesOfRuleCombination = "TO";
			amountOfDeleteUseConflictsOfRulecombination = "TO";
		}

		if (!canceled) {
			essCpaLogger.addData(firstRule, originalSecondRule, runTimesOfRuleCombination.toString(),
					amountOfDeleteUseConflictsOfRulecombination.toString());
			for (CriticalPair cp : essentialResult.getCriticalPairs()) {
				essentialCpaResults.addResult(cp);
			}
		}
		return canceled;
	}

	private boolean runCPA(Logger2 cpaLogger, CDAOptions cpaOptions, List<Rule> skippedRules, Rule firstRule,
			Rule secondRule, Rule originalRuleOfRule2, boolean canceled) {
		String runTimeOfRuleCombination = "";
		String amountOfDeleteUseConflictsOfRulecombination = "";

		List<Rule> firstRuleList = new LinkedList<Rule>();
		firstRuleList.add(firstRule);
		List<Rule> secondRuleList = new LinkedList<Rule>();
		secondRuleList.add(secondRule);
		CPAResult normalResult = null;

		SingleCpaTaskResultContainer singleCpaTaskResultContainer = new SingleCpaTaskResultContainer(firstRuleList,
				secondRuleList, cpaOptions);
		ExecutorService executor = Executors.newSingleThreadExecutor();

		AnalysisKind analysisKind;
		if (cpaLogger.getLogData() == Logger2.LogData.DELTE_USE_CONFLICTS) {
			analysisKind = CalculateCpaTask.AnalysisKind.CONFLICT;
		} else if (cpaLogger.getLogData() == Logger2.LogData.PRODUCE_USE_DEPENDENCY) {
			analysisKind = CalculateCpaTask.AnalysisKind.DEPENDENCY;
		} else {
			System.err.println("invalid configuration");
			return true;
		}

		try {
			executor.submit(new CalculateCpaTask(singleCpaTaskResultContainer, analysisKind)).get(20, TimeUnit.SECONDS);
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

		if (normalResult != null) {
			long normalRunTime = singleCpaTaskResultContainer.getAnalysisDuration();
			runTimeOfRuleCombination = String.valueOf(normalRunTime);

			List<CriticalPair> filteredDeleteUseConflicts = filterCriticalPairs(normalResult, analysisKind);
			amountOfDeleteUseConflictsOfRulecombination = String.valueOf(filteredDeleteUseConflicts.size());
		} else {
			System.err.println("normal CPA failed!");
			runTimeOfRuleCombination = "TO";
			amountOfDeleteUseConflictsOfRulecombination = "TO";
		}
		if (!canceled)
			cpaLogger.addData(firstRule, originalRuleOfRule2, runTimeOfRuleCombination,
					amountOfDeleteUseConflictsOfRulecombination.toString());

		for (CriticalPair cp : normalResult.getCriticalPairs()) {
			normalCpaResults.addResult(cp);
		}
		return canceled;
	}

	private List<Rule> buildCopiesOfRulesWithoutDeletion(List<Rule> allLoadedRules) {
		HenshinFactory henshinFactory = new HenshinFactoryImpl();
		List<Rule> copiesOfRulesWithoutDeletion = new LinkedList<>();
		for (Rule ruleToCopy : allLoadedRules) {
			Copier copierForRule = new Copier();
			Rule copyOfRule = (Rule) copierForRule.copy(ruleToCopy);
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

	public void setAnalysisKinds(boolean cpaConflictAnalysis, boolean cpaDependencyAnalysis,
			boolean essentialConflictAnalysis, boolean essentialDependencyAnalysis, boolean atomicConflictAnalysis,
			boolean atomicDependencyAnalysis) {
		this.runConflictAnalysis = cpaConflictAnalysis;
		this.runDependencyAnalysis = cpaDependencyAnalysis;
		this.runEssentialConflictAnalysis = essentialConflictAnalysis;
		this.runEssentialDependencyAnalysis = essentialDependencyAnalysis;
		this.runAtomicConflictAnalysis = atomicConflictAnalysis;
		this.runAtomicDependencyAnalysis = atomicDependencyAnalysis;
	}

	public static List<Rule> loadAllRulesFromFileSystemPaths(List<String> pathsToHenshinFiles,
			List<String> namesOfDeactivatedRules) {
		List<Rule> allEditRulesWithoutAmalgamation = new LinkedList<Rule>();

		for (String pathToHenshinFiles : pathsToHenshinFiles) {
			HenshinResourceSet henshinResourceSet = new HenshinResourceSet();
			Module module = henshinResourceSet.getModule(pathToHenshinFiles);
			for (Unit unit : module.getUnits()) {
				if (unit instanceof Rule /* && numberOfAddedRules<10 */) {
					// rulesAndAssociatedFileNames.put((Rule) unit, fileName);
					boolean deactivatedRule = false;
					for (String deactivatedRuleName : namesOfDeactivatedRules) {
						if (unit.getName().contains(deactivatedRuleName))
							deactivatedRule = true;
					}
					if (!deactivatedRule) {
						allEditRulesWithoutAmalgamation.add((Rule) unit);
					}
				}
			}
		}
		return allEditRulesWithoutAmalgamation;
	}

	public static List<CriticalPair> filterCriticalPairs(CPAResult cpaResult, AnalysisKind analysisKind) {
		// filter delete-use conflicts:
		if (cpaResult != null) {
			List<CriticalPair> criticalPairs = cpaResult.getCriticalPairs();
			// System.out.println("number of essential CPs: "+criticalPairs.size());
			List<CriticalPair> filteredCriticalPairs = new LinkedList<CriticalPair>();
			for (CriticalPair cp : criticalPairs) {
				if (cp instanceof Conflict && analysisKind == AnalysisKind.CONFLICT) {
					if (((Conflict) cp).getConflictKind().equals(ConflictKind.DELETE_USE_CONFLICT)) {
						filteredCriticalPairs.add(cp);
					}
				} else if (cp instanceof Dependency && analysisKind == AnalysisKind.DEPENDENCY) {
					if (((Dependency) cp).getDependencyKind().equals(DependencyKind.PRODUCE_USE_DEPENDENCY)) {
						filteredCriticalPairs.add(cp);
					}
				}
			}
			return filteredCriticalPairs;
		}
		return new LinkedList<CriticalPair>();
	}

	public static List<String> inspectDirectoryForHenshinFiles(File dir) {
		List<String> pathsToHenshinFiles = new LinkedList<String>();
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
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

}
