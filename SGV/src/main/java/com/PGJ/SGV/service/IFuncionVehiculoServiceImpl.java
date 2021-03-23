package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.IVehiculoFuncionDao;
import com.PGJ.SGV.models.entity.VehiculoFuncion;

@Service
public class IFuncionVehiculoServiceImpl implements IFuncionVehiculoService{
	
	@Autowired
	private IVehiculoFuncionDao FuncioDao; 
	
	@Override
	@Transactional
	public void save(VehiculoFuncion funcionvehiculo) {
		// TODO Auto-generated method stub
		FuncioDao.save(funcionvehiculo);
	}

	@Override
	@Transactional(readOnly = true)
	public VehiculoFuncion findOne(Long id_funcion) {
		// TODO Auto-generated method stub
		return FuncioDao.findById(id_funcion).orElse(null);
	}

	@Override
	public List<VehiculoFuncion> findAll() {
		// TODO Auto-generated method stub
		return (List<VehiculoFuncion>) FuncioDao.findAll();
	}
	
	@Override
	public Long funcionestotales() {
		return FuncioDao.funcionestotales();
	}

	@Override
	@Transactional(readOnly = true)
	public int ultimoId() {
		// TODO Auto-generated method stub
		return FuncioDao.ultimoId();
	}


}
