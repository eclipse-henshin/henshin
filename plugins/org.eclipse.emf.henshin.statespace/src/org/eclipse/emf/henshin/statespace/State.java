package org.eclipse.emf.henshin.statespace;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.properties.NotifyingList;
import org.eclipse.emf.henshin.statespace.properties.PropertyAdapter;

/**
 * @author Christian Krause
 */
public class State extends PropertyAdapter {
	
	// State properties:
	public static final int INCOMING = 2;
	public static final int OUTGOING = 3;
	public static final int NAME = 4;
	public static final int RESOURCE = 5;
	public static final int LOCATION = 6;

	
	// Location coordinates:
	private int x=0, y=0;
	
	// Name of this state:
	private String name;
	
	// Incoming and outgoing transitions:
	private List<Transition> incoming, outgoing;
	
	// Resource model associated to this state:
	private Resource resource;
	
	/**
	 * Default constructor.
	 */
	public State() {
		// Initialize list of incoming / outgoing transitions:
		outgoing = new NotifyingList<Transition>(this, OUTGOING);
		incoming = new NotifyingList<Transition>(this, INCOMING);
	}

	/**
	 * Convenience constructor.
	 * @param name Name of the state.
	 */
	public State(String name) {
		this();
		this.name = name;
	}
	
	/**
	 * Get the list of outgoing transitions.
	 * @return Outgoing transitions.
	 */
	public List<Transition> getOutgoing() {
		return outgoing;
	}
	
	/**
	 * Get the list of incoming transitions.
	 * @return Incoming transitions.
	 */
	public List<Transition> getIncoming() {
		return incoming;
	}
	
	/**
	 * Get the name of this state.
	 * @return State name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of this state.
	 * @param name State name.
	 */
	public void setName(String name) {
		this.name = name;
		firePropertyChange(NAME, name);
	}
	
	/**
	 * Get the resource model associated to this state.
	 * @return The resource model (can be <code>null</code>).
	 */
	public Resource getResource() {
		return resource;
	}

	/**
	 * Set the resource model associated to this state.
	 * @param resource The resource model (can be <code>null</code>).
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
		firePropertyChange(RESOURCE, resource);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		setXY(x,y);
	}

	public void setY(int y) {
		setXY(x,y);
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
		firePropertyChange(LOCATION, null);
	}
	
}