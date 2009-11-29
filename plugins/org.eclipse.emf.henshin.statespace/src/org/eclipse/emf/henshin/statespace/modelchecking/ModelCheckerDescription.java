package org.eclipse.emf.henshin.statespace.modelchecking;

/**
 * Wrapper class for model checkers that provides
 * additional metadata, such as a name and an Id.
 * 
 * @author Christian Krause
 * @generated NOT
 */
public class ModelCheckerDescription {
	
	// ID and name of the model checker:
	private String id, name;
	
	// Implementation:
	private ModelChecker implementation;
	
	public ModelCheckerDescription(String id, String name, ModelChecker implementation) {
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
