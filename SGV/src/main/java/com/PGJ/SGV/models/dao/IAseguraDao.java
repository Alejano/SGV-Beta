package com.PGJ.SGV.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.PGJ.SGV.models.entity.Aseguradora;


public interface IAseguraDao extends CrudRepository<Aseguradora, Long>{

	@Query("select max(a.fecha_fin) from Aseguradora a")
	public String fechamax ();
	
	@Query("select count(a) from Aseguradora a")
	public Long aseguradorastotales();
	
}
