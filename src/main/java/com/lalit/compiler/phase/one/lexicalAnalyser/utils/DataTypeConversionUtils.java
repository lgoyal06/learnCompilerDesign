package com.lalit.compiler.phase.one.lexicalAnalyser.utils;

public class DataTypeConversionUtils {
	public static String escapeCharToStringConversion(char currentChar) {

		String charToString = "";

		switch (currentChar) {
		case '\n':
			charToString = "\\n";
			break;
		case '\t':
			charToString = "\\t";
			break;
		case '\b':
			charToString = "\\b";
			break;
		case '\r':
			charToString = "\\r";
			break;
		case '\f':
			charToString = "\\f";
			break;
		case '\\':
			charToString = "\\";
			break;
		case '\'':
			charToString = "\\'";
			break;
		case '\"':
			charToString = "\"";
			break;
		default:
			return "" + currentChar + "";
		}
		return charToString;
	}
}