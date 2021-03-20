package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.IVehiculoMarcaDao;
import com.PGJ.SGV.models.entity.VehiculoMarca;

@Service
public class IMarcaVehiculoServiceImpl implements IMarcaVehiculoService {
	
	@Autowired
	private IVehiculoMarcaDao MarcaDao; 
	
	@Override
	@Transactional
	public void save(VehiculoMarca marcavehiculo) {
		// TODO Auto-generated method stub
		MarcaDao.save(marcavehiculo);
	}

	@Override
	@Transactional(readOnly = true)
	public VehiculoMarca findOne(Long id_marca) {
		// TODO Auto-generated method stub
		return MarcaDao.findById(id_marca).orElse(null);
	}


	@Override
	@Transactional(readOnly = true)
	public List<VehiculoMarca> findAll() {
		// TODO Auto-generated method stub
		return (List<VehiculoMarca>) MarcaDao.findAll();
	}
	
	@Override
	public Long marcastotales() {
		return MarcaDao.marcastotales();
	}


}
