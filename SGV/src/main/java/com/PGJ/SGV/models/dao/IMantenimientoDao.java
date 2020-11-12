package com.PGJ.SGV.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.Mantenimiento;

public interface IMantenimientoDao extends PagingAndSortingRepository<Mantenimiento, Long>{

	@Query("select m from Mantenimiento m inner join Vehiculo v on m.vehiculo.id_vehiculo = v.id_vehiculo inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion = ?1")
	public List<Mantenimiento> FindMantenimientoArea(Long id_adscripcion);
	
	@Query("select m from Mantenimiento m inner join Vehiculo v on m.vehiculo.id_vehiculo = v.id_vehiculo inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion = ?1")
	public Page<Mantenimiento> FindMantenimientoAreaPage(Long id_adscripcion,Pageable pageable);
	
	
	@Query("select m from Mantenimiento m inner join Vehiculo v on m.vehiculo.id_vehiculo = v.id_vehiculo inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where v.id_vehiculo = ?1")
	public List<Mantenimiento> FindMantPlaca(long id_vehiculo);
	
	@Query("select m from Mantenimiento m inner join Vehiculo v on m.vehiculo.id_vehiculo = v.id_vehiculo inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where v.id_vehiculo = ?1 and a.id_adscripcion = ?2")
	public List<Mantenimiento> FindMantPlacaAds(long id_vehiculo ,Long id_adscripcion);
	
	@Query("select m from Mantenimiento m inner join Vehiculo v on m.vehiculo.id_vehiculo = v.id_vehiculo inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where v.id_vehiculo = ?1")
	public Page<Mantenimiento> FindMantPlacaPage(Long id_vehiculo,Pageable pageable);
	
	@Query("select m from Mantenimiento m inner join Vehiculo v on m.vehiculo.id_vehiculo = v.id_vehiculo inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where v.id_vehiculo = ?1 and a.id_adscripcion = ?2")
	public Page<Mantenimiento> FindMantPlacaAreaPage(long id_vehiculo ,Long id_adscripcion,Pageable pageable);
	
	//busquedas en tablas
	
	@Query("select m from Mantenimiento m inner join Vehiculo v on m.vehiculo.id_vehiculo = v.id_vehiculo inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where v.placa like %?1% or m.fecha_entrega like %?1% or m.fecha_ingreso like %?1% or m.observaciones like %?1%")
	public Page<Mantenimiento> FindMantElemPageL(String elemento,Pageable pageable);
	
	@Query("select m from Mantenimiento m inner join Vehiculo v on m.vehiculo.id_vehiculo = v.id_vehiculo inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion like ?1 and (v.placa like %?2% or m.costo_mantenimiento like %?2% or m.fecha_entrega like %?2% or m.fecha_ingreso like %?2% or m.kilometraje like %?2% or m.observaciones like %?2%)")
	public Page<Mantenimiento> FindMantelemntAreaPage(Long id_adscripcion,String elemento ,Pageable pageable);
	
	@Query("select count(m) from Mantenimiento m")
	public Long totalMantenimiento();
	
	@Query("select count(m) from Mantenimiento m inner join Vehiculo v on m.vehiculo.placa = v.placa inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion like ?1")
	public int TotalMantenimientoArea(Long id_adscripcion);
	
	@Query("select MAX(m.id_mantenimiento) from Mantenimiento m")
	public Long ultimoRegistroMant();
	
	//notificacion
	@Query("select count(m) from Mantenimiento m where CAST(m.fecha_ingreso as date) = current_date")
	public Long NotificacionMant();
	
	@Query("select count(m) from Mantenimiento m where CAST(m.fecha_entrega as date) = current_date")
	public Long NotificacionMantEntrega();
	
}
