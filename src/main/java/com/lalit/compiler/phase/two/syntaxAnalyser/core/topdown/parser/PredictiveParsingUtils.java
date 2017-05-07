package com.lalit.compiler.phase.two.syntaxAnalyser.core.topdown.parser;

import java.util.Map;

import com.lalit.stack.api.StackViaLinkedList;

public class PredictiveParsingUtils {

	private static StackViaLinkedList<String> stackOfSymbols = new StackViaLinkedList<>();

	public static boolean isIncomingTokensStringValid() {
		String[] incomingTokensStringArray = new String[] { "(", "id", "+", "id", "*", "id", ")" };
		int lengthOfIncomingTokens = incomingTokensStringArray.length;
		int currentIndexOfIncomingTokens = 0;
		pushStartTerminal();
		while (stackOfSymbols.size() > 0 && (currentIndexOfIncomingTokens < lengthOfIncomingTokens)) {
			if (stackOfSymbols.peak().equals(incomingTokensStringArray[currentIndexOfIncomingTokens])) {
				stackOfSymbols.pop();
				++currentIndexOfIncomingTokens;
			} else {
				String[] production = getProductions(stackOfSymbols.peak(),
						incomingTokensStringArray[currentIndexOfIncomingTokens]);
				if (production != null) {
					stackOfSymbols.pop();
					for (int i = production.length - 1; i >= 0; --i) {
						if (!production[i].equals("$"))
							stackOfSymbols.push(production[i]);
					}
				} else {
					System.err.println("Error while parsing Incoming Token on given grammer!!");
					return false;
				}
			}
		}
		if (currentIndexOfIncomingTokens == lengthOfIncomingTokens) {
			System.out.println("Incoming Token is successfully parsed on given Grammer");
			return true;
		}
		return false;
	}

	private static void pushStartTerminal() {
		stackOfSymbols.push("E");
	}

	private static String[] getProductions(String topTerminalInStack, String currentIncomingStringToken) {
		Map<String, String[]> map = ParsingTableAlgo.nonTerminalProductionsMap.get(topTerminalInStack);
		return map.get(currentIncomingStringToken);
	}
}