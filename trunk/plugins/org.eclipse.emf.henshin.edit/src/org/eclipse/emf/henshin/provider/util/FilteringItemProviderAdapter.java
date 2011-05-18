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
package org.eclipse.emf.henshin.provider.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.henshin.provider.HenshinItemProviderAdapterFactory;
import org.eclipse.emf.henshin.provider.filter.BaseFilterProvider;

/**
 * 
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class FilteringItemProviderAdapter extends ItemProviderAdapter {
	
	protected boolean filteringEnabled = false;
	protected BaseFilterProvider filterProvider = null;
	
	/**
	 * @param adapterFactory
	 */
	public FilteringItemProviderAdapter(AdapterFactory adapterFactory) {
		super(adapterFactory);
		if (adapterFactory instanceof HenshinItemProviderAdapterFactory) {
			
			filterProvider = ((HenshinItemProviderAdapterFactory) adapterFactory)
					.getFeatureFilterProvider();
			filteringEnabled = filterProvider != null;
		}
	}
	
	protected Collection<? extends EStructuralFeature> getAnyChildrenFeatures(Object object) {
		Collection<? extends EStructuralFeature> result = getChildrenFeatures(object);
		if (filteringEnabled && !isWrappingNeeded(object))
			result = filterProvider.filterChildFeatures(result);
		else {
			//System.out.println("Filtering disabled");
		}
		return result.isEmpty() ? getChildrenReferences(object) : result;
	}
	
	@Override
	protected ChildrenStore getChildrenStore(Object object) {
		ChildrenStore store = super.getChildrenStore(object);
		// store.
		return store;
	}
	
	public Collection<?> getChildren(Object object) {
		ChildrenStore store = getChildrenStore(object);
		if (store != null) {
			return store.getChildren();
		}
		
		store = createChildrenStore(object);
		List<Object> result = store != null ? null : new ArrayList<Object>();
		EObject eObject = (EObject) object;
		
		for (EStructuralFeature feature : getAnyChildrenFeatures(object)) {
			if (feature.isMany()) {
				List<?> children = (List<?>) eObject.eGet(feature);
				int index = 0;
				for (Object unwrappedChild : children) {
					Object child = wrap(eObject, feature, unwrappedChild, index);
					if (store != null) {
						store.getList(feature).add(child);
					} else {
						result.add(child);
					}
					index++;
				}
			} else {
				Object child = eObject.eGet(feature);
				if (child != null) {
					child = wrap(eObject, feature, child, CommandParameter.NO_INDEX);
					if (store != null) {
						store.setValue(feature, child);
					} else {
						result.add(child);
					}
				}
			}
		}
		return store != null ? store.getChildren() : result;
	}
	
	protected boolean hasChildren(Object object, boolean optimized) {
		if (!optimized)
			return !getChildren(object).isEmpty();
		
		EObject eObject = (EObject) object;
		for (EStructuralFeature feature : getAnyChildrenFeatures(object)) {
			if (feature.isMany()) {
				List<?> children = (List<?>) eObject.eGet(feature);
				if (!children.isEmpty()) {
					return true;
				}
			} else if (eObject.eGet(feature, false) != null) {
				return true;
			}
		}
		
		return false;
	}
	
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// First, try an existing implementation of getChildReference(). This
		// provides backwards compatibility if that
		// method, now deprecated, was overridden.
		//
		EStructuralFeature oldFeature = getChildReference(object, child);
		if (oldFeature != null)
			return oldFeature;
		
		for (EStructuralFeature feature : getAnyChildrenFeatures(object)) {
			if (isValidValue(object, child, feature)) {
				return feature;
			}
		}
		return null;
	}
	
	public Collection<?> getNewChildDescriptors(Object object, EditingDomain editingDomain,
			Object sibling) {
		EObject eObject = (EObject) object;
		
		// Build the collection of new child descriptors.
		//
		Collection<Object> newChildDescriptors = new ArrayList<Object>();
		collectNewChildDescriptors(newChildDescriptors, object);
		
		// Add child descriptors contributed by extenders.
		//
		if (adapterFactory instanceof IChildCreationExtender) {
			newChildDescriptors.addAll(((IChildCreationExtender) adapterFactory)
					.getNewChildDescriptors(object, editingDomain));
		}
		
		// If a sibling has been specified, add the best index possible to each
		// CommandParameter.
		//
		if (sibling != null) {
			sibling = unwrap(sibling);
			
			// Find the index of a feature containing the sibling, or an
			// equivalent value, in the collection of children
			// features.
			//
			Collection<? extends EStructuralFeature> childrenFeatures = getAnyChildrenFeatures(object);
			int siblingFeatureIndex = -1;
			int i = 0;
			
			FEATURES_LOOP: for (EStructuralFeature feature : childrenFeatures) {
				Object featureValue = eObject.eGet(feature);
				if (feature.isMany()) {
					for (Object value : (Collection<?>) featureValue) {
						if (isEquivalentValue(sibling, value)) {
							siblingFeatureIndex = i;
							break FEATURES_LOOP;
						}
					}
				} else if (isEquivalentValue(sibling, featureValue)) {
					siblingFeatureIndex = i;
					break FEATURES_LOOP;
				}
				++i;
			}
			
			// For each CommandParameter with a non-null, multi-valued
			// structural feature...
			//
			DESCRIPTORS_LOOP: for (Object descriptor : newChildDescriptors) {
				if (descriptor instanceof CommandParameter) {
					CommandParameter parameter = (CommandParameter) descriptor;
					EStructuralFeature childFeature = parameter.getEStructuralFeature();
					if (childFeature == null || !childFeature.isMany()) {
						continue DESCRIPTORS_LOOP;
					}
					
					// Look for the sibling value or an equivalent in the new
					// child's feature. If it is found, the child should
					// immediately follow it.
					//
					i = 0;
					for (Object v : (Collection<?>) eObject.eGet(childFeature)) {
						if (isEquivalentValue(sibling, v)) {
							parameter.index = i + 1;
							continue DESCRIPTORS_LOOP;
						}
						++i;
					}
					
					// Otherwise, if a sibling feature was found, iterate
					// through the children features to find the index of
					// the child feature...
					//
					if (siblingFeatureIndex != -1) {
						i = 0;
						for (EStructuralFeature feature : childrenFeatures) {
							if (feature == childFeature) {
								// If the child feature follows the sibling
								// feature, the child should be first in its
								// feature.
								//
								if (i > siblingFeatureIndex) {
									parameter.index = 0;
								}
								continue DESCRIPTORS_LOOP;
							}
							++i;
						}
					}
				}
			}
		}
		return newChildDescriptors;
	}
	
	protected Command factorRemoveCommand(EditingDomain domain, CommandParameter commandParameter) {
		if (commandParameter.getCollection() == null || commandParameter.getCollection().isEmpty()) {
			return UnexecutableCommand.INSTANCE;
		}
		
		final EObject eObject = commandParameter.getEOwner();
		final List<Object> list = new ArrayList<Object>(commandParameter.getCollection());
		
		CompoundCommand removeCommand = new CompoundCommand(CompoundCommand.MERGE_COMMAND_ALL);
		
		// Iterator over all the child references to factor each child to the
		// right reference.
		//
		for (EStructuralFeature feature : getAnyChildrenFeatures(eObject)) {
			// If it is a list type value...
			//
			if (feature.isMany()) {
				List<?> value = (List<?>) getFeatureValue(eObject, feature);
				
				// These will be the children belonging to this feature.
				//
				Collection<Object> childrenOfThisFeature = new ArrayList<Object>();
				for (ListIterator<Object> objects = list.listIterator(); objects.hasNext();) {
					Object o = objects.next();
					
					// Is this object in this feature...
					//
					if (value.contains(o)) {
						// Add it to the list and remove it from the other list.
						//
						childrenOfThisFeature.add(o);
						objects.remove();
					}
				}
				
				// If we have children to remove for this feature, create a
				// command for it.
				//
				if (!childrenOfThisFeature.isEmpty()) {
					removeCommand.append(createRemoveCommand(domain, eObject, feature,
							childrenOfThisFeature));
				}
			} else {
				// It's just a single value
				//
				final Object value = getFeatureValue(eObject, feature);
				for (ListIterator<Object> objects = list.listIterator(); objects.hasNext();) {
					Object o = objects.next();
					
					// Is this object in this feature...
					//
					if (o == value) {
						// Create a command to unset this and remove the object
						// from the other list.
						//
						Command setCommand = createSetCommand(domain, eObject, feature,
								SetCommand.UNSET_VALUE);
						removeCommand.append(new CommandWrapper(setCommand) {
							protected Collection<?> affected;
							
							@Override
							public void execute() {
								super.execute();
								affected = Collections.singleton(eObject);
							}
							
							@Override
							public void undo() {
								super.undo();
								affected = Collections.singleton(value);
							}
							
							@Override
							public void redo() {
								super.redo();
								affected = Collections.singleton(eObject);
							}
							
							@Override
							public Collection<?> getResult() {
								return Collections.singleton(value);
							}
							
							@Override
							public Collection<?> getAffectedObjects() {
								return affected;
							}
						});
						objects.remove();
						break;
					}
				}
			}
		}
		
		// If all the objects are used up by the above, then we can't do the
		// command.
		//
		if (list.isEmpty()) {
			return removeCommand.unwrap();
		} else {
			removeCommand.dispose();
			return UnexecutableCommand.INSTANCE;
		}
	}
	
	protected Command factorAddCommand(EditingDomain domain, CommandParameter commandParameter) {
		if (commandParameter.getCollection() == null || commandParameter.getCollection().isEmpty()) {
			return UnexecutableCommand.INSTANCE;
		}
		
		final EObject eObject = commandParameter.getEOwner();
		final List<Object> list = new ArrayList<Object>(commandParameter.getCollection());
		int index = commandParameter.getIndex();
		
		CompoundCommand addCommand = new CompoundCommand(CompoundCommand.MERGE_COMMAND_ALL);
		
		while (!list.isEmpty()) {
			Iterator<Object> children = list.listIterator();
			final Object firstChild = children.next();
			EStructuralFeature childFeature = getChildFeature(eObject, firstChild);
			
			if (childFeature == null) {
				break;
			}
			// If it is a list type value...
			//
			else if (childFeature.isMany()) {
				// Correct the index, if necessary.
				//
				if (index != CommandParameter.NO_INDEX) {
					for (EStructuralFeature feature : getAnyChildrenFeatures(eObject)) {
						if (feature == childFeature) {
							break;
						}
						
						if (feature.isMany()) {
							index -= ((List<?>) (eObject).eGet(feature)).size();
						} else if (eObject.eGet(feature) != null) {
							index -= 1;
						}
					}
					if (index < 0) {
						break;
					}
				}
				
				// These will be the children belonging to this feature.
				//
				Collection<Object> childrenOfThisFeature = new ArrayList<Object>();
				childrenOfThisFeature.add(firstChild);
				children.remove();
				
				// Consume the rest of the appropriate children.
				//
				while (children.hasNext()) {
					Object child = children.next();
					
					// Is this child in this feature...
					//
					if (getChildFeature(eObject, child) == childFeature) {
						// Add it to the list and remove it from the other list.
						//
						childrenOfThisFeature.add(child);
						children.remove();
					}
				}
				
				// Create a command for this feature,
				//
				addCommand.append(createAddCommand(domain, eObject, childFeature,
						childrenOfThisFeature, index));
				
				if (index >= childrenOfThisFeature.size()) {
					index -= childrenOfThisFeature.size();
				} else {
					index = CommandParameter.NO_INDEX;
				}
			} else if (eObject.eGet(childFeature) == null) {
				Command setCommand = createSetCommand(domain, eObject, childFeature, firstChild);
				addCommand.append(new CommandWrapper(setCommand) {
					protected Collection<?> affected;
					
					@Override
					public void execute() {
						super.execute();
						affected = Collections.singleton(firstChild);
					}
					
					@Override
					public void undo() {
						super.undo();
						affected = Collections.singleton(eObject);
					}
					
					@Override
					public void redo() {
						super.redo();
						affected = Collections.singleton(firstChild);
					}
					
					@Override
					public Collection<?> getResult() {
						return Collections.singleton(firstChild);
					}
					
					@Override
					public Collection<?> getAffectedObjects() {
						return affected;
					}
				});
				children.remove();
			} else {
				break;
			}
		}
		
		// If all the objects aren't used up by the above, then we can't do the
		// command.
		//
		if (list.isEmpty()) {
			return addCommand.unwrap();
		} else {
			addCommand.dispose();
			return UnexecutableCommand.INSTANCE;
		}
	}
	
	protected Command factorMoveCommand(EditingDomain domain, CommandParameter commandParameter) {
		final EObject eObject = commandParameter.getEOwner();
		final Object value = commandParameter.getValue();
		int index = commandParameter.getIndex();
		
		EStructuralFeature childFeature = getChildFeature(eObject, value);
		
		if (childFeature != null && childFeature.isMany()) {
			// Compute the relative index as best as possible.
			//
			for (EStructuralFeature feature : getAnyChildrenFeatures(eObject)) {
				if (feature == childFeature) {
					break;
				}
				
				if (feature.isMany()) {
					index -= ((List<?>) (eObject).eGet(feature)).size();
				} else if (eObject.eGet(feature) != null) {
					index -= 1;
				}
			}
			
			// Create a command for this feature,
			//
			return createMoveCommand(domain, eObject, childFeature, value, index);
		} else {
			return UnexecutableCommand.INSTANCE;
		}
	}
	
	protected boolean isWrappingNeeded(Object object) {
		if (wrappingNeeded == null) {
			wrappingNeeded = Boolean.FALSE;
			
			for (EStructuralFeature f : getAnyChildrenFeatures(object)) {
				if (f instanceof EAttribute) {
					wrappingNeeded = Boolean.TRUE;
				}
			}
		}
		return wrappingNeeded;
	}
	
	protected ChildrenStore createChildrenStore(Object object) {
		ChildrenStore store = null;
		
		if (isWrappingNeeded(object)) {
			if (childrenStoreMap == null) {
				childrenStoreMap = new HashMap<Object, ChildrenStore>();
			}
			store = new FilteringChildrenStore(getAnyChildrenFeatures(object), filterProvider);
			childrenStoreMap.put(object, store);
		}
		return store;
	}
	
	protected static class FilteringChildrenStore extends ChildrenStore {
		
		protected BaseFilterProvider filterProvider;
		
		public FilteringChildrenStore(Collection<? extends EStructuralFeature> childrenFeatures,
				BaseFilterProvider filterProvider) {
			super(childrenFeatures);
			this.filterProvider = filterProvider;
		}
		
		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.emf.edit.provider.ItemProviderAdapter.ChildrenStore#get
		 * (org.eclipse.emf.ecore.EStructuralFeature, int)
		 */
		@Override
		public Object get(EStructuralFeature feature, int index) {
			System.out.println("simple get");
			return super.get(feature, index);
		}
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter.ChildrenStore#
		 * getChildren()
		 */
		@Override
		public List<Object> getChildren() {
			int size = 0;
			for (int i = 0; i < entries.length; i++) {
				if (entries[i].list != null
						&& !filterProvider.isFiltered(entries[i].feature.getEType())) {
					size += entries[i].feature.isMany() ? entries[i].list.size() : entries[i].list
							.get(0) != null ? 1 : 0;
				}
			}
			List<Object> result = new ArrayList<Object>(size);
			for (int i = 0; i < entries.length; i++) {
				if (entries[i].list != null
						&& !filterProvider.isFiltered(entries[i].feature.getEType())) {
					if (entries[i].feature.isMany()) {
						result.addAll(entries[i].list);
					} else if (entries[i].list.get(0) != null) {
						result.add(entries[i].list.get(0));
					}
				}
			}
			return result;
		}
	}
	
}
