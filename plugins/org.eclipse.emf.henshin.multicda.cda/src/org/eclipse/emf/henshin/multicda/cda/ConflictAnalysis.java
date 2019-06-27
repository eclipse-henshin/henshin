package org.eclipse.emf.henshin.multicda.cda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.computation.AtomCandidateComputation;
import org.eclipse.emf.henshin.multicda.cda.computation.ConflictReasonComputation;
import org.eclipse.emf.henshin.multicda.cda.computation.DeleteUseConflictReasonComputation;
import org.eclipse.emf.henshin.multicda.cda.computation.MinimalReasonComputation;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ChangeConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.CreateConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.CreateEdgeDeleteNodeConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cda.units.Span;

public class ConflictAnalysis implements MultiGranularAnalysis {

	private Rule rule1;
	private Rule rule1INV;
	private Rule rule2;
	private Rule originalR2;
	private Set<Rule> rule2NACs;
	public static int unnamedNodeID = 0;

	private Set<ConflictAtom> atoms = new HashSet<>();
	private Set<ConflictReason> mcrs = new HashSet<>();
	private Set<ConflictReason> crs = new HashSet<>();

	/**
	 * @param rule1
	 * @param rule2
	 */
	public ConflictAnalysis(Rule rule1, Rule rule2) {
		Utils.checkNull(rule1);
		Utils.checkNull(rule2);
		unnamedNodeID = 0;
		this.rule1 = prepare(rule1);
		this.originalR2 = prepare(rule2);
		this.rule2 = mergeRequire(originalR2);
		this.rule1INV = Utils.invertRuleForForbid(this.rule1);
		this.rule2NACs = Utils.createNACRules(this.rule2);
	}

	@Override
	public Set<ConflictAtom> computeAtoms() {
		return computeConflictAtoms();
	}

	@Override
	public ConflictAtom computeResultsBinary() {
		ConflictAtom result = hasConflicts();
		if (result == null)
			return null;
		else
			return result;
	}

	@Override
	public Set<ConflictReason> computeResultsCoarse() {
		if (mcrs.isEmpty()) {
			if (allowed()) {
				mcrs.addAll(computeMinimalDeleteUseConflictReasons(rule1, rule2));
				setRequires(mcrs);
			}
			if (mcrs.isEmpty())
				mcrs.addAll(computeCreateForbidConflictReasons(true));
		}
		return mcrs;
	}

	@Override
	public Set<ConflictReason> computeResultsFine() {
		if (crs.isEmpty()) {
			if (allowed()) {
				crs.addAll(computeDeleteUseConflictReasons(rule1, rule2));
				setRequires(crs);
			}
			if (crs.isEmpty())
				crs.addAll(computeCreateForbidConflictReasons(false));
		}
		return crs;
	}

	private Set<ConflictReason> computeCreateForbidConflictReasons(boolean minimal) {
		Set<ConflictReason> result = new HashSet<>();
		for (NestedCondition nc : originalR2.getLhs().getNACs())
			if (nc.getConclusion().getFormula() != null)
				return result;
		for (Rule r2 : rule2NACs) {
			Set<ConflictReason> reasons;
			if (minimal)
				reasons = computeMinimalDeleteUseConflictReasons(rule1INV, r2);
			else
				reasons = computeDeleteUseConflictReasons(rule1INV, r2);
			for (Reason confReason : reasons) {
				ConflictReason reason = ReasonFactory.eINSTANCE.createForbidReason(confReason, rule1, originalR2);
				if (reason != null)
					result.add(reason);
			}
		}
		return result;
	}

	private ConflictAtom hasConflicts() {
		Set<ConflictAtom> cas = computeConflictAtoms(true);
		if (cas.isEmpty())
			return null;
		else
			return new ArrayList<>(cas).get(0);
	}

