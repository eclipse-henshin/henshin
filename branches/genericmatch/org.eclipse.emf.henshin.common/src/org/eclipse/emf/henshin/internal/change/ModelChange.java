package org.eclipse.emf.henshin.internal.change;

import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

public interface ModelChange<TNode> {
	public void addCreatedObject(TNode eObject);
    
    public void addDeletedObject(TNode eObject);
    
    public void addObjectChange(TNode object, EStructuralFeature feature,
            Object newValue);

    public void applyChanges();

    public void undoChanges();

    public void redoChanges();

	/**
	 * @return the createdObjects
	 */
	public List<TNode> getCreatedObjects();

	/**
	 * @return the deletedObjects
	 */
	public List<TNode> getDeletedObjects();
}
