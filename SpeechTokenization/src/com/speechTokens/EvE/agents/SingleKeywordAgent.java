package com.speechTokens.EvE.agents;

import com.speechTokens.EvE.interestProfiles.SingleKeywordIP;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;

/**
 * Dieser Agent ist f�r die Diagnose des Verkehrs zust�ndig. Er wertet die
 * Verkehrsdaten aus und leitet daraus entsprechende Handlungen ab.
 * 
 * @author IngoT
 *
 */
public class SingleKeywordAgent extends AbstractAgent {



	/**
	 * 
	 */
	private static final long serialVersionUID = -5178589982589857056L;

	@Override
	protected void doOnInit() {
		
		this.setId("SingleKeywordAgent");
		
		try {
			AbstractInterestProfile ip = new SingleKeywordIP();
			ip.add(new IsEventType("SingleKeywordEvent")); // Da es der erste Agent in der Prozesskette ist abonniert er keine Events
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