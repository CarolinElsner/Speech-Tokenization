package com.speechTokens.tokenizer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

	private static String path = "C:/Users/Menz/eclipse-workspace/resources/";

	public static void doTokenization(String sentence) throws IOException {
		// the actual spoken text devided by whitespaces
		String tokens[] = tokenize(sentence);
		// further grammatical information about that token
		String posTags[] = posTagging(tokens);
		// Creates chunks of tokens: B --> start new token; not B --> belongs to the token with a B before
		String chunkResult[] = chunk(tokens, posTags);

		Parse parseResult[] = parse(sentence);

		for (Parse p : parseResult)
			p.show();

		for (int i = 0; i < chunkResult.length; i++) {
			System.out.println(tokens[i] + " " + posTags[i] + " " + chunkResult[i]);
		}

		// has to be initialised empty with every Tokenization
		List<String> chunkList = new ArrayList<String>();
		
		// iterates through every token
		for (int i = 0; i < chunkResult.length; i++) {
			// add a new token, since B indicates it is a new token
			if (chunkResult[i].charAt(0) == 'B') {
				chunkList.add(tokens[i]);
			// add the token to the last chunk since it is not B	
			} else if (chunkResult[i].charAt(0) != 'B') {
				int lastElement = chunkList.size() - 1; // last element in ArrayList
				String cacheElement = chunkList.get(lastElement); // cache last Element of ArrayList to avoid overrwriting it
				chunkList.set(lastElement, cacheElement + " " + tokens[i]); // concat the cached element with the new token
			}
		}
		
		// execute the createToken function in the com.speechTokens.XML Package
		XML_Token.createToken(chunkList);
		/*
		 * for (int j = 0; j < chunkList.size(); j++) {
		 * System.out.println(chunkList.get(j)); }
		 */
	}

	public static String[] Sentence(String sentence) throws IOException {

		// Sentence Detection Modell laden
		InputStream modelInSent = new FileInputStream(path + "en-sent.bin");
		SentenceModel modelSent = new SentenceModel(modelInSent);

		// Konstruktor Sentence Detector
		SentenceDetectorME sentDetector = new SentenceDetectorME(modelSent);

		// Sentence Detection OpenNLP
		String[] splitString = sentDetector.sentDetect(sentence);

		return splitString;

	}

	public static String[] tokenize(String sentence) throws IOException {

		// Tokenizer Modell laden
		InputStream modelIn = new FileInputStream(path + "en-token.bin");
		TokenizerModel model = new TokenizerModel(modelIn);

		// Konstruktor Tokenizer
		TokenizerME tokenizer = new TokenizerME(model);

		// Tokenisieren
		String tokens[] = tokenizer.tokenize(sentence);

		// Ermittlung Tokenizer Wahrscheinlichkeiten
		// double tokenProbs[] = tokenizer.getTokenProbabilities();

		return tokens;

	}

	public static String[] posTagging(String[] tokens) throws IOException {

		// Part-of-Speech Tagger Modell laden
		InputStream modelInPOS = new FileInputStream(path + "en-pos-maxent.bin");
		POSModel modelPOS = new POSModel(modelInPOS);
		POSTaggerME tagger = new POSTaggerME(modelPOS);

		// POS Tagging
		String posTags[] = tagger.tag(tokens);

		/*
		 * //Ausgabe POS for (int i = 0; i < posTags.length; i++) {
		 * System.out.println(tokens[i] + " " + posTags[i]); }
		 */
		return posTags;

	}

	public static String[] chunk(String[] tokens, String[] posTags) throws IOException {

		// Chunk Modell
		InputStream inputStream = new FileInputStream(path + "en-chunker.bin");
		ChunkerModel modelChunk = new ChunkerModel(inputStream);
		ChunkerME chunker = new ChunkerME(modelChunk);

		// Generieren von Chunks
		String chunkResult[] = chunker.chunk(tokens, posTags);

		return chunkResult;

	}

	public static Parse[] parse(String sentence) throws IOException {

		// Parser Modell laden
		InputStream modelInPars = new FileInputStream(path + "en-parser-chunking.bin");
		ParserModel modelPars = new ParserModel(modelInPars);

		// Konstruktor Parser
		Parser parser = ParserFactory.create(modelPars);

		// Parsing
		Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);

		return topParses;

	}

}
