package com.speechTokens.testing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import com.speechTokens.EvE.events.EventCreationHelper;
import com.speechTokens.semantic.analysis.KeywordSearch;
import com.speechTokens.tokenizer.Chunker;
import com.speechTokens.tokenizer.DetectApplication;
import com.speechTokens.tokenizer.DetectTermin;
import com.speechTokens.tokenizer.Tokenization;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.model.EventUtils;

public class Testing {


	public static void main(String[] args) {
		
		ArrayList<String> milestonePlan =new ArrayList<>();
		// keywords: milestone; phase; plan; project; deadline;
		//milestonePlan.add("	{\"head\": {\"vars\": [ \"Instanzname\" , \"Beziehung\" , \"Instanzname2\" , \"Classname\" , \"Attribut\" , \"x\" ]} ,\"results\": {\"bindings\": [ {\r\n\"{ \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#MilestonePlan\" } , \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsChangedBy\" } , \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FlorianHahn\" } , \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Document\" } , \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } , \"Name\": { \"type\": \"literal\" , \"value\": \"milestone plan\" } , \"Keyword\": { \"type\": \"literal\" , \"value\": \"milestone; phase; plan; project; deadline;\" } }]}} ,");
		milestonePlan.add("{\r\n" + 
				"  \"head\": {\r\n" + 
				"    \"vars\": [ \"Instanzname\" , \"Beziehung\" , \"Instanzname2\" , \"Classname\" , \"Attribut\" , \"x\" ]\r\n" + 
				"  } ,\r\n" + 
				"  \"results\": {\r\n" + 
				"    \"bindings\": [\r\n" + 
				"      {\r\n" + 
				"        \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostPlan\" } ,\r\n" + 
				"        \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsCreatedFor\" } ,\r\n" + 
				"        \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#HighNet\" } ,\r\n" + 
				"        \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Document\" } ,\r\n" + 
				"        \"Name\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#URL\" } ,\r\n" + 
				"        \"Keyword\": { \"type\": \"literal\" , \"value\": \"https://drive.google.com/open?id=17LFPYVxbBQK5Wdt5SrNgo3_iYxXJiXfXq6jYoLl8H4Y\" }\r\n" + 
				"      } ,\r\n" + 
				"      {\r\n" + 
				"        \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostPlan\" } ,\r\n" + 
				"        \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsCreatedFor\" } ,\r\n" + 
				"        \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#HighNet\" } ,\r\n" + 
				"        \"Classname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#ProjectControlling\" } ,\r\n" + 
				"        \"Name\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } ,\r\n" + 
				"        \"x\": { \"type\": \"literal\" , \"value\": \"costplan\" }\r\n" + 
				"      }]}}");
		
		
		
		
		
		
		ArrayList<String> thomas = new ArrayList<>();
		// keywords : manager; male
		thomas.add("{\r\n" + 
				"  \"head\": {\r\n" + 
				"    \"vars\": [ \"Instanzname\" , \"Beziehung\" , \"Instanzname2\" , \"Classname\" , \"Attribut\" , \"x\" ]\r\n" + 
				"  } ,\r\n" + 
				"  \"results\": {\r\n" + 
				"    \"bindings\": [\r\n" + 
				"      {\r\n" + 
				"        \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostPlan\" } ,\r\n" + 
				"        \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsCreatedFor\" } ,\r\n" + 
				"        \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#HighNet\" } ,\r\n" + 
				"        \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Person\" } ,\r\n" + 
				"        \"Name\": { \"type\": \"uri\" , \"value\": Thomas Mueller\" } ,\r\n" + 
				"        \"Keyword\": { \"type\": \"literal\" , \"value\":milestone plan\" }\r\n" + 
				"      } ,\r\n" + 
				"      {\r\n" + 
				"        \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#CostPlan\" } ,\r\n" + 
				"        \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsCreatedFor\" } ,\r\n" + 
				"        \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#HighNet\" } ,\r\n" + 
				"        \"Classname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#ProjectControlling\" } ,\r\n" + 
				"        \"Name\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } ,\r\n" + 
				"        \"x\": { \"type\": \"literal\" , \"value\": \"costplan\" }\r\n" + 
				"      }]}}");
				
		ArrayList<String> highnet = new ArrayList<>();
		// keywords : highnet; information
		highnet.add("{ \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#MilestonePlan\" } , \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsChangedBy\" } , \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FlorianHahn\" } , \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Project\" } , \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } , \"Name\": { \"type\": \"literal\" , \"value\": \"Highnet Project\" } , \"Keyword\": { \"type\": \"literal\" , \"value\": \"highnet; information; revenue;\" } } ,");
		
		ArrayList<String> josef = new ArrayList();
		// keywords: munich; revenue; stream; // Josef Project
		josef.add("{ \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Josef\" } , \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsChangedBy\" } , \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FlorianHahn\" } , \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Project\" } , \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } , \"Name\": { \"type\": \"literal\" , \"value\": \"Josef Project\" } , \"Keyword\": { \"type\": \"literal\" , \"value\": \"munich; revenue; stream;\" } } ,");
		// keywords: "word; hamilton; // Josef File
		josef.add("{ \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Josef\" } , \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsChangedBy\" } , \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FlorianHahn\" } , \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Project\" } , \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } , \"Name\": { \"type\": \"literal\" , \"value\": \"Josef File\" } , \"Keyword\": { \"type\": \"literal\" , \"value\": \"word; hamilton;\" } } ,");
		// keywords: male; big; accountant; // Josef Miller
		josef.add("{ \"Instanzname\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Josef\" } , \"Beziehung\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#IsChangedBy\" } , \"Instanzname2\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FlorianHahn\" } , \"Oberklasse\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#Person\" } , \"Attribut\": { \"type\": \"uri\" , \"value\": \"http://www.semanticweb.org/jennifertran/ontologies/2018/0/dokumentenRepraesentation#FileName\" } , \"Name\": { \"type\": \"literal\" , \"value\": \"Josef Miller\" } , \"Keyword\": { \"type\": \"literal\" , \"value\": \"male; big; accountant;\" } } ,");
		

		Tokenization chunking = new Tokenization();
		//Variable um zu prüfen ob Datum in Satz enthalten ist
		boolean foundDate;
		//boolean foundDayMonth = DetectTermin.dayMonthfound;
		String sentence= "Thomas and Milestone ";
		ArrayList<String> chunks = new ArrayList<String>();
		try {
			chunks = (ArrayList<String>) chunking.doTokenization(sentence);
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
		//Zu erst pürfen ob ein exaktes Datum angegeben wurde
		DetectTermin detector = new DetectTermin();
		foundDate = detector.validate(sentence);
		ArrayList<String> chunkscleaned = detector.searchDate(chunks);
		if(DetectTermin.dayMonthfound == true) {
			System.out.println("Kalenderaktion erkannt");
		}else if(foundDate == true) {
			System.out.println("explitziter Termin erkannt");
		}else {
			System.out.println("Keine Termine");
		}
		
		detector.deleteAllDates();
		//Chunker befüllen und alle Chunks in Kleinbuchstaben
		Chunker chunk = new Chunker();
		for (int i=0; i<chunkscleaned.size();i++) {
			chunk.addChunkContent(chunkscleaned.get(i).toLowerCase());
		}
		//Detecting Application Keywords and publishing ApplicationEvent
		DetectApplication appdetection = new DetectApplication();
		
		ArrayList<String> foundapps = appdetection.detection(chunk);
		
		if(foundapps.size()>0) {
			for (int i = 0; i < foundapps.size(); i++) {
				System.out.println("App erkannt:" + foundapps.get(i));
			}
		}else {
			System.out.println("Keine Apps");
		}
		if(chunk.size()!=0) {
			if(chunk.hasChunk("milestone")) {
				chunk.addSemanticToChunk("milestone", milestonePlan.get(0));
			}else if (chunk.hasChunk("thomas")) {
				chunk.addSemanticToChunk("thomas", thomas.get(0));
			}else {
				System.out.println("Error");
			}
			chunk.printList();
			//chunk.addSemanticToChunk(chunk, semantics);
			// zu dieesem Zeitpunk muss der Chunker Sem infos haben
			Chunker semFoundChunks = new Chunker();
			ArrayList<String> keywords = KeywordSearch.findKeywords(chunk);
			if(keywords.size() == 0) {
				semFoundChunks = KeywordSearch.noKeyword(chunk);
			}else if(keywords.size() == 1) {
				System.out.println(keywords);
				semFoundChunks = KeywordSearch.oneKeyword(keywords.get(0), chunk);
			}else if(keywords.size() >1) {
				semFoundChunks = KeywordSearch.severalKeywords(keywords, chunk);
			}else {
				System.out.println("###Error###");
			}
			for (int i = 0; i < semFoundChunks.size(); i++) {

				Object semantic = semFoundChunks.getSemanticAt(i);
					ArrayList<String> newSemantic = (ArrayList<String>) semantic;
					Chunker tempChunker = new Chunker(); // create a temporary chunker which consists of just one chunk
					String currChunk = semFoundChunks.getChunkContentAt(i);
					tempChunker.addChunkContent(currChunk);
					tempChunker.addSemanticToChunk(currChunk, semFoundChunks.readSemanticOfChunk(currChunk));
					if(newSemantic != null && newSemantic.size()>1) {// more than one sem data off the respective chunk, so we dont know which type
						System.out.println(createEvent(tempChunker));			
					}else if(newSemantic == null) {
						
					}
					else { // just one semantic entry was found for the chunk
						// Hier geht es um ein Event, die eine spezifische Aktion erfordert
						String chunkStr = semFoundChunks.getChunkContentAt(i);
						Object sem = semFoundChunks.getSemanticAt(i);
						Chunker tokenChunker = new Chunker();
						tokenChunker.addChunkContent(chunkStr);
						tokenChunker.addSemanticToChunk(chunkStr, sem);
						
						System.out.println(createEvent(tokenChunker));
					}
			}
		}
	}
	public static ArrayList<?> createEvent(Chunker chunks) {
		ArrayList<String> semanticStr = (ArrayList<String>) chunks.getSemanticAt(0);
		ArrayList<Property> properties = new ArrayList<>();
		Property<?> prop = new Property<>();
		for (int i = 0; i < semanticStr.size(); i++) {
			JSONObject semantics = new JSONObject(semanticStr.get(i));
			String semType = "";
			semType = (String) semantics.get("Oberklasse").toString(); // get the JSON Object and look in it for the Keyword

			if(semType.contains("#Document")== true) {
				prop = chunkerToEvent(semanticStr.get(i), "Document");
				
			}else if(semType.contains("#Project")== true) {
				prop = chunkerToEvent(semanticStr.get(i), "Project");
							
			}else if(semType.contains("#Person")== true) {
				prop = chunkerToEvent(semanticStr.get(i), "Person");
							
			}else { // The semantic information doesnt contain either one of the strings
				prop = chunkerToEvent(semanticStr.get(i), "Uncertain");
				System.out.println("EventCreationHelper.createEvent: No specific type detected, uncertain event has to be pushed");
			}
			properties.add(prop);
		}
		return properties;
	}
	
	/**
	 * creates a property Object containing the semantic info of the found chunk. It is a property in a property
	 * @param semanticJSON the semantic json String
	 * @param type The Type which was identified
	 * @return a new property Object {type:{name, keyword}}
	 */
	public static Property<?> chunkerToEvent(String semanticJSON, String type) { // The token Chunk has to be inserted, with just one Chunk and maybe more sem infos
		JSONObject semJs = new JSONObject(semanticJSON);
		String nameValue = "";
		String keywordValue= "";
		Iterator<String> keys = semJs.keys();
		while(keys.hasNext()) {
			String key = keys.next();
			if(key.equals("Name")) {
				if(semJs.get(key) instanceof JSONObject) {
					JSONObject name = semJs.getJSONObject(key);
					if(name.get("value") instanceof String) {
						nameValue = name.getString("value");
					}else {
						System.out.println("EventCreationHelper.chunkerToEvent: Wrong value in KEy: value of Name");						
					}					
				}else {
					System.out.println("EventCreationHelper.chunkerToEvent: Wrong value in KEy: Name");
				}
			}else if(key.equals("Keyword")) {
				if(semJs.get(key) instanceof JSONObject) {
					JSONObject keyword = semJs.getJSONObject(key);
					if(keyword.get("value") instanceof String) {
						keywordValue = keyword.getString("value");
					}else {
						System.out.println("EventCreationHelper.chunkerToEvent: Wrong value in KEy: value of Keyword");						
					}
				}else {
					System.out.println("EventCreationHelper.chunkerToEvent: Wrong value in KEy: Keyword");
				}
			}
		}
		// create new property that will be included in the actionProperty
		Property<String> nameKeyProp = new Property<String>();
		nameKeyProp.setKey(nameValue); // set the key to the name that was found
		nameKeyProp.setValue(keywordValue);		 // set the value to the keywords that DR group found
		Property<?> actionProperty = new Property<Property<String>>(type,new Property<String>(nameValue, keywordValue));
		
		return actionProperty;
	}
}

