package org.eclipse.emf.henshin.diagram.providers;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.henshin.model.actions.Action;
import org.eclipse.emf.henshin.model.actions.ActionType;
import org.eclipse.swt.graphics.Color;

/**
 * Static action color provider class.
 * @author Christian Krause
 */
public class ActionColorProvider {

	private static Color COLOR_PRESERVE = new Color(null, 128, 128, 128); 
	private static Color COLOR_CREATE = new Color(null, 0, 200, 0);
	private static Color COLOR_DELETE = new Color(null, 255, 0, 0);
	private static Color COLOR_FORBID = new Color(null, 0, 0, 255);
	private static Color COLOR_REQUIRE = new Color(null, 170, 68, 0);

	public static Color getColor(Action action) {
		ActionType type = action.getType();
		switch (type) {
		case PRESERVE: 
			return COLOR_PRESERVE;
		case CREATE:
			return COLOR_CREATE;
		case DELETE:
			return COLOR_DELETE;
		case FORBID:
			return COLOR_FORBID;
		case REQUIRE:
			return COLOR_REQUIRE;
		default:
			return ColorConstants.black;
		}
	}
}
