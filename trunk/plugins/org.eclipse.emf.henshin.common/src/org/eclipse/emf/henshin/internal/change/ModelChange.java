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
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class ModelChange {
    private List<ObjectChange> changes = new ArrayList<ObjectChange>();
    private List<EObject> createdObjects = new ArrayList<EObject>();
    private List<EObject> deletedObjects = new ArrayList<EObject>();

    public void addCreatedObject(EObject eObject) {
    	createdObjects.add(eObject);
    }
    
    public void addDeletedObject(EObject eObject) {
    	deletedObjects.add(eObject);
    }
    
    public void addObjectChange( EObject object, EStructuralFeature feature,
            Object newValue ) {
        if ( object != null && feature != null ) {
            boolean changeAdded = false;
            // try to add the modification to an existing ObjectChange
            for ( ObjectChange objectChange : changes ) {
                if ( objectChange.getOwner() == object ) {
                    objectChange.changeFeature( feature, newValue );
                    changeAdded = true;
                    break;
                }
            }

            // the owner of the feature doesn't have a modification
            if ( !changeAdded ) {
                ObjectChange objectChange = new ObjectChange( object );
                objectChange.changeFeature( feature, newValue );
                changes.add( objectChange );
            }
        }
    }

    public void applyChanges() {
        for ( ObjectChange change : changes ) {
            change.execute();
        }
    }

    public void undoChanges() {
        for ( ObjectChange change : changes ) {
            change.undo();
        }
    }

    public void redoChanges() {
        for ( ObjectChange change : changes ) {
            change.execute();
        }
    }

	/**
	 * @return the createdObjects
	 */
	public List<EObject> getCreatedObjects() {
		return createdObjects;
	}

	/**
	 * @return the deletedObjects
	 */
	public List<EObject> getDeletedObjects() {
		return deletedObjects;
	}
}
