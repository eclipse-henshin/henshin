/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.cpa;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.cpa.result.CPAResult;
import org.eclipse.emf.henshin.cpa.result.Conflict;
import org.eclipse.emf.henshin.cpa.result.CriticalPair;
import org.eclipse.emf.henshin.cpa.result.Dependency;
import org.eclipse.emf.henshin.cpa.persist.CriticalPairNode;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

/**
 * Utility for the critical pair analysis. Up till now mainly used for persisting the results in the file system.
 * 
 * @author Kristopher Born
 *
 */
public class CPAUtility {

	/**
	 * Persists the results of a critical pair analysis in the file system.
	 * 
	 * @param cpaResult A <code>CPAResult</code> of a critical pair analysis.
	 * @param path The path for saving the full result set.
	 * @return a <code>HashMap</code> of the saved results.
	 */
	public static HashMap<String, Set<CriticalPairNode>> persistCpaResult(CPAResult cpaResult, String path) {

		Date timestamp = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd-HHmmss");
		String timestampFolder = simpleDateFormat.format(timestamp);

		String pathWithDateStamp = path + File.separator + timestampFolder;

		HashMap<String, Set<CriticalPairNode>> persistedCPs = new HashMap<String, Set<CriticalPairNode>>();

		for (CriticalPair cp : cpaResult) {
			// naming of each single conflict
			String folderName = cp.getFirstRule().getName() + ", " + cp.getSecondRule().getName();

			int numberForRulePair = 1;

			if (persistedCPs.containsKey(folderName)) {
				numberForRulePair = persistedCPs.get(folderName).size() + 1;
			} else {
				persistedCPs.put(folderName, new HashSet<CriticalPairNode>());
			}

			String criticalPairKind = "";
			if (cp instanceof Conflict) {
				criticalPairKind = ((Conflict) cp).getConflictKind().toString();
			} else if (cp instanceof Dependency) {
				criticalPairKind = ((Dependency) cp).getDependencyKind().toString();
			}

			String formatedNumberForRulePair = new DecimalFormat("00").format(numberForRulePair);

			String numberedNameOfCPKind = "(" + formatedNumberForRulePair + ") " + criticalPairKind;

			// persist a single critical pair.
			CriticalPairNode newCriticalPairNode = persistSingleCriticalPair(cp, numberedNameOfCPKind,
					pathWithDateStamp);

			persistedCPs.get(folderName).add(newCriticalPairNode);
		}

		return persistedCPs;
	}

