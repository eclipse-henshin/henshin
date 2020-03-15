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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.henshin.diagram.edit.parts.InvocationEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.UnitCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.MultiUnit;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.UnaryUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated NOT
 */
public class UnitEditHelper extends HenshinBaseEditHelper {

	/**
	 * An enum to refer to special invocation views (subUnits).
	 */
	public static enum InvocationViewKey {

		IF(0), THEN(1), ELSE(2), LOOP(0), ITERATE(0);

		private int index;

		private InvocationViewKey(int index) {
			this.index = index;
		}

	}

	/**
	 * Get the invocation views of a unit view. This is a list of all views
	 * that correspond to subUnits of that unit.
	 * @param unitView The unit view.
	 * @param withNulls Whether to include nulls for missing subUnits.
	 * @return List of views.
	 */
	public static List<View> getInvocationViews(View unitView, boolean withNulls) {

		// Find the unit and the compartment view:
		if (String.valueOf(UnitCompartmentEditPart.VISUAL_ID).equals(unitView.getType())) {
			unitView = (View) unitView.eContainer();
		}
		View unitCompartment = ViewUtil.getChildBySemanticHint(unitView,
				String.valueOf(UnitCompartmentEditPart.VISUAL_ID));

		// Get the transformation unit and its subunits including nulls:
		Unit unit = (Unit) unitView.getElement();
		List<Unit> subunits = getSubUnitsWithNulls(unit);

		// Now search for the corresponding views:
		List<View> invocations = new ArrayList<View>(subunits.size());
		for (Unit subunit : subunits) {
			invocations.add(getInvocationView(unitCompartment, subunit, invocations));
		}
		return invocations;
	}

	public static View getInvocationView(View unitView, InvocationViewKey key) {
		return getInvocationViews(unitView, true).get(key.index);
	}

	/*
	 * Get the subUnits of a unit including nulls.
	 */
	private static List<Unit> getSubUnitsWithNulls(Unit unit) {
		List<Unit> subUnits = new ArrayList<Unit>();
		if (unit instanceof ConditionalUnit) {
			subUnits.add(((ConditionalUnit) unit).getIf());
			subUnits.add(((ConditionalUnit) unit).getThen());
			subUnits.add(((ConditionalUnit) unit).getElse());
		} else if (unit instanceof UnaryUnit) {
			subUnits.add(((UnaryUnit) unit).getSubUnit());
		} else if (unit instanceof MultiUnit) {
			subUnits.addAll(((MultiUnit) unit).getSubUnits());
		}
		return subUnits;
	}

	/*
	 * Find an invocation view.
	 */
	private static View getInvocationView(View unitCompartment, Unit target, Collection<View> exclude) {
		if (unitCompartment == null || target == null) {
			return null;
		}
		for (Object obj : unitCompartment.getChildren()) {
			View view = (View) obj;
			if (view.getElement() == target && String.valueOf(InvocationEditPart.VISUAL_ID).equals(view.getType())
					&& !exclude.contains(view)) {
				return view;
			}
		}
		return null;
	}

	/*
	 * Get the compartment view of a unit view.
	 */
	public static View getUnitCompartment(View view) {
		// Compartment type ID:
		String type = HenshinVisualIDRegistry.getType(UnitCompartmentEditPart.VISUAL_ID);
		// Check if it is already the compartment:
		if (type.equals(view.getType())) {
			return view;
		}
		// Otherwise search the compartment:
		return ViewUtil.getChildBySemanticHint(view, type);
	}
	
	/** delete the corresponding Mapping to invocation in Unit
	 * @param unit
	 * @param invocation
	 */
	public static void  removeParameterMappingsToInvocation(Unit unit,Unit invocation)
	{
		final List<Parameter>  invocationParams =invocation.getParameters();
		ParameterMapping mapping;
		Iterator<ParameterMapping> mappings = unit.getParameterMappings().iterator();
		while (mappings.hasNext()) {
			mapping = mappings.next();
			Parameter srcParam=mapping.getSource();
			Parameter tarParam=mapping.getTarget();
			if(invocationParams.contains(srcParam)||invocationParams.contains(tarParam))
			{
				mappings.remove();
			}
		}
	}

}
