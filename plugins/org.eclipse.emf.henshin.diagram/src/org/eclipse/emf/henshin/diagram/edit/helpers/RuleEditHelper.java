/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.edit.helpers;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleEditPart;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gmf.runtime.emf.core.resources.GMFResource;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class RuleEditHelper extends HenshinBaseEditHelper {

	public static class RuleListener extends EContentAdapter {

		private Rule rule;

		/**
		 * Default constructor.
		 */
		public RuleListener(EObject ruleElement) {
			while (ruleElement != null && !(ruleElement instanceof Rule)) {
				ruleElement = ruleElement.eContainer();
			}
			if (ruleElement == null) {
				throw new IllegalArgumentException();
			}
			this.rule = (Rule) ruleElement;
		}

		/**
		 * Dispose this listener.
		 */
		public void dispose() {
			rule.eAdapters().remove(rule);
		}
	}

	/*
	 * Keys for default action eAnnotations.
	 */
	private static final String DEFAULT_ACTION_EANNOTATION_KEY = "defaultAction";
	private static final String DEFAULT_ACTION_VALUE_KEY = "value";

	public static Action getDefaultAction(Rule rule) {
		View ruleView = findRuleView(rule.getRootRule());
		if (ruleView != null) {
			EAnnotation annotation = ruleView.getEAnnotation(DEFAULT_ACTION_EANNOTATION_KEY);
			if (annotation != null) {
				String value = annotation.getDetails().get("value");
				if (value != null) {
					try {
						return Action.parse(value);
					} catch (Throwable t) {
						// use fall-back (do nothing here)
					}
				}
			}
		}
		// Fall-back:
		return new Action(Action.Type.PRESERVE);
	}

	public static void setDefaultAction(Rule rule, Action action) {
		View ruleView = findRuleView(rule.getRootRule());
		if (ruleView != null) {
			EAnnotation annotation = ruleView.getEAnnotation(DEFAULT_ACTION_EANNOTATION_KEY);
			if (annotation == null) {
				annotation = EcoreFactory.eINSTANCE.createEAnnotation();
				annotation.setSource(DEFAULT_ACTION_EANNOTATION_KEY);
				annotation.setEModelElement(ruleView);
			}
			if (action == null) {
				action = new Action(Action.Type.PRESERVE);
			}
			annotation.getDetails().put(DEFAULT_ACTION_VALUE_KEY, action.toString());
		}
	}

	/**
	 * Check whether a given view is a rule view.
	 * @param view The view.
	 * @return <code>true</code> if it is a rule view.
	 */
	public static boolean isRuleView(View view) {
		// We allow only the root view for rules.
		return (view != null && view.getElement() instanceof Rule
				&& view.getType().equals(String.valueOf(RuleEditPart.VISUAL_ID)));
	}

	/**
	 * Find the corresponding view for a rule.
	 * @param rule Rule.
	 * @return The rule view if found.
	 */
	public static View findRuleView(Rule rule) {
		for (Resource resource : rule.eResource().getResourceSet().getResources()) {
			if (resource instanceof GMFResource) {
				for (EObject object : resource.getContents()) {
					if (object instanceof Diagram && (((Diagram) object).getElement() == rule.getModule())) {
						Diagram diagram = (Diagram) object;
						for (int i = 0; i < diagram.getChildren().size(); i++) {
							View view = (View) diagram.getChildren().get(i);
							if (isRuleView(view) && view.getElement() == rule)
								return view;
						}
					}
				}
			}
		}
		return null;
	}

}
