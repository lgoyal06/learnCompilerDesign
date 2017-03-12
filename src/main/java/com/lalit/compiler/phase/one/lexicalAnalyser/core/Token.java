package com.lalit.compiler.phase.one.lexicalAnalyser.core;

import java.util.HashMap;

public class Token {

	private String tokenType;
	private HashMap<String, String> attributes;

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public HashMap<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}
}
