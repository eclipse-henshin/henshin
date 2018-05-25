package org.eclipse.emf.henshin.multicda.cda.conflict;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.multicda.cda.Pushout;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

public class EssentialConflictReason extends Reason {

	Set<Atom> additionalConflictAtoms;

	public EssentialConflictReason(Reason initialReason, Node boundaryNodeOfCA, Node usedNodeInLhsOfR2,
			ConflictAtom additionalConflictAtom) {
		super(initialReason);

		HenshinFactory henshinFactory = HenshinFactory.eINSTANCE;

		Node boundaryNodeOfRule1 = additionalConflictAtom.getMappingIntoRule1(boundaryNodeOfCA).getImage();
		String nameOfNewBoundaryNode = boundaryNodeOfRule1.getName() + Reason.NODE_SEPARATOR
				+ usedNodeInLhsOfR2.getName();
		Node newBoundaryNodeInSpan = henshinFactory.createNode(graph, boundaryNodeOfCA.getType(),
				nameOfNewBoundaryNode);

		Mapping mappingInR1 = henshinFactory.createMapping(newBoundaryNodeInSpan, boundaryNodeOfRule1);
		mappingsInRule1.add(mappingInR1);
		Mapping mappingInR2 = henshinFactory.createMapping(newBoundaryNodeInSpan, usedNodeInLhsOfR2);
		mappingsInRule2.add(mappingInR2);

		additionalConflictAtoms = new HashSet<>();
		additionalConflictAtoms.add(additionalConflictAtom);
		if (initialReason instanceof EssentialConflictReason) {
			additionalConflictAtoms
					.addAll(((EssentialConflictReason) initialReason).getAdditionallyInvolvedConflictAtoms());
		}
	}

	public EssentialConflictReason(Reason initialReason) {
		super(initialReason); 
		additionalConflictAtoms = new HashSet<>();
	}

	public Set<Node> getLhsNodesOfR2UsedByAdditionalCAs() {
		Set<Node> result = new HashSet<Node>();
		for (Atom ca : additionalConflictAtoms) {
			Set<Mapping> mappingsInRule2 = ca.getMappingsInRule2();
			for (Mapping mappingInRule2 : mappingsInRule2) {
				result.add(mappingInRule2.getImage());
			}
		}
		return result;
	}

	public Set<Node> getUsedLhsNodesOfR2() {
		Set<Node> result = new HashSet<Node>();
		for (Mapping map : mappingsInRule2) {
			result.add(map.getImage());
		}
		return result;
	}

	/**
	 * @return the additionallyInvolvedConflictAtoms
	 */
	public Set<Atom> getAdditionallyInvolvedConflictAtoms() {
		return additionalConflictAtoms;
	}

	public Pushout getPushoutResult() {
		return new Pushout(getRule1(), this, getRule2());
	}

}
