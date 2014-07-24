package org.eclipse.emf.henshin.interpreter.impl;

import java.util.List;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Script engine wrapper for automatic handling of Java imports.
 */
public class ScriptEngineWrapper {

	/**
	 * The original scripting engine to delegate to.
	 */
	private ScriptEngine engine;

	public ScriptEngineWrapper(ScriptEngine engine) {
		this.engine = engine;
	}

	public ScriptEngine getEngine() {
		return engine;
	}

	/**
	 * Evaluates a given expression in a context which is extended with the
	 * provided imports
	 * 
	 * The imports are on purpose not added to the global scope to prevent
	 * pollution of the namespace.
	 * 
	 * @param script
	 *            Script to be executed.
	 * @param imports
	 *            List of imports.
	 * @return The result.
	 * @throws ScriptException
	 *             On script execution errors.
	 */
	public Object eval(String script, List<String> imports)
			throws ScriptException {
		if (!imports.isEmpty()) {
			script = "(function() { with (new JavaImporter("
					+ toImportString(imports) + ")) { return " + script
					+ " ; }}).call(this);";
		}
		return engine.eval(script);
	}

	/**
	 * Converts a list of imports like List("foo.Foo", "foo.bar.*") into one
	 * string "foo.Foo, foo.bar"
	 */
	private String toImportString(List<String> imports) {
		StringBuffer out = new StringBuffer();
		String delim = "";
		for (String i : imports) {
			out.append(delim).append(stripWildcard(i));
			delim = ", ";
		}
		return out.toString();
	}

	private String stripWildcard(String imp) {
		return isWildcard(imp) ? imp.substring(0, imp.length() - 2) : imp;
	}

	private boolean isWildcard(String imp) {
		return Pattern.matches("(.*)\\.\\*$", imp);
	}

	public static ScriptEngineWrapper newInstance() {
		ScriptEngine engine = new ScriptEngineManager()
				.getEngineByName("JavaScript");
		if (engine == null) {
			System.err.println("Warning: cannot find JavaScript engine");
		} else
			try {
				// Add java.lang to the global namespace
				engine.eval("importPackage(java.lang)");
			} catch (Throwable t1) {
				// Try again with compatibility library
				try {
					engine.eval("load(\"nashorn:mozilla_compat.js\");\n importPackage(java.lang)");
				} catch (Throwable t2) {
					// Also didn't work
					System.err
							.println("Warning: error importing java.lang package in JavaScript engine");
				}
			}
		return new ScriptEngineWrapper(engine);
	}

}
