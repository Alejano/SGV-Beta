package com.PGJ.SGV.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.PGJ.SGV.models.entity.BajaVehiculo;

public interface IBajaVehiculoDao extends CrudRepository<BajaVehiculo, Long> {
	
	@Query("select b from BajaVehiculo b inner join Vehiculo v on b.vehiculo.id_vehiculo = v.id_vehiculo where v.id_vehiculo = ?1")
	public BajaVehiculo findBaja(Long id_vehiculo);

	@Query("select b.url_acta_fnq from BajaVehiculo b inner join Vehiculo v on v.id_vehiculo = b.vehiculo.id_vehiculo where v.id_vehiculo = ?1 ")
	public String findActaFNQ(Long id_vehiculo);
	
	@Query("select b.url_oficio_baja from BajaVehiculo b inner join Vehiculo v on v.id_vehiculo = b.vehiculo.id_vehiculo where v.id_vehiculo = ?1 ")
	public String findOficio(Long id_vehiculo);
	
	@Query("select b.url_dictamen from  BajaVehiculo b inner join Vehiculo v on v.id_vehiculo = b.vehiculo.id_vehiculo where v.id_vehiculo = ?1 ")
	public String findDictamen(Long id_vehiculo);
}
