package org.eclipse.emf.henshin.tests.giraph;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.henshin.giraph.GiraphGenerator;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GiraphTest {

	private static Module tests;

	private static IProject testProject;

//	@BeforeClass
	public static void init() {
		HenshinResourceSet resourceSet = new HenshinResourceSet("src/org/eclipse/emf/henshin/tests/giraph");
		tests = resourceSet.getModule("GiraphTests.henshin");
		Assert.assertNotNull(tests);
	}

//	@Test
	public void fork() {
		run("ForkMain", "ForkStart");
	}

	private void run(String mainUnitName, String inputRuleName) {
		run(tests.getUnit(mainUnitName), ((Rule) tests.getUnit(inputRuleName)).getLhs());
	}

	private void run(Unit mainUnit, Graph inputGraph) {
		GiraphGenerator generator = new GiraphGenerator();
		String inputName = inputGraph.getRule().getName();
		System.out.println("Generating Giraph code for " + mainUnit + " and graph in rule " + inputName + "...");
		if (testProject == null) {
			System.out.println("Installing Hadoop Test Environment (may take a couple of minutes)...");
			generator.setTestEnvironment(true);
		}
		try {
			IFile file = generator.generate(mainUnit, inputGraph, mainUnit.getName(), inputName, null);
			testProject = file.getProject();
			System.out.println("Launching " + mainUnit + "...");
			IFile antFile = testProject.getFolder("launch").getFile(mainUnit.getName() + ".xml");
			Assert.assertTrue(generator.launch(antFile, null));
		} catch (Exception e) {
			throw new AssertionError(e);
		}
		System.out.println("OK\n");
	}

}
