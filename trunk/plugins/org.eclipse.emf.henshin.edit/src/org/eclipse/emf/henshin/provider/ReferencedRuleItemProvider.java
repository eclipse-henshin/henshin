/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.DelegatingWrapperItemProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.provider.util.IItemToolTipProvider;
/**
 * Represents Rule in the context of TransformationUnits.
 * 
 * @author Gregor Bonifer
 *
 */
public class ReferencedRuleItemProvider extends DelegatingWrapperItemProvider implements
		IItemToolTipProvider {
	
	protected RuleItemProvider ruleItemProvider;
	
	public ReferencedRuleItemProvider(Rule rule, Object referringUnit, EStructuralFeature feature,
			int index, AdapterFactory adapterFactory) {
		super(rule, referringUnit, feature, index, adapterFactory);
		ruleItemProvider = (RuleItemProvider) adapterFactory.adapt(value,
				ITreeItemContentProvider.class);
	}
	
	private static final Object LINK_OVR;
	static {
		LINK_OVR = HenshinEditPlugin.INSTANCE.getImage("full/ovr16/link_ovr");
	}
	
	@Override
	public Object getImage(Object object) {
		Object image = ruleItemProvider.getImage(value);
		List<Object> images = new ArrayList<Object>(2);
		images.add(image);
		images.add(LINK_OVR);
		image = new ComposedImage(images);
		return image;
	}
	
	@Override
	public Collection<?> getChildren(Object object) {
		return ((Rule) value).getParameters();
	}
	
	public Collection<?> getNewChildDescriptors(Object object, EditingDomain editingDomain,
			Object sibling) {
		return Collections.singleton(new CommandParameter(null,
				HenshinPackage.Literals.TRANSFORMATION_UNIT__PARAMETERS, HenshinFactory.eINSTANCE
						.createParameter()));
	}
	
	@Override
	public Object getToolTip(Object object) {
		if (delegateItemProvider instanceof IItemToolTipProvider)
			return ((IItemToolTipProvider) delegateItemProvider).getToolTip(value);
		return null;
	}
	
}
