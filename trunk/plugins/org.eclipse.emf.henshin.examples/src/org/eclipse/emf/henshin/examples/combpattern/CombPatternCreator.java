package org.eclipse.emf.henshin.examples.combpattern;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.ChangeImpl;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class CombPatternCreator {
		
	public static Rule createPattern(int size, HenshinResourceSet resourceSet) {
		
		// Turn off warnings:
		ChangeImpl.PRINT_WARNINGS = false;

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
		
		// Create the initial nodes:
		ruleAppl.setRule(createBasicPatternRule);
		ruleAppl.setParameterValue("system", system);
		ruleAppl.execute(null);
		
		Rule rule = null;

		// Extend the rule to the given size:
		ruleAppl.setRule(extendPatternRule);
		for (int i=0; i<size-1 ; i++){
			ruleAppl.execute(null);
			rule = (Rule) ruleAppl.getResultParameterValue("rule");
		}
		
		// Turn on warnings:
		ChangeImpl.PRINT_WARNINGS = true;

		return rule;
		
	}

}
