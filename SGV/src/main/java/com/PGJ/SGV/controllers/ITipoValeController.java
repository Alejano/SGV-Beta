package com.PGJ.SGV.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.PGJ.SGV.models.entity.Aseguradora;
import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.models.entity.TipoVale;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.IObtenerUserService;
import com.PGJ.SGV.service.ITipoValeService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.util.paginador.PageRender;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;

@Controller
public class ITipoValeController {
	
	@Autowired
	private ITipoValeService tipovaleService;
	@Autowired
	private IObtenerUserService obuserService;
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private ILogsAuditService logsauditService;

	List<TipoVale> tipovale = new ArrayList<TipoVale>();

    String user;
	boolean editar = false;
	Long id_vale;
	Double month29;

	@RequestMapping(value="/ListarVales", method = RequestMethod.GET)
	public String listar(Model model) {
		
		user = obuserService.obtenUser();
		model.addAttribute("usuario",user);	
		tipovale =  tipovaleService.findAll();	
	    if(tipovaleService.tipovalestotales()>= 5) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};	
		model.addAttribute("PageTitulo", "Tipo de Vale");
        model.addAttribute("PageSubTitulo", "Listado de Tipos de Vales");
		model.addAttribute("vales",tipovale);
		return "TipoVale";

	}
	
	@RequestMapping(value="/formTipoVale")
	public String crear(Map<String,Object> model) {	
		
		editar=false;
		user = obuserService.obtenUser();
		model.put("usuario",user);	
		TipoVale tipovale = new TipoVale();
		model.put("editar", editar);
		model.put("vales", tipovale);
		model.put("PageTitulo", "Agregar Tipo de Vale");
		model.put("editando", "no");
		
		return "formVale";
		
	}
	
	@RequestMapping(value="/formTipoVale/{id_vale}")
	public String editar(@PathVariable(value="id_vale") Long id_vale,Map<String,Object>model) {
		
		editar = true;
		user = obuserService.obtenUser();
		model.put("usuario",user);
		TipoVale tipovale = null;

		if(id_vale != 0) {
			tipovale = tipovaleService.findOne(id_vale);			
			id_vale = tipovale.getId_vale();
			}else {
				return "redirect:/ListarVales";
				}
		
		id_vale = tipovale.getId_vale();
		month29 = tipovale.getMonth_29();
		
		System.err.println("chiana editar"+id_vale);
		
		model.put("vales",tipovale);
		model.put("titulo", "Editar cliente");
		model.put("PageTitulo", "Editar Vale");
		model.put("editando", "si");
		
		return "formVale";
		
	}
	
	@RequestMapping(value="/estadoTipoVale/{id_vale}/{status}")
	public String estado (@PathVariable(value="id_vale")Long id_vale,@PathVariable(value="status")boolean enabled) {
		
		TipoVale tipovale = null;
		boolean seteo = false;
		tipovale = tipovaleService.findOne(id_vale);	
		
		if(enabled) {
			seteo=false;
			tipovale.setStatus(seteo);
			}else {
				seteo=true;
				tipovale.setStatus(seteo);	
				}
		
		TipoVale tipovale_old = null;
		tipovale_old = tipovaleService.findOne(tipovale.getId_vale());
	    String valor_old = tipovale_old.toString();
	    tipovaleService.save(tipovale);	
	
		//Auditoria
		
     	LogsAudit logs = new LogsAudit();
     	
     	logs.setId_usuario(obuserService.obtenEmpl());
 		logs.setTipo_role(obuserService.obtenUser());
		logs.setFecha(SystemDate.obtenFecha());
		logs.setHora(ObtenHour.obtenHour());
		logs.setName_table("TIPO VALE");
		logs.setValor_viejo(valor_old);
		logs.setTipo_accion("UPDATE ESTADO");
								
		logsauditService.save(logs);
		editar = false;		
	    return "redirect:/ListarVales";
	    
	}
	
	
	@RequestMapping(value="/VerTipoVale/{id_vale}")
	public String ver(@PathVariable(value="id_vale") Long id_vale,Map<String,Object>model) {
		
		editar = true;
		TipoVale tipovale = null;
		
		if(id_vale != 0) {
			tipovale = tipovaleService.findOne(id_vale);			
			id_vale = tipovale.getId_vale();
			}else {
				return "redirect:/ListarVales";
				}
		
		model.put("editar", editar);
		model.put("vales",tipovale);
		model.put("titulo", "Editar cliente");
		model.put("PageTitulo", "Información Tipo Vale");
		return "formVale";
		
	}
	
	
	@RequestMapping(value="/formVale",method = RequestMethod.POST)
	public String guardar(TipoVale tipovale) {	
		
		if(editar == true) {
	
			//TipoVale OLD
			
			TipoVale tipovale_old = null;
			tipovale_old = tipovaleService.findOne(tipovale.getId_vale());
		    String valor_old = tipovale_old.toString();
		    tipovaleService.save(tipovale);	
		    

			//Auditoria
			
	     	LogsAudit logs = new LogsAudit();
	     	
	     	logs.setId_usuario(obuserService.obtenEmpl());
	 		logs.setTipo_role(obuserService.obtenUser());
			logs.setFecha(SystemDate.obtenFecha());
			logs.setHora(ObtenHour.obtenHour());
			logs.setName_table("TIPO VALE");
			logs.setValor_viejo(valor_old);
			logs.setTipo_accion("UPDATE");
									
			logsauditService.save(logs);
		    
				
		}else {
		
		tipovaleService.save(tipovale);
		String valor_nuevo=tipovale.toString();
		
		//Auditoria
		
     	LogsAudit logs = new LogsAudit();
     	
     	logs.setId_usuario(obuserService.obtenEmpl());
 		logs.setTipo_role(obuserService.obtenUser());
		logs.setFecha(SystemDate.obtenFecha());
		logs.setHora(ObtenHour.obtenHour());
		logs.setName_table("TIPO VALE");
		logs.setValor_viejo(valor_nuevo);
		logs.setTipo_accion("INSERT");
								
		logsauditService.save(logs);
		
		}
		
		return "redirect:ListarVales";
	}

}
