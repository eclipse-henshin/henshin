/**
 * 
 */
package org.eclipse.emf.henshin.provider.descriptors;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.henshin.model.Port;
import org.eclipse.emf.henshin.model.PortMapping;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * @author Stefan Jurack
 * 
 */
public class PortMappingPropertyDescriptor extends ItemPropertyDescriptor {

	public PortMappingPropertyDescriptor(AdapterFactory adapterFactory,
			ResourceLocator resourceLocator, String displayName,
			String description, EStructuralFeature feature, boolean isSettable,
			boolean multiLine, boolean sortChoices, Object staticImage,
			String category, String[] filterFlags) {
		super(adapterFactory, resourceLocator, displayName, description,
				feature, isSettable, multiLine, sortChoices, staticImage,
				category, filterFlags);
	}// constructor

	/**
	 * 
	 * Collects ports.
	 * 
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemPropertyDescriptor#getComboBoxObjects
	 *      (java.lang.Object)
	 */
	@Override
	protected Collection<?> getComboBoxObjects(Object object) {

		if (object instanceof PortMapping) {
			PortMapping po = (PortMapping) object;
			TransformationUnit tu = (TransformationUnit) po.eContainer();
			if (tu!=null) {
				
				Collection<Port> ports = new ArrayList<Port>();
				ports.addAll(tu.getPorts());
				for (TransformationUnit sub: tu.getAllSubUnits()) {
					ports.addAll(sub.getPorts());
				}//for
				return ports;
			}//if
		}// if

		return super.getComboBoxObjects(object);
	}// getComboBoxObjects



}// class
