package com.PGJ.SGV.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.PGJ.SGV.models.entity.Adscripcion;
import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.service.IAdscripcionService;
import com.PGJ.SGV.service.ILogsAuditService;

@Controller
public class LogsAuditController {
	
    List<LogsAudit> logsaudit = new ArrayList<LogsAudit>();
	
	@Autowired
	private ILogsAuditService logsauditService;
	
	
	@RequestMapping(value="/Seguridad", method = RequestMethod.GET)
	public String listar(Model model) {
		logsaudit = logsauditService.findAll();
		
		if(logsauditService.logstotales()>= 5) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};	
		
		model.addAttribute("titulo","Seguridad");
		model.addAttribute("logs",logsaudit);
		
		return "LogsAudit";
	}
	
}
