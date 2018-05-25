/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http:
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa.result;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * This class represents a conflict, which is on of the two forms of a critical pair.
 * 
 * @author Florian Heﬂ, Kristopher Born
 *
 */
public class Conflict extends CriticalPair {

	/**
	 * The match of one of the two involved rules into the minimal model.
	 */
	Match match1;

	/**
	 * The match of one of the two involved rules into the minimal model.
	 */
	Match match2;

	/**
	 * Kind of the conflict.
	 */
	ConflictKind conflictKind;

	EGraph minimalModelEGraph;

	/**
	 * Default constructor.
	 * 
	 * @param r1 One of the two rules of the conflict.
	 * @param r2 The other rule of the two rules of the conflict.
	 * @param cpaEPackage The minimal model on which both rules are applicable.
	 * @param match1 The match of the rule <code>r1</code> into the <code>minimalModel</code>.
	 * @param match2 The match of the rule <code>r2</code> into the <code>minimalModel</code>.
	 * @param conflictKind The kind of the conflict.
	 */
	public Conflict(Rule r1, Rule r2, EPackage cpaEPackage, Match match1, Match match2, ConflictKind conflictKind,
			AppliedAnalysis appliedAnalysis) {
		super(r1, r2, cpaEPackage, appliedAnalysis);
		this.match1 = match1;
		this.match2 = match2;
		this.conflictKind = conflictKind;
	}

	/**
	 * Returns the match of rule r1 into the minimal model.
	 * 
	 * @return The match of rule r1 into the minimal model.
	 */
	public Match getMatch1() {
		return match1;
	}

	/**
	 * Returns the match of rule r2 into the minimal model.
	 * 
	 * @return The match of rule r2 into the minimal model.
	 */
	public Match getMatch2() {
		return match2;
	}

	/**
	 * Returns the kind of the conflict.
	 * 
	 * @return The kind of the conflict.
	 */
	public ConflictKind getConflictKind() {
		return conflictKind;
	}

	public EGraph getMinimalModelEGraph() {
		if (minimalModelEGraph == null) {

			HenshinFactory henshinFactory = HenshinFactory.eINSTANCE;
			Engine engine = new EngineImpl();

			minimalModelEGraph = new EGraphImpl();

			Copier importsCopier = new Copier();
			Collection<EPackage> copyOfAllImports = importsCopier.copyAll(getFirstRule().getModule().getImports());
			importsCopier.copyReferences();

			Module module = henshinFactory.createModule();
			module.getImports().addAll(copyOfAllImports);

			Rule r1TempRule = henshinFactory.createRule();
			module.getUnits().add(r1TempRule);
			Copier r1Copier = new Copier();
			Graph r1LhsCopy = (Graph) r1Copier.copy(getFirstRule().getLhs());
			r1LhsCopy.setName("Rhs");
			r1Copier.copyReferences();

			r1TempRule.setRhs(r1LhsCopy);

			RuleApplication ruleApplicationR1Temp = new RuleApplicationImpl(engine, minimalModelEGraph, r1TempRule,
					null);

			ruleApplicationR1Temp.execute(null);
			Match resultMatchOfTempR1 = ruleApplicationR1Temp.getResultMatch();

			Graph lhsOfR2 = getSecondRule().getLhs();
			Rule r2TempRule = henshinFactory.createRule();
			module.getUnits().add(r2TempRule);
			Copier r2Copier = new Copier();
			Graph r2LhsCopy = (Graph) r2Copier.copy(lhsOfR2);
			r2Copier.copyReferences();
			r2LhsCopy.setName("Rhs");
			r2TempRule.setRhs(r2LhsCopy);

			Copier r2NodeCopier = new Copier();
			Graph tempR2Lhs = henshinFactory.createGraph("Lhs");
			r2TempRule.setLhs(tempR2Lhs);

			List<EObject> match2NodeTargets = match2.getNodeTargets();
			if (match2NodeTargets.size() == 0) {
				System.err.println("This conflict is broken! Match2 is emtpy.");
			}

			Match completeMatchOfTempR2 = new MatchImpl(r2TempRule, false);

			for (Node nodeOfR2Lhs : lhsOfR2.getNodes()) {
				EObject minimalModelEObjectByR1 = match2.getNodeTarget(nodeOfR2Lhs);
				if (minimalModelEObjectByR1 != null) {

					for (Node nodeInR1 : getFirstRule().getLhs().getNodes()) {
						if (match1.getNodeTarget(nodeInR1) == minimalModelEObjectByR1) {
							Node nodeOfR1LhsCopy = (Node) r1Copier.get(nodeInR1);

							EObject newMinimalModelEObject = resultMatchOfTempR1.getNodeTarget(nodeOfR1LhsCopy);
							Node r2TempLhsNode = (Node) r2NodeCopier.copy(nodeOfR2Lhs);
							r2NodeCopier.copyReferences();
							tempR2Lhs.getNodes().add(r2TempLhsNode);
							completeMatchOfTempR2.setNodeTarget(r2TempLhsNode, newMinimalModelEObject);

							Node r2TempRhsNode = (Node) r2Copier.get(nodeOfR2Lhs);
							Mapping r2TempMapping = henshinFactory.createMapping(r2TempLhsNode, r2TempRhsNode);
							if (r2TempLhsNode.getGraph().getRule() != r2TempRhsNode.getGraph().getRule()) {
								System.err.println("FALSCH!!!");
							}

							tempR2Lhs.getNodes().add(r2TempLhsNode);
							r2TempRule.getMappings().add(r2TempMapping);
						}
					}
				}
			}

			MappingList r2TempMappings = r2TempRule.getMappings();

			for (Node tempR2LhsNode : tempR2Lhs.getNodes()) {

				Node tempR2RhsNode = r2TempMappings.getImage(tempR2LhsNode, r2TempRule.getRhs());

				Set<Edge> outgoingEdges = new HashSet<>(tempR2RhsNode.getOutgoing());
				for (Edge outgoingEdge : outgoingEdges) {

					Node originNodeInLhsOfTempR2 = r2TempMappings.getOrigin(outgoingEdge.getTarget());
					if (originNodeInLhsOfTempR2 != null) {

						r2TempRule.getRhs().getEdges().remove(outgoingEdge);

					}
				}
			}

			RuleApplication ruleApplicationR2Temp = new RuleApplicationImpl(engine, minimalModelEGraph, r2TempRule,
					completeMatchOfTempR2);

			boolean execute2 = ruleApplicationR2Temp.execute(null);
			if (!execute2)
				System.err.println("Execution of second temp rule NOT successfull!");
			return minimalModelEGraph;
		} else {
			return minimalModelEGraph;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Conflict)
			return super.equals(obj);
		return false;
	}
}
