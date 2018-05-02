package org.eclipse.emf.henshin.preprocessing;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class DirectoryAnalyser {

	public static void main(String[] args) {
		System.out.println("running");
		String subDirectoryPath = "testData\\featureModelingWithoutUpperLimitsOnReferences\\fmedit_noAmalgamation\\rules\\";
		analyseDirectory(subDirectoryPath);
	}

	public class AnalysisResult {
		int numberOfFiles = 0;
		int numberOfFilesWithUnits = 0;
		int numberOfRules = 0;
		int numberOfUnits = 0;
		int numberOfRulesContainingAC = 0;
		int numberOfRulesContainingMultirules = 0;
		int numberOfMultiRules = 0;
		int numberOfCreatingRules = 0;
		int numberOfDeletingRules = 0;

		public AnalysisResult(int numberOfFiles, int numberOfFilesWithUnits, int numberOfRules, int numberOfUnits,
				int numberOfRulesContainingAC, int numberOfRulesContainingMultirules, int numberOfMultiRules,
				int numberOfCreatingRules, int numberOfDeletingRules) {
			this.numberOfFiles = numberOfFiles;
			this.numberOfFilesWithUnits = numberOfFilesWithUnits;
			this.numberOfRules = numberOfRules;
			this.numberOfUnits = numberOfUnits;
			this.numberOfRulesContainingAC = numberOfRulesContainingAC;
			this.numberOfRulesContainingMultirules = numberOfRulesContainingMultirules;
			this.numberOfMultiRules = numberOfMultiRules;
			this.numberOfCreatingRules = numberOfCreatingRules;
			this.numberOfDeletingRules = numberOfDeletingRules;
		}

		public void printAllResultsOnConsole() {
			System.out.println("-------------------------------------------------------------");
			System.out.println("\\/ \\/ \\/ Metrics of rules in directory \\/ \\/ \\/");
			System.out.println("numberOfFiles: \t \t \t" + numberOfFiles);
			System.out.println("numberOfFilesWithUnits: \t" + numberOfFilesWithUnits);
			System.out.println("numberOfRules: \t \t \t" + numberOfRules);
			System.out.println("numberOfUnits: \t \t \t" + numberOfUnits);
			System.out.println("numberOfRulesContainingAC: \t" + numberOfRulesContainingAC);
			System.out.println("numberOfRulesContainingMultirules: " + numberOfRulesContainingMultirules);
			System.out.println("numberOfMultiRules: \t \t" + numberOfMultiRules);
			System.out.println("numberOfCreatingRules: \t \t" + numberOfCreatingRules);
			System.out.println("numberOfDeletingRules: \t \t" + numberOfDeletingRules);
			System.out.println("-------------------------------------------------------------");
		}

	}

	public static AnalysisResult analyseDirectory(String fullSubDirectoryPath) {

		File dir = new File(fullSubDirectoryPath);
		List<String> pathsToHenshinFiles = DirectoryUtil.inspectDirectoryForHenshinFiles(dir);

		int numberOfFiles = pathsToHenshinFiles.size();

		int numberOfFilesWithUnits = 0;
		int numberOfRules = 0;
		int numberOfUnits = 0;
		int numberOfRulesContainingAC = 0;
		int numberOfRulesContainingMultirules = 0;
		int numberOfMultiRules = 0;
		int numberOfCreatingRules = 0;
		int numberOfDeletingRules = 0;

		for (String pathToHenshinFiles : pathsToHenshinFiles) {
			HenshinResourceSet henshinResourceSet = new HenshinResourceSet();
			Module module = henshinResourceSet.getModule(pathToHenshinFiles);
			boolean unitContained = false;
			for (Unit unit : module.getUnits()) {
				// check regarding units:
				if (unit instanceof Rule) {
					numberOfRules++;
				} else {
					unitContained = true;
					numberOfUnits++;
				}
				if (unit instanceof Rule) {
					Rule rule = (Rule) unit;
					//check regarding element creation
					Action createAction = new Action(Action.Type.CREATE);
					EList<Node> creationNodes = rule.getActionNodes(createAction);
					EList<Edge> creationEdges = rule.getActionEdges(createAction);
					if (creationNodes.size() > 0 || creationEdges.size() > 0)
						numberOfCreatingRules++;
					//check regarding element deletion
					Action deleteAction = new Action(Action.Type.DELETE);
					EList<Node> deletionNodes = rule.getActionNodes(deleteAction);
					EList<Edge> deletionEdges = rule.getActionEdges(deleteAction);
					if (deletionNodes.size() > 0 || deletionEdges.size() > 0)
						numberOfDeletingRules++;

					// check regarding AC:
					Formula formula = rule.getLhs().getFormula();
					if (formula != null)
						numberOfRulesContainingAC++;
					if (rule.getMultiRules().size() > 0) {
						numberOfRulesContainingMultirules++;
						numberOfMultiRules += rule.getMultiRules().size();
					}
				}
			}
			if (unitContained) {
				numberOfFilesWithUnits++;
			}
		}
		AnalysisResult analysisResult = new DirectoryAnalyser().new AnalysisResult(numberOfFiles,
				numberOfFilesWithUnits, numberOfRules, numberOfUnits, numberOfRulesContainingAC,
				numberOfRulesContainingMultirules, numberOfMultiRules, numberOfCreatingRules, numberOfDeletingRules);
		return analysisResult;
	}
}
