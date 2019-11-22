package org.eclipse.emf.henshin.variability.multi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.Rule;
import org.sat4j.specs.TimeoutException;

import de.ovgu.featureide.fm.core.base.IFeatureModel;

public class VBExecution {

	private final VBProcessor processor;

	public VBExecution(VBProcessor processor) {
		this.processor = processor;
	}

	public void transformSecPlModel(IFeatureModel fmP, List<EObject> roots, Rule vbRule) throws TimeoutException {
		transformSecPlModelWithVBRule(fmP, roots, vbRule, new RuleToProductLineEngine());
	}

	public void transformSecPlModelWithVBRule(IFeatureModel fmP, List<EObject> roots, Rule vbRule,
			RuleToProductLineEngine engine) throws TimeoutException {

		Map<EObject, String> pcsP = new HashMap<>();
		EGraphImpl graphP = processor.createEGraphAndCollectPCs(roots, pcsP);

		transformSecPlModelWithVBRule(fmP, roots, vbRule, engine, pcsP, graphP);
	}

	public void transformSecPlModelWithVBRule(IFeatureModel fmP, List<EObject> roots, Rule vbRule,
			RuleToProductLineEngine engine, Map<EObject, String> pcsP, EGraphImpl graphP) {
		new VBRuleToProductLineEngine(vbRule, graphP, fmP, pcsP, engine).transform();
		processor.deleteObsoleteVariabilityAnnotations(roots, pcsP);
		processor.createNewVariabilityAnnotations(roots, pcsP);
	}

	public void transformSecPlModelWithClassicRule(IFeatureModel fmP, List<EObject> roots, Rule rule,
			RuleToProductLineEngine engine) throws TimeoutException {
		Map<EObject, String> pcsP = new HashMap<>();
		EGraphImpl graphP = processor.createEGraphAndCollectPCs(roots, pcsP);

		transformSecPlModelWithClassicRule(fmP, roots, rule, engine, pcsP, graphP);
	}

	public void transformSecPlModelWithClassicRule(IFeatureModel fmP, List<EObject> roots, Rule rule,
			RuleToProductLineEngine engine, Map<EObject, String> pcsP, EGraphImpl graphP) {
		Lifting lifting = new Lifting(engine, graphP, fmP, pcsP);

		List<Match> allMatches = InterpreterUtil.findAllMatches(engine, rule, graphP, null);
		for (Match m : allMatches) {
			lifting.liftAndApplyRule(m, rule);
		}

		processor.deleteObsoleteVariabilityAnnotations(roots, pcsP);

		processor.createNewVariabilityAnnotations(roots, pcsP);
	}
}
