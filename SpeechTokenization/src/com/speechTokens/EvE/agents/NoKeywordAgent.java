package com.speechTokens.EvE.agents;

import java.util.ArrayList;

import com.speechTokens.EvE.interestProfiles.NoKeywordIP;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 8636535785249602580L;

	@Override
	protected void doOnInit() {
		
		this.setId("NoKeywordAgent");
		try {
		AbstractInterestProfile nokeywordIP = new NoKeywordIP();		
		nokeywordIP.add(new IsEventType("NoKeywordEvent"));
			this.add(nokeywordIP);
		} catch (NoValidInterestProfileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			this.add("Keywords");	
		} catch (NoValidConsumingTopicException e) {			
			e.printStackTrace();
		}
		
	}		

}