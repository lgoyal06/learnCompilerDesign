package com.lalit.compiler.phase.two.syntaxAnalyser.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lalit goyal
 * 
 *         TODO : By 21st May 2017 Algo
 */
public class RemoveGrammerLeftRecursionUtils {

	private static final Map<String, String[]> nonTerminalToProductionsMap = new LinkedHashMap<>();
	private static String[] nonTerminals;

	static {
		nonTerminalToProductionsMap.put("E", new String[] { "E+T", "T", "F" });
		nonTerminalToProductionsMap.put("T", new String[] { "T*F", "F" });
		nonTerminalToProductionsMap.put("F", new String[] { "(E)", "identifier" });

		nonTerminals = new String[] { "E", "T", "F" };
	}

	public void removeLeftRecursion() {

		for (int i = 0; i < nonTerminals.length; ++i) {
			// TODO Handle InDirect Left Recursion
			for (int j = i + 1; j < nonTerminals.length; ++j) {
				removeInDirectLeftRecursion(nonTerminals[i], nonTerminals[j]);
			}
			nonTerminalToProductionsMap.put(nonTerminals[i], removeDirectLeftRecursion(nonTerminals[i]));
		}
	}

	private String[] removeDirectLeftRecursion(String currentNonTerminal) {

		String[] currentProductions = nonTerminalToProductionsMap.get(currentNonTerminal);

		String[] productionsWithDirectLeftRecursion = new String[currentProductions.length];
		String[] productionsWithOutDirectLeftRecursion = new String[currentProductions.length];

		int indexOfProductionsWithDirectLeftRecursion = 0;
		int indexOfProductionsWithOutDirectLeftRecursion = 0;

		for (int i = 0; i < currentProductions.length; ++i) {
			if (currentProductions[i].toCharArray()[0] == currentNonTerminal.toCharArray()[0]) {
				productionsWithDirectLeftRecursion[indexOfProductionsWithDirectLeftRecursion] = currentProductions[i];
				++indexOfProductionsWithDirectLeftRecursion;
			} else {
				productionsWithOutDirectLeftRecursion[indexOfProductionsWithOutDirectLeftRecursion] = currentProductions[i];
				++indexOfProductionsWithOutDirectLeftRecursion;
			}
		}
		String[] newProductionsWithoutLeftRecursion = new String[indexOfProductionsWithOutDirectLeftRecursion];

		if (indexOfProductionsWithDirectLeftRecursion > 0) {
			for (int i = 0; i < indexOfProductionsWithOutDirectLeftRecursion; ++i) {
				newProductionsWithoutLeftRecursion[i] = productionsWithOutDirectLeftRecursion[i] + currentNonTerminal
						+ "'";
			}
			String[] productions = new String[indexOfProductionsWithDirectLeftRecursion + 1];
			for (int i = 0; i < indexOfProductionsWithDirectLeftRecursion; ++i) {
				productions[i] = productionsWithDirectLeftRecursion[i].substring(1) + currentNonTerminal + "'";
			}

			nonTerminalToProductionsMap.put(currentNonTerminal + "'", productions);
			return newProductionsWithoutLeftRecursion;
		}
		return currentProductions;
	}

	private void removeInDirectLeftRecursion(String productionLeftSizeNonTerminal,
			String production1LeftSizeNonTerminal) {
		// TODO Add implementations
	}

	public static void main(String... s) {
		new RemoveGrammerLeftRecursionUtils().removeLeftRecursion();
	}
}
