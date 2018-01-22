package com.speechTokens.XML;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class readXMLResponse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			//XML File einlesen
			File fXmlFile = new File("C:\\Users\\Benedikt\\Desktop\\Studium\\Master\\1.Semester\\Cloud Computing Technology\\file.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("Chunk");

			System.out.println("----------------------------");
			
			//Über Chunks iterieren
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					//Ausgabe der einzelnen Chunkattribute
					System.out.println("chunk id : " + eElement.getAttribute("id"));
					System.out.println("First Name : " + eElement.getElementsByTagName("text").item(0).getTextContent());
					System.out.println("Last Name : " + eElement.getElementsByTagName("semantik").item(0).getTextContent());
					
				}
			}
			//Test um einzelnes Attribut von Chunk zu bekommen
			Node node2 = nList.item(4);
			Element element = (Element) node2;
			System.out.println(element.getElementsByTagName("text").item(0).getTextContent());
			System.out.println();
		    } catch (Exception e) {
			e.printStackTrace();
		    }

	}

}
