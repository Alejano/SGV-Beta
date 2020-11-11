package com.PGJ.SGV.service;

import java.util.List;

import com.PGJ.SGV.models.entity.Eventos;

public interface IEventoService {
	public List<Eventos> findAll(); 
	
	public void save(Eventos eventos);

	
	public Eventos findOne(Long id);
	
	public void delete(Long id);
	
	public long ultimoid();

}
