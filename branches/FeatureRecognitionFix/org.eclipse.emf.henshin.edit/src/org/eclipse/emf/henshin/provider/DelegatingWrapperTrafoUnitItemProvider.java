/**
 * 
 */
package org.eclipse.emf.henshin.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.DelegatingWrapperItemProvider;

/**
 * This class wraps TransformationUnits representing tree-editor items which are
 * referred to instead of contained in. In particular, the icon is extended by a
 * link icon.
 * 
 * @author Stefan Jurack (sjurack)
 * 
 */
public class DelegatingWrapperTrafoUnitItemProvider extends DelegatingWrapperItemProvider {
	
	private static final Object LINK_OVR;
	static {
		LINK_OVR = HenshinEditPlugin.INSTANCE.getImage("full/ovr16/link_ovr");
	}
	
	/**
	 * @param value
	 * @param owner
	 * @param feature
	 * @param index
	 * @param adapterFactory
	 */
	public DelegatingWrapperTrafoUnitItemProvider(Object value, Object owner,
			EStructuralFeature feature, int index, AdapterFactory adapterFactory) {
		super(value, owner, feature, index, adapterFactory);
	}// constructor
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.DelegatingWrapperItemProvider#getImage(
	 * java.lang.Object)
	 */
	@Override
	public Object getImage(Object object) {
		Object image = super.getImage(object);
		List<Object> images = new ArrayList<Object>(2);
		images.add(image);
		images.add(LINK_OVR);
		image = new ComposedImage(images);
		return image;
	}// getImage
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.WrapperItemProvider#createDragAndDropCommand
	 * (org.eclipse.emf.edit.domain.EditingDomain, java.lang.Object, float, int,
	 * int, java.util.Collection)
	 */
	@Override
	protected Command createDragAndDropCommand(EditingDomain domain, Object owner, float location,
			int operations, int operation, Collection<?> collection) {
		
		System.out.println("owner : " + owner);
		System.out.println("collection: " + collection);
		System.out.println("location/ops/op: " + location + "/" + operations + "/" + operation);
		
		// return new DragAndDropWithFeatureCommand(domain, owner, (EReference)
		// this.feature, location,
		// operations, operation, collection);
		
		return super.createDragAndDropCommand(domain, owner, location, operations, operation,
				collection);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" (Value:" + this.value + ")");
		sb.append(" (Owner:" + this.owner + ")");
		sb.append(" (Feature:" + this.feature + ")");
		sb.append(" (Index:" + this.index + ")");
		return sb.toString();
	}// toString
	
}// class
