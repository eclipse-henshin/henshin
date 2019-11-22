package org.eclipse.emf.henshin.variability.matcher;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.model.Rule;

import aima.core.logic.propositional.parsing.ast.Sentence;


/**
 * One match as yielded by variability-aware matching, comprising a regular
 * match and a set of selected features producing the regular rule yielding the match.
 * 
 * @author Daniel Strüber
 *
 */
public class VariabilityAwareMatch {
	private Rule rule;
	private RulePreparator rulePreperator;
	
	public VariabilityAwareMatch(Match match, Set<Sentence> selected, Rule rule, RulePreparator rulePreparator) {
		super();
		this.match = match;
		this.selected = new HashSet<Sentence>();
		this.selected.addAll(selected);
		this.rule = rule;
		this.rulePreperator = rulePreparator;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Set<Sentence> getSelected() {
		return selected;
	}

	public void setSelected(Set<Sentence> selected) {
		this.selected = selected;
	}

	private Match match;

	private Set<Sentence> selected;

	public Rule getRule() {
		return rule;
	}
	
	public void prepareRule() {
		rulePreperator.doPreparation();
	}
	
	public void undoPreparation() {
		rulePreperator.undo();
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}
}
