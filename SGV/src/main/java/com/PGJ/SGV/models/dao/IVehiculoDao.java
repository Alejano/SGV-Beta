package com.PGJ.SGV.models.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.Vehiculo;


public interface IVehiculoDao extends PagingAndSortingRepository<Vehiculo,Long > {	
	
	@Query("select v from Vehiculo v inner join VehiculoMarca m on v.vehiculo_marca.id_marca = m.id_marca inner join VehiculoEstado e on v.vehiculo_estado.id_estado = e.id_estado where m.clase like ?1 and e.id_estado='1'")
	public Page<Vehiculo> findTVechiulo(String vehiculo,Pageable pageable);
	
	@Query("select p from Vehiculo p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion inner join VehiculoEstado e on p.vehiculo_estado.id_estado = e.id_estado where a.id_adscripcion = ?1 and e.id_estado != '5'")
	public List<Vehiculo> findVehiculosArea(Long id_adscripcion);
	
	@Query("select p from Vehiculo p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion  inner join VehiculoEstado e on p.vehiculo_estado.id_estado = e.id_estado where adscripcion_id_adscripcion= ?1")
	public Page<Vehiculo> findVehiculosAreaPage(Long id_adscripcion,Pageable pageable);
	
	@Query("select v from Vehiculo v inner join Adscripcion a on v.adscripcion.id_adscripcion = a.id_adscripcion inner join VehiculoEstado e on v.vehiculo_estado.id_estado = e.id_estado where a.nombre_adscripcion like %?1% or v.placa like %?1% or v.no_serie like %?1% or v.no_inventario like %?1% or v.fecha_tarjeta like %?1% or v.vale like %?1% and e.id_estado = '1'")
	public Page<Vehiculo> findVehElemntoPage(String elemento,Pageable pageable);
	
	@Query("select v from Vehiculo v inner join Adscripcion a on v.adscripcion.id_adscripcion = a.id_adscripcion inner join VehiculoEstado e on v.vehiculo_estado.id_estado = e.id_estado where a.id_adscripcion = ?1 and (v.placa like %?2% or v.no_serie like %?2% or v.no_inventario like %?2% or v.fecha_tarjeta like %?2% or v.vale like %?2%)")
	public Page<Vehiculo> findVehElemAreaPage(Long id_adscripcion,String elemento,Pageable pageable);
	
	@Query("select count(v) from Vehiculo v")
	public Long TotalVehiculos();
	
	@Query("select count(p) from Vehiculo p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion like ?1")
	public int TotalVehiculosArea(Long id_adscripcion);
		
	@Query("select p.id_vehiculo from Vehiculo p where p.id_vehiculo = (select max(p.id_vehiculo) from Vehiculo p)")
	public int ultimoId();
	
	@Query("select v from Vehiculo v where v.placa = ?1 ")
	public Vehiculo findByPlaca(String placa);
	
	@Query("select v.placa from Vehiculo v where v.id_vehiculo = ?1 ")
	public String findPlaca(long id_vehiculo);
	
	
}
