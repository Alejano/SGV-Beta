package com.PGJ.SGV.controllers;

import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.PGJ.SGV.models.dao.IBajaVehiculoDao;
import com.PGJ.SGV.models.entity.BajaVehiculo;
import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.models.entity.VehiculoDetalle;
import com.PGJ.SGV.models.entity.VehiculoEstado;
import com.PGJ.SGV.service.IBajaVehiculoService;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.service.IUploadFileService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;

@Controller
public class BajaVehiculoController {
	Vehiculo vehiculo = new Vehiculo();
	public static Long cocheid;

	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private IBajaVehiculoDao bajaservice;
	@Autowired
	private IUploadFileService uploadFileService;
	@Autowired
	private ILogsAuditService logsauditService;
	@Autowired
	private IObtenerUserService obuserService;
	@Autowired
	private IBajaVehiculoService bajaVehiculoService;
	
	
	@RequestMapping(value="/formBaja/{id_vehiculo}")
	public String bajaform(@PathVariable(value="id_vehiculo") Long id_vehiculo,Map<String,Object>model) {	
		
		Vehiculo vehiculo = null;	
		BajaVehiculo bajavehiculo = new BajaVehiculo();
		
		if(!id_vehiculo.equals(null)) {
			vehiculo = vehiculoService.findOne(id_vehiculo);			
			cocheid = vehiculo.getId_vehiculo();
		}else {
			return "redirect:/Vehiculos";
		}
		
		model.put("PageTitulo", "Baja del Vehiculo");
		model.put("vehiculo",vehiculo);
		model.put("bajavehiculo", bajavehiculo);
		model.put("titulo", "Baja Vehiculo");
		return "formBaja";
	}
	
	
	@RequestMapping(value="/formBaja",method = RequestMethod.POST)
	public String guardarBaja(Authentication authentication,BajaVehiculo baja,@RequestParam("file1") MultipartFile url_acta_fnq,
			@RequestParam("file2") MultipartFile url_oficio_baja,@RequestParam("file3") MultipartFile url_dictamen){
		
		VehiculoEstado vehiculoestado = new VehiculoEstado();
		
		long id = 5;
		String estado = "Baja";
		String motivo =  "baja";
		
		if(!cocheid.equals(null)) {
			vehiculo = vehiculoService.findOne(cocheid);			
			
		}else {
			return "redirect:/Vehiculos";
		}
		
		System.out.println(cocheid);
		
		vehiculoestado.setId_estado(id);
		vehiculoestado.setNombre_estado(estado);
		
		if (!url_acta_fnq.isEmpty()) {

			if (baja.getId_baja_vehiculo() != null 
					&& baja.getUrl_acta_fnq() != null 
					&& baja.getUrl_dictamen() != null 
					&& baja.getUrl_oficio_baja() != null
					&& baja.getUrl_acta_fnq().length() > 0
					&& baja.getUrl_dictamen().length() > 0
					&& baja.getUrl_oficio_baja().length() > 0
					) 
			{
				
				uploadFileService.delete(baja.getUrl_acta_fnq());
				uploadFileService.delete(baja.getUrl_dictamen());
				uploadFileService.delete(baja.getUrl_oficio_baja());
			}
			String nombreUnico1 = null;
			String nombreUnico2 = null;
			String nombreUnico3 = null;
			try {
				nombreUnico1 = uploadFileService.copy(url_acta_fnq);
				nombreUnico2 = uploadFileService.copy(url_oficio_baja);
				nombreUnico3 = uploadFileService.copy(url_dictamen);
				System.out.println(nombreUnico1);
				System.out.println(nombreUnico2);
				System.out.println(nombreUnico3);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			baja.setUrl_acta_fnq(nombreUnico1);
			baja.setUrl_oficio_baja(nombreUnico2);
			baja.setUrl_dictamen(nombreUnico3);
			baja.setVehiculo(vehiculo);			
			vehiculo.setMotivo(motivo);
			vehiculo.setVehiculo_estado(vehiculoestado);
			vehiculoService.save(vehiculo);
			bajaservice.save(baja);
			
			String valor_nuevo=baja.toString();
			
			//Auditoria
			
	     	LogsAudit logs = new LogsAudit();
	     	
	     	logs.setId_usuario(obuserService.obtenEmpl());
	 		logs.setTipo_role(obuserService.obtenUser());
			logs.setFecha(SystemDate.obtenFecha());
			logs.setHora(ObtenHour.obtenHour());
			logs.setName_table("BAJAS VEHICULOS");
			logs.setValor_viejo(valor_nuevo);
			logs.setTipo_accion("INSERT");
									
			logsauditService.save(logs);
			
		}		
						
		return "redirect:Vehiculos";
	}
	
	
	@RequestMapping(value="/formVehiBaja/{id_vehiculo}", method = RequestMethod.GET)
	public String VehiBaja(@PathVariable(value="id_vehiculo") Long id_vehiculo,Map<String, Object> model) {		
		
		String user;
    	String auxfa = null;
    	String auxfb = null;
    	String sinbaja=null;

		Vehiculo vehiculo = null;
		VehiculoDetalle detalle = null;
		BajaVehiculo baja = null;
		String tcirculacion = "";
		String actafnq = "";
		String oficiobaja = "";
		String dictamen = "";

		user = obuserService.obtenUser();
		model.put("usuario", user);

		if (id_vehiculo != null) {
			vehiculo = vehiculoService.findOne(id_vehiculo);		
			detalle = vehiculoService.findDetalle(id_vehiculo);
			baja = bajaVehiculoService.findBaja(id_vehiculo);
			tcirculacion = vehiculoService.findTCDetalle(id_vehiculo);
			actafnq = bajaVehiculoService.findActaFNQ(id_vehiculo);
		    oficiobaja = bajaVehiculoService.findOficio(id_vehiculo);
		    dictamen = bajaVehiculoService.findDictamen(id_vehiculo);
		    
		    
		    if(vehiculo.getFecha_alta() == null) {
		    	auxfa = "no";
		    }else {
		    	auxfa = "si";
		    }
		    
		    try {
		    if(baja == null) {auxfb = "no";}else {auxfb = "si";}
		    }catch (Exception e) {
			}
			
			if (tcirculacion != null) {
				tcirculacion = "existe";
			} else {
				tcirculacion = "noexiste";
			};
			

			if (actafnq != null) {
				actafnq = "existe";
			} else {
				actafnq = "noexiste";
			};
			

			if (oficiobaja != null) {
				oficiobaja = "existe";
			} else {
				oficiobaja = "noexiste";
			};
			

			if (dictamen != null) {
				dictamen = "existe";
			} else {
				dictamen = "noexiste";
			};


		} else {
			return "redirect:/Vehiculos";
		}

		vehiculo.setVehiculo_detalle(detalle);
	    
		try {if(baja.getId_baja_vehiculo() != null)
		{
		vehiculo.setBajavehiculo(baja);
		System.err.println("FECHA ALTA"+vehiculo.getBajavehiculo().getFecha_baja());
		sinbaja = "si";}
		
		}catch(Exception e) {
		}

		model.put("editando", "si");
		model.put("tcirculacion", tcirculacion);
		model.put("actafnq", actafnq);
		model.put("oficiobaja", oficiobaja);
		model.put("dictamen", dictamen);
		model.put("sinbaja", sinbaja);
		model.put("vehiculo", vehiculo);
		model.put("detalle", detalle);
		model.put("auxfa", auxfa);
		model.put("auxfa", auxfb);
		model.put("baja", baja);
		model.put("PageTitulo", "Baja Vehiculo");
		model.put("titulo", "Baja Vehiculo");
		
			return "formVehiBaja";
			
	} //BRENDA
	

}
