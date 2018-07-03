package com.speechTokens.tokenizer;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.sentdetect.*;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.*;
import opennlp.tools.tokenize.*;
import opennlp.tools.util.Span;
import opennlp.tools.cmdline.parser.ParserTool; 
import opennlp.tools.parser.Parse; 
import opennlp.tools.parser.Parser; 
import opennlp.tools.parser.ParserFactory; 
import opennlp.tools.parser.ParserModel;
import opennlp.tools.chunker.*;


public class Tokenization {
	
	// with Tomcat you have to use absolute paths or relative paths starting at the Desktop
	// to make sure you can find the directory with this code: 
	//System.out.println(System.getProperty("user.dir").toString());
	
	/**
	 * @param sentence that should be tokenized
	 * @return 
	 * @Description
	 * Main function which executes the XML_Token.createToken(), readXMLResponse.addSemantics() and XML_Token.createXML() functions.
	 * In this function the sentence gets tokenized into an ArrayList, then sent to the createToken() function to create an XML Token.
	 * The returned Object is a {@link Document} which contains the sentence as xml file and this is sent to the addSemantics() function.
	 * The then returned Object is a modified {@link Document} which contains the semantics for each chunk.
	 */
	public static List<String> doTokenization(String sentence) throws IOException {
		// the actual spoken text devided by whitespaces
		String tokens[] = tokenize(sentence);
		// further grammatical information about that token
		String posTags[] = posTagging(tokens);
		// Creates chunks of tokens: B --> start new token; not B --> belongs to the
		// token with a B before
		String chunkResult[] = chunk(tokens, posTags);

		/*
		 * Console outputs of the tokenized sentence
		Parse parseResult[] = parse(sentence);
		for (Parse p : parseResult)
			p.show();
		for (int i = 0; i < chunkResult.length; i++) {
			System.out.println(tokens[i] + " " + posTags[i] + " " + chunkResult[i]);
		}
		*/
		// has to be iniitialised empty with every Tokenization
		List<String> chunkList = new ArrayList<String>();
		
		//System.out.println(chunkResult);
		// iterates through every token
		for (int i = 0; i < chunkResult.length; i++) {
			//System.out.println(posTags[i] + " und " + chunkResult[i] + " und " + tokens[i]);
			// add a new token, since B indicates it is a new token
			System.out.println("------------------------");
			System.out.println(tokens[i]+"---->"+chunkResult[i].charAt(0) + "-->" + posTags[i]);
			if (chunkResult[i].charAt(0) == 'B' && posTags[i].contains("NNP") ) {
					chunkList.add(tokens[i]);
				// add the token to the last chunk since it is not B
			} 	
			else if(chunkResult[i].charAt(0) != 'B' && posTags[i].contains("NNP") || chunkResult[i].charAt(0) != 'B' && posTags[i].equals("CD")) {
				if(chunkResult[i-1].charAt(0) == 'B' && posTags[i-1].contains("NNP") || chunkResult[i-1].charAt(0) == 'I' && posTags[i-1].contains("NN")) {
				//if(counterSubstantiv)
				int lastElement = chunkList.size() - 1; // last element in ArrayList
					if (lastElement >= 0) {
						String cacheElement = chunkList.get(lastElement); // cache last Element of ArrayList to avoid
																		// overwriting it
						chunkList.set(lastElement, cacheElement + " " + tokens[i]); // concat the cached element with the
																				// new token
					} else { // if the last element is < 0 it means the whole sentence consists of one element
							// wich does not start with a B chunk
						chunkList.add(tokens[i]);
					}
				}
				else {
					chunkList.add(tokens[i]);
				}
			}
			else if (chunkResult[i].charAt(0) == 'B' && posTags[i].equals("NN") || chunkResult[i].charAt(0) == 'B' && posTags[i].equals("NNS")) {
					chunkList.add(tokens[i]);
				// add the token to the last chunk since it is not B
			} 	
			else if(chunkResult[i].charAt(0) != 'B' && posTags[i].equals("NN") || chunkResult[i].charAt(0) != 'B' && posTags[i].equals("NNS") ) {
				if(chunkResult[i-1].charAt(0) == 'B' && posTags[i-1].equals("NN") || chunkResult[i-1].charAt(0) == 'I' && posTags[i-1].equals("NN")  ) {
				//if(counterSubstantiv)
				int lastElement = chunkList.size() - 1; // last element in ArrayList
					if (lastElement >= 0) {
						String cacheElement = chunkList.get(lastElement); // cache last Element of ArrayList to avoid
																		// overwriting it
						chunkList.set(lastElement, cacheElement + " " + tokens[i]); // concat the cached element with the
																				// new token
					} else { // if the last element is < 0 it means the whole sentence consists of one element
							// wich does not start with a B chunk
						chunkList.add(tokens[i]);
					}
				}
				else {
					chunkList.add(tokens[i]);
				}
			}
		}	

//		//List for elements which should be delete
//		List<String> ListForDelete = new ArrayList<String>();
//		List<String> ListRelevant = new ArrayList<String>();
//		// counter for getting the right posTags
//		int counter = 0;
//		// zusatz is necessary to match the right posTags if the splittArray is > 1
//		int zusatz = 0;
//		for (int j = 0; j < chunkList.size(); j++) {
//			boolean substantiv = false;
//			String variable;
//			String[] splittArray = chunkList.get(j).split(" "); 
//				for(int k = 0; k < splittArray.length ;k++) {
//					System.out.println("-----------------------------");
//					System.out.println("Gesamte Länge von k ist gleich " +splittArray.length);
//					System.out.println(splittArray[k] + " k ist gleich " +  k );
//					System.out.println(chunkList.get(j));
//					counter = k+j+zusatz;
//					variable= posTags[counter];
//					System.out.println(splittArray[k] + " ----> "+ variable);
//					if(variable.contains("NN")) {
//					//	System.out.println(variable);
//						ListRelevant.add(splittArray[k]);
//						substantiv = true;
//					}
//					else{
//						ListForDelete.add(splittArray[k]);
//					}
//				}
//				//if(substantiv == false) {
//					//ListForDelete.add(chunkList.get(j));
//				//}
//				if(splittArray.length > 1) {
//					zusatz = zusatz +1;
//				}
//		}
//
//		System.out.println(ListForDelete);
//		//delete chunks which are or have no substantive
//		for(int o = 0; o < ListForDelete.size(); o++ ) {
//			for (int u = 0 ; u < chunkList.size(); u++) {
//				if(ListForDelete.get(o) == chunkList.get(u)) {
//					System.out.println(ListForDelete.get(o) + "--->" + chunkList.get(u) );
//					chunkList.remove(u);
//				}
//			}
//		}
		//chunkList.remove(j);
		// execute the createToken function in the com.speechTokens.XML Package

		/*
		 * to show all the chunks in the List
		 * for (int j = 0; j < chunkList.size(); j++) {
		 * System.out.println(chunkList.get(j)); }
		 */
		return chunkList;
	}
	
