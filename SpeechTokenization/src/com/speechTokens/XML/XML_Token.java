package com.speechTokens.XML;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XML_Token {

	private static String xmlPath = "C:\\Users\\Menz\\Desktop\\file.xml";
	private static String firstDOM = "sentence";
	private static String secondDOM = "chunk";
	private static String thirdDOMone = "text";
	private static String thirdDOMtwo = "semantic";
	private static String thirdDOMtwoAttr = ""; // Semantic attributes
	// TODO: Muss noch dynamisch sein sodass die Zahlen hochgezählt werden
	private static int sentenceCounter = 1;

	// public static void createToken(String tokens[], String chunkResult[]) throws
	// IOException {
	public static void createToken(List<String> chunkList) throws IOException {
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root element sentence
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(firstDOM);
			doc.appendChild(rootElement);

			// set attribute to sentence element
			Attr attr = doc.createAttribute("id");
			attr.setValue(String.valueOf(sentenceCounter));
			rootElement.setAttributeNode(attr);

			// increase the sentence ID (will start with 1 with every server runtime)
			sentenceCounter += 1;
			// These variables have to be initialised here because they have to be reset
			// with every tokenization
			int sizeChunk = 0;
			// iterates through the chunks in the sentence
			for (int i = 0; i < chunkList.size(); i++) {
				// chunk element 2. Hierarchie
				Element chunk = doc.createElement(secondDOM);
				rootElement.appendChild(chunk);

				// set attribute to chunk element
				Attr attr2 = doc.createAttribute("id");
				attr2.setValue(String.valueOf(sizeChunk));
				chunk.setAttributeNode(attr2);

				// chunk Element wird mit Chunk-Text befüllt 3. Hierarchie
				Element chunks = doc.createElement(thirdDOMone);
				chunks.appendChild(doc.createTextNode(chunkList.get(i)));
				chunk.appendChild(chunks);

				// chunk Element wird um semantik erweitert, wo später Text von Semeantik
				// reinkommt 3. Hierarchie
				Element responseSemantik = doc.createElement(thirdDOMtwo);
				responseSemantik.appendChild(doc.createTextNode(thirdDOMtwoAttr));
				chunk.appendChild(responseSemantik);

				// after one element on 2nd hierarchy is created, increase the id
				sizeChunk = sizeChunk + 1;
			}



			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(xmlPath));
			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

}