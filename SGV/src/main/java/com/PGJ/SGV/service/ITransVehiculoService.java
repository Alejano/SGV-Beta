package com.PGJ.SGV.service;

import java.util.List;

import com.PGJ.SGV.models.entity.VehiculoTransmision;

public interface ITransVehiculoService {
	
    public void save(VehiculoTransmision transmisionvehiculo);
	
	public VehiculoTransmision findOne(Long id_transmision);
				
	public List<VehiculoTransmision> findAll();
	
	public Long transmisionestotales();
	
	public int ultimoId();
	
}
