package com.PGJ.SGV.service;

import java.util.List;


import com.PGJ.SGV.models.entity.AsigCombustibleExt;

public interface IAsigComExtService {

	
public List<AsigCombustibleExt> findAll(); 
	
	//public Page<AsigCombustible> findAll(Pageable pageable); 
	
	public List<AsigCombustibleExt> findAsig(String id_asignacion); 
	
	public List<AsigCombustibleExt> findextId(long id_asignacion);  	
	
	//public Page<AsigCombustible> findPlacaPage(String placa,Pageable pageable);
	
	public void save(AsigCombustibleExt combustiblext);
	
	public AsigCombustibleExt findOne(Long id_asignacion);
	
	public void delete(Long id_asignacion);
	
	public long ultimoid();
	
}
