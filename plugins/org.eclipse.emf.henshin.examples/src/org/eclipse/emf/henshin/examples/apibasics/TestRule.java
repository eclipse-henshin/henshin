package org.eclipse.emf.henshin.examples.apibasics;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.examples.apibasics.boxing.Boxing;
import org.eclipse.emf.henshin.examples.apibasics.boxing.BoxingPackage;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.LoggingApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestRule {
	
	private static final String BASEDIR = "src/org/eclipse/emf/henshin/examples/apibasics/models";
	
	private static Engine engine;
	private static Resource modelResource;
	private static EObject model;
	private static List<EObject> roots;
	private static Module module;
	private static UnitApplication unitApp;
	private static HenshinResourceSet rs;
	
	/*
	 *  Create the ResourceSet where models and rules are managed in and 
	 *  register metamodel.
	 */
	@BeforeAll
	public static void setup() {
		rs = new HenshinResourceSet(BASEDIR);
		rs.getPackageRegistry().put(BoxingPackage.eINSTANCE.getNsURI(), BoxingPackage.eINSTANCE);
		engine = new EngineImpl();
	}
	
	/* 
	 * Before each new test, the resource holding the input model and the rules
	 * need to be reset. This is necessary as 
	 * "modelResource = rs.getResource("xyz.xmi")" will not load the model xyz
	 * again if it is already loaded by modelResource. As a consequence, 
	 * changes we applied to xyz would not be discarded if we don't unload the
	 * model first. Additionally, we might want to slightly change the tested 
	 * rules (e.g., to make them deterministic) in a test case. The same 
	 * principle as for the model applies;  we need to use unload to reset the 
	 * rules. 
	 */
	@BeforeEach
	public void resetUnitApp() {
		if (modelResource != null) {
			modelResource.unload();
		}
		rs.getResource("rulesStatic.henshin").unload();
		module = rs.getModule("rulesStatic.henshin");
		unitApp = new UnitApplicationImpl(engine);
	}
	
	/*
	 *  The actual test case needs to load the input model, apply the rule and
	 *  check the resulting model.
	 */
	@Test
	void testItemIsAlreadyStored() {
		// The path is still resolved against the BASEDIR.
		modelResource = rs.getResource("../testModels/storedItem.xmi");
		
		// Another way to load a model into an EGraph
		roots = modelResource.getContents();
		unitApp.setEGraph(new EGraphImpl(roots));				
		unitApp.setUnit(module.getUnit("addItemToBox"));
		
		assertFalse(unitApp.execute(new LoggingApplicationMonitor()));
	}
		
		
	@Test
	void testItemGetsStored() {
		modelResource = rs.getResource("../testModels/unstoredItem.xmi");

		roots = modelResource.getContents();
		unitApp.setEGraph(new EGraphImpl(roots));		
		unitApp.setUnit(module.getUnit("addItemToBox"));
		
		assertTrue(unitApp.execute(null));
		
		// Get resulting model from graph
		Boxing boxing = (Boxing)unitApp.getEGraph().getRoots().get(0);
		assertTrue(boxing.getBoxes().get(0).getStores().get(0) == boxing.getItems().get(0));		
	}

}
