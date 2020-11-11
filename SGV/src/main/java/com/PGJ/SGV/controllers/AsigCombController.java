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
import org.springframework.web.bind.annotation.RequestParam;

import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.ObtenMonth;
import com.PGJ.SGV.utilities.SystemDate;
import com.PGJ.SGV.models.entity.AsigCombustible;
import com.PGJ.SGV.models.entity.AsigCombustibleExt;
import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.service.IAsigComExtService;
import com.PGJ.SGV.service.IAsigComService;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IVehiculoService;


@Controller
public class AsigCombController {

	@Autowired
	private IAsigComService AsigCombus;
	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private IAsigComExtService asigExtService;
	@Autowired
	private ILogsAuditService logsauditService;
	
	public static String placaURL; 
	boolean editar = false;
	
	
	@RequestMapping(value="/ListarCombustible/{id_vehiculo}", method = RequestMethod.GET)
	public String listar(@PathVariable(value="id_vehiculo") Long id_vehiculo,Model model) {
		boolean asignacion=false;
		List<AsigCombustible> combustiblePlacaMes = new ArrayList<AsigCombustible>();
		List<AsigCombustibleExt> combustibleExtePlacaMes = new ArrayList<AsigCombustibleExt>();
		List<AsigCombustible> combustiblePlacaHisto = new ArrayList<AsigCombustible>();
		//List<AsigCombustibleExt> combustibleExtePlacaHisto = new ArrayList<AsigCombustibleExt>();
		var user="";
		if(hasRole("ROLE_ADMIN")) {user ="ROLE_ADMIN";	model.addAttribute("usuario",user);}else {if(hasRole("ROLE_USER")) user = "ROLE_USER";model.addAttribute("usuario",user);};
		
		Vehiculo vehiculo = new Vehiculo();
		vehiculo = vehiculoService.findOne(id_vehiculo);
		
		combustiblePlacaMes = AsigCombus.findidVehiculoMensual(vehiculo.getId_vehiculo(),ObtenMonth.obtenNumMes());
	
		if(!combustiblePlacaMes.isEmpty()) {
			asignacion = true;
			combustibleExtePlacaMes =  asigExtService.findextId(combustiblePlacaMes.get(0).getId_asignacion());
		
			if(vehiculo.getKilometraje_inicial() == combustiblePlacaMes.get(0).getKilometraje_ord()) {
					asignacion = false;								
			}
			
			if(!combustibleExtePlacaMes.isEmpty()) {
				if(vehiculo.getKilometraje_inicial() == combustibleExtePlacaMes.get(combustibleExtePlacaMes.size()-1).getKilometraje_ext() || combustibleExtePlacaMes.size() > 3) {
					asignacion = false;
				}else {
					asignacion = true;
				}
			}
						
					
		}
		
		combustiblePlacaHisto = AsigCombus.findidVehiculo(vehiculo.getId_vehiculo());	
		
		model.addAttribute("existeAsignacion",asignacion);
		model.addAttribute("mes",ObtenMonth.obtenNumMes());
		model.addAttribute("titulo","Listado de Combustible de "+ ObtenMonth.obtenMes()+" de la placa: "+vehiculo.getPlaca());
		model.addAttribute("titulo2","Listado de Combustible Historico");
		//Mensual
		model.addAttribute("combustible",combustiblePlacaMes);
		model.addAttribute("combustibleext",combustibleExtePlacaMes);
		//historico
		model.addAttribute("combustibleHisto",combustiblePlacaHisto);		
		model.addAttribute("id_vehiculo",vehiculo.getId_vehiculo());
		
		return "Combustibles";
	}
		
	
	@RequestMapping(value="/formComb/Ag/{id_vehiculo}", method = RequestMethod.GET)
	public String crear(@PathVariable(value="id_vehiculo") Long id_vehiculo, Map<String,Object> model) {
		
		editar=false;
		AsigCombustible combustible = new AsigCombustible();
		Vehiculo vehiculo = new Vehiculo();
		vehiculo = vehiculoService.findOne(id_vehiculo);		
		combustible.setVehiculo(vehiculo);
		combustible.setKilometraje_ord(vehiculo.getKilometraje_inicial());
		combustible.setFecha_ini_ord(SystemDate.obtenFecha());
	
		model.put("placa", vehiculo.getPlaca());
		model.put("kilometraje", vehiculo.getKilometraje_inicial());
		model.put("combustible",combustible );				
		model.put("titulo", "Formulario de Combustible");
		model.put("mes", ObtenMonth.obtenMes());
							
		return "combustible/formComb";
	}
	
