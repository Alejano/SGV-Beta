package com.PGJ.SGV.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.OdometroAcombus;
import com.PGJ.SGV.models.entity.OdometroAcombusExt;


public interface IOdomExtDao extends PagingAndSortingRepository<OdometroAcombusExt, Long> {
	
	@Query("select p.id_odo from OdometroAcombusExt p where p.id_odo = (select max(p.id_odo) from OdometroAcombus p)")
	public int ultimoId();
	
	@Query("select p from OdometroAcombusExt p where p.id_asignacion = ?1")
	public OdometroAcombusExt ObtenerAsignacion(Long id_asignacion);
	
}
