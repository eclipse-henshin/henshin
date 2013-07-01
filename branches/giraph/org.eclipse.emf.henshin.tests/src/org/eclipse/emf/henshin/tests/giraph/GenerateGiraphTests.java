package org.eclipse.emf.henshin.tests.giraph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.henshin.interpreter.giraph.GiraphRuleData;
import org.eclipse.emf.henshin.interpreter.giraph.GiraphRuleTemplate;
import org.eclipse.emf.henshin.interpreter.giraph.HenshinUtilTemplate;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class GenerateGiraphTests {

	public static void generate(Rule rule, int appCount, boolean logging) throws IOException {
		String className = rule.getName() + appCount;
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("data", new GiraphRuleData(rule));
		args.put("className", className);
		args.put("packageName", "org.apache.giraph.examples");
		args.put("logging", logging);
		args.put("applicationCount", appCount);
		GiraphRuleTemplate ruleTemplate = new GiraphRuleTemplate();
		String giraphCode = ruleTemplate.generate(args);
		File file = new File("giraph-tests/classes/" + className + ".java");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(giraphCode);
		writer.close();
		HenshinUtilTemplate utilTemplate = new HenshinUtilTemplate();
		String utilCode = utilTemplate.generate(args);
		file = new File("giraph-tests/classes/HenshinUtil.java");
		writer = new BufferedWriter(new FileWriter(file));
		writer.write(utilCode);
		writer.close();
	}

	public static void main(String[] args) {
		HenshinResourceSet resourceSet = new HenshinResourceSet("src/org/eclipse/emf/henshin/tests/giraph");
		Module module = resourceSet.getModule("GiraphTests.henshin");
		Rule sierpinski = (Rule) module.getUnit("Sierpinski");
		try {
			generate(sierpinski, 1, true);
			generate(sierpinski, 3, true);
			generate(sierpinski, 6, false);
			generate(sierpinski, 9, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
