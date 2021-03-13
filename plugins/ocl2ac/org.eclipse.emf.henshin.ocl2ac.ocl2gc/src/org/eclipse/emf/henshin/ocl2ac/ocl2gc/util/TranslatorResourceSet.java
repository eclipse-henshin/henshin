/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.ocl2gc.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;

import laxcondition.LaxconditionPackage;

public class TranslatorResourceSet extends ResourceSetImpl {

	private URI baseDir;

	public TranslatorResourceSet(String baseDir) {

		// Make sure the standard packages are initialized:
		EcorePackage.eINSTANCE.getName();
		LaxconditionPackage.eINSTANCE.getName();

		// Set the base directory:
		if (baseDir != null) {
			baseDir = new File(baseDir).getAbsolutePath();
			if (!baseDir.endsWith(File.separator)) {
				baseDir = baseDir + File.separator;
			}
			this.baseDir = URI.createFileURI(baseDir);
			setURIConverter(new BaseDirURIConverter());
		}

	}

	public void saveEObject(EObject object, String path) {
		saveEObject(object, URI.createFileURI(path));
	}

	public void saveEObject(EObject object, URI uri) {
		Resource resource = createResource(uri);
		resource.getContents().clear();
		resource.getContents().add(object);
		Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMIResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		try {
			resource.save(options);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private class BaseDirURIConverter extends ExtensibleURIConverterImpl {
		@Override
		public URI normalize(URI uri) {
			if (uri.isFile() && uri.isRelative() && baseDir != null) {
				return uri.resolve(baseDir);
			} else {
				return super.normalize(uri);
			}
		}
	}

}