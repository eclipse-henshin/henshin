package org.eclipse.emf.henshin.model.actions.internal;

import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.actions.Action;
import org.eclipse.emf.henshin.model.actions.ActionType;
import org.eclipse.emf.henshin.model.util.HenshinACUtil;

/**
 * Utility methods to access and modify application conditions
 * (PACs and NACs) based on actions.
 * 
 * @author Christian Krause
 */
public class ActionACUtil {
	
	/**
	 * Name of the default application condition.
	 */
	public static final String DEFAULT_AC_NAME = "default";
		
	/**
	 * Find or create a positive or a negative application condition.
	 * @param action	FORBID/REQUIRE action
	 * @param rule		Rule
	 * @return the application condition.
	 */
	public static NestedCondition getOrCreateAC(Action action, Rule rule) {
		
		// Check if the action type is ok:
		if (!((action != null) && 
			((action.getType() == ActionType.FORBID) || 
			 (action.getType() == ActionType.REQUIRE)))) {
			throw new IllegalArgumentException("Application conditions can be created only for REQUIRE/FORBID actions");
		}
		
		// Determine whether it is a PAC or a NAC:
		boolean positive = (action.getType()==ActionType.REQUIRE);
		
		// Get the name of the application condition:
		String name = DEFAULT_AC_NAME;
		String[] args = action.getArguments();
		if (args != null && args.length > 0 && args[0] != null) {
			name = args[0];
		}
		
		// Find or create the application condition:
		NestedCondition ac = HenshinACUtil.getAC(rule, name, positive);
		if (ac == null) {
			ac = HenshinACUtil.createAC(rule, name, positive);
		}
		
		// Done.
		return ac;
		
	}

}
