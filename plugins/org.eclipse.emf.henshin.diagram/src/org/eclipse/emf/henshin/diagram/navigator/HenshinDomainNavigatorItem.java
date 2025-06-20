/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.navigator;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
 * @generated
 */
public class HenshinDomainNavigatorItem extends PlatformObject {

	/**
	 * @generated
	 */
	static {
		final Class<?>[] supportedTypes = new Class<?>[] { EObject.class, IPropertySource.class };
		Platform.getAdapterManager().registerAdapters(new IAdapterFactory() {
			@Override
			public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
				if (adaptableObject instanceof org.eclipse.emf.henshin.diagram.navigator.HenshinDomainNavigatorItem) {
					org.eclipse.emf.henshin.diagram.navigator.HenshinDomainNavigatorItem domainNavigatorItem = (org.eclipse.emf.henshin.diagram.navigator.HenshinDomainNavigatorItem) adaptableObject;
					EObject eObject = domainNavigatorItem.getEObject();
					if (adapterType == EObject.class) {
						return adapterType.cast(eObject);
					}
					if (adapterType == IPropertySource.class) {
						return adapterType.cast(domainNavigatorItem.getPropertySourceProvider().getPropertySource(eObject));
					}
				}
				return null;
			}

			@Override
			public Class<?>[] getAdapterList() {
				return supportedTypes;
			}
		}, org.eclipse.emf.henshin.diagram.navigator.HenshinDomainNavigatorItem.class);
	}

	/**
	 * @generated
	 */
	private Object myParent;

	/**
	 * @generated
	 */
	private EObject myEObject;

	/**
	 * @generated
	 */
	private IPropertySourceProvider myPropertySourceProvider;

	/**
	 * @generated
	 */
	public HenshinDomainNavigatorItem(EObject eObject, Object parent, IPropertySourceProvider propertySourceProvider) {
		myParent = parent;
		myEObject = eObject;
		myPropertySourceProvider = propertySourceProvider;
	}

	/**
	 * @generated
	 */
	public Object getParent() {
		return myParent;
	}

	/**
	 * @generated
	 */
	public EObject getEObject() {
		return myEObject;
	}

	/**
	 * @generated
	 */
	public IPropertySourceProvider getPropertySourceProvider() {
		return myPropertySourceProvider;
	}

	/**
	 * @generated
	 */
	public boolean equals(Object obj) {
		if (obj instanceof org.eclipse.emf.henshin.diagram.navigator.HenshinDomainNavigatorItem) {
			return EcoreUtil.getURI(getEObject()).equals(EcoreUtil
					.getURI(((org.eclipse.emf.henshin.diagram.navigator.HenshinDomainNavigatorItem) obj).getEObject()));
		}
		return super.equals(obj);
	}

	/**
	 * @generated
	 */
	public int hashCode() {
		return EcoreUtil.getURI(getEObject()).hashCode();
	}

}
