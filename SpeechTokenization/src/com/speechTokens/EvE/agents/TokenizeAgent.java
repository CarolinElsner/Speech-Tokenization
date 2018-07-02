package com.speechTokens.EvE.agents;

import com.speechTokens.EvE.interestProfiles.TokenizeInterestProfile;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;

public class TokenizeAgent extends AbstractAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6013105775118845557L;

	@Override
	protected void doOnInit() {
		
		this.setId("TokenizeAgent");
				
		try {
			AbstractInterestProfile tokenizeIP = new TokenizeInterestProfile();		
			tokenizeIP.add(new IsEventType("FeedbackEvent"));
			this.add(tokenizeIP);
		} catch (NoValidInterestProfileException e1) {
		
			e1.printStackTrace();
		}
		try {
			//Topic angeben --> Dokumentenrepresentation
			
			this.add("SemanticChunks");	
		} catch (NoValidConsumingTopicException e) {			
			e.printStackTrace();
		}
		
	}		

}

