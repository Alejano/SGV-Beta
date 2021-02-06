package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.IConductorDao;
import com.PGJ.SGV.models.dao.ITipoValeDao;
import com.PGJ.SGV.models.entity.Conductor;
import com.PGJ.SGV.models.entity.TipoVale;
import com.PGJ.SGV.models.entity.Viaje;

@Service
public class ITipoValeServiceImpl implements ITipoValeService {
	
	@Autowired
	private ITipoValeDao tipovaleDao;


	@Override
	@Transactional(readOnly = true)
	public List<TipoVale> findAll() {
		// TODO Auto-generated method stub					
		return  (List<TipoVale>) tipovaleDao.findAll();
	}
	
	@Override
	@Transactional
	public void save(TipoVale tipovale) {
		tipovaleDao.save(tipovale);
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public TipoVale findOne(Long id_vale) {
		// TODO Auto-generated method stub
		return tipovaleDao.findById(id_vale).orElse(null);
	}
	
	@Override
	public Page<TipoVale> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return tipovaleDao.findAll(pageable);
	}
	
	@Override
	public Long tipovalestotales() {
		// TODO Auto-generated method stub
		return tipovaleDao.tipovalestotales();
	}
	

}
