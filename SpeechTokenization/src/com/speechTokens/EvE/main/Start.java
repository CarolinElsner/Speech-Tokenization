package com.speechTokens.EvE.main;

import com.speechTokens.EvE.agents.CalendarAgent;
import com.speechTokens.EvE.agents.NoKeywordAgent;
import com.speechTokens.EvE.agents.SentenceAgent;
import com.speechTokens.EvE.agents.SeveralKeywordsAgent;
import com.speechTokens.EvE.agents.SingleKeywordAgent;
import com.speechTokens.EvE.agents.TokenizeAgent;

import eventprocessing.agent.AbstractAgent;
import eventprocessing.consume.kafka.ConsumerSettings;
import eventprocessing.consume.spark.streaming.NoValidAgentException;
import eventprocessing.consume.spark.streaming.StreamingExecution;
import eventprocessing.produce.kafka.Despatcher;
import eventprocessing.produce.kafka.ProducerSettings;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
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
		AbstractAgent calendarAgent = new CalendarAgent();
		AbstractAgent noKeywordAgent = new NoKeywordAgent();
		AbstractAgent severalKeywordsAgent = new SeveralKeywordsAgent();
		AbstractAgent singleKeywordAgent = new SingleKeywordAgent();
		AbstractAgent tokenizeAgent = new TokenizeAgent();
		
		
		sentenceAgent.setConsumerSettings(new ConsumerSettings(server, port, "sessionAgent"));
		sentenceAgent.setProducerSettings(new ProducerSettings(server,port));
		calendarAgent.setConsumerSettings(new ConsumerSettings(server, port, "calendarAgent"));
		calendarAgent.setProducerSettings(new ProducerSettings(server,port));
		noKeywordAgent.setConsumerSettings(new ConsumerSettings(server, port, "noKeywordAgent"));
		noKeywordAgent.setProducerSettings(new ProducerSettings(server,port));
		severalKeywordsAgent.setConsumerSettings(new ConsumerSettings(server, port, "severalKeywordsAgent"));
		severalKeywordsAgent.setProducerSettings(new ProducerSettings(server,port));
		singleKeywordAgent.setConsumerSettings(new ConsumerSettings(server, port, "singleKeywordAgent"));
		singleKeywordAgent.setProducerSettings(new ProducerSettings(server,port));
		tokenizeAgent.setConsumerSettings(new ConsumerSettings(server, port, "tokenizeAgent"));
		tokenizeAgent.setProducerSettings(new ProducerSettings(server,port));

		StreamingExecution.add(sentenceAgent);
		StreamingExecution.add(calendarAgent);
		StreamingExecution.add(noKeywordAgent);
		StreamingExecution.add(severalKeywordsAgent);
		StreamingExecution.add(singleKeywordAgent);
		StreamingExecution.add(tokenizeAgent);
		StreamingExecution.start();
	}				
}