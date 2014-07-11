package com.stome.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import com.ibm.cloudoe.service.StoreAndOrderLoader;
import com.stome.twitter.StomeTwitterServicesImpl;

/**
 * Servlet implementation class StomeServlet
 */
@WebServlet("/StomeServlet")
public class StomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StomeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("StomeServlet servlet initialied " );
		StoreAndOrderLoader.initialize(); 
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(new StomeTwitterServicesImpl());
		// sample() method internally creates a thread which manipulates
		// TwitterStream and calls these adequate listener methods continuously.
		twitterStream.user();
	}
}
