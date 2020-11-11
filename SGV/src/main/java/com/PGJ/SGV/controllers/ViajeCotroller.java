package com.PGJ.SGV.controllers;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.models.entity.Viaje;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.service.IViajeService;
import com.PGJ.SGV.util.paginador.PageRender;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;


@Controller
public class ViajeCotroller {
	
	List<Vehiculo> vehiculo = new ArrayList<Vehiculo>();	
	List<Viaje> viajeslist = new ArrayList<Viaje>();
	
	@Autowired
	private IViajeService viajeService;
	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private ILogsAuditService logsauditService;
	
	
	static String user="";
	boolean editar = false;
	boolean aux=false;
	Long id_vehi=null;
	Double kilom_final;
	//Long id_vehi_edit=null;
	

	@RequestMapping(value="/Viajes", method = RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue = "0") int page,Model model, HttpServletRequest request,Authentication authentication) {	

		aux=false;
		var ads="";						
		var user="";	
		//Long id_ads;
		//int id_ad;
		
				if(hasRole("ROLE_ADMIN")) {
					user ="ROLE_ADMIN";						
					model.addAttribute("usuario",user);
				}else {
					if(hasRole("ROLE_USER")) {
						user = "ROLE_USER";
						model.addAttribute("usuario",user);				
						}
					}
		
		ads = authentication.getName();
		Pageable pageRequest = PageRequest.of(page, 100);
		
		if(user.equals("ROLE_USER")){
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);	
			//id_ads=usus.getAdscripcion().getId_adscripcion();
			//id_ad=id_ads.intValue();
			Page<Viaje> viajespageArea = viajeService.ViajesAreaPage(usus.getAdscripcion().getId_adscripcion(), pageRequest);	
			PageRender<Viaje> pageRenderArea = new PageRender<>("/Viajes", viajespageArea);	
			if(viajeService.totalViajesArea(usus.getAdscripcion().getId_adscripcion())>= 9) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};				
			model.addAttribute("titulo","Listado de Viajes");
			model.addAttribute("auxiliar", aux);
			model.addAttribute("titulouser",usus.getAdscripcion().getNombre_adscripcion());
			model.addAttribute("viajes",viajespageArea);
			model.addAttribute("page",pageRenderArea);
			return "Viajes";
			}	
				
		Page<Viaje> viajespage = viajeService.findAll(pageRequest);		
		PageRender<Viaje> pageRender = new PageRender<>("/Viajes", viajespage);				
		if(viajeService.viajestotales() >= 5) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};	
		
		model.addAttribute("titulo","Listado de Viajes ");
		model.addAttribute("auxiliar", aux);
		model.addAttribute("viajes",viajespage);
		model.addAttribute("page",pageRender);
		return "Viajes";
		
	}
		
	
	@RequestMapping(value="/ListarViajes/{id_vehiculo}", method = RequestMethod.GET)
	public String listarpv(@PathVariable(value="id_vehiculo") Long id_vehiculo,@RequestParam(name="page", defaultValue = "0") int page,Model model,HttpServletRequest request,Authentication authentication) {	

		aux=true;
		var ads="";						
		var user="";	
		//Long id_ads;
		//int id_ad;
		id_vehi=id_vehiculo;
		
				if(hasRole("ROLE_ADMIN")) {
					user ="ROLE_ADMIN";						
					model.addAttribute("usuario",user);
				}else {
					if(hasRole("ROLE_USER")) {
						user = "ROLE_USER";
						model.addAttribute("usuario",user);				
						}
					}
		
		ads = authentication.getName();
		Pageable pageRequest = PageRequest.of(page, 100);
		
		
		Vehiculo vehiculo = new Vehiculo();
		vehiculo = vehiculoService.findOne(id_vehiculo);
		
		Page<Viaje> viajespageArea = viajeService.FindviajeVehi(vehiculo.getId_vehiculo(), pageRequest);
		PageRender<Viaje> pageRenderArea = new PageRender<>("/Viajes", viajespageArea);	
		if(viajeService.viajestotales()>= 9) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};	
		
		model.addAttribute("titulo","Listado de Viajes");
		model.addAttribute("id_vehiculo",vehiculo.getId_vehiculo());
		model.addAttribute("auxiliar", aux);
		model.addAttribute("viajes",viajespageArea);
		model.addAttribute("page",pageRenderArea);
		return "Viajes";
	}
	
	@RequestMapping(value="/formViaj/Ag/{id_vehiculo}")
	public String crear(@PathVariable(value="id_vehiculo") Long id_vehiculo,Map<String,Object> model, Authentication authentication) {
			
		String ads = ""; 	
		var user="";
		ads = authentication.getName();
		//rolaction(ads);
		String fecha_max;
		 
		if(hasRole("ROLE_ADMIN")) {user ="ROLE_ADMIN";model.put("usuario",user);}else {if(hasRole("ROLE_USER")) {user = "ROLE_USER";model.put("usuario",user);}};
		
		Viaje viaje = new Viaje();	
		Vehiculo vehi= new Vehiculo();
		editar=false;
		
		if(!id_vehiculo.equals(null)) {
			    vehi= vehiculoService.findOne(id_vehiculo);	
			    id_vehi=vehi.getId_vehiculo();
	    		viaje.setVehiculo(vehi);   
	    		viaje.setKilometraje_inicial(viaje.getVehiculo().getKilometraje_inicial());	    		
	    		fecha_max=viajeService.fechamax(id_vehiculo);
	    		
	    		viaje.setFinicial_registro(fecha_max);
	    		viaje.setFfinal_registro(fecha_max);
	
	    		model.put("viaje", viaje);
	    		model.put("titulo", "Formulario de Viajes");
	    		return "formViaj";
	    		}else {
	    			return "formViaj";
	    			}
	}
	
	
	@RequestMapping(value="/formViajEditar/{id_viaje}")
	public String editar(@PathVariable(value="id_viaje") Long id_viaje,Map<String,Object>model, Authentication authentication) {	
		Viaje viaje = null;
		editar = true;
		String ads = ""; 
		ads = authentication.getName();
		//rolaction(ads);
		
		if(!id_viaje.equals(null)) {
			viaje = viajeService.findOne(id_viaje);
			//id_vehi_edit=viaje.getVehiculo().getId_vehiculo();
		}else {
			return "redirect:/Viajes";
			}
		model.put("viaje",viaje);
		model.put("titulo", "Editar cliente");
		return "formViajEditar";
		
	}
	
	
	@RequestMapping(value="/formViaj",method = RequestMethod.POST)
	public String guardar(Viaje viaje,Authentication authentication){
		
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
		
		Vehiculo vehi= new Vehiculo();
		 
		
            if(editar == true) {
			
			//VIAJES OLD
		    
			Viaje viaje_old = null;
			viaje_old = viajeService.findOne(viaje.getId_viaje());
		    System.err.println("old:"+viaje_old.toString());
		    String valor_old = viaje_old.toString();
		    
		    viajeService.save(viaje);
			
			//Auditoria
			
         	LogsAudit logs = new LogsAudit();
         	
            logs.setId_usuario(no_user);
			logs.setTipo_role(user);
			logs.setFecha(SystemDate.obtenFecha());
			logs.setHora(ObtenHour.obtenHour());
			logs.setName_table("VIAJES");
			logs.setValor_viejo(valor_old);
			logs.setTipo_accion("UPDATE");
									
			logsauditService.save(logs);
			
		}else {
		
			
			vehi=vehiculoService.findOne(id_vehi);
      	    viaje.setVehiculo(vehi);
    		kilom_final=viaje.getKilometraje_final();
      	    vehi.setKilometraje_inicial(kilom_final);
      	    vehiculoService.save(vehi);
		    viajeService.save(viaje);
		    
			String valor_nuevo=viaje.toString();
			
			//Auditoria
			
	     	LogsAudit logs = new LogsAudit();
	     	
	        logs.setId_usuario(no_user);
			logs.setTipo_role(user);
			logs.setFecha(SystemDate.obtenFecha());
			logs.setHora(ObtenHour.obtenHour());
			logs.setName_table("VIAJES");
			logs.setValor_viejo(valor_nuevo);
			logs.setTipo_accion("INSERT");
									
			logsauditService.save(logs);
			
			}
		
	     /*	if(editar==false) {
	     	vehi=vehiculoService.findOne(id_vehi);
      	    viaje.setVehiculo(vehi);
    		kilom_final=viaje.getKilometraje_final();
      	    vehi.setKilometraje_inicial(kilom_final);
      	    vehiculoService.save(vehi);
		    viajeService.save(viaje);	
		    } else {
		    	viajeService.save(viaje);
		    	}*/
		
		return "redirect:ListarViajes/"+viaje.getVehiculo().getId_vehiculo();
	}
	
	
	@RequestMapping(value="/elimViaj/{id_viaje}")
	public String eliminar (@PathVariable(value="id_viaje")Long id_viaje) {
		
		if(id_viaje != null) {
			viajeService.delete(id_viaje);
		}
		return "redirect:/Viajes";
	}
	
	
	@RequestMapping(value="/formViajBuscarPv")
	public String Buscartablapv(@RequestParam(name="page", defaultValue = "0") int page,Authentication authentication,
		@RequestParam(value="elemento") String elemento,Model model){	
		
		 aux=true;
		 Pageable pageRequest = PageRequest.of(page, 100);
		 var ads="";
		 ads = authentication.getName();
		 String elementof="";
		 
		 if(hasRole("ROLE_ADMIN")) {user ="ROLE_ADMIN";	model.addAttribute("usuario",user);
			}else {if(hasRole("ROLE_USER")) user = "ROLE_USER"; model.addAttribute("usuario",user);}
				
		 if(!elemento.isBlank()) {			
							if(isValidDouble(elemento)) {
								Double Dato = Double.parseDouble(elemento);
									DecimalFormat formt = new DecimalFormat("0");
									elemento = formt.format(Dato);
									elemento = elemento.replaceAll(",","");	
							};
					
					elementof = elemento.toUpperCase();
					Page<Viaje> viajespage= viajeService.ViajeElemVehiPage(id_vehi, elementof, pageRequest);
					PageRender<Viaje> pageRender = new PageRender<>("/formViajBuscarPv?elemento="+elementof, viajespage);
					if(viajeService.totalviajeElem(elementof)>= 9) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
					model.addAttribute("viajes",viajespage);
					model.addAttribute("page",pageRender);
					model.addAttribute("auxiliar", aux);
					model.addAttribute("elemento",elementof);
					model.addAttribute("id_vehiculo",id_vehi);
					return "Viajes";
					}
					else{
					return "redirect:/Viajes";
					}		
						
	}
	
	@RequestMapping(value="/formViajBuscar")
	public String Buscartabla(@RequestParam(name="page", defaultValue = "0") int page,Authentication authentication,
		@RequestParam(value="elemento") String elemento,Model model){	
		
		 aux=false;
		 Pageable pageRequest = PageRequest.of(page, 100);
		 var ads="";
		 ads = authentication.getName();
		 String elementof="";
		 
		 if(hasRole("ROLE_ADMIN")) {user ="ROLE_ADMIN";	model.addAttribute("usuario",user);
			}else {if(hasRole("ROLE_USER")) user = "ROLE_USER"; model.addAttribute("usuario",user);}
				
		 if(!elemento.isBlank()) {			
							if(isValidDouble(elemento)) {
								Double Dato = Double.parseDouble(elemento);
									DecimalFormat formt = new DecimalFormat("0");
									elemento = formt.format(Dato);
									elemento = elemento.replaceAll(",","");	
							};
					
		if(user == "ROLE_USER") {
		Usuario usus = new Usuario();
		usus = usuarioService.findbyAdscripcion(ads);
		elementof = elemento.toUpperCase(); 
									
		Page<Viaje> viajespage = viajeService.ViajesElemAreaPage(usus.getAdscripcion().getId_adscripcion(), elementof, pageRequest);
								
		PageRender<Viaje> pageRender = new PageRender<>("/formViajBuscar?elemento="+elementof, viajespage);
		if(viajeService.totalViajesElemArea(usus.getAdscripcion().getId_adscripcion(), elementof)>= 9) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
		model.addAttribute("viajes",viajespage);
		model.addAttribute("page",pageRender);
		model.addAttribute("auxiliar", aux);
		model.addAttribute("elemento",elementof);
		return "Viajes";
		};


		elementof = elemento.toUpperCase();
		Page<Viaje> viajespage= viajeService.ViajeElemPage(elementof, pageRequest);			 			
					 
		PageRender<Viaje> pageRender = new PageRender<>("/formViajBuscar?elemento="+elementof, viajespage);
		if(viajeService.totalviajeElem(elementof)>= 9) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
		model.addAttribute("viajes",viajespage);
		model.addAttribute("page",pageRender);
		model.addAttribute("auxiliar", aux);
		model.addAttribute("elemento",elementof);
		model.addAttribute("id_vehiculo",id_vehi);
		return "Viajes";
		}
	    else{
		 return "redirect:/Viajes";
		 }				
						
	}

	
	private static boolean isValidDouble(String s) {
		
		  final String Digits     = "(\\p{Digit}+)";
		  final String HexDigits  = "(\\p{XDigit}+)";
		
		  final String Exp        = "[eE][+-]?"+Digits;
		  final String fpRegex    =
		      ("[\\x00-\\x20]*"+  
		       "[+-]?(" + // Optional sign character		       		           
		       // Digitos ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
		       "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

		       // . Digitos ExponentPart_opt FloatTypeSuffix_opt
		       "(\\.("+Digits+")("+Exp+")?)|"+

		       // Hexadecimal strings
		       "((" +
		        // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
		        "(0[xX]" + HexDigits + "(\\.)?)|" +

		        // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
		        "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

		        ")[pP][+-]?" + Digits + "))" +
		       "[fFdD]?))" +
		       "[\\x00-\\x20]*");

		  return Pattern.matches(fpRegex, s);
		  
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
	

	public void rolaction(String ads) {
		Usuario usus = new Usuario();
		usus = usuarioService.findbyAdscripcion(ads);
		if(hasRole("ROLE_ADMIN")) {
	//		conductores = conductorService.findConductorEstado();								
		}else {
			if(hasRole("ROLE_USER")) {				
		//	conductores = conductorService.findConductorAreaEstado(usus.getAdscripcion().getId_adscripcion());				
			}
		}
		
	}
	
}
