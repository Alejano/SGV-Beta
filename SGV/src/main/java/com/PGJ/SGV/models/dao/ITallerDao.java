package com.PGJ.SGV.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.PGJ.SGV.models.entity.Taller;

public interface ITallerDao extends CrudRepository<Taller, Long>{
	
	@Query("select m from Taller m where m.nombre like %?1% or m.calle like %?1% or m.cp like %?1% or m.numero like %?1% or m.alcaldia like %?1% or m.entidad like %?1% or m.no_contrato like %?1%")
	public Page<Taller> FindTallerElemPageL(String elemento,Pageable pageable);
	

}
