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
import com.PGJ.SGV.models.entity.VehiculoMarca;
import com.PGJ.SGV.service.IMarcaVehiculoService;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;

@Controller
public class MarcaController {
	
List<VehiculoMarca> marcas = new ArrayList<VehiculoMarca>();
	
	@Autowired
	private IMarcaVehiculoService marcaService;
	@Autowired
	private ILogsAuditService logsauditService;
	@Autowired
	private IObtenerUserService obuserService;
	
	boolean editar = false;
	String user;
	Long ID;
	
	@RequestMapping(value="/MarcasVehiculo", method = RequestMethod.GET)
	public String listar(Model model) {
		

		user = obuserService.obtenUser();
		marcas = marcaService.findAll();
		if(marcaService.marcastotales()>= 4) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};	
		model.addAttribute("PageTitulo", "Marcas Vehiculo");
		model.addAttribute("PageSubTitulo","Listado de Marcas Vehiculo");
		model.addAttribute("titulo","Listado de Marcas Vehiculo");
		model.addAttribute("marcas",marcas);
		model.addAttribute("usuario",user);	
		return "VehiculoMarcas";
		
	}
		
	
	@RequestMapping(value="/formMarca/Ag")
	public String crear(Map<String,Object> model) {
		
		VehiculoMarca marcavehiculo = new VehiculoMarca();
		
		long id_marca = marcaService.ultimoId() + 1;
		marcavehiculo.setId_marca(id_marca);
				
		model.put("marca", marcavehiculo);
		model.put("id", id_marca);
		model.put("usuario",obuserService.obtenUser());
		model.put("PageTitulo", "Agregar Marca Vehiculo");
		model.put("titulo", "Formulario de Marca Vehiculo");
		
		return "formMarca";
	}
	
	
	@RequestMapping(value="/formMarca/{id_marca}")
	public String editar(@PathVariable(value="id_marca") Long id_marca,Map<String,Object>model) {
		editar = true;
		VehiculoMarca marcavehiculo = null;
		
		if(id_marca != 0) {
			marcavehiculo = marcaService.findOne(id_marca);
			ID = marcavehiculo.getId_marca();
		}else {
			return "redirect:/VehiculoMarcas";
		}
				
		model.put("marca",marcavehiculo);
		model.put("PageTitulo", "Editar Marca Vehiculo");
		model.put("titulo", "Editar Marca Vehiculo");
		model.put("PageTitulo", "Editar Marca Vehiculo");
		return "formMarca";
		
	}
	
	
	@RequestMapping(value="/formMarca",method = RequestMethod.POST)
	public String guardar(VehiculoMarca marcavehiculo){
				
		if(editar == true) {
		
		//Marca OLD
	   
	    VehiculoMarca marcavehiculo_old = null;

	    marcavehiculo_old = marcaService.findOne(marcavehiculo.getId_marca());
	    String valor_old = marcavehiculo_old.toString();
	    
	    marcaService.save(marcavehiculo);			
		editar = false;	
		
		//Auditoria
		
     	LogsAudit logs = new LogsAudit();
     	
        logs.setId_usuario(obuserService.obtenEmpl());
		logs.setTipo_role(obuserService.obtenUser());
		logs.setFecha(SystemDate.obtenFecha());
		logs.setHora(ObtenHour.obtenHour());
		logs.setName_table("VEHICULO MARCA");
		logs.setValor_viejo(valor_old);
		logs.setTipo_accion("UPDATE");
								
		logsauditService.save(logs);
		editar = false;	
		
		}else {
			
			marcaService.save(marcavehiculo);	
			String valor_nuevo = marcavehiculo.toString();
			
			//Auditoria
			
			LogsAudit logs = new LogsAudit();
			
			logs.setId_usuario(obuserService.obtenEmpl());
			logs.setTipo_role(obuserService.obtenUser());
			logs.setFecha(SystemDate.obtenFecha());
			logs.setHora(ObtenHour.obtenHour());
			logs.setName_table("VEHICULO MARCA");
			logs.setValor_viejo(valor_nuevo);
			logs.setTipo_accion("INSERT");
			
			logsauditService.save(logs);
		}
		
		return "redirect:/MarcasVehiculo";
		
	} 


}
