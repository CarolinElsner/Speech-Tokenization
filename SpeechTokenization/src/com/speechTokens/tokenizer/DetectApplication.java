package com.speechTokens.tokenizer;

import java.util.ArrayList;

public class DetectApplication {
	
	public static ArrayList<String> detection(Chunker chunks){
			
			ArrayList<String> appdetection = new ArrayList<>();
			
			//Hier Keywords definieren, die für die Erzeugung von ApplicationEvents relevant sind
			String[][] appkeywords = {
					{"google mail","mail"},
					{"mail","mail"},
					{"google presentation","presentation"}, 
					{"google docs","docs"}				
					};
			
			for(int i=0;i<chunks.size();i++) {
				System.out.println("i"+i);

				for(int j=0;j<appkeywords.length;j++) {

					if(chunks.getChunkContentAt(i) == appkeywords[j][0]) {
						appdetection.add(appkeywords[j][1]);
					}
				}
					
			}				
			
			return appdetection;

		}


}
