package com.PGJ.SGV.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.IResguardanteDao;

import com.PGJ.SGV.models.entity.Resguardante;


@Service
public class IResguardoServiceImpl implements IResguardanteService {

	@Autowired
	private IResguardanteDao resguardoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Resguardante> findAll() {
		// TODO Auto-generated method stub
		return (List<Resguardante>)resguardoDao.findAll();
	}

	@Override
	@Transactional
	public void save(Resguardante resguardo) {
		// TODO Auto-generated method stub
		resguardoDao.save(resguardo);
	}

	@Override
	@Transactional(readOnly = true)
	public Resguardante findOne(Long id) {
		// TODO Auto-generated method stub
		return resguardoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		resguardoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Resguardante> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return (Page<Resguardante>)resguardoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public int ultimoId() {
		// TODO Auto-generated method stub
		int id=resguardoDao.ultimoId() ;
		if(id == 0) {
			id=1;
		}
				
		return id;
	}
	

	@Override
	@Transactional(readOnly = true)
	public Page<Resguardante> findAllByVehiculo(long id_vehiculo, Pageable pageable) {
		// TODO Auto-generated method stub
		return resguardoDao.findAllByVehiculo(id_vehiculo, pageable);
	}

	@Override
	public List<Resguardante> findActivos() {
		// TODO Auto-generated method stub
		return resguardoDao.findActivos();
	}

	@Override
	public List<Resguardante> findResg(Long id_vehiculo) {
		// TODO Auto-generated method stub
		return resguardoDao.findResg(id_vehiculo);
	}
	

}