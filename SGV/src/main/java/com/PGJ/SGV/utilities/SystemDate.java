package com.PGJ.SGV.utilities;

import java.util.Calendar;

public class SystemDate {
	   
	public static String obtenFecha() {

		Calendar calen = Calendar.getInstance();
		String fecha = null;
		   
		fecha = Integer.toString(calen.get(Calendar.YEAR));
	   
	     if(calen.get(Calendar.MONTH)<9){
	         fecha = fecha + "-" + "0" + Integer.toString(calen.get(Calendar.MONTH)+1);
	     }else
	         fecha = fecha + "-" + Integer.toString(calen.get(Calendar.MONTH)+1);
	   
	     if(calen.get(Calendar.DAY_OF_MONTH)<10){
	         fecha = fecha + "-" + "0" + (Integer.toString(calen.get(Calendar.DAY_OF_MONTH)));
	     }else
	         fecha = fecha + "-" + Integer.toString(calen.get(Calendar.DAY_OF_MONTH));
	   
	     System.err.println(fecha);
	     return fecha;
	   }
	
	public static int obtenAnio() {

		Calendar calen = Calendar.getInstance();
		int anio;
		anio = calen.get(Calendar.YEAR);
	     return anio;
	   }
	
	public static String obtenFechaIni() {

		Calendar calen = Calendar.getInstance();
		String fecha = null;
		   
		fecha = Integer.toString(calen.get(Calendar.YEAR));
	   
	     if(calen.get(Calendar.MONTH)<9){
	         fecha = fecha + "-" + "0" + Integer.toString(calen.get(Calendar.MONTH)+1) + "-" + "01" ;
	     }else
	         fecha = fecha + "-" + Integer.toString(calen.get(Calendar.MONTH)+1) + "-" + "01" ;

	     //System.err.println(fecha);
	     return fecha;
	   }
	
	public static String obtenFechaFin() {

		Calendar calen = Calendar.getInstance();
		String fecha = null;
		String tmaxday= null; 
				
		fecha = Integer.toString(calen.get(Calendar.YEAR));
		tmaxday = Integer.toString(ObtenMonth.numeroDeDiasMes(ObtenMonth.obtenMes(), SystemDate.obtenAnio()));
	   
	     if(calen.get(Calendar.MONTH)<9){
	         fecha = fecha + "-" + "0" + Integer.toString(calen.get(Calendar.MONTH)+1) + "-" + tmaxday;
	     }else
	         fecha = fecha + "-" + Integer.toString(calen.get(Calendar.MONTH)+1) + "-" + tmaxday;
	     
	     //System.err.println("FECHA FIN: "+fecha);
	     return fecha;
	   }

}
