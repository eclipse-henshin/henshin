package org.eclipse.emf.henshin.multicda.cpa.result;

import org.eclipse.emf.henshin.model.GraphElement;

import agg.xt_basis.GraphObject;

public class BoundaryNode extends OverlapElement {
	public BoundaryNode() {
		super();
	}
	
	public BoundaryNode(GraphObject commonElementOfCriticalGraph, GraphElement elementInFirstRule,
			GraphElement elementInSecondRule) {
		super(commonElementOfCriticalGraph, elementInFirstRule, elementInSecondRule);
	}

}
