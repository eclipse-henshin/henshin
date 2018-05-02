package org.eclipse.emf.henshin.multicda.cda.runner.overapproximation;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.multicda.cda.runner.Runner;
import org.eclipse.emf.henshin.multicda.cpa.CDAOptions;
import org.eclipse.emf.henshin.multicda.cpa.CpaByAGG;
import org.eclipse.emf.henshin.multicda.cpa.ICriticalPairAnalysis;
import org.eclipse.emf.henshin.multicda.cpa.UnsupportedRuleException;
import org.eclipse.emf.henshin.multicda.cpa.result.CPAResult;
import org.eclipse.emf.henshin.multicda.cpa.result.Conflict;
import org.eclipse.emf.henshin.multicda.cpa.result.ConflictKind;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;

public class EssentialCpaOnFeatureModelRunnerBUG {

	private static String path = "testData\\featureModelingWithoutUpperLimitsOnReferences\\fmedit_noAmalgamation_noNACs_noAttrChange_additionalPreserveProgrammatic\\normal_rules\\";

	public static void main(String[] args) {

		List<Rule> rules = new LinkedList<Rule>();

		final File f = new File(Runner.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String filePath = f.toString();
		String projectPath = filePath.replaceAll("bin", "");
		System.out.println(projectPath);
		String fullSubDirectoryPath = projectPath + path;

		File dir = new File(fullSubDirectoryPath);

		List<File> henshinFiles = inspectDirectoryForHenshinFiles(dir);

		System.out.println("number of henshin files: " + henshinFiles.size());

		for (File henshinFile : henshinFiles) {

			File containingFolder = henshinFile.getParentFile();
			String absolutePath = containingFolder.getAbsolutePath();
			String henshinFileName = henshinFile.getName();
			HenshinResourceSet resourceSet = new HenshinResourceSet(absolutePath);
			Module module = resourceSet.getModule(henshinFileName, false);
			EList<Unit> units = module.getUnits();
			Rule theRule = null;
			boolean multipleRules = false;
			for (Unit unit : units) {
				if (unit instanceof Rule) {
					multipleRules = (theRule != null) ? true : false;
					theRule = (Rule) unit;
					rules.add(theRule);
				}
			}
			if (multipleRules)
				System.err.println("amount of rules: " + units.size());
		}

		ICriticalPairAnalysis cpaByAGG = new CpaByAGG();
		CDAOptions options = new CDAOptions();
		options.essentialCP = true;
		try {
			cpaByAGG.init(new HashSet<>(rules), options);
		} catch (UnsupportedRuleException e) {
			e.printStackTrace();
		}
		CPAResult conflicts = cpaByAGG.runConflictAnalysis();
		CPAResult filteredConflicts = filterConflicts(conflicts);
		System.out.println("number of filtered conflicts: " + filteredConflicts.getCriticalPairs().size());

	}

	private static CPAResult filterConflicts(CPAResult conflicts) {
		CPAResult filteredResult = new CPAResult();
		System.err.println("results before filtering: " + conflicts.getCriticalPairs().size());
		for (CriticalPair cp : conflicts.getCriticalPairs()) {
			if (cp instanceof Conflict) {
				Conflict conflict = (Conflict) cp;
				if (conflict.getConflictKind() == ConflictKind.DELETE_USE_CONFLICT) {
					filteredResult.addResult(conflict);
				}
			}
		}
		System.err.println("results after filtering: " + filteredResult.getCriticalPairs().size());
		return filteredResult;
	}

	private static List<File> inspectDirectoryForHenshinFiles(File dir) {
		List<File> henshinFiles = new LinkedList<File>();
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				String fileName = child.getName();
				if (fileName.endsWith(".henshin")) {
					henshinFiles.add(child);
				} else if (!child.getName().contains(".")) {
					File subDir = child;
					henshinFiles.addAll(inspectDirectoryForHenshinFiles(subDir));
				}
			}
		}
		return henshinFiles;
	}

}
