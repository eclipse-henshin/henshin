package org.eclipse.emf.henshin.variability.mergein.normalize;

public class InputRuleNotSupportedException extends Exception {
	private static final long serialVersionUID = 6830449990620915597L;

	String message;
	
	public InputRuleNotSupportedException(String string) {
		message = string;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

}
