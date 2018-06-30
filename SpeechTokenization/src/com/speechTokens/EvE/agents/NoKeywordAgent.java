package com.speechTokens.EvE.agents;

import java.util.ArrayList;

import com.speechTokens.EvE.interestProfiles.TokenizeInterestProfile;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.NullPredicateException;
import eventprocessing.agent.interestprofile.predicates.logical.Or;
import eventprocessing.agent.interestprofile.predicates.statement.GetEverything;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.event.AbstractEvent;

public class NoKeywordAgent extends AbstractAgent {

	@Override
	protected void doOnInit() {
		
		this.setId("NoKeywordAgent");
		
		AbstractInterestProfile nokeywordIP = new TokenizeInterestProfile();		
		
		/*######
		Hier Event definieren, das von der Dokumentenrepräsenation kommt
		Beispielhaft "FeedbackEvent"
		######*/
		nokeywordIP.add(new IsEventType("NoKeywordEvent"));
		
		try {
			//Topic angeben --> Dokumentenrepresentation
			
			this.add("Keywords");	
		} catch (NoValidConsumingTopicException e) {			
			e.printStackTrace();
		}
		
	}		

}

