/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.provider.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * 
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class BaseFilterProvider {
	
	private Collection<IFilterChangeListener> listeners = new ArrayList<IFilterChangeListener>();
	protected Map<EClassifier, Boolean> filterMap = new HashMap<EClassifier, Boolean>();

	/**
	 * 
	 */
	public BaseFilterProvider(IFilterStore store) {		
		filterMap = store.getFilterPreferences();
		this.addFilterListener(store);
	}

	/**
	 * @param iFilterListener
	 */
	public void addFilterListener(IFilterChangeListener iFilterListener) {
		listeners.add(iFilterListener);
	}

	protected void setFiltered(boolean filtered, EClassifier c) {
		filterMap.put(c, filtered);
		for (IFilterChangeListener l : listeners)
			l.filterChanged(c, filtered);
	}

	public boolean isFiltered(EClassifier c) {
		Boolean value = filterMap.get(c);
		return value != null && value.booleanValue();
	}

	public Collection<? extends EStructuralFeature> filterChildFeatures(Collection<? extends EStructuralFeature> chidrenFeatures) {
		Collection<EStructuralFeature> result = new ArrayList<EStructuralFeature>();
		
		for (EStructuralFeature f : chidrenFeatures)
			if (!isFiltered(f.getEType()))
				result.add(f);
		
		return result;
	}
	
}