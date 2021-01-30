package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.PGJ.SGV.models.dao.ITipoAsignacionDao;
import com.PGJ.SGV.models.entity.TipoAsignacion;

@Service
public class ITipoAsignacionServiceImpl implements ITipoAsignacionService {
	
	@Autowired	
	private ITipoAsignacionDao tipoasignacionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoAsignacion> findAll() {
		// TODO Auto-generated method stub
		return (List<TipoAsignacion>) tipoasignacionDao.findAll();
	}

	
	@Override
	@Transactional(readOnly = true)
	public TipoAsignacion findOne(long id_tipo) {
		// TODO Auto-generated method stub
		return tipoasignacionDao.findById(id_tipo).orElse(null);
	}

	@Override
	@Transactional
	public void delete(long id_tipo) {
		// TODO Auto-generated method stub
		tipoasignacionDao.deleteById(id_tipo);
	}

}
