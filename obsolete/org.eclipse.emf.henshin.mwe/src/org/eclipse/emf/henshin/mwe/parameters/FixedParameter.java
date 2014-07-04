package org.eclipse.emf.henshin.mwe.parameters;

import org.eclipse.emf.henshin.mwe.Parameter;

public class FixedParameter extends Parameter{
	
	protected String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
