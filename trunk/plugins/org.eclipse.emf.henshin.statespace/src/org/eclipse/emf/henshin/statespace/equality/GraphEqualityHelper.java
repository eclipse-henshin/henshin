package org.eclipse.emf.henshin.statespace.equality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;

/**
 * Graph-based equality checker for EMF models. 
 * @author Christian Krause
 */
public class GraphEqualityHelper extends HashMap<EObject,EObject> {	
	
	// Default serial ID:
	private static final long serialVersionUID = 1L;
	
	// Helper for handling attributes:
	private EcoreEqualityHelper attributeHelper;

	// Whether node IDs should be ignored:
	private boolean ignoreNodeIDs;
	
	// Whether to ignore attribute values:
	private boolean ignoreAttributes;

	// Cached version of the current models to compare:
	private Model model1, model2;

	// Cached version of the current models to compare:
	private Map<Integer,List<EObject>> slots1, slots2;
	
	// Hash code maps:
	private HashCodeMap hashcodes1, hashcodes2;
	
	/**
	 * Default constructor.
	 * @param ignoreAttributes Whether to ignore attributes.
	 */
	public GraphEqualityHelper(boolean ignoreNodeIDs, boolean ignoreAttributes) {
		this.ignoreNodeIDs = ignoreNodeIDs;
		this.ignoreAttributes = ignoreAttributes;
		this.attributeHelper = new EcoreEqualityHelper(ignoreNodeIDs, ignoreAttributes);
	}
	
	/**
	 * Check graph equality for two models.
	 * @param model1 Model 1.
	 * @param map1 Hash codes for the elements in model 1.
	 * @param model2 Model 2.
	 * @param map2 Hash codes for the elements in model 2.
	 * @return <code>true</code> if they are equal.
	 */
	public boolean equals(Model model1, HashCodeMap map1, 
						  Model model2, HashCodeMap map2) {

		// Make sure we have both hash codes maps:
		if (map1==null || map2==null) {
			hashcodes1 = HashCodeMap.ECLASS_HASH_CODE_MAP;
			hashcodes2 = HashCodeMap.ECLASS_HASH_CODE_MAP;
			StateSpacePlugin.INSTANCE.logWarning("Using EClass-based node comparison (very slow!)");
		} else {
			hashcodes1 = map1;
			hashcodes2 = map2;
		}

		// Compute the slots for the two models:
		slots1 = generateSlots(model1, hashcodes1);
		slots2 = generateSlots(model2, hashcodes2);
		
		// Hash code sets must be equal:
		if (!slots1.keySet().equals(slots2.keySet())) {
			slots1 = null;
			slots2 = null;
			return false;
		}
		
		// Store the models:
		this.model1 = model1;
		this.model2 = model2;
		
		// Reset the match first:
		clear();
		
		// Depth-first comparison:
		boolean result = matchFirst();
		
		// Clean-up:
		slots1 = null;
		slots2 = null;
		hashcodes1 = null;
		hashcodes2 = null;
		this.model1 = null;
		this.model2 = null;
		
		// Done.
		return result;

	}
	
	/*
	 * Find the first object in model 1 that is not
	 * match yet and try to match it. This method
	 * is called recursively, thus will try to match 
	 * ALL objects.
	 */
	private boolean matchFirst() {
		
		// Find the first object to be matched
		Map.Entry<Integer,List<EObject>> next = null;
		
		// We take the one with the least number of similar objects:
		for (Map.Entry<Integer,List<EObject>> entry : slots1.entrySet()) {
			int elements = entry.getValue().size();
			if (elements>0) {
				if (next==null || elements<next.getValue().size()) {
					next = entry;
				}
			}
		}
		
		// Check if we are done already:
		if (next==null) {
			return true;
		}
		
		// Choose an object and try to find a match for it:
		EObject object = next.getValue().get(0);
		EObject[] candidates = slots2.get(next.getKey()).toArray(new EObject[0]);
		for (EObject candidate : candidates) {
			if (match(object, candidate)) {
				return true;
			}
		}
		
		// No match found:
		return false;
		
	}
	
