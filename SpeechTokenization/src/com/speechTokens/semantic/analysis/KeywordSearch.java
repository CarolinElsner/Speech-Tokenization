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
				foundResults = severalKeywords(foundkeywords, ch);
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
	 * Uses "contains" --> chunks like "Project Document" will be one keyword or "Highnet Document" of type Document
	 * Document will be added in the foundKeyword ArrayList, that we can search for it in the next step in the sem Infos
	 * @param ch chunker Object which contains the Chunks including the Sem infou found by the DR Group
	 * @return an ArrayList<String> with the keyword that can be person document or project
	 */
	
	public static ArrayList<String> findKeywords(Chunker ch) {
		
		
		String[][] keywords = {
				{"project","project"},
				{"document", "document"},
				{"person", "person"},
				{"contact", "person"},
				};
		
		ArrayList<String> foundkeywords = new ArrayList<>();
		
		for(int i=0; i<ch.size(); i++) {
			for(int j=0; j<keywords.length;j++) {
				
				if(ch.getChunkContentAt(i).contains(keywords[j][0])) {
					foundkeywords.add(keywords[j][1]);
					System.out.println("Keyword detected: " + keywords[j][1]);
				}
				
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
		ArrayList<String> noProjectFound = new ArrayList<String>();
		ArrayList<String> projectFound = new ArrayList<String>();
		
		for (int j = 0; j < chunks.size(); j++) {
			ArrayList<String> foundSemOfChunk = new ArrayList<String>(); // the semantics that were found by the keyword and belong to ONE chunk
			Boolean semanticExistance = chunks.getSemanticAt(j) != null; // there is semantic of the respective chunk
			Boolean isSemanticString = (chunks.getSemanticAt(j) instanceof String); // the semantic is a string
			Boolean keywordIsChunk = chunks.getChunkContentAt(j).equals(keyword); // check whether the keyword equals the current chunk
			if(semanticExistance && isSemanticString && !keywordIsChunk){
				JsonHandler js = new JsonHandler();
				// looks in the sem info of each chunk and searches in it for the keyword
				foundSemOfChunk = js.semanticLookUp((String) chunks.readSemanticOfChunk(chunks.getChunkContentAt(j)), keyword);
				// wird das keywort projekt in dem chunk erkannt soll untersucht werden ob nicht evtl noch ein Dokument der anderen Chunks relevant ist
				if(keyword.equalsIgnoreCase("project")) {
					if(foundSemOfChunk.isEmpty()) { // if no semantic regarding the keyword for the chunk was found:
						noProjectFound.add(chunks.getChunkContentAt(j)); // Chunk worüber wir evtl dokumente finden wollen, da wir keine projektinformationen darüber gefunden haben
					}else {
						projectFound.add(chunks.getChunkContentAt(j));
					}
					
				}
			}else if(!semanticExistance){
				System.out.println("KeywordSearch.oneKeyword: No semantic of chunk found");				
			}else if(!isSemanticString) {
				System.out.println("KeywordSearch.oneKeyword: Chunk is not a String");				
			}else if(keywordIsChunk) {
				System.out.println("KeywordSearch.oneKeyword: Keyword is a chunk");				
			}else {
				System.out.println("KeywordSearch.oneKeyword: not predictable error");
			}
			if(!foundSemOfChunk.isEmpty()) { // create new chunker were the found semantic and the chunk will be added
				results.addChunkContent(chunks.getChunkContentAt(j));
				results.addSemanticToChunk(chunks.getChunkContentAt(j), foundSemOfChunk);
			}else {
				System.out.println("KeywordSearch.oneKeyword: No semantic found at all in the respective chunk");
			}
		}
		// ausführen sollte ein Chunk gefunden werden der keine sem treffer hat und ein chunk der schon welche hat mit dem Keywort "Project"
		if(!noProjectFound.isEmpty() && !projectFound.isEmpty()) {
			// in den semantischen informationen von noProjectFound nach dem auftreten des chunks projectFound suchen
			
			 // iterieren durch die anzahl der chunks wozu keine projektinformationen gefunden wurden
			for (int i = 0; i < noProjectFound.size(); i++) {
				// iterieren durch die chunks wozu projektinformationen gefunden wurden
				for (int h = 0; h < projectFound.size(); h++) {
					JsonHandler js = new JsonHandler();
					// lese die semantischen informationen des "noProjectFound" Chunks aus, worin wir das auftreten des "projectFound" Chunks überprüfen
					ArrayList<String> semInfos = new ArrayList<>();
					semInfos = js.semanticLookUp((String) chunks.readSemanticOfChunk(noProjectFound.get(i)), projectFound.get(h));
					// wurden treffer gefunden wird weiter gemacht
					if(!semInfos.isEmpty()) {
						for (int k = 0; k < semInfos.size(); k++) {
							// in den treffern wird jetzt geprüft ob es sich bei den Semantischen Informationen um ein Dokument handelt, falls nicht wird es gelöscht
							if(!semInfos.get(k).contains("Document")) {
								semInfos.remove(k);
							}else {
								System.out.println("KeywordSearch.oneKeyword: Contains additional Document");
							}
						}
						// hier wird dann alles in das finale results chunker object zusammengeführt
						results.addChunkContent(noProjectFound.get(i));	
						results.addSemanticToChunk(noProjectFound.get(i), semInfos);
					}else {
						System.out.println("KeywordSearch.oneKeyword: no sem for this chunk");
					}
				}				
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
			for (int j = 0; j < tempChunker.size(); j++) {
				String currChunk =  tempChunker.getChunkContentAt(j);
				Object tempSemChunk = tempChunker.getSemanticAt(j);
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