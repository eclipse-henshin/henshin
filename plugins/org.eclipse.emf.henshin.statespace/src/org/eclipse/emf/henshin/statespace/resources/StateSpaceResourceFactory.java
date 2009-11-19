package org.eclipse.emf.henshin.statespace.resources;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * A resource factory for Henshin state space files.
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceResourceFactory implements Resource.Factory {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.resource.Resource.Factory#createResource(org.eclipse.emf.common.util.URI)
	 */
	public Resource createResource(URI uri) {
		
		// Platform resource ?
		if (uri.isPlatformResource()) {
			
			// Get the workspace root:
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IPath path = new Path(uri.toPlatformString(true));			
			IFile file = root.getFile(path);
			
		}
		
		return null;
		
	}
	
}
