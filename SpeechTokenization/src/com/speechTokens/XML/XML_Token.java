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

	
	// TODO: Weniger feste strings, diese alle in Variablen für diese klasse instanziieren
	// TODO: Variablen als PRIVATE INITIIEREN
	// TODO: Alle Variablen an den Anfang dieser Klasse der übersicht halber schreiben (nicht innerhalb der Methode initiieren)
	public static void createToken(String tokens[], String chunkResult[]) throws IOException {

	// TODO: Muss noch dynamisch sein sodass die Zahlen hochgezählt werden
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


	String chunkPhrase = "";
	int sizeChunk = 0;
	// iterates through the chunks in the sentence
	for(int i = 0; i < chunkResult.length; i++){
			// B indicates the start of a new chunk therefore, if it's not a B, it means the token belongs to the previous chunk
			if(chunkResult[i].charAt(0) != 'B') {
				//sizeChunk = sizeChunk + 1;
				chunkPhrase = chunkPhrase + " " + tokens[i];			
			// if chunkPhrase is a finished chunk
				// TODO: Prüfen dass Token nicht leer ist
			} else if(chunkResult[i].charAt(0) == 'B') {
				System.out.println(chunkPhrase+ "####"+ i);

				
				// chunk element 2. Hierarchie
				Element chunk = doc.createElement("chunk");
				rootElement.appendChild(chunk);
				
			
				// set attribute to chunk element
				Attr attr2 = doc.createAttribute("id");
				attr2.setValue(String.valueOf(sizeChunk));
				chunk.setAttributeNode(attr2);
				
				// chunk Element wird mit Chunk-Text befüllt 3. Hierarchie
				Element chunks = doc.createElement("text");
				chunks.appendChild(doc.createTextNode(chunkPhrase));
				chunk.appendChild(chunks);

				// chunk Element wird um semantik erweitert, wo später Text von Semeantik reinkommt 3. Hierarchie
				Element responseSemantik = doc.createElement("semantik");
				responseSemantik.appendChild(doc.createTextNode("in euer Gesicht"));
				chunk.appendChild(responseSemantik);
				
				chunkPhrase = "";
				chunkPhrase = chunkPhrase + " " + tokens[i];
				sizeChunk = sizeChunk + 1;
			}					
					
	}
	// chunk element für letzten Chunk
	Element chunk = doc.createElement("chunk");
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
	
	System.out.println(chunkPhrase +"chunkPhrase");
	System.out.println(sizeChunk +"sizeChunk");	


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
}