package com.PGJ.SGV.service;

import com.PGJ.SGV.models.entity.VehiculoTransmision;

public interface ITransVehiculoService {
	
    public void save(VehiculoTransmision transmisionvehiculo);
	
	public VehiculoTransmision findOne(Long id_transmision);
			
	public VehiculoTransmision findAll();

}
