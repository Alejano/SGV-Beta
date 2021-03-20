package com.PGJ.SGV.controllers;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.PGJ.SGV.models.entity.Adscripcion;
import com.PGJ.SGV.models.entity.BajaVehiculo;
import com.PGJ.SGV.models.entity.Conductor;
import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.models.entity.Resguardante;
import com.PGJ.SGV.models.entity.Revista;
import com.PGJ.SGV.models.entity.Seguro;
import com.PGJ.SGV.models.entity.TipoResguardante;
import com.PGJ.SGV.models.entity.TipoVale;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.models.entity.VehiculoDetalle;
import com.PGJ.SGV.models.entity.VehiculoEstado;
import com.PGJ.SGV.models.entity.VehiculoFuncion;
import com.PGJ.SGV.models.entity.VehiculoMarca;
import com.PGJ.SGV.models.entity.VehiculoPlacas;
import com.PGJ.SGV.models.entity.VehiculoTransmision;
import com.PGJ.SGV.service.IAdscripcionService;
import com.PGJ.SGV.service.IBajaVehiculoService;
import com.PGJ.SGV.service.IConductorService;
import com.PGJ.SGV.service.IEstadoVehiculoService;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.service.IResguardanteService;
import com.PGJ.SGV.service.IRevistaService;
import com.PGJ.SGV.service.ISeguroService;
import com.PGJ.SGV.service.ITipoResguardanteService;
import com.PGJ.SGV.service.ITipoValeService;
import com.PGJ.SGV.service.IUploadFileService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.service.IVehiculoPlacasService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.util.paginador.PageRender;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;

import net.sf.jasperreports.engine.JRException;

//Calev 
import java.util.*;
import java.text.ParseException;

@Controller
public class VehiculoController {

	public static String URL = "";

	List<Adscripcion> adscripcionlist = new ArrayList<Adscripcion>();
	List<Seguro> seguros = new ArrayList<Seguro>();
	List<Vehiculo> vehiculo = new ArrayList<Vehiculo>();
	List<VehiculoEstado> estado = new ArrayList<VehiculoEstado>();
	List<VehiculoTransmision> transmision = new ArrayList<VehiculoTransmision>();
	List<VehiculoFuncion> funcion = new ArrayList<VehiculoFuncion>();
	List<TipoVale> vales = new ArrayList<TipoVale>();
	List<String> clase = new ArrayList<String>();

	public static Resguardante Presguardante = new Resguardante();
	public static Resguardante Sresguardante = new Resguardante();
	public static Resguardante Tresguardante = new Resguardante();

	Usuario resguardante1 = new Usuario();
	Usuario resguardante2 = new Usuario();
	Vehiculo detalle_old = new Vehiculo();

	static boolean editar = false;
	String coche = "";
	String user;
	static int Corddocu = 0;
	static int Cordtabla = 0;

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
	@Autowired
	private IEstadoVehiculoService estadoService;
	@Autowired
	private IRevistaService revistaService;
	@Autowired
	private IVehiculoPlacasService vehiculoplacaService;
	@Autowired
	private ITipoValeService tipovaleService;
	@Autowired
	private IBajaVehiculoService bajaVehiculoService;
	
	
	@RequestMapping(value = "/Vehiculos", method = RequestMethod.GET)
	public String listar(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
		// List<Vehiculo> vehiculoArea = new ArrayList<Vehiculo>();

		var ads = "";
		ads = obuserService.obtenEmpl();
		user = obuserService.obtenUser();
		model.addAttribute("usuario", user);

		clase = vehiculoService.findallByClase();
		Pageable pageRequest = PageRequest.of(page, 1000);

		if (user.equals("ROLE_USER")) {
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);

			// vehiculoArea =
			// vehiculoService.findVehiculosArea(usus.getAdscripcion().getId_adscripcion());
			Page<Vehiculo> vehiculoPageAra = vehiculoService
					.findVehiculosAreaPage(usus.getAdscripcion().getId_adscripcion(), pageRequest);
			PageRender<Vehiculo> pageRender = new PageRender<>("/Vehiculos", vehiculoPageAra);
			if (vehiculoService.totalVehiculo() >= 4) {
				model.addAttribute("tamano", "mostrar");
			} else {
				model.addAttribute("tamano", "no mostrar");
			}
			;
			// model.addAttribute("vehiculos",vehiculoArea);

			model.addAttribute("Corddocu", Corddocu);
			model.addAttribute("Cordtabla", Cordtabla);
			model.addAttribute("thisurl", "Vehiculos");
			model.addAttribute("PageTitulo", "Vehiculos");
			model.addAttribute("PageSubTitulo", "Listado de Vehiculos");
			model.addAttribute("vehiculos", vehiculoPageAra);
			model.addAttribute("page", pageRender);
			model.addAttribute("PageTitulo", "Vehiculos");
			// calev
			adscripcionlist = adscripService.findAll();
			Collections.sort(adscripcionlist, new Comparator<Adscripcion>() {
				public int compare(Adscripcion a1, Adscripcion a2) {
					return a1.getNombre_adscripcion().compareTo(a2.getNombre_adscripcion());
				}
			});
			model.addAttribute("adslist", adscripcionlist);
			// -calev
			return "Vehiculos";
		}

		// adscripcionlist = adscripService.findAll();
		// seguros = seguroService.findAll();
		// vehiculo = vehiculoService.findAll();

		Page<Vehiculo> vehiculopage = vehiculoService.findTVehiculo("AUTOMOVIL", pageRequest);
		PageRender<Vehiculo> pageRender = new PageRender<>("/Vehiculos", vehiculopage);
		int tamaño = 4;
		if (vehiculoService.totalVehiculo() >= tamaño) {
			model.addAttribute("tamano", "mostrar");
		} else {
			model.addAttribute("tamano", "no mostrar");
		}
		;

		model.addAttribute("Corddocu", Corddocu);
		// calev acomoda por clase
		Collections.sort(clase, new Comparator<String>() {
			public int compare(String a1, String a2) {
				return a1.compareTo(a2);
			}
		});
		// calev
		model.addAttribute("marca", clase);
		model.addAttribute("Cordtabla", Cordtabla);
		model.addAttribute("thisurl", "Vehiculos");
		model.addAttribute("titulo", "Listado de Vehiculos");
		model.addAttribute("PageTitulo", "Vehiculos");
		model.addAttribute("PageSubTitulo", "Listado de Vehiculos");
		model.addAttribute("vehiculos", vehiculopage);
		model.addAttribute("page", pageRender);
		model.addAttribute("Clase", "AUTOMOVIL");
		model.addAttribute("PageTitulo", "Vehiculos");
		// calev

		adscripcionlist = adscripService.findAll();
		Collections.sort(adscripcionlist, new Comparator<Adscripcion>() {
			public int compare(Adscripcion a1, Adscripcion a2) {
				return a1.getNombre_adscripcion().compareTo(a2.getNombre_adscripcion());
			}
		});
		model.addAttribute("adslist", adscripcionlist);

