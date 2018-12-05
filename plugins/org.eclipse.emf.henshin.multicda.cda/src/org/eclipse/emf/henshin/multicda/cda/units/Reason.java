package org.eclipse.emf.henshin.multicda.cda.units;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.multicda.cda.ReasonFactory;
import org.eclipse.emf.henshin.multicda.cda.conflict.EssentialConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ConflictAtom;

/**
 * Base class of reasons
 * @author Jevgenij Huebert
 *
 */
public abstract class Reason extends Span {
	protected Set<Reason> originMCRs = new HashSet<>();
	protected Set<Atom> containedConflictAtom = new HashSet<>();
	protected boolean isMinimalReason = false;

	public void addContainedConflictAtom(Atom conflictAtom) {
		containedConflictAtom.add(conflictAtom);
	}

	public Set<Atom> getContainedConflictAtoms() {
		return containedConflictAtom;
	}

	/**
	 * @return the originMCRs
	 */
	public Set<Reason> getOriginMCRs() {
		return originMCRs;
	}

	public final boolean isMinimalReason() {
		return isMinimalReason;
	}

	public final Reason setMinimalReason(boolean isMinimalReason) {
		this.isMinimalReason = isMinimalReason;
		return this;
	}

	public Reason(Reason reason) {
		this(reason, reason.ID, reason.NAME);
	}

	protected Reason(Span reason, String tag, String name) {
		super(reason, tag, name);
		originMCRs = new HashSet<>();
		if (reason instanceof Reason && ((Reason) reason).isMinimalReason()) {
			isMinimalReason = ((Reason) reason).isMinimalReason;
			originMCRs.add((Reason) reason);
			containedConflictAtom.addAll(((Reason) reason).getContainedConflictAtoms());
		}

	}

	protected Reason(Set<Mapping> mappingsOfNewSpanInRule1, Graph graph1Copy, Set<Mapping> mappingsOfNewSpanInRule2,
			Set<Reason> originMCRs, String tag, String name) {
		super(mappingsOfNewSpanInRule1, graph1Copy, mappingsOfNewSpanInRule2, tag, name);
		this.originMCRs = originMCRs;
	}

	protected Reason(Set<Mapping> hashSet, Graph graph, Set<Mapping> hashSet2, String tag, String name) {
		super(hashSet, graph, hashSet2, tag, name);
	}

	protected Reason(Reason extSpan, Node origin, Node image, String tag, String name) {
		super(extSpan, origin, image, tag, name);
	}

	public Set<Atom> getCoveredEdgeConflictAtoms() {
		Set<Atom> edgeConflictAtoms = new HashSet<>();
		for (Reason mcr : originMCRs) {
			Set<Atom> containedConflictAtoms = mcr.getContainedConflictAtoms();
			for (Atom conflictAtom : containedConflictAtoms) {
				if (conflictAtom.isDeleteEdgeConflictAtom())
					edgeConflictAtoms.add(conflictAtom);
			}
		}
		return edgeConflictAtoms;
	}

	public Set<EssentialConflictReason> getAllDerivedConflictReasons(Set<ConflictAtom> uncoveredConflictAtoms) {
		Set<EssentialConflictReason> result = new HashSet<EssentialConflictReason>();
		if (!(this instanceof EssentialConflictReason)) {// this.toShortString()
			EssentialConflictReason conflictReasonWithoutBA = new EssentialConflictReason(this);
			result.add(conflictReasonWithoutBA);
		}

		for (ConflictAtom uncoveredCA : uncoveredConflictAtoms) {
			Set<Node> usedR1 = getUsedNodesOfR1();
			Set<Node> usedR2 = getUsedNodesOfR2();

			EList<Node> nodesOfUncoveredCA = uncoveredCA.getGraph().getNodes();
			if (nodesOfUncoveredCA.size() <= 1)
				return result;
			Node node1 = nodesOfUncoveredCA.get(0);
			Node node2 = nodesOfUncoveredCA.get(1);

			List<Node> potentialUsesN1R2 = new LinkedList<Node>(getRule2().getLhs().getNodes(node1.getType()));
			potentialUsesN1R2.removeAll(usedR2);
			boolean node1UsedInR1 = usedR1.contains(uncoveredCA.getMappingIntoRule1(node1).getImage());
			boolean node1UsedInR2 = usedR2.contains(uncoveredCA.getMappingIntoRule2(node1).getImage());
			if (!node1UsedInR1 && !node1UsedInR2) {
				for (Node potentialUseN1R2 : potentialUsesN1R2) {
					processPotentialUsesN1R2(uncoveredConflictAtoms, uncoveredCA, node1, node2, usedR2,
							potentialUseN1R2, result);
				}
			}

			List<Node> potentialUseNodesN2AloneR2 = new LinkedList<Node>(getRule2().getLhs().getNodes(node2.getType()));
			potentialUsesN1R2.removeAll(usedR2);
			boolean node2AlreadyUsedInR1 = usedR1.contains(uncoveredCA.getMappingIntoRule1(node2).getImage());
			boolean node2AlreadyUsedInR2 = usedR2.contains(uncoveredCA.getMappingIntoRule2(node2).getImage());
			if (!node2AlreadyUsedInR1 & !node2AlreadyUsedInR2) {
				for (Node potentialUseN2R2 : potentialUseNodesN2AloneR2) {
					extendCR(this, uncoveredConflictAtoms, uncoveredCA, node2, potentialUseN2R2, result);
				}
			}
		}

		return result;
	}

