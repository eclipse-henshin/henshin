package org.eclipse.emf.henshin.preprocessing;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class HenshinRuleLoader {
	public static List<String> inspectDirectoryForHenshinFiles(File dir) {
		List<String> pathsToHenshinFiles = new LinkedList<String>();
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				String fileName = child.getName();
				if (fileName.endsWith(".henshin")) {
					pathsToHenshinFiles.add(child.getAbsolutePath());
				} else if (!child.getName().contains(".")) {
					File subDir = child;
					pathsToHenshinFiles.addAll(inspectDirectoryForHenshinFiles(subDir));
				}
			}
		} else {
			throw new RuntimeException("Invalid directory.");
		}
		return pathsToHenshinFiles;
	}


	static List<String> filteredNames = Arrays.asList(
//			"Generalization_2-6","ExtractALTGroup","Specialization_3-6",
//			"28039_removeChild",
//			"28085_getParent",
//			"28215_setAttribute",
//			"28325_setAttribute",
//			"28364_addChildren"
);
	public static List<Rule> loadAllRulesFromFileSystemPaths(File dir) {
		List<String> pathsToHenshinFiles = inspectDirectoryForHenshinFiles(dir);
		List<Rule> rules = new LinkedList<Rule>();

		
		
		for (String pathToHenshinFiles : pathsToHenshinFiles) {
			HenshinResourceSet henshinResourceSet = new HenshinResourceSet();
			Module module = henshinResourceSet.getModule(pathToHenshinFiles);
			for (Unit unit : module.getUnits()) {
				if (unit instanceof Rule) {
					boolean deactivatedRule = false;
					if (!deactivatedRule) {
						if (!filteredNames.contains(unit.getName())) {
							rules.add((Rule) unit);
						} else
							System.out.println("Filtered out "+unit.getName());
					}
				}
			}
		}
		return rules;
	}
}
