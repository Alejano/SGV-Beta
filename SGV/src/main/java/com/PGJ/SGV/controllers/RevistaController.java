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
import com.PGJ.SGV.models.entity.Revista;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.service.IAdscripcionService;
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.service.IRevistaService;
import com.PGJ.SGV.service.IVehiculoService;


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

	String user;
	static List<Vehiculo> vehi = new ArrayList<Vehiculo>();
	static Long id_ads=(long)0;
	static List<Revista> revistas = new ArrayList<Revista>();

	
	@RequestMapping(value={"/Revista"}, method = RequestMethod.GET)
	public String HomeBarra(Model model, Authentication authentication,HttpServletRequest request){
		
	    List<Adscripcion> ads = new ArrayList<Adscripcion>();
		ads = adscripService.findAll();
		var nombre="";
		user = obuserService.obtenUser();
		model.addAttribute("usuario",user);	
			
	    nombre= usuario.getNombre();		
	    model.addAttribute("id",authentication.getPrincipal());
	    model.addAttribute("Online",nombre); 		   	
	    model.addAttribute("adscripciones",ads); 		   		
	   
		return "Revista";
	}
	
	
	@RequestMapping(value = "/refreshVehi")
	public @ResponseBody String refreshVehi(@RequestParam("id_ads") Long id,@RequestParam("title") String title,@RequestParam("Fini") String Fini,@RequestParam("Ffin") String Ffin, Model model) {
	
		if(id_ads != id) {
		vehi = vehiculoService.findVehiculosArea(id);
		id_ads =id;		
		}
		revistas = revistaService.findAllFuturo();
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
					
										
			//
			codigo+="$('#CB"+s.getId_vehiculo()+"').change(function() {"+ 	
					"$(\"#tr"+s.getId_vehiculo()+"\").load(\"Estadorevista\",\"idAds="+id+"&id_vehiculo="+s.getId_vehiculo()+"&rev_id="+idRevista+"&Fini="+Fini+"&Ffin="+Ffin+"&checkbox=\"+$(\"#CB"+s.getId_vehiculo()+"\").prop(\"checked\"));"
					
					+ "});";									
		}
				
		String fin ="</tbody>					\r\n" + 
				"				</table>\r\n" + 
				"            </div><script>"+codigo+"\r\n</script>";
			
	    return ini+html+fin;
	}	
	static Vehiculo ve = new Vehiculo();
	static Long id_vehi=(long) 0;
	
	@RequestMapping(value = "/Estadorevista")
	public @ResponseBody String revistaEstado(@RequestParam("idAds") Long idAds,@RequestParam("id_vehiculo") Long id_vehiculo,@RequestParam("rev_id") Long idRevista,@RequestParam("Fini") String Fini,@RequestParam("Ffin") String Ffin,@RequestParam("checkbox") boolean check, Model model) {
		
		if(id_vehi != id_vehiculo) {
			id_vehi=id_vehiculo;
			ve = vehiculoService.findOne(id_vehiculo);
			
		}
		
		Revista rev= new Revista();	
		
	
		if(idRevista != 0) {rev =revistaService.findOne(idRevista);};
				
		rev.setFecha_inicio(Fini);
		rev.setFecha_fin((Ffin=="")?Fini:Ffin);
		rev.setVehiculo(ve);
		rev.setEstado(check);
		rev.setEvento(idAds);
		String html="";	
		
		
		revistaService.save(rev);
		
		if(check) {

			html="Estado Actual de la revista: Paso";
		}else{						

			html="Estado Actual de la revista: No Paso";
		}
							
	    return html;
	}	
	
}
