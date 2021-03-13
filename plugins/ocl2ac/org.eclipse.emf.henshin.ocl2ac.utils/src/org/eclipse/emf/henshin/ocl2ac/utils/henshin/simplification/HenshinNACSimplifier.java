/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.utils.henshin.simplification;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.UnaryFormula;

import org.eclipse.emf.henshin.ocl2ac.utils.Activator;

public class HenshinNACSimplifier {

	private static final String PATTERN_HENSHIN_RELATIVE_PATH_NAME = "pattern/patterns.henshin";
	private static final String PATTERN_HENSHIN_PATH_NAME = getFullPath(PATTERN_HENSHIN_RELATIVE_PATH_NAME);
	private static final String FIND_NC_GR_OR = "find_NC_GR_OR";
	private static final String FIND_NC_GR_NOT_OR = "find_NC_GR_NOT_OR";
	private static final String FIND_NOTNOT = "find_NotNot";
	private static final String FIND_NC_GR_AND = "find_NC_GR_AND";
	private static final String FIND_NC_GR_NOT_AND = "find_NC_GR_NOT_AND";

	private static ArrayList<Graph> lsAndGraphs = new ArrayList<Graph>();
	private static ArrayList<Graph> lsOrGraphs = new ArrayList<Graph>();

	/**
	 * 
	 * @param rule
	 */
	public static void simplifyOrGraphsInAC(Rule rule) {
		NasHenshinCommand henCmd = new NasHenshinCommand(PATTERN_HENSHIN_PATH_NAME, rule.getLhs().getFormula());
		Rule rulePattern = (Rule) henCmd.getGrammarModule().getUnit(FIND_NC_GR_OR);
		List<Match> matches = henCmd.findAllMatches(rulePattern);
		Iterator<Match> iterator = matches.iterator();
		markAndRemoveUnwantedGraphsInOr(rulePattern, iterator);
		Rule rulePattern2 = (Rule) henCmd.getGrammarModule().getUnit(FIND_NC_GR_NOT_OR);
		List<Match> matches2 = henCmd.findAllMatches(rulePattern2);
		Iterator<Match> iterator2 = matches2.iterator();
		markAndRemoveUnwantedGraphsInOr(rulePattern2, iterator2);
	}

	/**
	 * 
	 * @param rule
	 */
	public static void simplifyAndGraphsInAC(Rule rule) {
		NasHenshinCommand henCmd = new NasHenshinCommand(PATTERN_HENSHIN_PATH_NAME, rule.getLhs().getFormula());
		Rule rulePattern = (Rule) henCmd.getGrammarModule().getUnit(FIND_NC_GR_AND);
		List<Match> matches = henCmd.findAllMatches(rulePattern);
		Iterator<Match> iterator = matches.iterator();
		markAndRemoveUnwantedGraphsInAnd(rulePattern, iterator);
		Rule rulePattern2 = (Rule) henCmd.getGrammarModule().getUnit(FIND_NC_GR_NOT_AND);
		List<Match> matches2 = henCmd.findAllMatches(rulePattern2);
		Iterator<Match> iterator2 = matches2.iterator();
		markAndRemoveUnwantedGraphsInAnd(rulePattern2, iterator2);
	}

	/**
	 * 
	 * @param rule
	 */
	public static void eliminateNotNotFromNAC(Rule rule) {
		NasHenshinCommand henCmd = new NasHenshinCommand(PATTERN_HENSHIN_PATH_NAME, rule.getLhs().getFormula());
		Rule rulePattern3 = (Rule) henCmd.getGrammarModule().getUnit(FIND_NOTNOT);
		List<Match> matches3 = henCmd.findAllMatches(rulePattern3);
		Iterator<Match> iterator3 = matches3.iterator();
		while (iterator3.hasNext()) {
			Match match = iterator3.next();
			Not outerNot = (Not) match.getParameterValue(rulePattern3.getParameter("outerNot"));
			Not innerNot = (Not) match.getParameterValue(rulePattern3.getParameter("innerNot"));
			if (outerNot.eContainer() instanceof Graph) {
				((Graph) outerNot.eContainer()).setFormula(innerNot.getChild());
			} else if (outerNot.eContainer() instanceof BinaryFormula) {
				BinaryFormula binaryFormula = (BinaryFormula) outerNot.eContainer();
				if (binaryFormula.getLeft() == outerNot) {
					binaryFormula.setLeft(innerNot.getChild());
				} else if (binaryFormula.getRight() == outerNot) {
					binaryFormula.setRight(innerNot.getChild());
				}
			}
		}
	}

