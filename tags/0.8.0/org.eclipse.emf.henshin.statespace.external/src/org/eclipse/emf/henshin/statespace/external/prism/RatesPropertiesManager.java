package org.eclipse.emf.henshin.statespace.external.prism;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;
import org.eclipse.emf.henshin.statespace.properties.StateSpacePropertiesManager;

/**
 * Utility functions for state space properties.
 * @author Christian Krause
 */
public class RatesPropertiesManager implements StateSpacePropertiesManager {
	
	// Properties key for PRISM path.
	public static final String PRISM_PATH_KEY = "prismPath";
	
	// Properties key for PRISM arguments.
	public static final String PRISM_ARGS_KEY = "prismArgs";
	
	// Properties key for PRISM experiment parameter.
	public static final String PRISM_EXPERIMENT_KEY = "prismExperiment";
	
	/**
	 * Data class for rates.
	 */
	public static class Rate {
		
		// Minimum, step, maximum values:
		public double min = 0, step = 0, max = 0;
		
		public Rate(double min, double step, double max) {
			this.min = min;
			this.step = step;
			this.max = max;
		}
		
		public Rate(double constant) {
			this(constant,0,constant);
		}
		
		public Rate(String value) throws ParseException {
			NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
			String[] fields = value.split(":");
			if (fields.length==1) {
				min = max = format.parse(fields[0]).doubleValue();
			}
			else if (fields.length==2) {
				min = format.parse(fields[0]).doubleValue();				
				max = format.parse(fields[1]).doubleValue();				
			}
			else if (fields.length==3) {
				min = format.parse(fields[0]).doubleValue();
				step = format.parse(fields[1]).doubleValue();
				max = format.parse(fields[2]).doubleValue();
			}
			else {
				throw new ParseException("Error parsing rate",0);
			}
		}
		
		public boolean isConstant() {
			return min==max;
		}
		
		public String toString() {
			if (isConstant()) {
				return String.valueOf(min);
			} else if (step<=0) {
				return min + ":" + max;
			} else {
				return min + ":" + step + ":" + max;
			}
		}
		
	}
	
	/**
	 * Get the rate of a rule, as specified in the state space properties.
	 * @param stateSpace State space.
	 * @param rule Rule.
	 * @return Its rate or <code>null</code>.
	 * @throws ParseException On parse errors.
	 */
	public static Rate getRate(StateSpace stateSpace, Rule rule) throws ParseException {
		String value = stateSpace.getProperties().get(getRateKey(rule));
		return (value!=null && value.trim().length()>0) ? new Rate(value) : null;
	}

	/**
	 * Set the rate of a rule, stored in the state space properties.
	 * @param stateSpace State space.
	 * @param rule Rule.
	 * @param rate Its rate.
	 */
	public static void setRate(StateSpace stateSpace, Rule rule, Rate rate) {
		String value = (rate!=null) ? rate.toString() : "";
		stateSpace.getProperties().put(getRateKey(rule), value);
	}
	
	/**
	 * Get the PRISM path property.
	 * @param stateSpace State space.
	 * @return PRISM path property (can be <code>null</code>)
	 */
	public static File getPRISMPath(StateSpace stateSpace) {
		String path = stateSpace.getProperties().get(PRISM_PATH_KEY);
		if (path!=null && path.trim().length()>0) {
			return new File(path.trim());
		}
		return null;
	}

	/**
	 * Get the PRISM arguments property.
	 * @param stateSpace State space.
	 * @return PRISM arguments property (can be <code>null</code>)
	 */
	public static String getPRISMArgs(StateSpace stateSpace) {
		return stateSpace.getProperties().get(PRISM_ARGS_KEY);
	}

	/**
	 * Get the PRISM experiment parameter.
	 * @param stateSpace State space.
	 * @return PRISM experiment parameter (can be <code>null</code>)
	 */
	public static String getPRISMExperiment(StateSpace stateSpace) {
		return stateSpace.getProperties().get(PRISM_EXPERIMENT_KEY);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.properties.StateSpacePropertiesManager#initialize(org.eclipse.emf.henshin.statespace.StateSpace)
	 */
	@Override
	public void initialize(StateSpace stateSpace) {
		
		// Initialize rates:
		for (Rule rule : stateSpace.getRules()) {
			try {
				if (getRate(stateSpace, rule)==null) {
					setRate(stateSpace, rule, new Rate(1));
				}
			} catch (ParseException e) {}
		}
		
		// Initialize default arguments:
		if (getPRISMArgs(stateSpace)==null) {
			stateSpace.getProperties().put(PRISM_ARGS_KEY, "-fixdl -gaussseidel");
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.properties.StateSpacePropertiesManager#validate(org.eclipse.emf.henshin.statespace.StateSpace)
	 */
	@Override
	public IStatus validate(StateSpace stateSpace) {
		
		// Try to parse all rates:
		for (Rule rule : stateSpace.getRules()) {
			try {
				getRate(stateSpace, rule);
			} catch (Throwable e) {
				return new Status(IStatus.ERROR, StateSpacePlugin.PLUGIN_ID, "Format error for property \"" + getRateKey(rule) + "\"");
			}
		}
		
		// Check the PRISM path:
		File path = getPRISMPath(stateSpace);
		if (path!=null && !path.isDirectory()) {
			return new Status(IStatus.ERROR, StateSpacePlugin.PLUGIN_ID, "PRISM path not found");
		}
		
		return Status.OK_STATUS;
	}

	
	
	/**
	 * Get the properties key for rule rates.
	 */
	public static String getRateKey(Rule rule) {
		return "rate" + capitalize(removeWhiteSpace(rule.getName()));
	}

	/*
	 * Capitalize a string.
	 */
	private static String capitalize(String string) {
		if (string==null || string.length()==0) return string;
		String first = string.substring(0,1).toUpperCase();
		if (string.length()==0) return first;
		else return first + string.substring(1);
	}
	
	/*
	 * Remove white space from a string.
	 */
	private static String removeWhiteSpace(String string) {
		string = string.replaceAll(" ", "_");
		string = string.replaceAll("\t", "_");
		string = string.replaceAll("\n", "_");
		return string;
	}
	
}
