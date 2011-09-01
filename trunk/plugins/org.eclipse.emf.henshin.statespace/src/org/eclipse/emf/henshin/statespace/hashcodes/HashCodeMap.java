package org.eclipse.emf.henshin.statespace.hashcodes;

import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;

/**
 * A map which associates hash codes to objects.
 * @author Christian Krause
 */
public class HashCodeMap extends HashMap<EObject,Integer> {
	
	/**
	 * A hash code map which returns the hash code of an
	 * eObjects's eClass. This hash code map is unmodifiable.
	 */
	public static final HashCodeMap ECLASS_HASH_CODE_MAP = new HashCodeMap() {

		// Default serial ID:
		private static final long serialVersionUID = 1L;
		
		@Override
		public Integer get(Object object) {
			return new Integer(((EObject) object).eClass().hashCode());
		}

		@Override
		public int getHashCode(EObject object) {
			return object.eClass().hashCode();
		}
		
		@Override
		public Integer put(EObject object, Integer hashcode) {
			throw new UnsupportedOperationException();
		}

};
	
	// Default serial ID:
	private static final long serialVersionUID = 1L;
	
	/**
	 * Get the hash code for a given object.
	 * Returns 0 if the object was not found.
	 * @param object The object.
	 * @return Its registered hash code or 0.
	 */
	public int getHashCode(EObject object) {
		Integer result = get(object);
		return (result!=null) ? result : 0;
	}

}
