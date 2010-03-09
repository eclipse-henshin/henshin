/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
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

	// Default cache size: 128
	public static final int DEFAULT_CACHE_SIZE = 128;

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
