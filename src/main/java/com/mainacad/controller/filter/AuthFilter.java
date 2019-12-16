package com.mainacad.controller.filter;

import java.io.IOException;

import javax.servlet.RequestDispatcher;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class AuthFilter extends Filter{
	
  @Override
public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
	 
		/*
		 * List<String> autHeaders = exchange.getRequestHeaders().get("X-Auth"); if
		 * (autHeaders.isEmpty() || autHeaders.get(0).equals("secret token")) {
		 * RequestDispatcher dispatcher = exchange.getHttpContext().
		 * req.setAttribute("errorMsg", "Login or password are wrong!");
		 * 
		 * }
		 * 
		 */
}

@Override
public String description() {
	// TODO Auto-generated method stub
	return null;
}

}
