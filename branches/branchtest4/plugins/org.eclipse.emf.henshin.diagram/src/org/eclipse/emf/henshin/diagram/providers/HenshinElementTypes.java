/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.diagram.providers;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.diagram.edit.parts.AttributeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.EdgeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.TransformationSystemEditPart;
import org.eclipse.emf.henshin.diagram.part.HenshinDiagramEditorPlugin;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * @generated
 */
public class HenshinElementTypes extends ElementInitializers {

	/**
	 * @generated
	 */
	private HenshinElementTypes() {
	}

	/**
	 * @generated
	 */
	private static Map elements;

	/**
	 * @generated
	 */
	private static ImageRegistry imageRegistry;

	/**
	 * @generated
	 */
	private static Set KNOWN_ELEMENT_TYPES;

	/**
	 * @generated
	 */
	public static final IElementType TransformationSystem_1000 = getElementType("org.eclipse.emf.henshin.diagram.TransformationSystem_1000"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Rule_2001 = getElementType("org.eclipse.emf.henshin.diagram.Rule_2001"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Node_3001 = getElementType("org.eclipse.emf.henshin.diagram.Node_3001"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Attribute_3002 = getElementType("org.eclipse.emf.henshin.diagram.Attribute_3002"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Edge_4001 = getElementType("org.eclipse.emf.henshin.diagram.Edge_4001"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	private static ImageRegistry getImageRegistry() {
		if (imageRegistry == null) {
			imageRegistry = new ImageRegistry();
		}
		return imageRegistry;
	}

	/**
	 * @generated
	 */
	private static String getImageRegistryKey(ENamedElement element) {
		return element.getName();
	}

	/**
	 * @generated
	 */
	private static ImageDescriptor getProvidedImageDescriptor(
			ENamedElement element) {
		if (element instanceof EStructuralFeature) {
			EStructuralFeature feature = ((EStructuralFeature) element);
			EClass eContainingClass = feature.getEContainingClass();
			EClassifier eType = feature.getEType();
			if (eContainingClass != null && !eContainingClass.isAbstract()) {
				element = eContainingClass;
			} else if (eType instanceof EClass
					&& !((EClass) eType).isAbstract()) {
				element = eType;
			}
		}
		if (element instanceof EClass) {
			EClass eClass = (EClass) element;
			if (!eClass.isAbstract()) {
				return HenshinDiagramEditorPlugin.getInstance()
						.getItemImageDescriptor(
								eClass.getEPackage().getEFactoryInstance()
										.create(eClass));
			}
		}
		// TODO : support structural features
		return null;
	}

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(ENamedElement element) {
		String key = getImageRegistryKey(element);
		ImageDescriptor imageDescriptor = getImageRegistry().getDescriptor(key);
		if (imageDescriptor == null) {
			imageDescriptor = getProvidedImageDescriptor(element);
			if (imageDescriptor == null) {
				imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
			}
			getImageRegistry().put(key, imageDescriptor);
		}
		return imageDescriptor;
	}

	/**
	 * @generated
	 */
	public static Image getImage(ENamedElement element) {
		String key = getImageRegistryKey(element);
		Image image = getImageRegistry().get(key);
		if (image == null) {
			ImageDescriptor imageDescriptor = getProvidedImageDescriptor(element);
			if (imageDescriptor == null) {
				imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
			}
			getImageRegistry().put(key, imageDescriptor);
			image = getImageRegistry().get(key);
		}
		return image;
	}

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(IAdaptable hint) {
		ENamedElement element = getElement(hint);
		if (element == null) {
			return null;
		}
		return getImageDescriptor(element);
	}

	/**
	 * @generated
	 */
	public static Image getImage(IAdaptable hint) {
		ENamedElement element = getElement(hint);
		if (element == null) {
			return null;
		}
		return getImage(element);
	}

	/**
	 * Returns 'type' of the ecore object associated with the hint.
	 * 
	 * @generated
	 */
	public static ENamedElement getElement(IAdaptable hint) {
		Object type = hint.getAdapter(IElementType.class);
		if (elements == null) {
			elements = new IdentityHashMap();

			elements.put(TransformationSystem_1000, HenshinPackage.eINSTANCE
					.getTransformationSystem());

			elements.put(Rule_2001, HenshinPackage.eINSTANCE.getRule());

			elements.put(Node_3001, HenshinPackage.eINSTANCE.getNode());

			elements.put(Attribute_3002, HenshinPackage.eINSTANCE
					.getAttribute());

			elements.put(Edge_4001, HenshinPackage.eINSTANCE.getEdge());
		}
		return (ENamedElement) elements.get(type);
	}

	/**
	 * @generated
	 */
	private static IElementType getElementType(String id) {
		return ElementTypeRegistry.getInstance().getType(id);
	}

	/**
	 * @generated
	 */
	public static boolean isKnownElementType(IElementType elementType) {
		if (KNOWN_ELEMENT_TYPES == null) {
			KNOWN_ELEMENT_TYPES = new HashSet();
			KNOWN_ELEMENT_TYPES.add(TransformationSystem_1000);
			KNOWN_ELEMENT_TYPES.add(Rule_2001);
			KNOWN_ELEMENT_TYPES.add(Node_3001);
			KNOWN_ELEMENT_TYPES.add(Attribute_3002);
			KNOWN_ELEMENT_TYPES.add(Edge_4001);
		}
		return KNOWN_ELEMENT_TYPES.contains(elementType);
	}

	/**
	 * @generated
	 */
	public static IElementType getElementType(int visualID) {
		switch (visualID) {
		case TransformationSystemEditPart.VISUAL_ID:
			return TransformationSystem_1000;
		case RuleEditPart.VISUAL_ID:
			return Rule_2001;
		case NodeEditPart.VISUAL_ID:
			return Node_3001;
		case AttributeEditPart.VISUAL_ID:
			return Attribute_3002;
		case EdgeEditPart.VISUAL_ID:
			return Edge_4001;
		}
		return null;
	}

}
