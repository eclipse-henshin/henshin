/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.edit.helpers;

import java.util.Comparator;

import org.eclipse.emf.ecore.EClassifier;

/**
 * A comparator for EClassifier collections.
 * @author sjtuner
 */
public final class EClassComparator implements Comparator<EClassifier> {

	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(EClassifier c1, EClassifier c2) {
		String n1 = (c1.getName()!=null) ? c1.getName() : "";
		String n2 = (c2.getName()!=null) ? c2.getName() : "";
		return n1.compareTo(n2);
	}
	
}
