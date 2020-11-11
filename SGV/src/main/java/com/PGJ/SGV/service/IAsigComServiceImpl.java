package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;

import com.PGJ.SGV.models.dao.IAsigComDao;
import com.PGJ.SGV.models.entity.AsigCombustible;

@Service
public class IAsigComServiceImpl implements IAsigComService {

	@Autowired
	private IAsigComDao asignacionDao;

	@Override
	@Transactional(readOnly = true)
	public List<AsigCombustible> findAll() {
		// TODO Auto-generated method stub
		return (List<AsigCombustible>) asignacionDao.findAll();
	}

	@Override
	@Transactional
	public void save(AsigCombustible combustible) {
		// TODO Auto-generated method stub
		asignacionDao.save(combustible);
	}

	@Override
	@Transactional(readOnly = true)
	public AsigCombustible findOne(long id_asignacion) {
		// TODO Auto-generated method stub
		return asignacionDao.findById(id_asignacion).orElse(null);
	}

	@Override
	@Transactional
	public void delete(long id_asignacion) {
		// TODO Auto-generated method stub
		asignacionDao.deleteById(id_asignacion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AsigCombustible> findidVehiculo(long id_vehiculo) {
		
		// TODO Auto-generated method stub
	return asignacionDao.findidVehiculo(id_vehiculo);
		
	}

	@Override
	public List<AsigCombustible> findidVehiculoMensual(long id_vehiculo, String mes) {
		// TODO Auto-generated method stub
		return asignacionDao.findidVehiculoMensual(id_vehiculo,mes);
	}

	/*@Override
	public List<AsigCombustible> findPlacaMes(String placa) {
		// TODO Auto-generated method stub
		return asignacionDao.findPlacaMes(placa);
	}*/

/*@Override
	public Page<AsigCombustible> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return asignacionDao.findAll(pageable);
	}

	@Override
	public Page<AsigCombustible> findPlacaPage (String placa, Pageable pageable) {
		// TODO Auto-generated method stub
		return asignacionDao.findPlacaPage(placa, pageable);
	}*/

}





