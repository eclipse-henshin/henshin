package org.eclipse.emf.henshin.interpreter.henshin;

import javax.script.ScriptEngine;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.internal.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.internal.constraints.ParameterConstraint;
import org.eclipse.emf.henshin.internal.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.internal.constraints.TypeConstraint;
import org.eclipse.emf.henshin.internal.interpreter.VariableInfo;
import org.eclipse.emf.henshin.internal.matching.Variable;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class HenshinVariableInfo extends VariableInfo<EClass, Node> {

	public HenshinVariableInfo(Rule rule, ScriptEngine scriptEngine) {
		super(rule, scriptEngine);
	}
	
	@Override
	protected AttributeConstraint<Node> createAttributeConstraint(
			Attribute attribute, Object value) {
		return new HenshinAttributeConstraint(attribute, value);
	}

	@Override
	protected ParameterConstraint<Node> createParameterConstraint(
			Attribute attribute) {
		return new HenshinParameterConstraint(attribute.getValue(), attribute);
	}

	@Override
	protected ReferenceConstraint<Node> createReferenceConstraint(
			Variable<EClass, Node> targetVariable, Edge edge) {
		return new HenshinReferenceConstraint(targetVariable, edge);
	}

	@Override
	protected TypeConstraint<EClass, Node> createTypeConstraint(Node node) {
		return new HenshinTypeConstraint(node.getType());
	}

}
