package com.PGJ.SGV.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PGJ.SGV.models.entity.Eventos;
import com.PGJ.SGV.models.entity.respuestaEvento;
import com.PGJ.SGV.service.IEventoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/event")
public class RestWebControllerCalendary {
	
	@Autowired
	private IEventoService eventoService;

	
	@GetMapping(value = "/all")
    public String getEvents() {		
		
        String jsonMsg = null;
        try {
            List<Eventos> events = new ArrayList<Eventos>();
            events = eventoService.findAll();

          
            ObjectMapper mapper = new ObjectMapper();
            jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }
        return jsonMsg;
    }
		
	
	@PostMapping(value = "/save")
	  public respuestaEvento postEventEdit(@RequestBody Eventos eventos) {
		
			
			eventoService.save(eventos);
		
					
			respuestaEvento respuesta= new respuestaEvento("done",eventos);
		    
		    return respuesta;
					  
	  }
	
	@PostMapping(value = "/serial")
	  public respuestaEvento postEventDelete(@RequestBody Eventos eventos) {

			
			eventoService.delete(eventos.getId());
					
		 respuestaEvento respuesta= new respuestaEvento("done",eventos.getTitle());
		    
		    return respuesta;
		  }
	
	
	
}
