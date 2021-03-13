/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.ocl2gc.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

import graph.Attribute;
import graph.Edge;
import graph.Graph;
import graph.Node;
import morphisms.Mapping;
import morphisms.Morphism;
import morphisms.MorphismsPackage;
import morphisms.Pair;

public class JointPairs {

	/**
	 * for finding the nodes which have to be overlapped. Compute the set of
	 * jointly surjective pairs that commute with a given span.
	 * 
	 * @param span
	 *            A span of morphisms
	 * @return the set of jointly surjective pairs that commute with the span
	 * 
	 */

	private static Pair pushout = null;

	/**
	 * for now it should be called after calling the method getJointPairs
	 * 
	 * @return
	 */
	public static Pair getPushout() {
		return pushout;
	}

	public static Set<Pair> getCommutingPairs(Pair span) {

		Map<Node, Node> nodeMap = new HashMap<>(); // which nodes have to be
													// joined
		for (Mapping mA : span.getA().getMappings()) {
			for (Mapping mB : span.getB().getMappings()) {
				if (mA.getOrigin() == mB.getOrigin()) {
					nodeMap.put(mA.getImage(), mB.getImage());
				}
			}
		}

		return getJointPairs(span.getA().getCodomain(), span.getB().getCodomain(), nodeMap);
	}

	/**
	 * Compute the set of jointly surjective pairs of inclusions from two
	 * graphs.
	 * 
	 * @param graphA
	 * @param graphB
	 * @return the set of jointly surjective inclusions
	 */
	public static Set<Pair> getJointInclusions(Graph graphA, Graph graphB) {

		Map<Node, Node> nodeMap = new HashMap<>(); // which nodes have to be
													// joined
		for (Node a : graphA.getNodes()) {
			for (Node b : graphB.getNodes()) {
				for (String name : a.getNames()) {
					if (b.getNames().contains(name)) {
						nodeMap.put(a, b);
						break;
					}
				}

			}
		}

		return getJointPairs(graphA, graphB, nodeMap);
	}

	/**
	 * Compute a set of jointly surjective pairs.
	 * 
	 * @param graphA
	 * @param graphB
	 * @param jointNodes
	 *            A map of nodes that must be joined, i.e. have the same image.
	 * @return
	 */
	private static Set<Pair> getJointPairs(Graph graphA, Graph graphB, Map<Node, Node> jointNodes) {
		Copier copier = new EcoreUtil.Copier();

		// create the graph of the first pair as a copy of graph A
		Graph graph = (Graph) copier.copy(graphA);
		copier.copyReferences();

		// Integrate graph B into the new graph
		// Calculate existing images for nodes
		Map<Node, Node> nodeMap = new HashMap<>(); // which nodes from codomain
													// B are already in the new
													// graph
		for (Entry<Node, Node> e : jointNodes.entrySet()) {
			nodeMap.put(e.getValue(), (Node) copier.get(e.getKey()));
		}

		// copy remaining nodes
		for (Node node : graphB.getNodes()) {
			Node image = nodeMap.get(node);
			if (image == null) { // copy the whole node
				image = EcoreUtil.copy(node);
				nodeMap.put(node, image);
				graph.getNodes().add(image);
			} else { // copy attributes only TODO: handle whole set of common
						// subtypes
				EClass jointType = getCommonSubtype(node.getType(), image.getType());
				if (jointType == null) {
					return Collections.emptySet();
				}
				image.setType(jointType);

				// supporting attribute values
				for (Attribute a : node.getAttributes()) {
					Attribute copy = EcoreUtil.copy(a);
					copy.setValue(a.getValue());
					copy.setOp(a.getOp());
					copy.setType(a.getType());
					image.getAttributes().add(copy);
				}

				image.addNames(node.getNames());
			}
		}

		// copy remaining edges
		copyEdges: for (Edge edge : graphB.getEdges()) {
			// leave out edges that are already present
			for (Edge e : graph.getEdges()) {
				if (edge.getType() == e.getType() && nodeMap.get(edge.getSource()) == e.getSource()
						&& nodeMap.get(edge.getTarget()) == e.getTarget()) {
					continue copyEdges;
				}
			}
			Edge image = EcoreUtil.copy(edge);
			image.setSource(nodeMap.get(edge.getSource()));
			image.setTarget(nodeMap.get(edge.getTarget()));
			graph.getEdges().add(image);
		}

		Pair initial = MorphismsPackage.eINSTANCE.getMorphismsFactory().createPair();
		Morphism morphA = MorphismsPackage.eINSTANCE.getMorphismsFactory().createMorphism();
		morphA.setDomain(graphA);
		morphA.setCodomain(graph);

		// Compute the initial morphisms
		for (Node node : graphA.getNodes()) {
			Mapping mapping = MorphismsPackage.eINSTANCE.getMorphismsFactory().createMapping();
			mapping.setOrigin(node);
			mapping.setImage((Node) copier.get(node));
			morphA.getMappings().add(mapping);
		}
		initial.setA(morphA);

		Morphism morphB = MorphismsPackage.eINSTANCE.getMorphismsFactory().createMorphism();
		morphB.setDomain(graphB);
		morphB.setCodomain(graph);

		for (Node node : graphB.getNodes()) {
			Mapping mapping = MorphismsPackage.eINSTANCE.getMorphismsFactory().createMapping();
			mapping.setOrigin(node);
			mapping.setImage(nodeMap.get(node));
			morphB.getMappings().add(mapping);
		}
		initial.setB(morphB);

		pushout = initial;

		Set<Pair> result = new HashSet<>();
		result.add(initial);
		// calculate all other pairs from the first pair
		collectJointPairs(initial, result, 0);
		return result;
	}

