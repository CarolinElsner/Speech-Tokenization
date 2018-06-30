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
		/*
		ArrayList<Object> sem = new ArrayList<>();
		ch.addChunkContent("documents");
		ch.addChunkContent("blalala");
		ch.addChunkContent("project");
		ch.addChunkContent("person");
		ch.addChunkContent("01.04.1992");
		ch.addSemanticToChunk("documents", sem);
		*/
		
		boolean semanticsgiven = CheckIfSemanticsGiven(ch);
		
		if(semanticsgiven == true) {
			
			ArrayList<String> foundkeywords = FindKeywords(ch);
			
			if(keywordcount == 0) {
				System.out.println("0 keywords"+foundkeywords);				
				//Procedure for 0 found keywords
				
			}else if (keywordcount == 1) {
				Interpretation.oneKeyword(foundkeywords.get(0), ch);
				//Test
				System.out.println("1 keyword"+foundkeywords);
				
				//Procedure for 1 found keyword
			}else {
				for (int i = 0; i < foundkeywords.size(); i++) {
					Interpretation.oneKeyword(foundkeywords.get(i), ch);
				}
				System.out.println(">1 keywords"+foundkeywords);								
			}
		}		
	}
	
	//Check if Chunker contains any semantic information
	public static boolean CheckIfSemanticsGiven(Chunker ch) {
		
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
	
	
}