	@RequestMapping(value="/Addext/{id_asignacion}", method = RequestMethod.GET)
	public String crearext(@PathVariable(value="id_asignacion") Long id_asignacion, Map<String,Object> model) {		
		
		editar=false;
		AsigCombustible combustible = new AsigCombustible();
		combustible = AsigCombus.findOne(id_asignacion);
		Vehiculo vehiculo = new Vehiculo();
		vehiculo = vehiculoService.findOne(combustible.getVehiculo().getId_vehiculo());
		
		AsigCombustibleExt extra = new AsigCombustibleExt();
		extra.setAsigCombustible(combustible);		
		extra.setKilometraje_ext(vehiculo.getKilometraje_inicial());
		
		model.put("combustible",combustible );	
		model.put("placa", combustible.getVehiculo().getPlaca());
		model.put("combustibleExt",extra);				
		model.put("titulo", "Formulario de Combustible");
		model.put("mes", ObtenMonth.obtenMes());
							
		return "combustible/formCombExt";
	}
	
	@RequestMapping(value="/formComb/{id_asignacion}")
	public String editar(@PathVariable(value="id_asignacion") Long id_asignacion,Map<String,Object> model ) {
		
		editar=true;
		AsigCombustible combustible = null;		
		
		if(!id_asignacion.equals(null)) {			
			combustible = AsigCombus.findOne(id_asignacion);
		}else {
			return "redirect:/ListarCombustible";
		}
		//model2.addAttribute("placa",combustible.getVehiculo().getPlaca());
		model.put("combustible",combustible );				
		model.put("titulo", "Editar Combustible");
		return "combustible/formComb";
	}
	
	
	@RequestMapping(value="/formComb",method = RequestMethod.POST)
	public String guardar(AsigCombustible combustible,Authentication authentication){
		
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
		 
		
		 Vehiculo vehiculo = null;	
		 vehiculo = vehiculoService.findOne(combustible.getVehiculo().getId_vehiculo());	
		 combustible.setVehiculo(vehiculo);
		 combustible.setKilometraje_ord(vehiculo.getKilometraje_inicial());

          if(editar == true) {
			
			//AsigComb OLD
        	  
        	AsigCombustible combustible_old = null;
        	combustible_old =  AsigCombus.findOne(combustible.getId_asignacion());
		    System.err.println("old:"+combustible_old.toString());
		    String valor_old = combustible_old.toString();
		    	
		    AsigCombus.save(combustible);	
			
			//Auditoria
			
         	LogsAudit logs = new LogsAudit();
         	
            logs.setId_usuario(no_user);
			logs.setTipo_role(user);
			logs.setFecha(SystemDate.obtenFecha());
			logs.setHora(ObtenHour.obtenHour());
			logs.setName_table("ACOMBUS_ORD");
			logs.setValor_viejo(valor_old);
			logs.setTipo_accion("UPDATE");
									
			logsauditService.save(logs);
			
			editar = false;	
			
		}else {
		
		AsigCombus.save(combustible);
		String valor_nuevo=combustible.toString();
		
		//Auditoria
		
     	LogsAudit logs = new LogsAudit();
     	
        logs.setId_usuario(no_user);
		logs.setTipo_role(user);
		logs.setFecha(SystemDate.obtenFecha());
		logs.setHora(ObtenHour.obtenHour());
		logs.setName_table("ACOMBUS_ORD");
		logs.setValor_viejo(valor_nuevo);
		logs.setTipo_accion("INSERT");
								
		logsauditService.save(logs);
		
		}
								
		return "redirect:/ListarCombustible/"+vehiculo.getId_vehiculo();
	}
	
