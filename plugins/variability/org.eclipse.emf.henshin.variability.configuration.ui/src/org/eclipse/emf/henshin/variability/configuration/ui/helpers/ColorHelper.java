package org.eclipse.emf.henshin.variability.configuration.ui.helpers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.graphics.Color;

/**
 * A helper class for retrieving the specific colors used in the Henshin UI.
 * 
 * @author Stefan Schulz
 *
 */
public class ColorHelper {
	private static final Color henshinGreen = new Color(null, 0, 200, 0, 255);  
	private static final Color fadedRed = new Color(null, 255, 180, 180, 255);
	private static final Color fadedGreen = new Color(null, 180, 255, 180, 255);
	private static final Color fadedBlue = new Color(null, 255, 180, 180, 255);
	private static final Color fadedBlack = new Color(null, 180, 180, 180, 255);
	private static final Color fadedGray = new Color(null, 220, 220, 220, 255);

	private static final Map<String, Color> concealColorMap;
	static {
		concealColorMap = new HashMap<String, Color>();
		concealColorMap.put(ColorConstants.black.toString(), fadedBlack);
		concealColorMap.put(ColorConstants.gray.toString(), fadedGray);
		concealColorMap.put(ColorConstants.blue.toString(), fadedBlue);
		concealColorMap.put(ColorConstants.red.toString(), fadedRed);
		concealColorMap.put(henshinGreen.toString(), fadedGreen);
	}

	private static final Map<String, Color> revealColorMap;
	static {
		revealColorMap = new HashMap<String, Color>();
		revealColorMap.put(fadedBlack.toString(), ColorConstants.black);
		revealColorMap.put(fadedGray.toString(), ColorConstants.gray);
		revealColorMap.put(fadedBlue.toString(), ColorConstants.blue);
		revealColorMap.put(fadedRed.toString(), ColorConstants.red);
		revealColorMap.put(fadedGreen.toString(), henshinGreen);
	}
	
	public static final Color getConcealColor(Color color) {
		Color newColor = concealColorMap.get(color.toString());
		System.out.println(color + " -> " + newColor);
		return newColor;
	}
	
	public static final Color getRevealColor(Color color) {
		Color newColor = revealColorMap.get(color.toString());
		System.out.println(color + " -> " + newColor);
		return newColor;
	}
}
