/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.ocl2gc.util;

import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import graph.Graph;
import morphisms.Pair;
import nestedcondition.Morphism;
import nestedcondition.NestedconditionFactory;
import nestedcondition.NodeMapping;

public class MorphismHelper {

	public static EList<MorphismPair> getCompleteMorphisms(Graph p, Graph c) {
		EList<MorphismPair> morphismPairs = new BasicEList<MorphismPair>();
		Set<Pair> pairs = JointPairs.getJointInclusions(p, c);
		for (Pair pair : pairs) {
			MorphismPair morphismPair = new MorphismPair(p, c);
			morphismPair.setResultGraph(pair.getA().getCodomain());
			morphismPair.setMorphismP(convertMorphism(pair.getA()));
			morphismPair.setMorphismC(convertMorphism(pair.getB()));
			morphismPairs.add(morphismPair);
		}
		return morphismPairs;
	}

	private static Morphism convertMorphism(morphisms.Morphism graphmorphism) {
		NestedconditionFactory factory = NestedconditionFactory.eINSTANCE;
		Morphism ecoremorphism = factory.createMorphism();
		ecoremorphism.setFrom(graphmorphism.getDomain());
		ecoremorphism.setTo(graphmorphism.getCodomain());
		for (morphisms.Mapping m : graphmorphism.getMappings()) {
			NodeMapping nm = factory.createNodeMapping();
			nm.setOrigin(m.getOrigin());
			nm.setImage(m.getImage());
			ecoremorphism.getNodeMappings().add(nm);
		}
		return ecoremorphism;
	}

}
