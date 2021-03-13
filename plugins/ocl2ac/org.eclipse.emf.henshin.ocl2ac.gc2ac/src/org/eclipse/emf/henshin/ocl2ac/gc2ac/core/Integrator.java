/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.gc2ac.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Xor;

import org.eclipse.emf.henshin.ocl2ac.gc2ac.util.GraphAdapter;
import org.eclipse.emf.henshin.ocl2ac.gc2ac.util.Shifter;
import graph.Attribute;
import graph.Graph;
import graph.util.extensions.Constants;
import laxcondition.Operator;
import nestedcondition.Morphism;
import nestedcondition.NestedCondition;
import nestedcondition.NestedConstraint;
import nestedcondition.NestedconditionFactory;
import nestedcondition.NodeMapping;
import nestedcondition.QuantifiedCondition;
import nestedcondition.Variable;

public class Integrator {

	private NestedCondition condition;
	private NestedCondition shiftedCondition;

	private NestedConstraint copyOfShitferResult;
	private NestedConstraint copyOfPreparedShiftedCondition;

	private Rule rule;
	private EPackage typeModel;
	private Graph emptyStartGraph;
	private org.eclipse.emf.henshin.model.Graph rhsGraph;
	private Graph adaptedRHSGraph;
	private GraphAdapter graphAdapter;
	private Morphism emptyMorphism;
	private NestedConstraint constraint;
	private HashMap<Graph, org.eclipse.emf.henshin.model.Graph> graphMap;
	private final NestedconditionFactory factory = NestedconditionFactory.eINSTANCE;
	private final HenshinFactory henshinFactory = HenshinFactory.eINSTANCE;
	public ArrayList<String> ncVariablesNames;

	private boolean enableOptimizer = false;

	public Integrator(NestedConstraint constraint, Rule rule) {
		if (rule.getModule().getImports().size() == 1
				&& rule.getModule().getImports().contains(constraint.getTypeGraph())) {
			System.out.println("The class name: " + this.getClass().getSimpleName());
			System.out.println(constraint);
			System.out.println(rule);
			this.constraint = constraint;
			this.condition = constraint.getCondition();
			this.rule = rule;
			this.typeModel = constraint.getTypeGraph();
			this.rhsGraph = rule.getRhs();
			this.emptyStartGraph = constraint.getDomain();
			this.graphAdapter = new GraphAdapter(rhsGraph, typeModel);
			this.graphAdapter.adaptFromHenshin();
			this.adaptedRHSGraph = this.graphAdapter.getGraph();
			this.graphMap = new HashMap<Graph, org.eclipse.emf.henshin.model.Graph>();
			this.graphMap.put(adaptedRHSGraph, rhsGraph);
			// create a morphism for the RHS of the rule
			this.emptyMorphism = factory.createMorphism();
			this.emptyMorphism.setFrom(emptyStartGraph);
			this.emptyMorphism.setTo(adaptedRHSGraph);
			this.ncVariablesNames = new ArrayList<String>();
		} else {
			System.out.println("Error when initializing Integrator: incompatible type graphs!");
			System.err.println("rule.getModule().getImports().size() " + rule.getModule().getImports().size());
			System.err.println("rule.getModule().getImports()" + rule.getModule().getImports());
			System.err.println("constraint.getTypeGraph() " + constraint.getTypeGraph());

		}
	}

