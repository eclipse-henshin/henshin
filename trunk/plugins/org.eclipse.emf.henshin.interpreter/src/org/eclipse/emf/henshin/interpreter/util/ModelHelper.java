/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.util;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.henshin.model.resource.HenshinResource;
import org.eclipse.emf.henshin.model.resource.HenshinResourceFactory;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

/**
 * @deprecated Use the functionalities in {@link HenshinResourceSet} instead.
 */
public class ModelHelper {
	
	public static void registerFileExtension(String extension) {
		if (HenshinResource.FILE_EXTENSION.equals(extension)) {
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(extension,
					new HenshinResourceFactory());
		} else {
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(extension,
					new XMIResourceFactoryImpl());
		}
	}
	
	/**
	 * Tries to open the Ecore file at the given URI location. If successful,
	 * the newly loaded EPackage instance is registered in the global EPackage
	 * registry (<code>EPackage.Registry</code>) and returned.
	 * 
	 * @param ecoreFileUri
	 * @return
	 */
	public static EPackage registerEPackageByEcoreFile(URI ecoreFileUri) {
		EPackage p = registerEPackageByEcoreFile(ecoreFileUri, null);
		if (p != null)
			EPackage.Registry.INSTANCE.put(p.getNsURI(), p);
		return p;
	}// registerEPackageByEcoreFile
	
	public static void registerEPackage(EPackage ePackage) {
		EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);
	}
	
	/**
	 * Tries to open the Ecore file at the given URI location in the context of
	 * the given ResourceSet. If successful, the newly loaded EPackage is
	 * registered in the local EPackage registry of the ResourceSet and
	 * returned.
	 */
	public static EPackage registerEPackageByEcoreFile(URI ecoreFileUri, ResourceSet rs) {
		EPackage result = null;
		if (rs == null) {
			rs = new ResourceSetImpl();
		}
		Resource packageResource = rs.createResource(ecoreFileUri);
		if (packageResource != null) {
			try {
				packageResource.load(null);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}// try catch
			
			if ((packageResource.getContents() != null)
					&& (packageResource.getContents().size() > 0)) {
				EObject tmp = packageResource.getContents().get(0);
				if (tmp != null && tmp instanceof EPackage) {
					result = (EPackage) tmp;
					rs.getPackageRegistry().put(result.getNsURI(), result);
				}// if
			}// if
		}// if
		
		return result;
	}// registerEPackageByEcoreFile
	

	public static EObject loadFile(String filename) {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.getResource(URI.createFileURI(filename), true);
		return resource.getContents().get(0);
	}

	public static void saveFile(String filename, EObject root) {
		Resource resource = new XMLResourceImpl(URI.createFileURI(filename));
		resource.getContents().add(root);
		try {
			resource.save(null);
		} catch (IOException e) {
		}
	}
		
}
