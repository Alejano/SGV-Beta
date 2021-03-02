package com.PGJ.SGV.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.PGJ.SGV.models.entity.Notificacion;

public interface INotificacionDao extends PagingAndSortingRepository<Notificacion, Long>{
	
	
	@Query("select n from Notificacion n where CAST(n.fecha_actual as date) = current_date and n.tipo like 'siniestro' order by n.id_notificacion asc")
	public List<Notificacion> NotifyRegSin();
	
	@Query("select n from Notificacion n where CAST(n.fecha_actual as date) = current_date and n.tipo like 'mantenimiento' order by n.id_notificacion asc")
	public List<Notificacion> NotifyRegMant();
	
}
