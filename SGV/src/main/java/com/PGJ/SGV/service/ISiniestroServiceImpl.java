package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.PGJ.SGV.models.dao.ISiniestroDao;
import com.PGJ.SGV.models.entity.Siniestro;

@Service
public class ISiniestroServiceImpl implements ISiniestroService {

	@Autowired
	private ISiniestroDao siniestroDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Siniestro> findAll() {
		// TODO Auto-generated method stub
		return (List<Siniestro>)siniestroDao.findAll();
	}

	@Override
	@Transactional
	public void save(Siniestro siniestro) {
		// TODO Auto-generated method stub
		siniestroDao.save(siniestro);
	}

	@Override
	@Transactional(readOnly = true)
	public Siniestro findOne(Long id_siniestro) {
		// TODO Auto-generated method stub
		return siniestroDao.findById(id_siniestro).orElse(null);
		//return null;
	}

	@Override
	@Transactional
	public void delete(Long id_siniestro) {
		// TODO Auto-generated method stub
		siniestroDao.deleteById(id_siniestro);
	}

	@Override
	public Page<Siniestro> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return siniestroDao.findAll(pageable);
	}
	
	@Override
	public Long totalSiniestro() {
		// TODO Auto-generated method stub
		return siniestroDao.totalSiniestro();
	}
	
	@Override
	public Page<Siniestro> FindsegVehi(Long id_vehiculo,Pageable pageable) {
		// TODO Auto-generated method stub
		return siniestroDao.FindsegVehi(id_vehiculo,pageable);
	}

	@Override
	public Page<Siniestro> FindSinElemVehiPage(Long id_vehiculo,String elemento,Pageable pageable) {
		// TODO Auto-generated method stub
		return siniestroDao.FindSinElemVehiPage(id_vehiculo,elemento,pageable);
	}
	
	@Override
	public Page<Siniestro> FindSinElemenAreaPage(Long id_adscripcion,String elemento,Pageable pageable) {
		// TODO Auto-generated method stub
		return siniestroDao.FindSinElemenAreaPage(id_adscripcion,elemento,pageable);
	}
	
	@Override
	public Page<Siniestro> FindSinElemenPage(String elemento,Pageable pageable) {
		// TODO Auto-generated method stub
		return siniestroDao.FindSinElemenPage(elemento,pageable);
	}
	
	
}
