/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.cpa;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * A class for saving the options used by the critical pair analysis within AGG.
 * 
 * @author Florian Heﬂ, Kristopher Born
 *
 */
public class CPAOptions {
	private boolean complete;
	private boolean strongAttrCheck = true;
	private boolean ignore;
	private boolean reduceSameMatch;
	private boolean directlyStrictConfluent = false;
	private boolean directlyStrictConfluentUpToIso = false;
	private boolean equalVName = false;
	private boolean essential = false;

	/**
	 * Default constructor.
	 */
	public CPAOptions() {
		reset();
	}

	/**
	 * Loads the options from the <code>optionsFile</code>.
	 * 
	 * @param optionsFile the path to the file (including file name)
	 * @return <code>true</code> if options were loaded, else <code>false</code>
	 */
	public boolean load(String optionsFile) {

		boolean success;

		try {
			InputStream file = new FileInputStream(optionsFile);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);

			setComplete(input.readBoolean());
			setIgnore(input.readBoolean());
			setReduceSameRuleAndSameMatch(input.readBoolean());

			input.close();
			success = true;

		} catch (IOException e) {
			reset();
			success = false;
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * Persists the options into the file <code>filePath</code>.
	 * 
	 * @param filePath The path and file name.
	 */
	public void persist(String filePath) {
		try {
			OutputStream file = new FileOutputStream(filePath);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);

			output.writeBoolean(complete);
			output.writeBoolean(ignore);
			output.writeBoolean(reduceSameMatch);

			output.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * resets the CPAOptions to default (<code>complete</code> and <code>reduceSameMatch</code> to <code>true</code>,
	 * everything else to <code>false</code>.)
	 */
	public void reset() {

		/**
		 * kBorn 09-05-2014 most times the different kinds of critical pairs are of interest. (-> true) if you are only
		 * interested in the existence of any CP between the two rules: set this option to false
		 */
		setComplete(true);

		/**
		 * kBorn 09-05-2014 generally the constraint should even be fulfilled for the graph constraints (->'true' by
		 * default) it is still unresolved, if there exists something like graph constraints in henshin and if they are
		 * exportet. -> TODO
		 */
		// setConsistent(true);

		/**
		 * kBorn 09-05-2014 since the focus is on software and system models, attribute values are elementary -> this
		 * should not only be 'true by default', there should be even no possibility to disable this option (until there
		 * is a good request to provide it)
		 */
		// setStrongAttrCheck(true);

		/**
		 * kBorn 09-05-2014 this is of no relevance when the following option is activated. (TODO: check if this
		 * dependency is also is in the AGG parser or just in the user interface)
		 */
		setIgnore(false);

		/**
		 * kBorn 09-05-2014 usual there is a conflict. activated by default (true), but this could be of IMPORTANCE for
		 * the MOCA project in regard of model changes
		 */
		setReduceSameRuleAndSameMatch(true);

		/**
		 * kBorn 09-05-2014 since this is very complex it wont be provided and deactivated (false) by default.
		 */
		// setDirectlyStrictConfluent(false);

		/**
		 * kBorn 09-05-2014 since this is very complex it wont be provided and deactivated (false) by default.
		 */
		// setDirectlyStrictConfluentUpToIso(false);

		/**
		 * kBorn 09-05-2014 function and necessity is unclear -> by default 'false' & not provided to the user
		 */
		// setEqualVName(false);

		/**
		 * kBorn 09-05-2014 since NACs are an elementary part of the rules, until further requests there is no reason to
		 * ignore them -> 'false' & not provided to the user
		 */
		// setEssential(false);

	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public boolean isStrongAttrCheck() {
		return strongAttrCheck;
	}

	public boolean isIgnore() {
		return ignore;
	}

	/**
	 * decides whether critical pairs with the first rule and the second rule being the same are ignored or not
	 * 
	 * @param ignore true to ignore results of pairs of the same rule.
	 */
	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	public boolean isReduceSameRuleAndSameMatch() {
		return reduceSameMatch;
	}

	public void setReduceSameRuleAndSameMatch(boolean reduceSameMatch) {
		this.reduceSameMatch = reduceSameMatch;
	}

	public boolean isDirectlyStrictConfluent() {
		return directlyStrictConfluent;
	}

	public boolean isDirectlyStrictConfluentUpToIso() {
		return directlyStrictConfluentUpToIso;
	}

	public boolean isEqualVName() {
		return equalVName;
	}

	public boolean isEssential() {
		return essential;
	}
}