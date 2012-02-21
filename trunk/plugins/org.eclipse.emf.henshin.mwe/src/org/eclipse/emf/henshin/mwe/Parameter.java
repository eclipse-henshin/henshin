package org.eclipse.emf.henshin.mwe;

import org.eclipse.emf.henshin.mwe.parameters.Value;


public class Parameter {

	protected String name;

	protected Value type;

	public void setName(String name) {
		this.name = name;
	}

	public void setValueType(Value type) {
		this.type = type;
	}

}
