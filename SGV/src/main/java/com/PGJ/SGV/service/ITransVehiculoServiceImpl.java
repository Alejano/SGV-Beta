package com.PGJ.SGV.service;

import java.util.List;

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
	public List<VehiculoTransmision> findAll() {
		// TODO Auto-generated method stub
		return (List<VehiculoTransmision>) TransDao.findAll();
	}
	
	@Override
	public Long transmisionestotales() {
		return TransDao.transmisionestotales();
	}
	
	@Override
	@Transactional(readOnly = true)
	public int ultimoId() {
		// TODO Auto-generated method stub
		return TransDao.ultimoId();
	}

}
