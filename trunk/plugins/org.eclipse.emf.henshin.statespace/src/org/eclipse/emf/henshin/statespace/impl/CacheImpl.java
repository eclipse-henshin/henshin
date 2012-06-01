package org.eclipse.emf.henshin.statespace.impl;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A universal cache class.
 * @author Christian Krause
 *
 * @param <K> Keys
 * @param <V> Values
 */
public class CacheImpl<K,V> extends LinkedHashMap<K,V> {

	// Default cache size, estimated with maximum number of MB of free memory:
	public static final int DEFAULT_CACHE_SIZE;
	
	static {
		if (StateSpaceDebug.ENFORCE_DETERMINISM) {
			DEFAULT_CACHE_SIZE = 1024; 
		} else {
			DEFAULT_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 1024 / 1024);
		}
	}
	
	// Serial id:
	private static final long serialVersionUID = 1L;

	// Cache size:
	private final int cacheSize;
	
	/**
	 * Constructor.
	 * @param cacheSize Cache size.
	 */
	public CacheImpl(int cacheSize) {
		this.cacheSize = cacheSize;
	}
	
	/**
	 * Constructor. Uses {@link #DEFAULT_CACHE_SIZE}.
	 */
	public CacheImpl() {
		this(DEFAULT_CACHE_SIZE);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
	 */
	@Override
	protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
		return size() > cacheSize;
	}
	
}