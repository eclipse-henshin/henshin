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
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.CreateAttrConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.CreateEdgeDeleteNodeConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.CreateEdgeRequireNodeConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.CreateForbidConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteAttrConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteEdgeForbidNodeConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteReadConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteRequireConflictReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.ChangeAttrDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.CreateAttrDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.CreateEdgeForbidNodeDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.CreateReadDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.CreateRequireDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.DeleteAttrDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.DeleteEdgeDeleteNodeDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.DeleteEdgeRequireNodeDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.DeleteForbidDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ChangeAttrConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ChangeAttrDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.CreateAttrConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.CreateAttrDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.CreateUseDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteAttrConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteAttrDependencyAtom;
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
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalCreateAttrConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalCreateAttrDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalCreateEdgeDeleteNodeConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalCreateEdgeForbidNodeDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalCreateEdgeRequireNodeConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalCreateForbidConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalCreateReadDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalCreateRequireDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDeleteAttrConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDeleteAttrDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDeleteEdgeDeleteNodeDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDeleteEdgeForbidNodeConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDeleteEdgeRequireNodeDependencyReason;
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
		if (reason instanceof MinimalDeleteAttrConflictReason || reason instanceof DeleteAttrConflictAtom)
			return new MinimalDeleteAttrConflictReason(reason);
		if (reason instanceof MinimalCreateAttrConflictReason || reason instanceof CreateAttrConflictAtom)
			return new MinimalCreateAttrConflictReason(reason);
		if (reason instanceof MinimalChangeAttrConflictReason || reason instanceof ChangeAttrConflictAtom)
			return new MinimalChangeAttrConflictReason(reason);
		try {
			throw new ReasonNotSupportedByThisFactory(reason);
		} catch (ReasonNotSupportedByThisFactory e) {
			e.printStackTrace();
			return null;
		}
	}

	public DependencyAtom createDependencyAtom(ConflictAtom atom) {
		if (atom instanceof DeleteUseConflictAtom)
			return new CreateUseDependencyAtom(atom);
		if (atom instanceof ForbidConflictAtom)
			return new ForbidDependencyAtom(atom);
		if (atom instanceof RequireConflictAtom)
			return new RequireDependencyAtom(atom);

		if (atom instanceof DeleteAttrConflictAtom)
			return new CreateAttrDependencyAtom(atom);
		if (atom instanceof CreateAttrConflictAtom)
			return new DeleteAttrDependencyAtom(atom);
		if (atom instanceof ChangeAttrConflictAtom)
			return new ChangeAttrDependencyAtom(atom);
		try {
			throw new ReasonNotSupportedByThisFactory(atom);
		} catch (ReasonNotSupportedByThisFactory e) {
			e.printStackTrace();
			return null;
		}
	}

	public MinimalDependencyReason createMinimalDependencyReason(MinimalConflictReason reason) {
		if (reason instanceof MinimalDeleteReadConflictReason)
			return new MinimalCreateReadDependencyReason(reason);
		if (reason instanceof MinimalCreateForbidConflictReason)
			return new MinimalDeleteForbidDependencyReason(reason);
		if (reason instanceof MinimalDeleteRequireConflictReason)
			return new MinimalCreateRequireDependencyReason(reason);
		if (reason instanceof MinimalChangeAttrConflictReason)
			return new MinimalChangeAttrDependencyReason(reason);
		if (reason instanceof MinimalCreateAttrConflictReason)
			return new MinimalCreateAttrDependencyReason(reason);
		if (reason instanceof MinimalDeleteAttrConflictReason)
			return new MinimalDeleteAttrDependencyReason(reason);
		if (reason instanceof MinimalCreateEdgeDeleteNodeConflictReason)
			return new MinimalDeleteEdgeDeleteNodeDependencyReason(reason);
		if (reason instanceof MinimalCreateEdgeRequireNodeConflictReason)
			return new MinimalDeleteEdgeRequireNodeDependencyReason(reason);
		if (reason instanceof MinimalDeleteEdgeForbidNodeConflictReason)
			return new MinimalCreateEdgeForbidNodeDependencyReason(reason);
		try {
			throw new ReasonNotSupportedByThisFactory(reason);
		} catch (ReasonNotSupportedByThisFactory e) {
			e.printStackTrace();
			return null;
		}
	}

	public DependencyReason createDependencyReason(ConflictReason reason) {
		if (reason instanceof DeleteReadConflictReason)
			return new CreateReadDependencyReason((DeleteReadConflictReason) reason);
		if (reason instanceof CreateForbidConflictReason)
			return new DeleteForbidDependencyReason(reason);
		if (reason instanceof DeleteRequireConflictReason)
			return new CreateRequireDependencyReason(reason);
		if (reason instanceof CreateAttrConflictReason)
			return new DeleteAttrDependencyReason((CreateAttrConflictReason) reason);
		if (reason instanceof DeleteAttrConflictReason)
			return new CreateAttrDependencyReason((DeleteAttrConflictReason) reason);
		if (reason instanceof ChangeAttrConflictReason)
			return new ChangeAttrDependencyReason((ChangeAttrConflictReason) reason);
		if (reason instanceof CreateEdgeDeleteNodeConflictReason)
			return new DeleteEdgeDeleteNodeDependencyReason(reason);
		if (reason instanceof CreateEdgeRequireNodeConflictReason)
			return new DeleteEdgeRequireNodeDependencyReason(reason);
		if (reason instanceof DeleteEdgeForbidNodeConflictReason)
			return new CreateEdgeForbidNodeDependencyReason(reason);
		try {
			throw new ReasonNotSupportedByThisFactory(reason);
		} catch (ReasonNotSupportedByThisFactory e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Reason> T createDDReason(T reason, Set<Reason> s2) {

		if (reason instanceof MinimalDeleteReadConflictReason)
			return (T) new MinimalDeleteReadConflictReason(reason, s2);
		if (reason instanceof MinimalDeleteAttrConflictReason)
			return (T) new MinimalDeleteAttrConflictReason(reason, s2);
		if (reason instanceof MinimalChangeAttrConflictReason)
			return (T) new MinimalChangeAttrConflictReason(reason, s2);

		if (reason instanceof DeleteReadConflictReason)
			return (T) new DeleteReadConflictReason(reason, s2);
		if (reason instanceof DeleteAttrConflictReason)
			return (T) new DeleteAttrConflictReason(reason, s2);
		if (reason instanceof ChangeAttrConflictReason)
			return (T) new ChangeAttrConflictReason(reason, s2);

		try {
			throw new ReasonNotSupportedByThisFactory(reason);
		} catch (ReasonNotSupportedByThisFactory e) {
			e.printStackTrace();
			return null;
		}
	}

	public ConflictReason createDRReason(MinimalConflictReason reason) {
		if (reason instanceof MinimalDeleteReadConflictReason)
			return new DeleteReadConflictReason(reason);
		if (reason instanceof MinimalCreateAttrConflictReason)
			return new CreateAttrConflictReason(reason);
		if (reason instanceof MinimalChangeAttrConflictReason)
			return new ChangeAttrConflictReason(reason);
		if (reason instanceof MinimalDeleteAttrConflictReason)
			return new DeleteAttrConflictReason(reason);
		try {
			throw new ReasonNotSupportedByThisFactory(reason);
		} catch (ReasonNotSupportedByThisFactory e) {
			e.printStackTrace();
			return null;
		}
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

		if (reason instanceof MinimalReason)
			return (T) new MinimalCreateEdgeDeleteNodeConflictReason(a);
		else
			return (T) new CreateEdgeDeleteNodeConflictReason(a);

	}

	public ConflictReason createForbidReason(Reason reason) {
		if (reason.isDoubleReason()) {
			if (reason instanceof ChangeAttrConflictReason || reason instanceof CreateAttrConflictReason) {
				Set<Reason> s2Set = new HashSet<>();
				for (Reason r : ((ChangeAttrConflictReason) reason).getS2Set())
					s2Set.add(new CreateForbidConflictReason(r));
				if (reason instanceof ChangeAttrConflictReason)
					return new ChangeAttrConflictReason(((DoubleSpan) reason).getS1(), s2Set);
				else
					return new CreateAttrConflictReason(((DoubleSpan) reason).getS1(), s2Set);
			}
			if (reason instanceof DoubleSpan && ((DoubleSpan) reason).isDoubleSpan())
				return new CreateForbidConflictReason(reason);
		}
		if (reason instanceof CreateEdgeDeleteNodeConflictReason)
			return new DeleteEdgeForbidNodeConflictReason(reason);
		return null;
	}

	public ConflictReason createRequireReason(Reason reason) {
		if (reason.isDoubleReason()) {
			if (reason instanceof ChangeAttrConflictReason || reason instanceof CreateAttrConflictReason) {
				Set<Reason> s2Set = new HashSet<>();
				for (Reason r : ((ChangeAttrConflictReason) reason).getS2Set())
					s2Set.add(new DeleteRequireConflictReason(r));
				if (reason instanceof ChangeAttrConflictReason)
					return new ChangeAttrConflictReason(((DoubleSpan) reason).getS1(), s2Set);
				else
					return new CreateAttrConflictReason(((DoubleSpan) reason).getS1(), s2Set);
			}
			if (reason instanceof DoubleSpan && ((DoubleSpan) reason).isDoubleSpan())
				return new DeleteRequireConflictReason(reason);
		}
		if (reason instanceof CreateEdgeDeleteNodeConflictReason)
			return new CreateEdgeRequireNodeConflictReason(reason);
		return null;
	}

	public static class ReasonNotSupportedByThisFactory extends Exception {
		public ReasonNotSupportedByThisFactory(Reason reason) {
			super("The " + reason.NAME + " is not supported by this Factory yet");
		}
	}
}
