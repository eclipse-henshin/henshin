package org.eclipse.emf.henshin.rulegen.ui.util;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;

/**
 * 
 * @author Manuel Ohrndorf, Timo Kehrer
 */
public class EMFHandlerUtil {

	public static URI getURI(IResource workbenchResource) {

		String projectName = workbenchResource.getProject().getName();
		String filePath = workbenchResource.getProjectRelativePath().toOSString();
		String platformPath = projectName + "/" + filePath;

		return URI.createPlatformResourceURI(platformPath, true);
	}
}
