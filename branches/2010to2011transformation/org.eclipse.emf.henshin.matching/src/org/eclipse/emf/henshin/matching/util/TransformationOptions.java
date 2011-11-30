/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.matching.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Possible options to control transformations. 
 *
 */
public class TransformationOptions {
	public final static String INJECTIVE = "injective";
	public final static String DANGLING = "dangling";
	public final static String DETERMINISTIC = "DETERMINISTIC";
	
	private Map<String, Object> options;
		
	public TransformationOptions() {
		options = new HashMap<String, Object>();
		options.put(DETERMINISTIC, true);
	}

	@Deprecated
	public boolean isInjective() {
		return (Boolean) getOption(INJECTIVE);
	}

	public void setInjective(boolean injective) {
		setOption(INJECTIVE, injective);
	}

	@Deprecated
	public boolean isDeterministic() {
		return (Boolean) getOption(DETERMINISTIC);
	}

	public void setDeterministic(boolean deterministic) {
		setOption(DETERMINISTIC, deterministic);
	}

	@Deprecated
	public boolean isDangling() {
		return (Boolean) getOption(DANGLING);
	}

	public void setDangling(boolean dangling) {
		setOption(DANGLING, dangling);
	}
	
	public Object getOption(String key) {
		return options.get(key);
	}
	
	public void setOption(String key, Object value) {
		options.put(key, value);
	}
	
	public void unsetOption(String key) {
		options.remove(key);
	}
	
	public TransformationOptions copy() {
		TransformationOptions copy = new TransformationOptions();
		copy.options = new HashMap<String, Object>(options);
		return copy;
	}
	
	@Override
	public String toString() {
		String result = "";
		List<String> setOptions = new ArrayList<String>(options.keySet());
		Collections.sort(setOptions);
		for (String option: setOptions) {
			result += option + ": " + options.get(option) + " ";
		}
		
		return result;
	}
}
