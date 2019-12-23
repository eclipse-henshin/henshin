 package org.eclipse.emf.henshin.variability.mergein.clone;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.conqat.engine.model_clones.detection.ModelCloneReporterMock;
import org.conqat.engine.model_clones.detection.ModelCloneReporterMock.ModelClone;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.mergein.conqat.ConqatManager;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinEdge;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraph;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraphFactory;
import org.eclipse.emf.henshin.variability.mergein.normalize.RuleToHenshinGraphMap;

/**
 * A clone detector for Henshin rules employing the ConQAT clone detection
 * technique. For internal calculations, the Henshin rule is transformed into a
 * normalized representation ({@link HenshinGraph}) and, consequently, into a
 * custom representation as required by ConQAT.
 * 
 * @author strueber
 *
 */
public class ConqatBasedCloneGroupDetector extends AbstractCloneGroupDetector {

	boolean includeRhs;
	int minSubCloneSize;
	
	public ConqatBasedCloneGroupDetector(List<Rule> rules) {
		super(rules);
	}
	public ConqatBasedCloneGroupDetector(List<Rule> rules, boolean includeRhs) {
		super(rules);
		this.includeRhs = includeRhs;
	}

	public ConqatBasedCloneGroupDetector(List<Rule> rules, int minSubCloneSize,
			boolean includeRhs) {
		super(rules);
		this.minSubCloneSize = minSubCloneSize;
		this.includeRhs = includeRhs;
	}
	@Override
	public void detectCloneGroups() {
		RuleToHenshinGraphMap ruleGraphs = HenshinGraphFactory.getInstance()
				.createIntegratedGraphs(rules, includeRhs);
		ConqatManager conquatManager = new ConqatManager(
				ruleGraphs.getHenshinGraphs(),minSubCloneSize);
		conquatManager.doCloneDetection();
		ModelCloneReporterMock reporter = conquatManager.getResultReporter();
		
		result = new HashSet<CloneGroup>();
		for (ModelClone clone : reporter.modelClones) {
			List<HenshinGraph> involvedRuleGraphs = conquatManager
					.getInvolvedHenshinGraphs(clone);
			Map<HenshinEdge, Map<HenshinGraph, HenshinEdge>> henshinEdgeMappings = conquatManager
					.createHenshinEdgeMappings(clone);
			Map<HenshinEdge, Map<HenshinGraph, HenshinEdge>> henshinAttributeMappings = conquatManager
					.createHenshinAttributeMappings(clone);
			
			if (henshinEdgeMappings.isEmpty() && henshinAttributeMappings.isEmpty())
				continue;
				
			if (!IsomorphyChecker.cloneIsIsomorphic(henshinEdgeMappings, henshinAttributeMappings)) {
				System.out.println("[Info] Removed an invalid (non-isomorphic) result entry  from the clone detection result!");
				continue;
			} 
			
			List<Rule> involvedRules = convertInvolvedRules(involvedRuleGraphs, 
					ruleGraphs);
			Map<Edge, Map<Rule, Edge>> edgeMappings = convertEdgeMappings(
					henshinEdgeMappings, ruleGraphs);
			Map<Attribute, Map<Rule, Attribute>> attributeMappings = convertAttributeMappings(
					henshinAttributeMappings, ruleGraphs);

			if (!involvedRules.isEmpty()) {
				CloneGroup newCloneGroup = new CloneGroup(involvedRules,
						edgeMappings, attributeMappings);
				result.add(newCloneGroup);
			}
		}
	}

}
