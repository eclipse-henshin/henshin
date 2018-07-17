package org.eclipse.emf.henshin.adapters.xtext.ui

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory
import org.osgi.framework.Bundle
import com.google.inject.Injector

/** 
 * Instantiates classes declared in the plugin.xml using the DI container.
 */
class HenshinExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {
	
	override protected Bundle getBundle() {
		return Activator.getDefault().getBundle()
	}

	override protected Injector getInjector() {
		return Activator.getDefault().getInjector()
	}
}
