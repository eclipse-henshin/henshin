package org.eclipse.emf.henshin.preprocessing.testsets;

import org.eclipse.emf.henshin.preprocessing.DirectoryAnalyser;
import org.eclipse.emf.henshin.preprocessing.RuleSetModifier;
import org.eclipse.emf.henshin.preprocessing.DirectoryAnalyser.AnalysisResult;

public class AnalyseAndModifyVisualContractsNanoXml {

	public static void main(String[] args) {

		// general path to visualContracts of NanoXML
		String subDirectoryPath = "testData\\visualContracts-nanoXML\\";
		// subdirectorys
		String original = "original\\";
//		String noUnits = "noUnits\\";
//		String noMultiRules = "noMultiRules\\";
//		String noMultiRulesNoAc = "noMultiRules_noAC\\";
//		String noMultiRulesNoAcNoAttrChange = "noMultiRules_noAC_noAttrChange\\";
		
		// analyze original
		DirectoryAnalyser directoryAnalyser = new DirectoryAnalyser();
		AnalysisResult analysesResult = directoryAnalyser.analyseDirectory(subDirectoryPath/*+original*/);
		analysesResult.printAllResultsOnConsole();
		
		//reduce units
		RuleSetModifier ruleSetModifier = new RuleSetModifier();
//		ruleSetModifier.removeUnits(subDirectoryPath+original, subDirectoryPath+noUnits);
		
		// reduce MultiRules
//		ruleSetModifier.removeMultiRules(subDirectoryPath+noUnits, subDirectoryPath+noMultiRules);
		
		// reduce ApplicationConditions 
//		ruleSetModifier.removeApplicationConditions(subDirectoryPath+noMultiRules, subDirectoryPath+noMultiRulesNoAc);
		
		// reduce attribute changes
		
		// analyze result
//		directoryAnalyser.analyseDirectory(subDirectoryPath+noMultiRulesNoAc).printAllResultsOnConsole();

	}

}
