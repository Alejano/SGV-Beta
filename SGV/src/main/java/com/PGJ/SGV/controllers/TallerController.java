package com.PGJ.SGV.controllers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.PGJ.SGV.models.entity.Authority;
import com.PGJ.SGV.models.entity.Mantenimiento;
import com.PGJ.SGV.models.entity.Taller;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.service.IAutoridadService;
import com.PGJ.SGV.service.ITallerService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.util.paginador.PageRender;
import com.PGJ.SGV.utilities.SystemDate;

@Controller
public class TallerController {
	
	@Autowired
	private ITallerService tallerservice;
	@Autowired
	private IAutoridadService autoservice;
	@Autowired
	private IUsuarioService ususervice;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	public static boolean editar = false;
	@RequestMapping(value="/Talleres", method = RequestMethod.GET)
	public String listar(Model model, Authentication authentication) {				
				
		List<Taller> talleres = new ArrayList<Taller>();
		
		talleres = tallerservice.findAll();
				
		model.addAttribute("talleres",talleres);		
		
		return "Talleres/Talleres";
	}
	
	@RequestMapping(value="/AddTaller")
	public String crear(Map<String,Object> model) {	
		editar=false;
		Taller taller = new Taller();				
		
		model.put("talleres", taller);
		return "Talleres/formTaller";
	}
	
	@RequestMapping(value="/AddTaller",method = RequestMethod.POST)
	public String guardar(Taller taller) {	
		Authority auto = new Authority();
		auto = autoservice.findOne(3);
		/*Usuario Usu = new Usuario();
		Usu.setNo_empleado(taller.getNombre());
		Usu.setNombre("");
		Usu.setApellido1("");
		Usu.setApellido2("");
		Usu.setAuthority(auto);
		Usu.setEnabled(true);
		Usu.setFecha_alta(SystemDate.obtenFecha());
		String pass = ObtenPass(taller.getNo_contrato());
		Usu.setContrasena(pass);
		ususervice.save(Usu);*/
		tallerservice.save(taller);
				
		editar = false;	
		return "redirect:Talleres";
	}
/*
	private String ObtenPass(String no_contrato) {
		String bcryptPassword="";
		for(int i=0; i<450; i++) {
		 bcryptPassword = passwordEncoder.encode(no_contrato);
			
		}
		
		return bcryptPassword;		
	}*/
	
	@RequestMapping(value="/formTallerBuscar")
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
							
			
			Page<Taller> tallerespage= tallerservice.FindTallerElemPageL(elemento, pageRequest);			 									 
			PageRender<Taller> pageRender = new PageRender<>("/formTallerBuscar?elemento="+elemento, tallerespage);
			
			model.addAttribute("talleres",tallerespage);
			model.addAttribute("page",pageRender);
			model.addAttribute("elemento",elemento);	
			return "Talleres/Talleres";
		}else {
			return "redirect:/Talleres/Talleres";
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
	
	
	

}
