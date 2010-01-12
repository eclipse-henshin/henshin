package org.eclipse.emf.henshin.internal.change;

import java.util.List;

public class ListElement implements Comparable<ListElement> {
    private int index;
    private Object value;

    ListElement(List<Object> list, Object value) {
	this.index = list.indexOf(value);
	this.value = value;
    }

    @Override
    public int compareTo(ListElement arg0) {
	return index - arg0.getIndex();
    }

    /**
     * @return the index
     */
    public int getIndex() {
	return index;
    }

    /**
     * @return the value
     */
    public Object getValue() {
	return value;
    }
}
