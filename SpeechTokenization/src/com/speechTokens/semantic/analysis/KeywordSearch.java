package com.speechTokens.semantic.analysis;

import java.lang.reflect.Array;
import java.util.ArrayList;
import com.speechTokens.tokenizer.Chunker;

public class KeywordSearch {
	
	/**
	 * Looks for Keywords in the Chunker, and scanns for singular and plural of those by just looking into "contains"
	 * The found Keyword and its semantic will be deleted from the Chunker Object
	 * @param ch chunker ArrayList, which contains the chunks with the sem Info
	 */
	@Deprecated
	public static void keywordCheck(Chunker ch) {

		Chunker foundResults = new Chunker();
		
		if(CheckIfSemanticsGiven(ch) == true) {
			
			ArrayList<String> foundkeywords = findKeywords(ch);
			int count= foundkeywords.size();
			if(count == 0) {
				foundResults = noKeyword(ch);
				System.out.println("0 keywords"+foundkeywords);				
				//Procedure for 0 found keywords
				
			}else if (count == 1) {
				foundResults = oneKeyword(foundkeywords.get(0), ch);
				System.out.println("1 keyword"+foundkeywords);
				//Procedure for 1 found keyword
			}else {
				// TODO: Mehr als 1 Keywort erkannt funktioniert noch nicht
				System.out.println(">1 keywords"+foundkeywords);								
			}
		}
	}
	
	//Check if Chunker contains any semantic information
	static boolean CheckIfSemanticsGiven(Chunker ch) {
		
		boolean semanticgiven = false;
		
		ArrayList<String> ChunkArray = ch.readChunks();
		
		for(int i=0;i < ChunkArray.size(); i++) {
			
			if(ch.hasSemInfo(ChunkArray.get(i))) {
				semanticgiven = true;
			}
		}
		return semanticgiven;
	}
	
	//Search through Chunker to find spoken Keywords
	/**
	 * Looks in the chunks for a specific keyword. Can detect singular and plural of Keywords
	 * @param ch chunker Object which contains the Chunks including the Sem infou found by the DR Group
	 * @return an ArrayList<String> with the KEywords that were found
	 */
	public static ArrayList<String> findKeywords(Chunker ch) {
		
		
		ArrayList<String> foundkeywords = new ArrayList<>();
		
		for(int i=0; i<ch.size(); i++) {

			if(ch.getChunkContentAt(i).contains("document")){
				
				System.out.println("document detected");
				foundkeywords.add("document");
				
			}else if (ch.getChunkContentAt(i).contains("project")) {
				
				System.out.println("project detected");
				foundkeywords.add("project");
				
			}else if (ch.getChunkContentAt(i).contains("person")) {
				
				System.out.println("person detected");
				foundkeywords.add("person");
			}else {
				System.out.println("KeywordSerch.findKeywords: Keywort nicht erkannt");
			}
		}
		
		return foundkeywords;
	}
	// Search through Chunker to find references to Google Application
	// Muss bei Bene in den Code
	private static void FindApplications(Chunker ch) {
		
		for(int i = 0; i < ch.size(); i++) {
			
			if(ch.getChunkContentAt(i).contains("google drive")) {
				//Application Agent mit Parameter "Drive" rufen.
				
			}else if (ch.getChunkContentAt(i).contains("mail")) {
				//Application Agent mit Parameter "Mail" rufen.
				
			}
			
			
		}
		
		
	}
	
	/**
	 * Searches a Chunker object for one keyword and returns a new Chunker object with the chunks whose sem info contains the keyword
	 * @param keyword which will be searched for in the Chunker
	 * @param chunks a Chunker wich will be searched
	 * @return a new Chunker Object containing the found sem infos in a arraylist Format: {chunk,{sem1,sem2}}
	 */
	public static Chunker oneKeyword(String keyword, Chunker chunks){
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
	
	
	
	
	
	/**
	 * Each chunk will be used to search in the other sem infos and a new Chunker object will be returned
	 * @param chunks a Chunker Object
	 * @return a new Chunker Object containing the found sem infos in a arraylist Format: {chunk,{sem1,sem2}}
	 */
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
	/**
	 * Reads the keywords that are given and searches for them in the semantic of each chunker Object. A new chunker object will be returned
	 * with all the semantic data and its chunks that were found by the keyword
	 * @param keywords which will be searched for in the sem information of the chunk
	 * @param chunks Chunker object which will be searched for every keyword
	 * @return a new Chunker Object which contains of the chunk including the semantic information where the keyword was found
	 */
	public static Chunker severalKeywords(ArrayList<String> keywords, Chunker chunks) {
		Chunker tempChunker = new Chunker();
		Chunker newChunker = new Chunker();
		for (int j = 0; j < chunks.size(); j++) {
			newChunker.addChunkContent(chunks.getChunkContentAt(j));
		}
		for (int i = 0; i < keywords.size(); i++) {
			tempChunker = oneKeyword(keywords.get(i), chunks);
			tempChunker.printList();
			for (int j = 0; j < tempChunker.size(); j++) {
				String currChunk =  tempChunker.getChunkContentAt(j);
				Object tempSemChunk = tempChunker.getSemanticAt(j);
				newChunker.printList();
				if(tempSemChunk instanceof ArrayList<?> && !((ArrayList<String>) tempSemChunk).isEmpty()) {
					newChunker.addSemanticToChunk(currChunk, (ArrayList<String>)tempSemChunk);	
				}else {
					System.out.println("KeywordSearch.severalKeywords(): semantic empty or not a ArrayList");
				}
			}
		}
		return newChunker;
	}
	
	
}