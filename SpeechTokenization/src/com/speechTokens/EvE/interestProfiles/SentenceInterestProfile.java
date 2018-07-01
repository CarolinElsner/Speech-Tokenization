package com.speechTokens.EvE.interestProfiles;

import java.awt.Checkbox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class SentenceInterestProfile extends AbstractInterestProfile {

	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static final long serialVersionUID = -6108185466150892913L;
	private static Logger LOGGER = LoggerFactory.getLogger(SentenceInterestProfile.class);

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {

		
		String sentence = EventUtils.findPropertyByKey(event, "Sentence").getValue().toString();
		
	
		
		
		Tokenization chunking = new Tokenization();
		//Variable um zu pr�fen ob Datum in Satz enthalten ist
		boolean foundDate;
		List<String> chunks = new ArrayList<String>();
		try {
			chunks = chunking.doTokenization(sentence);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Zu erst p�rfen ob ein exaktes Datum angegeben wurde
		DetectTermin detector = new DetectTermin();
		foundDate = detector.validate(sentence);
		
		//Wenn Datum gefunden, dann Kalenderevent
		if(foundDate == true) {
			AbstractEvent calendarevent = eventFactory.createEvent("AtomicEvent");
			calendarevent.setType("CalendarEvent");
			//Besitzt event nur eine UserID??
			calendarevent.add(new Property<>("UserID", EventUtils.findPropertyByKey(event, "UserID")));
			calendarevent.add(new Property<>("Timestamp", EventUtils.findPropertyByKey(event, "Timestamp")));
			calendarevent.add(new Property<>("SessionID", EventUtils.findPropertyByKey(event, "SessionID")));
			calendarevent.add(new Property<>("SentenceID", EventUtils.findPropertyByKey(event, "SentenceID")));
			calendarevent.add(new Property<>("Termin", detector.getfoundetDate()));
			
			try {
				this.getAgent().send(calendarevent, "TokenGeneration");
			} catch (NoValidEventException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoValidTargetTopicException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Chunker bef�llen und alle Chunks in Kleinbuchstaben
		Chunker chunk = new Chunker();
		for (int i=0; i<chunks.size();i++) {
			chunk.addChunkContent(chunks.get(i).toLowerCase());
		}
		
		
		detector.deleteAllDates();
		
		
		AbstractEvent sentenceEvent = eventFactory.createEvent("AtomicEvent");
		sentenceEvent.setType("SentenceEvent");
		sentenceEvent.add(new Property<>("UserID",EventUtils.findPropertyByKey(event, "UserID")));
		sentenceEvent.add(new Property<>("Timestamp",EventUtils.findPropertyByKey(event, "Timestamp")));
		sentenceEvent.add(new Property<>("SessionID",EventUtils.findPropertyByKey(event, "SessionID")));
		sentenceEvent.add(new Property<>("Sentence",EventUtils.findPropertyByKey(event, "SentenceID")));
		sentenceEvent.add(new Property<>("Chunks",chunk));
		
		try {
			this.getAgent().send(sentenceEvent, "ChunkGeneration");
			
		} catch (NoValidEventException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoValidTargetTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}