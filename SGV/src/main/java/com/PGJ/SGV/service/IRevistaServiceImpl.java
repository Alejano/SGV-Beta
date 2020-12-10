package com.PGJ.SGV.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.IRevistaDao;
import com.PGJ.SGV.models.entity.Revista;


@Service
public class IRevistaServiceImpl implements IRevistaService {

	@Autowired
	private IRevistaDao revistaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Revista> findAll() {
		// TODO Auto-generated method stub
		return (List<Revista>) revistaDao.findAll();
	}

	@Override
	@Transactional
	public void save(Revista revista) {
		// TODO Auto-generated method stub
		revistaDao.save(revista);
	}

	@Override
	@Transactional(readOnly = true)
	public Revista findOne(Long id_revista) {
		// TODO Auto-generated method stub
		return revistaDao.findById(id_revista).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id_revista) {
		revistaDao.deleteById(id_revista);
		
	}

	@Override
	public List<Revista> findAllFuturo() {
		// TODO Auto-generated method stub
		return revistaDao.findAllFuturo();
	}

	@Override
	public List<Revista> revistaEvento(Long evento) {
		// TODO Auto-generated method stub
		return revistaDao.revistaEvento(evento);
	}

	
	@Override
	@Transactional
	public void deleteAllbyEvento(Long evento) {
		revistaDao.deleteAllbyEvento(evento);
		
	}

	@Override
	public Revista UltimaRevistaVehiculo(Long id_vehiculo) {
		// TODO Auto-generated method stub
		return revistaDao.UltimaRevistaVehiculo(id_vehiculo);
	}

	

}
