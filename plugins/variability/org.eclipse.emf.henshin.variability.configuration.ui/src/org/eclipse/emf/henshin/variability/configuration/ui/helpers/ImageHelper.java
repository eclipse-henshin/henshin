package org.eclipse.emf.henshin.variability.configuration.ui.helpers;

import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;

/**
 * This class provides an interface to retrieve icons stored in this project.
 * 
 * @author Stefan Schulz
 *
 */
public class ImageHelper {
	
	public static Image getImage(String relativePath) {
		InputStream is = ImageHelper.class.getResourceAsStream(relativePath);
		return new Image(Display.getCurrent(), is);
	}
	
	public static ImageDescriptor getImageDescriptor(String relativePath) {
		Path path = new Path(relativePath);
		Bundle bundle = Platform.getBundle("org.eclipse.emf.henshin.variability.configuration.ui");
		URL fullPathString = FileLocator.find(bundle, path, null);
		return ImageDescriptor.createFromURL(fullPathString);
	}

}
