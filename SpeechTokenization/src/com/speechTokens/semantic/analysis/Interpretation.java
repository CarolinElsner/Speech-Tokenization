package com.speechTokens.semantic.analysis;

import java.util.ArrayList;

import com.speechTokens.tokenizer.Chunker;

public class Interpretation {
	public static String jsonString = "{\r\n" + 
			"  \"head\": {\r\n" + 
			"    \"vars\": [ \"Instanz\" , \"Keyword\" ]\r\n" + 
			"  } ,\r\n" + 
			"  \"results\": {\r\n" + 
			"    \"bindings\": [\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostPlan\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"cost; expenses; expense; costs;\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#BudgetPlan\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"budget; cost; plan;\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri1\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Receipt\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"bill; receipt; cost; expense;\" }\r\n" + 
			"      } ,\r\n" + 
			"      {\r\n" + 
			"        \"Instanz\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostStatement\" } ,\r\n" + 
			"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"cost; costs; expense; expenses; statement\" }\r\n" + 
			"      }\r\n" + 
			"    ]\r\n" + 
			"  }\r\n" + 
			"}\r\n" + 
			"";


	/**
	 * Searches a keyword in the Semantic information in the given Chunker object
	 * @param keyword the keyword that is searched for in the Chunker Object
	 * @param chunks the Chunker Object containing all the chunks with the sem infos
	 * @return a new Chunker Object, which only contains the Chunks whereto something was found plus the semantic data which was found
	 */
	public static Chunker oneKeyword(String keyword, Chunker chunks){
		// TODO: Das identifiziertes Keywort aus dem Chunker löschen
		// TODO: Evtl Fehlerbehebung
		Chunker results= new Chunker();
		for (int j = 0; j < chunks.size(); j++) {
			ArrayList<String> foundSemOfChunk = new ArrayList<String>(); // the semantics that were found by the keyword and belong to ONE chunk
			Boolean semanticExistance = chunks.readSemanticOfChunk(chunks.getChunkContentAt(j)) != null;
			Boolean isSemanticString = (chunks.readSemanticOfChunk(chunks.getChunkContentAt(j)) instanceof String);
			if(semanticExistance && isSemanticString){
				foundSemOfChunk = JsonHandler.semanticLookUp((String) chunks.readSemanticOfChunk(chunks.getChunkContentAt(j)), keyword);
			}else {
				System.out.println("No semantic or not a String");
			}
			if(!foundSemOfChunk.isEmpty()) {
				results.addChunkContent(chunks.getChunkContentAt(j));
				results.addSemanticToChunk(chunks.getChunkContentAt(j), foundSemOfChunk);
			}
		}
		return results;		
	}
}
