package org.eclipse.emf.henshin.sam.model;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage;
import org.eclipse.xtend.typesystem.emf.check.CheckRegistry;

public class SamigraActivator extends Plugin {

	public SamigraActivator() {
		EValidator.Registry.INSTANCE.put(SamrulesPackage.eINSTANCE, EObjectValidator.INSTANCE);
		EValidator.Registry.INSTANCE.put(SamgraphPackage.eINSTANCE, EObjectValidator.INSTANCE);		
		EValidator.Registry.INSTANCE.put(SamtypegraphPackage.eINSTANCE, EObjectValidator.INSTANCE);	
		CheckRegistry.getInstance();
	}

}
