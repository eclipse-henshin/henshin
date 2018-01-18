package org.eclipse.emf.henshin.rulegen.simple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.rulegen.matching.Correspondence;
import org.eclipse.emf.henshin.rulegen.matching.EMFCompareMatcherAdapter;
import org.eclipse.emf.henshin.rulegen.matching.IMatcher;
import org.eclipse.emf.henshin.rulegen.matching.Matching;

/**
 * Rule generator which constructs a Henshin rule from a pair of models (original model and changed model, called model
 * A and model B).
 * 
 * @author Timo Kehrer, Manuel Ohrndorf
 */
public class ExampleBasedRuleGenerator {

	// The generated rule
	private Rule rule;

	// LHS and RHS graphs
	private Graph gLHS;
	private Graph gRHS;

	// Corresponding objects in A and B
	private Matching matching_A_B;

	// Traces: eObjects in models to nodes in graphs (and vice versa)
	private Map<EObject, Node> modelA2lhs;
	private Map<EObject, Node> modelB2rhs;

	/**
	 * Constructs a Henshin rule from a pair of models (original model and changed model).
	 * 
	 * @param ruleName
	 * @param pathA
	 * @param pathB
	 */
	public Rule generateRule(String ruleName, Resource modelA, Resource modelB) {
		// Init
		modelA2lhs = new HashMap<EObject, Node>();
		modelB2rhs = new HashMap<EObject, Node>();

		// Create a matching to get corresponding elements in A and B
		IMatcher matcher = new EMFCompareMatcherAdapter();
		matching_A_B = matcher.createMatching(modelA, modelB);		

		// LHS and RHS graphs
		gLHS = HenshinFactory.eINSTANCE.createGraph("Lhs");
		gRHS = HenshinFactory.eINSTANCE.createGraph("Rhs");

		// Create rule
		rule = HenshinFactory.eINSTANCE.createRule(ruleName);
		rule.setLhs(gLHS);
		rule.setRhs(gRHS);

		// First, we map the eObjects to graph nodes
		traverseMatching();
		mapObjects(modelA, gLHS, modelA2lhs);
		mapObjects(modelB, gRHS, modelB2rhs);

		// Secondly, for each eReference, we create a corresponding graph edge
		createEdges(modelA, gLHS, modelA2lhs);
		createEdges(modelB, gRHS, modelB2rhs);

		// Return rule
		return rule;
	}

	/**
	 * Traverses the matching, converts all eObjects to graph nodes and adds a LHS-RHS mapping between those nodes.
	 */
	private void traverseMatching() {
		for (Correspondence c : matching_A_B.getCorrespondences()) {
			// Map objects to nodes
			Node lhsNode = eObject2Node(gLHS, c.getObjA());
			Node rhsNode = eObject2Node(gRHS, c.getObjB());

			if (lhsNode != null && rhsNode != null) {
				// Store the traces
				modelA2lhs.put(c.getObjA(), lhsNode);
				modelB2rhs.put(c.getObjB(), rhsNode);

				// Add mapping from lhsNode to rhsNode
				rule.getMappings().add(HenshinFactory.eINSTANCE.createMapping(lhsNode, rhsNode));
			}
		}
	}

	/**
	 * Traverses the given model and maps all eObjects to graph nodes.
	 * 
	 * @param model
	 * @param graph
	 * @param model2graph
	 */
	private void mapObjects(Resource model, Graph graph, Map<EObject, Node> model2graph) {
		// Iterate over all model objects
		TreeIterator<EObject> iterator = model.getAllContents();

		while (iterator.hasNext()) {
			EObject eObject = iterator.next();
			if (matching_A_B.isMatched(eObject)) {
				continue;
			}

			// Convert to node
			Node node = eObject2Node(graph, eObject);

			if (node != null) {
				// Store the trace
				model2graph.put(eObject, node);
			}
		}
	}

	/**
	 * Traverses the given model. For each reference found in the model, a corresponding graph edge is being created.
	 * 
	 * @param model
	 * @param graph
	 * @param model2Graph
	 */
	private void createEdges(Resource model, Graph graph, Map<EObject, Node> model2Graph) {
		// Iterate over all model objects
		TreeIterator<EObject> iterator = model.getAllContents();

		while (iterator.hasNext()) {
			EObject eObject = iterator.next();
			EClass eClass = eObject.eClass();

			// Check all eReference types (also inherited)
			for (EReference eReference : eClass.getEAllReferences()) {

				if (isUnconsideredStructualFeature(eReference)) {
					continue;
				}

				// Check the objects reference targets for imports
				if (eReference.isMany()) {
					@SuppressWarnings("unchecked")
					List<EObject> targets = (List<EObject>) eObject.eGet(eReference);

					for (int i = 0; i < targets.size(); i++) {
						EObject target = targets.get(i);
						createEdge(model2Graph, eObject, target, eReference);
					}
				} else {
					EObject target = (EObject) eObject.eGet(eReference);
					if (target != null) {
						createEdge(model2Graph, eObject, target, eReference);
					}
				}
			}
		} // end iterate
	}

	/**
	 * Create a Henshin node for the given EObject and adds it to the given Henshin graph.
	 * 
	 * @param graph
	 * @param obj
	 * @return
	 */
	private Node eObject2Node(Graph graph, EObject obj) {

		// node
		Node node = HenshinFactory.eINSTANCE.createNode(graph, obj.eClass(), "");

		// attributes
		for (EAttribute eAttribute : obj.eClass().getEAllAttributes()) {
			if (isUnconsideredStructualFeature(eAttribute)) {
				continue;
			}

			Object attValue = obj.eGet(eAttribute);
			if (attValue == null) {
				continue;
			}

			attValue = attValue.toString();

			// Quote String values
			if (eAttribute.getEAttributeType() == EcorePackage.eINSTANCE.getEString()) {
				attValue = "\"" + attValue + "\"";
			}

			HenshinFactory.eINSTANCE.createAttribute(node, eAttribute, attValue.toString());
		}

		return node;
	}

	/**
	 * Creates a Henshin edge representing the given reference in the Henshin graph. To that end, the Henshin nodes
	 * corresponding to the given source and target EObjects are obtained from the trace map model2Graph.
	 * 
	 * @param model2Graph
	 * @param srcObj
	 * @param tgtObj
	 * @param referenceType
	 */
	private void createEdge(Map<EObject, Node> model2Graph, EObject srcObj, EObject tgtObj, EReference referenceType) {
		Node srcNode = model2Graph.get(srcObj);
		Node tgtNode = model2Graph.get(tgtObj);

		if (srcNode != null && tgtNode != null) {
			HenshinFactory.eINSTANCE.createEdge(srcNode, tgtNode, referenceType);
		}
	}

	/**
	 * We do not consider unchangeable, derived and transient features.
	 * 
	 * @param structualFeatureType
	 * @return
	 */
	private boolean isUnconsideredStructualFeature(EStructuralFeature structualFeatureType) {
		if ((structualFeatureType.isChangeable() == false) || (structualFeatureType.isDerived() == true)
				|| (structualFeatureType.isTransient() == true)) {
			return true;
		} else {
			return false;
		}
	}

}
