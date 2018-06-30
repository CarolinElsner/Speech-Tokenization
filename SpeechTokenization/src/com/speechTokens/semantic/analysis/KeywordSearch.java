package com.speechTokens.semantic.analysis;

import java.util.ArrayList;

import com.speechTokens.semantic.analysis.Interpretation;
import com.speechTokens.tokenizer.Chunker;

public class KeywordSearch {
	
	public static int keywordcount = 0;

	/**
	 * Looks for Keywords in the Chunker, and scanns for singular and plural of those by just looking into "contains"
	 * The found Keyword and its semantic will be deleted from the Chunker Object
	 * @param ch chunker ArrayList, which contains the chunks with the sem Info
	 */
	public static void keywordCheck(Chunker ch) {

		Chunker foundResults = new Chunker();
		boolean semanticsgiven = CheckIfSemanticsGiven(ch);
		
		if(semanticsgiven == true) {
			
			ArrayList<String> foundkeywords = FindKeywords(ch);
			
			if(keywordcount == 0) {
				foundResults = noKeyword(ch);
				System.out.println("0 keywords"+foundkeywords);				
				//Procedure for 0 found keywords
				
			}else if (keywordcount == 1) {
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
	private static boolean CheckIfSemanticsGiven(Chunker ch) {
		
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
	
	private static ArrayList<String> FindKeywords(Chunker ch) {
		
		keywordcount = 0;
		
		ArrayList<String> foundkeywords = new ArrayList<>();
		
		for(int i=0; i<ch.size(); i++) {

			if(ch.getChunkContentAt(i).contains("document")){
				
				System.out.println("document detected");
				foundkeywords.add("document");
				ch.removeChunkAndSem(ch.getChunkContentAt(i));
				i--;
				keywordcount++;
				
			}else if (ch.getChunkContentAt(i).contains("project")) {
				
				System.out.println("project detected");
				foundkeywords.add("project");
				ch.removeChunkAndSem(ch.getChunkContentAt(i));
				i--;
				keywordcount++;
				
			}else if (ch.getChunkContentAt(i).contains("person")) {
				
				System.out.println("person detected");
				foundkeywords.add("person");
				ch.removeChunkAndSem(ch.getChunkContentAt(i));
				i--;
				keywordcount++;
				
			}else if (ch.getChunkContentAt(i).contains("activit")) {
				
				System.out.println("activity detected");
				foundkeywords.add("activity");
				ch.removeChunkAndSem(ch.getChunkContentAt(i));
				i--;
				keywordcount++;
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
	
	
}