package com.speechTokens.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eventprocessing.event.AbstractEvent;
import eventprocessing.event.Property;
import eventprocessing.produce.kafka.Despatcher;
import eventprocessing.produce.kafka.ProducerSettings;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.mapping.MessageMapper;
import eventprocessing.utils.model.EventUtils;

/**
 * Servlet implementation class ServletInterface
 */
@WebServlet(description = "Interface between Speech Recognition (JavaScript) and Tokenization (Java)", urlPatterns = { "/servletInterface" })
public class ServletInterface extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static AbstractFactory eventFactory = FactoryProducer.getFactory(FactoryValues.INSTANCE.getEventFactory());
	private static final MessageMapper messageMapper = new MessageMapper();


	//TODO: Klassen können noch nicht auf das EvE Framework zugreifen, wenn kein Kafka + Spark läuft
	//private static final Despatcher despatcher = new Despatcher(ProducerSettings.INSTANCE);
	// wandelt die Events in Nachrichten um.
	//private static final MessageMapper messageMapper = new MessageMapper();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletInterface() {
        //super();
    }
    
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String server= "10.142.0.2";
		String port ="9092";
		Despatcher despatcher = new Despatcher(new ProducerSettings(server,port));
		
		String JsSentence = request.getParameter("sentence"); // receive Parameter from GET Request, where the spoken text is stored
		String userID = request.getParameter("userID");
		String timestamp = request.getParameter("timestamp");
		String sessionID = request.getParameter("sessionID");
		String sentenceID = request.getParameter("sentenceID");
		// To execute the other class and its dependencies it is important to add these dependencies under "Deployment Assembly"
		System.out.println("Recorded Sentence: "+JsSentence);		
		
		AbstractEvent watsonEvent = eventFactory.createEvent("AtomicEvent");
		watsonEvent.setType("watsonEvent");
		watsonEvent.add(new Property<>("UserID",userID));
		watsonEvent.add(new Property<>("Timestamp",timestamp));
		watsonEvent.add(new Property<>("SessionID", sessionID));
		watsonEvent.add(new Property<>("Sentence", JsSentence));
		watsonEvent.add(new Property<>("SentenceID", sentenceID));
		String message = messageMapper.toJSON(watsonEvent);
		despatcher.deliver(message, "ChunkGeneration");
	}

}
