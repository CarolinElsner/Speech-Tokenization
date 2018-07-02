package com.speechTokens.EvE.agents;

import com.speechTokens.EvE.interestProfiles.NoKeywordIP;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;

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
			
			e1.printStackTrace();
		}
		try {
			
			this.add("Keywords");	
		} catch (NoValidConsumingTopicException e) {			
			e.printStackTrace();
		}
		
	}		

}