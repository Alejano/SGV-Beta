package com.PGJ.SGV.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

import com.PGJ.SGV.models.entity.AsigCombustible;
import com.PGJ.SGV.models.entity.Conductor;
import com.PGJ.SGV.models.entity.Greeting;
import com.PGJ.SGV.models.entity.MessageNotify;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.service.IConductorService;
import com.PGJ.SGV.service.IMantenimientoService;
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.utilities.ObtenMonth;
import com.PGJ.SGV.utilities.SystemDate;

@Controller
public class HomeController {	
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	Usuario usuario=new Usuario();
	List<Conductor> conductoreslic = new ArrayList<Conductor>();
	List<Conductor> conductoresine = new ArrayList<Conductor>();
	List<Usuario> usuarioslic = new ArrayList<Usuario>();
	List<Usuario> usuariosine = new ArrayList<Usuario>();

	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IConductorService conductorService;
	@Autowired
	private IMantenimientoService mantenimientoService;
	@Autowired
	private IObtenerUserService obuserService;

	String user;
	@RequestMapping(value={"/","/home"}, method = RequestMethod.GET)
	public String HomeBarra(Model model,Authentication authentication,HttpServletRequest request){
		
		var nombre="";
		user = obuserService.obtenUser();
		model.addAttribute("usuario",user);
		int num=2019;
			
		usuario = usuarioService.findOne(authentication.getName());
		nombre= usuario.getNombre();		
	    model.addAttribute("id",authentication.getPrincipal());
	    model.addAttribute("Online",nombre); 		   	
	    
	    System.err.println("LEO: "+SystemDate.obtefechaprevru());	    
	    System.err.println("ZURIEL: "+SystemDate.obtefechaprevrd());
	    
		List<Conductor> condlic = new ArrayList<Conductor>();
		List<Conductor> condine = new ArrayList<Conductor>();
		List<Usuario> usulic = new ArrayList<Usuario>();
		List<Usuario> usuine = new ArrayList<Usuario>();
	
	    //conductores= conductorService.NotifyVigLic(SystemDate.obtefechaprevru(), SystemDate.obtefechaprevrd());
	    conductoreslic= conductorService.NotifyCVigLic("2021-03-01", "2021-03-05");
	    conductoresine = conductorService.NotifyCVigIne("2021-03-01", "2021-03-05");
	    usuarioslic=  usuarioService.NotifyUVigLic("2021-03-01", "2021-03-05");
	    usuariosine =  usuarioService.NotifyUVigIne("2021-03-01", "2021-03-05");

	    //CONDUCTORES LICENCIAS
	    
		 for (int i=0;i<conductoreslic.size();i++) {
		         condlic.add(conductoreslic.get(i));
			     System.err.print("COND LIC: "+condlic.get(i).getNo_empleado());
			     System.err.print("\n");
		        }
	     System.err.print("COND LIC: "+condlic.size());
	     System.err.print("\n");
         model.addAttribute("condlic",condlic);
		
       //CONDUCTORES INE
         
         for (int i=0;i<conductoresine.size();i++) {
	         condine.add(conductoresine.get(i));
		     System.err.print("COND INE: "+condine.get(i).getNo_empleado());
		     System.err.print("\n");
	        }
         System.err.print("COND INE: "+condine.size());
         System.err.print("\n");
         model.addAttribute("condine",condine);
         
 	    //USUARIOS LICENCIAS

         for (int i=0;i<usuarioslic.size();i++) {
	         usulic.add(usuarioslic.get(i));
		     System.err.print("USU LIC: "+usulic.get(i).getNo_empleado());
		     System.err.print("\n");
	        }
         
         System.err.print("USU LIC: "+usulic.size());
         System.err.print("\n");
         model.addAttribute("usulic",usulic);
	 
       //USUARIOS INE
         
         for (int i=0;i<usuariosine.size();i++) {
              usuine.add(usuariosine.get(i));
	          System.err.print("USU INE: "+usuine.get(i).getNo_empleado());
	          System.err.print("\n");
          }
         
         System.err.print("USU INE: "+usuine.size());
         System.err.print("\n");
         model.addAttribute("usuine",usuine);
         model.addAttribute("num",num);
	     
	     return "home";
	     
	}
	
	
	
	public boolean hasRole (String role) {
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
		System.err.println("Recibi el mensaje MANT");
       String titulo = "Mantenimientos del Dia";
       String valor1 = "Altas:";
       String valor2 = "Salidas:";
        Long MantRegistro = mantenimientoService.NotificacionMant();
		Long MantEntrega = mantenimientoService.NotificacionMantEntrega();

        return new Greeting(titulo+"<br>"+valor1+HtmlUtils.htmlEscape(MantRegistro.toString())+"   "+ valor2 + HtmlUtils.htmlEscape(MantEntrega.toString()));
                 
    }


/*
	@GetMapping({"/home","/"})	
	public String login() {	
		return "home";
	}
*/
	
}
