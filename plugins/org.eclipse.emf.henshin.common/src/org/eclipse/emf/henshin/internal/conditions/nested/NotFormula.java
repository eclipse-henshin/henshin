package org.eclipse.emf.henshin.internal.conditions.nested;


public class NotFormula implements IFormula {
	private IFormula child;
	
	public NotFormula(IFormula child) {
		this.child = child;
	}
	
	@Override
	public boolean eval() {
		return !child.eval();
	}
}
