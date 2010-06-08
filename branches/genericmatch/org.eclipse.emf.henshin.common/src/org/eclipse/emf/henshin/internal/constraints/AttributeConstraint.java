package org.eclipse.emf.henshin.internal.constraints;

import java.util.List;

public interface AttributeConstraint<T> {
	public boolean check(T sourceValue);
	
	public void reduceDomain(List<T> sourceDomain);
}
