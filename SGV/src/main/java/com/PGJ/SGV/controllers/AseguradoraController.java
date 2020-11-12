package com.PGJ.SGV.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.PGJ.SGV.models.entity.Aseguradora;
import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.service.IAseguradoraService;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;



@Controller
public class AseguradoraController {

	List<Aseguradora> aseguradora = new ArrayList<Aseguradora>();
			
	@Autowired
	private IAseguradoraService asegService;
	@Autowired
	private ILogsAuditService logsauditService;
	
	Vehiculo vehiculo = new Vehiculo();
	static String user="";
	public static boolean editar = false;
	Long id_aseg;


	@RequestMapping(value="/Aseguradoras", method = RequestMethod.GET)
	public String listar(Model model,Authentication authentication) {
		

		var user="";	
				if(hasRole("ROLE_ADMIN")) {
					user ="ROLE_ADMIN";	model.addAttribute("usuario",user);
					}else {
						if(hasRole("ROLE_USER")) {
							user = "ROLE_USER"; model.addAttribute("usuario",user);
						}else {
							if(hasRole("ROLE_SEGURO")) {
								user = "ROLE_SEGURO"; model.addAttribute("usuario",user);
							}
						}	
					}

		aseguradora = asegService.findAll();
		
		if(asegService.aseguradorastotales()>= 5) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};	

		model.addAttribute("titulo","Listado de Aseguradoras");
		model.addAttribute("aseguradoras",aseguradora);
		return "Aseguradoras/Aseguradoras";

	}
	
	
	@RequestMapping(value="/formAseg")
	public String crear(Map<String,Object> model) {	
		
		editar=false;
		Aseguradora aseguradora = new Aseguradora();		
		model.put("editar", editar);
		model.put("aseguradora", aseguradora);
		
		return "Aseguradoras/formAseg";
		
	}
	
	
	@RequestMapping(value="/formAseg/{id_aseguradora}")
	public String editar(@PathVariable(value="id_aseguradora") Long id_aseguradora,Map<String,Object>model) {
		
		editar = true;
		Aseguradora aseguradora = null;
		if(id_aseguradora != 0) {
			aseguradora = asegService.findOne(id_aseguradora);			
			id_aseg = aseguradora.getId_aseguradora();
			}else {
				return "redirect:/Aseguradoras";
				}
		
		model.put("aseguradora",aseguradora);
		model.put("titulo", "Editar cliente");
		return "Aseguradoras/formAseg";
		
	}
	
	@RequestMapping(value="/estadoAseg/{id_aseguradora}/{enabled}")
	public String estado (@PathVariable(value="id_aseguradora")Long id_aseguradora,@PathVariable(value="enabled")boolean enabled,Authentication authentication) {
		
		var user="";
		var no_user ="";
		no_user = authentication.getName();
		
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";
			}else {
				if(hasRole("ROLE_USER")) {
					user = "ROLE_USER";
				}
			}
		
		Aseguradora aseguradora = null;
		boolean seteo = false;
		aseguradora = asegService.findOne(id_aseguradora);	
		
		if(enabled) {
			seteo=false;
			aseguradora.setEnabled(seteo);
			}else {
				seteo=true;
				aseguradora.setEnabled(seteo);	
				}
		
		Aseguradora aseguradora_old = null;
		aseguradora_old = asegService.findOne(aseguradora.getId_aseguradora());
	    System.err.println("old:"+aseguradora_old.toString());
	    String valor_old = aseguradora_old.toString();
	    
	    asegService.save(aseguradora);	
	
		//Auditoria
		
     	LogsAudit logs = new LogsAudit();
     	
        logs.setId_usuario(no_user);
		logs.setTipo_role(user);
		logs.setFecha(SystemDate.obtenFecha());
		logs.setHora(ObtenHour.obtenHour());
		logs.setName_table("ASEGURADORAS");
		logs.setValor_viejo(valor_old);
		logs.setTipo_accion("UPDATE");
								
		logsauditService.save(logs);
		
		editar = false;		
			
	    return "redirect:/Aseguradoras";
	    
	}
	
	@RequestMapping(value="/VerAseg/{id_aseguradora}")
	public String ver(@PathVariable(value="id_aseguradora") Long id_aseguradora,Map<String,Object>model) {
		
		editar = true;
		Aseguradora aseguradora = null;
		if(id_aseguradora != 0) {
			aseguradora = asegService.findOne(id_aseguradora);			
			id_aseg = aseguradora.getId_aseguradora();
			}else {
				return "redirect:/Aseguradoras";
				}
		
		model.put("editar", editar);
		model.put("aseguradora",aseguradora);
		model.put("titulo", "Editar cliente");
		return "Aseguradoras/formAseg";
		
	}
	
	@RequestMapping(value="/formAseg",method = RequestMethod.POST)
	public String guardar(Aseguradora aseguradora,Authentication authentication) {	

		var user="";
		var no_user ="";
		no_user = authentication.getName();
		
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";
			}else {
				if(hasRole("ROLE_USER")) {
					user = "ROLE_USER";
				}
			}
		
		
		if(editar == true) {
			
			//Aseguradora OLD
			
			Aseguradora aseguradora_old = null;
		    aseguradora_old = asegService.findOne(aseguradora.getId_aseguradora());	
		    System.err.println("old:"+aseguradora_old.toString());
		    String valor_old = aseguradora.toString();
		    
			asegService.save(aseguradora);
			
			//Auditoria
			
         	LogsAudit logs = new LogsAudit();
         	
            logs.setId_usuario(no_user);
			logs.setTipo_role(user);
			logs.setFecha(SystemDate.obtenFecha());
			logs.setHora(ObtenHour.obtenHour());
			logs.setName_table("ASEGURADORAS");
			logs.setValor_viejo(valor_old);
			logs.setTipo_accion("UPDATE");
									
			logsauditService.save(logs);
			editar = false;	
			
		
		}else {
		
		asegService.save(aseguradora);
		String valor_nuevo=aseguradora.toString();
		
		//Auditoria
		
     	LogsAudit logs = new LogsAudit();
     	
        logs.setId_usuario(no_user);
		logs.setTipo_role(user);
		logs.setFecha(SystemDate.obtenFecha());
		logs.setHora(ObtenHour.obtenHour());
		logs.setName_table("ASEGURADORAS");
		logs.setValor_viejo(valor_nuevo);
		logs.setTipo_accion("INSERT");
								
		logsauditService.save(logs);
		
		}
		
		//asegService.save(aseguradora);
		//editar = false;	
		
		return "redirect:Aseguradoras";
	}
	
	@RequestMapping(value="/elimAseg/{id_aseguradora}")
	public String eliminar (@PathVariable(value="id_aseguradora")Long id_aseguradora) {
		
		if(id_aseguradora != 0) {
			asegService.delete(id_aseguradora);
		}
		return "redirect:/Aseguradoras";
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
