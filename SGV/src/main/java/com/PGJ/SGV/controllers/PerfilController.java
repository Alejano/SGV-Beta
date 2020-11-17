package com.PGJ.SGV.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.service.IUsuarioService;

@Controller
public class PerfilController {

	@Autowired
	private IUsuarioService usuarioService;	
	@Autowired
	private IObtenerUserService obuserService;

	String user;
	
	@RequestMapping(value = "/perfil")
	public String editar(Map<String, Object> model) {
		
		
		 Usuario usuario = null;
		 var no_empleado="";
		 no_empleado = obuserService.obtenEmpl();
		 user = obuserService.obtenUser();
		 model.put("us",user);
			
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
	
}
