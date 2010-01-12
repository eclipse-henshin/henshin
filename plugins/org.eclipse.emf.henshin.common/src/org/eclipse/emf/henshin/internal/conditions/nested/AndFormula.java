package org.eclipse.emf.henshin.internal.conditions.nested;

public class AndFormula extends BinaryFormula {	
	public AndFormula(IFormula left, IFormula right) {
		super(left, right);
	}
	
	public boolean eval() {
		return left.eval() && right.eval();
	}

}
