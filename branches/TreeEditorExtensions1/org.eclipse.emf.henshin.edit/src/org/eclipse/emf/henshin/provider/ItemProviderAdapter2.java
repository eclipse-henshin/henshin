/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.CreateCopyCommand;
import org.eclipse.emf.edit.command.DragAndDropCommand;
import org.eclipse.emf.edit.command.InitializeCopyCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.ReplaceCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IWrapperItemProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.impl.TransformationSystemImpl;

/**
 * This class is a attempt to fix a problem of the ItemProviderAdapter
 * concerning the handling of non-containment references of a node heading
 * another node, e.g. non-containment references <code>kernelRule</code> and
 * <code>multiRules</code> run from class {@link AmalgamationUnit} to class
 * {@link Rule}.<br>
 * <br>
 * Remark: This implementation contains bugs yet but will hopefully be fixed
 * soon.
 * 
 * 
 * @author Stefan Jurack (sjurack)
 * 
 */
public class ItemProviderAdapter2 extends ItemProviderAdapter {

	/**
	 * @param adapterFactory
	 */
	public ItemProviderAdapter2(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}// constructor

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createCommand(java.
	 * lang.Object, org.eclipse.emf.edit.domain.EditingDomain, java.lang.Class,
	 * org.eclipse.emf.edit.command.CommandParameter)
	 */
	@Override
	public Command createCommand(Object object, EditingDomain domain,
			Class<? extends Command> commandClass, CommandParameter commandParameter) {

		// Commands should operate on the values, not their wrappers. If the
		// command's values needed to be unwrapped,
		// we'll back get a new CommandParameter.
		//
		CommandParameter oldCommandParameter = commandParameter;
		commandParameter = unwrapCommandValues(commandParameter, commandClass);

		Command result = UnexecutableCommand.INSTANCE;

		// System.out.println("createCommand: " + object + " -- " + commandClass.getCanonicalName());

		if (commandClass == SetCommand.class) {
			result = createSetCommand(
					domain,
					commandParameter.getEOwner(),
					commandParameter.getEStructuralFeature() != null ? commandParameter
							.getEStructuralFeature() : getSetFeature(commandParameter.getEOwner(),
							commandParameter.getValue()), commandParameter.getValue(),
					commandParameter.getIndex());

		} else if (commandClass == CopyCommand.class) {
			result = createCopyCommand(domain, commandParameter.getEOwner(),
					(CopyCommand.Helper) commandParameter.getValue());

		} else if (commandClass == CreateCopyCommand.class) {
			result = createCreateCopyCommand(domain, commandParameter.getEOwner(),
					(CopyCommand.Helper) commandParameter.getValue());

		} else if (commandClass == InitializeCopyCommand.class) {
			result = createInitializeCopyCommand(domain, commandParameter.getEOwner(),
					(CopyCommand.Helper) commandParameter.getValue());

		} else if (commandClass == RemoveCommand.class) {
			if (commandParameter.getEStructuralFeature() != null) {
				result = createRemoveCommand(domain, commandParameter.getEOwner(),
						commandParameter.getEStructuralFeature(), commandParameter.getCollection());
			} else {
				// the following line differs from the original
				result = factorRemoveCommand(domain, oldCommandParameter);
			}
		} else if (commandClass == AddCommand.class) {
			if (commandParameter.getEStructuralFeature() != null) {
				result = createAddCommand(domain, commandParameter.getEOwner(),
						commandParameter.getEStructuralFeature(), commandParameter.getCollection(),
						commandParameter.getIndex());
			} else {
				// the following line differs from the original
				result = factorAddCommand(domain, oldCommandParameter);
			}
		} else if (commandClass == MoveCommand.class) {
			boolean b = (object instanceof TransformationSystemImpl);
			if (commandParameter.getEStructuralFeature() != null) {
				result = createMoveCommand(domain, commandParameter.getEOwner(),
						commandParameter.getEStructuralFeature(), commandParameter.getValue(),
						commandParameter.getIndex());
			} else {
				result = factorMoveCommand(domain, commandParameter);
			}
		} else if (commandClass == ReplaceCommand.class) {
			result = createReplaceCommand(domain, commandParameter.getEOwner(),
					commandParameter.getEStructuralFeature(),
					(EObject) commandParameter.getValue(), commandParameter.getCollection());

		} else if (commandClass == DragAndDropCommand.class) {
			DragAndDropCommand.Detail detail = (DragAndDropCommand.Detail) commandParameter
					.getFeature();
			result = createDragAndDropCommand(domain, commandParameter.getOwner(), detail.location,
					detail.operations, detail.operation, commandParameter.getCollection());

		} else if (commandClass == CreateChildCommand.class) {
			CommandParameter newChildParameter = (CommandParameter) commandParameter.getValue();
			result = createCreateChildCommand(domain, commandParameter.getEOwner(),
					newChildParameter.getEStructuralFeature(), newChildParameter.getValue(),
					newChildParameter.getIndex(), commandParameter.getCollection());
		}

		// If necessary, get a command that replaces unwrapped values by their
		// wrappers in the result and affected objects.
		//
		return wrapCommand(result, object, commandClass, commandParameter, oldCommandParameter);

	}// createCommand

