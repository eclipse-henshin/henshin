package org.eclipse.emf.henshin.multicda.cda.runner.pullback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class EssentialCpaLoggerPB extends LoggerPB{

	private static DateFormat dateFormat = new SimpleDateFormat("yy_MM_dd-HHmmss");
	
	// access: analysisDurations[row][column] - reihe - Spalte
	String[][] runtimeResults;
	String[][] numberOfDeleteUseConflicts;
	
	Map<Rule, Integer> rowPositionOfRule;
	Map<Rule, Integer> columnPositionOfRule;
	
	int numberOfRules;
	
	boolean addDetailsOnRuleName = false;
	
	String totalRunTimes;
	String totalResults;
	
	
	public void init(int numberOfRules){
		
//		RuleSetMetricsCalculator ruleSetMetricsCalculator = new RuleSetMetricsCalculator(); 
		
		runtimeResults = new String[numberOfRules+3][numberOfRules+3]; // + ruleName + statistics
		numberOfDeleteUseConflicts = new String[numberOfRules+3][numberOfRules+3]; // + ruleName + statistics
		rowPositionOfRule = new HashMap<Rule, Integer>();
		columnPositionOfRule = new HashMap<Rule, Integer>();
		this.numberOfRules = numberOfRules+1;
	}
	
	public void setAnalysisKinds(boolean normal, boolean essential, boolean atomic){
		String runtimeString = "";
		String deleteUseconflicts = "";
		StringBuffer sb = new StringBuffer("");
		if(normal)
			sb.append("normalCPs");
		if(sb.length()>1 && essential)
			sb.append("/");
		if(essential)
			sb.append("essentialCPs");
		if(sb.length()>1 && atomic)
			sb.append(" / ");
		if(atomic)
			sb.append("conflictAtoms");
			
		runtimeString = sb.toString();
		deleteUseconflicts = sb.toString();
		if(atomic)
			deleteUseconflicts = deleteUseconflicts.concat(" / Candidates / Minimal Reasons");
		
		
		runtimeResults[0][0] = "RUNTIME - "+runtimeString+"[ms]";
		numberOfDeleteUseConflicts[0][0] = "D-U-CONFLICTS - "+deleteUseconflicts;			
	}
	
	public void addData(Rule rule1, Rule rule2, String runtime, String results){
		Integer rowPosition = rowPositionOfRule.get(rule1);
		if(rowPosition == null){
			rowPosition = rowPositionOfRule.size()+2;
			rowPositionOfRule.put(rule1, rowPosition);
//			String ruleDetails = analyseDetailsOfRule(rule1);
//			String ruleDetails = (addDetailsOnRuleName == true) ? analyseDetailsOfRule(rule1) : "";
			runtimeResults[rowPosition][0] = rule1.getName();// + ruleDetails;  
			numberOfDeleteUseConflicts[rowPosition][0] = rule1.getName();// + ruleDetails;
			runtimeResults[rowPosition][1] = runtime;// das sind hier die Metriken zur jeweiligen Regel und dies wird für jede Regel nur einmal zu Beginn eingetragen!  
			numberOfDeleteUseConflicts[rowPosition][1] = results;// das sind hier die Metriken zur jeweiligen Regel und dies wird für jede Regel nur einmal zu Beginn eingetragen!
		}
		Integer columnPosition = null;
		if(rule2 == null){
			columnPosition = -2;
		}else{
			if(rule2 != null)
				columnPosition = columnPositionOfRule.get(rule2);
			if(columnPosition == null){
				columnPosition = columnPositionOfRule.size()+2;
				columnPositionOfRule.put(rule2, columnPosition);
//			String ruleDetails = analyseDetailsOfRule(rule2);
				runtimeResults[0][columnPosition] = rule2.getName();// + ruleDetails;
				numberOfDeleteUseConflicts[0][columnPosition] = rule2.getName();// + ruleDetails;
				runtimeResults[1][columnPosition] = runtime;// das sind hier die Metriken zur jeweiligen Regel und dies wird für jede Regel nur einmal zu Beginn eingetragen!  
				numberOfDeleteUseConflicts[1][columnPosition] = results;// das sind hier die Metriken zur jeweiligen Regel und dies wird für jede Regel nur einmal zu Beginn eingetragen!
			}
			runtimeResults[rowPosition][columnPosition] = runtime;
			numberOfDeleteUseConflicts[rowPosition][columnPosition] = results;
		}
	}
	
	

	/**
	 * @param addDetailsOnRuleName the addDetailsOnRuleName to set
	 */
	public void setAddDetailsOnRuleName(boolean addDetailsOnRuleName) {
		this.addDetailsOnRuleName = addDetailsOnRuleName;
	}

	/*
	 *  todo: add details here: #LhsNode, #LhsEdges, #deleteNodes, #deleteEdges
	 */
	private String analyseDetailsOfRule(Rule rule) {
		
		int numberOfLhsNodes = rule.getLhs().getNodes().size();
		int numberOfLhsEdges = rule.getLhs().getEdges().size();
		Action deleteAction = new Action(Action.Type.DELETE);
		EList<Node> deletionNodes = rule.getActionNodes(deleteAction);
		int numberOfDeletionNodes = deletionNodes.size();
		EList<Edge> deletionEdges = rule.getActionEdges(deleteAction);
		int numberOfDeletionEdges = deletionEdges.size();
		
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("#LhsNode:");
		sb.append(numberOfLhsNodes);		
		sb.append(" ,#LhsEdges:");
		sb.append(numberOfLhsEdges);	
		sb.append(" ,#deleteNodes:");
		sb.append(numberOfDeletionNodes);		
		sb.append(" ,#deleteEdges:");
		sb.append(numberOfDeletionEdges);	
		sb.append("]");
		
		return sb.toString();
	}

	public void exportStoredRuntimeToCSV(String targetFolder){
		try {
			
			Date start = new Date();
			String filename = targetFolder + File.separator + dateFormat.format(start) + "__essentialCPs-runtime.csv";
			
			
			FileWriter fw = new FileWriter(filename);
			for(int row = 0; row<=numberOfRules; row++ ){
				
//				if(statistic && row ==1)
//					addRowStatistic();
				
				for(int column = 0; column <=numberOfRules; column++ ){
//					
//					if(statistic && column ==1)
//						addRowStatistic();
					
					fw.append(runtimeResults[row][column]);
					fw.append(";");
				}
				fw.append("\n");
			}
			if(totalRunTimes != null)
				fw.append(totalRunTimes);
			
			fw.flush();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exportStoredConflictsToCSV(String targetFolder){
		try {
			
			Date start = new Date();
			String filename = targetFolder + File.separator + dateFormat.format(start) + "_essentialCPs.csv";
			
			
			FileWriter fw = new FileWriter(filename);
			for(int row = 0; row<=numberOfRules; row++ ){
				for(int column = 0; column <=numberOfRules; column++ ){
					fw.append(numberOfDeleteUseConflicts[row][column]);
					fw.append(";");
				}
				fw.append("\n");
			}
			if(totalResults != null)
				fw.append(totalResults);
			
			fw.flush();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addRunTimes(long totalNormalRuntime, long totalEssentialRuntime, long totalAtomicRuntime) {
		StringBuffer sb = new StringBuffer();
		sb.append("Total run times [ms]- normal CPA: ");
		sb.append(totalNormalRuntime);
		sb.append(" essential CPA: ");
		sb.append(totalEssentialRuntime);
		sb.append(" atomic CPA: ");
		sb.append(totalAtomicRuntime);
		totalRunTimes = sb.toString();
	}

	public void addTotalResults(int totalNumberOfNormalCPs, int totalNumberOfEssentialCPs, int totalNumberOfAtomicCPs,
			int totalNumberOfConflictAtomCandidates, int totalNumberOfMinimalConflictReasons) {
		StringBuffer sb = new StringBuffer();
		sb.append("Total amount of rsults -");
		sb.append(" normal CPs: ");
		sb.append(totalNumberOfNormalCPs);
		sb.append(" essential CPs: ");
		sb.append(totalNumberOfEssentialCPs);
		sb.append(" conflict atoms: ");
		sb.append(totalNumberOfAtomicCPs);
		sb.append(" conflictAtomCandidates: ");
		sb.append(totalNumberOfConflictAtomCandidates);
		sb.append(" minimalConflictReasons: ");
		sb.append(totalNumberOfMinimalConflictReasons);
		totalResults = sb.toString();
	}
	
}