	/**
	 * 
	 * @param rulePattern
	 * @param iterator
	 */
	private static void markAndRemoveUnwantedGraphsInOr(Rule rulePattern, Iterator<Match> iterator) {
		while (iterator.hasNext()) {
			Match match = iterator.next();
			Or orContainer = (Or) match.getParameterValue(rulePattern.getParameter("paramOR"));
			lsOrGraphs.clear();

			// collect graphs among only OR operators
			collecGraphsOfOnlyORs(orContainer);

			if (lsOrGraphs.size() > 0) {
				// finding the smallest subgraphs by comparing and marking them
				// with null in the list.
				for (int i = 0; i < lsOrGraphs.size() - 1; i++) {
					for (int j = i + 1; j < lsOrGraphs.size(); j++) {
						if (isSubGraph(lsOrGraphs.get(i), lsOrGraphs.get(j))) {
							System.out.println(
									lsOrGraphs.get(i).getName() + " is subgraphof " + lsOrGraphs.get(j).getName());
							lsOrGraphs.set(j, null);
						} else if (isSubGraph(lsOrGraphs.get(j), lsOrGraphs.get(i))) {
							System.out.println(
									lsOrGraphs.get(j).getName() + " is subgraphof " + lsOrGraphs.get(i).getName());
							lsOrGraphs.set(i, null);
							break;
						}
					}
				}
				lsOrGraphs.removeIf(Objects::isNull);

				// manage the unwanted graphs, e.g., by setting them null
				removeMarkedGraphs(orContainer);
			}
		}
	}

	/**
	 * 
	 * @param rulePattern
	 * @param iterator
	 */
	private static void markAndRemoveUnwantedGraphsInAnd(Rule rulePattern, Iterator<Match> iterator) {
		while (iterator.hasNext()) {
			Match match = iterator.next();
			And andContainer = (And) match.getParameterValue(rulePattern.getParameter("paramAND"));
			lsAndGraphs.clear();

			// collect graphs among only OR operators
			collecGraphsOfOnlyANDs(andContainer);

			if (lsAndGraphs.size() > 0) {
				// finding the smallest subgraphs by comparing and marking them
				// with null in the list.
				for (int i = 0; i < lsAndGraphs.size() - 1; i++) {
					for (int j = i + 1; j < lsAndGraphs.size(); j++) {
						if (isSubGraph(lsAndGraphs.get(i), lsAndGraphs.get(j))) {
							System.out.println(
									lsAndGraphs.get(i).getName() + " is subgraphof " + lsAndGraphs.get(j).getName());
							lsAndGraphs.set(i, null);
							break;
						} else if (isSubGraph(lsAndGraphs.get(j), lsAndGraphs.get(i))) {
							System.out.println(
									lsAndGraphs.get(j).getName() + " is subgraphof " + lsAndGraphs.get(i).getName());
							lsAndGraphs.set(j, null);
						}
					}
				}
				lsAndGraphs.removeIf(Objects::isNull);
				// manage the unwanted graphs, e.g., by setting them null
				removeMarkedGraphs(andContainer);
			}
		}
	}

	/**
	 * collect the graphs among only OR operators in the same level
	 * 
	 * @param or
	 */
	private static void collecGraphsOfOnlyORs(Or or) {
		if (or.getLeft() instanceof Or) {
			collecGraphsOfOnlyORs((Or) or.getLeft());
		} else if (or.getLeft() instanceof NestedCondition) {
			NestedCondition leftNC = (NestedCondition) or.getLeft();
			Graph conclusion = leftNC.getConclusion();
			lsOrGraphs.add(conclusion);
		} else if (or.getLeft() instanceof UnaryFormula) {
			Not left = (Not) or.getLeft();
			if (left.getChild() instanceof NestedCondition) {
				NestedCondition leftNC = (NestedCondition) left.getChild();
				Graph conclusion = leftNC.getConclusion();
				lsOrGraphs.add(conclusion);
			}
		} else {
			// there is a mixing between OR operators and other operators in the
			// same level
			lsOrGraphs.clear();
			return;
		}

		if (or.getRight() instanceof Or) {
			collecGraphsOfOnlyORs((Or) or.getRight());
		} else if (or.getLeft() instanceof NestedCondition) {
			NestedCondition rightNC = (NestedCondition) or.getRight();
			Graph conclusion = rightNC.getConclusion();
			lsOrGraphs.add(conclusion);
		} else if (or.getRight() instanceof UnaryFormula) {
			Not right = (Not) or.getRight();
			if (right.getChild() instanceof NestedCondition) {
				NestedCondition rightNC = (NestedCondition) right.getChild();
				Graph conclusion = rightNC.getConclusion();
				lsOrGraphs.add(conclusion);
			}
		} else {
			// there is a mixing between OR operators and other operators in the
			// same level
			lsOrGraphs.clear();
			return;
		}

	}

