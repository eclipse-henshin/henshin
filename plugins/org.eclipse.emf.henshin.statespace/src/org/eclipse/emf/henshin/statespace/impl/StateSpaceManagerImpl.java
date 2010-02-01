package org.eclipse.emf.henshin.statespace.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSearch;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSearch.Path;

/**
 * Default state space manager implementation.
 * 
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceManagerImpl extends AbstractStateSpaceManagerWithIndex {
	
	// Default memory usage: 10%
	public static final double DEFAULT_MEMORY_USAGE = 1;
	
	// Percentage of models that are kept in memory:
	private double memoryUsage = DEFAULT_MEMORY_USAGE;
	
	// State model cache:
	private StateModelCache cache;
	
	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 */
	public StateSpaceManagerImpl(StateSpace stateSpace) {
		super(stateSpace);
		this.cache = new StateModelCache();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.AbstractStateSpaceManager#isOpen(org.eclipse.emf.henshin.statespace.State)
	 */
	@Override
	protected boolean isOpen(State state) throws StateSpaceException {
		
		// Get the model:
		Resource model = getModel(state);
		
		// Find all matches:
		Map<Rule,List<Match>> matches = new HashMap<Rule,List<Match>>();
		for (Rule rule : getStateSpace().getRules()) {
			RuleApplication application = new RuleApplication(createEngine(model), rule);
			matches.put(rule, application.findAllMatches());
		}
		
		// Check if all outgoing transitions are legal:
		for (Transition transition : state.getOutgoing()) {
			
			// Check the rule:
			Rule rule = transition.getRule();
			if (rule==null || !getStateSpace().getRules().contains(rule)) {
				throw new StateSpaceException("Illegal transition in state " + state);
			}
			
			// Check if the match index is valid:
			if (matches.get(rule).size()<=transition.getMatch()) {
				throw new StateSpaceException("Illegal transition in state " + state);
			}	
			
		}
		
		// Check if there is a transition for every found match:
		for (Rule rule : matches.keySet()) {
			int count = matches.get(rule).size();
			for (int i=0; i<count; i++) {
				if (findTransition(state, rule, i)==null) return true;
			}
		}
		
		// State is not open:
		return false;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#getModel(org.eclipse.emf.henshin.statespace.State)
	 */
	public Resource getModel(State state) throws StateSpaceException {
		
		// Model already set?
		if (state.getModel()!=null) {
			return state.getModel();
		}
		
		// Cached?
		Resource cached = cache.get(state);
		if (cached!=null) {
			return cached;
		}
		
		// Find a predecessor state that has a model:
		StateSpaceSearch search = new StateSpaceSearch() {
			@Override
			protected boolean shouldStop(State current, Path path) {
				return current.getModel()!=null || cache.get(current)!=null;
			}
		};
		boolean found = search.depthFirst(state, true);
		if (!found) {
			throw new RuntimeException("Unable to derive state model for state " + state.getIndex());
		}
		
		// Derive the current model:
		Resource model = deriveModel(search.getState().getModel(), search.getPath());
		
		// Decide whether the current model should be kept in memory:
		int states = getStateSpace().getStates().size();
		int stored = (int) (states * memoryUsage);			
		boolean storeCurrent = (stored>0) && (states % stored)==0;
		
		// Associated the model with the state (or not):
		state.setModel(storeCurrent ? model : null);
		
		// Always add it to the cache (is maintained automatically):
		cache.put(state, model);
		
		// Done.
		return model;
		
	}
	
	/*
	 * Derive a model.
	 */
	private Resource deriveModel(Resource start, Path path) throws StateSpaceException {
		
		// Copy the model first:
		Resource model = copyModel(start, null);
		
		// Derive the model for the current state:
		for (Transition transition : path) {
			
			Rule rule = transition.getRule();
			if (rule==null || !getStateSpace().getRules().contains(rule)) {
				throw new StateSpaceException("Illegal transition in state " + transition.getSource());
			}
			
			RuleApplication application = new RuleApplication(createEngine(model), rule);
			List<Match> matches = application.findAllMatches();
			if (matches.size()<=transition.getMatch()) {
				throw new StateSpaceException("Illegal transition in state " + transition.getSource());				
			}
			
			// Apply the rule with the found match:
			Match match = matches.get(transition.getMatch());
			application.setMatch(match);
			application.apply();
			
		}
		
		return model;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#explore(org.eclipse.emf.henshin.statespace.State)
	 */
	public List<Transition> exploreState(State state) throws StateSpaceException {
		
		// Get the state model:
		Resource model = getModel(state);
		
		// List of newly created transitions.
		List<Transition> transitions = new ArrayList<Transition>();
		int newStates = 0;
		
		// Apply all rules:
		for (Rule rule : getStateSpace().getRules()) {
			
			RuleApplication application = new RuleApplication(createEngine(model), rule);
			List<Match> matches = application.findAllMatches();
			
			for (int i=0; i<matches.size(); i++) {
				
				// Transform the model:
				Match match = matches.get(i);
				Resource transformed = copyModel(model, match);
				application = new RuleApplication(createEngine(transformed), rule);
				application.setMatch(match);
				application.apply();
				
				// Find existing transition / state:
				Transition existingTransition = findTransition(state, rule, i);
				State targetState = getState(transformed);
				
				if (existingTransition!=null) {
					
					// Check if the transition points to the correct state:
					if (existingTransition.getTarget()!=targetState) {
						markTainted();
						throw new StateSpaceException("Illegal transition in state " + state);
					}
					
				} else {
					
					// Create a new transition and state if required:
					Transition newTransition = createTransition(state, rule, i);
					if (targetState==null) {
						targetState = createOpenState(transformed, hashCode(transformed));
						targetState.setLocation(shiftedLocation(state, newStates++));
					}
					newTransition.setTarget(targetState);
					
				}
					
			}
			
		}
		
		// Mark the state as closed:
		setOpen(state, false);
		
		// Done.
		return transitions;
		
	}
	
	/*
	 * Find an outgoing transition.
	 */
	private Transition findTransition(State state, Rule rule, int match) {
		for (Transition transition : state.getOutgoing()) {
			if (rule==transition.getRule() && match==transition.getMatch()) {
				return transition;
			}
		}
		return null;
	}
	
	/*
	 * Create an interpreter engine
	 */
	private EmfEngine createEngine(Resource model) {
		EmfGraph graph = new EmfGraph();
		for (EObject root : model.getContents()) {
			graph.addRoot(root);
		}
		return new EmfEngine(graph);
	}
	
	/*
	 * Copy a state model.
	 */
	private Resource copyModel(Resource model, Match match) {
		Resource copy = new ResourceImpl();
		Copier copier = new Copier();
		copy.getContents().addAll(copier.copyAll(model.getContents()));
		copier.copyReferences();
		if (match!=null) {
			List<Node> nodes = new ArrayList<Node>(match.getNodeMapping().keySet());
			for (Node node : nodes) {
				EObject newImage = copier.get(match.getNodeMapping().get(node));
				match.getNodeMapping().put(node, newImage);
			}
		}
		return copy;
	}
	
	/*
	 * Create a shifted location.
	 */
	private int[] shiftedLocation(State base, int index) {
		int[] location = base.getLocation();
		double angle = (Math.PI * index * 0.25d);
		location[0] += 60 * Math.cos(angle);
		location[1] += 60 * Math.sin(angle);
		return location;
	}
	
	/**
	 * Set the memory usage for this state space manager. Must be between 0 and 1, 
	 * where 0 means that no model are kept in memory (except a cache of constant size)
	 * and 1 means that all models are kept in memory. For full effect, the manager has to
	 * be reloaded using {@link #reload(IProgressMonitor)}.
	 * @param memoryUsage Percentage for the memory usage.
	 */
	public void setMemoryUsage(double memoryUsage) {
		memoryUsage = Math.max(Math.min(memoryUsage,1),0);
	}

}
