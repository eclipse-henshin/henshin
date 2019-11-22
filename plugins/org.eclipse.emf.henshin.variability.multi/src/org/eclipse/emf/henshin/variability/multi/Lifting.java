package org.eclipse.emf.henshin.variability.multi;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.Change;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.util.Logic;
import org.eclipse.emf.henshin.variability.util.SatChecker;

import de.ovgu.featureide.fm.core.base.IFeatureModel;

public class Lifting {
	private EGraph graph;
	private RuleToProductLineEngine engine;
	private IFeatureModel fmP;
	private Map<EObject, String> pcsP;

	public Lifting(RuleToProductLineEngine engine, EGraph graph, IFeatureModel fmP, Map<EObject, String> pcsP) {
		this.engine = engine;
		this.graph = graph;
		this.fmP = fmP;
		this.pcsP = pcsP;
	}

	public Change liftAndApplyRule(Match match, Rule rule) {
		String phiApply = computePhiApply(match);

		String fmExpressionAsCNF = ElementHelper.getFMExpressionAsCNF(this.fmP);
		String eval = Logic.and(phiApply, fmExpressionAsCNF);
		SatChecker satChecker = new SatChecker();
		Boolean isSat = satChecker.isSatisfiable(eval);
		if (isSat.booleanValue()) {
			Match resultMatch = new MatchImpl(rule, true);
			Change change = engine.createChange(rule, graph, match, resultMatch, phiApply, fmExpressionAsCNF, pcsP);
			change.applyAndReverse();
			return change;
		}
		return null;
	}

	public String computePhiApply(Match match) {
		String phiApply = Logic.TRUE;
		for (EObject eObject : match.getNodeTargets()) {
			if (pcsP.containsKey(eObject)) {
				phiApply = Logic.and(phiApply, pcsP.get(eObject));
			}
		}
		return phiApply;
	}
}
