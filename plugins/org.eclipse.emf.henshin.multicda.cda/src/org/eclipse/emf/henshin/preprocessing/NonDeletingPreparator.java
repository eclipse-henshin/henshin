package org.eclipse.emf.henshin.preprocessing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

public class NonDeletingPreparator {
	public static List<RulePair> prepareNonDeletingVersions(List<Rule> rules) {
		HenshinFactory henshinFactory = new HenshinFactoryImpl();
		List<RulePair> copiesOfRulesWithoutDeletion = new LinkedList<RulePair>(); 
		for(Rule ruleToCopy : rules){
			Copier copierForRule = new Copier();
			Rule copyOfRule = (Rule) copierForRule.copy(ruleToCopy);

			copyOfRule.setDescription(Reason.NODE_SEPARATOR);
			copierForRule.copyReferences();
			
			MappingList mappings = copyOfRule.getMappings();
			mappings.clear();
			for(Node nodeInLhs : copyOfRule.getLhs().getNodes()){
				nodeInLhs.getAttributes().clear();
			}
					
			Copier copierForLhsGraph = new Copier();
			
			Graph copiedLhs = (Graph) copierForLhsGraph.copy(copyOfRule.getLhs());
			copierForLhsGraph.copyReferences();

			copiedLhs.setName("Rhs");
			copyOfRule.setRhs(copiedLhs);
			
			for(Node nodeInLhsOfCopiedRule : copyOfRule.getLhs().getNodes()){
				Node nodeInNewRhs = (Node) copierForLhsGraph.get(nodeInLhsOfCopiedRule);
				Mapping createdMapping = henshinFactory.createMapping(nodeInLhsOfCopiedRule, nodeInNewRhs);
				mappings.add(createdMapping);					
			}
			
			copiesOfRulesWithoutDeletion.add(new RulePair(copyOfRule, ruleToCopy));

		}
		return copiesOfRulesWithoutDeletion;
	}
	public static List<Rule> prepareNoneDeletingsVersionsRules(List<Rule> rules) {
		List<Rule> result = new ArrayList<Rule>();
		List<RulePair> pairs = prepareNonDeletingVersions(rules);
		for (RulePair rulePair : pairs) {
			result.add(rulePair.getCopy());
		}
		return result;
	}
	public static Rule prepareNoneDeletingsVersionsRules(Rule rule) {
		List<Rule> result = new ArrayList<Rule>();
		result.add(rule);
		List<RulePair> pairs = prepareNonDeletingVersions(result);
		return pairs.get(0).getCopy();
	}
}
