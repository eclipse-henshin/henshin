package org.eclipse.emf.henshin.multicda.cda.framework;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;

/**
 * @author Jevgenij Huebert
 */
public abstract class Worker {
	protected String NAME = "Worker";
	protected Map<CriticalPair, Reason> comparedResults;

	/**
	 * @return result of the analysis
	 */
	public abstract Set<? extends Object> getResult();

	public void print() {
		print(this.toString());
	}

	/**
	 * 
	 * @param message
	 *            message to print
	 * @param errorOut
	 *            first bool for error showing, second for System print. Default
	 *            values are false, true.
	 * @return
	 */
	public String print(String message, boolean... errorOut) {
		message = NAME + ": " + message;
		if (errorOut.length <= 1 || errorOut.length > 1 && errorOut[1])
			if (errorOut.length == 0 || errorOut.length != 0 && !errorOut[0])
				System.out.println(message);
			else
				System.err.println("\n" + message + "\n");
		return message;
	}

	public void ready() {
		comparedResults = null;
		print("Ready");
	}

}
