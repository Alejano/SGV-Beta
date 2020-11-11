package com.PGJ.SGV.models.dao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.PGJ.SGV.models.entity.Eventos;



public interface IEventoDao extends CrudRepository<Eventos, Long>{		

	@Query("select p.id from Eventos p where p.id = (select max(p.id) from Eventos p)")
	public int ultimoId();
}
