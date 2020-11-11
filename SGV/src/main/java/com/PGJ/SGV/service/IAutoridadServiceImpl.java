package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.IAutoridadDao;
import com.PGJ.SGV.models.entity.Authority;

@Service
public class IAutoridadServiceImpl implements IAutoridadService {

	@Autowired	
	private IAutoridadDao autoridadDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Authority> findAll() {
		// TODO Auto-generated method stub
		return (List<Authority>) autoridadDao.findAll();
	}

	@Override
	@Transactional
	public void save(Authority autoridad) {
		// TODO Auto-generated method stub
		autoridadDao.save(autoridad);
	}

	@Override
	@Transactional(readOnly = true)
	public Authority findOne(long id) {
		// TODO Auto-generated method stub
		return autoridadDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(long id) {
		// TODO Auto-generated method stub
		autoridadDao.deleteById(id);
	}

}
