package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.PGJ.SGV.models.entity.VehiculoPlacas;

public interface IVehiculoPlacasService {

    public List<VehiculoPlacas> findAll(); 
	
	public Page<VehiculoPlacas> findAll(Pageable pageable); 
	
	public Page<VehiculoPlacas> findPlacas(Long id_vehiculo,Pageable pageable);
	
	public List<VehiculoPlacas> findPlaca(Long id_vehiculo); 
	
	public List<VehiculoPlacas> findPlacaAnt(Long id_vehiculo);

	
}
