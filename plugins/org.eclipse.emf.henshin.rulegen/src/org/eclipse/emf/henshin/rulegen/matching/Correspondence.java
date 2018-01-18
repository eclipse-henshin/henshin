package org.eclipse.emf.henshin.rulegen.matching;

import org.eclipse.emf.ecore.EObject;

/**
 * Data class representing a pair of corresponding model elements (EObjects) of two models called A and B. Typically,
 * model A is the original model while B is the changed model.
 * 
 * @author Timo Kehrer
 */
public class Correspondence {

	private EObject objA;
	private EObject objB;

	public Correspondence(EObject objA, EObject objB) {
		super();
		this.objA = objA;
		this.objB = objB;
	}

	public EObject getObjA() {
		return objA;
	}

	public EObject getObjB() {
		return objB;
	}

}
