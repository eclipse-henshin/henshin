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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Stores changes to a many-reference.
 */
public class ManyFeatureChange implements FeatureChange {
	private Collection<Object> valueList;
	
	private Collection<Object> removedElements;
	private Collection<Object> addedElements;

	@SuppressWarnings("unchecked")
	public ManyFeatureChange(EObject owner, EStructuralFeature feature) {
		valueList = (List<Object>) owner.eGet(feature);
		
		removedElements = new HashSet<Object>();
		addedElements = new HashSet<Object>();
	}

	@Override
	public void addValue(Object value) {
		addedElements.add(value);
		
	}

	@Override
	public void removeValue(Object value) {
		// TODO Auto-generated method stub
		removedElements.add(value);
	}
	
	public void execute() {
		for (Object removedObject : removedElements)
			valueList.remove(removedObject);

		for (Object addedObject : addedElements)
			valueList.add(addedObject);
	}

	public void undo() {
		for (Object addedObject : addedElements)
			valueList.remove(addedObject);
		
		for (Object removedObject : removedElements)
			valueList.add(removedObject);
	}
}