	private Set<ConflictAtom> computeConflictAtoms(boolean... earlyExit) {
		if (atoms.isEmpty()) {
			// Compute CreateEdgeDeleteNodeConflictAtoms
			Set<Atom> candidates = new HashSet<>();
			// If computation is allowed due to forbid parts
			if (allowed()) {
				candidates = new AtomCandidateComputation(rule1, rule2).computeAtomCandidates();
				Set<Atom> candidatesS2 = new AtomCandidateComputation(rule2, rule1).computeAtomCandidates();
				for (Atom candidate : candidatesS2) {
					if (Utils.attributesCheck(candidate)) {
						Set<ConflictReason> minimalConflictReasons = new HashSet<>();
						new MinimalReasonComputation(rule2, rule1).computeMinimalConflictReasons(candidate,
								minimalConflictReasons);
						// Compute CEDNCR from minimal reasons. If the result is not empty, the
						// candidate is a CEDNCR.
						Set<ConflictReason> crs = Utils.computeCreateEdgeDeleteNode(minimalConflictReasons);
						if (!crs.isEmpty())
							atoms.add(new CreateEdgeDeleteNodeConflictAtom(candidate, crs));
					}
				}
				setRequires(candidates);
			}

			for (Rule rNac : rule2NACs)
				for (Atom a : new AtomCandidateComputation(rule1INV, rNac).computeAtomCandidates()) {
					Atom atom = ReasonFactory.eINSTANCE.createForbidAtom(a, rule1, originalR2);
					if (atom != null)
						candidates.add(atom);
				}
			for (Atom candidate : candidates)
				if (Utils.attributesCheck(candidate)) {
					Set<ConflictReason> minimalConflictReasons = new HashSet<>();
					if (candidate.isForbid()) {
						for (Rule rNac : rule2NACs)
							if (rNac == candidate.getRule2())
								new MinimalReasonComputation(rule1INV, rNac).computeMinimalConflictReasons(candidate,
										minimalConflictReasons);
					} else
						new MinimalReasonComputation(rule1, rule2).computeMinimalConflictReasons(candidate,
								minimalConflictReasons);

					ConflictAtom a = null;
					if (!minimalConflictReasons.isEmpty()) {
						if (candidate instanceof DeleteConflictAtom)
							a = new DeleteConflictAtom(candidate, minimalConflictReasons)
									.setForbid(candidate.isForbid()).setRequire(candidate.isRequire());
						else if (candidate instanceof CreateConflictAtom)
							a = new CreateConflictAtom(candidate, minimalConflictReasons)
									.setForbid(candidate.isForbid()).setRequire(candidate.isRequire());
						else if (candidate instanceof ChangeConflictAtom)
							a = new ChangeConflictAtom(candidate, minimalConflictReasons)
									.setForbid(candidate.isForbid()).setRequire(candidate.isRequire());
						else
							try {
								throw new Exception("\nUndentified Atom\n\n" + candidate + "\n");
							} catch (Exception e) {
								e.printStackTrace();
							}
						atoms.add(a);
					}
					if (!atoms.isEmpty() && earlyExit.length > 0 && earlyExit[0] == true) {
						Set<ConflictAtom> temp = atoms;
						atoms = new HashSet<>();
						return temp;
					}
				}
		}
		return atoms;

	}

	private Set<ConflictReason> computeMinimalDeleteUseConflictReasons(Rule r1, Rule r2) {
		Set<ConflictReason> normalCR = new HashSet<>();
		normalCR = new MinimalReasonComputation(r1, r2).computeMinimalConflictReasons();
		Set<ConflictReason> DUCR = normalCR;
		if (r1 != r2)
			DUCR = new MinimalReasonComputation(r2, r1).computeMinimalConflictReasons();
		return new DeleteUseConflictReasonComputation<>(r1, r2, normalCR, DUCR).computeDeleteUseConflictReason();
	}

