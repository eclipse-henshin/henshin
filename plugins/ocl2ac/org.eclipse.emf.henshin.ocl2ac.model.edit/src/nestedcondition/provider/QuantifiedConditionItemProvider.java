/**
 */
package nestedcondition.provider;


import graph.GraphFactory;

import java.util.Collection;
import java.util.List;

import laxcondition.Quantifier;

import nestedcondition.NestedconditionFactory;
import nestedcondition.NestedconditionPackage;
import nestedcondition.QuantifiedCondition;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link nestedcondition.QuantifiedCondition} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class QuantifiedConditionItemProvider extends NestedConditionItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QuantifiedConditionItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addQuantifierPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Quantifier feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addQuantifierPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_QuantifiedCondition_quantifier_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_QuantifiedCondition_quantifier_feature", "_UI_QuantifiedCondition_type"),
				 NestedconditionPackage.Literals.QUANTIFIED_CONDITION__QUANTIFIER,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(NestedconditionPackage.Literals.QUANTIFIED_CONDITION__CONDITION);
			childrenFeatures.add(NestedconditionPackage.Literals.QUANTIFIED_CONDITION__MORPHISM);
			childrenFeatures.add(NestedconditionPackage.Literals.QUANTIFIED_CONDITION__CODOMAIN);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns QuantifiedCondition.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/QuantifiedCondition"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		Quantifier labelValue = ((QuantifiedCondition)object).getQuantifier();
		String label = labelValue == null ? null : labelValue.toString();
		return label == null || label.length() == 0 ?
			getString("_UI_QuantifiedCondition_type") :
			getString("_UI_QuantifiedCondition_type") + " " + label;
	}
	

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(QuantifiedCondition.class)) {
			case NestedconditionPackage.QUANTIFIED_CONDITION__QUANTIFIER:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case NestedconditionPackage.QUANTIFIED_CONDITION__CONDITION:
			case NestedconditionPackage.QUANTIFIED_CONDITION__MORPHISM:
			case NestedconditionPackage.QUANTIFIED_CONDITION__CODOMAIN:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(NestedconditionPackage.Literals.QUANTIFIED_CONDITION__CONDITION,
				 NestedconditionFactory.eINSTANCE.createQuantifiedCondition()));

		newChildDescriptors.add
			(createChildParameter
				(NestedconditionPackage.Literals.QUANTIFIED_CONDITION__CONDITION,
				 NestedconditionFactory.eINSTANCE.createTrue()));

		newChildDescriptors.add
			(createChildParameter
				(NestedconditionPackage.Literals.QUANTIFIED_CONDITION__CONDITION,
				 NestedconditionFactory.eINSTANCE.createFormula()));

		newChildDescriptors.add
			(createChildParameter
				(NestedconditionPackage.Literals.QUANTIFIED_CONDITION__MORPHISM,
				 NestedconditionFactory.eINSTANCE.createMorphism()));

		newChildDescriptors.add
			(createChildParameter
				(NestedconditionPackage.Literals.QUANTIFIED_CONDITION__CODOMAIN,
				 GraphFactory.eINSTANCE.createGraph()));
	}

}