	public void integrate() {
		if (canIntegrate()) {
			// shift the morphsim of the RHS of the rule along the condition
			Shifter.reset();
			Shifter shifter = new Shifter(emptyMorphism, condition, typeModel);
			System.out.println(this.getClass().getSimpleName() + " " + enableOptimizer);
			if (enableOptimizer) {
				System.out.println("Integrator: Opt is activted");
				shifter.setEnableOptimizer(true, rule, constraint);
			}

			shifter.shift();
			this.shiftedCondition = shifter.getResult();
			if (this.shiftedCondition == null) {
				Shifter.reset();
				System.out.println("Return in The class name: " + this.getClass().getSimpleName() + "<-");
				return;
			}
			Shifter.reset();
			// TODO for testing purpose: It can be deleted
			this.constraint.setDomain(this.shiftedCondition.getDomain());
			this.constraint.setCondition(this.shiftedCondition);

			Copier copier = new Copier();
			this.copyOfShitferResult = (NestedConstraint) copier.copy(this.constraint);
			copier.copyReferences();

			integrateNC2Rule();

		} else {
			System.out.println("Error when integrating: input is null!");
		}
	}

	private void integrateNC2Rule() {
		// prepare nested condition
		NestedConditionPreparer preparer = new NestedConditionPreparer(this.constraint);
		preparer.prepare();

		this.shiftedCondition = preparer.getCondition();

		this.constraint.setDomain(this.shiftedCondition.getDomain());
		this.constraint.setCondition(this.shiftedCondition);

		Copier copier = new Copier();
		this.copyOfPreparedShiftedCondition = (NestedConstraint) copier.copy(this.constraint);
		copier.copyReferences();

		// translate nested condition
		Formula formula = translate2Formula(this.shiftedCondition);
		if (rhsGraph.getFormula() != null) {
			Formula existingFormula = rhsGraph.getFormula();
			And henshinAnd = henshinFactory.createAnd();
			rhsGraph.setFormula(henshinAnd);
			henshinAnd.setLeft(existingFormula);
			henshinAnd.setRight(formula);
		} else {
			this.rhsGraph.setFormula(formula);
		}

		getNCVaraibles();
		createHenAttributeConditions();
		addNCAttributeVariablesASParamsToHenRule();
	}

	// TODO it has to be developed to consider all the variables being defined
	// in the nested conditions.
	private void getNCVaraibles() {
		for (Variable v : this.condition.getVariables()) {
			if (!this.ncVariablesNames.contains(v.getName()))
				this.ncVariablesNames.add(v.getName());
		}
	}

	/**
	 * 
	 */
	private void createHenAttributeConditions() {
		HashMap<Parameter, Attribute> mapOfHenVar = this.graphAdapter.henVarMappingsToNcAttribute;
		if (mapOfHenVar != null) {
			Iterator<Parameter> keySet = mapOfHenVar.keySet().iterator();
			while (keySet.hasNext()) {
				Parameter henVar = keySet.next();
				Attribute ncAttribute = mapOfHenVar.get(henVar);
				AttributeCondition attCond = henshinFactory.createAttributeCondition();
				String stOp = ncAttribute.getOp();
				// TODO: refactor other operations
				if (stOp.equalsIgnoreCase(Constants.NOTEQUALS))
					stOp = Constants.NOTEQUALS2;

				String ncAttributeValue = ncAttribute.getValue();
				if (ncAttributeValue.isEmpty()
						&& ncAttribute.getType().getEType().equals(EcorePackage.Literals.ESTRING))
					ncAttributeValue = "\"\"";

				attCond.setConditionText(henVar.getName() + stOp + ncAttributeValue);
				this.rule.getAttributeConditions().add(attCond);
				this.rule.getParameters().add(henVar);
			}
		}
	}

	private void addNCAttributeVariablesASParamsToHenRule() {

		for (String param : this.ncVariablesNames) {
			Parameter henParam = henshinFactory.createParameter(param);
			if (!this.rule.getParameters().contains(henParam))
				this.rule.getParameters().add(henParam);
		}

	}

	private Formula translate2Formula(NestedCondition cond) {
		if (cond instanceof nestedcondition.Formula) {
			nestedcondition.Formula ncFormula = (nestedcondition.Formula) cond;
			return translateFormula2Formula(ncFormula);
		}
		if (cond instanceof QuantifiedCondition) {
			QuantifiedCondition qCond = (QuantifiedCondition) cond;
			return translateQuantifiedCondition2Formula(qCond);
		}
		return null;
	}

