package com.PGJ.SGV.controllers;



import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.service.IUsuarioService;

@Controller
public class PerfilController {

	@Autowired
	private IUsuarioService usuarioService;	
	

	@RequestMapping(value = "/perfil")
	public String editar(Map<String, Object> model,Authentication authentication) {
		 Usuario usuario = null;
			var user="";
		 if(hasRole("ROLE_ADMIN")) {
				user ="ROLE_ADMIN";						
				model.put("us",user);
			}else {
				if(hasRole("ROLE_USER")) {
					user = "ROLE_USER";
					model.put("us",user);				
				}
			};	   
			
		 var no_empleado ="";
		 no_empleado = authentication.getName();
		 
		if (!no_empleado.equals(null)) {
			
			usuario = usuarioService.findbyAdscripcion(no_empleado);			
			
		} else {
			return "redirect:/home";
		}						
		model.put("usuario", usuario);
		model.put("nombre", usuario.getNombre() +" "+usuario.getApellido1());
		model.put("nombrebienbenida", usuario.getNombre());
		model.put("Ads", usuario.getAdscripcion().getNombre_adscripcion());
		model.put("titulo", "Editar Perfil");
		return "perfil";
	}

	@RequestMapping(value = "/perfil", method = RequestMethod.POST)
	public String guardar(Usuario usuario) {		
									
		
		usuarioService.save(usuario);		

		return "redirect:home";
	}
	
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

}
