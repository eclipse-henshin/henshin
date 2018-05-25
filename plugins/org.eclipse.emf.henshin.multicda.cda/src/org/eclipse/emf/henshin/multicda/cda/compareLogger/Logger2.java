package org.eclipse.emf.henshin.multicda.cda.compareLogger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class Logger2 {

	public enum LogData {
		DELTE_USE_CONFLICTS, PRODUCE_USE_DEPENDENCY, ESSENTIAL_DELTE_USE_CONFLICTS, ESSENTIAL_PRODUCE_USE_DEPENDENCY, CONFLICT_CANDIDATE, DEPENDENCY_CANDIDATE, CONFLICT_ATOM, DEPENDENCY_ATOM, MINIMAL_CONFLICT_REASON, MINIMAL_DEPENDENCY_REASON, CONFLICT_REASON, DEPENDENCY_REASON
	}

	LogData logData;

	boolean dataAdded = false;

	/**
	 * @return the logData
	 */
	public LogData getLogData() {
		return logData;
	}

	// access: analysisDurations[row][column]
	String[][] analysisDurations;

	/**
	 * @return the analysisDurations
	 */
	public String[][] getAnalysisDurations() {
		return analysisDurations;
	}

	/**
	 * @return the analysisResults
	 */
	public String[][] getAnalysisResults() {
		return analysisResults;
	}

	String[][] analysisResults;

	List<Rule> firstRules;

	/**
	 * @return the firstRules
	 */
	public List<Rule> getFirstRules() {
		return firstRules;
	}

	/**
	 * @return the secondRules
	 */
	public List<Rule> getSecondRules() {
		return secondRules;
	}

	List<Rule> secondRules;

	boolean addDetailsOnRuleName = false;

	public Logger2(LogData logData, List<Rule> firstRules, List<Rule> secondRules) {
		this.logData = logData;
		this.firstRules = firstRules;
		this.secondRules = secondRules;
		analysisDurations = new String[firstRules.size()][secondRules.size()];
		analysisResults = new String[firstRules.size()][secondRules.size()];
	}

	public Logger2(LogData logData, List<Rule> rules) {
		this(logData, rules, rules);
	}

	public void setAnalysisKinds(boolean normal, boolean essential, boolean atomic) {
		String runtimeString = "";
		String deleteUseconflicts = "";
		StringBuffer sb = new StringBuffer("");
		if (normal)
			sb.append("normalCPs");
		if (sb.length() > 1 && essential)
			sb.append("/");
		if (essential)
			sb.append("essentialCPs");
		if (sb.length() > 1 && atomic)
			sb.append(" / ");
		if (atomic)
			sb.append("conflictAtoms");

		runtimeString = sb.toString();
		deleteUseconflicts = sb.toString();
		if (atomic)
			deleteUseconflicts = deleteUseconflicts.concat(" / Candidates / Minimal Reasons");

		analysisDurations[0][0] = "RUNTIME - " + runtimeString + "[ms]";
		analysisResults[0][0] = "D-U-CONFLICTS - " + deleteUseconflicts;
	}

	public void addData(Rule rule1, Rule rule2, String runtime, String results) {

		dataAdded = true;

		int rowPosition = firstRules.indexOf(rule1);
		int columnPosition = secondRules.indexOf(rule2);

		analysisDurations[rowPosition][columnPosition] = runtime;
		analysisResults[rowPosition][columnPosition] = results;
	}

	/**
	 * @param addDetailsOnRuleName the addDetailsOnRuleName to set
	 */
	public void setAddDetailsOnRuleName(boolean addDetailsOnRuleName) {
		this.addDetailsOnRuleName = addDetailsOnRuleName;
	}

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

	public void exportStoredRuntimeToCSV(String targetFolder) {

		String filename = targetFolder + File.separator + "_runtime.csv";
		File file = new File(filename);

		if (dataAdded) {
			try {
				exportDataToCSV(file, analysisDurations);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void exportStoredResultsToCSV(String targetFolder) {
		String filename = targetFolder + File.separator + logData.toString() + "_results.csv";
		File file = new File(filename);

		if (dataAdded) {
			try {
				exportDataToCSV(file, analysisResults);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void exportDataToCSV(File file, String[][] exportData) throws IOException {
		FileWriter fw = new FileWriter(file);

		fw.append("\\/firstRule / secondRule->;");
		for (Rule rule : secondRules) {
			fw.append(rule.getName().toString());
			fw.append(";");
		}
		fw.append("\n");

		for (int row = 0; row < firstRules.size(); row++) {

			fw.append(firstRules.get(row).getName().toString());
			fw.append(";");

			for (int column = 0; column < secondRules.size(); column++) {
				fw.append(exportData[row][column]);
				fw.append(";");
			}
			fw.append("\n");
		}

		addStatisticsToCSV(fw);

		fw.flush();
		fw.close();
	}

	private void addStatisticsToCSV(FileWriter fw) throws IOException {
		int lastRow = firstRules.size() + 1;

		fw.append("SUM:");
		fw.append(";");
		String sum = "=SUMME(B2:CCC" + lastRow + ")";
		fw.append(sum);
		fw.append(";");
		fw.append("\n");

		fw.append("TIMEOUTS:");
		fw.append(";");
		String to = "=ZÄHLENWENN(B2:CCC" + lastRow + ".\"TO\")";
		fw.append(to);
		fw.append(";");
		fw.append("\n");

		fw.append("MAXIMUM:");
		fw.append(";");
		String max = "=MAX(B2:CCC" + lastRow + ")";
		fw.append(max);
		fw.append(";");
		fw.append("\n");

		fw.append("AVERAGE:");
		fw.append(";");
		String avg = "=MITTELWERT(B2:CCC" + lastRow + ")";
		fw.append(avg);
		fw.append(";");
		fw.append("\n");
	}

	public int getTotalResultAmount() {
		return sumUpAllNumbers(analysisResults);
	}

	public int getTotalRuntimeAmount() {
		return sumUpAllNumbers(analysisDurations);
	}

	private int sumUpAllNumbers(String[][] values) {
		int sum = 0;
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				try {
					int number = Integer.parseInt(values[i][j]);
					sum += number;
				} catch (NumberFormatException nfe) {
				}
			}
		}
		return sum;
	}

}
