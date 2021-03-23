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
import com.PGJ.SGV.models.entity.VehiculoFuncion;
import com.PGJ.SGV.service.IFuncionVehiculoService;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;

@Controller
public class FuncionController {
	
List<VehiculoFuncion> funciones = new ArrayList<VehiculoFuncion>();
	
	@Autowired
	private IFuncionVehiculoService funcionService;
	@Autowired
	private ILogsAuditService logsauditService;
	@Autowired
	private IObtenerUserService obuserService;
	
	boolean editar = false;
	String user;
	Long ID;
	
	@RequestMapping(value="/FuncionesVehiculo", method = RequestMethod.GET)
	public String listar(Model model) {
		
		user = obuserService.obtenUser();
		funciones = funcionService.findAll();
		if(funcionService.funcionestotales()>= 4) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};	
		model.addAttribute("PageTitulo", "Funciones Vehiculo");
		model.addAttribute("PageSubTitulo","Listado de Funciones Vehiculo");
		model.addAttribute("titulo","Listado de Funciones Vehiculo");
		model.addAttribute("funciones",funciones);
		model.addAttribute("usuario",user);	
		return "VehiculoFunciones";
		
	}
		
	
	@RequestMapping(value="/formFuncion/Ag")
	public String crear(Map<String,Object> model) {
		
		VehiculoFuncion funcionvehiculo = new VehiculoFuncion();
	
		long id_funcion = funcionService.ultimoId() + 1;
		funcionvehiculo.setId_funcion(id_funcion);
				
		model.put("funcion", funcionvehiculo);
		model.put("id", id_funcion);
		model.put("usuario",obuserService.obtenUser());
		model.put("PageTitulo", "Agregar Funcion Vehiculo");
		model.put("titulo", "Formulario de Funcion Vehiculo");
		
		return "formFuncion";
	}
	
	
	@RequestMapping(value="/formFuncion/{id_funcion}")
	public String editar(@PathVariable(value="id_funcion") Long id_funcion,Map<String,Object>model) {
		editar = true;
		
		VehiculoFuncion funcionvehiculo = null;
		
		if(id_funcion != 0) {
			funcionvehiculo = funcionService.findOne(id_funcion);
			ID = funcionvehiculo.getId_funcion();
		}else {
			return "redirect:/VehiculoFunciones";
		}
				
		model.put("funcion",funcionvehiculo);
		model.put("PageTitulo", "Editar Funcion Vehiculo");
		model.put("titulo", "Editar Funcion Vehiculo");
		model.put("PageTitulo", "Editar Funcion Vehiculo");
		return "formFuncion";
		
	}
	
	
	@RequestMapping(value="/formFuncion",method = RequestMethod.POST)
	public String guardar(VehiculoFuncion funcionvehiculo){
				

		if(editar == true) {
		
		//Marca OLD
	   
	    VehiculoFuncion funcionvehiculo_old = null;
	    funcionvehiculo_old = funcionService.findOne(funcionvehiculo.getId_funcion());
	    String valor_old = funcionvehiculo_old.toString();
	    funcionService.save(funcionvehiculo);			
		editar = false;	
		
		//Auditoria
		
     	LogsAudit logs = new LogsAudit();
     	
        logs.setId_usuario(obuserService.obtenEmpl());
		logs.setTipo_role(obuserService.obtenUser());
		logs.setFecha(SystemDate.obtenFecha());
		logs.setHora(ObtenHour.obtenHour());
		logs.setName_table("VEHICULO FUNCION");
		logs.setValor_viejo(valor_old);
		logs.setTipo_accion("UPDATE");
								
		logsauditService.save(logs);
		editar = false;	
		
		}else {
		
			funcionService.save(funcionvehiculo);	
			String valor_nuevo = funcionvehiculo.toString();
			
			//Auditoria
			
			LogsAudit logs = new LogsAudit();
			
			logs.setId_usuario(obuserService.obtenEmpl());
			logs.setTipo_role(obuserService.obtenUser());
			logs.setFecha(SystemDate.obtenFecha());
			logs.setHora(ObtenHour.obtenHour());
			logs.setName_table("VEHICULO FUNCION");
			logs.setValor_viejo(valor_nuevo);
			logs.setTipo_accion("INSERT");
			
			logsauditService.save(logs);
		}
		
		return "redirect:/FuncionesVehiculo";
		
	} 

}
