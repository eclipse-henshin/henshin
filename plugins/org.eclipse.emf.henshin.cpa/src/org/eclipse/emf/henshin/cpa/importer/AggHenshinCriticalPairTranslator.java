/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.cpa.importer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.cpa.modelExtension.ComatchImpl;
import org.eclipse.emf.henshin.cpa.modelExtension.ExtendedMatchImpl;
import org.eclipse.emf.henshin.cpa.result.CPAResult;
import org.eclipse.emf.henshin.cpa.result.Conflict;
import org.eclipse.emf.henshin.cpa.result.ConflictKind;
import org.eclipse.emf.henshin.cpa.result.CriticalElement;
import org.eclipse.emf.henshin.cpa.result.Dependency;
import org.eclipse.emf.henshin.cpa.result.DependencyKind;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;

import agg.attribute.AttrInstance;
import agg.attribute.AttrMember;
import agg.attribute.impl.ValueTuple;
import agg.parser.CriticalPairData;
import agg.parser.DependencyPairContainer;
import agg.parser.ExcludePairContainer;
import agg.xt_basis.Arc;
import agg.xt_basis.GraphObject;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;

/**
 * This class provides an importer/converter for the results originating from AGG (which were computed based on the
 * prior "export to AGG" function).
 * 
 * @author Florian Heﬂ, Kristopher Born
 *
 */
public class AggHenshinCriticalPairTranslator {

	private enum CPType {
		Conflict, Dependency
	}

	private enum SequentialRule {
		FirstRule, SecondRule
	}

	private CPType criticalPairType;

	// private Module module;

	List<org.eclipse.emf.henshin.model.Rule> rulesToMapTheResultsOn;

	EcoreFactory ecoreFactory = EcoreFactory.eINSTANCE;

	EPackage cpaEPackage;

	Map<Integer, String> hashToName;

	private List<CriticalElement> criticalElements;

	/**
	 * Default constructor for the translator.
	 * 
	 * @param rules The rules which were used for prior "export to AGG" function.
	 */
	public AggHenshinCriticalPairTranslator(List<org.eclipse.emf.henshin.model.Rule> rules) {
		rulesToMapTheResultsOn = rules;
	}

	/**
	 * Transforms the ExludePairContainer <code>epc</code> into a list of <code>CriticalPair</code>s
	 * 
	 * @param epc The computed <code>ExcludePairContainer</code> by AGG.
	 * @return A list of <code>CriticalPair</code>s as a <code>CPAResult</code>.
	 */
	public CPAResult importExcludePairContainer(ExcludePairContainer epc) {
		CPAResult cPAresult = new CPAResult();

		if (epc instanceof DependencyPairContainer) {
			criticalPairType = CPType.Dependency;
		} else {
			criticalPairType = CPType.Conflict;
		}

		List<Rule> rules1 = epc.getRules();
		List<Rule> rules2 = epc.getRules2();

		for (Rule rule1 : rules1) {
			for (Rule rule2 : rules2) {
				CriticalPairData cpd = epc.getCriticalPairData(rule1, rule2);

				if (cpd == null)
					continue;
				while (cpd.next()) {
					processAGGresultOfRulePair(cPAresult, rule1, rule2, cpd);
				}
			}
		}
		return cPAresult;
	}

