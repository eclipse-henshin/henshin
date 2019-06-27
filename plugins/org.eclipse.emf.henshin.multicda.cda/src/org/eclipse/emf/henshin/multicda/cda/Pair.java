/*******************************************************************************
 * <copyright>
 * Copyright (c) 1995, 2015 Technische Universität Berlin. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.emf.henshin.multicda.cda;

/**
 * @author Olga Runge
 * 
 */
public class Pair<E, F> {
	public E first;

	public F second;

	public Pair(E firstE, F secondF) {
		this.first = firstE;
		this.second = secondF;
	}

	public boolean equals(final Pair<E, F> p) {
		if (this.first.equals(p.first)
				&& this.second.equals(p.second))
			return true;
	
		return false;
	}
	
	public boolean isEmpty() {
		return this.first == null && this.second == null;
	}
}
