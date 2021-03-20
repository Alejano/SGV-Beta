package com.PGJ.SGV.service;

import com.PGJ.SGV.models.entity.VehiculoFuncion;;

public interface IFuncionVehiculoService {

    public void save(VehiculoFuncion funcionvehiculo);
	
	public VehiculoFuncion findOne(Long id_funcion);
			
	public VehiculoFuncion findAll();
	
}
