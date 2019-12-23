package org.eclipse.emf.henshin.variability.mergein.clustering;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;

import mergeSuggestion.MergeNAC;
import mergeSuggestion.MergePAC;
import mergeSuggestion.MergeRule;
import mergeSuggestion.MergeRuleElement;
import mergeSuggestion.MergeSuggestion;

public class HierarchicalMergeSuggestionBuilder extends BasicMergeSuggestionBuilder {
//	private Set<GraphElement> checked = new HashSet<GraphElement>(); // caches checked
//																// NAC elements
//	private Map<GraphElement, MergeRuleElement> add = new HashMap<GraphElement, MergeRuleElement>();
//	private Set<MergeRuleElement> remove = new HashSet<MergeRuleElement>();

	MergeSuggestion createFromCloneHierarchy(CloneHierarchy cloneHierarchy) {
		Set<CloneGroup> topCloneGroups = cloneHierarchy.getTopCloneGroups();
		MergeSuggestion basisSuggestion = createFromBasisClones(topCloneGroups);

		for (CloneGroup topCloneGroup : topCloneGroups) {
			Rule rule = topCloneGroup.getRules().iterator().next(); // arbitrary
																	// one
			MergeRule mergeRule = basisSuggestion.findMergeRule(rule);
			extendMergeRule(mergeRule, topCloneGroup, cloneHierarchy);
		}
		return basisSuggestion;
	}

	private void extendMergeRule(MergeRule mergeRule, CloneGroup basisGroup, CloneHierarchy cloneHierarchy) {
		if (cloneHierarchy.getSubClones(basisGroup) != null && !cloneHierarchy.getSubClones(basisGroup).isEmpty()) {
			Set<CloneGroup> remaining = new HashSet<CloneGroup>();
			Set<CloneGroup> considered = new HashSet<CloneGroup>();
			Set<CloneGroup> integrated = new HashSet<CloneGroup>();
			considered.add(basisGroup);
			remaining.addAll(cloneHierarchy.getSubClones(basisGroup));

			while (!remaining.isEmpty()) {
				CloneGroup next = getNextSubClone(remaining, considered, cloneHierarchy);
				if (next == null)
					throw new RuntimeException(
							"Error during creation of merge suggestion. Possibly the sub-clone hierarchy was created in an ill-founded way.");

				boolean integrate = true;
				for (CloneGroup groups : integrated) {
					if (!SubCloneRelation.isSubClone(groups, next))
						integrate = false;
				}
				if (integrate) {
					mergeIn(mergeRule, next);
					integrated.add(next);
				}
				remaining.remove(next);
				considered.add(next);
			}
		}
	}

	private CloneGroup getNextSubClone(Set<CloneGroup> remaining, Set<CloneGroup> considered,
			CloneHierarchy cloneHierarchy) {
		CloneGroup candidate = null;

		for (CloneGroup cg : remaining) {
			if (considered.containsAll(cloneHierarchy.getSuperClones(cg)))
				if (candidate == null || cg.getSize() > candidate.getSize()) {
					candidate = cg;
				}
		}
		return candidate;
	}

	private void mergeIn(MergeRule mergeRule, CloneGroup subGroup) {
		MergeRule subGroupMergeRule = createFromBasisCloneGroup(subGroup);
		combineMergeRules(mergeRule, subGroupMergeRule, subGroup);
		for (Rule rule : subGroup.getRules()) {
			if (!mergeRule.getRules().contains(rule))
				mergeRule.getRules().add(rule);
		}
	}

	private void combineMergeRules(MergeRule mergeRule, MergeRule subGroupMergeRule, CloneGroup subGroup) {
		mergeRule.getElements().addAll(subGroupMergeRule.getElements());

		// Make sure each graph element knows its corresponding graph elements
		Map<GraphElement, Set<GraphElement>> graph2set = new HashMap<GraphElement, Set<GraphElement>>();
		for (MergeRuleElement el : mergeRule.getElements()) {
			for (GraphElement ge : el.getReferenceElements()) {
				Set<GraphElement> graphelems = graph2set.get(ge);
				if (graphelems == null) {
					graphelems = new HashSet<GraphElement>();
				}
				graphelems.addAll(el.getReferenceElements());
				for (GraphElement ge2 : el.getReferenceElements()) {
					graph2set.put(ge2, graphelems);
				}
			}
		}

		// Make sure each graph element knows all referring rule elements
		Map<GraphElement, Set<MergeRuleElement>> graph2ruleElem = new HashMap<GraphElement, Set<MergeRuleElement>>();
		for (MergeRuleElement el : mergeRule.getElements()) {
			for (GraphElement ge : el.getReferenceElements()) {
				Set<MergeRuleElement> ruleElems = graph2ruleElem.get(ge);
				if (ruleElems == null) {
					ruleElems = new HashSet<MergeRuleElement>();
					graph2ruleElem.put(ge, ruleElems);
				}
				ruleElems.add(el);
			}
		}


		// And now, assign each set of corresponding graph elements to
		// one arbitrarily selected MergeRuleElement ("master element") and
		// remove the non-master rule elements.
		for (Set<GraphElement> graphElems : graph2set.values()) {
			MergeRuleElement master = graph2ruleElem.get(graphElems.iterator().next()).iterator().next();
			EList<GraphElement> masterRefElems = master.getReferenceElements();
			for (GraphElement ge : graphElems) {
				if (!masterRefElems.contains(ge))
					masterRefElems.add(ge);
				for (MergeRuleElement re : graph2ruleElem.get(ge)) {
					if (re != master)
						mergeRule.getElements().remove(re);
				}
			}
		}
		
		handleNACs(mergeRule, subGroupMergeRule);
	}

