package com.PGJ.SGV.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.Adscripcion;
import com.PGJ.SGV.models.entity.Resguardante;

import com.PGJ.SGV.models.entity.Vehiculo;



public interface IResguardanteDao extends PagingAndSortingRepository<Resguardante, Long> {
	
		
		@Query("SELECT p.id_resguardante FROM Resguardante p WHERE p.id_resguardante=(SELECT MAX (p.id_resguardante) FROM Resguardante p)")
		public int ultimoId();
		
		@Query("select r from Resguardante r inner join Vehiculo v on r.vehiculo.id_vehiculo = v.id_vehiculo inner join TipoResguardante t on r.tipo_resguardante.id = t.id where v.id_vehiculo = ?1")
		public Page<Resguardante> findAllByVehiculo(long id_vehiculo, Pageable pageable);
		
	    //@Query("select p FROM Resguardante p WHERE p.activo = true order by p.id_resguardante ASC")
		//public List<Resguardante> findActivos();
		
		@Query("select p FROM Resguardante p WHERE p.activo = true and p.vehiculo.id_vehiculo=?1 order by p.id_resguardante ASC")
		public List<Resguardante> findActivos(Long id_vehiculo);
		
		@Query("select r from Resguardante r inner join Vehiculo v on r.vehiculo.id_vehiculo = v.id_vehiculo "
				+ "inner join TipoResguardante t on r.tipo_resguardante.id = t.id where v.id_vehiculo = ?1 and r.fecha_fin is null and r.activo=true")
		public List<Resguardante> findResg(Long id_vehiculo);

		@Query("select count(r) from Resguardante r")
		public Long resguardantestotales();
		
		@Query("select count(r) from Resguardante r where r.vehiculo.id_vehiculo = ?1 ")
		public Long resguardantespvtotales(Long id_vehiculo);
		
		@Query("select r from Resguardante r inner join Adscripcion a on r.adscripcion.id_adscripcion = a.id_adscripcion "
	   			+ "inner join Vehiculo v on r.vehiculo.id_vehiculo=v.id_vehiculo where a.id_adscripcion=?1 order by r.id_resguardante asc")
		public  List<Resguardante> findResgAll1(Long id_adscripcion);
		
		@Query("select r from Resguardante r inner join Adscripcion a on r.adscripcion.id_adscripcion = a.id_adscripcion "
	   			+ "inner join Vehiculo v on r.vehiculo.id_vehiculo=v.id_vehiculo order by r.id_resguardante asc")
		public  List<Integer> findResgIds();
		
		/*@Query("select r from Resguardante r inner join Adscripcion a on r.adscripcion.id_adscripcion = a.id_adscripcion "
	   			+ "inner join Vehiculo v on r.vehiculo.id_vehiculo=v.id_vehiculo where r.id_resguardante= ?1 order by r.id_resguardante asc")
		public  List<Resguardante> findResgInd(Long id_resguardante);*/
		
		/*@Query("select r from Resguardante r inner join Adscripcion a on r.adscripcion.id_adscripcion = a.id_adscripcion "
	   			+ "inner join Vehiculo v on r.vehiculo.id_vehiculo=v.id_vehiculo inner join VehiculoMarca m on v.vehiculo_marca.id_marca = m.id_marca "
	   			+ "inner join VehiculoDetalle d on Vehiculo v on v.id_vehiculo = d.vehiculo.id_vehiculo where r.id_resguardante= ?1 order by r.id_resguardante asc")*/
		
		@Query("select r from Resguardante r inner join Adscripcion a on r.adscripcion.id_adscripcion = a.id_adscripcion "
	   			+ "inner join Vehiculo v on r.vehiculo.id_vehiculo=v.id_vehiculo  where r.id_resguardante= ?1 order by r.id_resguardante asc")
		public  List<Resguardante> findResgInd(Long id_resguardante);
		
}
