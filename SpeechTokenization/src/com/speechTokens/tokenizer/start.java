package com.speechTokens.tokenizer;
import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class start {


	public static void main(String[] args) throws IOException {
			
		//String sentence = "During the French Revolution in the 1790s, \"The Law of the Maximum\" was imposed in an attempt to decrease inflation. It consisted of limits on wages and food prices. Many dissidents were executed for breaking this law. The law was repealed 14 months after its introduction.";
		Tokenization_neu tester = new Tokenization_neu();
		ArrayList<String> chunks = new ArrayList<String>();
		String sentence = "Please show me the documents from the HighNet project and what we do on April  .";

		//Get DetectTermin Object for date detection in sentence
		DetectTermin detector = new DetectTermin();
		System.out.println(detector.validate(sentence));
		
		//Tokinization
		chunks = (ArrayList<String>) tester.doTokenization(sentence);
		//Delete chunk which contains date
		chunks = detector.serachDate(chunks);
		
		// get Date which was detected
		detector.printList();
		//add the chunks to the chuncker and parse all chunks in lower case
		Chunker chunk = new Chunker();
		for (int i=0; i<chunks.size();i++) {
			chunk.addChunkContent(chunks.get(i).toLowerCase());
		}
		chunk.printList();
		//Am Ende aus der Liste alle Elemente entfernen
		detector.deleteAllDates();
		detector.printList();
	}

}
