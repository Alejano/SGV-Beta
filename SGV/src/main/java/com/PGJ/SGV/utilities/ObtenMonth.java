package com.PGJ.SGV.utilities;

import java.util.Calendar;

public class ObtenMonth {
	
	public static String obtenMes() {

		Calendar calen = Calendar.getInstance();
		int mes = 0;
		String name;
		
		mes = calen.get(Calendar.MONTH)+1;
	       
	      System.out.println(mes);
	   
	      switch (mes){
	       case 1: name="Enero";
	               break;
	       case 2: name="Febrero";
	               break;
	       case 3: name="Marzo";
	               break;
	       case 4: name="Abril";
	               break;
	       case 5: name="Mayo";
	               break;
	       case 6: name="Junio";
	               break;
	       case 7: name="Julio";
	               break;
	       case 8: name="Agosto";
	               break;
	       case 9: name="Septiembre";
	               break;
	       case 10: name="Octubre";
	               break;
	       case 11: name="Noviembre";
	               break;
	       case 12: name="Diciembre";
	               break;
	       default: name = "Mes inválido";
	       break;
	       
	   }
	   
	     System.err.println(name);
	     return name;
	   }
	
	public static String obtenNumMes() {
		Calendar calen = Calendar.getInstance();
		int mes = 0;
		String name;
		
		mes = calen.get(Calendar.MONTH)+1;
	       	      
	   
	      switch (mes){
	       case 1: name="01";
	               break;
	       case 2: name="02";
	               break;
	       case 3: name="03";
	               break;
	       case 4: name="04";
	               break;
	       case 5: name="05";
	               break;
	       case 6: name="06";
	               break;
	       case 7: name="07";
	               break;
	       case 8: name="08";
	               break;
	       case 9: name="09";
	               break;
	       case 10: name="10";
	               break;
	       case 11: name="22";
	               break;
	       case 12: name="12";
	               break;
	       default: name = "Mes inválido";
	       break;
	       
	   }
	   	    
	     return name;
	   }

}
