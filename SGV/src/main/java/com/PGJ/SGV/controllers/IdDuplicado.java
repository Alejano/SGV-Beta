package com.PGJ.SGV.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IdDuplicado {
	
	
	
	@RequestMapping(value="/idDuplicadoAds/{id_adscripcion}")
	public String idAdscripcion (@PathVariable(value="id_adscripcion")String id_adscripcion,Model model) {
		
		model.addAttribute("titulo","Lo sentimos pero la adcrispcion con el ID: ");
		model.addAttribute("cuerpo",id_adscripcion);
		model.addAttribute("complemento","Ya se encuentra asignada a otra unidad, prueba con otra.");
		model.addAttribute("URL","/formAds");
		return "idDuplicado";
	}
	
	
	@RequestMapping(value="/idDuplicadoUsu/{no_empleado}")
	public String idUsuario (@PathVariable(value="no_empleado")String no_empleado,Model model) {
		
		model.addAttribute("titulo","Lo sentimos pero el numero de empleado: ");
		model.addAttribute("cuerpo",no_empleado);
		model.addAttribute("complemento","Ya se encuentra asignado a otra persona, prueba con otra.");
		model.addAttribute("URL","/formUsu");
		return "idDuplicado";
	}
	
	@RequestMapping(value="/idDuplicadoVehi/{placa}")
	public String idVehiculo (@PathVariable(value="placa")String placa,Model model) {
		
		model.addAttribute("titulo","Lo sentimos pero la placa: ");
		model.addAttribute("cuerpo",placa);
		model.addAttribute("complemento","Ya se encuentra asignada a otra unidad, prueba con otra.");
		model.addAttribute("URL","/formVehi");
		return "idDuplicado";
	}
	@RequestMapping(value="/idDuplicadoUsuCrea/{no_empleado}/{empleado}")
	public String idCreateUsu (@PathVariable(value="no_empleado")String no_empleado,@PathVariable(value="empleado")String empleado,Model model) {
		
		model.addAttribute("titulo","Lo sentimos pero el numero de empleado");
		model.addAttribute("cuerpo",no_empleado);
		model.addAttribute("complemento","Debe ser el mismo numero de empleado para poder editar");
		model.addAttribute("URL","/formUsu/"+empleado);
		return "idDuplicado";
	}
	
	@RequestMapping(value="/idDuplicadoCon/{no_empleado}")
	public String idCreateCon (@PathVariable(value="no_empleado")String no_empleado,Model model) {
		
		model.addAttribute("titulo","Lo sentimos pero el numero de empleado del conductor");
		model.addAttribute("cuerpo",no_empleado);
		model.addAttribute("complemento","Ya se encuentra asignado a otra persona, prueba con otra.");	
		model.addAttribute("URL","/FormCond");
		return "idDuplicado";
	}
	
	@RequestMapping(value="/idDuplicadoAdsCrea/{no_adscripcion}/{ID}")
	public String idCreateAds (@PathVariable(value="no_adscripcion")String no_adscripcion,@PathVariable(value="ID")Long ID,Model model) {
		
		model.addAttribute("titulo","Lo sentimos pero el ID de la Adscripcion:");
		model.addAttribute("cuerpo",no_adscripcion);
		model.addAttribute("complemento","Debe ser el mismo numero de Adscripcion para poder editar");
		model.addAttribute("URL","/formAds/"+ID);
		return "idDuplicado";
	}
	
	@RequestMapping(value="/idDuplicadoVehiCrea/{no_vehiculo}/{coche}")
	public String idCreateVehiculo (@PathVariable(value="no_vehiculo")String no_vehiculo,@PathVariable(value="coche")String coche,Model model) {
		
		model.addAttribute("titulo","Lo sentimos pero La placa del vehiculo:");
		model.addAttribute("cuerpo",no_vehiculo);
		model.addAttribute("complemento","Debe ser la misma placa de empleado para poder editar");
		model.addAttribute("URL","/formVehi/"+coche);
		return "idDuplicado";
	}
	
	@RequestMapping(value="/idDuplicadoConCrea/{no_empleado}/{empleado}")
	public String idCreateConductor (@PathVariable(value="no_empleado")String no_empleado,@PathVariable(value="empleado")String empleado,Model model) {
		
		model.addAttribute("titulo","Lo sentimos:");
		model.addAttribute("cuerpo",no_empleado);
		model.addAttribute("complemento","Debe ser el mismo numero de empleado para poder editar");
		model.addAttribute("URL","/FormCond/"+empleado);
		return "idDuplicado";
	}


	

}
