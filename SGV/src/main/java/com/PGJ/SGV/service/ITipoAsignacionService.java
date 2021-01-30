package com.PGJ.SGV.service;

import java.util.List;

import com.PGJ.SGV.models.entity.TipoAsignacion;

public interface ITipoAsignacionService {
	
public List<TipoAsignacion> findAll(); 
		
	public TipoAsignacion findOne(long id_tipo);
	
	public void delete(long id_tipo);

}
