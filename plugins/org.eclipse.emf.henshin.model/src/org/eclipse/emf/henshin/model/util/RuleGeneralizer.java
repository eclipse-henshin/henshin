package org.eclipse.emf.henshin.model.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Generalizes a rule by making each << preserve >> and << delete >> node as abstract as possible.
 * 
 * 
 * @author Timo Kehrer, Manuel Ohrndorf
 */
public class RuleGeneralizer {

	/**
	 * Makes each << preserve >> and << delete >> node as abstract as possible.
	 * 
	 * @param module The rule to be generalized.
	 */
	public static void generalizeRule(Rule rule) {

		rule.eAllContents().forEachRemaining(element -> {
			if (element instanceof Node) {
				Node node = (Node) element;

				if (node.getActionNode().getAction().getType().equals(Type.DELETE)) {
					node.setType(selectMostAbstractType(getRequiredTypes(node)));
				}

				else if ((node != node.getActionNode())
						&& (node.getActionNode().getAction().getType().equals(Type.PRESERVE))) {

					Node lhsNode = node.getActionNode();
					Set<EClass> requiredTypes = getRequiredTypes(node);
					requiredTypes.addAll(getRequiredTypes(lhsNode));

					EClass mostAbstractType = selectMostAbstractType(requiredTypes);

					node.setType(mostAbstractType);
					lhsNode.setType(mostAbstractType);
				}
			}
		});
	}

	private static Set<EClass> getRequiredTypes(Node node) {
		Set<EClass> types = new HashSet<>();

		for (Edge ougoing : node.getOutgoing()) {
			types.add(ougoing.getType().getEContainingClass());
		}

		for (Edge incoming : node.getIncoming()) {
			types.add(incoming.getType().getEReferenceType());
		}

		for (Attribute attribute : node.getAttributes()) {
			types.add(attribute.getType().getEContainingClass());
		}

		return types;
	}

	private static EClass selectMostAbstractType(Collection<EClass> types) {

		if (!types.isEmpty()) {
			EClass mostSpecific = types.iterator().next();

			for (EClass type : types) {
				if (type.getEAllSuperTypes().contains(mostSpecific)) {
					mostSpecific = type;
				}
			}

			return mostSpecific;
		}

		return EcorePackage.eINSTANCE.getEObject();
	}
}
