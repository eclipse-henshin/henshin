package org.eclipse.emf.henshin.variability.matcher;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.model.Rule;

import aima.core.logic.propositional.parsing.ast.Sentence;

/**
 * One match as yielded by variability-aware matching, comprising a regular
 * match and a set of selected features producing the regular rule yielding the
 * match.
 * 
 * @author Daniel Strüber
 *
 */
public class VariabilityAwareMatch {
	
	final private Rule rule;
	final private Match match;
	final private Set<Sentence> selected;
	
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

	public Set<Sentence> getSelected() {
		return selected;
	}

	public Rule getRule() {
		return rule;
	}

	public void prepareRule() {
		rulePreperator.doPreparation();
	}

	public void undoPreparation() {
		rulePreperator.undo();
	}
}
