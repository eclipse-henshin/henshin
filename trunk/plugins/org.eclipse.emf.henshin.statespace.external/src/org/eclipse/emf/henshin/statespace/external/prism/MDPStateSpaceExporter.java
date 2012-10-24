/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.statespace.external.prism;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.external.AbstractStateSpaceExporter;
import org.eclipse.emf.henshin.statespace.external.prism.PRISMUtil.Range;
import org.eclipse.emf.henshin.statespace.impl.StateSpaceTimeInfo;

/**
 * Exporter for PRISM. This generates an MDP or a PTA model.
 * @author Christian Krause
 */
public class MDPStateSpaceExporter extends AbstractStateSpaceExporter {

	/**
	 * ID of this exporter.
	 */
	public static final String EXPORTER_ID = "org.eclipse.emf.henshin.statespace.external.export.prismmdp";

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceExporter#doExport(org.eclipse.emf.henshin.statespace.StateSpace, org.eclipse.emf.common.util.URI, java.lang.String, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doExport(StateSpace stateSpace, URI uri, String parameters, IProgressMonitor monitor) throws IOException, StateSpaceException {

		int stateCount = stateSpace.getStates().size();
		monitor.beginTask("Exporting state space...", 3 * stateCount);

		// Get the time info:
		StateSpaceTimeInfo timeInfo;
		try {
			timeInfo = new StateSpaceTimeInfo(index);
		} catch (StateSpaceException e) {
			throw new IOException(e);
		}
		boolean timed = timeInfo.isTimed();

		// For timed cases, we need an engine to find matches:
		Engine engine = timed ? new EngineImpl() : null;
		
		// Shall we produce an explicit model?
		if (timed && !"nm".equalsIgnoreCase(uri.fileExtension())) {
			throw new IOException("Explicit export for timed models not supported.");
		}
		boolean explicit = !timed && "tra".equalsIgnoreCase(uri.fileExtension());

		// Get the probabilistic rules:
		Map<String, List<Rule>> probRules = PRISMUtil.getProbabilisticRules(stateSpace);

		// Export to file...
		File file = new File(uri.toFileString());
		OutputStreamWriter writer = createWriter(file);

		// Get the probability constants:
		Map<String, String> probs = PRISMUtil.getAllProbs(stateSpace, explicit);

		// Output the header and constants:
		if (!explicit) {			
			writer.write(PRISMUtil.getModelHeader(timed ? "pta" : "mdp"));
			for (String ruleName : probRules.keySet()) {
				List<Rule> rules = probRules.get(ruleName);
				if (rules.size()>1) {
					for (int i=0; i<rules.size(); i++) {
						String key = PRISMUtil.getProbKey(rules.get(i), i);
						String value = probs.get(key);
						writer.write("const double " + key);
						if (value!=null && !Range.isRange(value)) {
							writer.write(" = " + value);
						}
						writer.write(";\n");
						writer.write("const double " + PRISMUtil.getProbVar(i) + " = " + key + ";\n");
					}
				}
			}
			writer.write("\nmodule " + uri.trimFileExtension().lastSegment() + "\n\n");
		}

		if (explicit) {
			// State and transition count:
			writer.write(stateCount + " " + stateSpace.getTransitionCount() + "\n");
		} else {
			// State variables:
			writer.write(PRISMUtil.getVariableDeclarations(stateSpace.getStateCount(), false));
			if (timed) {
				
				// Clock variables:
				for (String clock : timeInfo.getClocks()) {
					writer.write("\t" + clock + " : clock;\n");
				}
				
				// Clock invariants:
				boolean first = true;
				for (State s : stateSpace.getStates()) {
					String inv = timeInfo.getInvariant(s, getMatches(s, engine));
					if (inv!=null) {
						if (first) {
							writer.write("\n\tinvariant\n");
						}
						writer.write("\t\t");
						if (!first) writer.write("& ");
						writer.write("(s=" + s.getIndex()  + " => " + inv + ")\n");
						first = false;
					}
				}
				if (!first) {
					writer.write("\tendinvariant\n\n");
				}
				
			}
		}

