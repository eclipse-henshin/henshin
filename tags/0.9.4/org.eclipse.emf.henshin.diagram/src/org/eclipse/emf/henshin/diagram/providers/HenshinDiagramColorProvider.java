/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.providers;

import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.provider.util.HenshinColorProvider;
import org.eclipse.swt.graphics.Color;

/**
 * Static action color provider class.
 * @author Christian Krause
 */
public class HenshinDiagramColorProvider {
	
	public static final Color COLOR_RULE = convert(HenshinColorProvider.COLOR_RULE);
	public static final Color COLOR_TRANSFORMATION_UNIT = convert(HenshinColorProvider.COLOR_TRANSFORMATION_UNIT);
	
	public static Color getActionColor(Action action) {
		return convert(HenshinColorProvider.getActionColor(action));
	}
	
	public static Color convert(HenshinColorProvider.Color c) {
		return new Color(null, c.red, c.green, c.blue);		
	}
	
}
