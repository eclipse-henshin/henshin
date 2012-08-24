/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.ui.wizard;

import org.eclipse.emf.henshin.interpreter.UnitApplication;

/**
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class HenshinationException extends Exception {
	
	private static final long serialVersionUID = 5379555793141985843L;
	
	protected UnitApplication unitApplication;
	
	public HenshinationException(Exception e, UnitApplication unitApplication) {
		super(e);
		this.unitApplication = unitApplication;
	}
	
}