	private EssentialConflictReason extendCR(Reason original, Set<ConflictAtom> byInitialReasonUncoveredConflictAtoms,
			ConflictAtom uncoveredCA, Node node2, Node potentialUseN2R2, Set<EssentialConflictReason> result) {
		boolean stop = checkStoppingCriterion(uncoveredCA, potentialUseN2R2);
		if (!stop) {
			EssentialConflictReason extendedCR = new EssentialConflictReason(original, node2, potentialUseN2R2,
					uncoveredCA);
			result.add(extendedCR);
			Set<ConflictAtom> remainingUncoveredCAs = new HashSet<ConflictAtom>(byInitialReasonUncoveredConflictAtoms);
			remainingUncoveredCAs.remove(uncoveredCA);
			Set<EssentialConflictReason> recursivelyDerivedCRs = extendedCR
					.getAllDerivedConflictReasons(remainingUncoveredCAs);
			result.addAll(recursivelyDerivedCRs);
			return extendedCR;
		} else
			return null;
	}

	private void processPotentialUsesN1R2(Set<ConflictAtom> uncoveredCAs, ConflictAtom uncoveredCA, Node node1,
			Node node2, Set<Node> usedR2, Node potentialUseN1R2, Set<EssentialConflictReason> result) {

		EssentialConflictReason extendedCR = extendCR(this, uncoveredCAs, uncoveredCA, node1, potentialUseN1R2, result);

		if (extendedCR != null) {

			List<Node> potentialUsesN2R2 = new LinkedList<Node>(getRule2().getLhs().getNodes(node2.getType()));
			potentialUsesN2R2.removeAll(usedR2);
			potentialUsesN2R2.remove(potentialUseN1R2);

			Set<Node> usedR1ExtendedCR = extendedCR.getUsedNodesOfR1();
			Set<Node> usedR2ExtendedCR = extendedCR.getUsedNodesOfR2();
			for (Node potentialUseN2N2 : potentialUsesN2R2) {
				boolean usedInR1 = usedR1ExtendedCR.contains(uncoveredCA.getMappingIntoRule1(node2).getImage());
				boolean usedInR2 = usedR2ExtendedCR.contains(potentialUseN2N2);
				if (!usedInR1 && !usedInR2) {
					boolean node1MatchedOnCAOrigin = uncoveredCA.getMappingIntoRule2(node1)
							.getImage() == potentialUseN1R2;
					boolean node2MatchedOnCAOrigin = uncoveredCA.getMappingIntoRule2(node2)
							.getImage() == potentialUseN2N2;
					if (!(node1MatchedOnCAOrigin && node2MatchedOnCAOrigin)) {
						boolean stop2 = checkStoppingCriterion(uncoveredCA, potentialUseN2N2);
						if (!stop2) {

							extendCR(extendedCR, uncoveredCAs, uncoveredCA, node2, potentialUseN2N2, result);
						}
					}
				}
			}
		}

	}

	protected Set<Node> getUsedNodesOfR1() {
		Set<Node> usedNodesOfR1 = new HashSet<Node>();
		if (graph.getNodes().size() != mappingsInRule1.size()) {
			System.err.println("Error!");
		}
		for (Mapping mappingInRule1 : mappingsInRule1) {
			usedNodesOfR1.add(mappingInRule1.getImage());
		}
		return usedNodesOfR1;
	}

	protected Set<Node> getUsedNodesOfR2() {
		Set<Node> usedNodesOfR2 = new HashSet<Node>();
		if (graph.getNodes().size() != mappingsInRule2.size()) {
			System.err.println("Error!");
		}
		for (Mapping mappingInRule2 : mappingsInRule2) {
			usedNodesOfR2.add(mappingInRule2.getImage());
		}
		return usedNodesOfR2;
	}

	private boolean checkStoppingCriterion(ConflictAtom uncoveredCA, Node potentialUseInR2) {

		boolean potentialUseNodeCompletesContainedBA = false;
		boolean secondUncoveredCANodeIsAlreadyPresent = false;

		if (this instanceof EssentialConflictReason) {
			Set<Node> lhsNodesOfR2UsedByAdditionalCAs = ((EssentialConflictReason) this)
					.getLhsNodesOfR2UsedByAdditionalCAs();
			if (lhsNodesOfR2UsedByAdditionalCAs.contains(potentialUseInR2))
				potentialUseNodeCompletesContainedBA = true;

			Set<Node> useNodesOfR2OfAllInvolvedCAs = getAllUseNodesOfR2();// getAllUseNodesOfLhsOfR2OfAllInvolvedConflictAtoms();
			if (useNodesOfR2OfAllInvolvedCAs.contains(potentialUseInR2))
				secondUncoveredCANodeIsAlreadyPresent = true;

		}
		return potentialUseNodeCompletesContainedBA || secondUncoveredCANodeIsAlreadyPresent;
	}

	private Set<Node> getAllUseNodesOfR2() {
		Set<Node> allUseNodesOfLhsOfR2 = new HashSet<Node>();
		for (Mapping mappingInRule2 : mappingsInRule2) {
			allUseNodesOfLhsOfR2.add(mappingInRule2.getImage());
		}
		return allUseNodesOfLhsOfR2;
	}

	@Override
	public String toString() {
		return (isMinimalReason ? "M" : "") + super.toString();
	}

	@Override
	protected int sortID() {
		return super.sortID() - (isMinimalReason ? 24 : 0);
	}

	@Override
	public boolean equals(Object obj) {
		return isMinimalReason() == ((Reason) obj).isMinimalReason() && super.equals(obj);
	}
}