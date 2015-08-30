package org.eclipse.emf.henshin.sam.invcheck.filter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerPlugin;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;
import org.eclipse.emf.henshin.sam.model.samrules.GTS;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition;
import org.eclipse.emf.henshin.sam.paf.AbstractProducer;
import org.eclipse.emf.henshin.sam.paf.FilterDispatcher;
import org.eclipse.emf.henshin.sam.paf.IPipe;
import org.eclipse.emf.henshin.sam.paf.annotation.ResultDictEntry;

public class CombinationProducer extends AbstractProducer<CombinationProducer.Pair<Graph, GraphRule>> {

	private final static String name = "CombinationProducer";

	private boolean printDebug;

	private boolean alive = true;

	@ResultDictEntry(entryName = "generatedPairs")
	private int producedItems = 0;

	private Map<String, IPipe<Pair<Graph, GraphRule>>> outputPipes;

	public static class Pair<T, S> {
		public T first;
		public S second;

		public Pair(T f, S sec) {
			this.first = f;
			this.second = sec;
		}
	}

	private Graph[] properties;

	private GraphRule[] rules;

	private FilterDispatcher filterDispatcher;

	public CombinationProducer() {
	}

	public Graph[] getProperties() {
		return properties;
	}

	public void setProperties(Graph[] properties) {
		this.properties = properties;
	}

	public GraphRule[] getRules() {
		return rules;
	}

	public void setRules(GraphRule[] rules) {
		this.rules = rules;
	}

	public Map<String, IPipe<Pair<Graph, GraphRule>>> getOutputPipes() {
		if (this.outputPipes == null) {
			this.outputPipes = new HashMap<String, IPipe<Pair<Graph, GraphRule>>>();
		}
		return this.outputPipes;
	}

	protected void initData() {
		this.printDebug = this.getOption("printDebug");

		Assert.isNotNull(this.getFilterDispatcher());
		Assert.isNotNull(this.getFilterDispatcher().getFilterInput());

		final EObject theEObject = this.getFilterDispatcher().getFilterInput();
		Assert.isTrue(theEObject.eClass() == SamrulesPackage.eINSTANCE.getGTS());
		final GTS theGTS = (GTS) theEObject;

		List<Graph> forbidden = new BasicEList<Graph>();
		for (TypeGraphCondition g : theGTS.getTypes().get(0).getConditions()) {
			if (g.eClass() == SamgraphconditionPackage.eINSTANCE.getNegatedCondition()
					&& (isAssumedProperty((GraphCondition) g) == false)) {
				NegatedCondition nc = (NegatedCondition) g;
				if (nc.getOperand().eClass() == SamgraphconditionPackage.eINSTANCE.getQuantification()) {
					Quantification q = (Quantification) nc.getOperand();
					forbidden.add(q.getContext());
				}
			} else if (g.eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
				LogicalGCCoupling lgc = (LogicalGCCoupling) g;
				for (GraphCondition gc : lgc.getOperands()) {
					if (gc.eClass() == SamgraphconditionPackage.eINSTANCE.getNegatedCondition()
							&& (isAssumedProperty(gc) == false)) {
						NegatedCondition neg = (NegatedCondition) gc;
						if (neg.getOperand().eClass() == SamgraphconditionPackage.eINSTANCE.getQuantification()) {
							Quantification q = (Quantification) neg.getOperand();
							forbidden.add(q.getContext());
						}
					}
				}
			}
		}
		this.properties = forbidden.toArray(new Graph[forbidden.size()]);
		this.rules = theGTS.getRules().toArray(new GraphRule[theGTS.getRules().size()]);

	}

	/**
	 * Tests whether a given {@link GraphCondition} is a so called <emph>Assumed
	 * Property</emph><br />
	 * Technically a <code>GraphCondition</code> is considered as an assumed
	 * property if it owns an {@link Annotation}, whose <code>source</code>
	 * property is set to the value of
	 * {@link InvariantCheckerPlugin#ASSUMED_GUARANTEE_ANNOTATION_SOURCE}
	 * 
	 * @param cond
	 *            the <code>GraphCondition</code> to test
	 * @return <code>true</code> if the <code>GraphCondition</code> is a assumed
	 *         property, <code>false<code> otherwise.
	 */
	private boolean isAssumedProperty(GraphCondition cond) {
		boolean result = false;
		if (cond != null && !cond.getAnnotations().isEmpty()) {
			for (Annotation anno : cond.getAnnotations()) {
				if (InvariantCheckerPlugin.ASSUMED_GUARANTEE_ANNOTATION_SOURCE.equals(anno.getSource())) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	public String getName() {
		return CombinationProducer.name;
	}

	public void produce() {
		try {
			// Arrays.sort(this.rules, new GraphRuleComparator());
			for (int i = 0; i < this.properties.length; i++) {
				for (int j = 0; j < this.rules.length; j++) {
					Pair<Graph, GraphRule> pair = new Pair<Graph, GraphRule>(this.properties[i], this.rules[j]);
					producedItems++;
					if (printDebug) {
						this.println(">>> DEBUG >>> CombinationProducer >>> produced pair: (" + this.properties[i] + ";"
								+ this.rules[j] + ")");
					}
					for (Iterator<IPipe<Pair<Graph, GraphRule>>> iter = this.getOutputPipes().values().iterator(); iter
							.hasNext();) {
						// TODO: Avoid blocking when enquing in completely
						// filled IPipe
						IPipe<Pair<Graph, GraphRule>> nextPipe = iter.next();

						nextPipe.queue(pair);
						// check if we have to stop
						if (!this.filterDispatcher.isContinueComputation())
							return;
					}
				}
			}
		} catch (InterruptedException ie) {

		} finally {
			// close all connected outputpipes before leaving this method!!
			for (Iterator<IPipe<Pair<Graph, GraphRule>>> iter = this.getOutputPipes().values().iterator(); iter
					.hasNext();) {
				iter.next().close();
			}
		}
	}

	public void run() {
		initData();
		produce();
		System.err.println(Thread.currentThread().getName() + " produced " + producedItems + " items");
	}

	public FilterDispatcher getFilterDispatcher() {
		return this.filterDispatcher;
	}

	public void reset() {
		// TODO Auto-generated method stub

	}

	public boolean setFilterDispatcher(FilterDispatcher value) {
		boolean changed = false;
		if (this.filterDispatcher != value) {
			FilterDispatcher oldValue = this.filterDispatcher;
			this.filterDispatcher = null;
			if (oldValue != null) {
				oldValue.removeFromIDispatchable(this);
			}
			this.filterDispatcher = value;
			changed = true;
			if (value != null) {
				value.addToIDispatchable(this);
			}
		}
		return changed;
	}

	public boolean stillAlive() {
		return this.alive;
	}
}
