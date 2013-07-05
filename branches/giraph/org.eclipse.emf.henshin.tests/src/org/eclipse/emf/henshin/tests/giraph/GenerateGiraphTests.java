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
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class GenerateGiraphTests {

	public static void generateClass(Rule rule, int appCount, boolean logging, boolean useUUIDs) {
		try {
			String className = rule.getName() + appCount;
			Map<String,Object> args = new HashMap<String,Object>();
			args.put("data", new GiraphRuleData(rule));
			args.put("className", className);
			args.put("packageName", "org.apache.giraph.examples");
			args.put("logging", logging);
			args.put("useUUIDs", useUUIDs);
			args.put("applicationCount", appCount);

			GiraphRuleTemplate ruleTemplate = new GiraphRuleTemplate();
			String giraphCode = ruleTemplate.generate(args);
			save(new File("giraph-tests/classes/" + className + ".java"), giraphCode);

			HenshinUtilTemplate utilTemplate = new HenshinUtilTemplate();
			String utilCode = utilTemplate.generate(args);
			save(new File("giraph-tests/classes/HenshinUtil.java"), utilCode);
		} catch (Exception e) {
			System.err.println("Error generating class for rule " + rule.getName());
		}
	}

	public static void generateGraph(Rule rule) {
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

/*		Rule sierpinski = (Rule) module.getUnit("Sierpinski");
		generateClass(sierpinski, 1, true, false);
		generateClass(sierpinski, 3, true, false);
		generateClass(sierpinski, 6, false, false);
		generateClass(sierpinski, 9, false, true);
		generateGraph(sierpinski);

		Rule wheel = (Rule) module.getUnit("Wheel");
		Rule wheelStart = (Rule) module.getUnit("WheelStart");
		generateClass(wheel, 10, true, false); // maximum number of applications
		generateGraph(wheelStart);

*/		Rule deleteStar = (Rule) module.getUnit("DeleteStar");
		Rule extendStar = (Rule) module.getUnit("ExtendStar");
		Rule starStart = (Rule) module.getUnit("StarStart");
//		generateClass(deleteStar, 1, true, false);
		generateClass(extendStar, 3, true, true);
//		generateGraph(starStart);

	}

}
