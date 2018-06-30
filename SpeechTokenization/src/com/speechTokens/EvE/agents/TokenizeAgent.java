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

public class TokenizeAgent extends AbstractAgent {

	@Override
	protected void doOnInit() {
		
		this.setId("TokenizeAgent");
				
		try {
			AbstractInterestProfile tokenizeIP = new TokenizeInterestProfile();		
			tokenizeIP.add(new IsEventType("FeedbackEvent"));
			this.add(tokenizeIP);
		} catch (NoValidInterestProfileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			//Topic angeben --> Dokumentenrepresentation
			
			this.add("TokenGeneration");	
		} catch (NoValidConsumingTopicException e) {			
			e.printStackTrace();
		}
		
	}		

}

