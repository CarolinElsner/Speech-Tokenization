package com.speechTokens.XML;

import java.io.File;
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

public class XMLGeneriuerung {

	public static void main(String argv[]) {

	  try {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("sentence");
		doc.appendChild(rootElement);

		// staff elements
		Element staff = doc.createElement("Staff");
		rootElement.appendChild(staff);


		// set attribute to staff element
		Attr attr = doc.createAttribute("id");
		attr.setValue("1");
		staff.setAttributeNode(attr);
		


		// shorten way
		// staff.setAttribute("id", "1");

		// firstname elements
		Element firstname = doc.createElement("firstname");
		firstname.appendChild(doc.createTextNode("yong"));
		staff.appendChild(firstname);

		// lastname elements
		Element lastname = doc.createElement("lastname");
		lastname.appendChild(doc.createTextNode("mook kim"));
		staff.appendChild(lastname);

		// nickname elements
		Element nickname = doc.createElement("nickname");
		nickname.appendChild(doc.createTextNode("mkyong"));
		staff.appendChild(nickname);

		// salary elements
		Element salary = doc.createElement("salary");
		salary.appendChild(doc.createTextNode("100000"));
		staff.appendChild(salary);
		
		
		
		
		Element staff1 = doc.createElement("Staff");
		rootElement.appendChild(staff1);
		
		Attr attr2 = doc.createAttribute("id");
		attr2.setValue("2");
		staff1.setAttributeNode(attr2);
		
		// firstname elements
		Element firstname2 = doc.createElement("firstname");
		firstname2.appendChild(doc.createTextNode("yong"));
		staff1.appendChild(firstname2);

		// lastname elements
		Element lastname2 = doc.createElement("lastname");
		lastname2.appendChild(doc.createTextNode("mook kim"));
		staff1.appendChild(lastname2);

		// nickname elements
		Element nickname2 = doc.createElement("nickname");
		nickname2.appendChild(doc.createTextNode("mkyong"));
		staff1.appendChild(nickname2);

		// salary elements
		Element salary2 = doc.createElement("salary");
		salary2.appendChild(doc.createTextNode("100000"));
		staff1.appendChild(salary2);

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("C:\\Users\\Benedikt\\Desktop\\Studium\\Master\\1.Semester\\Cloud Computing Technology\\file.xml"));

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