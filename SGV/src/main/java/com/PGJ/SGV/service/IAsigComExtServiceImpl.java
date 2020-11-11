package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;

import com.PGJ.SGV.models.dao.IAsigComExtDao;
import com.PGJ.SGV.models.entity.AsigCombustibleExt;

@Service
public class IAsigComExtServiceImpl implements IAsigComExtService  {
	
	@Autowired
	private IAsigComExtDao asignacionextDao;

	@Override
	@Transactional(readOnly = true)
	public List<AsigCombustibleExt> findAll() {
		// TODO Auto-generated method stub
		return (List<AsigCombustibleExt>) asignacionextDao.findAll();
	}

	@Override
	@Transactional
	public void save(AsigCombustibleExt combustiblext) {
		// TODO Auto-generated method stub
		asignacionextDao.save(combustiblext);
	}

	@Override
	@Transactional(readOnly = true)
	public AsigCombustibleExt findOne(Long id_asignacion) {
		// TODO Auto-generated method stub
		return asignacionextDao.findById(id_asignacion).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id_asignacion) {
		// TODO Auto-generated method stub
		asignacionextDao.deleteById(id_asignacion);
	}

	@Override
	public List<AsigCombustibleExt> findAsig(String id_asignacion) {
		return null;
		// TODO Auto-generated method stub
		//return asignacionextDao.findAsig(id_asignacion);
	}

	@Override
	public List<AsigCombustibleExt> findextId(long id_asignacion) {
		
		// TODO Auto-generated method stub
		return asignacionextDao.findextId(id_asignacion);
				
	}

	@Override
	public long ultimoid() {
		// TODO Auto-generated method stub
		return asignacionextDao.ultimoId();
	}

}
