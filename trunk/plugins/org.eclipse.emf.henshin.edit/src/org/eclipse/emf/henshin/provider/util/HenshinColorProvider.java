/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.provider.util;

import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Action.Type;

import static org.eclipse.emf.henshin.provider.util.HenshinColorMode.Color;

/**
 * Henshin color provider class.
 * @author Christian Krause
 */
class HenshinColorProvider {

	public static Color COLOR_BLACK = new Color(0, 0, 0);

	public static Color COLOR_ACTION_PRESERVE = new Color(128, 128, 128); 
	public static Color COLOR_ACTION_CREATE = new Color(0, 200, 0);
	public static Color COLOR_ACTION_DELETE = new Color(255, 0, 0);
	public static Color COLOR_ACTION_FORBID = new Color(0, 0, 255);
	public static Color COLOR_ACTION_REQUIRE = new Color(170, 68, 0);

	// default background color for rules:
	public static Color COLOR_RULE = new Color(215, 225, 245);
	
	// default background color for units:
	public static Color COLOR_UNIT = new Color(215, 245, 225);

	public static Color getActionColor(Action action) {
		if (action==null) {
			return COLOR_BLACK;
		}
		Type type = action.getType();
		switch (type) {
		case PRESERVE: 
			return COLOR_ACTION_PRESERVE;
		case CREATE:
			return COLOR_ACTION_CREATE;
		case DELETE:
			return COLOR_ACTION_DELETE;
		case FORBID:
			return COLOR_ACTION_FORBID;
		case REQUIRE:
			return COLOR_ACTION_REQUIRE;
		default:
			return COLOR_BLACK;
		}
	}
}