	/**
	 * Persists a single critical pair (<code>cp</code>) in the file system.
	 * 
	 * @param cp The <code>CriticalPair</code> to be saved.
	 * @param numberedNameOfCriticalPair The numbered name of the critical pair.
	 * @param path The path for saving the files.
	 * @return a <code>CriticalPairNode</code>.
	 */
	private static CriticalPairNode persistSingleCriticalPair(CriticalPair cp, String numberedNameOfCriticalPair,
			String path) {

		ResourceSet commonResourceSet = new ResourceSetImpl();

		Match firstMatch = null;
		Match secondMatch = null;

		if (cp instanceof Conflict) {
			firstMatch = ((Conflict) cp).getMatch1();
			secondMatch = ((Conflict) cp).getMatch2();
		} else if (cp instanceof Dependency) {
			firstMatch = ((Dependency) cp).getComatch();
			secondMatch = ((Dependency) cp).getMatch();
		}

		Rule firstRule = cp.getFirstRule();
		EPackage minimalModel = cp.getMinimalModel();
		Rule secondRule = cp.getSecondRule();

		Graph firstRuleLHS = firstRule.getLhs();
		Graph firstRuleRHS = firstRule.getRhs();
		Graph secondRuleLHS = secondRule.getLhs();
		Graph secondRuleRHS = secondRule.getRhs();

		// serves for naming back the nodes of the involved rules
		HashMap<Node, String> renameMap = new HashMap<Node, String>();

		// this counter serves to give each node in the minimal model a unique and ascending number.
		int differentElementsCounter = 0;
		Map<Integer, String> hashToName = new HashMap<Integer, String>();

		Match match = firstMatch;
		// ------------------- First Rule: LHS -----------------;
		differentElementsCounter = processLhsOrRhsOfRuleForPersisting(minimalModel, firstRuleLHS,
				differentElementsCounter, hashToName, match, renameMap);

		// ------------------- First Rule: RHS -----------------;
		differentElementsCounter = processLhsOrRhsOfRuleForPersisting(minimalModel, firstRuleRHS,
				differentElementsCounter, hashToName, match, renameMap);

		match = secondMatch;

		// ------------------- Second Rule: LHS -----------------;
		differentElementsCounter = processLhsOrRhsOfRuleForPersisting(minimalModel, secondRuleLHS,
				differentElementsCounter, hashToName, match, renameMap);

		// ------------------- Second Rule: RHS -----------------;
		differentElementsCounter = processLhsOrRhsOfRuleForPersisting(minimalModel, secondRuleRHS,
				differentElementsCounter, hashToName, match, renameMap);

		// process the NACs of the second rule
		EList<NestedCondition> nestedConditions = secondRuleLHS.getNestedConditions();
		// ------------------- First Rule: NAC -----------------;
		for (NestedCondition nestCond : nestedConditions) {
			if (nestCond.isNAC()) {
				Graph secondRuleNAC = nestCond.getConclusion();
				EList<Node> nacNodes = secondRuleNAC.getNodes();
				for (Node nacNode : nacNodes) {
					if (match.getNodeTarget(nacNode) != null) {
						int overlapNodeHash = match.getNodeTarget(nacNode).hashCode();
						if (hashToName.containsKey(overlapNodeHash)) {

							String newName = hashToName.get(overlapNodeHash);
							renameMap.put(nacNode, nacNode.getName());
							nacNode.setName(newName);

						} else {

							differentElementsCounter++;
							String newName = differentElementsCounter + "";
							renameMap.put(nacNode, nacNode.getName());
							nacNode.setName(newName);

							EList<EClassifier> eclasses = minimalModel.getEClassifiers();

							for (EClassifier eclass : eclasses) {
								if (eclass.hashCode() == overlapNodeHash) {
									hashToName.put(overlapNodeHash, newName);
									break;
								}
							}
						}
					}
				}
			} else {
				break;
			}
		}

		// renaming of the nodes within the overlap graph
		renameNodesOfMinimalModel(minimalModel, hashToName);

		String pathForCurrentCriticalPair = path + File.separator + firstRule.getName() + "_AND_"
				+ secondRule.getName() + File.separator + numberedNameOfCriticalPair + File.separator;

		// save the first rule in the file system
		String fileNameRule1 = "(1)" + firstRule.getName() + ".henshin";
		String fullPathRule1 = pathForCurrentCriticalPair + fileNameRule1;
		URI firstRuleURI = saveRuleInFileSystem(commonResourceSet, firstRule, fullPathRule1);

		// save the minimal model in the file system
		String fileNameMinimalModel = "minimal-model" + ".ecore";
		String fullPathMinimalModel = pathForCurrentCriticalPair + fileNameMinimalModel;

		URI overlapURI = saveMinimalModelInFileSystem(commonResourceSet, minimalModel, fullPathMinimalModel);

		// save the second rule in the file system
		String fileNameRule2 = "(2)" + secondRule.getName() + ".henshin";
		String fullPathRule2 = pathForCurrentCriticalPair + fileNameRule2;
		URI secondRuleURI = saveRuleInFileSystem(commonResourceSet, secondRule, fullPathRule2);

		// save a dummy for the HenshinCPEditor
		String fileName = "dummy.henshinCp";
		String fullPath = pathForCurrentCriticalPair + fileName;
		URI criticalPairURI = saveCriticalPairInFileSystem(commonResourceSet, null/* criticalPairOfThisProcess */,
				fullPath);

		// rename the Nodes of the rules back to have the original rule for the renaming for the next processed critical
		// pair.
		for (Node node : renameMap.keySet()) {
			node.setName(renameMap.get(node));
		}

		return new CriticalPairNode(numberedNameOfCriticalPair, firstRuleURI, secondRuleURI, overlapURI,
				criticalPairURI);
	}

