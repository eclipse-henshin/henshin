package org.eclipse.emf.henshin.statespace.equality;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

public class EObjectReferenceMap {
	
	private Set<EObject>[][] refs;
	
	@SuppressWarnings("unchecked")
	public EObjectReferenceMap(int size) {
		refs = new Set[size][];
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Set<EObject> get(int id, EObject base, EReference reference) {
		Set[] current = refs[id];
		if (current==null) {
			current = refs[id] = new Set[base.eClass().getEAllStructuralFeatures().size()];
		}
		int featureID = reference.getFeatureID();
		if (current[featureID]==null) {
			if (reference.isMany()) {
				current[featureID] = new HashSet<EObject>((EList<EObject>) base.eGet(reference));
			} else {
				EObject obj = (EObject) base.eGet(reference);
				if (obj!=null) {
					current[featureID] = new HashSet<EObject>();
					current[featureID].add(obj);
				} else {
					current[featureID] = Collections.EMPTY_SET;
				}
			}
		}
		return current[featureID];
	}
	
}
