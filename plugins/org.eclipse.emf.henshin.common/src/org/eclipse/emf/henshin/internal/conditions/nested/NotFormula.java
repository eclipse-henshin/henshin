package org.eclipse.emf.henshin.internal.conditions.nested;

public class NotFormula extends UnaryFormula {
	public NotFormula(IFormula child) {
		super(child);
	}
	
	@Override
	public boolean eval() {
		return !child.eval();
	}
}
