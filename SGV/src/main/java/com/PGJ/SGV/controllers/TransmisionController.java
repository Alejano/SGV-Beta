package com.PGJ.SGV.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.models.entity.VehiculoTransmision;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.service.ITransVehiculoService;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;

@Controller
public class TransmisionController {
	
List<VehiculoTransmision> transmisiones = new ArrayList<VehiculoTransmision>();
	
	@Autowired
	private ITransVehiculoService transService;
	@Autowired
	private ILogsAuditService logsauditService;
	@Autowired
	private IObtenerUserService obuserService;
	
	boolean editar = false;
	String user;
	Long ID;
	
	@RequestMapping(value="/TransmisionVehiculo", method = RequestMethod.GET)
	public String listar(Model model) {
		
		user = obuserService.obtenUser();
		transmisiones = transService.findAll();
		if(transService.transmisionestotales()>= 4) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};	
		model.addAttribute("PageTitulo", "Transmisiones Vehiculo");
		model.addAttribute("PageSubTitulo","Listado de Transmisiones Vehiculo");
		model.addAttribute("titulo","Listado de Transmisiones Vehiculo");
		model.addAttribute("transmisiones",transmisiones);
		model.addAttribute("usuario",user);	
		return "VehiculoTrans";
		
	}
		
	
	@RequestMapping(value="/formTrans/Ag")
	public String crear(Map<String,Object> model) {
		
		VehiculoTransmision transmisionvehiculo = new VehiculoTransmision();
		
		long id_transmision = transService.ultimoId() + 1;
		transmisionvehiculo.setId_transmision(id_transmision);
				
		model.put("transmision", transmisionvehiculo);
		model.put("id", id_transmision);
		model.put("usuario",obuserService.obtenUser());
		model.put("PageTitulo", "Agregar Transmision Vehiculo");
		model.put("titulo", "Formulario de Transmision Vehiculo");
		
		return "formTrans";
	}
	
	
	@RequestMapping(value="/formTrans/{id_transmision}")
	public String editar(@PathVariable(value="id_transmision") Long id_transmision,Map<String,Object>model) {
		editar = true;
		
		VehiculoTransmision transmisionvehiculo = null;
		
		if(id_transmision != 0) {
			transmisionvehiculo = transService.findOne(id_transmision);
			ID = transmisionvehiculo.getId_transmision();
		}else {
			return "redirect:/VehiculoTrans";
		}
				
		model.put("transmision",transmisionvehiculo);
		model.put("PageTitulo", "Editar Transmision Vehiculo");
		model.put("titulo", "Editar Transmision Vehiculo");
		model.put("PageTitulo", "Editar Transmision Vehiculo");
		return "formTrans";
		
	}
	
	
	@RequestMapping(value="/formTrans",method = RequestMethod.POST)
	public String guardar(VehiculoTransmision transmisionvehiculo){
				
		if(editar == true) {
		
		//Marca OLD
	   
	    VehiculoTransmision transvehiculo_old = null;
	    transvehiculo_old = transService.findOne(transmisionvehiculo.getId_transmision());
	    String valor_old = transvehiculo_old.toString();
	    transService.save(transmisionvehiculo);			
		editar = false;	
		
		//Auditoria
		
     	LogsAudit logs = new LogsAudit();
     	
        logs.setId_usuario(obuserService.obtenEmpl());
		logs.setTipo_role(obuserService.obtenUser());
		logs.setFecha(SystemDate.obtenFecha());
		logs.setHora(ObtenHour.obtenHour());
		logs.setName_table("VEHICULO TRANSMISION");
		logs.setValor_viejo(valor_old);
		logs.setTipo_accion("UPDATE");
								
		logsauditService.save(logs);
		editar = false;	
		
		}else {
			
			transService.save(transmisionvehiculo);	
			String valor_nuevo = transmisionvehiculo.toString();
			
			//Auditoria
			
			LogsAudit logs = new LogsAudit();
			
			logs.setId_usuario(obuserService.obtenEmpl());
			logs.setTipo_role(obuserService.obtenUser());
			logs.setFecha(SystemDate.obtenFecha());
			logs.setHora(ObtenHour.obtenHour());
			logs.setName_table("VEHICULO TRANSMISION");
			logs.setValor_viejo(valor_nuevo);
			logs.setTipo_accion("INSERT");
			
			logsauditService.save(logs);
		}
		
		return "redirect:/TransmisionVehiculo";
		
	} 

}
