package org.eclipse.emf.henshin.internal.constraints;

import java.util.List;

import org.eclipse.emf.henshin.common.util.GraphSkeleton;

public interface TypeConstraint<TType, TNode> {

	public abstract boolean check(TNode sourceValue);

	public abstract List<TNode> reduceDomain(List<TNode> sourceDomain, GraphSkeleton<TType, TNode> graph);

	public abstract TType getType();
}