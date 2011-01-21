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
package org.eclipse.emf.henshin.editor.transformationCommands;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.presentation.HenshinEditorPlugin;

/**
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack (sjurack)
 */
public class HenshinBasedMenuContriutor {
	
	protected String henshinFile = "";
	protected String henshinResource = HenshinEditorPlugin.INSTANCE.getBaseURL() + henshinFile;
	protected TransformationSystem transformationSystem;
	
	protected void loadTransformation() {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource transResource = resourceSet.getResource(
				URI.createURI(HenshinEditorPlugin.INSTANCE.getBaseURL() + henshinFile), true);
		transformationSystem = (TransformationSystem) transResource.getContents().get(0);
	}
	
}
