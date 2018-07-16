package com.speechTokens.tokenizer;

import java.util.ArrayList;

public class DetectApplication {
	// Die Klasse DetectApplication übernimmt die Suche nach Keywords bzgl. der Google Application
	// Gefundene Keywords haben die Erstellung bzw. das Versenden eines ApplicationEvents zur Folge
	public static ArrayList<String> detection(String sentence){
			
			ArrayList<String> appdetection = new ArrayList<>();
			ArrayList<String> appkeywords = new ArrayList<>();
		
			appkeywords.add("google keep");
			appkeywords.add("google drive");
			appkeywords.add("google docs");
			appkeywords.add("presentation");
			appkeywords.add("google mail");
			appkeywords.add("mail");


//Iteration durch Array appkeywords. Prüfen, ob keywords im jeweiligen Satz enthalten sind.	
// Wird ein keyword gefunden, wird der ArrayList der zugehörige Apllikationstyp als neues Element hinzugefügt 		
			
			//Interation durch alle Keyword
			
			for(int i=0;i<appkeywords.size();i++) {
					if(sentence.toLowerCase().contains(appkeywords.get(i))) {
						//pr�fen ob Google in keyword enthalten ist, falls ja dann soll dies entfernt werden. Dient der besseren Anischt in der GUI
						if(sentence.toLowerCase().contains("google")) {
							String shortApplicationName = appkeywords.get(i).substring(7);
							appdetection.add(shortApplicationName);
							System.out.println("Application gefunden von Goolge " + shortApplicationName);
						}
						else {
						appdetection.add(appkeywords.get(i));
						System.out.println("Application detected: " + appkeywords.get(i));
						}
					}
				}
			return appdetection;
		}
}