	/**
	 * Processes a single critical pair of AGG results to obtain this result as part of the <code>CPAResult</code>.
	 * 
	 * @param result The finally provided <code>CPAResult</code> of Henshins critical pair analysis.
	 * @param rule1 The first rule of AGG of the critical pair.
	 * @param rule2 The second rule of AGG of the critical pair.
	 * @param cpd The container of AGG containing the critical pair.
	 */
	private void processAGGresultOfRulePair(CPAResult result, Rule rule1, Rule rule2, CriticalPairData cpd) {

		criticalElements = new LinkedList<CriticalElement>();

		boolean validCriticalPair = true;

		cpaEPackage = ecoreFactory.createEPackage();

		cpaEPackage.setName(rule1.getQualifiedName() + ", " + rule2.getQualifiedName());
		cpaEPackage.setNsPrefix("CPAPackage");

		String criticalPairKind = getCriticalPairKindString(cpd);
		cpaEPackage.setNsURI("http://cpapackage/" + rule1.getQualifiedName() + "/" + rule2.getQualifiedName() + "/"
				+ criticalPairKind);

		// initialize all the resulting objects of a single critical pair: -->
		// a copy of the original rules is essential
		org.eclipse.emf.henshin.model.Rule firstHenshinRuleOriginal = getResultRule(rule1.getName());
		org.eclipse.emf.henshin.model.Rule secondHenshinRuleOriginal = getResultRule(rule2.getName());
		// <-- initialize all the resulting objects of a single critical pair
		ExtendedMatchImpl firstRuleCopyMatch;
		ExtendedMatchImpl secondRuleCopyMatch;

		// HashMap for mapping unique Hash ID to the correct name
		hashToName = new HashMap<Integer, String>();

		// Map the first rule
		OrdinaryMorphism morph1 = cpd.getMorph1();
		// Returns the graph embedding of the first rule into the critical graph of the current overlapping pair.

		if (criticalPairType == CPType.Dependency) {
			firstRuleCopyMatch = new ComatchImpl(firstHenshinRuleOriginal, true);
		} else { // in case of a conflict
			firstRuleCopyMatch = new ExtendedMatchImpl(firstHenshinRuleOriginal, true);
		}

		Vector<GraphObject> morph1SourceObjects1 = morph1.getDomainObjects();
		Vector<GraphObject> morph1TargetObjects1 = morph1.getCodomainObjects();

		List<Node> processedHenshinRuleLhsNodes = new LinkedList<Node>();
		List<Node> processedHenshinRuleRhsNodes = new LinkedList<Node>();

		// mapping of AGG-nodes<->Henshin-rule-nodes - introduced
		// serves to resolve the associated henshin nodes when transforming a edge(/Arc) from Agg to Henshin [especially
		// for the processing of the critical elements]
		HashMap<GraphObject, Node> firstRuleLhsMapping = new HashMap<GraphObject, Node>();
		HashMap<GraphObject, Node> firstRuleRhsMapping = new HashMap<GraphObject, Node>();

		CriticalGraphMapping criticalGraphMapping = new CriticalGraphMapping();

		for (int i = 0; i < morph1SourceObjects1.size(); i++) {

			GraphObject morph1SourceObject = morph1SourceObjects1.elementAt(i);
			GraphObject morph1TargetObject = morph1TargetObjects1.elementAt(i);

			org.eclipse.emf.henshin.model.Graph rule1lhs = firstHenshinRuleOriginal.getLhs();
			org.eclipse.emf.henshin.model.Graph rule1rhs = firstHenshinRuleOriginal.getRhs();

			Node henshinNodeLhs = null;
			Node henshinNodeRhs = null;

			if (morph1TargetObject.isNode()) {
				String sourceName = morph1SourceObject.getType().getName();
				EList<Node> nodes = rule1lhs.getNodes();
				for (Node fnode : nodes) {
					if (fnode.getType().getName().equals(sourceName) && !processedHenshinRuleLhsNodes.contains(fnode)) {
						henshinNodeLhs = fnode;
						processedHenshinRuleLhsNodes.add(fnode);
						firstRuleLhsMapping.put(morph1SourceObject, fnode);
						break;
						// stops the process of searching the member node after (a/)the corresponding one is found
					}
				}
				nodes = rule1rhs.getNodes();
				for (Node fnode : nodes) {
					if (fnode.getType().getName().equals(sourceName) && !processedHenshinRuleRhsNodes.contains(fnode)) {
						henshinNodeRhs = fnode;
						processedHenshinRuleRhsNodes.add(fnode);
						firstRuleRhsMapping.put(morph1TargetObject, fnode);
						break;
						// stops the process of searching the member node after (a/)the corresponding one is found
					}
				}

				if (criticalPairType == CPType.Dependency) {
					DependencyKind depKind = transformCriticalKindOfDependency(cpd);
					if (depKind == DependencyKind.PRODUCE_USE_DEPENDENCY)
						;
					criticalGraphMapping.addFirstRuleMapping(morph1TargetObject, henshinNodeRhs);
					if (depKind == DependencyKind.DELETE_FORBID_DEPENDENCY)
						;
					criticalGraphMapping.addFirstRuleMapping(morph1TargetObject, henshinNodeLhs);
				} else if (criticalPairType == CPType.Conflict) {
					ConflictKind conflKind = transformCriticalKindOfConflict(cpd);
					if (conflKind == ConflictKind.DELETE_USE_CONFLICT)
						criticalGraphMapping.addFirstRuleMapping(morph1TargetObject, henshinNodeLhs);
					if (conflKind == ConflictKind.PRODUCE_FORBID_CONFLICT)
						criticalGraphMapping.addFirstRuleMapping(morph1TargetObject, henshinNodeRhs);
				}

				// add node to graph and into Match
				EClass targetEClass = ecoreFactory.createEClass();
				targetEClass.setName("" + morph1TargetObject.hashCode()); // hashValue of a AGG graphObject

				processAttributesOfMorphism(morph1TargetObject, targetEClass);

				if (morph1TargetObject.isCritical()) { // ensures the highlighting of the critical element
					hashToName.put(morph1TargetObject.hashCode(), "#" + morph1TargetObject.getType().getName() + "#");

					CriticalElement criticalElement = new CriticalElement();
					criticalElements.add(criticalElement);
					// when this critical element is added, its still unclear if the attribute does occur in the second
					// rule. If not, the critical element will be dropped later on.

					criticalElement.commonElementOfCriticalGraph = morph1TargetObject;
					if (criticalPairType == CPType.Conflict) {

						criticalElement.elementInFirstRule = henshinNodeLhs;
						// default. Other cases will be handled in the further .

						if (transformCriticalKindOfConflict(cpd) == ConflictKind.CHANGE_USE_ATTR_CONFLICT
								// TODO: refactor such that "transformCriticalKindOfConflict(...)" is called only once
								// per "processAGGresultOfRulePair(...)" an the result is stored in an internal
								// variable.
								|| transformCriticalKindOfConflict(cpd) == ConflictKind.CHANGE_FORBID_ATTR_CONFLICT
								|| transformCriticalKindOfConflict(cpd) == ConflictKind.PRODUCE_FORBID_CONFLICT) {
							// TODO: check for more concerned conflict kinds
							boolean anyAttributeProcessed = false;
							// check all attributes if they are the cause for the dependency/conflict
							for (Attribute henshinRhsAttribute : henshinNodeRhs.getAttributes()) {
								boolean attributeChanged = true; // even if the the attribute is not contained in the
																	// LHS, it is changed, since it is created
								if (henshinNodeLhs != null)
									for (Attribute henshinLhsAttribute : henshinNodeLhs.getAttributes()) {
										boolean attributeTypeIdentical = henshinLhsAttribute
												.getType() == henshinRhsAttribute.getType(); // type of both attributes
																								// must be identical
										boolean attributeNameEqual = henshinLhsAttribute.getType().getName()
												.equals(henshinRhsAttribute.getType().getName());
										if (attributeTypeIdentical && attributeNameEqual) {
											if (henshinRhsAttribute.getValue().equals(henshinLhsAttribute.getValue()))
												attributeChanged = false;
										}
									}
								if (attributeChanged) {
									if (!anyAttributeProcessed) {
										criticalElement.elementInFirstRule = henshinRhsAttribute;
										anyAttributeProcessed = true;
									} else {
										CriticalElement criticalElementforFurtherChangedAttribute = new CriticalElement();
										criticalElements.add(criticalElementforFurtherChangedAttribute);
										// when this critical element is added, its still unclear if the attribute does
										// occur in the second rule. If not, the critical element will be dropped later
										// on.
										criticalElementforFurtherChangedAttribute.commonElementOfCriticalGraph = morph1TargetObject;
										criticalElementforFurtherChangedAttribute.elementInFirstRule = henshinRhsAttribute;
									}
								}
							}
							if (!anyAttributeProcessed) {
								// System.err
								// .println("critical node in CHANGE_USE_ATTR_CONFLICT detected without any attribute
								// change. Not fully implemented yet. had been treated like a created node, if it might
								// be a deleted one.");
								criticalElement.elementInFirstRule = henshinNodeRhs;
							}
						}
					} else if (criticalPairType == CPType.Dependency) {
						// node of the RHS of the first rule contains the created element
						if (transformCriticalKindOfDependency(cpd) == DependencyKind.PRODUCE_USE_DEPENDENCY)
							criticalElement.elementInFirstRule = henshinNodeRhs;
						// node of the LHS of the first rule is being deleted and is the critical element. (since its
						// being deleted, it wont be part of the RHS)
						if (transformCriticalKindOfDependency(cpd) == DependencyKind.DELETE_FORBID_DEPENDENCY)
							criticalElement.elementInFirstRule = henshinNodeLhs;
						if (transformCriticalKindOfDependency(cpd) == DependencyKind.CHANGE_USE_ATTR_DEPENDENCY) {
							boolean anyAttributeProcessed = false;
							// check all attributes if they are the cause for the dependency/conflict
							for (Attribute henshinRhsAttribute : henshinNodeRhs.getAttributes()) {
								boolean attributeChanged = true; // even if the the attribute is not contained in the
																	// LHS, it is changed, since it is created
								for (Attribute henshinLhsAttribute : henshinNodeLhs.getAttributes()) {
									boolean attributeTypeIdentical = henshinLhsAttribute
											.getType() == henshinRhsAttribute.getType();
									// type of both attributes must be identical
									boolean attributeNameEqual = henshinLhsAttribute.getType().getName()
											.equals(henshinRhsAttribute.getType().getName());
									if (attributeTypeIdentical && attributeNameEqual) {
										if (henshinRhsAttribute.getValue().equals(henshinLhsAttribute.getValue()))
											attributeChanged = false;
									}
								}
								// if the value had changed, this seems to be the reason for the dependency.
								if (attributeChanged) {
									if (!anyAttributeProcessed) {
										criticalElement.elementInFirstRule = henshinRhsAttribute;
										anyAttributeProcessed = true;
									} else {
										CriticalElement criticalElementforFurtherChangedAttribute = new CriticalElement();
										criticalElements.add(criticalElementforFurtherChangedAttribute);
										criticalElementforFurtherChangedAttribute.commonElementOfCriticalGraph = morph1TargetObject;
										criticalElementforFurtherChangedAttribute.elementInFirstRule = henshinRhsAttribute;
									}
								}
							}
							// add the node if no change of the value occurred
							if (!anyAttributeProcessed) {
								// System.err
								// .println("critical node in CHANGE_USE_ATTR_CONFLICT detected without any attribute
								// change. Not fully implemented yet. had been treated like a created node, if it might
								// be a deleted one.");
								criticalElement.elementInFirstRule = henshinNodeRhs;
							}
						}
					}

				} else {
					hashToName.put(morph1TargetObject.hashCode(), morph1TargetObject.getType().getName());
				}
				if (criticalPairType == CPType.Conflict)
					if (henshinNodeLhs != null) {
						firstRuleCopyMatch.setNodeTarget(henshinNodeLhs, targetEClass);
					}
				if (criticalPairType == CPType.Dependency)
					if (henshinNodeRhs != null) {
						firstRuleCopyMatch.setNodeTarget(henshinNodeRhs, targetEClass);
					}

				if (!cpaEPackage.getEClassifiers().contains(targetEClass)) {
					cpaEPackage.getEClassifiers().add(targetEClass);
				}
			}

			else if (morph1TargetObject.isArc()) {
				try {
					boolean arcIsCritical = morph1SourceObject.isCritical() || morph1TargetObject.isCritical();
					if (criticalPairType == CPType.Conflict) { // LHS is of relevance
						processEdgeOfAGGResult(morph1TargetObject, SequentialRule.FirstRule, arcIsCritical,
								criticalGraphMapping);
					} else if (criticalPairType == CPType.Dependency) { // RHS is of relevance
						// since the 'produced' elements only occur in the RHS of the first rule
						if (transformCriticalKindOfDependency(cpd) == DependencyKind.PRODUCE_USE_DEPENDENCY)
							processEdgeOfAGGResult(morph1TargetObject, SequentialRule.FirstRule, arcIsCritical,
									criticalGraphMapping);
						// since the 'produced' elements only occur in the LHS of the first rule
						if (transformCriticalKindOfDependency(cpd) == DependencyKind.DELETE_FORBID_DEPENDENCY)
							processEdgeOfAGGResult(morph1TargetObject, SequentialRule.FirstRule, arcIsCritical,
									criticalGraphMapping); // only the nodes of the domain (morph1SourceObject) are
															// within the mapping
						if (transformCriticalKindOfDependency(cpd) == DependencyKind.CHANGE_USE_ATTR_DEPENDENCY) {
							processEdgeOfAGGResult(morph1TargetObject, SequentialRule.FirstRule, arcIsCritical,
									criticalGraphMapping);
							System.err.println("Unimplemented yet");
							// throw new Exception("processing of CHANGE_USE_ATTR_DEPENDENCY edges unimpleted yet");
							// //TODO: 02.08.2015: implementation sufficient???
						}
						if (transformCriticalKindOfDependency(cpd) == DependencyKind.CHANGE_FORBID_ATTR_DEPENDENCY) {
							System.err.println("Unimplemented yet");
							throw new Exception("processing of CHANGE_FORBID_ATTR_DEPENDENCY edges unimpleted yet");
						}
					}
				} catch (Exception e) {
					validCriticalPair = false;
					e.printStackTrace();
				}
			}
		}

		for (Parameter param : firstHenshinRuleOriginal.getParameters()) {
			firstRuleCopyMatch.removeParameter(param);
		}

		secondRuleCopyMatch = new ExtendedMatchImpl(secondHenshinRuleOriginal, true);
		// Map the second rule
		OrdinaryMorphism morph2 = cpd.getMorph2();

		boolean edgeProcessingOfSecondRuleBegun = false;

		Vector<GraphObject> morph2SourceObjects2 = morph2.getDomainObjects();
		Vector<GraphObject> morph2TargetObjects2 = morph2.getCodomainObjects();

		// mapping of AGG-nodes<->Henshin-rule-nodes
		HashMap<GraphObject, Node> secondRuleLhsMapping = new HashMap<GraphObject, Node>();

		List<Node> processedLhsNodes = new LinkedList<Node>();

		for (int i = 0; i < morph2SourceObjects2.size(); i++) {
			GraphObject morph2SourceObject = morph2SourceObjects2.elementAt(i);
			GraphObject morph2TargetObject = morph2TargetObjects2.elementAt(i);

			org.eclipse.emf.henshin.model.Graph rule2lhs = secondHenshinRuleOriginal.getLhs();

			Node henshinNodeLhs = null;
			Node henshinNodeNac = null;

			if (morph2TargetObject.isNode()) {
				String sourceName = morph2SourceObject.getType().getName();
				EList<Node> nodes = rule2lhs.getNodes();
				for (Node fnode : nodes) {
					if (fnode.getType().getName().equals(sourceName) && !processedLhsNodes.contains(fnode)) {
						henshinNodeLhs = fnode;
						processedLhsNodes.add(fnode);
						secondRuleLhsMapping.put(morph2TargetObject, henshinNodeLhs);
						break;
					}
				}

				EList<NestedCondition> nestedConditions = rule2lhs.getNestedConditions();
				for (NestedCondition nestCond : nestedConditions) {
					if (nestCond.isNAC()) {
						Graph nacGraph = nestCond.getConclusion();
						EList<Node> nacNodes = nacGraph.getNodes();
						for (Node fnode : nacNodes) {
							if (fnode.getType().getName().equals(sourceName)) {
								henshinNodeNac = fnode;
								henshinNodeNac.setName(fnode.getType().getName());
							}
						}
					} else if (nestCond.isPAC()) {
						// for future improvement of supported features - add PAC Handling here
						System.err.println("PAC's are not yet supported by the features.");
					} else {
						System.err.println("AGGResultImporter: nested condition is no NAC and thus not supported yet");
						break;
					}
				}

				if (henshinNodeLhs != null) {
					criticalGraphMapping.addSecondRuleMapping(morph2TargetObject, henshinNodeLhs);
				} else if (henshinNodeLhs == null && henshinNodeNac != null) {
					criticalGraphMapping.addSecondRuleMapping(morph2TargetObject, henshinNodeNac);
				}

				// add node to graph (when not yet added by rule1) and into Match
				EClass targetEClass = null;
				if (hashToName.containsKey(morph2TargetObject.hashCode())) {
					targetEClass = (EClass) cpaEPackage.getEClassifier("" + morph2TargetObject.hashCode());
				} else {
					targetEClass = ecoreFactory.createEClass();
					targetEClass.setName("" + morph2TargetObject.hashCode());
					if (morph2TargetObject.isCritical()) {
						hashToName.put(morph2TargetObject.hashCode(),
								"#" + morph2TargetObject.getType().getName() + "#");
					} else {
						hashToName.put(morph2TargetObject.hashCode(), morph2TargetObject.getType().getName());
					}
				}

				if (morph2TargetObject.isCritical()) {
					processCriticalElementOfSecondRule(cpd, morph2TargetObject, henshinNodeLhs, henshinNodeNac);
				}
				// TODO: how shall the critical element be processed beforehand (wich might be an attribute) and the
				// attributes are just afterwards being processed?
				processAttributesOfMorphism(morph2TargetObject, targetEClass);

				if (henshinNodeLhs != null) {
					secondRuleCopyMatch.setNodeTarget(henshinNodeLhs, targetEClass);
				}
				if (henshinNodeNac != null) {
					secondRuleCopyMatch.setNodeTarget(henshinNodeNac, targetEClass);
				}

				if (!cpaEPackage.getEClassifiers().contains(targetEClass)) {
					cpaEPackage.getEClassifiers().add(targetEClass);
				}

			}

			else if (morph2TargetObject.isArc()) {

				if (!edgeProcessingOfSecondRuleBegun) {
					extractNodeAssignmentsOfNestedConditions(SequentialRule.SecondRule, rule2lhs.getNestedConditions(),
							criticalGraphMapping);
					edgeProcessingOfSecondRuleBegun = true;
				}

				try {
					boolean arcIsCritical = morph2SourceObject.isCritical() || morph2TargetObject.isCritical();
					processEdgeOfAGGResult(morph2TargetObject, SequentialRule.SecondRule, arcIsCritical,
							criticalGraphMapping);
				} catch (Exception e) {
					validCriticalPair = false;
					e.printStackTrace();
				}
			}
		}

		// post process the match: remove rule parameters
		secondRuleCopyMatch.removeAllParameter(secondHenshinRuleOriginal.getParameters());

		if (validCriticalPair) {
			// rename back from hash
			rename(hashToName, cpaEPackage);

			if (criticalPairType == CPType.Dependency) {
				Dependency dep = new Dependency(firstHenshinRuleOriginal, secondHenshinRuleOriginal, cpaEPackage,
						firstRuleCopyMatch, secondRuleCopyMatch, transformCriticalKindOfDependency(cpd));

				dep.addCriticalElements(criticalElements);
				result.addResult(dep);
			} else if (criticalPairType == CPType.Conflict) {
				Conflict conf = new Conflict(firstHenshinRuleOriginal, secondHenshinRuleOriginal, cpaEPackage,
						firstRuleCopyMatch, secondRuleCopyMatch, transformCriticalKindOfConflict(cpd));

				conf.addCriticalElements(criticalElements);
				result.addResult(conf);
			}
		}
	}

