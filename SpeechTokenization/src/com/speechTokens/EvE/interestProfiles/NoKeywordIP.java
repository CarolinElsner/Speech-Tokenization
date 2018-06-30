package com.speechTokens.EvE.interestProfiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.speechTokens.EvE.events.SentenceEvent;
import com.speechTokens.EvE.events.WatsonEvent;
import com.speechTokens.semantic.analysis.KeywordSearch;
import com.speechTokens.tokenizer.Chunker;
import com.speechTokens.tokenizer.DetectTermin;
import com.speechTokens.tokenizer.Tokenization;

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


/**
 * Trifft anhand der Sensoren, Durchschnittsgeschwindigkeit sowie mit
 * "Fachwissen" die Entscheidung, ob eine Geschwindigkeitsüberschreitung
 * stattgefunden hat oder nicht. In beiden Fällen wird ein
 * <code>ComplexEvent</code> erzeugt und auf ein separates Topic weitergeleitet
 * 
 * @author IngoT
 *
 */
public class NoKeywordIP extends AbstractInterestProfile {

	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static final long serialVersionUID = -6108185466150892913L;
	private static Logger LOGGER = LoggerFactory.getLogger(NoKeywordIP.class);

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {

				
		Chunker chunks = (Chunker) EventUtils.findPropertyByKey(event, "Chunks").getValue();

		Chunker semFoundChunks = KeywordSearch.noKeyword(chunks);

		
		AbstractEvent noKeywordEvent = eventFactory.createEvent("AtomicEvent");
		noKeywordEvent.setType("noKeywordEvent");
		noKeywordEvent.add(new Property<>("UserID",EventUtils.findPropertyByKey(event, "UserID")));
		noKeywordEvent.add(new Property<>("Timestamp",EventUtils.findPropertyByKey(event, "Timestamp")));
		noKeywordEvent.add(new Property<>("SessionID",EventUtils.findPropertyByKey(event, "SessionID")));
		noKeywordEvent.add(new Property<>("Sentence",EventUtils.findPropertyByKey(event, "SentenceID")));
		noKeywordEvent.add(new Property<>("Chunks",semFoundChunks));

		
		try {
			this.getAgent().send(noKeywordEvent, "Keywords");
			
		} catch (NoValidEventException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}