package com.speechTokens.semantic.analysis;

import java.util.ArrayList;

import com.speechTokens.tokenizer.Chunker;

public class Interpretation {

	/*
	/**
	 * Searches a keyword in the Semantic information in the given Chunker object
	 * If there is a chunk with an equal name like the keyword, its semantic information
	 * @param keyword the keyword that is searched for in the Chunker Object
	 * @param chunks the Chunker Object containing all the chunks with the sem infos
	 * @return a new Chunker Object, which only contains the Chunks whereto something was found plus the semantic data which was found
	 
	public static Chunker oneKeyword(String keyword, Chunker chunks){
		// TODO: Das identifiziertes Keywort aus dem Chunker löschen
		// TODO: Evtl Fehlerbehebung
		Chunker results= new Chunker();
		System.out.println(chunks.size());
		for (int j = 0; j < chunks.size(); j++) {
			ArrayList<String> foundSemOfChunk = new ArrayList<String>(); // the semantics that were found by the keyword and belong to ONE chunk
			Boolean semanticExistance = chunks.getSemanticAt(j) != null;
			Boolean isSemanticString = (chunks.getSemanticAt(j) instanceof String);
			Boolean keywordIsChunk = chunks.getChunkContentAt(j).equals(keyword); // check whether the keyword equals the current chunk
			if(semanticExistance && isSemanticString && !keywordIsChunk){
			Object semOfChunk = chunks.getSemanticAt(j);

				if(semOfChunk instanceof String){ // The semantic information in the array is
					System.out.println(j);
					foundSemOfChunk = JsonHandler.semanticLookUp((String) semOfChunk, keyword);
				}else if(semOfChunk instanceof ArrayList<?>){
					ArrayList<String> list = (ArrayList<String>) semOfChunk;
					for (String string : list) {
						foundSemOfChunk = JsonHandler.semanticLookUp(string, keyword);	
					}
				}else{
					System.out.println("Error with sem info");
				}
				
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
*/
	
	
	
	
	public static Chunker oneKeyword(String keyword, Chunker chunks){
		// TODO: Das identifiziertes Keywort aus dem Chunker löschen
		// TODO: Evtl Fehlerbehebung
		Chunker results= new Chunker();
		for (int j = 0; j < chunks.size(); j++) {
			ArrayList<String> foundSemOfChunk = new ArrayList<String>(); // the semantics that were found by the keyword and belong to ONE chunk
			Boolean semanticExistance = chunks.getSemanticAt(j) != null;
			Boolean isSemanticString = (chunks.getSemanticAt(j) instanceof String);
			Boolean keywordIsChunk = chunks.getChunkContentAt(j).equals(keyword); // check whether the keyword equals the current chunk
			if(semanticExistance && isSemanticString && !keywordIsChunk){
				JsonHandler js = new JsonHandler();
				foundSemOfChunk = js.semanticLookUp((String) chunks.readSemanticOfChunk(chunks.getChunkContentAt(j)), keyword);
			}else {
				System.out.println("No semantic or not a String or keyword is a chunk");
			}
			if(!foundSemOfChunk.isEmpty()) {
				results.addChunkContent(chunks.getChunkContentAt(j));
				results.addSemanticToChunk(chunks.getChunkContentAt(j), foundSemOfChunk);
			}
		}
		return results;		
	}
	
	
	
	
	
	
	public static Chunker noKeyword(Chunker chunks) {
		Chunker newChunker = new Chunker();
		ArrayList<String> list = chunks.readChunks();
		for (int i = 0; i < list.size(); i++) {
			Chunker tempChunker = oneKeyword(chunks.getChunkContentAt(i), chunks); // returns a Chunker which can be empty or has one chunk with sem Information
			if(tempChunker.size()>0) { // If the chunker that was returned by the oneKeyword function has a chunk go further
				for (int j = 0; j < tempChunker.size(); j++) {
					String chunk = tempChunker.getChunkContentAt(j);
					if(tempChunker.hasSemInfo(chunk)){ // if the chunk still has Semantic Information go further
						newChunker.addChunkContent(chunk);
						newChunker.addSemanticToChunk(chunk, tempChunker.readSemanticOfChunk(chunk));
					}	
				}
			}
		}
		return newChunker;
	}
}
