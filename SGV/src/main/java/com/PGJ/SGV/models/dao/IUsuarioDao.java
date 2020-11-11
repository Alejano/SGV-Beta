package com.PGJ.SGV.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.PGJ.SGV.models.entity.Usuario;


public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, String> {
	
	@Query("select p,a from Usuario p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where p.no_empleado = ?1")
	public Usuario findByid_adscripcion(String no_empleado);
	
	@Query("select p,a from Usuario p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where p.no_empleado like %?1%")
	public Page<Usuario> findByAreaPage(String no_empleado,Pageable pageable);
	
	@Query("select u from Usuario u inner join Adscripcion a on u.adscripcion.id_adscripcion = a.id_adscripcion where u.fecha_baja is null and (u.no_empleado like %?1% or u.nombre like %?1% or u.apellido1 like %?1% or u.apellido2 like %?1% or a.nombre_adscripcion like %?1%)")
	public Page<Usuario> finUsuElemntPage(String elemento,Pageable pageable);
	
	@Query("select u from Usuario u inner join Adscripcion a on u.adscripcion.id_adscripcion = a.id_adscripcion where u.fecha_baja is not null and (u.no_empleado like %?1% or u.nombre like %?1% or u.apellido1 like %?1% or u.apellido2 like %?1% or a.nombre_adscripcion like %?1%)")
	public Page<Usuario> finUsuElemntBajasPage(String elemento,Pageable pageable);
	
	@Query("select count(u) from Usuario u where fecha_baja is null")
	public Long totalUsuarios();
	
	@Query("select count(u) from Usuario u where fecha_baja is not null")
	public Long totalUsuariosBajas();
	
	@Query("select count(u) from Usuario u inner join Adscripcion a on u.adscripcion.id_adscripcion = a.id_adscripcion where u.fecha_baja is null and (u.no_empleado like %?1% or u.nombre like %?1% or u.apellido1 like %?1% or u.apellido2 like %?1% or a.nombre_adscripcion like %?1%)")
	public Long totalfinUsuElemnt(String elemento);
	
	@Query("select count(u) from Usuario u inner join Adscripcion a on u.adscripcion.id_adscripcion = a.id_adscripcion where u.fecha_baja is not null and (u.no_empleado like %?1% or u.nombre like %?1% or u.apellido1 like %?1% or u.apellido2 like %?1% or a.nombre_adscripcion like %?1%)")
	public Long totalfinUsuElemntBajas(String elemento);
	
	@Query("select p from Usuario p where fecha_baja is null")
	public Page<Usuario> findByNl(Pageable pageable);
	
	@Query("select p from Usuario p where fecha_baja is not null")
	public Page<Usuario> findByNn(Pageable pageable);
	
	@Query("select p from Usuario p where fecha_alta is not null")
	public Page<Usuario> findByReg(Pageable pageable);
	
}