	/**
	 * 
	 * If the given collectionItem implements {@link IWrapperItemProvider}, it
	 * is unwrapped by obtaining a value from
	 * {@link IWrapperItemProvider#getValue getValue}. The unwrapping continues
	 * until a non-wrapper value is returned. This iterative unwrapping is
	 * required because values may be repeatedly wrapped, as children of a
	 * delegating wrapper. Furthermore, the feature contained in the wrapper is
	 * extracted and used to create a CommandParameter.<br>
	 * This is a workaround, see <a
	 * href="http://www.eclipse.org/forums/index.php?t=msg&th=172187&start=0"
	 * >this discussion</a>.
	 * 
	 * @param owner
	 * @param feature
	 * @param collectionItem
	 * @return
	 */
	private CommandParameter unwrapItemAndCreateCommandParameter(CommandParameter oldCP,
			Object collectionItem) {
		CommandParameter cp = new CommandParameter(oldCP.owner, oldCP.feature, oldCP.value,
				oldCP.index);
		// iter
		while (collectionItem instanceof IWrapperItemProvider) {
			IWrapperItemProvider wrapper = (IWrapperItemProvider) collectionItem;
			collectionItem = wrapper.getValue();
			if ((cp.feature == null) && (wrapper.getFeature() != null))
				cp.feature = wrapper.getFeature();
		}// while
		cp.collection = Collections.singletonList(collectionItem);
		return cp;
	}// unwrapItemAndCreateCommandParameter

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#factorRemoveCommand
	 * (org.eclipse.emf.edit.domain.EditingDomain,
	 * org.eclipse.emf.edit.command.CommandParameter)
	 */
	@Override
	protected Command factorRemoveCommand(EditingDomain domain, CommandParameter commandParameter) {

		if (commandParameter.getCollection() == null || commandParameter.getCollection().isEmpty()) {
			return UnexecutableCommand.INSTANCE;
		}

		final EObject eObject = commandParameter.getEOwner();
		List<Object> list = new ArrayList<Object>(commandParameter.getCollection());
		CompoundCommand removeCommand = new CompoundCommand(CompoundCommand.MERGE_COMMAND_ALL);

		Iterator<Object> listIt = list.iterator();
		while (listIt.hasNext()) {
			Object item = listIt.next();
			EStructuralFeature feature = null;

			if ((commandParameter.feature == null) && (item instanceof IWrapperItemProvider)) {
				IWrapperItemProvider wrapper = (IWrapperItemProvider) item;
				feature = wrapper.getFeature();
				item = unwrap(item);
				if (feature.isMany()) {
					removeCommand.append(createRemoveCommand(domain, eObject, feature,
							Collections.singleton(item)));
				} else {
					final Object value = item;
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
				}// if else
				listIt.remove();
			}// if

		}// while

		if (!list.isEmpty()) {

			// Iterator over all the child references to factor each child to
			// the
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
							// Add it to the list and remove it from the other
							// list.
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
							// Create a command to unset this and remove the
							// object
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
				}// if (many) else
			}// for (EStructuralFeature feature

		}// if (!list.isEmpty())

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#factorAddCommand(org
	 * .eclipse.emf.edit.domain.EditingDomain,
	 * org.eclipse.emf.edit.command.CommandParameter)
	 */
	@Override
	protected Command factorAddCommand(EditingDomain domain, CommandParameter commandParameter) {

		if (commandParameter.getCollection() == null || commandParameter.getCollection().isEmpty()) {
			return UnexecutableCommand.INSTANCE;
		}// if

		final EObject eObject = commandParameter.getEOwner();
		final List<Object> list = new ArrayList<Object>(commandParameter.getCollection());
		int index = commandParameter.getIndex();

		CompoundCommand addCommand = new CompoundCommand(CompoundCommand.MERGE_COMMAND_ALL);

		// Iterator<Object> listIt = list.iterator();
		// while (listIt.hasNext()) {
		// Object item = listIt.next();
		// EStructuralFeature feature = null;
		//
		// if ((commandParameter.feature == null) && (item instanceof
		// IWrapperItemProvider)) {
		// IWrapperItemProvider wrapper = (IWrapperItemProvider) item;
		// feature = wrapper.getFeature();
		// item = unwrap(item);
		// if (feature.isMany()) {
		// removeCommand.append(createRemoveCommand(domain, eObject, feature,
		// Collections.singleton(item)));
		// } else {
		// final Object value = item;
		// Command setCommand = createSetCommand(domain, eObject, feature,
		// SetCommand.UNSET_VALUE);
		// removeCommand.append(new CommandWrapper(setCommand) {
		// protected Collection<?> affected;
		//
		// @Override
		// public void execute() {
		// super.execute();
		// affected = Collections.singleton(eObject);
		// }
		//
		// @Override
		// public void undo() {
		// super.undo();
		// affected = Collections.singleton(value);
		// }
		//
		// @Override
		// public void redo() {
		// super.redo();
		// affected = Collections.singleton(eObject);
		// }
		//
		// @Override
		// public Collection<?> getResult() {
		// return Collections.singleton(value);
		// }
		//
		// @Override
		// public Collection<?> getAffectedObjects() {
		// return affected;
		// }
		// });
		// }// if else
		// listIt.remove();
		// }// if
		//
		// }// while

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#factorMoveCommand(org
	 * .eclipse.emf.edit.domain.EditingDomain,
	 * org.eclipse.emf.edit.command.CommandParameter)
	 */
	@Override
	protected Command factorMoveCommand(EditingDomain domain, CommandParameter commandParameter) {
		// TODO Auto-generated method stub
		return super.factorMoveCommand(domain, commandParameter);
	}

	/**
	 * Copied from ItemProviderAdapter.getAnyChildrenFeatures.
	 * 
	 * @param object
	 * @return
	 */
	private Collection<? extends EStructuralFeature> getAnyChildrenFeatures(Object object) {
		Collection<? extends EStructuralFeature> result = getChildrenFeatures(object);
		return result.isEmpty() ? getChildrenReferences(object) : result;
	}

}// class
