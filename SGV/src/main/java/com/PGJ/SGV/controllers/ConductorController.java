package com.PGJ.SGV.controllers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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

import com.PGJ.SGV.SystemDate;
import com.PGJ.SGV.models.entity.Adscripcion;
import com.PGJ.SGV.models.entity.Conductor;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.service.IAdscripcionService;
import com.PGJ.SGV.service.IConductorService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.util.paginador.PageRender;


@Controller
public class ConductorController {	
	
	@Autowired
	private IConductorService conductorService;
	@Autowired
	private IAdscripcionService adscripService;
	@Autowired
	private IUsuarioService usuarioService;
	
	List<Adscripcion> adscripcion = new ArrayList<Adscripcion>();	
	List<Conductor> conductores = new ArrayList<Conductor>();	
	
	String empleado ="";
    String falta_conductor="";
    String ealta_conductor="";
    String ads_usu="";
	boolean editar = false;
	static int 	Corddocu = 0;
	static int 	Cordtabla = 0;
	
	
	@RequestMapping(value="/Conductores", method = RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue = "0") int page,Model model,Model model2, Authentication authentication) {
		//List<Conductor> ConductorArea = new ArrayList<Conductor>();	
		var user="";
		var ads="";
		ads = authentication.getName();
		
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";						
			model.addAttribute("usuario",user);
		}else {
			if(hasRole("ROLE_USER")) {
				user = "ROLE_USER";
				model.addAttribute("usuario",user);				
			}
		}
					
		model.addAttribute("titulo_conductores","Listado de Conductores"); 	
		Pageable pageRequest = PageRequest.of(page, 100);
		//USUARIO
		if(user.equals("ROLE_USER")){
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);
			//ConductorArea = conductorService.findConductorArea(usus.getAdscripcion().getId_adscripcion());
			Page<Conductor> conductorareapage = conductorService.findConductorAreaPage(usus.getAdscripcion().getId_adscripcion(), pageRequest);
			PageRender<Conductor> pageRenderArea = new PageRender<> ("/Conductores",conductorareapage);
            if(conductorService.totalConductores()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");}
			model.addAttribute("conductores",conductorareapage);
			model.addAttribute("page",pageRenderArea);			
			return "Conductores";
		}
		
		//ADMINISTRADOR 
		//Page<Conductor> conductorPage = conductorService.findAll(pageRequest);
		Page<Conductor> conductorPage = conductorService.findByCNl(pageRequest); 
		PageRender<Conductor> pageRender = new PageRender<>("/Conductores",conductorPage);
		if(conductorService.totalConductores()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};	
		model.addAttribute("Corddocu",Corddocu);
		model.addAttribute("Cordtabla",Cordtabla);
		model.addAttribute("conductores",conductorPage);
		model.addAttribute("page",pageRender);	
		model.addAttribute("PageTitulo", "Conductores");
		return "Conductores";
	}
		

	
	@RequestMapping(value="/FormCond")
	public String crear(Map<String,Object> model,Authentication authentication) {
		
		var user="";
		var ads="";
		ads = authentication.getName();

		if(hasRole("ROLE_ADMIN")) {user ="ROLE_ADMIN";model.put("usuario",user);}else {if(hasRole("ROLE_USER")) {user = "ROLE_USER";model.put("usuario",user);}};
		
		//USUARIO
		if(user.equals("ROLE_USER")) {
			 
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);
			Conductor cond = new Conductor();	
			cond.setAdscripcion(usus.getAdscripcion());
			cond.setFecha_alta(SystemDate.obtenFecha());
			falta_conductor=cond.getFecha_alta();
			model.put("nombreAds",usus.getAdscripcion().getNombre_adscripcion());
			model.put("adscripcion",cond.getAdscripcion());
			model.put("conductor", cond);
			model.put("titulo", "Formulario de Conductores");
			return "FormCond";
		};
		
		//ADMINISTRADOR
		adscripcion = adscripService.findAll();
		Conductor cond = new Conductor();	
		cond.setFecha_alta(SystemDate.obtenFecha());
		falta_conductor=cond.getFecha_alta();
		model.put("adslist",adscripcion );
		model.put("conductor", cond);
		model.put("titulo", "Formulario de Conductores");	
		return "FormCond";
	}
	
	
	
	@RequestMapping(value="/FormCond/{no_empleado}")
	public String editar(@PathVariable(value="no_empleado") String no_empleado,Map<String,Object>model) {		

		Conductor conductor = null;	
		adscripcion = adscripService.findAll();
		editar = true;
		var user="";	
	
		if(hasRole("ROLE_ADMIN")) {user ="ROLE_ADMIN";model.put("usuario",user);}else {if(hasRole("ROLE_USER")) {user = "ROLE_USER";model.put("usuario",user);}};	
	
		if(!no_empleado.equals(null)) {
			conductor = conductorService.findOne(no_empleado);	
			empleado = conductor.getNo_empleado();
		}else {
			return "redirect:/Conductores";
		}
		
		ealta_conductor=conductor.getFecha_alta();
		model.put("nombreAds",conductor.getAdscripcion().getNombre_adscripcion());
		model.put("adslist",adscripcion );		
		model.put("conductor",conductor);
		model.put("titulo", "Editar cliente");
		return "FormCond";
	}
	
	
	
	@RequestMapping(value="/FormCond",method = RequestMethod.POST)
	public String guardar(Conductor conductor,Authentication authentication){
		
		conductores = conductorService.findAll();	
		adscripcion = adscripService.findAll();
				
		if(editar==false) {
			 conductor.setFecha_alta(falta_conductor);
		}else {
			conductor.setFecha_alta(ealta_conductor);
		}
		
		if(editar==false) {
			for(Conductor usu:conductores) {
				if(conductor.getNo_empleado().equals(usu.getNo_empleado())) {
					return "redirect:/idDuplicadoCon/"+conductor.getNo_empleado();
				};	
			}
		}else{
																
				if( !empleado.equals(conductor.getNo_empleado())) {		
					//System.out.println(conductor.getNo_empleado()+" "+ empleado);
					return "redirect:/idDuplicadoConCrea/"+conductor.getNo_empleado()+"/"+empleado;
				}									
			
		};
		
		for(Adscripcion ads:adscripcion) {
			if(ads.getId_adscripcion() == conductor.getAdscripcion().getId_adscripcion()) {
				conductor.setAdscripcion(ads);
			};
		}
		conductorService.save(conductor);
		editar = false;
		return "redirect:Conductores";
	}
	
	
	
	//@RequestMapping(value="/elimCond/{id_adscripcion}")
	@RequestMapping(value="/elimCond/{no_empleado}")
	public String eliminar (@PathVariable(value="no_empleado")String no_empleado) {
		
		Conductor condu = new Conductor();
		condu=conductorService.findOne(no_empleado);

		if(no_empleado != "") {
			//conductorService.delete(no_empleado);
			condu.setEnabled(false);
			condu.setFecha_baja(SystemDate.obtenFecha());
			conductorService.save(condu);
		}
		return "redirect:/Conductores";
	}	
	
	
	@RequestMapping(value="/estadoCond/{no_empleado}/{enabled}/{Corddocu}/{Cordtabla}")
	public String estado (@PathVariable(value="no_empleado")String no_empleado,@PathVariable(value="enabled")Integer enabled,
			@PathVariable(value="Corddocu")int docu,@PathVariable(value="Cordtabla")int tabla,@RequestParam(name="page", defaultValue = "0") int page) {
		Conductor cond = new Conductor();
		boolean seteo = false;
		Corddocu =docu;
		Cordtabla = tabla;
			cond = conductorService.findOne(no_empleado);
			switch (enabled) {
			case 1:
								seteo=false;
								cond.setEnabled(seteo);
				break;

			case 0:
								seteo=true;
								cond.setEnabled(seteo);				
				break;
				
					};
		
		conductorService.save(cond);		
	return "redirect:/Conductores?page="+page;
	}
	
	
	
	@RequestMapping(value="/formCondBuscar")
	public String Buscartabla(@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(value="elemento") String elemento,Model model, Authentication authentication){						 
		Pageable pageRequest = PageRequest.of(page, 100);
		 Double Dato;
		 var ads="";		
		 ads = authentication.getName();
		 var user="";
		 String elementof="";
		 
		 if(hasRole("ROLE_ADMIN")) {user ="ROLE_ADMIN";	model.addAttribute("usuario",user);
			}else {if(hasRole("ROLE_USER")) user = "ROLE_USER"; model.addAttribute("usuario",user);}
		if(!elemento.isBlank()) {			
			if(isValidDouble(elemento)) {
					Dato = Double.parseDouble(elemento);
					DecimalFormat formt = new DecimalFormat("0");
					elemento = formt.format(Dato);
					elemento = elemento.replaceAll(",","");	
			};
			//USUARIO
			if(user == "ROLE_USER") {
				 Usuario usus = new Usuario();
					usus = usuarioService.findbyAdscripcion(ads);
					elementof = elemento.toUpperCase(); 
				    Page<Conductor> conductorespage = conductorService.findCondElemAreaPage(usus.getAdscripcion().getId_adscripcion(), elementof, pageRequest);
					PageRender<Conductor> pageRender = new PageRender<>("/formCondBuscar?elemento="+elementof, conductorespage);
					model.addAttribute("conductores",conductorespage);
					model.addAttribute("page",pageRender);
					model.addAttribute("elemento",elementof);
					model.addAttribute("PageTitulo", "Conductores");
					return "Conductores";
			};			
			
			//ADMINISTRADOR
			elementof = elemento.toUpperCase(); 
			Page<Conductor> conductorespage= conductorService.findCondElemnPage("%"+elementof+"%", pageRequest);			 									
			PageRender<Conductor> pageRender = new PageRender<>("/formCondBuscar?elemento="+elementof, conductorespage);
			model.addAttribute("conductores",conductorespage);
			model.addAttribute("page",pageRender);
			model.addAttribute("elemento",elementof);	
			model.addAttribute("PageTitulo", "Conductores");
			return "Conductores";
		}else {
			return "redirect:/Conductores";
		}	
		
}
	
	
	//BAJA CONDUCTOR
	
	@RequestMapping(value="/BajasCond", method = RequestMethod.GET)
	public String listar2(@RequestParam(name="page", defaultValue = "0") int page,Model model,Model model2, Authentication authentication) {
		var user="";
		var ads="";
		ads = authentication.getName();
		
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";						
			model.addAttribute("usuario",user);
		}else {
			if(hasRole("ROLE_USER")) {
				user = "ROLE_USER";
				model.addAttribute("usuario",user);				
			}
		}
					
		model.addAttribute("titulo_conductores","Bajas Conductor"); 	
		Pageable pageRequest = PageRequest.of(page, 100);
		
		// USUARIO
		if(user.equals("ROLE_USER")){
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);
			//ConductorArea = conductorService.findConductorArea(usus.getAdscripcion().getId_adscripcion());
			
			Page<Conductor> conductorareapage = conductorService.findConductorAreaBajasPage(usus.getAdscripcion().getId_adscripcion(), pageRequest);
			PageRender<Conductor> pageRenderArea = new PageRender<> ("/Conductores",conductorareapage);
			if(conductorService.totalConductoresBajas()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};			
			model.addAttribute("conductores",conductorareapage);
			model.addAttribute("page",pageRenderArea);			
			return "BajasCond";
		}
		
		// ADMINISTRADOR
		Page<Conductor> conductorPage = conductorService.findByCNn(pageRequest);
		PageRender<Conductor> pageRender = new PageRender<>("/Conductores",conductorPage);
		if(conductorService.totalConductoresBajas()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};	
		model.addAttribute("Corddocu",Corddocu);
		model.addAttribute("Cordtabla",Cordtabla);
		model.addAttribute("conductores",conductorPage);
		model.addAttribute("page",pageRender);	
		return "BajasCond";
	}


	
	@RequestMapping(value="/RecuperarCond/{no_empleado}")
	public String recuperar (@PathVariable(value="no_empleado")String no_empleado) {
		Conductor condu = new Conductor();
		condu=conductorService.findOne(no_empleado);

		if(no_empleado != "") {
			//conductorService.delete(no_empleado);
			condu.setEnabled(true);
			condu.setFecha_baja(null);
			conductorService.save(condu);
		}
		return "redirect:/BajasCond";
	}	
	
	
	
	@RequestMapping(value="/formCondBajaBuscar")
	public String BuscarBajatabla(@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(value="elemento") String elemento,Model model, Authentication authentication){						 
		Pageable pageRequest = PageRequest.of(page, 100);
		 Double Dato;
		 var ads="";		
		 ads = authentication.getName();
		 var user="";
		 String elementof="";
		 if(hasRole("ROLE_ADMIN")) {user ="ROLE_ADMIN";	model.addAttribute("usuario",user);
			}else {if(hasRole("ROLE_USER")) user = "ROLE_USER"; model.addAttribute("usuario",user);}
		if(!elemento.isBlank()) {			
			if(isValidDouble(elemento)) {
					Dato = Double.parseDouble(elemento);
					DecimalFormat formt = new DecimalFormat("0");
					elemento = formt.format(Dato);
					elemento = elemento.replaceAll(",","");
					 System.err.println("gatts: "+elemento);
			};
			
			if(user == "ROLE_USER") {
				 Usuario usus = new Usuario();
					usus = usuarioService.findbyAdscripcion(ads);
					elementof = elemento.toUpperCase();
				    Page<Conductor> conductorespage = conductorService.findCondElemAreaBajasPage(usus.getAdscripcion().getId_adscripcion(), elementof, pageRequest);
					PageRender<Conductor> pageRender = new PageRender<>("/formCondBajaBuscar?elemento="+elementof, conductorespage);
					model.addAttribute("conductores",conductorespage);
					model.addAttribute("page",pageRender);
					model.addAttribute("elemento",elementof);
					return "BajasCond";
			};	
			
			elementof = elemento.toUpperCase();
			Page<Conductor> conductorespage= conductorService.findCondElemnBajasPage("%"+elementof+"%", pageRequest);			 									
			PageRender<Conductor> pageRender = new PageRender<>("/formCondBajaBuscar?elemento="+elementof, conductorespage);
			model.addAttribute("conductores",conductorespage);
			model.addAttribute("page",pageRender);
			model.addAttribute("elemento",elementof);	
			return "BajasCond";
			
		} else {
			return "redirect:/BajasCond";
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

	private boolean hasRole(String role) {
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
