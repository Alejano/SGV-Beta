package com.PGJ.SGV.service;

import java.util.List;
import com.PGJ.SGV.models.entity.Notificacion;

public interface INotificacionService {
	
    public List<Notificacion> findAll(); 
		
	public void save(Notificacion Notificacion);
	
	public Notificacion findOne(Long id_notifcacion);
	
	public List<Notificacion> NotifyRegSin();
	
	public List<Notificacion> NotifyRegMant();
	
}
