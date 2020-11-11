package com.PGJ.SGV.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
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
	

	
	@RequestMapping(value={"/Revista"}, method = RequestMethod.GET)
	public String HomeBarra(Model model, Authentication authentication,HttpServletRequest request){
		var nombre="";
		var user="";
		
	List<Adscripcion> ads = new ArrayList<Adscripcion>();
		ads = adscripService.findAll();
		
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";	model.addAttribute("usuario",user);
			}else {
				if(hasRole("ROLE_USER")) {
					user = "ROLE_USER"; model.addAttribute("usuario",user);
				}else {
					if(hasRole("ROLE_SEGURO")) {
						user = "ROLE_SEGURO"; model.addAttribute("usuario",user);
					}else {
						if(hasRole("ROLE_TALLER")) {
							user = "ROLE_TALLER"; model.addAttribute("usuario",user);
						}
					}
				}	
			};
			
			
						
	   nombre= usuario.getNombre();		
	   model.addAttribute("id",authentication.getPrincipal());
	   model.addAttribute("Online",nombre); 		   	
	   model.addAttribute("adscripciones",ads); 		   		
	   
		return "Revista";
	}
	static List<Vehiculo> vehi = new ArrayList<Vehiculo>();
	static Long id_ads=(long)0;
	static List<Revista> revistas = new ArrayList<Revista>();

	
	@RequestMapping(value = "/refreshVehi")
	public @ResponseBody String refreshVehi(@RequestParam("id_ads") Long id,@RequestParam("title") String title,@RequestParam("Fini") String Fini,@RequestParam("Ffin") String Ffin, Model model) {
	
		
		if(id_ads != id) {
		vehi = vehiculoService.findVehiculosArea((long)id);
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
					"$(\"#tr"+s.getId_vehiculo()+"\").load(\"revista\",\"placa="+s.getPlaca()+"&rev_id="+idRevista+"&Fini="+Fini+"&Ffin="+Ffin+"&checkbox=\"+$(\"#CB"+s.getId_vehiculo()+"\").prop(\"checked\"));"
					
					+ "});";									
		}
				
		String fin ="</tbody>					\r\n" + 
				"				</table>\r\n" + 
				"            </div><script>"+codigo+"\r\n</script>";
			
	    return ini+html+fin;
	}	
	static Vehiculo ve = new Vehiculo();
	static String placasave="";
	
	@RequestMapping(value = "/revista")
	public @ResponseBody String revistaEstado(@RequestParam("placa") String placa,@RequestParam("rev_id") Long idRevista,@RequestParam("Fini") String Fini,@RequestParam("Ffin") String Ffin,@RequestParam("checkbox") boolean check, Model model) {
		
		if(placasave != placa) {
			placasave=placa;
			ve = vehiculoService.findByPlaca(placa);
			
		}
		
		Revista rev= new Revista();	
		
	
		if(idRevista != 0) {rev =revistaService.findOne(idRevista);};
				
		rev.setFecha_inicio(Fini);
		rev.setFecha_fin((Ffin=="")?Fini:Ffin);
		rev.setVehiculo(ve);
		rev.setEstado(check);
		
		String html="";	
		
		
		revistaService.save(rev);
		
		if(check) {

			html="Estado Actual de la revista: Paso";
		}else{						

			html="Estado Actual de la revista: No Paso";
		}
							
	    return html;
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
	
}
