package com.PGJ.SGV.controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.PGJ.SGV.models.entity.CatalogoServicio;
import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.models.entity.Mantenimiento;
import com.PGJ.SGV.models.entity.Taller;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IMantenimientoService;
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.service.ITallerService;
import com.PGJ.SGV.service.IUploadFileService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.util.paginador.PageRender;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;

@Controller
public class MantenimintoController {
	List<Mantenimiento> mantenimiento = new ArrayList<Mantenimiento>();
	List<Vehiculo> vehiculo = new ArrayList<Vehiculo>();
	List<Taller> taller = new ArrayList<Taller>();
	
    static boolean Editar = false;
	static boolean Mplaca = false;
	
	@Autowired
	private IMantenimientoService mantService;
	@Autowired
	private IVehiculoService vehiculoService;	
	@Autowired
	private IUsuarioService usuarioService;	
	@Autowired
	private ITallerService tallerService;
	@Autowired
	private IUploadFileService uploadFileService;
	@Autowired
	private ILogsAuditService logsauditService;
	@Autowired
	private IObtenerUserService obuserService;
	
	String user;
	
	@RequestMapping(value="/Mantenimientos", method = RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue = "0") int page,Model model, Model model2) {				
		
		Mplaca = false;
		
		var ads="";
		ads = obuserService.obtenEmpl();
		user = obuserService.obtenUser();
		model.addAttribute("usuario",user);	
	
		Pageable pageRequest = PageRequest.of(page, 100);
		
		if(user.equals("ROLE_USER")){
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);	
			
			Page<Mantenimiento> MantAreaPage = mantService.FindMantenimientoAreaPage(usus.getAdscripcion().getId_adscripcion(), pageRequest);
			PageRender<Mantenimiento> MantRenderArea= new PageRender<>("/Mantenimientos",MantAreaPage);
			if(mantService.totalMantenimiento()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
			model.addAttribute("PageTitulo", "Mantenimientos");
			model.addAttribute("PageSubTitulo", "Listado de Mantenimientos");
			model.addAttribute("ads",usus.getAdscripcion().getNombre_adscripcion());
			model.addAttribute("mantenimientos",MantAreaPage);
			model.addAttribute("page",MantRenderArea);
			model.addAttribute("Mplaca",Mplaca);
			
			return "Mantenimientos";
			
		}
		
		//mantenimiento = mantService.findAll();
		
		Page<Mantenimiento> MantPage = mantService.findAll(pageRequest);
		PageRender<Mantenimiento> MantRender= new PageRender<>("/Mantenimientos",MantPage);
		if(mantService.totalMantenimiento()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};	
		model.addAttribute("PageTitulo", "Mantenimientos");
		model.addAttribute("PageSubTitulo", "Listado de Mantenimientos");
		model.addAttribute("titulo","Listado de Mantenimientos");
		model.addAttribute("mantenimientos",MantPage);
		model.addAttribute("page",MantRender);
		model.addAttribute("Mplaca",Mplaca);
		
		return "Mantenimientos";
		
	}
	
	
	@RequestMapping(value="/Mantenimientos/{id_vehiculo}", method = RequestMethod.GET)
	public String listarPlaca(@RequestParam(name="page", defaultValue = "0") int page,@PathVariable(value="id_vehiculo") Long id_vehiculo,Model model) {
		
		Mplaca = true;
		//List<Mantenimiento> MantArea = new ArrayList<Mantenimiento>();
		Vehiculo vehi = new Vehiculo();
		vehi = vehiculoService.findOne(id_vehiculo);
		
		var ads="";
		ads = obuserService.obtenEmpl();
		user = obuserService.obtenUser();
		model.addAttribute("usuario",user);	
		
		Pageable pageRequest = PageRequest.of(page, 100);
			if(user.equals("ROLE_USER")){
				Usuario usus = new Usuario();
				usus = usuarioService.findbyAdscripcion(ads);	
				
				Page<Mantenimiento> mantAreaPage = mantService.FindMantPlacaAreaPage(vehi.getId_vehiculo(), usus.getAdscripcion().getId_adscripcion(), pageRequest);
				PageRender<Mantenimiento> mantRender = new PageRender<>("/Mantenimientos/{placa}",mantAreaPage);
				
				model.addAttribute("PageTitulo", "Mantenimientos");
				model.addAttribute("PageSubTitulo", "Listado de Mantenimientos de la placa: "+vehi.getPlaca());
				model.addAttribute("placa",vehi.getPlaca());	
				model.addAttribute("id_vehiculo",vehi.getId_vehiculo());
				model.addAttribute("mantenimientos",mantAreaPage);
				model.addAttribute("page",mantRender);
				model.addAttribute("Mplaca",Mplaca);
				
				return "Mantenimientos";
				
			}
			
			Page<Mantenimiento> mantPage = mantService.FindMantPlacaPage(vehi.getId_vehiculo(), pageRequest);
			PageRender<Mantenimiento> mantRender = new PageRender<>("/Mantenimientos/"+vehi.getPlaca(),mantPage);
				
		model.addAttribute("PageTitulo", "Mantenimientos");
		model.addAttribute("PageSubTitulo", "Listado de Mantenimientos de la placa: "+vehi.getPlaca());
		model.addAttribute("titulo","Listado de Mantenimientos");
		model.addAttribute("id_vehiculo",vehi.getId_vehiculo());
		model.addAttribute("placa",vehi.getPlaca());
		model.addAttribute("mantenimientos",mantPage);
		model.addAttribute("page",mantRender);
		model.addAttribute("Mplaca",Mplaca);
		
		return "Mantenimientos";
		
	}
		
	
	@RequestMapping(value="/VehiMant/{id_vehiculo}")
	public String crear(@PathVariable(value="id_vehiculo") long id_vehiculo,Map<String,Object> model) {	
		
				Editar = false;	
				Vehiculo vehi = new Vehiculo();	
				Mantenimiento mantenimiento = new Mantenimiento();
				
				String placa="";
				taller = tallerService.findAll();
				vehi = vehiculoService.findOne(id_vehiculo);	
				placa= vehiculoService.findPlaca(id_vehiculo);																			
				mantenimiento.setVehiculo(vehi);
				model.put("Editar", Editar);
				model.put("PageTitulo", "Agregar Mantenimiento");
				model.put("taller",taller);
				model.put("mantenimiento",mantenimiento );				
				model.put("placa", placa);								
				model.put("titulo", "Formulario de Mantenimiento");		
				
		return "formMant";
					
	}
	
	
	List<CatalogoServicio> iterar(List<CatalogoServicio> servicios, String buscar) {
		List<CatalogoServicio> lista = new ArrayList<CatalogoServicio>();
		
		switch (buscar) {
		case "auto":
				for(CatalogoServicio cs: servicios) {
					if(cs.isAuto()) {lista.add(cs);}
				}
			break ;
		case "moto":
			for(CatalogoServicio cs: servicios) {
				if(cs.isMotocicleta()) {lista.add(cs);}
			}
		break ;
		case "blin":
			for(CatalogoServicio cs: servicios) {
				if(cs.isBlindado()) {lista.add(cs);}
			}
		break ;
		case "disel":
			for(CatalogoServicio cs: servicios) {
				if(cs.isDiesel()) {lista.add(cs);}
			}
		break ;
		
		}
		
		return lista;
	}
	
	
	@RequestMapping(value="/formMant/{id_mantenimiento}")
	public String editar(@PathVariable(value="id_mantenimiento") Long id_mantenimiento,Map<String,Object>model) {
		
		Editar = true;			
		Mantenimiento mant = null;
		
		if(id_mantenimiento != null) {
			mant = mantService.findOne(id_mantenimiento);
		}else {
			return "redirect:/Mantenimientos";
		}
		
		model.put("Editar", Editar);
		model.put("vehiculo", vehiculo);
		model.put("PageTitulo", "Editar Mantenimiento");
		model.put("placa", mant.getVehiculo().getPlaca());
		model.put("mantenimiento",mant);
		model.put("titulo", "Editar Mantenimiento");
		
		return "formMant";
		
	}
	
	
	@RequestMapping(value="/formMant",method = RequestMethod.POST)
	public String guardar(Mantenimiento mantenimiento,@RequestParam("file") MultipartFile documento){			
			Vehiculo vehiculoselect =new Vehiculo();
			
			if (!documento.isEmpty()) {
				
					if( mantenimiento.getUrl_documentacion().length() > 0 ) {
						uploadFileService.delete(mantenimiento.getUrl_documentacion());						
					}
					String nombreUnico = null;						
						try {
							nombreUnico = uploadFileService.copy(documento);
							mantenimiento.setUrl_documentacion(nombreUnico);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}					
			}
			
					if(Editar == true) {
			
						Mantenimiento mantenimiento_old = null;
						mantenimiento_old = mantService.findOne(mantenimiento.getId_mantenimiento());
						String valor_old = mantenimiento_old.toString();
						
						vehiculoselect = vehiculoService.findOne(mantenimiento.getVehiculo().getId_vehiculo());	
						mantenimiento.setVehiculo(vehiculoselect);
						mantenimiento.setKilometraje(vehiculoselect.getKilometraje_inicial());						
						mantService.save(mantenimiento);
						
						//Auditoria
						
		             	LogsAudit logs = new LogsAudit();
		             	
		             	logs.setId_usuario(obuserService.obtenEmpl());
		        		logs.setTipo_role(obuserService.obtenUser());
						logs.setFecha(SystemDate.obtenFecha());
						logs.setHora(ObtenHour.obtenHour());
						logs.setName_table("MANTENIMIENTOS");
						logs.setValor_viejo(valor_old);
						logs.setTipo_accion("UPDATE");
												
						logsauditService.save(logs);
						Editar = false;	
						
					}else {
					
					vehiculoselect = vehiculoService.findOne(mantenimiento.getVehiculo().getId_vehiculo());							
					mantenimiento.setVehiculo(vehiculoselect);
					mantenimiento.setKilometraje(vehiculoselect.getKilometraje_inicial());					
					mantService.save(mantenimiento);
					String valor_nuevo=mantenimiento.toString();
					
					//Auditoria
					
		         	LogsAudit logs = new LogsAudit();
		         	
		         	logs.setId_usuario(obuserService.obtenEmpl());
		    		logs.setTipo_role(obuserService.obtenUser());
					logs.setFecha(SystemDate.obtenFecha());
					logs.setHora(ObtenHour.obtenHour());
					logs.setName_table("MANTENIMIENTOS");
					logs.setValor_viejo(valor_nuevo);
					logs.setTipo_accion("INSERT");
											
					logsauditService.save(logs);
					
					}
								
		return "redirect:/Mantenimientos/"+mantenimiento.getVehiculo().getId_vehiculo();
	}
	
	
	@RequestMapping(value="/elimMant/{id_mantenimiento}")
	public String eliminar (@PathVariable(value="id_mantenimiento")Long id_mantenimiento) {
		
		if(id_mantenimiento != null) {
			mantService.delete(id_mantenimiento);
		}
		return "redirect:/Mantenimientos";
	}
	
	
	@RequestMapping(value="/formMantBuscar")
	public String Buscartabla(@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(value="elemento") String elemento,Model model){						 
		 Pageable pageRequest = PageRequest.of(page, 100);
		 Double Dato;

		 var ads="";
		 ads = obuserService.obtenEmpl();
		 user = obuserService.obtenUser();
		 model.addAttribute("usuario",user);	
		
		if(!elemento.isBlank()) {			
				if(isValidDouble(elemento)) {
						Dato = Double.parseDouble(elemento);
						DecimalFormat formt = new DecimalFormat("0");
						elemento = formt.format(Dato);
						elemento = elemento.replaceAll(",","");	
				};
			
				 if(user == "ROLE_USER") {
					 Usuario usus = new Usuario();
						usus = usuarioService.findbyAdscripcion(ads);
						
						Page<Mantenimiento> mantspage = mantService.FindMantelemntAreaPage(usus.getAdscripcion().getId_adscripcion(),"%"+elemento+"%", pageRequest);
						PageRender<Mantenimiento> pageRender = new PageRender<>("/formMantBuscar?elemento="+elemento, mantspage);
						model.addAttribute("mantenimientos",mantspage);
						model.addAttribute("page",pageRender);
						model.addAttribute("elemento",elemento);
						model.addAttribute("PageTitulo", "Mantenimientos");
						model.addAttribute("PageSubTitulo", "Listado de Mantenimientos");
						return "Mantenimientos";
				 };
			
			Page<Mantenimiento> mantspage= mantService.FindMantElemPage(elemento, pageRequest);			 									 
			PageRender<Mantenimiento> pageRender = new PageRender<>("/formMantBuscar?elemento="+elemento, mantspage);
			model.addAttribute("mantenimientos",mantspage);
			model.addAttribute("page",pageRender);
			model.addAttribute("elemento",elemento);	
			model.addAttribute("PageTitulo", "Mantenimientos");
			model.addAttribute("PageSubTitulo", "Listado de Mantenimientos");
			return "Mantenimientos";
		}else {
			return "redirect:/Mantenimientos";
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
