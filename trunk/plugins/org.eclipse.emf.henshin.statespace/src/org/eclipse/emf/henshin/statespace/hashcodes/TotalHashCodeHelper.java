package org.eclipse.emf.henshin.statespace.hashcodes;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.statespace.Model;

/**
 * Helper class for computing the total hash codes of models.
 * 
 * @author Christian Krause
 */
class TotalHashCodeHelper {

	// The first 10 prime numbers.
	private static final int[] PRIMES = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 };

	// Flags:
	private boolean useGraphEquality;
	private boolean useObjectKeys;
	private boolean useObjectAttributes;
	
	// Cached context hash codes:
	private ContextHashCodeHelper contextHashCodes;
	
	/**
	 * Default constructor.
	 * @param useGraphEquality 
	 * @param useObjectKeys
	 * @param useObjectAttributes
	 */
	TotalHashCodeHelper(
			boolean useGraphEquality,
			boolean useObjectKeys, 
			boolean useObjectAttributes) {
		
		this.useGraphEquality = useGraphEquality;
		this.useObjectKeys = useObjectKeys;
		this.useObjectAttributes = useObjectAttributes;
	}
	
	/**
	 * Compute the hash code for a given model.
	 */
	public int hashCode(Model model) {

		// Compute the context hash codes:
		contextHashCodes = new ContextHashCodeHelper(model,
				useGraphEquality, useObjectKeys, useObjectAttributes);
		
		// Compute the total hash code:
		int result = totalHashCode(null, model.getResource().getContents(), 0);
		
		// Cleanup:
		contextHashCodes = null;
		
		// Done.
		return result;
		
	}
	
	/**
	 * Compute the total hash code of a list of EObjects.
	 * This delegates to #totalhashCode() for a single EObject.
	 */
	protected int totalHashCode(EObject container, EList<EObject> nodes, int depth) {
		int[] hashcodes = new int[nodes.size()];
		for (int i=0; i<hashcodes.length; i++) {
			hashcodes[i] = totalHashCode(nodes.get(i), depth);
		}
		return listHashCode(hashcodes, depth);
	}
	
	/**
	 * Compute the total hash code of a single node. This uses 
	 * the context-aware hash code of the current object and merges 
	 * it with the ones from the contents of the object. Hence,
	 * the method walks down the containment tree of the node.
	 */
	@SuppressWarnings("unchecked")
	protected int totalHashCode(EObject object, int depth) {
		
		// Context-aware hash code of the current object:
		int hash = contextHashCodes.get(object);
		
		// Now add the children:
		for (EReference reference : object.eClass().getEAllContainments()) {
			EList<EObject> children;
			if (reference.isMany()) {
				children = (EList<EObject>) object.eGet(reference);
			} else {
				EObject child = (EObject) object.eGet(reference);
				children = new BasicEList<EObject>();
				if (child!=null) {
					children.add(child);
				}
			}
			hash = (hash * 31) + totalHashCode(object, children, depth+1);
		}
		return hash;		
	}
	
	/**
	 * Combine a list of hash codes into one single hash code.
	 * Depending on the equality type, the list is treated as 
	 * a sequence or as a set.
	 */
	protected int listHashCode(int[] hashCodes, int depth) {
		int hash = 0;
		for (int i=0; i<hashCodes.length; i++) {
			if (!useGraphEquality) {
				hash *= PRIMES[depth % PRIMES.length];
			}
			hash += hashCodes[i];
		}
		return hash;
	}	

}
