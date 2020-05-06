package org.eclipse.emf.henshin.variability.configuration.ui.helpers;

import org.eclipse.core.runtime.Adapters;
import org.eclipse.emf.henshin.diagram.edit.parts.AttributeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.EdgeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleEditPart;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.variability.matcher.FeatureExpression;
import org.eclipse.emf.henshin.variability.wrapper.TransactionalVariabilityFactory;

import aima.core.logic.propositional.parsing.ast.Sentence;
import configuration.Configuration;

/**
 * This class provides utility methods for using the different concealing strategies.
 * The default strategy is {@link FigureVisibilityConcealingStrategy}.
 *  
 * @author Stefan Schulz
 *
 */
public class RuleEditPartVisibilityHelper {
	
	private static boolean CONCEAL = true;
	private static boolean REVEAL = false;

	private static AbstractConcealingStrategy fadingStrategy = new FigureVisibilityConcealingStrategy();

	public static AbstractConcealingStrategy getFadingStrategy() {
		return fadingStrategy;
	}

	public static void setFadingStrategy(AbstractConcealingStrategy fadingStrategy) {
		RuleEditPartVisibilityHelper.fadingStrategy = fadingStrategy;
	}

	private static interface GraphElementConcealingCondition {
		public boolean shouldConceal(GraphElement graphElement);
	}

	private static class BaseRuleConcealingCondition implements GraphElementConcealingCondition {

		@Override
		public boolean shouldConceal(GraphElement graphElement) {
			String pc = TransactionalVariabilityFactory.INSTANCE.createVariabilityGraphElement(graphElement).getPresenceCondition();
			return pc != null && !pc.isEmpty();
		}
	}

	private static class ConfiguredRuleConcealingCondition implements GraphElementConcealingCondition {

		final Sentence configurationExpr;

		public ConfiguredRuleConcealingCondition(Configuration configuration) {
			configurationExpr = VariabilityModelHelper.getFeatureExpression(configuration);
		}

		@Override
		public boolean shouldConceal(GraphElement graphElement) {
			boolean result = REVEAL;
			String pc = TransactionalVariabilityFactory.INSTANCE.createVariabilityGraphElement(graphElement).getPresenceCondition();
			if (pc != null && !pc.isEmpty()) {
				Sentence expr = FeatureExpression.getExpr(pc);
				result = FeatureExpression.contradicts(expr, configurationExpr);
			}
			return result;
		}
	}

	private static RuleCompartmentEditPart getRuleCompartmentEditPart(RuleEditPart ruleEditPart) {
		if (ruleEditPart == null) {
			return null;
		}

		RuleCompartmentEditPart ruleCompEditPart = null;

		for (Object child : ruleEditPart.getChildren()) {
			if (child instanceof RuleCompartmentEditPart) {
				ruleCompEditPart = (RuleCompartmentEditPart) child;
			}
		}

		return ruleCompEditPart;
	}
	
	private static void toggleNode(NodeEditPart nodeEditPart, boolean shouldConceal) {
		fadingStrategy.apply(nodeEditPart, shouldConceal);

		// Since edges have to be connected to a node, we toggle the visibility
		// of attached edges too
		for (Object sourceConnection : nodeEditPart.getSourceConnections()) {
			if (sourceConnection instanceof EdgeEditPart) {
				EdgeEditPart edgeEditPart = (EdgeEditPart) sourceConnection;
				fadingStrategy.apply(edgeEditPart, shouldConceal);
			}
		}

		for (Object targetConnection : nodeEditPart.getTargetConnections()) {
			if (targetConnection instanceof EdgeEditPart) {
				EdgeEditPart edgeEditPart = (EdgeEditPart) targetConnection;
				fadingStrategy.apply(edgeEditPart, shouldConceal);
			}
		}
	}

	private static void toggleNode(NodeEditPart nodeEditPart, GraphElementConcealingCondition condition) {
		boolean shouldConceal = condition.shouldConceal(nodeEditPart.getNode());
		toggleNode(nodeEditPart, shouldConceal);
	}

