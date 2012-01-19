package org.eclipse.emf.henshin.diagram.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class for {@link NodeTypeParser}, providing methods to parse a string
 * and deduce typing and parameter information.
 * 
 * @author Stefan Jurack (sjurack)
 * @deprecated We use "->" to indicate changing parameter names.
 */
public class NodeTypeParserHelper {
	
	public enum ParameterDirection {
		IN("in"), OUT("out"), INOUT("");
		
		public String id;
		
		/**
		 * @param id
		 */
		ParameterDirection(String id) {
			this.id = id;
		}// constructor
		
		public static final ParameterDirection get(String id) {
			if (id == null || id.isEmpty())
				return INOUT;
			else if (id.trim().toLowerCase().equals(IN.id))
				return IN;
			else if (id.trim().toLowerCase().equals(OUT.id))
				return OUT;
			else
				return INOUT;
		}
	}// enum
	
	/**
	 * If this pattern matches, a non-empty (more or less) Java-conforming type
	 * is given! Furthermore, one or two (comma-separated) parameters could have
	 * been found. The indices are as follows:<br>
	 * 1: parameter1 (optional)<br>
	 * 2: parameter2 (optional)<br>
	 * 3: type (optional)
	 */
	private final Pattern PATTERN_NODE_NAME = Pattern
			.compile("^(?:([^,:]*)(?:,([^,:]*))?)?(?::([\\w\\.]+))?$");
	
	/**
	 * If this pattern matches, a parameter name is given, which may be followed
	 * by a string in brackets. The indices are as follows:<br>
	 * 1: parameter name <br>
	 * 2: additional string without brackets (optional)
	 * 
	 */
	private final Pattern PATTERN_PARAMETER = Pattern.compile(
			"^([^\\[\\]]+)(?:\\[([^\\[\\]]*)\\])?$", Pattern.CASE_INSENSITIVE);
	
	private String[] para = new String[2];
	private ParameterDirection[] paraDirection = new ParameterDirection[2];
	
	/**
	 * Type information contained in the parsed string.
	 */
	private String type;
	
	/**
	 * Constructor
	 */
	public NodeTypeParserHelper() {
		initialize();
	}// constructor
	
	/**
	 * Parses the given nodeString and extracts a type and one or two
	 * parameters. If no type information can be extracted, this method return
	 * false. If one parameter has been found, its information can be found via
	 * {@link #getParameter1()} and {@link #getDirection1()}. If a second one is
	 * available, please refer to {@link #getParameter2()} and
	 * {@link #getDirection2()}.
	 * 
	 * 
	 * @param nodeString
	 * @return
	 */
	public boolean parse(String nodeString) {
		initialize();
		
		if (isUndefined(nodeString)) // fast and simple test in advance
			return false;
		
		nodeString = nodeString.trim();
		
		final Matcher matcher = PATTERN_NODE_NAME.matcher(nodeString);
		
		if (!matcher.matches()) // check for valid type and parameters
			return false;
		
		// process the type
		this.type = matcher.group(3);
		this.type = (isUndefined(this.type)) ? null : this.type.trim();
		
		String p = matcher.group(1);
		parseParameterString(p, 0);
		p = matcher.group(2);
		parseParameterString(p, 1);
		
		// final work to ensure some sort of consist parameter definitions
		/*
		 * If something is given like ",p:type", only the second parameter is
		 * set. We are accommodating according to this and just switch the
		 * second with the first parameter.
		 */
		if (para[0] == null && para[1] != null) {
			para[0] = para[1];
			para[1] = null;
			paraDirection[0] = paraDirection[1];
			paraDirection[1] = null;
		}// if
		
		/*
		 * If something occurs like "p1[in],p2[in]:Type" the second parameter is
		 * skipped completely as two parameters with equal directions are not
		 * valid.
		 */
		if ((para[0] != null) && (paraDirection[0] == paraDirection[1])) {
			para[1] = null;
			paraDirection[1] = null;
		}// if
		
		/*
		 * If something occurs like "p1[in],p2:Type" the second parameter is
		 * skipped completely as two parameters with one being IN and OUT are
		 * not valid.
		 */
		if ((para[0] != null && para[1] != null)
				&& (paraDirection[0].equals(ParameterDirection.INOUT) || paraDirection[1]
						.equals(ParameterDirection.INOUT))) {
			para[1] = null;
			paraDirection[1] = null;
		}// if
		
		return true;
	}// parse
	
	/**
	 * @param value
	 * @param paraNo
	 */
	private void parseParameterString(String value, int paraNo) {
		
		// check if value is specified at all
		if (!isUndefined(value)) {
			value = value.trim();
			Matcher m = PATTERN_PARAMETER.matcher(value);
			// check for valid value and parse it
			if (m.matches()) {
				// fetch parameter name
				final String pName = m.group(1);
				if (!isUndefined(pName)) {// if parameter name not empty
					this.para[paraNo] = pName.trim();
					// now check for direction information
					final String pDir = m.group(2);
					this.paraDirection[paraNo] = ParameterDirection.get(pDir);
				}// if
			}// if
		}// if
	}// parseParameterString
	
	/**
	 * Resets all instance variables.
	 */
	private void initialize() {
		para[0] = null;
		para[1] = null;
		paraDirection[0] = null;
		paraDirection[1] = null;
		type = null;
	}// initialize
	
	/**
	 * Returns <code>true</code> if the given string <code>s</code> is null or
	 * an empty string. Otherwise <code>false</code> is returned.
	 * 
	 * @param s
	 * @return
	 */
	private boolean isUndefined(String s) {
		return s == null || s.trim().length() == 0;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getParameter1() {
		return this.para[0];
	}
	
	public String getParameter2() {
		return this.para[1];
	}
	
	public ParameterDirection getDirection1() {
		return this.paraDirection[0];
	}
	
	public ParameterDirection getDirection2() {
		return this.paraDirection[1];
	}
	
	/**
	 * Calculates the string according to the given parameter settings. The
	 * following settings are possible:<br>
	 * INOUT<br>
	 * IN<br>
	 * OUT<br>
	 * IN, OUT<br>
	 * OUT, IN<br>
	 * <br>
	 * This method always tries to generate an IN,OUT order of parameters, no
	 * matter in what order these parameter have been passed.
	 * 
	 * @param para1Name
	 * @param para1Dir
	 * @param para2Name
	 * @param para2Dir
	 * @return
	 */
	public String getParameterString(String para1Name, ParameterDirection para1Dir,
			String para2Name, ParameterDirection para2Dir) {
		String p1 = null;
		String p2 = null;
		String result = "";
		
		if (para1Name == null || para1Name.trim().isEmpty())
			return result;
		else {
			p1 = para1Name;
			if (para1Dir != ParameterDirection.INOUT) {
				p1 += "[" + para1Dir.id + "]";
				if (para2Name != null && !para2Name.trim().isEmpty()) {
					p2 = para2Name + "[" + para2Dir.id + "]";
					if (para1Dir == ParameterDirection.IN)
						result = p1 + "," + p2;
					else
						result = p2 + "," + p1;
				} else {
					result = p1;
				}
			} else
				result = p1;
			
		}// if else
		return result;
	}// getParameterString
	
}// class
