package com.PGJ.SGV.models.dao;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.PGJ.SGV.models.entity.Evento;



public interface IEventoDao extends CrudRepository<Evento, Long>{		
	
	@Query("select p.id from Evento p where p.id = (select max(p.id) from Evento p)")	
	public Long ultimoId();
	
	@Query("select e from Evento e")
	public List<Evento> allJson();
}
