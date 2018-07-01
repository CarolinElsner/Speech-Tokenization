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

public class CalendarInterestProfile extends AbstractInterestProfile {

	private static final long serialVersionUID = 1L;
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	Logger l = LoggerFactory.getLogger("SessionState");

	
	@Override
	protected void doOnReceive(AbstractEvent event) {
		// TODO Auto-generated method stub
		
				
		AbstractEvent calendarevent = eventFactory.createEvent("AtomicEvent");
		calendarevent.setType("CalendarEvent");
		//Besitzt event nur eine UserID??
		calendarevent.add(new Property<>("UserID", EventUtils.findPropertyByKey(event, "UserID")));
		calendarevent.add(new Property<>("Timestamp", EventUtils.findPropertyByKey(event, "Timestamp")));
		calendarevent.add(new Property<>("SessionID", EventUtils.findPropertyByKey(event, "SessionID")));
		calendarevent.add(new Property<>("SentenceID", EventUtils.findPropertyByKey(event, "SentenceID")));
	
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
	

}
