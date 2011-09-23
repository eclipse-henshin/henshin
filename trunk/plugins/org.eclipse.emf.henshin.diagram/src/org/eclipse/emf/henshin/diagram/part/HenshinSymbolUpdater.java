package org.eclipse.emf.henshin.diagram.part;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.diagram.edit.parts.SymbolEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.SymbolType;
import org.eclipse.emf.henshin.diagram.edit.parts.UnitCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.providers.HenshinViewProvider;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Update methods for symbol views.
 * @see SymbolEditPart
 * @author Christian Krause
 */
public class HenshinSymbolUpdater {

	// Preferences hint:
	private PreferencesHint prefHint;
	
	// Persisted flag:
	private boolean persisted;
	
	// View provider:
	private HenshinViewProvider provider;
	
	/**
	 * Default constructor.
	 * @param prefHint preferences hint.
	 * @param persisted persisted flag.
	 */
	public HenshinSymbolUpdater(PreferencesHint prefHint, boolean persisted) {
		this.prefHint = prefHint;
		this.persisted = persisted;
		this.provider = new HenshinViewProvider();
	}
	
	public void update(View unitView) {
		
		// Get the compartment view, the transformation unit and the subUnits:
		View compartment = getUnitCompartment(unitView);
		TransformationUnit unit = (TransformationUnit) ((View) compartment.eContainer()).getElement();
		EList<TransformationUnit> subUnits = unit.getSubUnits(false);
		
		// Remember the known symbols:
		Set<View> knownSymbols = new HashSet<View>();
		
		// Always ensure that there are start and end symbols:
		knownSymbols.add(ensureSingleSymbol(unit, compartment, SymbolType.UNIT_BEGIN, 15, 15));
		knownSymbols.add(ensureSingleSymbol(unit, compartment, SymbolType.UNIT_END, 205, 15));
		
		// For IndependentUnits with more than 1 subUnits, we need a symbol for denoting the choice:
		if (unit instanceof IndependentUnit && subUnits.size()>1) {
			knownSymbols.add(ensureSingleSymbol(unit, compartment, SymbolType.INDEPENDENT_CHOICE, 50, 15));
		}
		
		// Delete all unknown symbols:
		deleteUnknownSymbols(unit, compartment, knownSymbols);
		
	}
	
	/*
	 * Make sure there exists exactly one symbol of the given type.
	 */
	public View ensureSingleSymbol(TransformationUnit unit, View compartment, SymbolType type, int x, int y) {
		List<View> symbols = getSymbols(unit, compartment, type);
		while (symbols.size()>1) {
			ViewUtil.destroy(symbols.remove(symbols.size()-1));
		}
		if (symbols.isEmpty()) {
			return createSymbol(unit, compartment, -1, type, x, y);
		} else {
			return symbols.get(0);
		}
	}
	
	/*
	 * Delete all unknown symbols in a unit compartment.
	 */
	private void deleteUnknownSymbols(TransformationUnit unit, View compartment, Collection<View> knownSymbols) {
		List<View> allSymbols = getSymbols(unit, compartment, null);
		for (View symbol : allSymbols) {
			if (!knownSymbols.contains(symbol)) {
				ViewUtil.destroy(symbol);
			}
		}
	}
	
	/*
	 * Get the symbol views in a compartment view.
	 */
	public List<View> getSymbols(TransformationUnit unit, View compartment, SymbolType type) {
		List<View> result = new ArrayList<View>();
		String visualType = HenshinVisualIDRegistry.getType(SymbolEditPart.VISUAL_ID);
		for (Object child : compartment.getChildren()) {
			View view = (View) child;
			if (visualType.equals(view.getType())) {
				if (type==null || SymbolType.get(view)==type) {
					result.add(view);
				}
			}
		}
		return result;
	}
	
	/*
	 * Private helper method which creates a symbol view in a unit compartment.
	 */
	public Node createSymbol(TransformationUnit unit, View compartment, int index, SymbolType type, int x, int y) {

		// Create a location object:
		Location location = NotationFactory.eINSTANCE.createLocation();
		location.setX(x);
		location.setY(y);
		
		// Create the symbol:
		Node symbol = provider.createNode_3004(unit,
				compartment, index, persisted, prefHint);
		type.set(symbol);
		symbol.setLayoutConstraint(location);
		return symbol;
	}
	
	/*
	 * Get the compartment view of a unit view.
	 */
	public View getUnitCompartment(View view) {
		
		// Compartment type ID:
		String type = HenshinVisualIDRegistry
				.getType(UnitCompartmentEditPart.VISUAL_ID);
		
		// Check if it is already the compartment:
		if (type.equals(view.getType())) {
			return view;
		}
		
		// Otherwise search the compartment:
		return ViewUtil.getChildBySemanticHint(view, type);
	}

}
