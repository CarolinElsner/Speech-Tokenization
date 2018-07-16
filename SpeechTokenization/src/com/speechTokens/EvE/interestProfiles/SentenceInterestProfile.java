package com.speechTokens.EvE.interestProfiles;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.speechTokens.tokenizer.Chunker;
import com.speechTokens.tokenizer.DetectApplication;
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
 * "Fachwissen" die Entscheidung, ob eine GeschwindigkeitsÃ¼berschreitung
 * stattgefunden hat oder nicht. In beiden FÃ¤llen wird ein
 * <code>ComplexEvent</code> erzeugt und auf ein separates Topic weitergeleitet
 * 
 * @author IngoT
 *
 */
public class SentenceInterestProfile extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6623427848645226550L;
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
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
		//Variable um zu prüfen ob Datum in Satz enthalten ist
		boolean foundDate;
		boolean foundDayMonth = DetectTermin.dayMonthfound;
		
		ArrayList<String> chunks = new ArrayList<String>();
		try {
			chunks = (ArrayList<String>) chunking.doTokenization(sentence);
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
		//Zu erst pürfen ob ein exaktes Datum angegeben wurde
		DetectTermin detector = new DetectTermin();
		foundDate = detector.validate(sentence);
		ArrayList<String> chunkscleaned = detector.searchDate(chunks,sentence);
		
		//Wenn Datum gefunden, dann Kalenderevent
		
		if(foundDayMonth == true) {
			
			AbstractEvent calendarevent = eventFactory.createEvent("AtomicEvent");
			calendarevent.setType("CalendarEvent");
			//Besitzt event nur eine UserID??
			calendarevent.add(new Property<>("ApplicationType","calendar"));
			calendarevent.add(new Property<>("UserID", EventUtils.findPropertyByKey(event, "UserID").getValue()));
			calendarevent.add(new Property<>("SessionID", EventUtils.findPropertyByKey(event, "SessionID").getValue()));
			//calendarevent.add(new Property<>("SentenceID", EventUtils.findPropertyByKey(event, "SentenceID")));
			
			try {
				this.getAgent().send(calendarevent, "TokenGeneration");
			} catch (NoValidEventException e) {
			
				e.printStackTrace();
			} catch (NoValidTargetTopicException e) {
				
				e.printStackTrace();
			}
			
			
		}else if(foundDate == true || detector.datefound == true) { //Falls Datum mit angegebene sein sollte
			
			AbstractEvent calendarevent = eventFactory.createEvent("AtomicEvent");
			calendarevent.setType("CalendarEvent");
			//Besitzt event nur eine UserID??
			calendarevent.add(new Property<>("ApplicationType","calendar"));
			calendarevent.add(new Property<>("UserID", EventUtils.findPropertyByKey(event, "UserID").getValue()));
			calendarevent.add(new Property<>("SessionID", EventUtils.findPropertyByKey(event, "SessionID").getValue()));
			//calendarevent.add(new Property<>("SentenceID", EventUtils.findPropertyByKey(event, "SentenceID")));
			calendarevent.add(new Property<>("Termin", detector.getfoundDate()));
			
			try {
				this.getAgent().send(calendarevent, "TokenGeneration");
			} catch (NoValidEventException e) {
				
				e.printStackTrace();
			} catch (NoValidTargetTopicException e) {
				
				e.printStackTrace();
			}
		}
		//Chunker befüllen und alle Chunks in Kleinbuchstaben
		Chunker chunk = new Chunker();
		for (int i=0; i<chunkscleaned.size();i++) {
			chunk.addChunkContent(chunkscleaned.get(i).toLowerCase());
		}
		
		detector.deleteAllDates();
		
		//Detecting Application Keywords and publishing ApplicationEvent
			
		DetectApplication appdetection = new DetectApplication();
		
		ArrayList<String> foundapps = appdetection.detection(sentence);
		
		if(foundapps.size()>0) {
			for(int i = 0; i < foundapps.size(); i++) {
				
								
				AbstractEvent applicationEvent = eventFactory.createEvent("AtomicEvent");
				applicationEvent.setType("ApplicationEvent");
				applicationEvent.add(new Property<>("ApplicationType",foundapps.get(i)));
				applicationEvent.add(new Property<>("UserID",EventUtils.findPropertyByKey(event, "UserID").getValue()));
				applicationEvent.add(new Property<>("SessionID",EventUtils.findPropertyByKey(event, "SessionID").getValue()));
				//applicationEvent.add(new Property<>("SentenceID", EventUtils.findPropertyByKey(event, "SentenceID")));

				
				try {
					this.getAgent().send(applicationEvent, "TokenGeneration");
					
				} catch (NoValidEventException e) {
					e.printStackTrace();
				} catch (NoValidTargetTopicException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		//SentenceEvent________________________________
		
		if(chunk.size()!=0) {
			AbstractEvent sentenceEvent = eventFactory.createEvent("AtomicEvent");
			sentenceEvent.setType("SentenceEvent");
			sentenceEvent.add(new Property<>("UserID",EventUtils.findPropertyByKey(event, "UserID").getValue()));
			sentenceEvent.add(new Property<>("SessionID",EventUtils.findPropertyByKey(event, "SessionID").getValue()));
			//sentenceEvent.add(new Property<>("SentenceID", EventUtils.findPropertyByKey(event, "SentenceID")));

			sentenceEvent.add(new Property<>("Chunks", chunk.returnList()));// the chunker Object cant be pushed as it is and has to be parsed to the ArrayList
	
			try {
				this.getAgent().send(sentenceEvent, "ChunkGeneration");
				
			} catch (NoValidEventException e) {
				
				e.printStackTrace();
			} catch (NoValidTargetTopicException e) {
				
				e.printStackTrace();
			}
		}
	}
}