package org.eclipse.emf.henshin.diagram.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.diagram.edit.parts.InvocationEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.LinkEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.SymbolType;
import org.eclipse.emf.henshin.diagram.providers.HenshinViewProvider;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Edge;
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
		
		// Get the compartment view and the transformation unit:
		View compartment = symbolHelper.getUnitCompartment(unitView);
		TransformationUnit unit = (TransformationUnit) ((View) compartment.eContainer()).getElement();
		
		// Check the unit type:
		if (unit instanceof SequentialUnit) {
			SequentialUnit seq = (SequentialUnit) unit;
			
			// Update the links:
			View begin = getSymbol(unit, compartment, SymbolType.SEQUENTIAL_BEGIN);
			View end = getSymbol(unit, compartment, SymbolType.SEQUENTIAL_END);
			if (seq.getSubUnits().isEmpty()) {
				updateLink(seq, begin, end);
			} else {
				updateLink(seq, begin, getInvocation(compartment, seq.getSubUnits().get(0)));
				updateLink(seq, getInvocation(compartment, seq.getSubUnits().get(seq.getSubUnits().size()-1)), end);
				for (int i=1; i<seq.getSubUnits().size(); i++) {
					updateLink(seq, 
							getInvocation(compartment, seq.getSubUnits().get(i-1)),
							getInvocation(compartment, seq.getSubUnits().get(i)));					
				}
				
			}
		}
	}
	
	/*
	 * Make sure there exists exactly one link between two nodes.
	 */
	private void updateLink(TransformationUnit unit, View source, View target) {
		if (source!=null && target!=null) {
			List<Edge> links = getLinks(source, target);
			while (links.size()>1) {
				ViewUtil.destroy(links.remove(links.size()-1));
			}
			if (links.isEmpty()) {
				Edge link = provider.createLink_4002(source.getDiagram(), -1, persisted, prefHint);
				link.setElement(unit);
				link.setSource(source);
				link.setTarget(target);
			}
		}
	}
	
	private List<Edge> getLinks(View source, View target) {
		List<Edge> links = new ArrayList<Edge>();
		String visualType = HenshinVisualIDRegistry.getType(LinkEditPart.VISUAL_ID);
		if (source!=null && target!=null) {
			for (Object obj : source.getDiagram().getEdges()) {
				Edge edge = (Edge) obj;
				if (visualType.equals(edge.getType()) 
						&& source==edge.getSource()
						&& target==edge.getTarget()) {
					links.add(edge);
				}
			}
		}
		return links;
	}
	
	private View getSymbol(TransformationUnit unit, View compartment, SymbolType type) {
		if (unit==null || compartment==null || type==null) {
			return null;
		}
		List<View> symbols = symbolHelper.getSymbols(unit, compartment, type);
		return (symbols.isEmpty()) ? null : symbols.get(0);
	}
	
	private View getInvocation(View compartment, TransformationUnit target) {
		if (compartment==null || target==null) {
			return null;
		}
		String visualType = HenshinVisualIDRegistry.getType(InvocationEditPart.VISUAL_ID);
		return ViewUtil.getChildBySemanticHint(compartment, visualType);
	}
	
}
