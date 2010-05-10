/**
 * 
 */
package org.eclipse.emf.henshin.provider.descriptors;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.henshin.model.PortKind;
import org.eclipse.emf.henshin.model.PortParameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * @author Stefan Jurack
 * 
 */
public class PortParameterPropertyDescriptor extends ItemPropertyDescriptor {

	public PortParameterPropertyDescriptor(AdapterFactory adapterFactory,
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
	 * Collect nodes depending on the current direction value (see
	 * {@link PortKind}).<br>
	 * 
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemPropertyDescriptor#getComboBoxObjects
	 *      (java.lang.Object)
	 */
	@Override
	protected Collection<?> getComboBoxObjects(Object object) {
		/*
		 * The collection of variables a port parameters may point to depends on
		 * the container the port is contained in. If a rule is the container,
		 * the port parameter point to variables contained in that rule. If the
		 * container is a transformation unit (but in particular not a rule),
		 * ports must NOT refer anything.
		 */
		if (object instanceof PortParameter) {
			PortParameter po = (PortParameter) object;
			if (po.getUnit() instanceof Rule) {
				Rule rule = (Rule) po.getUnit();
				return Collections.unmodifiableCollection(rule.getVariables());
			} else if (po.getUnit() instanceof TransformationUnit) {
				return Collections.EMPTY_LIST;
			}// if else
		}// if

		return super.getComboBoxObjects(object);
	}// getComboBoxObjects

}// class
