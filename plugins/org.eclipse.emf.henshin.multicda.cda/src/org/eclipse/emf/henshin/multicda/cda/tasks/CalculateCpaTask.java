package org.eclipse.emf.henshin.multicda.cda.tasks;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cpa.CDAOptions;
import org.eclipse.emf.henshin.multicda.cpa.CpaByAGG;
import org.eclipse.emf.henshin.multicda.cpa.ICriticalPairAnalysis;
import org.eclipse.emf.henshin.multicda.cpa.result.CPAResult;

public class CalculateCpaTask implements Callable<CPAResult> {
	
	public enum AnalysisKind{
		CONFLICT,
		DEPENDENCY
	}

	List<Rule> firstRuleList;
	List<Rule> secondRuleList; 
	CDAOptions cpaOptions;
	
	AnalysisKind analysisKind;
	
	ICriticalPairAnalysis criticalPairAnalysis;
	
	long normalRunTime;
	
	SingleCpaTaskResultContainer taskResultContainer;
	
	public CalculateCpaTask(SingleCpaTaskResultContainer taskResultContainer, AnalysisKind analysisKind) {
		this.taskResultContainer = taskResultContainer;
		this.analysisKind = analysisKind;
		
		this.firstRuleList = taskResultContainer.getFirstRuleList();
		this.secondRuleList = taskResultContainer.getSecondRuleList();
		this.cpaOptions = taskResultContainer.getCpaOptions();	
		

		// normal CPA setup
		criticalPairAnalysis = new CpaByAGG();
	}

	@Override
	public CPAResult call() throws Exception {
//		System.out.println("CALLL!");C

		CPAResult cpaResult = null;

		long normalStartTime = System.currentTimeMillis();
		try {
			criticalPairAnalysis.init(new HashSet<>(firstRuleList), new HashSet<>(secondRuleList), cpaOptions);
			if(analysisKind == AnalysisKind.CONFLICT){
				cpaResult = criticalPairAnalysis.runConflictAnalysis();
			}else {

				cpaResult = criticalPairAnalysis.runDependencyAnalysis();
			}	
		} catch (Exception /*UnsupportedRuleException*/ e) {
			e.printStackTrace();
		}
		
		long cpaEndTime = System.currentTimeMillis();
		normalRunTime = cpaEndTime - normalStartTime;
		
		//Fehlerbehandlung für den Fall einer Exception einführen! 
		taskResultContainer.setResult(cpaResult, normalRunTime);
		
		return cpaResult;
	}

}
