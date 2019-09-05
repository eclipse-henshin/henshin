package org.eclipse.emf.henshin.examples.apibasics;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.LoggingApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class ExecuteHenshinRule {
		
	public static void main(String[] args) {
		EObject modelRoot = LoadModel.loadModel();
		
		/* 
		 * Generally it is a good idea to use the same ResourceSet for loading
		 * the model and the Henshin rules as it is less error prone. 
		 */		
		HenshinResourceSet rs = (HenshinResourceSet) modelRoot.eResource()
				.getResourceSet();
		
		/*
		 * Static rules need to be used when working with static models. 
		 * When creating a .henshin file, make sure to add the metamodel
		 * "From Registry" and not via the .ecore file.
		 * To add the metamodel to the Registry, start a new Eclipse
		 * instance (right click on project -> Run as -> Eclipse Application)
		 * from the workspace containing you metamodel classes. In that new
		 * instance you should be able to create henshin diagrams using
		 * the "From Registry" button to load the model.
		 * 
		 * You might want to compare rulesStatic and rulesDynamic in a text
		 * editor to learn about the differences.
		 */
		Module rules = rs.getModule("rulesStatic.henshin", true);
		Unit testRule = rules.getUnit("addItemToBox");
		
		/* For performance reasons you should reuse the Engine and EGraph in 
		 * your code when possible. However, do NOT reuse UnitApplications if
		 * you don't have a specific reason for that. UnitApplication keep some
		 * state of former rule executions. Reusing them can lead to unintended
		 * behavior.
		 */
		Engine engine = new EngineImpl();
		EGraph graph = new EGraphImpl(modelRoot);
		
		/* 
		 * If multiple macthes for a rule exists in a model, the following 
		 * allows a rule to select randomly where it is applied.
		 */ 
		engine.getOptions().put(Engine.OPTION_DETERMINISTIC, false);
		
		UnitApplication application = new UnitApplicationImpl(engine, graph, testRule, null);
	
		/*
		 *  If you want to analyse the execution of a rule you can use a
		 *  LoggingApplicationMonitor to get some feedback on console.
		 *  Otherwise use null as parameter. 
		 */	
		application.execute(new LoggingApplicationMonitor());
	}
}

/*
 * For more information on using the Henshin API visit
 * https://wiki.eclipse.org/Henshin/Interpreter.
 */