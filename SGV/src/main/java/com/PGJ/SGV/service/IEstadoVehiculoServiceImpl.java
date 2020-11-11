package com.PGJ.SGV.service;

import com.PGJ.SGV.models.entity.VehiculoEstado;

public interface IEstadoVehiculoServiceImpl {
	
	public void save(VehiculoEstado estadovehiculo);
	
	public VehiculoEstado findOne(Long id_estadovehiculo);
	
	public VehiculoEstado findbyPlaca(String placa);
	
	public VehiculoEstado findall();

}
