package com.speechTokens.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.speechTokens.tokenizer.Tokenization;


/**
 * Servlet implementation class ServletInterface
 */
@WebServlet(description = "Interface between Speech Recognition (JavaScript) and Tokenization (Java)", urlPatterns = { "/servletInterface" })
public class ServletInterface extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletInterface() {
        //super();
        // TODO Auto-generated constructor stub
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String JsSentence = request.getParameter("param"); // receive Parameter from GET Request, where the spoken text is stored
		// To execute the other class and its dependencies it is important to add these dependencies under "Deployment Assembly"
		Tokenization.doTokenization(JsSentence);				// forward recorded sentence to the Tokenization Java Class
		System.out.println(request.getParameter("param"));
	}

}
