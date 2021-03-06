package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PGJ.SGV.models.dao.INotificacionDao;
import com.PGJ.SGV.models.entity.Notificacion;

@Service
public class INotificacionServiceImpl implements INotificacionService {

	@Autowired
	private INotificacionDao notiDao;
	
	@Override
	public List<Notificacion> findAll() {
		// TODO Auto-generated method stub
	 return (List<Notificacion>) notiDao.findAll();
	}

	@Override
	public void save(Notificacion notificacion) {
		// TODO Auto-generated method stub
		notiDao.save(notificacion);
	}

	@Override
	public Notificacion findOne(Long id_notificacion) {
		// TODO Auto-generated method stub
		return notiDao.findById(id_notificacion).orElse(null);
	}

	
	@Override
	public List<Notificacion> NotifyRegSin(){
		// TODO Auto-generated method stub
		return notiDao.NotifyRegSin();
	}


	@Override
	public List<Notificacion> NotifyRegMant(){
		// TODO Auto-generated method stub
		return notiDao.NotifyRegMant();
	}

	@Override
	public Long TotalRegSin() {
		// TODO Auto-generated method stub
		return notiDao.TotalRegSin();
	}


	@Override
	public Long TotalRegMant() {
		// TODO Auto-generated method stub
		return notiDao.TotalRegMant();
	}
	
}
