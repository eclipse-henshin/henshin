package org.eclipse.emf.henshin;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;

public class HenshinModelPlugin extends EMFPlugin {

	public static final HenshinModelPlugin INSTANCE = new HenshinModelPlugin();
	
	private static Implementation plugin;	
	
	public HenshinModelPlugin() {
		super
			(new ResourceLocator [] {
			});
	}	
	
	public HenshinModelPlugin(ResourceLocator[] delegateResourceLocators) {
		super(delegateResourceLocators);
	}

	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	public static Implementation getPlugin() {
		return plugin;
	}
	
	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class Implementation extends EclipsePlugin {
		/**
		 * Creates an instance.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public Implementation() {
			super();

			// Remember the static instance.
			//
			plugin = this;
		}
	}	
	
}
