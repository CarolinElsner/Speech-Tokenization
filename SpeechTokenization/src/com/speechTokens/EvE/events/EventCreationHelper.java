package com.speechTokens.EvE.events;

import java.util.ArrayList;

import com.speechTokens.semantic.analysis.JsonHandler;
import com.speechTokens.tokenizer.Chunker;

import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.model.EventUtils;

public class EventCreationHelper {
	/**
	 * Looks in the semantic of a Chunker Object with one chunk and one sem info and returns the proprate event type
	 * @param chunks contains just one Element with one semantic information, otherwise we would not be sure which actionEvent to send
	 * @param actionEvent will be configured to the new event that will be sent, can be DocumentEvent, PersonEvent or ProjectEvent
	 * @param oldEvent the data from the old event
	 */
	public static AbstractEvent createEvent(Chunker chunks, AbstractEvent actionEvent, AbstractEvent oldEvent) {

		actionEvent.add(new Property<>("UserID",EventUtils.findPropertyByKey(oldEvent, "UserID")));
		actionEvent.add(new Property<>("Timestamp",EventUtils.findPropertyByKey(oldEvent, "Timestamp")));
		actionEvent.add(new Property<>("SessionID",EventUtils.findPropertyByKey(oldEvent, "SessionID")));
		actionEvent.add(new Property<>("Sentence",EventUtils.findPropertyByKey(oldEvent, "SentenceID")));

		ArrayList<String> semanticStr = (ArrayList<String>) chunks.getSemanticAt(0);
		JsonHandler js = new JsonHandler();
		if(js.semanticLookUp(semanticStr.get(0), "Document").size()==1) {
			actionEvent.setType("DocumentEvent");
			actionEvent.add(new Property<>("Chunks", chunks));
			
		}else if(js.semanticLookUp(semanticStr.get(0), "Project").size()==1) {
			actionEvent.setType("ProjectEvent");
			actionEvent.add(new Property<>("Chunks", chunks));
						
		}else if(js.semanticLookUp(semanticStr.get(0), "Person").size()==1) {
			actionEvent.setType("PersonEvent");
			actionEvent.add(new Property<>("Chunks", chunks));
						
		}else {
			actionEvent.setType("UncertainEvent");
			actionEvent.add(new Property<>("Chunks", chunks));
			System.out.println("No specific type detected, uncertain event has to be pushed");
		}
		return actionEvent;
	}
}
