package com.lalit.compiler.phase.two.syntaxAnalyser.core.topdown.parser;

import java.util.HashMap;
import java.util.Map;

public class ParsingTableAlgo {

	public static Map<String, Map<String, String>> nonTerminalProductionsMap = new HashMap<String, Map<String, String>>();

	static {
		Map<String, String> mapTerminalToProductionMapForT1 = new HashMap<String, String>();
		mapTerminalToProductionMapForT1.put("identifier", "TE'");
		mapTerminalToProductionMapForT1.put("(", "TE'");
		nonTerminalProductionsMap.put("E", mapTerminalToProductionMapForT1);

		Map<String, String> mapTerminalToProductionMapForT2 = new HashMap<String, String>();
		mapTerminalToProductionMapForT2.put("+", "+TE'");
		mapTerminalToProductionMapForT2.put(")", "$");
		nonTerminalProductionsMap.put("E'", mapTerminalToProductionMapForT2);

		Map<String, String> mapTerminalToProductionMapForT3 = new HashMap<String, String>();
		mapTerminalToProductionMapForT3.put("identifier", "FT'");
		mapTerminalToProductionMapForT3.put("(", "FT'");
		nonTerminalProductionsMap.put("T", mapTerminalToProductionMapForT3);

		Map<String, String> mapTerminalToProductionMapForT4 = new HashMap<String, String>();
		mapTerminalToProductionMapForT4.put("+", "&");
		mapTerminalToProductionMapForT4.put("*", "*FT'");
		mapTerminalToProductionMapForT4.put("(", "$");
		nonTerminalProductionsMap.put("T'", mapTerminalToProductionMapForT4);

		Map<String, String> mapTerminalToProductionMapForT5 = new HashMap<String, String>();
		mapTerminalToProductionMapForT5.put("identifier", "identifier");
		mapTerminalToProductionMapForT5.put("(", "$");
		nonTerminalProductionsMap.put("F", mapTerminalToProductionMapForT4);
	}
}
