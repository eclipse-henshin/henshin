package org.eclipse.emf.henshin.rulegen.tests;

import java.io.File;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.rulegen.RuleGenerationFacade;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Borderline test for testing the simple example-based rule generator.
 * 
 * @author Timo Kehrer
 */
public class CreateRuleByExampleTest {

	/**
	 * The historic model version.
	 */
	private Resource resAtestCase1;

	/**
	 * The actual model version.
	 */
	private Resource resBtestCase1;

	@Before
	public void setUp() throws Exception {
		// Prepare model type and resource factory
		EcorePackage.eINSTANCE.eClass();

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("ecore", new XMIResourceFactoryImpl());

		// Load models
		ResourceSet resourceSet = new ResourceSetImpl();

		resAtestCase1 = resourceSet.getResource(
				URI.createFileURI(new File("tests/CreateRuleByExample/TestCase1/A/A.ecore").getAbsolutePath()), true);
		resAtestCase1.load(Collections.EMPTY_MAP);

		resBtestCase1 = resourceSet.getResource(
				URI.createFileURI(new File("tests/CreateRuleByExample/TestCase1/B/A.ecore").getAbsolutePath()), true);
		resBtestCase1.load(Collections.EMPTY_MAP);
	}

	@Test
	public void testCreateRuleByExampleCase1() {
		Module module = RuleGenerationFacade.createRuleByExample(resAtestCase1, resBtestCase1);

		if (module != null) {
			Assert.assertEquals(module.getUnits().size(), 1);

			Rule rule = (Rule) module.getUnits().get(0);
			Assert.assertNotEquals(0, rule.getLhs().getNodes().size());
			Assert.assertNotEquals(0, rule.getRhs().getNodes().size());
			Assert.assertNotEquals(0, rule.getLhs().getEdges().size());
			Assert.assertNotEquals(0, rule.getRhs().getEdges().size());

		} else {
			Assert.fail("No module was generated!");
		}
	}
}
