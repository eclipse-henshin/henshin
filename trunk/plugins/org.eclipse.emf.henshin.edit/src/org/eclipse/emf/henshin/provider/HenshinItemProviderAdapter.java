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
package org.eclipse.emf.henshin.provider;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.Disposable;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.henshin.provider.util.IItemToolTipProvider;

/**
 * Base class for all Henshin ItemProviderAdapters. Adds support for
 * {@link IItemToolTipProvider}.
 * 
 * @author Gregor Bonifer
 * 
 */
public class HenshinItemProviderAdapter extends ItemProviderAdapter implements IItemToolTipProvider {
	
	public HenshinItemProviderAdapter(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	@Override
	public Object getToolTip(Object object) {
		return null;
	}
	
	protected Disposable getDisposable() {
		if (wrappers == null)
			wrappers = new Disposable();
		return wrappers;
	}
	
}