	public static String[] Sentence(String sentence) throws IOException {
		
		//Sentence Detection Modell laden
		InputStream modelInSent = new FileInputStream("ressources/en-sent.bin");
		SentenceModel modelSent = new SentenceModel(modelInSent);
		
		//Konstruktor Sentence Detector
		SentenceDetectorME sentDetector = new SentenceDetectorME(modelSent);
		
		// Sentence Detection OpenNLP		
		String[] splitString = sentDetector.sentDetect(sentence);
		
		return splitString;		
		
	}
	
	public static String[] tokenize(String sentence) throws IOException {
		
		//Tokenizer Modell laden
		InputStream modelIn = new FileInputStream("ressources/en-token.bin");
		TokenizerModel model = new TokenizerModel(modelIn);
		
		//Konstruktor Tokenizer
		TokenizerME tokenizer = new TokenizerME(model);
		
		//Tokenisieren
		String tokens[] = tokenizer.tokenize(sentence);
		
		
		//Ermittlung Tokenizer Wahrscheinlichkeiten
		double tokenProbs[] = tokenizer.getTokenProbabilities();
		
		return tokens;
    	
    }
	
	public static String[] posTagging(String[] tokens) throws IOException {
		
		//Part-of-Speech Tagger Modell laden
		InputStream modelInPOS = new FileInputStream("ressources/en-pos-maxent.bin");
		POSModel modelPOS = new POSModel(modelInPOS);
		POSTaggerME tagger = new POSTaggerME(modelPOS);
		
	    //POS Tagging
	    String posTags[] = tagger.tag(tokens);
	    
	    /*
	    //Ausgabe POS
	    for (int i = 0; i < posTags.length; i++) {
	    		System.out.println(tokens[i] + " " + posTags[i]);
	    }
	    */
	    return posTags;
		
	}
	
	public static String[] chunk(String[] tokens, String[] posTags) throws IOException {
		
		//Chunk Modell
		InputStream inputStream = new FileInputStream("ressources/en-chunker.bin"); 
		ChunkerModel modelChunk = new ChunkerModel(inputStream); 
		ChunkerME chunker = new ChunkerME(modelChunk); 
		
	    //Generieren von Chunks	    
	    String chunkResult[] = chunker.chunk(tokens, posTags);
	    		
	    return chunkResult;
	    		
	}
	
	public static Parse[] parse(String sentence) throws IOException {
		
		//Parser Modell laden
		InputStream modelInPars = new FileInputStream("ressources/en-parser-chunking.bin");
		ParserModel modelPars = new ParserModel(modelInPars);
		
		//Konstruktor Parser
		Parser parser = ParserFactory.create(modelPars);
		
	    //Parsing
	    Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
	    
	    return topParses;
		
	}
	
}
