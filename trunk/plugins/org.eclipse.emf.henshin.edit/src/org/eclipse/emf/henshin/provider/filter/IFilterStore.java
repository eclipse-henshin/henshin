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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClassifier;

/**
 * 
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public interface IFilterStore extends IFilterChangeListener {
	
	public abstract Map<EClassifier, Boolean> getFilterPreferences();
	
	public static IFilterStore TRANSIENT_STORE = new IFilterStore() {
		
		@Override
		public void filterChanged(EClassifier classifier, boolean filtered) {
			System.out.println("filterChanged");
		}
		
		@Override
		public Map<EClassifier, Boolean> getFilterPreferences() {
			System.out.println("restoreing filters");
			return new HashMap<EClassifier, Boolean>();
		}
	};
	
}