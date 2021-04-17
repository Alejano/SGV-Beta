package com.PGJ.SGV.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
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
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.service.IResguardanteService;
import com.PGJ.SGV.service.ITipoResguardanteService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.util.paginador.PageRender;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

//calev
import com.PGJ.SGV.models.entity.Adscripcion;
import com.PGJ.SGV.service.IAdscripcionService;

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
	@Autowired
	private IObtenerUserService obuserService;

	public static Resguardante Presguardante = new Resguardante();
	public static Resguardante Sresguardante = new Resguardante();
	public static Resguardante Tresguardante = new Resguardante();
	static int Corddocu = 0;
	static int Cordtabla = 0;
	String user;
	boolean editar;
	Long id;

	// calev
	@Autowired
	private IAdscripcionService adscripService;
	List<Adscripcion> adscripcionlist = new ArrayList<Adscripcion>();
	// -calev

	@RequestMapping(value = "/infoResguardante/{id_vehiculo}", method = RequestMethod.GET)
	public String infoResguardo(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@PathVariable(value = "id_vehiculo") long id_vehiculo) {

		user = obuserService.obtenUser();
		model.addAttribute("usuario", user);

		Pageable pageRequest = PageRequest.of(page, 500);
		Vehiculo placa = new Vehiculo();

		placa = vehiculoService.findOne(id_vehiculo);
		Page<Resguardante> vehiculopage = reguardanteservice.findAllByVehiculo(id_vehiculo, pageRequest);
		PageRender<Resguardante> pageRender = new PageRender<>("/infoResguardante/{id_vehiculo}", vehiculopage);
		
		System.err.println("cacha: " + reguardanteservice.resguardantespvtotales(id_vehiculo));
		if(reguardanteservice.resguardantespvtotales(id_vehiculo) >= 6) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
		id=placa.getId_vehiculo();

		model.addAttribute("Placa", placa.getPlaca());
		model.addAttribute("id_vehiculo", placa.getId_vehiculo());
		model.addAttribute("Corddocu", Corddocu);
		model.addAttribute("Cordtabla", Cordtabla);
		model.addAttribute("PageTitulo", "Resguardantes");
		model.addAttribute("PageSubTitulo", "Listado de Resguardantes de la placa: " + placa.getPlaca());
		model.addAttribute("thisurl", "infoResguardante");
		model.addAttribute("titulo", "Listado de Resguardos");
		if (placa.getVehiculo_estado()!= null) {
		model.addAttribute("idestado", placa.getVehiculo_estado().getId_estado());
		model.addAttribute("estado", placa.getVehiculo_estado().getNombre_estado());
		}
		model.addAttribute("Resguardantes", vehiculopage);
		model.addAttribute("page", pageRender);

		return "/vehiculos/infoResguardante";

	}

	@RequestMapping(value = "/AddResguardo/{id_vehiculo}")
	public String Agregar(Map<String, Object> model, @PathVariable(value = "id_vehiculo") Long id_vehiculo) {

		Resguardante Presguardante = new Resguardante();
		Resguardante Sresguardante = new Resguardante();
		Resguardante Cresguardante = new Resguardante();

		TipoResguardante tiposResguardoP = new TipoResguardante();
		TipoResguardante tiposResguardoS = new TipoResguardante();

		tiposResguardoP = tiporesguardoService.findOne((long) 1);
		tiposResguardoS = tiporesguardoService.findOne((long) 2);

		List<Usuario> usuariosdb = new ArrayList<Usuario>();
		List<Conductor> conductoresdb = new ArrayList<Conductor>();
		usuariosdb = usuarioService.findAll();
		conductoresdb = conductorService.findAll();

		model.put("id_vehiculo", id_vehiculo);
		model.put("Presguardante", Presguardante);
		model.put("Sresguardante", Sresguardante);
		model.put("Cresguardante", Cresguardante);
		model.put("PageTitulo", "Agregar Resguardantes");
		model.put("tiposResguardoP", tiposResguardoP);
		model.put("tiposResguardoS", tiposResguardoS);
		model.put("conductores", conductoresdb);
		model.put("usuarios", usuariosdb);
		// calev
		adscripcionlist = adscripService.findAll();
		Collections.sort(adscripcionlist, new Comparator<Adscripcion>() {
			public int compare(Adscripcion a1, Adscripcion a2) {
				return a1.getNombre_adscripcion().compareTo(a2.getNombre_adscripcion());
			}
		});
		model.put("adscripciones", adscripcionlist);
		// -calev
		return "vehiculos/AddResguardo";
	}

	@RequestMapping(value = "/AddResguardo/{id_vehiculo}", method = RequestMethod.POST)
	public String saveR(Map<String, Object> model, Resguardante resguardante,
			@PathVariable(value = "id_vehiculo") Long id_vehiculo) {

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
		String[] domicilio = domicilios.split("¡");
		String[] ids_adscripcion = ids_adscripciones.split(",");

		Presguardante.setNo_licencia(no_licencia[0]);
		Presguardante.setRfc(rfc[0]);
		Presguardante.setIne(ine[0]);
		Presguardante.setTelefono(telefono[0]);
		Presguardante.setDomicilio(domicilio[0].substring(0, domicilio[0].length()-1));

		Adscripcion adsc1 = adscripService.findOne(Long.valueOf(ids_adscripcion[0]));
		Presguardante.setAdscripcion(adsc1);

		Sresguardante.setNo_licencia(no_licencia[1]);
		Sresguardante.setRfc(rfc[1]);
		Sresguardante.setIne(ine[1]);
		Sresguardante.setTelefono(telefono[1]);
		Sresguardante.setDomicilio(domicilio[1].substring(1));

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
		Presguardante.setTipo_resguardante_id(tiporesguardoService.findOne((long) 1));
		Presguardante.setActivo(true);

		Sresguardante.setId_resguardante(Presguardante.getId_resguardante() + 1);
		Sresguardante.setFecha_inicio(formateador.format(ahora));
		Sresguardante.setTipo_resguardante_id(tiporesguardoService.findOne((long) 2));
		Sresguardante.setActivo(true);

		
		List<Resguardante> res = new ArrayList<Resguardante>();
		res = reguardanteservice.findActivos(id_vehiculo);
		
		System.err.println("zurieñ"+res.size());

		for (Resguardante re : res) {
			re.setActivo(false);
			re.setFecha_fin(SystemDate.obtenFecha());
			reguardanteservice.save(re);
		}
		
		Vehiculo vehi = new Vehiculo();
		vehi = vehiculoService.findOne(id_vehiculo);
		
		Presguardante.setVehiculo(vehi);
		Sresguardante.setVehiculo(vehi);

		
		System.err.println("HC: "+Presguardante.getId_resguardante());
		System.err.println("HC2: "+Sresguardante.getId_resguardante());

		
		reguardanteservice.save(Presguardante);
		reguardanteservice.save(Sresguardante);

		String valor_oldp = Presguardante.toString();
		LogsAudit logsp = new LogsAudit();

		logsp.setId_usuario(obuserService.obtenEmpl());
		logsp.setTipo_role(obuserService.obtenUser());
		logsp.setFecha(SystemDate.obtenFecha());
		logsp.setHora(ObtenHour.obtenHour());
		logsp.setName_table("RESGUARDANTE");
		logsp.setValor_viejo(valor_oldp);
		logsp.setTipo_accion("INSERT");

		String valor_olds = Sresguardante.toString();
		LogsAudit logss = new LogsAudit();

		logss.setId_usuario(obuserService.obtenEmpl());
		logss.setTipo_role(obuserService.obtenUser());
		logss.setFecha(SystemDate.obtenFecha());
		logss.setHora(ObtenHour.obtenHour());
		logss.setName_table("RESGUARDANTE");
		logss.setValor_viejo(valor_olds);
		logss.setTipo_accion("INSERT");

		logsauditService.save(logsp);
		logsauditService.save(logss);

		return "redirect:/infoResguardante/" + vehi.getId_vehiculo();
	}

	@RequestMapping(value = "/TResguardante/{id_vehiculo}{Pnombre}{Papellido1}{Papellido2}{Pcargo}{Snombre}{Sapellido1}{Sapellido2}{Scargo}{HPno_licencia}{HPrfc}{HPine}{HPtelefono}{HPdomicilio}{HPadscripcion_id_adscripcion}{HSno_licencia}{HSrfc}{HSine}{HStelefono}{HSdomicilio}{HSadscripcion_id_adscripcion}")
	public String Tres(Map<String, Object> model, @PathVariable(value = "id_vehiculo") Long id_vehiculo,
			@RequestParam(value = "Pnombre") String Pnombre, @RequestParam(value = "Papellido1") String Papellido1,
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
		System.out.println("Calev123" + Pno_licencia);
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

		List<Usuario> usuariosdb = new ArrayList<Usuario>();
		List<Conductor> conductoresdb = new ArrayList<Conductor>();

		usuariosdb = usuarioService.findAll();
		conductoresdb = conductorService.findAll();

		model.put("conductores", conductoresdb);
		model.put("usuarios", usuariosdb);
		model.put("id_vehiculo", id_vehiculo);
		model.put("Presguardante", Presguardante);
		model.put("Sresguardante", Sresguardante);
		model.put("Tresguardante", Tresguardante);
		model.put("PageTitulo", "Agregar Un Tercer Resguardante");

		return "vehiculos/AddTResguardante";
	}

	@RequestMapping(value = "/TResguardante/{id_vehiculo}", method = RequestMethod.POST)
	public String tresPOST(Map<String, Object> model, Resguardante resguardante,
			@PathVariable(value = "id_vehiculo") Long id_vehiculo) {

		Long id = Sresguardante.getId_resguardante();
		Tresguardante.setNombre(resguardante.getNombre());
		Tresguardante.setApellido1(resguardante.getApellido1());
		Tresguardante.setApellido2(resguardante.getApellido2());
		Tresguardante.setCargo(resguardante.getCargo());
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		Tresguardante.setFecha_inicio(formateador.format(ahora));
		Tresguardante.setId_resguardante(id + 1);
		Tresguardante.setTipo_resguardante_id(tiporesguardoService.findOne((long) 3));

		Presguardante.setActivo(true);
		Sresguardante.setActivo(true);
		Tresguardante.setActivo(true);
		
		Tresguardante.setNo_licencia(resguardante.getNo_licencia());
		Tresguardante.setRfc(resguardante.getRfc());
		Tresguardante.setIne(resguardante.getIne());
		Tresguardante.setTelefono(resguardante.getTelefono());
		Tresguardante.setDomicilio(resguardante.getDomicilio());
		Tresguardante.setAdscripcion(resguardante.getAdscripcion());

		List<Resguardante> res = new ArrayList<Resguardante>();
		res = reguardanteservice.findActivos(id_vehiculo);

		for (Resguardante re : res) {
			re.setFecha_fin(formateador.format(ahora));
			re.setActivo(false);
			re.setFecha_fin(SystemDate.obtenFecha());
			reguardanteservice.save(re);
		}

		Vehiculo vehi = new Vehiculo();
		vehi = vehiculoService.findOne(id_vehiculo);
		Presguardante.setVehiculo(vehi);
		Sresguardante.setVehiculo(vehi);
		Tresguardante.setVehiculo(vehi);

		reguardanteservice.save(Presguardante);
		reguardanteservice.save(Sresguardante);
		reguardanteservice.save(Tresguardante);

		String valor_oldp = Presguardante.toString();
		LogsAudit logsp = new LogsAudit();

		logsp.setId_usuario(obuserService.obtenEmpl());
		logsp.setTipo_role(obuserService.obtenUser());
		logsp.setFecha(SystemDate.obtenFecha());
		logsp.setHora(ObtenHour.obtenHour());
		logsp.setName_table("RESGUARDANTE");
		logsp.setValor_viejo(valor_oldp);
		logsp.setTipo_accion("INSERT");

		String valor_olds = Sresguardante.toString();
		LogsAudit logss = new LogsAudit();

		logss.setId_usuario(obuserService.obtenEmpl());
		logss.setTipo_role(obuserService.obtenUser());
		logss.setFecha(SystemDate.obtenFecha());
		logss.setHora(ObtenHour.obtenHour());
		logss.setName_table("RESGUARDANTE");
		logss.setValor_viejo(valor_olds);
		logss.setTipo_accion("INSERT");

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

		model.put("Presguardante", Presguardante);
		model.put("Sresguardante", Sresguardante);
		model.put("Tresguardante", Tresguardante);

		return "redirect:/infoResguardante/" + vehi.getId_vehiculo();

	}
	
	@RequestMapping(value ="/verResguardante/{id_resguardante}")
	public String ver(@PathVariable(value = "id_resguardante") Long id_resguardante, Map<String, Object> model) {
		
		adscripcionlist = adscripService.findAll();
		
		editar=true;
		user = obuserService.obtenUser();
		model.put("usuario",user);	
		Resguardante resguardante = null;
		
		if(!id_resguardante.equals(null)) {
			resguardante = reguardanteservice.findOne(id_resguardante);
			
		}else {
			return "redirect:/infoResguardante/" + resguardante.getVehiculo().getId_vehiculo();
		}
		model.put("aux", true);
		model.put("adslist", adscripcionlist);
		model.put("resguardante",resguardante);
		model.put("editar", editar);
		model.put("PageTitulo", "Información del Resguardante");
		
		return "vehiculos/formResg";

	}
	

	@RequestMapping(value="/formResg/{id_resguardante}")
	public String editar(@PathVariable(value="id_resguardante") Long id_resguardante,Map<String,Object>model) {

        adscripcionlist = adscripService.findAll();
		
		editar=true;
		user = obuserService.obtenUser();
		model.put("usuario",user);	
		//Resguardante Presguardante = null;
		Resguardante resguardante = null;

		
		if(!id_resguardante.equals(null)) {
			resguardante = reguardanteservice.findOne(id_resguardante);
			
		}else {
			return "redirect:/infoResguardante/" + resguardante.getVehiculo().getId_vehiculo();
		}
		model.put("aux", true);
		model.put("adslist", adscripcionlist);
		model.put("resguardante",resguardante);
		model.put("editar", editar);
		model.put("PageTitulo", "Editar del Resguardante");
		
		return "vehiculos/formResg";

	}
	
	@RequestMapping(value="/formResg",method = RequestMethod.POST)
	public String guardar(Resguardante resguardante,Map<String, Object> model) 
	{
				
		Resguardante resguardante_old = null;
		resguardante_old = reguardanteservice.findOne(resguardante.getId_resguardante());
	    String valor_old = resguardante_old.toString();
	    
	    
		Vehiculo vehiculoselect =new Vehiculo();
		vehiculoselect = vehiculoService.findOne(resguardante.getVehiculo().getId_vehiculo());	
		resguardante.setVehiculo(vehiculoselect);
	    reguardanteservice.save(resguardante);
	    	   
		LogsAudit logsp = new LogsAudit();

		logsp.setId_usuario(obuserService.obtenEmpl());
		logsp.setTipo_role(obuserService.obtenUser());
		logsp.setFecha(SystemDate.obtenFecha());
		logsp.setHora(ObtenHour.obtenHour());
		logsp.setName_table("RESGUARDANTE");
		logsp.setValor_viejo(valor_old);
		logsp.setTipo_accion("INSERT");

		logsauditService.save(logsp);

		return "redirect:/infoResguardante/" + resguardante.getVehiculo().getId_vehiculo();

	}
	

	@RequestMapping(value="/export/reportResg", method=RequestMethod.GET)  
    //public String report(HttpServletResponse response,@RequestParam(name="page", defaultValue = "0") int page) throws Exception,IOException,FileNotFoundException {

	private String exportReport(@RequestParam(name="page", defaultValue = "0") int page, Model model) throws FileNotFoundException, JRException {
	
		String format = "pdf";
		//return usuarioService.exportReport(format);
		reguardanteservice.exportReport(format);
		
		model.addAttribute("PageTitulo", "Reporte Resg");
        return "verResg";
        
	}
	

	@RequestMapping(value="/export/reportResg2", method=RequestMethod.GET)  
    public void report(HttpServletResponse response) throws Exception {

	   /* response.setContentType("text/html");
	    
	    List<Resguardante> resg = new ArrayList<Resguardante>();
		resg =  reguardanteservice.findResgAll1(6l);
		
		JRBeanCollectionDataSource ds1 = new JRBeanCollectionDataSource(resg);
		InputStream Input = this.getClass().getResourceAsStream("/templates/resguardantes.jrxml");
		JasperDesign design = JRXmlLoader.load(Input);
	    JasperReport jasper = JasperCompileManager.compileReport(design);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, null, ds1);
		
		JRPdfExporter pdf = new JRPdfExporter();
		pdf.setExporterInput(new SimpleExporterInput(jasperPrint));
		ByteArrayOutputStream streamp = new ByteArrayOutputStream();
		pdf.setExporterOutput(new SimpleOutputStreamExporterOutput(streamp));
		pdf.exportReport();
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Leght", String.valueOf(streamp.size()));
		response.addHeader("Content-Disposition", "inline; filename=jasper.pdf;");
		
		OutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(streamp.toByteArray());
		responseOutputStream.close();
		streamp.close();*/
		
		int con=0;
        response.setContentType("text/html");
	   
	    List<Resguardante> resg = new ArrayList<Resguardante>();
		resg =  reguardanteservice.findResgAll1(6l);

		System.err.println("carroo"+resg.size());
		
		List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		
		
        for (int i=0;i<resg.size();i++) {
			
			List<Resguardante> individual = new ArrayList<Resguardante>();
			individual = reguardanteservice.findResgInd(resg.get(i).getId_resguardante());
			
			JRBeanCollectionDataSource ds1 = new JRBeanCollectionDataSource(individual);
			InputStream Input = this.getClass().getResourceAsStream("/templates/resguardantes.jrxml");
			JasperDesign design = JRXmlLoader.load(Input);
		    JasperReport jasper = JasperCompileManager.compileReport(design);
		    Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha", SystemDate.obtenFecha());			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters, ds1);
			jasperPrintList.add(jasperPrint);			
	        }
		
		JRPdfExporter pdf = new JRPdfExporter();
		pdf.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
		
		ByteArrayOutputStream streamp = new ByteArrayOutputStream();
		pdf.setExporterOutput(new SimpleOutputStreamExporterOutput(streamp));
		pdf.exportReport();
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Leght", String.valueOf(streamp.size()));
		response.addHeader("Content-Disposition", "inline; filename=Resguardantes.pdf;");
		
		OutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(streamp.toByteArray());
		responseOutputStream.close();
		streamp.close();

	}
	
	
	@RequestMapping(value="/export/reportResg3", method=RequestMethod.GET)  
	private String verReport(Model model) {
		
		model.addAttribute("PageTitulo", "Reporte Usuarios");
        return "verSeguro";
        
	}
	
	
	
}
