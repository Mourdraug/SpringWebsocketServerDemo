package org.mordraug.wssecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*") //For developement purpouse I allowed all origins
@RestController
public class HttpController {
	
	/*
	 * Nice little endpoint. On successful authentication will return our username.
	 * Request will have status 401 otherwise. 
	 */
	@RequestMapping("/user")
	public String user(){
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	/*
	 * Endpoint that ignores csrf protection for token collection
	 */
	@RequestMapping("/csrf")
	public String csrf(){
		return "";
	}
}
