package com.curso.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@Value("${application.controllers.index.message}")
	private String message;
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("message", this.message);
		return "index";
	}
}
