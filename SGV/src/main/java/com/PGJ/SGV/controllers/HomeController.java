package com.PGJ.SGV.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
import com.PGJ.SGV.models.entity.Conductor;
import com.PGJ.SGV.models.entity.Greeting;
import com.PGJ.SGV.models.entity.MessageNotify;
import com.PGJ.SGV.models.entity.Notificacion;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.service.IConductorService;
import com.PGJ.SGV.service.IMantenimientoService;
import com.PGJ.SGV.service.INotificacionService;
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.utilities.SystemDate;

@Controller
public class HomeController {	
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	Usuario usuario=new Usuario();
	List<Conductor> conductoreslic = new ArrayList<Conductor>();
	List<Conductor> conductoresine = new ArrayList<Conductor>();
	List<Usuario> usuarioslic = new ArrayList<Usuario>();
	List<Usuario> usuariosine = new ArrayList<Usuario>();
	List<Notificacion> notificacionsin = new ArrayList<Notificacion>();
	List<Notificacion> notificacionmant = new ArrayList<Notificacion>();

	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IConductorService conductorService;
	@Autowired
	private IMantenimientoService mantenimientoService;
	@Autowired
	private IObtenerUserService obuserService;
	@Autowired
	private INotificacionService notiService;

	String user;
	@RequestMapping(value={"/","/home"}, method = RequestMethod.GET)
	public String HomeBarra(Model model,Authentication authentication,HttpServletRequest request){
		
		var nombre="";
		user = obuserService.obtenUser();
		model.addAttribute("usuario",user);
		
		Long totalvig;
		Long tcondlic;
		Long tcondine;
		Long tusulic;
		Long tusuine;
		Long totalreg;
		Long tsin;
		Long tmant;
		Long total;
			
		usuario = usuarioService.findOne(authentication.getName());
		nombre= usuario.getNombre();		
	    model.addAttribute("id",authentication.getPrincipal());
	    model.addAttribute("Online",nombre); 		   	
	    
		List<Conductor> condlic = new ArrayList<Conductor>();		
		List<Conductor> condine = new ArrayList<Conductor>();
		List<Usuario> usulic = new ArrayList<Usuario>();
		List<Usuario> usuine = new ArrayList<Usuario>();
		List<Notificacion> notisin = new ArrayList<Notificacion>();
		List<Notificacion> notimant = new ArrayList<Notificacion>();
	
	   //conductoreslic= conductorService.NotifyCVigLic("2021-03-01", "2021-03-05");
	    conductoreslic= conductorService.NotifyCVigLic(SystemDate.obtefechaprevru(), SystemDate.obtefechaprevrd());
		conductoresine = conductorService.NotifyCVigIne(SystemDate.obtefechaprevru(), SystemDate.obtefechaprevrd());
	    usuarioslic=  usuarioService.NotifyUVigLic(SystemDate.obtefechaprevru(), SystemDate.obtefechaprevrd());
	    usuariosine =  usuarioService.NotifyUVigIne(SystemDate.obtefechaprevru(), SystemDate.obtefechaprevrd());
	    
	    tcondlic = conductorService.TotalCVigLic(SystemDate.obtefechaprevru(), SystemDate.obtefechaprevrd());
	    tcondine = conductorService.TotalCVigIne(SystemDate.obtefechaprevru(), SystemDate.obtefechaprevrd());    		
	    tusulic = usuarioService.TotalUVigLic(SystemDate.obtefechaprevru(), SystemDate.obtefechaprevrd());
	    tusuine = usuarioService.TotalUVigIne(SystemDate.obtefechaprevru(), SystemDate.obtefechaprevrd());
			
	    totalvig = tcondlic + tcondine + tusulic + tusuine ;
	    
	    notificacionsin = notiService.NotifyRegSin();
	    notificacionmant = notiService.NotifyRegMant();
	    
	    tsin = notiService.TotalRegSin();
	    tmant = notiService.TotalRegMant();
	    
	    totalreg = tsin + tmant;
	    total = totalvig+totalreg;
	    
	    //CONDUCTORES LICENCIAS
	    
		 for (int i=0;i<conductoreslic.size();i++) {
		         condlic.add(conductoreslic.get(i));
		        }
	     System.err.println("COND LIC: "+condlic.size());
         model.addAttribute("condlic",condlic);
		
       //CONDUCTORES INE
         
         for (int i=0;i<conductoresine.size();i++) {
	         condine.add(conductoresine.get(i));
	        }
         System.err.println("COND INE: "+condine.size());
         model.addAttribute("condine",condine);
         
 	    //USUARIOS LICENCIAS

         for (int i=0;i<usuarioslic.size();i++) {
	         usulic.add(usuarioslic.get(i));
	        }
         
         System.err.println("USU LIC: "+usulic.size());
         model.addAttribute("usulic",usulic);
	 
       //USUARIOS INE
         
         for (int i=0;i<usuariosine.size();i++) {
              usuine.add(usuariosine.get(i));
          }
         
         System.err.println("USU INE: "+usuine.size());
         model.addAttribute("usuine",usuine);
	     
         //NOTIFICACION
         
         for (int i=0;i<notificacionsin.size();i++) {
              notisin.add(notificacionsin.get(i));
         }
        
        System.err.println("NOTIFICACIONSIN: "+notisin.size());
        model.addAttribute("notisin",notisin);
         
        
        for (int i=0;i<notificacionmant.size();i++) {
            notimant.add(notificacionmant.get(i));
         }
      
       System.err.println("NOTIFICACIONMANT: "+notimant.size());
       model.addAttribute("notimant",notimant);
       model.addAttribute("totalvig",totalvig);
       model.addAttribute("totalreg",totalreg);
       model.addAttribute("total",total);
         
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
