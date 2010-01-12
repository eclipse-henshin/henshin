package org.eclipse.emf.henshin.internal.conditions.nested;

public class TrueFormula implements IFormula {

	@Override
	public boolean eval() {
		return true;
	}

	@Override
	public void optimizeVariableOrder() {

	}

	@Override
	public void reset() {

	}
}