	/**
	 * Renames the nodes of the minimal model based on the names of the rules.
	 * 
	 * @param minimalModel The <code>EGraph</code>, which should be a minimal model of a critical pair.
	 * @param hashValueToNameMapping A HashMap, mapping the future names to the hash values of the nodes of the rules.
	 */
	private static void renameNodesOfMinimalModel(EPackage minimalModel, Map<Integer, String> hashValueToNameMapping) {
		EList<EClassifier> eclasses = minimalModel.getEClassifiers();
		for (EClassifier eclass : eclasses) {
			String name = eclass.getName();
			String newName = hashValueToNameMapping.get(eclass.hashCode()) + ":" + name;
			eclass.setName(newName);
		}
	}

	/**
	 * Saves an <code>EGraph</code>, which might be a minimal model on the given path within the file system.
	 * 
	 * @param resourceSetThe common <code>ResourceSet</code>.
	 * @param minimalModel The minimal model to be saved.
	 * @param fullPathMinimalModel The full path of the file.
	 * @return the <code>URI</code> of the saved file.
	 */
	private static URI saveMinimalModelInFileSystem(ResourceSet resourceSet, EPackage minimalModel,
			String fullPathMinimalModel) {
		URI overlapURI = URI.createFileURI(fullPathMinimalModel);
		Resource overlapResource = resourceSet.createResource(overlapURI, "ecore");

		overlapResource.getContents().add(minimalModel);

		try {
			overlapResource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return overlapURI;
	}

	/**
	 * Saves a <code>Rule</code> on the given path within the file system.
	 * 
	 * @param resourceSet The common <code>ResourceSet</code>.
	 * @param rule The <code>Rule</code> to be saved.
	 * @param fullFilePath The full path of the file.
	 * @return the <code>URI</code> of the saved file.
	 */
	private static URI saveRuleInFileSystem(ResourceSet resourceSet, Rule rule, String fullFilePath) {
		URI firstRuleURI = URI.createFileURI(fullFilePath);
		Resource firstRuleRes = resourceSet.createResource(firstRuleURI, "henshin");
		firstRuleRes.getContents().add(rule);

		try {
			firstRuleRes.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return firstRuleURI;
	}

	/**
	 * Processes the the LHS or RHS of a rule by renaming the nodes in the minimal model.
	 * 
	 * @param minimalModel The minimal model of the critical pair.
	 * @param LHS_or_RHS_ofRule The LHS or RHS of a rule.
	 * @param differentElementsCounter A counter to give each node in the minimal model a unique and ascending number.
	 * @param hashToName a <code>HashMap</code> to store associated names in the minimal model to the nodes of the
	 *            rules.
	 * @param match The match linking the rule into the minimal model.
	 * @return The resulting value of the <code>differentElementsCounter</code>
	 */
	private static int processLhsOrRhsOfRuleForPersisting(EPackage minimalModel, Graph LHS_or_RHS_ofRule,
			int differentElementsCounter, Map<Integer, String> hashToName, Match match, HashMap<Node, String> renameMap) {
		for (Node elem : LHS_or_RHS_ofRule.getNodes()) {
			if (match.getNodeTarget(elem) != null) {
				int overlapNodeHash = match.getNodeTarget(elem).hashCode();
				if (hashToName.containsKey(overlapNodeHash)) {

					String newName = hashToName.get(overlapNodeHash);
					renameMap.put(elem, elem.getName());
					elem.setName(newName);
				} else {
					differentElementsCounter++;

					String newName = differentElementsCounter + "";
					renameMap.put(elem, elem.getName());
					elem.setName(newName);

					EList<EClassifier> eclasses = minimalModel.getEClassifiers();

					for (EClassifier eclass : eclasses) {
						if (eclass.hashCode() == overlapNodeHash) {
							hashToName.put(overlapNodeHash, newName);
						}
					}
				}
			}
		}
		return differentElementsCounter;
	}

	/**
	 * Changes the order of the rules within the module based on the order of the strings.
	 * 
	 * @param module The <code>Module</code> in which the order of the rules shall be changed.
	 * @param newRuleOrder The new order of the rules based on a set of sorted <code>String</code> values.
	 */
	public void changeRuleOrder(Module module, String[] newRuleOrder) {
		Vector<String> ruleOrderList = new Vector<String>(Arrays.asList(newRuleOrder));

		BasicEList<Unit> newSortedRulesList = new BasicEList<Unit>();

		// 1. order of Rules
		for (String nameOfOrderedRule : ruleOrderList) {
			for (Unit rule : module.getUnits()) {
				if (rule.getName().equals(nameOfOrderedRule)) {
					newSortedRulesList.add(rule);
				}
			}
		}

		// add remaining rules
		// 1.1 reduce original rules by already processed ones
		for (Unit rule : newSortedRulesList) {
			module.getUnits().remove(rule);
		}
		// add the remaining rules to the
		newSortedRulesList.addAll(module.getUnits());

		// 1.2 replace old list by new list
		module.getUnits().clear();
		module.getUnits().addAll(newSortedRulesList);
	}

	/**
	 * This method clears the both lists. The rules within the <code>Module</code> will be searched for the two of which
	 * the names had been passed and each rule will be added to the associated <code>List</code>. This method serves for
	 * analyzing explicit combinations of rules. Frequently used in the test suite.
	 * 
	 * @param module The <code>Module</code> containing the rules.
	 * @param firstRule The first <code>Rule</code> as a <code>List</code>. The <code>List</code> will be cleared and
	 *            afterwards filled with the first <code>Rule</code> as single containment of the <code>List</code>.
	 * @param firstRuleName The name of the first <code>Rule</code>, which will be searched in the <code>Module</code>
	 *            and added to the <code>List</code>.
	 * @param secondRule The second <code>Rule</code> as a <code>List</code>. The <code>List</code> will be cleared and
	 *            afterwards filled with the second <code>Rule</code> as single containment of the <code>List</code>.
	 * @param secondRuleName The name of the second <code>Rule</code>, which will be searched in the <code>Module</code>
	 *            and added to the <code>List</code>.
	 */
	public static void extractSingleRules(Module module, List<Rule> firstRule, String firstRuleName,
			List<Rule> secondRule, String secondRuleName) {
		for (Unit unit : module.getUnits()) {
			if (unit.getName().equalsIgnoreCase(firstRuleName))
				firstRule.add((Rule) unit);
			if (unit.getName().equalsIgnoreCase(secondRuleName))
				secondRule.add((Rule) unit);
		}
	}

	/**
	 * Extracts all the rules of a module within a List.
	 * 
	 * @param module The <code>Module</code> containing the rules.
	 * @return a <code>List</code> containing the <code>Rule</code>s within the <code>Module</code>.
	 */
	public static List<Rule> extractAllRules(Module module) {
		List<Rule> allExtractedRules = new LinkedList<Rule>();
		for (Unit unit : module.getUnits()) {
			if (unit instanceof Rule)
				allExtractedRules.add((Rule) unit);
		}
		return allExtractedRules;
	}

	private static URI saveCriticalPairInFileSystem(ResourceSet resourceSet,
			org.eclipse.emf.henshin.cpa.criticalpair.CriticalPair criticalPairOfThisProcess, String fullPath) {
		URI fileURI = URI.createFileURI(fullPath);
		Resource criticalPairResource = resourceSet.createResource(fileURI, "henshinCp");

		try {
			criticalPairResource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileURI;
	}
}