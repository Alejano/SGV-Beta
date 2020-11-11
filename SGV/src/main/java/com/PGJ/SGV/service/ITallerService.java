package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.PGJ.SGV.models.entity.Taller;

public interface ITallerService {

public List<Taller> findAll(); 
	
	public void save(Taller taller);
	
	public Taller findOne(Long id_taller);
	
	public void delete(Long id_taller);
	
	public Page<Taller> FindTallerElemPageL(String elemento, Pageable page);
	
	public Long totalTalleres();
	
}
