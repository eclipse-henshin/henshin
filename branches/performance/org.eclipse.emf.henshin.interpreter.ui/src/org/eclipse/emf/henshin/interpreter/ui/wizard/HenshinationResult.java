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
package org.eclipse.emf.henshin.interpreter.ui.wizard;

import org.eclipse.emf.henshin.interpreter.UnitApplicationImpl;

/**
 * 
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class HenshinationResult {
	
	protected Henshination henshination;
	
	protected UnitApplicationImpl unitApplication;
	
	protected boolean success;
	
	public HenshinationResult(Henshination henshination, UnitApplicationImpl unitApplication,boolean success) {
		super();
		this.henshination = henshination;
		this.unitApplication = unitApplication;
		this.success = success;
	}
	
	public UnitApplicationImpl getUnitApplication() {
		return unitApplication;
	}
	
	public Henshination getHenshination() {
		return henshination;
	}

	public boolean isSuccess() {
		return success;
	}
	
}
