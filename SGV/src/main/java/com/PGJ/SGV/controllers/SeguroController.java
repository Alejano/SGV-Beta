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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.PGJ.SGV.models.entity.Aseguradora;
import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.models.entity.Seguro;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.service.IAseguradoraService;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IObtenerUserService;
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
	@Autowired
	private IObtenerUserService obuserService;
	
	boolean editar = false;
	boolean aux=false;
	Long id_vehi;
	String id_placa;
	String user;
	
	@RequestMapping(value="/Seguros", method = RequestMethod.GET)
		public String listar(@RequestParam(name="page", defaultValue = "0") int page,Model model) {	

		aux=false;
		var ads="";
		ads = obuserService.obtenEmpl();
		user = obuserService.obtenUser();
		model.addAttribute("usuario",user);	
		
		Pageable pageRequest = PageRequest.of(page, 100);
		
		if(user.equals("ROLE_USER")){
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);
			
			Page<Seguro> SeguroAreaPage = seguroService.FindSeguroAreaPage(usus.getAdscripcion().getId_adscripcion(), pageRequest);
			PageRender<Seguro> SeguroRenderArea = new PageRender<>("/SegurosVehi",SeguroAreaPage);
			if(seguroService.totalSeguroAreaPage(usus.getAdscripcion().getId_adscripcion())>=6) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};

			model.addAttribute("titulo","Listado de Seguros");
			model.addAttribute("PageTitulo", "Seguros");
			model.addAttribute("PageSubTitulo", "Listado de Seguros");
			model.addAttribute("ads",usus.getAdscripcion().getNombre_adscripcion());
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
		model.addAttribute("PageTitulo", "Seguros");
		model.addAttribute("PageSubTitulo", "Listado de Seguros");
		model.addAttribute("auxiliar", aux);
		model.addAttribute("seguros",SeguroPage);
		model.addAttribute("page",SeguroRender);
		return "SegurosVehi";
		
	}
		
	
	@RequestMapping(value="/ListarSeguros/{id_vehiculo}", method = RequestMethod.GET)
	public String listarpv(@PathVariable(value="id_vehiculo") Long id_vehiculo,@RequestParam(name="page", defaultValue = "0") int page,Model model) {	
	
		aux=true;
		user = obuserService.obtenUser();
		model.addAttribute("usuario",user);		
		id_vehi=id_vehiculo;
	
		
		Pageable pageRequest = PageRequest.of(page, 100);
	
			Vehiculo vehiculo = new Vehiculo();
			vehiculo = vehiculoService.findOne(id_vehiculo);
			id_placa=vehiculo.getPlaca();

			Page<Seguro> SeguroPage = seguroService.FindsegVehi(vehiculo.getId_vehiculo(),pageRequest);
			PageRender<Seguro> SeguroRenderArea = new PageRender<>("/SegurosVehi",SeguroPage);
			if(seguroService.totalSegurosVehi(id_vehiculo)>=6) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
			
			model.addAttribute("titulo","Listado de Seguros");
			model.addAttribute("PageTitulo", "Seguros");
			model.addAttribute("PageSubTitulo", "Listado de Seguros de la placa: "+vehiculo.getPlaca());
			model.addAttribute("id_vehiculo",vehiculo.getId_vehiculo());
			model.addAttribute("auxiliar", aux);
			model.addAttribute("seguros",SeguroPage);
			model.addAttribute("page",SeguroRenderArea);
			return "SegurosVehi";

	}

	
	@RequestMapping(value="/formSeg/Ag/{id_vehiculo}")
	public String crear(@PathVariable(value="id_vehiculo") Long id_vehiculo,Map<String,Object> model) {

		editar=false;
		user = obuserService.obtenUser();
		model.put("usuario",user);	
		Seguro seguro = new Seguro();
		Vehiculo vehi= new Vehiculo();
	
		vehi = vehiculoService.findOne(id_vehiculo);
		seguro.setVehiculo(vehi);
		aseguradora = aseguradoraService.findAll();
		model.put("vehiculos", vehi);
		model.put("id_vehiculo", vehi.getId_vehiculo());
		model.put("editar", editar);
		model.put("seguros", seguro);
		model.put("aseguradoras", aseguradora);
		model.put("PageTitulo", "Agregar Seguro");
		model.put("titulo", "Formulario de Seguros");	
		return "formSeg";
		
	}
	
	
	@RequestMapping(value="/formSeg/{id_seguro}")
	public String editar(@PathVariable(value="id_seguro") Long id_seguro,Map<String,Object>model) {
		
		editar=true;
		user = obuserService.obtenUser();
		model.put("usuario",user);	
		Seguro seguro = null;
		
		if(!id_seguro.equals(null)) {
			seguro = seguroService.findOne(id_seguro);
		}else {
			return "redirect:/seguros";
		}
		model.put("id_vehiculo", seguro.getVehiculo().getId_vehiculo());
		model.put("editar", editar);
		model.put("seguros",seguro);
		model.put("PageTitulo", "Editar Seguro");
		model.put("titulo", "Editar Seguro");
		return "formSeg";
	}
	
	
	@RequestMapping(value ="/verSeguro/{id_seguro}")
	public String ver(@PathVariable(value = "id_seguro") Long id_seguro, Map<String, Object> model) {
		
		user = obuserService.obtenUser();
		model.put("usuario",user);	
		Seguro seguro = null;
		
		if(!id_seguro.equals(null)) {
			seguro = seguroService.findOne(id_seguro);
		}else {
			return "redirect:/seguros";
		}
		model.put("id_vehiculo", seguro.getVehiculo().getId_vehiculo());
		model.put("aux", true);
		model.put("seguros",seguro);
		model.put("PageTitulo", "Información del Seguro");
		model.put("titulo", "Editar Seguro");
		return "formSeg";

		/*Seguro seguro = null;
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
		return "verSeguro";*/
		
	}
	
	
	@RequestMapping(value = "/formSeg", method = RequestMethod.POST)
	public String guardar(Seguro seguro, @RequestParam("file") MultipartFile documento,Map<String,Object> model) {
					
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
		    String valor_old = seguro_old.toString();
		    		    
			System.err.println("entroeditar: "+seguro.getId_seguro());
			vehiculos = vehiculoService.findOne(seguro.getVehiculo().getId_vehiculo());	
			seguro.setVehiculo(vehiculos);
			seguroService.save(seguro);
			
			//Auditoria
			
         	LogsAudit logs = new LogsAudit();
         	
         	logs.setId_usuario(obuserService.obtenEmpl());
    		logs.setTipo_role(obuserService.obtenUser());
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
     	
     	logs.setId_usuario(obuserService.obtenEmpl());
		logs.setTipo_role(obuserService.obtenUser());
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
	public String Buscartablapv(@RequestParam(name="page", defaultValue = "0") int page,
		@RequestParam(value="elemento") String elemento,Model model){	
		
		 aux=true;
		 Pageable pageRequest = PageRequest.of(page, 100);
		 user = obuserService.obtenUser();
		 model.addAttribute("usuario",user);
		 String elementof="";
		 
		 if(!elemento.isBlank()) {			
							if(isValidDouble(elemento)) {
								Double Dato = Double.parseDouble(elemento);
									DecimalFormat formt = new DecimalFormat("0");
									elemento = formt.format(Dato);
									elemento = elemento.replaceAll(",","");	
							};
						
					elementof = elemento.toUpperCase(); 
					Page<Seguro> segurospage= seguroService.FindSegElemVehiPage(id_vehi, elementof, pageRequest);
					PageRender<Seguro> pageRender = new PageRender<>("/formSegBuscarPv?elemento="+elementof, segurospage);
					if(seguroService.totalSegElemVehiPage(id_vehi, elementof)>=5) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
					
					model.addAttribute("seguros",segurospage);
					model.addAttribute("page",pageRender);
					model.addAttribute("elemento",elementof);	
					model.addAttribute("auxiliar", aux);
					model.addAttribute("id_vehiculo",id_vehi);
					model.addAttribute("PageTitulo", "Seguros");
					model.addAttribute("PageSubTitulo", "Listado de Seguros de la placa: "+id_placa);
	     			return "SegurosVehi";		
		}else{
			return "redirect:/Seguros";
		}
		 
	}
	
	
	@RequestMapping(value="/formSegBuscar")
	public String Buscartabla(@RequestParam(name="page", defaultValue = "0") int page,
		@RequestParam(value="elemento") String elemento,Model model){	
		
		 aux=false;
		 Pageable pageRequest = PageRequest.of(page, 100);
		 user = obuserService.obtenUser();
		 model.addAttribute("usuario",user);
		 String elementof="";
		 
		 var ads="";
		 ads = obuserService.obtenEmpl();
		 
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
							Page<Seguro> segurospage= seguroService.FindSegElemenAreaPage(usus.getAdscripcion().getId_adscripcion(), elementof, pageRequest);
						    PageRender<Seguro> pageRender = new PageRender<>("/formSegBuscar?elemento="+elementof, segurospage);
							if(seguroService.totalSegElemenAreaPage(usus.getAdscripcion().getId_adscripcion(), elementof)>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};

						    model.addAttribute("seguros",segurospage);
							model.addAttribute("auxiliar", aux);
							model.addAttribute("page",pageRender);
							model.addAttribute("elemento",elementof);
							model.addAttribute("id_vehiculo",id_vehi);
							model.addAttribute("PageTitulo", "Seguros");
							model.addAttribute("PageSubTitulo", "Listado de Seguros");
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
					model.addAttribute("PageTitulo", "Seguros");
					model.addAttribute("PageSubTitulo", "Listado de Seguros");
	     			return "SegurosVehi";		
		}else{
			return "redirect:/Seguros";
		}
		 
	}


	@RequestMapping(value = "/ListarSeguros/refreshImgpv")
	public @ResponseBody String verImgpv(@RequestParam("id_seguro") Long id_seguro, Model model) {
		
		Seguro seg = new Seguro();
	    seg = seguroService.findOne(id_seguro);
	    
		String html="";
				if(seg.getUrl_poliza().equals(null)) {
					 html="<div>No se encontro la imagen en el servidor</div>";	
				}else {
					 html="<div class=\"card-body justify-content-center col-sm-12\">\r\n" + 
							"		<div class=\"card-body d-flex justify-content-center\" >\r\n" + 
							"				<iframe src=\"/upload/"+seg.getUrl_poliza()+"\" type=\"application/pdf\" style=\"height:715px;width:600px;\"/>\r\n" + 
							"		</div>\r\n" + 
							"	</div>";	
				}			
				
				return html;
	}		
	
	@RequestMapping(value = "/Seguros/refreshImg")
	public @ResponseBody String verImg(@RequestParam("id_seguro") Long id_seguro, Model model) {
		
		Seguro seg = new Seguro();
	    seg = seguroService.findOne(id_seguro);
	    	    
		String html="";
				if(seg.getUrl_poliza().equals(null)) {
					 html="<div>No se encontro la imagen en el servidor</div>";	
				}else {
					 html="<div class=\"card-body justify-content-center col-sm-12\">\r\n" + 
							"		<div class=\"card-body d-flex justify-content-center\" >\r\n" + 
							"				<iframe src=\"/upload/"+seg.getUrl_poliza()+"\" type=\"application/pdf\" style=\"height:715px;width:600px;\"/>\r\n" + 
							"		</div>\r\n" + 
							"	</div>";	
				}			
				
				return html;
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