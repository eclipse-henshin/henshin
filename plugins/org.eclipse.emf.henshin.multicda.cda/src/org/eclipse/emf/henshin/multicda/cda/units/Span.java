package org.eclipse.emf.henshin.multicda.cda.units;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.Pair;
import org.eclipse.emf.henshin.multicda.cda.Utils;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.CreateEdgeDeleteNodeConflictReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.ChangeDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.CreateDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.DeleteDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.DeleteEdgeDeleteNodeDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ChangeDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.CreateDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteDependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteEdgeDeleteNodeDependencyAtom;

public abstract class Span implements Comparable<Span> {
	public final String ID;
	public final String NAME;

	protected int sortID = 0;
	protected String tag = "";
	protected boolean isForbid = false;
	protected boolean isRequire = false;

	HenshinFactory henshinFactory = HenshinFactory.eINSTANCE;

	private Rule rule1;
	private Rule rule2;

	public Set<Mapping> mappingsInRule1;
	public Set<Mapping> mappingsInRule2;

	protected Graph graph;

	private Copier copierForSpanAndMappings;
	protected Set<GraphElement> deletionElementsInRule1;
	protected Set<GraphElement> deletionElementsInRule1_2;

	public Span(Span s1, String tag, String name) {
		ID = tag;
		NAME = name;
		// copy Graph and mappings!
		// Copier
		copierForSpanAndMappings = new Copier();
		// copy of graph
		Graph copiedGraph = (Graph) copierForSpanAndMappings.copy(s1.getGraph());
		copierForSpanAndMappings.copyReferences();
		this.graph = copiedGraph;

		// extract to method
		Set<Mapping> mappingsInRule1 = new HashSet<Mapping>();
		for (Mapping mapping : s1.getMappingsInRule1()) {
			Mapping copiedMapping = (Mapping) copierForSpanAndMappings.copy(mapping);
			copierForSpanAndMappings.copyReferences();
			mappingsInRule1.add(copiedMapping);
		}
		this.mappingsInRule1 = mappingsInRule1;

		Set<Mapping> mappingsInRule2 = new HashSet<Mapping>();
		for (Mapping mapping : s1.getMappingsInRule2()) {
			Mapping copiedMapping = (Mapping) copierForSpanAndMappings.copy(mapping);
			copierForSpanAndMappings.copyReferences();
			mappingsInRule2.add(copiedMapping);
		}
		this.mappingsInRule2 = mappingsInRule2;

		this.setRule1(getRuleOfMappings(mappingsInRule1));
		this.setRule2(getRuleOfMappings(mappingsInRule2));
	}

	public Span(Span extSpan, Node origin, Node image, String tag, String name) {
		this(extSpan, tag, name);
		Node transformedOrigin = (Node) copierForSpanAndMappings.get(origin);

		Mapping r2Mapping = henshinFactory.createMapping(transformedOrigin, image);
		mappingsInRule2.add(r2Mapping);
	}

	public Span(Set<Mapping> rule1Mappings, Graph s1, Set<Mapping> rule2Mappings, String tag, String name) {
		ID = tag;
		NAME = name;
		this.mappingsInRule1 = rule1Mappings;
		this.mappingsInRule2 = rule2Mappings;
		this.graph = s1;
		this.setRule1(getRuleOfMappings(rule1Mappings));
		this.setRule2(getRuleOfMappings(rule2Mappings));
	}

	public final boolean isForbid() {
		return isForbid;
	}

	public final boolean isRequire() {
		return isRequire;
	}

	public final String getTag() {
		return tag;
	}

