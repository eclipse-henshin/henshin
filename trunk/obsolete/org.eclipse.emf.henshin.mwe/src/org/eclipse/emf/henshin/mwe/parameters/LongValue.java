package org.eclipse.emf.henshin.mwe.parameters;

public class LongValue extends TypedValue {

	public Long getValue() {
		return Long.parseLong(value);
	}

}