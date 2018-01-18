package org.eclipse.emf.henshin.rulegen.matching;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * A model matcher that delegates to the EMFCompare matching engine.
 * 
 * @author Timo Kehrer
 */
public class EMFCompareMatcherAdapter implements IMatcher {

	private Matching matching;

	/**
	 * Initialize
	 */
	public EMFCompareMatcherAdapter() {
		super();
	}

	@Override
	public Matching createMatching(Resource modelA, Resource modelB) {
		matching = new Matching();

		// Compare models and get all matches
		IComparisonScope emfScope = new DefaultComparisonScope(modelA, modelB, null);
		Comparison comparison = EMFCompare.builder().build().compare(emfScope);
		EList<Match> matches = comparison.getMatches();

		// Convert to our own representation of correspondences
		populateCorrespondences(matches);

		return matching;
	}

	private void populateCorrespondences(EList<Match> matches) {
		for (Iterator<Match> it = matches.iterator(); it.hasNext();) {
			Match match = it.next();
			populateCorrespondence(match);
		}
	}

	private void populateCorrespondence(Match match) {
		if (match.getLeft() != null && match.getRight() != null) {
			matching.addCorrespondence(match.getLeft(), match.getRight());
		}
		for (Match subMatch : match.getSubmatches()) {
			populateCorrespondence(subMatch);
		}
	}

	@Override
	public boolean canHandle(Resource modelA, Resource modelB) {
		return true;
	}
}
