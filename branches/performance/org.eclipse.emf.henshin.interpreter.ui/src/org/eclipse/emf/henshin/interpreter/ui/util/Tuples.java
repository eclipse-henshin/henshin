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
 * Utility class for different kinds of Tuples. 
 * 
 * @author Gregor Bonifer
 *
 */
public final class Tuples{
	
	private Tuples() {}
	
	public static Boolean and(Tuple<Boolean,Boolean> t){
		return t.getFirst() && t.getSecond();
	}
	
	public static Boolean or(Tuple<Boolean,Boolean> t){
		return t.getFirst() || t.getSecond();
	}
	
	public static Boolean equal(Tuple<Boolean,Boolean> t){
		return t.getFirst() == t.getSecond();
	}
	
	public static Boolean xor(Tuple<Boolean,Boolean> t){
		return t.getFirst() != t.getSecond();
	}
	
	public static Boolean implies(Tuple<Boolean,Boolean> t){
		return !t.getFirst() || t.getSecond();
	}
	
	public static String concat(Tuple<String,String> t){
		return t.getFirst() + t.getSecond();
	}
	
	
	
}
