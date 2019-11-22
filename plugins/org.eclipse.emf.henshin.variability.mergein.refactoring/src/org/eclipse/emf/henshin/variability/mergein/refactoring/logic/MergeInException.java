package org.eclipse.emf.henshin.variability.mergein.refactoring.logic;

public class MergeInException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5017026567939896083L;
	public static final String NO_MODULE = "The master rule is not contained in a module.";
	public static final String NO_MERGERULE = "There is no merge rule.";
	public static final String NO_MASTERRULE = "The merge rule does not have a master rule.";
	public static final String SAVING_IMPOSSIBLE = "The module can not be saved.";
	public static final String NO_MODULE_2 = "One of the rules is not directly contained in the module.";
	public static final String NO_MODULE_3 = "Can not locate Henshin file.";
	public static final String NAME_CONFLICT = "There is already a unit with the specified name.";

	public MergeInException() {	}
	
	public MergeInException(String s) {
		super(s);
	}

}
