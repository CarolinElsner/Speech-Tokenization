package com.speechTokens.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletInterface
 */
@WebServlet(description = "Interface between Speech Recognition (JavaScript) and Tokenization (Java)", urlPatterns = { "/servletInterface" })
public class ServletInterface extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
		   response.setContentType("text/plain");
		      // Actual logic goes here.
		   OutputStream stream = response.getOutputStream();
		   OutputStreamWriter writer = new OutputStreamWriter(stream, "UTF-8");
		   writer.write("Hallo Weelt");
		   writer.flush();
		   response.setCharacterEncoding("UTF-8");

		// TODO: A response can be added in the future which is caught in the JS Script and shown on the website
		// TODO: Parameter entsprechend im NodeJS anpassen
		String JsSentence = request.getParameter("sentence"); // receive Parameter from GET Request, where the spoken text is stored
		String userID = request.getParameter("userID");
		String timestamp = request.getParameter("timestamp");
		String sessionID = request.getParameter("sessionID");
		// To execute the other class and its dependencies it is important to add these dependencies under "Deployment Assembly"
		System.out.println(JsSentence);
		/*WatsonEvent wat = new WatsonEvent();
		wat.add(new eventprocessing.event.Property("sentence", JsSentence));
		wat.add(new eventprocessing.event.Property("UserID", userID));// Hier die Properties an das neue Event übergebenübergeben
		wat.add(new eventprocessing.event.Property("Timestamp", timestamp));
		wat.add(new eventprocessing.event.Property("SessionID", sessionID));
		String message = messageMapper.toJSON(wat);
		despatcher.deliver(message, "SessionStart");*/
	}

}
