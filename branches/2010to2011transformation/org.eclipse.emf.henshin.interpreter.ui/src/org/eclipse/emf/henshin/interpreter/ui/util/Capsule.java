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
package org.eclipse.emf.henshin.interpreter.ui.util;
/**
 * Simple Encapsulation for arbitrary classes. Allows mutable values in final contexts.
 * 
 * @author Gregor Bonifer
 *
 * @param <T>
 */
public class Capsule<T> {
	
	protected T value;
	
	public Capsule() {	
	}
	
	public Capsule(T value) {
		setValue(value);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}	
	
}
