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
	

}
