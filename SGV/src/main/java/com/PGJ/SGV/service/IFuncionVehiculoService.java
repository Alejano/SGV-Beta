package com.PGJ.SGV.service;

import java.util.List;

import com.PGJ.SGV.models.entity.VehiculoFuncion;

public interface IFuncionVehiculoService {

    public void save(VehiculoFuncion funcionvehiculo);
	
	public VehiculoFuncion findOne(Long id_funcion);
			
	public List<VehiculoFuncion> findAll();
		
	public Long funcionestotales();
	
	public int ultimoId();
	
	
}
