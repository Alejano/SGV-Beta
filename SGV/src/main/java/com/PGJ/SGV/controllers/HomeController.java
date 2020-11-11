package com.PGJ.SGV.controllers;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;
import com.PGJ.SGV.models.entity.Greeting;
import com.PGJ.SGV.models.entity.MessageNotify;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.service.IMantenimientoService;
import com.PGJ.SGV.service.IUsuarioService;

@Controller
public class HomeController {	
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	Usuario usuario=new Usuario();	
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IMantenimientoService mantenimientoService;

	
	@RequestMapping(value={"/","/home"}, method = RequestMethod.GET)
	public String HomeBarra(Model model, Authentication authentication,HttpServletRequest request){
		var nombre="";
		var user="";
		
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";	model.addAttribute("usuario",user);
			}else {
				if(hasRole("ROLE_USER")) {
					user = "ROLE_USER"; model.addAttribute("usuario",user);
				}else {
					if(hasRole("ROLE_SEGURO")) {
						user = "ROLE_SEGURO"; model.addAttribute("usuario",user);
					}else {
						if(hasRole("ROLE_TALLER")) {
							user = "ROLE_TALLER"; model.addAttribute("usuario",user);
						}else {
							if(hasRole("ROLE_SINIESTRO")) {
								user = "ROLE_SINIESTRO"; model.addAttribute("usuario",user);
							}
						}
					}
				}	
			}
		
		usuario = usuarioService.findOne(authentication.getName());
		nombre= usuario.getNombre();		
	   model.addAttribute("id",authentication.getPrincipal());
	   model.addAttribute("Online",nombre); 		   	
	   
	   
		return "home";
	}
	/*
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public String Home(Model model, Authentication authentication) {
		var nombre="";
		var UAuth ="";
		if(hasRole("ROLE_ADMIN")) {
			UAuth = "ROLE_ADMIN";
			model.addAttribute("usuario",UAuth);
		}else {
			if(hasRole("ROLE_USER")) {
				UAuth = "ROLE_USER";
				model.addAttribute("usuario",UAuth);
			}
		}	    		    	  
						   		
		
		usuario = usuarioService.findOne(authentication.getName());    
		nombre= usuario.getNombre();
		model.addAttribute("id",authentication.getName());
		 model.addAttribute("Online",nombre); 		
		return "home";
	}
	*/
	
	public static boolean hasRole(String role) {
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context==null) {
		return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> autorithies = auth.getAuthorities();
		for(GrantedAuthority authority: autorithies) {
			if(role.equals(authority.getAuthority())) {return true;}
		}
		return false;
	}
	
	@MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(MessageNotify message) throws Exception {
        Thread.sleep(1000); // simulated delay
        Long NotificacionMant = mantenimientoService.NotificacionMant();
        return new Greeting("El vehiculo con placa: "+HtmlUtils.htmlEscape(message.getName())+" Se le a asignado un nuevo mantenimiento, "
        		+ "Mantenimientos Registrados el dia de hoy: " + HtmlUtils.htmlEscape(NotificacionMant.toString()) + " "  );
    }
	
	@MessageMapping("/TimeReal")
    @SendTo("/topic/MantTimeReal")
    public Greeting MantTimeReal(MessageNotify message) throws Exception {
		//System.out.println("Recibi el mensaje");
       String titulo = "Mantenimientos del Dia";
       String valor1 = "Altas:";
       String valor2 = "Salidas:";
        Long MantRegistro = mantenimientoService.NotificacionMant();
		Long MantEntrega = mantenimientoService.NotificacionMantEntrega();
        return new Greeting(titulo+"<br>"+valor1+HtmlUtils.htmlEscape(MantRegistro.toString())+"   "+ valor2 + HtmlUtils.htmlEscape(MantEntrega.toString()) );
    }


/*
	
	@GetMapping({"/home","/"})	
	public String login() {
		
		
				
		return "home";
	}
*/
}
