package com.curso.springboot.jpa.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LocaleControler {

	@RequestMapping(value = "/locale", method = RequestMethod.GET)
	public String locale(HttpServletRequest request) {
		
		String lastUrl = request.getHeader("referer");
		return "redirect:" + lastUrl;
		
	}
}
