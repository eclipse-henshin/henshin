package org.eclipse.emf.henshin.multicda.cda.tasks;

import java.util.List;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cpa.CDAOptions;
import org.eclipse.emf.henshin.multicda.cpa.result.CPAResult;

public class SingleCpaTaskResultContainer {
	
	List<Rule> firstRuleList;
	List<Rule> secondRuleList; 
	CDAOptions normalOptions;
	
	CPAResult cpaResult;
	
	long analysisDuration;

	public SingleCpaTaskResultContainer(List<Rule> firstRuleList, List<Rule> secondRuleList, CDAOptions cpaOptions) {
		this.firstRuleList = firstRuleList;
		this.secondRuleList = secondRuleList;
		this.normalOptions = cpaOptions;
	}

	public List<Rule> getFirstRuleList() {
		return firstRuleList;
	}

	public List<Rule> getSecondRuleList() {
		return secondRuleList;
	}

	public CDAOptions getCpaOptions() {
		return normalOptions;
	}

//	public void addResult(CPAResult normalResult) {
//		this.cpaResult = normalResult;
//	}

	public CPAResult getResult() {
		return cpaResult;
	}

//	public void setCalculationTime(long conflictAtomRunTime) {
//		this.analysisDuration = conflictAtomRunTime;
//	}

	public long getAnalysisDuration() {
		return analysisDuration;
	}

	public void setResult(CPAResult cpaResult, long analysisDuration) {
		this.cpaResult = cpaResult;
		this.analysisDuration = analysisDuration;
	}

}
