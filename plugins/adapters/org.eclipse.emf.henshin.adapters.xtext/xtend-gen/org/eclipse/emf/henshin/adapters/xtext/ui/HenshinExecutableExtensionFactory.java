package org.eclipse.emf.henshin.adapters.xtext.ui;

import com.google.inject.Injector;
import org.eclipse.emf.henshin.adapters.xtext.ui.Activator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

/**
 * Instantiates classes declared in the plugin.xml using the DI container.
 */
@SuppressWarnings("all")
public class HenshinExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {
  @Override
  protected Bundle getBundle() {
    return Activator.getDefault().getBundle();
  }
  
  @Override
  protected Injector getInjector() {
    return Activator.getDefault().getInjector();
  }
}
