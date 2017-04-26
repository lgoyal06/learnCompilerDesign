package com.lalit.compiler.phase.two.syntaxAnalyser.core.topdown.parser;

import com.lalit.stack.api.StackViaLinkedList;

//TODO 4 AM at 27th April 2017 must

public class PredictiveParsingAlgo {

	public static void main(String... s) {
		StackViaLinkedList<String> stackOfSymbols = new StackViaLinkedList<>();

		char[] incomingTokenBuffer = s[0].toCharArray();

		// Push top Non terminal to start
		stackOfSymbols.push("E");

		while (stackOfSymbols.size() > 0) {
			// terminal matched move pointer
			if (stackOfSymbols.peak().equals(s[0])) {
				System.out.println();
			}
			// Use Parse Table to find if any entry exists
			else if (true) {

			} else {
				// error
			}
		}
	}
}