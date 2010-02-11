/**
 * 
 */
package org.eclipse.emf.henshin.provider.descriptors;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Rule;

/**
 * @author Stefan Jurack
 * 
 */
public class MappingOriginPropertyDescriptor extends ItemPropertyDescriptor {

	/**
	 * @param adapterFactory
	 * @param resourceLocator
	 * @param displayName
	 * @param description
	 * @param feature
	 */
	public MappingOriginPropertyDescriptor(AdapterFactory adapterFactory,
			ResourceLocator resourceLocator, String displayName,
			String description, EStructuralFeature feature) {
		super(adapterFactory, resourceLocator, displayName, description,
				feature, true, false, true, null, null, null);
	}// constructor

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.edit.provider.ItemPropertyDescriptor#getComboBoxObjects
	 * (java.lang.Object)
	 */
	@Override
	protected Collection<?> getComboBoxObjects(Object object) {

		if (object instanceof Mapping) {
			Mapping mapping = (Mapping) object;
			EObject eobject = mapping.eContainer();
			if (eobject instanceof Rule) {
				Rule rule = (Rule) eobject;
				Collection<?> nodeList = Collections.unmodifiableList(rule
						.getLhs().getNodes());
				return nodeList;
			}// if
		}// if
		return super.getComboBoxObjects(object);
	}// getComboBoxObjects

}// class
