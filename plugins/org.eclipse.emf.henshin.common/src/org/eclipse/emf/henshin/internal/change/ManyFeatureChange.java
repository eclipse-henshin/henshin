package org.eclipse.emf.henshin.internal.change;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Stores changes to a many-reference.
 */
public class ManyFeatureChange extends FeatureChange {
    private List<ListElement> removedElements = new ArrayList<ListElement>();
    private List<ListElement> addedElements = new ArrayList<ListElement>();

    private List<Object> valueList;

    public ManyFeatureChange(EStructuralFeature target, List<Object> valueList) {
	this.target = target;
	this.valueList = valueList;
    }

    public void update(Object newValue) {
	if (valueList.contains(newValue))
	    removedElements.add(new ListElement(valueList, newValue));
	else
	    addedElements.add(new ListElement(valueList, newValue));
    }

    public void execute() {
	for (ListElement listElement : removedElements)
	    valueList.remove(listElement.getValue());

	for (ListElement listElement : addedElements)
	    valueList.add(listElement.getValue());
    }

    public void undo() {
	Collections.sort(removedElements);

	for (ListElement listElement : addedElements)
	    valueList.remove(listElement.getValue());

	for (ListElement listElement : removedElements)
	    valueList.add(listElement.getIndex(), listElement.getValue());
    }
}
