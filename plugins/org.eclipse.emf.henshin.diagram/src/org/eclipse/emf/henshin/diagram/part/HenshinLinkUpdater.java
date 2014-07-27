/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.part;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.henshin.diagram.edit.helpers.UnitEditHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.UnitEditHelper.InvocationViewKey;
import org.eclipse.emf.henshin.diagram.edit.parts.LinkEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.SymbolType;
import org.eclipse.emf.henshin.diagram.providers.HenshinViewProvider;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.IteratedUnit;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.RoutingStyle;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Update methods for link views.
 * @see LinkEditPart
 * @author Christian Krause
 */
public class HenshinLinkUpdater {

	// Preferences hint:
	private PreferencesHint prefHint;
	
	// Persisted flag:
	private boolean persisted;
	
	// View provider:
	private HenshinViewProvider provider;
	
	// Symbol helper:
	private HenshinSymbolUpdater symbolHelper;
	
	/**
	 * Default constructor.
	 * @param prefHint preferences hint.
	 * @param persisted persisted flag.
	 */
	public HenshinLinkUpdater(PreferencesHint prefHint, boolean persisted) {
		this.prefHint = prefHint;
		this.persisted = persisted;
		this.provider = new HenshinViewProvider();
		this.symbolHelper = new HenshinSymbolUpdater(prefHint, persisted);
	}
	
	public void update(View unitView) {
		
		// Get the compartment view:
		View compartment = UnitEditHelper.getUnitCompartment(unitView);
		
		// Get the unit:
		Unit unit = (Unit) ((View) compartment.eContainer()).getElement();
		
		// Get the begin and the end symbol:
		View begin = getSymbol(unit, compartment, SymbolType.UNIT_BEGIN);
		View end = getSymbol(unit, compartment, SymbolType.UNIT_END);

		// Get all invocations in the unit view:
		List<View> invocations = UnitEditHelper.getInvocationViews(unitView, false);

		// Known links:
		Set<Edge> knownLinks = new HashSet<Edge>();
		
		// Known nodes:
		Set<View> nodes = new HashSet<View>();
		for (View node : invocations) {
			if (node!=null) nodes.add(node);
		}
		if (begin!=null) nodes.add(begin);
		if (end!=null) nodes.add(end);
		
		/* NOW WE ARE READY TO UPDATE THE LINKS */
		
		// Sequential and priority units:
		if (unit instanceof SequentialUnit ||
			unit instanceof PriorityUnit) {
			
			// Update the links:
			if (invocations.isEmpty()) {
				knownLinks.add(updateLink(unit, begin, end));
			} else {
				int count = invocations.size();
				knownLinks.add(updateLink(unit, begin, invocations.get(0)));
				knownLinks.add(updateLink(unit, invocations.get(count-1), end));
				for (int i=1; i<count; i++) {
					knownLinks.add(updateLink(unit, invocations.get(i-1), invocations.get(i)));
				}
			}
		}
		
		// Independent units:
		if (unit instanceof IndependentUnit) {
			int size = invocations.size();
			
			// Update the links:
			if (size==0) {
				knownLinks.add(updateLink(unit, begin, end));
			}
			else if (size==1) {
				knownLinks.add(updateLink(unit, begin, invocations.get(0)));
				knownLinks.add(updateLink(unit, invocations.get(0), end));
			}
			else {
				View choice = getSymbol(unit, compartment, SymbolType.INDEPENDENT_CHOICE);
				knownLinks.add(updateLink(unit, begin, choice));
				for (View invocation : invocations) {
					knownLinks.add(updateLink(unit, choice, invocation));
					knownLinks.add(updateLink(unit, invocation, end));
				}
				nodes.add(choice);
			}
		}
		
		// Conditional units:
		if (unit instanceof ConditionalUnit) {
			View ifView = UnitEditHelper.getInvocationView(unitView, InvocationViewKey.IF);
			View thenView = UnitEditHelper.getInvocationView(unitView, InvocationViewKey.THEN);
			View elseView = UnitEditHelper.getInvocationView(unitView, InvocationViewKey.ELSE);
			if (ifView!=null) {
				knownLinks.add(updateLink(unit, begin, ifView));
				if (thenView!=null) {
					knownLinks.add(updateLink(unit, ifView, thenView));
					knownLinks.add(updateLink(unit, thenView, end));
				}
				if (elseView!=null) {
					knownLinks.add(updateLink(unit, ifView, elseView));
					knownLinks.add(updateLink(unit, elseView, end));
				}
			}
		}
		
		// Loop and Iterated units:
		if (unit instanceof LoopUnit || unit instanceof IteratedUnit) {
			InvocationViewKey key = (unit instanceof LoopUnit) ? InvocationViewKey.LOOP : InvocationViewKey.ITERATE;
			View subUnitView = UnitEditHelper.getInvocationView(unitView, key);
			if (subUnitView!=null) {
				knownLinks.add(updateLink(unit, begin, subUnitView));
				knownLinks.add(updateLink(unit, subUnitView, end));
				knownLinks.add(updateLink(unit, subUnitView, subUnitView));
			} else {
				knownLinks.add(updateLink(unit, begin, end));
			}
		}
				
		// Delete unknown links:
		deleteUnknownLinks(unitView.getDiagram(), knownLinks, nodes);

	}
	
