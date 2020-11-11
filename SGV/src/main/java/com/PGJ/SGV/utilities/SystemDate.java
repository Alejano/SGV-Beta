package com.PGJ.SGV.utilities;

import java.util.Calendar;

public class SystemDate {
	   
	public static String obtenFecha() {

		Calendar calen = Calendar.getInstance();
		String fecha = null;
		   
		fecha = Integer.toString(calen.get(Calendar.YEAR));
	   
	     if(calen.get(Calendar.MONTH)<10){
	         fecha = fecha + "-" + "0" + (Integer.toString((calen.get(Calendar.MONTH))+1));
	     }else
	         fecha = fecha + "-" + Integer.toString(calen.get(Calendar.MONTH)+1);
	   
	     if(calen.get(Calendar.DAY_OF_MONTH)<10){
	         fecha = fecha + "-" + "0" + (Integer.toString(calen.get(Calendar.DAY_OF_MONTH)));
	     }else
	         fecha = fecha + "-" + Integer.toString(calen.get(Calendar.DAY_OF_MONTH));
	   
	     System.err.println(fecha);
	     return fecha;
	   }

}
