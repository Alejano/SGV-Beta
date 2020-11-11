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
import com.PGJ.SGV.service.IAdscripcionService;



@Controller
public class AdscripcionController {
	List<Adscripcion> adscripciones = new ArrayList<Adscripcion>();
	
	@Autowired
	private IAdscripcionService adscripService;
	
	boolean editar = false;
	Long ID;
	
	@RequestMapping(value="/Adscripciones", method = RequestMethod.GET)
	public String listar(Model model) {
		adscripciones = adscripService.findAll();
		model.addAttribute("titulo","Listado de Adscripciones");
		model.addAttribute("adscripciones",adscripciones);
		
		return "Adscripciones";
	}
		
	
	@RequestMapping(value="/formAds")
	public String crear(Map<String,Object> model) {
		Adscripcion ads = new Adscripcion();
		model.put("adscripcion", ads);
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
				
		if(editar==false) {
			for(Adscripcion ads:adscripciones) {
				if(adscripcion.getId_adscripcion().equals(ads.getId_adscripcion())) {					
					return "redirect:/idDuplicadoAds/"+adscripcion.getId_adscripcion();
				};	
			}
		}else{
																
				if( !ID.equals(adscripcion.getId_adscripcion())) {		
					//System.out.println(usuario.getNo_empleado()+" "+ empleado);
					return "redirect:/idDuplicadoAdsCrea/"+adscripcion.getId_adscripcion()+"/"+ID;
				}									
			
		};
		adscripService.save(adscripcion);			
		editar = false;	
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






