	private Set<ConflictReason> computeDeleteUseConflictReasons(Rule r1, Rule r2) {
		Set<ConflictReason> normalCR = new HashSet<>();
		normalCR = new ConflictReasonComputation(r1, r2).computeConflictReasons();
		Set<ConflictReason> DUCR = normalCR;
		if (r1 != r2)
			DUCR = new ConflictReasonComputation(r2, r1).computeConflictReasons();
		return new DeleteUseConflictReasonComputation<>(r1, r2, normalCR, DUCR).computeDeleteUseConflictReason();
	}

	/**
	 * ID wich gets the next unnamed node by preparing the Rule with
	 * {@link #prepare(Rule) prepare} method
	 */

	public static Rule prepare(Rule r) {
		// Copy and delete attribute conditions

		Copier ruleC = new Copier();
		Rule rule = (Rule) ruleC.copy(r);
		ruleC.copyReferences();
		rule.getAttributeConditions().clear();
		return prepareWithACons(rule);
	}

	public static Rule prepareWithACons(Rule r) {
		for (Node n : r.getRhs().getNodes())
			if (n.getName() == null || n.getName().isEmpty()) {
				n.setName("|" + unnamedNodeID++ + "|");
				Node origin = r.getMappings().getOrigin(n);
				if (origin != null)
					origin.setName(n.getName());
			}
		for (Node n : r.getLhs().getNodes())
			if (n.getName() == null || n.getName().isEmpty())
				n.setName("|" + unnamedNodeID++ + "|");
		return r;
	}

	private Pair<Boolean, Node> containsExactNode(Graph g, Node n) {
		Node gN = g.getNode(n.getName());
		if (gN == null)
			return new Pair<>(false, null);
		if (gN.getAttributes().size() != n.getAttributes().size())
			return new Pair<>(false, gN);
		for (Attribute a : n.getAttributes())
			if (gN.getAttribute(a.getType()) == null)
				return new Pair<>(false, gN);
		return new Pair<>(true, gN);

	}

	private Rule mergeRequire(Rule rule) {
		rule = prepare(rule);
		Rule newRule = rule;

		Set<Rule> rNCs = Utils.createPACRules(rule);
		for (Rule rNC : rNCs) {
			if (newRule == rule)
				newRule = rNC;
			else {
				Set<Node> nodes = new HashSet<>(rNC.getLhs().getNodes());
				for (Node n : nodes) {
					Pair<Boolean, Node> contains = containsExactNode(newRule.getLhs(), n);
					if (!contains.first && contains.second == null)
						n.setGraph(newRule.getLhs());
					else if (contains.second != null) {
						for (Attribute a : n.getAttributes())
							if (contains.second.getAttribute(a.getType()) == null)
								HenshinFactory.eINSTANCE.createAttribute(contains.second, a.getType(), a.getValue());
					}
				}
				nodes = new HashSet<>(rNC.getRhs().getNodes());
				for (Node n : nodes) {
					Pair<Boolean, Node> contains = containsExactNode(newRule.getRhs(), n);
					if (!contains.first && contains.second == null)
						n.setGraph(newRule.getRhs());
					else if (contains.second != null) {
						for (Attribute a : n.getAttributes())
							if (contains.second.getAttribute(a.getType()) == null)
								HenshinFactory.eINSTANCE.createAttribute(contains.second, a.getType(), a.getValue());
					}
				}
				for (Edge e : rNC.getLhs().getEdges()) {
					Node source = e.getSource();
					if (!newRule.getLhs().getNodes().contains(source))
						source = newRule.getLhs().getNode(source.getName());
					Node target = e.getTarget();
					if (!newRule.getLhs().getNodes().contains(target))
						target = newRule.getLhs().getNode(target.getName());
					if (source.getOutgoing(e.getType(), target) == null)
						newRule.getLhs().getEdges()
								.add(HenshinFactory.eINSTANCE.createEdge(source, target, e.getType()));
				}
				for (Edge e : rNC.getRhs().getEdges()) {
					Node source = e.getSource();
					if (!newRule.getRhs().getNodes().contains(source))
						source = newRule.getRhs().getNode(source.getName());
					Node target = e.getTarget();
					if (!newRule.getRhs().getNodes().contains(target))
						target = newRule.getRhs().getNode(target.getName());
					if (source.getOutgoing(e.getType(), target) == null)
						newRule.getRhs().getEdges()
								.add(HenshinFactory.eINSTANCE.createEdge(source, target, e.getType()));
				}
				for (Mapping map : rNC.getMappings())
					if (newRule.getLhs().getNodes().contains(map.getImage()))
						newRule.getMappings().add(HenshinFactory.eINSTANCE
								.createMapping(newRule.getMappings().getOrigin(map.getImage()), map.getImage()));
					else {
						Node origin = map.getOrigin();
						if (!newRule.getLhs().getNodes().contains(origin))
							origin = newRule.getLhs().getNode(origin.getName());
						Node image = map.getImage();
						if (!newRule.getRhs().getNodes().contains(image))
							image = newRule.getRhs().getNode(image.getName());
						if (newRule.getMappings().getOrigin(image) != origin)
							newRule.getMappings().add(HenshinFactory.eINSTANCE.createMapping(origin, image));
					}
			}
		}
		return newRule;
	}

