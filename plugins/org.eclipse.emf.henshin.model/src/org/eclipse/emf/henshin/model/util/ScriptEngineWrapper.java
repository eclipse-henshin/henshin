/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.model.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Script engine wrapper for automatic handling of Java imports.
 */
public class ScriptEngineWrapper {

	private static final Pattern WILDCARD_PATTERN = Pattern.compile("(.*)\\.\\*$");

	private static final Pattern DIR_FQN_PATTERN = Pattern.compile("(.*)\\.([a-z][^.]*)*$");
	
	private static final Pattern CLASS_FQN_PATTERN = Pattern.compile("(.*)\\.([A-Z][^.]*)$");
	

	/**
	 * The original scripting engine to delegate to.
	 */
	private final ScriptEngine engine;

	/**
	 * List of global imports.
	 */
	private final List<String> globalImports;

	/**
	 * Constructor.
	 * 
	 * @param engine Script engine to be used.
	 * @param globalImports List of global Java imports.
	 */
	public ScriptEngineWrapper(ScriptEngine engine, String[] globalImports) {
		this.engine = engine;
		this.globalImports = new ArrayList<String>();
		if (globalImports != null) {
			for (int i = 0; i < globalImports.length; i++) {
				this.globalImports.add(globalImports[i]);
			}
		}
		if (engine == null) {
			System.err.println("Warning: cannot find JavaScript engine");
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param globalImports List of global Java imports.
	 */
	public ScriptEngineWrapper(String[] globalImports) {
		this(new ScriptEngineManager().getEngineByName("JavaScript"), globalImports);
	}

	/**
	 * Get the wrapped JavaScript engine.
	 * 
	 * @return The wrapped JavaScript engine.
	 */
	public ScriptEngine getEngine() {
		return engine;
	}

	/**
	 * Evaluates a given expression in a context which is extended with the provided imports. The imports are on purpose
	 * not added to the global scope to prevent pollution of the namespace.
	 * 
	 * @param script Script to be executed.
	 * @param localImports List of imports.
	 * @return The result.
	 * @throws ScriptException On script execution errors.
	 */
	@SuppressWarnings("unchecked")
	public Object eval(String script, List<String> localImports) throws ScriptException {
		if (!globalImports.isEmpty() || !localImports.isEmpty()) {
  			script =  toStringWithImports(script,globalImports, localImports);
		}
		return engine.eval(script);
	}
	
	/**
	 * A wrapper for the put operation of the wrapped script engine.
	 * @see javax.script.ScriptEngine.put(String key, Object value)
	 * 
	 * @param key The name of named value to add
	 * @param value The value of named value to add
	 */
	public void put(String key, Object value) {
		engine.put(key, value);
	}

	/**
	 * Converts a list of imports like List("foo.Foo", "foo.bar.*") into one string "foo.Foo, foo.bar"
	 */
	private static String toStringWithImports(String script, List<String>... imports) {
		StringBuffer out = new StringBuffer();
		String delim = "";
		out.append("with (new JavaImporter(");
		
		for (int i = 0; i < imports.length; i++) {
			for (String entry : imports[i]) {
				if (isDirectory(entry) || isWildcard(entry)) {
					out.append(delim).append(stripWildcard(entry));
					delim = ", ";
				}
			}
		}

		out.append(")) { ");

		for (int i = 0; i < imports.length; i++) {
			for (String entry : imports[i]) {
				Matcher m = CLASS_FQN_PATTERN.matcher(entry);
				if (m.matches()) {
					String filename = m.group(2);
					out.append("var ");
					out.append(filename);
					out.append(" = Java.type('");
					out.append(entry);
					out.append("'); ");
				}
			}
		}
		
		out.append(script);
		out.append( " }");
		return out.toString();
	}

	private static String stripWildcard(String imp) {
		return isWildcard(imp) ? imp.substring(0, imp.length() - 2) : imp;
	}

	private static boolean isDirectory(String imp) {
		return DIR_FQN_PATTERN.matcher(imp).matches();
	}
	
	private static boolean isWildcard(String imp) {
		return WILDCARD_PATTERN.matcher(imp).matches();
	}
}
