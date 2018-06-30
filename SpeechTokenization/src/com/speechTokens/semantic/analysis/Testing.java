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
			"        \"Keyword\": { \"type\": \"literal\"}\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\"} ,\r\n" + 
			"        \"Keyword\": { \"type\": \"literal\"}\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri1\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"bill; receipt; cost; expense;\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\"} ,\r\n" + 
			"        \"Keyword\": { \"type\": \"literal\" }\r\n" + 
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
			"        \"Instanz\": { \"type\": \"test\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostPlan\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"uri\" , \"value\": \"cost; expenses; expense; costs;\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"test1\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#BudgetPlan\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"welt\" , \"value\": \"budget; cost; plan;\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"test2\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Receipt\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"hallo\" , \"value\": \"bill; receipt; cost; expense;\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"test\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostStatement\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"was\" , \"value\": \"cost; costs; expense; expenses; statement\" }\r\n" + 
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
			"        \"Instanz\": { \"type\": \"get\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostPlan\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"post\" , \"value\": \"cost; expenses; expense; costs;\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#BudgetPlan\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"budget; cost; plan;\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"wo\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Receipt\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"warum\" , \"value\": \"bill; receipt; cost; expense;\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostStatement\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"welt\" , \"value\": \"cost; costs; expense; expenses; statement\" }\r\n" + 
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
		chunks.addSemanticToChunk("uri", jsonString1);
		chunks.addSemanticToChunk("Mond", jsonString2);

		//chunks.addSemanticToChunk("Geeeht", "test");
		//System.out.println(chunks.getSemanticAt(2));
		//System.out.println(chunks.readSemanticOfChunk("Geeeht"));
		//chunks.printList();
		//Chunker ch = Interpretation.oneKeyword("test", chunks);

		Chunker ch = Interpretation.noKeyword(chunks);
		for (int i = 0; i < ch.size(); i++) {
			System.out.println(((ArrayList<String>)ch.getSemanticAt(i)));
			
		}
		
		//System.out.println(((ArrayList<String>)testing.getSemanticAt(0)).get(3));
	
	}

}
