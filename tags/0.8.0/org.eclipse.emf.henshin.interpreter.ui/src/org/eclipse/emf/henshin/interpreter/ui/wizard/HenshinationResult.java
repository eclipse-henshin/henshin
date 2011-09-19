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

import org.eclipse.emf.henshin.interpreter.UnitApplication;

/**
 * 
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class HenshinationResult {
	
	protected Henshination henshination;
	
	protected UnitApplication unitApplication;
	
	public HenshinationResult(Henshination henshination, UnitApplication unitApplication) {
		super();
		this.henshination = henshination;
		this.unitApplication = unitApplication;
	}
	
	public UnitApplication getUnitApplication() {
		return unitApplication;
	}
	
	public Henshination getHenshination() {
		return henshination;
	}
	
}
