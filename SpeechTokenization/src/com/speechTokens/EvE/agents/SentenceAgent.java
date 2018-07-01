package com.speechTokens.EvE.agents;

import com.speechTokens.EvE.interestProfiles.SentenceInterestProfile;

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
public class SentenceAgent extends AbstractAgent {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4224913594653783860L;

	@Override
	protected void doOnInit() {
		
		this.setId("SentenceAgend");
		
		try {
			AbstractInterestProfile ip = new SentenceInterestProfile();
			ip.add(new IsEventType("WatsonEvent")); // Da es der erste Agent in der Prozesskette ist abonniert er keine Events
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
		try {
			this.add("ChunkGeneration"); // Topic
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}
		// TODO: Consumer Settings in der Hauptdatei verwalten, an Vorlage der Eventgruppe orientieren
	}
}