package org.eclipse.emf.henshin.statespace.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.State;

/**
 * A cache for state models.
 * @generated NOT
 * @author Christian Krause
 */
public class StateModelCache extends LinkedHashMap<State,Resource> {	

	// Default cache size: 64
	public static final int DEFAULT_CACHE_SIZE = 64;

	// Serial id:
	private static final long serialVersionUID = 1L;	

	// Cache size:
	private int cacheSize;
	
	/**
	 * Default constructor.
	 */
	public StateModelCache() {
		this(DEFAULT_CACHE_SIZE);
	}
	
	/**
	 * Constructor.
	 * @param cacheSize Cache size.
	 */
	public StateModelCache(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
	 */
	@Override @SuppressWarnings("unchecked")
	protected boolean removeEldestEntry(Map.Entry eldest) {
		return size() > cacheSize;
	}

}
