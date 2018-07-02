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

	private static final long serialVersionUID = 1L;
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	Logger l = LoggerFactory.getLogger("SessionState");
	
	
	
	@Override
	protected void doOnReceive(AbstractEvent event) {
		
		
		Chunker chunks = (Chunker) EventUtils.findPropertyByKey(event, "Chunks").getValue();
		ArrayList<String> keywords = KeywordSearch.findKeywords(chunks);
		if(keywords.size() == 0) {
		
		AbstractEvent noKeywordEvent = eventFactory.createEvent("AtomicEvent");
		noKeywordEvent.setType("NoKeywordsEvent");
		//Besitzt event nur eine UserID??
		noKeywordEvent.add(new Property<>("UserID", EventUtils.findPropertyByKey(event, "UserID")));
		noKeywordEvent.add(new Property<>("Timestamp", EventUtils.findPropertyByKey(event, "Timestamp")));
		noKeywordEvent.add(new Property<>("SessionID", EventUtils.findPropertyByKey(event, "SessionID")));
		noKeywordEvent.add(new Property<>("SentenceID", EventUtils.findPropertyByKey(event, "SentenceID")));
		noKeywordEvent.add(new Property<>("Chunks", EventUtils.findPropertyByKey(event, "Chunks")));		
		
		try {
			this.getAgent().send(noKeywordEvent, "TokenGeneration");
		} catch (NoValidEventException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}else if (keywords.size() == 1) {

			chunks.removeChunkAndSem(keywords.get(0));// remove keyword chunk
			AbstractEvent singleKeywordEvent = eventFactory.createEvent("AtomicEvent");		
			
			singleKeywordEvent.setType("SingleKeywordEvent");
			//Besitzt event nur eine UserID??
			singleKeywordEvent.add(new Property<>("UserID", EventUtils.findPropertyByKey(event, "UserID")));
			singleKeywordEvent.add(new Property<>("Timestamp", EventUtils.findPropertyByKey(event, "Timestamp")));
			singleKeywordEvent.add(new Property<>("SessionID", EventUtils.findPropertyByKey(event, "SessionID")));
			singleKeywordEvent.add(new Property<>("SentenceID", EventUtils.findPropertyByKey(event, "SentenceID")));
			singleKeywordEvent.add(new Property<>("Chunks", chunks)); // Give the new chunker object where keyword chunk is removed
			singleKeywordEvent.add(new Property<>("Keywords", keywords));

			try {
				this.getAgent().send(singleKeywordEvent, "TokenGeneration");
			} catch (NoValidEventException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoValidTargetTopicException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}else {
			
			for (int i = 0; i < keywords.size(); i++) {
				chunks.removeSemanticOfChunk(keywords.get(i)); // there have to be more than one keyword included, remove them
			}
			AbstractEvent severalKeywordsEvent = eventFactory.createEvent("AtomicEvent");
			severalKeywordsEvent.setType("SeveralKeywordsEvent");
			//Besitzt event nur eine UserID??
			severalKeywordsEvent.add(new Property<>("UserID", EventUtils.findPropertyByKey(event, "UserID")));
			severalKeywordsEvent.add(new Property<>("Timestamp", EventUtils.findPropertyByKey(event, "Timestamp")));
			severalKeywordsEvent.add(new Property<>("SessionID", EventUtils.findPropertyByKey(event, "SessionID")));
			severalKeywordsEvent.add(new Property<>("SentenceID", EventUtils.findPropertyByKey(event, "SentenceID")));
			severalKeywordsEvent.add(new Property<>("Chunks", chunks));// add new chunker object
			severalKeywordsEvent.add(new Property<>("Keywords", keywords));
	
			
			try {
				this.getAgent().send(severalKeywordsEvent, "TokenGeneration");
			} catch (NoValidEventException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoValidTargetTopicException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