	private org.eclipse.emf.henshin.model.Rule getResultRule(String ruleName) {
		for (org.eclipse.emf.henshin.model.Rule rule : rulesToMapTheResultsOn) {
			if (rule.getName().equals(ruleName))
				return rule;
		}
		return null;
	}

	/**
	 * Processes a critical element of the second rule.
	 * 
	 * @param criticalPairData The result of AGG for this current critical pair.
	 * @param morph2TargetObject A critical element of the second rule.
	 * @param henshinNodeLhs The LHS of the second rule, which is always involved.
	 * @param henshinNodeNac The NAC of the second rule, which might be involved.
	 */
	private void processCriticalElementOfSecondRule(CriticalPairData criticalPairData, GraphObject morph2TargetObject,
			Node henshinNodeLhs, Node henshinNodeNac) {
		for (CriticalElement existingCritElem : criticalElements) {
			if (existingCritElem.elementInFirstRule instanceof Node) {
				if (existingCritElem.commonElementOfCriticalGraph == morph2TargetObject)
					existingCritElem.elementInSecondRule = henshinNodeLhs;
				if (henshinNodeLhs == null && henshinNodeNac != null)
					existingCritElem.elementInSecondRule = henshinNodeNac;
			}
			if (transformCriticalKindOfDependency(criticalPairData) == DependencyKind.CHANGE_USE_ATTR_DEPENDENCY) {
				if (existingCritElem.elementInFirstRule instanceof Attribute) {
					if (existingCritElem.commonElementOfCriticalGraph == morph2TargetObject) {
						for (Attribute attributeOfCriticalNode : henshinNodeLhs.getAttributes()) {
							Attribute attributeOfFirstRule = (Attribute) existingCritElem.elementInFirstRule;
							boolean attributeTypeIdentical = attributeOfCriticalNode.getType() == attributeOfFirstRule
									.getType();
							boolean attributeNameEqual = attributeOfCriticalNode.getType().getName()
									.equals(attributeOfFirstRule.getType().getName());
							boolean attributeValueEqual = attributeOfCriticalNode.getValue()
									.equals(attributeOfFirstRule.getValue());
							if (attributeTypeIdentical && attributeNameEqual && attributeValueEqual) {
								existingCritElem.elementInSecondRule = attributeOfCriticalNode;
							}
						}
					}
				}
			}
			if (transformCriticalKindOfConflict(criticalPairData) == ConflictKind.CHANGE_USE_ATTR_CONFLICT) {
				if (existingCritElem.elementInFirstRule instanceof Attribute) {
					if (existingCritElem.commonElementOfCriticalGraph == morph2TargetObject) {
						for (Attribute attributeOfCriticalNode : henshinNodeLhs.getAttributes()) {
							Attribute attributeOfFirstRule = (Attribute) existingCritElem.elementInFirstRule;
							boolean attributeTypeIdentical = attributeOfCriticalNode.getType() == attributeOfFirstRule
									.getType();
							boolean attributeNameEqual = attributeOfCriticalNode.getType().getName()
									.equals(attributeOfFirstRule.getType().getName());
							boolean attributeValueEqual = attributeOfCriticalNode.getValue()
									.equals(attributeOfFirstRule.getValue());
							if (attributeTypeIdentical && attributeNameEqual && !attributeValueEqual) {
								existingCritElem.elementInSecondRule = attributeOfCriticalNode;
							}
						}
					}
				}
			}
			if (transformCriticalKindOfConflict(criticalPairData) == ConflictKind.CHANGE_FORBID_ATTR_CONFLICT
					|| transformCriticalKindOfConflict(criticalPairData) == ConflictKind.PRODUCE_FORBID_CONFLICT) {
				if (existingCritElem.elementInFirstRule instanceof Attribute) {
					if (existingCritElem.commonElementOfCriticalGraph == morph2TargetObject) {
						for (Attribute attributeOfCriticalNode : henshinNodeNac.getAttributes()) {
							Attribute attributeOfFirstRule = (Attribute) existingCritElem.elementInFirstRule;
							boolean attributeTypeIdentical = attributeOfCriticalNode.getType() == attributeOfFirstRule
									.getType();
							boolean attributeNameEqual = attributeOfCriticalNode.getType().getName()
									.equals(attributeOfFirstRule.getType().getName());
							boolean attributeValueEqual = attributeOfCriticalNode.getValue()
									.equals(attributeOfFirstRule.getValue());
							if (attributeTypeIdentical && attributeNameEqual && attributeValueEqual) {
								existingCritElem.elementInSecondRule = attributeOfCriticalNode;
							}
						}
					}
				}
			} // TODO: may attributes be involved in case of ConflictKind.DELETE_USE_CONFLICT? At least an attribute
				// might not be the critical element of the ConflictKind.DELETE_USE_CONFLICT.
			if (existingCritElem.elementInFirstRule == null) {
				// this is an intended behavior. When the critical elements of the first rule had been created it still
				// had been unclear if they are part of the second rule.
				// TODO: maybe add:
				// .println("a critical element had been instantiated in the processing of the first rule, but no
				// appropriate henshin element had been assigned");
			}
		}
	}

