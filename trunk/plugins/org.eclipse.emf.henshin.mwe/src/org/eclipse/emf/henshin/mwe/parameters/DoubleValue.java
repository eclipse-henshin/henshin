package org.eclipse.emf.henshin.mwe.parameters;

public class DoubleValue extends TypedValue {
	
	public Double getValue(){
		return Double.parseDouble(value);
	}
}