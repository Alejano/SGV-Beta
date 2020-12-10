package com.PGJ.SGV.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.IEstadoVehiculoDao;
import com.PGJ.SGV.models.entity.VehiculoEstado;

@Service
public class IEstadoVehiculoServiceImpl implements IEstadoVehiculoService{
	

	@Autowired
	private IEstadoVehiculoDao EstadoDao; 
	
	@Override
	@Transactional
	public void save(VehiculoEstado estadovehiculo) {
		// TODO Auto-generated method stub
		 EstadoDao.save(estadovehiculo);
	}

	@Override
	@Transactional(readOnly = true)
	public VehiculoEstado findOne(Long id_estadovehiculo) {
		// TODO Auto-generated method stub
		return EstadoDao.findById(id_estadovehiculo).orElse(null);
	}

	@Override
	public VehiculoEstado findall() {
		// TODO Auto-generated method stub
		return (VehiculoEstado) EstadoDao.findAll();
	}

	@Override
	public VehiculoEstado findbyPlaca(String placa) {
		// TODO Auto-generated method stub
		return EstadoDao.findbyplaca(placa);
	}

	@Override
	public VehiculoEstado findbyVehiculo(Long id_vehiculo) {
		// TODO Auto-generated method stub
		return EstadoDao.findbyVehiculo(id_vehiculo);
	}

	



}
