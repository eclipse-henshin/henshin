/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
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
import org.eclipse.emf.henshin.diagram.edit.parts.InvocationEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.LinkEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.ModuleEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.SymbolEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.UnitEditPart;
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
public class HenshinElementTypes {

	/**
	 * @generated
	 */
	private HenshinElementTypes() {
	}

	/**
	 * @generated
	 */
	private static Map<IElementType, ENamedElement> elements;

	/**
	 * @generated
	 */
	private static ImageRegistry imageRegistry;

	/**
	 * @generated
	 */
	private static Set<IElementType> KNOWN_ELEMENT_TYPES;

	/**
	 * @generated
	 */
	public static final IElementType Module_1000 = getElementType("org.eclipse.emf.henshin.diagram.Module_1000"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Rule_2001 = getElementType("org.eclipse.emf.henshin.diagram.Rule_2001"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Unit_2002 = getElementType("org.eclipse.emf.henshin.diagram.Unit_2002"); //$NON-NLS-1$

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
	public static final IElementType Node_3004 = getElementType("org.eclipse.emf.henshin.diagram.Node_3004"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Unit_3003 = getElementType("org.eclipse.emf.henshin.diagram.Unit_3003"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Edge_4001 = getElementType("org.eclipse.emf.henshin.diagram.Edge_4001"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Link_4002 = getElementType("org.eclipse.emf.henshin.diagram.Link_4002"); //$NON-NLS-1$

	/**
	 * Get the image registry.
	 * @generated NOT
	 */
	private static ImageRegistry getImageRegistry() {
		if (imageRegistry == null) {
			imageRegistry = new ImageRegistry();

			// Initialize the icon of for the abstract class Unit manually:
			String key = getImageRegistryKey(HenshinPackage.eINSTANCE.getUnit());
			ImageDescriptor imageDesc = HenshinDiagramEditorPlugin
					.findImageDescriptor("/org.eclipse.emf.henshin.edit/icons/full/obj16/Unit.gif");
			if (imageDesc != null) {
				imageRegistry.put(key, imageDesc);
			}
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
			elements = new IdentityHashMap<IElementType, ENamedElement>();

			elements.put(Module_1000, HenshinPackage.eINSTANCE.getModule());

			elements.put(Rule_2001, HenshinPackage.eINSTANCE.getRule());

			elements.put(Unit_2002, HenshinPackage.eINSTANCE.getUnit());

			elements.put(Node_3001, HenshinPackage.eINSTANCE.getNode());

			elements.put(Attribute_3002,
					HenshinPackage.eINSTANCE.getAttribute());

			elements.put(Unit_3003, HenshinPackage.eINSTANCE.getUnit());

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
			KNOWN_ELEMENT_TYPES = new HashSet<IElementType>();
			KNOWN_ELEMENT_TYPES.add(Module_1000);
			KNOWN_ELEMENT_TYPES.add(Rule_2001);
			KNOWN_ELEMENT_TYPES.add(Unit_2002);
			KNOWN_ELEMENT_TYPES.add(Node_3001);
			KNOWN_ELEMENT_TYPES.add(Attribute_3002);
			KNOWN_ELEMENT_TYPES.add(Node_3004);
			KNOWN_ELEMENT_TYPES.add(Unit_3003);
			KNOWN_ELEMENT_TYPES.add(Edge_4001);
			KNOWN_ELEMENT_TYPES.add(Link_4002);
		}
		return KNOWN_ELEMENT_TYPES.contains(elementType);
	}

	/**
	 * @generated
	 */
	public static IElementType getElementType(int visualID) {
		switch (visualID) {
		case ModuleEditPart.VISUAL_ID:
			return Module_1000;
		case RuleEditPart.VISUAL_ID:
			return Rule_2001;
		case UnitEditPart.VISUAL_ID:
			return Unit_2002;
		case NodeEditPart.VISUAL_ID:
			return Node_3001;
		case AttributeEditPart.VISUAL_ID:
			return Attribute_3002;
		case SymbolEditPart.VISUAL_ID:
			return Node_3004;
		case InvocationEditPart.VISUAL_ID:
			return Unit_3003;
		case EdgeEditPart.VISUAL_ID:
			return Edge_4001;
		case LinkEditPart.VISUAL_ID:
			return Link_4002;
		}
		return null;
	}

}
