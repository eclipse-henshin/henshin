package org.eclipse.emf.henshin.statespace.modelchecking;

import java.util.ArrayList;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;

/**
 * A registry implementation for model checkers.
 * 
 * @generated NOT
 * @author Christian Krause
 */
public class ModelCheckerRegistry extends ArrayList<ModelCheckerDescription> {
	
	// Default instance:
	public static final ModelCheckerRegistry INSTANCE = new ModelCheckerRegistry();
	
	// Extension point Id for model checkers:
	public static final String EXTENSION_POINT_ID = "org.eclipse.emf.henshin.statespace.modelCheckers";
	
	// Default serial Id:
	private static final long serialVersionUID = 1L;
	
	// Automatically initialize the registry:
	static {
		INSTANCE.loadFromPlatform();
	}
	
	/*
	 * Load the registry from the platform.
	 */
	private void loadFromPlatform() {
		
		// Get registered model checkers.
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT_ID);
		IConfigurationElement[] elements = extensionPoint.getConfigurationElements();
		
		// Register the model checkers.
		for (int i=0;i<elements.length; i++) {
			if ("modelChecker".equals(elements[i].getName())) {
				try {
					ModelChecker implementation = (ModelChecker) elements[i].createExecutableExtension("class");
					ModelCheckerDescription description = new ModelCheckerDescription(
																elements[i].getAttribute("id"),
																elements[i].getAttribute("name"),
																implementation);
					add(description);
				}
				catch (Exception e) {
					StateSpacePlugin.INSTANCE.logError("Error loading model checker", e);
				}
			}
		}
	}
	
	/**
	 * Find a model checker description based on its Id.
	 * @param id Id of the model checker.
	 * @return The model checker description if found.
	 */
	public ModelCheckerDescription get(String id) {
		for (ModelCheckerDescription description : this) {
			if (id.equals(description.getId())) return description;
		}
		return null;
	}
	
}
