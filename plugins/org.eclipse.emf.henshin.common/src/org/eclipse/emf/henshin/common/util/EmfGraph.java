/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * This is a graph-like representation of a set of EObjects. Only objects known
 * to this class will be used for matching.
 */
public class EmfGraph {
	protected Collection<EObject> eObjects;
	protected Collection<EPackage> ePackages;
	Map<EClass, Collection<EObject>> domainMap;
	Map<EClass, Collection<EClass>> inheritanceMap;

	public EmfGraph() {
		eObjects = new LinkedHashSet<EObject>();
		ePackages = new HashSet<EPackage>();
		domainMap = new HashMap<EClass, Collection<EObject>>();
		inheritanceMap = new HashMap<EClass, Collection<EClass>>();
	}

	/**
	 * Adds a new eObject to this graph.
	 * 
	 * @param eObject
	 *            The eObject which will be added to the graph.
	 * 
	 * @return true, if the object was added. false, if it is already contained
	 *         in the graph.
	 */
	public boolean addEObject(EObject eObject) {
		return addEObjectToGraph(eObject);
	}

	/**
	 * Removes an eObject to this graph.
	 * 
	 * @param eObject
	 *            The eObject which will be removed from the graph.
	 * 
	 * @return true, if the object was removed. false, if it wasn't contained in
	 *         the graph.
	 */
	public boolean removeEObject(EObject eObject) {
		return removeEObjectFromGraph(eObject);
	}

	/**
	 * Adds a new containment tree to this graph.
	 * 
	 * @param root
	 *            The eObject which is the root of the containment tree.
	 * 
	 * @return true, if any eObject was added.
	 */
	public boolean addRoot(EObject root) {
		boolean collectionChanged = false;

		collectionChanged |= addEObjectToGraph(root);
		for (Iterator<EObject> it = root.eAllContents(); it.hasNext();) {
			collectionChanged |= addEObjectToGraph(it.next());
		}

		return collectionChanged;
	}

	/**
	 * Removes a containment tree from this graph.
	 * 
	 * @param root
	 *            The eObject which is the root of the containment tree.
	 * 
	 * @return true, if any eObject was removed.
	 */
	public boolean removeRoot(EObject root) {
		boolean collectionChanged = false;

		collectionChanged |= removeEObjectFromGraph(root);
		for (Iterator<EObject> it = root.eAllContents(); it.hasNext();) {
			collectionChanged |= removeEObjectFromGraph(it.next());
		}

		return collectionChanged;
	}

	/**
	 * Returns all possible EObjects of this graph which are compatible with the
	 * given type.
	 * 
	 * @param type
	 *            The type of the eObjects.
	 * 
	 * @return A collection of eObjects compatible with the type.
	 */
	public Collection<EObject> getDomainForType(EClass type) {
		Collection<EObject> domain = new ArrayList<EObject>();

		if (inheritanceMap.get(type) != null)
			for (EClass child : inheritanceMap.get(type)) {
				domain.addAll(getDomain(child));
			}

		return domain;
	}

	protected boolean addEObjectToGraph(EObject eObject) {
		boolean isNew = eObjects.add(eObject);

		if (isNew) {
			EClass type = eObject.eClass();
			EPackage ePackage = type.getEPackage();

			Collection<EObject> domain = domainMap.get(type);
			if (domain == null) {
				domain = new ArrayList<EObject>();
				domainMap.put(type, domain);
			}
			domain.add(eObject);

			addEPackage(ePackage);
		}

		return isNew;
	}

	protected boolean removeEObjectFromGraph(EObject eObject) {
		boolean wasRemoved = eObjects.remove(eObject);

		if (wasRemoved) {
			EClass type = eObject.eClass();

			Collection<EObject> domain = domainMap.get(type);
			domain.remove(eObject);
		}

		return wasRemoved;
	}

	protected boolean addEPackage(EPackage ePackage) {
		boolean isNew = ePackages.add(ePackage);

		if (isNew) {
			for (EClassifier classifier : ePackage.getEClassifiers()) {
				if (classifier instanceof EClass) {
					EClass type = (EClass) classifier;
					addChildParentRelation(type, type);
					for (EClass parent : type.getEAllSuperTypes()) {
						addChildParentRelation(type, parent);
					}
				}
			}
		}
		return isNew;
	}

	protected void addChildParentRelation(EClass child, EClass parent) {
		Collection<EClass> children = inheritanceMap.get(parent);
		if (children == null) {
			children = new HashSet<EClass>();
			inheritanceMap.put(parent, children);
		}
		children.add(child);
	}

	protected Collection<EObject> getDomain(EClass type) {
		Collection<EObject> domain = domainMap.get(type);

		if (domain == null) {
			domain = new ArrayList<EObject>();
			domainMap.put(type, domain);
		}

		return domain;
	}

	/**
	 * Returns all eObjects contained in this graph.
	 * 
	 * @return A collection of all eObjects.
	 */
	public Collection<EObject> geteObjects() {
		return eObjects;
	}
}