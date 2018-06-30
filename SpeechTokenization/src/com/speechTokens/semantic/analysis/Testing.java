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
			"        \"Instanz\": { \"type\": \"uri1\"} ,\r\n" + 
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
			"        \"Keyword\": { \"type\": \"test\"}\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"Project\"} ,\r\n" + 
			"        \"Keyword\": { \"type\": \"Highnet\"}\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"Application\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"Mueller\" }\r\n" + 
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
		//chunks.addSemanticToChunk("post", jsonString);
		//chunks.addSemanticToChunk("Hallo", jsonString1);
		chunks.addSemanticToChunk("Mond", jsonString2);
		//KeywordSearch.noKeyword(chunks).printList();
		//chunks.addSemanticToChunk("Geeeht", "test");
		test(chunks);
	
	}

	public static void test(Chunker chunks) {
		Chunker semFoundChunks = KeywordSearch.noKeyword(chunks);
		for (int i = 0; i < semFoundChunks.size(); i++) {
			Object semantic = semFoundChunks.getSemanticAt(i);
			if(semantic instanceof ArrayList<?>) {
				ArrayList<?> newSemantic = (ArrayList<?>) semantic; 
				if(newSemantic.size()>1) {// more than one sem data off the respective chunk, so we dont know which type
					String chunk = semFoundChunks.getChunkContentAt(i);
					Object sem = semFoundChunks.getSemanticAt(i);
					Chunker tokenChunker = new Chunker();
					tokenChunker.addChunkContent(chunk);
					tokenChunker.addSemanticToChunk(chunk, sem);
					
					//
					// TODO HIER DIE ERSTELLUNG VON EVENTS EINBINDEN WO DER TYP NICHT KLAR IST
				}else { // just one semantic entry was found for the chunk
					// Hier geht es um ein Event, die eine spezifische Aktion erfordert
					String chunk = semFoundChunks.getChunkContentAt(i);
					Object sem = semFoundChunks.getSemanticAt(i);
					Chunker tokenChunker = new Chunker();
					tokenChunker.addChunkContent(chunk);
					tokenChunker.addSemanticToChunk(chunk, sem);
					tokenChunker.printList();
					// TODO: ACTIONEVENT oder spezifische Events hier erstellen
				}
			}
		}
	}

}
