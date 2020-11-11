package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.PGJ.SGV.models.entity.Resguardante;


public interface IResguardanteService {
		public List<Resguardante> findAll(); 
		
		public Page<Resguardante> findAllByVehiculo(long id_vehiculo, Pageable pageable);
	    
	    public Page<Resguardante> findAll(Pageable pageable);
		
		public void save(Resguardante resguardante);
		
		public Resguardante findOne(Long id);
		
		public void delete(Long id);
		
		public int ultimoId();
		
		public List<Resguardante> findActivos();
		
		public List<Resguardante> findResg(Long id_vehiculo);

		
		//update
		
		
}