	private void handleNACs(MergeRule mergeRule, MergeRule subGroupMergeRule) {
		mergeRule.getMergeNacs().addAll(subGroupMergeRule.getMergeNacs());
		// Make sure each NAC knows its corresponding NACs
		Map<Graph, Set<Graph>> nac2nacs = new HashMap<Graph, Set<Graph>>();
		for (MergeNAC mergeNac : mergeRule.getMergeNacs()) {
			for (Graph nac1 : mergeNac.getReferenceNACs()) {
				Set<Graph> nacs = nac2nacs.get(nac1);
				if (nacs == null) {
					nacs = new HashSet<Graph>();
				}
				nacs.addAll(mergeNac.getReferenceNACs());
				for (Graph nac2 : mergeNac.getReferenceNACs()) {
					nac2nacs.put(nac2, nacs);
				}
			}
		}
		
		// Make sure each NAC knows all referring rule NACs
		Map<Graph, Set<MergeNAC>> nac2mergeNacs = new HashMap<Graph, Set<MergeNAC>>();
		for (MergeNAC mergeNac : mergeRule.getMergeNacs()) {
			for (Graph nac : mergeNac.getReferenceNACs()) {
				Set<MergeNAC> mergeNacs = nac2mergeNacs.get(nac);
				if (mergeNacs == null) {
					mergeNacs = new HashSet<MergeNAC>();
					nac2mergeNacs.put(nac, mergeNacs);
				}
				mergeNacs.add(mergeNac);
			}
		}
		

		// And now, assign each set of corresponding NACs to
		// one arbitrarily selected NAC ("master rule") and
		// remove the non-master NACs.
		for (Set<Graph> nacs : nac2nacs.values()) {
			MergeNAC master = nac2mergeNacs.get(nacs.iterator().next()).iterator().next();
			EList<Graph> masterNACs = master.getReferenceNACs();
			for (Graph nac : masterNACs) {
				if (!masterNACs.contains(nac))
					masterNACs.add(nac);
				for (MergeNAC mergeNAC : nac2mergeNacs.get(nac)) {
					if (mergeNAC!= master)
						mergeRule.getMergeNacs().remove(mergeNAC);
				}
			}
		}
		
		// And now, the same for PACs.
		mergeRule.getMergePacs().addAll(subGroupMergeRule.getMergePacs());
		Map<Graph, Set<Graph>> pac2pacs = new HashMap<Graph, Set<Graph>>();
		for (MergePAC mergePac : mergeRule.getMergePacs()) {
			for (Graph pac1 : mergePac.getReferencePACs()) {
				Set<Graph> pacs = pac2pacs.get(pac1);
				if (pacs == null) {
					pacs = new HashSet<Graph>();
				}
				pacs.addAll(mergePac.getReferencePACs());
				for (Graph pac2 : mergePac.getReferencePACs()) {
					pac2pacs.put(pac2, pacs);
				}
			}
		}
		Map<Graph, Set<MergePAC>> pac2mergePacs = new HashMap<Graph, Set<MergePAC>>();
		for (MergePAC mergePac : mergeRule.getMergePacs()) {
			for (Graph pac : mergePac.getReferencePACs()) {
				Set<MergePAC> mergePacs = pac2mergePacs.get(pac);
				if (mergePacs == null) {
					mergePacs = new HashSet<MergePAC>();
					pac2mergePacs.put(pac, mergePacs);
				}
				mergePacs.add(mergePac);
			}
		}
		for (Set<Graph> pacs : pac2pacs.values()) {
			MergePAC master = pac2mergePacs.get(pacs.iterator().next()).iterator().next();
			EList<Graph> masterPACs = master.getReferencePACs();
			for (Graph pac : masterPACs) {
				if (!masterPACs.contains(pac))
					masterPACs.add(pac);
				for (MergePAC mergePAC : pac2mergePacs.get(pac)) {
					if (mergePAC!= master)
						mergeRule.getMergePacs().remove(mergePAC);
				}
			}
		}
	}
}
