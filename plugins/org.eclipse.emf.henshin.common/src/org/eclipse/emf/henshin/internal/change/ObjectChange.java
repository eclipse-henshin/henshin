package org.eclipse.emf.henshin.internal.change;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class ObjectChange {
	EObject owner;

	List<FeatureChange> changedFeatures = new ArrayList<FeatureChange>();

	public ObjectChange(EObject eObject) {
		owner = eObject;
	}

	@SuppressWarnings("unchecked")
	public void changeFeature(EStructuralFeature feature, Object newValue) {
		FeatureChange featureChange = null;
		for (FeatureChange temp : changedFeatures) {
			if (temp.getTarget() == feature) {
				featureChange = temp;
				break;
			}
		}

		if (feature.isMany()) {
			ManyFeatureChange manyFeatureChange;
			if (featureChange == null) {
				manyFeatureChange = new ManyFeatureChange(feature,
						(List<Object>) owner.eGet(feature));
				changedFeatures.add(manyFeatureChange);
			} else {
				manyFeatureChange = (ManyFeatureChange) featureChange;
			}
			manyFeatureChange.update(newValue);
		} else {
			SingleFeatureChange singleFeatureChange;
			if (featureChange == null) {
				singleFeatureChange = new SingleFeatureChange(feature, owner
						.eGet(feature));
				changedFeatures.add(singleFeatureChange);
			} else {
				singleFeatureChange = (SingleFeatureChange) featureChange;
			}
			singleFeatureChange.update(newValue);
		}
	}

	public void execute() {
		for (FeatureChange featureChange : changedFeatures) {
			if (featureChange instanceof SingleFeatureChange) {
				SingleFeatureChange singleFeatureChange = (SingleFeatureChange) featureChange;
				owner.eSet(singleFeatureChange.getTarget(), singleFeatureChange
						.getNewValue());
			} else {
				ManyFeatureChange manyFeatureChange = (ManyFeatureChange) featureChange;
				manyFeatureChange.execute();
			}
		}
	}

	public void undo() {
		for (FeatureChange featureChange : changedFeatures) {
			if (featureChange instanceof SingleFeatureChange) {
				SingleFeatureChange singleFeatureChange = (SingleFeatureChange) featureChange;
				owner.eSet(singleFeatureChange.getTarget(), singleFeatureChange
						.getOldValue());
			} else {
				ManyFeatureChange manyFeatureChange = (ManyFeatureChange) featureChange;
				manyFeatureChange.undo();
			}
		}
	}

	/**
	 * @return the owner
	 */
	public EObject getOwner() {
		return owner;
	}
}