	@RequestMapping(value="/formCombExt",method = RequestMethod.POST)
	public String guardarExt(AsigCombustibleExt combustibleext,Authentication authentication){
	
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
		 
		AsigCombustibleExt extra = new AsigCombustibleExt();
		AsigCombustible combustible = null;		
		combustible = AsigCombus.findOne(combustibleext.getAsigCombustible().getId_asignacion());	
		combustibleext.setAsigCombustible(combustible);		
		combustibleext.setKilometraje_ext(combustible.getVehiculo().getKilometraje_inicial());
		long id_i;
		try {
		 id_i=asigExtService.ultimoid()+1;
		}catch (Exception e) {
			id_i=0;
		}
		combustibleext.setId_asignacion(id_i);
		

		 if(editar == true) {
				
				//AsigCombExt OLD
	        	  
	        	AsigCombustible combustible_old = null;
	    		combustible_old = AsigCombus.findOne(combustibleext.getId_asignacion());	
			    System.err.println("old:"+combustible_old.toString());
			    String valor_old = combustible_old.toString();
			    	
			    asigExtService.save(combustibleext);	
				
				//Auditoria
				
	         	LogsAudit logs = new LogsAudit();
	         	
	            logs.setId_usuario(no_user);
				logs.setTipo_role(user);
				logs.setFecha(SystemDate.obtenFecha());
				logs.setHora(ObtenHour.obtenHour());
				logs.setName_table("ACOMBUS_EXT");
				logs.setValor_viejo(valor_old);
				logs.setTipo_accion("UPDATE");
										
				logsauditService.save(logs);
				
				editar = false;	
				
			}else {
			
			asigExtService.save(combustibleext);
			String valor_nuevo=combustibleext.toString();
			
			//Auditoria
			
	     	LogsAudit logs = new LogsAudit();
	     	
	        logs.setId_usuario(no_user);
			logs.setTipo_role(user);
			logs.setFecha(SystemDate.obtenFecha());
			logs.setHora(ObtenHour.obtenHour());
			logs.setName_table("ACOMBUS_EXT");
			logs.setValor_viejo(valor_nuevo);
			logs.setTipo_accion("INSERT");
									
			logsauditService.save(logs);
			
			}
		 
			return "redirect:/ListarCombustible/"+combustible.getVehiculo().getId_vehiculo();
	}
	
	
	@RequestMapping(value="/elimComb/{id_asignacion}/{placa}")
	public String eliminar (@PathVariable(value="id_asignacion")Long id_asignacion,@PathVariable(value="placa")String placa) {
		
		if(!id_asignacion.equals(null)) {
			AsigCombus.delete(id_asignacion);
		}
		return "redirect:/ListarCombustible/"+placa;
	}
	
	@RequestMapping(value="/elimCombext/{id_asignacion}/{placa}")
	public String eliminarext (@PathVariable(value="id_asignacion")Long id_asignacion,@PathVariable(value="placa")String placa) {
		
		if(!id_asignacion.equals(null)) {
			asigExtService.delete(id_asignacion);
		}
		return "redirect:/ListarCombustible/"+placa;
	}
	
	@RequestMapping(value="/Combustibles", method = RequestMethod.GET)
	public String listarCA(Map<String,Object> model, @RequestParam(value="id_vehiculo")Long id_vehiculo,Model model2) throws Exception {
		
		var user="";
	if(hasRole("ROLE_ADMIN")) {	user ="ROLE_ADMIN";	model.put("usuario",user);}else {	if(hasRole("ROLE_USER")) user = "ROLE_USER";model.put("usuario",user);	}
		
		placaURL=vehiculoService.findOne(id_vehiculo).getPlaca();
		List<AsigCombustible> combustible = new ArrayList<AsigCombustible>();
		List<AsigCombustible> combustiblePlaca = new ArrayList<AsigCombustible>();
		combustible = AsigCombus.findAll();	
	/*	
		for(AsigCombustible comb: combustible) {
			if(comb.getVehiculo().getPlaca().equals(placaURL)) {
											
				combustiblePlaca.add(comb);
				}
		}*/
					
	    model2.addAttribute("placa", placaURL);
		model.put("combustible", combustiblePlaca);
		model.put("titulo", "Formulario de Combustible");
							
		return "Combustibles";
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
