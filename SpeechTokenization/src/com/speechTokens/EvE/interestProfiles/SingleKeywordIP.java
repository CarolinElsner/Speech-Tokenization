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
public class SingleKeywordIP extends AbstractInterestProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6922837623061511383L;
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static Logger LOGGER = LoggerFactory.getLogger(SingleKeywordIP.class);

	/**
	 * Verarbeitung des empfangenen Events.
	 * 
	 * @param event
	 */
	@Override
	public void doOnReceive(AbstractEvent event) {
		
		String keyword = EventUtils.findPropertyByKey(event, "Keywords").getValue().toString(); // is one String containing the Keyword that was found as a Chunk
		ArrayList<Object> chunkArrList = (ArrayList<Object>) EventUtils.findPropertyByKey(event, "Chunks").getValue();
		Chunker chunks = new Chunker();
		chunks.parseArrayList(chunkArrList);
		
		
		Chunker semFoundChunks = KeywordSearch.oneKeyword(keyword, chunks); // Chunker object with all the chunks where sem info regarding the Keyword was found
		for (int i = 0; i < semFoundChunks.size(); i++) { // A Token will just consist of one Chunk, with the sem Data, therefore iterate through the Chunks
			Object semantic = semFoundChunks.getSemanticAt(i);// get the sem info for the current chunk
			Chunker tempChunker = new Chunker(); // create a temporary chunker which consists of just one chunk
			String currChunk = semFoundChunks.getChunkContentAt(i);
			tempChunker.addChunkContent(currChunk);
			tempChunker.addSemanticToChunk(currChunk, semFoundChunks.readSemanticOfChunk(currChunk));
			if(semantic instanceof ArrayList<?>) {
				ArrayList<String> newSemantic = (ArrayList<String>) semantic; 
				if(newSemantic.size()>1) { // if the chunk as more than one semantic info
					AbstractEvent uncertainEvent = eventFactory.createEvent("AtomicEvent");
					uncertainEvent = EventCreationHelper.createEvent(tempChunker, uncertainEvent, event);

					try {
						this.getAgent().send(uncertainEvent, "TokenGeneration");
						
					} catch (NoValidEventException e) {
						
						e.printStackTrace();
					} catch (NoValidTargetTopicException e) {
					
						e.printStackTrace();
					}
				}else { // the chunk has exactly one sem information
					AbstractEvent actionEvent = eventFactory.createEvent("AtomicEvent");
					actionEvent = EventCreationHelper.createEvent(tempChunker, actionEvent, event);
					try {
						this.getAgent().send(actionEvent, "TokenGeneration");
						
					} catch (NoValidEventException e) {
						
						e.printStackTrace();
					} catch (NoValidTargetTopicException e) {
						
						e.printStackTrace();
					}
				}
			}else {
				System.out.println("SingleKeywordIP.doOnReceive: Wrong Instanceof semantic Array");
			}
		}
	}
}