package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.PGJ.SGV.models.dao.IEventoDao;
import com.PGJ.SGV.models.entity.Evento;



@Service
public class IEventoServiceImpl implements IEventoService {

	@Autowired
	private IEventoDao eventoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Evento> findAll() {
		// TODO Auto-generated method stub
		return (List<Evento>) eventoDao.findAll();
	}

	@Override
	@Transactional
	public void save(Evento eventos) {
		// TODO Auto-generated method stub
		eventoDao.save(eventos);
	}

	@Override
	@Transactional(readOnly = true)
	public Evento findOne(Long id) {
		// TODO Auto-generated method stub
		return eventoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		eventoDao.deleteById(id);
		
	}

	@Override
	public long ultimoid() {
		Long id= eventoDao.ultimoId();
		if(id == null) {
			return 0;
		}else {
			return eventoDao.ultimoId();
		}
		
	}

	@Override
	public List<Evento> allJson() {
		// TODO Auto-generated method stub
		return eventoDao.allJson();
	}



	

	

}
