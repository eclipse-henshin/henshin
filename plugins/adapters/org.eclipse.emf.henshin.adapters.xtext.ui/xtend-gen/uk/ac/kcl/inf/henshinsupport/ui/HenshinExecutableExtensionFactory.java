package uk.ac.kcl.inf.henshinsupport.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

/**
 * Instantiates classes declared in the plugin.xml using the DI container.
 */
@SuppressWarnings("all")
public class HenshinExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {
  @Override
  protected Bundle getBundle() {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field Activator is undefined"
      + "\ngetDefault cannot be resolved"
      + "\ngetBundle cannot be resolved");
  }
  
  @Override
  protected Injector getInjector() {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field Activator is undefined"
      + "\ngetDefault cannot be resolved"
      + "\ngetInjector cannot be resolved");
  }
}
