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

	public static void generateClass(Rule rule, int appCount, boolean logging) throws IOException {
		String className = rule.getName() + appCount;
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("data", new GiraphRuleData(rule));
		args.put("className", className);
		args.put("packageName", "org.apache.giraph.examples");
		args.put("logging", logging);
		args.put("applicationCount", appCount);
		
		GiraphRuleTemplate ruleTemplate = new GiraphRuleTemplate();
		String giraphCode = ruleTemplate.generate(args);
		save(new File("giraph-tests/classes/" + className + ".java"), giraphCode);
		
		HenshinUtilTemplate utilTemplate = new HenshinUtilTemplate();
		String utilCode = utilTemplate.generate(args);
		save(new File("giraph-tests/classes/HenshinUtil.java"), utilCode);
	}

	public static void generateGraph(Rule rule) throws IOException {
		String graphCode = GiraphUtil.getInstanceCode(rule);
		save(new File("giraph-tests/graphs/" + rule.getName() + ".json"), graphCode);
	}

	private static void save(File file, String content) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(content);
		writer.close();
	}
	
	
	public static void main(String[] args) {
		HenshinResourceSet resourceSet = new HenshinResourceSet("src/org/eclipse/emf/henshin/tests/giraph");
		Module module = resourceSet.getModule("GiraphTests.henshin");
		try {
			
			Rule sierpinski = (Rule) module.getUnit("Sierpinski");
			generateClass(sierpinski, 1, true);
			generateClass(sierpinski, 3, true);
			generateClass(sierpinski, 6, false);
			generateClass(sierpinski, 9, false);
			generateGraph(sierpinski);
			
			Rule wheel = (Rule) module.getUnit("Wheel");
			Rule wheelStart = (Rule) module.getUnit("WheelStart");
			generateClass(wheel, 10, true); // maximum number of applications
			generateGraph(wheelStart);

			Rule star = (Rule) module.getUnit("Star");
			Rule starStart = (Rule) module.getUnit("StarStart");
			generateClass(star, 1, true);
			generateGraph(starStart);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
