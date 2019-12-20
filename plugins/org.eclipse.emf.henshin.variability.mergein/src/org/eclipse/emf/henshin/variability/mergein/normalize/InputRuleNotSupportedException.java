package org.eclipse.emf.henshin.variability.mergein.normalize;


public class InputRuleNotSupportedException extends Exception {
	String message; 
	
	public InputRuleNotSupportedException(String string) {
		message = string;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

}
