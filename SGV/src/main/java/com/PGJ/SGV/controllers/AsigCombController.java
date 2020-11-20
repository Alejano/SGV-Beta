package com.PGJ.SGV.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.ObtenMonth;
import com.PGJ.SGV.utilities.SystemDate;
import com.PGJ.SGV.models.entity.AsigCombustible;
import com.PGJ.SGV.models.entity.AsigCombustibleExt;
import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.models.entity.OdometroAcombus;
import com.PGJ.SGV.models.entity.OdometroAcombusExt;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.service.IAsigComExtService;
import com.PGJ.SGV.service.IAsigComService;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.service.IOdomExtService;
import com.PGJ.SGV.service.IOdomService;
import com.PGJ.SGV.service.IUploadFileService;
import com.PGJ.SGV.service.IVehiculoService;

@Controller
public class AsigCombController {

	@Autowired
	private IAsigComService AsigCombus;
	@Autowired
	private IOdomService OdometroService;
	@Autowired
	private IOdomExtService OdometroExtService;
	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private IAsigComExtService asigExtService;
	@Autowired
	private ILogsAuditService logsauditService;
	@Autowired
	private IObtenerUserService obuserService;
	@Autowired
	private IUploadFileService uploadFileService;
	
	public static OdometroAcombus odome = new OdometroAcombus();
	public static OdometroAcombusExt odomeext = new OdometroAcombusExt();
	public static String placaURL; 
	boolean editar = false;
	String user;
	
	@RequestMapping(value="/ListarCombustible/{id_vehiculo}", method = RequestMethod.GET)
	public String listar(@PathVariable(value="id_vehiculo") Long id_vehiculo,Model model) {
		
		boolean asignacionext=false;
		boolean asignacion=false;
		user = obuserService.obtenUser();
		
		List<AsigCombustible> combustiblePlacaMes = new ArrayList<AsigCombustible>();
		List<AsigCombustibleExt> combustibleExtePlacaMes = new ArrayList<AsigCombustibleExt>();
		List<AsigCombustible> combustiblePlacaHisto = new ArrayList<AsigCombustible>();
		List<AsigCombustibleExt> combustibleExtePlacaHisto = new ArrayList<AsigCombustibleExt>();
		
		Vehiculo vehiculo = new Vehiculo();
		vehiculo = vehiculoService.findOne(id_vehiculo);
		//System.out.println( "el mes es: " + ObtenMonth.obtenNumMes());
		combustiblePlacaMes = AsigCombus.findidVehiculoMensual(vehiculo.getId_vehiculo(),ObtenMonth.obtenNumMes());
	
		if(!combustiblePlacaMes.isEmpty()) {
			asignacion = false;
			asignacionext = true;
			
		
			if(vehiculo.getKilometraje_inicial() == combustiblePlacaMes.get(0).getKilometraje_ord()) {
				asignacionext = false;								
			}
			
			combustibleExtePlacaMes =  asigExtService.findextId(combustiblePlacaMes.get(0).getId_asignacion());
			if(!combustibleExtePlacaMes.isEmpty()) {
				if(vehiculo.getKilometraje_inicial() == combustibleExtePlacaMes.get(combustibleExtePlacaMes.size()-1).getKilometraje_ext() || combustibleExtePlacaMes.size() > 3) {
					asignacionext = false;
				}else {
					asignacionext = true;
				}
			}
						
					
		}else {asignacion = true;}
		
		combustiblePlacaHisto = AsigCombus.findidVehiculo(vehiculo.getId_vehiculo());	
		
		combustibleExtePlacaHisto = asigExtService.findAsigVehi(vehiculo.getId_vehiculo());
		
		model.addAttribute("existeAsignacion",asignacionext);
		model.addAttribute("ascombus",asignacion);
		model.addAttribute("mes",ObtenMonth.obtenNumMes());
		model.addAttribute("usuario",user);
		model.addAttribute("titulo","Listado de Combustible de "+ ObtenMonth.obtenMes()+" de la placa: "+vehiculo.getPlaca());
		model.addAttribute("titulo2","Listado de Combustible Historico");
		//Mensual
		model.addAttribute("combustible",combustiblePlacaMes);
		model.addAttribute("combustibleext",combustibleExtePlacaMes);
		//historico
		model.addAttribute("combustibleHisto",combustiblePlacaHisto);	
		model.addAttribute("combustibleExtHisto",combustibleExtePlacaHisto);	
		model.addAttribute("id_vehiculo",vehiculo.getId_vehiculo());
		
		return "Combustibles";
	}
		
	
	@RequestMapping(value="/formComb/Ag/{id_vehiculo}", method = RequestMethod.GET)
	public String crear(@PathVariable(value="id_vehiculo") Long id_vehiculo, Map<String,Object> model) {
		
		int ultimoid = AsigCombus.ultimoId();
	
		editar=false;
		AsigCombustible combustible = new AsigCombustible();
		OdometroAcombus odometro = new OdometroAcombus();
		Vehiculo vehiculo = new Vehiculo();
		vehiculo = vehiculoService.findOne(id_vehiculo);		
		combustible.setVehiculo(vehiculo);
		combustible.setKilometraje_ord(vehiculo.getKilometraje_inicial());
		combustible.setFecha_ini_ord(SystemDate.obtenFecha());
	    combustible.setId_asignacion(ultimoid+1);
	    
	    
		model.put("editar", editar);
		model.put("Odometro", odometro);
		model.put("placa", vehiculo.getPlaca());
		model.put("kilometraje", vehiculo.getKilometraje_inicial());
		model.put("combustible",combustible );				
		model.put("titulo", "Formulario de Combustible");
		model.put("mes", ObtenMonth.obtenMes());
							
		return "combustible/formComb";
	}
	
