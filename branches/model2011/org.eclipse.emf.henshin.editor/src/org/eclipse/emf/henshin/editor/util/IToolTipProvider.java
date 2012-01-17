/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.editor.util;

/**
 * Implementation interface for ToolTipProviders as required by
 * {@link TreeViewerToolTipSupport}.
 * 
 * @author Gregor Bonifer
 * 
 */
public interface IToolTipProvider {
	
	public Object getToolTip(Object object);
	
}
