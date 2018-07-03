package com.speechTokens.EvE.main;

import com.speechTokens.EvE.agents.NoKeywordAgent;
import com.speechTokens.EvE.agents.SentenceAgent;
import com.speechTokens.EvE.agents.SeveralKeywordsAgent;
import com.speechTokens.EvE.agents.SingleKeywordAgent;
import com.speechTokens.EvE.agents.TokenizeAgent;
import com.speechTokens.testing.Testing;
import com.speechTokens.tokenizer.Chunker;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.consume.spark.streaming.NoValidAgentException;
import eventprocessing.consume.spark.streaming.StreamingExecution;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.produce.kafka.Despatcher;
import eventprocessing.produce.kafka.ProducerSettings;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import eventprocessing.utils.mapping.MessageMapper;

/**
 * Startpunkt der Anwendung.
 * 
 * hier werden die Agenten initialisiert und die Sparkumgebung ausgefÃ¼hrt.
 * 
 * @author RobertRapp
 *
 */
public class Start {


		
		 // FÃ¼r die Versendung der DemoEvents an das Topic nötig.
	private static Despatcher despatcher = null;
	// wandelt die Events in Nachrichten um.
	private static final MessageMapper messageMapper = new MessageMapper();
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static AbstractFactory agentFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getAgentFactory());
	
	
	public static void main(String[] args) throws NoValidAgentException, InterruptedException{
		String server= "localhost";
		String port ="9092";
		despatcher = new Despatcher(new ProducerSettings(server,port));
		AbstractAgent sentenceAgent = new SentenceAgent();
		AbstractAgent noKeywordAgent = new NoKeywordAgent();
		AbstractAgent severalKeywordsAgent = new SeveralKeywordsAgent();
		AbstractAgent singleKeywordAgent = new SingleKeywordAgent();
		AbstractAgent tokenizeAgent = new TokenizeAgent();
		
		
		sentenceAgent.setConsumerSettings(new ConsumerSettings(server, port, "SentenceAgent"));
		sentenceAgent.setProducerSettings(new ProducerSettings(server,port));
		noKeywordAgent.setConsumerSettings(new ConsumerSettings(server, port, "NoKeywordAgent"));
		noKeywordAgent.setProducerSettings(new ProducerSettings(server,port));
		severalKeywordsAgent.setConsumerSettings(new ConsumerSettings(server, port, "SeveralKeywordsAgent"));
		severalKeywordsAgent.setProducerSettings(new ProducerSettings(server,port));
		singleKeywordAgent.setConsumerSettings(new ConsumerSettings(server, port, "SingleKeywordAgent"));
		singleKeywordAgent.setProducerSettings(new ProducerSettings(server,port));
		tokenizeAgent.setConsumerSettings(new ConsumerSettings(server, port, "TokenizeAgent"));
		tokenizeAgent.setProducerSettings(new ProducerSettings(server,port));

		StreamingExecution.add(sentenceAgent);
		StreamingExecution.add(noKeywordAgent);
		StreamingExecution.add(severalKeywordsAgent);
		StreamingExecution.add(singleKeywordAgent);
		StreamingExecution.add(tokenizeAgent);
		StreamingExecution.start();
		
		publishDemoEvents();
		
		
	}	
	
	private static void publish(AbstractEvent event, String topic) {
		LoggerFactory.getLogger("StartServices!");				
		String message = messageMapper.toJSON(event);	
		if(message != null && topic != null) {
			despatcher.deliver(message, topic);	
		}
	}
	
	private static void publishDemoEvents() throws InterruptedException {		

				System.out.println(14);
				// TODO: A response can be added in the future which is caught in the JS Script and shown on the website
				// TODO: Parameter entsprechend im NodeJS anpassen
				Chunker chunks1= new Chunker();
				chunks1.addChunkContent("Hallo");
				chunks1.addChunkContent("Mond");
				chunks1.addSemanticToChunk("Hallo", Testing.jsonString1);
				chunks1.addSemanticToChunk("Mond", Testing.jsonString2);		
				String userID = "test";
		
				System.out.println(16);				
				String sessionID = "Session1";
				// To execute the other class and its dependencies it is important to add these dependencies under "Deployment Assembly"
				AbstractEvent wat = eventFactory.createEvent("AtomicEvent");
				System.out.println(17);
				wat.setType("NoKeywordEvent");
				wat.add(new Property<String>("UserID", userID));// Hier die Properties an das neue Event übergebenübergeben
				wat.add(new Property<String>("SentenceID", "5"));// Hier die Properties an das neue Event übergebenübergeben
				wat.add(new Property<String>("SessionID", sessionID));
				wat.add(new Property<>("Chunks",chunks1.returnList()));
				//String message = messageMapper.toJSON(wat);
				System.out.println(18);
				publish(wat, "Keywords");
				//despatcher.deliver(message, "ChunkGeneration");
				System.out.println(19);
				Thread.sleep(1000);
				

	}
}