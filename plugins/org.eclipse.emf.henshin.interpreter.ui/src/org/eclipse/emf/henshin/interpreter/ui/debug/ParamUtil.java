package org.eclipse.emf.henshin.interpreter.ui.debug;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.interpreter.ui.util.ParameterConfig;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterKind;
import org.eclipse.emf.henshin.model.Unit;

public class ParamUtil {

	/**
	 * finds the IFile at the location provided by the string uri
	 * @param uriString
	 * @return
	 */
	public static IFile getIFile(URI uri) {
		try {
			if (uri.isPlatformResource()) {
				IPath path = new Path(uri.toPlatformString(false));
				return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			}
		} catch (IllegalArgumentException e) {
			return null;
		}
		return null;
	}

	/**
	 * gets the parameter preferences from a given unit
	 * @param unit
	 * @return
	 */
	public static List<ParameterConfig> getParameterPreferences(Unit unit) {
		List<ParameterConfig> result = new ArrayList<ParameterConfig>();
		if (unit != null) {
			for (Parameter param : unit.getParameters()) {
				result.add(new ParameterConfig(param));
			}
		}
		return result;
	}

	/**
	 * converts the String value to the given type (see ParameterConfig) and returns it as an Object
	 * @param type
	 * @param value
	 * @return the value of the correct type as an Object
	 */
	public static Object paramConfigToObject(int type, String value) {
	    if(type == ParameterConfig.NULL) return null;
	    if(type == ParameterConfig.BOOLEAN) return Boolean.parseBoolean(value);
	    if(type == ParameterConfig.INT) return Integer.parseInt(value);
	    if(type == ParameterConfig.LONG) return Long.parseLong(value);
	    if(type == ParameterConfig.FLOAT) return Float.parseFloat(value);
	    if(type == ParameterConfig.DOUBLE) return Double.parseDouble(value);
	    return value; // string is also returned unchanged
	}

	/**
	 * fills the given parameter configuration with the given parameter types and values
	 * @param paramConfigs
	 * @param paramTypes
	 * @param paramValues
	 */
	public static void fillParamConfigs(Collection<ParameterConfig> paramConfigs,
			Map<String, String> paramTypes,	Map<String, String> paramValues, List<String> unsetParamNames) {
		for (ParameterConfig paramConfig : paramConfigs) {
			// in case the parameter kinds have changed since the last save: update the mandatory 'unset' values
			if (paramConfig.getKind() == ParameterKind.IN || paramConfig.getKind() == ParameterKind.INOUT) {
				paramConfig.setUnset(false); // has to be set
			} else if (paramConfig.getKind() == ParameterKind.OUT || paramConfig.getKind() == ParameterKind.VAR) {
				paramConfig.setUnset(true); // must not be set
			} else {
				// only for UNKNOWN parameter kinds: use the stored 'unset' value
				paramConfig.setUnset(unsetParamNames.contains(paramConfig.getName()));
			}

			if (paramConfig.isUnset()) {
				continue;
			}

			// parse the type and value (they are stored as strings in the configuration)
			// (use default type if none was stored)
			int paramType = paramConfig.getType();
			if (paramTypes.get(paramConfig.getName()) != null) {
				paramType = Integer.parseInt(paramTypes.get(paramConfig.getName()));
			}

			Object paramValue = null;
			if (!paramConfig.isUnset()) {
				String stringValue = paramValues.get(paramConfig.getName());
				try {
					paramValue = (stringValue == null ? null : paramConfigToObject(paramType, stringValue));
				} catch (NumberFormatException e) {
					paramValue = null;
				}
			}

			// set the type and value of the parameter
			paramConfig.setType(paramType);
			paramConfig.setValue(paramValue);
		}
	}
}
