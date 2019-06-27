package org.eclipse.emf.henshin.multicda.cda.framework;

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
import org.eclipse.emf.henshin.multicda.cda.Pair;
import org.eclipse.emf.henshin.multicda.cda.units.Span;

/**
 * Creates normal and abstract table with results from CdaWorker or a set of spans. 
 * @author Jevgenij Huebert 25.05.2018
 * @see CdaWorker
 */
public abstract class ResultCreator {

	private static Set<? extends Span> spans = new HashSet<>();
	private static String title = "";
	private static String path = "";
	private static Set<Rule> first;
	private static Set<Rule> second;
	private static Table table;
	private static String begin = "<th>";
	private static String beginFirst = "<th style='background:lightgrey'>";
	private static String end = "</th>";
	private static String comma = ", ";
	public static boolean withBorder = true;

	public static void create(Set<? extends Span> spans, Set<Rule> first, Set<Rule> second) {
		if (!spans.isEmpty()) {
			ResultCreator.spans = spans;
			ResultCreator.first = first;
			ResultCreator.second = second;
			recreateTable();
		}
	}

	public static void create(Set<? extends Span> spans, Set<Rule> first, Set<Rule> second, String... titlePath) {
		if (titlePath.length >= 1)
			ResultCreator.title = titlePath[0];
		if (titlePath.length >= 2)
			ResultCreator.path = titlePath[1] + "/";
		create(spans, first, second);
	}

	public static void create(Set<? extends Span> spans, Set<Rule> first, Set<Rule> second, boolean norm, boolean abs,
			String... titlePath) {
		if (!spans.isEmpty()) {
			ResultCreator.first = first;
			ResultCreator.second = second;
			ResultCreator.spans = spans;
			if (titlePath.length >= 1)
				ResultCreator.title = titlePath[0];
			if (titlePath.length >= 2)
				ResultCreator.path = titlePath[1] + "/";
			table = new Table();
			if (norm)
				exportResultTable(table);
			if (abs)
				exportAbstractTable(table);
		}
	}

	public static void create(CdaWorker cda, String... titlePath) {
		if (titlePath.length >= 1)
			ResultCreator.title = titlePath[0];
		if (titlePath.length >= 2)
			ResultCreator.path = titlePath[1] + "/";
		create(cda.getResult(), cda.first, cda.second);
	}

	public static void recreateTable() {
		table = new Table();
		exportResultTable(table);
		exportAbstractTable(table);
	}

