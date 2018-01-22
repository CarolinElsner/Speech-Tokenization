package com.speechTokens.tokenizer;

import java.io.InputStream;

import com.speechTokens.XML.XML_Token;

import java.io.IOException;
import java.io.FileInputStream;
import opennlp.tools.postag.*;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.*;
import opennlp.tools.cmdline.parser.ParserTool; 
import opennlp.tools.parser.Parse; 
import opennlp.tools.parser.Parser; 
import opennlp.tools.parser.ParserFactory; 
import opennlp.tools.parser.ParserModel;

import opennlp.tools.chunker.*;

public class Tokenization {
	/*
	public static void main(String[] args) throws IOException {
		tokenization("Das ist ein Behfa");
	}*/

	public static void doTokenization(String sentence) throws IOException {
			
					
		String tokens[] = tokenize(sentence);
		
		String posTags[] = posTagging(tokens);
		
		String chunkResult[] = chunk(tokens, posTags);
		
		Parse parseResult[] = parse(sentence);
		
		XML_Token.createToken(tokens, chunkResult);
		
		for (Parse p: parseResult)
			p.show();
		
		
		for(int i = 0; i < chunkResult.length; i++) {		
			System.out.println(tokens[i] + " " + posTags[i] + " " + chunkResult[i]);
		}
		
		String chunkPhrase = "";
		
		for(int i = 0; i < chunkResult.length; i++){
				
				
				if(chunkResult[i].charAt(0) != 'B') {
					chunkPhrase = chunkPhrase + " " + tokens[i];
					
				} else if(chunkResult[i].charAt(0) == 'B') {
					System.out.println(chunkPhrase);
					chunkPhrase = "";
					chunkPhrase = chunkPhrase + " " + tokens[i];
				}
									
						
		}
		System.out.println(chunkPhrase);
	}
	
	public static String[] Sentence(String sentence) throws IOException {
		
		//Sentence Detection Modell laden
		InputStream modelInSent = new FileInputStream("C:/Users/Menz/eclipse-workspace/resources/en-sent.bin");
		SentenceModel modelSent = new SentenceModel(modelInSent);
		
		//Konstruktor Sentence Detector
		SentenceDetectorME sentDetector = new SentenceDetectorME(modelSent);
		
		// Sentence Detection OpenNLP		
		String[] splitString = sentDetector.sentDetect(sentence);
		
		return splitString;		
		
	}
	
	public static String[] tokenize(String sentence) throws IOException {
		
		//Tokenizer Modell laden
		InputStream modelIn = new FileInputStream("C:/Users/Menz/eclipse-workspace/resources/en-token.bin");
		TokenizerModel model = new TokenizerModel(modelIn);
		
		//Konstruktor Tokenizer
		TokenizerME tokenizer = new TokenizerME(model);
		
		//Tokenisieren
		String tokens[] = tokenizer.tokenize(sentence);
		
		
		//Ermittlung Tokenizer Wahrscheinlichkeiten
		//double tokenProbs[] = tokenizer.getTokenProbabilities();
		
		return tokens;
    	
    }
	
	public static String[] posTagging(String[] tokens) throws IOException {
		
		//Part-of-Speech Tagger Modell laden
		InputStream modelInPOS = new FileInputStream("C:/Users/Menz/eclipse-workspace/resources/en-pos-maxent.bin");
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
		InputStream inputStream = new FileInputStream("C:/Users/Menz/eclipse-workspace/resources/en-chunker.bin"); 
		ChunkerModel modelChunk = new ChunkerModel(inputStream); 
		ChunkerME chunker = new ChunkerME(modelChunk); 
		
	    //Generieren von Chunks	    
	    String chunkResult[] = chunker.chunk(tokens, posTags);
	    		
	    return chunkResult;
	    		
	}
	
	public static Parse[] parse(String sentence) throws IOException {
		
		//Parser Modell laden
		InputStream modelInPars = new FileInputStream("C:/Users/Menz/eclipse-workspace/resources/en-parser-chunking.bin");
		ParserModel modelPars = new ParserModel(modelInPars);
		
		//Konstruktor Parser
		Parser parser = ParserFactory.create(modelPars);
		
	    //Parsing
	    Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
	    
	    return topParses;
		
	}
	
}
