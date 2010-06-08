package org.eclipse.emf.henshin.internal.interpreter;

import javax.script.ScriptEngine;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.internal.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.internal.constraints.ParameterConstraint;
import org.eclipse.emf.henshin.internal.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.internal.constraints.TypeConstraint;
import org.eclipse.emf.henshin.internal.constraints.impl.EmfAttributeConstraint;
import org.eclipse.emf.henshin.internal.constraints.impl.EmfParameterConstraint;
import org.eclipse.emf.henshin.internal.constraints.impl.EmfReferenceConstraint;
import org.eclipse.emf.henshin.internal.constraints.impl.EmfTypeConstraint;
import org.eclipse.emf.henshin.internal.matching.Variable;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class EmfVariableInfo extends VariableInfo<EClass, EObject> {

	public EmfVariableInfo(Rule rule, ScriptEngine scriptEngine) {
		super(rule, scriptEngine);
	}

	@Override
	protected AttributeConstraint<EObject> createAttributeConstraint(
			Attribute attribute, Object value) {
		return new EmfAttributeConstraint(attribute.getType(), value);
	}

	@Override
	protected ParameterConstraint<EObject> createParameterConstraint(
			Attribute attribute) {
		return new EmfParameterConstraint(attribute.getValue(), attribute
				.getType());
	}

	@Override
	protected ReferenceConstraint<EObject> createReferenceConstraint(
			Variable<EClass, EObject> targetVariable, Edge edge) {
		return new EmfReferenceConstraint(targetVariable, edge.getType());
	}

	@Override
	protected TypeConstraint<EClass, EObject> createTypeConstraint(Node node) {
		return new EmfTypeConstraint(node.getType());
	}

}
