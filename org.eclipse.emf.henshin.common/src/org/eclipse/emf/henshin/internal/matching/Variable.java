package org.eclipse.emf.henshin.internal.matching;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.internal.constraints.Constraint;
import org.eclipse.emf.henshin.internal.constraints.InstanciationConstraint;

public class Variable implements Comparable<Variable> {
    //TODO(enrico): refactor this class
    
	private List<EObject> domain;

	private boolean instanciated;
	private EObject instanceValue;
	private int instanceIndex;
	private int startingIndex;

	private List<Constraint> constraints;
	private InstanciationConstraint instanceConstraint;

	private boolean enabled;
	private boolean selfenabled;
	private boolean derived;

	private EmfGraph emfGraph;
	private EClass type;

	private List<InstanciationConstraint> acConstraints;

	public Variable(EmfGraph emfGraph, EClass type) {
		this.emfGraph = emfGraph;
		this.type = type;

		constraints = new ArrayList<Constraint>();
		acConstraints = new ArrayList<InstanciationConstraint>();
		instanceConstraint = new InstanciationConstraint(this, this);

		instanciated = false;
		instanceIndex = -1;
		startingIndex = -1;
		instanceValue = null;

		selfenabled = false;
		enabled = false;
	}

	public void enable() {
		if (!enabled) {
			selfenabled = true;
			enabled = true;

			deinstanciate();
			instanceIndex = startingIndex;

			domain = emfGraph.getDomainForType(type);
			domain.removeAll(emfGraph.usedObjects);
			// Collections.shuffle( domain );
		}
	}

	public void enable(List<EObject> values) {
		if (!enabled) {
			enabled = true;
			domain = new ArrayList<EObject>(values);
			// Collections.shuffle( domain );

			deinstanciate();
			instanceIndex = startingIndex;
		}
	}

	public void enable(EObject value) {
		if (!enabled) {
			enabled = true;
			domain = new ArrayList<EObject>();
			domain.add(value);

			deinstanciate();
			instanceIndex = startingIndex;
		}
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @return the selfenabled
	 */
	public boolean isSelfenabled() {
		return selfenabled;
	}

	public void disable() {
		if (enabled) {
			deinstanciate();
			enabled = false;
		}
	}

	public void addQuery(Constraint query) {
		constraints.add(query);
	}

	/**
	 * Adds a query between this variable and a corresponding variable in an
	 * application condition.
	 * 
	 * @param query
	 */
	public void addACQuery(InstanciationConstraint query) {
		acConstraints.add(query);
	}

	public boolean sameEntity(Variable var) {
		for (InstanciationConstraint acQuery : acConstraints) {
			if (acQuery.getTarget() == var)
				return true;
		}

		return false;
	}

	public boolean nextInstance() {
		enable();

		while (instanceIndex < domain.size() - 1) {
			instanceIndex++;

			instanceValue = domain.get(instanceIndex);
			instanciated = true;

			// checks all queries
			boolean queryOK = true;
			queryOK = instanceConstraint.eval();

			if (queryOK) {
				for (InstanciationConstraint acQuery : acConstraints) {
					queryOK = queryOK && acQuery.eval();
				}
			}

			if (queryOK) {
				for (Constraint query : constraints) {
					if (!query.eval()) {
						queryOK = false;
						break;
					}
				}
			}

			if (queryOK) {
				return true;
			}

			deinstanciate();
		}

		if (selfenabled)
			disable();

		// instanceIndex = -1;
		return false;
	}

	public void deinstanciate() {
		for (int i = constraints.size() - 1; i >= 0; i--) {
			constraints.get(i).undo();
		}

		for (InstanciationConstraint acQuery : acConstraints) {
			acQuery.undo();
		}

		instanceConstraint.undo();

		instanceValue = null;
		instanciated = false;
	}

	public void reset() {
		deinstanciate();

		instanceValue = null;
		instanciated = false;
		instanceIndex = -1;
		startingIndex = -1;

		for (int i = constraints.size() - 1; i >= 0; i--) {
			constraints.get(i).reset();
		}

		for (InstanciationConstraint acQuery : acConstraints) {
			acQuery.reset();
		}

		instanceConstraint.reset();

		if (selfenabled)
			enabled = false;
	}

	/**
	 * @return the instanciated
	 */
	public boolean isInstanciated() {
		return instanciated;
	}

	/**
	 * @return the instanceValue
	 */
	public EObject getInstanceValue() {
		return instanceValue;
	}

	/**
	 * @return the domain
	 */
	public List<EObject> getDomain() {
		return domain;
	}

	/**
	 * @return the type
	 */
	public EClass getType() {
		return type;
	}

	/**
	 * @return the instanceIndex
	 */
	public int getInstanceIndex() {
		return instanceIndex;
	}

	/**
	 * @param instanceIndex
	 *            the instanceIndex to set
	 */
	public void setInstanceIndex(int instanceIndex) {
		this.instanceIndex = instanceIndex;
	}

	/**
	 * @param startingIndex
	 *            the startingIndex to set
	 */
	public void setStartingIndex(int startingIndex) {
		this.startingIndex = startingIndex;
	}

	/**
	 * @return the derived
	 */
	public boolean isDerived() {
		return derived;
	}

	/**
	 * @param derived
	 *            the derived to set
	 */
	public void setDerived(boolean derived) {
		this.derived = derived;
	}

	/**
	 * @return the emfGraph
	 */
	public EmfGraph getEmfGraph() {
		return emfGraph;
	}

	@Override
	public int compareTo(Variable arg0) {
		return constraints.size() - arg0.constraints.size();
	}
}
