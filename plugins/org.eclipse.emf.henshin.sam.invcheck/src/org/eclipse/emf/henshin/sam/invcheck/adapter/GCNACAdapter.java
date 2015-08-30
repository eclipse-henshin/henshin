package org.eclipse.emf.henshin.sam.invcheck.adapter;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.Collections;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;

public class GCNACAdapter extends EObjectImpl
		/* NegativeApplicationConditionImpl */ implements NegativeApplicationCondition {

	private NegatedCondition gc;
	private org.eclipse.emf.henshin.sam.model.samgraph.Graph nac;

	private static Map<GraphCondition, GCNACAdapter> instances = Collections
			.synchronizedMap(new WeakHashMap<GraphCondition, GCNACAdapter>());
	// private static Map<GraphCondition, GCNACAdapter> instances = new
	// ConcurrentHashMap<GraphCondition, GCNACAdapter>();

	public static GCNACAdapter getInstance(GraphCondition gc) {
		if (instances.get(gc) == null) {
			if (gc.eClass() == SamgraphconditionPackage.eINSTANCE.getNegatedCondition()) {
				if (((NegatedCondition) gc).getOperand().eClass() == SamgraphconditionPackage.eINSTANCE
						.getQuantification()) {
					Quantification tmp = (Quantification) ((NegatedCondition) gc).getOperand();
					GCNACAdapter instance = new GCNACAdapter((NegatedCondition) gc, tmp.getContext());
					// instances.put(gc, instance);
					return instance;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return instances.get(gc);
		}
	}

	public boolean equals(Object other) {
		if (other instanceof GCNACAdapter) {
			return this.gc == ((GCNACAdapter) other).gc;
		} else {
			return false;
		}

	}

	private GCNACAdapter(NegatedCondition gc, Graph nac) {
		this.nac = nac;
		this.gc = gc;
	}

	@Override
	public EList<Edge> getEdges() {
		return nac.getEdges();
	}

	@Override
	public EList<Node> getNodes() {
		return nac.getNodes();
	}

	@Override
	public Graph getGraph() {
		// TODO Auto-generated method stub
		EObject cont = gc.eContainer();
		while (cont != null) {
			if (SamgraphPackage.eINSTANCE.getGraph().isSuperTypeOf(cont.eClass())) {
				return (Graph) cont;
			} else {
				cont = cont.eContainer();
			}
		}
		return (Graph) cont;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGraph(Graph value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setName(String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public EList<Annotation> getAnnotations() {
		return gc.getAnnotations();
	}
}
