package org.eclipse.emf.henshin.rulegen.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;

/**
 * Specific utility methods for analyzing Henshin Modules, Units and Rules.
 * 
 * @author Timo Kehrer, Manuel Ohrndorf
 */
public class HenshinModelHelper {

	public static Set<EPackage> calculateImports(Module module) {
		Set<EPackage> imports = new HashSet<>();

		module.eAllContents().forEachRemaining(element -> {
			if (element instanceof Node) {
				imports.add(((Node) element).getType().getEPackage());
			}
		});

		return imports;
	}

}