package com.PGJ.SGV.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.Resguardante;



public interface IResguardanteDao extends PagingAndSortingRepository<Resguardante, Long> {
	
		
		@Query("SELECT p.id_resguardante FROM Resguardante p WHERE p.id_resguardante=(SELECT MAX (p.id_resguardante) FROM Resguardante p)")
		public int ultimoId();
		
		@Query("select r from Resguardante r inner join Vehiculo v on r.vehiculo.id_vehiculo = v.id_vehiculo inner join TipoResguardante t on r.tipo_resguardante.id = t.id where v.id_vehiculo = ?1")
		public Page<Resguardante> findAllByVehiculo(long id_vehiculo, Pageable pageable);
		
		@Query("select p FROM Resguardante p WHERE p.activo = true")
		public List<Resguardante> findActivos();
		
		@Query("select r from Resguardante r inner join Vehiculo v on r.vehiculo.id_vehiculo = v.id_vehiculo "
				+ "inner join TipoResguardante t on r.tipo_resguardante.id = t.id where v.id_vehiculo = ?1 and r.fecha_fin is null and r.activo=true")
		public List<Resguardante> findResg(Long id_vehiculo);


}
