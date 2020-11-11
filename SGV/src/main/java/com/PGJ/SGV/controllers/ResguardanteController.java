package com.PGJ.SGV.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.PGJ.SGV.models.entity.Conductor;
import com.PGJ.SGV.models.entity.Resguardante;
import com.PGJ.SGV.models.entity.TipoResguardante;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.service.IConductorService;
import com.PGJ.SGV.service.IResguardanteService;
import com.PGJ.SGV.service.ITipoResguardanteService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.util.paginador.PageRender;

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
	
	public static Resguardante Presguardante = new Resguardante();
	public static Resguardante Sresguardante = new Resguardante();
	public static Resguardante Tresguardante = new Resguardante();
	static int 	Corddocu = 0;
	static int 	Cordtabla = 0;
	
	@RequestMapping(value="/infoResguardante/{id_vehiculo}", method = RequestMethod.GET)
	public String infoResguardo(Model model,Authentication authentication,@RequestParam(name="page", defaultValue = "0") int page
			,@PathVariable(value="id_vehiculo") long id_vehiculo) {						
				
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
			@PathVariable(value="placa") String placa
			) {			

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
			@PathVariable(value="placa") String placa
			) {
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

		
		model.put("Presguardante", Presguardante);
		model.put("Sresguardante", Sresguardante);
		model.put("Tresguardante", Tresguardante);				
		return "redirect:/infoResguardante/"+vehi.getId_vehiculo();
	}
	

}
