package br.usp.ime.bcc.mac499.ses.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLReader {
	
	private Document doc;
	
	public XMLReader(String target) {
		File xmlFile = new File(target);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
		} catch (ParserConfigurationException e) {
			System.out.println("Exception at XMLReader in the call of the function dbFactory.newDocumentBuilder()!");
			e.printStackTrace();
		} catch (IOException | SAXException e) {
			System.out.println("Exception at XMLReader in the call of function dBuilder.parse(fXmlFile)!");
			e.printStackTrace();
		}
	}
	
	public XMLReader(File xmlFile) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
		} catch (ParserConfigurationException e) {
			System.out.println("Exception at XMLReader in the call of the function dbFactory.newDocumentBuilder()!");
			e.printStackTrace();
		} catch (IOException | SAXException e) {
			System.out.println("Exception at XMLReader in the call of function dBuilder.parse(fXmlFile)!");
			e.printStackTrace();
		}
	}
	
	public Document getDocument() {
		return doc;
	}
	
}
