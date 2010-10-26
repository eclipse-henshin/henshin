/**
 * 
 */
package org.eclipse.emf.henshin.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.DragAndDropCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * This class tries to introduce a feature given from the beginning into the
 * drag'n'drop process. Therefore, any method creating commands with setting
 * feature to <code>null</code> are overridden creating command with the given
 * feature at hand.
 * 
 * 
 * @author Stefan Jurack (sjurack)
 * 
 */
public class DragAndDropWithFeatureCommand extends DragAndDropCommand {
	
	protected EReference feature;
	
	/**
	 * @param domain
	 * @param owner
	 * @param location
	 * @param operations
	 * @param operation
	 * @param collection
	 */
	public DragAndDropWithFeatureCommand(EditingDomain domain, Object owner, EReference feature,
			float location, int operations, int operation, Collection<?> collection) {
		super(domain, owner, location, operations, operation, collection);
		this.feature = feature;
	}
	
	/**
	 * @param domain
	 * @param owner
	 * @param location
	 * @param operations
	 * @param operation
	 * @param collection
	 * @param optimize
	 */
	public DragAndDropWithFeatureCommand(EditingDomain domain, Object owner, EReference feature,
			float location, int operations, int operation, Collection<?> collection,
			boolean optimize) {
		super(domain, owner, location, operations, operation, collection, optimize);
		this.feature = feature;
	}
	
	/**
	 * @return the feature
	 */
	public EReference getFeature() {
		return feature;
	}
	
	/**
	 * This attempts to prepare a drop move insert operation.
	 */
	protected boolean prepareDropMoveInsert(Object parent, Collection<?> children, int index) {
		// We don't want to move insert an object before or after itself...
		//
		if (collection.contains(owner)) {
			dragCommand = IdentityCommand.INSTANCE;
			dropCommand = UnexecutableCommand.INSTANCE;
		}
		// If the dragged objects share a parent...
		//
		else if (children.containsAll(collection)) {
			dragCommand = IdentityCommand.INSTANCE;
			
			// Create move commands for all the objects in the collection.
			//
			CompoundCommand compoundCommand = new CompoundCommand();
			List<Object> before = new ArrayList<Object>();
			List<Object> after = new ArrayList<Object>();
			
			int j = 0;
			for (Object object : children) {
				if (collection.contains(object)) {
					if (j < index) {
						before.add(object);
					} else if (j > index) {
						after.add(object);
					}
				}
				++j;
			}
			
			for (Object object : before) {
				compoundCommand.append(MoveCommand.create(domain, parent, feature, object,
						index - 1));
			}
			
			for (ListIterator<Object> objects = after.listIterator(after.size()); objects
					.hasPrevious();) {
				Object object = objects.previous();
				compoundCommand.append(MoveCommand.create(domain, parent, feature, object, index));
			}
			
			dropCommand = compoundCommand.getCommandList().size() == 0 ? (Command) IdentityCommand.INSTANCE
					: compoundCommand;
		} else if (isCrossDomain()) {
			dragCommand = IdentityCommand.INSTANCE;
			dropCommand = UnexecutableCommand.INSTANCE;
		} else {
			// Just remove the objects and add them.
			//
			dropCommand = AddCommand.create(domain, parent, feature, collection, index);
			if (analyzeForNonContainment(dropCommand)) {
				dropCommand.dispose();
				dropCommand = UnexecutableCommand.INSTANCE;
				dragCommand = IdentityCommand.INSTANCE;
			} else {
				dragCommand = RemoveCommand.create(domain, collection);
			}
		}
		
		boolean result = dragCommand.canExecute() && dropCommand.canExecute();
		return result;
	}// prepareDropMoveInsert
	
