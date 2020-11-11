package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;

import com.PGJ.SGV.models.dao.IAsigComDao;
import com.PGJ.SGV.models.entity.AsigCombustible;
import com.PGJ.SGV.models.entity.OdometroAcombus;

@Service
public class IOdomServiceImpl implements IOdomService {

	@Autowired
	private IOdomService odomDao;

	@Override
	@Transactional(readOnly = true)
	public List<OdometroAcombus> findAll() {
		// TODO Auto-generated method stub
		return odomDao.findAll();
	}

	@Override
	@Transactional
	public void save(OdometroAcombus odometro) {
		// TODO Auto-generated method stub
		odomDao.save(odometro);
	}

	@Override
	@Transactional(readOnly = true)
	public OdometroAcombus findOne(long id_odo) {
		// TODO Auto-generated method stub
		return odomDao.findOne(id_odo);
	}

	@Override
	@Transactional
	public void delete(long id_odo) {
		// TODO Auto-generated method stub
		odomDao.delete(id_odo);
	}	


}





