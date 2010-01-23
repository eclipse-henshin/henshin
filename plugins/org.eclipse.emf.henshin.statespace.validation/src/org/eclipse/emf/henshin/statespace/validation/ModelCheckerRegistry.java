package org.eclipse.emf.henshin.statespace.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;

/**
 * A registry implementation for model checkers.
 * 
 * @author Christian Krause
 */
public class ModelCheckerRegistry {

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
	
	/**
	 * Wrapper class for model checkers that provides
	 * additional metadata, such as a name and an Id.
	 */
	public static class Entry {
		
		// ID and name of the model checker:
		private String id, name;
		
		// Implementation:
		private ModelChecker implementation;
		
		public Entry(String id, String name, ModelChecker implementation) {
			this.id = id;
			this.name = name;
			this.implementation = implementation;
		}
		
		public String getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public ModelChecker getImplementation() {
			return implementation;
		}
		
	}
	
	/**
	 * Registered model checker entries.
	 */
	private List<Entry> entries = new ArrayList<Entry>();
	
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
					entries.add(new Entry(elements[i].getAttribute("id"),
											elements[i].getAttribute("name"),
											implementation));
				}
				catch (Exception e) {
					StateSpacePlugin.INSTANCE.logError("Error loading model checker", e);
				}
			}
		}
	}
	
	/**
	 * Get the list of registered model checker entries.
	 * @return List of registry entries.
	 */
	public List<Entry> getEntries() {
		return entries;
	}
	
	/**
	 * Find a registry entry based on its Id.
	 * @param id Id of the model checker.
	 * @return The registry entry if found.
	 */
	public Entry findEntry(String id) {
		for (Entry entry : entries) {
			if (id.equals(entry.getId())) return entry;
		}
		return null;
	}
	
}
