/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.edit.helpers;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Module;

/**
 * @generated
 */
public class ModuleEditHelper extends HenshinBaseEditHelper {

	/**
	 * Find an EClassifier by its name. It is allowed to specify the fully
	 * qualified name, i.e., including package informations
	 * ("package.Name", "package.package.Name", "Name").
	 * 
	 * @return an array which contains all EClassifier with the given name.
	 */
	public static EClassifier[] getEClassifiers(Module module, String name) {

		// Name must be set:
		if (name == null) {
			return new EClassifier[] {};
		}

		EClassifier result = null;
		name = name.trim();
		final String packageSeparatorRegExpr = "\\.";
		String[] names = name.split(packageSeparatorRegExpr);
		if (names.length > 1) { // if package information available

			// watch out for the correct package path
			for (EPackage p : collectAllEPackages(module, true)) {
				if (p.getName().equals(names[0])) {
					result = getEClassifier(p, names, 1);
					break;
				}
			}
			return new EClassifier[] { result };

		} else {
			// no package path is given, so we collect all matching EClassifier
			Set<EClassifier> eclassifierList = collectAllEClassifiers(module, true);
			List<EClassifier> resultList = new ArrayList<EClassifier>();
			for (EClassifier ec : eclassifierList) {
				if (ec.getName().equals(name)) {
					resultList.add(ec);
				}
			}
			return resultList.toArray(new EClassifier[resultList.size()]);
		}
	}

	/**
	 * Finds an EClassifier in a package with a full qualified name. 
	 * This helper method works recursively with <code>index</code> 
	 * being the current iteration depth in the qualified name.
	 */
	private static EClassifier getEClassifier(EPackage epackage, String[] qualifiedName, int index) {
		EClassifier classifier = null;
		if (index < (qualifiedName.length - 1)) {
			for (EPackage subPackage : epackage.getESubpackages()) {
				if (subPackage.getName().equals(qualifiedName[index])) {
					classifier = getEClassifier(subPackage, qualifiedName, index + 1);
					break;
				}
			}
		} else {
			classifier = epackage.getEClassifier(qualifiedName[qualifiedName.length - 1]);
		}
		return classifier;
	}

	/**
	 * Collects all classifiers found in the imported EPackages of a module.
	 */
	public static Set<EClassifier> collectAllEClassifiers(Module module, boolean withEcore) {
		Set<EClassifier> classifiers = new LinkedHashSet<EClassifier>();
		for (EPackage p : collectAllEPackages(module, withEcore)) {
			collectAllEClassifierHelper(classifiers, p);
		}
		return classifiers;
	}

	/**
	 * Collects all classes found in the imported EPackages of a module.
	 */
	public static Set<EClass> collectAllEClasses(Module module, boolean withEcore) {
		Set<EClassifier> classifiers = collectAllEClassifiers(module, withEcore);
		Set<EClass> classes = new LinkedHashSet<EClass>();
		for (EClassifier classifier : classifiers) {
			if (classifier instanceof EClass) {
				classes.add((EClass) classifier);
			}
		}
		return classes;
	}

	private static Set<EPackage> collectAllEPackages(Module module, boolean withEcore) {
		Set<EPackage> packages = new LinkedHashSet<EPackage>();
		packages.addAll(module.getImports());
		if (withEcore) {
			packages.add(EcorePackage.eINSTANCE);
		}
		return packages;
	}

	private static void collectAllEClassifierHelper(Set<EClassifier> elements, EPackage p) {
		elements.addAll(p.getEClassifiers());
		for (EPackage p2 : p.getESubpackages()) {
			collectAllEClassifierHelper(elements, p2);
		}
	}

}
