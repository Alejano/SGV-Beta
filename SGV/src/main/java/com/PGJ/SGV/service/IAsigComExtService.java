package com.PGJ.SGV.service;

import java.util.List;


import com.PGJ.SGV.models.entity.AsigCombustibleExt;

public interface IAsigComExtService {

	
	public List<AsigCombustibleExt> findAll(); 
	
	
	public List<AsigCombustibleExt> findAsigVehi(long id_vehiculo); 
	
	public List<AsigCombustibleExt> findextId(long id_asignacion);  
	
	
	//public Page<AsigCombustible> findPlacaPage(String placa,Pageable pageable);
	
	public void save(AsigCombustibleExt combustiblext);
	
	public AsigCombustibleExt findOne(Long id_asignacion);
	
	public void delete(Long id_asignacion);
	
	public int ultimoid();
	
}
