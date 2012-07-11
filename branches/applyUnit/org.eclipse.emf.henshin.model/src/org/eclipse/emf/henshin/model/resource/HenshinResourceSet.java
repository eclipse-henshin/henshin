package org.eclipse.emf.henshin.model.resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * A resource set implementation for Henshin that provides some 
 * convenience methods for easy use and supports automatic resolving 
 * of relative file URIs using a base directory.
 * 
 * @author Christian Krause
 */
public class HenshinResourceSet extends ResourceSetImpl {

	/**
	 * Base directory URI converter. If the base directory is set and 
	 * a relative file URI should be converted, then the relative file 
	 * URI is resolved using the base directory.
	 */
	private class BaseDirURIConverter extends ExtensibleURIConverterImpl {
		@Override
		public URI normalize(URI uri) {
			if (uri.isFile() && uri.isRelative() && baseDir!=null) {
				return uri.resolve(baseDir);
			} else {
				return super.normalize(uri);
			}
		}		
	}

	// Absolute file path of the base directory as a file URI:
	private URI baseDir;

	/**
	 * Constructor which sets the base directory for this resource set.
	 * 
	 * @param baseDir Base directory (can be also <code>null</code>).
	 */
	public HenshinResourceSet(String baseDir) {
		
		// Make sure the standard packages are initialized:
		EcorePackage.eINSTANCE.getName();
		HenshinPackage.eINSTANCE.getName();
		
		// Try to load the trace model too:
		try {
			Class<?> clazz = Class.forName("org.eclipse.emf.henshin.trace.impl.TracePackageImpl");
			if (clazz!=null) {
				clazz.getMethod("init").invoke(clazz);
			}
		} catch (Throwable t) {
			// do nothing
		}
		
		// Register common XMI file resource factories:
		registerXMIResourceFactories(HenshinResource.FILE_EXTENSION, "ecore", "xmi");
		
		// Set the base directory:
		if (baseDir!=null) {
			baseDir = new File(baseDir).getAbsolutePath();
			if (!baseDir.endsWith(File.separator)) {
				baseDir = baseDir + File.separator;
			}
			this.baseDir = URI.createFileURI(baseDir);
			setURIConverter(new BaseDirURIConverter());
		}
		
	}

	/**
	 * Constructor without base directory.
	 */
	public HenshinResourceSet() {
		this(null);
	}

	/**
	 * Get the base directory for this resource set as a file URI.
	 * 
	 * @return The base directory as a file URI or <code>null</code>.
	 */
	public URI getBaseDir() {
		return baseDir;
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
			} else if ("ecore".equals(extension)) {
				factory = new EcoreResourceFactoryImpl();
			} else {
				factory = new XMIResourceFactoryImpl();
			}
			if (!map.containsKey(extension)) {
				map.put(extension, factory);
			}
		}
	}

	/**
	 * Tries to open the Ecore file at the given location. 
	 * If successful, all {@link EPackage}s in the model are
	 * registered in the local package registry of this resource set.
	 * 
	 * @param ecorePath The relative path to an Ecore file.
	 * @return List of loaded and registered {@link EPackage}s.
	 */
	public List<EPackage> registerDynamicEPackages(String ecorePath) {
		List<EPackage> result = new ArrayList<EPackage>();
		try {
			Resource resource = getResource(ecorePath);
			Iterator<EObject> it = resource.getAllContents();
			while (it.hasNext()) {
				EObject next = it.next();
				if (next instanceof EPackage) {
					result.add((EPackage) next);
					getPackageRegistry().put(((EPackage) next).getNsURI(), next);
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Loads a resource for the given file name. If the path is relative, 
	 * it will be resolved using the base directory of this resource set.
	 * 
	 * @param path Possible relative model path.
	 * @return The loaded resource.
	 */
	public Resource getResource(String path) {
		return getResource(URI.createFileURI(path), true);
	}

	/**
	 * Loads a resource for the given file name and returns the first
	 * object contained in it. If the path is relative, it will be resolved 
	 * using the base directory of this resource set.
	 * 
	 * @param path Possible relative path and file name.
	 * @return The first contained object.
	 */
	public EObject getObject(String path) {
		Resource resource = getResource(path);
		if (resource!=null && !resource.getContents().isEmpty()) {
			return resource.getContents().get(0);
		}
		return null;
	}

	/**
	 * Convenience method for loading a {@link TransformationSystem} from a 
	 * Henshin file given as a path and file name.
	 * 
	 * @param path Possible relative path and file name of a Henshin resource.
	 * @return The contained {@link TransformationSystem}.
	 */
	public TransformationSystem getTransformationSystem(String path) {
		return (TransformationSystem) getObject(path);
	}
	
	/**
	 * Save an object at a given path. This creates a new resource
	 * under the given path, adds the object to the resource and saves it.
	 * 
	 * @param object Object to be saved.
	 * @param path Possibly relative file path.
	 */
	public void saveObject(EObject object, String path) {
		URI uri = URI.createFileURI(path);
		Resource resource = createResource(uri);
		resource.getContents().clear();
		resource.getContents().add(object);		
		Map<Object,Object> options = new HashMap<Object,Object>();
		options.put(XMIResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		try {
			resource.save(options);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
