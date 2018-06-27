package com.speechTokens.EvE.agents;

import com.speechTokens.EvE.interestProfiles.TokenInterestProfile;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.demo.ShowcaseValues;

/**
 * Dieser Agent ist für die Diagnose des Verkehrs zuständig. Er wertet die
 * Verkehrsdaten aus und leitet daraus entsprechende Handlungen ab.
 * 
 * @author IngoT
 *
 */
public class TokenAgent extends AbstractAgent {

	 
	private static final long serialVersionUID = -1214551597582602455L;

	@Override
	protected void doOnInit() {
		/*
		 * Fügt dem Agenten ein InteressenProfil hinzu Ein Agent kann mehrere
		 * InteressenProfile besitzen
		 */
		try {
			AbstractInterestProfile ip = new TokenInterestProfile();
			ip.add(new IsEventType("SentenceFeedbackEvent")); // Da es der erste Agent in der Prozesskette ist abonniert er keine Events
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
		try {
			this.add("SemanticChunks");
		} catch (NoValidConsumingTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO: Consumer Settings in der Hauptdatei verwalten, an Vorlage der Eventgruppe orientieren
	}
}
