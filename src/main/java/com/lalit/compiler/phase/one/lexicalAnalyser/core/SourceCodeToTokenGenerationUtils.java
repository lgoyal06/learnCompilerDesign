package com.lalit.compiler.phase.one.lexicalAnalyser.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lalit.compiler.phase.one.lexicalAnalyser.utils.DataTypeConversionUtils;

/**
 * @author lalit goyal
 * 
 *         Assumptions : Only white space (except when inside a textual literal
 *         or comment) separates tokens.
 * 
 */
public class SourceCodeToTokenGenerationUtils {

	static int currentLexemeIndex = 0;
	static int forwardPointerIndex = 0;
	static StringBuffer currentLexeme = new StringBuffer("");
	static List<Token> tokenList = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		File file = new File(args[0]);
		FileInputStream fis = null;
		boolean isStringLiteralFound = false;
		boolean isCharLiteralFound = false;
		boolean isCommentTokenDetected = false;
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

				/*
				 * Rule 1: Logic String literal
				 * 
				 */
				if ((!isCharLiteralFound && !isCommentTokenDetected)
						&& (isStringLiteralFound || TokenPatternMatcher.isMatchingStringLiteralToken(currentChar))) {
					if (!isStringLiteralFound) {
						isStringLiteralFound = true;
					} else {
						// Found Next Double quote
						if (currentChar == '"') {
							// Escaped double char i.e. String s = "abd\"ed";
							if (currentLexeme.charAt(currentLexeme.length() - 2) == '\\') {
								continue;
							}
							// Closing Double quote is found
							else {
								buildLiteralTypeTokenObjAndAddToTokenList(
										currentLexeme.substring(1, currentLexeme.length() - 1), "Literal", "String");
								currentLexeme = currentLexeme.delete(0, forwardPointerIndex - currentLexemeIndex);
								currentLexemeIndex = forwardPointerIndex;
								isStringLiteralFound = false;
							}
						}
						// Case end of the line reached but not yet found
						// closing double quotes . Invalid token error
						else if (currentChar == '\r' || currentChar == '\n') {
							buildLiteralTypeTokenWithErrorObjAndAddToTokenList(
									currentLexeme.substring(0, currentLexeme.length() - 1), "Literal", "String",
									new String[] { "Invalid String literal should close with dobule quotes" });
							currentLexeme = currentLexeme.delete(0, forwardPointerIndex - currentLexemeIndex);
							currentLexemeIndex = forwardPointerIndex;
							isStringLiteralFound = false;
						}
					}
					continue;
				}
				/*
				 * Rule 2: char literal
				 * 
				 * 
				 */
				if ((!isStringLiteralFound && !isCommentTokenDetected)
						&& (isCharLiteralFound || TokenPatternMatcher.isMatchingCharLiteralToken(currentChar))) {
					if (!isCharLiteralFound) {
						isCharLiteralFound = true;
					} else if (currentChar == '\'') {
						// Invalid char token as length >1
						if (currentLexeme.length() > 3) {
							buildLiteralTypeTokenWithErrorObjAndAddToTokenList("", "Literal", "char",
									new String[] { "Invalid char as length more than one" });
							currentLexeme = currentLexeme.delete(0, forwardPointerIndex - currentLexemeIndex);
							currentLexemeIndex = forwardPointerIndex;
							isCharLiteralFound = false;
						} else {
							buildLiteralTypeTokenObjAndAddToTokenList(
									DataTypeConversionUtils.escapeCharToStringConversion(currentLexeme.charAt(1)),
									"Literal", "char");
							currentLexeme = currentLexeme.delete(0, forwardPointerIndex - currentLexemeIndex);
							currentLexemeIndex = forwardPointerIndex;
							isCharLiteralFound = false;
						}
					}
					continue;
				}

				/*
				 * Rule 3: Comments Token Code done and tested with
				 * CommentsTokenSampleFile.txt Dated : 21 March 2017
				 * 
				 * We don't send Comment token to next stage i.e. parser of
				 * compiler
				 */

				if (TokenPatternMatcher.isMatchingCommentToken(currentLexeme.toString().trim())
						|| (isCommentTokenDetected)) {
					if (!isCommentTokenDetected) {
						currentCommentTokenStartingTag = currentLexeme.toString().trim();
						currentLexeme = currentLexeme.delete(0, forwardPointerIndex - currentLexemeIndex);
						currentLexemeIndex = forwardPointerIndex;
						isCommentTokenDetected = true;
					} else if (currentCommentTokenStartingTag.equals("//")
							&& (currentChar == '\r' || currentChar == '\n')) {
						currentLexeme = currentLexeme.delete(0, forwardPointerIndex - currentLexemeIndex);
						currentLexemeIndex = forwardPointerIndex;
						isCommentTokenDetected = false;
						currentCommentTokenStartingTag = "";
					} else if ("/*".equals(currentCommentTokenStartingTag)
							&& (currentChar == '*' || currentChar == '/')) {
						currentCommentTokenClosingTag = currentCommentTokenClosingTag + String.valueOf(currentChar);
						char[] chrArr = currentCommentTokenClosingTag.toCharArray();
						if ((chrArr.length > 1
								&& (chrArr[chrArr.length - 2] == '*' && chrArr[chrArr.length - 1] == '/'))) {
							currentLexeme = currentLexeme.delete(0, forwardPointerIndex - currentLexemeIndex);
							currentLexemeIndex = forwardPointerIndex;
							isCommentTokenDetected = false;
							currentCommentTokenStartingTag = "";
							currentCommentTokenClosingTag = "";
						}
					}
					continue;
				}