	/*
	 * Make sure there exists exactly one link between two nodes.
	 */
	private Edge updateLink(Unit unit, View source, View target) {
		if (source!=null && target!=null) {
			List<Edge> links = getLinks(source, target);
			if (links.isEmpty()) {
				Edge link = provider.createLink_4002(source.getDiagram(), -1, persisted, prefHint);
				RoutingStyle routingStyle = (RoutingStyle) link.getStyle(NotationPackage.eINSTANCE.getRoutingStyle());
				routingStyle.setRouting(Routing.RECTILINEAR_LITERAL);
				link.setElement(unit);
				link.setSource(source);
				link.setTarget(target);
				return link;
			} else {
				while (links.size()>1) {
					ViewUtil.destroy(links.remove(links.size()-1));
				}
				return links.get(0);
			}
		}
		return DUMMY_EDGE;
	}

	private void deleteUnknownLinks(Diagram diagram, Collection<Edge> knownLinks, Collection<View> nodes) {
		for (Object link : diagram.getEdges().toArray()) {
			if (knownLinks.contains(link)) continue;
			if (nodes.contains(((Edge) link).getSource()) && 
				nodes.contains(((Edge) link).getTarget())) {
				ViewUtil.destroy((Edge) link);
			}
		}
	}

	private List<Edge> getLinks(View source, View target) {
		List<Edge> links = new ArrayList<Edge>();
		if (source!=null && target!=null) {
			for (Object obj : source.getDiagram().getEdges()) {
				Edge edge = (Edge) obj;
				if (LINK_VISUAL_ID.equals(edge.getType()) 
						&& source==edge.getSource()
						&& target==edge.getTarget()) {
					links.add(edge);
				}
			}
		}
		return links;
	}
	
	private View getSymbol(Unit unit, View compartment, SymbolType type) {
		if (unit==null || compartment==null || type==null) {
			return null;
		}
		List<View> symbols = symbolHelper.getSymbols(unit, compartment, type);
		return (symbols.isEmpty()) ? null : symbols.get(0);
	}


	/**
	 * Check whether a view is an If-link in a ConditionalUnit.
	 * @param unit Unit.
	 * @param link The link view (should be an edge).
	 * @return <code>true</code> if it is an if-link.
	 */
	public static boolean isIfLink(Unit unit, View link) {
		if (unit instanceof ConditionalUnit && link instanceof Edge) {
			View target = ((Edge) link).getTarget();
			if (target!=null) {
				View unitView = (View) target.eContainer().eContainer();
				View ifView = UnitEditHelper.getInvocationView(unitView, InvocationViewKey.IF);
				return (target==ifView);
			}
		}
		return false;
	}

	/**
	 * Check whether a view is an Then-link in a ConditionalUnit.
	 * @param unit Unit.
	 * @param link The link view (should be an edge).
	 * @return <code>true</code> if it is an then-link.
	 */
	public static boolean isThenLink(Unit unit, View link) {
		if (unit instanceof ConditionalUnit && link instanceof Edge) {
			View source = ((Edge) link).getSource();
			View target = ((Edge) link).getTarget();
			if (source!=null && target!=null) {
				View unitView = (View) source.eContainer().eContainer();
				View ifView = UnitEditHelper.getInvocationView(unitView, InvocationViewKey.IF);
				View thenView = UnitEditHelper.getInvocationView(unitView, InvocationViewKey.THEN);
				return (source==ifView && target==thenView);
			}
		}
		return false;
	}

	/**
	 * Check whether a view is an Else-link in a ConditionalUnit.
	 * @param unit Unit.
	 * @param link The link view (should be an edge).
	 * @return <code>true</code> if it is an else-link.
	 */
	public static boolean isElseLink(Unit unit, View link) {
		if (unit instanceof ConditionalUnit && link instanceof Edge) {
			View source = ((Edge) link).getSource();
			View target = ((Edge) link).getTarget();
			if (source!=null && target!=null) {
				View unitView = (View) source.eContainer().eContainer();
				View ifView = UnitEditHelper.getInvocationView(unitView, InvocationViewKey.IF);
				View elseView = UnitEditHelper.getInvocationView(unitView, InvocationViewKey.ELSE);
				return (source==ifView && target==elseView);
			}
		}
		return false;
	}
	
	private static final String LINK_VISUAL_ID = HenshinVisualIDRegistry.getType(LinkEditPart.VISUAL_ID);	
	private static final Edge DUMMY_EDGE = NotationFactory.eINSTANCE.createEdge();
	
}
