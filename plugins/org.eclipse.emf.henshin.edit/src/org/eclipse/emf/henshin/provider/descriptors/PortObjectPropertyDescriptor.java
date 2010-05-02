/**
 * 
 */
package org.eclipse.emf.henshin.provider.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.PortKind;
import org.eclipse.emf.henshin.model.PortObject;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.UnaryFormula;

/**
 * @author Stefan Jurack
 * 
 */
public class PortObjectPropertyDescriptor extends ItemPropertyDescriptor {

	public PortObjectPropertyDescriptor(AdapterFactory adapterFactory,
			ResourceLocator resourceLocator, String displayName,
			String description, EStructuralFeature feature, boolean isSettable,
			boolean multiLine, boolean sortChoices, Object staticImage,
			String category, String[] filterFlags) {
		super(adapterFactory, resourceLocator, displayName, description,
				feature, isSettable, multiLine, sortChoices, staticImage,
				category, filterFlags);
	}// constructor

	/**
	 * 
	 * Collect nodes depending on the current direction value (see
	 * {@link PortKind}).<br>
	 * 
	 * This is currently implemented for {@link Rule}s only, i.e. for other
	 * kinds of units the default is return that is any node in the
	 * transformation system.
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemPropertyDescriptor#getComboBoxObjects
	 *      (java.lang.Object)
	 */
	@Override
	protected Collection<?> getComboBoxObjects(Object object) {

		if (object instanceof PortObject) {
			PortObject po = (PortObject) object;
			if (po.getUnit() instanceof Rule) {
				Rule rule = (Rule) po.getUnit();
				Collection<Node> nodes = new HashSet<Node>();
				if (po.getDirection() == PortKind.INPUT) {
					nodes.addAll(collectPossibleRuleInputObjects(rule));
				} else if (po.getDirection() == PortKind.OUTPUT) {
					nodes.addAll(collectPossibleRuleOutputObjects(rule));
				} else {
					nodes.addAll(collectPossibleRuleInputObjects(rule));
					nodes.addAll(collectPossibleRuleOutputObjects(rule));
				}
				return nodes;
			}// if
		}// if

		return super.getComboBoxObjects(object);
	}// getComboBoxObjects

	/**
	 * @param rule
	 * @return
	 */
	private Collection<Node> collectPossibleRuleInputObjects(Rule rule) {
		List<Node> result = new ArrayList<Node>();

		Graph lhs = (Graph) rule.getLhs();
		result.addAll(lhs.getNodes());

		if (lhs.getFormula() != null) {
			Set<Node> formulaSet = new HashSet<Node>();
			Formula f = lhs.getFormula();
			collectFormulaNodes(f, formulaSet);
			result.addAll(formulaSet);
		}// if
		return result;
	}// collectPossibleRuleInputObjects

	/**
	 * @param rule
	 * @return
	 */
	private Collection<Node> collectPossibleRuleOutputObjects(Rule rule) {
		List<Node> result = new ArrayList<Node>();

		Graph rhs = (Graph) rule.getRhs();
		result.addAll(rhs.getNodes());

		if (rhs.getFormula() != null) {
			Set<Node> formulaSet = new HashSet<Node>();
			Formula f = rhs.getFormula();
			collectFormulaNodes(f, formulaSet);
			result.addAll(formulaSet);
		}// if
		return result;
	}// collectPossibleRuleOutputObjects

	/**
	 * Collects all nodes from (sub-)formulas. <br>
	 * Using a passed collection instead of a return value is to prevent this
	 * method from instantiating several collections but rather using one and
	 * only.
	 * 
	 * @param f
	 * @param coll
	 *            is typically a {@link Set} to prevent doublets.
	 */
	private void collectFormulaNodes(Formula f, Collection<Node> coll) {

		if (f instanceof UnaryFormula) {
			UnaryFormula uf = (UnaryFormula) f;
			collectFormulaNodes(uf.getChild(), coll);
		} else if (f instanceof BinaryFormula) {
			BinaryFormula bf = (BinaryFormula) f;
			collectFormulaNodes(bf.getLeft(), coll);
			collectFormulaNodes(bf.getRight(), coll);
		} else if (f instanceof NestedCondition) {
			NestedCondition nc = (NestedCondition) f;
			coll.addAll(nc.getConclusion().getNodes());
		} else
			throw new IllegalArgumentException("Type of formula f not known");

	}// collectFormulaNodes

}// class
