package com.PGJ.SGV.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.PGJ.SGV.models.entity.VehiculoFuncion;

public interface IVehiculoFuncionDao extends CrudRepository<VehiculoFuncion, Long> {
	
	@Query("select count(v) from VehiculoFuncion v")
	public Long funcionestotales();
	
	@Query("select p.id_funcion from VehiculoFuncion p where p.id_funcion = (select max(p.id_funcion) from VehiculoFuncion p)")
	public int ultimoId();

}
