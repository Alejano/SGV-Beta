package com.PGJ.SGV.service;

import java.util.List;

import com.PGJ.SGV.models.entity.VehiculoMarca;

public interface IMarcaVehiculoService {

    public void save(VehiculoMarca marcavehiculo);
	
	public VehiculoMarca findOne(Long id_marca);
			
	public List<VehiculoMarca> findAll(); 
	
	public Long marcastotales();
	
	public int ultimoId();
	

}
