/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.ui.util;

/**
 * A general-purpose Tuple implementation. 
 * 
 * @author Gregor Bonifer
 *
 * @param <A>
 * @param <B>
 */
public class Tuple<A, B> {
	
	protected A first;
	
	protected B second;
	
	public Tuple() {
	}
	
	public Tuple(A first, B second) {
		setFirst(first);
		setSecond(second);
	}
	
	public A getFirst() {
		return first;
	}
	
	public void setFirst(A first) {
		this.first = first;
	}
	
	public B getSecond() {
		return second;
	}
	
	public void setSecond(B second) {
		this.second = second;
	}
	
	public static <A, B> Tuple<A, B> createFirst(A first) {
		return new Tuple<A, B>(first, null);
	}
	
	public static <A, B> Tuple<A, B> createSecond(B second) {
		return new Tuple<A, B>(null, second);
	}
	
}
