package org.eclipse.emf.henshin.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * This is a graph-like representation of a set of EObjects. Only objects known
 * to this class will be used for matching.
 */
public class EmfGraph {
    protected Set<EObject> eObjects;
    protected Set<EPackage> ePackages;
    Map<EClass, Set<EObject>> domainMap;
    Map<EClass, Set<EClass>> inheritanceMap;

    public List<EObject> usedObjects = new ArrayList<EObject>();

    public EmfGraph() {
        eObjects = new HashSet<EObject>();
        ePackages = new HashSet<EPackage>();
        domainMap = new HashMap<EClass, Set<EObject>>();
        inheritanceMap = new HashMap<EClass, Set<EClass>>();
    }

    public boolean addEObject(EObject eObject) {
        return addEObjectToGraph(eObject);
    }

    public boolean removeEObject(EObject eObject) {
        return removeEObjectFromGraph(eObject);
    }

    public boolean addRoot(EObject root) {
        boolean collectionChanged = false;

        collectionChanged |= addEObjectToGraph(root);
        for (Iterator<EObject> it = root.eAllContents(); it.hasNext();) {
            collectionChanged |= addEObjectToGraph(it.next());
        }

        return collectionChanged;
    }

    public boolean removeRoot(EObject root) {
        boolean collectionChanged = false;

        collectionChanged |= removeEObjectFromGraph(root);
        for (Iterator<EObject> it = root.eAllContents(); it.hasNext();) {
            collectionChanged |= removeEObjectFromGraph(it.next());
        }

        return collectionChanged;
    }

    public List<EObject> getDomainForType(EClass type) {
        List<EObject> domain = new ArrayList<EObject>();

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

            Set<EObject> domain = domainMap.get(type);
            if (domain == null) {
                domain = new HashSet<EObject>();
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

            Set<EObject> domain = domainMap.get(type);
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
        Set<EClass> children = inheritanceMap.get(parent);
        if (children == null) {
            children = new HashSet<EClass>();
            inheritanceMap.put(parent, children);
        }
        children.add(child);
    }

    protected Set<EObject> getDomain(EClass type) {
        Set<EObject> domain = domainMap.get(type);

        if (domain == null) {
            domain = new HashSet<EObject>();
            domainMap.put(type, domain);
        }

        return domain;
    }
}