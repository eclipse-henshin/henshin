package org.eclipse.emf.henshin.internal.conditions.nested;

public interface IFormula {
	public boolean eval();
	
	public void reset();
	
	public void optimizeVariableOrder();
}