	private static void exportResultTable(Table table) {
		String result = (withBorder ? "<style>th{border: 1px solid black;padding:7px;}</style>" : "")
				+ "<div style=\"position: relative; left: 50%; right: 50%;\">" + title
				+ "</div><table style=\"border-collapse: collapse;\" align=\"center\">\n<tr style='background:lightgrey'></th><th>";
		for (Rule r2 : second)
			if (r2 != null)
				result += begin + r2.getName() + end;
		result += "</tr>\n";
		Map<String, String> reasonKinds = new HashMap<>();
		for (Rule r1 : first)
			if (r1 != null) {
				result += "<tr>" + beginFirst + r1.getName() + end;
				String row = "";
				for (Rule r2 : second) {
					Set<Span> reasons = table.getSpans(r1, r2);
					if (reasons == null)
						row += begin + end;
					else {
						String entry = "";
						Map<String, Pair<String, Integer>> kinds = new HashMap<>();
						for (Span reason : reasons) {
							Pair<String, Integer> id = kinds.get(reason.getFullID());
							if (id == null)
								id = new Pair<>(reason.getFullName(), 0);
							id.second++;
							kinds.put(reason.getFullID(), id);
						}
						for (String id : kinds.keySet()) {
							reasonKinds.put(id, kinds.get(id).first);
							if (!entry.contains(id))
								entry += comma + id + (kinds.get(id).second>1?"(" + kinds.get(id).second + ")":"");
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
		saveTXT(result);
	}

	private static void exportAbstractTable(Table table) {
		String result = "";
		String firstline = (withBorder ? "<style>th{border: 1px solid black;padding:7px;}</style>" : "")
				+ "<div style=\"\r\n" + "position: relative;\r\n" + "left: 50%;\r\n" + "right: 50%;\r\n" + "\">" + title
				+ "</div>"
				+ "<table style=\"border-collapse: collapse;\" align=\"center\">\n<tr style='background:lightgrey'><th></th>";
		Map<RuleConfigurator, Map<RuleConfigurator, Map<String, Integer>>> data = new LinkedHashMap<>();
		Set<RuleConfigurator.RuleType> ruleConfigurations = new HashSet<>();
		Map<String, String> reasonKinds = new HashMap<>();
		List<RuleConfigurator> firstIn = new ArrayList<>();
		List<RuleConfigurator> secondIn = new ArrayList<>();
		for (Rule r1 : first)
			if (r1 != null) {
				RuleConfigurator rc1 = new RuleConfigurator(r1);
				for (Rule r2 : second)
					if (r2 != null) {
						Set<Span> reasons = table.getSpans(r1, r2);
						if (reasons != null) {
							RuleConfigurator rc2 = new RuleConfigurator(r2);
							ruleConfigurations.addAll(rc1.getTypes());
							ruleConfigurations.addAll(rc2.getTypes());
							Map<RuleConfigurator, Map<String, Integer>> map = data.get(rc1);
							if (map == null) {
								map = new LinkedHashMap<>();
								data.put(rc1, map);
							}
							if (!firstIn.contains(rc1))
								firstIn.add(rc1);
							if (!secondIn.contains(rc2))
								secondIn.add(rc2);
							Map<String, Integer> tags = map.get(rc2);
							if (tags == null) {
								tags = new HashMap<>();
								map.put(rc2, tags);
							}
							for (Span reason : reasons) {
								Integer size = tags.get(reason.getFullID());
								if (size == null)
									size = 0;
								size++;
								reasonKinds.put(reason.getFullID(), reason.getFullName());
								tags.put(reason.getFullID(), size);
							}
						}
					}
			}
		for (RuleConfigurator r : secondIn)
			firstline += begin + r.getTAG() + end;
		for (RuleConfigurator rc1 : firstIn) {
			String row = "";
			Map<RuleConfigurator, Map<String, Integer>> rc1Data = data.get(rc1);
			if (rc1Data != null) {
				for (RuleConfigurator rc2 : secondIn) {
					Map<String, Integer> rc2Data = rc1Data.get(rc2);
					String reasons = "";
					if (rc2Data == null)
						reasons = comma;
					else
						for (String tag : rc2Data.keySet())
							reasons += comma + tag + (rc2Data.get(tag)>1?"(" + rc2Data.get(tag) + ")":"");
					row += begin + reasons.substring(comma.length()) + end;
				}
				result += "<tr>" + beginFirst + rc1.getTAG() + end + row + "</tr>\n";
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
		saveTXT(result, 'a');
	}

	private static void saveTXT(String data, char... sufix) {
		BufferedWriter writer;
		try {
			String name = title.replaceAll("\\ ", "_");
			for (char c : sufix)
				name += "_" + c;
			writer = new BufferedWriter(new FileWriter(path+ name + ".html"));
			writer.write(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class Table {
		private Map<String, Map<String, Set<Span>>> content = new HashMap<>();

		public Table() {
			for (Span span : ResultCreator.spans) {
				Map<String, Set<Span>> firstR = content.get(span.getRule1().getName());
				if (firstR == null) {
					firstR = new HashMap<>();
					content.put(span.getRule1().getName(), firstR);
				}
				Set<Span> secondR = firstR.get(span.getRule2().getName());
				if (secondR == null) {
					secondR = new HashSet<>();
					firstR.put(span.getRule2().getName(), secondR);
				}
				secondR.add(span);
			}
		}

		public Set<Span> getSpans(Rule r1, Rule r2) {
			Map<String, Set<Span>> result = content.get(r1.getName());
			if (result == null)
				return null;
			return result.get(r2.getName());
		}
	}
}
