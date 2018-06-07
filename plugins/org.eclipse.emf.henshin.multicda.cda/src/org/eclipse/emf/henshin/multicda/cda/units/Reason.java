package org.eclipse.emf.henshin.multicda.cda.units;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.conflict.EssentialConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDependencyReason;

public abstract class Reason extends Span implements Comparable<Reason> {
	public final String TAG;
	public final String NAME;
	protected int sortID = 0;

	protected Set<Reason> originMCRs;

	/**
	 * @return the originMCRs
	 */
	public Set<Reason> getOriginMCRs() {
		return originMCRs;
	}

	public Reason(Reason reason) {
		this(reason, "R", "Reason");
	}

	public Reason(Set<Mapping> mappingsOfNewSpanInRule1, Graph graph1Copy, Set<Mapping> mappingsOfNewSpanInRule2,
			Set<Reason> originMCRs) {
		this(mappingsOfNewSpanInRule1, graph1Copy, mappingsOfNewSpanInRule2, originMCRs, "R", "Reason");
	}

	public Reason(Set<Mapping> hashSet, Graph graph, Set<Mapping> hashSet2) {
		super(hashSet, graph, hashSet2);
		TAG = "R";
		NAME = "Reason";
	}

	protected Reason(Reason reason, String tag, String name) {
		super(reason);
		TAG = tag;
		NAME = name;
		setDeletionElements();
		originMCRs = new HashSet<>();
		if (reason instanceof MinimalConflictReason || reason instanceof MinimalDependencyReason)
			originMCRs.add(reason);

	}

	protected Reason(Set<Mapping> mappingsOfNewSpanInRule1, Graph graph1Copy, Set<Mapping> mappingsOfNewSpanInRule2,
			Set<Reason> originMCRs, String tag, String name) {
		super(mappingsOfNewSpanInRule1, graph1Copy, mappingsOfNewSpanInRule2);
		TAG = tag;
		NAME = name;
		setDeletionElements();
		this.originMCRs = originMCRs;
	}

	protected Reason(Set<Mapping> hashSet, Graph graph, Set<Mapping> hashSet2, String tag, String name) {
		super(hashSet, graph, hashSet2);
		TAG = tag;
		NAME = name;
	}

	protected Reason(Reason extSpan, Node origin, Node image, String tag, String name) {
		super(extSpan, origin, image);
		TAG = tag;
		NAME = name;
	}

	protected Reason(Mapping nodeInRule1Mapping, Graph s1, Mapping nodeInRule2Mapping, String tag, String name) {
		super(nodeInRule1Mapping, s1, nodeInRule2Mapping);
		TAG = tag;
		NAME = name;
	}

	public Set<Atom> getCoveredEdgeConflictAtoms() {
		Set<Atom> edgeConflictAtoms = new HashSet<>();
		for (Reason mcr : originMCRs) {
			Set<Atom> containedConflictAtoms = ((MinimalConflictReason) mcr).getContainedConflictAtoms();
			for (Atom conflictAtom : containedConflictAtoms) {
				if (conflictAtom.isDeleteEdgeConflictAtom())
					edgeConflictAtoms.add(conflictAtom);
			}
		}
		return edgeConflictAtoms;
	}

