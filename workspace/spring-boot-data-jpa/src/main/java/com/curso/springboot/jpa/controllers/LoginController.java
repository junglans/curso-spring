package com.curso.springboot.jpa.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public String login(@RequestParam(value="error", required=false) String error,
						@RequestParam(value="logout", required=false) String logout,
						Model model, Principal principal, RedirectAttributes flash) {
		
		if (principal != null) {
			BindingAwareModelMap bModel = (BindingAwareModelMap)model;
			bModel.asMap().forEach((key,value) -> {
				flash.addFlashAttribute(key, value);
			});
				
			return "redirect:/clients";
		}
		
		if(logout != null) {
			model.addAttribute("info", "Sesión cerrada con éxito.");
		}
		if(error != null) {
			model.addAttribute("error", "Nombre de usuario o contraseña incorrectos. Inténtelo de nuevo.");
		}
		
		return "login";
	}
	
}
