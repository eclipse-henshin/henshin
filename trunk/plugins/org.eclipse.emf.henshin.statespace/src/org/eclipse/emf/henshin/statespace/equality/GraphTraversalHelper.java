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
import org.eclipse.emf.henshin.statespace.hashcodes.HashCodeMap;

/**
 * Graph traversal helper used for depth-first traversal
 * of models in the {@link GraphEqualityHelper} class.
 * @author Christian Krause
 */
public class GraphTraversalHelper {
	
	/*
	 * Helper class for storing a pair of an object and a reference.
	 */
	private static class EObjectEReferencePair {
		
		// The object:
		public EObject object;
		
		// The reference:
		public EReference reference;

		/*
		 * Constructor.
		 */
		public EObjectEReferencePair(EObject object, EReference reference) {
			this.object = object;
			this.reference = reference;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return (object.hashCode() * 31) + reference.hashCode();
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
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
	
	// Hash codes to be used:
	private HashCodeMap hashcodes;
	
	// Sorted lists of references for all objects in the model:
	private Map<EObject,List<EReference>> references;
	
	// Sorted lists of contents for every object-reference pair:
	private Map<EObjectEReferencePair,List<EObject>> contents;
	
	/**
	 * Default constructor.
	 * @param hashcodes Hash codes to be used.
	 */
	public GraphTraversalHelper(HashCodeMap hashcodes) {
		this.hashcodes = hashcodes;
		this.references = new HashMap<EObject, List<EReference>>();
		this.contents = new HashMap<EObjectEReferencePair, List<EObject>>();
	}
	
	/**
	 * Get a sorted list of references for a given object.
	 * @param object Object.
	 * @return List of references sorted by difficulty.
	 */
	public List<EReference> getReferences(EObject object) {
		
		// Already computed for this object?
		List<EReference> refs = references.get(object);
		if (refs==null) {

			// All references of the object's type:
			refs = new ArrayList<EReference>(object.eClass().getEAllReferences());

			// Compute the difficulties of the references:
			final Map<EReference,Integer> difficulties = new HashMap<EReference,Integer>();
			for (EReference reference : refs) {
				difficulties.put(reference, getListDifficulty(referenceAsList(object, reference)));
			}

			// Now sort the references based on the difficulties:
			Collections.sort(refs, new Comparator<EReference>() {
				@Override
				public int compare(EReference r1, EReference r2) {
					return difficulties.get(r2) - difficulties.get(r1);
				}
			});

			// Remember this.
			references.put(object, refs);

		}
		
		// Done.
		return refs;
		
	}
	
	public List<EObject> getContents(EObject object, EReference reference) {
		
		// If the object has no associated hash code, we assume it does not belong to us:
		if (!hashcodes.containsKey(object)) {
			return referenceAsList(object, reference);
		}
		
		// Already computed?
		EObjectEReferencePair key = new EObjectEReferencePair(object, reference);
		List<EObject> con = contents.get(key);
		if (con==null) {
			
			// Get the contents (unsorted):
			con = new ArrayList<EObject>(referenceAsList(object, reference));
			
			// Compute the difficulties for all objects:
			final Map<EObject,Integer> difficulties = new HashMap<EObject, Integer>();
			for (EObject current : con) {
				difficulties.put(current, getObjectDifficulty(current, con));
			}

			// Now sort the objects based on their difficulties:
			Collections.sort(con, new Comparator<EObject>() {
				@Override
				public int compare(EObject o1, EObject o2) {
					return difficulties.get(o2) - difficulties.get(o1);
				}
			});

			// Remember it:
			contents.put(key, con);

		}
		
		// Done.
		return con;
		
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
