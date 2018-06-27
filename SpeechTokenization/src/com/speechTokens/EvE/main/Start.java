package com.speechTokens.EvE.main;

import java.util.logging.Level;

import com.speechTokens.EvE.agents.SentenceAgent;
import com.speechTokens.EvE.agents.TokenAgent;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.agent.NoValidConsumingTopicException;
import eventprocessing.agent.dispatch.NoValidInterestProfileException;
import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.agent.interestprofile.predicates.statement.GetEverything;
import eventprocessing.agent.interestprofile.predicates.statement.IsFromTopic;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.consume.spark.streaming.NoValidAgentException;
import eventprocessing.consume.spark.streaming.StreamingExecution;
import eventprocessing.event.AbstractEvent;
import eventprocessing.event.AtomicEvent;
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
		String server= "10.142.0.2";
		String port ="9092";
		despatcher = new Despatcher(new ProducerSettings(server,port));
		AbstractAgent sentenceAgent = new SentenceAgent();
		AbstractAgent tokenAgent = new TokenAgent();
		
		sentenceAgent.setConsumerSettings(new ConsumerSettings(server, port, "sessionAgent"));
		sentenceAgent.setProducerSettings(new ProducerSettings(server,port));
		tokenAgent.setConsumerSettings(new ConsumerSettings(server, port, "tokenAgent"));
		tokenAgent.setProducerSettings(new ProducerSettings(server,port));

		StreamingExecution.add(sentenceAgent);
		StreamingExecution.add(tokenAgent);
		StreamingExecution.start();
	}				
}