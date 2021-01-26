package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.IVehiculoPlacasDao;
import com.PGJ.SGV.models.entity.VehiculoPlacas;

@Service
public class IVehiculoPlacasServiceImpl implements IVehiculoPlacasService {
	
	
	@Autowired
	private IVehiculoPlacasDao vehiculoplacasDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<VehiculoPlacas> findAll() {
		// TODO Auto-generated method stub
		 return (List<VehiculoPlacas>) vehiculoplacasDao.findAll();
	}


	@Override
	public Page<VehiculoPlacas> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return vehiculoplacasDao.findAll(pageable);
	}
	
	
	@Override
	public Page<VehiculoPlacas> findPlacas(Long id_vehiculo,Pageable pageable) {
		// TODO Auto-generated method stub
		return vehiculoplacasDao.findPlacas(id_vehiculo,pageable);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<VehiculoPlacas> findPlaca(Long id_vehiculo) {
		// TODO Auto-generated method stub
		return (List<VehiculoPlacas>) vehiculoplacasDao.findPlaca(id_vehiculo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<VehiculoPlacas> findPlacaAnt(Long id_vehiculo) {
		// TODO Auto-generated method stub
		return (List<VehiculoPlacas>) vehiculoplacasDao.findPlacaAnt(id_vehiculo);
	}
	

}



