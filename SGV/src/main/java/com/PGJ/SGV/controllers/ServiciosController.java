package com.PGJ.SGV.controllers;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;
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
import com.PGJ.SGV.models.entity.CatalogoServicio;
import com.PGJ.SGV.service.ICatalogoServicioService;
import com.PGJ.SGV.service.IUploadFileService;
import com.PGJ.SGV.util.paginador.PageRender;


@Controller
public class ServiciosController {
	@Autowired
	private ICatalogoServicioService catalogoServicio;
	
	@Autowired
	private IUploadFileService uploadFileService;
	static int 	Corddocu = 0;
	static int 	Cordtabla = 0;
	
	
	@RequestMapping(value="/Servicios", method = RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue = "0") int page,Model model, Authentication authentication) {				
	
		Pageable pageRequest = PageRequest.of(page, 100);
		
		var user="";
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";						
			model.addAttribute("usuario",user);
		}else {
			if(hasRole("ROLE_USER")) {
				user = "ROLE_USER";
				model.addAttribute("usuario",user);				
			}
		}
		
		
		Page<CatalogoServicio> catalogoPage = catalogoServicio.findAll(pageRequest);
		PageRender<CatalogoServicio> catalogorender = new PageRender<>("/Servicios",catalogoPage);
		
		if(catalogoServicio.totalCatalogoServicios()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
		model.addAttribute("Corddocu",Corddocu);
		model.addAttribute("Cordtabla",Cordtabla);
		model.addAttribute("servicios",catalogoPage);
		model.addAttribute("page", catalogorender);
		
		return "Servicios";
	}
	
	@RequestMapping(value="/estado/{campo}/{id}/{enabled}/{Corddocu}/{Cordtabla}")
	public String estado (@PathVariable(value="id")Long id,@PathVariable(value="enabled")boolean enabled,@PathVariable(value="campo")String campo,
			@PathVariable(value="Corddocu")int docu,@PathVariable(value="Cordtabla")int tabla,@RequestParam(name="page", defaultValue = "0") int page) {
		CatalogoServicio uss = new CatalogoServicio();
		boolean seteo = false;			
		uss = catalogoServicio.findOne(id);
		Corddocu =docu;
		Cordtabla = tabla;
		
		switch (campo) {
		case "Auto":
				if(enabled) {seteo=false; uss.setAuto(seteo);}else {seteo=true;uss.setAuto(seteo);	}
			break;
		case "Moto":
			if(enabled) {seteo=false; uss.setMotocicleta(seteo);}else {seteo=true;uss.setMotocicleta(seteo);}
		break;
		case "Blin":
			if(enabled) {seteo=false; uss.setBlindado(seteo);}else {seteo=true;uss.setBlindado(seteo);}
		break;
		case "uno":
			if(enabled) {seteo=false; uss.setUn_cilindro(seteo);}else {seteo=true;uss.setUn_cilindro(seteo);}
		break;		
		case "dos":
			if(enabled) {seteo=false; uss.setDos_cilindros(seteo);}else {seteo=true;uss.setDos_cilindros(seteo);}
		break;
		case "tres":
			if(enabled) {seteo=false; uss.setTres_cilindros(seteo);}else {seteo=true;uss.setTres_cilindros(seteo);}
		break;
		case "cuatro":
			if(enabled) {seteo=false; uss.setCuatro_cilindros(seteo);}else {seteo=true;uss.setCuatro_cilindros(seteo);}
		break;
		case "cinco":
			if(enabled) {seteo=false; uss.setCinco_cilindros(seteo);}else {seteo=true;uss.setCinco_cilindros(seteo);}
		break;
		case "seis":
			if(enabled) {seteo=false; uss.setSeis_cilindros(seteo);}else {seteo=true;uss.setSeis_cilindros(seteo);}
		break;
		case "ocho":
			if(enabled) {seteo=false; uss.setOcho_cilindros(seteo);}else {seteo=true;uss.setOcho_cilindros(seteo);}
		break;
		case "dies":
			if(enabled) {seteo=false; uss.setDies_cilindros(seteo);}else {seteo=true;uss.setDies_cilindros(seteo);}
		break;
		case "gaso":
			if(enabled) {seteo=false; uss.setGasolina(seteo);}else {seteo=true;uss.setGasolina(seteo);}
		break;
		case "die":
			if(enabled) {seteo=false; uss.setDiesel(seteo);}else {seteo=true;uss.setDiesel(seteo);}
		break;
		case "gas":
			if(enabled) {seteo=false; uss.setGas(seteo);}else {seteo=true;uss.setGas(seteo);}
		break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + campo);
		}
		
		
									
		catalogoServicio.save(uss);		
		
	return "redirect:/Servicios?page="+page;
	}
	
	@RequestMapping(value="/formServ")
	public String crear(Map<String,Object> model) {
		CatalogoServicio catalogo = new CatalogoServicio();
		
		model.put("catalogo", catalogo);								
		return "formServ";
	}
	
	@RequestMapping(value="/formServ/{id}")
	public String editar(@PathVariable(value="id") Long id,Map<String,Object>model) {
	
		CatalogoServicio catalogo = null;
		
		if(!id.equals(null)) {
			catalogo = catalogoServicio.findOne(id);			
		}else {
			return "redirect:/Servicios";
		}
		
		model.put("catalogo", catalogo);			
		return "formServ";
	}
	
	@RequestMapping(value = "/formServ", method = RequestMethod.POST)
	public String guardar(CatalogoServicio catalogo) {
		
		catalogoServicio.save(catalogo);			
	
		return "redirect:/Servicios";
	}

	
	@RequestMapping(value="/elimServ/{id}")
	public String eliminar (@PathVariable(value="id")Long id) {
		
		if(id != null) {
			catalogoServicio.delete(id);							
		}
		return "redirect:/Servicios";
	}
	
	@RequestMapping(value = "/Servicios/upload", method = RequestMethod.POST)
	public String Subir(@RequestParam("file") MultipartFile documento) {
		
		if (!documento.isEmpty()) {
			uploadFileService.deleteall();
			try {
				uploadFileService.deleteall();
				uploadFileService.copyImport(documento);
				uploadFileService.importarServicios();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return "redirect:/Servicios";
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
	
	@RequestMapping(value="/formServBuscar")
	public String Buscartabla(@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(value="elemento") String elemento,Model model, Authentication authentication){	
		Pageable pageRequest = PageRequest.of(page, 100);
		 Double Dato;
		 
		 if(!elemento.isBlank()) {			
				if(isValidDouble(elemento)) {
						Dato = Double.parseDouble(elemento);
						DecimalFormat formt = new DecimalFormat("0");
						elemento = formt.format(Dato);
						elemento = elemento.replaceAll(",","");	
				};
		 
		 Page<CatalogoServicio> usuariospage = catalogoServicio.finCatElemntPage(elemento, pageRequest);
		 PageRender<CatalogoServicio> renderpage = new PageRender<>("/formServBuscar",usuariospage);
		 
		 model.addAttribute("servicios",usuariospage);
		 model.addAttribute("page",renderpage);
		 model.addAttribute("elemento",elemento);
		 return "Servicios";
		 }
		 
		return "redirect:/Servicios";
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


}
