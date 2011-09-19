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

import org.eclipse.emf.henshin.model.HenshinPackage;

/**
 * 
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class FilterProvider extends BaseFilterProvider {
	
	public FilterProvider(IFilterStore store) {
		super(store);
	}
	
	//
	// Methods for HenshinPackage.Literals.MAPPING
	//
	public void filterMappings(boolean filter) {
		setFiltered(filter, HenshinPackage.Literals.MAPPING);
	}
	
	public boolean mappingsFiltered() {
		return isFiltered(HenshinPackage.Literals.MAPPING);
	}
	
	//
	// Methods for HenshinPackage.Literals.PARAMETER
	//
	public void filterParameters(boolean filtered) {
		setFiltered(filtered, HenshinPackage.Literals.PARAMETER);
	}
	
	public boolean parametersFiltered() {
		return isFiltered(HenshinPackage.Literals.PARAMETER);
	}
	
	//
	// Methods for HenshinPackage.Literals.PARAMETER_MAPPING
	//
	public void filterParameterMappings(boolean filtered) {
		setFiltered(filtered, HenshinPackage.Literals.PARAMETER_MAPPING);
	}
	
	public boolean parameterMappingsFiltered() {
		return isFiltered(HenshinPackage.Literals.PARAMETER_MAPPING);
	}
	
}
