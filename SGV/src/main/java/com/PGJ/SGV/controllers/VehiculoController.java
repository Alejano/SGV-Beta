package com.PGJ.SGV.controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.PGJ.SGV.models.entity.Adscripcion;
import com.PGJ.SGV.models.entity.Conductor;
import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.models.entity.Resguardante;
import com.PGJ.SGV.models.entity.Seguro;
import com.PGJ.SGV.models.entity.TipoResguardante;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.models.entity.VehiculoDetalle;
import com.PGJ.SGV.models.entity.VehiculoEstado;
import com.PGJ.SGV.models.entity.VehiculoFuncion;
import com.PGJ.SGV.models.entity.VehiculoMarca;
import com.PGJ.SGV.models.entity.VehiculoTransmision;
import com.PGJ.SGV.service.IAdscripcionService;
import com.PGJ.SGV.service.IConductorService;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.service.IResguardanteService;
import com.PGJ.SGV.service.ISeguroService;
import com.PGJ.SGV.service.ITipoResguardanteService;
import com.PGJ.SGV.service.IUploadFileService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.util.paginador.PageRender;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;

@Controller
public class VehiculoController {
	
	public static String URL ="";
	
	List<Adscripcion> adscripcionlist = new ArrayList<Adscripcion>();
	List<Seguro> seguros = new ArrayList<Seguro>();
	List<Vehiculo> vehiculo = new ArrayList<Vehiculo>();
	List<VehiculoEstado> estado = new ArrayList<VehiculoEstado>();
	List<VehiculoTransmision> transmision = new ArrayList<VehiculoTransmision>();
	List<VehiculoFuncion> funcion = new ArrayList<VehiculoFuncion>();
	List<String> clase = new ArrayList<String>();
	
	public static Resguardante Presguardante = new Resguardante();
	public static Resguardante Sresguardante = new Resguardante();
	public static Resguardante Tresguardante = new Resguardante();
	
	Usuario resguardante1 = new Usuario();
	Usuario resguardante2 = new Usuario();
	Vehiculo detalle_old = new Vehiculo();
	
	static boolean editar = false;
	String coche="";
	String user;
	static int 	Corddocu = 0;
	static int 	Cordtabla = 0;
	
	@Autowired
	private IVehiculoService vehiculoService;	
	@Autowired
	private IAdscripcionService adscripService;
	@Autowired
	private ISeguroService seguroService;
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IUploadFileService uploadFileService;
	@Autowired
	private IConductorService conductorService;
	@Autowired
	private ITipoResguardanteService tiporesguardoService;
	@Autowired
	private IResguardanteService reguardanteservice;
	@Autowired
	private ILogsAuditService logsauditService;
	@Autowired
	private IObtenerUserService obuserService;
	
	
	@RequestMapping(value="/Vehiculos", method = RequestMethod.GET)
	public String listar(Model model,@RequestParam(name="page", defaultValue = "0") int page) {		
		//List<Vehiculo> vehiculoArea = new ArrayList<Vehiculo>();			
				
		var ads="";
		ads = obuserService.obtenEmpl();
		user = obuserService.obtenUser();
		model.addAttribute("usuario",user);
		
		clase = vehiculoService.findallByClase();	
		Pageable pageRequest = PageRequest.of(page, 1000);
		
		if(user.equals("ROLE_USER")){
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);								
			
			//vehiculoArea = vehiculoService.findVehiculosArea(usus.getAdscripcion().getId_adscripcion());
			Page<Vehiculo> vehiculoPageAra = vehiculoService.findVehiculosAreaPage(usus.getAdscripcion().getId_adscripcion(), pageRequest);
			PageRender<Vehiculo> pageRender = new PageRender<>("/Vehiculos", vehiculoPageAra);
			if(vehiculoService.totalVehiculo()>=4) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
			//model.addAttribute("vehiculos",vehiculoArea);						
			
			model.addAttribute("Corddocu",Corddocu);
			model.addAttribute("Cordtabla",Cordtabla);
			model.addAttribute("thisurl","Vehiculos");
			model.addAttribute("PageTitulo", "Vehiculos");
            model.addAttribute("PageSubTitulo", "Listado de Vehiculos");
			model.addAttribute("vehiculos",vehiculoPageAra);		
			model.addAttribute("page",pageRender);
			model.addAttribute("PageTitulo", "Vehiculos");
			return "Vehiculos";
		}
		
