package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.ITipoResguardanteDao;
import com.PGJ.SGV.models.entity.TipoResguardante;

@Service
public class ITipoResguardanteServiceImpl implements ITipoResguardanteService {

	@Autowired
	private ITipoResguardanteDao TipoResguardantedao;
	
	
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoResguardante> findAll() {
		// TODO Auto-generated method stub
		return (List<TipoResguardante>) TipoResguardantedao.findAll();
	}



	@Override
	public TipoResguardante findOne(Long id) {
		// TODO Auto-generated method stub
		return TipoResguardantedao.findById(id).orElse(null);
	}
	
	
	
}
