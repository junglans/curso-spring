package com.curso.springboot.jpa.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value="error", required=false) String error,Model model, Principal principal, RedirectAttributes flash) {
		
		if (principal != null) {
			flash.addFlashAttribute("info", "Ya existe una sesión en curso.");
			return "redirect:/";
		}
		
		if(error != null) {
			model.addAttribute("error", "Nombre de usuario o contraseña incorrectos. Inténtelo de nuevo.");
		}
		return "login";
	}
	
}
