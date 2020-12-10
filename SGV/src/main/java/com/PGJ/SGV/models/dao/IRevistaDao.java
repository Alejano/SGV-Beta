package com.PGJ.SGV.models.dao;



import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.PGJ.SGV.models.entity.Revista;



public interface IRevistaDao extends CrudRepository<Revista, Long>{		

	@Query("select r from Revista r where cast(now() as date) <= cast(fecha_ini as date)")
	public List<Revista> findAllFuturo();	
	
	@Query("select r from Revista r where r.evento_id = ?1")
	public List<Revista> revistaEvento(Long evento);
	
	@Query("delete from Revista r where r.evento_id = ?1")
	public List<Revista> deleteAllbyEvento(Long evento);
	
	@Query("select r from Revista r inner join Vehiculo v on r.vehiculo.id_vehiculo = v.id_vehiculo where v.id_vehiculo = ?1 and evento_id = (select max(evento_id) from Revista)")
	public Revista UltimaRevistaVehiculo(Long id_vehiculo);
	
}
