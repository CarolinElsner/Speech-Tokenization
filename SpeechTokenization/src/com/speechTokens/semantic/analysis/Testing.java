package com.speechTokens.semantic.analysis;

public class Testing {

	public static void main(String[] args) {
		System.out.println(JsonHandler.semanticLookUp(Interpretation.jsonString, "budget; ; plan;").size());
		JsonHandler.prettyPrint(Interpretation.jsonString);
		// TODO: SCHAUEN WENN KEINE ERGEBNISSE IN BINDINGS SIND
	}

}
