package com.speechTokens.EvE.interestProfiles;

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


/**
 * Trifft anhand der Sensoren, Durchschnittsgeschwindigkeit sowie mit
 * "Fachwissen" die Entscheidung, ob eine Geschwindigkeitsüberschreitung
 * stattgefunden hat oder nicht. In beiden Fällen wird ein
 * <code>ComplexEvent</code> erzeugt und auf ein separates Topic weitergeleitet
 * 
 * @author IngoT
 *
 */
public class SingleKeywordIP extends AbstractInterestProfile {

	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static final long serialVersionUID = -6108185466150892913L;
	private static Logger LOGGER = LoggerFactory.getLogger(SingleKeywordIP.class);

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {

		
		String keyword = EventUtils.findPropertyByKey(event, "Keyword").getValue().toString();
		Chunker chunks = (Chunker) EventUtils.findPropertyByKey(event, "Chunks").getValue();
		Chunker semFoundChunks = KeywordSearch.oneKeyword(keyword, chunks);

		//TODO: HIER EVENTS WIE DOCUMENT ETC EVENTS ERSTELLEN; IMMMER BEZ�GLICH DES ERKANNTEN TYPES
		AbstractEvent actionEvent = eventFactory.createEvent("AtomicEvent");
		actionEvent.setType("actionEvent");
		actionEvent.add(new Property<>("UserID",EventUtils.findPropertyByKey(event, "UserID")));
		actionEvent.add(new Property<>("Timestamp",EventUtils.findPropertyByKey(event, "Timestamp")));
		actionEvent.add(new Property<>("SessionID",EventUtils.findPropertyByKey(event, "SessionID")));
		actionEvent.add(new Property<>("Sentence",EventUtils.findPropertyByKey(event, "SentenceID")));
		actionEvent.add(new Property<>("Chunks", semFoundChunks));
		// TODO: ACTIONEVENT oder spezifische Events hier erstellen
		try {
			this.getAgent().send(actionEvent, "TokenGeneration");
			
		} catch (NoValidEventException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}