package org.eclipse.emf.henshin.multicda.cda.runner.overapproximation;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.compareLogger.Logger2;
import org.eclipse.emf.henshin.multicda.cda.runner.Runner;
import org.eclipse.emf.henshin.multicda.cda.tasks.CalculateCpaTask;
import org.eclipse.emf.henshin.multicda.cda.tasks.CalculateCpaTask.AnalysisKind;
import org.eclipse.emf.henshin.multicda.cda.tasks.SingleCpaTaskResultContainer;
import org.eclipse.emf.henshin.multicda.cpa.CDAOptions;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;

public class EssentialCpaPreserveOverapproximationUtil {

	public static void analyseEssentialCpaPreserveOverapproximation(List<Rule> originalRules, List<Rule> modifiedRules,
			String resultPath) {
		CDAOptions essentialCpaOptions = new CDAOptions();
		essentialCpaOptions.essentialCP = true;

		Logger2 normalLogger = new Logger2(Logger2.LogData.DELTE_USE_CONFLICTS, originalRules);

		for (Rule firstRule : originalRules) {

			for (Rule secondRule : originalRules) {

				List<Rule> firstRuleList = new LinkedList<Rule>();
				firstRuleList.add(firstRule);
				List<Rule> secondRuleList = new LinkedList<Rule>();
				secondRuleList.add(secondRule);

				SingleCpaTaskResultContainer singleCpaTaskResultContainer = new SingleCpaTaskResultContainer(
						firstRuleList, secondRuleList, essentialCpaOptions);
				ExecutorService executor = Executors.newSingleThreadExecutor();
				try {
					executor.submit(new CalculateCpaTask(singleCpaTaskResultContainer, AnalysisKind.CONFLICT))
							.get(Long.MAX_VALUE, TimeUnit.MINUTES);
				} catch (NullPointerException | InterruptedException | ExecutionException e) {
					System.err.println("Timeout!");
					executor.shutdown();
				} catch (TimeoutException e) {
					System.err.println("TIME OUT!!!");
					e.printStackTrace();
				}

				String runTimesOfRuleCombination = String.valueOf(singleCpaTaskResultContainer.getAnalysisDuration());
				List<CriticalPair> filteredDeleteUseConflicts = Runner
						.filterCriticalPairs(singleCpaTaskResultContainer.getResult(), AnalysisKind.CONFLICT);
				String amountOfDeleteUseConflictsOfRuleCombination = String.valueOf(filteredDeleteUseConflicts.size());

				normalLogger.addData(firstRule, secondRule, runTimesOfRuleCombination,
						amountOfDeleteUseConflictsOfRuleCombination);

				executor.shutdown();
				System.gc();
			}
		}

		Logger2 modifiedLogger = new Logger2(Logger2.LogData.DELTE_USE_CONFLICTS, originalRules, modifiedRules);

		int overallModifiedResults = 0;

		for (Rule firstRule : originalRules) {

			for (Rule secondRule : modifiedRules) {

				List<Rule> firstRuleList = new LinkedList<Rule>();
				firstRuleList.add(firstRule);
				List<Rule> secondRuleList = new LinkedList<Rule>();
				secondRuleList.add(secondRule);

				SingleCpaTaskResultContainer singleCpaTaskResultContainer = new SingleCpaTaskResultContainer(
						firstRuleList, secondRuleList, essentialCpaOptions);
				ExecutorService executor = Executors.newSingleThreadExecutor();
				try {
					executor.submit(new CalculateCpaTask(singleCpaTaskResultContainer, AnalysisKind.CONFLICT))
							.get(Long.MAX_VALUE, TimeUnit.MINUTES);
				} catch (NullPointerException | InterruptedException | ExecutionException e) {
					System.err.println("Timeout!");
					executor.shutdown();
				} catch (TimeoutException e) {
					System.err.println("TIME OUT!!!");
					e.printStackTrace();
				}

				String runTimesOfRuleCombination = String.valueOf(singleCpaTaskResultContainer.getAnalysisDuration());
				List<CriticalPair> filteredDeleteUseConflicts = Runner
						.filterCriticalPairs(singleCpaTaskResultContainer.getResult(), AnalysisKind.CONFLICT);
				String amountOfDeleteUseConflictsOfRuleCombination = String.valueOf(filteredDeleteUseConflicts.size());

				modifiedLogger.addData(firstRule, secondRule, runTimesOfRuleCombination,
						amountOfDeleteUseConflictsOfRuleCombination);

				executor.shutdown();
				System.gc();

				overallModifiedResults += filteredDeleteUseConflicts.size();
			}
		}

		System.err.println("OVERALL_MODIFIED RESULTS: " + overallModifiedResults);

		System.err.println("ANALYSIS DONE!!!");

//		File xlsResultFile = new File(resultPath + "overapprBasedOnEssCPA.xls");//xlsResultFile.toPath();
//
//		XlsOverapproximationWriter xlsOverapproximationWriter = new XlsOverapproximationWriter();
//		xlsOverapproximationWriter.export(normalLogger, modifiedLogger, xlsResultFile);
	}
}