	/**
	 * Extracts the assignments of nodes within nested conditions to complete the <code>CriticalGraphMapping</code>.
	 * 
	 * @param sequentialRule The information, if it is the first or the second rule of the critical pair.
	 * @param nestedConditions The nested conditions to be processed.
	 * @param criticalGraphMapping The mapping of the AGG overlap graph, also called minimal model of AGG and the nodes
	 *            in the two Henshin rules forming the critical pair.
	 */
	private void extractNodeAssignmentsOfNestedConditions(SequentialRule sequentialRule,
			EList<NestedCondition> nestedConditions, CriticalGraphMapping criticalGraphMapping) {
		for (NestedCondition nestedCondition : nestedConditions) {
			Graph conclusion = nestedCondition.getConclusion();
			MappingList mappings = nestedCondition.getMappings();
			EList<Node> nodes = conclusion.getNodes();
			for (Node node : nodes) {
				Node origin = mappings.getOrigin(node);
				if (origin != null) {
					if (sequentialRule == SequentialRule.FirstRule)
						criticalGraphMapping.addFirstRuleNodesOfNestedGraphs(origin, node);
					if (sequentialRule == SequentialRule.SecondRule)
						criticalGraphMapping.addSecondRuleNodesOfNestedGraphs(origin, node);
				}
			}
		}
	}

