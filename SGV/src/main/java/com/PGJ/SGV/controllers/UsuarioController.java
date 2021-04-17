package com.PGJ.SGV.controllers;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.PGJ.SGV.models.entity.Adscripcion;
import com.PGJ.SGV.models.entity.Authority;
import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.service.IAdscripcionService;
import com.PGJ.SGV.service.IAutoridadService;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.util.paginador.PageRender;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;

import net.sf.jasperreports.engine.JRException;

@Controller
public class UsuarioController {
	static int 	Corddocu = 0;
	static int 	Cordtabla = 0;
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IAdscripcionService adscripService;
	@Autowired
	private IAutoridadService autoridadService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private ILogsAuditService logsauditService;
	@Autowired
	private IObtenerUserService obuserService;
	
	
	List<Adscripcion> adscripcion = new ArrayList<Adscripcion>();
	List<Usuario> usuarios = new ArrayList<Usuario>();
	List<Authority> autoridad = new ArrayList<Authority>();
    String empleado;
    static String no_emple;
    static Usuario usu=new Usuario();
    String falta_usuario;
    String ealta_usuario;
    String user;
	static boolean editar = false;
	
	
	@RequestMapping(value="/Usuarios", method = RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue = "0") int page,Model model) {			
		
		user = obuserService.obtenUser();
		model.addAttribute("usuario",user);	
		
		Pageable pageRequest = PageRequest.of(page, 100);
		Page<Usuario> usuarioPage = usuarioService.findByNl(pageRequest);

		PageRender <Usuario> usuarioRender = new PageRender<>("/Usuarios",usuarioPage);
		if(usuarioService.totalUsuarios()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
		
		model.addAttribute("PageTitulo", "Usuarios");
        model.addAttribute("PageSubTitulo", "Listado de Usuarios");
		model.addAttribute("usuarios",usuarioPage);
		model.addAttribute("page",usuarioRender);
		//model.addAttribute("autoridad",autoridad);
		//model.addAttribute("adslist",adscripcion);
		model.addAttribute("Corddocu",Corddocu);
		model.addAttribute("Cordtabla",Cordtabla);
		model.addAttribute("titulo","Listado de Usuarios");
		//model.addAttribute("usuarios",usuarios);
		return "Usuarios";
	}
		
	
	@RequestMapping(value="/formUsu")
	public String crear(Map<String,Object> model) {
		
		usuarios = usuarioService.findAll();
		autoridad = autoridadService.findAll();
		adscripcion = adscripService.findAll();
		Usuario usuario = new Usuario();	
		editar = false;		
		usuario.setFecha_alta(SystemDate.obtenFecha());
		falta_usuario=usuario.getFecha_alta();
		Collections.sort(adscripcion, new Comparator<Adscripcion>() {
			public int compare(Adscripcion a1, Adscripcion a2) {
				return a1.getNombre_adscripcion().compareTo(a2.getNombre_adscripcion());
			}
		});
		model.put("adslist", adscripcion);
		model.put("usuarios", usuario);
		model.put("PageTitulo", "Agregar Usuario");
		model.put("titulo", "Formulario de Adscripciones");	
		return "formUsu";

	}

	
	@RequestMapping(value="/formUsu/{no_empleado}")
	public String editar(@PathVariable(value="no_empleado") String no_empleado,Map<String,Object>model) {
		
		editar = true;
		no_emple=no_empleado;
		Usuario usuario = null;
		
		
		if(!no_empleado.equals(null)){
			   
			usuario = usuarioService.findOne(no_emple);
			empleado = usuario.getNo_empleado();
			 
		} else {
			  return "redirect:/Usuarios";
		}
		
		ealta_usuario=usuario.getFecha_alta();		
		adscripcion = adscripService.findAll();
		
		model.put("adslist", adscripcion);
		model.put("PageTitulo", "Editar Usuario");
		model.put("usuarios",usuario);
		return "formUsu";
	}
	
	
	@RequestMapping(value="/formUsu",method = RequestMethod.POST)
	public String guardar(Usuario usuario){	
		
		Adscripcion adc = new Adscripcion();
		var password = usuario.getContrasena();		
		String bcryptPassword = passwordEncoder.encode(password);		
		usuario.setContrasena(bcryptPassword);
				
		if(editar==false) {
			 usuario.setFecha_alta(falta_usuario);
		}else {
			usuario.setFecha_alta(ealta_usuario);
		}

		if(editar==false) {
			for(Usuario usu:usuarios) {
				if(usuario.getNo_empleado().equals(usu.getNo_empleado())) {	
					return "redirect:/idDuplicadoUsu/"+usuario.getNo_empleado();
				};	
			}
		 
		}else{												
				if( !empleado.equals(usuario.getNo_empleado())) {		
					//System.out.println(usuario.getNo_empleado()+" "+ empleado);
					return "redirect:/idDuplicadoUsuCrea/"+usuario.getNo_empleado()+"/"+empleado;
				}									
			
		};
					
		for(Adscripcion ads:adscripcion) {
			if(ads.getId_adscripcion()==usuario.getAdscripcion().getId_adscripcion()) {
				adc = ads;
			};
		}
		
		
		if(editar==true) {
    		
			usuario.setFecha_alta(ealta_usuario);
    		
			//Conductor OLD
			
		    Usuario usuario_old = null;	
		    usuario_old = usuarioService.findOne(usuario.getNo_empleado());
		    String valor_old = usuario_old.toString();
			usuarioService.save(usuario);
			
			//Auditoria
			
         	LogsAudit logs = new LogsAudit();
         	
         	logs.setId_usuario(obuserService.obtenEmpl());
    		logs.setTipo_role(obuserService.obtenUser());
			logs.setFecha(SystemDate.obtenFecha());
			logs.setHora(ObtenHour.obtenHour());
			logs.setName_table("USUARIOS");
			logs.setValor_viejo(valor_old);
			logs.setTipo_accion("UPDATE");
									
			logsauditService.save(logs);                       
			editar = false;	
    		
    		}else {
    			
    			usuario.setFecha_alta(falta_usuario);
    			
    			usuarioService.save(usuario);
				String valor_nuevo=usuario.toString();
				
				//Auditoria
				
		     	LogsAudit logs = new LogsAudit();
		     	
		     	logs.setId_usuario(obuserService.obtenEmpl());
	    		logs.setTipo_role(obuserService.obtenUser());
				logs.setFecha(SystemDate.obtenFecha());
				logs.setHora(ObtenHour.obtenHour());
				logs.setName_table("USUARIOS");
				logs.setValor_viejo(valor_nuevo);
				logs.setTipo_accion("INSERT");
										
				logsauditService.save(logs);
    			
    			}
				
		return "redirect:Usuarios";
		
	 }	
	
	@RequestMapping(value="/estadoUsu/{no_empleado}/{enabled}")
    public String estado (@PathVariable(value="no_empleado")String no_empleado,@PathVariable(value="enabled")boolean enabled,
		@RequestParam(name="page", defaultValue = "0") int page) {
	
	  Usuario uss = new Usuario();
	  uss = usuarioService.findOne(no_empleado);
	  boolean seteo = false;
	  
	  if(enabled) {
		seteo=false;
		uss.setEnabled(seteo);
		}else {
			seteo=true;
			uss.setEnabled(seteo);	
			}
	  
	  Usuario usuario_old = null;
	  usuario_old = usuarioService.findOne(uss.getNo_empleado());
	  String valor_old = usuario_old.toString();
	  
	  usuarioService.save(uss);	
	  
	  //Auditoria
	  
	  LogsAudit logs = new LogsAudit();
	  
	  logs.setId_usuario(obuserService.obtenEmpl());
	  logs.setTipo_role(obuserService.obtenUser());
	  logs.setFecha(SystemDate.obtenFecha());
	  logs.setHora(ObtenHour.obtenHour());
	  logs.setName_table("USUARIOS");
	  logs.setValor_viejo(valor_old);
	  logs.setTipo_accion("UPDATE ESTADO");
	  
	  logsauditService.save(logs);
	  
	  editar = false;		
	  
	  return "redirect:/Usuarios?page="+page;
	  
	}


	@RequestMapping(value="/elimUsu/{no_empleado}")
	public String eliminar (@PathVariable(value="no_empleado")String no_empleado) {
		
		Usuario usua = new Usuario();
		usua=usuarioService.findOne(no_empleado);
		
		if(no_empleado != "") {	
			//usuarioService.delete(no_empleado);
			usua.setEnabled(false);
			usua.setFecha_baja(SystemDate.obtenFecha());
			usuarioService.save(usua);
		}
		return "redirect:/Usuarios";
		
	}
	
	
	@RequestMapping(value="/formUsuBuscar")
	public String Buscartabla(@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(value="elemento") String elemento,Model model){	
		
		 Pageable pageRequest = PageRequest.of(page, 100);
		 Double Dato;
		 String elementof="";
		 
		 if(!elemento.isBlank()) {			
			 if(isValidDouble(elemento)) {
						Dato = Double.parseDouble(elemento);
						DecimalFormat formt = new DecimalFormat("0");
						elemento = formt.format(Dato);
						elemento = elemento.replaceAll(",","");	
						};
				
		 elementof = elemento.toUpperCase(); 
		 Page<Usuario> usuariospage = usuarioService.finUsuElemntPage(elementof, pageRequest);
		 PageRender<Usuario> renderpage = new PageRender<>("/formUsuBuscar",usuariospage);
		 if(usuarioService.totalfinUsuElemnt(elementof)>=5) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
		 
		 model.addAttribute("usuarios",usuariospage);
		 model.addAttribute("PageTitulo", "Usuarios");
	     model.addAttribute("PageSubTitulo", "Listado de Usuarios");
		 model.addAttribute("page",renderpage);
		 model.addAttribute("elemento",elementof);
		 return "Usuarios";
		 }
		   return "redirect:/Usuarios";
		   
	}
	
	// BAJAS DE USUARIO
	
	@RequestMapping(value="/BajasUsu", method = RequestMethod.GET)
	public String listar2(@RequestParam(name="page", defaultValue = "0") int page,Model model) {			
		
		user = obuserService.obtenUser();
		model.addAttribute("usuario",user);	
		Pageable pageRequest = PageRequest.of(page, 100);
		Page<Usuario> usuarioPage = usuarioService.findByNn(pageRequest);

		PageRender <Usuario> usuarioRender = new PageRender<>("/Bajas",usuarioPage);
		if(usuarioService.totalUsuariosBajas()>=5) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
		model.addAttribute("PageTitulo", "Bajas Usuarios");
        model.addAttribute("PageSubTitulo", "Listado de Bajas Usuarios");
		model.addAttribute("usuarios",usuarioPage);
		model.addAttribute("page",usuarioRender);
		model.addAttribute("Corddocu",Corddocu);
		model.addAttribute("Cordtabla",Cordtabla);
		model.addAttribute("titulo","Bajas Usuario");
		//model.addAttribute("usuarios",usuarios);
		return "BajasUsu";
		
	}
	
	
	@RequestMapping(value="/RecuperarUsu/{no_empleado}")
	public String recuperar(@PathVariable(value="no_empleado") String no_empleado) {
		  
			Usuario usuarec = new Usuario();
			usuarec=usuarioService.findOne(no_empleado);
	
			if(no_empleado != "") {	
				//usuarioService.delete(no_empleado);
			    usuarec.setEnabled(true);
				usuarec.setFecha_baja(null);
				
				Usuario usuario_old = null;
				usuario_old = usuarioService.findOne(usuarec.getNo_empleado());
			    String valor_old = usuario_old.toString();
				
				usuarioService.save(usuarec);
				
				 //Auditoria
				
	         	LogsAudit logs = new LogsAudit();
	         	
	         	logs.setId_usuario(obuserService.obtenEmpl());
	    		logs.setTipo_role(obuserService.obtenUser());
				logs.setFecha(SystemDate.obtenFecha());
				logs.setHora(ObtenHour.obtenHour());
				logs.setName_table("USUARIOS");
				logs.setValor_viejo(valor_old);
				logs.setTipo_accion("UPDATE ESTADO");
										
				logsauditService.save(logs);
				
				}
			
			return "redirect:/BajasUsu";
			
	}
		
	
	@RequestMapping(value="/formUsuBajaBuscar")
	public String Buscartablabaja(@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(value="elemento") String elemento,Model model){	
		
		 Pageable pageRequest = PageRequest.of(page, 100);
		 Double Dato;
		 String elementof="";
		 
		 if(!elemento.isBlank()) {			
				if(isValidDouble(elemento)) {
						Dato = Double.parseDouble(elemento);
						DecimalFormat formt = new DecimalFormat("0");
						elemento = formt.format(Dato);
						elemento = elemento.replaceAll(",","");	
						};
		 
		 elementof = elemento.toUpperCase();			
		 Page<Usuario> usuariospage = usuarioService.finUsuElemntBajasPage(elementof, pageRequest);
		 PageRender<Usuario> renderpage = new PageRender<>("/formUsuBajaBuscar",usuariospage);
		 if(usuarioService.totalfinUsuElemntBajas(elementof)>=5) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
		 model.addAttribute("usuarios",usuariospage);
		 model.addAttribute("PageTitulo", "Bajas Usuarios");
	        model.addAttribute("PageSubTitulo", "Listado de Bajas Usuarios");
		 model.addAttribute("page",renderpage);
		 model.addAttribute("elemento",elementof);		 
		 return "BajasUsu";
		 }
		 return "redirect:/BajasUsu";
		 
	}
	
	
	@RequestMapping(value="/export/reportUsuarios", method=RequestMethod.GET)  
    //public String report(HttpServletResponse response,@RequestParam(name="page", defaultValue = "0") int page) throws Exception,IOException,FileNotFoundException {

	private String exportReport(@RequestParam(name="page", defaultValue = "0") int page, Model model) throws FileNotFoundException, JRException {
	
		String format = "pdf";
		//return usuarioService.exportReport(format);
		usuarioService.exportReport(format);
		
		model.addAttribute("PageTitulo", "Reporte Usuarios");
        return "verSeguro";
        
	}
	
	
	@RequestMapping(value="/reportUsuarios", method=RequestMethod.GET)  
	private String verReport(Model model) {
		
		model.addAttribute("PageTitulo", "Reporte Usuarios");
        return "verSeguro";
        
	}
	
    
/*
		FileInputStream inputStream = new FileInputStream(file);
		byte[] buffer = new byte[8192];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int bytesRead;
		while ((bytesRead = inputStream.read(buffer)) != -1)
		{
		      baos.write(buffer, 0, bytesRead);
		}
		response.setHeader("Content-Disposition","inline; filename=\""+file.getName()+"\"");
		response.setContentType("application/pdf");
		ServletOutputStream outputStream = response.getOutputStream();
		baos.writeTo(outputStream);
		outputStream.flush();*/
	
			//response.setHeader("Content-disposition","inline; filename=c://users//brend//usuariosformat.pdf");
			//response.setContentType("application/pdf");

		//return "redirect:/Usuarios";
		
		/*response.setContentType("text/html");
		response.setHeader("Content-Lenght", String.valueOf(pdf.size()));
		response.addHeader("Content-Disposition", "inline; filename=usuarios.pdf;");
		

		
	}
	
	 /*private void createPdfReport() throws JRException {
		
			List<Usuario> usuarios = new ArrayList<Usuario>();
	        final InputStream stream = (InputStream) this.getClass().getResourceAsStream("/usuarios.jrxml");
	        final JasperReport report = JasperCompileManager.compileReport(stream);
	 	    final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(usuarios);
	 
	        final Map<String, Object> parameters = new HashMap<>();
	        parameters.put("createdBy", "javacodegeek.com");
	        
	        final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);
			final String filePath = "\\";
			JasperExportManager.exportReportToPdfFile(print, filePath + "Usuarios_report.pdf");
	        
	        // Export the report to a PDF file.*/
	    //}
	
		
		
		
		
	/*@RequestMapping(value="/export/reportUsuarios", method = RequestMethod.GET)  
      public void report(HttpServletResponse response) throws Exception,IOException {
		  
		try {
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		usuarios = usuarioService.findAll();
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(usuarios);
		InputStream in = (InputStream) this.getClass().getResourceAsStream("usuarios.jrxml");
		
		JasperReport jasper = JasperCompileManager.compileReport(in);
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("gain java", "knowledge");

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters,ds);
			
	/*	JRPdfExporter pdfExporter =  new JRPdfExporter();
		pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		ByteArrayOutputStream pdf = new ByteArrayOutputStream();
		pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdf));
		pdfExporter.exportReport();*/
		
	/*	HtmlExporter htmlExporter = new HtmlExporter(DefaultJasperReportsContext.getInstance());
		htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
		htmlExporter.exportReport();

		

		//response.setContentType("application/pdf");
		/*response.setContentType("text/html");
		response.setHeader("Content-Lenght", String.valueOf(pdf.size()));
		response.addHeader("Content-Disposition", "inline; filename=usuarios.pdf;");
		
		OutputStream responseOutput = response.getOutputStream();
		responseOutput.write(pdf.toByteArray());
		responseOutput.close();
		//pdf.close();
		 
	  } catch (MalformedURLException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }*/

		/*List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = usuarioService.findAll();
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(usuarios);
		InputStream in = (InputStream) this.getClass().getResourceAsStream("usuarios.jrxml");
		
		JasperReport jasper = JasperCompileManager.compileReport(in);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, null,ds);
		HtmlExporter htmlExporter = new HtmlExporter(DefaultJasperReportsContext.getInstance());
		htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
		htmlExporter.exportReport();

		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\""
                + nombreFichero+ "\"");

		*/
		
//	}

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
		 
}
