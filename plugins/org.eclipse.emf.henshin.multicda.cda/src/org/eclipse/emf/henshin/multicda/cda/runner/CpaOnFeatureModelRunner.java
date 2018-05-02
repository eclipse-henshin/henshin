package org.eclipse.emf.henshin.multicda.cda.runner;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class CpaOnFeatureModelRunner extends Runner {

	public static void main(String args[]) {
		System.out.println("test");

//		FeatureModelPackage.eINSTANCE.eClass();
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xmi", new XMIResourceFactoryImpl());

		ResourceSetImpl resourceSet = new ResourceSetImpl();

		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore",
				new EcoreResourceFactoryImpl());

		List<String> deactivatedRules = new LinkedList<String>();
		deactivatedRules.add("Refactoring_1-9");
		deactivatedRules.add("deleteGroup_IN_FeatureModel");
		deactivatedRules.add("PushDownGroup");

		final File f = new File(Runner.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String filePath = f.toString();
		String projectPath = filePath.replaceAll("bin", "");
		System.out.println(projectPath);
		String subDirectoryPath = "testData\\featureModeling\\fmedit\\rules\\";
		String fullSubDirectoryPath = projectPath + subDirectoryPath;

		Runner runner = new Runner();
		runner.setAnalysisKinds(false, false, true, false, false, false);
		runner.run(fullSubDirectoryPath, deactivatedRules);
	}
}
