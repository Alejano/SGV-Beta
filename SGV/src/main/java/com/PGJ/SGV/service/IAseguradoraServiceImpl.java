package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.IAseguraDao;
import com.PGJ.SGV.models.entity.Aseguradora;

@Service
public class IAseguradoraServiceImpl implements IAseguradoraService {

	@Autowired
	private IAseguraDao aseguraDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Aseguradora> findAll() {
		// TODO Auto-generated method stub
		return (List<Aseguradora>) aseguraDao.findAll();
	}

	@Override
	@Transactional
	public void save(Aseguradora aseguradora) {
		// TODO Auto-generated method stub
		aseguraDao.save(aseguradora);
	}

	@Override
	@Transactional(readOnly = true)
	public Aseguradora findOne(Long id_aseguradora) {
		// TODO Auto-generated method stub
		return aseguraDao.findById(id_aseguradora).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long id_aseguradora) {
		aseguraDao.deleteById(id_aseguradora);
		
	}
	
	@Override
	public String fechamax() {
		// TODO Auto-generated method stub
		return aseguraDao.fechamax();
	}
	

	@Override
	public Long aseguradorastotales() {
		return aseguraDao.aseguradorastotales();
	}
}
