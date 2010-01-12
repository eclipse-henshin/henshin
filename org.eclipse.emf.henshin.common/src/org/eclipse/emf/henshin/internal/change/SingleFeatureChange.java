package org.eclipse.emf.henshin.internal.change;

import org.eclipse.emf.ecore.EStructuralFeature;

public class SingleFeatureChange extends FeatureChange {
    private Object oldValue;
    private Object newValue;

    public SingleFeatureChange( EStructuralFeature target, Object oldValue ) {
        this.target = target;
        this.oldValue = oldValue;
    }

    public void update( Object newValue ) {
        this.newValue = newValue;
    }

    /**
     * @return the oldValue
     */
    public Object getOldValue() {
        return oldValue;
    }

    /**
     * @return the newValue
     */
    public Object getNewValue() {
        return newValue;
    }
}
