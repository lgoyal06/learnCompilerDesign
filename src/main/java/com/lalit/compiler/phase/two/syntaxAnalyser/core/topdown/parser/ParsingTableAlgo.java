package com.lalit.compiler.phase.two.syntaxAnalyser.core.topdown.parser;

import java.util.HashMap;
import java.util.Map;

public class ParsingTableAlgo {

	public static Map<String, Map<String, String[]>> nonTerminalProductionsMap = new HashMap<String, Map<String, String[]>>();

	static {
		Map<String, String[]> mapTerminalToProductionMapForT1 = new HashMap<String, String[]>();
		mapTerminalToProductionMapForT1.put("id", new String[] { "T", "E'" });
		mapTerminalToProductionMapForT1.put("(", new String[] { "T", "E'" });
		nonTerminalProductionsMap.put("E", mapTerminalToProductionMapForT1);

		Map<String, String[]> mapTerminalToProductionMapForT2 = new HashMap<String, String[]>();
		mapTerminalToProductionMapForT2.put("+", new String[] { "+", "T", "E'" });
		mapTerminalToProductionMapForT2.put(")", new String[] { "$" });
		nonTerminalProductionsMap.put("E'", mapTerminalToProductionMapForT2);

		Map<String, String[]> mapTerminalToProductionMapForT3 = new HashMap<String, String[]>();
		mapTerminalToProductionMapForT3.put("id", new String[] { "F", "T'" });
		mapTerminalToProductionMapForT3.put("(", new String[] { "F", "T'" });
		nonTerminalProductionsMap.put("T", mapTerminalToProductionMapForT3);

		Map<String, String[]> mapTerminalToProductionMapForT4 = new HashMap<String, String[]>();
		mapTerminalToProductionMapForT4.put("+", new String[] { "$" });
		mapTerminalToProductionMapForT4.put("*", new String[] { "*", "F", "T'" });
		mapTerminalToProductionMapForT4.put(")", new String[] { "$" });
		nonTerminalProductionsMap.put("T'", mapTerminalToProductionMapForT4);

		Map<String, String[]> mapTerminalToProductionMapForT5 = new HashMap<String, String[]>();
		mapTerminalToProductionMapForT5.put("id", new String[] { "id" });
		mapTerminalToProductionMapForT5.put("(", new String[] { "(", "E", ")" });
		nonTerminalProductionsMap.put("F", mapTerminalToProductionMapForT5);
	}
}
