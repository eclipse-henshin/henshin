package org.eclipse.emf.henshin.variability.mergein.mining.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.henshin.variability.mergein.mining.MiningManager;
import org.eclipse.emf.henshin.variability.mergein.mining.labels.IEdgeLabel;
import org.eclipse.emf.henshin.variability.mergein.mining.labels.INodeLabel;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraph;

import de.parsemis.Miner;
import de.parsemis.graph.Graph;
import de.parsemis.miner.general.Fragment;

/**
 * This class performs graph mining. 
 * @author strüber
 *
 */
public class TransformationSystemMiner {
	
	private MiningManager manager;
	public TransformationSystemMiner(MiningManager man) {
		this.manager = man;
	}
	
	public void createParsemisInputGraphs() {
		HenshinGraphToParsemisConverter minableGraphCreator = new HenshinGraphToParsemisConverter();
		for (HenshinGraph ruleGraph : manager.getRuleGraphs()) {
			Graph<INodeLabel, IEdgeLabel> parsemisGraph = minableGraphCreator.createParsemisGraph(ruleGraph);
			manager.addParsemisGraph(ruleGraph, parsemisGraph);
		}
	}
	
	public void doGraphMining() {
		if (manager.getSettings() == null) {
			return;
		}

		createParsemisInputGraphs();
		List<Graph<INodeLabel, IEdgeLabel>> graphs = new ArrayList<Graph<INodeLabel, IEdgeLabel>>();
		graphs.addAll(manager.getParsemisGraphs());

		Collection<Fragment<INodeLabel, IEdgeLabel>> minedFragmentsCollection = Miner
				.mine(graphs, manager.getSettings());
		
		manager.setMinedFragments(new ArrayList<Fragment<INodeLabel, IEdgeLabel>>());		
		manager.getMinedFragments().addAll(minedFragmentsCollection);
		ResultFragmentSorter.sort(manager.getMinedFragments());

		if (manager.getMinedFragments() != null && manager.getMinedFragments().size() != 0) {
			ParsemisParserSerializer ser = new ParsemisParserSerializer();
			for (Fragment fragment : manager.getMinedFragments()) {
						ser.serialize(fragment.toGraph());
			}
		} else {
			System.err.println("\tThere were no frequent subgraphs found.");
		}
	}
}
