/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa;

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
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.print.attribute.HashAttributeSet;

import org.eclipse.emf.henshin.multicda.cpa.CDAOptions.GranularityType;

/**
 * A class for saving the options used by the critical pair analysis within AGG.
 * 
 * @author Florian Heﬂ, Kristopher Born
 *
 */
public class CDAOptions {
	private boolean complete = true;
	private boolean strongAttrCheck = true;
	private boolean ignoreSameRules = false;
	private boolean reduceSameMatch = false;
	private boolean directlyStrictConfluent = false;
	private boolean directlyStrictConfluentUpToIso = false;
	private boolean equalVName = false;

	public boolean essentialCP = false;
	public boolean initialCP = true;
	public boolean otherCP = false;

	public boolean isBTable = false;
	public boolean isBATable = true;
	public boolean isCTable = false;
	public boolean isCATable = false;
	public boolean isFTable = false;
	public boolean isFATable = false;

	// (KB) new since 2017-08-21 due to CDA project and missing multiplicity support of the essential CPA
	private boolean ignoreMultiplicities = false;
	public Set<GranularityType> granularities = new HashSet<>();
	public ConflictType cpTypes = ConflictType.NONE;
	private boolean cpaComputation = false;

	public static enum GranularityType {
		BINARY("Binary", "Checks if rule pair is in conflict (dependent)", 1), COARSE("Coarse",
				"Shows minimal conflict (dependency) reason", 2), FINE("Fine", "Shows conflict (dependency) reasons",
						4), VERY_FINE("Very fine", "Shows critical pairs", 8);
		public final String name;
		public final String description;
		public final int id;

		GranularityType(String name, String description, int id) {
			this.name = name;
			this.description = description;
			this.id = id;
		}

		public static Set<GranularityType> getGranularities(boolean... granularityTypes) {
			if (granularityTypes.length > GranularityType.values().length)
				return new HashSet<>();
			Set<GranularityType> result = new HashSet<>();
			GranularityType[] granularities = values();
			for (int i = 0; i < granularityTypes.length; i++)
				if (granularityTypes[i])
					result.add(granularities[i]);
			return result;
		}

		public static int getGranularities(GranularityType... granularityTypes) {
			int gType = 0;
			Set<GranularityType> visited = new HashSet<>();
			for (GranularityType gt : granularityTypes)
				if (visited.add(gt))
					gType += gt.id;
			return gType;
		}

		public static Set<GranularityType> getGranularities(int i) {
			Set<GranularityType> result = new HashSet<>();
			if (i >= VERY_FINE.id) {
				result.add(VERY_FINE);
				i -= VERY_FINE.id;
			}
			if (i >= FINE.id) {
				result.add(FINE);
				i -= FINE.id;
			}
			if (i >= COARSE.id) {
				result.add(COARSE);
				i -= COARSE.id;
			}
			if (i >= BINARY.id) {
				result.add(BINARY);
				i -= BINARY.id;
			}
			return result;
		}
	};

	public static enum ConflictType {
		NONE("", -3), CONFLICT("Conflicts", 1), DEPENDENCY("Dependencies", 2), BOTH("Conflicts and dependencies", 3);
		public final String name;
		public final int id;

		ConflictType(String name, int id) {
			this.name = name;
			this.id = id;
		}

		public ConflictType update(ConflictType type, boolean active) {
			int value = id < 0 ? 0 : id;
			value += (active ? type.id : -type.id);
			switch (value) {
			case 1:
				return ConflictType.CONFLICT;
			case 2:
				return ConflictType.DEPENDENCY;
			case 3:
				return ConflictType.BOTH;
			case -3:
				return ConflictType.NONE;
			case 0:
				return ConflictType.NONE;
			}
			return this;
		}
	};

	/**
	 * @return the ignoreMultiplicities
	 */
	public boolean isIgnoreMultiplicities() {
		return ignoreMultiplicities;
	}

	/**
	 * @param ignoreMultiplicities the ignoreMultiplicities to set
	 */
	public void setIgnoreMultiplicities(boolean ignoreMultiplicities) {
		this.ignoreMultiplicities = ignoreMultiplicities;
	}

	/**
	 * Default constructor.
	 */
	public CDAOptions() {
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

			granularities = GranularityType.getGranularities(input.readBoolean(), input.readBoolean(),
					input.readBoolean(), input.readBoolean());
			initialCP = input.readBoolean();
			essentialCP = input.readBoolean();
			otherCP = input.readBoolean();
			ignoreSameRules = input.readBoolean();

			isBTable= input.readBoolean();
			isBATable= input.readBoolean();
			isCTable= input.readBoolean();
			isCATable= input.readBoolean();
			isFTable= input.readBoolean();
			isFATable= input.readBoolean();

			input.close();
			success = true;

		} catch (IOException e) {
			reset();
			success = false;
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

			output.writeBoolean(granularities.contains(GranularityType.BINARY));
			output.writeBoolean(granularities.contains(GranularityType.COARSE));
			output.writeBoolean(granularities.contains(GranularityType.FINE));
			output.writeBoolean(granularities.contains(GranularityType.VERY_FINE));

			output.writeBoolean(initialCP);
			output.writeBoolean(essentialCP);
			output.writeBoolean(otherCP);
			output.writeBoolean(ignoreSameRules);

			output.writeBoolean(isBTable);
			output.writeBoolean(isBATable);
			output.writeBoolean(isCTable);
			output.writeBoolean(isCATable);
			output.writeBoolean(isFTable);
			output.writeBoolean(isFATable);

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

		setIgnoreMultiplicities(false);
		granularities.clear();
		granularities.add(GranularityType.BINARY);
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

	public boolean isIgnoreSameRules() {
		return ignoreSameRules;
	}

	/**
	 * decides whether critical pairs with the first rule and the second rule being the same are ignored or not
	 * 
	 * @param ignoreSameRules true to ignoreSameRules results of pairs of the same rule.
	 */
	public void setIgnoreSameRules(boolean ignoreSameRules) {
		this.ignoreSameRules = ignoreSameRules;
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

	public void setCpaComputation(boolean cpaComputation) {
		this.cpaComputation = cpaComputation;
	}

	public boolean getCpaComputation() {
		return this.cpaComputation;
	}
}