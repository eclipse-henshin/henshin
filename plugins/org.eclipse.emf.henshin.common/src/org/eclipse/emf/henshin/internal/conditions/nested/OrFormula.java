package org.eclipse.emf.henshin.internal.conditions.nested;

public class OrFormula extends BinaryFormula {
	public OrFormula(IFormula left, IFormula right) {
		super(left, right);
	}
	
	public boolean eval() {
		return left.eval() || right.eval();
	}
}
