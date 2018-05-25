package org.eclipse.emf.henshin.multicda.cda;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.conflict.EssentialConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cpa.result.Conflict;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;

/**
 * This class is intended to identify ess.CPs and ConflictReasons without a counterpart in the other set.
 * This class is intended NOT to:
 * - implement the Comparable interface
 * - make use of java.util.Comparator
 * 
 * @author born
 *
 */
public class CpaCdaComparator {

	public CpaCdaComparator() {
		super();
	}

	public CompareResult compare(Set<CriticalPair> essentialCPs, Set<Reason> conflictReasons) {
		CompareResult compareResult = new CompareResult(essentialCPs, conflictReasons);
		return compareResult;
	}

	public class CompareResult {

		Engine engine = new EngineImpl();
		HenshinFactory henshinFactory = HenshinFactory.eINSTANCE;

		Set<CriticalPair> unassignedCPs;
		Set<Reason> unassignedCRs;

		Map<CriticalPair, Set<Reason>> CpaToCda = new HashMap<>();
		Map<Reason, Set<CriticalPair>> CdaToCpa = new HashMap<>();

		public CompareResult(Set<CriticalPair> essentialCPs, Set<Reason> conflictReasons) {
			unassignedCPs = new HashSet<CriticalPair>(essentialCPs);
			unassignedCRs = new HashSet<>(conflictReasons);
			for (CriticalPair cp : essentialCPs) {
				for (Reason cr : conflictReasons) {

					Pushout pr = new Pushout(cr.getRule1(), cr, cr.getRule2());

					Rule dummyPO_rule = henshinFactory.createRule();

					dummyPO_rule.setLhs(pr.getResultGraph());

					Module module = (Module) EcoreUtil.copy(cr.getRule1().getModule());

					module.getUnits().clear();
					module.getImports().get(0);
					((Conflict) cp).getMatch1().getNodeTargets();
					Conflict conflict = ((Conflict) cp);
					EGraph graph = new EGraphImpl(conflict.getMinimalModel());

//					EGraph graph = Utils.ePackageToEGraph(p);

					module.getUnits().add(dummyPO_rule);

					Iterable<Match> matches = engine.findMatches(dummyPO_rule, graph, null);

					boolean matchesFound = false;
					int numberOfMatches = 0;
					for (Match match : matches) {
						matchesFound = true;
						numberOfMatches++;
					}

					boolean numberOfNodesAreEqual = pr.getResultGraph().getNodes().size() == graph.size();
					boolean numberOfEdgesAreEqual = pr.getResultGraph().getEdges().size() == InterpreterUtil
							.countEdges(graph);

					if (matchesFound && numberOfNodesAreEqual && numberOfEdgesAreEqual) {
						unassignedCPs.remove(cp);
						unassignedCRs.remove(cr);

						Set<Reason> crs = CpaToCda.get(cp);
						if (crs == null) {
							crs = new HashSet<>();
							CpaToCda.put(cp, crs);
						}
						crs.add(cr);

						Set<CriticalPair> cps = CdaToCpa.get(cr);
						if (cps == null) {
							cps = new HashSet<CriticalPair>();
							CdaToCpa.put(cr, cps);
						}
						cps.add(cp);
					}
				}
			}
		}

		/**
		 * @return the unassignedCPs
		 */
		public Set<CriticalPair> getUnassignedCPs() {
			return unassignedCPs;
		}

		/**
		 * @return the unassignedCRs
		 */
		public Set<Reason> getUnassignedCRs() {
			return unassignedCRs;
		}

		/**
		 * @return the unassignedCPs
		 */
		public Set<CriticalPair> getAssignedCPs() {
			return CpaToCda.keySet();
		}

		/**
		 * @return the unassignedCRs
		 */
		public Set<Reason> getAssignedCRs() {
			return CdaToCpa.keySet();
		}

		/**
		 * @return the unassignedCPs
		 */
		public Set<CriticalPair> getCPsOfCR(EssentialConflictReason conflictReason) {
			return CdaToCpa.get(conflictReason);
		}

		/**
		 * @return the unassignedCRs
		 */
		public Set<Reason> getCRsOfCP(CriticalPair criticalPair) {
			return CpaToCda.get(criticalPair);
		}

//		TODO: check if multiple mappings are containing in all results .
//		TODO: get all multiple mappings

	}

}
