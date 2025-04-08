/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http:
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa.importer;

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
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.multicda.cpa.modelExtension.ComatchImpl;
import org.eclipse.emf.henshin.multicda.cpa.modelExtension.ExtendedMatchImpl;
import org.eclipse.emf.henshin.multicda.cpa.result.CPAResult;
import org.eclipse.emf.henshin.multicda.cpa.result.Conflict;
import org.eclipse.emf.henshin.multicda.cpa.result.ConflictKind;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalElement;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair.AppliedAnalysis;
import org.eclipse.emf.henshin.multicda.cpa.result.Dependency;
import org.eclipse.emf.henshin.multicda.cpa.result.DependencyKind;

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
	public CPAResult importExcludePairContainer(ExcludePairContainer epc, boolean essential) {
		CPAResult cPAresult = new CPAResult();

		if (epc instanceof DependencyPairContainer) {
			criticalPairType = CPType.Dependency;
		} else {
			criticalPairType = CPType.Conflict;
		}

		List<Rule> rules1 = epc.getRules();
		List<Rule> rules2 = epc.getRules2();
		AppliedAnalysis appliedAnalysis = essential ? AppliedAnalysis.ESSENTIAL : AppliedAnalysis.COMPLETE;
		for (Rule rule1 : rules1) {
			for (Rule rule2 : rules2) {
				CriticalPairData cpd = epc.getCriticalPairData(rule1, rule2);

				if (cpd == null)
					continue;
				while (cpd.next()) {
					processAGGresultOfRulePair(cPAresult, rule1, rule2, cpd, appliedAnalysis);
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
	private void processAGGresultOfRulePair(CPAResult result, Rule rule1, Rule rule2, CriticalPairData cpd,
			AppliedAnalysis appliedAnalysis) {

		criticalElements = new LinkedList<CriticalElement>();

		boolean validCriticalPair = true;

		cpaEPackage = ecoreFactory.createEPackage();

		cpaEPackage.setName(rule1.getQualifiedName() + "_" + rule2.getQualifiedName());
		cpaEPackage.setNsPrefix("CPAPackage");

		String criticalPairKind = getCriticalPairKindString(cpd);
		cpaEPackage.setNsURI("http://cpapackage/" + rule1.getQualifiedName() + "/" + rule2.getQualifiedName() + "/"
				+ criticalPairKind);

		org.eclipse.emf.henshin.model.Rule firstHenshinRuleOriginal = getResultRule(rule1.getName());
		org.eclipse.emf.henshin.model.Rule secondHenshinRuleOriginal = getResultRule(rule2.getName());

		ExtendedMatchImpl firstRuleCopyMatch;
		ExtendedMatchImpl secondRuleCopyMatch;

		hashToName = new HashMap<Integer, String>();

		OrdinaryMorphism morph1 = cpd.getMorph1();

		if (criticalPairType == CPType.Dependency) {
			firstRuleCopyMatch = new ComatchImpl(firstHenshinRuleOriginal, true);
		} else {
			firstRuleCopyMatch = new ExtendedMatchImpl(firstHenshinRuleOriginal, true);
		}

		Vector<GraphObject> morph1SourceObjects1 = morph1.getDomainObjects();
		Vector<GraphObject> morph1TargetObjects1 = morph1.getCodomainObjects();

		List<Node> processedHenshinRuleLhsNodes = new LinkedList<Node>();
		List<Node> processedHenshinRuleRhsNodes = new LinkedList<Node>();

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

					}
				}
				nodes = rule1rhs.getNodes();
				for (Node fnode : nodes) {
					if (fnode.getType().getName().equals(sourceName) && !processedHenshinRuleRhsNodes.contains(fnode)) {
						henshinNodeRhs = fnode;
						processedHenshinRuleRhsNodes.add(fnode);
						firstRuleRhsMapping.put(morph1TargetObject, fnode);
						break;

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

				EClass targetEClass = ecoreFactory.createEClass();
				targetEClass.setName("" + morph1TargetObject.hashCode());

				processAttributesOfMorphism(morph1TargetObject, targetEClass);

				if (morph1TargetObject.isCritical()) {
					hashToName.put(morph1TargetObject.hashCode(), "#" + morph1TargetObject.getType().getName() + "#");

					CriticalElement criticalElement = new CriticalElement();
					criticalElements.add(criticalElement);

					criticalElement.commonElementOfCriticalGraph = morph1TargetObject;
					if (criticalPairType == CPType.Conflict) {

						criticalElement.elementInFirstRule = henshinNodeLhs;

						if (transformCriticalKindOfConflict(cpd) == ConflictKind.CHANGE_USE_ATTR_CONFLICT

								|| transformCriticalKindOfConflict(cpd) == ConflictKind.CHANGE_FORBID_ATTR_CONFLICT
								|| transformCriticalKindOfConflict(cpd) == ConflictKind.PRODUCE_FORBID_CONFLICT) {

							boolean anyAttributeProcessed = false;

							for (Attribute henshinRhsAttribute : henshinNodeRhs.getAttributes()) {
								boolean attributeChanged = true;

								if (henshinNodeLhs != null)
									for (Attribute henshinLhsAttribute : henshinNodeLhs.getAttributes()) {
										boolean attributeTypeIdentical = henshinLhsAttribute
												.getType() == henshinRhsAttribute.getType();

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

										criticalElementforFurtherChangedAttribute.commonElementOfCriticalGraph = morph1TargetObject;
										criticalElementforFurtherChangedAttribute.elementInFirstRule = henshinRhsAttribute;
									}
								}
							}
							if (!anyAttributeProcessed) {

								criticalElement.elementInFirstRule = henshinNodeRhs;
							}
						}
					} else if (criticalPairType == CPType.Dependency) {

						if (transformCriticalKindOfDependency(cpd) == DependencyKind.PRODUCE_USE_DEPENDENCY)
							criticalElement.elementInFirstRule = henshinNodeRhs;

						if (transformCriticalKindOfDependency(cpd) == DependencyKind.DELETE_FORBID_DEPENDENCY)
							criticalElement.elementInFirstRule = henshinNodeLhs;
						if (transformCriticalKindOfDependency(cpd) == DependencyKind.CHANGE_USE_ATTR_DEPENDENCY) {
							boolean anyAttributeProcessed = false;

							for (Attribute henshinRhsAttribute : henshinNodeRhs.getAttributes()) {
								boolean attributeChanged = true;

								for (Attribute henshinLhsAttribute : henshinNodeLhs.getAttributes()) {
									boolean attributeTypeIdentical = henshinLhsAttribute
											.getType() == henshinRhsAttribute.getType();

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
										criticalElementforFurtherChangedAttribute.commonElementOfCriticalGraph = morph1TargetObject;
										criticalElementforFurtherChangedAttribute.elementInFirstRule = henshinRhsAttribute;
									}
								}
							}

							if (!anyAttributeProcessed) {

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
					if (criticalPairType == CPType.Conflict) {
						processEdgeOfAGGResult(morph1TargetObject, SequentialRule.FirstRule, arcIsCritical,
								criticalGraphMapping);
					} else if (criticalPairType == CPType.Dependency) {

						if (transformCriticalKindOfDependency(cpd) == DependencyKind.PRODUCE_USE_DEPENDENCY)
							processEdgeOfAGGResult(morph1TargetObject, SequentialRule.FirstRule, arcIsCritical,
									criticalGraphMapping);

						if (transformCriticalKindOfDependency(cpd) == DependencyKind.DELETE_FORBID_DEPENDENCY)
							processEdgeOfAGGResult(morph1TargetObject, SequentialRule.FirstRule, arcIsCritical,
									criticalGraphMapping);

						if (transformCriticalKindOfDependency(cpd) == DependencyKind.CHANGE_USE_ATTR_DEPENDENCY) {
							processEdgeOfAGGResult(morph1TargetObject, SequentialRule.FirstRule, arcIsCritical,
									criticalGraphMapping);
							System.err.println("Unimplemented yet");

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

		OrdinaryMorphism morph2 = cpd.getMorph2();

		boolean edgeProcessingOfSecondRuleBegun = false;

		Vector<GraphObject> morph2SourceObjects2 = morph2.getDomainObjects();
		Vector<GraphObject> morph2TargetObjects2 = morph2.getCodomainObjects();

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
//								henshinNodeNac.setName(fnode.getType().getName());
							}
						}
					} else if (nestCond.isPAC()) {

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
//					e.printStackTrace();
				}
			}
		}

		secondRuleCopyMatch.removeAllParameter(secondHenshinRuleOriginal.getParameters());

		if (validCriticalPair) {

			rename(hashToName, cpaEPackage);

			if (criticalPairType == CPType.Dependency) {
				Dependency dep = new Dependency(firstHenshinRuleOriginal, secondHenshinRuleOriginal, cpaEPackage,
						firstRuleCopyMatch, secondRuleCopyMatch, transformCriticalKindOfDependency(cpd),
						appliedAnalysis);

				dep.addCriticalElements(criticalElements);
				result.addResult(dep);
			} else if (criticalPairType == CPType.Conflict) {
				Conflict conf = new Conflict(firstHenshinRuleOriginal, secondHenshinRuleOriginal, cpaEPackage,
						firstRuleCopyMatch, secondRuleCopyMatch, transformCriticalKindOfConflict(cpd), appliedAnalysis);

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
			}

			if (existingCritElem.elementInFirstRule == null) {

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

				boolean dontProcessThisAttribute = false;

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
					if (edge.getTarget() == henshinTargetNode) {
						critEdgeElem.elementInFirstRule = edge;
						critEdgeElem.commonElementOfCriticalGraph = morphismTargetObject;
					}
				}

			}

			if (sequentialRule == SequentialRule.SecondRule) {
				Node henshinSourceNode = criticalGraphMapping.getSecondRuleNode(sourceNode);
				Node henshinTargetNode = criticalGraphMapping.getSecondRuleNode(targetNode);

				Edge correspondingHenshinEdge = null;
				for (Edge edge : henshinSourceNode.getOutgoing()) {
					if (edge.getTarget() == henshinTargetNode) {
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
			return transformCriticalKindOfConflict(cpd).toString();
		} else if (criticalPairType == CPType.Dependency) {
			return transformCriticalKindOfDependency(cpd).toString();
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
