package com.PGJ.SGV.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.PGJ.SGV.models.entity.Adscripcion;
import com.PGJ.SGV.models.entity.Evento;
import com.PGJ.SGV.models.entity.EventosExternos;
import com.PGJ.SGV.models.entity.Revista;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.service.IAdscripcionService;
import com.PGJ.SGV.service.IEventoService;
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.service.IRevistaService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.utilities.ObtenMonth;
import com.PGJ.SGV.utilities.SystemDate;


@Controller
public class RevistaController {	
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	Usuario usuario=new Usuario();	
	
	@Autowired
	private IRevistaService revistaService;
	@Autowired
	private IAdscripcionService adscripService;
	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private IObtenerUserService obuserService;
	@Autowired
	private IEventoService eventoService;

	String user;
	static List<Vehiculo> vehi = new ArrayList<Vehiculo>();
	static Long id_ads=(long)0;
	static List<Revista> revistas = new ArrayList<Revista>();

	
	@RequestMapping(value={"/Revista"}, method = RequestMethod.GET)
	public String HomeBarra(Model model, Authentication authentication,HttpServletRequest request){
		
		System.err.println("entro revista");
 
		Long ultimoEvento = (long) 0;		
		ultimoEvento = eventoService.ultimoid(); 
		
	    List<Adscripcion> ads = new ArrayList<Adscripcion>();
		ads = adscripService.findAll();
		
		List<EventosExternos> eventos = new ArrayList<EventosExternos>();
		for(Adscripcion a:ads) {
			ultimoEvento++;
			EventosExternos evento = new EventosExternos();		
			evento.setId(ultimoEvento);
			evento.setId_adscripcion(a.getId_adscripcion());
			evento.setNombre_adscripcion(a.getNombre_adscripcion());
		
			eventos.add(evento);
						
		}
		var nombre="";
		user = obuserService.obtenUser();
		model.addAttribute("usuario",user);	
		
			
	    nombre= usuario.getNombre();	
	    model.addAttribute("PageTitulo", "Revista Vehicular");
	    model.addAttribute("id",authentication.getPrincipal());
	    model.addAttribute("Online",nombre); 		   	
	    model.addAttribute("eventosexternos",eventos); 		   		
	   
		return "Revista";
	}
	
	
	@RequestMapping(value = "/refreshVehi")
	public @ResponseBody String refreshVehi(@RequestParam("id") Long id,@RequestParam("title") String title,@RequestParam("Fini") String Fini,@RequestParam("Ffin") String Ffin, Model model) {
		
		revistas = revistaService.revistaEvento(id);
		
		Evento ev = null;
		if(id != 0) {ev =eventoService.findOne(id);};
		ev.setEnd((Ffin=="")?Fini:Ffin);
		eventoService.save(ev);
		
		
		
		String html="";			
		String codigo="";
		
		String ini="<div class=\"modal-header\">                \r\n" + 
				"                <h4 id=\"modalTitle\" class=\"modal-title\">"+title+"</h4>\r\n" + 
				"            </div>\r\n" + 
				"            <div id=\"modalBody\" class=\"modal-body\" style=\"    height: 500px;\">\r\n" + 
				"            	<table class=\"table table-striped table-bordered fixed_header\" id=\"VehiT\">\r\n" + 
				"\r\n" + 
				"					<thead class=\"table-success\">\r\n" + 
				"						<tr>					\r\n" + 
				"							<th class=\"celda\" style=\"width:300px\">Vehiculos Encontrados</th>					\r\n" + 
				"						</tr>				\r\n" + 
				"					</thead>\r\n" + 
				"					<tbody  style=\"position:absolute; overflow-y: scroll; height: 400px;width: 95%;\">";
		
		
		for(Revista r:revistas){
			
			html+="<tr><td class="+"'celda'"+" style="+"'width:10%'>"+r.getVehiculo().getPlaca()+"</td><td id=\"tr"+r.getVehiculo().getId_vehiculo()+"\" class="+"'celda'"+" style="+"'width:40%'>Estado Actual de la revista: "+((r.isEstado())?"Paso":"No Paso")+"</td><td class="+"'celda'"+" style="+"'width:20%'><div style=\"text-align-last: center;\"><input type=\"checkbox\" value=\""+r.isEstado()+"\" id=\"CB"+r.getVehiculo().getId_vehiculo()+"\" "+((r.isEstado())?"checked":"")+"/></div></td></tr>";
			
			codigo+="$('#CB"+r.getVehiculo().getId_vehiculo()+"').change(function() {"+ 	
					"$(\"#tr"+r.getVehiculo().getId_vehiculo()+"\").load(\"Estadorevista\",\"id="+r.getId_revista()+"&id_vehiculo="+r.getVehiculo().getId_vehiculo()+"&idevento="+r.getEvento_id()+"&Fini="+Fini+"&Ffin="+Ffin+"&checkbox=\"+$(\"#CB"+r.getVehiculo().getId_vehiculo()+"\").prop(\"checked\"));"
					
					+ "});";
			
		}
		
		
		/*
		
		long controlid =0;
		long idRevista =0;
		for(Vehiculo s:vehi) {
				for(Revista r:revistas){	
					if(r.getVehiculo().getId_vehiculo() == s.getId_vehiculo()) {					
							html+="<tr><td class="+"'celda'"+" style="+"'width:10%'>"+s.getPlaca()+"</td><td id=\"tr"+s.getId_vehiculo()+"\" class="+"'celda'"+" style="+"'width:40%'>Estado Actual de la revista: "+((r.isEstado())?"Paso":"No Paso")+"</td><td class="+"'celda'"+" style="+"'width:20%'><div style=\"text-align-last: center;\"><input type=\"checkbox\" value=\""+r.isEstado()+"\" id=\"CB"+s.getId_vehiculo()+"\" "+((r.isEstado())?"checked":"")+"/></div></td></tr>";
							controlid= s.getId_vehiculo();	
							idRevista = r.getId_revista();
					}
				}
				
				if(controlid!=s.getId_vehiculo()) {
					html+="<tr><td class="+"'celda'"+" style="+"'width:10%'>"+s.getPlaca()+"</td><td id=\"tr"+s.getId_vehiculo()+"\" class="+"'celda'"+" style="+"'width:40%'>Estado Actual de la revista: No paso</td><td class="+"'celda'"+" style="+"'width:20%'><div style=\"text-align-last: center;\"><input type=\"checkbox\" value=\"false\" id=\"CB"+s.getId_vehiculo()+"\"/></div></td></tr>";
					idRevista=0;
				}
					
			codigo+="$('#CB"+s.getId_vehiculo()+"').change(function() {"+ 	
					"$(\"#tr"+s.getId_vehiculo()+"\").load(\"Estadorevista\",\"id="+id+"&id_vehiculo="+s.getId_vehiculo()+"&rev_id="+idRevista+"&Fini="+Fini+"&Ffin="+Ffin+"&checkbox=\"+$(\"#CB"+s.getId_vehiculo()+"\").prop(\"checked\"));"
					
					+ "});";									
		}*/
				
		String fin ="</tbody>					\r\n" + 
				"				</table>\r\n" + 
				"            </div><script>"+codigo+"\r\n</script>";
		
	    
		String mes=ev.getStart().substring(5,7);
		String digito = ev.getEnd().substring(8,10);
		String digitoc = ev.getEnd().substring(9,10);
		String fecha = ev.getEnd().substring(0,8);
		int dia;
		int max;
	
		System.err.println("Digito: "+digito);
		System.err.println("Digitoc: "+digitoc);
		
		if (!digito.equals("0")) {
            System.err.println("diferente cero");
            dia = Integer.parseInt(ev.getEnd().substring(8,10))-1;
            fecha = fecha + dia;
    		System.err.println("fecha"+fecha);

		}else {
			
            System.err.println("igual a cero");

		   if(digitoc.equals("1")) {
				
			   if(mes.equals("01")) 
			   { max = ObtenMonth.numeroDeDiasMes("enero", SystemDate.obtenAnio())-1;
			     fecha = fecha+max;
			   }
			   if(mes.equals("02"))
			   { max = ObtenMonth.numeroDeDiasMes("febrero", SystemDate.obtenAnio())-1;
			     fecha = fecha+max;
			   }
			   if(mes.equals("03")) 
			   { max = ObtenMonth.numeroDeDiasMes("marzo", SystemDate.obtenAnio())-1;
			     fecha = fecha+max;
			   }
			   if(mes.equals("04")) 
			   { max = ObtenMonth.numeroDeDiasMes("abril", SystemDate.obtenAnio())-1;
			     fecha = fecha+max;
			   }
			   if(mes.equals("05")) 
			   { max = ObtenMonth.numeroDeDiasMes("mayo", SystemDate.obtenAnio())-1;
			     fecha = fecha+max;
			   }
			   if(mes.equals("06")) 
			   { max = ObtenMonth.numeroDeDiasMes("junio", SystemDate.obtenAnio())-1;
			     fecha = fecha+max;
			   }
			   if(mes.equals("07"))
			   { max = ObtenMonth.numeroDeDiasMes("julio", SystemDate.obtenAnio())-1;
			     fecha = fecha+max;
			   }
			   if(mes.equals("08"))
			   { max = ObtenMonth.numeroDeDiasMes("agosto", SystemDate.obtenAnio())-1;
			     fecha = fecha+max;
			   }
			   if(mes.equals("09")) 
			   { max = ObtenMonth.numeroDeDiasMes("septiembre", SystemDate.obtenAnio())-1;
			     fecha = fecha+max;
			   }
			
			   System.err.println("fecha"+fecha);

			}else {
			
	            System.err.println("sin mes");
			    dia = Integer.parseInt(ev.getEnd().substring(1,2))-1;
	            fecha = fecha + dia;
	    		System.err.println("fecha"+fecha);

		     }
	}
		
		
       
	    return ini+html+fin;
	}	
	
	
	static Vehiculo ve = new Vehiculo();
	static Long id_vehi=(long) 0;
	
	
	@RequestMapping(value = "/Estadorevista")
	public @ResponseBody String revistaEstado(@RequestParam("id") Long id,@RequestParam("id_vehiculo") Long id_vehiculo,@RequestParam("idevento") Long idevento,@RequestParam("Fini") String Fini,@RequestParam("Ffin") String Ffin,@RequestParam("checkbox") boolean check, Model model) {
		
		if(id_vehi != id_vehiculo) {
			id_vehi=id_vehiculo;
			ve = vehiculoService.findOne(id_vehiculo);
			
		}
	

		
		
		Revista rev= null;	
		Evento ev = null;
		if(id != 0) {rev =revistaService.findOne(id);};
		if(idevento != 0) {ev =eventoService.findOne(idevento);};
		
		ev.setEnd((Ffin=="")?Fini:Ffin);
	
		
		
		rev.setFecha_inicio(Fini);		
		rev.setFecha_fin((Ffin=="")?Fini:Ffin);		
		rev.setVehiculo(ve);
		rev.setEstado(check);
		rev.setEvento_id(idevento);
		
		String html="";	
		
		revistaService.save(rev);
		eventoService.save(ev);
		
		if(check) {

			html="Estado Actual de la revista: Paso";
		}else{						

			html="Estado Actual de la revista: No Paso";
		}
							
	    return html;
	}	
	
}
