package org.eclipse.emf.henshin.tests.giraph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.henshin.interpreter.giraph.GiraphRuleData;
import org.eclipse.emf.henshin.interpreter.giraph.GiraphRuleTemplate;
import org.eclipse.emf.henshin.interpreter.giraph.GiraphUtil;
import org.eclipse.emf.henshin.interpreter.giraph.HenshinUtilTemplate;
import org.eclipse.emf.henshin.model.IteratedUnit;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class GenerateGiraphTests {

	public static void generateComputeClass(Unit mainUnit, boolean logging, boolean useUUIDs) {
		try {
			String className = mainUnit.getName();
			if (mainUnit instanceof IteratedUnit) {
				className = className + ((IteratedUnit) mainUnit).getIterations();
			}
			Map<Rule,GiraphRuleData> data = GiraphUtil.generateRuleData(mainUnit);
			Map<String,Object> args = new HashMap<String,Object>();
			args.put("ruleData", data);
			args.put("mainUnit", mainUnit);
			args.put("className", className);
			args.put("packageName", "org.apache.giraph.examples");
			args.put("logging", logging);
			args.put("useUUIDs", useUUIDs);

			GiraphRuleTemplate ruleTemplate = new GiraphRuleTemplate();
			String giraphCode = ruleTemplate.generate(args);
			save(new File("giraph-tests/classes/" + className + ".java"), giraphCode);

			HenshinUtilTemplate utilTemplate = new HenshinUtilTemplate();
			String utilCode = utilTemplate.generate(args);
			save(new File("giraph-tests/classes/HenshinUtil.java"), utilCode);
		} catch (Exception e) {
			System.err.println("Error generating compute class for " + mainUnit);
			e.printStackTrace();
		}
	}

	public static void generateInputGraph(Rule rule) {
		try {
			String graphCode = GiraphUtil.getInstanceCode(rule);
			save(new File("giraph-tests/graphs/" + rule.getName() + ".json"), graphCode);
		} catch (Exception e) {
			System.err.println("Error generating graph for rule " + rule.getName());			
		}
	}

	private static void save(File file, String content) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(content);
		writer.close();
	}


	public static void main(String[] args) {
		
		HenshinResourceSet resourceSet = new HenshinResourceSet("src/org/eclipse/emf/henshin/tests/giraph");
		Module module = resourceSet.getModule("GiraphTests.henshin");

		// Sierpinski
		Rule sierpinskiRule = (Rule) module.getUnit("Sierpinski");
		generateInputGraph(sierpinskiRule);
		generateComputeClass(sierpinskiRule, true, false);		
		IteratedUnit sierpinskiMain = (IteratedUnit) module.getUnit("SierpinskiMain");
		sierpinskiMain.setIterations("1");
		generateComputeClass(sierpinskiMain, true, false);
		sierpinskiMain.setIterations("3");
		generateComputeClass(sierpinskiMain, true, false);
		sierpinskiMain.setIterations("6");
		generateComputeClass(sierpinskiMain, false, false);
		sierpinskiMain.setIterations("9");
		generateComputeClass(sierpinskiMain, false, true);

		// Wheel
		Rule wheelStart = (Rule) module.getUnit("WheelStart");
		generateInputGraph(wheelStart);
		LoopUnit wheel = (LoopUnit) module.getUnit("WheelMain");
		generateComputeClass(wheel, true, false);
		
		// Star
		Rule starStart = (Rule) module.getUnit("StarStart");
		generateInputGraph(starStart);
		SequentialUnit starMain = (SequentialUnit) module.getUnit("StarMain");
		generateComputeClass(starMain, true, false);

		// Fork
		Rule forkStart = (Rule) module.getUnit("ForkStart");
		generateInputGraph(forkStart);
		LoopUnit forkMain = (LoopUnit) module.getUnit("ForkMain");
		generateComputeClass(forkMain, true, false);

	}

}