	/**
	 * Processes all the attributes in a node of the minimal model from AGG, such that they occur in the minimal model
	 * provided by Henshins CPA later on.
	 * 
	 * @param morphObjectOfAGG The node of AGG containing the attributes, which shall be processed.
	 * @param targetEClass The node in the minimal model which shall contain equivalent attributes in the future.
	 */
	private void processAttributesOfMorphism(GraphObject morphObjectOfAGG, EClass targetEClass) {
		AttrInstance attributes = morphObjectOfAGG.getAttribute();
		if (attributes != null) {
			for (int attrNr = 0; attrNr < attributes.getNumberOfEntries(); attrNr++) {
				// 24.07.2015: all attributes with the corresponding type are checked in the meta model( / type graph)
				boolean dontProcessThisAttribute = false;
				// only those present in the minimal model are being instantiated.
				EAttribute newAttrForMinimalGraph = ecoreFactory.createEAttribute();
				AttrMember memberAt = attributes.getMemberAt(attrNr);

				if (memberAt.getHoldingTuple() instanceof ValueTuple) {
					ValueTuple holdingTuple = (ValueTuple) memberAt.getHoldingTuple();
					String nameOfAttribute = holdingTuple.getNameAsString(attrNr);
					holdingTuple.getTypeAsString(attrNr);

					newAttrForMinimalGraph.setEType(getAppropriateEcoreEDataType(holdingTuple.getTypeAsString(attrNr)));

					if (holdingTuple.getMemberAt(attrNr).toString().equals(""))
						dontProcessThisAttribute = true;

					String newValueOfAttribute = holdingTuple.getMemberAt(attrNr).toString();
					newAttrForMinimalGraph.setName(nameOfAttribute + " = " + newValueOfAttribute);
					EList<EStructuralFeature> eStructuralFeatures = targetEClass.getEStructuralFeatures();
					for (EStructuralFeature eStructFeat : eStructuralFeatures) {
						if (eStructFeat instanceof EAttribute && !dontProcessThisAttribute) {
							EAttribute allreadyExistingEAttribute = (EAttribute) eStructFeat;
							// check if eAttribute is already processed
							if (allreadyExistingEAttribute.getName().equals(newAttrForMinimalGraph.getName())) {
								dontProcessThisAttribute = true;
								break;
							}
						}
					}
					if (!dontProcessThisAttribute)
						targetEClass.getEStructuralFeatures().add(newAttrForMinimalGraph);
				}
			}
		}
	}

