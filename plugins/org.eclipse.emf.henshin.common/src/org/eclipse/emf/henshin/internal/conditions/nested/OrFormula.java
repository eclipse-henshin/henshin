package org.eclipse.emf.henshin.internal.conditions.nested;


public class OrFormula implements IFormula {
	private IFormula left;
	private IFormula right;
	
	public OrFormula(IFormula left, IFormula right) {
		this.left = left;
		this.right = right;
	}
	
	public boolean eval() {
		return left.eval() || right.eval();
	}
}
