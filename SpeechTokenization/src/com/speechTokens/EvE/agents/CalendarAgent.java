package com.speechTokens.EvE.agents;

import java.util.ArrayList;

import com.speechTokens.EvE.interestProfiles.CalendarInterestProfile;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.NullPredicateException;
import eventprocessing.agent.interestprofile.predicates.logical.Or;
import eventprocessing.agent.interestprofile.predicates.statement.GetEverything;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.event.AbstractEvent;

public class CalendarAgent extends AbstractAgent {

	@Override
	protected void doOnInit() {
		
		this.setId("CalendarAgent");
		
		AbstractInterestProfile calendarIP = new CalendarInterestProfile();		

		calendarIP.add(new IsEventType("CalendarEvent"));
		
		try {
			this.add("TokenGeneration");	
		} catch (NoValidConsumingTopicException e) {			
			e.printStackTrace();
		}
		
	}		

}
