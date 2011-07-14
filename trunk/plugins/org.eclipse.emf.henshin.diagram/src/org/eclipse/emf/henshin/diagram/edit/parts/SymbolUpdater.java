package org.eclipse.emf.henshin.diagram.edit.parts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.diagram.part.HenshinDiagramEditorPlugin;
import org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry;
import org.eclipse.emf.henshin.diagram.providers.HenshinViewProvider;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Helper methods for updating symbol views.
 * @author Christian Krause
 */
public class SymbolUpdater {
	
	// View provider:
	private static final HenshinViewProvider VIEW_PROVIDER = new HenshinViewProvider();
	
	/**
	 * Update the symbols in a unit view.
	 * This creates missing or deletes extra symbols.
	 * @param unitView The unit view.
	 */
	public static void update(View unitView) {		
		EObject unit = unitView.getElement();
		if (unit instanceof SequentialUnit) {
			updateSequentialUnit(unitView);
		}
	}

	/**
	 * Update a sequential unit view.
	 * @param unitView Unit view.
	 */
	private static void updateSequentialUnit(View unitView) {
		
		// Get the sequential unit and the compartment view:
		SequentialUnit unit = (SequentialUnit) unitView.getElement();
		View compartment = getUnitCompartment(unitView);
		
		// Check if the begin symbol exists:
		if (SymbolType.SEQUENTIAL_BEGIN.findSymbolView(compartment, unit)==null) {
			createSymbol(unit, compartment, SymbolType.SEQUENTIAL_BEGIN, 15, 15);
		}

		// Check if the end symbol exists:
		if (SymbolType.SEQUENTIAL_END.findSymbolView(compartment, unit)==null) {
			createSymbol(unit, compartment, SymbolType.SEQUENTIAL_END, 75, 15);
		}

	}


	/**
	 * Private helper method which creates a symbol view in a unit compartment.
	 * @param compartment The compartment.
	 * @param type Symbol type.
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	private static Node createSymbol(TransformationUnit unit, View compartment, SymbolType type, int x, int y) {

		// Create a location object:
		Location location = NotationFactory.eINSTANCE.createLocation();
		location.setX(x);
		location.setY(y);

		// Create the symbol:
		Node symbol = VIEW_PROVIDER.createNode_3003(unit, compartment, -1,
				false, HenshinDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
		type.set(symbol);
		symbol.setLayoutConstraint(location);
		return symbol;
		
	}
	
	/**
	 * Get the compartment view of a unit view.
	 * @param view The unit view.
	 * @return Compartment view.
	 */
	public static View getUnitCompartment(View view) {
		
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
