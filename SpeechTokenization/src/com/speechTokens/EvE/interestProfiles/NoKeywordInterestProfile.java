package com.speechTokens.EvE.interestProfiles;

import java.util.logging.Logger;

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

public class NoKeywordInterestProfile extends AbstractInterestProfile {

	private static final long serialVersionUID = 1L;
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	Logger l = LoggerFactory.getLogger("SessionState");
	
	
	@Override
	protected void doOnReceive(AbstractEvent event) {
		
		//beispielvariable zur unterscheidung
		String type = "document";
		
		if(type.contentEquals("document")) {
			
			AbstractEvent documentevent = eventFactory.createEvent("AtomicEvent");
			
			documentevent.setType("DocumentEvent");
			//Besitzt event nur eine UserID??
			documentevent.add(new Property<>("UserID", EventUtils.findPropertyByKey(event, "UserID")));
			documentevent.add(new Property<>("Timestamp", EventUtils.findPropertyByKey(event, "Timestamp")));
			documentevent.add(new Property<>("SessionID", EventUtils.findPropertyByKey(event, "SessionID")));
			documentevent.add(new Property<>("SentenceID", EventUtils.findPropertyByKey(event, "SentenceID")));
			documentevent.add(new Property<>("Chunks", EventUtils.findPropertyByKey(event, "Chunks")));
			
			//documentevent publishen		
			try {
				this.getAgent().send(documentevent, "TokenGeneration");
			} catch (NoValidEventException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoValidTargetTopicException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
		}else if (type.contentEquals("project")) {
			
			AbstractEvent projectevent = eventFactory.createEvent("AtomicEvent");
			
			projectevent.setType("ProjectEvent");
			//Besitzt event nur eine UserID??
			projectevent.add(new Property<>("UserID", EventUtils.findPropertyByKey(event, "UserID")));
			projectevent.add(new Property<>("Timestamp", EventUtils.findPropertyByKey(event, "Timestamp")));
			projectevent.add(new Property<>("SessionID", EventUtils.findPropertyByKey(event, "SessionID")));
			projectevent.add(new Property<>("SentenceID", EventUtils.findPropertyByKey(event, "SentenceID")));
			projectevent.add(new Property<>("Chunks", EventUtils.findPropertyByKey(event, "Chunks")));
			
			//projectevent publishen
			try {
				this.getAgent().send(projectevent, "TokenGeneration");
			} catch (NoValidEventException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoValidTargetTopicException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
			
		}else if (type.contentEquals("contact")) {
			
			AbstractEvent contactevent = eventFactory.createEvent("AtomicEvent");
			
			contactevent.setType("ProjectEvent");
			//Besitzt event nur eine UserID??
			contactevent.add(new Property<>("UserID", EventUtils.findPropertyByKey(event, "UserID")));
			contactevent.add(new Property<>("Timestamp", EventUtils.findPropertyByKey(event, "Timestamp")));
			contactevent.add(new Property<>("SessionID", EventUtils.findPropertyByKey(event, "SessionID")));
			contactevent.add(new Property<>("SentenceID", EventUtils.findPropertyByKey(event, "SentenceID")));
			contactevent.add(new Property<>("Chunks", EventUtils.findPropertyByKey(event, "Chunks")));
			
			//contactevent publishen
			try {
				this.getAgent().send(contactevent, "TokenGeneration");
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

