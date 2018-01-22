package com.speechTokens.XML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class createToken {
public static void main(String[] args) throws ParserConfigurationException {
	
	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	
	// root element sentence
	Document doc = docBuilder.newDocument();
	Element rootElement = doc.createElement("sentence");
	doc.appendChild(rootElement);
	
	// set attribute to sentence element
	Attr attr = doc.createAttribute("id");
	attr.setValue(String.valueOf(1));
	rootElement.setAttributeNode(attr);
	Element chunk = doc.createElement("Chunk");
	rootElement.appendChild(chunk);
	
	// set attribute to chunk element
	Attr attr2 = doc.createAttribute("id");
	attr2.setValue(String.valueOf("2"));


	// chunk Element wird mit Chunk-Text befüllt
	Element chunks = doc.createElement("text");
	chunks.appendChild(doc.createTextNode("test123"));
	chunk.appendChild(chunks);
	
	Element responseSemantik = doc.createElement("semantik");
	responseSemantik.appendChild(doc.createTextNode("in euer Gesicht"));
	chunk.appendChild(responseSemantik);
	
	System.out.println(doc.getXmlEncoding());
}
}
