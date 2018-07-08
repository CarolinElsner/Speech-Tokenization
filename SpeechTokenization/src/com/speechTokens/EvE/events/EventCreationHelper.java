package com.speechTokens.EvE.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONObject;

import com.speechTokens.semantic.analysis.JsonHandler;
import com.speechTokens.tokenizer.Chunker;

import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.model.EventUtils;

public class EventCreationHelper {
	/**
	 * The chunker may only contain one Chunk and the Chunk may only contain one Semantic Information in the Array, further sem infos wont be read
	 * Looks in the semantic of a Chunker Object with one chunk and one sem info and returns the proprate event type
	 * @param chunks contains just one Element with one semantic information, otherwise we would not be sure which actionEvent to send
	 * @param actionEvent will be configured to the new event that will be sent, can be DocumentEvent, PersonEvent or ProjectEvent
	 * @param oldEvent the data from the old event
	 */
	public static AbstractEvent createEvent(Chunker chunks, AbstractEvent actionEvent, AbstractEvent oldEvent) {

		actionEvent.add(new Property<>("UserID",EventUtils.findPropertyByKey(oldEvent, "UserID").getValue()));
		actionEvent.add(new Property<>("SessionID",EventUtils.findPropertyByKey(oldEvent, "SessionID").getValue()));
		actionEvent.add(new Property<>("SentenceID", EventUtils.findPropertyByKey(oldEvent, "SentenceID").getValue()));

		// TODO: Nicht so nach keywords suchen, da Projectplan zu keyword "Projekt" gehören würde, desewegen nach keyword #projekt suchen
		ArrayList<String> semanticStr = (ArrayList<String>) chunks.getSemanticAt(0);
		for (int i = 0; i < semanticStr.size(); i++) {
			JSONObject semantics = new JSONObject(semanticStr.get(i));
			String semType = "";
			semType = (String) semantics.get("Oberklasse").toString(); // get the JSON Object and look in it for the Keyword

			if(semType.contains("#Document")== true) {
				actionEvent.setType("DocumentEvent");
				actionEvent.add(chunkerToEvent(semanticStr.get(i), "Document"));
				
			}else if(semType.contains("#Project")== true) {
				actionEvent.setType("ProjectEvent");
				actionEvent.add(chunkerToEvent(semanticStr.get(i), "Project"));
							
			}else if(semType.contains("#Person")== true) {
				actionEvent.setType("PersonEvent");
				actionEvent.add(chunkerToEvent(semanticStr.get(i), "Person"));
							
			}else { // The semantic information doesnt contain either one of the strings
				actionEvent.setType("UncertainEvent");
				actionEvent.add(chunkerToEvent(semanticStr.get(i), "Uncertain"));
				System.out.println("EventCreationHelper.createEvent: No specific type detected, uncertain event has to be pushed");
			}
		}
		return actionEvent;
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
