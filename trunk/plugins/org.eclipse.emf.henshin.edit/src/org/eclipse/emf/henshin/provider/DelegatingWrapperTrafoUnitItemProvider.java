/**
 * 
 */
package org.eclipse.emf.henshin.provider;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
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
		images.add(HenshinEditPlugin.INSTANCE.getImage("full/ovr16/link_ovr"));
		image = new ComposedImage(images);
		return image;
	}// getImage
	
}// class
