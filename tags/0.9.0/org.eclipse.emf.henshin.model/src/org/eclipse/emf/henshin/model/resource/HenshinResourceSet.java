package org.eclipse.emf.henshin.model.resource;

import java.io.File;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
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
	 * @return The base directory as a file URI or <code>null</code>.
	 */
	public URI getBaseDir() {
		return baseDir;
	}
	
	/**
	 * Register {@link XMIResourceFactoryImpl}s for the given file extensions.
	 * The factories are registered in the scope of this resource set.
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
	 * Loads a resource for the given file name and returns the first root
	 * object in that resource. If the path is relative, it will be resolved
	 * using the base directory of this resource.
	 * 
	 * @param path Possible relative model path.
	 * @return The first root object in this model.
	 */
	public EObject getFirstRoot(String path) {
		return getResource(URI.createFileURI(path), true).getContents().get(0);
	}

	/**
	 * Convenience method for loading a {@link TransformationSystem} from a 
	 * Henshin file given as a path and file name.
	 * @param path Possible relative path and file name of a Henshin resource.
	 * @return The contained {@link TransformationSystem}.
	 */
	public TransformationSystem getTransformationSystem(String path) {
		return (TransformationSystem) getFirstRoot(path);
	}
	
}
