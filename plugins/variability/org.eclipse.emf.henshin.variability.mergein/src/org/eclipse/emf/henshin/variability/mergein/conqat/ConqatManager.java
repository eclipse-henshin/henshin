package org.eclipse.emf.henshin.variability.mergein.conqat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.conqat.engine.core.logging.ELogLevel;
import org.conqat.engine.core.logging.IConQATLogger;
import org.conqat.engine.model_clones.detection.ModelCloneReporterMock;
import org.conqat.engine.model_clones.detection.ModelCloneReporterMock.ModelClone;
import org.conqat.engine.model_clones.detection.clustering.CloneClusterer;
import org.conqat.engine.model_clones.detection.pairs.PairDetector;
import org.conqat.engine.model_clones.detection.util.AugmentedModelGraph;
import org.conqat.engine.model_clones.model.IDirectedEdge;
import org.conqat.engine.model_clones.model.IModelGraph;
import org.eclipse.emf.henshin.variability.mergein.normalize.AttributeEReference;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinEdge;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraph;

/**
 * Manages the clone detection process based on ConQat: Creates and maintains a
 * mapping between the normalized HenshinGraph representation and the
 * IModelGraph representation required by ConQat, triggers the creation of the
 * results and creates HenshinGraph-based representations of the results.
 * 
 * @author strueber
 *
 */
public class ConqatManager {

	

	private List<HenshinGraph> ruleGraphs;
	private IModelGraph modelGraph;
	private HenshinToConqatGraphElementMap henshin2ConqatGraphElementMap = new HenshinToConqatGraphElementMap();
	private ModelCloneReporterMock resultReporter;
	private int minSize;

	public ConqatManager(List<HenshinGraph> ruleGraphs, int minSize) {
		this.ruleGraphs = ruleGraphs;
		this.minSize = minSize;
	}

	public void doCloneDetection() {
		modelGraph = createModelGraph();
		try {
			resultReporter = runDetection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private IModelGraph createModelGraph() {
		return new HenshinToConqatGraphConverter(ruleGraphs,
				henshin2ConqatGraphElementMap).createConqatGraph();
	}

	protected ModelCloneReporterMock runDetection() throws Exception {
		AugmentedModelGraph mag = new AugmentedModelGraph(modelGraph);
		ModelCloneReporterMock result = new ModelCloneReporterMock();
		IConQATLogger logger = createDummyLogger();
		CloneClusterer clusterer = new CloneClusterer(mag, result, logger,
				false);
		new PairDetector(mag, minSize, 1, true, clusterer,
				logger).execute();
		clusterer.performInclusionAnalysis();
		clusterer.performClustering();
		return result;
	}

	public ModelCloneReporterMock getResultReporter() {
		return resultReporter;
	}

	public List<HenshinGraph> getInvolvedHenshinGraphs(ModelClone clone) {
		List<HenshinGraph> result = new ArrayList<HenshinGraph>();
		for (List<IDirectedEdge> edgeList : clone.edges) {
			for (IDirectedEdge edge : edgeList) {
				result.add(henshin2ConqatGraphElementMap.get(edge)
						.getHenshinGraph());
			}
		}
		return result;
	}

	public Map<HenshinEdge, Map<HenshinGraph, HenshinEdge>> createHenshinEdgeMappings(
			ModelClone clone) {
		Map<HenshinEdge, Map<HenshinGraph, HenshinEdge>> outerMap = new LinkedHashMap<HenshinEdge, Map<HenshinGraph, HenshinEdge>>();
		List<Map<HenshinGraph, HenshinEdge>> innerMaps = new ArrayList<Map<HenshinGraph, HenshinEdge>>();

		List<IDirectedEdge> arbitraryCloneInstance = clone.edges.iterator()
				.next();
		int numberOfEdgesInClone = arbitraryCloneInstance.size();
		for (int i = 0; i < numberOfEdgesInClone; i++)
			innerMaps.add(new HashMap<HenshinGraph, HenshinEdge>());

		for (List<IDirectedEdge> cloneInstance : clone.edges) {
			// We have to access the mappings using indices.
			for (int i = 0; i < cloneInstance.size(); i++) {
				Map<HenshinGraph, HenshinEdge> innerMap = innerMaps.get(i);
				IDirectedEdge edge = cloneInstance.get(i);
				HenshinEdge newEdge = henshin2ConqatGraphElementMap.get(edge);
				HenshinGraph newGraph = newEdge.getHenshinGraph();

					innerMap.put(newGraph, newEdge);
					outerMap.put(newEdge, innerMap);
			}
		}
		return outerMap;
	}

	public Map<HenshinEdge, Map<HenshinGraph, HenshinEdge>> createHenshinAttributeMappings(
			ModelClone clone) {
		Map<HenshinEdge, Map<HenshinGraph, HenshinEdge>> outerMap = new LinkedHashMap<HenshinEdge, Map<HenshinGraph, HenshinEdge>>();
		List<Map<HenshinGraph, HenshinEdge>> innerMaps = new ArrayList<Map<HenshinGraph, HenshinEdge>>();

		List<IDirectedEdge> arbitraryCloneInstance = clone.edges.iterator()
				.next();
		int numberOfEdgesInClone = arbitraryCloneInstance.size();
		for (int i = 0; i < numberOfEdgesInClone; i++)
			innerMaps.add(new HashMap<HenshinGraph, HenshinEdge>());

		for (List<IDirectedEdge> edgesOfOneGraph : clone.edges) {
			// Again unfortunately, we have to access the mappings using
			// indices.
			for (int i = 0; i < edgesOfOneGraph.size(); i++) {
				Map<HenshinGraph, HenshinEdge> innerMap = innerMaps.get(i);
				IDirectedEdge edge = edgesOfOneGraph.get(i);
				HenshinEdge newEdge = henshin2ConqatGraphElementMap.get(edge);
				if (newEdge.getType() == AttributeEReference.instance) {
					HenshinGraph newGraph = newEdge.getHenshinGraph();

					innerMap.put(newGraph, newEdge);
					outerMap.put(newEdge, innerMap);
				}
			}
		}
		return outerMap;
	}

	/**
	 * Creates a dummy logger that won't perform any actions. This is to keep
	 * the console clean from ConQAT-related entries.
	 * 
	 * @return
	 */
	private IConQATLogger createDummyLogger() {
		return new IConQATLogger() {
			public void debug(Object arg0) {
			}

			public void debug(Object arg0, Throwable arg1) {
			}

			public void error(Object arg0) {
			}

			public void error(Object arg0, Throwable arg1) {
			}

			public void info(Object arg0) {
			}

			public void info(Object arg0, Throwable arg1) {
			}

			public void warn(Object arg0) {
			}

			public void warn(Object arg0, Throwable arg1) {
			}

			public ELogLevel getMinLogLevel() {
				return null;
			}

			public void log(ELogLevel arg0, Object arg1) {

			}

			public void log(ELogLevel arg0, Object arg1, Throwable arg2) {
			}

		};
	}

}
