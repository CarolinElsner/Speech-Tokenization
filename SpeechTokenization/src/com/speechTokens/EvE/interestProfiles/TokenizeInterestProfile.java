package com.speechTokens.EvE.interestProfiles;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.speechTokens.semantic.analysis.KeywordSearch;
import com.speechTokens.tokenizer.Chunker;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;

public class TokenizeInterestProfile extends AbstractInterestProfile {


	private static final long serialVersionUID = -8805054035365429362L;
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static Logger LOGGER = LoggerFactory.getLogger(TokenizeInterestProfile.class);
	
	
	
	@Override
	protected void doOnReceive(AbstractEvent event) {
		
		
		ArrayList<Object> chunkObjectList = (ArrayList<Object>) EventUtils.findPropertyByKey(event, "Chunks").getValue();
		Chunker chunks = new Chunker();
		chunks.parseArrayList(chunkObjectList); // The EVE Framework can't work with chunker Objects therefore, they are sent as ArrayLists and then parsed to Chunker Objects
		ArrayList<String> keywords = KeywordSearch.findKeywords(chunks);
		if(keywords.size() == 0) { // no keyword was found
		
		AbstractEvent noKeywordEvent = eventFactory.createEvent("AtomicEvent");
		noKeywordEvent.setType("NoKeywordEvent");
		//Besitzt event nur eine UserID??
		noKeywordEvent.add(new Property<>("UserID", EventUtils.findPropertyByKey(event, "UserID").getValue()));
		noKeywordEvent.add(new Property<>("SessionID", EventUtils.findPropertyByKey(event, "SessionID").getValue()));
		noKeywordEvent.add(new Property<>("SentenceID", EventUtils.findPropertyByKey(event, "SentenceID").getValue()));
		noKeywordEvent.add(new Property<>("Chunks", EventUtils.findPropertyByKey(event, "Chunks")).getValue());
		
		try {
			this.getAgent().send(noKeywordEvent, "Keywords");
		} catch (NoValidEventException e) {
		
			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
			
			e.printStackTrace();
		}
		
		}else if (keywords.size() == 1) { // one Keyword was found
			AbstractEvent singleKeywordEvent = eventFactory.createEvent("AtomicEvent");		
			
			singleKeywordEvent.setType("SingleKeywordEvent");
			//Besitzt event nur eine UserID??
			singleKeywordEvent.add(new Property<>("UserID", EventUtils.findPropertyByKey(event, "UserID").getValue()));
			singleKeywordEvent.add(new Property<>("SessionID", EventUtils.findPropertyByKey(event, "SessionID").getValue()));
			singleKeywordEvent.add(new Property<>("SentenceID", EventUtils.findPropertyByKey(event, "SentenceID").getValue()));
			singleKeywordEvent.add(new Property<>("Chunks", chunks.returnList())); // Give the new chunker object where keyword chunk is removed
			singleKeywordEvent.add(new Property<>("Keywords", keywords.get(0))); // Pushes the Keyword as an String (the Keyword) into the Event

			try {
				this.getAgent().send(singleKeywordEvent, "Keywords");
			} catch (NoValidEventException e) {
				e.printStackTrace();
			} catch (NoValidTargetTopicException e) {
				
				e.printStackTrace();
			}
		
		}else { // size of Keywords Arraylist is bigger than one, so more keywords were found
			
			AbstractEvent severalKeywordsEvent = eventFactory.createEvent("AtomicEvent");
			severalKeywordsEvent.setType("SeveralKeywordsEvent");
			//Besitzt event nur eine UserID??
			severalKeywordsEvent.add(new Property<>("UserID", EventUtils.findPropertyByKey(event, "UserID").getValue()));
			severalKeywordsEvent.add(new Property<>("SessionID", EventUtils.findPropertyByKey(event, "SessionID").getValue()));
			severalKeywordsEvent.add(new Property<>("SentenceID", EventUtils.findPropertyByKey(event, "SentenceID").getValue()));
			severalKeywordsEvent.add(new Property<>("Chunks", chunks.returnList()));// add new chunker object
			severalKeywordsEvent.add(new Property<>("Keywords", keywords)); // Pushes the Keyword as an ArrayList<String> with multiple entries (the Keywords) into the Event
	
			
			try {
				this.getAgent().send(severalKeywordsEvent, "Keywords");
			} catch (NoValidEventException e) {

				e.printStackTrace();
			} catch (NoValidTargetTopicException e) {
				
				e.printStackTrace();
			}
		}
		
	}

}