	/*
	 * Check whether two objects are allowed to be matched.
	 * This does not change the current match. It performs
	 * only a basic check.
	 */
	private boolean canMatch(EObject o1, EObject o2) {
		
		// Check if the first object has been matched already:
		EObject match = get(o1);
		if (match!=null) {
			
			// If yes, it must be o2:
			return (match==o2);
			
		} else {
			
			// If not, o2 must not have been matched yet by another object.
			if (values().contains(o2)) {
				return false;
			}
			
		}
		
		// Must have the same hash code:
		if (hashcodes1.getHashCode(o1)!=hashcodes2.getHashCode(o2)) {
			return false;
		}
		
		// Compare the node IDs, if necessary:
		if (!ignoreNodeIDs && 
			model1.getNodeIDsMap().get(o1).intValue()!=model2.getNodeIDsMap().get(o2).intValue()) {
			return false;
		}
		
		// Must be of the same type:
		EClass type = o1.eClass();
		if (!o2.eClass().equals(type)) {
			return false;
		}

		// Compare all attributes:
		if (!ignoreAttributes) {
			for (EAttribute attribute : type.getEAllAttributes()) {
				if (!attributeHelper.haveEqualAttribute(o1, o2, attribute)) {
					return false;
				}
			}
		}
		
		// Ok:
		return true;

	}
	
	/*
	 * Perform a match operation.
	 */
	@SuppressWarnings("unchecked")
	private boolean match(EObject o1, EObject o2) {
		
		// Check if a match is possible:
		if (!canMatch(o1,o2)) {
			return false;
		}
		
		// Check if the objects are match already:
		if (containsKey(o1)) {
			// canMatch() already verified that it is matched with o2
			// We still need to match the rest though:
			return matchFirst();
		}
		
		// They have the same hash code:
		Integer hash = hashcodes1.get(o1);
		
		// For now we assume that the match works:
		put(o1, o2);
		slots1.get(hash).remove(o1);
		slots2.get(hash).remove(o2);
		
		// Now check the references:
		for (EReference reference : o1.eClass().getEAllReferences()) {
			if (reference.isMany()) {
				
				// List of referenced objects:
				EList<EObject> list1 = (EList<EObject>) o1.eGet(reference);
				EList<EObject> list2 = (EList<EObject>) o2.eGet(reference);
				
				// Must have the same size:
				if (list1.size()!=list2.size()) {
					remove(o1);			// abort the current match
					slots1.get(hash).add(0,o1);
					slots2.get(hash).add(0,o2);
					return false;
				}
				
				// Match all objects in the two lists:
				for (EObject l1 : list1) {
					for (EObject l2 : list2) {
						if (match(l1, l2)) {
							return true;	// done!
						}
					}
				}
				
				// No match found; we abort:
				remove(o1);
				slots1.get(hash).add(0,o1);
				slots2.get(hash).add(0,o2);
				return false;
				
			} else {
				
				// Single referenced objects:
				EObject l1 = (EObject) o1.eGet(reference);
				EObject l2 = (EObject) o2.eGet(reference);
				if ((l1==null && l2!=null) || 
					(l1!=null && l2==null) ||
					(l1!=null && !match(l1,l2))) {
					remove(o1);			// abort the current match
					slots1.get(hash).add(0,o1);
					slots2.get(hash).add(0,o2);
					return false;
				}
			}
			
		}
		
		// Still need to match the rest:
		if (matchFirst()) {
			return true;
		}
		
		// Otherwise abort:
		remove(o1);
		slots1.get(hash).add(0,o1);
		slots2.get(hash).add(0,o2);
		return false;

	}
	
	/*
	 * Generate slots for a given model.
	 */
	private static Map<Integer,List<EObject>> generateSlots(Model model, HashCodeMap map) {
		Map<Integer,List<EObject>> slots = new LinkedHashMap<Integer,List<EObject>>();
		TreeIterator<EObject> it = model.getResource().getAllContents();
		while (it.hasNext()) {
			EObject next = it.next();
			Integer hash = map.get(next);
			List<EObject> objects = slots.get(hash);
			if (objects==null) {
				slots.put(hash, objects = new ArrayList<EObject>());
			}
			objects.add(next);
		}
		return slots;
	}
	
}	
