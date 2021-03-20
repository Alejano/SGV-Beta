package com.PGJ.SGV.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.IVehiculoTransDao;
import com.PGJ.SGV.models.entity.VehiculoTransmision;

@Service
public class ITransVehiculoServiceImpl implements ITransVehiculoService{

	@Autowired
	private IVehiculoTransDao TransDao; 
	
	@Override
	@Transactional
	public void save(VehiculoTransmision transmisionvehiculo) {
		// TODO Auto-generated method stub
		TransDao.save(transmisionvehiculo);
	}

	@Override
	@Transactional(readOnly = true)
	public VehiculoTransmision findOne(Long id_transmision) {
		// TODO Auto-generated method stub
		return TransDao.findById(id_transmision).orElse(null);
	}

	@Override
	public VehiculoTransmision findAll() {
		// TODO Auto-generated method stub
		return (VehiculoTransmision) TransDao.findAll();
	}


}
