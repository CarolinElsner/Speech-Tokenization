package com.speechTokens.tokenizer;

import java.util.ArrayList;

public class DetectApplication {
	// Die Klasse DetectApplication übernimmt die Suche nach Keywords bzgl. der Google Application
	// Gefundene Keywords haben die Erstellung bzw. das Versenden eines ApplicationEvents zur Folge
	public static ArrayList<String> detection(String sentence){
			
			ArrayList<String> appdetection = new ArrayList<>();
			System.out.println(sentence);
// Zweidimesionales String Array zur Definition von Keywords --> {"Keyword", "zugeordneter Applikationstyp"}
// Keywords sind stark vereinfacht definiert (Bsp. keep bezieht sich nicht nur auf die application) --> Ansatz: Möglichst viele Treffer
// --> Jeden Nennung von "keep" --> ApplicationEvent auch wenn "keep" auch eine andere Bedeutung hat
			

			String[][] appkeywords = {
					{"mail","mail"},
					{"presentation", "presentation"},
					{"doc", "docs"},
					{"google keep", "keep"},
					{"note", "keep"},
					{"google drive","drive"}
					};

//Iteration durch Array appkeywords. Prüfen, ob keywords im jeweiligen Satz enthalten sind.	
// Wird ein keyword gefunden, wird der ArrayList der zugehörige Apllikationstyp als neues Element hinzugefügt 		
			
			//Interation durch alle Keyword und 
			
			for(int i=0;i<appkeywords.length;i++) {
					if(sentence.toLowerCase().contains(appkeywords[i][0])) {
						appdetection.add(appkeywords[i][1]);
						System.out.println("Application detected: " + appkeywords[i][1]);
					}
				}
								

			return appdetection;

		}


}
