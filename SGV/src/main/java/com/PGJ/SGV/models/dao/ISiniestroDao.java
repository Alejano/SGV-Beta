package com.PGJ.SGV.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.Seguro;
import com.PGJ.SGV.models.entity.Siniestro;

public interface ISiniestroDao extends PagingAndSortingRepository<Siniestro, Long>{

	@Query("select count(s) from Siniestro s")
	public Long totalSiniestro();
	
	@Query("select s from Siniestro s inner join Vehiculo v on s.vehiculo.id_vehiculo = v.id_vehiculo where v.id_vehiculo = ?1")
	public Page<Siniestro> FindsegVehi(Long id_vehiculo,Pageable pageable);
	
	@Query("select s from Siniestro s inner join Vehiculo v on s.vehiculo.id_vehiculo = v.id_vehiculo where v.id_vehiculo = ?1 and (v.placa like %?2% or cast(s.numero_siniestro AS string) like %?2% or s.nombre_conductor like %?2% or s.apellido1_conductor like %?2% or s.apellido2_conductor like %?2% or s.fecha_siniestro like %?2% or s.fecha_ingreso_taller like %?2% or s.fecha_salida_taller like %?2%)")	
	public Page<Siniestro> FindSinElemVehiPage(Long id_vehiculo,String elemento,Pageable pageable);
		
	@Query("select s from Siniestro s inner join Vehiculo v on s.vehiculo.id_vehiculo = v.id_vehiculo inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion = ?1 and (v.placa like %?2% or cast(s.numero_siniestro AS string) like %?2% or s.nombre_conductor like %?2% or s.apellido1_conductor like %?2% or s.apellido2_conductor like %?2% or s.fecha_siniestro like %?2% or s.fecha_ingreso_taller like %?2% or s.fecha_salida_taller like %?2%)")
	public Page<Siniestro> FindSinElemenAreaPage(Long id_adscripcion,String elemento,Pageable pageable);
	
	@Query("select s from Siniestro s inner join Vehiculo v on s.vehiculo.id_vehiculo = v.id_vehiculo where v.placa like %?1% or cast(s.numero_siniestro AS string) like %?1% or s.nombre_conductor like %?1% or s.apellido1_conductor like %?1% or s.apellido2_conductor like %?1% or s.fecha_siniestro like %?1% or s.fecha_ingreso_taller like %?1% or s.fecha_salida_taller like %?1%")
	public Page<Siniestro> FindSinElemenPage(String elemento,Pageable pageable);
	
}
