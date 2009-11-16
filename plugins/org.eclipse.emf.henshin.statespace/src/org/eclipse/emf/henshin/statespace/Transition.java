package org.eclipse.emf.henshin.statespace;

import org.eclipse.emf.henshin.statespace.properties.PropertyAdapter;

/**
 * Light-weight transition model.
 * @author Christian Krause
 */
public class Transition extends PropertyAdapter {
	
	// Source and target states:
	private State source, target;
	
	// Transition name:
	private String name;

	// Transition properties:
	public static final int TRANSITION_NAME = 7;
	
	/**
	 * Default constructor.
	 * @param source Source of the transition.
	 * @param target Target of the transition.
	 * @param name Name of the transition.
	 */
	public Transition(State source, State target, String name) {
		reconnect(source, target);
		this.name = name;
	}
	
	public void disconnect() {
		if (source!=null) source.getOutgoing().remove(this);
		if (target!=null) target.getIncoming().remove(this);
	}

	public State getSource() {
		return source;
	}

	public State getTarget() {
		return target;
	}
	
	public String getName() {
		return name;
	}
	
	public void reconnect() {
		if (!source.getOutgoing().contains(this)) {
			source.getOutgoing().add(this);
		}
		if (!target.getIncoming().contains(this)) {
			target.getIncoming().add(this);
		}
	}
	
	public void reconnect(State source, State target) {
		if (source==null || target==null) {
			throw new IllegalArgumentException();
		}
		disconnect();
		this.source = source;
		this.target = target;
		reconnect();
	}
	
}