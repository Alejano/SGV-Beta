package com.PGJ.SGV.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.PGJ.SGV.models.entity.Conductor;
import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.models.entity.Resguardante;
import com.PGJ.SGV.models.entity.TipoResguardante;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.service.IConductorService;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IResguardanteService;
import com.PGJ.SGV.service.ITipoResguardanteService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.util.paginador.PageRender;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;

@Controller
public class ResguardanteController {
	
	@Autowired
	private ITipoResguardanteService tiporesguardoService;
	@Autowired
	private IResguardanteService reguardanteservice;
	@Autowired
	private IUsuarioService usuarioService;	
	@Autowired
	private IConductorService conductorService;
	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private ILogsAuditService logsauditService;
	
	
	public static Resguardante Presguardante = new Resguardante();
	public static Resguardante Sresguardante = new Resguardante();
	public static Resguardante Tresguardante = new Resguardante();
	static int 	Corddocu = 0;
	static int 	Cordtabla = 0;
	
	@RequestMapping(value="/infoResguardante/{id_vehiculo}", method = RequestMethod.GET)
	public String infoResguardo(Model model,Authentication authentication,@RequestParam(name="page", defaultValue = "0") int page
			,@PathVariable(value="id_vehiculo") long id_vehiculo) {	
		
		var user="";
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";	model.addAttribute("usuario",user);
			}else {
				if(hasRole("ROLE_USER")) {
					user = "ROLE_USER"; model.addAttribute("usuario",user);
				}
			}

		Pageable pageRequest = PageRequest.of(page, 500);
		Vehiculo placa = new Vehiculo();
		
		placa = vehiculoService.findOne(id_vehiculo);
		Page<Resguardante> vehiculopage = reguardanteservice.findAllByVehiculo(id_vehiculo, pageRequest);
		PageRender<Resguardante> pageRender = new PageRender<>("/infoResguardante/{id_vehiculo}", vehiculopage);		
	
		model.addAttribute("Placa", placa.getPlaca());
		model.addAttribute("Corddocu",Corddocu);		
		model.addAttribute("Cordtabla",Cordtabla);
		model.addAttribute("thisurl","infoResguardante");
		model.addAttribute("titulo","Listado de Resguardos");
		model.addAttribute("Resguardantes",vehiculopage);
		model.addAttribute("page",pageRender);				
		
