package org.eclipse.emf.henshin.multicda.cda;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.ChangeAttrConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.CreateEdgeDeleteNodeConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.CreateForbidConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteAttrConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteDeleteConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteReadConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteRequireConflictReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.ChangeAttrDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.CreateDeleteDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.CreateReadDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.CreateRequireDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.DeleteAttrDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.DeleteEdgeDeleteNodeDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.DeleteForbidDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.CreateEdgeDeleteNodeConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.CreateUseDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteUseConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ForbidConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ForbidDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.RequireConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.RequireDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.DoubleSpan;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalChangeAttrConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalChangeAttrDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalCreateDeleteDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalCreateEdgeDeleteNodeConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalCreateForbidConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalCreateReadDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalCreateRequireDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDeleteAttrConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDeleteAttrDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDeleteDeleteConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDeleteEdgeDeleteNodeDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDeleteForbidDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDeleteReadConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDeleteRequireConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

public class ReasonFactory {
	private ReasonFactory() {

	}

	public static final ReasonFactory eINSTANCE = new ReasonFactory();

	public MinimalReason createMinimalReason(Reason reason) {
		if (reason instanceof DeleteUseConflictAtom || reason instanceof MinimalDeleteReadConflictReason)
			return new MinimalDeleteReadConflictReason(reason);
		if (reason instanceof CreateUseDependencyAtom || reason instanceof MinimalCreateReadDependencyReason)
			return new MinimalCreateReadDependencyReason(reason);
		if (reason instanceof ForbidConflictAtom || reason instanceof MinimalCreateForbidConflictReason)
			return new MinimalCreateForbidConflictReason(reason);
		if (reason instanceof ForbidDependencyAtom || reason instanceof MinimalDeleteForbidDependencyReason)
			return new MinimalDeleteForbidDependencyReason(reason);
		if (reason instanceof RequireConflictAtom || reason instanceof MinimalDeleteRequireConflictReason)
			return new MinimalDeleteRequireConflictReason(reason);
		if (reason instanceof RequireDependencyAtom || reason instanceof MinimalCreateRequireDependencyReason)
			return new MinimalCreateRequireDependencyReason(reason);
		if (reason instanceof MinimalCreateDeleteDependencyReason)
			return new MinimalCreateDeleteDependencyReason((DoubleSpan) reason);
		if (reason instanceof MinimalDeleteDeleteConflictReason)
			return new MinimalDeleteDeleteConflictReason((DoubleSpan) reason);
		return null;
	}

	public ConflictReason createDependencyAtom(ConflictReason reason) {
		return null;
	}

	public DependencyAtom createDependencyAtom(ConflictAtom atom) {
		if (atom instanceof DeleteUseConflictAtom)
			return new CreateUseDependencyAtom(atom);
		if (atom instanceof ForbidConflictAtom)
			return new ForbidDependencyAtom(atom);
		if (atom instanceof RequireConflictAtom)
			return new RequireDependencyAtom(atom);
		return null;
	}

	public MinimalDependencyReason createMinimalDependencyReason(MinimalConflictReason cr) {
		if (cr instanceof MinimalDeleteReadConflictReason)
			return new MinimalCreateReadDependencyReason(cr);
		if (cr instanceof MinimalDeleteDeleteConflictReason)
			return new MinimalCreateDeleteDependencyReason((DoubleSpan) cr);
		if (cr instanceof MinimalCreateForbidConflictReason)
			return new MinimalDeleteForbidDependencyReason(cr);
		if (cr instanceof MinimalDeleteRequireConflictReason)
			return new MinimalCreateRequireDependencyReason(cr);
		if (cr instanceof MinimalChangeAttrConflictReason)
			return new MinimalChangeAttrDependencyReason(cr);
		if (cr instanceof MinimalDeleteAttrConflictReason)
			return new MinimalDeleteAttrDependencyReason(cr);
		if (cr instanceof MinimalCreateEdgeDeleteNodeConflictReason)
			return new MinimalDeleteEdgeDeleteNodeDependencyReason(cr);
		return null;
	}

	public DependencyReason createDependencyReason(ConflictReason cr) {
		if (cr instanceof DeleteReadConflictReason)
			return new CreateReadDependencyReason(cr);
		if (cr instanceof DeleteDeleteConflictReason)
			return new CreateDeleteDependencyReason((DoubleSpan) cr);
		if (cr instanceof CreateForbidConflictReason)
			return new DeleteForbidDependencyReason(cr);
		if (cr instanceof DeleteRequireConflictReason)
			return new CreateRequireDependencyReason(cr);
		if (cr instanceof ChangeAttrConflictReason)
			return new ChangeAttrDependencyReason(cr);
		if (cr instanceof DeleteAttrConflictReason)
			return new DeleteAttrDependencyReason(cr);
		if (cr instanceof CreateEdgeDeleteNodeConflictReason)
			return new DeleteEdgeDeleteNodeDependencyReason(cr);
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends Reason> T createDDReason(T s1, Set<Reason> s2) {
		if (s1 instanceof MinimalConflictReason || s1 instanceof MinimalDeleteAttrConflictReason)
			return (T) new MinimalDeleteDeleteConflictReason(s1, s2);
		if (s1 instanceof ConflictReason || s1 instanceof MinimalChangeAttrConflictReason)
			return (T) new DeleteDeleteConflictReason(s1, s2);
		return null;
	}

	public ConflictReason createDRReason(MinimalConflictReason s1) {
		if (s1 instanceof MinimalDeleteReadConflictReason)
			return new DeleteReadConflictReason(s1);
		if (s1 instanceof MinimalChangeAttrConflictReason)
			return new ChangeAttrConflictReason(s1);
		if (s1 instanceof MinimalDeleteAttrConflictReason)
			return new DeleteAttrConflictReason(s1);
		return null;
	}


	@SuppressWarnings("unchecked")
	public <T extends Reason> T createCEDNReason(Reason reason) {
		Graph S1 = HenshinFactory.eINSTANCE.createGraph();
		Set<Mapping> rule1Mappings = new HashSet<Mapping>();
		Set<Mapping> rule2Mappings = new HashSet<Mapping>();
		Map<Node, Node> map = new HashMap<>();
		for (Mapping m1 : reason.mappingsInRule2) {
			Mapping m2 = reason.getMappingIntoRule1(m1.getOrigin());
			map.put(m1.getOrigin(),
					Utils.addNodeToGraph(m1.getImage(), m2.getImage(), S1, rule1Mappings, rule2Mappings));
		}
		for (Edge e : reason.getGraph().getEdges()) {
			Node s = map.get(e.getSource());
			Node t = map.get(e.getTarget());
			S1.getEdges().add(HenshinFactory.eINSTANCE.createEdge(s, t, e.getType()));
		}

		Atom a = new DeleteUseConflictAtom(rule1Mappings, S1, rule2Mappings);

		if (reason instanceof Atom)
			return (T) new CreateEdgeDeleteNodeConflictAtom(a);
		if (reason instanceof MinimalReason)
			return (T) new MinimalCreateEdgeDeleteNodeConflictReason(a);
		else
			return (T) new CreateEdgeDeleteNodeConflictReason(a);

	}
}
