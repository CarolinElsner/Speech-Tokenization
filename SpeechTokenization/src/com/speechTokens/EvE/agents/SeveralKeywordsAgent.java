package com.speechTokens.EvE.agents;

import com.speechTokens.EvE.interestProfiles.SeveralKeywordsIP;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;

/**
 * Dieser Agent ist für die Diagnose des Verkehrs zuständig. Er wertet die
 * Verkehrsdaten aus und leitet daraus entsprechende Handlungen ab.
 * 
 * @author IngoT
 *
 */
public class SeveralKeywordsAgent extends AbstractAgent {




	/**
	 * 
	 */
	private static final long serialVersionUID = 8624939079782101956L;

	@Override
	protected void doOnInit() {
		
		this.setId("SeveralKeywordsAgent");
		
		try {
			AbstractInterestProfile ip = new SeveralKeywordsIP();
			ip.add(new IsEventType("SeveralKeywordsEvent")); // Da es der erste Agent in der Prozesskette ist abonniert er keine Events
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
		try {
			this.add("Keywords"); // Topic
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}
		// TODO: Consumer Settings in der Hauptdatei verwalten, an Vorlage der Eventgruppe orientieren
	}
}