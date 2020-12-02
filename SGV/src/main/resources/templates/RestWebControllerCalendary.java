package com.PGJ.SGV.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PGJ.SGV.models.entity.Evento;
import com.PGJ.SGV.models.entity.Revista;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.models.entity.respuestaEvento;
import com.PGJ.SGV.service.IEventoService;
import com.PGJ.SGV.service.IRevistaService;
import com.PGJ.SGV.service.IVehiculoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/event")
public class RestWebControllerCalendary {
	
	@Autowired
	private IEventoService eventoService;
	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private IRevistaService revistaService;
	
	@GetMapping(value = "/all")
    public String getEvents() {		
		
        String jsonMsg = "";
        try {
            List<Evento> events = new ArrayList<Evento>();
            events = eventoService.findAll();
            
            ObjectMapper mapper = new ObjectMapper();            
            
            jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);
            
           
        } catch (Exception ioex) {
            System.out.println(ioex);
        }
        return jsonMsg;
    }
		
	
	@PostMapping(value = "/save")
	  public respuestaEvento postEventSave(@RequestBody Evento eventos) {
							
			
			eventoService.save(eventos);				
			
			List<Vehiculo> vl = new ArrayList<Vehiculo>();
			vl = vehiculoService.findVehiculosArea(eventos.getId_adscripcion());
			for(Vehiculo v:vl) {
				Revista revi = new Revista();
				revi.setVehiculo(v);
				revi.setFecha_inicio(eventos.getStart());
				revi.setFecha_fin(eventos.getEnd());
				revi.setEstado(false);
				revi.setEvento_id(eventos.getId());
				revistaService.save(revi);
			}
		
					
			respuestaEvento respuesta= new respuestaEvento("done",eventos);
		    
		    return respuesta;
					  
	  }
	@PostMapping(value = "/ed")
	  public respuestaEvento postEventEdit(@RequestBody Evento eventos) {				
			
			Evento ev = null;
			
			ev = eventoService.findOne(eventos.getId());
			ev.setStart(eventos.getStart());
			ev.setEnd(eventos.getEnd());
			eventoService.save(ev);
		
					
			respuestaEvento respuesta= new respuestaEvento("done",eventos);
		    
		    return respuesta;
					  
	  }
	
	@PostMapping(value = "/serial")
	  public respuestaEvento postEventDelete(@RequestBody Evento eventos) {

		
			eventoService.delete(eventos.getId());
			List<Revista> r = null;
			r = revistaService.revistaEvento(eventos.getId());
			for(Revista lista:r) {revistaService.delete(lista.getId_revista());;}
					
		 respuestaEvento respuesta= new respuestaEvento("done",eventos.getTitle());
		    
		    return respuesta;
		  }
	
	
	
}
