/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.provider;

import static org.eclipse.emf.henshin.model.Action.Type.PRESERVE;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.Disposable;
import org.eclipse.emf.edit.provider.IItemFontProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.provider.util.HenshinColorProvider;
import org.eclipse.emf.henshin.provider.util.IItemToolTipProvider;

/**
 * Base class for all Henshin ItemProviderAdapters. 
 * Adds support for {@link IItemToolTipProvider}.
 * 
 * @author Gregor Bonifer, Christian Krause
 */
public class HenshinItemProviderAdapter extends ItemProviderAdapter implements IItemToolTipProvider, IItemFontProvider {
	
	/**
	 * Default constructor.
	 * @param adapterFactory Adapter factory.
	 */
	public HenshinItemProviderAdapter(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.provider.util.IItemToolTipProvider#getToolTip(java.lang.Object)
	 */
	@Override
	public Object getToolTip(Object object) {
		return null;
	}
	
	protected Disposable getDisposable() {
		if (wrappers == null) {
			wrappers = new Disposable();
		}
		return wrappers;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getForeground(java.lang.Object)
	 */
	@Override
	public Object getForeground(Object object) {
		if (object instanceof GraphElement) {
			Action action = ((GraphElement) object).getAction();
			if (action==null) {
				return HenshinColorProvider.COLOR_ACTION_PRESERVE.toURI();  // gray for elements without an action
			} if (action.getType()==PRESERVE) {
				return super.getForeground(object);  // default color for preserve-elements
			}
			return HenshinColorProvider.getActionColor(action).toURI();  // otherwise take the suggested color			
		}
		return super.getForeground(object);
	}

}
