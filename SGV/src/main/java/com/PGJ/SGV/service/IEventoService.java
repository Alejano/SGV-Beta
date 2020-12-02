package com.PGJ.SGV.service;

import java.util.List;

import com.PGJ.SGV.models.entity.Evento;

public interface IEventoService {
	public List<Evento> findAll(); 
	
	public List<Evento> allJson();
	
	public void save(Evento eventos);

	
	public Evento findOne(Long id);
	
	public void delete(Long id);
	
	public long ultimoid();

}
