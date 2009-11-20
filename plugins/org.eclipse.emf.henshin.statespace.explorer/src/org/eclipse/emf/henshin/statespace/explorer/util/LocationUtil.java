package org.eclipse.emf.henshin.statespace.explorer.util;

import java.util.Arrays;

/**
 * @author Christian Krause
 */
public class LocationUtil {
	
	public static int getX(int[] location) {
		return (location!=null && location.length>0) ? location[0] : 0;
	}
	
	public static int getY(int[] location) {
		return (location!=null && location.length>1) ? location[1] : 0;
	}
	
	public static int getZ(int[] location) {
		return (location!=null && location.length>2) ? location[2] : 0;
	}

	public static int[] getLocation(int... coordinates) {
		return coordinates;
	}

	public static int[] getMoved(int[] location, int... delta) {
		if (location==null) {
			return Arrays.copyOf(delta, delta.length);
		}
		location = Arrays.copyOf(location, delta.length);
		for (int i=0; i<location.length; i++) {
			location[i] += delta[i];
		}
		return location;
	}
	
	
}
