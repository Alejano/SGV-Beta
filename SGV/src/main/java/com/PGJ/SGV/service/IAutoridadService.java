package com.PGJ.SGV.service;

import java.util.List;
import com.PGJ.SGV.models.entity.Authority;


public interface IAutoridadService {
	public List<Authority> findAll(); 
	
	public void save(Authority autoridad);
	
	public Authority findOne(long id);
	
	public void delete(long id);
}
