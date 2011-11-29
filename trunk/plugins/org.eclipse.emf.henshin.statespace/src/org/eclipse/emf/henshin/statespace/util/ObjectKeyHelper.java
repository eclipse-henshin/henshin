package org.eclipse.emf.henshin.statespace.util;

import org.eclipse.emf.ecore.EClass;

/**
 * Helper class for dealing with object keys.
 * @author Christian Krause
 */
public class ObjectKeyHelper {
	
	/**
	 * Extract the object type of an object key. 
	 */
	public static EClass getObjectType(int objectKey, EClass[] supportedTypes) {
		int index = ((objectKey >>> 24) & 0xFF)-1;
		if (index>=0 && index<supportedTypes.length) {
			return supportedTypes[index];			
		}
		return null;
	}

	/**
	 * Extract the supported type prefix of an object key. 
	 */
	public static String getObjectTypePrefix(int objectKey, String[] supportedTypePrefixes) {
		int index = ((objectKey >>> 24) & 0xFF)-1;
		if (index>=0 && index<supportedTypePrefixes.length) {
			return supportedTypePrefixes[index];
		}
		return null;
	}

	/**
	 * Extract the object ID of an object identity. 
	 */
	public static int getObjectID(int objectKey) {
		return objectKey & 0xFFFFFF;
	}
	
	/**
	 * Extract the object type of an object key. 
	 */
	public static int createObjectKey(EClass type, int id, EClass[] supportedTypes) {
		
		// Should not be null:
		if (supportedTypes==null) {
			throw new NullPointerException("Missing object types");
		}
		
		// Find out the type id:
		int typeId = 0;
		for (int i=0; i<supportedTypes.length; i++) {
			if (supportedTypes[i]==type) {
				typeId = i+1;
				break;
			}
		}
		
		// If the type is unknown, the id also does not matter:
		if (typeId==0) {
			id = 0;
		}
		
		// Compose everything:
		return ((typeId & 0xFF) << 24) | (id & 0xFFFFFF);
	}
	
}
