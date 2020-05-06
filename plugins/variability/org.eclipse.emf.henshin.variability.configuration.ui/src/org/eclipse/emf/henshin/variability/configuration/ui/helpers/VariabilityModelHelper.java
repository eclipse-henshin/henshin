package org.eclipse.emf.henshin.variability.configuration.ui.helpers;

import java.util.ArrayList;

import org.eclipse.emf.henshin.diagram.edit.parts.RuleEditPart;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.matcher.FeatureExpression;
import org.eclipse.emf.henshin.variability.util.Logic;
import org.eclipse.emf.henshin.variability.wrapper.TransactionalVariabilityFactory;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityNode;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;

import aima.core.logic.propositional.parsing.ast.Sentence;
import configuration.Configuration;
import configuration.Feature;
import configuration.FeatureBinding;

/**
 * This class helps handling the variability-aware model elements.
 * 
 * @author Stefan Schulz
 *
 */
public class VariabilityModelHelper {

	public static Rule getRuleForEditPart(RuleEditPart ruleEditPart) {
		Rule result = null;

		if (ruleEditPart != null) {
			result = (Rule) ((ShapeImpl) ruleEditPart.getModel()).getElement();
		}

		return result;
	}

	public static Sentence getFeatureExpression(Configuration configuration) {
		Sentence expr = FeatureExpression.getExpr(TransactionalVariabilityFactory.INSTANCE.createVariabilityRule(configuration.getRule()).getFeatureConstraint());
		for (Feature vp : configuration.getFeatures()) {
			if (vp.getBinding() == FeatureBinding.TRUE) {
				expr = FeatureExpression.and(expr, FeatureExpression.getExpr(vp.getName()));
			} else if (vp.getBinding() == FeatureBinding.FALSE) {
				expr = FeatureExpression.andNot(expr, FeatureExpression.getExpr(vp.getName()));
			}
		}
		return expr;
	}

	public static String getPresenceCondition(Configuration configuration) {
		StringBuilder result = new StringBuilder();
		String delimiter = "";

		for (Feature vp : configuration.getFeatures()) {
			if (vp.getBinding() != FeatureBinding.UNBOUND) {
				result.append(delimiter);

				if (vp.getBinding() == FeatureBinding.FALSE) {
					result.append("!");
				}
				result.append(vp.getName());
				delimiter = " & ";
			}
		}

		return result.toString();
	}

	private static Sentence getFeatureExpression(Configuration configuration, Feature feature) {
		Sentence expr = FeatureExpression.getExpr(TransactionalVariabilityFactory.INSTANCE.createVariabilityRule(configuration.getRule()).getFeatureConstraint());
		if (expr == null)
			expr = FeatureExpression.getExpr(Logic.TRUE);
		for (Feature vp : configuration.getFeatures()) {
			if (vp.getName() != feature.getName() && vp.getBinding() == FeatureBinding.TRUE) {
				expr = FeatureExpression.and(expr, FeatureExpression.getExpr(vp.getName()));
			} else if (vp.getName() != feature.getName() && vp.getBinding() == FeatureBinding.FALSE) {
				expr = FeatureExpression.andNot(expr, FeatureExpression.getExpr(vp.getName()));
			}
		}
		return expr;
	}

	public static String[] getNonContradictingBindingOptions(Configuration configuration, Feature vp) {
		ArrayList<String> options = new ArrayList<String>();
		Sentence configurationExpr = getFeatureExpression(configuration, vp);

		options.add(FeatureBinding.UNBOUND.getName());
		if (!FeatureExpression.contradicts(configurationExpr, FeatureExpression.getExpr(vp.getName()))) {
			options.add(FeatureBinding.TRUE.getName());
		}

		if (!FeatureExpression.contradicts(configurationExpr, FeatureExpression.getExpr("!" + vp.getName()))) {
			options.add(FeatureBinding.FALSE.getName());
		}

		String[] result = new String[options.size()];
		for (int i = result.length - 1; i >= 0; i--) {
			result[i] = options.get(i);
		}

		return result;
	}

	public static String getPresenceConditionForNewEdge(Edge edge, Configuration configuration) {
		String configPC = getPresenceCondition(configuration);
		VariabilityNode varSource = TransactionalVariabilityFactory.INSTANCE.createVariabilityNode(edge.getSource());
		VariabilityNode varTarget = TransactionalVariabilityFactory.INSTANCE.createVariabilityNode(edge.getTarget());
		Sentence config = FeatureExpression.getExpr(configPC);
		Sentence source = FeatureExpression.getExpr(varSource.getPresenceCondition());
		Sentence target = FeatureExpression.getExpr(varTarget.getPresenceCondition());

		// Out of the current configuration, the source node, and the target
		// node, try to find the strongest presence condition. If there is 
		// no single strongest, do a conjunction over the two strongest
		// or all.
		if (FeatureExpression.implies(source, config)) {
			if (FeatureExpression.implies(source, target)) {
				return varSource.getPresenceCondition();
			} else {
				return varSource.getPresenceCondition() + " & " + varTarget.getPresenceCondition();
			}
		}

		if (FeatureExpression.implies(target, config)) {
			if (FeatureExpression.implies(target, config)) {
				return varTarget.getPresenceCondition();
			} else {
				return varSource.getPresenceCondition() + " & " + varTarget.getPresenceCondition();
			}
		}

		if (FeatureExpression.implies(config, source)) {
			if (FeatureExpression.implies(config, target)) {
				return configPC;
			} else {
				return config + " & " + varTarget.getPresenceCondition();
			}
		}

		if (FeatureExpression.implies(config, target)) {
			return varSource.getPresenceCondition() + " & " + configPC;
		} else {
			return varSource.getPresenceCondition() + " & " + varTarget.getPresenceCondition() + " & "
					+ configPC;
		}
	}
}
