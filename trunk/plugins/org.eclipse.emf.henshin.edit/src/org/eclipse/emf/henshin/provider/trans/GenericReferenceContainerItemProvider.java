package org.eclipse.emf.henshin.provider.trans;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.DragAndDropCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.DelegatingWrapperItemProvider;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.henshin.provider.HenshinEditPlugin;

public class GenericReferenceContainerItemProvider extends ItemProviderAdapter implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource {
	
	protected EReference reference;
	protected String labelKey;
	protected String imagePath;
	protected EObject target;
	
	public GenericReferenceContainerItemProvider(AdapterFactory adapterFactory, EObject target,
			EReference reference, String labelKey, String imagePath) {
		super(adapterFactory);
		target.eAdapters().add(this);
		this.target = target;
		this.reference = reference;
		this.labelKey = labelKey;
		this.imagePath = imagePath;
	}
	
	@Override
	protected Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(reference);
		}
		return childrenFeatures;
	}
	
	@Override
	public String getText(Object object) {
		return getString(labelKey);
	}
	
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage(imagePath));
	}
	
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
		newChildDescriptors.add(createChildParameter(reference, reference.getEReferenceType()
				.getEPackage().getEFactoryInstance().create(reference.getEReferenceType())));
	}
	
	@Override
	public void notifyChanged(Notification notification) {
		if (notification.getFeatureID(reference.getEContainingClass().getInstanceClass()) == reference
				.getFeatureID()) {
			updateChildren(notification);
			fireNotifyChanged(new ViewerNotification(notification, this, true, false));
			return;
		}
		super.notifyChanged(notification);
	}
	
	@Override
	public Collection<?> getChildren(Object object) {
		return super.getChildren(target);
	}
	
	@Override
	public Object getParent(Object object) {
		return target;
	}
	
	@Override
	public Collection<?> getNewChildDescriptors(Object object, EditingDomain editingDomain,
			Object sibling) {
		return super.getNewChildDescriptors(target, editingDomain, sibling);
	}
	
	@Override
	protected Command createAddCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, final Collection<?> collection, int index) {
		
		return createWrappedCommand(
				super.createAddCommand(domain, owner, feature, collection, index), owner);
	}
	
	@Override
	protected Command createRemoveCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection<?> collection) {
		return createWrappedCommand(super.createRemoveCommand(domain, owner, feature, collection),
				owner);
	}
	
	protected Command createWrappedCommand(Command command, final EObject owner) {
		return new CommandWrapper(command) {
			public Collection<?> getAffectedObjects() {
				Collection<?> affected = super.getAffectedObjects();
				if (affected.contains(owner))
					affected = Collections.singleton(GenericReferenceContainerItemProvider.this);
				return affected;
			}
		};
	}
	
	public Command createCommand(Object object, EditingDomain domain,
			Class<? extends Command> commandClass, CommandParameter commandParameter) {
		if (commandClass != DragAndDropCommand.class)
			commandParameter.setOwner(target);
		return super.createCommand(object, domain, commandClass, commandParameter);
	}
	
	@Override
	public ResourceLocator getResourceLocator() {
		return HenshinEditPlugin.INSTANCE;
	}
	
	@Override
	protected boolean isWrappingNeeded(Object object) {
		return true;
	}
	
	@Override
	protected Object createWrapper(EObject object, EStructuralFeature feature, Object value,
			int index) {
		return new DelegatingWrapperItemProvider(value, this, feature, index, adapterFactory);
	}
	
}
