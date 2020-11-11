package com.PGJ.SGV.service;

import java.util.List;


import com.PGJ.SGV.models.entity.TipoResguardante;

public interface ITipoResguardanteService {

	List<TipoResguardante> findAll();
	
	public TipoResguardante findOne(Long id);
		
}
