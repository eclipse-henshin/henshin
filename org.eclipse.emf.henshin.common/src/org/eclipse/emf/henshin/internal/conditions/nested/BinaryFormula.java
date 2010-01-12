package org.eclipse.emf.henshin.internal.conditions.nested;

public abstract class BinaryFormula implements IFormula {
	IFormula left;
	IFormula right;
	
	public BinaryFormula(IFormula left, IFormula right) {
		this.left = left;
		this.right = right;
	}
		
	public void optimizeVariableOrder() {
		left.optimizeVariableOrder();
		right.optimizeVariableOrder();
	}

	public void reset() {
		left.reset();
		right.reset();
	}
}
