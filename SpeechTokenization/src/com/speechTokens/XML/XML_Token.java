package com.speechTokens.XML;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XML_Token {

	public static String firstDOM = "sentence";
	public static String secondDOM = "chunk";
	public static String thirdDOMone = "text";
	public static String thirdDOMtwo = "semantic";
	private static String thirdDOMtwoAttr = ""; // Semantic attributes
	private static int sentenceCounter = 1;

	
	

	/** 
	 * @Description This function creates an specific hierarchy for the chunks
	 * @param chunkList has to be a list, with each entry being one chunk
	 * @return {@link Document} the xml representation of the chunks
	 * 
	 */
	public static Document createToken(List<String> chunkList) throws IOException {
		Document doc = null;
		try {
			// These variables have to be initialised here because they have to be reset
			// with every tokenization
			int sizeChunk = 0;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root element sentence
			doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(firstDOM);
			doc.appendChild(rootElement);

			// set attribute to sentence element
			Attr attr = doc.createAttribute("id");
			attr.setValue(String.valueOf(sentenceCounter));
			rootElement.setAttributeNode(attr);
			// increase the sentence ID (will start with 1 with every server runtime)
			sentenceCounter += 1;

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
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		return doc;
	}

	/**
	 * @Description creates a real XML File at the location that is stored in the variable xmlPath
	 * @param doc represents any XML Document Object
	 * @param xmlPath represents the absolute Path where the xml file should be stored
	 * */
	public static void createXML(Document doc, String xmlPath) {
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		}
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(xmlPath));
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		System.out.println("File saved as"+ xmlPath);
	}
}