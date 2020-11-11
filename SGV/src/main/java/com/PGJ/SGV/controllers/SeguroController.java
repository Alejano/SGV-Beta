package com.PGJ.SGV.controllers;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import com.PGJ.SGV.models.entity.Aseguradora;
import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.models.entity.Seguro;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.service.IAseguradoraService;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.ISeguroService;
import com.PGJ.SGV.service.IUploadFileService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.util.paginador.PageRender;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;


@Controller
public class SeguroController {
	
	List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
	List<Seguro> seguros = new ArrayList<Seguro>();
	List<Aseguradora> aseguradora = new ArrayList<Aseguradora>();

	static String user="";
	@Autowired
	private ISeguroService seguroService;
	@Autowired
	private IAseguradoraService aseguradoraService;
	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private IUploadFileService uploadFileService;
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private ILogsAuditService logsauditService;

	
	boolean editar = false;
	boolean aux=false;
	Long id_vehi;
	
	@RequestMapping(value="/Seguros", method = RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue = "0") int page,Model model,Authentication authentication) {	
			
		aux=false;
		var ads="";			
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

		ads = authentication.getName();
		Pageable pageRequest = PageRequest.of(page, 100);
		
		if(user.equals("ROLE_USER")){
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);
			
			Page<Seguro> SeguroAreaPage = seguroService.FindSeguroAreaPage(usus.getAdscripcion().getId_adscripcion(), pageRequest);
			PageRender<Seguro> SeguroRenderArea = new PageRender<>("/SegurosVehi",SeguroAreaPage);
			if(seguroService.totalSeguroAreaPage(usus.getAdscripcion().getId_adscripcion())>=6) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};

			model.addAttribute("titulo","Listado de Seguros");
			model.addAttribute("auxiliar", aux);
			model.addAttribute("seguros",SeguroAreaPage);
			model.addAttribute("page",SeguroRenderArea);
			return "SegurosVehi";
		}
	
		Page<Seguro> SeguroPage = seguroService.findAll(pageRequest);
		PageRender<Seguro> SeguroRender = new PageRender<>("/SegurosVehi",SeguroPage);
		if(seguroService.totalSeguros()>=6) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
		System.err.println(seguroService.totalSeguros());
		
		model.addAttribute("titulo","Listado de Seguros");
		model.addAttribute("auxiliar", aux);
		model.addAttribute("seguros",SeguroPage);
		model.addAttribute("page",SeguroRender);
		return "SegurosVehi";
		
	}
		
	@RequestMapping(value="/ListarSeguros/{id_vehiculo}", method = RequestMethod.GET)
	public String listarpv(@PathVariable(value="id_vehiculo") Long id_vehiculo,@RequestParam(name="page", defaultValue = "0") int page,Model model,Authentication authentication) {	
	
		aux=true;
		var ads="";		
		id_vehi=id_vehiculo;
		
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
		
		
		ads = authentication.getName();
		Pageable pageRequest = PageRequest.of(page, 100);
		
		//if(user.equals("ROLE_USER")){
			//Usuario usus = new Usuario();
			//usus = usuarioService.findbyAdscripcion(ads);
			Vehiculo vehiculo = new Vehiculo();
			vehiculo = vehiculoService.findOne(id_vehiculo);
			
			//segurosArea = seguroService.FindSeguroArea(usus.getAdscripcion().getId_adscripcion());
			//Page<Seguro> SeguroAreaPage = seguroService.FindsegVehiArea(id_vehiculo, usus.getAdscripcion().getId_adscripcion(), pageRequest);
			Page<Seguro> SeguroPage = seguroService.FindsegVehi(vehiculo.getId_vehiculo(),pageRequest);
			PageRender<Seguro> SeguroRenderArea = new PageRender<>("/SegurosVehi",SeguroPage);
			if(seguroService.totalSeguros()>=6) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
			model.addAttribute("titulo","Listado de Seguros");
			model.addAttribute("id_vehiculo",vehiculo.getId_vehiculo());
			model.addAttribute("auxiliar", aux);
			model.addAttribute("seguros",SeguroPage);
			model.addAttribute("page",SeguroRenderArea);
			return "SegurosVehi";
		//}
	
		/*Vehiculo vehiculo = new Vehiculo();
		vehiculo = vehiculoService.findOne(id_vehiculo);
		Page<Seguro> SeguroPage = seguroService.FindsegVehi(vehiculo.getId_vehiculo(),pageRequest);
		PageRender<Seguro> SeguroRender = new PageRender<>("/SegurosVehi",SeguroPage);
		if(seguroService.totalSeguros()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
		model.addAttribute("titulo","Listado de Polizas");
		model.addAttribute("id_vehiculo",vehiculo.getId_vehiculo());
		model.addAttribute("auxiliar", aux);
		model.addAttribute("seguros",SeguroPage);
		model.addAttribute("page",SeguroRender);
		return "SegurosVehi";
		*/
	}

	
	@RequestMapping(value="/formSeg/Ag/{id_vehiculo}")
	public String crear(@PathVariable(value="id_vehiculo") Long id_vehiculo,Map<String,Object> model,Authentication authentication) {

		editar=false;
		var ads="";						
		ads = authentication.getName();
		var user="";
		Seguro seguro = new Seguro();
		Vehiculo vehi= new Vehiculo();
		
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";	model.put("usuario",user);
			}else {
				if(hasRole("ROLE_USER")) {
					user = "ROLE_USER"; model.put("usuario",user);
				}else {
					if(hasRole("ROLE_SEGURO")) {
						user = "ROLE_SEGURO"; model.put("usuario",user);
					}
				}	
			}
		
		if(user.equals("ROLE_USER")){
			
			/*List<Vehiculo> Vehi = new ArrayList<Vehiculo>();
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);
			Vehi = vehiculoService.findVehiculosArea(usus.getAdscripcion().getId_adscripcion());
			
			model.put("vehiculos", Vehi);
			model.put("seguros", seguro);
			model.put("titulo", "Formulario de Seguros");
			return "formSeg";*/
		};
		
		vehi = vehiculoService.findOne(id_vehiculo);
		seguro.setVehiculo(vehi);
		aseguradora = aseguradoraService.findAll();
		
		model.put("vehiculos", vehi);
		model.put("id_vehiculo", vehi.getId_vehiculo());
		model.put("editar", editar);
		model.put("seguros", seguro);
		model.put("aseguradoras", aseguradora);
		model.put("titulo", "Formulario de Seguros");	
		return "formSeg";
		
	}
	
	
	@RequestMapping(value="/formSeg/{id_seguro}")
	public String editar(@PathVariable(value="id_seguro") Long id_seguro,Map<String,Object>model) {
		
		editar=true;
		Seguro seguro = null;
		
		if(!id_seguro.equals(null)) {
			seguro = seguroService.findOne(id_seguro);
			System.err.println(id_seguro);
		}else {
			return "redirect:/seguros";
		}
		model.put("id_vehiculo", seguro.getVehiculo().getId_vehiculo());
		model.put("editar", editar);
		model.put("seguros",seguro);
		model.put("titulo", "Editar Seguro");
		return "formSeg";
	}
	
	
	@RequestMapping(value ="/verSeguro/{id_seguro}")
	public String ver(@PathVariable(value = "id_seguro") Long id_seguro, Map<String, Object> model) {

		Seguro seguro = null;
		String documento = "existe";
		if (!id_seguro.equals(null)) {
			seguro = seguroService.findOne(id_seguro);
			if(seguro.getUrl_poliza().isBlank())
			{documento = "";};
		} else {
			return "redirect:Seguros";
		}
        
		model.put("id_vehiculo",seguro.getVehiculo().getId_vehiculo());
		model.put("vehiculo", vehiculos);
		model.put("documento", documento);
		model.put("seguro", seguro);
		model.put("titulo", "Ver Seguro");
		return "verSeguro";
		
	}
	
	
	@RequestMapping(value = "/formSeg", method = RequestMethod.POST)
	public String guardar(Seguro seguro, @RequestParam("file") MultipartFile documento,Authentication authentication,Map<String,Object> model) {
		   
		   var user="";
		   var no_user ="";
		   no_user = authentication.getName();
				
			
					if(hasRole("ROLE_ADMIN")) {
						user ="ROLE_ADMIN";
						}else {
							if(hasRole("ROLE_USER")) {
								user = "ROLE_USER";
							}else {
								if(hasRole("ROLE_SEGURO")) {
									user = "ROLE_SEGURO";
								}
							}	
						}
					
		Vehiculo vehiculos =new Vehiculo();
		
		if (!documento.isEmpty()) {
			if( seguro.getUrl_poliza().length() > 0 ) {	
				uploadFileService.delete(seguro.getUrl_poliza());						
			}
			String nombreUnico = null;						
				try {
					nombreUnico = uploadFileService.copy(documento);
					seguro.setUrl_poliza(nombreUnico);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
		}
				
		if(editar == true) {
			
			//Seguro OLD
		    
			Seguro seguro_old = null;
			seguro_old = seguroService.findOne(seguro.getId_seguro());
		    System.err.println("old:"+seguro_old.toString());
		    String valor_old = seguro_old.toString();
		    		    
			System.err.println("entroeditar: "+seguro.getId_seguro());
			vehiculos = vehiculoService.findOne(seguro.getVehiculo().getId_vehiculo());	
			seguro.setVehiculo(vehiculos);
			seguroService.save(seguro);
			
			//Auditoria
			
         	LogsAudit logs = new LogsAudit();
         	
            logs.setId_usuario(no_user);
			logs.setTipo_role(user);
			logs.setFecha(SystemDate.obtenFecha());
			logs.setHora(ObtenHour.obtenHour());
			logs.setName_table("SEGUROS");
			logs.setValor_viejo(valor_old);
			logs.setTipo_accion("UPDATE");
									
			logsauditService.save(logs);
			
			editar = false;	
			
		}else {
		
	    System.err.println("entrocrear: "+seguro.getId_seguro());
		vehiculos = vehiculoService.findOne(seguro.getVehiculo().getId_vehiculo());						
		seguro.setVehiculo(vehiculos);		
		seguroService.save(seguro);
		String valor_nuevo=seguro.toString();
		
		//Auditoria
		
     	LogsAudit logs = new LogsAudit();
     	
        logs.setId_usuario(no_user);
		logs.setTipo_role(user);
		logs.setFecha(SystemDate.obtenFecha());
		logs.setHora(ObtenHour.obtenHour());
		logs.setName_table("SEGUROS");
		logs.setValor_viejo(valor_nuevo);
		logs.setTipo_accion("INSERT");
								
		logsauditService.save(logs);
		
		}
		
		return "redirect:ListarSeguros/"+seguro.getVehiculo().getId_vehiculo();
	
    } 

	
	@RequestMapping(value="/elimSeg/{id_seguro}/{documento}")
	public String eliminar (@PathVariable(value="id_seguro")Long id_seguro,@PathVariable(value="documento")String documento) {
		
		if(id_seguro != null) {
			seguroService.delete(id_seguro);	
			if(documento != "") {
				uploadFileService.delete(documento);
			}
		}
		return "redirect:/Vehiculos";
	}
	
	
	@RequestMapping(value="/elimSeg/{id_seguro}/")
	public String eliminar (@PathVariable(value="id_seguro")Long id_seguro) {
		
		if(id_seguro != null) {
			seguroService.delete(id_seguro);				
		}
		return "redirect:/Vehiculos";
	}
	
	
	@RequestMapping(value="/formSegBuscarPv")
	public String Buscartablapv(@RequestParam(name="page", defaultValue = "0") int page,Authentication authentication,
		@RequestParam(value="elemento") String elemento,Model model){	
		
		 aux=true;
		 Pageable pageRequest = PageRequest.of(page, 100);
		 var ads="";		
		 ads = authentication.getName();
		 String elementof="";
		 
			 if(user.equals("ROLE_ADMIN")) {
				 model.addAttribute("usuario",user);
				}else {
					if(user.equals("ROLE_USER")) { 
						model.addAttribute("usuario",user);
						}else {
							if(user.equals("ROLE_SEGURO")) {
								model.addAttribute("usuario",user);
							}
						}
					};
				
				
		 if(!elemento.isBlank()) {			
							if(isValidDouble(elemento)) {
								Double Dato = Double.parseDouble(elemento);
									DecimalFormat formt = new DecimalFormat("0");
									elemento = formt.format(Dato);
									elemento = elemento.replaceAll(",","");	
							};
						/*if(user.equals("ROLE_USER")) {
						 Usuario usus = new Usuario();
						 usus = usuarioService.findbyAdscripcion(ads);
						 elementof = elemento.toUpperCase(); 
						  //  Page<Seguro> segurospage = seguroService.FindSegElemVehiAreaPage(id_vehi, usus.getAdscripcion().getId_adscripcion(), elementof, pageRequest);
							Page<Seguro> segurospage= seguroService.FindSegElemVehiPage(id_vehi, elementof, pageRequest);
						    PageRender<Seguro> pageRender = new PageRender<>("/formSegBuscarPv?elemento="+elementof, segurospage);
							model.addAttribute("seguros",segurospage);
							model.addAttribute("auxiliar", aux);
							model.addAttribute("page",pageRender);
							model.addAttribute("elemento",elementof);
							model.addAttribute("id_vehiculo",id_vehi);
							return "SegurosVehi";
					};*/
					
					elementof = elemento.toUpperCase(); 
					Page<Seguro> segurospage= seguroService.FindSegElemVehiPage(id_vehi, elementof, pageRequest);
					PageRender<Seguro> pageRender = new PageRender<>("/formSegBuscarPv?elemento="+elementof, segurospage);
					if(seguroService.totalSegElemVehiPage(id_vehi, elementof)>=5) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
					
					model.addAttribute("seguros",segurospage);
					model.addAttribute("page",pageRender);
					model.addAttribute("elemento",elementof);	
					model.addAttribute("auxiliar", aux);
					model.addAttribute("id_vehiculo",id_vehi);
	     			return "SegurosVehi";		
		}else{
			return "redirect:/Seguros";
		}
		 
	}
	
	
	@RequestMapping(value="/formSegBuscar")
	public String Buscartabla(@RequestParam(name="page", defaultValue = "0") int page,Authentication authentication,
		@RequestParam(value="elemento") String elemento,Model model){	
		
		 aux=false;
		 Pageable pageRequest = PageRequest.of(page, 100);
		 var ads="";		
		 ads = authentication.getName();
		 String elementof="";
		 
				 if(user.equals("ROLE_ADMIN")) {
					 model.addAttribute("usuario",user);
					}else {
						if(user.equals("ROLE_USER")) { 
							model.addAttribute("usuario",user);
							}else {
								if(user.equals("ROLE_SEGURO")) {
									model.addAttribute("usuario",user);
								}
							}
						};
			 
		 if(!elemento.isBlank()) {			
							if(isValidDouble(elemento)) {
								Double Dato = Double.parseDouble(elemento);
									DecimalFormat formt = new DecimalFormat("0");
									elemento = formt.format(Dato);
									elemento = elemento.replaceAll(",","");	
							};
						if(user.equals("ROLE_USER")) {
						 Usuario usus = new Usuario();
						 usus = usuarioService.findbyAdscripcion(ads);
						 elementof = elemento.toUpperCase(); 
						  //  Page<Seguro> segurospage = seguroService.FindSegElemVehiAreaPage(id_vehi, usus.getAdscripcion().getId_adscripcion(), elementof, pageRequest);
							Page<Seguro> segurospage= seguroService.FindSegElemenAreaPage(usus.getAdscripcion().getId_adscripcion(), elementof, pageRequest);
						    PageRender<Seguro> pageRender = new PageRender<>("/formSegBuscar?elemento="+elementof, segurospage);
							if(seguroService.totalSegElemenAreaPage(usus.getAdscripcion().getId_adscripcion(), elementof)>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};

						    model.addAttribute("seguros",segurospage);
							model.addAttribute("auxiliar", aux);
							model.addAttribute("page",pageRender);
							model.addAttribute("elemento",elementof);
							model.addAttribute("id_vehiculo",id_vehi);
							return "SegurosVehi";
					};
					
					elementof = elemento.toUpperCase(); 
					Page<Seguro> segurospage= seguroService.FindSegElemenPage(elementof, pageRequest);
					PageRender<Seguro> pageRender = new PageRender<>("/formSegBuscar?elemento="+elementof, segurospage);
					if(seguroService.totalSegElemenPage(elementof)>=5) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};

					model.addAttribute("seguros",segurospage);
					model.addAttribute("page",pageRender);
					model.addAttribute("elemento",elementof);	
					model.addAttribute("auxiliar", aux);
					model.addAttribute("id_vehiculo",id_vehi);
	     			return "SegurosVehi";		
		}else{
			return "redirect:/Seguros";
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
}