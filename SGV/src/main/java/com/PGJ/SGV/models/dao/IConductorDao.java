package com.PGJ.SGV.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.PGJ.SGV.models.entity.Conductor;


public interface IConductorDao extends PagingAndSortingRepository<Conductor, String> {
	
	@Query("select p from Conductor p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion like ?1")
	public List<Conductor> findConductorArea(Long id_adscripcion);
	
	@Query("select p from Conductor p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion = ?1 and p.fecha_baja is null")
	public Page<Conductor> findConductorAreaPage(Pageable pageable,Long id_adscripcion); 
	
	@Query("select p from Conductor p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion = ?1 and p.fecha_baja is not null")
	public Page<Conductor> findConductorAreaBajasPage(Pageable pageable,Long id_adscripcion);
	
	@Query("select p from Conductor p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where p.fecha_baja is null and (p.no_empleado like %?1% or p.nombre like %?1% or p.apellido1 like %?1% or p.apellido2 like %?1% or p.adscripcion.nombre_adscripcion like %?1%)")
	public Page<Conductor> findCondElemnPage(String elemento,Pageable pageable);

	@Query("select p from Conductor p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where p.fecha_baja is not null and (p.no_empleado like %?1% or p.nombre like %?1% or p.apellido1 like %?1% or p.apellido2 like %?1% or p.adscripcion.nombre_adscripcion like %?1%)")
	public Page<Conductor> findCondElemnBajasPage(String elemento,Pageable pageable);
	
	@Query("select p from Conductor p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion= ?1 and p.fecha_baja is null and (p.no_empleado like %?2% or p.nombre like %?2% or p.apellido1 like %?2% or p.apellido2 like %?2%)")
	public Page<Conductor> findCondElemnAreaPage(Long id_adscripcion,String elemento,Pageable pageable);
	
	@Query("select p from Conductor p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion= ?1 and p.fecha_baja is not null and (p.no_empleado like %?2% or p.nombre like %?2% or p.apellido1 like %?2% or p.apellido2 like %?2%)")
	public Page<Conductor> findCondElemnAreaBajasPage(Long id_adscripcion,String elemento,Pageable pageable);
		
	@Query("select count(c) from Conductor c where fecha_baja is null")
	public Long totalConductor();
	
	@Query("select count(c) from Conductor c where fecha_baja is not null")
	public Long totalConductorBajas();
	
	@Query("select count(c) from Conductor c inner join Adscripcion a on c.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion = ?1 and c.fecha_baja is null")
	public Long totalConductorArea(Long id_adscripcion); 
	
	@Query("select count(c) from Conductor c inner join Adscripcion a on c.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion = ?1 and c.fecha_baja is not null")
	public Long totalConductorAreaBajas(Long id_adscripcion);
	
	@Query("select count(c) from Conductor c inner join Adscripcion a on c.adscripcion.id_adscripcion = a.id_adscripcion where c.fecha_baja is null and (c.no_empleado like %?1% or c.nombre like %?1% or c.apellido1 like %?1% or c.apellido2 like %?1% or c.adscripcion.nombre_adscripcion like %?1%)")
	public Long totalfindCondElemnPage(String elemento);
	
	@Query("select count(c) from Conductor c inner join Adscripcion a on c.adscripcion.id_adscripcion = a.id_adscripcion where c.fecha_baja is not null and (c.no_empleado like %?1% or c.nombre like %?1% or c.apellido1 like %?1% or c.apellido2 like %?1% or c.adscripcion.nombre_adscripcion like %?1%)")
	public Long totalfindCondBajaElemnPage(String elemento);
	
	@Query("select count(c) from Conductor c inner join Adscripcion a on c.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion= ?1 and c.fecha_baja is null and (c.no_empleado like %?2% or c.nombre like %?2% or c.apellido1 like %?2% or c.apellido2 like %?2% or c.adscripcion.nombre_adscripcion like %?2%)")
	public Long totalfindCondElemnAreaPage(Long id_adscripcion,String elemento);
	
	@Query("select count(c) from Conductor c inner join Adscripcion a on c.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion= ?1 and c.fecha_baja is not null and (c.no_empleado like %?2% or c.nombre like %?2% or c.apellido1 like %?2% or c.apellido2 like %?2% or c.adscripcion.nombre_adscripcion like %?2%)")
	public Long totalfindCondBajaElemnAreaPage(Long id_adscripcion,String elemento);
	
	@Query("select p from Conductor p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion like ?1 and p.enabled = '1'")
	public List<Conductor> findConductorAreaEstado(Long id_adscripcion);
	
	@Query("select p from Conductor p where p.enabled = '1'")
	public List<Conductor> findConductorEstado();
	
	@Query("select p from Conductor p where fecha_baja is null")
	public Page<Conductor> findByCNl(Pageable pageable);
	
	@Query("select p from Conductor p where fecha_baja is not null")
	public Page<Conductor> findByCNn(Pageable pageable);
	
	
}
