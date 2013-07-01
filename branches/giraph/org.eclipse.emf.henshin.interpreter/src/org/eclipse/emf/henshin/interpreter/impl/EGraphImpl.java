/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.interpreter.EGraph;

/**
 * Default {@link EGraph} implementation. Based on linked hash sets.
 */
public class EGraphImpl extends LinkedHashSet<EObject> implements EGraph {
	
	// Generated serial ID:
	private static final long serialVersionUID = -1917534761444871093L;

	// All involved EPackages:
	protected final Set<EPackage> packages;
	
	// Mappings from each type to all its instances.
	protected final Map<EClass, List<EObject>> domainMap;
	
	// Mappings from each type to all its extending subtypes:
	protected final Map<EClass, Set<EClass>> inheritanceMap;
	
	// Cross reference adapter for determining cross references between registered objects:
	protected final ECrossReferenceAdapter crossReferenceAdapter;
	
	/**
	 * Default constructor.
	 * @param unique Whether to check for uniqueness of the elements.
	 */
	public EGraphImpl() {
		this(32);
	}

	/**
	 * Convenience constructor. Adds an object and all reachable objects to this graph. 
	 * @param object An object.
	 */
	public EGraphImpl(EObject object) {
		this();
		addGraph(object);
	}

	/**
	 * Convenience constructor. Adds a collection of objects and all reachable objects to this graph.
	 * @param collection A collection of objects.
	 */
	public EGraphImpl(Collection<? extends EObject> collection) {
		this();
		for (EObject object : collection) {
			if (!contains(object)) { // omit computing the transitive closure if possible
				addGraph(object);
			}
		}
	}

	/**
	 * Convenience constructor. Adds the contents of a resource and all reachable objects to this graph.
	 * @param resource A resource.
	 */
	public EGraphImpl(Resource resource) {
		this(resource.getContents());
	}
	
