package org.eclipse.emf.henshin.diagram.edit.actions;

import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinNCUtil;

/**
 * @generated NOT
 */
public class ActionNACUtil {
	
	/**
	 * Name of the default NC.
	 */
	public static final String DEFAULT_NC_NAME = "default";
	
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
		
		return getOrCreateNC(action, rule, false);
	}
	
	public static NestedCondition getOrCreatePAC(Action action, Rule rule) {
		
		// Make sure the action is of type REQUIRE.
		if ((action == null) || (action.getType() != ActionType.REQUIRE)) {
			throw new IllegalArgumentException("PACs can be created only for REQUIRE actions");
		}
		
		return getOrCreateNC(action, rule, true);
	}
	
	
	public static NestedCondition getOrCreateNC(Action action, Rule rule, boolean positive) {
		
		if (!((action != null) && 
				((action.getType() == ActionType.FORBID) || (action.getType() == ActionType.REQUIRE)))) {
			throw new IllegalArgumentException("NCs can be created only for FORBID/REQUIRE actions");
		}
		
		String name = DEFAULT_NC_NAME;
		String[] args = action.getArguments();
		
		if (args != null && args.length > 0 && args[0] != null) {
			name = args[0];
		}
		
		// Find or create the NC:
		NestedCondition nc = HenshinNCUtil.getNC(rule, name, positive);
		if (nc == null) {
			nc = HenshinNCUtil.createNC(rule, name, positive);
		}
		
		return nc;
		
	}
	
	

}
