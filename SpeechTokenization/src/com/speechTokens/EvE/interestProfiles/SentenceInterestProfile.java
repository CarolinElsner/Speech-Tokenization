package com.speechTokens.EvE.interestProfiles;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.speechTokens.EvE.events.SentenceEvent;
import com.speechTokens.EvE.events.WatsonEvent;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.factory.LoggerFactory;

/**
 * Trifft anhand der Sensoren, Durchschnittsgeschwindigkeit sowie mit
 * "Fachwissen" die Entscheidung, ob eine GeschwindigkeitsÃ¼berschreitung
 * stattgefunden hat oder nicht. In beiden FÃ¤llen wird ein
 * <code>ComplexEvent</code> erzeugt und auf ein separates Topic weitergeleitet
 * 
 * @author IngoT
 *
 */
public class SentenceInterestProfile extends AbstractInterestProfile {

	private static final long serialVersionUID = -6108185466150892913L;
	private static Logger LOGGER = LoggerFactory.getLogger(SentenceInterestProfile.class);
	// Factory fÃ¼r die Erzeugung der Events
	private List<String> chunkList;

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {
		
		List<List> test= null;
		List<Object> sublist = null;
		sublist.add("chunk");
		sublist.add("semantic2");
		test.add(sublist);
		
		SentenceEvent e = new SentenceEvent(); // geht das auch so oder muss ich es wie in der Zeile drüber machen
		// Pruefe ob das empfangene Event vom Typ WatsonEvent ist
		if (event instanceof WatsonEvent) {
			// TODO: PROBLEM: Es ist ungünsting wenn jedes mal an das neue Event die 3 standarddaten übergeben werdne müssen
			WatsonEvent watsonEvent = (WatsonEvent) event;
			// Alle benoetigten Informationen werden aus dem Event entnommen
			e.add(new Property("UserID", "watsonEvent.userID"));// Hier die Properties an das neue Event übergebenübergeben
			e.add(new Property("Timestamp", "watsonEvent.timestamp"));
			e.add(new Property("SessionID", "watsonEvent.sessionID"));
			List<?> list = watsonEvent.getProperties();
			/*
			for (int j = 0; j < list.size(); j++) {
				if(list.get(j).getKey().equals("sentence")) {
					try {
						chunkList = Tokenization.doTokenization(list.get(j).getValue());
						//TODO: PROBLEM: Wir haben hier eine chunk list und können sie nicht als Property hinzugügen
						e.add(new Property("chunks", "chunklist"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}*/
		}

		try {
			getAgent().send(e, "Chunks");
		} catch (NoValidEventException e1) {
			LOGGER.log(Level.WARNING, () -> String.format("%s", e));
		} catch (NoValidTargetTopicException e1) {
			LOGGER.log(Level.WARNING, () -> String.format("%s", e));
		}
	}
}
