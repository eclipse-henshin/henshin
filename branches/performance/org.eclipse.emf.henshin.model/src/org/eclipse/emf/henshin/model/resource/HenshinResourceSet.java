package org.eclipse.emf.henshin.model.resource;

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
		
		// Register XMI file resource factories:
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
	 * Convenience method for loading a {@link TransformationSystem} from a 
	 * Henshin file given as a path and file name.
	 * 
	 * @param path Possible relative path and file name of a Henshin resource.
	 * @return The contained {@link TransformationSystem}.
	 */
	public TransformationSystem getTransformationSystem(String path) {
		Resource resource = getResource(path);
		if (resource!=null) {
			for (EObject object : resource.getContents()) {
				if (object instanceof TransformationSystem) {
					return (TransformationSystem) object;
				}
			}
		}
		return null;
	}
	
	/**
	 * Convenience method for saving a resource to a file.
	 * If the path is relative, it will be resolved using the
	 * base directory of this resource set. This method will
	 * override the URI of the resource. If the resource is
	 * an {@link XMIResource}, the schema locations will be saved.
	 * This method will throw runtime exception on I/O errors.
	 * 
	 * @param resource The resource to save.
	 * @param path The file path.
	 */
	public void saveResource(Resource resource, String path) {
		URI uri = URI.createFileURI(path);
		resource.setURI(uri);
		Map<Object,Object> options = new HashMap<Object,Object>();
		options.put(XMIResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		try {
			resource.save(options);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
