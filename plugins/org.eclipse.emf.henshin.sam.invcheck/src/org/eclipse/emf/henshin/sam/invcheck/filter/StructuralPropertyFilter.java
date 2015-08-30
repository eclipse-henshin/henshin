package org.eclipse.emf.henshin.sam.invcheck.filter;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;
import org.eclipse.emf.henshin.sam.model.samrules.GTS;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition;
import org.eclipse.emf.henshin.sam.paf.FilterSkeleton;
import org.eclipse.emf.henshin.sam.paf.annotation.ResultDictEntry;

public class StructuralPropertyFilter extends FilterSkeleton<GraphVerificationData, GraphVerificationData> {

	private ContainsPropertyHelper internalHelper;

	public StructuralPropertyFilter() {
		this.filterName = "StructuralPropertyFilter"; //$NON-NLS-1$
	}

	@ResultDictEntry(entryName = "checked")
	private int checked = 0;

	@ResultDictEntry(entryName = "discarded")
	private int discarded = 0;

	@ResultDictEntry(entryName = "nacDiscarded")
	private static int nacDiscard = 0;

	public static void incrementNacDiscard() {
		nacDiscard++;
	}

	// private boolean skip = true;

	@Override
	protected void initData() {
		Assert.isNotNull(this.getFilterDispatcher());
		Assert.isNotNull(this.getFilterDispatcher().getFilterInput());

		final EObject theEObject = this.getFilterDispatcher().getFilterInput();
		Assert.isTrue(theEObject.eClass() == SamrulesPackage.eINSTANCE.getGTS());
		final GTS theGTS = (GTS) theEObject;

		List<Graph> forbidden = new BasicEList<Graph>();
		for (TypeGraphCondition g : theGTS.getTypes().get(0).getConditions()) {
			if (g.eClass() == SamgraphconditionPackage.eINSTANCE.getNegatedCondition()) {
				NegatedCondition nc = (NegatedCondition) g;
				if (nc.getOperand().eClass() == SamgraphconditionPackage.eINSTANCE.getQuantification()) {
					Quantification q = (Quantification) nc.getOperand();
					forbidden.add(q.getContext());
				}
			} else if (g.eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
				LogicalGCCoupling lgc = (LogicalGCCoupling) g;
				for (GraphCondition gc : lgc.getOperands()) {
					if (gc.eClass() == SamgraphconditionPackage.eINSTANCE.getNegatedCondition()) {
						NegatedCondition neg = (NegatedCondition) gc;
						if (neg.getOperand().eClass() == SamgraphconditionPackage.eINSTANCE.getQuantification()) {
							Quantification q = (Quantification) neg.getOperand();
							forbidden.add(q.getContext());
						}
					}
				}
			}
		}

		Graph[] props = forbidden.toArray(new Graph[forbidden.size()]);
		// Graph[] guarantees = theVerificationTask.getNonexistent().toArray(new
		// Graph[theVerificationTask.getNonexistent().size()]);
		// Graph[] combinedArray = new Graph[props.length + guarantees.length];
		Graph[] combinedArray = new Graph[props.length];
		System.arraycopy(props, 0, combinedArray, 0, props.length);
		// System.arraycopy(guarantees, 0, combinedArray, props.length,
		// guarantees.length);
		this.internalHelper = new ContainsPropertyHelper(combinedArray);
	}

	public static void resetNacDiscard() {
		nacDiscard = 0;
	}

	public void produce() {
		checked++;
		/*
		 * if (true) { return; }
		 */
		/*
		 * if (true) { GraphVerificationData result = new GraphVerificationData(
		 * this.currentInput.pair, this.currentInput.sourceGraph,
		 * this.currentInput.targetGraph); //result.propertyGraphMappings = map;
		 * try { this.defaultOutputPipe.queue(result); } catch
		 * (InterruptedException e) { this.running = false; } return; }
		 */
		int tmp = nacDiscard;
		// System.out.println("spf " + this.currentInput.pair.second.getName() +
		// " " + ((NegatedCondition)
		// this.currentInput.pair.first.eContainer().eContainer()).getName());
		// System.out.println(this.currentInput.sourceGraph.getNodes().size() +
		// "::" + this.currentInput.sourceGraph.getEdges().size());
		if (this.internalHelper.findProperties(this.currentInput.sourceGraph)) {
			discarded++;
			// System.out.println(this.currentInput.pair.second.getName() + " "
			// + ((NegatedCondition)
			// this.currentInput.pair.first.eContainer().eContainer()).getName()
			// + " discarded");
			if (nacDiscard != tmp) {
				// System.out.println("spf " +
				// this.currentInput.pair.second.getName() + " " +
				// ((NegatedCondition)
				// this.currentInput.pair.first.eContainer().eContainer()).getName());
				// System.out.println(this.currentInput.sourceGraph.getNodes().size()
				// + "::" + this.currentInput.sourceGraph.getEdges().size());
			}
			return;
		} else {
			GraphVerificationData result = new GraphVerificationData(this.currentInput.pair,
					this.currentInput.sourceGraph, this.currentInput.targetGraph);
			// result.propertyGraphMappings = map;
			try {
				this.defaultOutputPipe.queue(result);
			} catch (InterruptedException e) {
				this.running = false;
			}
		}
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		this.filterName = "StructuralPropertyFilter";
	}

}
