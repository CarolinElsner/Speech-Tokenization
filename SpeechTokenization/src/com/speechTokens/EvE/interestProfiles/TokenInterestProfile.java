package com.speechTokens.EvE.interestProfiles;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.speechTokens.EvE.events.SentenceEvent;
import com.speechTokens.EvE.events.TokenEvent;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.factory.LoggerFactory;

/**
 * Trifft anhand der Sensoren, Durchschnittsgeschwindigkeit sowie mit
 * "Fachwissen" die Entscheidung, ob eine Geschwindigkeitsüberschreitung
 * stattgefunden hat oder nicht. In beiden Fällen wird ein
 * <code>ComplexEvent</code> erzeugt und auf ein separates Topic weitergeleitet
 * 
 * @author IngoT
 *
 */
public class TokenInterestProfile extends AbstractInterestProfile {
	/**
	 * 
	 */
	private static final long serialVersionUID = -194575241513454551L;
	private static Logger LOGGER = LoggerFactory.getLogger(TokenInterestProfile.class);
	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {
		TokenEvent e = new TokenEvent();
		if (event instanceof SentenceEvent) {
			SentenceEvent sentenceEvent = (SentenceEvent) event;
			
			List<?> list = sentenceEvent.getProperties();
			/*
			for (int j = 0; j < list.size(); j++) {
				if(list.get(j).getKey().equals("chunks")) {
					list.get(j).getValue(); // hier kommt die chunklist mit den semantischen informationen heraus
					// TODO: eigene Logik, die aus den semantischen Infos zusammenh�nge erstellt

					
					e.add(new Property("chunks", "�berarbeitete chunklist"));
				}
			}*/
		}

		try {
			getAgent().send(e, "TokenGeneration");
		} catch (NoValidEventException e1) {
			LOGGER.log(Level.WARNING, () -> String.format("%s", e));
		} catch (NoValidTargetTopicException e1) {
			LOGGER.log(Level.WARNING, () -> String.format("%s", e));
		}
	}
}
