package org.eclipse.emf.henshin.statespace.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Abstract state space manager implementation that provides a cache
 * for storing temporary state models.
 * 
 * @generated NOT
 * @author Christian Krause
 */
public abstract class AbstractStateSpaceManagerWithCache extends AbstractStateSpaceManager {
		
	/**
	 * Default cache size: 64
	 */
	public static final int DEFAULT_CACHE_SIZE = 64;
		
	// Cache size:
	private int cacheSize = DEFAULT_CACHE_SIZE;
	
	// Cache for the last accessed states:
	private Map<State,Resource> cache = new LinkedHashMap<State,Resource>() {	
		private static final long serialVersionUID = 1L;
		@Override @SuppressWarnings("unchecked")
		protected boolean removeEldestEntry(Map.Entry eldest) {
			return size() > cacheSize;
		}
	};
	
	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 */
	public AbstractStateSpaceManagerWithCache(StateSpace stateSpace) {
		super(stateSpace);
	}
	
	/**
	 * Get the cache for this state space manager.
	 * The size of the cache is maintained automatically.
	 * @return The cache.
	 */
	protected Map<State,Resource> getCache() {
		return cache;
	}
	
	/**
	 * Set the cache size.
	 * @param cacheSize Cache size.
	 */
	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}
	
}
