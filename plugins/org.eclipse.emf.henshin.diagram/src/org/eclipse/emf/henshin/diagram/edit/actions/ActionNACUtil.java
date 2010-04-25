package org.eclipse.emf.henshin.diagram.edit.actions;

import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinNACUtil;

public class ActionNACUtil {
	
	/**
	 * Name of the default NAC.
	 */
	public static final String DEFAULT_NAC_NAME = "default";
	
	/**
	 * Find or create a NAC for a FOBIRD action.
	 * @param action FORBID action.
	 * @param rule Rule.
	 * @return The NAC.
	 */
	public static NestedCondition getOrCreateNAC(Action action, Rule rule) {
		
		// Make sure the action is of type FORBID.
		if (action==null || action.getType()!=ActionType.FORBID) {
			throw new IllegalArgumentException("NACs can be created only for FORBID actions");
		}
		
		// Determine the name of the NAC:
		String name = DEFAULT_NAC_NAME;
		String[] args = action.getArguments();
		if (args!=null && args.length>0 && args[0]!=null) {
			name = args[0];
		}
		
		// Find or create the NAC:
		NestedCondition nac = HenshinNACUtil.getNAC(rule, name);
		if (nac==null) {
			nac = HenshinNACUtil.createNAC(rule, name);
		}
		
		return nac;
		
	}

}
