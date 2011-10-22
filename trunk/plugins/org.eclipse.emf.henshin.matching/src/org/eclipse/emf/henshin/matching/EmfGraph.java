/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.matching;

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
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;

/**
 * This is a graph-like representation of a set of EObjects. Only objects known
 * to this class will be used for matching.
 */
public class EmfGraph {
	/**
	 * All objects (instances) in this graph.
	 */
	protected final Collection<EObject> eObjects;
	/**
	 * All EPackages involved.
	 */
	protected final Collection<EPackage> ePackages;
	/**
	 * Mappings from each type to all its instances.
	 */
	final Map<EClass, Collection<EObject>> domainMap;
	/**
	 * Mappings from each type to all its extending subtypes.
	 */
	final Map<EClass, Collection<EClass>> inheritanceMap;
	/**
	 * Determines cross references for registered objects
	 */
	final ECrossReferenceAdapter crossReferenceAdapter;
	
	/**
	 * Constructor
	 */
	public EmfGraph() {
		eObjects = new LinkedHashSet<EObject>();
		ePackages = new HashSet<EPackage>();
		domainMap = new HashMap<EClass, Collection<EObject>>();
		inheritanceMap = new HashMap<EClass, Collection<EClass>>();
		crossReferenceAdapter = new ECrossReferenceAdapter();
	}
	
	/**
	 * Constructor which adds the given roots ({@link #addRoot(EObject)}) to
	 * <code>this</code> EmfGraph in addition.
	 * 
	 * @param roots
	 */
	public EmfGraph(final EObject... roots) {
		this();
		for (EObject r : roots) {
			addRoot(r);
		}
	}// constructor
	
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
		boolean collectionChanged;
		
		collectionChanged = addEObjectToGraph(root);
		for (final Iterator<EObject> it = root.eAllContents(); it.hasNext();) {
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
		boolean collectionChanged;
		
		collectionChanged = removeEObjectFromGraph(root);
		for (final Iterator<EObject> it = root.eAllContents(); it.hasNext();) {
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
		final Collection<EObject> domain = new ArrayList<EObject>();
		
		Collection<EClass> inhMap = inheritanceMap.get(type);
		if (inhMap != null) {
			for (EClass child : inhMap) {
				domain.addAll(getDomain(child));
			}
		}
		
		return domain;
	}
	
	/**
	 * @param eObject
	 * @return
	 */
	protected boolean addEObjectToGraph(EObject eObject) {
		boolean isNew = eObjects.add(eObject);
		
		if (isNew) {
			eObject.eAdapters().add(crossReferenceAdapter);
			final EClass type = eObject.eClass();
			final EPackage ePackage = type.getEPackage();
			
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
	
	/**
	 * @param eObject
	 * @return
	 */
	protected boolean removeEObjectFromGraph(EObject eObject) {
		boolean wasRemoved = eObjects.remove(eObject);
		
		if (wasRemoved) {
			final EClass type = eObject.eClass();
			
			final Collection<EObject> domain = domainMap.get(type);
			domain.remove(eObject);
		}
		
		return wasRemoved;
	}
	
	/**
	 * @param ePackage
	 * @return
	 */
	protected boolean addEPackage(EPackage ePackage) {
		boolean isNew = ePackages.add(ePackage);
		
		if (isNew) {
			for (EClassifier classifier : ePackage.getEClassifiers()) {
				if (classifier instanceof EClass) {
					final EClass type = (EClass) classifier;
					addChildParentRelation(type, type);
					for (EClass parent : type.getEAllSuperTypes()) {
						addChildParentRelation(type, parent);
					}
				}
			}
		}
		return isNew;
	}
	
	/**
	 * @param child
	 * @param parent
	 */
	protected void addChildParentRelation(EClass child, EClass parent) {
		Collection<EClass> children = inheritanceMap.get(parent);
		if (children == null) {
			children = new HashSet<EClass>();
			inheritanceMap.put(parent, children);
		}
		children.add(child);
	}
	
	/**
	 * @param type
	 * @return
	 */
	protected Collection<EObject> getDomain(EClass type) {
		Collection<EObject> domain = domainMap.get(type);
		
		if (domain == null) {
			domain = new ArrayList<EObject>();
			domainMap.put(type, domain);
		}
		
		return domain;
	}
	
	/**
	 * Returns all eobject contained in this graph (@see {@link #geteObjects()})
	 * which are container, i.e. which are not contained by other eobjects.
	 * 
	 * @return
	 */
	public Collection<EObject> getRootObjects() {
		final Collection<EObject> rootObjects = new HashSet<EObject>();
		for (EObject eObject : geteObjects()) {
			if (eObject.eContainer() == null) rootObjects.add(eObject);
		}
		
		return rootObjects;
	}
	
	/**
	 * Returns all eObjects contained in this graph.
	 * 
	 * @return A collection of all eObjects.
	 */
	public Collection<EObject> geteObjects() {
		return eObjects;
	}
	

	public ECrossReferenceAdapter getCrossReferenceAdapter() {
		return crossReferenceAdapter;
	}	
}