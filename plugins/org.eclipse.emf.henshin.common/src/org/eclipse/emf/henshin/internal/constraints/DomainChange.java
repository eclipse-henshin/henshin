package org.eclipse.emf.henshin.internal.constraints;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

public class DomainChange {
	public List<EObject> removedObjects;
	public List<EObject> remainingObjects;
	
	public DomainChange(List<EObject> remainingObjects, List<EObject> removedObjects) {
		this.remainingObjects = remainingObjects;
		this.removedObjects = removedObjects;
	}
}