	/**
	 * collect the graphs among only OR operators in the same level
	 * 
	 * @param and
	 */
	private static void collecGraphsOfOnlyANDs(And and) {
		if (and.getLeft() instanceof And) {
			collecGraphsOfOnlyANDs((And) and.getLeft());
		} else if (and.getLeft() instanceof NestedCondition) {
			NestedCondition leftNC = (NestedCondition) and.getLeft();
			Graph conclusion = leftNC.getConclusion();
			lsAndGraphs.add(conclusion);
		} else if (and.getLeft() instanceof UnaryFormula) {
			Not left = (Not) and.getLeft();
			if (left.getChild() instanceof NestedCondition) {
				NestedCondition leftNC = (NestedCondition) left.getChild();
				Graph conclusion = leftNC.getConclusion();
				lsAndGraphs.add(conclusion);
			}
		} else {
			// there is a mixing between OR operators and other operators in the
			// same level
			lsAndGraphs.clear();
			return;
		}

		if (and.getRight() instanceof And) {
			collecGraphsOfOnlyANDs((And) and.getRight());
		} else if (and.getLeft() instanceof NestedCondition) {
			NestedCondition rightNC = (NestedCondition) and.getRight();
			Graph conclusion = rightNC.getConclusion();
			lsAndGraphs.add(conclusion);
		} else if (and.getRight() instanceof UnaryFormula) {
			Not right = (Not) and.getRight();
			if (right.getChild() instanceof NestedCondition) {
				NestedCondition rightNC = (NestedCondition) right.getChild();
				Graph conclusion = rightNC.getConclusion();
				lsAndGraphs.add(conclusion);
			}
		} else {
			// there is a mixing between OR operators and other operators in the
			// same level
			lsAndGraphs.clear();
			return;
		}

	}

	/**
	 * Remove unwanted subgraphs and refactor the condition.
	 * 
	 * @param or
	 */
	private static void removeMarkedGraphs(Or or) {
		if (or.getLeft() instanceof Or) {
			collecGraphsOfOnlyORs((Or) or.getLeft());
		} else if (or.getLeft() instanceof NestedCondition) {
			NestedCondition leftNC = (NestedCondition) or.getLeft();
			Graph conclusion = leftNC.getConclusion();
			if (!lsOrGraphs.contains(conclusion)) {
				or.setLeft(null);
				refactorLeftChild(or);
			}
		} else if (or.getLeft() instanceof UnaryFormula) {
			Not left = (Not) or.getLeft();
			if (left.getChild() instanceof NestedCondition) {
				NestedCondition leftNC = (NestedCondition) left.getChild();
				Graph conclusion = leftNC.getConclusion();
				if (!lsOrGraphs.contains(conclusion)) {
					or.setLeft(null);
					refactorLeftChild(or);
				}
			}
		}

		if (or.getRight() instanceof Or) {
			collecGraphsOfOnlyORs((Or) or.getRight());
		} else if (or.getLeft() instanceof NestedCondition) {
			NestedCondition rightNC = (NestedCondition) or.getRight();
			Graph conclusion = rightNC.getConclusion();
			if (!lsOrGraphs.contains(conclusion)) {
				or.setRight(null);
				refactorRightChild(or);
			}
		} else if (or.getRight() instanceof UnaryFormula) {
			Not right = (Not) or.getRight();
			if (right.getChild() instanceof NestedCondition) {
				NestedCondition rightNC = (NestedCondition) right.getChild();
				Graph conclusion = rightNC.getConclusion();
				if (!lsOrGraphs.contains(conclusion)) {
					or.setRight(null);
					refactorRightChild(or);
				}
			}
		}

	}

	/**
	 * Remove unwanted subgraphs and refactor the condition.
	 * 
	 * @param and
	 */
	private static void removeMarkedGraphs(And and) {
		if (and.getLeft() instanceof And) {
			collecGraphsOfOnlyANDs((And) and.getLeft());
		} else if (and.getLeft() instanceof NestedCondition) {
			NestedCondition leftNC = (NestedCondition) and.getLeft();
			Graph conclusion = leftNC.getConclusion();
			if (!lsAndGraphs.contains(conclusion)) {
				and.setLeft(null);
				refactorLeftChild(and);
			}
		} else if (and.getLeft() instanceof UnaryFormula) {
			Not left = (Not) and.getLeft();
			if (left.getChild() instanceof NestedCondition) {
				NestedCondition leftNC = (NestedCondition) left.getChild();
				Graph conclusion = leftNC.getConclusion();
				if (!lsAndGraphs.contains(conclusion)) {
					and.setLeft(null);
					refactorLeftChild(and);
				}
			}
		}

		if (and.getRight() instanceof And) {
			collecGraphsOfOnlyANDs((And) and.getRight());
		} else if (and.getLeft() instanceof NestedCondition) {
			NestedCondition rightNC = (NestedCondition) and.getRight();
			Graph conclusion = rightNC.getConclusion();
			if (!lsAndGraphs.contains(conclusion)) {
				and.setRight(null);
				refactorRightChild(and);
			}
		} else if (and.getRight() instanceof UnaryFormula) {
			Not right = (Not) and.getRight();
			if (right.getChild() instanceof NestedCondition) {
				NestedCondition rightNC = (NestedCondition) right.getChild();
				Graph conclusion = rightNC.getConclusion();
				if (!lsAndGraphs.contains(conclusion)) {
					and.setRight(null);
					refactorRightChild(and);
				}
			}
		}

	}

