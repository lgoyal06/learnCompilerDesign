package com.lalit.compiler.phase.one.lexicalAnalyser.core;

import java.util.HashMap;

public class Token {

	private String tokenType;
	private HashMap<String, String> attributes;
	private String[] errorMessages;

	public Token(String tokenType, HashMap<String, String> attributes) {
		this.tokenType = tokenType;
		this.attributes = attributes;
	}

	public Token(String tokenType, HashMap<String, String> attributes, String[] errorMessages) {
		this(tokenType, attributes);
		this.errorMessages = errorMessages;
	}

	public String[] getErrorMessages() {
		return errorMessages;
	}

	public String getTokenType() {
		return tokenType;
	}

	public HashMap<String, String> getAttributes() {
		return attributes;
	}
}