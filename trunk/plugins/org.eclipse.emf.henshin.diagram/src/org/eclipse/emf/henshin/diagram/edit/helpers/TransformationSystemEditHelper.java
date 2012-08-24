/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.edit.helpers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * @generated
 */
public class TransformationSystemEditHelper extends HenshinBaseEditHelper {

	/**
	 * @param ts
	 * @param name
	 *            of the classifier. It is allowed to specify the <i>full</i>
	 *            qualified name, i.e., including package informations
	 *            ("package.Name", "package.package.Name", "Name").
	 * @return empty array if no EClassifier could be found, otherwise an array
	 *         which contains all EClassifier with the given name.
	 */
	public static EClassifier[] findEClassifierByName(TransformationSystem ts,
			String name) {

		if (name == null)
			return new EClassifier[] {};

		EClassifier result = null;
		name = name.trim();
		final String packageSeparatorRegExpr = "\\.";
		String[] names = name.split(packageSeparatorRegExpr);
		if (names.length > 1) { // if package information available

			// watch out for the correct package path
			for (EPackage p : ts.getImports()) {
				if (p.getName().equals(names[0])) {
					result = findClassifierByFQN(p, names, 1);
					break;
				}// if
			}// for
			return new EClassifier[] { result };

		} else {
			// no package path is given, so we collect all matching EClassifier
			final List<EClassifier> eclassifierList = collectAllEClassifier(ts);
			final List<EClassifier> resultList = new ArrayList<EClassifier>();
			for (EClassifier ec : eclassifierList) {
				if (ec.getName().equals(name))
					resultList.add(ec);
			}// for

			return resultList.toArray(new EClassifier[resultList.size()]);
		}// if else
	}// findEClassifierByName

	/**
	 * Finds an EClassifier in a package structure <code>p</code> with a
	 * full-qualified-name <code>fqn</code>. This helper method works
	 * recursively with <code>index</code> being the current iteration depth in
	 * <code>fqn</code>.
	 * 
	 * @param p
	 * @param fqn
	 * @param index
	 * @return
	 */
	private static EClassifier findClassifierByFQN(EPackage p, String[] fqn,
			int index) {

		EClassifier result = null;
		if (index < (fqn.length - 1)) {
			for (EPackage pp : p.getESubpackages()) {
				if (pp.getName().equals(fqn[index])) {
					result = findClassifierByFQN(pp, fqn, index + 1);
					break;
				}// if
			}// for
		} else {
			result = p.getEClassifier(fqn[fqn.length - 1]);
		}// if else

		return result;
	}// findClassifierByFQN

	/**
	 * Collects all classifiers found in the imported (
	 * {@link TransformationSystem#getImports()}) EPackages of the given
	 * {@link TransformationSystem}
	 * 
	 * @param ts
	 * @return
	 */
	public static List<EClassifier> collectAllEClassifier(
			TransformationSystem ts) {

		List<EClassifier> list = new ArrayList<EClassifier>();

		for (EPackage p : ts.getImports()) {
			collectAllEClassifierHelper(list, p);
		}// for

		return list;
	}// collectAllEClassifier

	/**
	 * @param elements
	 * @param p
	 */
	private static void collectAllEClassifierHelper(List<EClassifier> elements,
			EPackage p) {
		elements.addAll(p.getEClassifiers());
		for (EPackage p2 : p.getESubpackages())
			collectAllEClassifierHelper(elements, p2);
	}// collectAllEClassifierHelper

}
