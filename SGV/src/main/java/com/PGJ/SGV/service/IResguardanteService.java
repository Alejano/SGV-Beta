package com.PGJ.SGV.service;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.PGJ.SGV.models.entity.Resguardante;

import net.sf.jasperreports.engine.JRException;


public interface IResguardanteService {
	
		public List<Resguardante> findAll(); 
		
		public Page<Resguardante> findAllByVehiculo(long id_vehiculo, Pageable pageable);
	    
	    public Page<Resguardante> findAll(Pageable pageable);
		
		public void save(Resguardante resguardante);
		
		public Resguardante findOne(Long id);
		
		public void delete(Long id);
		
		public int ultimoId();
		
		//public List<Resguardante> findActivos();
		
		public List<Resguardante> findActivos(Long id_vehiculo);
		
		public List<Resguardante> findResg(Long id_vehiculo);
		
		public Long resguardantestotales();
		
		public Long resguardantespvtotales(Long id_vehiculo);
		
		public  List<Resguardante> findResgAll1(Long id_adscripcion);
		
		public  List<Integer> findResgIds();
		
		public  List<Resguardante> findResgInd(Long id_resguardante);
		
		public String exportReport(String format) throws FileNotFoundException, JRException;


}
