/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.ui.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.henshin.interpreter.ui.HenshinInterpreterUIPlugin;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Manages a parameter's type and value.
 *  
 * @author Gregor Bonifer
 */
public class ParameterConfiguration {
	
	public static final int CLEAR = 0;
	public static final int NULL = 1;
	public static final int STRING = 2;
	public static final int BOOLEAN = 3;
	public static final int INT = 4;
	public static final int FLOAT = 5;
	public static final int LONG = 6;
	public static final int DOUBLE = 7;
	
	
	private static Map<Integer, String> supportedTypes;
	
	public static Map<Integer, String> getSupportedTypes() {
		if (supportedTypes == null) {
			supportedTypes = new HashMap<Integer, String>();
			supportedTypes.put(CLEAR, HenshinInterpreterUIPlugin.LL("_UI_ParameterType_Clear"));
			supportedTypes.put(NULL, HenshinInterpreterUIPlugin.LL("_UI_ParameterType_Null"));
			supportedTypes.put(STRING, HenshinInterpreterUIPlugin.LL("_UI_ParameterType_String"));
			supportedTypes.put(BOOLEAN, HenshinInterpreterUIPlugin.LL("_UI_ParameterType_Boolean"));
			supportedTypes.put(INT, HenshinInterpreterUIPlugin.LL("_UI_ParameterType_Int"));
			supportedTypes.put(LONG, HenshinInterpreterUIPlugin.LL("_UI_ParameterType_Long"));
			supportedTypes.put(FLOAT, HenshinInterpreterUIPlugin.LL("_UI_ParameterType_Float"));
			supportedTypes.put(DOUBLE, HenshinInterpreterUIPlugin.LL("_UI_ParameterType_Double"));
		}
		return supportedTypes;
	}
	
	public static ParameterConfiguration loadConfiguration(IPreferenceStore store, String name) {
		ParameterConfiguration cfg = new ParameterConfiguration();
		cfg.name = name;
		cfg.store = store;
		cfg.initType();
		return cfg;
	}
	
	protected IPreferenceStore store;
	protected String name;
	protected Object value;
	protected int type;
	
	public String getName() {
		return name;
	}
	
	public Object getValue() {
		return value;
	}
	
	public Integer getType() {
		return type;
	}
	
	public String getTypeLabel() {
		return getSupportedTypes().get(type);
	}
	
	public boolean isClear() {
		return type == CLEAR;
	}
	
	public void setType(int type) {
		if (!getSupportedTypes().containsKey(type))
			throw new IllegalArgumentException(type + " is not a valid type!");
		if (type == this.type)
			return;
		this.type = type;
		initValue();
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	protected void initType() {
		Integer type = store.getInt(getTypeKey());
		this.type = getSupportedTypes().containsKey(type) ? type : NULL;
		initValue();
	}
	
	protected void initValue() {
		
		switch (getType()) {
			case NULL:
				setValue(null);
				return;
			case STRING:
				setValue(store.getString(getValueKey()));
				return;
			case BOOLEAN:
				setValue(store.getBoolean(getValueKey()));
				return;
			case DOUBLE:
				setValue(store.getDouble(getValueKey()));
				return;
			case LONG:
				setValue(store.getLong(getValueKey()));
				return;
			case INT:
				setValue(store.getInt(getValueKey()));
				return;
			case FLOAT:
				setValue(store.getFloat(getValueKey()));
				return;
				
		}
	}
	
	public void persist(IPreferenceStore store) {
		store.setValue(getTypeKey(), type);
		
		switch (getType()) {			
			case STRING:
				store.setValue(getValueKey(), (String) value);
				break;
			case BOOLEAN:
				store.setValue(getValueKey(), (Boolean) value);
				break;
			case DOUBLE:
				store.setValue(getValueKey(), (Double) value);
				break;
			case LONG:
				store.setValue(getValueKey(), (Long) value);
				break;
			case INT:
				store.setValue(getValueKey(), (Integer) value);
				break;
			case FLOAT:
				store.setValue(getValueKey(), (Float) value);
				break;
		}
	}
	
	protected String getTypeKey() {
		return "param_" + name + "_type";
	}
	
	protected String getValueKey() {
		return "param_" + name + "_value_" + type;
	}
	
}
