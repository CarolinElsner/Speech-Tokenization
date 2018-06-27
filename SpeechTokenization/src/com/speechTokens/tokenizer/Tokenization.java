package com.speechTokens.tokenizer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import com.speechTokens.XML.MatchChunkSemantics;
import com.speechTokens.XML.XML_Token;
import com.sun.xml.internal.fastinfoset.util.StringArray;

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
	
	// with Tomcat you have to use absolute paths or relative paths starting at the Desktop
	// to make sure you can find the directory with this code: System.out.println(System.getProperty("user.dir").toString());
	private static String NLPmodulePath = "resources/"; // path where the opennlp/lib jars are stored
	private static String XMLoutputPath = "Neuer Ordner/"; // path where the tokens should be stored as XML
	//update
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

		
		// Console outputs of the tokenized sentence
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
			if (chunkResult[i].charAt(0) == 'B' && posTags[i].contains("NN")) {
				chunkList.add(tokens[i]);
				// add the token to the last chunk since it is not B
			} else if (chunkResult[i].charAt(0) != 'B' && posTags[i].contains("NN")) {
				int lastElement = chunkList.size() - 1; // last element in ArrayList
				if (lastElement >= 0) {
					String cacheElement = chunkList.get(lastElement); // cache last Element of ArrayList to avoid
																		// overrwriting it
					chunkList.set(lastElement, cacheElement + " " + tokens[i]); // concat the cached element with the
																				// new token
				} else { // if the last element is < 0 it means the whole sentence consists of one element
							// wich does not start with a B chunk
					chunkList.add(tokens[i]);
				}
			}
		}

		// execute the createToken function in the com.speechTokens.XML Package

		//List for elements which should be delete
				List<String> ListForDelete = new ArrayList<String>();
				// counter for getting the right posTags
				int counter = 0;
				// zusatz is necessary to match the right posTags if the splittArray is > 1
				int zusatz = 0;
				for (int j = 0; j < chunkList.size(); j++) {
					boolean substantiv = false;
					String variable;
					String[] splittArray = chunkList.get(j).split(" "); 
						for(int k = 0; k < splittArray.length ;k++) {
						// zur Überpürfung
							System.out.println("-----------------------------");
							System.out.println("Gesamte Länge von k ist gleich " +splittArray.length);
							System.out.println(splittArray[k] + " k ist gleich " +  k );
							System.out.println(chunkList.get(j));
							counter = k+j+zusatz;
							variable= posTags[counter];
							System.out.println(splittArray[k] + " ----> "+ variable);
							if(variable.contains("NN")) {
							//	System.out.println(variable);
								substantiv = true;
							}
						}
						if(substantiv == false) {
							ListForDelete.add(chunkList.get(j));
						}
						if(splittArray.length > 1) {
							zusatz = zusatz +1;
						}
				}
				System.out.println(ListForDelete);
				//delete chunks which are or have no substantive
				for(int o = 0; o < ListForDelete.size(); o++ ) {
					for (int u = 0 ; u < chunkList.size(); u++) {
						if(ListForDelete.get(o) == chunkList.get(u)) {
							System.out.println(ListForDelete.get(o) + "--->" + chunkList.get(u) );
							chunkList.remove(u);
						}
					}
				}
		// now the sentence was chunked and stored in an ArrayList. It is now sent to create a XML Token
		Document docToken = XML_Token.createToken(chunkList);
		
		// The tokenized sentence is sent to the addSemantics() function to insert semantics that mach the chunks
		Document modifiedDoc = MatchChunkSemantics.addSemantics(docToken);
		
		// creates an XML file with the new token which has the semantics
		// the sentenceCounter is added to create with every token an new file
		XML_Token.createXML(modifiedDoc, XMLoutputPath+"sentence"+XML_Token.sentenceCounter+".xml");


/*		 //to show all the chunks in the List
		 for (int j = 0; j < chunkList.size(); j++) {
		 System.out.println(chunkList.get(j)); }
		 */
		return chunkList;
	}


	public static String[] Sentence(String sentence) throws IOException {

		// Sentence Detection Modell laden
		InputStream modelInSent = new FileInputStream(NLPmodulePath + "en-sent.bin");
		SentenceModel modelSent = new SentenceModel(modelInSent);

		// Konstruktor Sentence Detector
		SentenceDetectorME sentDetector = new SentenceDetectorME(modelSent);

		// Sentence Detection OpenNLP
		String[] splitString = sentDetector.sentDetect(sentence);

		return splitString;

	}
	
	/**
	 * @param the sentence that was recorded
	 * @return {@link StringArray} of each word, number or sign (token)
	 * */
	public static String[] tokenize(String sentence) throws IOException {

		// Tokenizer Modell laden
		InputStream modelIn = new FileInputStream(NLPmodulePath + "en-token.bin");
		TokenizerModel model = new TokenizerModel(modelIn);

		// Konstruktor Tokenizer
		TokenizerME tokenizer = new TokenizerME(model);

		// Tokenisieren
		String tokens[] = tokenizer.tokenize(sentence);

		// Ermittlung Tokenizer Wahrscheinlichkeiten
		// double tokenProbs[] = tokenizer.getTokenProbabilities();

		return tokens;
	}

	/**
	 * @param the sentence that was recorded
	 * @return {@link StringArray} grammatic information about each token
	 * */
	public static String[] posTagging(String[] tokens) throws IOException {

		// Part-of-Speech Tagger Modell laden
		InputStream modelInPOS = new FileInputStream(NLPmodulePath + "en-pos-maxent.bin");
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

	/**
	 * @param {@link StringArray} tokens of the recourded sentence (ideally the output of the tokenize() function)
	 * @param {@link StringArray} gramatic iformation about each token of the recourded sentence (ideally the output of the posTagging() function)
	 * @return {@link StringArray} chunks, grouped tokens (tokens which belong together)
	 * */
	public static String[] chunk(String[] tokens, String[] posTags) throws IOException {

		// Chunk Modell
		InputStream inputStream = new FileInputStream(NLPmodulePath + "en-chunker.bin");
		ChunkerModel modelChunk = new ChunkerModel(inputStream);
		ChunkerME chunker = new ChunkerME(modelChunk);

		// Generieren von Chunks
		String chunkResult[] = chunker.chunk(tokens, posTags);
		return chunkResult;
	}

	public static Parse[] parse(String sentence) throws IOException {

		// Parser Modell laden
		InputStream modelInPars = new FileInputStream(NLPmodulePath + "en-parser-chunking.bin");
		ParserModel modelPars = new ParserModel(modelInPars);

		// Konstruktor Parser
		Parser parser = ParserFactory.create(modelPars);

		// Parsing
		Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);

		return topParses;
	}
}