		//adscripcionlist = adscripService.findAll();
		//seguros = seguroService.findAll();
		//vehiculo = vehiculoService.findAll();		
		
		
		Page<Vehiculo> vehiculopage = vehiculoService.findTVechiulo("AUTOMOVIL", pageRequest);
		PageRender<Vehiculo> pageRender = new PageRender<>("/Vehiculos", vehiculopage);
		int tamaño = 4;
		if(vehiculoService.totalVehiculo()>= tamaño) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};

		model.addAttribute("Corddocu",Corddocu);
		model.addAttribute("marca",clase);
		model.addAttribute("Cordtabla",Cordtabla);
		model.addAttribute("thisurl","Vehiculos");
		model.addAttribute("titulo","Listado de Vehiculos");
		model.addAttribute("PageTitulo", "Vehiculos");
        model.addAttribute("PageSubTitulo", "Listado de Vehiculos");
		model.addAttribute("vehiculos",vehiculopage);
		model.addAttribute("page",pageRender);
		model.addAttribute("Clase","AUTOMOVIL");
		model.addAttribute("PageTitulo", "Vehiculos");
		
		return "Vehiculos";
	}
	
	@RequestMapping(value="/VehiculoClase", method = RequestMethod.GET)
	public String listarporClase(@RequestParam(value="clase") String Clase,Model model,
			@RequestParam(name="page", defaultValue = "0") int page) {	
		
		var ads="";
		ads = obuserService.obtenEmpl();
		user = obuserService.obtenUser();
		model.addAttribute("usuario",user);	
		List<String> clase = new ArrayList<String>();
		
		Pageable pageRequest = PageRequest.of(page, 1000);
		
		if(user.equals("ROLE_USER")){
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);								
			
			//vehiculoArea = vehiculoService.findVehiculosArea(usus.getAdscripcion().getId_adscripcion());
			Page<Vehiculo> vehiculoPageAra = vehiculoService.findVehiculosAreaPage(usus.getAdscripcion().getId_adscripcion(), pageRequest);
			PageRender<Vehiculo> pageRender = new PageRender<>("/Vehiculos", vehiculoPageAra);
			if(vehiculoService.totalVehiculo()>=4) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
			//model.addAttribute("vehiculos",vehiculoArea);						
			
			model.addAttribute("Corddocu",Corddocu);
			model.addAttribute("Cordtabla",Cordtabla);
			model.addAttribute("thisurl","Vehiculos");
			model.addAttribute("PageTitulo", "Vehiculos");
            model.addAttribute("PageSubTitulo", "Listado de Vehiculos");
			model.addAttribute("vehiculos",vehiculoPageAra);		
			model.addAttribute("page",pageRender);
			model.addAttribute("PageTitulo", "Vehiculos");
			return "Vehiculos";
		}
		
		//adscripcionlist = adscripService.findAll();
		//seguros = seguroService.findAll();
		//vehiculo = vehiculoService.findAll();		
		clase = vehiculoService.findallByClase();			
		Page<Vehiculo> vehiculopage = vehiculoService.findTVechiulo(Clase, pageRequest);
		PageRender<Vehiculo> pageRender = new PageRender<>("/Vehiculos", vehiculopage);
		int tamaño = 4;
		if(vehiculoService.totalVehiculo()>= tamaño) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
	
		model.addAttribute("Corddocu",Corddocu);
		model.addAttribute("marca",clase);
		model.addAttribute("Cordtabla",Cordtabla);
		model.addAttribute("thisurl","Vehiculos");
		model.addAttribute("titulo","Listado de Vehiculos");
		model.addAttribute("PageTitulo", "Vehiculos");
        model.addAttribute("PageSubTitulo", "Listado de Vehiculos");
		model.addAttribute("vehiculos",vehiculopage);
		model.addAttribute("page",pageRender);
		model.addAttribute("Clase",Clase);
		model.addAttribute("PageTitulo", "Vehiculos");
		return "Vehiculos";
	}
	
	
	@RequestMapping(value="/formVehiP1")
	public String crearP1(Map<String,Object> model) {
		
		Resguardante Presguardante = new Resguardante();
		Resguardante Sresguardante = new Resguardante();
		Resguardante Tresguardante = new Resguardante();
		
		TipoResguardante tiposResguardoP = new TipoResguardante();
		TipoResguardante tiposResguardoS = new TipoResguardante();
		
		tiposResguardoP = tiporesguardoService.findOne((long)1);
		tiposResguardoS = tiporesguardoService.findOne((long)2);
		
		List<Usuario> usuariosdb = new ArrayList<Usuario>();
		List<Conductor> conductoresdb = new ArrayList<Conductor>();	
		usuariosdb = usuarioService.findAll();
		conductoresdb = conductorService.findAll();
		
		model.put("Presguardante", Presguardante);
		model.put("Sresguardante", Sresguardante);
		model.put("Tresguardante", Tresguardante);
		model.put("tiposResguardoP", tiposResguardoP);
		model.put("tiposResguardoS", tiposResguardoS);
		model.put("conductores", conductoresdb);
		model.put("PageTitulo", "Agregar Vehiculo");
		model.put("usuarios",usuariosdb);
		model.put("PageTitulo", "Agregar Vehiculo");
		
		return "vehiculos/formVehiP1";
	}
	
	@RequestMapping(value="/formVehiP2",method = RequestMethod.POST)
	public String crearP1post(Map<String,Object> model,Resguardante resguardante) {
		
		user = obuserService.obtenUser();
		model.put("usuario",user);
						
		transmision = vehiculoService.findAllTransmision();
		funcion = vehiculoService.findAllFuncion();					
		editar = false;				
		List<String> marca = new ArrayList<String>();
		marca = vehiculoService.findAllMarcaUnica();
		
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
		
		adscripcionlist = adscripService.findAll();
		seguros = seguroService.findAll();
	
		Vehiculo vehi = new Vehiculo();
			
		long id_vehiculo = vehiculoService.ultimoId()+1;		
		vehi.setId_vehiculo(id_vehiculo);
		VehiculoDetalle detalle = new VehiculoDetalle();
		
		VehiculoEstado estado = new VehiculoEstado();
		estado = vehiculoService.findByVehiculoEstado((long) 1);
		vehi.setVehiculo_estado(estado);
		model.put("estado", estado);
		model.put("id", id_vehiculo);
		model.put("transmisiones", transmision);
		model.put("funciones", funcion);
		model.put("editando", "no");
		model.put("seguroslist", seguros);
		model.put("adslist",adscripcionlist );
		model.put("vehiculo", vehi);
		model.put("marcas",marca);	
		model.put("detalle",detalle);
		model.put("titulo", "Formulario de Vehiculos");	
		model.put("PageTitulo", "Agregar Vehiculo");
		model.put("Presguardante", Presguardante);
		model.put("Sresguardante", Sresguardante);
		model.put("Tresguardante", Tresguardante);		
				
		return "/formVehi";
		
	}
		
	
	@RequestMapping(value="/formVehiTResguardante{Pnombre}{Papellido1}{Papellido2}{Pcargo}{Snombre}{Sapellido1}{Sapellido2}{Scargo}" )
	public String tresguardante(Map<String,Object> model,
			@RequestParam(value="Pnombre") String Pnombre,
			@RequestParam(value="Papellido1") String Papellido1,
			@RequestParam(value="Papellido2") String Papellido2,
			@RequestParam(value="Pcargo") String Pcargo,
			@RequestParam(value="Snombre") String Snombre,
			@RequestParam(value="Sapellido1") String Sapellido1,
			@RequestParam(value="Sapellido2") String Sapellido2,
			@RequestParam(value="Scargo") String Scargo
			) {
		
		List<Usuario> usuariosactualizada = new ArrayList<Usuario>();
		List<Conductor> conductoresactualizada = new ArrayList<Conductor>();				
		List<Usuario> usuariosdb = new ArrayList<Usuario>();
		List<Conductor> conductoresdb = new ArrayList<Conductor>();	
		
		usuariosdb = usuarioService.findAll();
		conductoresdb = conductorService.findAll();
		
		for(Usuario us:usuariosdb) {
			//System.out.println(us.getNombre()+" "+Pnombre+" "+us.getApellido1()+" "+Papellido1+" "+us.getApellido2()+" "+Papellido2);
			if(us.getNombre() != Pnombre && us.getApellido1() != Papellido1 && us.getApellido2() != Papellido2) {
				usuariosactualizada.add(us);
			};
		};
		
		for(Conductor us:conductoresdb) {
			if(us.getNombre() != Snombre && us.getApellido1() != Sapellido1 && us.getApellido2() != Sapellido2) {
				conductoresactualizada.add(us);
			};
		};

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
	
		model.put("Presguardante", Presguardante);
		model.put("Sresguardante", Sresguardante);
		model.put("Tresguardante", Tresguardante);
		model.put("PageTitulo", "Agregar Vehiculo");
		model.put("conductores", conductoresactualizada);
		model.put("usuarios",usuariosactualizada);
		return "vehiculos/formVehiTR";
	}
	
	@RequestMapping(value="/formVehiTResguardante",method = RequestMethod.POST)
	public String tresguardantePOST(Map<String,Object> model,Resguardante resguardante
			) {
		
		Tresguardante.setNombre(resguardante.getNombre());			
		Tresguardante.setApellido1(resguardante.getApellido1());					
		Tresguardante.setApellido2(resguardante.getApellido2());					
		Tresguardante.setCargo(resguardante.getCargo());	
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		Tresguardante.setFecha_inicio(formateador.format(ahora));
		Tresguardante.setTipo_resguardante_id(tiporesguardoService.findOne((long)3));
		Tresguardante.setId_resguardante(Sresguardante.getId_resguardante()+1);	
		
		//Long id = Sresguardante.getId_resguardante();
		//Tresguardante.setId_resguardante(id+1);				
		
		transmision = vehiculoService.findAllTransmision();
		funcion = vehiculoService.findAllFuncion();					
		editar = false;
		List<String> marca = new ArrayList<String>();
		marca = vehiculoService.findAllMarcaUnica();	
				
		adscripcionlist = adscripService.findAll();
		seguros = seguroService.findAll();
	
		Vehiculo vehi = new Vehiculo();
			
		long id_vehiculo = vehiculoService.ultimoId()+1;		
		vehi.setId_vehiculo(id_vehiculo);
		VehiculoDetalle detalle = new VehiculoDetalle();
		
		VehiculoEstado estado = new VehiculoEstado();
		estado = vehiculoService.findByVehiculoEstado((long) 1);
		vehi.setVehiculo_estado(estado);
		model.put("estado", estado);
		model.put("id", id_vehiculo);
		model.put("transmisiones", transmision);
		model.put("funciones", funcion);
		model.put("editando", "no");
		model.put("seguroslist", seguros);
		model.put("adslist",adscripcionlist );
		model.put("vehiculo", vehi);
		model.put("marcas",marca);	
		model.put("detalle",detalle);
		model.put("titulo", "Formulario de Vehiculos");	
		model.put("PageTitulo", "Agregar Vehiculo");
		model.put("Presguardante", Presguardante);
		model.put("Sresguardante", Sresguardante);
		model.put("Tresguardante", Tresguardante);				
		return "formVehi";
	}
		
	
	@RequestMapping(value="/formVehi")
	public String crear(Map<String,Object> model) {
		transmision = vehiculoService.findAllTransmision();
		funcion = vehiculoService.findAllFuncion();					
		editar = false;
		List<String> marca = new ArrayList<String>();
		marca = vehiculoService.findAllMarcaUnica();	
				
		adscripcionlist = adscripService.findAll();
		seguros = seguroService.findAll();
	
		Vehiculo vehi = new Vehiculo();
		VehiculoDetalle detalle = new VehiculoDetalle();		
		vehi.setMotivo("Alta de Vehiculo");
		model.put("transmisiones", transmision);
		model.put("funciones", funcion);
		model.put("editando", "no");
		model.put("seguroslist", seguros);
		model.put("adslist",adscripcionlist );
		model.put("PageTitulo", "Agregar Vehiculo");
		model.put("vehiculo", vehi);
		model.put("marcas",marca);	
		model.put("detalle",detalle);
		model.put("PageTitulo", "Agregar Vehiculo");
		model.put("titulo", "Formulario de Vehiculos");
							
		return "formVehi";
	}
	
	@RequestMapping(value="/formVehi/{id_vehiculo}")
	public String editar(@PathVariable(value="id_vehiculo") Long id_vehiculo,Map<String,Object>model) {		
		adscripcionlist = adscripService.findAll();
		Vehiculo vehiculo = null;
		VehiculoDetalle detalle = null;
		
		List<String> marca = new ArrayList<String>();
		String documento = "";								
		
		user = obuserService.obtenUser();
		model.put("usuario",user);	
	
		editar = true;
		if(id_vehiculo != null) {
			vehiculo = vehiculoService.findOne(id_vehiculo);				
			marca = vehiculoService.findAllMarcaUnica();		
			transmision = vehiculoService.findAllTransmision();
			funcion = vehiculoService.findAllFuncion();
			detalle = vehiculoService.findDetalle(id_vehiculo);
			documento = vehiculoService.findTCDetalle(id_vehiculo);
			
			if(documento != null) {documento = "existe";}else{documento = "noexiste";};
			
			coche = vehiculo.getPlaca();
			
		}else {
			return "redirect:/Vehiculos";
		}
		
		vehiculo.setVehiculo_detalle(detalle);
		detalle_old.setVehiculo_detalle(detalle);
		
		model.put("transmisiones", transmision);
		model.put("funciones", funcion);
		model.put("editando", "si");
		model.put("documento", documento);
		model.put("seguroslist", seguros);
		model.put("adslist",adscripcionlist );		
		model.put("vehiculo",vehiculo);		
		model.put("detalle",detalle);	
		model.put("marcas",marca);			
		model.put("PageTitulo", "Editar Vehiculo");
		model.put("titulo", "Editar Vehiculo");	
		
		return "formVehi";
	}
	
	
	@RequestMapping(value="/formVehi",method = RequestMethod.POST)
	public String guardar(Vehiculo vehiculox,@RequestParam("file") MultipartFile tarjeta_circulacion){		
		
		String valor_oldetalle = detalle_old.getVehiculo_detalle().toString();
		VehiculoDetalle detalle = new VehiculoDetalle();
		
		detalle.setId_detalle(vehiculox.getId_vehiculo());
		detalle.setBalisado(vehiculox.getVehiculo_detalle().getBalisado());
		detalle.setBlindaje(vehiculox.getVehiculo_detalle().getBlindaje());
		detalle.setColor(vehiculox.getVehiculo_detalle().getColor());
		detalle.setFecha_compra(vehiculox.getVehiculo_detalle().getFecha_compra());
		detalle.setNo_cilindros(vehiculox.getVehiculo_detalle().getNo_cilindros());
		detalle.setNo_economico(vehiculox.getVehiculo_detalle().getNo_economico());
		detalle.setNo_motor(vehiculox.getVehiculo_detalle().getNo_motor());
		detalle.setNo_personas(vehiculox.getVehiculo_detalle().getNo_personas());
		detalle.setNo_puertas(vehiculox.getVehiculo_detalle().getNo_puertas());
		detalle.setTipo_combustible(vehiculox.getVehiculo_detalle().getTipo_combustible());
		detalle.setValor_compra(vehiculox.getVehiculo_detalle().getValor_compra());
		detalle.setVigencia_circulacion(vehiculox.getVehiculo_detalle().getVigencia_circulacion());
		detalle.setRin(vehiculox.getVehiculo_detalle().getRin());
		detalle.setTarjeta_circulacion(vehiculox.getVehiculo_detalle().getTarjeta_circulacion());
				
		if (!tarjeta_circulacion.isEmpty()) {

			if (detalle.getTarjeta_circulacion() != null 
				&& detalle.getTarjeta_circulacion().length() > 0) 
			{							
				uploadFileService.delete(detalle.getTarjeta_circulacion());				
			}
			String nombreUnico1 = null;
			
			try {
				nombreUnico1 = uploadFileService.copy(tarjeta_circulacion);		
				//System.out.println(nombreUnico1.length());
				detalle.setTarjeta_circulacion(nombreUnico1);
				//vehiculox.getVehiculo_detalle().setTarjeta_circulacion(nombreUnico1);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}	
		
		if(editar == false) {
				for(Vehiculo v:vehiculo) {
					if(v.getPlaca().equals(vehiculox.getPlaca())) {
						return "redirect:/idDuplicadoVehi/"+vehiculox.getPlaca();
					};
				}
				
				Presguardante.setActivo(true);
				Sresguardante.setActivo(true);
				Tresguardante.setActivo(true);
				Presguardante.setVehiculo(vehiculox);		
				Sresguardante.setVehiculo(vehiculox);
				Tresguardante.setVehiculo(vehiculox);
				vehiculox.setMotivo("Alta");
				vehiculox.setVehiculo_detalle(detalle);
				
				vehiculoService.save(vehiculox);
				
				reguardanteservice.save(Presguardante);
				reguardanteservice.save(Sresguardante);
				reguardanteservice.save(Tresguardante);
				
				System.err.println("Primero: "+Presguardante.toString());
				 
				String valor_oldp = Presguardante.toString();
				LogsAudit logsp = new LogsAudit();
		     	
				logsp.setId_usuario(obuserService.obtenEmpl());
	    		logsp.setTipo_role(obuserService.obtenUser());
				logsp.setFecha(SystemDate.obtenFecha());
				logsp.setHora(ObtenHour.obtenHour());
				logsp.setName_table("RESGUARDANTE");
				logsp.setValor_viejo(valor_oldp);
				logsp.setTipo_accion("INSERT");
										
				System.err.println("Segundo: "+Sresguardante.toString());
				
				String valor_olds = Sresguardante.toString();
				LogsAudit logss = new LogsAudit();
		     	
				logss.setId_usuario(obuserService.obtenEmpl());
	    		logss.setTipo_role(obuserService.obtenUser());
				logss.setFecha(SystemDate.obtenFecha());
				logss.setHora(ObtenHour.obtenHour());
				logss.setName_table("RESGUARDANTE");
				logss.setValor_viejo(valor_olds);
				logss.setTipo_accion("INSERT");
				
	            System.err.println("Tercero: "+Tresguardante.toString());
				
				String valor_oldt = Tresguardante.toString();
				LogsAudit logst = new LogsAudit();
		     	
				logst.setId_usuario(obuserService.obtenEmpl());
	    		logst.setTipo_role(obuserService.obtenUser());
				logst.setFecha(SystemDate.obtenFecha());
				logst.setHora(ObtenHour.obtenHour());
				logst.setName_table("RESGUARDANTE");
				logst.setValor_viejo(valor_oldt);
				logst.setTipo_accion("INSERT");
			
				logsauditService.save(logsp);
				logsauditService.save(logss);
				logsauditService.save(logst);

			    System.out.println("ALTA: "+vehiculox.toString());
			    String valor_nuevov = vehiculox.toString();
		     	String valor_nuevod = vehiculox.getVehiculo_detalle().toString();
			
			    LogsAudit logsv = new LogsAudit();
			    LogsAudit logsd = new LogsAudit();
	         
			    logsv.setId_usuario(obuserService.obtenEmpl());
	    		logsv.setTipo_role(obuserService.obtenUser());
				logsv.setFecha(SystemDate.obtenFecha());
				logsv.setHora(ObtenHour.obtenHour());
				logsv.setName_table("VEHICULOS");
				logsv.setValor_viejo(valor_nuevov);
				logsv.setTipo_accion("INSERT");
				
				logsd.setId_usuario(obuserService.obtenEmpl());
	    		logsd.setTipo_role(obuserService.obtenUser());
				logsd.setFecha(SystemDate.obtenFecha());
				logsd.setHora(ObtenHour.obtenHour());
				logsd.setName_table("VEHICULO_DETALLE");
				logsd.setValor_viejo(valor_nuevod);
				logsd.setTipo_accion("INSERT");
												
				logsauditService.save(logsv);
				logsauditService.save(logsd);

				return "redirect:Vehiculos";
		}else{
				if(!coche.equals(vehiculox.getPlaca())) {
							return "redirect:/idDuplicadoVehiCrea/"+vehiculox.getPlaca()+"/"+coche;
					};
				
				Vehiculo vehiculo_old = new Vehiculo();
				vehiculo_old = vehiculoService.findOne(vehiculox.getId_vehiculo());					
				String valor_olvehi = vehiculo_old.toString();
			
				vehiculox.setVehiculo_detalle(detalle);		
				//System.out.println("epieza: "+vehiculox.getVehiculo_detalle().getTarjeta_circulacion()+":para");
				vehiculox.setMotivo("Editado"+SystemDate.obtenFecha());
				vehiculoService.save(vehiculox);
				
				LogsAudit logsv = new LogsAudit();
				 LogsAudit logsd = new LogsAudit();
		         
				    logsv.setId_usuario(obuserService.obtenEmpl());
		    		logsv.setTipo_role(obuserService.obtenUser());
					logsv.setFecha(SystemDate.obtenFecha());
					logsv.setHora(ObtenHour.obtenHour());
					logsv.setName_table("VEHICULOS");
					logsv.setValor_viejo(valor_olvehi);
					logsv.setTipo_accion("UPDATE");
					
					logsd.setId_usuario(obuserService.obtenEmpl());
		    		logsd.setTipo_role(obuserService.obtenUser());
					logsd.setFecha(SystemDate.obtenFecha());
					logsd.setHora(ObtenHour.obtenHour());
					logsd.setName_table("VEHICULO_DETALLE");
					logsd.setValor_viejo(valor_oldetalle);
					logsd.setTipo_accion("UPDATE");
													
					logsauditService.save(logsv);
					logsauditService.save(logsd);
				
		};		
		
		editar = false;						
		return "redirect:Vehiculos";

	}
	
	
	@RequestMapping(value="/elimVehi/{placa}")
	public String eliminar (@PathVariable(value="placa")Long placa) {
		
		if(placa != null) {
			vehiculoService.delete(placa);
		}
		return "redirect:/Vehiculos";
	}		
	
	/*
	@RequestMapping(value="/estadoVehi/{placa}/{estado}/{url}/{Corddocu}/{Cordtabla}")
	public String estado (@PathVariable(value="placa")String placa,@PathVariable(value="estado")String estado,@PathVariable(value="url")String url,
			@PathVariable(value="Corddocu")int docu,@PathVariable(value="Cordtabla")int tabla,@RequestParam(name="page", defaultValue = "0") int page) {
		Vehiculo v = new Vehiculo();
		var seteo = "";
		Corddocu =docu;
		Cordtabla = tabla;
		if(placa != "") {
			v = vehiculoService.findOne(placa);
			switch (estado) {
			case "DISPONIBLE":
								seteo="NO DISPONIBLE";
								v.setEstado(seteo);
				break;

			case "NO DISPONIBLE":
								seteo="DISPONIBLE";
								v.setEstado(seteo);				
				break;
				
			case "EN VIAJE":
				seteo="DISPONIBLE";
				v.setEstado(seteo);				
				break;
				
			
					};
		}else {
		return "redirect:/Vehiculos";
		}
		vehiculoService.save(v);
		
	return "redirect:/"+url+"?page="+page;
	}*/
	
	@RequestMapping(value="/formVehBuscar")
	public String Buscartabla(@RequestParam(name="page", defaultValue = "0") 
	int page,@RequestParam(value="elemento") String elemento,Model model){	
		
		Pageable pageRequest = PageRequest.of(page, 1000);
		 Double Dato;
		 var ads="";
		 ads = obuserService.obtenEmpl();
		 user = obuserService.obtenUser();
		 model.addAttribute("usuario",user);	
		
		if(!elemento.isBlank()) {			
			if(isValidDouble(elemento)) {
					Dato = Double.parseDouble(elemento);
					DecimalFormat formt = new DecimalFormat("0");
					elemento = formt.format(Dato);
					elemento = elemento.replaceAll(",","");	
			};
			
			if(user == "ROLE_USER") {
				 Usuario usus = new Usuario();
				usus = usuarioService.findbyAdscripcion(ads);					
				Page<Vehiculo> vehiculopage = vehiculoService.findVehElemAreaPage(usus.getAdscripcion().getId_adscripcion(), elemento, pageRequest);
				if(vehiculoService.totalVehiculo()>=4) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
				PageRender<Vehiculo> pageRender = new PageRender<>("/formVehBuscar?elemento="+elemento, vehiculopage);
		
				model.addAttribute("marca",clase);
				model.addAttribute("vehiculos",vehiculopage);
				model.addAttribute("page",pageRender);
				model.addAttribute("elemento",elemento);
				model.addAttribute("PageTitulo", "Vehiculos");
					return "Vehiculos";
			};					
			Page<Vehiculo> vehiculopage= vehiculoService.findVehElemntoPage(elemento, pageRequest);		 									
			PageRender<Vehiculo> pageRender = new PageRender<>("/formVehBuscar?elemento="+elemento, vehiculopage);
			if(vehiculoService.totalVehiculo()>=4) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};

			model.addAttribute("marca",clase);
			model.addAttribute("vehiculos",vehiculopage);
			model.addAttribute("page",pageRender);
			model.addAttribute("elemento",elemento);	
			model.addAttribute("PageTitulo", "Vehiculos");
			return "Vehiculos";
		}else {
			return "redirect:/Vehiculos";
		}
						
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
	
	@RequestMapping(value = "/refreshSubMarca")
	public @ResponseBody String refreshSubMarca(@RequestParam("nombre_marca") String nombre_marca, Model model) {
		
		List<String> itemList = vehiculoService.findallsubMarcaUnica(nombre_marca);
		String html="<option value=''> Selecciona la submarca </option>";
		
		for(String s:itemList) {
			
			html+="<option value='"+s+"'> "+s+" </option>";
		}
		
	    return html;
	    
	}	
	
	
	@RequestMapping(value = "/refreshModelo")
	public @ResponseBody String refreshModelo(@RequestParam("nombre_submarca") String nombre_submarca,@RequestParam("nombre_marca") String nombre_marca, Model model) {
		
		List<String> Modelos = vehiculoService.findallModeloUnico(nombre_marca,nombre_submarca);
		String html="<option value=''> Selecciona el Modelo </option>";
		
		for(String s:Modelos) {
			System.out.println(s);
			html+="<option value='"+s+"'> "+s+" </option>";
		}
		
	    return html;
	}
	
	
	@RequestMapping(value = "/refreshTipo")
	public @ResponseBody String refreshTipo(@RequestParam("nombre_submarca") String nombre_submarca,
			@RequestParam("nombre_marca") String nombre_marca,
			@RequestParam("modelo") String modelo,Model model) {
		
		List<String> tipos = vehiculoService.findallTipoUnico(nombre_marca, nombre_submarca, modelo);
		String html="<option value=''> Selecciona El Tipo  </option>";
		
		for(String s:tipos) {
			System.out.println(s);
			html+="<option value='"+s+"'> "+s+" </option>";
		}
		
	    return html;
	}
	
	
	@RequestMapping(value = "/refreshClase")
	public @ResponseBody String refreshClase(@RequestParam("nombre_submarca") String nombre_submarca,
			@RequestParam("nombre_marca") String nombre_marca,
			@RequestParam("modelo") String modelo,
			@RequestParam("tipo") String tipo,Model model) {

		List<String> tipos = vehiculoService.findallClaseUnico(nombre_marca, nombre_submarca, modelo,tipo);
		String html="<option value=''> Selecciona La Clase  </option>";
		
		for(String s:tipos) {
			System.out.println(s);
			html+="<option value='"+s+"'> "+s+" </option>";
		}
		
	    return html;
	}	
	
	
	@RequestMapping(value = "/refreshID")
	public @ResponseBody String refreshId(@RequestParam("nombre_submarca") String nombre_submarca,
			@RequestParam("nombre_marca") String nombre_marca,
			@RequestParam("modelo") String modelo,
			@RequestParam("tipo") String tipo,
			@RequestParam("clase") String clase,Model model) {
		
		VehiculoMarca marca = vehiculoService.findMarca(nombre_marca, nombre_submarca, modelo,tipo,clase);
		System.out.println(marca.getId_marca());
		String html="<input id=\"vehiculo_marca\" name=\"vehiculo_marca\" type=\"hidden\" value=\""+marca.getId_marca()+"\"/>";			
		
		model.addAttribute("marca", marca);
		
	    return html;
	}	
	
	
	@RequestMapping(value="/info/{id_vehiculo}")
	public String info(@PathVariable(value="id_vehiculo") Long id_vehiculo,Map<String,Object>model) {	
		
		adscripcionlist = adscripService.findAll();
		Vehiculo vehiculo = null;
		VehiculoDetalle detalle = null;
		
		List<String> marca = new ArrayList<String>();
		String documento = "";								
		user = obuserService.obtenUser();
		model.put("usuario",user);	

		if(id_vehiculo != null) {
			vehiculo = vehiculoService.findOne(id_vehiculo);				
			marca = vehiculoService.findAllMarcaUnica();		
			transmision = vehiculoService.findAllTransmision();
			funcion = vehiculoService.findAllFuncion();
			detalle = vehiculoService.findDetalle(id_vehiculo);
			documento = vehiculoService.findTCDetalle(id_vehiculo);
			
			if(documento != null) {documento = "existe";}else{documento = "noexiste";};
			
			coche = vehiculo.getPlaca();
			
		}else {
			return "redirect:/Vehiculos";
		}
		
		model.put("transmisiones", transmision);
		model.put("funciones", funcion);
		model.put("editando", "si");
		model.put("documento", documento);
		model.put("seguroslist", seguros);
		model.put("adslist",adscripcionlist );		
		model.put("vehiculo",vehiculo);
		model.put("detalle",detalle);
		model.put("marcas",marca);				
		model.put("PageTitulo", "Información del Vehiculo");
		model.put("titulo", "Información Vehiculo");	
		return "infoVehi";
	}	
	
}
