package org.eclipse.emf.henshin.interpreter;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Match interface for mapping {@link Node}s to {@link EObject} and 
 * assigning parameter values by extending {@link Assignment}.
 * 
 * @author Christian Krause, Gregor Bonifer, Enrico Biermann
 */
public interface Match extends Assignment {
	
	/**
	 * Get the rule that this match is used for.
	 * @return The rule.
	 */
	Rule getRule();

	/**
	 * Get the match target for a node.
	 * @param node The node.
	 * @return The matched target object.
	 */
	EObject getNodeTarget(Node node);

	/**
	 * Set the match target for a node.
	 * @param node The node.
	 * @param target The match target.
	 */
	void setNodeTarget(Node node, EObject target);

	/**
	 * Get all node targets of this match.
	 * @return All node targets.
	 */
	List<EObject> getNodeTargets();

	/**
	 * Get the nested matches for a multi-rule.
	 * @param multiRule The multi-rule.
	 * @return List of matches.
	 */
	List<Match> getNestedMatches(Rule multiRule);

	/**
	 * Checks whether this match overlaps with another match. 
	 * The second match can be from a different rule. Two matches
	 * overlap if {@link #getNodeTargets()} contain shared elements.
	 * @param match A second match to check against.
	 * @return <code>true</code> if both matches have common targets.
	 */
	boolean overlapsWith(Match match);

	/**
	 * Checks if all nodes have a target and all nested matches are also complete.
	 * @return <code>true</code> if all nodes are matched.
	 */
	boolean isComplete();
	
	/**
	 * Checks whether this match is complete, whether the typing of the matched
	 * objects is correct with respect to the node types, and whether all edges
	 * are present.
	 * @return <code>true</code> if the match is valid.
	 */
	boolean isValid();

	/**
	 * Checks whether this is a match for a result of a rule application. 
	 * @return <code>true</code> if it is a result match.
	 */
	boolean isResultMatch();
	
}
