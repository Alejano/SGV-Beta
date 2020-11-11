package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.IAdscripDao;
import com.PGJ.SGV.models.entity.Adscripcion;


@Service
public class IAdscripcionServiceImpl implements IAdscripcionService {

	@Autowired
	private IAdscripDao adscripcionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Adscripcion> findAll() {
		// TODO Auto-generated method stub
		return (List<Adscripcion>) adscripcionDao.findAll();
	}

	@Override
	@Transactional
	public void save(Adscripcion adscripcion) {
		// TODO Auto-generated method stub
		adscripcionDao.save(adscripcion);
	}

	@Override
	@Transactional(readOnly = true)
	public Adscripcion findOne(Long id_adscripcion) {
		// TODO Auto-generated method stub
		return adscripcionDao.findById(id_adscripcion).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id_adscripcion) {
		adscripcionDao.deleteById(id_adscripcion);
		
	}
	
	@Override
	public Long adscripcionestotales() {
		return adscripcionDao.adscripcionestotales();
	}
}
