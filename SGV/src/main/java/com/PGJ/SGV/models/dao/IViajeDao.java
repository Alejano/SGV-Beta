package com.PGJ.SGV.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.PGJ.SGV.models.entity.Viaje;

	
public interface IViajeDao extends PagingAndSortingRepository<Viaje, Long> {	

	@Query("select v from Viaje v inner join Vehiculo l on v.vehiculo.id_vehiculo = l.id_vehiculo inner join Adscripcion a on l.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion = ?1")
	public Page<Viaje> ViajesAreaPage(Long id_adscripcion,Pageable pageable);

	@Query("select v from Viaje v inner join Vehiculo l on v.vehiculo.id_vehiculo = l.id_vehiculo inner join Adscripcion a on l.adscripcion.id_adscripcion=a.id_adscripcion where l.placa like %?1% or v.finicial_registro like %?1% or v.ffinal_registro like %?1% or cast(v.kilometraje_inicial AS string) like %?1% or cast(v.kilometraje_final AS string) like %?1% or cast(v.distancia_mensual AS string) like %?1% or a.nombre_adscripcion like %?1%")
	public Page<Viaje> ViajeElemPage(String elemento,Pageable pageable);
	
	@Query("select v from Viaje v inner join Vehiculo l on v.vehiculo.id_vehiculo = l.id_vehiculo inner join Adscripcion a on l.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion = ?1 and (l.placa like %?2% or v.finicial_registro like %?2% or v.ffinal_registro like %?2% or cast(v.kilometraje_inicial AS string) like %?2% or cast(v.kilometraje_final AS string) like %?2% or cast(v.distancia_mensual AS string) like %?2% )")
	public Page<Viaje> ViajesElemAreaPage(Long id_adscripcion,String elemento,Pageable pageable);
	
	@Query("select count(v) from Viaje v")
	public Long viajestotales();
	
	@Query("select count(v) from Viaje v inner join Vehiculo l on v.vehiculo.id_vehiculo=l.id_vehiculo inner join Adscripcion a on l.adscripcion.id_adscripcion=a.id_adscripcion where l.placa like %?1% or v.finicial_registro like %?1% or v.ffinal_registro like %?1% or cast(v.kilometraje_inicial AS string) like %?1% or cast(v.kilometraje_final AS string) like %?1% or cast(v.distancia_mensual AS string) like %?1%  or a.nombre_adscripcion like %?1%")
	public Long totalviajeElem(String elemento);
	
	@Query("select count(v) from Viaje v inner join Vehiculo l on v.vehiculo.id_vehiculo = l.id_vehiculo inner join Adscripcion a on l.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion=?1")
	public Long totalViajesArea(Long id_adscripcion);
	
	@Query("select count(v) from Viaje v inner join Vehiculo l on v.vehiculo.id_vehiculo = l.id_vehiculo inner join Adscripcion a on l.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion = ?1 and (l.placa like %?2% or v.finicial_registro like %?2% or v.ffinal_registro like %?2% or cast(v.kilometraje_inicial AS string) like %?2% or cast(v.kilometraje_final AS string) like %?2% or cast(v.distancia_mensual AS string) like %?2% )")
	public Long totalViajesElemArea(Long id_adscripcion,String elemento);
	
	@Query("select max(v.ffinal_registro) from Viaje v inner join Vehiculo l on v.vehiculo.id_vehiculo = l.id_vehiculo where l.id_vehiculo = ?1")
	public String fechamax (Long id_vehiculo);
	
	@Query("select v from Viaje v inner join Vehiculo l on v.vehiculo.id_vehiculo = l.id_vehiculo where l.id_vehiculo = ?1")
	public Page<Viaje> FindviajeVehi(Long id_vehiculo,Pageable pageable);
	
	@Query("select v from Viaje v inner join Vehiculo l on v.vehiculo.id_vehiculo = l.id_vehiculo inner join Adscripcion a on l.adscripcion.id_adscripcion=a.id_adscripcion where l.id_vehiculo = ?1 and (l.placa like %?2% or v.finicial_registro like %?2% or v.ffinal_registro like %?2% or cast(v.kilometraje_inicial AS string) like %?2% or cast(v.kilometraje_final AS string) like %?2% or cast(v.distancia_mensual AS string) like %?2% )")
	public Page<Viaje> ViajeElemVehiPage(Long id_vehiculo,String elemento,Pageable pageable);
	
}
