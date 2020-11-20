package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.PGJ.SGV.models.dao.IOdomExtDao;
import com.PGJ.SGV.models.entity.OdometroAcombusExt;

@Service
public class IOdomExtServiceImpl implements IOdomExtService {

	@Autowired
	private IOdomExtDao odomextDao;

	@Override
	@Transactional(readOnly = true)
	public List<OdometroAcombusExt> findAll() {
		// TODO Auto-generated method stub
		return (List<OdometroAcombusExt>) odomextDao.findAll();
	}

	@Override
	@Transactional
	public void save(OdometroAcombusExt odometro) {
		// TODO Auto-generated method stub
		odomextDao.save(odometro);
	}

	@Override
	@Transactional(readOnly = true)
	public OdometroAcombusExt findOne(long id_odo) {
		// TODO Auto-generated method stub
		return odomextDao.findById(id_odo).orElse(null);
	}

	@Override
	@Transactional
	public void delete(long id_odo) {
		// TODO Auto-generated method stub
		odomextDao.deleteById(id_odo);
	}

	@Override
	@Transactional(readOnly = true)
	public int ultimoID() {
		// TODO Auto-generated method stub
		return odomextDao.ultimoId();
	}

	@Override
	@Transactional(readOnly = true)
	public OdometroAcombusExt ObtenerAsignacion(Long id_asignacion) {
		// TODO Auto-generated method stub
		return odomextDao.ObtenerAsignacion(id_asignacion);
	}	


}





