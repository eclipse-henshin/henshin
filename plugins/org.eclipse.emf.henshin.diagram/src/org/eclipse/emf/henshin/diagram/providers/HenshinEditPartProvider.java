/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.providers;

import org.eclipse.emf.henshin.diagram.edit.parts.HenshinEditPartFactory;
import org.eclipse.emf.henshin.diagram.edit.parts.ModuleEditPart;
import org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.providers.DefaultEditPartProvider;

/**
 * @generated
 */
public class HenshinEditPartProvider extends DefaultEditPartProvider {

	/**
	 * @generated
	 */
	public HenshinEditPartProvider() {
		super(new HenshinEditPartFactory(), HenshinVisualIDRegistry.TYPED_INSTANCE, ModuleEditPart.MODEL_ID);
	}
}
