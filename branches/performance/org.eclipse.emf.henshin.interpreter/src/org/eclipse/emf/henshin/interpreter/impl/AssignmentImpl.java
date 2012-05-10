package org.eclipse.emf.henshin.interpreter.impl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.henshin.interpreter.Assignment;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * Default {@link Assignment} implementation.
 * 
 * @author Christian Krause
 */
public class AssignmentImpl implements Assignment {

	// The target transformation unit: 
	protected final TransformationUnit unit;
	
	// Map for storing the assigned values:
	protected final Map<Object,Object> values;
	
	/**
	 * Default constructor.
	 */
	public AssignmentImpl(TransformationUnit unit) {
		if (unit==null) {
			throw new NullPointerException("Transformation unit cannot be null");
		}
		this.unit = unit;
		this.values = new HashMap<Object,Object>();
	}
	
	/**
	 * Constructor which copies an assignment.
	 * @param assignment Assignment to be copied.
	 */
	public AssignmentImpl(Assignment assignment) {
		this(assignment.getUnit());
		for (Parameter param : unit.getParameters()) {
			setParameterValue(param, assignment.getParameterValue(param));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Assignment#getUnit()
	 */
	@Override
	public TransformationUnit getUnit() {
		return unit;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Assignment#getParameterValue(org.eclipse.emf.henshin.model.Parameter)
	 */
	@Override
	public Object getParameterValue(Parameter param) {
		return values.get(param);
	}

	/*
	 * Set a value or remove a key.
	 */
	protected void setValue(Object key, Object value) {
		if (value==null) {
			values.remove(key);
		} else {
			values.put(key, value);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Assignment#setParameterValue(org.eclipse.emf.henshin.model.Parameter, java.lang.Object)
	 */
	@Override
	public void setParameterValue(Parameter param, Object value) {
		setValue(param, value);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Assignment#clear()
	 */
	@Override
	public void clear() {
		values.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return values.hashCode();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Assignment) {
			Assignment a = (Assignment) obj;
			if (unit!=a.getUnit()) {
				return false;
			}
			for (Parameter param : unit.getParameters()) {
				Object v1 = values.get(param);
				Object v2 = a.getParameterValue(param);
				if ((v1==null && v2!=null) || (v1!=null && !v1.equals(v2))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
}
