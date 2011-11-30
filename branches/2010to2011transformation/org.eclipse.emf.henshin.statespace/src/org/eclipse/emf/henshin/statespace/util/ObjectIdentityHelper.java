package org.eclipse.emf.henshin.statespace.util;

import org.eclipse.emf.ecore.EClass;

/**
 * Helper class for dealing with object identities.
 * @author Christian Krause
 */
public class ObjectIdentityHelper {
	
	/**
	 * Extract the object type of an object identity. 
	 */
	public static EClass getObjectType(int objectIdentity, EClass[] objectTypes) {
		return objectTypes[(objectIdentity >>> 24) & 0xFF];
	}

	/**
	 * Extract the object type prefix of an object identity. 
	 */
	public static String getObjectTypePrefix(int objectIdentity, String[] objectTypePrefixes) {
		return objectTypePrefixes[(objectIdentity >>> 24) & 0xFF];
	}

	/**
	 * Extract the object ID of an object identity. 
	 */
	public static int getObjectID(int objectIdentity) {
		return objectIdentity & 0xFFFFFF;
	}
	
	/**
	 * Extract the object type of an object identity. 
	 */
	public static int createObjectIdentitity(EClass type, int id, EClass[] objectTypes) {
		if (objectTypes==null) {
			throw new NullPointerException("Missing object types");
		}
		int typeId = -1;
		for (int i=0; i<objectTypes.length; i++) {
			if (objectTypes[i]==type) {
				typeId = i;
				break;
			}
		}
		if (typeId==-1) {
			throw new RuntimeException("Object type " + type.getName() + " not supported");
		}
		return ((typeId & 0xFF) << 24) | (id & 0xFFFFFF);
	}
	
}
