package org.eclipse.emf.henshin.model.resource;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * A resource set implementation for Henshin that provides some
 * convenience methods for easy use.
 * 
 * @author Christian Krause
 */
public class HenshinResourceSet extends ResourceSetImpl {

	/**
	 * Default constructor.
	 */
	public HenshinResourceSet() {
		
		// Register common file extensions:
		registerXMIResourceFactories(
				HenshinResource.FILE_EXTENSION, 
				"ecore", "xmi");
		
		// Make sure the Ecore and the Henshin packages are initialized:
		EcorePackage.eINSTANCE.getName();
		HenshinPackage.eINSTANCE.getName();
		
	}
	
	/**
	 * Register {@link XMIResourceFactoryImpl}s for the given file extensions.
	 * The factories are registered in the scope of this resource set.
	 * 
	 * @param fileExtension File extensions.
	 */
	public void registerXMIResourceFactories(String... fileExtensions) {
		Map<String, Object> map = getResourceFactoryRegistry().getExtensionToFactoryMap();
		for (String extension : fileExtensions) {
			Resource.Factory factory;
			if (HenshinResource.FILE_EXTENSION.equals(extension)) {
				factory = new HenshinResourceFactory();
			} else {
				factory = new XMIResourceFactoryImpl();
			}
			if (!map.containsKey(extension)) {
				map.put(extension, factory);
			}
		}
	}

	/**
	 * Register EPackages in this resource set's local package registry.
	 * @param ePackages EPackages.
	 */
	public void registerEPackages(EPackage... ePackages) {
		for (EPackage ePackage : ePackages) {
			getPackageRegistry().put(ePackage.getNsURI(), ePackage);
			for (EPackage subPackage : ePackage.getESubpackages()) {
				registerEPackages(subPackage);
			}
		}
	}

	/**
	 * Load a given list of Ecore files and register all EPackages
	 * using {@link #registerEPackages(EPackage...)}.
	 * @param ecoreFiles Ecore file URIs.
	 */
	public void registerEPackages(URI... ecoreFiles) {
		for (URI ecoreFile : ecoreFiles) {
			Resource resource = getResource(ecoreFile, true);
			for (EObject object : resource.getContents()) {
				if (object instanceof EPackage) {
					registerEPackages((EPackage) object);
				}
			}
		}
	}

	/**
	 * Load a given list of Ecore files and register all EPackages
	 * using {@link #registerEPackages(EPackage...)}. This creates
	 * file URIs for the given paths and file names. 
	 * @param ecoreFiles Ecore file names.
	 */
	public void registerEPackages(String... ecoreFiles) {
		for (String ecoreFile : ecoreFiles) {
			registerEPackages(URI.createFileURI(ecoreFile));
		}
	}

	/**
	 * Loads a resource for the given URI and returns the first root
	 * object in that resource.
	 * @param uri Model URI.
	 * @return The first root object in this model.
	 */
	public EObject getFirstRoot(URI uri) {
		return getResource(uri, true).getContents().get(0);
	}

	/**
	 * Loads a resource for the given file name and returns the first root
	 * object in that resource. This delegates to {@link #getFirstRoot(URI)}.
	 * @param path Model path and file name.
	 * @return The first root object in this model.
	 */
	public EObject getFirstRoot(String path) {
		return getFirstRoot(URI.createFileURI(path));
	}

	/**
	 * Convenience method for loading a {@link TransformationSystem}
	 * from a Henshin file given as a URI.
	 * @param uri URI of a Henshin resource.
	 * @return The contained {@link TransformationSystem}.
	 */
	public TransformationSystem getTransformationSystem(URI uri) {
		return (TransformationSystem) getFirstRoot(uri);
	}

	/**
	 * Convenience method for loading a {@link TransformationSystem}
	 * from a Henshin file given as a path and file name.
	 * @param path Path and file name of a Henshin resource.
	 * @return The contained {@link TransformationSystem}.
	 */
	public TransformationSystem getTransformationSystem(String path) {
		return (TransformationSystem) getFirstRoot(URI.createFileURI(path));
	}

}
