package com.PGJ.SGV.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class IndexController {
		
	@GetMapping ("/index")
	public String index(@RequestParam(value="error",required = false)String error,
			Principal principal, Model model,RedirectAttributes flash) {
		if(error!=null) {
			model.addAttribute("error","Nombre de usuario o contraseña incorrectos");
		}
		if(principal != null) {	
			flash.addFlashAttribute("info", "Ya ha iniciado sesion");
			//System.out.print(" entro al if: "+principal.getName());
			return "redirect:/";
		}else {			
						
		return "index";
		}
		
	}
	
	@GetMapping("/login-error")
	  public String loginError(Model model) {
	    model.addAttribute("loginError", true);
	    return "index";
	  }
		
}