	public boolean allowed() {
		Set<Node> l1 = new HashSet<>(rule1.getLhs().getNodes());
		Set<Node> l2 = new HashSet<>(originalR2.getLhs().getNodes());
		Set<Node> f = new HashSet<>();
		for (NestedCondition nc : originalR2.getLhs().getNACs())
			for (Node fn : nc.getConclusion().getNodes()) {
				Node mappedNode = nc.getMappings().getOrigin(fn);
				if (mappedNode == null)
					f.add(fn);
				else if (fn.getAttributes().size() != mappedNode.getAttributes().size()) {
					l2.remove(mappedNode);
					f.add(fn);
				}
			}

		if (!f.isEmpty()) {
			Map<Node, Node> result = new HashMap<>();
			f.addAll(l2);
			Utils.mapNodes(l1, f, result, true);
			boolean allowed = result.size() < f.size();
			if (!allowed)
				return false;
		}
		f.clear();
		l2 = new HashSet<>(originalR2.getLhs().getNodes());
		l1 = new HashSet<>(rule1.getLhs().getNodes());
		for (NestedCondition nc : rule1.getLhs().getNACs())
			for (Node fn : nc.getConclusion().getNodes()) {
				Node mappedNode = nc.getMappings().getOrigin(fn);
				if (mappedNode == null)
					f.add(fn);
				else if (fn.getAttributes().size() != mappedNode.getAttributes().size()) {
					l1.remove(mappedNode);
					f.add(fn);
				}
			}

		if (!f.isEmpty()) {
			Map<Node, Node> result = new HashMap<>();
			f.addAll(l1);
			Utils.mapNodes(l2, f, result, true);
			boolean allowed = result.size() < f.size();
			if (!allowed)
				return false;
		}
		return true;
	}

	public <T extends Span> Set<T> setRequires(Set<T> spans) {
		for (T s : spans) {
			boolean found = false;
			for (Node nn : s.getGraph().getNodes()) {
				Node n2 = s.getMappingIntoRule2(nn).getImage();
				if (found = Utils.isRealNcNode(n2, false))
					break;
			}
			if (!found)
				for (Edge e : s.getGraph().getEdges()) {
					Node n2Source = s.getMappingIntoRule2(e.getSource()).getImage();
					Node n2Target = s.getMappingIntoRule2(e.getTarget()).getImage();
					n2Source = s.getRule2().getMappings().getOrigin(n2Source);
					n2Target = s.getRule2().getMappings().getOrigin(n2Target);
					if (n2Source != null && n2Target != null)
						if (found = (n2Source.getOutgoing(e.getType(), n2Target) == null))
							break;
				}
			if (found)
				ReasonFactory.eINSTANCE.createRequireReason(s);
		}
		return spans;
	}
}