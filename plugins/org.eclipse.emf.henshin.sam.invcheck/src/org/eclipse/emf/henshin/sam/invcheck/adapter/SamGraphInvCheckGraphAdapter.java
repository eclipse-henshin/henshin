package org.eclipse.emf.henshin.sam.invcheck.adapter;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph;

public class SamGraphInvCheckGraphAdapter extends EObjectImpl implements GraphWithNacs {
	
	private EList<NegativeApplicationCondition> nacs;
	private org.eclipse.emf.henshin.sam.model.samgraph.Graph graph;
	
	private static Map<Graph, SamGraphInvCheckGraphAdapter> instances = Collections.synchronizedMap(new WeakHashMap<Graph, SamGraphInvCheckGraphAdapter>());
//	private static Map<Graph, SamGraphInvCheckGraphAdapter> instances = new ConcurrentHashMap<Graph, SamGraphInvCheckGraphAdapter>();
	
	public static SamGraphInvCheckGraphAdapter getInstance(Graph g) {
		if (instances.get(g) != null) {
			if (g.eClass() != SamrulesPackage.eINSTANCE.getRuleGraph()) {
				return instances.get(g);
			} else if (((RuleGraph) g).getCondition() == null) {
				return instances.get(g);
			} else if (((RuleGraph) g).getCondition().eClass() == SamgraphconditionPackage.eINSTANCE.getNegatedCondition()) {
				if (instances.get(g).nacs.size() == 1) {
					return instances.get(g);
				}
			} else if (((RuleGraph) g).getCondition().eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
				RuleGraph rg = (RuleGraph) g;
				if (((LogicalGCCoupling) rg.getCondition()).getOperands().size() == instances.get(g).nacs.size()) {
					boolean equal = true;
					for (GraphCondition gc : ((LogicalGCCoupling) rg.getCondition()).getOperands()) {
						if (!instances.get(g).nacs.contains(GCNACAdapter.getInstance(gc))) {
							equal = false;
						}
					}
					if (equal) {
						return instances.get(g);
					}
				}
			}
		}
		
		if (g.eClass() == SamrulesPackage.eINSTANCE.getRuleGraph()) {
			RuleGraph rG = (RuleGraph) g;
			if (rG.getCondition() != null) {
				if (rG.getCondition().eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
					LogicalGCCoupling conditions = (LogicalGCCoupling) rG.getCondition();
					EList<NegativeApplicationCondition> nacs = new BasicEList<NegativeApplicationCondition>();
					for (GraphCondition cond : conditions.getOperands()) {
						//NegativeApplicationCondition nac = (NegativeApplicationCondition) Platform.getAdapterManager().getAdapter(cond, NegativeApplicationCondition.class);
						NegativeApplicationCondition nac = (NegativeApplicationCondition) GCNACAdapter.getInstance(cond);
						if (nac != null) {
							//nacs.add((NegativeApplicationCondition) Platform.getAdapterManager().getAdapter(cond, NegativeApplicationCondition.class));
							nacs.add((NegativeApplicationCondition) GCNACAdapter.getInstance(cond));
						}						
					}
					SamGraphInvCheckGraphAdapter adapter = new SamGraphInvCheckGraphAdapter(nacs, g);
					//instances.put(g, adapter);
					return adapter;
				} else {
					EList<NegativeApplicationCondition> nacs = new BasicEList<NegativeApplicationCondition>();
					//NegativeApplicationCondition nac = (NegativeApplicationCondition) Platform.getAdapterManager().getAdapter(rG.getCondition(), NegativeApplicationCondition.class);
					NegativeApplicationCondition nac = (NegativeApplicationCondition) GCNACAdapter.getInstance(rG.getCondition());
					nacs.add(nac);
					SamGraphInvCheckGraphAdapter adapter = new SamGraphInvCheckGraphAdapter(nacs, g);
					//instances.put(g, adapter);
					return adapter;
				}
			} else {
				SamGraphInvCheckGraphAdapter adapter = new SamGraphInvCheckGraphAdapter(new BasicEList<NegativeApplicationCondition>(), g); 
				//instances.put(g, adapter);
				return adapter;
			}
		} else {
			SamGraphInvCheckGraphAdapter adapter = new SamGraphInvCheckGraphAdapter(new BasicEList<NegativeApplicationCondition>(), g); 
			//instances.put(g, adapter);
			return adapter;
		}
	}
	
	private SamGraphInvCheckGraphAdapter(EList<NegativeApplicationCondition> nacs, Graph g) {
		this.nacs = nacs;
		this.graph = g;
	}

	@Override
	public EList<NegativeApplicationCondition> getNacs() {
		return nacs;
	}

	@Override
	public OperationUse getAttributeCondition() {
		return graph.getAttributeCondition();
	}

	@Override
	public EList<Edge> getEdges() {
		return graph.getEdges();
	}

	@Override
	public EList<Node> getNodes() {
		return graph.getNodes();
	}

	@Override
	public TypeGraph getTypedOver() {
		return graph.getTypedOver();
	}

	@Override
	public void setAttributeCondition(OperationUse value) {
		graph.setAttributeCondition(value);
	}

	@Override
	public void setTypedOver(TypeGraph value) {
		graph.setTypedOver(value);		
	}

	@Override
	public EList<Annotation> getAnnotations() {
		// TODO Auto-generated method stub
		return graph.getAnnotations();
	}
}
