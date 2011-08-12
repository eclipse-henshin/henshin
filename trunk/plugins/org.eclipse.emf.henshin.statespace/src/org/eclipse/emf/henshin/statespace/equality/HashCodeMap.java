package org.eclipse.emf.henshin.statespace.equality;

import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;

public class HashCodeMap extends HashMap<EObject,Integer> {
	
	// Default serial ID:
	private static final long serialVersionUID = 1L;

	public int getHashCode(EObject key) {
		Integer result = get(key);
		return (result!=null) ? result : 0;
	}

}
