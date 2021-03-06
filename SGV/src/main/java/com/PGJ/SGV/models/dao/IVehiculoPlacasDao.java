package com.PGJ.SGV.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.PGJ.SGV.models.entity.VehiculoPlacas;

public interface IVehiculoPlacasDao extends PagingAndSortingRepository<VehiculoPlacas, Long>{

	@Query("select v from VehiculoPlacas v inner join Vehiculo l on v.vehiculo.id_vehiculo = l.id_vehiculo where l.id_vehiculo = ?1 order by v.fecha_inicio asc")
	public Page<VehiculoPlacas> findPlacas(Long id_vehiculo,Pageable pageable);
	
	@Query("select v from VehiculoPlacas v inner join Vehiculo l on v.vehiculo.id_vehiculo = l.id_vehiculo where l.id_vehiculo = ?1 and v.fecha_fin <> (select max(p.fecha_fin) from VehiculoPlacas p) order by v.fecha_inicio asc")
	public List<VehiculoPlacas> findPlaca(Long id_vehiculo);
	
	@Query("select v from VehiculoPlacas v inner join Vehiculo l on v.vehiculo.id_vehiculo = l.id_vehiculo where v.fecha_fin = (select max(p.fecha_fin) from VehiculoPlacas p) and l.id_vehiculo = ?1")
	public List<VehiculoPlacas> findPlacaAnt(Long id_vehiculo);
	
}
