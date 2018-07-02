package com.speechTokens.EvE.interestProfiles;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.speechTokens.EvE.events.EventCreationHelper;
import com.speechTokens.semantic.analysis.KeywordSearch;
import com.speechTokens.tokenizer.Chunker;

import eventprocessing.agent.NoValidEventException;
import eventprocessing.agent.NoValidTargetTopicException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.model.EventUtils;


/**
 * Trifft anhand der Sensoren, Durchschnittsgeschwindigkeit sowie mit
 * "Fachwissen" die Entscheidung, ob eine Geschwindigkeitsüberschreitung
 * stattgefunden hat oder nicht. In beiden Fällen wird ein
 * <code>ComplexEvent</code> erzeugt und auf ein separates Topic weitergeleitet
 * 
 * @author IngoT
 *
 */
public class NoKeywordIP extends AbstractInterestProfile {

	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static final long serialVersionUID = -6108185466150892913L;
	private static Logger LOGGER = LoggerFactory.getLogger(NoKeywordIP.class);

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {

				
		Chunker chunks = (Chunker) EventUtils.findPropertyByKey(event, "Chunks").getValue();

		Chunker semFoundChunks = KeywordSearch.noKeyword(chunks);
		
		for (int i = 0; i < semFoundChunks.size(); i++) { // iterate through all the chunks that 
			Object semantic = semFoundChunks.getSemanticAt(i);
			if(semantic instanceof ArrayList<?>) {
				ArrayList<?> newSemantic = (ArrayList<?>) semantic; 
				if(newSemantic.size()>1) {// more than one sem data off the respective chunk, so we dont know which type
					Chunker tempChunker = new Chunker();
					String currChunk = semFoundChunks.getChunkContentAt(i);
					tempChunker.addChunkContent(currChunk);
					tempChunker.addSemanticToChunk(currChunk, tempChunker.readSemanticOfChunk(currChunk));
					AbstractEvent uncertainEvent = eventFactory.createEvent("AtomicEvent");
					uncertainEvent.setType("UncertainEvent");
					uncertainEvent.add(new Property<>("UserID",EventUtils.findPropertyByKey(event, "UserID")));
					uncertainEvent.add(new Property<>("Timestamp",EventUtils.findPropertyByKey(event, "Timestamp")));
					uncertainEvent.add(new Property<>("SessionID",EventUtils.findPropertyByKey(event, "SessionID")));
					uncertainEvent.add(new Property<>("Chunks", tempChunker));
					try {
						this.getAgent().send(uncertainEvent, "TokenGeneration");
						
					} catch (NoValidEventException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoValidTargetTopicException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else { // just one semantic entry was found for the chunk
					// Hier geht es um ein Event, die eine spezifische Aktion erfordert
					String chunk = semFoundChunks.getChunkContentAt(i);
					Object sem = semFoundChunks.getSemanticAt(i);
					Chunker tokenChunker = new Chunker();
					tokenChunker.addChunkContent(chunk);
					tokenChunker.addSemanticToChunk(chunk, sem);
					AbstractEvent actionEvent = eventFactory.createEvent("AtomicEvent");
					actionEvent = EventCreationHelper.createEvent(semFoundChunks, actionEvent, event);
					try {
						this.getAgent().send(actionEvent, "Action");
						
					} catch (NoValidEventException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoValidTargetTopicException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}