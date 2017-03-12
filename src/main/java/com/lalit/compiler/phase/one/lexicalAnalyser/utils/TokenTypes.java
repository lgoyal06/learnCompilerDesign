package com.lalit.compiler.phase.one.lexicalAnalyser.utils;

public class TokenTypes {

	public static final String[] KEYWORLDS_TOKEN_LIST = new String[] { "abstract", "continue", "goto", "package",
			"switch", "assert", "default", "if", "private", "this", "boolean", "do", "implements", "protected", "throw",
			"break", "double", "import", "public", "throws", "byte", "else", "instanceof", "return", "transient",
			"case", "extends", "int", "short", "try", "catch", "final", "interface", "static", "void", "char",
			"finally", "long", "strictfp", "volatile", "class", "float", "native", "super", "while", "const", "for",
			"new", "synchronized" };

	public static final char[] SEPARATORS_TOKEN_LIST = new char[] { ';', ',', '.', '(', ')', '{', '}', '[', ']' };

	public static final String[] OPERATORS_TOKEN_LIST = new String[] { "=", ">", "<", "!", "~", "?", ",", ":", "==",
			"<=", ">=", "!=", "&&", "||", "++", "--", "+", "-", "*", "/", "&", "|", "^", "%", "<<", ">>", ">>>", "+=",
			"-=", "*=", "/=", "&=", "|=", "^=", "%=", "<<=", ">>=", ">>=" };

	public static final String[] ESCAPE_SQUENCECES_LIST = new String[] { "\n", "\t", "\\v", "\b", "\r", "\f", "\\a",
			"\\", "\'", "\"" };

	public static final String[] COMMENTS_TOKEN_LIST = new String[] { "//", "/*", "*/" };

	public static final String[] LITERALS_TOKEN_LIST = new String[] { "int", "double", "boolean", "char", "String",
			"null", "float" };
}
