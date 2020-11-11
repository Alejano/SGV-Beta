package com.PGJ.SGV.models.dao;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.PGJ.SGV.models.entity.Revista;



public interface IRevistaDao extends CrudRepository<Revista, Long>{		

	@Query("select r from Revista r where cast(now() as date) <= cast(fecha_ini as date)")
	public List<Revista> findAllFuturo();
	
	@Query("select r from Revista r where r.vehiculo.id_vehiculo = ?1 and r.evento = ?2 and r.fecha_inicio = ?3")
	public Optional<Long> BuscarId(Long id_vehiculo, Long evento, String fecha_ini);
	
}
