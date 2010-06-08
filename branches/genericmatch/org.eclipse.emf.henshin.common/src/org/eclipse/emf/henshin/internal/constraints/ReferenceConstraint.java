package org.eclipse.emf.henshin.internal.constraints;

import java.util.List;

import org.eclipse.emf.henshin.internal.matching.Variable;

public interface ReferenceConstraint<TNode> {

	public abstract boolean check(TNode sourceValue, TNode targetValue);

	public abstract DomainChange<TNode> reduceTargetDomain(TNode sourceValue,
			List<TNode> targetDomain);

	public abstract Variable<?, TNode> getTarget();
}
