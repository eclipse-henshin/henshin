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
public class MappingImagePropertyDescriptor extends ItemPropertyDescriptor {

	/**
	 * @param adapterFactory
	 * @param resourceLocator
	 * @param displayName
	 * @param description
	 * @param feature
	 */
	public MappingImagePropertyDescriptor(AdapterFactory adapterFactory,
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

				/*
				 * As I am currently not sure, how Enrico is implementing
				 * NestedConditions and its Mappings, I decided to allow all
				 * Nodes in this combo-box, except the ones of the LHS.
				 */
				Collection<EObject> result = getReachableObjectsOfType(
						(EObject) object, feature.getEType());
				result.removeAll(rule.getLhs().getNodes());

				return Collections.unmodifiableCollection(result);
			}// if
		}// if
		return super.getComboBoxObjects(object);
	}// getComboBoxObjects

}// class