	private Formula translateQuantifiedCondition2Formula(QuantifiedCondition qCond) {
		org.eclipse.emf.henshin.model.NestedCondition henshinNC = henshinFactory.createNestedCondition();
		this.graphAdapter = new GraphAdapter(qCond.getCodomain());
		this.graphAdapter.adaptToHenshin();
		org.eclipse.emf.henshin.model.Graph henshinGraph = this.graphAdapter.getHenshinGraph();
		graphMap.put(qCond.getCodomain(), henshinGraph);
		henshinNC.setConclusion(henshinGraph);
		henshinNC.getMappings().addAll(createMappings(qCond.getMorphism()));
		Formula henshinFormula = translate2Formula(qCond.getCondition());
		if (henshinFormula != null) {
			henshinGraph.setFormula(henshinFormula);
		}
		return henshinNC;
	}

	private EList<Mapping> createMappings(Morphism morphism) {
		EList<Mapping> mappings = new BasicEList<Mapping>();
		for (NodeMapping nm : morphism.getNodeMappings()) {
			Node sourceNode = getNode(graphMap.get(morphism.getFrom()), nm.getOrigin());
			Node targetNode = getNode(graphMap.get(morphism.getTo()), nm.getImage());
			Mapping mapping = henshinFactory.createMapping();
			mapping.setOrigin(sourceNode);
			mapping.setImage(targetNode);
			mappings.add(mapping);
		}
		return mappings;
	}

	private Node getNode(org.eclipse.emf.henshin.model.Graph graph, graph.Node node) {
		for (Node henshinNode : graph.getNodes()) {
			if (node.getName().equals(henshinNode.getName()) && node.getType() == henshinNode.getType()) {
				return henshinNode;
			}
		}
		return null;
	}

	private Formula translateFormula2Formula(nestedcondition.Formula ncFormula) {
		if (ncFormula.getOperator().equals(Operator.NOT)) {
			Not henshinNot = henshinFactory.createNot();
			Formula henshinFormula = translate2Formula(ncFormula.getArguments().get(0));
			henshinNot.setChild(henshinFormula);
			return henshinNot;
		}
		Formula henshinFormula1 = translate2Formula(ncFormula.getArguments().get(0));
		Formula henshinFormula2 = translate2Formula(ncFormula.getArguments().get(1));
		if (ncFormula.getOperator().equals(Operator.AND)) {
			And henshinAnd = henshinFactory.createAnd();
			henshinAnd.setLeft(henshinFormula1);
			henshinAnd.setRight(henshinFormula2);
			return henshinAnd;
		}
		if (ncFormula.getOperator().equals(Operator.OR)) {
			Or henshinOr = henshinFactory.createOr();
			henshinOr.setLeft(henshinFormula1);
			henshinOr.setRight(henshinFormula2);
			return henshinOr;
		}
		if (ncFormula.getOperator().equals(Operator.XOR)) {
			Xor henshinXor = henshinFactory.createXor();
			henshinXor.setLeft(henshinFormula1);
			henshinXor.setRight(henshinFormula2);
			return henshinXor;
		}
		return null;
	}

	private boolean canIntegrate() {
		return (null != this.condition && null != this.emptyMorphism);
	}

	public Rule getRule() {
		return rule;
	}

	public NestedCondition getShiftedCondition() {
		return shiftedCondition;
	}

	public NestedConstraint getCopyShitferResult() {
		return copyOfShitferResult;
	}

	public NestedConstraint getCopyPreparedShiftedCondition() {
		return copyOfPreparedShiftedCondition;
	}

	public boolean isEnableOptimizer() {
		return enableOptimizer;
	}

	public void setEnableOptimizer(boolean enableOptimizer) {
		this.enableOptimizer = enableOptimizer;
	}

}
