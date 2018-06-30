package com.speechTokens.EvE.agents;

<<<<<<< HEAD
import com.speechTokens.EvE.interestProfiles.SentenceInterestProfile;
=======
import java.util.ArrayList;

import com.speechTokens.EvE.interestProfiles.TokenizeInterestProfile;
>>>>>>> ae892f5f527a0d2c17bb16c1ae6b725f264dfe01

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
<<<<<<< HEAD
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
public class NoKeywordAgent extends AbstractAgent {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4224913594653783860L;

=======
import eventprocessing.agent.interestprofile.predicates.NullPredicateException;
import eventprocessing.agent.interestprofile.predicates.logical.Or;
import eventprocessing.agent.interestprofile.predicates.statement.GetEverything;
import eventprocessing.agent.interestprofile.predicates.statement.IsEventType;
import eventprocessing.event.AbstractEvent;

public class NoKeywordAgent extends AbstractAgent {

>>>>>>> ae892f5f527a0d2c17bb16c1ae6b725f264dfe01
	@Override
	protected void doOnInit() {
		
		this.setId("NoKeywordAgent");
		
<<<<<<< HEAD
		try {
			AbstractInterestProfile ip = new SentenceInterestProfile();
			ip.add(new IsEventType("NoKeywordEvent")); // Da es der erste Agent in der Prozesskette ist abonniert er keine Events
			this.add(ip);
		} catch (NoValidInterestProfileException e1) {
			e1.printStackTrace();
		}
		
		try {
			this.add("TokenGeneration"); // Topic
		} catch (NoValidConsumingTopicException e) {
			e.printStackTrace();
		}
		// TODO: Consumer Settings in der Hauptdatei verwalten, an Vorlage der Eventgruppe orientieren
	}
}
=======
		AbstractInterestProfile nokeywordIP = new TokenizeInterestProfile();		
		
		/*######
		Hier Event definieren, das von der DokumentenreprÃ¤senation kommt
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

>>>>>>> ae892f5f527a0d2c17bb16c1ae6b725f264dfe01
