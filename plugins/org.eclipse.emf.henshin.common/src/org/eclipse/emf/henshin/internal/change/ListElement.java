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

import java.util.List;

public class ListElement implements Comparable<ListElement> {
    private int index;
    private Object value;

    ListElement(List<Object> list, Object value) {
	this.index = list.indexOf(value);
	this.value = value;
    }

    @Override
    public int compareTo(ListElement arg0) {
	return index - arg0.getIndex();
    }

    /**
     * @return the index
     */
    public int getIndex() {
	return index;
    }

    /**
     * @return the value
     */
    public Object getValue() {
	return value;
    }
}
