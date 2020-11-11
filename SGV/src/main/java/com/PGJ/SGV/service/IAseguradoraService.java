package com.PGJ.SGV.service;

import java.util.List;

import com.PGJ.SGV.models.entity.Aseguradora;

public interface IAseguradoraService {

      public List<Aseguradora> findAll(); 
	
      public void save(Aseguradora aseguradora);
	
	  public Aseguradora findOne(Long id_aseguradora);
	
	  public void delete(Long id_aseguradora);
	  
	  public String fechamax ();
	  
	  public Long aseguradorastotales();
	  
}
