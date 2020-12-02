package com.PGJ.SGV.service;

import java.util.List;
import java.util.Optional;

import com.PGJ.SGV.models.entity.Revista;

public interface IRevistaService {
	
	public List<Revista> findAll(); 
	
	public List<Revista> findAllFuturo(); 
	
	public void save(Revista revista);
	
	public Revista findOne(Long id_revista);
	
	public void delete(Long id_revista);
	
	public List<Revista> revistaEvento(Long evento);
	
	public void deleteAllbyEvento(Long evento);

}
