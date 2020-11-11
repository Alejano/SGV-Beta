package com.PGJ.SGV.models.dao;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.PGJ.SGV.models.entity.Adscripcion;



public interface IAdscripDao extends CrudRepository<Adscripcion, Long>{		

	@Query("select count(a) from Adscripcion a")
	public Long adscripcionestotales();
	
}