	/**
	 * This attempts to prepare a drop copy insert operation.
	 */
	protected boolean prepareDropCopyInsert(final Object parent, Collection<?> children,
			final int index) {
		boolean result;
		
		// We don't want to copy insert an object before or after itself...
		//
		if (collection.contains(owner)) {
			dragCommand = IdentityCommand.INSTANCE;
			dropCommand = UnexecutableCommand.INSTANCE;
			result = false;
		} else {
			// Copy the collection
			//
			dragCommand = CopyCommand.create(domain, collection);
			
			if (optimize) {
				result = optimizedCanExecute();
				if (result) {
					optimizedDropCommandOwner = parent;
				}
			} else {
				if (dragCommand.canExecute() && dragCommand.canUndo()) {
					dragCommand.execute();
					isDragCommandExecuted = true;
					
					// And add the copy.
					//
					dropCommand = AddCommand.create(domain, parent, feature,
							dragCommand.getResult(), index);
					if (analyzeForNonContainment(dropCommand)) {
						dropCommand.dispose();
						dropCommand = UnexecutableCommand.INSTANCE;
						
						dragCommand.undo();
						dragCommand.dispose();
						isDragCommandExecuted = false;
						dragCommand = IdentityCommand.INSTANCE;
					}
					result = dropCommand.canExecute();
				} else {
					dropCommand = UnexecutableCommand.INSTANCE;
					result = false;
				}
			} // if optimize
		} // if collection
		
		return result;
	}// prepareDropCopyInsert
	
	/**
	 * This attempts to prepare a drop link insert operation.
	 */
	protected boolean prepareDropLinkInsert(Object parent, Collection<?> children, int index) {
		boolean result;
		
		// We don't want to insert an object before or after itself...
		//
		if (collection.contains(owner)) {
			dragCommand = IdentityCommand.INSTANCE;
			dropCommand = UnexecutableCommand.INSTANCE;
			result = false;
		} else {
			dragCommand = IdentityCommand.INSTANCE;
			
			// Add the collection
			//
			dropCommand = AddCommand.create(domain, parent, feature, collection, index);
			if (!analyzeForNonContainment(dropCommand)) {
				dropCommand.dispose();
				dropCommand = UnexecutableCommand.INSTANCE;
			}
			result = dropCommand.canExecute();
		}
		
		return result;
	}// prepareDropLinkInsert
	
	/**
	 * This attempts to prepare a drop move on operation.
	 */
	protected boolean prepareDropMoveOn() {
		if (isCrossDomain()) {
			dragCommand = IdentityCommand.INSTANCE;
			dropCommand = UnexecutableCommand.INSTANCE;
		} else {
			dropCommand = AddCommand.create(domain, owner, feature, collection);
			if (analyzeForNonContainment(dropCommand)) {
				dropCommand.dispose();
				dropCommand = UnexecutableCommand.INSTANCE;
				dragCommand = IdentityCommand.INSTANCE;
			} else {
				dragCommand = RemoveCommand.create(domain, collection);
			}
		}
		
		boolean result = dragCommand.canExecute() && dropCommand.canExecute();
		return result;
	}// prepareDropMoveOn
	
	/**
	 * This attempts to prepare a drop copy on operation.
	 */
	protected boolean prepareDropCopyOn() {
		boolean result;
		
		dragCommand = CopyCommand.create(domain, collection);
		
		if (optimize) {
			result = optimizedCanExecute();
			if (result) {
				optimizedDropCommandOwner = owner;
			}
		} else {
			if (dragCommand.canExecute() && dragCommand.canUndo()) {
				dragCommand.execute();
				isDragCommandExecuted = true;
				dropCommand = AddCommand.create(domain, owner, feature, dragCommand.getResult());
				if (analyzeForNonContainment(dropCommand)) {
					dropCommand.dispose();
					dropCommand = UnexecutableCommand.INSTANCE;
					
					dragCommand.undo();
					dragCommand.dispose();
					isDragCommandExecuted = false;
					dragCommand = IdentityCommand.INSTANCE;
				}
			} else {
				dropCommand = UnexecutableCommand.INSTANCE;
			}
			
			result = dragCommand.canExecute() && dropCommand.canExecute();
		}
		return result;
	}// prepareDropCopyOn
	
