package com.speechTokens.XML;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XML_Token {

	public static void main(String argv[]) throws IOException {
		

		int sentenceCounter = 1;

		  try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			// root element sentence
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("sentence");
			doc.appendChild(rootElement);
			
			// set attribute to sentence element
			Attr attr = doc.createAttribute("id");
			attr.setValue(String.valueOf(sentenceCounter));
			rootElement.setAttributeNode(attr);

		
		//String sentence = "During the French Revolution in the 1790s, \"The Law of the Maximum\" was imposed in an attempt to decrease inflation. It consisted of limits on wages and food prices. Many dissidents were executed for breaking this law. The law was repealed 14 months after its introduction.";
		String sentence = "This is Michael Jordan from the Daimler Project.";

				
		String tokens[] = tokenize(sentence);
		
		String posTags[] = posTagging(tokens);
		
		String chunkResult[] = chunk(tokens, posTags);
		
		Parse parseResult[] = parse(sentence);
		
		for (Parse p: parseResult)
			p.show();
		
		
		for(int i = 0; i < chunkResult.length; i++) {		
			System.out.println(tokens[i] + " " + posTags[i] + " " + chunkResult[i]);
		}

		String chunkPhrase = "";
		int sizeChunk = 0;
		
		for(int i = 0; i < chunkResult.length; i++){
				
				
				if(chunkResult[i].charAt(0) != 'B') {
					//sizeChunk = sizeChunk + 1;
					chunkPhrase = chunkPhrase + " " + tokens[i];
					
				} else if(chunkResult[i].charAt(0) == 'B') {
					System.out.println(chunkPhrase);

					
					// chunk element
					Element chunk = doc.createElement("Chunk");
					rootElement.appendChild(chunk);
					
				
					// set attribute to chunk element
					Attr attr2 = doc.createAttribute("id");
					attr2.setValue(String.valueOf(sizeChunk));
					chunk.setAttributeNode(attr2);
					
					// chunk Element wird mit Chunk-Text befüllt
					Element chunks = doc.createElement("text");
					chunks.appendChild(doc.createTextNode(chunkPhrase));
					chunk.appendChild(chunks);

					// chunk Element wird um semantik erweitert, wo später Text von Semeantik reinkommt
					Element responseSemantik = doc.createElement("semantik");
					responseSemantik.appendChild(doc.createTextNode("in euer Gesicht"));
					chunk.appendChild(responseSemantik);
					
					chunkPhrase = "";
					chunkPhrase = chunkPhrase + " " + tokens[i];
					sizeChunk = sizeChunk + 1;
					
				}
									
						
		}
		// chunk element für letzten Chunk
		Element chunk = doc.createElement("Chunk");
		rootElement.appendChild(chunk);


		// set attribute to chunk element
		Attr attr2 = doc.createAttribute("id");
		attr2.setValue(String.valueOf(sizeChunk));
		chunk.setAttributeNode(attr2);
		
		// chunk Element wird mit Chunk-Text befüllt
		Element chunks = doc.createElement("text");
		chunks.appendChild(doc.createTextNode(chunkPhrase));
		chunk.appendChild(chunks);

		// chunk Element wird um semantik erweitert, wo später Text von Semeantik reinkommt
		Element responseSemantik = doc.createElement("semantik");
		responseSemantik.appendChild(doc.createTextNode("in euer Gesicht"));
		chunk.appendChild(responseSemantik);
		
		System.out.println(chunkPhrase);
		System.out.println(sizeChunk);	
	

		// shorten way
		// staff.setAttribute("id", "1");

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("C:\\Users\\Menz\\Desktop\\file.xml"));

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);

		System.out.println("File saved!");

	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
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
		double tokenProbs[] = tokenizer.getTokenProbabilities();
		
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


