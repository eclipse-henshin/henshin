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
