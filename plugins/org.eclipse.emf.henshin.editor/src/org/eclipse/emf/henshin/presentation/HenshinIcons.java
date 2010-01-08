package org.eclipse.emf.henshin.presentation;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Henshin and Ecore icons.
 * @generated NOT
 * @author Christian Krause
 */
public class HenshinIcons {

	public static final Image EPACKAGE_ICON;
	public static final Image ECLASS_ICON;
	public static final Image EREFERENCE_ICON;

	static {
		
		ImageDescriptor descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(HenshinEditorPlugin.ID, "icons/full/obj16/EPackage.gif");
		EPACKAGE_ICON = descriptor!=null ? descriptor.createImage() : null;
		
		descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(HenshinEditorPlugin.ID, "icons/full/obj16/EClass.gif");
		ECLASS_ICON = descriptor!=null ? descriptor.createImage() : null;

		descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(HenshinEditorPlugin.ID, "icons/full/obj16/EReference.gif");
		EREFERENCE_ICON = descriptor!=null ? descriptor.createImage() : null;

	}
	
}
