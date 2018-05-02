package org.eclipse.emf.henshin.multicda.cda.runner.overapproximation;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.runner.Runner;
import org.eclipse.emf.henshin.multicda.cpa.result.CPAResult;
import org.eclipse.emf.henshin.multicda.cpa.result.Conflict;
import org.eclipse.emf.henshin.multicda.cpa.result.ConflictKind;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;

public class EssentialCpaOnFeatureModelRunner {

	static String path = "testData\\featureModelingWithoutUpperLimitsOnReferences\\fmedit_noAmalgamation_noNACs_noAttrChange\\rules\\";

	public static void main(String[] args) {

//		FeatureModelPackage.eINSTANCE.eClass();  

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xmi", new XMIResourceFactoryImpl());

		final File f = new File(Runner.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String filePath = f.toString();
		String projectPath = filePath.replaceAll("bin", "");

		System.out.println(projectPath);
		String fullSubDirectoryPath = projectPath + path;

		File dir = new File(fullSubDirectoryPath);

		List<String> pathsToHenshinFiles = inspectDirectoryForHenshinFiles(dir);

		LoaderUtil loaderUtil = new LoaderUtil();
		List<Rule> allLoadedRules = loaderUtil.loadAllRulesFromFileSystemPaths(pathsToHenshinFiles);

		System.out.println("number of henshin files: " + pathsToHenshinFiles.size());
		Runner runner = new Runner();
		runner.setAnalysisKinds(false, false, true, false, false, false);
		List<String> deactivatedRules = new LinkedList<String>();
		runner.run(fullSubDirectoryPath, deactivatedRules);

		CPAResult essentialCpaResults = runner.getEssentialCpaResults();

		CPAResult filteredConflicts = filterConflicts(essentialCpaResults);

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

	private static List<String> inspectDirectoryForHenshinFiles(File dir) {
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
}
