package com.speechTokens.EvE.agents;

import com.speechTokens.EvE.interestProfiles.SentenceInterestProfile;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.exceptions.NoValidConsumingTopicException;
import eventprocessing.demo.ShowcaseValues;
import eventprocessing.dispatch.NoValidInterestProfileException;
import eventprocessing.input.kafka.ConsumerSettings;
import eventprocessing.interestprofile.AbstractInterestProfile;
import eventprocessing.interestprofile.predicates.statement.IsEventType;

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
		try {
			AbstractInterestProfile ip = new SentenceInterestProfile();
			ip.add(new IsEventType("WatsonEvent")); // Da es der erste Agent in der Prozesskette ist abonniert er keine Events
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
		try {
			this.add("SessionStart"); // Topic
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}
		
		this.setConsumerSettings(new ConsumerSettings(ShowcaseValues.INSTANCE.getIpKafka(),
				ShowcaseValues.INSTANCE.getPortKafka(), ShowcaseValues.INSTANCE.getGroupIdDiagnosis()));

	}
}