	public boolean isDoubleReason() {
		return this instanceof DoubleSpan && ((DoubleSpan) this).isDoubleSpan();
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
			// Knoten aus R2 dï¿½rfen nicht mehrfach in ein CR involviert sein!
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

	public String toS2String(boolean complete) {
		String result = "";
		if (isDoubleReason()) {
			if (!complete) {
				int size = ((DoubleSpan) this).getS2Set().size() - 1;
				Reason s2 = ((DoubleSpan) this).getS2Set().iterator().next();
				result = TAG + ": " + this.getGraph().getEdges() + "  |\t" + this.getGraph().getNodes() + "  -+->  "
						+ s2.TAG + ": " + s2.getGraph().getEdges() + "  |\t" + s2.getGraph().getNodes()
						+ (size > 0 ? "... " + size + " more" : "");
			} else {
				result = TAG + ": " + this.getGraph().getEdges() + "  |\t" + this.getGraph().getNodes() + "  -+->  ";
				String s2String = "";
				for (Reason s2 : ((DoubleSpan) this).getS2Set())
					s2String += ", " + s2.TAG + ": " + s2.getGraph().getEdges() + "  |\t" + s2.getGraph().getNodes();
				result += s2String.substring(2);
			}
		} else
			result = TAG + ": " + this.getGraph().getEdges() + "  |\t" + this.getGraph().getNodes();
		return result;
	}

	@Override
	public String toString() {
		return TAG + ": " + this.getGraph().getEdges() + "  |\t" + this.getGraph().getNodes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Rule r1 = getRule1();
		Rule r2 = getRule2();
		Rule r1o = ((Reason) obj).getRule1();
		Rule r2o = ((Reason) obj).getRule2();
		if (!r1.getName().equals(r1o.getName()) || !r2.getName().equals(r2o.getName()))
			return false;
		Set<GraphElement> ge = getDeletionElementsInRule1_2();
		Set<GraphElement> geO = ((Reason) obj).getDeletionElementsInRule1_2();
		if (ge.size() != geO.size())
			return false;

		for (GraphElement element : ge) {
			boolean found = false;
			for (GraphElement elementO : geO)
				if (found |= element.getClass() == elementO.getClass() && getHash(element) == getHash(elementO))
					break;
			if (!found)
				return false;
		}
		List<Node> oNodes = ((Reason) obj).getGraph().getNodes();
		List<Edge> oEdges = ((Reason) obj).getGraph().getEdges();
		List<Node> nodes = graph.getNodes();
		List<Edge> edges = graph.getEdges();
		if (oNodes.size() != nodes.size() || edges.size() != oEdges.size())
			return false;
		for (Node n1 : oNodes) {
			boolean found = false;
			for (Node n2 : nodes)
				if (found |= n1.getName().equals(n2.getName()))
					break;
			if (!found)
				return false;
		}
		for (Edge e1 : oEdges) {
			boolean found = false;
			for (Edge e2 : edges)
				if (found |= e1.toString().equals(e2.toString()))
					break;
			if (!found)
				return false;
		}
		if (this instanceof DoubleSpan && ((DoubleSpan) this).isDoubleSpan()) {
			Set<Reason> oS2 = ((DoubleSpan) obj).getS2Set();
			Set<Reason> S2 = ((DoubleSpan) this).getS2Set();
			if (oS2.size() != S2.size())
				return false;
			// return oS2.equals(S2); //Comparison of two Sets might be not enough.
			Set<Reason> checked = new HashSet<>(); // Strong comparison of s2 sets
			for (Reason reason1 : S2) {
				boolean found = false;
				for (Reason reason2 : oS2)
					if (found = reason1.equals(reason2) && checked.add(reason2))
						break;
				if (!found)
					return false;
			}
		}
		return true;
	}

	public boolean invertNamesForHash = false;
	public static final String NODE_SEPARATOR = "_";

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hashN = 0;
		int hashE = 0;
		for (Node n : graph.getNodes())
			hashN += getHash(n);
		for (Edge e : graph.getEdges())
			hashE += getHash(e);
		return TAG.hashCode() + hashN + hashE + getRule1().hashCode() * 3 + getRule2().hashCode() + sortID;
	}

	private int getHash(GraphElement n) {
		if (n instanceof Node)
			if (((Node) n).getType() == null)
				return (((Node) n).getName() + "").hashCode();
			else {
				int result = 0;
				String[] names = ((Node) n).getName().split(Reason.NODE_SEPARATOR);
				if (names.length == 2)
					if (invertNamesForHash) {
						result = (names[1] + "$%%§$&$" + Reason.NODE_SEPARATOR + names[0]).hashCode();
					} else
						result = (names[0] + "$%%§$&$" + Reason.NODE_SEPARATOR + names[1]).hashCode();
				return (result + ":" + ((Node) n).getType().getName()).hashCode();
			}

		else if (n instanceof Edge) {
			Edge e = (Edge) n;
			if (e.getSource() == null || e.getTarget() == null || e.getType() == null)
				return super.hashCode();
			else
				return getHash(e.getSource()) * 11 + getHash(e.getTarget()) + e.getType().hashCode();
		}
		return 0;
	}

	public final void print() {
		System.out.println(this);
	}
	@Override
	public int compareTo(Reason o) {
		if (o == null)
			return 1;
		if (getClass() != o.getClass())
			return sortID - o.sortID;
		int value = 0;

		String no1 = graph.getNodes().toString();
		String no2 = o.graph.getNodes().toString();
		String ed1 = graph.getEdges().toString();
		String ed2 = o.graph.getEdges().toString();
		if ((value = ed1.length() - ed2.length()) != 0)
			return value;
		if ((value = no1.length() - no2.length()) != 0)
			return value;

		if (isDoubleReason() && o.isDoubleReason()) {
			DoubleSpan T = (DoubleSpan) this;
			DoubleSpan O = (DoubleSpan) o;
			no1 = T.getS2Set().toString();
			no2 = O.getS2Set().toString();
			ed1 = T.getS2Set().toString();
			ed2 = O.getS2Set().toString();
			if ((value = ed1.length() - ed2.length()) != 0)
				return value;
			if ((value = no1.length() - no2.length()) != 0)
				return value;
			if ((value = ed1.compareTo(ed2)) != 0)
				return value;
			if ((value = no1.compareTo(no2)) != 0)
				return value;
		}
		if ((value = ed1.compareTo(ed2)) != 0)
			return value;
		if ((value = no1.compareTo(no2)) != 0)
			return value;
		return toString().compareTo(o.toString());
	}
}