	/**
	 * This attempts to prepare a drop link on operation.
	 */
	protected boolean prepareDropLinkOn() {
		dragCommand = IdentityCommand.INSTANCE;
		dropCommand = SetCommand.create(domain, owner, feature, collection);
		
		// If we can't set the collection, try setting use the single value of
		// the collection.
		//
		if (!dropCommand.canExecute() && collection.size() == 1) {
			dropCommand.dispose();
			dropCommand = SetCommand.create(domain, owner, feature, collection.iterator().next());
		}
		
		if (!dropCommand.canExecute() || !analyzeForNonContainment(dropCommand)) {
			dropCommand.dispose();
			dropCommand = AddCommand.create(domain, owner, feature, collection);
			if (!analyzeForNonContainment(dropCommand)) {
				dropCommand.dispose();
				dropCommand = UnexecutableCommand.INSTANCE;
			}
		}
		
		boolean result = dropCommand.canExecute();
		return result;
	}// prepareDropLinkOn
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.command.DragAndDropCommand#optimizedCanExecute()
	 */
	protected boolean optimizedCanExecute() {
		// We'll assume that the copy command can execute and that adding a copy
		// of the clipboard
		// is the same test as adding the clipboard contents itself.
		//
		Command addCommand = AddCommand.create(domain, owner, feature, collection);
		boolean result = addCommand.canExecute() && !analyzeForNonContainment(addCommand);
		addCommand.dispose();
		return result;
	}// optimizedCanExecute
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.edit.command.DragAndDropCommand#execute()
	 */
	public void execute() {
		if (dragCommand != null && !isDragCommandExecuted) {
			// special case in optimized mode, the drag command wasn't executed,
			// so drag command was not yet created
			// if ((optimizedDropCommandOwner != null) && (dropCommand == null))
			// {
			if (optimizedDropCommandOwner != null) {
				if (dragCommand.canExecute()) {
					dragCommand.execute();
					dropCommand = AddCommand.create(domain, optimizedDropCommandOwner, feature,
							dragCommand.getResult());
				} else {
					// Thread.dumpStack();
				}
			} else {
				dragCommand.execute();
			}
		}
		
		if (dropCommand != null) {
			if (dropCommand.canExecute()) {
				dropCommand.execute();
			} else {
				// Thread.dumpStack();
			}
		}
	}// execute
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.edit.command.DragAndDropCommand#prepare()
	 */
	@Override
	protected boolean prepare() {
		// We'll default to this.
		//
		boolean result = false;
		
		// If there isn't something obviously wrong with the arguments...
		//
		if (owner != null && collection != null && operations != DROP_NONE
				&& operation != DROP_NONE) {
			
			// If the feature has cardinality *, we'll start by trying to do
			// a drop insert.
			//
			if (feature.isMany()) {
				// If we could do a drop insert operation...
				//
				result = prepareDropInsert();
				if (result) {
					// Set the bounds so that we re-check when we are closer to
					// the middle.
					//
					if (location <= 0.20) {
						lowerLocationBound = 0.0F;
						upperLocationBound = 0.2F;
					} else {
						lowerLocationBound = 0.8F;
						upperLocationBound = 1.0F;
					}
				} else {
					// We can try to do a drop on instead.
					//
					reset();
					result = prepareDropOn();
					
					// Set the bounds so that we re-check when we get near the
					// other end.
					//
					if (location <= 0.20) {
						lowerLocationBound = 0.0F;
						upperLocationBound = 0.8F;
					} else {
						lowerLocationBound = 0.2F;
						upperLocationBound = 1.0F;
					}
				}
			}
			// We are near the middle, so we'll start by trying to do a drop on.
			//
			else {
				// If we can do a drop on operation.
				//
				result = prepareDropOn();
				if (result) {
					// Set the range so that we re-check when we get aren't in
					// the middle.
					//
					lowerLocationBound = 0.2F;
					upperLocationBound = 0.8F;
				} else {
					// We can reset and try a drop insert instead.
					//
					reset();
					result = prepareDropInsert();
					
					// Set the range so that we re-check when we get into the
					// other half.
					//
					if (location <= 0.50) {
						lowerLocationBound = 0.0F;
						upperLocationBound = 0.5F;
					} else {
						lowerLocationBound = 0.5F;
						upperLocationBound = 1.0F;
					}
				}
			}
		} else {
			// We'll always be wrong for these arguments, so don't bother
			// re-checking.
			//
			lowerLocationBound = 0.0F;
			upperLocationBound = 1.0F;
		}
		
		return result;
	}// prepare
	
}// class
