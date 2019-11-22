package org.eclipse.emf.henshin.variability;

import java.util.ArrayList;
import java.util.List;

/**
 * Log being recorded during variability-aware matching.
 * 
 * @author Daniel Strüber
 *
 */
public class MatchingLog {
	static List<MatchingLogEntry> entries = new ArrayList<MatchingLogEntry>();

	public static List<MatchingLogEntry> getEntries() {
		return entries;
	}

	public static void setEntries(List<MatchingLogEntry> entries) {
		MatchingLog.entries = entries;
	}
	
	public static String createString() {
		StringBuffer sb = new StringBuffer();
		sb.append(entries.size());
		sb.append(" entries:\n");
		int i=1;
		for (MatchingLogEntry entry :entries) {
			sb.append(i);
			sb.append("\t");
			sb.append(entry.getUnit().getName());

			sb.append("\t");
			sb.append(entry.isSuccessful());
			sb.append("\t");
			sb.append(entry.getGraphNodes());
			sb.append("\t");
			sb.append(entry.getGraphEdges());
			sb.append("\t");
			sb.append(entry.getRuntime());
			sb.append("\n");
			i++;
		}
		return sb.toString();
	}
	
	public static String createStringForSuccessfulEntries() {
		StringBuffer result = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		result.append(entries.size());
		result.append(" matching attemps in total, ");
		int i=1;
		for (MatchingLogEntry entry :entries) { 
			if (entry.isSuccessful()) {

				sb.append(i);
				sb.append("\t");
				sb.append(entry.getUnit().getName());

				sb.append("\t");
				sb.append(entry.isSuccessful());
				sb.append("\t");
				sb.append(entry.getGraphNodes());
				sb.append("\t");
				sb.append(entry.getGraphEdges());
				sb.append("\t");
				sb.append(entry.getRuntime());
				sb.append("\n");
				i++;
			}
		}
		result.append(i-1);
		result.append(" being successful:\n");
		return result.append(sb).toString();
	}
}
