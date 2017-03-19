package com.lalit.compiler.phase.one.lexicalAnalyser.core;

import com.lalit.compiler.phase.one.lexicalAnalyser.utils.ASCIICharactersSets;
import com.lalit.compiler.phase.one.lexicalAnalyser.utils.TokenTypes;

public class TokenPatternMatcher {

	// Transition Diagram for An Identifier matching
	public static boolean isMatchingIdentifierToken(String word) {

		char[] charArr = word.toCharArray();
		int index = 0;
		for (char charac : charArr) {
			if (index == 0) {
				if (isMatchingAlphabatPattern(charac) || charac == '$' || charac == '_') {
					return true;
				}
			} else {
				if (isMatchingAlphabatPattern(charac) || charac == '$' || charac == '_'
						|| isMatchingNumericPattern(charac)) {
					return true;
				}
			}
		}
		return false;
	}

	// Transition Diagram for Finding Keyword
	public static boolean isMatchingKeywordToken(String word) {
		for (String keyword : TokenTypes.KEYWORLDS_TOKEN_LIST)
			if (keyword.equals(word)) {
				return true;
			}
		return false;
	}

	private static boolean isMatchingAlphabatPattern(char chara) {
		for (char lowerChar : ASCIICharactersSets.LOWER_CASE_ALPHABAT_CHAR) {
			if (chara == lowerChar) {
				return true;
			}
		}

		for (char upparChar : ASCIICharactersSets.UPPAR_CASE_ALPHABAT_CHAR) {
			if (chara == upparChar) {
				return true;
			}
		}
		return false;
	}

	private static boolean isMatchingNumericPattern(char chara) {
		for (char numChar : ASCIICharactersSets.NUMERIC_CHAR) {
			if (chara == numChar) {
				return true;
			}
		}
		return false;
	}

	// Transition Diagram for Literals i.e. int , double , float, boolean,
	// String, null
	public boolean isMatchingLiteralPattern(char charac) {

		return false;
	}

	public boolean isMatchingIntLiteralPattern(char charac) {
		if (isMatchingNumericPattern(charac))
			return true;
		return false;
	}

	// Transition Diagram for Arithimatic or Logical Operators
	public static boolean isMatchingOperatorToken(String word) {
		for (String operator : TokenTypes.OPERATORS_TOKEN_LIST)
			if (operator.equals(word)) {
				return true;
			}
		return false;
	}

	// Transition Diagram for Arithimatic or Logical Operators
	public static boolean isMatchingSeparatorToken(char charac) {
		for (char separator : TokenTypes.SEPARATORS_TOKEN_LIST)
			if (separator == charac) {
				return true;
			}
		return false;
	}

	// Transition Diagram for Char Literal
	public static boolean isMatchingCharLiteralToken(char chara) {
		for (char escChar : TokenTypes.ESCAPE_SQUENCECES_LIST)
			if (chara == escChar) {
				return true;
			}
		return false;
	}

	// Transition Diagram for Comment Literal
	public static boolean isMatchingCommentToken(String word) {
		for (String operator : TokenTypes.COMMENTS_TOKEN_LIST)
			if (operator.equals(word)) {
				return true;
			}
		return false;
	}

}