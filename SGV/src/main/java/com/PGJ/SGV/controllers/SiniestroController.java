package com.PGJ.SGV.controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import org.springframework.web.multipart.MultipartFile;

import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.models.entity.Mantenimiento;
import com.PGJ.SGV.models.entity.Resguardante;
import com.PGJ.SGV.models.entity.Seguro;
import com.PGJ.SGV.models.entity.Siniestro;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.service.IUploadFileService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.util.paginador.PageRender;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IResguardanteService;
import com.PGJ.SGV.service.ISiniestroService;

@Controller
public class SiniestroController {
 

	List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
	List<Siniestro> siniestros = new ArrayList<Siniestro>();

	static String user="";
	@Autowired
	private ISiniestroService siniestroService;
	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private IUploadFileService uploadFileService;
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IResguardanteService resguardanteService;
	@Autowired
	private ILogsAuditService logsauditService;
	
	boolean editar = false;
	boolean aux=false;
	Long id_vehi;
	Long id_sin;
	
	@RequestMapping(value="/Siniestros", method = RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue = "0") int page,Model model,Authentication authentication) {	
		
		aux=false;
		var ads="";			
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";	model.addAttribute("usuario",user);
			}else {
				if(hasRole("ROLE_USER")) {
					user = "ROLE_USER"; model.addAttribute("usuario",user);
				}else {
					if(hasRole("ROLE_SEGURO")) {
						user = "ROLE_SEGURO"; model.addAttribute("usuario",user);
					}else {
						if(hasRole("ROLE_SINIESTRO")) {
						user = "ROLE_SINIESTRO"; model.addAttribute("usuario",user);
						}
				   }
			    }	
			}

		ads = authentication.getName();
		Pageable pageRequest = PageRequest.of(page, 100);
		
		if(user.equals("ROLE_USER")){
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);
			
		}
	
		Page<Siniestro> SiniestroPage = siniestroService.findAll(pageRequest);
		PageRender<Siniestro> SiniestroRender = new PageRender<>("/Siniestros",SiniestroPage);
		if(siniestroService.totalSiniestro()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
		
		model.addAttribute("titulo","Listado de Siniestros");
		model.addAttribute("auxiliar", aux);
		model.addAttribute("siniestros",SiniestroPage);
		model.addAttribute("page",SiniestroRender);
		return "Siniestros";
		
	}
	
	@RequestMapping(value="/ListarSiniestros/{id_vehiculo}", method = RequestMethod.GET)
	public String listarpv(@PathVariable(value="id_vehiculo") Long id_vehiculo,@RequestParam(name="page", defaultValue = "0") int page,Model model,Authentication authentication) {	
	
		aux=true;
		var ads="";		
		id_vehi=id_vehiculo;

		
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";	model.addAttribute("usuario",user);
			}else {
				if(hasRole("ROLE_USER")) {
					user = "ROLE_USER"; model.addAttribute("usuario",user);
				}else {
					if(hasRole("ROLE_SEGURO")) {
						user = "ROLE_SEGURO"; model.addAttribute("usuario",user);
					}else {
						if(hasRole("ROLE_SINIESTRO")) {
						user = "ROLE_SINIESTRO"; model.addAttribute("usuario",user);
						}
				   }
			    }	
			}

		ads = authentication.getName();
		Pageable pageRequest = PageRequest.of(page, 100);
		
		
			Vehiculo vehiculo = new Vehiculo();
			vehiculo = vehiculoService.findOne(id_vehiculo);
			
			Page<Siniestro> SiniestroPage = siniestroService.FindsegVehi(vehiculo.getId_vehiculo(), pageRequest);
			PageRender<Siniestro> SiniestroRenderArea = new PageRender<>("/Siniestros",SiniestroPage);
			if(siniestroService.totalSiniestro()>=6) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
			model.addAttribute("titulo","Listado de Siniestros");
			model.addAttribute("id_vehiculo",vehiculo.getId_vehiculo());
			model.addAttribute("auxiliar", aux);
			model.addAttribute("siniestros",SiniestroPage);
			model.addAttribute("page",SiniestroRenderArea);
			return "Siniestros";

	}
		
	
	@RequestMapping(value="/formSin/Ag/{id_vehiculo}")
	public String crear(@PathVariable(value="id_vehiculo") Long id_vehiculo,Map<String,Object> model,Authentication authentication) {

		editar=false;
		var ads="";						
		ads = authentication.getName();
		var user="";
		Siniestro siniestro = new Siniestro();
		Vehiculo vehi= new Vehiculo();
		List<Resguardante> resguardantes = new ArrayList<Resguardante>();
		
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";	model.put("usuario",user);
			}else {
				if(hasRole("ROLE_USER")) {
					user = "ROLE_USER"; model.put("usuario",user);
				}else {
					if(hasRole("ROLE_SEGURO")) {
						user = "ROLE_SEGURO"; model.put("usuario",user);
					}else {
						if(hasRole("ROLE_SINIESTRO")) {
						user = "ROLE_SINIESTRO"; model.put("usuario",user);
						}
				   }
			    }	
			}
		
		resguardantes = resguardanteService.findResg(id_vehiculo);
		vehi = vehiculoService.findOne(id_vehiculo);
		siniestro.setVehiculo(vehi);
		model.put("id_vehiculo", vehi.getId_vehiculo());
		model.put("editar", editar);
		model.put("resguardantes", resguardantes);
		model.put("siniestros", siniestro);
		model.put("titulo", "Formulario de Siniestros");	
		return "formSin";
		
		
	}
	
	@RequestMapping(value="/formSin/{id_siniestro}")
	public String editar(@PathVariable(value="id_siniestro") Long id_siniestro,Map<String,Object>model) {
				
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";	model.put("usuario",user);
			}else {
				if(hasRole("ROLE_USER")) {
					user = "ROLE_USER"; model.put("usuario",user);
				}else {
					if(hasRole("ROLE_SEGURO")) {
						user = "ROLE_SEGURO"; model.put("usuario",user);
					}else {
						if(hasRole("ROLE_SINIESTRO")) {
						user = "ROLE_SINIESTRO"; model.put("usuario",user);
						}
				   }
			    }	
			}
		
		editar=true;
		Siniestro siniestro = null;
		
	
		if(!id_siniestro.equals(null)) {
			siniestro = siniestroService.findOne(id_siniestro);
			id_sin=siniestro.getId_siniestro();			
		}else {
			return "redirect:/Siniestros";
		}
		model.put("id_vehiculo", siniestro.getVehiculo().getId_vehiculo());
		model.put("editar", editar);
		model.put("siniestros",siniestro);
		model.put("titulo", "Editar Siniestro");
		return "formSin";

	}

	
	@RequestMapping(value="/formSin",method = RequestMethod.POST)
	public String guardar(Siniestro siniestro, @RequestParam("file_ide") MultipartFile identificacion,@RequestParam("file_ine") MultipartFile ine,
			@RequestParam("file_licencia") MultipartFile licencia, @RequestParam("file_declaracion") MultipartFile declaracion_universal,
			@RequestParam("file_ingreso") MultipartFile constancia_ingreso, @RequestParam("file_salida") MultipartFile constancia_salida
			,Authentication authentication) {
		
		    var user="";
			var no_user ="";
			no_user = authentication.getName();
		
	
		   if(hasRole("ROLE_ADMIN")) {
				user ="ROLE_ADMIN";						
			}else {
				if(hasRole("ROLE_USER")) {
					user = "ROLE_USER";
				}
			};
			
			Vehiculo vehiculoselect =new Vehiculo();
			
			//IDENTIFICACION
			
			if(!identificacion.isEmpty() && ine.isEmpty() && licencia.isEmpty() && declaracion_universal.isEmpty() 
					&& constancia_ingreso.isEmpty() && constancia_salida.isEmpty()) {
	
				if(siniestro.getUrl_ine().length()>0 && siniestro.getUrl_licencia().length()>0 
						&& siniestro.getUrl_declaracion_universal().length()>0 && siniestro.getUrl_constancia_ingreso_taller().length()>0 
						&& siniestro.getUrl_constancia_salida_taller().length()>0) 
				{
					System.err.println("IDENTIFICACION");
					uploadFileService.delete(siniestro.getUrl_identificacion_fgjcdmx());}
				String nombreUnicoIDE = null;	
				try {
					nombreUnicoIDE = uploadFileService.copy(identificacion);
					siniestro.setUrl_identificacion_fgjcdmx(nombreUnicoIDE);
 				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}								
			}
			
			//INE
			
			if(identificacion.isEmpty() && !ine.isEmpty() && licencia.isEmpty() && declaracion_universal.isEmpty() 
					&& constancia_ingreso.isEmpty() && constancia_salida.isEmpty()) {
	
				if(siniestro.getUrl_identificacion_fgjcdmx().length()>0 && siniestro.getUrl_licencia().length()>0 
						&& siniestro.getUrl_declaracion_universal().length()>0 && siniestro.getUrl_constancia_ingreso_taller().length()>0 
						&& siniestro.getUrl_constancia_salida_taller().length()>0) 
				{
					System.err.println("INE");
					uploadFileService.delete(siniestro.getUrl_ine());}
				String nombreUnicoINE = null;	
				try {
					nombreUnicoINE = uploadFileService.copy(ine);
					siniestro.setUrl_ine(nombreUnicoINE);
 				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}								
			}
			
			//LICENCIA
			
			if(identificacion.isEmpty() && ine.isEmpty() && !licencia.isEmpty() && declaracion_universal.isEmpty() 
					&& constancia_ingreso.isEmpty() && constancia_salida.isEmpty()) {
	
				if(siniestro.getUrl_identificacion_fgjcdmx().length()>0 && siniestro.getUrl_ine().length()>0 
						&& siniestro.getUrl_declaracion_universal().length()>0 && siniestro.getUrl_constancia_ingreso_taller().length()>0 
						&& siniestro.getUrl_constancia_salida_taller().length()>0) 
				{
					System.err.println("LICENCIA");
					uploadFileService.delete(siniestro.getUrl_licencia());}
				String nombreUnicoLI = null;	
				try {
					nombreUnicoLI = uploadFileService.copy(licencia);
					siniestro.setUrl_licencia(nombreUnicoLI);
 				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}								
			}
			
			//DECLARACION UNIVERSAL
			
			if(identificacion.isEmpty() && ine.isEmpty() && licencia.isEmpty() && !declaracion_universal.isEmpty() 
					&& constancia_ingreso.isEmpty() && constancia_salida.isEmpty()) {
	
				if(siniestro.getUrl_identificacion_fgjcdmx().length()>0 && siniestro.getUrl_ine().length()>0 
						&& siniestro.getUrl_licencia().length()>0 && siniestro.getUrl_constancia_ingreso_taller().length()>0 
						&& siniestro.getUrl_constancia_salida_taller().length()>0) 
				{
					System.err.println("DECLARACION UNIVERSAL");
					uploadFileService.delete(siniestro.getUrl_declaracion_universal());}
				String nombreUnicoDEC = null;	
				try {
					
					nombreUnicoDEC = uploadFileService.copy(declaracion_universal);
					siniestro.setUrl_declaracion_universal(nombreUnicoDEC);
 				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}								
			}
			
			//CONSTANCIA INGRESO TALLER
			
			if(identificacion.isEmpty() && ine.isEmpty() && licencia.isEmpty() && declaracion_universal.isEmpty() 
					&& !constancia_ingreso.isEmpty() && constancia_salida.isEmpty()) {
	
				if(siniestro.getUrl_identificacion_fgjcdmx().length()>0 && siniestro.getUrl_ine().length()>0 
						&& siniestro.getUrl_licencia().length()>0 && siniestro.getUrl_declaracion_universal().length()>0 
						&& siniestro.getUrl_constancia_salida_taller().length()>0) 
				{
					System.err.println("CONSTANCIA INGRESO TALLER");
					uploadFileService.delete(siniestro.getUrl_constancia_ingreso_taller());}
				String nombreUnicoCIN= null;	
				try {
					nombreUnicoCIN = uploadFileService.copy(constancia_ingreso);
					siniestro.setUrl_constancia_ingreso_taller(nombreUnicoCIN);
 				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}								
			}
		
			//CONSTANCIA SALIDA TALLER
			
			if(identificacion.isEmpty() && ine.isEmpty() && licencia.isEmpty() && declaracion_universal.isEmpty() 
					&& constancia_ingreso.isEmpty() && !constancia_salida.isEmpty()) {
	
				if(siniestro.getUrl_identificacion_fgjcdmx().length()>0 && siniestro.getUrl_ine().length()>0 
						&& siniestro.getUrl_licencia().length()>0 && siniestro.getUrl_declaracion_universal().length()>0
						&& siniestro.getUrl_constancia_ingreso_taller().length()>0 ) 
				{
					System.err.println("CONSTANCIA SALIDA TALLER");
					uploadFileService.delete(siniestro.getUrl_constancia_salida_taller());}
				String nombreUnicoCSA = null;	
				try {
					nombreUnicoCSA = uploadFileService.copy(constancia_salida);
					siniestro.setUrl_constancia_salida_taller(nombreUnicoCSA);
 				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}								
			}

			// ARCHIVOS COMPLETOS
	
			if (!identificacion.isEmpty() && !ine.isEmpty() && !licencia.isEmpty() && !declaracion_universal.isEmpty() 
					&& !constancia_ingreso.isEmpty() && !constancia_salida.isEmpty())
					 {
								
				if(siniestro.getUrl_identificacion_fgjcdmx().length()>0 && siniestro.getUrl_ine().length()>0 && siniestro.getUrl_licencia().length()>0 
						&& siniestro.getUrl_declaracion_universal().length()>0 && siniestro.getUrl_constancia_ingreso_taller().length()>0 
						&& siniestro.getUrl_constancia_salida_taller().length()>0) 
				{	
					
					uploadFileService.delete(siniestro.getUrl_identificacion_fgjcdmx());
					uploadFileService.delete(siniestro.getUrl_ine());
					uploadFileService.delete(siniestro.getUrl_licencia());
				    uploadFileService.delete(siniestro.getUrl_declaracion_universal());
					uploadFileService.delete(siniestro.getUrl_constancia_ingreso_taller());
					uploadFileService.delete(siniestro.getUrl_constancia_salida_taller());
					}
				
				String nombreUnicoIDE = null;	
				String nombreUnicoINE = null;						
				String nombreUnicoLI = null;						
				String nombreUnicoDEC = null;						
				String nombreUnicoIN = null;						
				String nombreUnicoSA = null;	
				

					try {
						nombreUnicoIDE = uploadFileService.copy(identificacion);
						nombreUnicoINE = uploadFileService.copy(ine);
						nombreUnicoLI = uploadFileService.copy(licencia);						
						nombreUnicoDEC = uploadFileService.copy(declaracion_universal);						
						nombreUnicoIN = uploadFileService.copy(constancia_ingreso);						
						nombreUnicoSA = uploadFileService.copy(constancia_salida);
						siniestro.setUrl_identificacion_fgjcdmx(nombreUnicoIDE);
						siniestro.setUrl_ine(nombreUnicoINE);
						siniestro.setUrl_licencia(nombreUnicoLI);
						siniestro.setUrl_declaracion_universal(nombreUnicoDEC);
						siniestro.setUrl_constancia_ingreso_taller(nombreUnicoIN);
						siniestro.setUrl_constancia_salida_taller(nombreUnicoSA);
						
	 				} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
			}
			
			
					if(editar == true) {
						
						//Siniestro OLD
					    
						Siniestro siniestro_old = null;
						siniestro_old = siniestroService.findOne(siniestro.getId_siniestro());
					    System.err.println("old:"+siniestro_old.toString());
					    String valor_old = siniestro_old.toString();
					    
						System.err.println("entroeditar: "+siniestro.getId_siniestro());
						vehiculoselect = vehiculoService.findOne(siniestro.getVehiculo().getId_vehiculo());	
						siniestro.setVehiculo(vehiculoselect);
						siniestroService.save(siniestro);
						
						//Auditoria
						
                     	LogsAudit logs = new LogsAudit();
                     	
                        logs.setId_usuario(no_user);
						logs.setTipo_role(user);
						logs.setFecha(SystemDate.obtenFecha());
						logs.setHora(ObtenHour.obtenHour());
						logs.setName_table("SINIESTROS");
						logs.setValor_viejo(valor_old);
						logs.setTipo_accion("UPDATE");
												
						logsauditService.save(logs);
						
					    editar = false;	
						
					}else {
					
					System.err.println("entrocrear: "+siniestro.getId_siniestro());
				
					vehiculoselect = vehiculoService.findOne(siniestro.getVehiculo().getId_vehiculo());							
					siniestro.setVehiculo(vehiculoselect);
					System.err.println("CREA:"+siniestro.toString());
					siniestroService.save(siniestro);
					String valor_nuevo=siniestro.toString();
					
					//Auditoria
					
                 	LogsAudit logs = new LogsAudit();
                 	
                    logs.setId_usuario(no_user);
					logs.setTipo_role(user);
					logs.setFecha(SystemDate.obtenFecha());
					logs.setHora(ObtenHour.obtenHour());
					logs.setName_table("SINIESTROS");
					logs.setValor_viejo(valor_nuevo);
					logs.setTipo_accion("INSERT");
											
					logsauditService.save(logs);

					}				
				
	    return "redirect:/ListarSiniestros/"+siniestro.getVehiculo().getId_vehiculo();
						
	}
	


	@RequestMapping(value="/elimSin/{id_siniestro}/{identificacion}/{ine}/{licencia}/{declaracion}/{ingreso}/{salida}")
	public String eliminar (@PathVariable(value="id_siniestro")Long id_siniestro,@PathVariable(value="identificacion")String identificacion,
			@PathVariable(value="ine")String ine, @PathVariable(value="licencia")String licencia,
			@PathVariable(value="declaracion")String declaracion,@PathVariable(value="ingreso")String ingreso,
			@PathVariable(value="salida")String salida) {
		
	
		
		if(id_siniestro != null) {
			siniestroService.delete(id_siniestro);	
			if(identificacion != "" && ine != "" && licencia != "" && declaracion != "" && ingreso != "" && salida != "") {
				uploadFileService.delete(identificacion);
				uploadFileService.delete(ine);
				uploadFileService.delete(licencia);
				uploadFileService.delete(declaracion);
				uploadFileService.delete(ingreso);
				uploadFileService.delete(salida);
			}
		}
		return "redirect:/Vehiculos";
	}
	
	
	@RequestMapping(value="/elimSeg/{id_siniestro}/")
	public String eliminar (@PathVariable(value="id_siniestro")Long id_siniestro) {
		
		if(id_siniestro != null) {
			siniestroService.delete(id_siniestro);				
		}
		return "redirect:/Vehiculos";
	}
	
	
	@RequestMapping(value="/formSinBuscarPv")
	public String Buscartablapv(@RequestParam(name="page", defaultValue = "0") int page,Authentication authentication,
		@RequestParam(value="elemento") String elemento,Model model){	
		
		 aux=true;
		 Pageable pageRequest = PageRequest.of(page, 100);
		 var ads="";		
		 ads = authentication.getName();
		 String elementof="";
		 
			if(hasRole("ROLE_ADMIN")) {
				user ="ROLE_ADMIN";	model.addAttribute("usuario",user);
				}else {
					if(hasRole("ROLE_USER")) {
						user = "ROLE_USER"; model.addAttribute("usuario",user);
					}else {
						if(hasRole("ROLE_SEGURO")) {
							user = "ROLE_SEGURO"; model.addAttribute("usuario",user);
						}else {
							if(hasRole("ROLE_SINIESTRO")) {
							user = "ROLE_SINIESTRO"; model.addAttribute("usuario",user);
							}
					   }
				    }	
				}
				
		 if(!elemento.isBlank()) {			
							if(isValidDouble(elemento)) {
								Double Dato = Double.parseDouble(elemento);
									DecimalFormat formt = new DecimalFormat("0");
									elemento = formt.format(Dato);
									elemento = elemento.replaceAll(",","");	
							};
					elementof = elemento.toUpperCase(); 
					Page<Siniestro> siniestrospage= siniestroService.FindSinElemVehiPage(id_vehi, elementof, pageRequest);
					PageRender<Siniestro> pageRender = new PageRender<>("/formSinBuscarPv?elemento="+ elementof, siniestrospage);
					if(siniestroService.totalSiniestro()>=5) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
					
					model.addAttribute("siniestros",siniestrospage);
					model.addAttribute("page",pageRender);
					model.addAttribute("elemento",elementof);	
					model.addAttribute("auxiliar", aux);
					model.addAttribute("id_vehiculo",id_vehi);
	     			return "Siniestros";		
		}else{
			return "redirect:/Siniestros";
		}
		 
	}
	
	@RequestMapping(value="/formSinBuscar")
	public String Buscartabla(@RequestParam(name="page", defaultValue = "0") int page,Authentication authentication,
		@RequestParam(value="elemento") String elemento,Model model){	
		
		 aux=false;
		 Pageable pageRequest = PageRequest.of(page, 100);
		 var ads="";		
		 ads = authentication.getName();
		 String elementof="";
		 
			if(hasRole("ROLE_ADMIN")) {
				user ="ROLE_ADMIN";	model.addAttribute("usuario",user);
				}else {
					if(hasRole("ROLE_USER")) {
						user = "ROLE_USER"; model.addAttribute("usuario",user);
					}else {
						if(hasRole("ROLE_SEGURO")) {
							user = "ROLE_SEGURO"; model.addAttribute("usuario",user);
						}else {
							if(hasRole("ROLE_SINIESTRO")) {
							user = "ROLE_SINIESTRO"; model.addAttribute("usuario",user);
							}
					   }
				    }	
				}
			
		 if(!elemento.isBlank()) {			
							if(isValidDouble(elemento)) {
								Double Dato = Double.parseDouble(elemento);
									DecimalFormat formt = new DecimalFormat("0");
									elemento = formt.format(Dato);
									elemento = elemento.replaceAll(",","");	
							};
						if(user.equals("ROLE_USER")) {
						 Usuario usus = new Usuario();
						 usus = usuarioService.findbyAdscripcion(ads);
						 elementof = elemento.toUpperCase(); 

							Page<Siniestro> siniestrospage= siniestroService.FindSinElemenAreaPage(usus.getAdscripcion().getId_adscripcion(), elementof, pageRequest);
						    PageRender<Siniestro> pageRender = new PageRender<>("/formSinBuscar?elemento="+elementof, siniestrospage);
							if(siniestroService.totalSiniestro()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};

						    model.addAttribute("siniestros",siniestrospage);
							model.addAttribute("auxiliar", aux);
							model.addAttribute("page",pageRender);
							model.addAttribute("elemento",elementof);
							model.addAttribute("id_vehiculo",id_vehi);
							return "Siniestros";
					};
					
					elementof = elemento.toUpperCase(); 
					Page<Siniestro> siniestrospage= siniestroService.FindSinElemenPage(elementof, pageRequest);
					PageRender<Siniestro> pageRender = new PageRender<>("/formSinBuscar?elemento="+elementof, siniestrospage);
					if(siniestroService.totalSiniestro()>=5) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};

					model.addAttribute("siniestros",siniestrospage);
					model.addAttribute("page",pageRender);
					model.addAttribute("elemento",elementof);	
					model.addAttribute("auxiliar", aux);
					model.addAttribute("id_vehiculo",id_vehi);
	     			return "Siniestros";		
		}else{
			return "redirect:/Siniestros";
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
