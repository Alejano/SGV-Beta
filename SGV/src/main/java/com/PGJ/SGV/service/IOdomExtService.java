package com.PGJ.SGV.service;

import java.util.List;

import com.PGJ.SGV.models.entity.OdometroAcombus;
import com.PGJ.SGV.models.entity.OdometroAcombusExt;

public interface IOdomExtService {

	public List<OdometroAcombusExt> findAll(); 
	
	public void save(OdometroAcombusExt odometro);
	
	public OdometroAcombusExt findOne(long id_odo);
	
	public void delete(long id_odo);
		
	public int ultimoID();
	
	public OdometroAcombusExt ObtenerAsignacion(Long id_asignacion);
	
}
