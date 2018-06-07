package org.eclipse.emf.henshin.multicda.cda.tester;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.tester.Tester.Options;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

/**
 * 
 * @author Jevgenij Huebert 25.05.2018
 *
 */
public class ResultCreator {

	private CDATester cda = null;
	private Table table;
	// private String tab = "/t";
	private String begin = "<th>";
	private String end = "</th>";
	private String comma = ", ";
	public boolean withBorder = false;

	public ResultCreator(CDATester cda) {
		this.cda = cda;
		recreateTable();
	}

	public void run(CDATester cda) {
		this.cda = cda;
		recreateTable();
	}

	public void recreateTable() {
		this.table = new Table(cda);
		exportResultTable(table);
		exportAbstractTable(table);
	}

	private void exportResultTable(Table table) {
		String name = cda.getOptions().is(Options.DEPENDENCY) ? "Dependencies" : "Conflicts";
		String result = (withBorder ? "<style>th{border: 1px solid black;padding:7px;}</style>" : "")
				+ "<div style=\"position: relative; left: 50%; right: 50%;\">" + name
				+ "</div><table style=\"border-collapse: collapse;\" align=\"center\">\n<tr></th><th>";
		for (Rule r2 : table.cda.second)
			result += begin + r2.getName() + end;
		result += "</tr>\n";
		Map<String, String> reasonKinds = new HashMap<>();
		for (Rule r1 : table.cda.first) {
			result += "<tr>" + begin + r1.getName() + end;
			String row = "";
			for (Rule r2 : table.cda.second) {
				Set<Reason> reasons = table.getReasons(r1, r2);
				if (reasons == null)
					row += begin + end;
				else {
					String entry = "";
					for (Reason r : reasons)
						if (!entry.contains(r.TAG)) {
							entry += comma + r.TAG;
							reasonKinds.put(r.TAG, r.NAME);
						}
					row += begin + entry.substring(1) + end;
				}
			}
			// row = row.substring(0, row.length() - tab.length());
			result += row + "</tr>\n";
		}
		result += "</table>";
		String legende = "<div style=\"position: relative; left: 50%; right: 50%;\">";
		for (String tag : reasonKinds.keySet())
			legende += tag + " = " + reasonKinds.get(tag) + "\n<br>";
		result = result + "\n<br>" + legende + "</div>";
		saveTXT(name, result);
	}

	private void exportAbstractTable(Table table) {
		String result = "";
		String name = (cda.getOptions().is(Options.DEPENDENCY) ? "Dependencies" : "Conflicts") + " abstract";
		String firstline = (withBorder ? "<style>th{border: 1px solid black;padding:7px;}</style>" : "")
				+ "<div style=\"\r\n" + "position: relative;\r\n" + "left: 50%;\r\n" + "right: 50%;\r\n" + "\">" + name
				+ "</div>" + "<table style=\"border-collapse: collapse;\" align=\"center\">\n<tr><th></th>";
		Map<RuleConfigurator, Map<RuleConfigurator, Set<String>>> data = new LinkedHashMap<>();
		Set<RuleConfigurator.RuleType> ruleConfigurations = new HashSet<>();
		Map<String, String> reasonKinds = new HashMap<>();
		List<Rule> first = new ArrayList<>(table.cda.first);
		List<Rule> second = new ArrayList<>(table.cda.second);
		List<RuleConfigurator> firstIn = new ArrayList<>();
		List<RuleConfigurator> secondIn = new ArrayList<>();
		for (Rule r1 : first) {
			RuleConfigurator rc1 = new RuleConfigurator(r1);
			for (Rule r2 : second) {
				Set<Reason> reasons = table.getReasons(r1, r2);
				if (reasons != null) {
					RuleConfigurator rc2 = new RuleConfigurator(r2);
					ruleConfigurations.addAll(rc1.getTypes());
					ruleConfigurations.addAll(rc2.getTypes());
					Map<RuleConfigurator, Set<String>> map = data.get(rc1);
					if (map == null) {
						map = new LinkedHashMap<>();
						data.put(rc1, map);
					}
					if (!firstIn.contains(rc1))
						firstIn.add(rc1);
					if (!secondIn.contains(rc2))
						secondIn.add(rc2);
					Set<String> tags = map.get(rc2);
					if (tags == null) {
						tags = new HashSet<>();
						map.put(rc2, tags);
					}
					for (Reason reason : table.getReasons(r1, r2)) {
						reasonKinds.put(reason.TAG, reason.NAME);
						tags.add(reason.TAG);
					}
				}
			}
		}
		for (RuleConfigurator r : secondIn)
			firstline += begin + r.getTAG() + end;
		for (RuleConfigurator rc1 : firstIn) {
			String row = "";
			Map<RuleConfigurator, Set<String>> rc1Data = data.get(rc1);
			if (rc1Data != null) {
				for (RuleConfigurator rc2 : secondIn) {
					Set<String> rc2Data = rc1Data.get(rc2);
					String reasons = "";
					if (rc2Data == null)
						reasons = comma;
					else
						for (String tag : rc2Data)
							reasons += comma + tag;
					row += begin + reasons.substring(comma.length()) + end;
				}
				result += "<tr>" + begin + rc1.getTAG() + end + row + "</tr>\n";
			}
		}
		result = firstline + "\n" + result;
		result += "</table>";
		String legende = "<div style=\"position: relative; left: 50%; right: 50%;\">";
		for (RuleConfigurator.RuleType rc : ruleConfigurations)
			legende += rc.TAG + " = " + rc.description + "\n<br>";
		legende += "\n<br>";
		for (String tag : reasonKinds.keySet())
			legende += tag + " = " + reasonKinds.get(tag) + "\n<br>";
		result = result + "\n<br>" + legende + "</div>";
		saveTXT(name, result);
	}

	private void saveTXT(String name, String data) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(name + ".html"));
			writer.write(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class Table {
		private Map<String, Map<String, Set<Reason>>> content = new HashMap<>();
		CDATester cda;

		public Table(CDATester cda) {
			this.cda = cda;
			Set<Reason> reasons = cda.getResult();
			for (Reason r : reasons) {
				Map<String, Set<Reason>> first = content.get(r.getRule1().getName());
				if (first == null) {
					first = new HashMap<>();
					content.put(r.getRule1().getName(), first);
				}
				Set<Reason> second = first.get(r.getRule2().getName());
				if (second == null) {
					second = new HashSet<>();
					first.put(r.getRule2().getName(), second);
				}
				second.add(r);
			}
		}

		public Set<Reason> getReasons(Rule r1, Rule r2) {
			Map<String, Set<Reason>> result = content.get(r1.getName());
			if (result == null)
				return null;
			return result.get(r2.getName());
		}
	}
}