	/**
	 * Calculate all jointly surjective pairs from the first pair. I.e. this is
	 * the pair with the biggest co-domain. This is done by recursively
	 * calculating all epimorphisms e: I->K from the co-domain I of the initial
	 * pair such that f ang g are injective.
	 * 
	 * a b A ---> I <--- B \ | / \ |e / f\ | /g \ | / \ | / JVL K
	 * 
	 * @param initial
	 *            the first pair (i.e. the pair with the biggest graph)
	 * @param result
	 * @param nodeIndex
	 */
	private static void collectJointPairs(Pair initial, Set<Pair> result, int nodeIndex) {
		initial.getA().getDomain().getNodes();
		List<Node> nodes = initial.getA().getDomain().getNodes();
		if (nodeIndex >= nodes.size()) {
			return;
		}
		Node a = nodes.get(nodeIndex);

		for (Node b : initial.getB().getDomain().getNodes()) {
			for (Pair joined : join(initial, a, b)) {
				if (joined != null) {
					result.add(joined);
					collectJointPairs(joined, result, nodeIndex + 1);
				}
			}
		}
		collectJointPairs(initial, result, nodeIndex + 1);
	}

	// TODO handling attributes are not yet complete. 
	// we merge if two nodes of the same type have the same attribute values or the attribute values are not yet set.
	private static boolean isAttributeValueIn(Attribute attA, Node image) {
		for (Attribute attB : image.getAttributes()) {
			if (attA.getType().getName().equals(attB.getType().getName())) {
				if (attA.getValue().equals(attB.getValue()) || attA.getValue()==null ||  attB.getValue() ==null)
					return true;
			}
		}
		return false;
	}

