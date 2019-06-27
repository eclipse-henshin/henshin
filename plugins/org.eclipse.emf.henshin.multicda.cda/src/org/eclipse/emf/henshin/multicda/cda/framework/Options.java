package org.eclipse.emf.henshin.multicda.cda.framework;

/**
 * This Options defines preferences of the {@link CdaWorker} and {@link CpaWorker}
 * @author Jevgenij Huebert
 *
 */
public class Options {

	public final static int NONE = 0;
	/**
	 * For dependency analysis.
	 */
	public final static int DEPENDENCY = 1;
	/**
	 * For essential CPA Analysis.
	 */
	public final static int ESSENTIAL = 2;
	/**
	 * For initial CPA Analysis.
	 */
	public final static int INITIAL = 4;
	/**
	 * Prepare first rule. No units or multi-nodes are contained.
	 */
	public final static int PREPARE = 8;
	/**
	 * All delete parts of the first rule will be transformed to preserve.
	 */
	public final static int NONE_DELETION_SECOND_RULE = 16;
	/**
	 * Print header of analysis in the console.
	 */
	public final static int PRINT_HEADER = 32;
	/**
	 * Print results in the console.
	 */
	public final static int PRINT_RESULT = 64;
	/**
	 * No console logs of AGG will be displayed. Default on.
	 */
	public final static int SILENT = 128;
	/**
	 * Print just when a pair of rules contains reasons. Otherwise print no header.
	 */
	public final static int PRINT_WHEN_RESULT = 256;
	/**
	 * Print complete symmetric reasons. Even if they are kilometers long. 
	 */
	public static final int PRINT_COMPLETE = 512;
	/**
	 * Enable all settings
	 */
	public final static int ALL = 1023;
	private int state = PRINT_HEADER + PRINT_RESULT + SILENT + PRINT_WHEN_RESULT;

	/**
	 * Default is printHeader, printResults, printWhenResult and silent on.
	 * 
	 * @param options
	 *            1:dependency, 2:essential, 3:initial, 4:prepare,
	 *            5:noneDeletionSecondRule, 6:printHeader, 7:printResults, 8:silent,
	 *            9:print when result found, 10:print whole s2 Set
	 */
	public Options(boolean... options) {
		int actualState = 1;

		for (Boolean b : options) {
			if (b)
				add(actualState);
			actualState *= 2;
		}
	}

	/**
	 * Default is printHeader, printResults, printWhenResult and silent on.
	 * 
	 * @param options
	 *            1:dependency, 2:essential, 3:initial, 4:prepare
	 *            5:noneDeletionSecondRule, 6:printHeader, 7:printResults, 8:silent,
	 *            9:print when result found, 10:print whole s2 Set
	 */
	public Options(int flag, int... flags) {
		this();
		add(flag);
		add(flags);
	}

	/**
	 * Checks state of this Option
	 * 
	 * @param flag
	 *            is the state that has to be checked
	 * @return true if state of the given flag has been set true
	 */
	public boolean is(int flag) {
		return (state & flag) == flag;
	}

	/**
	 * Sets state of the given flags true
	 * 
	 * @param flags
	 *            the options, that should be set true
	 * @return this option object for better in one other nesting
	 */
	public Options add(int... flags) {
		for (Integer i : flags) {
			if (i == ESSENTIAL)
				remove(INITIAL);
			else if (i == INITIAL)
				remove(ESSENTIAL);
			state |= i;
		}
		return this;
	}

	/**
	 * Sets state of the given flags false
	 * 
	 * @param flags
	 *            the options, that should be set false
	 * @return this option object for better in one other nesting
	 */
	public Options remove(int... flags) {
		for (Integer i : flags) {
			state &= (ALL - i);
		}
		return this;
	}

	public String toCDAString() {
		String result = ((state & DEPENDENCY) == DEPENDENCY ? "Dependency, " : "")
				+ ((state & PREPARE) == PREPARE ? "Prepared, " : "")
				+ ((state & NONE_DELETION_SECOND_RULE) == NONE_DELETION_SECOND_RULE ? "Second rule none deletion, "
						: "")
				+ ((state & PRINT_HEADER) == PRINT_HEADER ? "With header, " : "")
				+ ((state & PRINT_RESULT) == PRINT_RESULT ? "With results, " : "")
				+ ((state & PRINT_WHEN_RESULT) == PRINT_WHEN_RESULT ? "Print only if conflicts found, " : "")
				+ ((state & PRINT_COMPLETE) == PRINT_COMPLETE ? "Print whole S2 Set, " : "");
		return "Options:" + (result.isEmpty() ? "" : " " + result.substring(0, result.length() - 2) + ".");
	}

	@Override
	public String toString() {
		String result = ((state & DEPENDENCY) == DEPENDENCY ? "Dependency, " : "")
				+ ((state & ESSENTIAL) == ESSENTIAL ? "Essential, " : "")
				+ ((state & PREPARE) == PREPARE ? "Prepared, " : "")
				+ ((state & NONE_DELETION_SECOND_RULE) == NONE_DELETION_SECOND_RULE ? "Second rule none deletion, "
						: "")
				+ ((state & PRINT_HEADER) == PRINT_HEADER ? "With header, " : "")
				+ ((state & PRINT_RESULT) == PRINT_RESULT ? "With results, " : "")
				+ ((state & PRINT_WHEN_RESULT) == PRINT_WHEN_RESULT ? "Print only if conflicts found, " : "")
				+ ((state & SILENT) == SILENT ? "Ignore AGG output, " : "");
		return "Options:" + (result.isEmpty() ? "" : " " + result.substring(0, result.length() - 2) + ".");
	}
}