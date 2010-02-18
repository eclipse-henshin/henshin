package org.eclipse.emf.henshin.common.util;

public class TransformationOptions {
	private boolean injective;
	private boolean deterministic;
	
	public TransformationOptions() {
		injective = true;
		deterministic = true;
	}

	public boolean isInjective() {
		return injective;
	}

	public void setInjective(boolean injective) {
		this.injective = injective;
	}

	public boolean isDeterministic() {
		return deterministic;
	}

	public void setDeterministic(boolean deterministic) {
		this.deterministic = deterministic;
	}
	
	
}
