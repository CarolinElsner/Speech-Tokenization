package com.speechTokens.tokenizer;

import java.util.ArrayList;

public class DetectApplication {
	// Die Klasse DetectApplication �bernimmt die Suche nach Keywords bzgl. der Google Application
	// Gefundene Keywords haben die Erstellung bzw. das Versenden von 
	public static ArrayList<String> detection(Chunker chunks){
			
			ArrayList<String> appdetection = new ArrayList<>();
			chunks.printList();

// Zweidimesionales String Array zur Definition von Keywords --> {"Keyword", "zugeordneter Applikationstyp"}
// Keywords sind stark vereinfacht definiert (Bsp. keep bezieht sich nicht nur auf die application) --> Ansatz: M�glichst viele Treffer
// --> Jeden Nennung von "keep" --> ApplicationEvent auch wenn "keep" auch eine andere Bedeutung hat
			

			String[][] appkeywords = {
					{"google mail","mail"},
					{"mails","mail"},
					{"mail","mail"},
					{"google presentation","presentation"}, 
					{"presentation", "presentation"},
					{"presentations", "presentation"},
					{"doc", "docs"},
					{"docs", "docs"},
					{"google docs","docs"},
					{"google keep", "keep"},
					{"notes", "keep"},
					{"note", "note"},
					};

// �bergebenes Chunker-Objekt wird durchlaufen und mit dem Array appkeywords verglichen.
// Wird ein keyword gefunden, wird der ArrayList der zugeh�rige Apllikationstyp als neues Element hinzugef�gt 		
			
			for(int i=0;i<chunks.size();i++) {
				for(int j=0;j<appkeywords.length;j++) {
					if(chunks.getChunkContentAt(i).equals(appkeywords[j][0])) {
						appdetection.add(appkeywords[j][1]);
					}
				}
					
			}			

			return appdetection;

		}


}