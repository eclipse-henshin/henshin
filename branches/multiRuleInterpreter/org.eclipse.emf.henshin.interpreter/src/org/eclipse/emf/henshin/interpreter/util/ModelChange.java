/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.ChangeFactory;
import org.eclipse.emf.ecore.change.ChangeKind;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.emf.ecore.change.ListChange;

public class ModelChange {
	private ChangeDescription change;
	
	private Collection<EObject> createdObjects = new ArrayList<EObject>();
	private Collection<EObject> deletedObjects = new ArrayList<EObject>();
	
	public ModelChange() {
		change = ChangeFactory.eINSTANCE.createChangeDescription();
		
		createdObjects = new ArrayList<EObject>();
		deletedObjects = new ArrayList<EObject>();
	}
	
	public void addCreatedObject(EObject eObject) {
		createdObjects.add(eObject);
	}
	
	public void addDeletedObject(EObject eObject) {
		deletedObjects.add(eObject);
	}
	
	private FeatureChange getFeatureChange(EObject eObject, EStructuralFeature feature) {
		if (change.getObjectChanges().get(eObject) == null) {
			change.getObjectChanges().put(eObject, new BasicEList<FeatureChange>());
		}
		EList<FeatureChange> featureChanges = change.getObjectChanges().get(eObject);
		
		for (FeatureChange existingChange : featureChanges) {
			if (existingChange.getFeature() == feature) return existingChange;
		}
		
		FeatureChange featureChange = ChangeFactory.eINSTANCE.createFeatureChange();
		featureChange.setFeature(feature);
		featureChanges.add(featureChange);
		
		return featureChange;
	}
	
	/**
	 * This method ensures, that only one list change per ChangeKind ADD,
	 * REMOVE, MOVE is created
	 */
	@SuppressWarnings("unused")
	private ListChange getListChangeByKind(FeatureChange featureChange, ChangeKind kind) {
		for (ListChange existingChange : featureChange.getListChanges()) {
			if (existingChange.getKind() == kind) return existingChange;
		}
		
		ListChange listChange = ChangeFactory.eINSTANCE.createListChange();
		listChange.setKind(kind);
		
		featureChange.getListChanges().add(listChange);
		return listChange;
	}
	
	private boolean deletingChangeExists(FeatureChange featureChange, EObject value) {
		for (ListChange existingChange : featureChange.getListChanges()) {
			if (existingChange.getKind() == ChangeKind.REMOVE_LITERAL)
				return existingChange.getReferenceValues().contains(value);
			
		}
		return false;
	}
	
	public void addReferenceChange(EObject eObject, EReference reference, EObject value,
			boolean deletion) {
		FeatureChange featureChange = getFeatureChange(eObject, reference);
		
		if (reference.isMany()) {
			if (deletingChangeExists(featureChange, value)) return;
			
			ListChange listChange = ChangeFactory.eINSTANCE.createListChange();
			listChange.setKind(deletion ? ChangeKind.REMOVE_LITERAL : ChangeKind.ADD_LITERAL);
			listChange.getReferenceValues().add(value);
			
			// TODO: This is a bugfix and should be considered as workaround.
			if (deletion) {
				@SuppressWarnings("unchecked")
				int idx = ((List<Object>) eObject.eGet(reference)).indexOf(value);
				for (ListChange lChange : featureChange.getListChanges()) {
					if (lChange.getKind() == ChangeKind.ADD_LITERAL) {
						if (lChange.getIndex() < idx) idx++;
					} else if (lChange.getKind() == ChangeKind.REMOVE_LITERAL) {
						if (lChange.getIndex() < idx) idx--;
					} else if (lChange.getKind() == ChangeKind.MOVE_LITERAL) {
						if (lChange.getIndex() < idx && lChange.getMoveToIndex() > idx) idx--;
						if (lChange.getIndex() > idx && lChange.getMoveToIndex() < idx) idx++;
					}
				}
				listChange.setIndex(idx);
			}
			// Bugfix end
			
			featureChange.getListChanges().add(listChange);
		} else {
			if (deletion)
				featureChange.setReferenceValue(null);
			else
				featureChange.setReferenceValue(value);
		}
	}
	
	public void addAttributeChange(EObject eObject, EAttribute attribute, String value,
			boolean deletion) {
		FeatureChange featureChange = getFeatureChange(eObject, attribute);
		
		if (attribute.isMany()) {
			ListChange listChange = ChangeFactory.eINSTANCE.createListChange();
			listChange.setFeature(attribute);
			listChange.setKind(deletion ? ChangeKind.REMOVE_LITERAL : ChangeKind.ADD_LITERAL);
			listChange.setIndex(((List<?>) eObject.eGet(attribute)).indexOf(value));
			if (!deletion) {
				listChange.getValues().add(value);
			}
			featureChange.getListChanges().add(listChange);
		} else {
			featureChange.setDataValue(value);
		}
	}
	
	public void applyChanges() {
		change.applyAndReverse();
		// for (ObjectChange change : changes.values()) {
		// change.execute();
		// }
	}
	
	public void undoChanges() {
		change.applyAndReverse();
		// for (ObjectChange change : changes.values()) {
		// change.undo();
		// }
	}
	
	public void redoChanges() {
		change.applyAndReverse();
		// for (ObjectChange change : changes.values()) {
		// change.execute();
		// }
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
