package com.speechTokens.semantic.analysis;

import java.util.ArrayList;

import com.speechTokens.tokenizer.Chunker;

public class Testing {
	public static String jsonString = "{\r\n" + 
			"  \"head\": {\r\n" + 
			"    \"vars\": [ \"Instanz\" , \"Keyword\" ]\r\n" + 
			"  } ,\r\n" + 
			"  \"results\": {\r\n" + 
			"    \"bindings\": [\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\"} ,\r\n" + 
			"        \"Keyword\": { \"type\": \"Test\"}\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\"} ,\r\n" + 
			"        \"Keyword\": { \"type\": \"test1\"}\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"Wie\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"Test2\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"es\"} ,\r\n" + 
			"        \"Keyword\": { \"type\": \"test4\" }\r\n" + 
			"      }\r\n" + 
			"    ]\r\n" + 
			"  }\r\n" + 
			"}\r\n" + 
			"";

	public static String jsonString1 = "{\r\n" + 
			"  \"head\": {\r\n" + 
			"    \"vars\": [ \"Instanz\" , \"Keyword\" ]\r\n" + 
			"  } ,\r\n" + 
			"  \"results\": {\r\n" + 
			"    \"bindings\": [\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\"} ,\r\n" + 
			"        \"Keyword\": { \"type\": \"Test1\"}\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"Welt\"} ,\r\n" + 
			"        \"Keyword\": { \"type\": \"feuer\"}\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"Wie\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"geht\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"es\"} ,\r\n" + 
			"        \"Keyword\": { \"type\": \"dir\" }\r\n" + 
			"      }\r\n" + 
			"    ]\r\n" + 
			"  }\r\n" + 
			"}\r\n" + 
			"";
	
	public static String jsonString2 = "{\r\n" + 
			"  \"head\": {\r\n" + 
			"    \"vars\": [ \"Instanz\" , \"Keyword\" ]\r\n" + 
			"  } ,\r\n" + 
			"  \"results\": {\r\n" + 
			"    \"bindings\": [\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"Document\"} ,\r\n" + 
			"        \"Keyword\": { \"type\": \"uri\"}\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"Project\"} ,\r\n" + 
			"        \"Keyword\": { \"type\": \"Highnet\"}\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"Application\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"Welt\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"Welt\"} ,\r\n" + 
			"        \"Keyword\": { \"type\": \"Person\" }\r\n" + 
			"      }\r\n" + 
			"    ]\r\n" + 
			"  }\r\n" + 
			"}\r\n" + 
			"";

	
	public static void main(String[] args) {

		//System.out.println(JsonHandler.semanticLookUp("{}", "litrgdsteral").isEmpty());
		//JsonHandler.prettyPrint(Interpretation.jsonString);
		Chunker chunks = new Chunker();

		
		ArrayList<String> test =new ArrayList<>();
		test.add("fdsfdsfdsa");

		
		chunks.addChunkContent("uri");
		chunks.addChunkContent("Welt");
		chunks.addChunkContent("Mond");
		
		chunks.addChunkContent("post");
		chunks.addChunkContent("Hallo");
		chunks.addSemanticToChunk("post", jsonString);
		//chunks.addSemanticToChunk("Hallo", jsonString1);
		//chunks.addSemanticToChunk("Mond", jsonString2);

		//chunks.addSemanticToChunk("Geeeht", "test");

	
	}

}