				/*
				 * Rule 4: Other than Comment, String and char literals and
				 * whitespace is found
				 */

				if (!isCommentTokenDetected && !isCharLiteralFound && !isStringLiteralFound) {
					if ((currentChar == ' ' || TokenPatternMatcher.isMatchingSeparatorToken(currentChar))
							&& currentLexeme.toString().trim().length() > 0) {
						if (TokenPatternMatcher.isMatchingIntLiteralPattern(currentLexeme.toString().trim())) {
							buildLiteralTypeTokenObjAndAddToTokenList(currentLexeme.toString().trim(), "Literal",
									"int");
						} else if (TokenPatternMatcher
								.isMatchingBooleanLiteralPattern(currentLexeme.toString().trim())) {
							buildLiteralTypeTokenObjAndAddToTokenList(currentLexeme.toString().trim(), "Literal",
									"boolean");
						} else if (TokenPatternMatcher
								.isMatchingDoubleLiteralPattern(currentLexeme.toString().trim())) {
							buildLiteralTypeTokenObjAndAddToTokenList(currentLexeme.toString().trim(), "Literal",
									"double");
						} else if (TokenPatternMatcher.isMatchingCharEscapeLiteralToken(currentChar)) {
							buildLiteralTypeTokenObjAndAddToTokenList(
									DataTypeConversionUtils.escapeCharToStringConversion(currentChar), "Literal",
									"char");
						} else if (TokenPatternMatcher.isMatchingSeparatorToken(currentChar)) {
							buildTokenObjAndAddToTokenList(String.valueOf(currentChar), "Separator");
						} else if (TokenPatternMatcher.isMatchingKeywordToken(currentLexeme.toString().trim())) {
							buildTokenObjAndAddToTokenList(currentLexeme.toString().trim(), "Keyword");
						} else if (TokenPatternMatcher.isMatchingOperatorToken(currentLexeme.toString().trim())) {
							buildTokenObjAndAddToTokenList(currentLexeme.toString().trim(), "Operator");
						} else if (TokenPatternMatcher.isMatchingIdentifierToken(currentLexeme.toString())) {
							buildTokenObjAndAddToTokenList(currentLexeme.toString().trim(), "Identifier");
						} else if (currentLexeme.toString().length() == 1 && TokenPatternMatcher
								.isMatchingSeparatorToken(currentLexeme.toString().toCharArray()[0])) {
							buildTokenObjAndAddToTokenList(currentLexeme.toString(), "Separator");
						}
						currentLexeme = currentLexeme.delete(0, forwardPointerIndex - currentLexemeIndex);
						currentLexemeIndex = forwardPointerIndex;
					} else if (TokenPatternMatcher.isMatchingCharEscapeLiteralToken(currentChar)) {
						buildLiteralTypeTokenObjAndAddToTokenList(
								DataTypeConversionUtils.escapeCharToStringConversion(currentChar), "Literal", "char");
						currentLexeme = currentLexeme.delete(0, forwardPointerIndex - currentLexemeIndex);
						currentLexemeIndex = forwardPointerIndex;
					}
				}
			}
			if (isCommentTokenDetected) {
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

	private static void buildLiteralTypeTokenWithErrorObjAndAddToTokenList(String currentToken, String tokenType,
			String literalType, String... errorMessage) {
		HashMap<String, String> attributeValueMap = new HashMap<>();
		attributeValueMap.put("value", currentToken);
		attributeValueMap.put("type", literalType);
		tokenList.add(new Token(tokenType, attributeValueMap, errorMessage));
	}

	private static void printTokenOnConsoleForDeveloper(List<Token> list) {
		for (Token token : list) {
			System.out.println(token.getTokenType());
			for (Map.Entry<String, String> entry : token.getAttributes().entrySet()) {
				System.out.println("\t" + entry.getKey() + ":" + entry.getValue());
			}
			if (token.getErrorMessages() != null && token.getErrorMessages().length > 0) {
				for (String error : token.getErrorMessages())
					System.out.println("\tError: " + error);
			}
		}
	}
}