package org.eclipse.emf.henshin.provider.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Action.Type;

/**
 * Henshin color provider class.
 * @author Christian Krause
 */
public class HenshinColorProvider {

	public static class Color {
		public final int red, green, blue;
		public Color(int red, int green, int blue) {
			this.red = red;
			this.green = green;
			this.blue = blue;
		}
		public URI toURI() {
			return URI.createURI("color://rgb/" + red + "/" + green + "/" + blue);
		}
	}

	public static Color COLOR_BLACK = new Color(0, 0, 0);

	public static Color COLOR_ACTION_PRESERVE = new Color(128, 128, 128); 
	public static Color COLOR_ACTION_CREATE = new Color(0, 200, 0);
	public static Color COLOR_ACTION_DELETE = new Color(255, 0, 0);
	public static Color COLOR_ACTION_FORBID = new Color(0, 0, 255);
	public static Color COLOR_ACTION_REQUIRE = new Color(170, 68, 0);

	// default background color for rules:
	public static Color COLOR_RULE = new Color(215, 225, 245);
	
	// default background color for transformation units:
	public static Color COLOR_TRANSFORMATION_UNIT = new Color(215, 245, 225);

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
