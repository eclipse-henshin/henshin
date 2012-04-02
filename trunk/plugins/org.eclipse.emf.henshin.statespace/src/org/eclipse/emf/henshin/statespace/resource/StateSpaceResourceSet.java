package org.eclipse.emf.henshin.statespace.resource;

import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpacePackage;

/**
 * A resource set implementation for state spaces that provides some 
 * convenience methods for easy use and supports automatic resolving 
 * of relative file URIs using a base directory as implemented
 * in {@link HenshinResourceSet}.
 * 
 * @author Christian Krause
 */
public class StateSpaceResourceSet extends HenshinResourceSet {
	
	/**
	 * Constructor which sets the base directory for this resource set.
	 * @param baseDir Base directory (can be also <code>null</code>).
	 */
	public StateSpaceResourceSet(String baseDir) {
		super(baseDir);
		StateSpacePackage.eINSTANCE.getName();
		getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				StateSpaceResource.FILE_EXTENSION, new StateSpaceResourceFactory());
	}

	/**
	 * Constructor without base directory.
	 */
	public StateSpaceResourceSet() {
		this(null);
	}

	/**
	 * Convenience method for loading a {@link StateSpace} from a 
	 * file given as a path and file name.
	 * @param path Possible relative path and file name of a state space resource.
	 * @return The contained {@link TransformationSystem}.
	 */
	public StateSpace getStateSpace(String path) {
		return (StateSpace) getFirstRoot(path);
	}

}
