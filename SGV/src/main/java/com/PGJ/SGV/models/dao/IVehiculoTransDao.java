package com.PGJ.SGV.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.PGJ.SGV.models.entity.VehiculoTransmision;

public interface IVehiculoTransDao extends CrudRepository<VehiculoTransmision, Long> {
	
	@Query("select count(v) from VehiculoTransmision v")
	public Long transmisionestotales();
	
	@Query("select p.id_transmision from VehiculoTransmision p where p.id_transmision = (select max(p.id_transmision) from VehiculoTransmision p)")
	public int ultimoId();
	
}
