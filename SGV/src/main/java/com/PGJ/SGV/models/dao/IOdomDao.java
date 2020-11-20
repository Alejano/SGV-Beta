package com.PGJ.SGV.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.PGJ.SGV.models.entity.OdometroAcombus;


public interface IOdomDao extends PagingAndSortingRepository<OdometroAcombus, Long> {
	
	@Query("select p.id_odo from OdometroAcombus p where p.id_odo = (select max(p.id_odo) from OdometroAcombus p)")
	public int ultimoId();
	
	@Query("select p from OdometroAcombus p where p.id_asignacion = ?1")
	public OdometroAcombus ObtenerAsignacion(Long id_asignacion);
	
}
