package org.eclipse.emf.henshin.interpreter.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * This class contains informations about partial matches for a given module and generates a textual report,which can be used to give the Henshin users a detailed feedback.
 * 
 * @author Svetlana Arifulina
 *
 */
public class PartialMatchReport {

	/**
	 * Module to be used.
	 */
	Module module;
	/**
	 * List of partial matches.
	 */
	List<Match> matches;
	/**
	 * Map connecting a rule from the module with partial matches, which have been found for it.
	 */
	Map<Rule, List<PartialMatchInfo>> infos;

	/**
	 * Constructor
	 */
	public PartialMatchReport(Module module, List<Match> matches) {
		super();
		this.module = module;
		this.matches = matches;
	}

	/**
	 * Helping class containing the information about one partial match and the delta with the lhs.
	 * 
	 * @author Svetlana Arifulina
	 *
	 */
	private class PartialMatchInfo {

		/**
		 * Complete or partial match
		 */
		Match match;
		/**
		 * Delta from the partial match to the lhs
		 */
		Graph delta;
		/**
		 * Flag to indicate a complete match
		 */
		boolean isComplete = false;
		/**
		 * Coverage of the lhs with the partial match
		 */
		int coverage;

		public int getCoverage() {
			return coverage;
		}

		public void setCoverage(int coverage) {
			this.coverage = coverage;
		}

		public boolean isComplete() {
			return isComplete;
		}

		public void setComplete(boolean isComplete) {
			this.isComplete = isComplete;
		}

		public Match getMatch() {
			return match;
		}

		public void setMatch(Match match) {
			this.match = match;
		}

		public Graph getDelta() {
			return delta;
		}

		public void setDelta(Graph delta) {
			this.delta = delta;
		}

	}

	/**
	 * The method generating a textual report.
	 * 
	 * @return Textual report
	 */
	public String getReport() {

		this.infos = collectPartialMatchInfos(module, matches);

		StringBuffer buffer = new StringBuffer();

		buffer.append("============================\n");
		buffer.append("Partial match statistics\n");
		buffer.append("============================\n");

		for (Rule rule : infos.keySet()) {
			buffer.append("Partial match for " + rule.getName() + "\n");
			buffer.append("----------------------------------\n");
			buffer.append("Matches and deltas:\n");
			
			for (PartialMatchInfo partialMatchInfo : infos.get(rule)) {
				if (partialMatchInfo.isComplete()) {
					buffer.append("This is a complete match.\n");
				}
				
				buffer.append(partialMatchInfo.getMatch().toString() + "\n");
				buffer.append(partialMatchInfo.getDelta().toString() + "\n");
				
				for (Node node : partialMatchInfo.getDelta().getNodes()) {
					buffer.append(node.toString() + "\n");
				}
				
				for (Edge edge : partialMatchInfo.getDelta().getEdges()) {
					buffer.append(edge.toString() + "\n");
				}
			}
			
			buffer.append("============================\n");
		}

		return buffer.toString();

	}

	/**
	 * Method collecting the report information for the given partial matches
	 * 
	 * @param module Module to be used.
	 * @param matches Module to be used.
	 * @return 
	 */
	private Map<Rule, List<PartialMatchInfo>> collectPartialMatchInfos(
			Module module, List<Match> matches) {

		Map<Rule, List<PartialMatchInfo>> infos = new HashMap<Rule, List<PartialMatchInfo>>();

		for (Match match : matches) {

			Rule originalRule = (Rule) module
					.getUnit(match.getRule().getName());

			if (!infos.containsKey(originalRule)) {
				infos.put(originalRule, new ArrayList<PartialMatchInfo>());
			}

			PartialMatchInfo info = new PartialMatchInfo();
			info.setMatch(match);
			
			info.setDelta(computeDelta(originalRule, match.getRule(), match));
			
			if (info.getDelta().getNodes().isEmpty()
					&& info.getDelta().getEdges().isEmpty()) {
				info.setComplete(true);
			}
			
			info.setCoverage((info.getDelta().getNodes().size() + info.getDelta().getEdges().size())/(originalRule.getLhs().getNodes().size() + originalRule.getLhs().getEdges().size()));

			infos.get(originalRule).add(info);
		}

		return infos;

	}

	/**
	 * Method computing the different between a partial match and a lhs.
	 * 
	 * @param originalRule
	 * @param matchingRule
	 * @param match
	 * @return
	 */
	private Graph computeDelta(Rule originalRule, Rule matchingRule, Match match) {

		Graph delta = HenshinFactory.eINSTANCE
				.createGraph("Partial match delta for "
						+ matchingRule.getName());
		boolean nodeFound = false;

		for (Node node : originalRule.getLhs().getNodes()) {
			nodeFound = false;
			for (Node node2 : matchingRule.getLhs().getNodes()) {
				if (node.getType() == node2.getType()) {
					nodeFound = true;
					break;
				}
			}
			if (!nodeFound) {
				HenshinFactory.eINSTANCE.createNode(delta, node.getType(),
						node.getName());
				delta.getEdges().addAll(node.getIncoming());
				delta.getEdges().addAll(node.getOutgoing());
			}
		}

		return delta;
	}

	/**
	 * Method computing the coverage of a lhs by a partial match
	 * 
	 * @return
	 */
	public double getCoverage() {
		
		int coverage = 0;
		
		for (Rule rule : infos.keySet()) {
			for (PartialMatchInfo info : infos.get(rule)) {
				coverage += info.getCoverage();
			}
		}
		
		coverage = coverage/infos.keySet().size();
		
		return coverage;
	}

}
