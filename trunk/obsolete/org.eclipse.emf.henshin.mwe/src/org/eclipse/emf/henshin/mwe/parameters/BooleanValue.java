package org.eclipse.emf.henshin.mwe.parameters;

public class BooleanValue extends TypedValue {

	boolean value;

	public void setValue(boolean value) {
		this.value = value;
	}

	public Boolean getValue() {
		return value;// Boolean.parseBoolean(value);
	}
}