		// Output the transitions:
		int removedIllegal = 0;
		for (State s : stateSpace.getStates()) {

			// Sort transitions by labels:
			Map<MDPLabel, List<Transition>> trs = MDPLabel.getTransitionsByLabel(s);
			int transitionIndex = 0;
			for (MDPLabel l : trs.keySet()) {
				List<Transition> ts = trs.get(l);
				if (ts.isEmpty()) continue;

				// Check if all rules are enabled:
				String label = l.getTransition().getRule().getName();
				List<Rule> rules = probRules.get(label);
				boolean allEnabled = true;
				for (Rule r : rules) {
					boolean enabled = false;
					for (Transition t : ts) {
						if (t.getRule()==r) {
							enabled = true;
							break;
						}
					}
					if (!enabled) {
						allEnabled = false;
						break;
					}
				}
				if (!allEnabled) {
					removedIllegal++;
					continue;
				}

				// Output the transition:
				if (!explicit) {
					String guard = null;
					if (timed) {
						Match match = getMatch(l.getTransition(), engine);
						Match resultMatch = getResultMatch(l.getTransition(), engine);
						guard = timeInfo.getGuard(l.getTransition(), match, resultMatch);
					}
					writer.write("\t[" + label + "] " + PRISMUtil.getPRISMState(s.getIndex(), guard, false) + " -> ");
				}

				boolean first = true;
				for (Transition t : ts) {
					if (!first) {
						writer.write(explicit ? "\n" : " + ");
					}
					String probKey = PRISMUtil.getProbVar(rules.indexOf(t.getRule()));
					if (explicit) {
						String prob = (rules.size()>1) ? probs.get(probKey) : "1";
						writer.write(s.getIndex() + " " + transitionIndex + " " + t.getTarget().getIndex() + " " + prob);
					} else {
						String prob = (rules.size()>1) ? probKey+":" : "";
						String resets = null;
						if (timed) {
							Match match = getMatch(t, engine);
							Match resultMatch = getResultMatch(t, engine);
							resets = timeInfo.getResets(t, match, resultMatch);
						}
						writer.write(prob + PRISMUtil.getPRISMState(t.getTarget().getIndex(), resets, true));
					}
					first = false;
				}

				if (explicit) {
					writer.write("\n");
					transitionIndex++;
				} else {				
					writer.write(";\n");
				}
			}

			// Update the monitor:
			monitor.worked(1);
			if (monitor.isCanceled()) {
				break;
			}

		}

		// Did we remove any illegal transitions?
		if (removedIllegal>0) {
			StateSpacePlugin.INSTANCE.logWarning("Removed " + removedIllegal + " illegal probabilistic transitions");
		}

		// Initial states
		if (!explicit) {
			writer.write("\nendmodule\n\n");
			if (!timed) {
				writer.write("init\n\t");
				writer.write(PRISMUtil.getPRISMStates(stateSpace.getInitialStates()));
				writer.write("\nendinit\n");
			}
		}

		// State labels:		
		if (parameters!=null) {
			try {
				String expanded = PRISMUtil.expandLabels(parameters, index, new SubProgressMonitor(monitor, stateCount));
				if (explicit) {
					OutputStreamWriter labelsWriter = createWriter(new File(uri.toFileString().replaceAll(".tra", ".lab")));
					labelsWriter.write(expanded);
					labelsWriter.close();
				} else {
					writer.write("\n" + expanded + "\n");
				}
			} catch (Exception e) {
				throw new IOException(e);
			}
		}

		// States file:
		if (explicit) {
			OutputStreamWriter statesWriter = createWriter(new File(uri.toFileString().replaceAll(".tra", ".sta")));
			statesWriter.write(PRISMUtil.getVariableDeclarations(stateSpace.getStateCount(), true) + "\n");					
			for (int i=0; i<stateCount; i++) {
				statesWriter.write(i + ":(" + i + ")\n");
			}
			statesWriter.close();
		}

		// Finished:
		writer.close();
		if (!monitor.isCanceled()) {
			monitor.done();
		}

	}

	
	private Match getMatch(Transition transition, Engine engine) throws StateSpaceException {
		
		// Get the source model and create an EGraph:
		Model model = index.getModel(transition.getSource());
		EGraph graph = model.getEGraph();
		
		// Set the model!!!
		transition.getSource().setModel(model);
		
		// Get the match:
		int matchIndex = transition.getMatch();
		int currentIndex = 0;
		for (Match m : engine.findMatches(transition.getRule(), graph, null)) {
			if (currentIndex++==matchIndex) {
				return m;
			}
		}
		return null;
		
	}

	private List<Match> getMatches(State state, Engine engine) throws StateSpaceException {
		List<Match> matches = new ArrayList<Match>();
		for (Transition t : state.getOutgoing()) {
			matches.add(getMatch(t, engine));
		}
		return matches;
	}
	
	private Match getResultMatch(Transition transition, Engine engine) throws StateSpaceException {
		
		// Copy the source model and create an EGraph:
		Model copiedModel = index.getModel(transition.getSource()).getCopy(null);
		EGraph graph = copiedModel.getEGraph();
		
		// Get the match:
		int currentIndex = 0;
		Match match = null;
		int matchIndex = transition.getMatch();
		for (Match m : engine.findMatches(transition.getRule(), graph, null)) {
			if (currentIndex++==matchIndex) {
				match = m; break;
			}
		}
		
		// Transform the graph:
		RuleApplication app = new RuleApplicationImpl(engine, graph, transition.getRule(), match);
		app.execute(null);
		
		// Update the object keys:
		copiedModel.updateObjectKeys(index.getStateSpace().getEqualityHelper().getIdentityTypes());
		
		// Update the model of the target state!!!
		transition.getTarget().setModel(copiedModel);
		
		// Done.
		return app.getResultMatch();
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.export.StateSpaceExporter#getName()
	 */
	@Override
	public String getName() {
		return "PRISM MDP/PTA";
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.export.StateSpaceExporter#getFileExtensions()
	 */
	@Override
	public String[] getFileExtensions() {
		return new String[] { "nm", "tra" };
	}

}
