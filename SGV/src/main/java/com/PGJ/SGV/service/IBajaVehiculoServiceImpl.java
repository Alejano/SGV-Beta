package com.PGJ.SGV.service;

import com.PGJ.SGV.models.entity.BajaVehiculo;

public interface IBajaVehiculoServiceImpl {

	public void baja(BajaVehiculo bajavehiculo);
	
	public BajaVehiculo findOne(Long id_baja_vehiculo);
	
	public BajaVehiculo findBaja(Long id_vehiculo);
	
	public String findActaFNQ(Long id_vehiculo);
	
	public String findOficio(Long id_vehiculo);
	
	public String findDictamen(Long id_vehiculo);
}
