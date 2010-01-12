package org.eclipse.emf.henshin.internal.change;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Abstract class for the definition of model changes.
 */
public abstract class FeatureChange {
    /**
     * The EStructuralFeature which should be changed.
     */
    protected EStructuralFeature target;

    /**
     * @return the target
     */
    public final EStructuralFeature getTarget() {
        return target;
    }

}
