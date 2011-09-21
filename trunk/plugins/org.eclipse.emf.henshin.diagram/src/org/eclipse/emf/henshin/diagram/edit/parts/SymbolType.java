package org.eclipse.emf.henshin.diagram.edit.parts;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Enum for symbol types.
 * @author Christian Krause
 */
public enum SymbolType {
	
	UNIT_BEGIN,
	UNIT_END;
	
	// Keys to be used internally to get/set view annotations.
	private static String SOURCE = "symbol";
	private static String KEY = "type";
	
	/**
	 * Get the symbol type from a given view.
	 * This looks for a special annotation in the view.
	 * @param view The view, should be of type {@value SymbolEditPart#VISUAL_ID}.
	 * @return The symbol type or <code>null</code>.
	 */
	public static SymbolType get(View view) {
		EAnnotation annotation = view.getEAnnotation(SOURCE);
		if (annotation==null || annotation.getDetails().get(KEY)==null) {
			return null;
		}
		try {
			return valueOf(annotation.getDetails().get(KEY));
		} catch (Throwable t) {
			return null;
		}
	}
	
	/**
	 * Set the symbol type for a given view.
	 * This sets a special annotation in the view.
	 * @param view The view, should be of type {@value SymbolEditPart#VISUAL_ID}.
	 */
	public void set(View view) {
		EAnnotation annotation = view.getEAnnotation(SOURCE);
		if (annotation==null) {
			annotation = EcoreFactory.eINSTANCE.createEAnnotation();
			annotation.setSource(SOURCE);
			view.getEAnnotations().add(annotation);
		}
		annotation.getDetails().put(KEY, toString());
	}
	
}
