package com.PGJ.SGV.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.PGJ.SGV.models.entity.Seguro;

public interface ISeguroDao extends PagingAndSortingRepository<Seguro, Long> {

	@Query("select s from Seguro s inner join Vehiculo v on s.vehiculo.id_vehiculo = v.id_vehiculo where v.id_vehiculo = ?1")
	public Page<Seguro> FindsegVehi(Long id_vehiculo,Pageable pageable);
	
	@Query("select s from Seguro s inner join Vehiculo v on s.vehiculo.id_vehiculo = v.id_vehiculo inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion = ?1")
	public Page<Seguro> FindSeguroAreaPage(Long id_adscripcion,Pageable pageable);
	
	@Query("select s from Seguro s inner join Vehiculo v on s.vehiculo.id_vehiculo = v.id_vehiculo inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where v.id_vehiculo = ?1 and a.id_adscripcion = ?2")
	public Page<Seguro> FindsegVehiArea(Long id_vehiculo,Long id_adscripcion,Pageable pageable);
	
	@Query("select count(s) from Seguro s")
	public Long totalSeguro();
	
	@Query("select count(s) from Seguro s inner join Vehiculo v on s.vehiculo.id_vehiculo = v.id_vehiculo inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion = ?1")
	public Long totalSeguroAreaPage(Long id_adscripcion);
	
	@Query("select count(s) from Seguro s inner join Vehiculo v on s.vehiculo.id_vehiculo = v.id_vehiculo inner join Aseguradora p on s.aseguradora.id_aseguradora=p.id_aseguradora where v.id_vehiculo = ?1 and (v.placa like %?2% or cast(p.no_poliza_padre AS string) like %?2% or p.nombre_aseguradora like %?2% or cast(s.no_poliza_hijo AS string) like %?2% or s.nombre_contratante like %?2% or s.rfc_contratante like %?2% or s.codigo_cliente like %?2%)")	
	public Long totalSegElemVehiPage(Long id_vehiculo,String elemento);
	
	@Query("select count(s) from Seguro s inner join Vehiculo v on s.vehiculo.id_vehiculo = v.id_vehiculo inner join Aseguradora p on s.aseguradora.id_aseguradora=p.id_aseguradora inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion = ?1 and (v.placa like %?2% or cast(p.no_poliza_padre AS string) like %?2% or p.nombre_aseguradora like %?2% or cast(s.no_poliza_hijo AS string) like %?2% or s.nombre_contratante like %?2% or s.rfc_contratante like %?2% or s.codigo_cliente like %?2%)")
	public Long totalSegElemenAreaPage(Long id_adscripcion,String elemento);
	
	@Query("select count(s) from Seguro s inner join Vehiculo v on s.vehiculo.id_vehiculo = v.id_vehiculo inner join Aseguradora p on s.aseguradora.id_aseguradora=p.id_aseguradora where v.placa like %?1% or cast(p.no_poliza_padre AS string) like %?1% or p.nombre_aseguradora like %?1% or cast(s.no_poliza_hijo AS string) like %?1% or s.nombre_contratante like %?1% or s.rfc_contratante like %?1% or s.codigo_cliente like %?1%")
	public Long totalSegElemenPage(String elemento);

	@Query("select s from Seguro s inner join Vehiculo v on s.vehiculo.id_vehiculo = v.id_vehiculo inner join Aseguradora p on s.aseguradora.id_aseguradora=p.id_aseguradora where v.id_vehiculo = ?1 and (v.placa like %?2% or cast(p.no_poliza_padre AS string) like %?2% or p.nombre_aseguradora like %?2% or cast(s.no_poliza_hijo AS string) like %?2% or s.nombre_contratante like %?2% or s.rfc_contratante like %?2% or s.codigo_cliente like %?2%)")	
	public Page<Seguro> FindSegElemVehiPage(Long id_vehiculo,String elemento,Pageable pageable);
	
	@Query("select s from Seguro s inner join Vehiculo v on s.vehiculo.id_vehiculo = v.id_vehiculo inner join Aseguradora p on s.aseguradora.id_aseguradora=p.id_aseguradora inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where v.id_vehiculo = ?1 and a.id_adscripcion = ?2 and (v.placa like %?3% or cast(p.no_poliza_padre AS string) like %?3% or p.nombre_aseguradora like %?3% or cast(s.no_poliza_hijo AS string) like %?3% or s.nombre_contratante like %?3% or s.rfc_contratante like %?3% or s.codigo_cliente like %?3%)")	
	public Page<Seguro> FindSegElemVehiAreaPage(Long id_vehiculo,Long id_adscripcion,String elemento,Pageable pageable);

	@Query("select s from Seguro s inner join Vehiculo v on s.vehiculo.id_vehiculo = v.id_vehiculo inner join Aseguradora p on s.aseguradora.id_aseguradora=p.id_aseguradora inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion = ?1 and (v.placa like %?2% or cast(p.no_poliza_padre AS string) like %?2% or p.nombre_aseguradora like %?2% or cast(s.no_poliza_hijo AS string) like %?2% or s.nombre_contratante like %?2% or s.rfc_contratante like %?2% or s.codigo_cliente like %?2%)")
	public Page<Seguro> FindSegElemenAreaPage(Long id_adscripcion,String elemento,Pageable pageable);
	
	@Query("select s from Seguro s inner join Vehiculo v on s.vehiculo.id_vehiculo = v.id_vehiculo inner join Aseguradora p on s.aseguradora.id_aseguradora=p.id_aseguradora where v.placa like %?1% or cast(p.no_poliza_padre AS string) like %?1% or p.nombre_aseguradora like %?1% or cast(s.no_poliza_hijo AS string) like %?1% or s.nombre_contratante like %?1% or s.rfc_contratante like %?1% or s.codigo_cliente like %?1%")
	public Page<Seguro> FindSegElemenPage(String elemento,Pageable pageable);


}
