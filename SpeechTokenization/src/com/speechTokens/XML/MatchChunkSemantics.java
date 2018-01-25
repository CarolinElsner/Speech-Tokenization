package com.speechTokens.XML;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.speechTokens.semanticSimulation.SemanticData;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;

public class MatchChunkSemantics {
	
	//private static final String semanticFolderPath = "C:\\Users\\Menz\\Desktop\\CLOUD DATA"; // absolute Path
	private static final String semanticFolderPath = "resources"; // relative path (to find yours, type: System.out.println(System.getProperty("user.dir").toString());)
	/**
	 * Possible input parameters are the source of the xml file or the java Document
	 * object deprecated
	 * @deprecated
	 */
	public static void readXML(Object obj) {

		try {
			Document doc = null;
			if (obj instanceof String) {
				// XML File einlesen
				File fXmlFile = new File(obj.toString());
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(fXmlFile);
			} else if (obj instanceof Document) {
				doc = (Document) obj;
			}

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName(XML_Token.secondDOM); // --> chunk

			System.out.println("----------------------------");

			// Über Chunks iterieren
			for (int temp = 0; temp < nList.getLength(); temp++) {
				String thirdDOMone = null;
				String thirdDOMtwo = null;
				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element: " + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					// Ausgabe der einzelnen Chunkattribute
					thirdDOMone = eElement.getElementsByTagName(XML_Token.thirdDOMone).item(0).getTextContent();
					thirdDOMtwo = eElement.getElementsByTagName(XML_Token.thirdDOMtwo).item(0).getTextContent();
					System.out.println("chunk id: " + eElement.getAttribute("id"));
					System.out.println(XML_Token.thirdDOMone + ": " + thirdDOMone); // --> text
					System.out.println(XML_Token.thirdDOMtwo + ": " + thirdDOMtwo); // --> semantic
				}
			}
			/*
			 * // Test um einzelnes Attribut von Chunk zu bekommen Node node2 =
			 * nList.item(4); Element element = (Element) node2;
			 * System.out.println(element.getElementsByTagName("text").item(0).
			 * getTextContent());
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param obj Can either be the Path of an XML file as a {@link String} or a Java {@link Document} object containing the xml File
	 * @return {@link Document} object with the newly added semantics to the respective token
	 */
	public static Document addSemantics(Object obj) {
		// TODO: Hier und oben noch NullPointerException abfangen, falls der XML Link
		// falsch ist (oder FileNotFoundExction)
		Document doc = null;
		if (obj instanceof String) {
			// XML File einlesen
			File fXmlFile = new File(obj.toString());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = null;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			try {
				doc = dBuilder.parse(fXmlFile);
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (obj instanceof Document) {
			doc = (Document) obj;
		}
		doc.getDocumentElement().normalize();
		
		if (doc.hasChildNodes()) {
			for (int i = 0; i < doc.getChildNodes().getLength(); i++) {
				Node node = doc.getChildNodes().item(i);
				// sends the current node for further processing
				utilReadAnyXML(node);
			}
		}
		return doc;
	}
	
	/**
	 * @Description
	 * Is the recursive part to read any XML file, no matter how many hierarchies
	 * Here does the magic happens!
	 * the spoken text bits in the nodes are sent to the semanticLookup function including the semanticFolderPath
	 * this returns the found semantics for the respective text bit
	 * this can also be used to read any XML file
	 * @param node is the current node which may contain children
	 */
	public static void utilReadAnyXML(Node node) {
		String[] foundSemantics= null;		
		if (node.hasChildNodes()) {
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				Node currNode = node.getChildNodes().item(i);
				// at the lowest level the "node" is just the text string without the catual node, it is therefore represented as "#text" in java
				if (currNode.getNodeName().equals("#text")) { // because at some point the node is just the text content
					// just the Text Content is here (they have no child nodes)
					// compares the text content with the semantic data and stores the found elements in "foundSemantics"
					foundSemantics = SemanticData.semanticLookUp(currNode.getTextContent(), semanticFolderPath);
					if (!(foundSemantics == null)) { // checks whether something was found
						for (int j = 0; j < foundSemantics.length; j++) {
							// TODO: right now the semantics are just comma seperated without further information about the semantic type, here might be a new Node for each semantic input helpful
							// the text bit is always in the <text> node and we want to push the found semantics in its sibling node, the <semantics> node
							currNode.getParentNode().getNextSibling().setTextContent(String.join(",", foundSemantics)); 
							// joins the elements in the foundSemantics Array into the node with a comma as seperator
						}
					}					
				} else {
					// They have child nodes or at least some text content
					// Recursion because of the depth of the xml
					utilReadAnyXML(currNode);
				}
			}
		}
	}
}
