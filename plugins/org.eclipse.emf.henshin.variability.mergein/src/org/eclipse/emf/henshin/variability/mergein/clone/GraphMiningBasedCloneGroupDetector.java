package org.eclipse.emf.henshin.variability.mergein.clone;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.mergein.mining.MiningManager;
import org.eclipse.emf.henshin.variability.mergein.mining.MiningManagerFactory;
import org.eclipse.emf.henshin.variability.mergein.mining.labels.IEdgeLabel;
import org.eclipse.emf.henshin.variability.mergein.mining.labels.INodeLabel;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraph;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraphElement;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraphFactory;
import org.eclipse.emf.henshin.variability.mergein.normalize.RuleToHenshinGraphMap;

import de.parsemis.miner.general.Fragment;

/**
 * A clone detector for Henshin rules employing a graph mining algorithm. For
 * internal calculations, the Henshin rule is transformed into a normalized
 * representation ({@link HenshinGraph}) and, consequently, into a custom
 * representation as required by the graph mining algorithm.
 * 
 * @author strueber
 *
 */
public class GraphMiningBasedCloneGroupDetector extends
		AbstractCloneGroupDetector {

	public GraphMiningBasedCloneGroupDetector(List<Rule> rules) {
		super(rules);
	}

	public GraphMiningBasedCloneGroupDetector(EList<Rule> rules, boolean b) {
		super(rules, b);
	}

	@Override
	public void detectCloneGroups() {
		RuleToHenshinGraphMap ruleGraphs = HenshinGraphFactory.getInstance()
				.createIntegratedGraphs(rules, includeRhs);
		MiningManager miningManager = MiningManagerFactory.getInstance()
				.createNewMiningManager(ruleGraphs.getHenshinGraphs(), 1);
		miningManager.doMining();

		result = new HashSet<CloneGroup>();
		int i = 0;
		for (Fragment<INodeLabel, IEdgeLabel> fragment : miningManager
				.getMinedFragments()) {
			List<Rule> affected = new ArrayList<Rule>();

			List<Set<HenshinGraphElement>> elementsOfEmbeddings = miningManager
					.getGraphElementsOfEmbeddings(fragment);
			for (Set<HenshinGraphElement> set : elementsOfEmbeddings) {
				HenshinGraph graph = set.iterator().next().getHenshinGraph();
				Rule rule = ruleGraphs.get(graph);
				affected.add(rule);
			}

			Map<Edge, Map<Rule, Edge>> edgeMappings = convertEdgeMappings(
					miningManager.createHenshinEdgeMappings(fragment),
					ruleGraphs);
			Map<Attribute, Map<Rule, Attribute>> attributeMappings = convertAttributeMappings(
					miningManager.createHenshinAttributeMappings(fragment),
					ruleGraphs);

			// System.out.println(i + " "
			// + elementsOfEmbeddings.iterator().next().size() + " "
			// + affected.size());

			result.add(new CloneGroup(affected, edgeMappings, attributeMappings));
			i++;
		}

	}

}
