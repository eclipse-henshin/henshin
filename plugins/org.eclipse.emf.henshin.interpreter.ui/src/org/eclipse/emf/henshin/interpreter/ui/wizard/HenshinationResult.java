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
public class HenshinationResult {
	
	protected Henshination henshination;
	
	protected UnitApplication unitApplication;
	
	protected boolean success;
	
	public HenshinationResult(Henshination henshination, UnitApplication unitApplication,boolean success) {
		super();
		this.henshination = henshination;
		this.unitApplication = unitApplication;
		this.success = success;
	}
	
	public UnitApplication getUnitApplication() {
		return unitApplication;
	}
	
	public Henshination getHenshination() {
		return henshination;
	}

	public boolean isSuccess() {
		return success;
	}
	
}
