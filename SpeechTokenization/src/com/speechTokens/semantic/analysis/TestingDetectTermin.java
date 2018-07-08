package com.speechTokens.semantic.analysis;

import java.io.IOException;
import java.util.ArrayList;

import com.speechTokens.tokenizer.DetectApplication;
import com.speechTokens.tokenizer.DetectTermin;
import com.speechTokens.tokenizer.Tokenization;

public class TestingDetectTermin {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DetectTermin termin = new DetectTermin();
		Tokenization chunks = new Tokenization(); 
		ArrayList <String> testChunks = new ArrayList<String>();
		
		String sentence = "Do you have time for a meeting next Friday ?";
		
		//Test ob exaktes Datum gefunden wird
		termin.validate(sentence);
		System.out.println(termin.getfoundDate());
		
		//Test
		try {
			testChunks = (ArrayList<String>) chunks.doTokenization(sentence);
			System.out.println(testChunks.size());
			System.out.println(testChunks);
			System.out.println("-------Check----------------");
			System.out.println(termin.searchDate(testChunks, sentence));
			termin.printList();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
