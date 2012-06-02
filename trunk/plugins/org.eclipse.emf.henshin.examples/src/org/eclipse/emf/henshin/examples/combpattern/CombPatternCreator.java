package org.eclipse.emf.henshin.examples.combpattern;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class CombPatternCreator {
		
	//public final static int PATTER_SIZE = 3; // should be at least 2
	
	public static Rule create(final int PATTER_SIZE, HenshinResourceSet resourceSet) {
		
		// Create a resource set with a base directory:
		//HenshinResourceSet resourceSet = new HenshinResourceSet("models");

		// Load the transformation system:
		TransformationSystem trasys = resourceSet.getTransformationSystem("comb-pattern-creator.henshin");

		// Load the rules:
		Rule createBasicPatternRule = trasys.findRuleByName("createBasicPatternRule");
		Rule extendPatternRule = trasys.findRuleByName("extendPatternRule");
		
		// Initialize the Henshin interpreter:
		EGraph graph = InterpreterFactory.INSTANCE.createEGraph();
		EPackage combPackage = (EPackage) resourceSet.getObject("comb.ecore");		
		graph.addTree(combPackage);
		TransformationSystem system = HenshinFactory.eINSTANCE.createTransformationSystem();
		graph.add(system);
		
		Engine engine = InterpreterFactory.INSTANCE.createEngine();
		RuleApplication ruleAppl = InterpreterFactory.INSTANCE.createRuleApplication(engine);
		ruleAppl.setEGraph(graph);
		
		//create Initial Nodes
		ruleAppl.setRule(createBasicPatternRule);
		ruleAppl.setParameterValue("system", system);
		ruleAppl.execute(null);
		
		Rule rule = null;

		//extend Rule to the given size
		ruleAppl.setRule(extendPatternRule);
		for(int i = 0; i < PATTER_SIZE - 1 ; i++){
			ruleAppl.execute(null);
			rule = (Rule) ruleAppl.getResultParameterValue("rule");
			ruleAppl.setCompleteMatch(null);
		}
		System.out.println("Pattern of size "+ PATTER_SIZE +" has been created.");
		return rule;
		//resourceSet.saveObject(system, "result.henshin");
	}

}