	@RequestMapping(value="/Addext/{id_asignacion}", method = RequestMethod.GET)
	public String crearext(@PathVariable(value="id_asignacion") Long id_asignacion, Map<String,Object> model) {		
		int ultimoid = asigExtService.ultimoid();
		editar = false;
		AsigCombustible combustible = new AsigCombustible();
		OdometroAcombus odometro = new OdometroAcombus();
		
		combustible = AsigCombus.findOne(id_asignacion);
		Vehiculo vehiculo = new Vehiculo();
		vehiculo = vehiculoService.findOne(combustible.getVehiculo().getId_vehiculo());
		
		AsigCombustibleExt extra = new AsigCombustibleExt();
		extra.setAsigCombustible(combustible);		
		extra.setKilometraje_ext(vehiculo.getKilometraje_inicial());
		extra.setId_asignacion((long) (ultimoid+1));
		
		model.put("editar", editar);
		model.put("Odometro", odometro);
		model.put("combustible",combustible );	
		model.put("placa", combustible.getVehiculo().getPlaca());
		model.put("combustibleExt",extra);				
		model.put("titulo", "Formulario de Combustible Extemporaneo de la placa: "+combustible.getVehiculo().getPlaca());
		model.put("mes", ObtenMonth.obtenMes());
							
		return "combustible/formCombExt";
	}
	
	@RequestMapping(value="/formComb/{id_asignacion}")
	public String editar(@PathVariable(value="id_asignacion") Long id_asignacion,Map<String,Object> model ) {
		AsigCombustible combustible = null;	
		OdometroAcombus odo = null;
		editar = true;
		if(!id_asignacion.equals(null)) {			
			combustible = AsigCombus.findOne(id_asignacion);
			odo = OdometroService.ObtenerAsignacion(id_asignacion);
		}else {
			return "redirect:/ListarCombustible";
		}
		model.put("Odometro", odo);
		model.put("combustible",combustible );	
		model.put("editar", editar);	
		model.put("titulo", "Editar Combustible");
		return "combustible/formComb";
	}
	
