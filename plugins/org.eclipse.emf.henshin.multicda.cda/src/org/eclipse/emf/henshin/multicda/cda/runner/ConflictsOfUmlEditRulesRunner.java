package org.eclipse.emf.henshin.multicda.cda.runner;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.uml2.uml.UMLPackage;

public class ConflictsOfUmlEditRulesRunner extends Runner{
	

	public static void main(String args[]){
		System.out.println("test");
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
		String subDirectoryPath = "testData\\umlEditRules\\original\\";
		String fullSubDirectoryPath = projectPath + subDirectoryPath;
		
		Runner runner = new Runner();
		runner.setNoApplicationConditions(true);
		runner.setNoMultirules(true);
		runner.setAnalysisKinds(false, false, false, false, true, false);
		runner.run(fullSubDirectoryPath, deactivatedRules);
	}
}
