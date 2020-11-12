package com.PGJ.SGV.controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.PGJ.SGV.models.entity.Adscripcion;
import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.service.IAdscripcionService;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;


@Controller
public class AdscripcionController {
	
	List<Adscripcion> adscripciones = new ArrayList<Adscripcion>();
	
	@Autowired
	private IAdscripcionService adscripService;
	@Autowired
	private ILogsAuditService logsauditService;
	@Autowired
	private IObtenerUserService obuserService;
	
	boolean editar = false;
	Long ID;
	
	@RequestMapping(value="/Adscripciones", method = RequestMethod.GET)
	public String listar(Model model) {
		
		adscripciones = adscripService.findAll();
		if(adscripService.adscripcionestotales()>= 4) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};	
		model.addAttribute("titulo","Listado de Adscripciones");
		model.addAttribute("adscripciones",adscripciones);
		model.addAttribute("usuarios",obuserService.obtenUser());
		
		return "Adscripciones";
	}
		
	
	@RequestMapping(value="/formAds")
	public String crear(Map<String,Object> model) {
		
		Adscripcion ads = new Adscripcion();
		model.put("adscripcion", ads);
		model.put("usuarios",obuserService.obtenUser());
		model.put("titulo", "Formulario de Adscripciones");
							
		return "formAds";
	}
	
	
	@RequestMapping(value="/formAds/{id_adscripcion}")
	public String editar(@PathVariable(value="id_adscripcion") Long id_adscripcion,Map<String,Object>model) {
		editar = true;
		Adscripcion adscripcion = null;
		
		if(id_adscripcion != 0) {
			adscripcion = adscripService.findOne(id_adscripcion);
			ID = adscripcion.getId_adscripcion();
		}else {
			return "redirect:/Adscripcion";
		}
				
		model.put("adscripcion",adscripcion);
		model.put("titulo", "Editar cliente");
		return "formAds";
	}
	
	
	@RequestMapping(value="/formAds",method = RequestMethod.POST)
	public String guardar(Adscripcion adscripcion){
				
		if(editar == true) {
		
		//Adscripciones OLD
	    
		Adscripcion adscripcion_old = null;
		adscripcion_old = adscripService.findOne(adscripcion.getId_adscripcion());
				
	    System.err.println("old:"+adscripcion_old.toString());
	    String valor_old = adscripcion_old.toString();
	    
	    adscripService.save(adscripcion);			
		editar = false;	
		
		//Auditoria
		
     	LogsAudit logs = new LogsAudit();
     	
        logs.setId_usuario(obuserService.obtenEmpl());
		logs.setTipo_role(obuserService.obtenUser());
		logs.setFecha(SystemDate.obtenFecha());
		logs.setHora(ObtenHour.obtenHour());
		logs.setName_table("ADSCRIPCIONES");
		logs.setValor_viejo(valor_old);
		logs.setTipo_accion("UPDATE");
								
		logsauditService.save(logs);
		
		editar = false;	
		
		}else {
			
			adscripService.save(adscripcion);	
			String valor_nuevo = adscripcion.toString();
			
			//Auditoria
			
			LogsAudit logs = new LogsAudit();
			
			logs.setId_usuario(obuserService.obtenEmpl());
			logs.setTipo_role(obuserService.obtenUser());
			logs.setFecha(SystemDate.obtenFecha());
			logs.setHora(ObtenHour.obtenHour());
			logs.setName_table("ADSCRIPCIONES");
			logs.setValor_viejo(valor_nuevo);
			logs.setTipo_accion("INSERT");
			
			logsauditService.save(logs);
			
		}
		
		return "redirect:Adscripciones";

	} 
		
	
	@RequestMapping(value="/elimAds/{id_adscripcion}")
	public String eliminar (@PathVariable(value="id_adscripcion")Long id_adscripcion) {
		
		if(id_adscripcion != 0) {
			adscripService.delete(id_adscripcion);
		}
		return "redirect:/Adscripciones";
	}
	
}






