		return "/vehiculos/infoResguardante";
	}
	
	@RequestMapping(value="/AddResguardo/{placa}")
	public String Agregar(Map<String,Object> model,@PathVariable(value="placa") String placa) {
		
		Resguardante Presguardante = new Resguardante();
		Resguardante Sresguardante = new Resguardante();
		
		TipoResguardante tiposResguardoP = new TipoResguardante();
		TipoResguardante tiposResguardoS = new TipoResguardante();
		
		tiposResguardoP = tiporesguardoService.findOne((long)1);
		tiposResguardoS = tiporesguardoService.findOne((long)2);			
		
		List<Usuario> usuariosdb = new ArrayList<Usuario>();
		List<Conductor> conductoresdb = new ArrayList<Conductor>();	
		usuariosdb = usuarioService.findAll();
		conductoresdb = conductorService.findAll();
		
		model.put("placa", placa);
		model.put("Presguardante", Presguardante);
		model.put("Sresguardante", Sresguardante);		
		model.put("tiposResguardoP", tiposResguardoP);
		model.put("tiposResguardoS", tiposResguardoS);
		model.put("conductores", conductoresdb);
		model.put("usuarios",usuariosdb);
		
		return "vehiculos/AddResguardo";
	}
	
	
	@RequestMapping(value="/AddResguardo/{placa}",method = RequestMethod.POST)
	public String saveR(Map<String,Object> model,Resguardante resguardante,
			@PathVariable(value="placa") String placa,Authentication authentication
			) {			

		  var user="";
		   var no_user ="";
		   no_user = authentication.getName();
				
			
					if(hasRole("ROLE_ADMIN")) {
						user ="ROLE_ADMIN";
						}else {
							if(hasRole("ROLE_USER")) {
								user = "ROLE_USER";
							}else {
								if(hasRole("ROLE_SEGURO")) {
									user = "ROLE_SEGURO";
								}
							}	
						}
				
		String nombres = resguardante.getNombre();
		String apellidos1 = resguardante.getApellido1();
		String apellidos2 = resguardante.getApellido2();
		String cargos = resguardante.getCargo();			
				
		String[] nombre = nombres.split(",");
		String[] apellido1 = apellidos1.split(",");
		String[] apellido2 = apellidos2.split(",");
		String[] cargo = cargos.split(",");			
		
		Presguardante.setNombre(nombre[0]);
		Sresguardante.setNombre(nombre[1]);
			
		Presguardante.setApellido1(apellido1[0]);
		Sresguardante.setApellido1(apellido1[1]);		
		
		Presguardante.setApellido2(apellido2[0]);
		Sresguardante.setApellido2(apellido2[1]);		
		
		Presguardante.setCargo(cargo[0]);
		Sresguardante.setCargo(cargo[1]);
			
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		int id=0;
		try {id=reguardanteservice.ultimoId();}catch (Exception e) {
			id=0;
		}
		
		Presguardante.setId_resguardante(id+1);
		Presguardante.setFecha_inicio(formateador.format(ahora));
		Sresguardante.setId_resguardante(Presguardante.getId_resguardante()+1);
		Sresguardante.setFecha_inicio(formateador.format(ahora));
		Presguardante.setTipo_resguardante_id(tiporesguardoService.findOne((long) 1));
		Sresguardante.setTipo_resguardante_id(tiporesguardoService.findOne((long) 2));
		
		Presguardante.setActivo(true);
		Sresguardante.setActivo(true);
		
		List<Resguardante> res = new ArrayList<Resguardante>();
		res = reguardanteservice.findActivos();
		
		for(Resguardante re:res) {
			re.setActivo(false);
			reguardanteservice.save(re);
		
		}
		
		Vehiculo vehi = new Vehiculo();
		vehi = vehiculoService.findByPlaca(placa);
		Presguardante.setVehiculo(vehi);
		Sresguardante.setVehiculo(vehi);
		
		reguardanteservice.save(Presguardante);
		reguardanteservice.save(Sresguardante);
		
	    System.err.println("Primero: "+Presguardante.toString());
		
		String valor_oldp = Presguardante.toString();
		LogsAudit logsp = new LogsAudit();
     	
        logsp.setId_usuario(no_user);
		logsp.setTipo_role(user);
		logsp.setFecha(SystemDate.obtenFecha());
		logsp.setHora(ObtenHour.obtenHour());
		logsp.setName_table("RESGUARDANTE");
		logsp.setValor_viejo(valor_oldp);
		logsp.setTipo_accion("INSERT");
								
		System.err.println("Segundo: "+Sresguardante.toString());
		
		String valor_olds = Sresguardante.toString();
		LogsAudit logss = new LogsAudit();
     	
        logss.setId_usuario(no_user);
		logss.setTipo_role(user);
		logss.setFecha(SystemDate.obtenFecha());
		logss.setHora(ObtenHour.obtenHour());
		logss.setName_table("RESGUARDANTE");
		logss.setValor_viejo(valor_olds);
		logss.setTipo_accion("INSERT");
								
		logsauditService.save(logsp);
		logsauditService.save(logss);
		
		return "redirect:/infoResguardante/"+vehi.getId_vehiculo();
	}
		
	@RequestMapping(value="/TResguardante/{placa}{Pnombre}{Papellido1}{Papellido2}{Pcargo}{Snombre}{Sapellido1}{Sapellido2}{Scargo}" )
	public String Tres(Map<String,Object> model,
			@PathVariable(value="placa") String placa,
			@RequestParam(value="Pnombre") String Pnombre,
			@RequestParam(value="Papellido1") String Papellido1,
			@RequestParam(value="Papellido2") String Papellido2,
			@RequestParam(value="Pcargo") String Pcargo,
			@RequestParam(value="Snombre") String Snombre,
			@RequestParam(value="Sapellido1") String Sapellido1,
			@RequestParam(value="Sapellido2") String Sapellido2,
			@RequestParam(value="Scargo") String Scargo
			) {

		Presguardante.setNombre(Pnombre);
		Sresguardante.setNombre(Snombre);
			
		Presguardante.setApellido1(Papellido1);
		Sresguardante.setApellido1(Sapellido1);		
		
		Presguardante.setApellido2(Papellido2);
		Sresguardante.setApellido2(Sapellido2);		
		
		Presguardante.setCargo(Pcargo);
		Sresguardante.setCargo(Scargo);
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		
		int id=0;
		try {id=reguardanteservice.ultimoId();}catch (Exception e) {
			id=0;
		}
		
		Presguardante.setId_resguardante(id+1);
		Presguardante.setFecha_inicio(formateador.format(ahora));
		Presguardante.setTipo_resguardante_id(tiporesguardoService.findOne((long) 1));
		Sresguardante.setTipo_resguardante_id(tiporesguardoService.findOne((long) 2));
		Sresguardante.setId_resguardante(Presguardante.getId_resguardante()+1);
		Sresguardante.setFecha_inicio(formateador.format(ahora));
		
		List<Usuario> usuariosdb = new ArrayList<Usuario>();
		List<Conductor> conductoresdb = new ArrayList<Conductor>();	
		
		
		usuariosdb = usuarioService.findAll();
		conductoresdb = conductorService.findAll();
	
		model.put("conductores", conductoresdb);
		model.put("usuarios",usuariosdb);
		model.put("placa", placa);					
		model.put("Presguardante", Presguardante);
		model.put("Sresguardante", Sresguardante);
		model.put("Tresguardante", Tresguardante);
		
		return "vehiculos/AddTResguardante";
	}
	
	@RequestMapping(value="/TResguardante/{placa}",method = RequestMethod.POST)
	public String tresPOST(Map<String,Object> model,Resguardante resguardante,
			@PathVariable(value="placa") String placa,Authentication authentication
			) {
		
		 var user="";
		   var no_user ="";
		   no_user = authentication.getName();
				
			
					if(hasRole("ROLE_ADMIN")) {
						user ="ROLE_ADMIN";
						}else {
							if(hasRole("ROLE_USER")) {
								user = "ROLE_USER";
							}else {
								if(hasRole("ROLE_SEGURO")) {
									user = "ROLE_SEGURO";
								}
							}	
						}
					
		Long id = Sresguardante.getId_resguardante();
		Tresguardante.setNombre(resguardante.getNombre());			
		Tresguardante.setApellido1(resguardante.getApellido1());					
		Tresguardante.setApellido2(resguardante.getApellido2());					
		Tresguardante.setCargo(resguardante.getCargo());	
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		Tresguardante.setFecha_inicio(formateador.format(ahora));
		Tresguardante.setId_resguardante(id +1);	
		Tresguardante.setTipo_resguardante_id(tiporesguardoService.findOne((long) 3));
		System.out.println(Presguardante.getId_resguardante());
		System.out.println(Sresguardante.getId_resguardante());
		System.out.println(Tresguardante.getId_resguardante());
		
		Presguardante.setActivo(true);
		Sresguardante.setActivo(true);
		Tresguardante.setActivo(true);
		
		List<Resguardante> res = new ArrayList<Resguardante>();
		res = reguardanteservice.findActivos();
		
		for(Resguardante re:res) {
			re.setFecha_fin(formateador.format(ahora));
			re.setActivo(false);
			reguardanteservice.save(re);
		}
		
		Vehiculo vehi = new Vehiculo();
		vehi = vehiculoService.findByPlaca(placa);
		Presguardante.setVehiculo(vehi);
		Sresguardante.setVehiculo(vehi);
		Tresguardante.setVehiculo(vehi);

		reguardanteservice.save(Presguardante);
		reguardanteservice.save(Sresguardante);
		reguardanteservice.save(Tresguardante);
		
		 System.err.println("Primero: "+Presguardante.toString());
		 
			String valor_oldp = Presguardante.toString();
			LogsAudit logsp = new LogsAudit();
	     	
	        logsp.setId_usuario(no_user);
			logsp.setTipo_role(user);
			logsp.setFecha(SystemDate.obtenFecha());
			logsp.setHora(ObtenHour.obtenHour());
			logsp.setName_table("RESGUARDANTE");
			logsp.setValor_viejo(valor_oldp);
			logsp.setTipo_accion("INSERT");
									
			System.err.println("Segundo: "+Sresguardante.toString());
			
			String valor_olds = Sresguardante.toString();
			LogsAudit logss = new LogsAudit();
	     	
	        logss.setId_usuario(no_user);
			logss.setTipo_role(user);
			logss.setFecha(SystemDate.obtenFecha());
			logss.setHora(ObtenHour.obtenHour());
			logss.setName_table("RESGUARDANTE");
			logss.setValor_viejo(valor_olds);
			logss.setTipo_accion("INSERT");
			
            System.err.println("Tercero: "+Tresguardante.toString());
			
			String valor_oldt = Tresguardante.toString();
			LogsAudit logst = new LogsAudit();
	     	
	        logst.setId_usuario(no_user);
			logst.setTipo_role(user);
			logst.setFecha(SystemDate.obtenFecha());
			logst.setHora(ObtenHour.obtenHour());
			logst.setName_table("RESGUARDANTE");
			logst.setValor_viejo(valor_oldt);
			logst.setTipo_accion("INSERT");
		
			logsauditService.save(logsp);
			logsauditService.save(logss);
			logsauditService.save(logst);
		
		model.put("Presguardante", Presguardante);
		model.put("Sresguardante", Sresguardante);
		model.put("Tresguardante", Tresguardante);				
		return "redirect:/infoResguardante/"+vehi.getId_vehiculo();
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
