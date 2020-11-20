package com.PGJ.SGV.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IObtenerUserService;

@Controller
public class LogsAuditController {
	
    List<LogsAudit> logsaudit = new ArrayList<LogsAudit>();
	
	@Autowired
	private ILogsAuditService logsauditService;
	@Autowired
	private IObtenerUserService obuserService;
	
	String user;
	
	@RequestMapping(value="/Seguridad", method = RequestMethod.GET)
	public String listar(Model model) {
		
		user = obuserService.obtenUser();
		logsaudit = logsauditService.findAll();
		if(logsauditService.logstotales()>= 5) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};	
		
		model.addAttribute("PageTitulo", "Seguridad");
		 model.addAttribute("PageSubTitulo", "Listado de Seguridad");
		model.addAttribute("titulo","Seguridad");
		model.addAttribute("logs",logsaudit);
		model.addAttribute("usuario",user);
		
		return "LogsAudit";
		
	}
	
}
