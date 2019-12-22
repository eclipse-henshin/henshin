package org.eclipse.emf.henshin.variability.mergein.refactoring.logic;

import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Parameter;

public class TestPrinter {

	private static void printCommons(RuleSpecifics commons) {
		System.out.println("--- commons");
		System.out.println("Rule: " +  commons.getRule().getName());
		for (GraphElement ge : commons.getSpecificElements()) {
			System.out.println("GraphElemenent: " + ge);
		}
		System.out.println("---");
	}

	private static void printMasterSpecifics(RuleSpecifics masterSpecifics) {
		System.out.println("--- MasterSpecifics");
		System.out.println("Rule: " +  masterSpecifics.getRule().getName());
		for (GraphElement ge : masterSpecifics.getSpecificElements()) {
			System.out.println("GraphElemenent: " + ge);
		}
		System.out.println("---");
	}

	private static void printRuleSpecifics(List<RuleSpecifics> ruleSpecifics) {
		System.out.println("--- RuleSpecifics");
		for (RuleSpecifics re : ruleSpecifics) {
			System.out.println("Rule: " +  re.getRule().getName());
			for (GraphElement ge : re.getSpecificElements()) {
				System.out.println("GraphElemenent: " + ge);
			}
			System.out.println("---");
		}
	}

	public static void printMap(
			HashMap<GraphElement, GraphElement> mapOldNewGraphElements) {
		System.out.println("--- map");
		for (GraphElement ge : mapOldNewGraphElements.keySet()) {
			System.out.println("Key: " + ge + "; Value: " + mapOldNewGraphElements.get(ge));
		}
		System.out.println("=> Number of pairs: " + mapOldNewGraphElements.size());
		System.out.println("---");
	}

	public static void printAll(RuleSpecifics commons,
			RuleSpecifics masterSpecifics, List<RuleSpecifics> ruleSpecifics,
			HashMap<GraphElement, GraphElement> mapOldNewGraphElements) {
		TestPrinter.printCommons(commons);
		TestPrinter.printMasterSpecifics(masterSpecifics);
		TestPrinter.printRuleSpecifics(ruleSpecifics);
		TestPrinter.printMap(mapOldNewGraphElements);
	}
	
	public static void printAll(RuleParameters commonParameters,
			RuleParameters masterSpecificParameters, List<RuleParameters> ruleSpecificParameters) {
		TestPrinter.printCommonParameters(commonParameters);
		TestPrinter.printMasterSpecificParameters(masterSpecificParameters);
		TestPrinter.printRuleSpecificParameters(ruleSpecificParameters);
	}

	private static void printRuleSpecificParameters(
			List<RuleParameters> ruleSpecificParameters) {
		System.out.println("--- Rule specific parameters");
		for (RuleParameters rp : ruleSpecificParameters) {
			System.out.println("Rule: " +  rp.getRule().getName());
			for (Parameter param : rp.getParameters()) {
				System.out.println("Parameter: " + param);
			}
			System.out.println("---");
		}
	}

	private static void printMasterSpecificParameters(
			RuleParameters masterSpecificParameters) {
		System.out.println("--- master specific parameters");
		System.out.println("Rule: " +  masterSpecificParameters.getRule().getName());
		for (Parameter param : masterSpecificParameters.getParameters()) {
			System.out.println("Parameter: " + param);
		}
		System.out.println("---");
	}

	private static void printCommonParameters(RuleParameters commonParameters) {
		System.out.println("--- common parameters");
		System.out.println("Rule: " +  commonParameters.getRule().getName());
		for (Parameter param : commonParameters.getParameters()) {
			System.out.println("Parameter: " + param);
		}
		System.out.println("---");
	}

	public static void printAll(RuleSpecifics masterSubCommons, List<RuleSpecifics> ruleSubCommons) {
		TestPrinter.printMasterSubCommons(masterSubCommons);
		TestPrinter.printRuleSubCommons(ruleSubCommons);
	}

	private static void printRuleSubCommons(List<RuleSpecifics> ruleSubCommons) {
		System.out.println("--- rule sub commons");
		for (RuleSpecifics re : ruleSubCommons) {
			System.out.println("Rule: " +  re.getRule().getName());
			for (GraphElement ge : re.getSpecificElements()) {
				System.out.println("GraphElemenent: " + ge);
			}
			System.out.println("---");
		}
	}

	private static void printMasterSubCommons(RuleSpecifics masterSubCommons) {
		System.out.println("--- Master Sub Commons");
		System.out.println("Rule: " +  masterSubCommons.getRule().getName());
		for (GraphElement ge : masterSubCommons.getSpecificElements()) {
			System.out.println("GraphElemenent: " + ge);
		}
		System.out.println("---");
	}

	public static void printInfo(RuleSpecifics specifics) {
//		System.out.println("Rule: " +  specifics.getRule().getName());
//			int lhs = 0;
//			int rhs = 0;
//			for (GraphElement ge : specifics.getSpecificElements()) {
//				if (ge.getGraph().isLhs())
//					lhs++;
//				if (ge.getGraph().isRhs())
//					rhs++;
//	
//			}
//			System.out.println("LHS : "+lhs + ", RHS: " + rhs);
	}
}
