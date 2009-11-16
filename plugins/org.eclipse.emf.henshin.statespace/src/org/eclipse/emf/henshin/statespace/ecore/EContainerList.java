package org.eclipse.emf.henshin.statespace.ecore;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * A list implementation that for container EObjects.
 * That means for every pair of objects with different indices
 * in the list, {@link EcoreUtil#isAncestor(EObject, EObject)}
 * returns <code>false</code>.
 * <p>
 * The list is ordered. However, {@link #equals(Object)} and
 * {@link #hashCode()} ignore this order. The reason for keeping
 * the order is to ensure that the Henshin match finder returns
 * a deterministic list of matches for this model instance.
 * </p>
 * 
 * @author Christian Krause
 * 
 */
public class EContainerList extends ArrayList<EObject> {
	
	// Default serial ID:
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 */
	public EContainerList() {
		super();
	}
	
	/**
	 * Convenience constructor.
	 * @param objects Collection of objects.
	 */
    public EContainerList(Collection<? extends EObject> objects) {
    	super(objects.size());
    	addAll(objects);
    }
    
    /**
     * Check whether this list contains an object or one of its ancestors. 
     * This just calls {@link EcoreUtil#isAncestor(Collection, EObject)}.
     * @param object Object to be checked.
     * @return <code>true</code> if it contains an ancestor.
     */
    public boolean containsAncestor(EObject object) {
    	return EcoreUtil.isAncestor(this, object);
    }
    
	/*
	 * (non-Javadoc)
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	@Override
	public boolean add(EObject object) {
		
		// Check if it is null:
		if (object==null) {
			throw new IllegalArgumentException();
		}
		
		// Check if the object or one of its ancestors exists already:
		if (containsAncestor(object)) return false;
		
		// Add the object:
		if (super.add(object)) {

			// Remove existing children from the list:
			removeAllChildren(object);
			return true;
			
		} else {
			return false;
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.ArrayList#addAll(java.util.Collection)
	 */
	@Override
    public boolean addAll(Collection<? extends EObject> objects) {
    	for (EObject current : objects) {
    		if (!add(current)) return false;
    	}
    	return true;
    }
	
	/*
	 * Remove all children of an object in the list.
	 */
	private void removeAllChildren(EObject object) {
		for (int i=0; i<size(); i++) {
			EObject current = get(i).eContainer();
			if (current!=null && EcoreUtil.isAncestor(object, current)) {
				remove(i--);
			}
		}
	}
	
	/* -------------- *
	 * Object methods *
	 * -------------- */
	
	@Override
	public Object clone() {
		return new EContainerList(this);
	}
	
	@Override
	public boolean equals(Object object) {
		
		if (object instanceof EContainerList) {
			
			// Create a copy of the argument list:
			EContainerList copy = new EContainerList((EContainerList) object);
			
			// Compare the sizes.
			if (copy.size()!=size()) return false;
			
			// Compare the objects:
			for (int i=0; i<size(); i++) {
				boolean found = false;
				for (int j=0; j<copy.size(); j++) {
					if (EcoreUtil.equals(get(i), copy.get(j))) {
						copy.remove(j);
						found = true;
						break;
					}
				}
				if (!found) return false;
			}
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		// Sum up the hash code of all objects.
		int hash = 0;
		for (EObject object : this) {
			hash += hashCode(object);
		}
		return hash;
	}
	
	/*
	 * Compute the hash code of an object.
	 */
	private int hashCode(EObject object) {
		
		// Get the class and package:
		EClass eclass = object.eClass();
		EPackage epackage = eclass.getEPackage();
		
		// Initialize hash based on class ID and package nsURI:
		int hash = eclass.getClassifierID() + 23;
		if (epackage!=null && epackage.getNsURI()!=null) {
			hash = hash * epackage.getNsURI().hashCode() + 47;
		}
		
		// Add the feature hash codes:
		for (EStructuralFeature feature : eclass.getEAllStructuralFeatures()) {
			
			// Compute the hash code of the values:
			int valueHash = 0;
			if (feature.isMany()) {
				EList<?> list = (EList<?>) object.eGet(feature);
				for (Object item : list) {
					valueHash += hashCode(item, feature);
				}
			} else {
				valueHash += hashCode(object.eGet(feature), feature);
			}
			
			// Include feature ID:
			hash += valueHash * (feature.getFeatureID() + 17);
			
		}
		
		return hash;
	}
	
	
	private int hashCode(Object value, EStructuralFeature feature) {
		
		// Value null?
		if (value==null) return 0;
		
		// Attributes:
		if (feature instanceof EAttribute) {
			return value.hashCode();
		}
		
		// References:
		if (feature instanceof EReference) {
			
			// Containment reference?
			if (((EReference) feature).isContainment()) {
				return hashCode((EObject) value);
			} else {
				return value.hashCode();
			}
			
		}
		
		// Should not get here.
		throw new IllegalArgumentException("Unknow feature type: " + feature);
		
	}
	
	/* --------------------------- *
	 * Unsupported list operations *
	 * --------------------------- */
	
	/*
	 * (non-Javadoc)
	 * @see java.util.ArrayList#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, EObject object) {
		throw new UnsupportedOperationException();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.ArrayList#addAll(int, java.util.Collection)
	 */
	@Override
    public boolean addAll(int index, Collection<? extends EObject> objects) {
		throw new UnsupportedOperationException();		
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.ArrayList#set(int, java.lang.Object)
	 */
	@Override
	public EObject set(int index, EObject object) {
		throw new UnsupportedOperationException();		
	}

}