	/**
	 * Returns the EDataType of Ecore to the name of a Java data type. The supported data types shall match those, which
	 * are supported by the exporter. By using Java 1.7 this may be replaced by a smarter 'switch-case' statement.
	 * 
	 * @param stringRepresentationOfDatatype The <code>string</code> representation of a Java data type, which here
	 *            means the name of the data type.
	 * @return The associated <code>EDataType</code> of Ecore.
	 */
	private EClassifier getAppropriateEcoreEDataType(String stringRepresentationOfDatatype) {
		if (stringRepresentationOfDatatype.equals("String")) {
			return EcorePackage.eINSTANCE.getEString();
		} else if (stringRepresentationOfDatatype.equals("int")) {
			return EcorePackage.eINSTANCE.getEInt();
		} else if (stringRepresentationOfDatatype.equals("boolean")) {
			return EcorePackage.eINSTANCE.getEBoolean();
		} else if (stringRepresentationOfDatatype.equals("double")) {
			return EcorePackage.eINSTANCE.getEDouble();
		} else
			return EcorePackage.eINSTANCE.getEObject();
	}

	/**
	 * Processes a single edge of AGGs critical pair result.
	 * 
	 * @param morphismTargetObject The edge to be processed. In The wording of AGG this is an 'arc'.
	 * @param sequentialRule The information, if it is the first or the second rule of the critical pair.
	 * @param arcIsCritical The information whether this edge(arc) is a critical element.
	 * @param criticalGraphMapping The mapping of the AGG overlap graph, also called minimal model of AGG and the nodes
	 *            in the two Henshin rules forming the critical pair.
	 * @throws Exception in case of a duplicate edge. When the graph result contains a duplicate edge, this is not
	 *             conform to the metametamodel. There cant be two edges of the same kind between two nodes.
	 */
	private void processEdgeOfAGGResult(GraphObject morphismTargetObject, SequentialRule sequentialRule,
			boolean arcIsCritical, CriticalGraphMapping criticalGraphMapping) throws Exception {

		GraphObject sourceNode = ((Arc) morphismTargetObject).getSource();
		GraphObject targetNode = ((Arc) morphismTargetObject).getTarget();

		String sourceN = "" + sourceNode.hashCode();
		String targetN = "" + targetNode.hashCode();

		EClass from = (EClass) cpaEPackage.getEClassifier(sourceN);
		EClass to = (EClass) cpaEPackage.getEClassifier(targetN);

		if (from == null) {
			from = ecoreFactory.createEClass();
			from.setName("" + sourceNode.hashCode());
			cpaEPackage.getEClassifiers().add(from);
		}

		if (to == null) {
			to = ecoreFactory.createEClass();
			to.setName("" + targetNode.hashCode());
			cpaEPackage.getEClassifiers().add(to);
		}

		if (!hashToName.containsKey(morphismTargetObject.hashCode())) {
			EReference eReference = ecoreFactory.createEReference();
			eReference.setName("" + morphismTargetObject.hashCode());
			if (morphismTargetObject.isCritical()) {
				hashToName.put(morphismTargetObject.hashCode(), "#" + morphismTargetObject.getType().getName() + "#");
			} else {
				hashToName.put(morphismTargetObject.hashCode(), morphismTargetObject.getType().getName());
			}

			// this duplicateEdge check filters results which are based on duplicated edges between nodes.
			// since this is not possible in ecore we wont provide this result.
			boolean duplicateEdge = false;

			for (EStructuralFeature structuralFeature : from.getEStructuralFeatures()) {
				if (structuralFeature instanceof EReference) {
					if (structuralFeature.getEType().getName().equals(to.getName())) {
						if (hashToName.get(Integer.parseInt(structuralFeature.getName()))
								.equals(hashToName.get(Integer.parseInt(eReference.getName())))) {
							duplicateEdge = true;
						}
					}
				}
			}
			if (duplicateEdge) {
				System.out.println("duplicateEdge - duplicateEdge - duplicateEdge - duplicateEdge");
				throw new Exception(
						"duplicate edge - the graph results contains a duplicate edge, which is not conform to the metametamodel - there cant be two edges of the same kind between two nodes!");
			}
			eReference.setEType(to);
			from.getEStructuralFeatures().add(eReference);
		}

		if (arcIsCritical) {
			if (sequentialRule == SequentialRule.FirstRule) {
				CriticalElement critEdgeElem = new CriticalElement();
				criticalElements.add(critEdgeElem);
				Node henshinSourceNode = criticalGraphMapping.getFirstRuleNode(sourceNode);
				Node henshinTargetNode = criticalGraphMapping.getFirstRuleNode(targetNode);
				if (henshinSourceNode == null || henshinTargetNode == null)
					System.err.println("WARNING! - cant process the critical edge '" + morphismTargetObject.toString()
							+ "' since related henshin node cant be resolved.");
				for (Edge edge : henshinSourceNode.getOutgoing()) {
					if (edge.getTarget() == henshinTargetNode) { // correct edge found
						critEdgeElem.elementInFirstRule = edge;
						critEdgeElem.commonElementOfCriticalGraph = morphismTargetObject;
					}
				}

			}

			if (sequentialRule == SequentialRule.SecondRule) {
				Node henshinSourceNode = criticalGraphMapping.getSecondRuleNode(sourceNode);
				Node henshinTargetNode = criticalGraphMapping.getSecondRuleNode(targetNode);
				// if (henshinSourceNode == null || henshinTargetNode == null)
				// System.err.println("WARNING! - cant process the critical edge '" + morphismTargetObject.toString()
				// + "' since related henshin node cant be resolved.");
				// 1. extraction of the edge in the Henshin rule
				Edge correspondingHenshinEdge = null;
				for (Edge edge : henshinSourceNode.getOutgoing()) {
					if (edge.getTarget() == henshinTargetNode) { // correct edge found
						correspondingHenshinEdge = edge;
						break;
					}
				}
				if (correspondingHenshinEdge == null) {
					List<Node> potentialHenshinSourceNodesOfNestedConditions = criticalGraphMapping
							.getSecondRuleNodesOfNestedGraphs(sourceNode);
					List<Node> potentialHenshinTargetNodesOfNestedConditions = criticalGraphMapping
							.getSecondRuleNodesOfNestedGraphs(targetNode);
					for (Node potentialSourceNode : potentialHenshinSourceNodesOfNestedConditions) {
						for (Edge edge : potentialSourceNode.getOutgoing()) {
							if (criticalGraphMapping.getSecondRuleNode(targetNode) == edge.getTarget()
									|| potentialHenshinTargetNodesOfNestedConditions.contains(edge.getTarget()))
								correspondingHenshinEdge = edge;
						}
					}
				}
				if (correspondingHenshinEdge != null) {
					for (CriticalElement critElem : criticalElements) {
						if (critElem.elementInFirstRule instanceof Edge) {
							if (critElem.commonElementOfCriticalGraph == morphismTargetObject) {
								critElem.elementInSecondRule = correspondingHenshinEdge;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Extracts the kind of the critical pair out of AGGs <code>CriticalPairData</code>.
	 * 
	 * @param cpd The <code>CriticalPairData</code> containing the kind of conflict or dependency.
	 * @return a String with the kind of conlfict or dependency.
	 */
	private String getCriticalPairKindString(CriticalPairData cpd) {
		if (criticalPairType == CPType.Conflict) {
			return transformCriticalKindOfConflict(cpd).toString(); // TODO: may return NULL - handle this case
		} else if (criticalPairType == CPType.Dependency) {
			return transformCriticalKindOfDependency(cpd).toString(); // TODO: may return NULL - handle this case
		}
		return null;
	}

	/**
	 * Rename the EClassifiers from <code>cpaEPackage</code> according to <code>hashToName</code>.
	 * 
	 * @param hashToName A <code>HashMap</code> for mapping unique Hash ID to the correct name.
	 * @param cpaEPackage The container of the minimal model.
	 */
	private void rename(Map<Integer, String> hashToName, EPackage cpaEPackage) {
		for (EClassifier eclass : cpaEPackage.getEClassifiers()) {
			int oldName = Integer.parseInt(eclass.getName());
			String newName = hashToName.get(oldName);
			eclass.setName(newName);

			for (EStructuralFeature eref : ((EClass) eclass).getEStructuralFeatures()) {
				try {
					int oldRefName = Integer.parseInt(eref.getName());
					String newRefName = hashToName.get(oldRefName);
					eref.setName(newRefName);
				} catch (NumberFormatException e) {
				}
			}
		}
	}

	/**
	 * transforms AGG's kind of critical pair (from <code>cpd</code>) into our own dependency kinds
	 * 
	 * @param cpd The critical pair data from AGG.
	 * @return matching henshin CPA kind with type <code>DependencyKind</code>.
	 */
	private DependencyKind transformCriticalKindOfDependency(CriticalPairData cpd) {
		switch (cpd.getKindOfCurrentCritical()) {
		case CriticalPairData.DELETE_FORBID_DEPENDENCY:
			return DependencyKind.DELETE_FORBID_DEPENDENCY;
		case CriticalPairData.CHANGE_FORBID_ATTR_DEPENDENCY:
			return DependencyKind.CHANGE_FORBID_ATTR_DEPENDENCY;
		case CriticalPairData.PRODUCE_USE_DEPENDENCY:
			return DependencyKind.PRODUCE_USE_DEPENDENCY;
		case CriticalPairData.PRODUCE_NEED_DEPENDENCY:
			return DependencyKind.PRODUCE_USE_DEPENDENCY;
		case CriticalPairData.CHANGE_USE_ATTR_DEPENDENCY:
			return DependencyKind.CHANGE_USE_ATTR_DEPENDENCY;
		case CriticalPairData.CHANGE_NEED_ATTR_DEPENDENCY:
			return DependencyKind.CHANGE_USE_ATTR_DEPENDENCY;
		case CriticalPairData.PRODUCE_DELETE_DEPENDENCY:
			return DependencyKind.PRODUCE_USE_DEPENDENCY;
		case CriticalPairData.PRODUCE_CHANGE_DEPENDENCY:
			return DependencyKind.PRODUCE_USE_DEPENDENCY;
		}
		return null;
	}

	/**
	 * transforms AGG's kind of critical pair (from <code>cpd</code>) into our own conflict kinds
	 * 
	 * @param cpd the critical pair data from AGG
	 * @return matching henshin CPA kind with type <code>ConflictKind</code> (see enum ConflictKind.java)
	 */
	private ConflictKind transformCriticalKindOfConflict(CriticalPairData cpd) {
		switch (cpd.getKindOfCurrentCritical()) {
		case CriticalPairData.DELETE_USE_CONFLICT:
			return ConflictKind.DELETE_USE_CONFLICT;
		case CriticalPairData.DELETE_NEED_CONFLICT:
			return ConflictKind.DELETE_USE_CONFLICT;
		case CriticalPairData.PRODUCE_FORBID_CONFLICT:
			return ConflictKind.PRODUCE_FORBID_CONFLICT;
		case CriticalPairData.PRODUCE_EDGE_DELTE_NODE_CONFLICT:
			return ConflictKind.PRODUCE_EDGE_DELETE_NODE_CONFLICT;
		case CriticalPairData.CHANGE_USE_ATTR_CONFLICT:
			return ConflictKind.CHANGE_USE_ATTR_CONFLICT;
		case CriticalPairData.CHANGE_NEED_ATTR_CONFLICT:
			return ConflictKind.CHANGE_USE_ATTR_CONFLICT;
		case CriticalPairData.CHANGE_FORBID_ATTR_CONFLICT:
			return ConflictKind.CHANGE_FORBID_ATTR_CONFLICT;
		}
		return null;
	}
}
