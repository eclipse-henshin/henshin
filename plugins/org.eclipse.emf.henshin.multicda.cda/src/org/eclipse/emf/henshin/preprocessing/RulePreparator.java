package org.eclipse.emf.henshin.preprocessing;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class RulePreparator {

	public static Rule prepareRule(Rule rule) {
		return prepareRule(rule, true);
	}
	
	

	public static Rule prepareRule(Rule rule, boolean removeAttributes ) {
		
		Module module = rule.getModule();
		Rule newRule = HenshinFactory.eINSTANCE.createRule();
		rule.getLhs().setFormula(null);
		newRule.setLhs(rule.getLhs());
		newRule.setRhs(rule.getRhs());
		newRule.getMappings().addAll(rule.getMappings());
		newRule.setName(rule.getName());

		if (removeAttributes) {
		for (Node node : newRule.getLhs().getNodes()) {
			node.getAttributes().clear();
		}
		}
		for (Node node : newRule.getRhs().getNodes()) {
			node.getAttributes().clear();
		}
		if (module != null) {
			module.getUnits().remove(rule);
			module.getUnits().add(newRule);
		}
		return newRule;
	}

	public static List<Rule> prepareRule(List<Rule> rules) {
		List<Rule> result = new ArrayList<Rule>();
		for (Rule rule : rules) {
			result.add(prepareRule(rule));
		}
		return result;
	}
}