	/**
	 * Constructor.
	 * @param initialSize Initial size of the graph.
	 */
	public EGraphImpl(int initialSize) {
		super(initialSize);
		packages = new LinkedHashSet<EPackage>();
		domainMap = new LinkedHashMap<EClass, List<EObject>>();
		inheritanceMap = new LinkedHashMap<EClass, Set<EClass>>();
		crossReferenceAdapter = new ECrossReferenceAdapter();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.HashSet#add(java.lang.Object)
	 */
	@Override
	public boolean add(EObject object) {
		boolean added = super.add(object);
		if (added) {
			object.eAdapters().add(crossReferenceAdapter);
			EClass type = object.eClass();
			EPackage ePackage = type.getEPackage();
			List<EObject> domain = domainMap.get(type);
			if (domain == null) {
				domain = new ArrayList<EObject>();
				domainMap.put(type, domain);
			}
			domain.add(object);
			addEPackage(ePackage);
		}
		return added;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.HashSet#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object object) {
		boolean removed = super.remove(object);
		if (removed && object instanceof EObject) {
			domainMap.get(((EObject) object).eClass()).remove(object);
			((EObject) object).eAdapters().remove(crossReferenceAdapter);
		}
		return removed;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.EGraph#addTree(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean addTree(EObject root) {
		boolean changed = add(root);
		for (Iterator<EObject> it = root.eAllContents(); it.hasNext();) {
			if (add(it.next())) changed = true;
		}
		return changed;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.EGraph#removeTree(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean removeTree(EObject root) {
		boolean changed = remove(root);
		for (final Iterator<EObject> it = root.eAllContents(); it.hasNext();) {
			if (remove(it.next())) changed = true;
		}
		return changed;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.EGraph#addGraph(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean addGraph(EObject object) {
		Set<EObject> closure = new LinkedHashSet<EObject>(); 
		computeTransitiveClosure(object, closure);
		return addAll(closure);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.EGraph#removeGraph(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean removeGraph(EObject object) {
		Set<EObject> closure = new LinkedHashSet<EObject>(); 
		computeTransitiveClosure(object, closure);
		return removeAll(closure);
	}

	/*
	 * Compute the transitive closure of referenced EObjects.
	 */
	private boolean computeTransitiveClosure(EObject object, Set<EObject> closure) {
		if (closure.contains(object)) {
			return false;
		} else {
			closure.add(object);
			for (EReference ref : object.eClass().getEAllReferences()) {
				if (ref.isMany()) {
					@SuppressWarnings("unchecked")
					EList<EObject> targets = (EList<EObject>) object.eGet(ref);
					for (EObject target : targets) {
						computeTransitiveClosure(target, closure);
					}
				} else {
					EObject target = (EObject) object.eGet(ref);
					if (target!=null) {
						computeTransitiveClosure(target, closure);
					}
				}
			}
			return true;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.AbstractCollection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends EObject> objs) {
		boolean changed = false;
		for (EObject object : objs) {
			if (add(object)) changed = true;
		}
		return changed;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.AbstractSet#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> objs) {
		boolean changed = false;
		for (Object object : objs) {
			if (remove(object)) changed = true;
		}
		return changed;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.HashSet#clear()
	 */
	@Override
	public void clear() {
		for (EObject object : this) {
			object.eAdapters().remove(crossReferenceAdapter);
		}
		super.clear();
		packages.clear();
		domainMap.clear();
		inheritanceMap.clear();
	}
	
	/*
	 * Add an EPackage.
	 */
	protected boolean addEPackage(EPackage ePackage) {
		boolean added = packages.add(ePackage);
		if (added) {
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
		return added;
	}
	
	/*
	 * Update the inheritance map.
	 */
	protected void addChildParentRelation(EClass child, EClass parent) {
		Set<EClass> children = inheritanceMap.get(parent);
		if (children == null) {
			children = new LinkedHashSet<EClass>();
			inheritanceMap.put(parent, children);
		}
		children.add(child);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.EGraph#getDomain(org.eclipse.emf.ecore.EClass, boolean)
	 */
	@Override
	public List<EObject> getDomain(EClass type, boolean strict) {
		if (strict) {
			return new ArrayList<EObject>(getDomain(type));
		}
		List<EObject> domain = new ArrayList<EObject>();
		Set<EClass> inhMap = inheritanceMap.get(type);
		if (inhMap != null) {
			for (EClass child : inhMap) {
				domain.addAll(getDomain(child));
			}
		}
		return domain;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.EGraph#getDomainSize(org.eclipse.emf.ecore.EClass, boolean)
	 */
	@Override
	public int getDomainSize(EClass type, boolean strict) {
		if (strict) {
			return getDomain(type).size();
		}
		Set<EClass> inhMap = inheritanceMap.get(type);
		int size = 0;
		if (inhMap != null) {
			for (EClass child : inhMap) {
				size += getDomain(child).size();
			}
		}
		return size;
	}

	/*
	 * Get the domain for a given type.
	 */
	protected Collection<EObject> getDomain(EClass type) {
		List<EObject> domain = domainMap.get(type);
		if (domain == null) {
			domain = new ArrayList<EObject>();
			domainMap.put(type, domain);
		}
		return domain;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.EGraph#getCrossReferenceAdapter()
	 */
	@Override
	public ECrossReferenceAdapter getCrossReferenceAdapter() {
		return crossReferenceAdapter;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.EGraph#copy(java.util.Map)
	 */
	@Override
	public EGraph copy(Map<EObject, EObject> copies) {
		if (copies==null) {
			Copier copier = new Copier();
			copier.copyAll(getRoots());
			copier.copyReferences();
			copies = copier;
		}
		EGraph copy = new EGraphImpl(size());
		for (EObject object : this) {
			copy.add(copies.get(object));
		}
		return copy;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.EGraph#getRoots()
	 */
	@Override
	public List<EObject> getRoots() {
		List<EObject> roots = new ArrayList<EObject>();
		for (EObject object : this) {
			while (object.eContainer()!=null) {
				object = object.eContainer();
			}
			if (!roots.contains(object)) {
				roots.add(object);
			}
		}
		return roots;
	}

}