	@SuppressWarnings("unchecked")
	public final <T extends Span> T setForbid(boolean is) {
		this.isForbid = is;
		this.isRequire = isRequire && !isForbid;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public final <T extends Span> T setRequire(boolean is) {
		this.isRequire = is;
		this.isForbid = isForbid && !isRequire;
		return (T) this;
	}

	public Graph getGraph() {
		return graph;
	}

	/**
	 * @return the deletionElementsInRule1
	 */
	public Set<GraphElement> getDeletionElementsInRule1() {
		if (deletionElementsInRule1 == null)
			setDeletionElements();
		return deletionElementsInRule1;
	}

	/**
	 * @return the deletionElementsInRule1
	 */
	public Set<GraphElement> getDeletionElementsInRule1_2() {
		if (deletionElementsInRule1_2 == null)
			setDeletionElements();
		return deletionElementsInRule1_2;
	}

	/**
	 * @return the rule1
	 */
	public Rule getRule1() {
		return rule1;
	}

	/**
	 * @return the rule2
	 */
	public Rule getRule2() {
		return rule2;
	}

	public void setRule1(Rule rule1) {
		this.rule1 = rule1;
	}

	public void setRule2(Rule rule2) {
		this.rule2 = rule2;
	}

	/**
	 * @return the mappingsInRule1
	 */
	public Set<Mapping> getMappingsInRule1() {
		return mappingsInRule1;
	}

	/**
	 * @return the mappingsInRule2
	 */
	public Set<Mapping> getMappingsInRule2() {
		return mappingsInRule2;
	}

	public Mapping getMappingFromGraphToRule2(Node imageNode) {
		for (Mapping mappingInRule2 : mappingsInRule2) {
			if (mappingInRule2.getImage() == imageNode)
				return mappingInRule2;
		}
		return null;
	}

	public Mapping getMappingFromGraphToRule1(Node imageNode) {
		for (Mapping mappingInRule1 : mappingsInRule1) {
			if (mappingInRule1.getImage() == imageNode)
				return mappingInRule1;
		}
		return null;
	}

	public Mapping getMappingIntoRule1(Node originNode) {
		for (Mapping mapping : mappingsInRule1) {
			if (mapping.getOrigin().equals(originNode))
				return mapping;
		}
		return null;
	}

	public Mapping getMappingIntoRule2(Node originNode) {
		for (Mapping mapping : mappingsInRule2) {
			if (mapping.getOrigin().equals(originNode))
				return mapping;
		}
		return null;
	}

	protected void setDeletionElements() {
		Pair<Set<GraphElement>, Set<GraphElement>> result = getActionElements(new Action(Action.Type.DELETE));
		deletionElementsInRule1 = result.first;
		deletionElementsInRule1_2 = result.second;
	}

	public Pair<Set<GraphElement>, Set<GraphElement>> getActionElements(Action action) {
		Pair<Set<GraphElement>, Set<GraphElement>> result = new Pair<>(new HashSet<>(), new HashSet<>());
		Set<Mapping> mappingsOfSpanInRule1 = getMappingsInRule1();
		Set<Mapping> mappingsOfSpanInRule2 = getMappingsInRule2();
		for (Mapping mapping : mappingsOfSpanInRule1) {
			if (mapping.getImage().getAction().getType().equals(Action.Type.DELETE)
					|| this instanceof CreateEdgeDeleteNodeConflictReason
					|| this instanceof DeleteEdgeDeleteNodeDependencyReason) {
				result.first.add(mapping.getImage());
				result.second.add(mapping.getOrigin());
			}
		}
		for (Mapping mapping : mappingsOfSpanInRule1) {
			Pair<Node, Set<Pair<Attribute, Attribute>>> attributed = Utils.getAttributeChanges(mapping.getImage());
			if (!attributed.second.isEmpty()) {
				result.first.add(mapping.getImage());
				result.second.add(mapping.getOrigin());
			}
		}
		for (Edge egdeInS : graph.getEdges()) {
			Node sourceNodeInS = egdeInS.getSource();
			Node targetNodeInS = egdeInS.getTarget();
			Mapping mappingOfSourceInR1 = getMappingIntoRule(mappingsOfSpanInRule1, sourceNodeInS);
			Node sourceNodeInR1 = mappingOfSourceInR1.getOrigin();
			Node sourceNodeInR1Image = mappingOfSourceInR1.getImage();
			Mapping mappingOfTargetInR1 = getMappingIntoRule(mappingsOfSpanInRule1, targetNodeInS);
			Node targetNodeInR1 = mappingOfTargetInR1.getOrigin();
			Node targetNodeInR1Image = mappingOfTargetInR1.getImage();
			Edge associatedEdgeInR1Image = sourceNodeInR1Image.getOutgoing(egdeInS.getType(), targetNodeInR1Image);
			Edge associatedEdgeInR1 = sourceNodeInR1.getOutgoing(egdeInS.getType(), targetNodeInR1);
			if (associatedEdgeInR1 != null
					&& associatedEdgeInR1Image.getAction().getType().equals(Action.Type.DELETE)) {
				result.first.add(associatedEdgeInR1Image);
				result.second.add(associatedEdgeInR1);
			}
		}
		return result;
	}

	private Mapping getMappingIntoRule(Set<Mapping> mappingsFromSpanInRule, Node originNode) {
		for (Mapping mapping : mappingsFromSpanInRule) {
			if (mapping.getOrigin() == originNode)
				return mapping;
		}
		return null;
	}

	public boolean validate(Graph lhs1, Graph lhs2) {
		if (mappingsInRule1.size() != graph.getNodes().size() || mappingsInRule2.size() != graph.getNodes().size())
			return false;
		for (Node node : graph.getNodes()) {
			Mapping mappingIntoRule1 = getMappingIntoRule1(node);
			if (mappingIntoRule1.getImage() == null)
				return false;
			Node imageInRule1 = mappingIntoRule1.getImage();
			if (imageInRule1.eContainer() != lhs1)
				return false;
			Mapping mappingIntoRule2 = getMappingIntoRule2(node);
			if (mappingIntoRule2.getImage() == null)
				return false;
			Node imageInRule2 = mappingIntoRule2.getImage();
			if (imageInRule2.eContainer() != lhs2)
				return false;

		}
		return true;
	}


	/**
	 * returns the kernel rule of the first mapping or <code>null</code> if the set
	 * <code>mappings</code> is empty.
	 * 
	 * @param mappings
	 * @return a <code>Rule</code> or null.
	 */
	private Rule getRuleOfMappings(Set<Mapping> mappings) {
		try {
			return mappings.iterator().next().getImage().getGraph().getRule();
		} catch (Exception e) {
			// nothing to do here
		}
		return null;
	}

	private Map<Node, Node> getPairedNodes(Span conflictReason, SpanMappings spanMap) {
		Map<Node, Node> result = new HashMap<Node, Node>();
		for (Node n1 : spanMap.s1ToRule1.keySet()) {
			result.put(spanMap.s1ToRule1.get(n1), spanMap.s1ToRule2.get(n1));
		}
		return result;
	}

	private Object shortStringInfoOfGraphEdge(Edge edge) {
		StringBuilder sB = new StringBuilder();
		Node src = edge.getSource();
		Node tgt = edge.getTarget();
		sB.append(getMappingIntoRule1(src).getImage().getName());
		sB.append(",");
		sB.append(getMappingIntoRule2(src).getImage().getName());
		sB.append("->");
		sB.append(getMappingIntoRule1(tgt).getImage().getName());
		sB.append(",");
		sB.append(getMappingIntoRule2(tgt).getImage().getName());
		sB.append(":");
		sB.append(edge.getType().getName());
		return sB.toString();
	}

	private String shortStringInfoOfGraphNode(Node node) {
		StringBuilder sB = new StringBuilder();
		Mapping mappingIntoRule1 = getMappingIntoRule1(node);
		Mapping mappingIntoRule2 = getMappingIntoRule2(node);
		sB.append(mappingIntoRule1.getImage().getName());
		sB.append(",");
		sB.append(mappingIntoRule2.getImage().getName());
		sB.append(":");
		sB.append(node.getType().getName());
		return sB.toString();
	}

	@Override
	public String toString() {
		EList<Edge> edges = getGraph().getEdges();
		EList<Node> nodes = getGraph().getNodes();
		String newID = getFullID();
		return newID + ": " + (edges.isEmpty() ? "" : edges) + (!edges.isEmpty() && !nodes.isEmpty() ? " | " : "")
				+ (nodes.isEmpty() ? "" : nodes);
	}

	public String getFullID() {
		String tag = "";
		if (isForbid)
			tag = "F";
		else if (isRequire)
			tag = "Req";
		return ID.substring(0, ID.length() / 2) + tag + ID.substring(ID.length() / 2);
	}

	public String getFullName() {
		String result = NAME;
		String tag = "";
		if (isForbid)
			tag = " forbid";
		else if (isRequire)
			tag = " require";
		String[] parts = result.split(" ");
		result = parts[0] + tag + result.substring(parts[0].length());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Span s = (Span) obj;
		if (isRequire() != s.isRequire() || isForbid() != s.isForbid())
			return false;
		if (!getFullID().equals(s.getFullID()))
			return false;
		Rule r1 = getRule1();
		Rule r2 = getRule2();
		Rule r1o = ((Span) obj).getRule1();
		Rule r2o = ((Span) obj).getRule2();
		if (r1 != r1o || r2 != r2o)
			return false;
		return equalElements(s);
	}

	public boolean equalElements(Span span) {
		Set<GraphElement> ge = getDeletionElementsInRule1_2();
		Set<GraphElement> geO = span.getDeletionElementsInRule1_2();
		if (ge.size() != geO.size())
			return false;

		for (GraphElement element1 : ge) {
			boolean found = false;
			for (GraphElement element2 : geO)
				if (found = element1.getClass() == element2.getClass()
						&& element1.toString().equals(element2.toString()))
					break;
			if (!found)
				return false;
		}
		return true;
	}

	public static final String NODE_SEPARATOR = "_";

	@Override
	public int hashCode() {
		int hashN = 0;
		int hashE = 0;
		for (Node n : graph.getNodes())
			hashN += getHash(n);
		for (Edge e : graph.getEdges())
			hashE += getHash(e);
		return ID.hashCode() + hashN + hashE + (getRule1() != null ? getRule1().hashCode() : 0) * 3
				+ (getRule2() != null ? getRule2().hashCode() : 0);
	}

	private int getHash(GraphElement n) {
		if (n instanceof Node)
			if (((Node) n).getType() == null)
				return (((Node) n).getName() + "").hashCode();
			else {
				int result = 0;
				String[] names = ((Node) n).getName().split(Reason.NODE_SEPARATOR);
				if (names.length == 2)
					result = names[0].hashCode() * 2 + names[1].hashCode();
				return (result + ":" + ((Node) n).getType().getName()).hashCode();
			}

		else if (n instanceof Edge) {
			Edge e = (Edge) n;
			if (e.getSource() == null || e.getTarget() == null || e.getType() == null)
				return super.hashCode();
			else
				return getHash(e.getSource()) * 11 + getHash(e.getTarget()) + e.getType().getName().hashCode();
		}
		return 0;
	}

	public void print() {
		System.out.println(this);
	}

	protected int sortID() {
		return sortID + (isForbid ? 8 : 0) + (isRequire ? 16 : 0);
	}

	@Override
	public int compareTo(Span o) {
		if (o == null)
			return -1;
		int value = 0;
		if (!getRule1().getName().equals(o.getRule1().getName()))
			if ((value = getRule1().hashCode() - o.getRule1().hashCode()) != 0)
				return value < 0 ? -1 : 1;
		if (!getRule2().getName().equals(o.getRule2().getName()))
			if ((value = getRule2().hashCode() - o.getRule2().hashCode()) != 0)
				return value < 0 ? -1 : 1;

		if (sortID() - o.sortID() != 0)
			return sortID() - o.sortID();
		if ((value = getFullID().compareTo(o.getFullID())) != 0)
			return value < 0 ? -1 : 1;

		String ed1 = graph.getEdges().toString();
		String ed2 = o.graph.getEdges().toString();
		if ((value = ed1.length() - ed2.length()) != 0)
			return value < 0 ? -1 : 1;
		String no1 = graph.getNodes().toString();
		String no2 = o.graph.getNodes().toString();
		if ((value = no1.length() - no2.length()) != 0)
			return value < 0 ? -1 : 1;

		value = toString().compareTo(o.toString());
		return value < 0 ? -1 : value > 0 ? 1 : 0;
	}

	public final boolean is(String string) {
		return ID.equals(string);
	}

	public final boolean isDependency() {
		if (this instanceof SymmetricReason)
			return ((SymmetricReason) this).getS1().isDependency();
		return this instanceof CreateDependencyAtom || this instanceof CreateDependencyReason
				|| this instanceof DeleteDependencyAtom || this instanceof DeleteDependencyReason
				|| this instanceof ChangeDependencyAtom || this instanceof ChangeDependencyReason
				|| this instanceof DeleteEdgeDeleteNodeDependencyAtom
				|| this instanceof DeleteEdgeDeleteNodeDependencyReason;
	}
}
