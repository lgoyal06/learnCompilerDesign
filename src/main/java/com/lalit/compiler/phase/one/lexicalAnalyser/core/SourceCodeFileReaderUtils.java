package com.lalit.compiler.phase.one.lexicalAnalyser.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceCodeFileReaderUtils {

	static int currentLexemeIndex = 0;
	static int forwardPointerIndex = 0;
	static StringBuffer currentLexeme = new StringBuffer("");
	static List<Token> tokenList = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		File file = new File(args[0]);
		FileInputStream fis = null;
		boolean isStringLiteralFound = false;
		boolean isCommentTokenFound = false;
		String currentCommentTokenStartingTag = "";
		String currentCommentTokenClosingTag = "";
		if (!file.exists()) {
			System.out.println(args[0] + " does not exist.");
			return;
		}
		if (!(file.isFile() && file.canRead())) {
			System.out.println(file.getName() + " cannot be read from.");
			return;
		}
		try {
			fis = new FileInputStream(file);
			char currentChar;
			while (fis.available() > 0) {
				currentChar = (char) fis.read();
				currentLexeme = currentLexeme.append(currentChar);
				++forwardPointerIndex;
				// // TODO Priority 2 String Literal Token
				// if (currentChar == '"') {
				// isStringLitrealFound =true;
				//
				// continue;
				// }

				// TODO Priority 1 Doing Comment Token
				if (TokenPatternMatcher.isMatchingCommentToken(currentLexeme.toString().trim())
						|| (isCommentTokenFound)) {
					if (!isCommentTokenFound) {
						currentCommentTokenStartingTag = currentLexeme.toString().trim();
						currentLexeme = currentLexeme.delete(0, forwardPointerIndex - currentLexemeIndex);
						currentLexemeIndex = forwardPointerIndex;
						isCommentTokenFound = true;
					} else if (currentCommentTokenStartingTag.equals("//")
							&& (currentChar == '\r' || currentChar == '\n')) {
						buildTokenObjAndAddToTokenList(currentLexeme.toString().trim(), "Comments");
						currentLexeme = currentLexeme.delete(0, forwardPointerIndex - currentLexemeIndex);
						currentLexemeIndex = forwardPointerIndex;
						isCommentTokenFound = false;
						currentCommentTokenStartingTag = "";
					} else if ("/*".equals(currentCommentTokenStartingTag)
							&& (currentChar == '*' || currentChar == '/')) {
						currentCommentTokenClosingTag = currentCommentTokenClosingTag + String.valueOf(currentChar);
						if ("*/".equals(currentCommentTokenClosingTag)) {
							buildTokenObjAndAddToTokenList(
									currentLexeme.toString().trim().substring(0, currentLexeme.length() - 3),
									"Comments");
							currentLexeme = currentLexeme.delete(0, forwardPointerIndex - currentLexemeIndex);
							currentLexemeIndex = forwardPointerIndex;
							isCommentTokenFound = false;
							currentCommentTokenStartingTag = "";
							currentCommentTokenClosingTag = "";
						}
					}
					continue;
				}

				if (currentChar == ' ' || TokenPatternMatcher.isMatchingCharLiteralToken(currentChar)
						|| TokenPatternMatcher.isMatchingSeparatorToken(currentChar)) {

					// Add Separator Token in case if it combined with keywork
					// or identifier,
					if (TokenPatternMatcher.isMatchingSeparatorToken(currentChar)) {
						buildTokenObjAndAddToTokenList(String.valueOf(currentChar), "Separator");
						currentLexeme.deleteCharAt(currentLexeme.length() - 1);
					}
					if (TokenPatternMatcher.isMatchingKeywordToken(currentLexeme.toString().trim())) {
						buildTokenObjAndAddToTokenList(currentLexeme.toString().trim(), "Keyword");
					} else if (TokenPatternMatcher.isMatchingOperatorToken(currentLexeme.toString().trim())) {
						buildTokenObjAndAddToTokenList(currentLexeme.toString().trim(), "Operator");
					} else if (TokenPatternMatcher.isMatchingIdentifierToken(currentLexeme.toString().trim())) {
						buildTokenObjAndAddToTokenList(currentLexeme.toString().trim(), "Identifier");
					} else if (currentLexeme.toString().trim().length() == 1 && TokenPatternMatcher
							.isMatchingSeparatorToken(currentLexeme.toString().trim().toCharArray()[0])) {
						buildTokenObjAndAddToTokenList(currentLexeme.toString().trim(), "Separator");
					}
					// TODO print Char literal as \r, \n in console
					if (TokenPatternMatcher.isMatchingCharLiteralToken(currentChar)) {
						buildLiteralTypeTokenObjAndAddToTokenList(String.valueOf(currentChar), "Literal", "char");
					}
					currentLexeme = currentLexeme.delete(0, forwardPointerIndex - currentLexemeIndex);
					currentLexemeIndex = forwardPointerIndex;
				}
			}
			if (isCommentTokenFound) {
				buildTokenObjAndAddToTokenList(currentLexeme.toString().trim(), "Comments");
				currentLexeme = currentLexeme.delete(0, forwardPointerIndex - currentLexemeIndex);
				currentLexemeIndex = forwardPointerIndex;
			}
			printTokenOnConsoleForDeveloper(tokenList);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fis.close();
		}

	}

	private static void buildTokenObjAndAddToTokenList(String currentToken, String tokenType) {
		HashMap<String, String> attributeValueMap = new HashMap<>();
		attributeValueMap.put("value", currentToken);
		tokenList.add(new Token(tokenType, attributeValueMap));
	}

	private static void buildLiteralTypeTokenObjAndAddToTokenList(String currentToken, String tokenType,
			String literalType) {
		HashMap<String, String> attributeValueMap = new HashMap<>();
		attributeValueMap.put("value", currentToken);
		attributeValueMap.put("type", literalType);
		tokenList.add(new Token(tokenType, attributeValueMap));
	}

	private static void printTokenOnConsoleForDeveloper(List<Token> list) {
		for (Token token : list) {
			System.out.println(token.getTokenType());
			for (Map.Entry<String, String> entry : token.getAttributes().entrySet()) {
				System.out.println("\t" + entry.getKey() + ":" + entry.getValue());
			}
		}
	}
}