package com.PGJ.SGV.controllers;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.PGJ.SGV.models.entity.Adscripcion;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.service.IAdscripcionService;
import com.PGJ.SGV.service.IMantenimientoService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.service.IViajeService;

@Controller
public class DashboardController {
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IAdscripcionService adscripcionService;
	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private IViajeService viajeService;
	@Autowired
	private IMantenimientoService mantenimientoService;
 
	
	@RequestMapping(value="/Dashboard", method = RequestMethod.GET)
	public String listar(Model model, Authentication authentication) {	
		Month fechaActual = LocalDate.now().getMonth();
		Calendar now = Calendar.getInstance();
		int añoActual = LocalDate.now().getYear();
		List<String> meses = new ArrayList<String>();
		List<String> mesesNume = new ArrayList<String>();
		List<Integer> años = new ArrayList<Integer>();
		String mes = fechaActual.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));		
		String[] strDays = new String[]{"Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
		String Dia = strDays[now.get(Calendar.DAY_OF_WEEK) - 1];
		List<Adscripcion> adscripciones = new ArrayList<Adscripcion>();
		List<Datos> DatosGenerales = new ArrayList<Datos>();
		adscripciones = adscripcionService.findAll();
		int numeromaximo=0;		
		if(mes == "diciembre") {añoActual+=1;}
		for(int i = 0;i<=11;i++) {			
			meses.add(mes);			
			switch (mes) {
				case "enero": mes = "diciembre";mesesNume.add("01"); break;
				case "febrero": mes = "enero";mesesNume.add("02");break;
				case "marzo": mes = "febrero";mesesNume.add("03");break;
				case "abril": mes = "marzo";mesesNume.add("04");break;
				case "mayo": mes = "abril";mesesNume.add("05");break;
				case "junio": mes = "mayo";mesesNume.add("06");break;
				case "julio": mes = "junio";mesesNume.add("07");break;
				case "agosto": mes = "julio";mesesNume.add("08");break;
				case "septiembre": mes = "agosto";mesesNume.add("09");break;
				case "octubre": mes = "septiembre";mesesNume.add("10");break;
				case "noviembre": mes = "octubre";mesesNume.add("11");break;
				case "diciembre": añoActual-=1;mes = "noviembre";mesesNume.add("12");break;
			
			}
			años.add(añoActual);
		}				
						
		for(Adscripcion ads:adscripciones) {
			if(numeromaximo <= 5) {
			Datos D=new Datos();
			D.setAdscripcion(ads.getId_adscripcion());
			D.setNombre_adscripcion(ads.getNombre_adscripcion());
			D.setVehiculos(vehiculoService.totalVehiculoArea(ads.getId_adscripcion()));
			//D.setViajes(viajeService.TotalViajesArea(ads.getId_adscripcion()));
			D.setMantenimientos(mantenimientoService.TotalMantenimientoArea(ads.getId_adscripcion()));
			DatosGenerales.add(D);
			numeromaximo++;
			}			
		}
		numeromaximo=0;
				
		
		String usuario = authentication.getName();
		Usuario usus = new Usuario();
		usus = usuarioService.findbyAdscripcion(usuario);		
		model.addAttribute("dataSemana", obtenerdataSemana(strDays, Dia,now));
		model.addAttribute("dataMes", obtenerdataMes(meses,años,mesesNume));
		model.addAttribute("DatosGenerales",DatosGenerales);	
		model.addAttribute("Usuario",usus.getNombre()+" "+usus.getApellido1());				
		return "Dashboard";
	}
	
	public List<Map<Object,Object>> obtenerdataSemana(String[] strDays, String Dia,Calendar fecha){
		Map<Object,Object> map = null;
		 List<List<Map<Object,Object>>> list = new ArrayList<List<Map<Object,Object>>>();
		 List<Map<Object,Object>> dataPoints1 = new ArrayList<Map<Object,Object>>();
		 switch (Dia) {
			case "Lunes":     strDays = new String[]{"Lunes","Domingo","Sabado","Viernes","Jueves","Miercoles","Martes"};break;
			case "Martes":    strDays = new String[]{"Martes","Lunes","Domingo","Sabado","Viernes","Jueves","Miercoles"};break;
			case "Miercoles": strDays = new String[]{"Miercoles","Martes","Lunes","Domingo","Sabado","Viernes","Jueves"};break;
			case "Jueves":    strDays = new String[]{"jueves","Miercoles","Martes","Lunes","Domingo","Sabado","Viernes"};break;
			case "Viernes":   strDays = new String[]{"viernes","jueves","Miercoles","Martes","Lunes","Domingo","Sabado"};break;
			case "Sabado":    strDays = new String[]{"sabado","viernes","jueves","Miercoles","Martes","Lunes","Domingo"};break;
			case "Domingo":   strDays = new String[]{"domingo","sabado","viernes","jueves","Miercoles","Martes","Lunes"};break;
			}		
		
		//map = new HashMap<Object,Object>(); map.put("label", strDays[6]); map.put("y", viajeService.TotalViajesxDia(fecha.get(Calendar.DATE)-6,fecha.get(Calendar.MONTH)+1));dataPoints1.add(map);
		//map = new HashMap<Object,Object>(); map.put("label", strDays[5]); map.put("y", viajeService.TotalViajesxDia(fecha.get(Calendar.DATE)-5,fecha.get(Calendar.MONTH)+1));dataPoints1.add(map);
		//map = new HashMap<Object,Object>(); map.put("label", strDays[4]); map.put("y", viajeService.TotalViajesxDia(fecha.get(Calendar.DATE)-4,fecha.get(Calendar.MONTH)+1));dataPoints1.add(map);
		//map = new HashMap<Object,Object>(); map.put("label", strDays[3]); map.put("y", viajeService.TotalViajesxDia(fecha.get(Calendar.DATE)-3,fecha.get(Calendar.MONTH)+1));dataPoints1.add(map);
		//map = new HashMap<Object,Object>(); map.put("label", strDays[2]); map.put("y", viajeService.TotalViajesxDia(fecha.get(Calendar.DATE)-2,fecha.get(Calendar.MONTH)+1));dataPoints1.add(map);
		//map = new HashMap<Object,Object>(); map.put("label", strDays[1]); map.put("y", viajeService.TotalViajesxDia(fecha.get(Calendar.DATE)-1,fecha.get(Calendar.MONTH)+1));dataPoints1.add(map);
		//map = new HashMap<Object,Object>(); map.put("label", strDays[0]); map.put("y", viajeService.TotalViajesxDia(fecha.get(Calendar.DATE),fecha.get(Calendar.MONTH)+1));dataPoints1.add(map);	
 
		list.add(dataPoints1);
		
		return dataPoints1;
	}
	
	public List<Map<Object,Object>> obtenerdataMes(List<String>meses,List<Integer>años,List<String>mesesNum){
		Map<Object,Object> map = null;
		 List<List<Map<Object,Object>>> list = new ArrayList<List<Map<Object,Object>>>();
		 List<Map<Object,Object>> dataPoints1 = new ArrayList<Map<Object,Object>>();		
		
		/*map = new HashMap<Object,Object>(); map.put("label", meses.get(11)); map.put("y",viajeService.TotalViajesMensual(años.get(11)+"-"+mesesNum.get(11)));dataPoints1.add(map);
		map = new HashMap<Object,Object>(); map.put("label", meses.get(10)); map.put("y", viajeService.TotalViajesMensual(años.get(10)+"-"+mesesNum.get(10)));dataPoints1.add(map);
		map = new HashMap<Object,Object>(); map.put("label", meses.get(9)); map.put("y", viajeService.TotalViajesMensual(años.get(9)+"-"+mesesNum.get(9)));dataPoints1.add(map);
		map = new HashMap<Object,Object>(); map.put("label", meses.get(8)); map.put("y", viajeService.TotalViajesMensual(años.get(8)+"-"+mesesNum.get(8)));dataPoints1.add(map);
		map = new HashMap<Object,Object>(); map.put("label", meses.get(7)); map.put("y", viajeService.TotalViajesMensual(años.get(7)+"-"+mesesNum.get(7)));dataPoints1.add(map);
		map = new HashMap<Object,Object>(); map.put("label", meses.get(6)); map.put("y", viajeService.TotalViajesMensual(años.get(6)+"-"+mesesNum.get(6)));dataPoints1.add(map);
		map = new HashMap<Object,Object>(); map.put("label", meses.get(5)); map.put("y", viajeService.TotalViajesMensual(años.get(5)+"-"+mesesNum.get(5)));dataPoints1.add(map);
		map = new HashMap<Object,Object>(); map.put("label", meses.get(4)); map.put("y", viajeService.TotalViajesMensual(años.get(4)+"-"+mesesNum.get(4)));dataPoints1.add(map);
		map = new HashMap<Object,Object>(); map.put("label", meses.get(3)); map.put("y", viajeService.TotalViajesMensual(años.get(3)+"-"+mesesNum.get(3)));dataPoints1.add(map);
		map = new HashMap<Object,Object>(); map.put("label", meses.get(2)); map.put("y", viajeService.TotalViajesMensual(años.get(2)+"-"+mesesNum.get(2)));dataPoints1.add(map);
		map = new HashMap<Object,Object>(); map.put("label", meses.get(1)); map.put("y", viajeService.TotalViajesMensual(años.get(1)+"-"+mesesNum.get(1)));dataPoints1.add(map);
		map = new HashMap<Object,Object>(); map.put("label", meses.get(0)); map.put("y", viajeService.TotalViajesMensual(años.get(0)+"-"+mesesNum.get(0)));dataPoints1.add(map);
		*/
 
		list.add(dataPoints1);
		
		return dataPoints1;
	}
	
	public class Datos{
		private Long Adscripcion;
		private String nombre_adscripcion;
		private int vehiculos;
		private int viajes;
		private int Mantenimientos;
		public Long getAdscripcion() {
			return Adscripcion;
		}
		public void setAdscripcion(Long adscripcion) {
			Adscripcion = adscripcion;
		}
		public int getVehiculos() {
			return vehiculos;
		}
		public void setVehiculos(int vehiculos) {
			this.vehiculos = vehiculos;
		}
		public int getViajes() {
			return viajes;
		}
		public void setViajes(int viajes) {
			this.viajes = viajes;
		}
		public int getMantenimientos() {
			return Mantenimientos;
		}
		public void setMantenimientos(int mantenimientos) {
			Mantenimientos = mantenimientos;
		}
		public String getNombre_adscripcion() {
			return nombre_adscripcion;
		}
		public void setNombre_adscripcion(String nombre_adscripcion) {
			this.nombre_adscripcion = nombre_adscripcion;
		}	
						
		
	}
		
	
}
