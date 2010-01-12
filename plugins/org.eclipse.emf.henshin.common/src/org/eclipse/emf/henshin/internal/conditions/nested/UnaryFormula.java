package org.eclipse.emf.henshin.internal.conditions.nested;

public abstract class UnaryFormula implements IFormula {
	IFormula child;
	
	public UnaryFormula(IFormula child) {
		this.child = child; 
	}

	public void optimizeVariableOrder() {
		child.optimizeVariableOrder();
	}
	
	public void reset() {
		child.reset();
	}
}
