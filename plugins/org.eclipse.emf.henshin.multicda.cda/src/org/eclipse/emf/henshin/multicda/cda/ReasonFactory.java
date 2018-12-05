package org.eclipse.emf.henshin.multicda.cda;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.ChangeConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.CreateConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.CreateEdgeDeleteNodeConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteConflictReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.ChangeDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.CreateDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.DeleteDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.DeleteEdgeDeleteNodeDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ChangeConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ChangeDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.CreateConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.CreateDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.CreateEdgeDeleteNodeConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteEdgeDeleteNodeDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cda.units.Span;
import org.eclipse.emf.henshin.multicda.cda.units.SymmetricReason;

public class ReasonFactory {
	private ReasonFactory() {

	}

	public static final ReasonFactory eINSTANCE = new ReasonFactory();

	public Reason createMinimalReason(Span reason) {
		if (reason instanceof Reason)
			return ((Reason) reason).setMinimalReason(true);
		else if (reason instanceof Atom)
			return createMinimalReason((Atom) reason);
		try {
			throw new ReasonNotSupportedByThisFactory(reason);
		} catch (ReasonNotSupportedByThisFactory e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param atom that should be transformed into minimal reason
	 * @return minimal Reason with span of the atom
	 */
	public Reason createMinimalReason(Atom atom) {
		if (atom instanceof DeleteConflictAtom)
			return new DeleteConflictReason(atom).setMinimalReason(true);
		if (atom instanceof CreateConflictAtom)
			return new CreateConflictReason(atom).setMinimalReason(true);
		if (atom instanceof ChangeConflictAtom)
			return new ChangeConflictReason(atom).setMinimalReason(true);
		if (atom instanceof DeleteDependencyAtom)
			return new DeleteDependencyReason(atom).setMinimalReason(true);
		if (atom instanceof CreateDependencyAtom)
			return new CreateDependencyReason(atom).setMinimalReason(true);
		if (atom instanceof ChangeDependencyAtom)
			return new ChangeDependencyReason(atom).setMinimalReason(true);

		if (atom instanceof CreateEdgeDeleteNodeConflictAtom)
			return new CreateEdgeDeleteNodeConflictReason(atom).setMinimalReason(true);
		if (atom instanceof DeleteEdgeDeleteNodeDependencyAtom)
			return new DeleteEdgeDeleteNodeDependencyReason(atom).setMinimalReason(true);
		try {
			throw new ReasonNotSupportedByThisFactory(atom);
		} catch (ReasonNotSupportedByThisFactory e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param atom that should be converted into dependency atom
	 * @return dependency atom
	 */
	public DependencyAtom createDependencyAtom(ConflictAtom atom) {
		if (atom instanceof DeleteConflictAtom)
			return new CreateDependencyAtom(atom);
		if (atom instanceof CreateConflictAtom)
			return new DeleteDependencyAtom(atom);
		if (atom instanceof ChangeConflictAtom)
			return new ChangeDependencyAtom(atom);
		if (atom instanceof CreateEdgeDeleteNodeConflictAtom)
			return new DeleteEdgeDeleteNodeDependencyAtom(atom);

		try {
			throw new ReasonNotSupportedByThisFactory(atom);
		} catch (ReasonNotSupportedByThisFactory e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param reason that should be converted into dependency reason
	 * @return dependency reason
	 */
	public Reason createDependencyReason(Reason reason) {
		if (!validDependency(reason))
			return null;
		if (reason instanceof SymmetricReason) {
			SymmetricReason r = (SymmetricReason) reason;
			return new SymmetricReason(createDependencyReason(r.getS1()), r.getS2());
		}
		if (reason instanceof DeleteConflictReason)
			return new CreateDependencyReason((DeleteConflictReason) reason);
		if (reason instanceof CreateConflictReason)
			return new DeleteDependencyReason(reason);
		if (reason instanceof ChangeConflictReason)
			return new ChangeDependencyReason(reason);
		if (reason instanceof CreateEdgeDeleteNodeConflictReason)
			return new DeleteEdgeDeleteNodeDependencyReason(reason);
		try {
			throw new ReasonNotSupportedByThisFactory(reason);
		} catch (ReasonNotSupportedByThisFactory e) {
			e.printStackTrace();
			return null;
		}
	}

	private boolean validDependency(Reason reason) {
		for (Mapping m : reason.getMappingsInRule1()) {
			Node n1L = m.getImage();
			Node n1R = n1L.getGraph().getRule().getMappings().getImage(n1L, null);
			String desc = reason.getRule1().getDescription();
			desc = desc == null ? "" : desc;
			if (n1R == null && Utils.isInverted(reason.getRule1())) {
				Node n2L = reason.getMappingIntoRule2(m.getOrigin()).getImage();
				Node n2R = n2L.getGraph().getRule().getMappings().getImage(n2L, null);
				if (n1L.getAttributes().isEmpty() && !n2L.getAttributes().isEmpty())
					return false;
			}
		}
		return true;
	}

	public Reason createMinimalDependencyReason(Reason reason) {
		Reason result = createDependencyReason(reason);
		if (result != null)
			result.setMinimalReason(true);
		return result;
	}

	public <T extends Reason> T createSymmetricReason(T reason, Set<Reason> s2) {
		if (reason instanceof DeleteEdgeDeleteNodeDependencyReason
				|| reason instanceof CreateEdgeDeleteNodeConflictReason)
			try {
				throw new ReasonNotSupportedByThisFactory(reason);
			} catch (ReasonNotSupportedByThisFactory e) {
				e.printStackTrace();
				return null;
			}
		return (T) new SymmetricReason(reason, s2);

	}

	public ConflictReason createDRReason(ConflictReason reason) {
		reason.setMinimalReason(false);
		return reason;
	}

	@SuppressWarnings("unchecked")
	public <T extends Span> T createCEDNReason(T reason) {
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

		Atom a = new DeleteConflictAtom(rule1Mappings, S1, rule2Mappings);

		T result = null;
		if (reason instanceof Reason) {
			result = (T) new CreateEdgeDeleteNodeConflictReason(a);
			((Reason) result).setMinimalReason(((Reason) reason).isMinimalReason());
		}
		return result;
	}

	public ConflictReason createForbidReason(Reason reason, Rule originalR1, Rule originalR2) {
		if (Utils.checkNcReason(reason, originalR1, originalR2)) {
			if (reason instanceof DeleteConflictReason)
				return (ConflictReason) new CreateConflictReason(reason, true);
			else if (reason instanceof CreateConflictReason)
				return (ConflictReason) new DeleteConflictReason(reason, true);
			else if (reason instanceof ChangeConflictReason)
				return (ConflictReason) new ChangeConflictReason(reason, true);
		}
		return null;
	}

	public Span createRequireReason(Span reason) {
		if (!(reason instanceof SymmetricReason) && !(reason instanceof CreateEdgeDeleteNodeConflictAtom)
				&& !(reason instanceof CreateEdgeDeleteNodeConflictReason)
				&& !(reason instanceof DeleteEdgeDeleteNodeDependencyAtom)
				&& !(reason instanceof DeleteEdgeDeleteNodeDependencyReason))
			reason.setRequire(true);
		return reason;
	}

	public static class ReasonNotSupportedByThisFactory extends Exception {
		private static final long serialVersionUID = 123121434L;

		public ReasonNotSupportedByThisFactory(Span reason) {
			super("The " + reason.NAME + " is not supported by this Factory yet");
		}

	}

	public ConflictReason createJoinedReason(Reason reason1, Reason reason2) {

		Map<Node, Node> s2ToS1 = Utils.getS2toS1Map(reason1, reason2);
		if (s2ToS1 == null) // is null iff we cannot join them
			return null;

		// Copy G1 and its mappings to rules 1 and 2
		Copier g1ToCopy = new Copier();
		Graph graph1Copy = (Graph) g1ToCopy.copy(reason1.getGraph());
		g1ToCopy.copyReferences();

		Copier mappingS1Copier = new Copier();
		Collection<Mapping> mappingsS1R1copies = mappingS1Copier.copyAll(reason1.getMappingsInRule1());
		mappingS1Copier.copyReferences();
		Collection<Mapping> mappingS1R2copies = mappingS1Copier.copyAll(reason1.getMappingsInRule2());
		mappingS1Copier.copyReferences();
		for (Mapping mapping : mappingsS1R1copies) {
			Node newOrigin = (Node) g1ToCopy.get(mapping.getOrigin());
			mapping.setOrigin(newOrigin);
		}
		for (Mapping mapping : mappingS1R2copies) {
			Node newOrigin = (Node) g1ToCopy.get(mapping.getOrigin());
			mapping.setOrigin(newOrigin);
		}

		// Copy G1 and its mappings to rules 1 and 2
		Copier g2toCopy = new Copier();
		Graph graph2Copy = (Graph) g2toCopy.copy(reason2.getGraph());
		g2toCopy.copyReferences();

		// MAPPINGS of Graph2:
		Copier mappingsS2Copier = new Copier();
		Collection<Mapping> mappingsS2R1copies = mappingsS2Copier.copyAll(reason2.getMappingsInRule1());
		mappingsS2Copier.copyReferences();
		Collection<Mapping> mappingS2R2copies = mappingsS2Copier.copyAll(reason2.getMappingsInRule2());
		mappingsS2Copier.copyReferences();

		for (Mapping mapping : mappingsS2R1copies) {
			Node newOrigin = (Node) g2toCopy.get(mapping.getOrigin());
			mapping.setOrigin(newOrigin);
		}
		for (Mapping mapping : mappingS2R2copies) {
			Node newOrigin = (Node) g2toCopy.get(mapping.getOrigin());
			mapping.setOrigin(newOrigin);
		}

		// Identify redundant nodes in G2's copy, and reroute
		// their adjacent edges to G1's copy
		List<Node> toDeleteInG2Copy = new LinkedList<Node>();
		for (Edge edgeG2 : reason2.getGraph().getEdges()) {
			Edge edgeG2Copy = (Edge) g2toCopy.get(edgeG2);
			if (s2ToS1.containsKey(edgeG2.getSource())) {
				Node nodeInGraph1 = s2ToS1.get(edgeG2.getSource());
				Node newSourceG1Copy = (Node) g1ToCopy.get(nodeInGraph1);
				toDeleteInG2Copy.add(edgeG2Copy.getSource());
				edgeG2Copy.setSource(newSourceG1Copy);
			}
			if (s2ToS1.containsKey(edgeG2.getTarget())) {
				Node nodeInGraph1 = s2ToS1.get(edgeG2.getTarget());
				Node newTargetG1Copy = (Node) g1ToCopy.get(nodeInGraph1);
				toDeleteInG2Copy.add(edgeG2Copy.getTarget());
				edgeG2Copy.setTarget(newTargetG1Copy);
			}
		}

		// Remove redundant nodes from G2's copy and their mappings into
		// rules 1 and 2
		Utils.removeRedundantNodes(graph2Copy, mappingsS2R1copies, mappingS2R2copies, toDeleteInG2Copy);

		// Add nodes, edges, and mappings to those of G1
		graph1Copy.getNodes().addAll(graph2Copy.getNodes());
		graph1Copy.getEdges().addAll(graph2Copy.getEdges());
		Set<Mapping> mappingsToR1 = new HashSet<Mapping>();
		mappingsToR1.addAll(mappingsS1R1copies);
		mappingsToR1.addAll(mappingsS2R1copies);
		Set<Mapping> mappingsToR2 = new HashSet<Mapping>();
		mappingsToR2.addAll(mappingS1R2copies);
		mappingsToR2.addAll(mappingS2R2copies);

		Set<Reason> originMCRs = new HashSet<>();
		if (reason1.isMinimalReason()) {
			originMCRs.add(reason1);
		} else {
			originMCRs.addAll(reason1.getOriginMCRs());
		}
		if (reason2.isMinimalReason()) {
			originMCRs.add(reason2);
		} else {
			originMCRs.addAll(reason2.getOriginMCRs());
		}
		if (reason1.getClass() == reason2.getClass())
			if (reason1 instanceof ChangeConflictReason)
				return new ChangeConflictReason(mappingsToR1, graph1Copy, mappingsToR2, originMCRs);
			else if (reason1 instanceof DeleteConflictReason)
				return new DeleteConflictReason(mappingsToR1, graph1Copy, mappingsToR2, originMCRs);
			else if (reason1 instanceof CreateConflictReason)
				return new CreateConflictReason(mappingsToR1, graph1Copy, mappingsToR2, originMCRs);
		return null;
	}

	public Atom createForbidAtom(Atom a, Rule originalR1, Rule originalR2) {
		if (Utils.checkNcReason(a, originalR1, originalR2)) {
			if (a instanceof DeleteConflictAtom)
				return new CreateConflictAtom(a).setForbid(true);
			else if (a instanceof CreateConflictAtom)
				return new DeleteConflictAtom(a).setForbid(true);
			else if (a instanceof ChangeConflictAtom)
				return new ChangeConflictAtom(a).setForbid(true);
		}
		return null;
	}

	public Atom createRequireAtom(Atom a, Rule originalR1, Rule originalR2) {
		// if (reqReason(a))
		if (Utils.checkNcReason(a, originalR1, originalR2))
			return (Atom) a.setRequire(true);
		return null;
	}
}
