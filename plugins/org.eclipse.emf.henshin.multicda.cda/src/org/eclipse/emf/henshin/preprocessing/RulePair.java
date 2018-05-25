package org.eclipse.emf.henshin.preprocessing;

import org.eclipse.emf.henshin.model.Rule;

public class RulePair {
	public RulePair(Rule copy, Rule original) {
		super();
		this.copy = copy;
		this.original = original;
	}
	public Rule getCopy() {
		return copy;
	}
	public void setCopy(Rule copy) {
		this.copy = copy;
	}
	public Rule getOriginal() {
		return original;
	}
	public void setOriginal(Rule original) {
		this.original = original;
	}
	Rule copy;
	Rule original;
}
