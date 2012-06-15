/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter.matching.conditions;

public interface IFormula {
	
	/**
	 * Formula which always evaluates to <code>true</code>.
	 */
	public final IFormula TRUE = new IFormula() {
		public boolean eval() {
			return true;
		}
	};

	/**
	 * Formula which always evaluates to <code>false</code>.
	 */
	public final IFormula FALSE = new IFormula() {
		public boolean eval() {
			return false;
		}
	};

	/**
	 * Evaluate this formula.
	 * @return Result of the evaluation.
	 */
	boolean eval();
	
}
