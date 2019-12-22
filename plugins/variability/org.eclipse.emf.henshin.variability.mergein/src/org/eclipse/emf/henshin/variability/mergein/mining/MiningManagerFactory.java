package org.eclipse.emf.henshin.variability.mergein.mining;

import java.util.List;

import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraph;

/**
 * Static factory for getting the mining manager.
 */
public class MiningManagerFactory {

	private static MiningManagerFactory instance = new MiningManagerFactory();

	public static MiningManagerFactory getInstance() {
		return instance;
	}

	private MiningManager manager;

	private MiningManagerFactory() {
	}

	public MiningManager createNewMiningManager(List<HenshinGraph> ruleGraphs,
			int minFreq) {
		manager = new MiningManager(ruleGraphs);
		return manager;
	}

	public MiningManager getManager() {
		return manager;
	}
}
