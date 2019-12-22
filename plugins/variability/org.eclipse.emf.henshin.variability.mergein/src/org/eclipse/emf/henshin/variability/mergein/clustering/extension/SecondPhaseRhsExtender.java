//package org.eclipse.emf.henshin.variability.mergein.clustering.extension;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import mergeSuggestion.MergeRule;
//import mergeSuggestion.MergeRuleElement;
//
//import org.eclipse.emf.common.util.EList;
//import org.eclipse.emf.henshin.model.Action.Type;
//import org.eclipse.emf.henshin.model.Attribute;
//import org.eclipse.emf.henshin.model.Edge;
//import org.eclipse.emf.henshin.model.GraphElement;
//import org.eclipse.emf.henshin.model.Node;
//import org.eclipse.emf.henshin.model.Rule;
//import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;
//import org.eclipse.emf.henshin.variability.mergein.clustering.CloneClustering;
//import org.eclipse.emf.henshin.variability.mergein.clustering.HierarchicalMergeSuggestionBuilder;
//import org.eclipse.emf.henshin.variability.mergein.clustering.SubCloneRelation;
//
//public class SecondPhaseRhsExtender {
//
//	private MergeRule mergeRule;
//	private CloneGroup basisCloneGroup;
//	private ClusteringExtension clusterExtension;
//	private List<CloneGroup> integratedBefore;
//
//	public SecondPhaseRhsExtender(MergeRule mergeRule,
//			CloneGroup basisCloneGroup, ClusteringExtension clusterExtension,
//			List<CloneGroup> integratedBefore) {
//		this.mergeRule = mergeRule;
//		this.basisCloneGroup = basisCloneGroup;
//		this.clusterExtension = clusterExtension;
//		this.integratedBefore = integratedBefore;
//	}
//
//	public void extendMergeRule() {
//		if (clusterExtension.getExtensions(basisCloneGroup) != null
//				&& !clusterExtension.getExtensions(basisCloneGroup).isEmpty()) {
//			List<CloneGroup> newlyIntegrated = new ArrayList<CloneGroup>();
//
//			for (CloneGroup cg : integratedBefore) {
//				CloneGroup candidate = findCandidate(cg, newlyIntegrated);
//				if (candidate != null) {
//					mergeInNewlyCreated(mergeRule, candidate);
//					newlyIntegrated.add(candidate);
//				}
//			}
//
//			// while (!remaining.isEmpty()) {
//			// CloneGroup next = getNextExtensionClone(remaining, considered,
//			// clusterExtension);
//			// if (next == null)
//			// throw new RuntimeException(
//			// "Error during creation of merge suggestion. Possibly the sub-clone hierarchy was created in an ill-founded way.");
//			//
//			// boolean integrate = true;
//			// // for (CloneGroup groups : integrated) {
//			// // if (!SubCloneRelation.isSubClone(groups, next))
//			// // integrate = false;
//			// // }
//			// if (integrate) {
//			// mergeInNewlyCreated(mergeRule, next);
//			// integrated.add(next);
//			// }
//			// remaining.remove(next);
//			// considered.add(next);
//			// }
//		}
//	}
//
//	private CloneGroup findCandidate(CloneGroup cg,
//			List<CloneGroup> newlyIntegrated) {
//		CloneGroup result = null;
//		for (CloneGroup candidate : clusterExtension.getExtensions(cg)) {
//			boolean compatible = true;
//			for (CloneGroup newly : newlyIntegrated) {
//				if (!SubCloneRelation.isSubClone(newly, candidate))
//					compatible = false;
//			}
//			if (compatible
//					&& (result == null || result.getSize() < candidate
//							.getSize())) {
//				result = candidate;
//			}
//		}
//		return result;
//	}
//
//	private void mergeInNewlyCreated(MergeRule mergeRule, CloneGroup subGroup) {
//		MergeRule subGroupMergeRule = HierarchicalMergeSuggestionBuilder
//				.getInstance().createFromBasisCloneGroup(subGroup);
//		combineNewlyCreated(mergeRule, subGroupMergeRule, subGroup);
//		for (Rule rule : subGroup.getRules()) {
//			if (!mergeRule.getRules().contains(rule))
//				mergeRule.getRules().add(rule);
//		}
//	}
//
//	private void combineNewlyCreated(MergeRule mergeRule,
//			MergeRule subGroupMergeRule, CloneGroup subGroup) {
//		// We consider all MergeRuleElements for newly created GraphElements...
//		List<MergeRuleElement> add = new ArrayList<MergeRuleElement>();
//		for (MergeRuleElement mre : subGroupMergeRule.getElements()) {
//			GraphElement referenceElem = mre.getReferenceElements().get(0);
//			if (isNewlyCreated(referenceElem)) {
//				add.add(mre);
//			}
//		}
//
//		// ...unless they represent an Edge whose nodes
//		// and targets are non-create not already merged in the reference MergeRule.
//		// (Or an equivalent Attribute.)
//		List<MergeRuleElement> except = new ArrayList<MergeRuleElement>();
//		for (MergeRuleElement mre : add) {
//			if (mre.getReferenceElements().get(0) instanceof Edge) {
//				Set<MergeRuleElement> sources = new HashSet<MergeRuleElement>();
//				Set<MergeRuleElement> targets = new HashSet<MergeRuleElement>();
//				for (GraphElement ge : mre.getReferenceElements()) {
//					Node source = ((Edge)ge).getSource();
//					Node target = ((Edge)ge).getTarget();
//					// if source or target is preserved
//					if (source.getAction() == null || target.getAction() == null) {
//						MergeRuleElement referenceSource = mergeRule
//								.findMergeRuleElement(source);
//						sources.add(referenceSource);
//						
//					}
//					
//					// if source and target are preserved
//					// (only in this case consider targets)
//					if (source.getAction() == null && target.getAction() == null) {
//						MergeRuleElement referenceTarget = mergeRule
//								.findMergeRuleElement(target);
//						targets.add(referenceTarget);
//						
//					}
//				}
//				if (sources.size() > 1 || targets.size() > 1) {
//					except.add(mre);
//				}
//			} else if (mre.getReferenceElements().get(0) instanceof Attribute) {
//				if (mre.getReferenceElements().get(0) instanceof Edge) {
//					Set<MergeRuleElement> nodes = new HashSet<MergeRuleElement>();
//					for (GraphElement ge : mre.getReferenceElements()) {
//						Attribute a = (Attribute)ge;
//						MergeRuleElement node = mergeRule
//								.findMergeRuleElement(a.getNode());
//						nodes.add(node);
//
//					}
//					if (nodes.size() > 1)
//						except.add(mre);
//				}
//			}
//		}
//
////		System.out.println(" ### "+except);
//		add.removeAll(except);
//		mergeRule.getElements().addAll(add);
//
//		// Make sure each *create* element knows its corresponding graph
//		// elements
//		Map<GraphElement, Set<GraphElement>> graph2set = new HashMap<GraphElement, Set<GraphElement>>();
//		for (MergeRuleElement el : mergeRule.getElements()) {
//			for (GraphElement ge : el.getReferenceElements()) {
//				if (isNewlyCreated(ge)) {
//					Set<GraphElement> graphelems = graph2set.get(ge);
//					if (graphelems == null) {
//						graphelems = new HashSet<GraphElement>();
//					}
//					graphelems.addAll(el.getReferenceElements());
//					for (GraphElement ge2 : el.getReferenceElements()) {
//						graph2set.put(ge2, graphelems);
//					}
//
//				}
//			}
//		}
//
//		// Make sure each graph elements knows all referring rule elements
//		Map<GraphElement, Set<MergeRuleElement>> graph2ruleElem = new HashMap<GraphElement, Set<MergeRuleElement>>();
//		for (MergeRuleElement el : mergeRule.getElements()) {
//			for (GraphElement ge : el.getReferenceElements()) {
//				if (isNewlyCreated(ge)) {
//					Set<MergeRuleElement> ruleElems = graph2ruleElem.get(ge);
//					if (ruleElems == null) {
//						ruleElems = new HashSet<MergeRuleElement>();
//						graph2ruleElem.put(ge, ruleElems);
//					}
//					ruleElems.add(el);
//				}
//			}
//		}
//		// And now, assign each set of corresponding graph elements to
//		// one arbitrarily selected MergeRuleElement ("master element") and
//		// remove the non-master rule elements.
//		for (Set<GraphElement> graphElems : graph2set.values()) {
//			MergeRuleElement master = graph2ruleElem
//					.get(graphElems.iterator().next()).iterator().next();
//			EList<GraphElement> masterRefElems = master.getReferenceElements();
//			for (GraphElement ge : graphElems) {
//				if (!masterRefElems.contains(ge))
//					masterRefElems.add(ge);
//				for (MergeRuleElement re : graph2ruleElem.get(ge)) {
//					if (re != master)
//						mergeRule.getElements().remove(re);
//				}
//			}
//		}
//	}
//
//	private boolean isNewlyCreated(GraphElement referenceElem) {
//		return referenceElem.getAction() != null
//				&& referenceElem.getAction().getType() == Type.CREATE;
//	}
//
//}
