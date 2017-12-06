/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.ui.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.interpreter.ui.HenshinInterpreterUIPlugin;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterKind;

/**
 * Manages a parameter's type and value.
 *  
 * @author Gregor Bonifer
 */
public class ParameterConfig {
	
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

	protected String name;
	protected Object value;
	protected int type;
	protected ParameterKind kind;
	protected boolean unset;
	
	public ParameterConfig() {
	}

	public ParameterConfig(Parameter param) {
		name = param.getName();
		if (param.getType() instanceof EDataType && param.getType().getEPackage() == EcorePackage.eINSTANCE) {
			switch (param.getType().getClassifierID()) {
			case EcorePackage.ESTRING:
				type = STRING;
				break;
			case EcorePackage.EBOOLEAN:
				type = BOOLEAN;
				break;
			case EcorePackage.EINT:
				type = INT;
				break;
			case EcorePackage.ELONG:
				type = LONG;
				break;
			case EcorePackage.EFLOAT:
				type = FLOAT;
				break;
			case EcorePackage.EDOUBLE:
				type = DOUBLE;
				break;
			}
		}
		kind = param.getKind();
		initValue();
	}
	
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
	
	public ParameterKind getKind() {
		return kind;
	}
	
	public boolean isClear() {
		return type == CLEAR;
	}
	
	public boolean isUnset() {
		return unset;
	}

	public void setUnset(boolean unset) {
		this.unset = unset;
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
	
	protected void initValue() {
		switch (getType()) {
			case NULL:
				setValue(null);
				return;
			case STRING:
				setValue("");
				return;
			case BOOLEAN:
				setValue(false);
				return;
			case DOUBLE:
				setValue((double) 0);
				return;
			case LONG:
				setValue((long) 0);
				return;
			case INT:
				setValue((int) 0);
				return;
			case FLOAT:
				setValue((float) 0);
				return;
		}
	}
	
}
