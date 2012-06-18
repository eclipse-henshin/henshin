/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.diagram.navigator;

import org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * @generated
 */
public class HenshinNavigatorSorter extends ViewerSorter {

	/**
	 * @generated
	 */
	private static final int GROUP_CATEGORY = 7005;

	/**
	 * @generated
	 */
	public int category(Object element) {
		if (element instanceof HenshinNavigatorItem) {
			HenshinNavigatorItem item = (HenshinNavigatorItem) element;
			return HenshinVisualIDRegistry.getVisualID(item.getView());
		}
		return GROUP_CATEGORY;
	}

}
