package com.PGJ.SGV.service;

import java.util.List;

import com.PGJ.SGV.models.entity.AsigCombustible;

public interface IAsigComService {

	public List<AsigCombustible> findAll(); 
	
	//public Page<AsigCombustible> findAll(Pageable pageable); 
	
	public List<AsigCombustible> findidVehiculo(long id_vehiculo);  
	
	//public List<AsigCombustible> findPlacaMes(String placa); 
	//public Page<AsigCombustible> findPlacaPage(String placa,Pageable pageable);
	
	public void save(AsigCombustible combustible);
	
	public AsigCombustible findOne(long id_asignacion);
	
	public void delete(long id_asignacion);
	
	public List<AsigCombustible> findidVehiculoMensual(long id_vehiculo,String mes);
	
}
