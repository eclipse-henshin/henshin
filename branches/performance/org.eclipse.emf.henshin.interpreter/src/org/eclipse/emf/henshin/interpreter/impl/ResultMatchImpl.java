package org.eclipse.emf.henshin.interpreter.impl;

import org.eclipse.emf.ecore.xml.type.internal.RegEx.Match;
import org.eclipse.emf.henshin.interpreter.Assignment;
import org.eclipse.emf.henshin.model.Rule;

/**
 * {@link Match} implementation for result matches.
 * 
 * @author Christian Krause
 */
public final class ResultMatchImpl extends MatchImpl {

	/**
	 * Default constructor.
	 * @param rule The rule that this result match is used for.
	 */
	public ResultMatchImpl(Rule rule) {
		super(rule, true);
	}

	/**
	 * Constructor which copies an assignment or a match.
	 * @param assignment Assignment or match to be copied.
	 */
	public ResultMatchImpl(Assignment assignment) {
		super(assignment, true);
	}

}