	/**
	 * Calculate some injective jointly surjective pair from some injective
	 * jointly surjective pair by joining nodeA and nodeB. If nodeA and nodeB
	 * are not type compatible or one of them already has a pre-image in both
	 * graphs, null is returned.
	 * 
	 * @param pair
	 * @param nodeA
	 *            some node from the co-domain of the span that has a pre-image
	 *            in graph A
	 * @param nodeB
	 *            some node from the co-domain of the span that has a pre-image
	 *            in graph B
	 * @return the jointly surjective pair resulting from merging nodeA and
	 *         nodeB or null
	 */
	private static Collection<Pair> join(Pair pair, Node nodeA, Node nodeB) {
		EClass jt = getCommonSubtype(nodeA.getType(), nodeB.getType());
		if (jt != null) {
//			if (nodeA.getAttributes().size() != nodeB.getAttributes().size()) {
//				return Collections.emptySet();
//			} else {
			// TODO handling attributes are not yet complete. 
				for (Attribute attA : nodeA.getAttributes()) {
					if (!isAttributeValueIn(attA, nodeB))
						return Collections.emptySet();

				}

//			}
		}


		Mapping mappingA = getOutgoingMapping(pair.getA(), nodeA);
		Mapping mappingB = getOutgoingMapping(pair.getB(), nodeB);
		if (getIncomingMapping(pair.getB(), mappingA.getImage()) != null
				|| getIncomingMapping(pair.getA(), mappingB.getImage()) != null) {
			return Collections.emptySet();
		}
		Collection<Pair> pairs = new LinkedList<Pair>();
		for (EClass jointType : getCommonSubtypes(nodeA.getType(), nodeB.getType(),
				pair.getA().getCodomain().getTypegraph())) {
			Copier copier = new EcoreUtil.Copier();
			Pair result = (Pair) copier.copy(pair);
			Graph graph = (Graph) copier.copy(pair.getA().getCodomain());
			copier.copyReferences();
			mappingA = (Mapping) copier.get(mappingA);
			mappingB = (Mapping) copier.get(mappingB);

			Node remaining = mappingA.getImage();
			Node removed = mappingB.getImage();
			remaining.setType(jointType);
			remaining.getAttributes().addAll(removed.getAttributes()); // TODO
																		// handle
																		// multiple
																		// attributes

			remaining.addNames(removed.getNames());
			mappingB.setImage(remaining);

			// do not copy multiple edges
			Iterator<Edge> edgeIterator = removed.getIncoming().iterator();
			moveIncoming:
			// for (Edge edge: removed.getIncoming()) {
			while (edgeIterator.hasNext()) {
				Edge edge = edgeIterator.next();
				for (Edge e : remaining.getIncoming()) {
					if (edge.getType() == e.getType() && edge.getSource() == e.getSource()) {
						edge.setSource(null);
						graph.getEdges().remove(edge);
						continue moveIncoming;
					}
				}
				edgeIterator.remove();
				edge.setTarget(remaining);
			}
			edgeIterator = removed.getOutgoing().iterator();
			moveOutgoing: while (edgeIterator.hasNext()) {
				Edge edge = edgeIterator.next();
				for (Edge e : remaining.getOutgoing()) {
					if (edge.getType() == e.getType() && edge.getTarget() == e.getTarget()) {
						edge.setTarget(null);
						graph.getEdges().remove(edge);
						continue moveOutgoing;
					}
				}
				edgeIterator.remove();
				edge.setSource(remaining);
			}
			graph.getNodes().remove(removed);
			pairs.add(result);
		}
		return pairs;
	}

	private static Mapping getOutgoingMapping(Morphism morphism, Node node) {
		for (Mapping mapping : morphism.getMappings()) {
			if (mapping.getOrigin() == node) {
				return mapping;
			}
		}
		return null;
	}

	private static Mapping getIncomingMapping(Morphism morphism, Node node) {
		for (Mapping mapping : morphism.getMappings()) {
			if (mapping.getImage() == node) {
				return mapping;
			}
		}
		return null;
	}

	/**
	 *
	 * 
	 * @param a
	 * @param b
	 * @return the class that is a super class of both classes
	 */
	private static EClass getCommonSubtype(EClass a, EClass b) {
		if (a == b)
			return a;
		if (a.getEAllSuperTypes().contains(b)) {
			return a;
		} else if (b.getEAllSuperTypes().contains(a)) {
			return b;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @param typeGraph
	 * @return the classes from typeGraph that is a super class of both classes
	 */
	private static List<EClass> getCommonSubtypes(EClass a, EClass b, EPackage typeGraph) {
		if (a == b)
			return Collections.singletonList(a); 
		if (a.getEAllSuperTypes().contains(b)) {
			return Collections.singletonList(a);
		} else if (b.getEAllSuperTypes().contains(a)) {
			return Collections.singletonList(b);
		} else {
			List<EClass> result = new BasicEList<EClass>();
			for (EClassifier e : typeGraph.getEClassifiers()) {
				if (e instanceof EClass) {
					List<EClass> superTypes = ((EClass) e).getEAllSuperTypes();
					if (superTypes.contains(a) && superTypes.contains(b)) {
						result.add((EClass) e);
					}
				}
			}
			return result;
		}
	}

}
