package org.eclipse.emf.henshin.variability.wrapper;

import org.eclipse.emf.henshin.model.GraphElement;

/**
 * A representation of variability-aware graph elements.
 * 
 * @author Stefan Schulz
 *
 */
public interface VariabilityGraphElement extends GraphElement {
	public String getPresenceCondition();
	public void setPresenceCondition(String pc);
	public GraphElement getGraphElement();
}