	@RequestMapping(value="/formCombext/{id_asignacion}")
	public String editarext(@PathVariable(value="id_asignacion") Long id_asignacion,Map<String,Object> model ) {		
		AsigCombustibleExt combusext=null;
		OdometroAcombusExt odox=null;
		
		editar = true;
		if(!id_asignacion.equals(null)) {			
			odox = OdometroExtService.ObtenerAsignacion(id_asignacion);			
			combusext = asigExtService.findOne(id_asignacion);
			
		}else {
			return "redirect:/ListarCombustible";
		}
		model.put("Odometro", odox);
		model.put("combustible",combusext );	
		model.put("editar", editar);	
		model.put("titulo", "Editar Combustible");
		return "combustible/formCombExt";
	}
	
	
	@RequestMapping(value="/formComb",method = RequestMethod.POST)
	public String guardar(AsigCombustible combustible,@RequestParam("file") MultipartFile documento,@RequestParam(value="id_odo")Long id_odo){
		OdometroAcombus odomex = new OdometroAcombus();
			if (!documento.isEmpty()) {
					var existe= false;
						try {
							odomex = OdometroService.findOne(id_odo);
							if(!odomex.equals(null)) {
								existe = true;
							}
						}catch (Exception e) {
							existe = false;
						}
				if(existe) {					
					uploadFileService.delete(odomex.getOdometro());
					odome.setId_asignacion(odomex.getId_asignacion());
					odome.setId_odo(odomex.getId_odo());					
				}else {
					
					odome.setId_asignacion(combustible.getId_asignacion());
					
				};
				String nombreUnico = "";						
					try {
						nombreUnico = uploadFileService.copy(documento);
						odome.setOdometro(nombreUnico);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
			}
			
		Vehiculo vehiculo = null;	
		vehiculo = vehiculoService.findOne(combustible.getVehiculo().getId_vehiculo());	
		combustible.setVehiculo(vehiculo);
		combustible.setKilometraje_ord(vehiculo.getKilometraje_inicial());
				
        if(editar == true) {
			
			//AsigComb OLD
        	  
        	AsigCombustible combustible_old = null;
        	combustible_old =  AsigCombus.findOne(combustible.getId_asignacion());
		    //System.err.println("old:"+combustible_old.toString());
		    String valor_old = combustible_old.toString();
		    	
		    AsigCombus.save(combustible);	
			OdometroService.save(odome);
			//Auditoria
			
         	LogsAudit logs = new LogsAudit();
         	
         	logs.setId_usuario(obuserService.obtenEmpl());
    		logs.setTipo_role(obuserService.obtenUser());
			logs.setFecha(SystemDate.obtenFecha());
			logs.setHora(ObtenHour.obtenHour());
			logs.setName_table("ACOMBUS_ORD");
			logs.setValor_viejo(valor_old);
			logs.setTipo_accion("UPDATE");
									
			logsauditService.save(logs);
			
			editar = false;	
			
			}else {
									
				AsigCombus.save(combustible);															
				OdometroService.save(odome);
				
				String valor_nuevo=combustible.toString();
				
				//Auditoria
				
				LogsAudit logs = new LogsAudit();
				
				logs.setId_usuario(obuserService.obtenEmpl());
				logs.setTipo_role(obuserService.obtenUser());
				logs.setFecha(SystemDate.obtenFecha());
				logs.setHora(ObtenHour.obtenHour());
				logs.setName_table("ACOMBUS_ORD");
				logs.setValor_viejo(valor_nuevo);
				logs.setTipo_accion("INSERT");
				
				logsauditService.save(logs);
		
		}
								
		return "redirect:/ListarCombustible/"+vehiculo.getId_vehiculo();
								
	}
	
	@RequestMapping(value="/formCombExt",method = RequestMethod.POST)
	public String guardarExt(AsigCombustibleExt combustibleext,@RequestParam("file") MultipartFile documento,@RequestParam(value="id_odo")Long id_odo){
			OdometroAcombusExt odomex = new OdometroAcombusExt();	
			if (!documento.isEmpty()) {
					var existe= false;
						try {
							odomex = OdometroExtService.findOne(id_odo);
							if(!odomex.equals(null)) {
								existe = true;
							}
						}catch (Exception e) {
							existe = false;
						}
				if(existe) {					
					uploadFileService.delete(odomex.getOdometro());
					odomeext.setId_asignacion(odomex.getId_asignacion());
					odomeext.setId_odo(odomex.getId_odo());					
				}else {
					
					odomeext.setId_asignacion(combustibleext.getId_asignacion());
					
				};
				String nombreUnico = "";						
					try {
						nombreUnico = uploadFileService.copy(documento);
						odomeext.setOdometro(nombreUnico);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
			}
			
		AsigCombustible combustible = null;		
		combustible = AsigCombus.findOne(combustibleext.getAsigCombustible().getId_asignacion());	
		combustibleext.setAsigCombustible(combustible);		
		combustibleext.setKilometraje_ext(combustible.getVehiculo().getKilometraje_inicial());

		 if(editar == true) {
				
				//AsigCombExt OLD
	        	  
	        	AsigCombustible combustible_old = null;
	    		combustible_old = AsigCombus.findOne(combustibleext.getId_asignacion());	
			   // System.err.println("old:"+combustible_old.toString());
			    String valor_old = combustible_old.toString();
			    	
			    asigExtService.save(combustibleext);	
				OdometroExtService.save(odomeext);
				//Auditoria
				
	         	LogsAudit logs = new LogsAudit();
	         	
	         	logs.setId_usuario(obuserService.obtenEmpl());
				logs.setTipo_role(obuserService.obtenUser());
				logs.setFecha(SystemDate.obtenFecha());
				logs.setHora(ObtenHour.obtenHour());
				logs.setName_table("ACOMBUS_EXT");
				logs.setValor_viejo(valor_old);
				logs.setTipo_accion("UPDATE");
										
				logsauditService.save(logs);
				
				editar = false;	
				
			}else {
			
			asigExtService.save(combustibleext);
			OdometroExtService.save(odomeext);
			
			String valor_nuevo=combustibleext.toString();
			
			//Auditoria
			
	     	LogsAudit logs = new LogsAudit();
	     	
	     	logs.setId_usuario(obuserService.obtenEmpl());
			logs.setTipo_role(obuserService.obtenUser());
			logs.setFecha(SystemDate.obtenFecha());
			logs.setHora(ObtenHour.obtenHour());
			logs.setName_table("ACOMBUS_EXT");
			logs.setValor_viejo(valor_nuevo);
			logs.setTipo_accion("INSERT");
									
			logsauditService.save(logs);
			
			}
		 
			return "redirect:/ListarCombustible/"+combustible.getVehiculo().getId_vehiculo();
	}

	
	@RequestMapping(value="/elimComb/{id_asignacion}/{placa}")
	public String eliminar (@PathVariable(value="id_asignacion")Long id_asignacion,@PathVariable(value="placa")String placa) {
		
		if(!id_asignacion.equals(null)) {
			AsigCombus.delete(id_asignacion);
		}
		return "redirect:/ListarCombustible/"+placa;
	}
	
	@RequestMapping(value="/elimCombext/{id_asignacion}/{placa}")
	public String eliminarext (@PathVariable(value="id_asignacion")Long id_asignacion,@PathVariable(value="placa")String placa) {
		
		if(!id_asignacion.equals(null)) {
			asigExtService.delete(id_asignacion);
			
		}
		return "redirect:/ListarCombustible/"+placa;
	}
	
	@RequestMapping(value="/Combustibles", method = RequestMethod.GET)
	public String listarCA(Map<String,Object> model, @RequestParam(value="id_vehiculo")Long id_vehiculo,Model model2) throws Exception {
		
		user = obuserService.obtenUser();
		placaURL=vehiculoService.findOne(id_vehiculo).getPlaca();
		List<AsigCombustible> combustible = new ArrayList<AsigCombustible>();
		List<AsigCombustible> combustiblePlaca = new ArrayList<AsigCombustible>();
		combustible = AsigCombus.findAll();	
	/*	
		for(AsigCombustible comb: combustible) {
			if(comb.getVehiculo().getPlaca().equals(placaURL)) {
											
				combustiblePlaca.add(comb);
				}
		}*/
					
	    model2.addAttribute("placa", placaURL);
	    model.put("usuario",user);
		model.put("combustible", combustiblePlaca);
		model.put("titulo", "Formulario de Combustible");
							
		return "Combustibles";
	}
	
	
	@RequestMapping(value = "/ListarCombustible/refreshImg")
	public @ResponseBody String verImg(@RequestParam("id_asignacion") Long id_asignacion, Model model) {
		OdometroAcombus odo = new OdometroAcombus();
		odo = OdometroService.ObtenerAsignacion(id_asignacion);		
		String html="";
				if(odo.getOdometro().equals(null)) {
					 html="<div>No se encontro la imagen en el servidor</div>";	
				}else {
					 html="<div class=\"card-body justify-content-center col-sm-12\">\r\n" + 
							"		<div class=\"card-body d-flex justify-content-center\" >\r\n" + 
							"				<img src=\"/upload/"+odo.getOdometro()+"\" type=\"image/png, image/jpg, image/jpeg\" style=\"height:715px;\"/>\r\n" + 
							"		</div>\r\n" + 
							"	</div>";	
				}						
							
	    return html;
	}	
	
	@RequestMapping(value = "/ListarCombustible/refreshImgext")
	public @ResponseBody String verImgext(@RequestParam("id_asignacion") Long id_asignacion, Model model) {		
		OdometroAcombusExt odo = new OdometroAcombusExt();
		odo = OdometroExtService.ObtenerAsignacion(id_asignacion);		
		String html="";
				if(odo.getOdometro().equals(null)) {
					 html="<div>No se encontro la imagen en el servidor</div>";	
				}else {
					 html="<div class=\"card-body justify-content-center col-sm-12\">\r\n" + 
							"		<div class=\"card-body d-flex justify-content-center\" >\r\n" + 
							"				<img src=\"/upload/"+odo.getOdometro()+"\" type=\"image/png, image/jpg, image/jpeg\" style=\"height:715px;\"/>\r\n" + 
							"		</div>\r\n" + 
							"	</div>";	
				}						
							
	    return html;
	}	
	
	
}
