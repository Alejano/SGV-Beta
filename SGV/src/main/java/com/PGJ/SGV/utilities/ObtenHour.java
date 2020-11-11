package com.PGJ.SGV.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ObtenHour {
	
	public static String obtenHour() {

        Date date= new Date();
		
         SimpleDateFormat hora = new SimpleDateFormat("hh:mm:ss");
	     System.err.println(hora.format(date));
	     return hora.format(date);
	   }

}


