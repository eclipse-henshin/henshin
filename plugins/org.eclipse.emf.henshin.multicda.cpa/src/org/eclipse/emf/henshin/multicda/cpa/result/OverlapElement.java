package org.eclipse.emf.henshin.multicda.cpa.result;

import org.eclipse.emf.henshin.model.GraphElement;

import agg.xt_basis.Arc;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Node;

public class OverlapElement {

	public OverlapElement() {

	}

	public OverlapElement(GraphObject commonElementOfCriticalGraph, GraphElement elementInFirstRule,
			GraphElement elementInSecondRule) {
		super();
		this.commonElementOfCriticalGraph = commonElementOfCriticalGraph;
		this.elementInFirstRule = elementInFirstRule;
		this.elementInSecondRule = elementInSecondRule;
	}

	/**
	 * The critical element from within the AGG result.
	 */
	public GraphObject commonElementOfCriticalGraph;

	/**
	 * The occurrence of the critical element in the first rule.
	 */
	public GraphElement elementInFirstRule;

	/**
	 * The occurrence of the critical element in the second rule.
	 */
	public GraphElement elementInSecondRule;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return toString(commonElementOfCriticalGraph) + "\n" + elementInFirstRule.toString() + "\n"
				+ elementInSecondRule.toString();
	}

	/**
	 * @param elementInSecondRule2
	 * @return
	 */
	private String toString(GraphElement elementInSecondRule2) {
		// TODO Auto-generated method stub
		return null;
	}

	private String toString(GraphObject go) {
		if (go instanceof Arc)
			return toString((Arc) go);
		else
			return toString((Node) go);
	}

	private String toString(Arc arc) {
		String result = "";
		String t = arc.getType().getStringRepr();
		if (t.equals(""))
			t = "[unnamed]";
		String tSrc = toString((Node) arc.getSource());
		String tTrg = toString((Node) arc.getTarget());
		result = "(Arc: " + tSrc + "---" + t + "--->" + tTrg + ") ";
		return result;
	}

	private String toString(Node node) {
		String result = "";
		String t = node.getType().getStringRepr();
		result = " (Node: " + t + " : " + node.getObjectName() + ")  ";
		return result;
	}
}
