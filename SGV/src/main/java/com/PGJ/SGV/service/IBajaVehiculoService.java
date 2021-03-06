package com.PGJ.SGV.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.PGJ.SGV.models.dao.IBajaVehiculoDao;
import com.PGJ.SGV.models.entity.BajaVehiculo;


@Service
public class IBajaVehiculoService implements IBajaVehiculoServiceImpl {
	
	@Autowired
	private IBajaVehiculoDao bajavehiculodao;

	@Override
	@Transactional
	public void baja(BajaVehiculo bajavehiculo) {
		// TODO Auto-generated method stub
		bajavehiculodao.save(bajavehiculo);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public BajaVehiculo findOne(Long id_baja_vehiculo) {
			// TODO Auto-generated method stub
	    	return bajavehiculodao.findById(id_baja_vehiculo).orElse(null);
	    	//return null;
		}
	
	
	
	@Override
	@Transactional(readOnly = true)
	public BajaVehiculo findBaja(Long id_vehiculo) {
			// TODO Auto-generated method stub
	    	return bajavehiculodao.findBaja(id_vehiculo);
	    	//return null;
		}
	
	@Override
	@Transactional(readOnly = true)
	public String findActaFNQ(Long id_vehiculo) {
			// TODO Auto-generated method stub
	    	return bajavehiculodao.findActaFNQ(id_vehiculo);
	    	//return null;
		}
	
	@Override
	@Transactional(readOnly = true)
	public String findOficio(Long id_vehiculo) {
			// TODO Auto-generated method stub
	    	return bajavehiculodao.findOficio(id_vehiculo);
	    	//return null;
		}
	
	
	@Override
	@Transactional(readOnly = true)
	public String findDictamen(Long id_vehiculo) {
			// TODO Auto-generated method stub
	    	return bajavehiculodao.findDictamen(id_vehiculo);
	    	//return null;
		}
	
	
}
