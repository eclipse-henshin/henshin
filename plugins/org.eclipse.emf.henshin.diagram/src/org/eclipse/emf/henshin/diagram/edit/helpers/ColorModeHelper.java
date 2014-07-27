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
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.provider.util.HenshinColorMode;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.LineStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;

/**
 * Helper class for accessing color modes.
 * @author Christian Krause
 */
public class ColorModeHelper extends HenshinBaseEditHelper {

	/**
	 * Key for color mode EAnnotations.
	 */
	public static final String COLOR_MODE_EANNOTATION_KEY = "colorMode";
	
	/**
	 * Key for color mode values (in the details of an EAnnotation.
	 */
	public static final String COLOR_MODE_VALUE_KEY = "value";
	
	/**
	 * Get the color mode for a given view. Can return <code>null</code>, but it shouldn't.
	 * @param view The view.
	 * @return Its color mode.
	 */
	public static HenshinColorMode getColorMode(View view) {
		
		// Get the diagram:
		if (view==null) {
			return HenshinColorMode.getDefaultColorMode();
		}
		Diagram diagram = view.getDiagram();
		if (diagram==null) {
			return HenshinColorMode.getDefaultColorMode();
		}
		
		// Get the color mode eAnnotation:
		EAnnotation annotation = diagram.getEAnnotation(COLOR_MODE_EANNOTATION_KEY);
		if (annotation==null) {
			return HenshinColorMode.getDefaultColorMode();
		}
		
		// Get the value:
		String value = annotation.getDetails().get(COLOR_MODE_VALUE_KEY);
		if (value==null) {
			return HenshinColorMode.getDefaultColorMode();
		}
		
		// Find the color mode:
		HenshinColorMode mode = HenshinColorMode.REGISTRY.get(value);
		if (mode!=null) {
			return mode;
		}
		
		// Fall-back:
		return HenshinColorMode.getDefaultColorMode();
		
	}

	/**
	 * Set the color mode for a view. This does not run any commands, but modifies the model directly.
	 * @param view The view.
	 * @param mode The color mode.
	 */
	public static void setColorMode(View view, HenshinColorMode mode) {
		
		// Get the diagram:
		if (view==null) {
			return;
		}
		Diagram diagram = view.getDiagram();
		if (diagram==null) {
			return;
		}

		// Reset if default mode:
		if (mode==HenshinColorMode.getDefaultColorMode() || (mode!=null && mode.getName()==null)) {
			mode = null;
		}
		
		if (mode==null) {
			
			// Remove the color mode EAnnotation:
			EAnnotation annotation = diagram.getEAnnotation(COLOR_MODE_EANNOTATION_KEY);
			if (annotation!=null) {
				diagram.getEAnnotations().remove(annotation);
			}
			
		} else {
			
			// Add or set the EAnnotation
			EAnnotation annotation = diagram.getEAnnotation(COLOR_MODE_EANNOTATION_KEY);
			if (annotation==null) {
				annotation = EcoreFactory.eINSTANCE.createEAnnotation();
				annotation.setSource(COLOR_MODE_EANNOTATION_KEY);
				diagram.getEAnnotations().add(annotation);
			}
			annotation.getDetails().put(COLOR_MODE_VALUE_KEY, mode.getName());
			
		}
		
	}
	
	public static HenshinColorMode.Color getActionColor(View view, boolean foreground) {
		if (view==null) {
			return null;
		}
		if (!(view.getElement() instanceof GraphElement)) {
			return null;
		}
		GraphElement element = (GraphElement) view.getElement();
		Action action = element.getAction();
		if (action==null) {
			return null;
		}
		return getCustomOrDefaultColor(view, HenshinColorMode.getActionColorKey(action, foreground), foreground);
	}
	
	public static HenshinColorMode.Color getColor(View view, String type) {
		if (view==null) {
			return null;
		}
		if (type.startsWith("BG_") || type.startsWith("FG_")) {
			return getCustomOrDefaultColor(view, type, type.startsWith("FG_"));
		}
		HenshinColorMode mode = getColorMode(view);
		if (mode==null) {
			return null;
		}
		return mode.getColor(type);
	}

	private static HenshinColorMode.Color getCustomOrDefaultColor(View view, String type, boolean foreground) {
		
		// Get the custom and the default colors:
		HenshinColorMode.Color customColor = null;
		HenshinColorMode.Color defaultColor = null;
		if (!foreground) {
			customColor = getCustomColor(view, foreground);
		}
		HenshinColorMode mode = getColorMode(view);
		if (mode!=null) {
			defaultColor = mode.getColor(type);
		}
		// Use custom color?
		HenshinColorMode classic = HenshinColorMode.REGISTRY.get("classic");
		if (customColor!=null && classic!=null) {
			int sum = customColor.red + customColor.green + customColor.blue;
			if (sum!=0 && sum!=765 && !customColor.equals(classic.getColor(type))) {
				return customColor;
			}
		}
		// Otherwise the default one:
		return defaultColor;
	}

	
	private static HenshinColorMode.Color getCustomColor(View view, boolean foreground) {
		if (foreground) {
			LineStyle style = (LineStyle) view.getStyle(NotationPackage.Literals.LINE_STYLE);
			if (style != null) {
				Color color = DiagramColorRegistry.getInstance().getColor(Integer.valueOf(style.getLineColor()));
				if (color!=null) {
					return getHenshinColor(color);
				}
			}				
		} else {
			FillStyle style = (FillStyle) view.getStyle(NotationPackage.Literals.FILL_STYLE);
			if (style != null) {
				Color color = DiagramColorRegistry.getInstance().getColor(Integer.valueOf(style.getFillColor()));
				if (color!=null) {
					return getHenshinColor(color);
				}
			}
		}
		return null;
	}
	
	public static Color getSWTColor(HenshinColorMode.Color c) {
		return new Color(null, c.red, c.green, c.blue);
	}

	public static HenshinColorMode.Color getHenshinColor(Color c) {
		return new HenshinColorMode.Color(c.getRed(), c.getGreen(), c.getBlue());
	}

	public static Integer getIntegerColor(HenshinColorMode.Color c) {
		return FigureUtilities.colorToInteger(getSWTColor(c));
	}
	
}
