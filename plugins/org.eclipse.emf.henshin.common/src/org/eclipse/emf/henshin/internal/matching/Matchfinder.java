package org.eclipse.emf.henshin.internal.matching;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.internal.conditions.nested.ApplicationCondition;

public class Matchfinder extends ApplicationCondition {

	public Matchfinder(EmfGraph emfGraph,
			Map<Variable, DomainSlot> variableDomainMap,
			AttributeConditionHandler conditionHandler) {
		super(emfGraph, variableDomainMap, conditionHandler, false);
	}

	/**
	 * Returns a random match. This is as slow as computing all matches because
	 * it actually does compute all matches and randomly chooses one as a
	 * result.
	 * 
	 * @return A random match or null if no match exists.
	 */
	public Solution getRandomMatch() {
		while (getNextMatch() != null) {
		}

		if (solutions.size() > 0) {
			int x = new Random().nextInt(solutions.size());
			return solutions.get(x);
		}

		return null;
	}

	/**
	 * Returns a match. On consecutive calls other matches will be returned.
	 * 
	 * @return A match or null if no match exists.
	 */
	public Solution getNextMatch() {
		boolean success = findGraph();

		if (success)
			return solutions.get(solutions.size() - 1);

		return null;
	}

	public List<Solution> getAllMatches() {
		while (getNextMatch() != null) {
		}
		;

		return solutions;
	}
}
