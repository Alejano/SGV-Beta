package com.PGJ.SGV.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.AsigCombustible;


public interface IAsigComDao extends PagingAndSortingRepository<AsigCombustible, Long> {
	
	
	@Query("select a from AsigCombustible a inner join Vehiculo v on a.vehiculo.id_vehiculo = v.id_vehiculo where v.id_vehiculo = ?1")
	public List<AsigCombustible> findidVehiculo(long id_vehiculo);
	
	@Query("select a from AsigCombustible a inner join Vehiculo v on a.vehiculo.id_vehiculo = v.id_vehiculo where v.id_vehiculo = ?1 and a.fecha_ini_ord like %?2%")
	public List<AsigCombustible> findidVehiculoMensual(long id_vehiculo,String mes);
	
	//@Query("select a from AsigCombustible a inner join Vehiculo v on a.vehiculo.placa = v.placa where v.placa like %?1% and (select substring(a.fecha_ini_ord, 6, 2) = (select substring(curdate(), 6, 2)))")  
	//public List<AsigCombustible> findPlacaMes(String placa);

	//@Query("select a from AsigCombustible a inner join Vehiculo v on a.vehiculo.placa = v.placa where v.placa like %?1%")
	//public Page<AsigCombustible> findPlacaPage(String placa,Pageable pageable);
}