	/**
	 * 
	 * @param bnf
	 */
	private static void refactorRightChild(BinaryFormula bnf) {
		if (bnf.eContainer() instanceof Graph) {
			Graph gc = (Graph) bnf.eContainer();
			gc.setFormula(bnf.getLeft());
		} else if (bnf.eContainer() instanceof BinaryFormula) {
			BinaryFormula conf = (BinaryFormula) bnf.eContainer();
			if (conf.getLeft() == bnf) {
				conf.setLeft(bnf.getLeft());
			} else if (conf.getRight() == bnf) {
				conf.setRight(bnf.getLeft());
			}
		} else if (bnf.eContainer() instanceof Not) {
			Not not = (Not) bnf.eContainer();
			not.setChild(bnf.getLeft());
		}
	}

	/**
	 * 
	 * @param bnf
	 */
	private static void refactorLeftChild(BinaryFormula bnf) {
		if (bnf.eContainer() instanceof Graph) {
			Graph gc = (Graph) bnf.eContainer();
			gc.setFormula(bnf.getRight());
		} else if (bnf.eContainer() instanceof BinaryFormula) {
			BinaryFormula b = (BinaryFormula) bnf.eContainer();
			if (b.getLeft() == bnf) {
				b.setLeft(bnf.getRight());
			} else if (b.getRight() == bnf) {
				b.setRight(bnf.getRight());
			}
		} else if (bnf.eContainer() instanceof Not) {
			Not not = (Not) bnf.eContainer();
			not.setChild(bnf.getRight());
		}
	}

	/**
	 * 
	 * @param a
	 * @param ls
	 * @return
	 */
	private static Attribute getBy(Attribute a, EList<Attribute> ls) {
		for (Attribute b : ls) {
			if (b.getType() == a.getType() && b.getValue() == a.getValue()) {
				return b;
			}
		}

		return null;
	}

	/**
	 * 
	 * 
	 * @param graphA
	 * @param graphB
	 * @return
	 */
	public static boolean isSubGraph(Graph graphA, Graph graphB) {
		HashMap<Node, Node> mappingNodeA2NodeB = new HashMap<Node, Node>();
		for (Node nodeA : graphA.getNodes()) {
			for (Node nodeB : graphB.getNodes()) {
				if (nodeA.getType() == nodeB.getType()) {
					if (getNames(nodeB.getName()).containsAll(getNames(nodeA.getName()))) {

						// TODO check it attribute
						if (nodeB.getAttributes().containsAll(nodeA.getAttributes())) {
							boolean attISO = true;
							for (Attribute a : nodeA.getAttributes()) {
								if (getBy(a, nodeB.getAttributes()) == null)
									attISO = false;
							}
							if (attISO)
								mappingNodeA2NodeB.put(nodeA, nodeB);
						}

						// mappingNodeA2NodeB.put(nodeA, nodeB);
					}
				}
			}
		}
		if (graphA.getNodes().size() != mappingNodeA2NodeB.size()) {
			return false;
		}
		for (Edge edgeA : graphA.getEdges()) {
			Node srcEdgeA = edgeA.getSource();
			Node tgtEdgeA = edgeA.getTarget();

			boolean edgeIsMapped = false;
			for (Edge edgeB : graphB.getEdges()) {
				Node srcEdgeB = edgeB.getSource();
				Node tgtEdgeB = edgeB.getTarget();
				if (edgeA.getType() == edgeB.getType() && mappingNodeA2NodeB.get(srcEdgeA) == srcEdgeB
						&& mappingNodeA2NodeB.get(tgtEdgeA) == tgtEdgeB) {
					edgeIsMapped = true;
					break;
				}
			}
			if (!edgeIsMapped) {
				return false;
			}
		}
		return true;
	}

	private static List<String> getNames(String name) {
		String[] names = name.split("=");
		return Arrays.asList(names);
	}

	private static String getFullPath(String path) {
		URL url = FileLocator.find(Activator.getDefault().getBundle(), new Path(path), Collections.EMPTY_MAP);
		URL fileUrl = null;
		try {
			fileUrl = FileLocator.toFileURL(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileUrl.getPath();
	}
}
