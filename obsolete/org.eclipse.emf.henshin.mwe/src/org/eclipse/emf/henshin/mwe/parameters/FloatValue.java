package org.eclipse.emf.henshin.mwe.parameters;

public class FloatValue extends TypedValue{
	public Float getValue(){
		return Float.parseFloat(value);
	}
}
