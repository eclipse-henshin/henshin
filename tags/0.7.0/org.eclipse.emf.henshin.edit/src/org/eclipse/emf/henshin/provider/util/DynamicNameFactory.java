/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.provider.util;

/**
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack (sjurack)
 * 
 */
public class DynamicNameFactory {
	
	public static DynamicNameFactory INSTANCE = new DynamicNameFactory();
	
	protected DynamicNameFactory() {
	}
	
	int counter = 1;
	
	public String getName(String name) {
		return name + (counter++);
	}
	
	// /**
	// * Feature to be: Dynamic numbering of unnamed objects.
	// * @author Gregor Bonifer
	// */
	// @Override
	// public String getText(Object object) {
	// return DynamicNameFactory.INSTANCE.getName(super.getText(object));
	// }
	
}