	private static void toggleRuleParts(RuleEditPart ruleEditPart, GraphElementConcealingCondition condition) {
		RuleCompartmentEditPart ruleCompEditPart = getRuleCompartmentEditPart(ruleEditPart);

		for (Object child : ruleCompEditPart.getChildren()) {
			if (child instanceof NodeEditPart) {
				NodeEditPart nodeEditPart = (NodeEditPart) child;

				toggleNode(nodeEditPart, condition);
				toggleEdges(nodeEditPart, condition);
				toggleAttributes(nodeEditPart, condition);
			}
		}
	}

	private static void toggleEdges(NodeEditPart nodeEditPart, GraphElementConcealingCondition condition) {
		for (Object sourceConnection : nodeEditPart.getSourceConnections()) {
			if (sourceConnection instanceof EdgeEditPart) {
				EdgeEditPart edgeEditPart = (EdgeEditPart) sourceConnection;
				boolean shouldConceal = condition.shouldConceal((Edge) (edgeEditPart.getNotationView().getElement()));
				fadingStrategy.apply(edgeEditPart, shouldConceal);
			}
		}

		for (Object targetConnection : nodeEditPart.getTargetConnections()) {
			if (targetConnection instanceof EdgeEditPart) {
				EdgeEditPart edgeEditPart = (EdgeEditPart) targetConnection;
				boolean shouldConceal = condition.shouldConceal((Edge) (edgeEditPart.getNotationView().getElement()));
				fadingStrategy.apply(edgeEditPart, shouldConceal);
			}
		}
	}
	
	private static void toggleAttributes(NodeEditPart nodeEditPart, GraphElementConcealingCondition condition) {
		for (Object nodeChild : nodeEditPart.getChildren()) {
			if (nodeChild instanceof NodeCompartmentEditPart) {
				NodeCompartmentEditPart compartmentEditPart = (NodeCompartmentEditPart) nodeChild;
				for (Object compartmentChild : compartmentEditPart.getChildren()) {
					if (compartmentChild instanceof AttributeEditPart) {
						AttributeEditPart attributeEditPart = (AttributeEditPart) compartmentChild;
						Attribute attribute = Adapters.adapt(attributeEditPart, Attribute.class, true); //TODO: test this
						boolean shouldConceal = condition.shouldConceal(attribute);
						fadingStrategy.apply(attributeEditPart, shouldConceal);
					}
				}
			}
		}
	}

	public static void showBaseRule(RuleEditPart ruleEditPart) {
		if (ruleEditPart == null) {
			return;
		}

		toggleRuleParts(ruleEditPart, new BaseRuleConcealingCondition());
	}

	public static void showConfiguredRule(RuleEditPart ruleEditPart, Configuration configuration, String featureConstraint) {
		if (featureConstraint == null || configuration == null || ruleEditPart == null) {
			return;
		}

		toggleRuleParts(ruleEditPart, new ConfiguredRuleConcealingCondition(configuration));

//		if (ruleEditPart.getParent().getViewer() != null &&  ruleEditPart.getParent().getViewer().getControl() != null) {
//			((FigureCanvas)ruleEditPart.getParent().getViewer().getControl()).scrollToX(1);
//			((FigureCanvas)ruleEditPart.getParent().getViewer().getControl()).scrollToX(-1);
//		}
		
	}

	public static void showFullRule(RuleEditPart ruleEditPart) {
		if (ruleEditPart == null) {
			return;
		}

		RuleCompartmentEditPart ruleCompEditPart = getRuleCompartmentEditPart(ruleEditPart);
		for (Object child : ruleCompEditPart.getChildren()) {
			if (child instanceof NodeEditPart) {
				NodeEditPart nodeEditPart = (NodeEditPart) child;
				toggleNode(nodeEditPart, REVEAL);
				for (Object nodeChild : nodeEditPart.getChildren()) {
					if (nodeChild instanceof NodeCompartmentEditPart) {
						NodeCompartmentEditPart compartmentEditPart = (NodeCompartmentEditPart) nodeChild;
						for (Object compartmentChild : compartmentEditPart.getChildren()) {
							if (compartmentChild instanceof AttributeEditPart) {	
								AttributeEditPart attributeEditPart = (AttributeEditPart) compartmentChild;
								fadingStrategy.apply(attributeEditPart, REVEAL);
							}
						}
					}
				}
			}
		}
	}

}
