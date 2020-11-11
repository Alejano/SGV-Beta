package com.PGJ.SGV.service;

import java.util.List;

import com.PGJ.SGV.models.entity.AsigCombustible;
import com.PGJ.SGV.models.entity.OdometroAcombus;

public interface IOdomService {

	public List<OdometroAcombus> findAll(); 
	
	public void save(OdometroAcombus odometro);
	
	public OdometroAcombus findOne(long id_odo);
	
	public void delete(long id_odo);
		
	
}
