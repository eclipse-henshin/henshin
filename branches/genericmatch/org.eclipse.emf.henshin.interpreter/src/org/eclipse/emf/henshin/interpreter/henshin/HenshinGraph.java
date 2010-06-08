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
package org.eclipse.emf.henshin.interpreter.henshin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.common.util.GraphSkeleton;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;

/**
 * This is a graph-like representation of a set of EObjects. Only objects known
 * to this class will be used for matching.
 */
public class HenshinGraph implements GraphSkeleton<EClass, Node> {
	protected Graph graph;
	protected Set<EPackage> ePackages; 
	
	Map<EClass, Collection<Node>> domainMap;
	public Map<EClass, Collection<EClass>> inheritanceMap;

	public HenshinGraph(Graph graph) {
		this.graph = graph;
		
		domainMap = new HashMap<EClass, Collection<Node>>();
		inheritanceMap = new HashMap<EClass, Collection<EClass>>();
		ePackages = new HashSet<EPackage>();
		
		for (Node node: graph.getNodes()) {
			addEObject(node);
		}
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
	public boolean addEObject(Node eObject) {
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
	public boolean removeEObject(Node eObject) {
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
	public boolean addRoot(Node root) {
		return addEObjectToGraph(root);
	}

	/**
	 * Removes a containment tree from this graph.
	 * 
	 * @param root
	 *            The eObject which is the root of the containment tree.
	 * 
	 * @return true, if any eObject was removed.
	 */
	public boolean removeRoot(Node root) {
		return removeEObjectFromGraph(root);
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
	public Collection<Node> getDomainForType(EClass type) {
		Collection<Node> domain = new ArrayList<Node>();

		if (inheritanceMap.get(type) != null)
			for (EClass child : inheritanceMap.get(type)) {
				domain.addAll(getDomain(child));
			}

		return domain;
	}

	protected boolean addEObjectToGraph(Node eObject) {
		boolean isNew = !graph.getNodes().contains(eObject); 
			
		if (isNew) {
			graph.getNodes().add(eObject);
			EClass type = eObject.getType();
			EPackage ePackage = type.getEPackage();
			addEPackage(ePackage);

			Collection<Node> domain = domainMap.get(type);
			if (domain == null) {
				domain = new ArrayList<Node>();
				domainMap.put(type, domain);
			}
			domain.add(eObject);
		}

		return isNew;
	}

	protected boolean removeEObjectFromGraph(Node eObject) {
		boolean wasRemoved = graph.getNodes().remove(eObject);

		if (wasRemoved) {
			EClass type = eObject.eClass();

			Collection<Node> domain = domainMap.get(type);
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

	public Collection<Node> getDomain(EClass type) {
		Collection<Node> domain = domainMap.get(type);

		if (domain == null) {
			domain = new ArrayList<Node>();
			domainMap.put(type, domain);
		}

		return domain;
	}

	/**
	 * Returns all eObjects contained in this graph.
	 * 
	 * @return A collection of all eObjects.
	 */
	public Collection<Node> geteObjects() {
		return graph.getNodes();
	}
}