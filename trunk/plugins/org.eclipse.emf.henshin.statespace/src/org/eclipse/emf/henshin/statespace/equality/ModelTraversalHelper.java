package org.eclipse.emf.henshin.statespace.equality;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

public class ModelTraversalHelper {
	
	private static class EObjectEReferencePair {
		
		public EObject object;
		public EReference reference;

		public EObjectEReferencePair(EObject object, EReference reference) {
			this.object = object;
			this.reference = reference;
		}
		
		@Override
		public int hashCode() {
			return (object.hashCode() * 31) + reference.hashCode();
		}
		
		@Override
		public boolean equals(Object o) {
			if (o instanceof EObjectEReferencePair) {
				return ((EObjectEReferencePair) o).object==object && 
					   ((EObjectEReferencePair) o).reference==reference;
			} else {
				return false;
			}
		}
		
	}
	
	private HashCodeMap hashcodes;
	
	private Map<EObject,List<EReference>> sortedReferences;
	private Map<EObjectEReferencePair,List<EObject>> sortedLists;
	
	public ModelTraversalHelper(HashCodeMap hashcodes) {
		this.hashcodes = hashcodes;
		this.sortedReferences = new HashMap<EObject, List<EReference>>();
		this.sortedLists = new HashMap<EObjectEReferencePair, List<EObject>>();
	}
	
	public List<EReference> getReferences(EObject object) {
		
		// Already computed for this object?
		List<EReference> references = sortedReferences.get(object);
		if (references!=null) {
			return references;
		}
		
		// All references of the object's type:
		references = new ArrayList<EReference>(object.eClass().getEAllReferences());
		
		// Compute the difficulties of the references:
		final Map<EReference,Integer> difficulties = new HashMap<EReference,Integer>();
		for (EReference reference : references) {
			difficulties.put(reference, getListDifficulty(referenceAsList(object, reference)));
		}
		
		// Now sort the references based on the difficulties:
		Collections.sort(references, new Comparator<EReference>() {
			@Override
			public int compare(EReference r1, EReference r2) {
				return difficulties.get(r2) - difficulties.get(r1);
			}
		});
		
		// Remember...
		sortedReferences.put(object, references);
		
		// Done.
		return references;
		
	}
	
	public List<EObject> getReferenceAsList(EObject object, EReference reference) {
		
		// If the object has no associated hash code, we assume it does not belong to us:
		if (!hashcodes.containsKey(object)) {
			return referenceAsList(object, reference);
		}
		
		EObjectEReferencePair key = new EObjectEReferencePair(object, reference);
		List<EObject> result = sortedLists.get(key);
		if (result!=null) {
			return result;
		}
		
		result = new ArrayList<EObject>(referenceAsList(object, reference));
		final Map<EObject,Integer> difficulties = new HashMap<EObject, Integer>();
		for (EObject current : result) {
			difficulties.put(current, getObjectDifficulty(current, result));
		}
		
		// Now sort the references based on the difficulties:
		Collections.sort(result, new Comparator<EObject>() {
			@Override
			public int compare(EObject o1, EObject o2) {
				return difficulties.get(o2) - difficulties.get(o1);
			}
		});

		// Remember...
		sortedLists.put(key, result);
		
		// Done.
		return result;
		
	}
	
	/*
	 * Get the difficulty of an object in a list.
	 * The higher the number, the more difficult.
	 */
	private int getObjectDifficulty(EObject object, List<EObject> list) {
		int hash = hashcodes.getHashCode(object);
		int difficulty = 0;
		for (EObject current : list) {
			if (current!=object && hashcodes.get(current)==hash) {
				difficulty++;
			}
		}
		return difficulty;
	}
	
	/*
	 * Compute the difficulty of matching a list.
	 * The higher the number, the more difficult.
	 */
	private int getListDifficulty(List<EObject> list) {
		Set<Integer> known = new HashSet<Integer>();
		int difficulty = 0;
		for (EObject obj : list) {
			Integer hash = hashcodes.get(obj);
			if (known.contains(hash)) {
				difficulty++;
			}
			known.add(hash);
		}
		return difficulty;
	}
	
	/*
	 * Get a reference of an object as a list of target objects.
	 */
	@SuppressWarnings("unchecked")
	private static List<EObject> referenceAsList(EObject object, EReference reference) {
		if (reference.isMany()) {
			return (EList<EObject>) object.eGet(reference);
		} else {
			EObject target = (EObject) object.eGet(reference);
			List<EObject> list = new ArrayList<EObject>();
			if (target!=null) {
				list.add(target);
			}
			return list;
		}
	}
	
}
