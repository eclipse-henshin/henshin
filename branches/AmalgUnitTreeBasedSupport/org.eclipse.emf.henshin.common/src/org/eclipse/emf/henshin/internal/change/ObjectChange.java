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

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class ObjectChange {
	EObject owner;

	Map<EStructuralFeature, FeatureChange> changedFeatures;

	public ObjectChange(EObject eObject) {
		changedFeatures = new LinkedHashMap<EStructuralFeature, FeatureChange>();
		owner = eObject;
	}

	public void removeValue(EStructuralFeature feature, Object value) {
		FeatureChange featureChange = changedFeatures.get(feature);

		if (featureChange == null) {
			if (feature.isMany()) {
				featureChange = new ManyFeatureChange(owner, feature);
			} else {
				featureChange = new SingleFeatureChange(owner, feature);
			}
			changedFeatures.put(feature, featureChange);
		}
		
		featureChange.removeValue(value);
	}
	
	public void addValue(EStructuralFeature feature, Object value) {
		FeatureChange featureChange = changedFeatures.get(feature);

		if (featureChange == null) {
			if (feature.isMany()) {
				featureChange = new ManyFeatureChange(owner, feature);
			} else {
				featureChange = new SingleFeatureChange(owner, feature);
			}
			changedFeatures.put(feature, featureChange);
		}
		featureChange.addValue(value);
	}

	public void execute() {
		for (FeatureChange change : changedFeatures.values()) {
			change.execute();
		}
	}

	public void undo() {
		for (FeatureChange change : changedFeatures.values()) {
			change.undo();
		}
	}

	/**
	 * @return the owner
	 */
	public EObject getOwner() {
		return owner;
	}
}
