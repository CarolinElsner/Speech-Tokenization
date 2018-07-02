package com.speechTokens.semantic.analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.speechTokens.tokenizer.Chunker;
import com.speechTokens.tokenizer.Tokenization;

import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.model.EventUtils;

public class Testing {
	public static String jsonString = "{\r\n" + 
			"  \"head\": {\r\n" + 
			"    \"vars\": [ \"Instanz\" , \"Keyword\" ]\r\n" + 
			"  } ,\r\n" + 
			"  \"results\": {\r\n" + 
			"    \"bindings\": [\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"Document\", } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"Test\"}\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"Document\"} ,\r\n" + 
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
			"        \"Instanz\": { \"type\": \"Project\"} ,\r\n" + 
			"        \"Keyword\": { \"type\": \"Highnet\"}\r\n" + 
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

		Chunker chunks = new Chunker();

		ArrayList<String> test1 =new ArrayList<>();
		test1.add("{\"Instanz\":{\"type\":\"http://semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Person\"},\"Keyword\":{\"type\":\"Test\"}}");
		ArrayList<String> test =new ArrayList<>();
		test.add("{\\r\\n\" + \r\n" + 
				"			\"        \\\"Instanz\\\": { \\\"type\\\": \\\"Project\\\"} ,\\r\\n\" + \r\n" + 
				"			\"        \\\"Keyword\\\": { \\\"type\\\": \\\"Highnet\\\"}\\r\\n\" + \r\n" + 
				"			\"      }");
		ArrayList<String> test2 =new ArrayList<>();
		chunks.addChunkContent("uri");
		chunks.addChunkContent("fhad");
		chunks.addChunkContent("Mond");
		
		chunks.addChunkContent("hilfe");
		chunks.addChunkContent("Hallo");
		chunks.addSemanticToChunk("Hallo", jsonString);
		chunks.addSemanticToChunk("Mond", jsonString1);
		chunks.addSemanticToChunk("uri", jsonString2);

		

		
		//chunks.printList();
		Chunker newChunker = new Chunker();
		newChunker.addChunkContent("test");
		//newChunker.addSemanticToChunk("test", test1);

		newChunker.removeChunkAndSem("test");
		newChunker.printList();
		//KeywordSearch.noKeyword(chunks).printList();
		System.out.println("adsf");
	
		//System.out.println(chunks.getSemanticAt(4));
		//chunks.addSemanticToChunk("Mond", jsonString);
		//chunks.addSemanticToChunk("post", jsonString1);
		//chunks.addSemanticToChunk("Hallo", jsonString2);
		/*
		ArrayList<String> keys= new ArrayList<>();
		keys.add("Welt");
		keys.add("Person");
		keys.add("uri");
		KeywordSearch.severalKeywords(keys, chunks).printList();
		
		//chunks.printList();
		//KeywordSearch.noKeyword(chunks).printList();
		//chunks.addSemanticToChunk("Geeeht", "test");
		//JsonHandler js = new JsonHandler();
		//System.out.println(js.semanticLookUp(jsonString1, "uri1"));
		String satz= "Thomas how are you doing at Highnet?";
		try {
			List<String> tokens = Tokenization.doTokenization(satz);
			System.out.println(tokens);
			Chunker chunk = new Chunker();
			for (int i=0; i<tokens.size();i++) {
				chunk.addChunkContent(tokens.get(i).toLowerCase());
			}
			chunk.addSemanticToChunk("highnet", jsonString2);
			chunk.addSemanticToChunk("thomas", jsonString1);

			Chunker foundResults = new Chunker();
			int keywordcount=0;
			if(KeywordSearch.CheckIfSemanticsGiven(chunk) == true) {
				 
				ArrayList<String> foundkeywords = KeywordSearch.findKeywords(chunk);
				
				if(keywordcount == 0) {
					foundResults = KeywordSearch.noKeyword(chunk);
					System.out.println("0 keywords"+foundkeywords);				
					//Procedure for 0 found keywords
					
				}else if (keywordcount == 1) {
					foundResults = KeywordSearch.oneKeyword(foundkeywords.get(0), chunk);
					System.out.println("1 keyword"+foundkeywords);
					//Procedure for 1 found keyword
				}else {
					
					System.out.println(">1 keywords"+foundkeywords);								
				}
			}
			foundResults.printList();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
	
	public static void createEvent(Chunker chunks) {
		ArrayList<String> semanticStr = (ArrayList<String>) chunks.getSemanticAt(0);
		if(semanticStr.get(0).contains("Document")== true) {

			chunks.printList();
		}else if(semanticStr.get(0).contains("Project")== true) {
			chunks.printList();
						System.out.println("Projekt");
		}else if(semanticStr.get(0).contains("Person")== true) {
			chunks.printList();
						System.out.println("Person");
		}else {
			chunks.printList();
			System.out.println("No specific type detected, uncertain event has to be pushed");
		}

	}
	
}
