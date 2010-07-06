/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.internal.change;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class ModelChange {
	private Map<EObject, ObjectChange> changes;
	
	private Collection<EObject> createdObjects = new ArrayList<EObject>();
	private Collection<EObject> deletedObjects = new ArrayList<EObject>();

	public ModelChange() {
		changes = new HashMap<EObject, ObjectChange>();
		
		createdObjects = new ArrayList<EObject>();
		deletedObjects = new ArrayList<EObject>();
	}
	
	public void addCreatedObject(EObject eObject) {
		createdObjects.add(eObject);
	}

	public void addDeletedObject(EObject eObject) {
		deletedObjects.add(eObject);
	}

	public void addObjectChange(EObject eObject, EStructuralFeature feature,
			Object value, boolean deletion) {
		if (eObject != null && feature != null) {
			ObjectChange objectChange = changes.get(eObject);
			
			if (objectChange == null) {
				objectChange = new ObjectChange(eObject);
				objectChange.changeFeature(feature, value, deletion);
				changes.put(eObject, objectChange);
			}
		}
	}

	public void applyChanges() {
		for (ObjectChange change : changes.values()) {
			change.execute();
		}
	}

	public void undoChanges() {
		for (ObjectChange change : changes.values()) {
			change.undo();
		}
	}

	public void redoChanges() {
		for (ObjectChange change : changes.values()) {
			change.execute();
		}
	}

	/**
	 * @return the createdObjects
	 */
	public Collection<EObject> getCreatedObjects() {
		return createdObjects;
	}

	/**
	 * @return the deletedObjects
	 */
	public Collection<EObject> getDeletedObjects() {
		return deletedObjects;
	}
}
