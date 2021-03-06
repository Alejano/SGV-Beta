package com.PGJ.SGV.service;

import java.util.List;

import com.PGJ.SGV.models.entity.VehiculoEstado;


public interface IEstadoVehiculoService {
	
	public void save(VehiculoEstado estadovehiculo);
	
	public VehiculoEstado findOne(Long id_estadovehiculo);
	
	public VehiculoEstado findbyPlaca(String placa);
	
	public VehiculoEstado findbyVehiculo(Long id_vehiculo);
	
	public VehiculoEstado findall();
	
	public List<VehiculoEstado> findEstados();
	
	public List<VehiculoEstado> findEstadoBaja();
	
	}
