package com.speechTokens.EvE.agents;

import com.speechTokens.EvE.interestProfiles.CalendarInterestProfile;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;

public class CalendarAgent extends AbstractAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1681020941417483524L;

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
