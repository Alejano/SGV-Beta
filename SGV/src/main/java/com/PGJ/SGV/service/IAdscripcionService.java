package com.PGJ.SGV.service;

import java.util.List;

import com.PGJ.SGV.models.entity.Adscripcion;

public interface IAdscripcionService {
	public List<Adscripcion> findAll(); 
	
	public void save(Adscripcion adscripcion);
	
	public Adscripcion findOne(Long id_adscripcion);
	
	public void delete(Long id_adscripcion);
	
	public Long adscripcionestotales();
	
}