		return "Vehiculos";
	}

	@RequestMapping(value = "/VehiculoClase", method = RequestMethod.GET)
	public String listarporClase(@RequestParam(value = "clase") String Clase, Model model,
			@RequestParam(name = "page", defaultValue = "0") int page) {

		// calev
		adscripcionlist = adscripService.findAll();
		Collections.sort(adscripcionlist, new Comparator<Adscripcion>() {
			public int compare(Adscripcion a1, Adscripcion a2) {
				return a1.getNombre_adscripcion().compareTo(a2.getNombre_adscripcion());
			}
		});
		model.addAttribute("adslist", adscripcionlist);
		model.addAttribute("elemento", Clase);
		// ---------

		var ads = "";
		ads = obuserService.obtenEmpl();
		user = obuserService.obtenUser();
		model.addAttribute("usuario", user);
		List<String> clase = new ArrayList<String>();

		Pageable pageRequest = PageRequest.of(page, 1000);

		if (user.equals("ROLE_USER")) {
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);

			// vehiculoArea =
			// vehiculoService.findVehiculosArea(usus.getAdscripcion().getId_adscripcion());
			Page<Vehiculo> vehiculoPageAra = vehiculoService
					.findVehiculosAreaPage(usus.getAdscripcion().getId_adscripcion(), pageRequest);
			PageRender<Vehiculo> pageRender = new PageRender<>("/Vehiculos", vehiculoPageAra);
			if (vehiculoService.totalVehiculo() >= 4) {
				model.addAttribute("tamano", "mostrar");
			} else {
				model.addAttribute("tamano", "no mostrar");
			}
			;
			// model.addAttribute("vehiculos",vehiculoArea);

			model.addAttribute("Corddocu", Corddocu);
			model.addAttribute("Cordtabla", Cordtabla);
			model.addAttribute("thisurl", "Vehiculos");
			model.addAttribute("PageTitulo", "Vehiculos");
			model.addAttribute("PageSubTitulo", "Listado de Vehiculos");
			model.addAttribute("vehiculos", vehiculoPageAra);
			model.addAttribute("page", pageRender);
			model.addAttribute("PageTitulo", "Vehiculos");
			return "Vehiculos";
		}

		// adscripcionlist = adscripService.findAll();
		// seguros = seguroService.findAll();
		// vehiculo = vehiculoService.findAll();
		clase = vehiculoService.findallByClase();
		Page<Vehiculo> vehiculopage = vehiculoService.findTVehiculo(Clase, pageRequest);
		PageRender<Vehiculo> pageRender = new PageRender<>("/Vehiculos", vehiculopage);
		int tamaño = 4;
		if (vehiculoService.totalVehiculo() >= tamaño) {
			model.addAttribute("tamano", "mostrar");
		} else {
			model.addAttribute("tamano", "no mostrar");
		}
		;

		model.addAttribute("Corddocu", Corddocu);
		// calev acomoda por clase
		Collections.sort(clase, new Comparator<String>() {
			public int compare(String a1, String a2) {
				return a1.compareTo(a2);
			}
		});
		// calev
		model.addAttribute("marca", clase);
		model.addAttribute("Cordtabla", Cordtabla);
		model.addAttribute("thisurl", "Vehiculos");
		model.addAttribute("titulo", "Listado de Vehiculos");
		model.addAttribute("PageTitulo", "Vehiculos");
		model.addAttribute("PageSubTitulo", "Listado de Vehiculos");
		model.addAttribute("vehiculos", vehiculopage);
		model.addAttribute("page", pageRender);
		model.addAttribute("Clase", Clase);
		model.addAttribute("PageTitulo", "Vehiculos");
		return "Vehiculos";
	}

	@RequestMapping(value = "/formVehiP1")
	public String crearP1(Map<String, Object> model) {

		Resguardante Presguardante = new Resguardante();
		Resguardante Sresguardante = new Resguardante();
		Resguardante Tresguardante = new Resguardante();

		TipoResguardante tiposResguardoP = new TipoResguardante();
		TipoResguardante tiposResguardoS = new TipoResguardante();

		tiposResguardoP = tiporesguardoService.findOne((long) 1);
		tiposResguardoS = tiporesguardoService.findOne((long) 2);

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
		model.put("usuarios", usuariosdb);
		model.put("PageTitulo", "Agregar Vehiculo");
		// calev
		adscripcionlist = adscripService.findAll();
		Collections.sort(adscripcionlist, new Comparator<Adscripcion>() {
			public int compare(Adscripcion a1, Adscripcion a2) {
				return a1.getNombre_adscripcion().compareTo(a2.getNombre_adscripcion());
			}
		});

		model.put("adscripciones", adscripcionlist);
		// ---------

		return "vehiculos/formVehiP1";
	}

	@RequestMapping(value = "/formVehiP2", method = RequestMethod.POST)
	public String crearP1post(Map<String, Object> model, Resguardante resguardante) {

		user = obuserService.obtenUser();
		model.put("usuario", user);

		transmision = vehiculoService.findAllTransmision();
		vales = tipovaleService.findAll();
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

		// Calev
		String no_licencias = resguardante.getNo_licencia();
		String rfcs = resguardante.getRfc();
		String ines = resguardante.getIne();
		String telefonos = resguardante.getTelefono();
		String domicilios = resguardante.getDomicilio();
		String ids_adscripciones = resguardante.getIds_adscripcion();

		String[] no_licencia = no_licencias.split(",");
		String[] rfc = rfcs.split(",");
		String[] ine = ines.split(",");
		String[] telefono = telefonos.split(",");
		String[] domicilio = domicilios.split(",");
		String[] ids_adscripcion = ids_adscripciones.split(",");

		Presguardante.setNo_licencia(no_licencia[0]);
		Presguardante.setRfc(rfc[0]);
		Presguardante.setIne(ine[0]);
		Presguardante.setTelefono(telefono[0]);
		Presguardante.setDomicilio(domicilio[0]);

		// CALEV +
		Presguardante.setTipo_resguardante_id(tiporesguardoService.findOne((long) 1));
		Sresguardante.setTipo_resguardante_id(tiporesguardoService.findOne((long) 2));
		// -CALEV

		Adscripcion adsc1 = adscripService.findOne(Long.valueOf(ids_adscripcion[0]));
		Presguardante.setAdscripcion(adsc1);

		Sresguardante.setNo_licencia(no_licencia[1]);
		Sresguardante.setRfc(rfc[1]);
		Sresguardante.setIne(ine[1]);
		Sresguardante.setTelefono(telefono[1]);
		Sresguardante.setDomicilio(domicilio[1]);

		Adscripcion adsc2 = adscripService.findOne(Long.valueOf(ids_adscripcion[1]));
		Sresguardante.setAdscripcion(adsc2);
		// --Calev

		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		int id = 0;
		try {
			id = reguardanteservice.ultimoId();
		} catch (Exception e) {
			id = 0;
		}
		Presguardante.setId_resguardante(id + 1);
		Presguardante.setFecha_inicio(formateador.format(ahora));
		Sresguardante.setId_resguardante(Presguardante.getId_resguardante() + 1);
		Sresguardante.setFecha_inicio(formateador.format(ahora));

		adscripcionlist = adscripService.findAll();
		Collections.sort(adscripcionlist, new Comparator<Adscripcion>() {
			public int compare(Adscripcion a1, Adscripcion a2) {
				return a1.getNombre_adscripcion().compareTo(a2.getNombre_adscripcion());
			}
		});
		seguros = seguroService.findAll();

		Vehiculo vehi = new Vehiculo();

		long id_vehiculo = vehiculoService.ultimoId() + 1;
		vehi.setId_vehiculo(id_vehiculo);
		VehiculoDetalle detalle = new VehiculoDetalle();

		VehiculoEstado estado = new VehiculoEstado();
		estado = vehiculoService.findByVehiculoEstado((long) 1);
		vehi.setVehiculo_estado(estado);
		model.put("estado", estado);
		model.put("id", id_vehiculo);
		model.put("transmisiones", transmision);
		model.put("vales", vales);
		model.put("funciones", funcion);
		model.put("editando", "no");
		model.put("seguroslist", seguros);
		model.put("adslist", adscripcionlist);
		model.put("vehiculo", vehi);
		model.put("marcas", marca);
		model.put("detalle", detalle);
		model.put("titulo", "Formulario de Vehiculos");
		model.put("PageTitulo", "Agregar Vehiculo");
		model.put("Presguardante", Presguardante);
		model.put("Sresguardante", Sresguardante);
		model.put("Tresguardante", Tresguardante);

		return "/formVehi";

	}

	@RequestMapping(value = "/formVehiTResguardante{Pnombre}{Papellido1}{Papellido2}{Pcargo}{Snombre}{Sapellido1}{Sapellido2}{Scargo}{Snombre}{Sapellido1}{Sapellido2}{Scargo}{HPno_licencia}{HPrfc}{HPine}{HPtelefono}{HPdomicilio}{HPadscripcion_id_adscripcion}{HSno_licencia}{HSrfc}{HSine}{HStelefono}{HSdomicilio}{HSadscripcion_id_adscripcion}")
	public String tresguardante(Map<String, Object> model, @RequestParam(value = "Pnombre") String Pnombre,
			@RequestParam(value = "Papellido1") String Papellido1,
			@RequestParam(value = "Papellido2") String Papellido2, @RequestParam(value = "Pcargo") String Pcargo,
			@RequestParam(value = "Snombre") String Snombre, @RequestParam(value = "Sapellido1") String Sapellido1,
			@RequestParam(value = "Sapellido2") String Sapellido2, @RequestParam(value = "Scargo") String Scargo,
			// calev
			// Resguardante1
			@RequestParam(value = "HPno_licencia") String Pno_licencia, @RequestParam(value = "HPrfc") String Prfc,
			@RequestParam(value = "HPine") String Pine, @RequestParam(value = "HPtelefono") String Ptelefono,
			@RequestParam(value = "HPdomicilio") String Pdomicilio,
			@RequestParam(value = "HPadscripcion_id_adscripcion") String Padscripcion_id_adscripcion,
			// Resguardante2
			@RequestParam(value = "HSno_licencia") String Sno_licencia, @RequestParam(value = "HSrfc") String Srfc,
			@RequestParam(value = "HSine") String Sine, @RequestParam(value = "HStelefono") String Stelefono,
			@RequestParam(value = "HSdomicilio") String Sdomicilio,
			@RequestParam(value = "HSadscripcion_id_adscripcion") String Sadscripcion_id_adscripcion
	// --Calev
	) {

		List<Usuario> usuariosactualizada = new ArrayList<Usuario>();
		List<Conductor> conductoresactualizada = new ArrayList<Conductor>();
		List<Usuario> usuariosdb = new ArrayList<Usuario>();
		List<Conductor> conductoresdb = new ArrayList<Conductor>();

		usuariosdb = usuarioService.findAll();
		conductoresdb = conductorService.findAll();

		for (Usuario us : usuariosdb) {
			// System.out.println(us.getNombre()+" "+Pnombre+" "+us.getApellido1()+"
			// "+Papellido1+" "+us.getApellido2()+" "+Papellido2);
			if (us.getNombre() != Pnombre && us.getApellido1() != Papellido1 && us.getApellido2() != Papellido2) {
				usuariosactualizada.add(us);
			}
			;
		}
		;

		for (Conductor us : conductoresdb) {
			if (us.getNombre() != Snombre && us.getApellido1() != Sapellido1 && us.getApellido2() != Sapellido2) {
				conductoresactualizada.add(us);
			}
			;
		}
		;

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

		int id = 0;
		try {
			id = reguardanteservice.ultimoId();
		} catch (Exception e) {
			id = 0;
		}

		Presguardante.setId_resguardante(id + 1);
		Presguardante.setFecha_inicio(formateador.format(ahora));
		Presguardante.setTipo_resguardante_id(tiporesguardoService.findOne((long) 1));
		Sresguardante.setTipo_resguardante_id(tiporesguardoService.findOne((long) 2));
		Sresguardante.setId_resguardante(Presguardante.getId_resguardante() + 1);
		Sresguardante.setFecha_inicio(formateador.format(ahora));

		// CALEV
		Presguardante.setNo_licencia(Pno_licencia);
		Presguardante.setRfc(Prfc);
		Presguardante.setIne(Pine);
		Presguardante.setTelefono(Ptelefono);
		Presguardante.setDomicilio(Pdomicilio);
		Adscripcion adsc1 = adscripService.findOne(Long.valueOf(Padscripcion_id_adscripcion));
		Presguardante.setAdscripcion(adsc1);

		Sresguardante.setNo_licencia(Sno_licencia);
		Sresguardante.setRfc(Srfc);
		Sresguardante.setIne(Sine);
		Sresguardante.setTelefono(Stelefono);
		Sresguardante.setDomicilio(Sdomicilio);
		Adscripcion adsc2 = adscripService.findOne(Long.valueOf(Sadscripcion_id_adscripcion));
		Sresguardante.setAdscripcion(adsc2);

		adscripcionlist = adscripService.findAll();
		Collections.sort(adscripcionlist, new Comparator<Adscripcion>() {
			public int compare(Adscripcion a1, Adscripcion a2) {
				return a1.getNombre_adscripcion().compareTo(a2.getNombre_adscripcion());
			}
		});
		model.put("adscripciones", adscripcionlist);

		// --Calev

		model.put("Presguardante", Presguardante);
		model.put("Sresguardante", Sresguardante);
		model.put("Tresguardante", Tresguardante);

		model.put("PageTitulo", "Agregar Vehiculo");
		model.put("conductores", conductoresactualizada);
		model.put("usuarios", usuariosactualizada);
		return "vehiculos/formVehiTR";
	}

	@RequestMapping(value = "/formVehiTResguardante", method = RequestMethod.POST)
	public String tresguardantePOST(Map<String, Object> model, Resguardante resguardante) {

		Tresguardante.setNombre(resguardante.getNombre());
		Tresguardante.setApellido1(resguardante.getApellido1());
		Tresguardante.setApellido2(resguardante.getApellido2());
		Tresguardante.setCargo(resguardante.getCargo());
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		Tresguardante.setFecha_inicio(formateador.format(ahora));
		Tresguardante.setTipo_resguardante_id(tiporesguardoService.findOne((long) 3));
		Tresguardante.setId_resguardante(Sresguardante.getId_resguardante() + 1);

		// Long id = Sresguardante.getId_resguardante();
		// Tresguardante.setId_resguardante(id+1);

		transmision = vehiculoService.findAllTransmision();
		vales = tipovaleService.findAll();
		funcion = vehiculoService.findAllFuncion();
		editar = false;
		List<String> marca = new ArrayList<String>();
		marca = vehiculoService.findAllMarcaUnica();

		adscripcionlist = adscripService.findAll();
		seguros = seguroService.findAll();

		Vehiculo vehi = new Vehiculo();

		long id_vehiculo = vehiculoService.ultimoId() + 1;
		vehi.setId_vehiculo(id_vehiculo);
		VehiculoDetalle detalle = new VehiculoDetalle();
		// Calev
		Tresguardante.setNo_licencia(resguardante.getNo_licencia());
		Tresguardante.setRfc(resguardante.getRfc());
		Tresguardante.setIne(resguardante.getIne());
		Tresguardante.setTelefono(resguardante.getTelefono());
		Tresguardante.setDomicilio(resguardante.getDomicilio());
		Tresguardante.setAdscripcion(resguardante.getAdscripcion());
		// --calev

		VehiculoEstado estado = new VehiculoEstado();
		estado = vehiculoService.findByVehiculoEstado((long) 1);
		vehi.setVehiculo_estado(estado);
		model.put("estado", estado);
		model.put("id", id_vehiculo);
		model.put("transmisiones", transmision);
		model.put("vales", vales);
		model.put("funciones", funcion);
		model.put("editando", "no");
		model.put("seguroslist", seguros);
		Collections.sort(adscripcionlist, new Comparator<Adscripcion>() {
			public int compare(Adscripcion a1, Adscripcion a2) {
				return a1.getNombre_adscripcion().compareTo(a2.getNombre_adscripcion());
			}
		});
		model.put("adslist", adscripcionlist);
		model.put("vehiculo", vehi);
		model.put("marcas", marca);
		model.put("detalle", detalle);
		model.put("titulo", "Formulario de Vehiculos");
		model.put("PageTitulo", "Agregar Vehiculo");
		model.put("Presguardante", Presguardante);
		model.put("Sresguardante", Sresguardante);
		model.put("Tresguardante", Tresguardante);
		return "formVehi";
	}

	@RequestMapping(value = "/formVehi")
	public String crear(Map<String, Object> model) {
		transmision = vehiculoService.findAllTransmision();
		vales = tipovaleService.findAll();

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
		model.put("vales", vales);
		model.put("funciones", funcion);
		model.put("editando", "no");
		model.put("seguroslist", seguros);
		model.put("adslist", adscripcionlist);
		model.put("PageTitulo", "Agregar Vehiculo");
		model.put("vehiculo", vehi);
		model.put("marcas", marca);
		model.put("detalle", detalle);
		model.put("PageTitulo", "Agregar Vehiculo");
		model.put("titulo", "Formulario de Vehiculos");

		return "formVehi";
	}

	@RequestMapping(value = "/formVehi/{id_vehiculo}")
	public String editar(@PathVariable(value = "id_vehiculo") Long id_vehiculo, Map<String, Object> model) {

		adscripcionlist = adscripService.findAll();
		estado = estadoService.findEstados();

		Vehiculo vehiculo = null;
		VehiculoDetalle detalle = null;

		List<String> marca = new ArrayList<String>();
		String documento = "";

		user = obuserService.obtenUser();
		model.put("usuario", user);

		editar = true;
		if (id_vehiculo != null) {
			vehiculo = vehiculoService.findOne(id_vehiculo);
			marca = vehiculoService.findAllMarcaUnica();
			transmision = vehiculoService.findAllTransmision();
			vales = tipovaleService.findAll();
			funcion = vehiculoService.findAllFuncion();
			detalle = vehiculoService.findDetalle(id_vehiculo);
			documento = vehiculoService.findTCDetalle(id_vehiculo);

			if (documento != null) {
				documento = "existe";
			} else {
				documento = "noexiste";
			}
			;

			coche = vehiculo.getPlaca();

		} else {
			return "redirect:/Vehiculos";
		}

		vehiculo.setVehiculo_detalle(detalle);
		detalle_old.setVehiculo_detalle(detalle);

		model.put("transmisiones", transmision);
		model.put("vales", vales);
		model.put("funciones", funcion);
		model.put("editando", "si");
		model.put("documento", documento);
		model.put("seguroslist", seguros);
		model.put("adslist", adscripcionlist);
		model.put("estado", estado);
		model.put("vehiculo", vehiculo);
		model.put("detalle", detalle);
		model.put("marcas", marca);
		model.put("PageTitulo", "Editar Vehiculo");
		model.put("titulo", "Editar Vehiculo");

		return "formVehi";
	}

	@RequestMapping(value = "/formVehi", method = RequestMethod.POST)
	public String guardar(Vehiculo vehiculox, @RequestParam("file") MultipartFile tarjeta_circulacion) {

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

			if (detalle.getTarjeta_circulacion() != null && detalle.getTarjeta_circulacion().length() > 0) {
				uploadFileService.delete(detalle.getTarjeta_circulacion());
			}
			String nombreUnico1 = null;

			try {
				nombreUnico1 = uploadFileService.copy(tarjeta_circulacion);
				// System.out.println(nombreUnico1.length());
				detalle.setTarjeta_circulacion(nombreUnico1);
				// vehiculox.getVehiculo_detalle().setTarjeta_circulacion(nombreUnico1);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (editar == false) {
			for (Vehiculo v : vehiculo) {
				if (v.getPlaca().equals(vehiculox.getPlaca())) {
					return "redirect:/idDuplicadoVehi/" + vehiculox.getPlaca();
				}
				;
			}
			// calev
			Presguardante.setTipo_resguardante_id(tiporesguardoService.findOne((long) 1));
			Sresguardante.setTipo_resguardante_id(tiporesguardoService.findOne((long) 2));
			// -calev
			Presguardante.setActivo(true);
			Sresguardante.setActivo(true);
			Tresguardante.setActivo(true);
			Presguardante.setVehiculo(vehiculox);
			Sresguardante.setVehiculo(vehiculox);
			Tresguardante.setVehiculo(vehiculox);
			vehiculox.setMotivo("Alta");
			vehiculox.setVehiculo_detalle(detalle);
			// BRENDA CAMBIO 20/01/21
			vehiculox.setFecha_alta(SystemDate.obtenFecha());
			// --vehiculox.setNo_tarjeta();
			// -calev set numero de tarjeta
			// BRENDA

			vehiculoService.save(vehiculox);
			reguardanteservice.save(Presguardante);
			reguardanteservice.save(Sresguardante);
			try {
				if (!Tresguardante.getNombre().equals(null) || !Tresguardante.getNombre().equals("")) {
					System.out.println("Calev456 - Entro al if : " + Tresguardante);
					reguardanteservice.save(Tresguardante);
				}
			} catch (Exception e) {
			}

			System.err.println("Primero: " + Presguardante.toString());

			String valor_oldp = Presguardante.toString();
			LogsAudit logsp = new LogsAudit();

			logsp.setId_usuario(obuserService.obtenEmpl());
			logsp.setTipo_role(obuserService.obtenUser());
			logsp.setFecha(SystemDate.obtenFecha());
			logsp.setHora(ObtenHour.obtenHour());
			logsp.setName_table("RESGUARDANTE");
			logsp.setValor_viejo(valor_oldp);
			logsp.setTipo_accion("INSERT");

			System.err.println("Segundo: " + Sresguardante.toString());

			String valor_olds = Sresguardante.toString();
			LogsAudit logss = new LogsAudit();

			logss.setId_usuario(obuserService.obtenEmpl());
			logss.setTipo_role(obuserService.obtenUser());
			logss.setFecha(SystemDate.obtenFecha());
			logss.setHora(ObtenHour.obtenHour());
			logss.setName_table("RESGUARDANTE");
			logss.setValor_viejo(valor_olds);
			logss.setTipo_accion("INSERT");

			System.err.println("Tercero: " + Tresguardante.toString());

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

			System.out.println("ALTA: " + vehiculox.toString());
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

			return "redirect:/Vehiculos";
		} else {
			Vehiculo vehiPlacaDup = vehiculoService.findByPlaca(vehiculox.getPlaca());
			if ((vehiPlacaDup != null) && (vehiPlacaDup.getId_vehiculo()!=vehiculox.getId_vehiculo())) {
				System.out.println("Placaigual");
				return "redirect:/idDuplicadoVehiCrea/" + vehiculox.getPlaca() + "/" + coche;
			}

			Vehiculo vehiculo_old = new Vehiculo();
			vehiculo_old = vehiculoService.findOne(vehiculox.getId_vehiculo());
			String valor_olvehi = vehiculo_old.toString();

			vehiculox.setVehiculo_detalle(detalle);
			// System.out.println("epieza:
			// "+vehiculox.getVehiculo_detalle().getTarjeta_circulacion()+":para");
			vehiculox.setMotivo("Editado" + SystemDate.obtenFecha());
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

		}

		editar = false;
		return "redirect:/Vehiculos";

	}

	@RequestMapping(value = "/elimVehi/{placa}")
	public String eliminar(@PathVariable(value = "placa") Long placa) {

		if (placa != null) {
			vehiculoService.delete(placa);
		}
		return "redirect:/Vehiculos";
	}

	/*
	 * @RequestMapping(value=
	 * "/estadoVehi/{placa}/{estado}/{url}/{Corddocu}/{Cordtabla}") public String
	 * estado (@PathVariable(value="placa")String
	 * placa,@PathVariable(value="estado")String
	 * estado,@PathVariable(value="url")String url,
	 * 
	 * @PathVariable(value="Corddocu")int docu,@PathVariable(value="Cordtabla")int
	 * tabla,@RequestParam(name="page", defaultValue = "0") int page) { Vehiculo v =
	 * new Vehiculo(); var seteo = ""; Corddocu =docu; Cordtabla = tabla; if(placa
	 * != "") { v = vehiculoService.findOne(placa); switch (estado) { case
	 * "DISPONIBLE": seteo="NO DISPONIBLE"; v.setEstado(seteo); break;
	 * 
	 * case "NO DISPONIBLE": seteo="DISPONIBLE"; v.setEstado(seteo); break;
	 * 
	 * case "EN VIAJE": seteo="DISPONIBLE"; v.setEstado(seteo); break;
	 * 
	 * 
	 * }; }else { return "redirect:/Vehiculos"; } vehiculoService.save(v);
	 * 
	 * return "redirect:/"+url+"?page="+page; }
	 */

	@RequestMapping(value = "/formVehBuscar")
	public String Buscartabla(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(value = "elemento") String elemento, Model model) {

		Pageable pageRequest = PageRequest.of(page, 1000);
		Double Dato;
		var ads = "";
		ads = obuserService.obtenEmpl();
		user = obuserService.obtenUser();
		model.addAttribute("usuario", user);
		// calev
		adscripcionlist = adscripService.findAll();
		Collections.sort(adscripcionlist, new Comparator<Adscripcion>() {
			public int compare(Adscripcion a1, Adscripcion a2) {
				return a1.getNombre_adscripcion().compareTo(a2.getNombre_adscripcion());
			}
		});
		model.addAttribute("adslist", adscripcionlist);
		// calev acomoda por clase
		clase = vehiculoService.findallByClase();
		Collections.sort(clase, new Comparator<String>() {
			public int compare(String a1, String a2) {
				return a1.compareTo(a2);
			}
		});
		// calev
		model.addAttribute("marca", clase);
		model.addAttribute("elemento", elemento);
		System.out.println(elemento);
		//
		if (!elemento.isBlank()) {
			if (isValidDouble(elemento)) {
				Dato = Double.parseDouble(elemento);
				DecimalFormat formt = new DecimalFormat("0");
				elemento = formt.format(Dato);
				elemento = elemento.replaceAll(",", "");
			}
			;

			if (user == "ROLE_USER") {
				Usuario usus = new Usuario();
				usus = usuarioService.findbyAdscripcion(ads);
				Page<Vehiculo> vehiculopage = vehiculoService
						.findVehElemAreaPage(usus.getAdscripcion().getId_adscripcion(), elemento, pageRequest);
				if (vehiculoService.totalVehiculo() >= 4) {
					model.addAttribute("tamano", "mostrar");
				} else {
					model.addAttribute("tamano", "no mostrar");
				}
				;
				PageRender<Vehiculo> pageRender = new PageRender<>("/formVehBuscar?elemento=" + elemento, vehiculopage);

				// calev acomoda por clase
				Collections.sort(clase, new Comparator<String>() {
					public int compare(String a1, String a2) {
						return a1.compareTo(a2);
					}
				});
				// calev
				model.addAttribute("marca", clase);
				model.addAttribute("vehiculos", vehiculopage);
				model.addAttribute("page", pageRender);
				model.addAttribute("elemento", elemento);
				model.addAttribute("PageTitulo", "Vehiculos");
				model.addAttribute("PageSubTitulo", "Listado de Vehiculos");
				// calev
				adscripcionlist = adscripService.findAll();
				Collections.sort(adscripcionlist, new Comparator<Adscripcion>() {
					public int compare(Adscripcion a1, Adscripcion a2) {
						return a1.getNombre_adscripcion().compareTo(a2.getNombre_adscripcion());
					}
				});
				model.addAttribute("adslist", adscripcionlist);

				return "Vehiculos";
			}
			;
			Page<Vehiculo> vehiculopage = vehiculoService.findVehElemntoPage(elemento, pageRequest);
			PageRender<Vehiculo> pageRender = new PageRender<>("/formVehBuscar?elemento=" + elemento, vehiculopage);
			if (vehiculoService.totalVehiculo() >= 4) {
				model.addAttribute("tamano", "mostrar");
			} else {
				model.addAttribute("tamano", "no mostrar");
			}
			;
			// calev acomoda por clase
			Collections.sort(clase, new Comparator<String>() {
				public int compare(String a1, String a2) {
					return a1.compareTo(a2);
				}
			});
			// calev
			model.addAttribute("marca", clase);
			model.addAttribute("vehiculos", vehiculopage);
			model.addAttribute("page", pageRender);
			model.addAttribute("elemento", elemento);
			model.addAttribute("PageTitulo", "Vehiculos");
			model.addAttribute("PageSubTitulo", "Listado de Vehiculos");
			// calev
			adscripcionlist = adscripService.findAll();
			Collections.sort(adscripcionlist, new Comparator<Adscripcion>() {
				public int compare(Adscripcion a1, Adscripcion a2) {
					return a1.getNombre_adscripcion().compareTo(a2.getNombre_adscripcion());
				}
			});
			model.addAttribute("adslist", adscripcionlist);

			return "Vehiculos";
		} else {
			return "redirect:/Vehiculos";
		}

	}

	private static boolean isValidDouble(String s) {
		final String Digits = "(\\p{Digit}+)";
		final String HexDigits = "(\\p{XDigit}+)";

		final String Exp = "[eE][+-]?" + Digits;
		final String fpRegex = ("[\\x00-\\x20]*" + "[+-]?(" + // Optional sign character
		// Digitos ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
				"(((" + Digits + "(\\.)?(" + Digits + "?)(" + Exp + ")?)|" +

				// . Digitos ExponentPart_opt FloatTypeSuffix_opt
				"(\\.(" + Digits + ")(" + Exp + ")?)|" +

				// Hexadecimal strings
				"((" +
				// 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
				"(0[xX]" + HexDigits + "(\\.)?)|" +

				// 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
				"(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

				")[pP][+-]?" + Digits + "))" + "[fFdD]?))" + "[\\x00-\\x20]*");

		return Pattern.matches(fpRegex, s);
	}

	@RequestMapping(value = "/refreshSubMarca")
	public @ResponseBody String refreshSubMarca(@RequestParam("nombre_marca") String nombre_marca, Model model) {

		List<String> itemList = vehiculoService.findallsubMarcaUnica(nombre_marca);
		String html = "<option value=''> Selecciona la submarca </option>";

		for (String s : itemList) {

			html += "<option value='" + s + "'> " + s + " </option>";
		}

		return html;

	}

	@RequestMapping(value = "/refreshModelo")
	public @ResponseBody String refreshModelo(@RequestParam("nombre_submarca") String nombre_submarca,
			@RequestParam("nombre_marca") String nombre_marca, Model model) {

		List<String> Modelos = vehiculoService.findallModeloUnico(nombre_marca, nombre_submarca);
		String html = "<option value=''> Selecciona el Modelo </option>";

		for (String s : Modelos) {
			System.out.println(s);
			html += "<option value='" + s + "'> " + s + " </option>";
		}

		return html;
	}

	@RequestMapping(value = "/refreshTipo")
	public @ResponseBody String refreshTipo(@RequestParam("nombre_submarca") String nombre_submarca,
			@RequestParam("nombre_marca") String nombre_marca, @RequestParam("modelo") String modelo, Model model) {

		List<String> tipos = vehiculoService.findallTipoUnico(nombre_marca, nombre_submarca, modelo);
		String html = "<option value=''> Selecciona El Tipo  </option>";

		for (String s : tipos) {
			System.out.println(s);
			html += "<option value='" + s + "'> " + s + " </option>";
		}

		return html;
	}

	@RequestMapping(value = "/refreshClase")
	public @ResponseBody String refreshClase(@RequestParam("nombre_submarca") String nombre_submarca,
			@RequestParam("nombre_marca") String nombre_marca, @RequestParam("modelo") String modelo,
			@RequestParam("tipo") String tipo, Model model) {

		List<String> tipos = vehiculoService.findallClaseUnico(nombre_marca, nombre_submarca, modelo, tipo);
		String html = "<option value=''> Selecciona La Clase  </option>";

		for (String s : tipos) {
			System.out.println(s);
			html += "<option value='" + s + "'> " + s + " </option>";
		}

		return html;
	}

	@RequestMapping(value = "/refreshID")
	public @ResponseBody String refreshId(@RequestParam("nombre_submarca") String nombre_submarca,
			@RequestParam("nombre_marca") String nombre_marca, @RequestParam("modelo") String modelo,
			@RequestParam("tipo") String tipo, @RequestParam("clase") String clase, Model model) {
		System.out.println("n: " + nombre_marca + " NSUB: " + nombre_submarca + " MOD: " + modelo + " Tipo: " + tipo
				+ " Clase: " + clase);
		VehiculoMarca marca = vehiculoService.findMarca(nombre_marca, nombre_submarca, modelo, tipo, clase);

		System.out.println("ID_marca: " + marca.getId_marca());
		String html = "<input id=\"vehiculo_marca\" name=\"vehiculo_marca\" type=\"hidden\" value=\""
				+ marca.getId_marca() + "\"/>";

		model.addAttribute("marca", marca);

		return html;
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/info/{id_vehiculo}")
	public String info(@PathVariable(value = "id_vehiculo") Long id_vehiculo, Map<String, Object> model) {

		Revista revista = null;
		adscripcionlist = adscripService.findAll();
		Vehiculo vehiculo = null;
		VehiculoDetalle detalle = null;
		VehiculoEstado estado = null;
		List<Resguardante> res = null;
		List<String> marca = new ArrayList<String>();
		String documento = "";
		user = obuserService.obtenUser();
		model.put("usuario", user);

		if (id_vehiculo != null) {
			vehiculo = vehiculoService.findOne(id_vehiculo);
			marca = vehiculoService.findAllMarcaUnica();
			transmision = vehiculoService.findAllTransmision();
			vales = tipovaleService.findAll();
			funcion = vehiculoService.findAllFuncion();
			detalle = vehiculoService.findDetalle(id_vehiculo);
			documento = vehiculoService.findTCDetalle(id_vehiculo);
			// calev null
			
				res = reguardanteservice.findResg(vehiculo.getId_vehiculo());
			if(res==null) {
				Resguardante r1 = new Resguardante();
				res = new ArrayList<Resguardante>();
				r1.getTipo_resguardante().setNombre("N/A");
				r1.setNombre("N/A");
				r1.setApellido1("N/A");
				r1.setApellido2("N/A");
				res.add(r1);
				Resguardante r2 = new Resguardante();
				r2.getTipo_resguardante().setNombre("N/A");
				r2.setNombre("N/A");
				r2.setApellido1("N/A");
				r2.setApellido2("N/A");
				res.add(r2);
				Resguardante r3 = new Resguardante();
				r3.getTipo_resguardante().setNombre("N/A");
				r3.setNombre("N/A");
				r3.setApellido1("N/A");
				r3.setApellido2("N/A");
				res.add(r3);
			}
			// --null calev
			try {
				estado = estadoService.findbyPlaca(vehiculo.getPlaca());
			} catch (Exception e) {
				e.printStackTrace();
			}

			revista = revistaService.UltimaRevistaVehiculo(vehiculo.getId_vehiculo());
			if (revista == null) {
				Revista rev = new Revista();
				rev.setFecha_inicio("N/A");
				rev.setFecha_fin("N/A");
				revista = rev;
			}

			if (documento != null) {
				documento = "existe";
			} else {
				documento = "noexiste";
			}
			;
			try {
				coche = vehiculo.getPlaca();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return "redirect:/Vehiculos";
		}

		// Calev

		String[] añoRevista = revista.getFecha_inicio().split("-");
		model.put("revistaUltimaFecha", añoRevista[0]);

		model.put("transmisiones", transmision);
		model.put("vales", vales);
		model.put("funciones", funcion);
		model.put("editando", "si");
		model.put("Resguardante", res);
		model.put("estado", estado);

		// --calev
		model.put("revista", revista);
		model.put("documento", documento);
		model.put("seguroslist", seguros);
		model.put("adslist", adscripcionlist);
		// calev null vehiculo_transmision.nombre_transmision
		System.out.println("calev123 " + vehiculo);
		if (vehiculo.getNo_serie() == null) {
			vehiculo.setNo_serie("N/A");
		}
		if (vehiculo.getNo_inventario() == null) {
			vehiculo.setNo_inventario("N/A");
		}
		if (vehiculo.getPlaca() == null) {
			vehiculo.setPlaca("N/A");
		}
		if (Double.isNaN(vehiculo.getKilometraje_inicial())) {
			vehiculo.setKilometraje_inicial(0);
		}
		if (vehiculo.getVehiculo_detalle() == null) {
			VehiculoDetalle vehiDetalle1 = new VehiculoDetalle();
			vehiDetalle1.setNo_motor("N/A");
			vehiDetalle1.setValor_compra("N/A");
			vehiDetalle1.setColor("N/A");
			vehiDetalle1.setTipo_combustible("N/A");
			vehiDetalle1.setNo_puertas("N/A");
			vehiDetalle1.setRin("N/A");
			vehiDetalle1.setNo_cilindros("N/A");
			vehiDetalle1.setNo_personas("N/A");
			vehiculo.setVehiculo_detalle(vehiDetalle1);
		}
		if (vehiculo.getVehiculo_marca() == null) {
			VehiculoMarca vehiMarca1 = new VehiculoMarca();
			vehiMarca1.setNombre_marca("N/A");
			vehiMarca1.setNombre_submarca("N/A");
			vehiMarca1.setClase("N/A");
			vehiMarca1.setTipo("N/A");
			vehiculo.setVehiculo_marca(vehiMarca1);
		}
		if (vehiculo.getVehiculo_funcion() == null) {
			VehiculoFuncion vehiFuncion1 = new VehiculoFuncion();
			vehiFuncion1.setNombre_funcion("N/A");
			vehiculo.setVehiculo_funcion(vehiFuncion1);
		}
		if (vehiculo.getVale() == null) {
			vehiculo.setVale("N/A");
		}
		if (vehiculo.getVehiculo_transmision() == null) {
			VehiculoTransmision trans1 = new VehiculoTransmision();
			trans1.setNombre_transmision("N/A");
			vehiculo.setVehiculo_transmision(trans1);
		}
		if(vehiculo.getTipo_vale() == null) {
			TipoVale tipovale1 = new TipoVale();
			tipovale1.setDescr_vale("N/A");
			vehiculo.setTipo_vale(tipovale1);
		}
		// ---calev vull
		// Contar los dias que lleva deshabilitado

		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaDate = null;
		
		try {
			if (vehiculo.getFecha_estado() != null) {
				fechaDate = formato.parse(vehiculo.getFecha_estado());
				Date fechaHoy = new Date();
				long conteoDiasL;
				conteoDiasL = fechaHoy.getTime() - fechaDate.getTime();
				conteoDiasL = conteoDiasL / ((1000 * 60 * 60) * 24);
				System.out.println("Hace el conteo: " + String.valueOf(conteoDiasL));
				if (conteoDiasL >= 1) {
					model.put("dias_estado", "( " + String.valueOf(conteoDiasL) + " dias)");
				} else {
					model.put("dias_estado", "");
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// --calev contar los dias inactivo
		model.put("vehiculo", vehiculo);
		model.put("detalle", detalle);
		model.put("marcas", marca);
		model.put("PageTitulo", "Información del Vehiculo");
		model.put("titulo", "Información Vehiculo");
		return "infoVehi";

	}

	// BAJAS

	@RequestMapping(value = "/BajasVehiculos", method = RequestMethod.GET)
	public String listarbajas(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
		// List<Vehiculo> vehiculoArea = new ArrayList<Vehiculo>();

		var ads = "";
		ads = obuserService.obtenEmpl();
		user = obuserService.obtenUser();
		model.addAttribute("usuario", user);

		clase = vehiculoService.findallByClase();
		Pageable pageRequest = PageRequest.of(page, 1000);

		// calev
		adscripcionlist = adscripService.findAll();
		Collections.sort(adscripcionlist, new Comparator<Adscripcion>() {
			public int compare(Adscripcion a1, Adscripcion a2) {
				return a1.getNombre_adscripcion().compareTo(a2.getNombre_adscripcion());
			}
		});
		model.addAttribute("adslist", adscripcionlist);
		// -calev

		if (user.equals("ROLE_USER")) {
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);

			// vehiculoArea =
			// vehiculoService.findVehiculosArea(usus.getAdscripcion().getId_adscripcion());
			Page<Vehiculo> vehiculoPageAra = vehiculoService
					.findVehiculosBajaAreaPage(usus.getAdscripcion().getId_adscripcion(), pageRequest);
			PageRender<Vehiculo> pageRender = new PageRender<>("/BajasVehiculos", vehiculoPageAra);
			if (vehiculoService.totalVehiculo() >= 4) {
				model.addAttribute("tamano", "mostrar");
			} else {
				model.addAttribute("tamano", "no mostrar");
			}
			;
			// model.addAttribute("vehiculos",vehiculoArea);

			model.addAttribute("Corddocu", Corddocu);
			model.addAttribute("Cordtabla", Cordtabla);
			model.addAttribute("thisurl", "Bajas Vehiculo");
			model.addAttribute("PageTitulo", "Bajas Vehiculos");
			model.addAttribute("PageSubTitulo", "Listado de Bajas Vehiculo");
			model.addAttribute("vehiculos", vehiculoPageAra);
			model.addAttribute("page", pageRender);
			return "BajasVehiculos";
		}

		// adscripcionlist = adscripService.findAll();
		// seguros = seguroService.findAll();
		// vehiculo = vehiculoService.findAll();

		Page<Vehiculo> vehiculopage = vehiculoService.findTBajaVechiulo("AUTOMOVIL", pageRequest);
		PageRender<Vehiculo> pageRender = new PageRender<>("/BajasVehiculos", vehiculopage);
		int tamano = 4;
		if (vehiculoService.totalVehiculo() >= tamano) {
			model.addAttribute("tamano", "mostrar");
		} else {
			model.addAttribute("tamano", "no mostrar");
		}
		;

		model.addAttribute("Corddocu", Corddocu);
		// calev acomoda por clase
		Collections.sort(clase, new Comparator<String>() {
			public int compare(String a1, String a2) {
				return a1.compareTo(a2);
			}
		});
		// calev
		model.addAttribute("marca", clase);
		model.addAttribute("Cordtabla", Cordtabla);
		model.addAttribute("thisurl", "Bajas Vehiculos");
		model.addAttribute("titulo", "Listado de Vehiculos");
		model.addAttribute("PageTitulo", "Bajas Vehiculos");
		model.addAttribute("PageSubTitulo", "Listado de Bajas Vehiculos");
		model.addAttribute("vehiculos", vehiculopage);
		model.addAttribute("page", pageRender);
		model.addAttribute("Clase", "AUTOMOVIL");

		return "BajasVehiculos";
	}

	@RequestMapping(value = "/formVehBajaBuscar")
	public String Buscartablabajas(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(value = "elemento") String elemento, Model model) {

		Pageable pageRequest = PageRequest.of(page, 1000);
		Double Dato;
		var ads = "";
		ads = obuserService.obtenEmpl();
		user = obuserService.obtenUser();
		model.addAttribute("usuario", user);
		// calev
		adscripcionlist = adscripService.findAll();
		Collections.sort(adscripcionlist, new Comparator<Adscripcion>() {
			public int compare(Adscripcion a1, Adscripcion a2) {
				return a1.getNombre_adscripcion().compareTo(a2.getNombre_adscripcion());
			}
		});
		model.addAttribute("adslist", adscripcionlist);
		// -calev
		if (!elemento.isBlank()) {
			if (isValidDouble(elemento)) {
				Dato = Double.parseDouble(elemento);
				DecimalFormat formt = new DecimalFormat("0");
				elemento = formt.format(Dato);
				elemento = elemento.replaceAll(",", "");
			}
			;

			if (user == "ROLE_USER") {
				Usuario usus = new Usuario();
				usus = usuarioService.findbyAdscripcion(ads);
				Page<Vehiculo> vehiculopage = vehiculoService
						.findVehBajaElemAreaPage(usus.getAdscripcion().getId_adscripcion(), elemento, pageRequest);
				if (vehiculoService.totalVehiculo() >= 4) {
					model.addAttribute("tamano", "mostrar");
				} else {
					model.addAttribute("tamano", "no mostrar");
				}
				;
				PageRender<Vehiculo> pageRender = new PageRender<>("/formVehBajaBuscar?elemento=" + elemento,
						vehiculopage);
				// calev acomoda por clase
				Collections.sort(clase, new Comparator<String>() {
					public int compare(String a1, String a2) {
						return a1.compareTo(a2);
					}
				});
				// calev
				model.addAttribute("marca", clase);
				model.addAttribute("vehiculos", vehiculopage);
				model.addAttribute("page", pageRender);
				model.addAttribute("elemento", elemento);
				model.addAttribute("PageTitulo", "Bajas Vehiculos");
				model.addAttribute("PageSubTitulo", "Listado de Bajas Vehiculos");
				return "BajasVehiculos";
			}
			;
			Page<Vehiculo> vehiculopage = vehiculoService.findVehBajaElemntoPage(elemento, pageRequest);
			PageRender<Vehiculo> pageRender = new PageRender<>("/formVehBajaBuscar?elemento=" + elemento, vehiculopage);
			if (vehiculoService.totalVehiculo() >= 4) {
				model.addAttribute("tamano", "mostrar");
			} else {
				model.addAttribute("tamano", "no mostrar");
			}
			;
			// calev acomoda por clase
			Collections.sort(clase, new Comparator<String>() {
				public int compare(String a1, String a2) {
					return a1.compareTo(a2);
				}
			});
			// calev
			model.addAttribute("marca", clase);
			model.addAttribute("vehiculos", vehiculopage);
			model.addAttribute("page", pageRender);
			model.addAttribute("elemento", elemento);
			model.addAttribute("PageTitulo", "Bajas Vehiculos");
			model.addAttribute("PageSubTitulo", "Listado de Bajas Vehiculos");
			return "BajasVehiculos";
		} else {
			return "redirect:/BajasVehiculos";
		}

	}
	
	// BRENDA CAMBIO 22/01/2021
	
		@RequestMapping(value="/infoPlacas/{id_vehiculo}", method = RequestMethod.GET)
		public String infoPlacas(@PathVariable(value="id_vehiculo") Long id_vehiculo,@RequestParam(name="page", defaultValue = "0") int page,Model model) {	
		
			List<VehiculoPlacas> vehiculoplacas = new ArrayList<VehiculoPlacas>();
			List<VehiculoPlacas> vehiculoplaca = new ArrayList<VehiculoPlacas>();
			
			user = obuserService.obtenUser();
			model.addAttribute("usuario",user);
		
			//Pageable pageRequest = PageRequest.of(page, 100);
			
				Vehiculo vehiculo = new Vehiculo();
				vehiculo = vehiculoService.findOne(id_vehiculo);
				
				vehiculoplacas = vehiculoplacaService.findPlaca(vehiculo.getId_vehiculo());
				vehiculoplaca = vehiculoplacaService.findPlacaAnt(vehiculo.getId_vehiculo());
				
				System.err.println("jesus"+vehiculoplaca.size());
				
			/*	Page<VehiculoPlacas> PlacasPage = vehiculoplacaService.findPlacas(vehiculo.getId_vehiculo(), pageRequest);
				PageRender<VehiculoPlacas> PlacasRenderArea = new PageRender<>("/infoPlacas",PlacasPage);
				model.addAttribute("PageTitulo", "Historico Placas");
				model.addAttribute("PageSubTitulo", "Listado de Placas Anteriores: "+vehiculo.getPlaca());
				model.addAttribute("id_vehiculo",vehiculo.getId_vehiculo());
				model.addAttribute("idestado",vehiculo.getVehiculo_estado().getId_estado());
				model.addAttribute("estado",vehiculo.getVehiculo_estado().getNombre_estado());
				model.addAttribute("placas",PlacasPage);
				model.addAttribute("page",PlacasRenderArea);*/
				
				
				model.addAttribute("PageTitulo", "Historico de Placas");
				model.addAttribute("PageSubTitulo", "Listado de Placas Anteriores: "+vehiculo.getPlaca());
				model.addAttribute("id_vehiculo",vehiculo.getId_vehiculo());
				model.addAttribute("idestado",vehiculo.getVehiculo_estado().getId_estado());
				model.addAttribute("estado",vehiculo.getVehiculo_estado().getNombre_estado());
				model.addAttribute("placas",vehiculoplacas);
				model.addAttribute("placa",vehiculoplaca);
		
				return "VehiculoPlacas";

		} //BRENDA
		
		@RequestMapping(value="/download/vehiculo", method = RequestMethod.GET)
		//@GetMapping(value="/download/vehiculo")  
	    //public String report(HttpServletResponse response,@RequestParam(name="page", defaultValue = "0") int page) throws Exception,IOException,FileNotFoundException {
		public ResponseEntity<InputStreamResource> excelReport() throws IOException{
						
			ByteArrayInputStream stream = vehiculoService.vehiculotoexcel();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=vehiculo.xls");
			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
		}	
		
}