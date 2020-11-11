package com.PGJ.SGV.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.PGJ.SGV.models.entity.VehiculoDetalle;

public interface IVehiculoDetalleDao extends CrudRepository<VehiculoDetalle, Long> {
	
	@Query("select d from VehiculoDetalle d inner join Vehiculo v on v.id_vehiculo = d.vehiculo.id_vehiculo where v.id_vehiculo = ?1 ")
	public VehiculoDetalle findByVehiculoDetalle(Long id_vehiculo);
	
	@Query("select d.tarjeta_circulacion from VehiculoDetalle d inner join Vehiculo v on v.id_vehiculo = d.vehiculo.id_vehiculo where v.id_vehiculo = ?1 ")
	public String findTCDetalle(Long id_vehiculo);
}
