/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.commands;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * Custom command for adding rules to a transformation system. This
 * automatically initializes the rules by adding a LHS and RHS.
 * 
 * @author Christian Krause
 * @generated NOT
 */
public class AddRuleCommand extends AddCommand implements Command {

	/**
	 * Constructor.
	 * 
	 * @param domain
	 *            Editing domain.
	 * @param system
	 *            Transformation system.
	 * @param rule
	 *            Rule.
	 * @param index
	 *            Index.
	 */
	public AddRuleCommand(EditingDomain domain, TransformationSystem system,
			Rule rule, int index) {
		this(domain, system, Collections.singleton(rule), index);
	}

	/**
	 * Constructor.
	 * 
	 * @param domain
	 *            Editing domain.
	 * @param system
	 *            Transformation system.
	 * @param rules
	 *            Rules.
	 * @param index
	 *            Index.
	 */
	public AddRuleCommand(EditingDomain domain, TransformationSystem system,
			Collection<Rule> rules, int index) {
		super(domain, system, HenshinPackage.eINSTANCE
				.getTransformationSystem_Rules(), rules, index);
	}

	@Override
	public void doExecute() {
		int rules = ((TransformationSystem) owner).getRules().size();

		for (Object object : getCollection()) {
			Rule rule = (Rule) object;

			// Initialize LHS and RHS:
			if (rule.getLhs() == null) {
				Graph lhs = HenshinFactory.eINSTANCE.createGraph();
				lhs.setName("LHS");
				rule.setLhs(lhs);
			}
			if (rule.getRhs() == null) {
				Graph rhs = HenshinFactory.eINSTANCE.createGraph();
				rhs.setName("RHS");
				rule.setRhs(rhs);
			}

			// Set the name:
			if (rule.getName() == null) {
				rule.setName("rule" + (rules++));
			}			
		}
		super.doExecute();
	}

}
