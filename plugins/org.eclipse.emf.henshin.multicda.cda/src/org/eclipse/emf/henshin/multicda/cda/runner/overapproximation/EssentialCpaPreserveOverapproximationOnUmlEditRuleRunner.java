package org.eclipse.emf.henshin.multicda.cda.runner.overapproximation;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.runner.Runner;
import org.eclipse.emf.henshin.preprocessing.RuleSetModifier;
import org.eclipse.uml2.uml.UMLPackage;

public class EssentialCpaPreserveOverapproximationOnUmlEditRuleRunner {

	public static void main(String args[]) {

		UMLPackage.eINSTANCE.eClass();

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xmi", new XMIResourceFactoryImpl());

		ResourceSetImpl resourceSet = new ResourceSetImpl();

		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore",
				new EcoreResourceFactoryImpl());

		List<String> deactivatedRules = new LinkedList<String>();

		final File f = new File(Runner.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String filePath = f.toString();

		String projectPath = filePath.replaceAll("bin", "");
		System.out.println(projectPath);
		String subDirectoryPath = "testData\\umlEditRules\\";
		String resultPath = projectPath + subDirectoryPath;

		String fullSubDirectoryPathOfOriginalRules = projectPath + subDirectoryPath + "noMultiRules_noAC\\";

		File dirOriginal = new File(fullSubDirectoryPathOfOriginalRules);
		List<String> pathsToOriginalHenshinFiles = Runner.inspectDirectoryForHenshinFiles(dirOriginal);
		List<Rule> allLoadedOriginalRules = Runner.loadAllRulesFromFileSystemPaths(pathsToOriginalHenshinFiles,
				deactivatedRules);

		String fullSubDirectoryPathOfModifiedRules = projectPath + subDirectoryPath
				+ "noMultiRules_noAC_delToPreserve\\";
		File dirModified = new File(fullSubDirectoryPathOfModifiedRules);
		List<String> pathsToModifiedHenshinFiles = Runner.inspectDirectoryForHenshinFiles(dirModified);
		List<Rule> allLoadedModifiedRules = Runner.loadAllRulesFromFileSystemPaths(pathsToModifiedHenshinFiles,
				deactivatedRules);

		EssentialCpaPreserveOverapproximationUtil.analyseEssentialCpaPreserveOverapproximation(allLoadedOriginalRules,
				allLoadedModifiedRules, resultPath);
	}
}
