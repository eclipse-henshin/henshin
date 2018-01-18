package org.eclipse.emf.henshin.rulegen;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.rulegen.simple.ExampleBasedRuleGenerator;
import org.eclipse.emf.henshin.rulegen.util.HenshinModelHelper;

/**
 * Provides convenient access to rule generation facilities.
 * 
 * @author Manuel Ohrndorf, Timo Kehrer
 */
public class RuleGenerationFacade {

	/**
	 * Constructs a Henshin rule from a pair of models, i.e. an example which declaratively demonstrates the
	 * transformation in terms of an original model A and a changed model B.<br/>
	 * 
	 * Basically, the corresponding elements in A and B will be treated as elements to be preserved by the rule,
	 * elements contained in A only will be treated as elements to be deleted, and elements contained in B only will be
	 * treated as elements to be created.<br/>
	 * 
	 * Corresponding elements in A and B are currently determined by calculating a matching using EMFCompare. This might
	 * be a variation point in the future, the basic infrastructure is designed for exchangeability of the matcher used
	 * to determine the corresponding elements in A and B.
	 * 
	 * 
	 * @param modelA The historic model version.
	 * @param modelB The actual model version.
	 * @return The constructed Henshin Module.
	 */
	public static Module createRuleByExample(Resource modelA, Resource modelB) {
		String name = modelA.getURI().segments()[modelA.getURI().segmentCount() - 1] + "-"
				+ modelB.getURI().segments()[modelB.getURI().segmentCount() - 1];

		// Create Module serving as rule container:
		Module module = HenshinFactory.eINSTANCE.createModule();
		module.setName(name);

		ExampleBasedRuleGenerator generator = new ExampleBasedRuleGenerator();
		Rule rule = generator.generateRule(name, modelA, modelB);

		module.getUnits().add(rule);

		module.getImports().addAll(HenshinModelHelper.calculateImports(module));

		return module;
	}

}
